package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"io"
	"io/ioutil"
	"log"
	"mime/multipart"
	"net/http"
	"net/textproto"
	"strings"
	"time"
)

func main(){

	// Http 服务
	ctx, cancel := context.WithCancel(context.Background())
	ch := make(chan struct{})
	go server(ctx, ch)

	// 管道流
	r, w := io.Pipe()
	defer r.Close()

	// 创建 multipart，指定writer
	formWriter := multipart.NewWriter(w)
	go func() {
		defer w.Close()
		var writer io.Writer

		// 快速构建普通表单项，key/value都是字符串
		formWriter.WriteField("lang", "PHP是宇宙最好的语言")

		// 构建普通的表单项，通过Writer写入数据
		writer, _ = formWriter.CreateFormField("lang")
		writer.Write([]byte("Java是世界上最好的语言"))

		// 构建文件表单项，指定表单名称，以及文件名称，通过Writer写入数据，默认的ContentType 是 application/octet-stream
		writer, _ = formWriter.CreateFormFile("file", "app.json")
		jsonVal, _ := json.Marshal(map[string] string {"name": "KevinBlandy"})
		writer.Write(jsonVal)

		// 自定义part表单项，可以添加自定义的header
		header := textproto.MIMEHeader{}
		header.Set("Content-Disposition", `form-data; name="file"; filename="app1.json"`)		// 自定表单字段名称，文件名称，这是必须的
		header.Set("Content-Type", `application/octet-stream`)									// 指定ContentType，这是必须的
		writer, _ = formWriter.CreatePart(header)
		writer.Write([]byte("foo"))

		// 完成写入，需要调用close方法
		formWriter.Close()
	}()

	// 创建http客户端
	client := http.Client{}
	// 创建request请求，指定body reader
	req, _ := http.NewRequest(http.MethodPost, "http://127.0.0.1/upload", r)
	req.Header.Set("Content-Type", formWriter.FormDataContentType()) // 需要正确的设置ContentType

	// 执行请求获取响应
	resp, _ := client.Do(req)
	defer resp.Body.Close()

	// 获取响应
	data, _ := ioutil.ReadAll(resp.Body)
	fmt.Println(string(data))

	// 停止服务器
	cancel()

	// 等待退出
	<- ch
}
func server(ctx context.Context, ch chan <- struct{}){
	router := gin.Default()
	router.POST("/upload", func(ctx *gin.Context) {
		form, _ := ctx.MultipartForm()
		fmt.Println("普通表单项-------------------")
		for key, value := range form.Value {
			fmt.Printf("name=%s, value=%s\n", key, strings.Join(value, ","))
		}
		fmt.Println("文件表单项-------------------")
		for key, value := range form.File {
			for _, file := range value {
				fmt.Printf("name=%s, size=%d, fileName=%s, headers=%v\n", key, file.Size, file.Filename, file.Header)
			}
		}
		ctx.Writer.Header().Set("Content-Type", "text/plan")
		ctx.Writer.WriteString("success")
	})
	server := http.Server{
		Addr: ":80",
		Handler: router,
	}

	// 启动服务
	go func() {
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Panic(err)
		}
	}()

	for {
		select {
			case <- ctx.Done():{
				ctx, _ := context.WithTimeout(context.Background(), time.Second * 2)
				server.Shutdown(ctx)
				log.Println("服务器停止...")
				ch <- struct{}{}
				return
			}
		}
	}
}


