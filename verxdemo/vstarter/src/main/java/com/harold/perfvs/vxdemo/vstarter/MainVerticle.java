package com.harold.perfvs.vxdemo.vstarter;

import com.harold.perfvs.vxdemo.vstarter.controller.OrderController;
import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class MainVerticle extends VerticleBase {


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }


    @Override
    public Future<?> start() {
        // 创建路由器
        Router router = Router.router(vertx);

        // 初始化控制器并配置路由
        OrderController orderController = new OrderController(vertx);
        orderController.configureRoutes(router);

        // 创建HTTP服务器并使用路由器
        return vertx.createHttpServer().requestHandler(router)
                .listen(8080)
                .onSuccess(http -> {
                    System.out.println("HTTP server started on port 8080");
                });
    }
}
