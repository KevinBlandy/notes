----------------
net
----------------

----------------
����
----------------
	var (
		IPv4bcast     = IPv4(255, 255, 255, 255) // limited broadcast
		IPv4allsys    = IPv4(224, 0, 0, 1)       // all systems
		IPv4allrouter = IPv4(224, 0, 0, 2)       // all routers
		IPv4zero      = IPv4(0, 0, 0, 0)         // all zeros
	)

	var (
		IPv6zero                   = IP{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
		IPv6unspecified            = IP{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
		IPv6loopback               = IP{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
		IPv6interfacelocalallnodes = IP{0xff, 0x01, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x01}
		IPv6linklocalallnodes      = IP{0xff, 0x02, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x01}
		IPv6linklocalallrouters    = IP{0xff, 0x02, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x02}
	)
	
	var DefaultResolver = &Resolver{}
	
	var ErrClosed = errClosed
		* ���������ϵ�I/O���÷��صĴ��󣬸������Ѿ����رգ�������I/O���֮ǰ����һ��goroutine�ر�
		* ����ܱ���װ����һ�������У�ͨ��Ӧ��ʹ�� errors.Is(err, net.ErrClosed)�����ԡ�
		* Close Listener ��ʱ��Accept ���������ͻ᷵�� ErrClosed ����

	var (
		ErrWriteToConnected = errors.New("use of WriteTo with pre-connected connection")
	)


----------------
type
----------------
	# type Addr interface {
			Network() string // name of the network (for example, "tcp", "udp")
			String() string  // string form of address (for example, "192.0.2.1:25", "[2001:db8::1]:80")
		}
		func InterfaceAddrs() ([]Addr, error)
	
	
	# type AddrError struct {
			Err  string
			Addr string
		}
		func (e *AddrError) Error() string
		func (e *AddrError) Temporary() bool
		func (e *AddrError) Timeout() bool
	
	# type Buffers [][]byte
		func (v *Buffers) Read(p []byte) (n int, err error)
		func (v *Buffers) WriteTo(w io.Writer) (n int64, err error)
	
	# type Conn interface {
			Read(b []byte) (n int, err error)
			Write(b []byte) (n int, err error)
			Close() error
			LocalAddr() Addr
			RemoteAddr() Addr
			SetDeadline(t time.Time) error
			SetReadDeadline(t time.Time) error
			SetWriteDeadline(t time.Time) error
		}
		func Dial(network, address string) (Conn, error)
		func DialTimeout(network, address string, timeout time.Duration) (Conn, error)
			* ����һ�����ӣ��������ӳ�ʱ

		func FileConn(f *os.File) (c Conn, err error)
	
	# type DNSConfigError struct {
			Err error
		}
		func (e *DNSConfigError) Error() string
		func (e *DNSConfigError) Temporary() bool
		func (e *DNSConfigError) Timeout() bool
		func (e *DNSConfigError) Unwrap() error
	
	# type DNSError struct {
			Err         string // description of the error
			Name        string // name looked for
			Server      string // server used
			IsTimeout   bool   // if true, timed out; not all timeouts set this
			IsTemporary bool   // if true, error is temporary; not all errors set this
			IsNotFound  bool   // if true, host could not be found
		}
		func (e *DNSError) Error() string
		func (e *DNSError) Temporary() bool
		func (e *DNSError) Timeout() bool
	
	# type Dialer struct {
			Timeout time.Duration
			Deadline time.Time
			LocalAddr Addr
			DualStack bool
			FallbackDelay time.Duration
			KeepAlive time.Duration
			KeepAliveConfig KeepAliveConfig
			Resolver *Resolver
			Cancel <-chan struct{}
			Control func(network, address string, c syscall.RawConn) error
		}
		func (d *Dialer) Dial(network, address string) (Conn, error)
		func (d *Dialer) DialContext(ctx context.Context, network, address string) (Conn, error)
		func (d *Dialer) MultipathTCP() bool
		func (d *Dialer) SetMultipathTCP(use bool)
			* ���� MultipathTCP 
			* ��Ҫ�ڵ��� Dialer.Dial �� Dialer.DialContext ����֮ǰ���� Dialer.SetMultipathTCP ����
	
	# type Error interface {
			error
			Timeout() bool   // Is the error a timeout?
				* �Ƿ��ǳ�ʱ�쳣�����ӡ�����д��
			Temporary() bool // Is the error temporary?
				* �Ƿ�����ʱ�쳣
				* �ѷ�������ʱ����û����ȷ���塣����� ����ʱ �������ǳ�ʱ������������������˳Ծ���
				* ����ʹ�ô˷�����
		}

		* NET ��ص��쳣
	
	# type Flags uint
		const (
			FlagUp           Flags = 1 << iota // interface is up
			FlagBroadcast                      // interface supports broadcast access capability
			FlagLoopback                       // interface is a loopback interface
			FlagPointToPoint                   // interface belongs to a point-to-point link
			FlagMulticast                      // interface supports multicast access capability
		)
		func (f Flags) String() string

	# type HardwareAddr []byte
		func ParseMAC(s string) (hw HardwareAddr, err error)
		func (a HardwareAddr) String() string
	
	# type IP []byte
		func IPv4(a, b, c, d byte) IP
		func LookupIP(host string) ([]IP, error)
		func ParseIP(s string) IP
			* ���IP��ַ�Ƿ����򷵻� nill
		func (ip IP) AppendText(b []byte) ([]byte, error)
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
		func (ip IP) IsPrivate() bool 
			* �Ƿ��Ǿ�����IP
			* �ж�һ����ַ�Ƿ�ΪRFC 1918�涨��˽��IPv4��ַ��RFC 4193�涨�ı���IPv6��ַ��
	
	# type IPAddr struct {
			IP   IP
			Zone string // IPv6 scoped addressing zone
		}
		func ResolveIPAddr(network, address string) (*IPAddr, error)
		func (a *IPAddr) Network() string
		func (a *IPAddr) String() string
	

	# type IPConn struct {
		}
		func DialIP(network string, laddr, raddr *IPAddr) (*IPConn, error)
		func ListenIP(network string, laddr *IPAddr) (*IPConn, error)
		func (c *IPConn) Close() error
		func (c *IPConn) File() (f *os.File, err error)
		func (c *IPConn) LocalAddr() Addr
		func (c *IPConn) Read(b []byte) (int, error)
		func (c *IPConn) ReadFrom(b []byte) (int, Addr, error)
		func (c *IPConn) ReadFromIP(b []byte) (int, *IPAddr, error)
		func (c *IPConn) ReadMsgIP(b, oob []byte) (n, oobn, flags int, addr *IPAddr, err error)
		func (c *IPConn) RemoteAddr() Addr
		func (c *IPConn) SetDeadline(t time.Time) error
			* ���÷��ͽ������ݳ�ʱ

		func (c *IPConn) SetReadBuffer(bytes int) error
		func (c *IPConn) SetReadDeadline(t time.Time) error
		func (c *IPConn) SetWriteBuffer(bytes int) error
		func (c *IPConn) SetWriteDeadline(t time.Time) error
		func (c *IPConn) SyscallConn() (syscall.RawConn, error)
		func (c *IPConn) Write(b []byte) (int, error)
		func (c *IPConn) WriteMsgIP(b, oob []byte, addr *IPAddr) (n, oobn int, err error)
		func (c *IPConn) WriteTo(b []byte, addr Addr) (int, error)
		func (c *IPConn) WriteToIP(b []byte, addr *IPAddr) (int, error)
	
	# type IPMask []byte
		func CIDRMask(ones, bits int) IPMask
		func IPv4Mask(a, b, c, d byte) IPMask
		func (m IPMask) Size() (ones, bits int)
		func (m IPMask) String() string
	
	# type IPNet struct {
			IP   IP     // network number
			Mask IPMask // network mask
		}
		func (n *IPNet) Contains(ip IP) bool
		func (n *IPNet) Network() string
		func (n *IPNet) String() string
	
	# type Interface struct {
			Index        int          // positive integer that starts at one, zero is never used
			MTU          int          // maximum transmission unit
			Name         string       // e.g., "en0", "lo0", "eth0.100"
			HardwareAddr HardwareAddr // IEEE MAC-48, EUI-48 and EUI-64 form
			Flags        Flags        // e.g., FlagUp, FlagLoopback, FlagMulticast
		}
		
		* ����ӿ���Ϣ

		func InterfaceByIndex(index int) (*Interface, error)
		func InterfaceByName(name string) (*Interface, error)
		func Interfaces() ([]Interface, error)
			* �����±꣬���ƣ����ر��ص�����ӿ���Ϣ

		func (ifi *Interface) Addrs() ([]Addr, error)
			* ���ص�ַ��Ϣ

		func (ifi *Interface) MulticastAddrs() ([]Addr, error)
			* ���ع㲥��ַ
	
	# type InvalidAddrError string
		func (e InvalidAddrError) Error() string
		func (e InvalidAddrError) Temporary() bool
		func (e InvalidAddrError) Timeout() bool
	
	# type KeepAliveConfig struct {
			Enable bool
			Idle time.Duration
			Interval time.Duration
			Count int
		}

		* ���� TCP ��������ѡ�
	
	# type ListenConfig struct {
			Control func(network, address string, c syscall.RawConn) error
			KeepAlive time.Duration
			KeepAliveConfig KeepAliveConfig
		}
		func (lc *ListenConfig) Listen(ctx context.Context, network, address string) (Listener, error)
		func (lc *ListenConfig) ListenPacket(ctx context.Context, network, address string) (PacketConn, error)
		func (lc *ListenConfig) MultipathTCP() bool
		func (lc *ListenConfig) SetMultipathTCP(use bool)
			* Ҫ�� Server ��ʹ��MultipathTCP���ڵ��� ListenConfig.Listen ����֮ǰ���� ListenConfig.SetMultipathTCP ������
	
	# type Listener interface {
			Accept() (Conn, error)
			Close() error
			Addr() Addr
		}
		func FileListener(f *os.File) (ln Listener, err error)
		func Listen(network, address string) (Listener, error)
	
	# type MX struct {
			Host string
			Pref uint16
		}
		
		* ��ȡ������MX(�ʼ�)��¼

		func LookupMX(name string) ([]*MX, error)
	
	# type NS struct {
			Host string
		}
		
		* ������DNS��������ַ��Ϣ

		func LookupNS(name string) ([]*NS, error)

	
	# type OpError struct {
			Op string
			Net string
			Source Addr
			Addr Addr
			Err error
		}

		* OpError �� net ���еĺ���ͨ�����صĴ������͡��������˲������������ͺʹ����ַ��

		func (e *OpError) Error() string
		func (e *OpError) Temporary() bool
		func (e *OpError) Timeout() bool
			* �Ƿ��ǳ�ʱ�쳣

		func (e *OpError) Unwrap() error
			* �ײ��쳣���� Err
	
	# type PacketConn interface {
			ReadFrom(p []byte) (n int, addr Addr, err error)
			WriteTo(p []byte, addr Addr) (n int, err error)
			Close() error
			LocalAddr() Addr
			SetDeadline(t time.Time) error
				* ���÷��ͽ������ݳ�ʱ
			SetReadDeadline(t time.Time) error
			SetWriteDeadline(t time.Time) error
		}
		func FilePacketConn(f *os.File) (c PacketConn, err error)
		func ListenPacket(network, address string) (PacketConn, error)
	
	# type ParseError struct {
			Type string
			Text string
		}
		func (e *ParseError) Error() string
	
	# type Resolver struct {
			PreferGo bool
			StrictErrors bool
			Dial func(ctx context.Context, network, address string) (Conn, error)
		}
		func (r *Resolver) LookupAddr(ctx context.Context, addr string) (names []string, err error)
		func (r *Resolver) LookupCNAME(ctx context.Context, host string) (cname string, err error)
		func (r *Resolver) LookupHost(ctx context.Context, host string) (addrs []string, err error)
		func (r *Resolver) LookupIP(ctx context.Context, network, host string) ([]IP, error)
		func (r *Resolver) LookupIPAddr(ctx context.Context, host string) ([]IPAddr, error)
		func (r *Resolver) LookupMX(ctx context.Context, name string) ([]*MX, error)
		func (r *Resolver) LookupNS(ctx context.Context, name string) ([]*NS, error)
		func (r *Resolver) LookupPort(ctx context.Context, network, service string) (port int, err error)
		func (r *Resolver) LookupSRV(ctx context.Context, service, proto, name string) (cname string, addrs []*SRV, err error)
		func (r *Resolver) LookupTXT(ctx context.Context, name string) ([]string, error)
		func (r *Resolver) LookupNetIP(ctx context.Context, network, host string) ([]netip.Addr, error)
	
	# type SRV struct {
			Target   string
			Port     uint16
			Priority uint16
			Weight   uint16
		}
		func LookupSRV(service, proto, name string) (cname string, addrs []*SRV, err error)
	
	# type TCPAddr struct {
			IP   IP
			Port int
			Zone string // IPv6 scoped addressing zone
		}
		func ResolveTCPAddr(network, address string) (*TCPAddr, error)
		func TCPAddrFromAddrPort(addr netip.AddrPort) *TCPAddr

		func (a *TCPAddr) Network() string
		func (a *TCPAddr) String() string
		func (a *TCPAddr) AddrPort() netip.AddrPort
	
	# type TCPConn struct {
		}
		func DialTCP(network string, laddr, raddr *TCPAddr) (*TCPConn, error)
		func (c *TCPConn) Close() error
		func (c *TCPConn) CloseRead() error
		func (c *TCPConn) CloseWrite() error
		func (c *TCPConn) File() (f *os.File, err error)
		func (c *TCPConn) LocalAddr() Addr
		func (c *TCPConn) Read(b []byte) (int, error)
		func (c *TCPConn) ReadFrom(r io.Reader) (int64, error)
		func (c *TCPConn) RemoteAddr() Addr
		func (c *TCPConn) SetDeadline(t time.Time) error
			* ���÷��ͽ������ݳ�ʱ

		func (c *TCPConn) SetKeepAlive(keepalive bool) error
		func (c *TCPConn) SetKeepAliveConfig(config KeepAliveConfig) error
		func (c *TCPConn) SetKeepAlivePeriod(d time.Duration) error
		func (c *TCPConn) SetLinger(sec int) error
		func (c *TCPConn) SetNoDelay(noDelay bool) error
		func (c *TCPConn) SetReadBuffer(bytes int) error
		func (c *TCPConn) SetReadDeadline(t time.Time) error
		func (c *TCPConn) SetWriteBuffer(bytes int) error
		func (c *TCPConn) SetWriteDeadline(t time.Time) error
		func (c *TCPConn) SyscallConn() (syscall.RawConn, error)
		func (c *TCPConn) Write(b []byte) (int, error)
		func (c *TCPConn) MultipathTCP() (bool, error)
			* ����Ƿ�֧�� MultipathTCP
	
	# type TCPListener struct {

		}
		
		* TCP ����

		func ListenTCP(network string, laddr *TCPAddr) (*TCPListener, error)
		func (l *TCPListener) Accept() (Conn, error)
		func (l *TCPListener) AcceptTCP() (*TCPConn, error)
			* ��������ȡ���ӡ������� Listener �� Close ����ʱ���᷵�� error
			* ������ errors.Is(err, net.ErrClosed) ���жϣ��Ƿ��������� Close ���� 
			
		func (l *TCPListener) Addr() Addr
		func (l *TCPListener) Close() error
			* �رռ�����ע�ⲻ��ر��Ѵ��������ӡ�

		func (l *TCPListener) File() (f *os.File, err error)
		func (l *TCPListener) SetDeadline(t time.Time) error
		func (l *TCPListener) SyscallConn() (syscall.RawConn, error)
	
	# type UDPAddr struct {
			IP   IP
			Port int
			Zone string // IPv6 scoped addressing zone
		}

		* UDP Addr��ַ

		func ResolveUDPAddr(network, address string) (*UDPAddr, error)
		func UDPAddrFromAddrPort(addr netip.AddrPort) *UDPAddr

		func (a *UDPAddr) Network() string
		func (a *UDPAddr) String() string
		func (a *UDPAddr) AddrPort() netip.AddrPort

	# type UDPConn struct {
		}
		func DialUDP(network string, laddr, raddr *UDPAddr) (*UDPConn, error)
		func ListenMulticastUDP(network string, ifi *Interface, gaddr *UDPAddr) (*UDPConn, error)
		func ListenUDP(network string, laddr *UDPAddr) (*UDPConn, error)
		func (c *UDPConn) Close() error
		func (c *UDPConn) File() (f *os.File, err error)
		func (c *UDPConn) LocalAddr() Addr
		func (c *UDPConn) Read(b []byte) (int, error)
		func (c *UDPConn) ReadFrom(b []byte) (int, Addr, error)
		func (c *UDPConn) ReadFromUDP(b []byte) (int, *UDPAddr, error)
		func (c *UDPConn) ReadMsgUDP(b, oob []byte) (n, oobn, flags int, addr *UDPAddr, err error)
		func (c *UDPConn) RemoteAddr() Addr
		func (c *UDPConn) SetDeadline(t time.Time) error
		func (c *UDPConn) SetReadBuffer(bytes int) error
		func (c *UDPConn) SetReadDeadline(t time.Time) error
		func (c *UDPConn) SetWriteBuffer(bytes int) error
		func (c *UDPConn) SetWriteDeadline(t time.Time) error
		func (c *UDPConn) SyscallConn() (syscall.RawConn, error)
		func (c *UDPConn) Write(b []byte) (int, error)
		func (c *UDPConn) WriteMsgUDP(b, oob []byte, addr *UDPAddr) (n, oobn int, err error)
		func (c *UDPConn) WriteTo(b []byte, addr Addr) (int, error)
		func (c *UDPConn) WriteToUDP(b []byte, addr *UDPAddr) (int, error)
		func (c *UDPConn) ReadFromUDPAddrPort(b []byte) (n int, addr netip.AddrPort, err error)
		func (c *UDPConn) ReadMsgUDPAddrPort(b, oob []byte) (n, oobn, flags int, addr netip.AddrPort, err error)
		func (c *UDPConn) WriteToUDPAddrPort(b []byte, addr netip.AddrPort) (int, error)
		func (c *UDPConn) WriteMsgUDPAddrPort(b, oob []byte, addr netip.AddrPort) (n, oobn int, err error)
	
	# type UnixAddr struct {
			Name string
			Net  string
		}
		func ResolveUnixAddr(network, address string) (*UnixAddr, error)
		func (a *UnixAddr) Network() string
		func (a *UnixAddr) String() string
	
	# type UnixConn struct {
		}
		func DialUnix(network string, laddr, raddr *UnixAddr) (*UnixConn, error)
		func ListenUnixgram(network string, laddr *UnixAddr) (*UnixConn, error)
		func (c *UnixConn) Close() error
		func (c *UnixConn) CloseRead() error
		func (c *UnixConn) CloseWrite() error
		func (c *UnixConn) File() (f *os.File, err error)
		func (c *UnixConn) LocalAddr() Addr
		func (c *UnixConn) Read(b []byte) (int, error)
		func (c *UnixConn) ReadFrom(b []byte) (int, Addr, error)
		func (c *UnixConn) ReadFromUnix(b []byte) (int, *UnixAddr, error)
		func (c *UnixConn) ReadMsgUnix(b, oob []byte) (n, oobn, flags int, addr *UnixAddr, err error)
		func (c *UnixConn) RemoteAddr() Addr
		func (c *UnixConn) SetDeadline(t time.Time) error
		func (c *UnixConn) SetReadBuffer(bytes int) error
		func (c *UnixConn) SetReadDeadline(t time.Time) error
		func (c *UnixConn) SetWriteBuffer(bytes int) error
		func (c *UnixConn) SetWriteDeadline(t time.Time) error
		func (c *UnixConn) SyscallConn() (syscall.RawConn, error)
		func (c *UnixConn) Write(b []byte) (int, error)
		func (c *UnixConn) WriteMsgUnix(b, oob []byte, addr *UnixAddr) (n, oobn int, err error)
		func (c *UnixConn) WriteTo(b []byte, addr Addr) (int, error)
		func (c *UnixConn) WriteToUnix(b []byte, addr *UnixAddr) (int, error)
	
	# type UnixListener struct {
		}
		func ListenUnix(network string, laddr *UnixAddr) (*UnixListener, error)
		func (l *UnixListener) Accept() (Conn, error)
		func (l *UnixListener) AcceptUnix() (*UnixConn, error)
		func (l *UnixListener) Addr() Addr
		func (l *UnixListener) Close() error
		func (l *UnixListener) File() (f *os.File, err error)
		func (l *UnixListener) SetDeadline(t time.Time) error
		func (l *UnixListener) SetUnlinkOnClose(unlink bool)
		func (l *UnixListener) SyscallConn() (syscall.RawConn, error)
	
	# type UnknownNetworkError string
		func (e UnknownNetworkError) Error() string
		func (e UnknownNetworkError) Temporary() bool
		func (e UnknownNetworkError) Timeout() bool
	
----------------
����
----------------
	func JoinHostPort(host, port string) string
	func LookupAddr(addr string) (names []string, err error)
		* ����IP��������������Ϣ

	func LookupCNAME(host string) (cname string, err error)
	func LookupHost(host string) (addrs []string, err error)
		* ����������Ϣ������IP��Ϣ

	func LookupPort(network, service string) (port int, err error)
	func LookupTXT(name string) ([]string, error)
	func ParseCIDR(s string) (IP, *IPNet, error)
	func Pipe() (Conn, Conn)
	func SplitHostPort(hostport string) (host, port string, err error)
		* ����������ַ�е�HOST�Ͷ˿�
			if clientIP, port, err := net.SplitHostPort(req.RemoteAddr); err == nil {
				...
			}

	


----------------
Demo
----------------
	# ��ȡ���� IP
		func host() []string {
			interfaces, err := net.InterfaceAddrs()
			if err != nil {
				return nil
			}

			var ipSlice []string

			for _, v := range interfaces {
				if ipNet, ok := v.(*net.IPNet); ok {

					if ipNet.IP.IsLoopback() {
						// ���ػػ���ַ
						continue
					}

					// ֻҪ���ؾ�������ַ
					if ipv4 := ipNet.IP.To4(); ipv4 != nil && ipv4.IsPrivate() {
						ipSlice = append(ipSlice, ipNet.IP.To4().String())
					}
				}
			}
			return ipSlice
		}
