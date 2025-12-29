package main

import (
	"beeapp/models"
	"beeapp/routers"
	"log"

	"github.com/beego/beego/v2/server/web"
)

func main() {
	// 初始化数据库（建立连接并建表）
	if err := models.InitDB(); err != nil {
		log.Fatalf("failed to init db: %v", err)
	}

	// 注册路由并启动 beego
	routers.InitRouter()

	// 启动 beego 应用
	web.BConfig.Listen.HTTPPort = 8080
	web.BConfig.RunMode = "dev"
	web.Run()

	log.Println("Starting beeapp on :8080")

}
