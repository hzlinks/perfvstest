package com.haorld.perfvs.sdemo.dao;

import com.haorld.perfvs.sdemo.model.StockDo;
import java.util.Random;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockDao extends BaseDao {


    public boolean batchStock(Integer times) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);

            System.out.println("===== 开始插入数据 =====");
            long startTime = System.currentTimeMillis();
            String sqlInsert = "INSERT INTO item_stock (item_id, stock, price) VALUES (?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlInsert);

            Random random = new Random();
            for (int i = 1; i <= times; i++) {
                // 设置参数
                preparedStatement.setLong(1, i);
                preparedStatement.setInt(2, random.nextInt(1000));
                preparedStatement.setInt(3, random.nextInt(100));

                // 添加到批处理中
                preparedStatement.addBatch();
                if (i % 1000 == 0) {
                    // 每1000条数据提交一次
                    preparedStatement.executeBatch();
                    connection.commit();
                    System.out.println("成功插入第 " + i + " 条数据");
                }

            }
            // 处理剩余的数据
            preparedStatement.executeBatch();
            connection.commit();
            long spendTime = System.currentTimeMillis() - startTime;
            System.out.println("成功插入 " + times + " 条数据,耗时：" + spendTime + "毫秒");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeJdbc(connection, preparedStatement);
        }
        return false;
    }

    // 创建库存记录
    public boolean createStock(StockDo stockDo) {
        String sql = "INSERT INTO item_stock (item_id, stock, price) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, stockDo.getItemId());
            ps.setInt(2, stockDo.getStock());
            ps.setInt(3, stockDo.getPrice());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("创建库存记录失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }



    // 根据商品ID获取库存信息
    public StockDo getStockByItemId(Long itemId) {
        StockDo stockDo = null;
        String sql = "SELECT * FROM item_stock WHERE item_id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, itemId);
            rs = ps.executeQuery();

            if (rs.next()) {
                stockDo = new StockDo();
                stockDo.setItemId(rs.getLong("item_id"));
                stockDo.setStock(rs.getInt("stock"));
                stockDo.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            System.out.println("查询库存信息失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        return stockDo;
    }

    // 获取所有库存信息
    public List<StockDo> getAllStocks() {
        List<StockDo> stocks = new ArrayList<>();
        String sql = "SELECT * FROM item_stock";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                StockDo stockDo = new StockDo();
                stockDo.setItemId(rs.getLong("item_id"));
                stockDo.setStock(rs.getInt("stock"));
                stockDo.setPrice(rs.getInt("price"));
                stocks.add(stockDo);
            }
        } catch (SQLException e) {
            System.out.println("查询所有库存信息失败: " + e.getMessage());
        } finally {
            closeJdbc(connection, ps);
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        return stocks;
    }

    // 更新库存信息
    public boolean updateStock(StockDo stockDo) {
        String sql = "UPDATE item_stock SET stock = ?, price = ? WHERE item_id = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, stockDo.getStock());
            ps.setInt(2, stockDo.getPrice());
            ps.setLong(3, stockDo.getItemId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("更新库存信息失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }

    // 删除库存记录
    public boolean deleteStock(Long itemId) {
        String sql = "DELETE FROM item_stock WHERE item_id = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setLong(1, itemId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("删除库存记录失败: " + e.getMessage());
            return false;
        } finally {
            closeJdbc(connection, ps);
        }
    }
}
