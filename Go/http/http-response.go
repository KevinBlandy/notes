------------
response
------------
	# ResponseWriter 接口
		type ResponseWriter interface {
			Header() Header
				* header
			Write([]byte) (int, error)
				* 响应body
			WriteHeader(statusCode int)
				* 响应http状态码
		}

	# Response 类
		type Response struct {
			Status     string // e.g. "200 OK"
			StatusCode int    // e.g. 200
			Proto      string // e.g. "HTTP/1.0"
			ProtoMajor int    // e.g. 1
			ProtoMinor int    // e.g. 0
			Header Header
			Body io.ReadCloser
				* 响应数据流

			ContentLength int64
				* body长度
			
			TransferEncoding []string
			Close bool
				* 响应是否已经关闭
			Uncompressed bool
			Trailer Header
			Request *Request
			TLS *tls.ConnectionState
		}

		func Get(url string) (resp *Response, err error)
		func Head(url string) (resp *Response, err error)
		func Post(url, contentType string, body io.Reader) (resp *Response, err error)
		func PostForm(url string, data url.Values) (resp *Response, err error)
			* 发起Http请求，获取响应
			* 默认使用的是默认的客户端
				var DefaultClient = &Client{}

		func ReadResponse(r *bufio.Reader, req *Request) (*Response, error)

		func (r *Response) Cookies() []*Cookie
			* 返回Cookie信息

		func (r *Response) Location() (*url.URL, error)
			* 返回URI信息
		func (r *Response) ProtoAtLeast(major, minor int) bool
		func (r *Response) Write(w io.Writer) error
			* 输出到指定的Writer