package com.harold.perfvs.vxdemo.vstarter.dao;

import com.harold.perfvs.vxdemo.vstarter.model.OrderDo;
import io.vertx.core.Future;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class OrderDao {

    private final Pool jdbcPool;

    public OrderDao(Pool jdbcPool) {
        this.jdbcPool = jdbcPool;
    }

    public Future<OrderDo> createWithClient(OrderDo order) {
        return Future.future(promise -> {
            String sql = "INSERT INTO order_main (item_id, item_name, buyer_id, price, item_num, total_price) VALUES ( ?, ?, ?, ?, ?, ?)";
            jdbcPool.preparedQuery(sql)
                    .execute(Tuple.of(order.getItemId(),order.getItemName(),order.getBuyerId(),order.getPrice(),
                            order.getItemNum(), order.getTotalPrice()))
                    .onSuccess(result -> {
                        promise.complete(order);
                    })
                    .onFailure(promise::fail);
        });
    }
}
