------------------------
httptrace
------------------------
	# 客户端HTTP请求的栈跟踪

------------------------
变量
------------------------

------------------------
type
------------------------
	
	# type ClientTrace struct {
			GetConn func(hostPort string)
			GotConn func(GotConnInfo)
			PutIdleConn func(err error)
			GotFirstResponseByte func()
			Got100Continue func()
			Got1xxResponse func(code int, header textproto.MIMEHeader) error
			DNSStart func(DNSStartInfo)
			DNSDone func(DNSDoneInfo)
			ConnectStart func(network, addr string)
			ConnectDone func(network, addr string, err error)
			TLSHandshakeStart func()
			TLSHandshakeDone func(tls.ConnectionState, error)
			WroteHeaderField func(key string, value []string)
			WroteHeaders func()
			Wait100Continue func()
			WroteRequest func(WroteRequestInfo)
		}

		func ContextClientTrace(ctx context.Context) *ClientTrace
	
	# type DNSDoneInfo struct {
			Addrs []net.IPAddr
			Err error
			Coalesced bool
		}
	# type DNSStartInfo struct {
			Host string
		}
	# type GotConnInfo struct {
			Conn net.Conn
			Reused bool
			WasIdle bool
			IdleTime time.Duration
		}
	# type WroteRequestInfo struct {
			Err error
		}

------------------------
func
------------------------
	func WithClientTrace(ctx context.Context, trace *ClientTrace) context.Context