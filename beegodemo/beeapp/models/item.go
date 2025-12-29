package models

import (
	"database/sql"
	"errors"
	"fmt"
)

type ItemInfoBo struct {
	ItemId      int64  `json:"itemId"`
	ItemName    string `json:"itemName"`
	MarketPrice int    `json:"marketPrice"`
	Price       int    `json:"price"`
	Stock       int    `json:"stock"`
}

// GetItemInfo 查询 item_info 与 item_stock 并合并为 ItemInfoBo
func GetItemInfo(itemId int64) (*ItemInfoBo, error) {
	if DB == nil {
		return nil, errors.New("db not initialized")
	}

	var itemName string
	var marketPrice int
	err := DB.QueryRow("SELECT item_name, market_price FROM item_info WHERE id = ?", itemId).Scan(&itemName, &marketPrice)
	if err != nil {
		if err == sql.ErrNoRows {
			return nil, nil // caller 可识别为未找到
		}
		return nil, err
	}

	var price, stock int
	err = DB.QueryRow("SELECT price, stock FROM item_stock WHERE item_id = ?", itemId).Scan(&price, &stock)
	if err != nil {
		if err == sql.ErrNoRows {
			// 如果 item_stock 没有对应行，也返回市场价与零库存
			return &ItemInfoBo{
				ItemId:      itemId,
				ItemName:    itemName,
				MarketPrice: marketPrice,
				Price:       0,
				Stock:       0,
			}, nil
		}
		return nil, err
	}

	return &ItemInfoBo{
		ItemId:      itemId,
		ItemName:    itemName,
		MarketPrice: marketPrice,
		Price:       price,
		Stock:       stock,
	}, nil
}

// CreateOrder 在事务中检查库存、扣减库存并插入 order_main，成功返回插入 order id。
func CreateOrder(itemId, buyerId int64, quality int) (int64, error) {
	if DB == nil {
		return 0, errors.New("db not initialized")
	}

	// 查询 item_info
	var itemName string
	var marketPrice int
	err := DB.QueryRow("SELECT item_name, market_price FROM item_info WHERE id = ?", itemId).Scan(&itemName, &marketPrice)
	if err != nil {
		if err == sql.ErrNoRows {
			return 0, fmt.Errorf("item not found")
		}
		return 0, err
	}

	// 查询 stock 和 price
	var price, stock int
	err = DB.QueryRow("SELECT price, stock FROM item_stock WHERE item_id = ?", itemId).Scan(&price, &stock)
	if err != nil {

		if err == sql.ErrNoRows {
			return 0, fmt.Errorf("item stock not found")
		}
		return 0, err
	}

	if stock < quality {

		return 0, fmt.Errorf("insufficient stock: have=%d need=%d", stock, quality)
	}

	totalPrice := price * quality

	// 插入 order_main
	res, err := DB.Exec("INSERT INTO order_main (item_id, item_name, buyer_id, price, item_num, total_price) VALUES (?,?,?,?,?,?)",
		itemId, itemName, buyerId, price, quality, totalPrice)
	if err != nil {

		return 0, err
	}
	orderID, err := res.LastInsertId()
	if err != nil {

		return 0, err
	}

	return orderID, nil
}
