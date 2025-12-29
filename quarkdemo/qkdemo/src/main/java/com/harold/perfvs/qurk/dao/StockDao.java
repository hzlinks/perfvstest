package com.harold.perfvs.qurk.dao;

import com.harold.perfvs.qurk.model.StockDo;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class StockDao extends BaseDao{

    @Inject
    AgroalDataSource ds;

    public StockDo getStockByItemId(Long itemId) {
        StockDo stockDo = null;
        String sql = "SELECT * FROM item_stock WHERE item_id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ds.getConnection();
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
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return stockDo;
    }
}
