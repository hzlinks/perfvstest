package main

import (
	"database/sql"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

type Handler struct {
	db *sql.DB
}

func NewHandler(db *sql.DB) *Handler {
	return &Handler{db: db}
}

// GET /query?itemId=???
func (h *Handler) Query(c *gin.Context) {
	// parse itemId
	itemIdStr := c.Query("itemId")
	if itemIdStr == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "itemId is required"})
		return
	}

	var itemId int64
	if _, err := fmt.Sscan(itemIdStr, &itemId); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid itemId"})
		return
	}

	// query item_info
	var itemName string
	var marketPrice int
	err := h.db.QueryRow("SELECT item_name, market_price FROM item_info WHERE id = ?", itemId).Scan(&itemName, &marketPrice)
	if err == sql.ErrNoRows {
		c.JSON(http.StatusNotFound, gin.H{"error": "item not found"})
		return
	}
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	// query item_stock
	var stock, price int
	err = h.db.QueryRow("SELECT stock, price FROM item_stock WHERE item_id = ?", itemId).Scan(&stock, &price)
	if err == sql.ErrNoRows {
		// if no stock row, return stock=0 and price=0
		stock = 0
		price = 0
	} else if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	resp := ItemInfoBo{
		ItemId:      itemId,
		ItemName:    itemName,
		MarketPrice: marketPrice,
		Price:       price,
		Stock:       stock,
	}
	c.JSON(http.StatusOK, resp)
}

// POST /create? expects itemId, buyerId, quality
// Accepts JSON or form values.
func (h *Handler) Create(c *gin.Context) {
	var req CreateOrderRequest
	if err := c.ShouldBind(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "missing or invalid parameters: itemId, buyerId, quality"})
		return
	}

	// item_info
	var itemName string
	var marketPrice int
	err := h.db.QueryRow("SELECT item_name, market_price FROM item_info WHERE id = ?", req.ItemId).Scan(&itemName, &marketPrice)
	if err == sql.ErrNoRows {
		c.JSON(http.StatusBadRequest, gin.H{"error": "item not found"})
		return
	}
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	var stock, price int
	err = h.db.QueryRow("SELECT stock, price FROM item_stock WHERE item_id = ?", req.ItemId).Scan(&stock, &price)
	if err == sql.ErrNoRows {
		c.JSON(http.StatusBadRequest, gin.H{"error": "no stock info for item"})
		return
	}
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	if req.Quality <= 0 {
		c.JSON(http.StatusBadRequest, gin.H{"error": "quality must be positive"})
		return
	}
	if stock < req.Quality {
		c.JSON(http.StatusBadRequest, gin.H{"error": "insufficient stock"})
		return
	}

	totalPrice := price * req.Quality

	// insert into order_main
	res, err := h.db.Exec(`INSERT INTO order_main (item_id, item_name, buyer_id, price, item_num, total_price)
		VALUES (?, ?, ?, ?, ?, ?)`, req.ItemId, itemName, req.BuyerId, price, req.Quality, totalPrice)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	orderId, _ := res.LastInsertId()

	c.JSON(http.StatusOK, gin.H{
		"orderId": orderId,
		"success": true,
	})
}
