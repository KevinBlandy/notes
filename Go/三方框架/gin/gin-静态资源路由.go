
* 类似于 Spring 的资源处理器
* 不需要URI前缀，可以映射多个资源目录
* 先处理静态资源，静态资源不存在才路由到Handler
* 不列出目录，访问目录，默认响应 index.html 文件，如果没有，响应404




-------------------

package main

import (
	"context"
	"github.com/gin-gonic/gin"
	"log"
	"math/rand"
	"net/http"
	"os"
	"os/signal"
	"path"
	"path/filepath"
	"strings"
	"time"
)

func init() {
	rand.Seed(time.Now().UnixNano())
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.LstdFlags | log.Lshortfile)
}

// MultiHttpFileSystem 支持多个目录的 Http File System
type MultiHttpFileSystem struct {
	Fs []http.FileSystem
}

func (m MultiHttpFileSystem) Open(path string) (http.File, error) {
	for _, f := range m.Fs {
		file, err := f.Open(path)
		if err != nil {
			if os.IsNotExist(err) {
				continue
			}
		}
		return file, err
	}
	return nil, os.ErrNotExist
}

// FsHandler 资源文件处理
func FsHandler(fs http.FileSystem) gin.HandlerFunc {

	return func(ctx *gin.Context) {

		reqPath := ctx.Request.URL.Path
		if !strings.HasPrefix(reqPath, "/") {
			reqPath = "/" + reqPath
			ctx.Request.URL.Path = reqPath
		}

		reqFile, err := fs.Open(path.Clean(reqPath))
		if err != nil {
			if os.IsNotExist(err) {
				ctx.Next()
			} else {
				_ = ctx.AbortWithError(http.StatusInternalServerError, err)
			}
			return
		}

		defer func() {
			_ = reqFile.Close()
		}()

		reqFileStat, err := reqFile.Stat()
		if err != nil {
			if os.IsNotExist(err) {
				ctx.Next()
			} else {
				_ = ctx.AbortWithError(http.StatusInternalServerError, err)
			}
			return
		}

		if reqFileStat.IsDir() {
			indexPath := strings.TrimSuffix(reqPath, "/")
			indexFile, err := fs.Open(indexPath + "/index.html")
			if err != nil {
				if os.IsNotExist(err) {
					ctx.Next()
				} else {
					_ = ctx.AbortWithError(http.StatusInternalServerError, err)
				}
				return
			}

			defer func() {
				_ = indexFile.Close()
			}()

			indexFileStat, err := indexFile.Stat()
			if err != nil {
				if os.IsNotExist(err) {
					ctx.Next()
				} else {
					_ = ctx.AbortWithError(http.StatusInternalServerError, err)
				}
				return
			}

			if indexFileStat.IsDir() {
				ctx.Next()
				return
			}

			reqPath = indexPath
			reqFile = indexFile
			reqFileStat = indexFileStat
		}

		// 响应内容
		http.ServeContent(ctx.Writer, ctx.Request, reqPath, reqFileStat.ModTime(), reqFile)

		// 阻断调用链表
		ctx.Abort()
	}
}

func main() {

	dir, err := os.Getwd()
	if err != nil {
		log.Fatalf("获取运行目录异常: %s\n", err.Error())
	}

	router := gin.New()

	// 普通路由
	router.GET("/hello", func(ctx *gin.Context) {
		ctx.String(http.StatusOK, "Hello World")
	})

	// 未经过所有路由，尝试解析文件
	router.Use(FsHandler(MultiHttpFileSystem{
		Fs: []http.FileSystem{http.Dir(filepath.Join(dir, "resources"))},
	}))

	// 处理404
	router.NoRoute(func(ctx *gin.Context) {
		ctx.String(http.StatusNotFound, "啥也没")
	})

	server := http.Server{
		Addr:    ":80",
		Handler: router,
	}

	closed := make(chan struct{})

	go func() {
		ctx, stop := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
		defer stop()
		<-ctx.Done()

		log.Println("Server Shutdown")

		ctx, cancel := context.WithTimeout(context.Background(), time.Second*5)
		defer cancel()
		if err := server.Shutdown(ctx); err != nil {
			log.Printf("Server Shutdown Error: %s\n", err.Error())
		}
		close(closed)
	}()

	log.Println("Server Start")
	if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
		log.Printf("Server Listen Error: %s\n", err.Error())
	}

	<-closed
}
