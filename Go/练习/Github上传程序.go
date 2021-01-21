package main

import (
	"bytes"
	"context"
	"encoding/base64"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"net/url"
	"os"
	"os/signal"
	"strconv"
	"strings"
	"time"
)

var (
	authorizationHeader string // 身份认证
)

const (
	// 最大内存 1Mb
	maxMemory int64 = 1024 << 10
)

// 默认的HTTP客户端
var httpClient = http.Client{
	Timeout: time.Second * 10,
}

// 登录验证
func BasicAuthHandler(handler http.Handler) http.HandlerFunc {
	return func(writer http.ResponseWriter, request *http.Request) {
		authorization := request.Header.Get("Authorization")
		if authorizationHeader != authorization {
			// 未登录或者验证失败
			writer.Header().Set("WWW-Authenticate", "Basic realm="+strconv.Quote("Authorization Required"))
			writer.WriteHeader(http.StatusUnauthorized)
			return
		}
		handler.ServeHTTP(writer, request)
	}
}

func AuthorizationHeader(user, password string) string {
	return "Basic " + base64.StdEncoding.EncodeToString([]byte(user+":"+password))
}

// 用日期打散路径
func FilePath(suffix string) string {
	builder := strings.Builder{}
	now := time.Now()
	builder.WriteString(fmt.Sprintf("%d/", now.Year()))
	builder.WriteString(fmt.Sprintf("%02d/", now.Month()))
	builder.WriteString(fmt.Sprintf("%02d/%d.%s", now.Day(), now.Unix(), suffix))
	return builder.String()
}

// 获取文件后缀
func Suffix(filename string) (string, error) {
	index := strings.LastIndex(filename, ".")
	if index == -1 {
		return "", errors.New("不是合法的文件")
	}
	suffix := filename[index+1:]
	// TODO 文件类型校验
	return suffix, nil
}

// 响应异常信息
func ErrorResponse(writer http.ResponseWriter, message string, code int) {
	writer.Header().Set("Content-Type", "text/plan; charset=utf-8")
	writer.WriteHeader(code)
	_, _ = io.WriteString(writer, message)
	return
}

var (
	host = flag.String("host", "", "监听host，不指定则监听所有")
	port = flag.Int("port", 8081, "使用的端口")
	user = flag.String("user", "admin", "访问账户")
	password = flag.String("password", "admin", "访问密码")
	githubAccessToken = flag.String("token", "", "Github接口访问的Token")
	githubAccount = flag.String("account", "KevinBlandy", "Github账户")
	githubRepository = flag.String("repository", "image-bucket", "Github存储图片的仓库")
)

func main() {

	flag.Parse()

	log.Printf("host=%s, port=%d, user=%s, password=%s, githubAccessToken=%s, githubAccount=%s, githubRepository=%s\n",
		*host, *port, *user, *password, *githubAccessToken, *githubAccount, *githubRepository)

	if *githubAccessToken == "" || *githubAccount == "" || *githubRepository == "" {
		log.Println("Github配置信息不能不能为空")
		os.Exit(1)
	}

	authorizationHeader = AuthorizationHeader(*user, *password)

	router := http.NewServeMux()

	// 静态资源目录
	router.Handle("/", BasicAuthHandler(http.FileServer(http.Dir("./static"))))

	// 执行上传
	router.HandleFunc("/upload", BasicAuthHandler(http.HandlerFunc(func(writer http.ResponseWriter, request *http.Request) {
		defer func() {
			_ = request.Body.Close()
		}()
		err := request.ParseMultipartForm(maxMemory)
		if err != nil {
			// MultipartForm解析异常
			ErrorResponse(writer, "bad request", http.StatusBadRequest)
			return
		}
		form := request.MultipartForm
		images := make([]string, 0, 0)
		for _, files := range form.File {
			for _, file := range files {

				filename := file.Filename
				size := file.Size
				log.Printf("┏ 文件:filename=%s, size=%d\n", filename, size)

				// 获取文件后缀
				suffix, err := Suffix(filename)
				if err != nil {
					ErrorResponse(writer, err.Error(), http.StatusBadRequest)
					return
				}

				// 打开文件
				f, _ := file.Open()
				// 读取文件
				data, _ := ioutil.ReadAll(f)
				_ = f.Close()

				// 请求体
				requestBody, _ := json.Marshal(map[string]string{
					"message": "file upload",
					"content": base64.StdEncoding.EncodeToString(data),
				})

				// 资源存储路径
				filePath := FilePath(suffix)

				log.Printf("┃ 上传路径:%s\n", filePath)

				// 构建URL
				requestUrl, _ := url.Parse(fmt.Sprintf("https://api.github.com/repos/%s/%s/contents/%s", *githubAccount, *githubRepository, filePath))
				log.Printf("┃ 请求URL:%s\n", requestUrl)
				request, _ := http.NewRequest(http.MethodPut, requestUrl.String(), bytes.NewReader(requestBody))
				request.Header.Set("Accept", "application/json")
				request.Header.Set("Content-Type", "application/json")
				request.Header.Set("Authorization", "token " + *githubAccessToken)
				response, err := httpClient.Do(request)
				if err != nil {
					ErrorResponse(writer, fmt.Sprintf("Github请求异常:%s\n", err.Error()), http.StatusInternalServerError)
					return
				}
				responseBody, _ := ioutil.ReadAll(response.Body)
				_ = response.Body.Close()

				log.Printf("┗ Github上传响应:code=%d, body=%s\n", response.StatusCode, responseBody)

				if response.StatusCode != http.StatusCreated {
					ErrorResponse(writer, fmt.Sprintf("Github响应状态码:code=%d, body=%s\n", response.StatusCode, responseBody), response.StatusCode)
					return
				}

				// 构建访问URL
				images = append(images, fmt.Sprintf("https://cdn.jsdelivr.net/gh/%s/%s/%s", *githubAccount, *githubRepository, filePath))
			}
		}

		jsonVal, _ := json.Marshal(images)
		writer.WriteHeader(http.StatusCreated)
		writer.Header().Set("Content-Type", "application/json")
		_, _ = writer.Write(jsonVal)
	})))
	server := http.Server{
		Addr:    fmt.Sprintf("%s:%d", *host, *port),
		Handler: router,
	}
	go func() {
		log.Println("服务启动...")
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Printf("服务启动异常:%s\n", err.Error())
		}
	}()

	quit := make(chan os.Signal)
	signal.Notify(quit, os.Interrupt, os.Kill)
	<-quit

	log.Println("服务停止中...")

	ctx, cancel := context.WithTimeout(context.Background(), time.Second*5)
	defer cancel()
	err := server.Shutdown(ctx)
	if err != nil {
		log.Printf("服务停止异常:%s\n", err.Error())
	}
	log.Println("服务已经停止")
}


--------------
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Upload</title>
    </head>
    <body>
        <form id="form">
            <input name="file" type="file" multiple="multiple" accept="image/*"/>
        </form>
    </body>
    <script>

        window.onload = () => {
            document.querySelector(`input[name="file"]`).addEventListener('change', (e) => {
                const files = e.target.files
                if (!files) {
                    return
                }
                const formData = new FormData(document.querySelector('#form'))
                const xhr = new XMLHttpRequest()
                xhr.open("POST", "/upload")
                if (xhr.upload){
                    xhr.upload.onprogress = e => {
                        const percent  = ((e.loaded / e.total) * 100).toFixed(2);
                        console.log(`上传进度 ${percent}`);
                    }
                }
                xhr.onload = e => {
                    document.querySelector('#form').reset()
                    if (xhr.status == 201){
                        const images = JSON.parse(xhr.responseText)
                        images.forEach(i => console.log(i))
                    } else {
                        const errorMessage = xhr.responseText
                        alert(errorMessage);
                    }
                };
                xhr.send(formData)
            })
        }
    </script>
</html>