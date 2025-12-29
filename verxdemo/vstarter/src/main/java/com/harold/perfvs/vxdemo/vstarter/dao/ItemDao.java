package com.harold.perfvs.vxdemo.vstarter.dao;

import com.harold.perfvs.vxdemo.vstarter.model.ItemDo;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

public class ItemDao {

    private Pool jdbcPool;

    public ItemDao(Pool jdbcPool) {
        this.jdbcPool = jdbcPool;
    }

    public Future<ItemDo> findById(Long id) {
        return Future.future(promise -> {
            String sql = "SELECT * FROM item_info WHERE id = ?";
            jdbcPool.preparedQuery(sql)
                    .execute(Tuple.of(id))
                    .onSuccess(rowSet -> {
                        if (rowSet.size() == 0) {
                            promise.complete(null);
                        } else {
                            promise.complete(mapToItemDo(rowSet.iterator().next()));
                        }
                    })
                    .onFailure(promise::fail);
        });
    }

    private ItemDo mapToItemDo(Row row) {
        ItemDo item = new ItemDo();
        item.setItemId(row.getLong("id"));
        item.setItemName(row.getString("item_name"));
        item.setMarketPrice(row.getInteger("market_price"));
        return item;
    }
}