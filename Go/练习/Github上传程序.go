package main

import (
	"bytes"
	"context"
	"encoding/base64"
	"encoding/json"
	"errors"
	"flag"
	"fmt"
	"html/template"
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

// 配置
var (
	user string							// 登录账户
	password string						// 登录密码
	githubAccessToken string			// Github的访问Token
	githubAccount string				// Github账户
	githubRepository string				// 存储图片的仓库
)

var (
	authorizationHeader string
	htmlTemplate string
)

const (
	// 最大内存 1Mb
	maxMemory int64 = 1024 * 1024
)

// 登录验证
func BasicAuthHandler (handlerFunc http.HandlerFunc)  http.HandlerFunc {
	return func(writer http.ResponseWriter, request *http.Request) {
		authorization := request.Header.Get("Authorization")
		if authorizationHeader != authorization {
			// 未登录或者验证失败
			writer.Header().Set("WWW-Authenticate", "Basic realm=" + strconv.Quote("Authorization Required"))
			writer.WriteHeader(http.StatusUnauthorized)
			return
		}
		handlerFunc.ServeHTTP(writer, request)
	}
}

func AuthorizationHeader(user, password string) string {
	return "Basic " + base64.StdEncoding.EncodeToString([]byte(user + ":" + password))
}

// 加载HTML
func LoadTemplate() (string, error) {
	if htmlTemplate != "" {
		return htmlTemplate, nil
	}
	temp, err := template.ParseFiles("./index.html")
	if err != nil {
		return "", err
	}
	builder := new(strings.Builder)
	err = temp.Execute(builder, nil)
	if err != nil {
		return "", err
	}
	htmlTemplate = builder.String()
	return htmlTemplate, nil
}

// 默认的HTTP客户端
var httpClient = http.Client{
	Timeout: time.Second * 5,
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
func Suffix (filename string) (string, error){
	index := strings.LastIndex(filename, ".")
	if index == -1 {
		return "", errors.New("不是合法的文件")
	}
	suffix := filename[index + 1:]
	return suffix, nil
}

// 以文本形式响应异常信息
func ErrorResponse (writer http.ResponseWriter, message string, code int){
	writer.Header().Set("Content-Type", "text/plan")
	writer.WriteHeader(code)
	io.WriteString(writer, message)
	return
}

func main() {

	// 解析命令参数
	var host = *flag.String("host", "", "绑定网卡，不指定则监听所有")
	var port = *flag.Int("port", 8081, "使用的端口")
	user = *flag.String("user", "admin", "访问账户，默认admin")
	password = *flag.String("password", "admin", "访问密码，默认admin")
	githubAccessToken = *flag.String("token", "bf31fb1a611a13bce449619ae854d5fc77f25733", "Github接口访问的Token")
	githubAccount = *flag.String("account", "KevinBlandy", "Github账户")
	githubRepository = *flag.String("repository", "image-bucket", "Github存储图片的仓库")

	flag.Parse()

	if githubAccessToken == "" || githubAccount == "" || githubRepository == "" {
		log.Panic("Github配置信息不能不能为空")
	}

	authorizationHeader = AuthorizationHeader(user, password)

	log.Printf("host=%s, port=%d, user=%s, password=%s, githubAccessToken=%s, githubAccount=%s, githubRepository=%s\n",
		host, port, user, password, githubAccessToken, githubAccount, githubRepository)

	router := http.NewServeMux()

	// 渲染HTML页面
	router.HandleFunc("/", BasicAuthHandler(func(writer http.ResponseWriter, request *http.Request) {
		html, err := LoadTemplate()
		if err != nil {
			ErrorResponse(writer, fmt.Sprintf("index.html加载异常:%s\n", err.Error()), http.StatusInternalServerError)
			return
		}
		writer.WriteHeader(http.StatusOK)
		writer.Header().Set("Content-Type", "text/html")
		io.WriteString(writer, html)
	}))
	// 执行上传
	router.HandleFunc("/upload", BasicAuthHandler(func(writer http.ResponseWriter, request *http.Request) {
		defer request.Body.Close()
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
				log.Printf("文件:filename=%s, size=%d\n", filename, size)

				// TODO 文件类型判断

				suffix, _ := Suffix(filename)
				// 打开文件
				f, _ := file.Open()
				// 读取文件
				data, _ := ioutil.ReadAll(f)

				// 请求体
				requestBody, _ := json.Marshal(map[string] string {
					"message": "file upload",
					"content": base64.StdEncoding.EncodeToString(data),
				})

				// 资源存储路径
				filePath := FilePath(suffix)

				log.Printf("上传路径:%s\n", filePath)

				// 构建URL
				requestUrl, _ := url.Parse(fmt.Sprintf("https://api.github.com/repos/%s/%s/contents/%s", githubAccount, githubRepository, filePath))
				log.Printf("请求URL:%s\n", requestUrl)
				request, _ := http.NewRequest(http.MethodPut, requestUrl.String(), bytes.NewReader(requestBody))
				request.Header.Set("Accept", "application/json")
				request.Header.Set("Content-Type", "application/json")
				request.Header.Set("Authorization", "token " + githubAccessToken)
				response, err := httpClient.Do(request)
				if err != nil {
					ErrorResponse(writer, fmt.Sprintf("请求异常:%s\n", err.Error()), http.StatusInternalServerError)
					return
				}
				if response.StatusCode != http.StatusCreated  {
					ErrorResponse(writer, fmt.Sprintf("响应状态码异常:code=%d\n", response.StatusCode), response.StatusCode)
					return
				}
				responseBody, _ := ioutil.ReadAll(response.Body)
				response.Body.Close()

				log.Printf("github上传响应:code=%d, body=%s\n", response.StatusCode, responseBody)

				// 构建访问URL
				images = append(images, fmt.Sprintf("https://cdn.jsdelivr.net/gh/%s/%s/%s", githubAccount, githubRepository, filePath))
			}
		}

		jsonVal, _ := json.Marshal(images)
		writer.WriteHeader(http.StatusCreated)
		writer.Header().Set("Content-Type", "application/json")
		writer.Write(jsonVal)
	}))
	server := http.Server{
		Addr: fmt.Sprintf("%s:%d", host, port),
		Handler: router,
	}
	go func() {
		log.Println("服务启动...")
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Panicf("服务启动异常:%s\n", err.Error())
		}
	}()
	quit := make(chan os.Signal)
	signal.Notify(quit, os.Interrupt, os.Kill)
	<- quit

	log.Println("服务停止中...")

	ctx, cancel := context.WithTimeout(context.Background(), time.Second * 5)
	defer cancel()
	err := server.Shutdown(ctx)
	if err != nil {
		log.Panicf("服务停止异常:%s\n", err.Error())
	}
	log.Println("服务已经停止")
}