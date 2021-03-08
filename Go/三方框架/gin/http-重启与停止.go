--------------------
优雅的停止
--------------------
	package main
	import (
		"context"
		"github.com/gin-gonic/gin"
		"log"
		"net/http"
		"os"
		"os/signal"
		"time"
	)

	func main() {

		router := gin.New()
		server := http.Server{
			Addr: ":8080",
			Handler: router,
		}
		// 异步启动服务
		go func() {
			if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
				// 服务启动异常，退出程序
				log.Fatalf("listen: %s\n", err)
			}
		}()

		// 注册信号监听，监听中断信号
		quit := make(chan os.Signal)
		signal.Notify(quit, os.Interrupt)
		<- quit

		// 收到了信号，停止服务
		ctx, cancel := context.WithTimeout(context.Background(), time.Second * 10)
		defer cancel()
		if err := server.Shutdown(ctx); err != nil {
			// 服务停止异常，退出程序
			log.Fatal("Server Shutdown:", err)
		}
		log.Println("Server exiting")
	}
