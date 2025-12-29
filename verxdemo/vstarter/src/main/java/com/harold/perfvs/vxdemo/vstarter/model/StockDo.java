package com.harold.perfvs.vxdemo.vstarter.model;

import java.io.Serializable;

public class StockDo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long itemId;
    Integer price;
    Integer stock;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}