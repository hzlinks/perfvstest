package com.harold.perfvs.qurk.dao;

import com.harold.perfvs.qurk.model.ItemDo;
import com.harold.perfvs.qurk.model.OrderDo;
import com.harold.perfvs.qurk.model.StockDo;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class OrderDao extends BaseDao{

    @Inject
    AgroalDataSource ds;

    @Inject
    ItemDao itemDao;
    @Inject
    StockDao stockDao;


    public boolean quickCreateOrder(Long itemId, Long buyerId, Integer quality) {
        ItemDo item = itemDao.getItemById(itemId);
        StockDo stock = stockDao.getStockByItemId(itemId);

        Integer price = stock.getPrice();
        Integer total = quality * price;
        OrderDo order = new OrderDo();
        order.setItemId(itemId);
        order.setItemName(item.getItemName());
        order.setBuyerId(buyerId);
        order.setPrice(price);
        order.setItemNum(quality);
        order.setTotalPrice(total);

        return createOrder(order);
    }

    // 创建订单
    public boolean createOrder(OrderDo order) {
        String sql = "INSERT INTO order_main (item_id, item_name, buyer_id, price, item_num, total_price) VALUES ( ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = ds.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, order.getItemId());
            ps.setString(2, order.getItemName());
            ps.setLong(3, order.getBuyerId());
            ps.setInt(4, order.getPrice());
            ps.setInt(5, order.getItemNum());
            ps.setInt(6, order.getTotalPrice());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("创建订单失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }


}
