package com.haorld.perfvs.sdemo.service;

import com.haorld.perfvs.sdemo.dao.ItemDao;
import com.haorld.perfvs.sdemo.dao.OrderDao;
import com.haorld.perfvs.sdemo.dao.StockDao;
import com.haorld.perfvs.sdemo.model.ItemDo;
import com.haorld.perfvs.sdemo.model.OrderDo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class ItemOrderService {

    @Resource
    private ItemDao itemDao;

    @Resource
    private StockDao stockDao;

    @Resource
    private OrderDao orderDao;


    public ItemDo queryItem(Long itemId) {

        ItemDo itemDo = new ItemDo();

        itemDo = itemDao.getItemById(itemId);

        return itemDo;
    }


    public Boolean createOrder(Long itemId, Long buyerId, Integer quality) {

        return orderDao.quickCreateOrder(itemId, buyerId, quality);
    }

    public OrderDo queryOrder(Long orderId) {
        return orderDao.getOrderById(orderId);
    }

}
