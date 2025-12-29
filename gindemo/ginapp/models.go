package main

// ItemInfoBo 返回类型
type ItemInfoBo struct {
	ItemId      int64  `json:"itemId"`
	ItemName    string `json:"itemName"`
	MarketPrice int    `json:"marketPrice"`
	Price       int    `json:"price"`
	Stock       int    `json:"stock"`
}

// CreateOrderRequest POST /create 请求体（支持 JSON 或表单）
type CreateOrderRequest struct {
	ItemId  int64 `json:"itemId" form:"itemId" binding:"required"`
	BuyerId int64 `json:"buyerId" form:"buyerId" binding:"required"`
	Quality int   `json:"quality" form:"quality" binding:"required"`
}
