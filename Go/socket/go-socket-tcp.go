---------------------
tcp
---------------------
	# 参考
		https://colobu.com/2014/12/02/go-socket-programming-TCP/
	
	# TCP地址对象
		type TCPAddr struct {
			IP   IP
			Port int
			Zone string // IPv6 scoped addressing zone
		}
		func ResolveTCPAddr(network, address string) (*TCPAddr, error)
			* 解析一个地址为RctpAddr
		
		func (a *TCPAddr) Network() string
		func (a *TCPAddr) String() string
	

---------------------
tcp - 客户端
---------------------
	# 创建链接
		func DialTCP(net string, laddr, raddr *TCPAddr) (c *TCPConn, err error)
			* net 指定TCP版本号: tcp4
			* laddr 指定本地地址，可以设置为nil
			* raddr 指定远程地址
	
	# net.TCPConn链接方法
		type TCPConn struct {
		}
		func (c *TCPConn) Close() error
		func (c *TCPConn) CloseRead() error
		func (c *TCPConn) CloseWrite() error
		func (c *TCPConn) File() (f *os.File, err error)
		func (c *TCPConn) LocalAddr() Addr
		func (c *TCPConn) Read(b []byte) (int, error)
		func (c *TCPConn) ReadFrom(r io.Reader) (int64, error)
		func (c *TCPConn) RemoteAddr() Addr
		func (c *TCPConn) SetDeadline(t time.Time) error
		func (c *TCPConn) SetKeepAlive(keepalive bool) error
		func (c *TCPConn) SetKeepAlivePeriod(d time.Duration) error
		func (c *TCPConn) SetLinger(sec int) error
		func (c *TCPConn) SetNoDelay(noDelay bool) error
		func (c *TCPConn) SetReadBuffer(bytes int) error
		func (c *TCPConn) SetReadDeadline(t time.Time) error
		func (c *TCPConn) SetWriteBuffer(bytes int) error
		func (c *TCPConn) SetWriteDeadline(t time.Time) error
		func (c *TCPConn) SyscallConn() (syscall.RawConn, error)
		func (c *TCPConn) Write(b []byte) (int, error)
	
	# Demo
		// 创建链接对象
		addr, _ := net.ResolveTCPAddr("tcp4", "127.0.0.1:1024")
		conn, _ := net.DialTCP("tcp4", nil, addr)
		defer  conn.Close()

		// 从标准输入照中读取一行数据

		reader := bufio.NewReader(os.Stdin)
		fmt.Print(">> ")
		text, _ :=  reader.ReadSlice('\n')

		// 发送给服务器
		io.WriteString(conn, string(text))

		// 从服务器读取一行数据打印
		reader = bufio.NewReader(conn)
		text, _ = reader.ReadSlice('\n')
		fmt.Printf("服务器响应:%s\n", text)
	
---------------------
tcp - 服务端
---------------------
	# 创建监听器
		func ListenTCP(network string, laddr *TCPAddr) (*TCPListener, error)
			* 专门监听TCP

	
	# net.TCPListener 
		type TCPListener struct {}

		func (l *TCPListener) Accept() (Conn, error)
			* 开始阻塞监听，返回通用的Conn

		func (l *TCPListener) AcceptTCP() (*TCPConn, error)
			* 开始阻塞监听，返回TCPConn

		func (l *TCPListener) Addr() Addr
		func (l *TCPListener) Close() error
		func (l *TCPListener) File() (f *os.File, err error)
		func (l *TCPListener) SetDeadline(t time.Time) error
		func (l *TCPListener) SyscallConn() (syscall.RawConn, error)
	
	# Demo
		// 本地监听地址
		addr, err := net.ResolveTCPAddr("tcp4", "0.0.0.0:1024")
		if err != nil {
			fmt.Fprint(os.Stderr, err)
		}

		// 创建监听器
		server, err := net.ListenTCP("tcp4", addr)
		if err != nil {
			fmt.Fprint(os.Stderr, err)
		}

		// 开始监听服务
		for {
			conn, err := server.AcceptTCP()

			// 处理客户端连接
			go func(conn *net.TCPConn, err error) {
				if err != nil {
					fmt.Fprint(os.Stderr, err)
					conn.Close()
					return
				}
				// 读取客户端的输入
				text, _ := bufio.NewReader(conn).ReadSlice('\n')
				fmt.Printf("收到客户端消息:%s", text)

				// 响应给客户端
				io.WriteString(conn, fmt.Sprintf("我收到了消息:%s\n", string(text)))

				// 关闭客户端
				conn.Close()
			}(conn, err)
		}