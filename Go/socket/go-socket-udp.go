---------------------
udp
---------------------
	# 参考学习
		https://colobu.com/2016/10/19/Go-UDP-Programming/
		https://colobu.com/2014/12/02/go-socket-programming-UDP/
	

	# UDP地址对象
		type UDPAddr struct {
			IP   IP
			Port int
			Zone string // IPv6 scoped addressing zone
		}
		func ResolveUDPAddr(network, address string) (*UDPAddr, error)
			* 解析UDP地址
				net.ResolveUDPAddr("udp4", "127.0.0.1:1024")
			
		func (a *UDPAddr) Network() string
		func (a *UDPAddr) String() string
	
	# net.UDPConn链接方法
		type *UDPConn struct {
		}
		func (c *UDPConn) Close() error
		func (c *UDPConn) File() (f *os.File, err error)
		func (c *UDPConn) LocalAddr() Addr
		func (c *UDPConn) Read(b []byte) (int, error)
		func (c *UDPConn) ReadFrom(b []byte) (int, Addr, error)
		func (c *UDPConn) ReadFromUDP(b []byte) (int, *UDPAddr, error)
			* 从客户端读取一个UDP消息

		func (c *UDPConn) ReadMsgUDP(b, oob []byte) (n, oobn, flags int, addr *UDPAddr, err error)
		func (c *UDPConn) RemoteAddr() Addr
		func (c *UDPConn) SetDeadline(t time.Time) error
		func (c *UDPConn) SetReadBuffer(bytes int) error
		func (c *UDPConn) SetReadDeadline(t time.Time) error
		func (c *UDPConn) SetWriteBuffer(bytes int) error
		func (c *UDPConn) SetWriteDeadline(t time.Time) error
		func (c *UDPConn) SyscallConn() (syscall.RawConn, error)
		func (c *UDPConn) Write(b []byte) (int, error)
			* 给服务器发送一个UDP消息

		func (c *UDPConn) WriteMsgUDP(b, oob []byte, addr *UDPAddr) (n, oobn int, err error)
		func (c *UDPConn) WriteTo(b []byte, addr Addr) (int, error)
		func (c *UDPConn) WriteToUDP(b []byte, addr *UDPAddr) (int, error)
			* 给客户端响应UDP消息
	

---------------------
udp - 客户端
---------------------
	# 创建
		func DialUDP(net string, laddr, raddr *UDPAddr) (c *UDPConn, err error)
			* net 指定网络: udp4
	
	# Demo
		// 地址
		addr, _ := net.ResolveUDPAddr("udp", "127.0.0.1:1024")
		// 创建客户端
		conn, _ := net.DialUDP("udp", nil, addr)
		// 发送消息
		size, _ := conn.Write([]byte("Hello Udp"))
		fmt.Printf("给服务器发送%d字节消息 ", size)

		// 读取响应
		buffer := make([]byte, 65507, 65507)
		size, _ = conn.Read(buffer)
		fmt.Printf("收到服务器的回复:%s\n", string(buffer[:size]))
---------------------
udp - 服务端
---------------------
	# 监听
		func ListenUDP(network string, laddr *UDPAddr) (*UDPConn, error)
	


	# Demo
		// 服务器监听地址
		addr, _ := net.ResolveUDPAddr("udp", "127.0.0.1:1024")
		// 服务端
		server, _ := net.ListenUDP("udp", addr)

		buffer := make([]byte, 65507, 65507) // udp消息最大字节数
		for {
			size, addr, _  := server.ReadFromUDP(buffer)
			
			// 读取数据
			data := buffer[:size]
			fmt.Printf("收到客户端[%s]消息:%s", addr.String(), string(data))

			// 给客户端回写数据
			size, _ = server.WriteToUDP([]byte("我收到啦"), addr)
			fmt.Printf("回写了%d字节数据\n", size)
		}
	
	# 从指定端口发出UDP数据包
		TODO

---------------------
udp - udp多播/组播
---------------------
	TODO

---------------------
udp - udp任播
---------------------
	TODO

---------------------
udp - udp广播
---------------------
	TODO