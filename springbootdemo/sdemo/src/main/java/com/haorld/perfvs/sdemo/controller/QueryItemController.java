package com.haorld.perfvs.sdemo.controller;

import com.haorld.perfvs.sdemo.model.ItemDo;
import com.haorld.perfvs.sdemo.service.ItemOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryItemController {

    @Resource
    ItemOrderService itemOrderService;

    @GetMapping("/query")
    public ItemDo index(Long itemId) {
        return itemOrderService.queryItem(itemId);
    }

}
