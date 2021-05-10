-----------------------
file-rotatelogs
-----------------------
	# logrus本身不支持滚动文件，需要第三方依赖
	# file-rotatelogs
		https://github.com/lestrrat-go/file-rotatelogs
	
		"github.com/lestrrat-go/file-rotatelogs"
	
	
	# 一些要点
		* 最大保存天数/最大保存数量冲突，不能同时设置
	
	# Demo
		var logFile, err = rotatelogs.New(
			"log/app.%Y%m%d%H%M",						// 日志文件输出格式
			rotatelogs.ForceNewFile(),					// 强制创建新文件
			rotatelogs.WithClock(rotatelogs.Local),		// 获取时间函数
			rotatelogs.WithLocation(time.Local),		// 时区
			rotatelogs.WithLinkName("log/app.log"),		// 最新的日志文件软连接
			rotatelogs.WithMaxAge(-1),					// 文件无限期保存
			rotatelogs.WithRotationCount(3),			// 出了当前的输出文件，历史文件最多保存3个
			rotatelogs.WithRotationSize(1024),			// 单个文件最多1个字节，就会切割
			rotatelogs.WithRotationTime(time.Hour * 24),// 单个文件最多1个小时，就会切割
			rotatelogs.WithHandler(rotatelogs.HandlerFunc(func(event rotatelogs.Event) {
				if rotatelogs.FileRotatedEventType == event.Type() {
					// 日志切割事件
				}
			})),
		)
		if err != nil {
			log.Fatalf("创建日志文件异常：%s\n", err.Error())
		}
		log.SetOutput(io.MultiWriter(logFile, os.Stdout))  // 组合流，不但输出到日志，还输出到标准输出

	
-----------------------
var
-----------------------
	var Local = clockFn(time.Now)
		* 获取本地时间的接口实现（默认）

	var UTC = clockFn(func() time.Time { return time.Now().UTC() })
		* 获取UTC时间的接口实现

-----------------------
type
-----------------------
	# type Clock interface {
			Now() time.Time
		}
		
		* 文件格式化使用的，获取时间接口

	# type Event interface {
			Type() EventType
		}

	# type EventType int 
		const (
			InvalidEventType EventType = iota		// 错误的异常类型
			FileRotatedEventType					// 文件滚动事件
		)

	# type FileRotatedEvent struct {
			// contains filtered or unexported fields
		}
		func (e *FileRotatedEvent) CurrentFile() string
		func (e *FileRotatedEvent) PreviousFile() string
		func (e *FileRotatedEvent) Type() EventType

	# type Handler interface {
			Handle(Event)
		}

	# type HandlerFunc func(Event)
		func (h HandlerFunc) Handle(e Event)
	
	# type Option interface {
			Name() string
			Value() interface{}
		}
		
		* 文件切割的配置项

		func ForceNewFile() Option
			* 强制创建新文件

		func WithClock(c Clock) Option
		func WithHandler(h Handler) Option
			* 设置handler

		func WithLinkName(s string) Option
			* 生成软连接地址，永远指向最新的文件

		func WithLocation(loc *time.Location) Option
			* 设置时区

		func WithMaxAge(d time.Duration) Option
			* 设置文件清理前的最长保存时间
			* 默认7天，设置为-1，表示不限制

		func WithRotationCount(n uint) Option
			* 设置文件清理前最多保存的个数
			* 设置为-1表示不限制

		func WithRotationSize(s int64) Option
			* 设置文件的最大体积

		func WithRotationTime(d time.Duration) Option
			* 设置日志分割的时间，隔多久分割一次
			* 最小为1分钟轮询。默认60s  低于1分钟就按1分钟来

	# type RotateLogs struct {
		}
		func New(p string, options ...Option) (*RotateLogs, error)
			* 创建新的日志，指定输出文件，以及配置信息
				 "/path/to/access_log.%Y%m%d%H%M",

		func (rl *RotateLogs) Close() error
			* 关闭

		func (rl *RotateLogs) CurrentFileName() string
			* 返回当前文件名称

		func (rl *RotateLogs) Rotate() error
		func (rl *RotateLogs) Write(p []byte) (n int, err error)
			* 实现Writer接口，写入数据

-----------------------
func
-----------------------