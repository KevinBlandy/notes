---------------------
tcp
---------------------
	# �ο�
		https://colobu.com/2014/12/02/go-socket-programming-TCP/
	
	# TCP��ַ����
		type TCPAddr struct {
			IP   IP
			Port int
			Zone string // IPv6 scoped addressing zone
		}
		func ResolveTCPAddr(network, address string) (*TCPAddr, error)
			* ����һ����ַΪRctpAddr
		
		func (a *TCPAddr) Network() string
		func (a *TCPAddr) String() string
	

---------------------
tcp - �ͻ���
---------------------
	# ��������
		func DialTCP(net string, laddr, raddr *TCPAddr) (c *TCPConn, err error)
			* net ָ��TCP�汾��: tcp4
			* laddr ָ�����ص�ַ����������Ϊnil
			* raddr ָ��Զ�̵�ַ
	
	# net.TCPConn���ӷ���
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
		// �������Ӷ���
		addr, _ := net.ResolveTCPAddr("tcp4", "127.0.0.1:1024")
		conn, _ := net.DialTCP("tcp4", nil, addr)
		defer  conn.Close()

		// �ӱ�׼�������ж�ȡһ������

		reader := bufio.NewReader(os.Stdin)
		fmt.Print(">> ")
		text, _ :=  reader.ReadSlice('\n')

		// ���͸�������
		io.WriteString(conn, string(text))

		// �ӷ�������ȡһ�����ݴ�ӡ
		reader = bufio.NewReader(conn)
		text, _ = reader.ReadSlice('\n')
		fmt.Printf("��������Ӧ:%s\n", text)
	
---------------------
tcp - �����
---------------------
	# ����������
		func ListenTCP(network string, laddr *TCPAddr) (*TCPListener, error)
			* ר�ż���TCP

	
	# net.TCPListener 
		type TCPListener struct {}

		func (l *TCPListener) Accept() (Conn, error)
			* ��ʼ��������������ͨ�õ�Conn

		func (l *TCPListener) AcceptTCP() (*TCPConn, error)
			* ��ʼ��������������TCPConn
			* ��� ServerClose ��ô������������������� error
				accept tcp [::]:1024: use of closed network connection

		func (l *TCPListener) Addr() Addr
		func (l *TCPListener) Close() error
		func (l *TCPListener) File() (f *os.File, err error)
		func (l *TCPListener) SetDeadline(t time.Time) error
		func (l *TCPListener) SyscallConn() (syscall.RawConn, error)
	
	# Demo
		// ���ؼ�����ַ
		addr, err := net.ResolveTCPAddr("tcp4", "0.0.0.0:1024")
		if err != nil {
			fmt.Fprint(os.Stderr, err)
		}

		// ����������
		server, err := net.ListenTCP("tcp4", addr)
		if err != nil {
			fmt.Fprint(os.Stderr, err)
		}

		// ��ʼ��������
		for {
			conn, err := server.AcceptTCP()

			// ����ͻ�������
			go func(conn *net.TCPConn, err error) {
				if err != nil {
					fmt.Fprint(os.Stderr, err)
					conn.Close()
					return
				}
				// ��ȡ�ͻ��˵�����
				text, _ := bufio.NewReader(conn).ReadSlice('\n')
				fmt.Printf("�յ��ͻ�����Ϣ:%s", text)

				// ��Ӧ���ͻ���
				io.WriteString(conn, fmt.Sprintf("���յ�����Ϣ:%s\n", string(text)))

				// �رտͻ���
				conn.Close()
			}(conn, err)
		}