package models

import (
	"database/sql"
	"os"
	"path/filepath"

	_ "modernc.org/sqlite"
)

var DB *sql.DB

// InitDB 打开数据库并建表；数据库文件位于 {user.home}/perf.db
func InitDB() error {
	home, err := os.UserHomeDir()
	if err != nil {
		return err
	}
	dbPath := filepath.Join(home, "perf.db")
	dsn := dbPath // modernc sqlite accepts plain path
	db, err := sql.Open("sqlite", dsn)
	if err != nil {
		return err
	}
	// 设置全局 DB
	DB = db

	// 连接测试
	if err := DB.Ping(); err != nil {
		return err
	}

	return nil
}
