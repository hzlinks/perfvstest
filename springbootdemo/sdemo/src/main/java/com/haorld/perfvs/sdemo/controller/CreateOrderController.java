package com.haorld.perfvs.sdemo.controller;

import com.haorld.perfvs.sdemo.service.ItemOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateOrderController {
    @Resource
    ItemOrderService itemOrderService;

    @PostMapping("/create")
    public Boolean createOrder(Long itemId,Long buyerId, Integer quality) {
        return itemOrderService.createOrder(itemId,buyerId, quality);
    }

}
