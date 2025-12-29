package com.harold.perfvs.qurk.model;

import java.io.Serializable;

public class ItemDo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long itemId;
    private String itemName;
    private Integer marketPrice;


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

}
