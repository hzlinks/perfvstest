package com.harold.perfvs.vxdemo.vstarter.dao;

import com.harold.perfvs.vxdemo.vstarter.model.StockDo;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

public class StockDao {

    private final Pool jdbcPool;

    public StockDao(Pool jdbcPool) {
        this.jdbcPool = jdbcPool;
    }

    public Future<StockDo> findByItemId(Long itemId) {
        return Future.future(promise -> {
            String sql = "SELECT * FROM item_stock WHERE item_id = ?";
            jdbcPool.preparedQuery(sql)
                    .execute(Tuple.of(itemId))
                    .onSuccess(rowSet -> {
                        if (rowSet.size() == 0) {
                            promise.complete(null);
                        } else {
                            promise.complete(mapToStockDo(rowSet.iterator().next()));
                        }
                    })
                    .onFailure(promise::fail);
        });
    }

    public Future<StockDo> findByItemIdWithClient(SqlConnection client, Long itemId) {
        return Future.future(promise -> {
            String sql = "SELECT * FROM item_stock WHERE item_id = ?";
            jdbcPool.preparedQuery(sql)
                    .execute(Tuple.of(itemId))
                    .onSuccess(rowSet -> {
                        if (rowSet.size() == 0) {
                            promise.complete(null);
                        } else {
                            promise.complete(mapToStockDo(rowSet.iterator().next()));
                        }
                    })
                    .onFailure(promise::fail);
        });
    }

    public Future<Boolean> updateWithClient(SqlConnection client, StockDo stock) {
        return Future.future(promise -> {
            String sql = "UPDATE item_stock SET stock = ? WHERE item_id = ?";
            jdbcPool.preparedQuery(sql)
                    .execute(Tuple.of(stock.getStock(), stock.getItemId()))
                    .onSuccess(result -> {
                        promise.complete(result.rowCount() > 0);
                    })
                    .onFailure(promise::fail);
        });
    }

    private StockDo mapToStockDo(Row row) {
        StockDo stock = new StockDo();
        stock.setPrice(row.getInteger("price"));
        stock.setItemId(row.getLong("item_id"));
        stock.setStock(row.getInteger("stock"));
        return stock;
    }
}