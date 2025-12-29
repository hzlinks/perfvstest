package com.haorld.perfvs.sdemo.dao;

import com.haorld.perfvs.sdemo.model.ItemDo;
import com.haorld.perfvs.sdemo.model.StockDo;
import jakarta.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;



import com.haorld.perfvs.sdemo.model.OrderDo;

@Service
public class OrderDao extends BaseDao {

    @Resource
    private ItemDao itemDao;

    @Resource
    private StockDao stockDao;


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
            connection = jdbcTemplate.getDataSource().getConnection();
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

    // 根据ID获取订单
    public OrderDo getOrderById(Long orderId) {
        OrderDo order = null;
        String sql = "SELECT * FROM order_main WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new OrderDo();
                order.setOrderId(rs.getLong("id"));
                order.setItemId(rs.getLong("item_id"));
                order.setItemName(rs.getString("item_name"));
                order.setBuyerId(rs.getLong("buyer_id"));
                order.setPrice(rs.getInt("price"));
                order.setItemNum(rs.getInt("item_num"));
                order.setTotalPrice(rs.getInt("total_price"));
            }
        } catch (SQLException e) {
            System.out.println("查询订单失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        return order;
    }

    // 获取所有订单
    public List<OrderDo> getAllOrders() {
        List<OrderDo> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_main";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDo order = new OrderDo();
                order.setOrderId(rs.getLong("id"));
                order.setItemId(rs.getLong("item_id"));
                order.setItemName(rs.getString("item_name"));
                order.setBuyerId(rs.getLong("buyer_id"));
                order.setPrice(rs.getInt("price"));
                order.setItemNum(rs.getInt("item_num"));
                order.setTotalPrice(rs.getInt("total_price"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("查询所有订单失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        return orders;
    }

    // 更新订单
    public boolean updateOrder(OrderDo order) {
        String sql = "UPDATE order_main SET item_id = ?, item_name = ?, buyer_id = ?, price = ?, item_num = ?, total_price = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, order.getItemId());
            ps.setString(2, order.getItemName());
            ps.setLong(3, order.getBuyerId());
            ps.setInt(4, order.getPrice());
            ps.setInt(5, order.getItemNum());
            ps.setInt(6, order.getTotalPrice());
            ps.setLong(7, order.getOrderId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("更新订单失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }

    // 删除订单
    public boolean deleteOrder(Long orderId) {
        String sql = "DELETE FROM order_main WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, orderId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("删除订单失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }
}
