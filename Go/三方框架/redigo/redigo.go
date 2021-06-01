-----------------------------
redigo
-----------------------------
	# Doc
		https://pkg.go.dev/github.com/gomodule/redigo/redis
		https://github.com/gomodule/redigo

	# package
		 github.com/gomodule/redigo/redis

-----------------------------
var
-----------------------------
	var ErrNil = errors.New("redigo: nil returned")
		* 从redis服务器获取到的数据是空
		* 尝试对返回的空数据，进行封装，会返回这个异常

	var ErrPoolExhausted = errors.New("redigo: connection pool exhausted")
		* 连接池耗尽，从连接池中无法获取链接的时候回返回该异常

-----------------------------
type
-----------------------------
	# type Args []interface{}
		func (args Args) Add(value ...interface{}) Args
			* 直接将值追加到args后面的结果返回，返回新的，不会修改args
			* 源码
				return append(args, value...)

		func (args Args) AddFlat(v interface{}) Args
			* 据值的类型存储，分为以下五种情况：Struct Slice Map Ptr 其他类型
			* 类型与映射
				Struct
					* 根据反射读取所有导出字段，先把字段名称以字符串添加到参数，再把这个字段的值添加到参数
				Slice
					* 把每个元素都添加到参数
				Slice
					* 遍历Map每一项，先把KEY添加到参数，再把VALU添加到参数
				Ptr
					* 指针，就是指针，不会去读取指针的值
				其他类型
					* 直接添加到参数后面
			* 如果参数是struct，支持的注解，主要是映射在Redis的Hash上
				var p1, p2 struct {
					Title  string `redis:"title"`
					Author string `redis:"author"`
					Body   string `redis:"body"`
				}
			
			
	
	# type Argument interface {
			RedisArg() interface{}
		}
	
	# type Conn interface {
			Close() error
				* 关闭链接
			
			Err() error
				* 获取异常信息

			Do(commandName string, args ...interface{}) (reply interface{}, err error)
				* 执行命令，返回结果

			Send(commandName string, args ...interface{}) error
				* 发送一个或者多个命令
			
			Flush() error
				* 把Send发送的命令，发送到服务器执行

			Receive() (reply interface{}, err error)
				* 获取Flush后，返回的结果
		}
		func Dial(network, address string, options ...DialOption) (Conn, error)
		func DialContext(ctx context.Context, network, address string, options ...DialOption) (Conn, error)
			* 创建链接，通过options设置N个参数
				c, err := redis.Dial("tcp", ":6379")

		func DialTimeout(network, address string, ...) (Conn, error)
		func DialURL(rawurl string, options ...DialOption) (Conn, error)
			* 根据Redis的URL创建链接
		func NewConn(netConn net.Conn, readTimeout, writeTimeout time.Duration) Conn
		func NewLoggingConn(conn Conn, logger *log.Logger, prefix string) Conn
		func NewLoggingConnFilter(conn Conn, logger *log.Logger, prefix string, skip func(cmdName string) bool) Conn
	
	# type ConnWithTimeout interface {
			Conn
			DoWithTimeout(timeout time.Duration, commandName string, args ...interface{}) (reply interface{}, err error)
			ReceiveWithTimeout(timeout time.Duration) (reply interface{}, err error)
		}
	
	# type DialOption struct {
		}

		* Redis服务器连接参数

		func DialClientName(name string) DialOption
		func DialConnectTimeout(d time.Duration) DialOption
			* 设置连接超时时间
		func DialContextFunc(f func(ctx context.Context, network, addr string) (net.Conn, error)) DialOption
		func DialDatabase(db int) DialOption
			* 设置连接数据库
		func DialKeepAlive(d time.Duration) DialOption
		func DialNetDial(dial func(network, addr string) (net.Conn, error)) DialOption
		func DialPassword(password string) DialOption
			* 设置连接redis密码
		func DialReadTimeout(d time.Duration) DialOption	
			* 设置读超时时间
		func DialTLSConfig(c *tls.Config) DialOption
			* 设置TLS配置信息
		func DialTLSHandshakeTimeout(d time.Duration) DialOption
		func DialTLSSkipVerify(skip bool) DialOption
			* 是否跳过TLS验证
		func DialUseTLS(useTLS bool) DialOption
			* 是否使用TLS
		func DialUsername(username string) DialOption
			* 账户名称
		func DialWriteTimeout(d time.Duration) DialOption
			* 设置读超时时间
	
	# type Error string
		func (err Error) Error() string
	
	# type Message struct {
			Channel string	 // 频道名称
			Pattern string	//  频道模式（格式）
			Data []byte		//  数据
		}
	# type Pong struct {
			Data string
		}
	
	# type Pool struct {
			Dial func() (Conn, error)
				* 创建连接的方法
			
			DialContext func(ctx context.Context) (Conn, error)
				* 创建连接的方法，参数多了context

			TestOnBorrow func(c Conn, t time.Time) error
				* 在链接使用之前，检查状态，t 是链接返回的时间
				* 如果函数返回错误，则连接将被删除

			MaxIdle int
			MaxActive int
			IdleTimeout time.Duration
			Wait bool
				* 是否阻塞Get方法，直到它获取到最新的链接

			MaxConnLifetime time.Duration
		}

		* 连接池

		func NewPool(newFn func() (Conn, error), maxIdle int) *Pool
			* 废弃方法，自己创建 Pool 实例
		func (p *Pool) ActiveCount() int
			* 获取存活数量
		func (p *Pool) Close() error
			* 关闭
		func (p *Pool) Get() Conn
		func (p *Pool) GetContext(ctx context.Context) (Conn, error)
			* 获取一个链接
		func (p *Pool) IdleCount() int
			* 获取空闲数量
		func (p *Pool) Stats() PoolStats
			*  获取池的状态


	# type PoolStats struct {
			ActiveCount int		// 活动链接数量
			IdleCount int		// 空闲链接数量
			WaitCount int64		// 等待数量
			WaitDuration time.Duration // 等待超时时间
		}

		* 连接池状态
	
	# type PubSubConn struct {
			Conn Conn
		}
	
		* 订阅通道

		func (c PubSubConn) Close() error
			* 关闭订阅
		func (c PubSubConn) PSubscribe(channel ...interface{}) error
			* 订阅一个或多个符合给定模式的频道
		func (c PubSubConn) PUnsubscribe(channel ...interface{}) error
			* 退订所有给定模式的频道
		func (c PubSubConn) Ping(data string) error
			* 测试客户端是否能够继续连通
		func (c PubSubConn) Receive() interface{}
			* 获取信息，可能会返回异常信息，如果是消息，则是：Message
		func (c PubSubConn) ReceiveWithTimeout(timeout time.Duration) interface{}
			* 获取消息，指定超时时间
		func (c PubSubConn) Subscribe(channel ...interface{}) error
			* 订阅给定的一个或多个频道的信息
		func (c PubSubConn) Unsubscribe(channel ...interface{}) error
			* 指退订给定的频道

	# type Scanner interface {
			RedisScan(src interface{}) error
		}
	
	# type Script struct {
		}
		
		* 脚本

		func NewScript(keyCount int, src string) *Script
		func (s *Script) Do(c Conn, keysAndArgs ...interface{}) (interface{}, error)
		func (s *Script) Hash() string
		func (s *Script) Load(c Conn) error
		func (s *Script) Send(c Conn, keysAndArgs ...interface{}) error
		func (s *Script) SendHash(c Conn, keysAndArgs ...interface{}) error
	
	# type SlowLog struct {
			ID int64
			Time time.Time
			ExecutionTime time.Duration
			Args []string
			ClientAddr string
			ClientName string
		}
		func SlowLogs(result interface{}, err error) ([]SlowLog, error)
	
	# type Subscription struct {
			Kind string
			Channel string
			Count int
		}

		* 订阅配置

		

-----------------------------
func
-----------------------------
	func Bool(reply interface{}, err error) (bool, error)
		* 把结果转换为bool值
		* 如果err不等于nil，返回false，反之根据以下条件
			integer         value != 0, nil
			bulk string     strconv.ParseBool(reply)
			nil             false, ErrNil
			other           false, error

	func ByteSlices(reply interface{}, err error) ([][]byte, error)
	func Bytes(reply interface{}, err error) ([]byte, error)
	func DoWithTimeout(c Conn, timeout time.Duration, cmd string, args ...interface{}) (interface{}, error)
		* 执行一次Redis操作，设置超时时间

	func Float64(reply interface{}, err error) (float64, error)
	func Float64s(reply interface{}, err error) ([]float64, error)
	func Int(reply interface{}, err error) (int, error)
	func Int64(reply interface{}, err error) (int64, error)
		* 尝试把结果转换为数值类型

	func Int64Map(result interface{}, err error) (map[string]int64, error)
	func Int64s(reply interface{}, err error) ([]int64, error)
	func IntMap(result interface{}, err error) (map[string]int, error)
	func Ints(reply interface{}, err error) ([]int, error)
	func MultiBulk(reply interface{}, err error) ([]interface{}, error)
	func Positions(result interface{}, err error) ([]*[2]float64, error)
	func ReceiveWithTimeout(c Conn, timeout time.Duration) (interface{}, error)

	func Scan(src []interface{}, dest ...interface{}) ([]interface{}, error)
		* 把返回数据，封装到dest

	func ScanSlice(src []interface{}, dest interface{}, fieldNames ...string) error
		* 把返回数据封装到切片
		* 可以通过 fieldNames 指定要封装的字段（切片可以是结构体）
	
	func ScanStruct(src []interface{}, dest interface{}) error
		* 把返回数据封装到Map

	func String(reply interface{}, err error) (string, error)
		* 把结果转换为字符串

	func StringMap(result interface{}, err error) (map[string]string, error)
		* 把结果转换为Map
	
	func Strings(reply interface{}, err error) ([]string, error)
		* 把结果转换为字符串切片

	func Uint64(reply interface{}, err error) (uint64, error)
	func Uint64Map(result interface{}, err error) (map[string]uint64, error)
	func Uint64s(reply interface{}, err error) ([]uint64, error)

	func Values(reply interface{}, err error) ([]interface{}, error)
		* 把返回值的value，封装为切片
		* 好几个封装方法的形参都是要求切片形式的返回值