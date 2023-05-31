----------------
go-redis
----------------
	# Go推荐的Redis客户端
	
	# Client
		* 使用 Do() 方法执行尚不支持或者任意命令
			val, err := rdb.Do(ctx, "get", "key").Result()
			if err != nil {
				if err == redis.Nil {
					fmt.Println("key does not exists")
					return
				}
				panic(err)
			}
			fmt.Println(val.(string))

			* Do() 方法返回 Cmd 类型，你可以使用它获取你 想要的类型：
		
	# Conn
		*  最底层的连接，不建议获取，如果获取的话，必须要返回给go-redis（cn.Close()）

	# Cmd
		* 本质上就是一个命令，封装了结果，参数，异常等等信息
		* cmd 可以获取各种类型的结果
			s, err := cmd.Text()
			flag, err := cmd.Bool()

			num, err := cmd.Int()
			num, err := cmd.Int64()
			num, err := cmd.Uint64()
			num, err := cmd.Float32()
			num, err := cmd.Float64()

			ss, err := cmd.StringSlice()
			ns, err := cmd.Int64Slice()
			ns, err := cmd.Uint64Slice()
			fs, err := cmd.Float32Slice()
			fs, err := cmd.Float64Slice()
			bs, err := cmd.BoolSlice()


----------------
var
----------------
	const KeepTTL = -1
		* KeepTTL是Redis的KEEPTTL选项，用于保持现有的TTL，要求Redis版本 >= 6.0

	const Nil = proto.Nil
		* redis.Nil 是一种特殊的错误，严格意义上来说它并不是错误，而是代表一种状态，例如你使用 Get 命令获取 key 的值，当 key 不存在时，返回 redis.Nil
		* 在其他比如 BLPOP 、 ZSCORE 也有类似的响应，你需要区 分错误
			val, err := rdb.Get(ctx, "key").Result()
			switch {
			case err == redis.Nil:
				fmt.Println("key不存在")
			case err != nil:
				fmt.Println("错误", err)
			case val == "":
				fmt.Println("值是空字符串")
			}

	const TxFailedErr = proto.RedisError("redis: transaction failed")
	var ErrClosed = pool.ErrClosed

----------------
type
----------------
	# type BitCount struct {
			Start, End int64
		}
	
	# type BoolCmd struct {
		}
	
	# type BoolSliceCmd struct {
		}
	
	# type ChannelOption func(c *channel)
		func WithChannelHealthCheckInterval(d time.Duration) ChannelOption
		func WithChannelSendTimeout(d time.Duration) ChannelOption
		func WithChannelSize(size int) ChannelOption
	
	# type Client struct {
		}
		func NewClient(opt *Options) *Client
		func NewFailoverClient(failoverOpt *FailoverOptions) *Client
	
	# type Cmd struct {
		}
	
	# type Cmdable interface {
			// ... 很多，略
		}
	
	# type Cmder interface {
		}
	
	# type CommandInfo struct {
		}
	
	# type CommandsInfoCmd struct {
			// contains filtered or unexported fields
		}
	
	# type Conn struct {
		}
	
	# type ConsistentHash interface {
			Get(string) string
		}

	# type DialHook func(ctx context.Context, network, addr string) (net.Conn, error)
	# type DurationCmd struct {
		}
	
	# type Error interface {
			error
			RedisError()
				* 无操作的函数，但用于区分作为Redis错误的类型和普通错误
				* 如果一个类型有一个RedisError方法，它就是一个Redis错误。
		}
	
	# type FailoverOptions struct {
			//... 略
		}
	
	# type FloatCmd struct {
		}
	
	# type FloatSliceCmd struct {
		}
	
	# type GeoLocation struct {
		}
	
	# type GeoLocationCmd struct {
			// contains filtered or unexported fields
		}
	
	# type GeoPos struct {
			Longitude, Latitude float64
		}
	
	# type GeoPosCmd struct {
			// contains filtered or unexported fields
		}
	
	# type GeoRadiusQuery struct {
		}

	# type GeoSearchLocationCmd struct {
		}
	# type GeoSearchLocationQuery struct {
		}

	# type GeoSearchQuery struct {
		}

	# type GeoSearchStoreQuery struct {
			GeoSearchQuery
			StoreDist bool
		}
	
	# type Hook interface {
			DialHook(hook DialHook) DialHook
			ProcessHook(hook ProcessHook) ProcessHook
			ProcessPipelineHook(hook ProcessPipelineHook) ProcessPipelineHook
		}
	
	# type IntCmd struct {
			// contains filtered or unexported fields
		}
	
	# type IntSliceCmd struct {
			// contains filtered or unexported fields
		}
	
	# type KeyValue struct {
			Key   string
			Value string
		}
	
	# type KeyValueSliceCmd struct {
		}
	
	# type LPosArgs struct {
			Rank, MaxLen int64
		}
	
	# type Limiter interface {
			Allow() error
			ReportResult(result error)
		}
		
	# type MapStringIntCmd struct {
		}
	
	# type MapStringInterfaceCmd struct {
		}
	
	# type MapStringStringCmd struct {
		}
	
	# type MapStringStringSliceCmd struct {
		}
	
	# type Message struct {
			Channel      string
			Pattern      string
			Payload      string
			PayloadSlice []string
		}

	








----------------
func
----------------
	func HasErrorPrefix(err error, prefix string) bool
	func NewDialer(opt *Options) func(context.Context, string, string) (net.Conn, error)
	func SetLogger(logger internal.Logging)
	func Version() string
