
* 类似于 Spring 的资源处理器
* 不需要URI前缀，可以映射多个资源目录
* 先处理静态资源，静态资源不存在才路由到Handler
* 不列出目录，访问目录，默认响应 index.html 文件，如果没有，响应404




// ----------



package main

import (
	"context"
	"demo/resources/public"
	"errors"
	"github.com/gin-gonic/gin"
	"io/fs"
	"log"
	"net/http"
	"os"
	"os/signal"
	"path"
	"strings"
	"time"
)

func init() {
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
}

func main() {
	HttpServer()
}

// MultiFileSystem 多资源FS
type MultiFileSystem struct {
	Fs []http.FileSystem
}

func (m MultiFileSystem) Open(name string) (http.File, error) {
	for _, fileSystem := range m.Fs {
		file, err := fileSystem.Open(name)
		if err != nil {
			if os.IsNotExist(err) {
				continue
			}
			return nil, err
		}
		return file, err
	}
	return nil, os.ErrNotExist
}

// WebSource Web资源可以有多个目录
var WebSource = MultiFileSystem{Fs: []http.FileSystem{
	http.Dir("."),       // 当前运行目录
	http.FS(public.Fs)}, // 嵌入式目录
}

// WebSourceHandler Web资源处理器
func WebSourceHandler(ctx *gin.Context) {
	// 如果不是 / 开头，则添加前缀
	requestPath := ctx.Request.URL.Path
	if !strings.HasPrefix(requestPath, "/") {
		requestPath = "/" + requestPath
		ctx.Request.URL.Path = requestPath
	}

	// 清除路径中的..等非法路径
	requestPath = path.Clean(requestPath)

	// 打开目标文件/或者文件夹
	requestFile, err := WebSource.Open(requestPath)
	if err != nil {
		if os.IsNotExist(err) {
			ctx.Next()
		} else {
			ctx.AbortWithStatus(toHTTPErrorStatus(err))
		}
		return
	}
	defer func() {
		_ = requestFile.Close()
	}()

	requestFileStat, err := requestFile.Stat()
	if err != nil {
		if os.IsNotExist(err) {
			ctx.Next()
		} else {
			ctx.AbortWithStatus(toHTTPErrorStatus(err))
		}
		return
	}

	// 目录的话，尝试加载index.html
	if requestFileStat.IsDir() {
		indexPath := strings.TrimSuffix(requestPath, "/") + "/index.html"
		indexFile, err := WebSource.Open(indexPath)
		if err != nil {
			// index.html 读取异常
			if os.IsNotExist(err) {
				ctx.Next()
			} else {
				ctx.AbortWithStatus(toHTTPErrorStatus(err))
			}
			return
		}
		// 有index.html文件
		defer func() {
			_ = indexFile.Close()
		}()
		indexFileStat, err := indexFile.Stat()
		if err != nil {
			if os.IsNotExist(err) {
				ctx.Next()
			} else {
				ctx.AbortWithStatus(toHTTPErrorStatus(err))
			}
			return
		}
		requestPath = indexPath
		requestFileStat = indexFileStat
		requestFile = indexFile
	}

	// TODO 可以在这里进行鉴权

	http.ServeContent(ctx.Writer, ctx.Request, requestPath, requestFileStat.ModTime(), requestFile)

	// 阻断调用链
	ctx.Abort()

	return
}

// toHTTPErrorStatus 把系统异常转换为HTTP异常状态码
func toHTTPErrorStatus(err error) (httpStatus int) {
	if errors.Is(err, fs.ErrNotExist) {
		return http.StatusNotFound
	}
	if errors.Is(err, fs.ErrPermission) {
		return http.StatusForbidden
	}
	return http.StatusInternalServerError
}

func HttpServer() {

	router := gin.New()

	// Web资源加载器
	router.Use(WebSourceHandler)

	// 业务Handler
	router.GET("/hello", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"success": true})
	})

	server := &http.Server{
		Addr:    ":80",
		Handler: router,
	}

	go func() {
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("server err: %s\n", err.Error())
		}
	}()

	ctx, cancel := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
	defer cancel()

	for {
		<-ctx.Done()
		func() {
			ctx, cancel := context.WithTimeout(context.Background(), time.Second*5)
			defer cancel()
			log.Println("Service Shutdown...")
			if err := server.Shutdown(ctx); err != nil {
				log.Printf("Error: %s\n", err.Error())
			}
		}()
		return
	}
}


// ---- resource_fs

demo
	|-resources
		|-public
			|-resource_fs.go

-------------------
package public

import "embed"

//go:embed *
var Fs embed.FS
