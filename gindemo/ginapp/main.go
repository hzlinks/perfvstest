package main

import (
	"database/sql"
	"fmt"
	"log"
	"os"
	"path/filepath"

	"github.com/gin-gonic/gin"
	_ "modernc.org/sqlite"
)

func main() {
	// determine DB path: {user.home}/perf.db
	home, err := os.UserHomeDir()
	if err != nil {
		log.Fatalf("failed to get user home dir: %v", err)
	}
	dbPath := filepath.Join(home, "perf.db")
	dsn := fmt.Sprintf("file:%s?_foreign_keys=1", dbPath)

	db, err := sql.Open("sqlite", dsn)
	if err != nil {
		log.Fatalf("failed to open sqlite db: %v", err)
	}
	defer db.Close()

	// inject db into handlers
	h := NewHandler(db)

	r := gin.Default()

	r.GET("/query", h.Query)
	r.POST("/create", h.Create)

	addr := ":8080"
	log.Printf("starting server on %s, sqlite db: %s", addr, dbPath)
	if err := r.Run(addr); err != nil {
		log.Fatalf("server exited: %v", err)
	}
}
