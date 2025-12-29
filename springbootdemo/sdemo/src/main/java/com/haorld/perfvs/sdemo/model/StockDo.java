package com.haorld.perfvs.sdemo.model;

import java.io.Serializable;

public class StockDo implements Serializable {

    private static final long serialVersionUID = 1L;
    Long itemId;

    public StockDo() {
    }

    public StockDo(Long itemId, Integer price, Integer stock) {
        this.itemId = itemId;
        this.price = price;
        this.stock = stock;
    }

    Integer price;
    Integer stock;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
