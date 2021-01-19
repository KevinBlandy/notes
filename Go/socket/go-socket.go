-------------------------
socket
-------------------------
	# Go语言标准库对socket建立的过程进行了抽象和封装

	# 客户端
		* 无论期望使用什么协议建立什么形式的连接，都只需要调用net.Dial()即可

			func Dial(network, address string) (Conn, error)
			func DialTimeout(network, address string, timeout time.Duration) (Conn, error)
			func FileConn(f *os.File) (c Conn, err error)
			
			network
				* 网络协议名称
			address
				* 目标地址
			timeout
				* 超时时间
			f
				* 本地文件Unix通信
			Conn
				* 成功的链接
			error
				* 异常信息
		
		* Conn 接口
			type Conn interface {
				Read(b []byte) (n int, err error)
				Write(b []byte) (n int, err error)
				Close() error
				LocalAddr() Addr
				RemoteAddr() Addr
				SetDeadline(t time.Time) error
				SetReadDeadline(t time.Time) error
				SetWriteDeadline(t time.Time) error
			}

		
		* 一些常见的协议
			conn, err := net.Dial("tcp", "192.168.0.10:2100")	//tcp
			conn, err := net.Dial("udp", "192.168.0.12:975")	//udp
			conn, err := net.Dial("ip4:icmp", "www.baidu.com")	// ICMP链接（使用协议名称）
			conn, err := net.Dial("ip4:1", "10.0.0.3")			// ICMP链接（使用协议编号）
		
		* 协议编号可以在线查看
			https://www.iana.org/assignments/address-family-numbers/address-family-numbers.xhtml
		
		* 高级的协议封装方法
			func DialTCP(net string, laddr, raddr *TCPAddr) (c *TCPConn, err error)
			func DialUDP(net string, laddr, raddr *UDPAddr) (c *UDPConn, err error)
			func DialIP(netProto string, laddr, raddr *IPAddr) (*IPConn, error)
			func DialUnix(net string, laddr, raddr *UnixAddr) (c *UnixConn, err error)
	
	# 服务端
		func Listen(network, address string) (Listener, error)
			* network，指定要监听的网络类型: tcp/udp....
			* string 指定地址，如果只有端口的话， 会监听所有网卡
			
		* Listener 接口
			type Listener interface {
				Accept() (Conn, error)
				Close() error
				Addr() Addr
			}
			func FileListener(f *os.File) (ln Listener, err error)
			func Listen(network, address string) (Listener, error)
		
-------------------------
通用的一些对象/接口
-------------------------
	# 网络地址
		type Addr interface {
			Network() string // name of the network (for example, "tcp", "udp")
			String() string  // string form of address (for example, "192.0.2.1:25", "[2001:db8::1]:80")
		}
		func InterfaceAddrs() ([]Addr, error)


	# IP
		type IP []byte

		func IPv4(a, b, c, d byte) IP
		func LookupIP(host string) ([]IP, error)
		func ParseIP(s string) IP

		func (ip IP) DefaultMask() IPMask
		func (ip IP) Equal(x IP) bool
		func (ip IP) IsGlobalUnicast() bool
		func (ip IP) IsInterfaceLocalMulticast() bool
		func (ip IP) IsLinkLocalMulticast() bool
		func (ip IP) IsLinkLocalUnicast() bool
		func (ip IP) IsLoopback() bool
		func (ip IP) IsMulticast() bool
		func (ip IP) IsUnspecified() bool
		func (ip IP) MarshalText() ([]byte, error)
		func (ip IP) Mask(mask IPMask) IP
		func (ip IP) String() string
		func (ip IP) To16() IP
		func (ip IP) To4() IP
		func (ip *IP) UnmarshalText(text []byte) error
	
