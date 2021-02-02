---------------------------
http
---------------------------
	# Web学习
		https://gowebexamples.com/

---------------------------
http 模块的一些方法
---------------------------
	func CanonicalHeaderKey(s string) string
	func DetectContentType(data []byte) string
	func Error(w ResponseWriter, error string, code int)

	func Handle(pattern string, handler Handler)
	func HandleFunc(pattern string, handler func(ResponseWriter, *Request))
		* 根据路径，设置HTTP处理器，注册到默认的ServeMux (DefaultServeMux)

	func ListenAndServe(addr string, handler Handler) error
	func ListenAndServeTLS(addr, certFile, keyFile string, handler Handler) error
		* 启动一个HTTP服务
		* 如果handler为nil，在这种情况下，使用DefaultServeMux

	func MaxBytesReader(w ResponseWriter, r io.ReadCloser, n int64) io.ReadCloser
	func NotFound(w ResponseWriter, r *Request)
	func ParseHTTPVersion(vers string) (major, minor int, ok bool)
	func ParseTime(text string) (t time.Time, err error)
	func ProxyFromEnvironment(req *Request) (*url.URL, error)
	func ProxyURL(fixedURL *url.URL) func(*Request) (*url.URL, error)
	func Redirect(w ResponseWriter, r *Request, url string, code int)
	func Serve(l net.Listener, handler Handler) error
	func ServeContent(w ResponseWriter, req *Request, name string, modtime time.Time, ...)
	func ServeFile(w ResponseWriter, r *Request, name string)
	func ServeTLS(l net.Listener, handler Handler, certFile, keyFile string) error
	func SetCookie(w ResponseWriter, cookie *Cookie)
	func StatusText(code int) string

---------------------------
http GET 请求
---------------------------
import (
	"fmt"
	"io/ioutil"
	"net/http"
	"net/url"
	"os"
)

func main(){
	// url
	target, err := url.Parse("http://localhost")
	if err != nil {
		fmt.Fprintf(os.Stderr, "URL异常：%s\n", err.Error())
		os.Exit(1)
	}

	// 获取并且修改/添加/删除请求参数
	query := target.Query()
	query.Add("name", "test")
	target.RawQuery = query.Encode()

	// 创建http客户端
	client := http.Client{}

	// 创建http请求
	req, err := http.NewRequest(http.MethodGet, target.String(), nil)

	// 通过客户端，执行http请求
	resp, err := client.Do(req)
	if err != nil {
		fmt.Fprintf(os.Stderr, "请求异常：%s\n", err.Error())
		os.Exit(1)
	}
	defer resp.Body.Close()

	// 获取响应体
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Fprintf(os.Stderr, "响应读取：%s\n", err.Error())
		os.Exit(1)
	}
	fmt.Println(string(body))
}


---------------------------
http POST 请求
---------------------------


---------------------------
http Multipart 请求
---------------------------
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

	* 如果请求体不是很大的话，可以考虑使用 bytes.Buffer 作为底层的reader/writer


---------------------------
http 自己解析 Multipart 
---------------------------
	reader := multipart.NewReader(in, "bu")	// in ，就是请求体，bu 就是分隔数据
	for part, err := reader.NextPart(); err != io.EOF {
		headers := part.Header		// 请求头
		count, err := part.Read(make([]byte, 1024)) 	// 读取数据
		fileName := part.FileName()	// 文件名字
		formName := part.FormName()	// 表单名称
		err := part.Close()		// 关闭资源
	}