package main

import (
	"context"
	"errors"
	"io/fs"
	"log"
	"net/http"
	"os"
	"os/signal"
	"path"
	"path/filepath"
	"strings"
)

func init() {
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.LstdFlags | log.Lshortfile)
}

// MultiFileSystem 支持多个资源目录
type MultiFileSystem struct {
	Fs []http.FileSystem
}
func (m MultiFileSystem) Open(name string) (http.File, error){
	for _, f := range m.Fs {
		file, err := f.Open(name)
		if err != nil {
			if os.IsNotExist(err) {
				// 文件不存在
				continue
			}
			return nil, err
		}
		return file, err
	}
	return nil, os.ErrNotExist
}

// Handler 应用Handler，首先处理静态资源，再处理业务接口
func Handler (fs http.FileSystem, handler http.Handler) http.Handler{
	return http.HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {

		// 参考了 http.FileServer 的实现

		// 如果不是 / 开头，则添加前缀
		filePath := request.URL.Path

		if !strings.HasPrefix(filePath, "/") {
			filePath = "/" + filePath
			request.URL.Path = filePath
		}

		// 清除路径中的..等非法路径
		filePath = path.Clean(filePath)

		// 打开目标文件/或者文件夹
		file, err := fs.Open(filePath)
		if err != nil {
			if os.IsNotExist(err) {
				// 文件不存在，则执行handler调用
				handler.ServeHTTP(writer, request)
			} else {
				// 文件IO异常
				writer.WriteHeader(toHTTPErrorStatus(err))
			}
			return
		}
		defer func() {
			_ = file.Close()
		}()

		// 获取文件信息
		fileStat, err := file.Stat()
		if err != nil {
			if os.IsNotExist(err) {
				// 文件不存在，则执行handler调用
				handler.ServeHTTP(writer, request)
			} else {
				// 文件IO异常
				writer.WriteHeader(toHTTPErrorStatus(err))
			}
			return
		}

		// 目录的话，尝试加载下面的index.html
		if fileStat.IsDir() {
			indexFilePath := strings.TrimSuffix(filePath, "/") + "/index.html"
			indexFile, err := fs.Open(indexFilePath)
			if err != nil {
				// index.html 读取异常
				if os.IsNotExist(err) {
					// handler调用
					handler.ServeHTTP(writer, request)
				} else {
					// 文件IO异常
					writer.WriteHeader(toHTTPErrorStatus(err))
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
					// handler调用
					handler.ServeHTTP(writer, request)
				} else {
					// 文件IO异常
					writer.WriteHeader(toHTTPErrorStatus(err))
				}
				return
			}
			filePath = indexFilePath
			fileStat = indexFileStat
			file = indexFile
		}

		// TODO 静态文件确定存在的情况下，可以校验 referer 防止盗链

		http.ServeContent(writer, request, fileStat.Name(), fileStat.ModTime(), file)
	})
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

func main() {

	// 工作目录
	workDir, err := os.Getwd()
	if err != nil {
		log.Fatalf("获取工作目录异常: %s\n", err.Error())
	}

	// 工作目录下的 public 目录为静态资源目录
	publicFs := MultiFileSystem{Fs: []http.FileSystem {http.Dir(filepath.Join(workDir, "public"))}}

	// Api 路由
	router := http.NewServeMux()

	server := http.Server {
		Addr: ":80",
		Handler: Handler(publicFs, router),
	}

	go func() {
		log.Println("Server start")
		ctx, cancel := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
		defer cancel()
		<- ctx.Done()
		if err := server.Close(); err != nil{
			log.Printf("Server Close Error: %s\n", err.Error())
		}
	}()

	if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
		log.Fatalf("Server Close Error: %s\n", err.Error())
	}

	log.Println("Server shutdown")
}
