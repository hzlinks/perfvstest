package controllers

import (
	"beeapp/models"
	"github.com/beego/beego/v2/server/web"
)

type ItemController struct {
	web.Controller
}

// GET /query?itemId=...
func (c *ItemController) Query() {
	itemId, err := c.GetInt64("itemId")
	if err != nil {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": "invalid itemId"}
		c.ServeJSON()
		return
	}

	item, err := models.GetItemInfo(itemId)
	if err != nil {
		c.Ctx.Output.SetStatus(500)
		c.Data["json"] = map[string]interface{}{"error": err.Error()}
		c.ServeJSON()
		return
	}
	if item == nil {
		c.Ctx.Output.SetStatus(404)
		c.Data["json"] = map[string]interface{}{"error": "item not found"}
		c.ServeJSON()
		return
	}
	c.Data["json"] = item
	c.ServeJSON()
}

// POST /create?itemId=...&buyerId=...&quality=...
func (c *ItemController) Create() {
	itemId, err := c.GetInt64("itemId")
	if err != nil {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": "invalid itemId"}
		c.ServeJSON()
		return
	}
	buyerId, err := c.GetInt64("buyerId")
	if err != nil {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": "invalid buyerId"}
		c.ServeJSON()
		return
	}
	quality, err := c.GetInt("quality")
	if err != nil {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": "invalid quality"}
		c.ServeJSON()
		return
	}
	if quality <= 0 {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": "quality must be > 0"}
		c.ServeJSON()
		return
	}

	orderID, err := models.CreateOrder(itemId, buyerId, quality)
	if err != nil {
		c.Ctx.Output.SetStatus(400)
		c.Data["json"] = map[string]interface{}{"error": err.Error()}
		c.ServeJSON()
		return
	}
	c.Data["json"] = map[string]interface{}{"success": true, "order_id": orderID}
	c.ServeJSON()
}