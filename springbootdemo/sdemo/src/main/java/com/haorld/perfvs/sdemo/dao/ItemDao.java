package com.haorld.perfvs.sdemo.dao;

import com.haorld.perfvs.sdemo.model.ItemDo;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemDao extends BaseDao{


    // 创建(新增)操作
    public boolean createItem(ItemDo item) {
        String sql = "INSERT INTO item_info (item_name, market_price) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getMarketPrice());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("创建商品失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }

    // 读取(查询单个)操作
    public ItemDo getItemById(Long itemId) {
        ItemDo itemDo = null;
        String sql = "SELECT * FROM item_info WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                itemDo = new ItemDo();
                itemDo.setItemId(rs.getLong("id"));
                itemDo.setItemName(rs.getString("item_name"));
                itemDo.setMarketPrice(rs.getInt("market_price"));
            }
        } catch (SQLException e) {
            System.out.println("查询商品失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        
        return itemDo;
    }

    // 读取(查询所有)操作
    public List<ItemDo> getAllItems() {
        List<ItemDo> items = new ArrayList<>();
        String sql = "SELECT * FROM item_info";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ItemDo itemDo = new ItemDo();
                itemDo.setItemId(rs.getLong("id"));
                itemDo.setItemName(rs.getString("item_name"));
                itemDo.setMarketPrice(rs.getInt("market_price"));
                items.add(itemDo);
            }
        } catch (SQLException e) {
            System.out.println("查询所有商品失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        
        return items;
    }

    // 更新操作
    public boolean updateItem(ItemDo item) {
        String sql = "UPDATE item_info SET item_name = ?, market_price = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getMarketPrice());
            ps.setLong(3, item.getItemId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("更新商品失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }

    // 删除操作
    public boolean deleteItem(Long itemId) {
        String sql = "DELETE FROM item_info WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, itemId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("删除商品失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }



}
