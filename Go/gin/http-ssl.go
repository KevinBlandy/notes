-------------------
Let's Encrypt的支持
-------------------
	# 一行代码
		package main

		import (
			"log"

			"github.com/gin-gonic/autotls"
			"github.com/gin-gonic/gin"
		)

		func main() {
			r := gin.Default()

			// Ping handler
			r.GET("/ping", func(c *gin.Context) {
				c.String(200, "pong")
			})
			
			// 
			log.Fatal(autotls.Run(r, "example1.com", "example2.com"))
		}
	
	# 默认的证书缓存路径
		* darwin
			${home}/Library/Caches/golang-autocert
		
		* windows
			* 从环境变量读取路径,依次检索，如果有就返回
				APPDATA
				CSIDL_APPDATA
				TEMP
				TMP
				${env}/golang-autocert

			* 如果环境没
				${home}/golang-autocert
		
		* 如果有环境变量	XDG_CACHE_HOME
			${XDG_CACHE_HOME}/golang-autocert

		* Linux
			${hone}/.cache/golang-autocert
