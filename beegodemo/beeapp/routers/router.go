package routers

import (
	"beeapp/controllers"

	"github.com/beego/beego/v2/server/web"
)

func InitRouter() {
	web.Router("/query", &controllers.ItemController{}, "get:Query")
	web.Router("/create", &controllers.ItemController{}, "post:Create")
}

// BeeAppRun 把 beego.Run 放在 models 包里调用，避免 main 中直接依赖 beego 多处导入问题。
// 这里简单封装一下。
