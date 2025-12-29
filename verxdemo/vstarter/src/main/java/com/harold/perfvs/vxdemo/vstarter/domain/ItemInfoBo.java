package com.harold.perfvs.vxdemo.vstarter.domain;

import java.io.Serializable;

public class ItemInfoBo implements Serializable {

    private static final long serialVersionUID = 1L;
    Long itemId;
    String itemName;
    Integer marketPrice;
    Integer price;
    Integer stock;


    public ItemInfoBo() {};

    public ItemInfoBo(Long itemId, String itemName, Integer marketPrice, Integer price, Integer stock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.marketPrice = marketPrice;
        this.price = price;
        this.stock = stock;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
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
