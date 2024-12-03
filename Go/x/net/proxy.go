----------------
Proxy
----------------
	# 网络代理相关的设置



----------------
var
----------------
	var Direct = direct{}

----------------
type
----------------

	# type Auth struct {
		User, Password string
		}
	
	# type ContextDialer interface {
			DialContext(ctx context.Context, network, address string) (net.Conn, error)
		}

	# type Dialer interface {
			Dial(network, addr string) (c net.Conn, err error)
		}
		func FromEnvironment() Dialer
		func FromEnvironmentUsing(forward Dialer) Dialer
		func FromURL(u *url.URL, forward Dialer) (Dialer, error)
		func SOCKS5(network, address string, auth *Auth, forward Dialer) (Dialer, error)
	
	# type PerHost struct {
		}
		func NewPerHost(defaultDialer, bypass Dialer) *PerHost
		func (p *PerHost) AddFromString(s string)
		func (p *PerHost) AddHost(host string)
		func (p *PerHost) AddIP(ip net.IP)
		func (p *PerHost) AddNetwork(net *net.IPNet)
		func (p *PerHost) AddZone(zone string)
		func (p *PerHost) Dial(network, addr string) (c net.Conn, err error)
		func (p *PerHost) DialContext(ctx context.Context, network, addr string) (c net.Conn, err error)

----------------
func
----------------

	func Dial(ctx context.Context, network, address string) (net.Conn, error)
	func RegisterDialerType(scheme string, f func(*url.URL, Dialer) (Dialer, error))
