---------------------
request
---------------------
	# type Request struct {
			Method string
			URL *url.URL
			Proto      string // "HTTP/1.0"
			ProtoMajor int    // 1
			ProtoMinor int    // 0
			Header Header
			Body io.ReadCloser
			GetBody func() (io.ReadCloser, error)
			ContentLength int64
			TransferEncoding []string
			Close bool
			Host string
			Form url.Values
				* 检索参数

			PostForm url.Values
				* form表单
			MultipartForm *multipart.Form
				* 多部件表单体，必须先调用ParseMultipartForm后，这个字段才不会为nil

			Trailer Header
				* 客户端请求头
			RemoteAddr string
				* 客户端地址
			RequestURI string
				* URI地址
			TLS *tls.ConnectionState
			Cancel <-chan struct{}
			Response *Response
				* 获取Response对象
		}
		func NewRequest(method, url string, body io.Reader) (*Request, error)
		func NewRequestWithContext(ctx context.Context, method, url string, body io.Reader) (*Request, error)
		func ReadRequest(b *bufio.Reader) (*Request, error)

		func (r *Request) AddCookie(c *Cookie)
		func (r *Request) BasicAuth() (username, password string, ok bool)
		func (r *Request) Clone(ctx context.Context) *Request
		func (r *Request) Context() context.Context
		func (r *Request) Cookie(name string) (*Cookie, error)
			* 获取cookie，如果不存在，返回异常 http.ErrNoCookie
		
		func (r *Request) Cookies() []*Cookie
		func (r *Request) FormFile(key string) (multipart.File, *multipart.FileHeader, error)
		func (r *Request) FormValue(key string) string
		func (r *Request) MultipartReader() (*multipart.Reader, error)
			* 获取multipart/form-data 或 multipart/mixed 请求体
			* 如果不是，则返回异常	http.ErrNotMultipart
				reader,_ := request.MultipartReader()
				for part, err := reader.NextPart(); err != io.EOF {
					headers := part.Header		// 请求头
					count, err := part.Read(make([]byte, 1024)) 	// 读取数据
					fileName := part.FileName()	// 文件名字
					formName := part.FormName()	// 表单名称
					err := part.Close()		// 关闭资源
				}
		
		func (r *Request) ParseForm() error
			* 解析请求的查询参数和POST请求参数，也就是填充r.Form和r.PostForm的值
			* ParseForm是幂等的

		func (r *Request) ParseMultipartForm(maxMemory int64) error
			* 如果是multipar请求，需要先调用这个方法，指定内存的最大存储空间，超过这个空间的数据会被IO到临时文件
			* ParseMultipartForm会自动调用ParseForm

		func (r *Request) PostFormValue(key string) string
		func (r *Request) ProtoAtLeast(major, minor int) bool
		func (r *Request) Referer() string
		func (r *Request) SetBasicAuth(username, password string)
		func (r *Request) UserAgent() string
		func (r *Request) WithContext(ctx context.Context) *Request
		func (r *Request) Write(w io.Writer) error
		func (r *Request) WriteProxy(w io.Writer) error

------------------------------------------
request 处理multipart/formdata
------------------------------------------
import (
	"fmt"
	"io"
	"net/http"
	"os"
	"path/filepath"
)

func main(){
	serverMux := http.NewServeMux()
	serverMux.HandleFunc("/upload", func(writer http.ResponseWriter, request *http.Request) {
		// 先解析
		request.ParseMultipartForm(1024)

		// 多部件表单体
		form := request.MultipartForm

		if form == nil {
			return
		}

		defer request.MultipartForm.RemoveAll() // 删除所有关联的临时文件
		defer request.Body.Close()

		// 遍历普通表单项 map[string][]string
		values := form.Value
		for key, value := range values {
			fmt.Printf("name=%v value=%v\n", key, value)
		}

		// 文件表单项 map[string][]*FileHeader
		file := form.File["file"][0]
		fileName := file.Filename
		size := file.Size
		fmt.Printf("文件名称:%s, 文件大小:%d\n", fileName, size)

		// 获取请求头信息 map[string][]string
		header := file.Header
		for name, val := range header {
			fmt.Printf("header name=%s, value=%s\n", name, val)
		}

		// 创建本地文件
		localFile, err := os.OpenFile(filepath.Join("D:", "upload", fileName), os.O_CREATE | os.O_WRONLY | os.O_EXCL , os.ModePerm)
		if err != nil {
			fmt.Fprintf(os.Stderr, "本地文件创建异常:%v\n", err)
			return
		}
		defer localFile.Close()

		// 获取到上传的文件信息 multipart.File
		uploadFile, err := file.Open()
		if err != nil {
			fmt.Fprintf(os.Stderr, "读取请求体异常:%v\n", err)
			return
		}

		// io到磁盘
		io.Copy(localFile, uploadFile)

		// 响应客户端
		writer.WriteHeader(http.StatusOK)
		writer.Header().Set("Content-Type", "text/plan")
		writer.Write([]byte("success"))
	})
	server := http.Server{
		Addr: "0.0.0.0:1024",
		Handler: serverMux,
	}
	server.ListenAndServe()
}