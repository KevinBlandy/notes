--------------------
http客户端
--------------------
	# 客户端对象
		type Client struct {
			Transport RoundTripper
			CheckRedirect func(req *Request, via []*Request) error
			Jar CookieJar
			Timeout time.Duration
		}
		func (c *Client) CloseIdleConnections()
		func (c *Client) Do(req *Request) (*Response, error)
		func (c *Client) Get(url string) (resp *Response, err error)
		func (c *Client) Head(url string) (resp *Response, err error)
		func (c *Client) Post(url, contentType string, body io.Reader) (resp *Response, err error)
		func (c *Client) PostForm(url string, data url.Values) (resp *Response, err error)
	
	
	# 底层传输控制
		type Transport struct {
				Proxy func(*Request) (*url.URL, error)
				DialContext func(ctx context.Context, network, addr string) (net.Conn, error)
				Dial func(network, addr string) (net.Conn, error)
				DialTLSContext func(ctx context.Context, network, addr string) (net.Conn, error)
				TLSClientConfig *tls.Config
				TLSHandshakeTimeout time.Duration
				DisableKeepAlives bool
				DisableCompression bool
				MaxIdleConns int
				MaxIdleConnsPerHost int
				MaxConnsPerHost int
				IdleConnTimeout time.Duration
				ResponseHeaderTimeout time.Duration
				ExpectContinueTimeout time.Duration
				TLSNextProto map[string]func(authority string, c *tls.Conn) RoundTripper
				ProxyConnectHeader Header
				MaxResponseHeaderBytes int64
				WriteBufferSize int
				ReadBufferSize int
				ForceAttemptHTTP2 bool // contains filtered or unexported fields
			}
			func (t *Transport) CancelRequest(req *Request)
			func (t *Transport) Clone() *Transport
			func (t *Transport) CloseIdleConnections()
			func (t *Transport) RegisterProtocol(scheme string, rt RoundTripper)
			func (t *Transport) RoundTrip(req *Request) (*Response, error)
	

	# 忽略SSL证书校验
		client := http.Client{
			Transport: &http.Transport{
				TLSClientConfig: &tls.Config{
					InsecureSkipVerify: true,
				},
			},
		}
	
