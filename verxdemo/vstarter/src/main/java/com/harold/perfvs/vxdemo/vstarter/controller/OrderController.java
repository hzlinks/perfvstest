package com.harold.perfvs.vxdemo.vstarter.controller;

import com.harold.perfvs.vxdemo.vstarter.service.ItemOrderService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class OrderController {

    private final ItemOrderService itemOrderService;

    public OrderController(Vertx vertx) {
        this.itemOrderService = new ItemOrderService(vertx);
    }

    public void configureRoutes(Router router) {
        // 创建订单接口
        router.post("/create").handler(this::createOrder);

        // 查询订单接口
        router.get("/query").handler(this::queryItem);
    }

    private void createOrder(RoutingContext ctx) {
        try {
            Long itemId = Long.parseLong(ctx.request().getParam("itemId"));
            Long buyerId = Long.parseLong(ctx.request().getParam("buyerId"));
            Integer quantity = Integer.parseInt(ctx.request().getParam("quality"));

            if (quantity <= 0) {
                ctx.response()
                        .setStatusCode(400)
                        .end("stock 必须大于 0");
                return;
            }

            itemOrderService.createOrder(itemId, quantity, buyerId)
                    .onSuccess(ret -> {
                        // 创建响应对象
                        JsonObject itemResponse = new JsonObject()
                                     .put("订单创建", ret);
                        ctx.response()
                                .setStatusCode(200)
                                .end(itemResponse.toString());
                    })
                    .onFailure(err -> {
                        ctx.response()
                                .setStatusCode(400)
                                .end(err.getMessage());
                    });

        } catch (NumberFormatException e) {
            ctx.response()
                    .setStatusCode(400)
                    .end("参数格式错误: " + e.getMessage());
        }
    }

    private void queryItem(RoutingContext ctx) {
        try {
            Long itemId = Long.parseLong(ctx.request().getParam("itemId"));

            itemOrderService.getItemWithStock(itemId)
                    .onSuccess(item -> {
                        if (item == null) {
                            ctx.response()
                                    .setStatusCode(404)
                                    .end("商品不存在");
                        } else {
                            // 创建响应对象
                            JsonObject itemResponse = new JsonObject()
                                    .put("name", item.getItemName())
                                    .put("marketPrice", item.getMarketPrice())
                                    .put("price", item.getMarketPrice()) // 使用市场价格作为价格
                                    .put("stock", item.getStock());

                            ctx.response()
                                    .setStatusCode(200)
                                    .end(itemResponse.toString());
                        }
                    })
                    .onFailure(err -> {
                        ctx.response()
                                .setStatusCode(500)
                                .end("查询商品失败: " + err.getMessage());
                    });
        } catch (NumberFormatException e) {
            ctx.response()
                    .setStatusCode(400)
                    .end("参数格式错误: " + e.getMessage());
        }
    }
}
