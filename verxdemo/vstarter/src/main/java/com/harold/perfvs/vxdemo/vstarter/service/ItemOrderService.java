package com.harold.perfvs.vxdemo.vstarter.service;

import com.harold.perfvs.vxdemo.vstarter.dao.ItemDao;
import com.harold.perfvs.vxdemo.vstarter.dao.OrderDao;
import com.harold.perfvs.vxdemo.vstarter.dao.StockDao;
import com.harold.perfvs.vxdemo.vstarter.domain.ItemInfoBo;
import com.harold.perfvs.vxdemo.vstarter.model.OrderDo;
import com.harold.perfvs.vxdemo.vstarter.model.StockDo;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import java.nio.file.Paths;

public class ItemOrderService {

    private final ItemDao itemDao;
    private final StockDao stockDao;
    private final OrderDao orderDao;

    private final Pool jdbcPool;

    public ItemOrderService(Vertx vertx) {

        String dbPath = Paths.get(System.getProperty("user.home"), "perf.db").toString();

        JDBCConnectOptions connectOptions = new JDBCConnectOptions ()
                .setJdbcUrl("jdbc:sqlite:" + dbPath);

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(10);

        jdbcPool = JDBCPool.pool(vertx, connectOptions, poolOptions);

        this.itemDao = new ItemDao(jdbcPool);
        this.stockDao = new StockDao(jdbcPool);
        this.orderDao = new OrderDao(jdbcPool);
    }


    public Future<ItemInfoBo> getItemWithStock(Long itemId) {

        return itemDao.findById(itemId)
                .flatMap(itemDO -> {
                    if (itemDO == null) {
                        return Future.failedFuture("商品不存在");
                    }
                    // 在 flatMap 中使用 itemDO 的信息
                    return stockDao.findByItemId(itemId)
                            .map(stockDO -> {
                                if (stockDO == null) {
                                    throw new RuntimeException("库存信息不存在");
                                }
                                return new ItemInfoBo(
                                        itemId,
                                        itemDO.getItemName(),
                                        itemDO.getMarketPrice(),
                                        stockDO.getStock(),
                                        stockDO.getStock()
                                );
                            });
                });

    }

    public Future<Boolean> createOrder(Long itemId, Integer quantity, Long buyerId) {

        return getItemWithStock(itemId).flatMap(itemInfoBo -> getItemWithStock(itemId).flatMap(itemInfoBo1 -> {
                    // 调用 createOrder 方法完成订单创建流程
                    OrderDo order = new OrderDo();
                    order.setItemId(itemId);
                    order.setItemName(itemInfoBo.getItemName());
                    order.setItemNum(quantity);
                    Integer price = itemInfoBo.getMarketPrice();
                    order.setPrice(price);
                    order.setTotalPrice(price * quantity);
                    order.setBuyerId(buyerId);
                    return orderDao.createWithClient(order)
                            .map(ret -> Boolean.TRUE)
                            .otherwise(Boolean.FALSE);
                })
        ).otherwise(Boolean.FALSE);
    }


}
