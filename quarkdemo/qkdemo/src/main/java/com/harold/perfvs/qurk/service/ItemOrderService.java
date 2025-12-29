package com.harold.perfvs.qurk.service;

import com.harold.perfvs.qurk.dao.ItemDao;
import com.harold.perfvs.qurk.dao.OrderDao;
import com.harold.perfvs.qurk.dao.StockDao;
import com.harold.perfvs.qurk.domain.ItemInfoBo;
import com.harold.perfvs.qurk.model.ItemDo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ItemOrderService {

    @Inject
    private ItemDao itemDao;

    @Inject
    private StockDao stockDao;

    @Inject
    private OrderDao orderDao;

    public ItemInfoBo getItemWithStock(Long itemId) {

        return itemDao.getItemWithStock(itemId);

    }

    public boolean createOrder(Long itemId, Long buyerId, Integer quantity) {

        return orderDao.quickCreateOrder(itemId, buyerId, quantity);
    }


}
