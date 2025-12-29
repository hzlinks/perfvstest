package com.harold.perfvs.qurk.dao;

import com.harold.perfvs.qurk.domain.ItemInfoBo;
import com.harold.perfvs.qurk.model.ItemDo;
import com.harold.perfvs.qurk.model.StockDo;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class ItemDao extends BaseDao{

    @Inject
    AgroalDataSource ds;

    @Inject
    StockDao stockDao;

    public ItemDo getItemById(Long itemId) {
        ItemDo itemDo = null;
        String sql = "SELECT * FROM item_info WHERE id = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ds.getConnection();
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


    public ItemInfoBo getItemWithStock(Long itemId)
    {
        ItemInfoBo itemInfoBo = new ItemInfoBo();
        StockDo stockDo = stockDao.getStockByItemId(itemId);
        ItemDo itemDo = getItemById(itemId);
        itemInfoBo.setItemId(itemDo.getItemId());
        itemInfoBo.setItemName(itemDo.getItemName());
        itemInfoBo.setMarketPrice(itemDo.getMarketPrice());
        itemInfoBo.setStock(stockDo.getStock());

        return itemInfoBo;

    }


}
