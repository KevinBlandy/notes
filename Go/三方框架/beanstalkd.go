------------------------
Beanstalkd
------------------------
	# 一款C语言编写的消息队列框架
	# Package
		github.com/beanstalkd/go-beanstalk

		https://github.com/beanstalkd/beanstalkd


------------------------
var
------------------------
	const DefaultDialTimeout = 10 * time.Secondconst DefaultDialTimeout = 10 * time.Second
	const DefaultKeepAlivePeriod = 10 * time.Second
	const NameChars = `\-+/;.$_()0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`

	var (
		ErrBadFormat  = errors.New("bad command format")
		ErrBuried     = errors.New("buried")
		ErrDeadline   = errors.New("deadline soon")
		ErrDraining   = errors.New("draining")
		ErrInternal   = errors.New("internal error")
		ErrJobTooBig  = errors.New("job too big")
		ErrNoCRLF     = errors.New("expected CR LF")
		ErrNotFound   = errors.New("not found")
		ErrNotIgnored = errors.New("not ignored")
		ErrOOM        = errors.New("server is out of memory")
		ErrTimeout    = errors.New("timeout")
		ErrUnknown    = errors.New("unknown command")
	)

	var (
		ErrEmpty   = errors.New("name is empty")
		ErrBadChar = errors.New("name has bad char") // contains a character not in NameChars
		ErrTooLong = errors.New("name is too long")
	)


------------------------
type
------------------------
	# type Conn struct {
			Tube
			TubeSet
		}
		func Dial(network, addr string) (*Conn, error)
		func DialTimeout(network, addr string, timeout time.Duration) (*Conn, error)
		func NewConn(conn io.ReadWriteCloser) *Conn
		func (c *Conn) Bury(id uint64, pri uint32) error
		func (c *Conn) Close() error
		func (c *Conn) Delete(id uint64) error
		func (c *Conn) KickJob(id uint64) error
		func (c *Conn) ListTubes() ([]string, error)
		func (c *Conn) Peek(id uint64) (body []byte, err error)
		func (c *Conn) Release(id uint64, pri uint32, delay time.Duration) error
		func (c *Conn) Stats() (map[string]string, error)
		func (c *Conn) StatsJob(id uint64) (map[string]string, error)
		func (c *Conn) Touch(id uint64) error
	
	# type ConnError struct {
			Conn *Conn
			Op   string
			Err  error
		}
		func (e ConnError) Error() string
		func (e ConnError) Unwrap() error

	# type NameError struct {
			Name string
			Err  error
		}
		func (e NameError) Error() string
		func (e NameError) Unwrap() error
					
	# type Tube struct {
			Conn *Conn
			Name string
		}
		func (t *Tube) Kick(bound int) (n int, err error)
		func (t *Tube) Pause(d time.Duration) error
		func (t *Tube) PeekBuried() (id uint64, body []byte, err error)
		func (t *Tube) PeekDelayed() (id uint64, body []byte, err error)
		func (t *Tube) PeekReady() (id uint64, body []byte, err error)
		func (t *Tube) Put(body []byte, pri uint32, delay, ttr time.Duration) (id uint64, err error)
		func (t *Tube) Stats() (map[string]string, error)
	
	# type TubeSet struct {
			Conn *Conn
			Name map[string]bool
		}
		func NewTubeSet(c *Conn, name ...string) *TubeSet
		func (t *TubeSet) Reserve(timeout time.Duration) (id uint64, body []byte, err error)

------------------------
func
------------------------
