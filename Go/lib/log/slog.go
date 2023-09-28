--------------
slog
--------------
	# 标准库的日志增强
	# 文档
		https://pkg.go.dev/log/slog

--------------
var
--------------
	const (
		TimeKey = "time"
			* 输出日期时间的key

		LevelKey = "level"
			* 输出日志级别的key

		MessageKey = "msg"
			* 输出日志消息的key

		SourceKey = "source"
			* 输出源文件（调用）的key
	)

--------------
type
--------------
	# type Attr struct {
			Key   string
			Value Value
		}

		* 日志中的输出属性

		func Any(key string, value any) Attr
		func Bool(key string, v bool) Attr
		func Duration(key string, v time.Duration) Attr
		func Float64(key string, v float64) Attr
		func Group(key string, args ...any) Attr
			* 构建一个分组属性
				group := slog.Group("http",
					slog.Group("request",
						slog.String("method", "Get"),
						slog.String("Host", "go.dev")),
					slog.Group("response",
						slog.Int("status", 200),
						slog.String("Content-Type", "application/json")),
				)

				{
					"http":{
						"request":{"method":"Get","Host":"go.dev"},
						"response":{"status":200,"Content-Type":"application/json"}
					}
				}

		func Int(key string, value int) Attr
		func Int64(key string, value int64) Attr
		func String(key, value string) Attr
		func Time(key string, v time.Time) Attr
		func Uint64(key string, v uint64) Attr

		func (a Attr) Equal(b Attr) bool
		func (a Attr) String() string
	
	# type Handler interface {
			Enabled(context.Context, Level) bool
				* 是否在指定级别启用

			Handle(context.Context, Record) error
				* 处理日志记录

			WithAttrs(attrs []Attr) Handler
				* 给此 handler 额外新增一些输出属性，在最后
			WithGroup(name string) Handler
				* 给此 handler 额外新增一个分组，在最后
		}

		* 日志处理器
	
	# type HandlerOptions struct {
			AddSource bool
				* 是否添加调用信息

			Level Leveler
				* 日志级别动态修改句柄
				
			ReplaceAttr func(groups []string, a Attr) Attr
				* 替换属性，在输出前会对非 group 属性进行重写
				* 如果返回 nil，则该属性会被丢弃

		}

		* handler属性配置
	
	# type JSONHandler struct {
		}

		* json 处理器

		func NewJSONHandler(w io.Writer, opts *HandlerOptions) *JSONHandler
		func (h *JSONHandler) Enabled(_ context.Context, level Level) bool
		func (h *JSONHandler) Handle(_ context.Context, r Record) error
		func (h *JSONHandler) WithAttrs(attrs []Attr) Handler
		func (h *JSONHandler) WithGroup(name string) Handler
	
	# type Kind int
		const (
			KindAny Kind = iota
			KindBool
			KindDuration
			KindFloat64
			KindInt64
			KindString
			KindTime
			KindUint64
			KindGroup
			KindLogValuer
		)
		func (k Kind) String() string
	
	# type Level int
			
		* 日志级别

		const (
			LevelDebug Level = -4
			LevelInfo  Level = 0
			LevelWarn  Level = 4
			LevelError Level = 8
		)

		func (l Level) Level() Level
		func (l Level) MarshalJSON() ([]byte, error)
		func (l Level) MarshalText() ([]byte, error)
		func (l Level) String() string
		func (l *Level) UnmarshalJSON(data []byte) error
		func (l *Level) UnmarshalText(data []byte) error
	
	# type LevelVar struct {
		}
		
		* 日志变量，可以修改日志级别，线程安全的

		func (v *LevelVar) Level() Level
		func (v *LevelVar) MarshalText() ([]byte, error)
		func (v *LevelVar) Set(l Level)
		func (v *LevelVar) String() string
		func (v *LevelVar) UnmarshalText(data []byte) error

		* 如：
			// 动态的日志级别
			var level = &slog.LevelVar{}
			level.Set(slog.LevelDebug) // 初始化日志级别

			slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
				AddSource:   true,
				Level:       level, // 日志级别
				ReplaceAttr: nil,
			})))

			level.Set(slog.LevelInfo) // 修改日志级别
	
	# type Leveler interface {
			Level() Level
		}

		* 返回level的接口
	
	# type LogValuer interface {
			LogValue() Value
		}

		* 日志输出值接口，实现了这个接口，日志输出的时候就会调用
		* 例如 Token 类
			type Token string
			func (Token) LogValue() slog.Value {
				return slog.StringValue("REDACTED_TOKEN")
			}
	
	# type Logger struct {
		}
		
		* 日志记录器

		func Default() *Logger
		func New(h Handler) *Logger
		func With(args ...any) *Logger
		func (l *Logger) Debug(msg string, args ...any)
			* 如果 args 不是 Attr 参数，则会尝试编码为 Attr
				log.Debug("hello", "name", "vin", "age")		// "msg":"hello","name":"vin","!BADKEY":"age"}
				log.Debug("hello", "name", "vin", "age", 19)	// "msg":"hello","name":"vin","age":19

		func (l *Logger) DebugContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Enabled(ctx context.Context, level Level) bool
		func (l *Logger) Error(msg string, args ...any)
		func (l *Logger) ErrorContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Handler() Handler
		func (l *Logger) Info(msg string, args ...any)
		func (l *Logger) InfoContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Log(ctx context.Context, level Level, msg string, args ...any)
		func (l *Logger) LogAttrs(ctx context.Context, level Level, msg string, attrs ...Attr)
		func (l *Logger) Warn(msg string, args ...any)
		func (l *Logger) WarnContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) With(args ...any) *Logger
		func (l *Logger) WithGroup(name string) *Logger

	
	# type Record struct {
			Time time.Time
				* 时间
			Message string
				* 消息
			Level Level
				* 级别
			PC uintptr
				* 调用栈
		}
		
		* 日志记录

		func NewRecord(t time.Time, level Level, msg string, pc uintptr) Record
			* 创建新的日志记录

		func (r *Record) Add(args ...any)
			* 按照 Logger.Log 中的说明将 args 转换为 Attrs，然后将 Attrs 附加到 Record 的 Attrs 列表中。
			* 忽略空的属性组

		func (r *Record) AddAttrs(attrs ...Attr)
			* 新增属性，忽略空的属性组

		func (r Record) Attrs(f func(Attr) bool)
			* 遍历属性，返回 false 停止

		func (r Record) Clone() Record
			* clone

		func (r Record) NumAttrs() int
			* 获取属性数量
	
	# type Source struct {
			Function string `json:"function"`
				* 方法
			File string `json:"file"`
				* 文件
			Line int    `json:"line"`
				* 行号
		}

		* 调用信息
	
	# type TextHandler struct {
		}
		
		* txt输出

		func NewTextHandler(w io.Writer, opts *HandlerOptions) *TextHandler
		func (h *TextHandler) Enabled(_ context.Context, level Level) bool
		func (h *TextHandler) Handle(_ context.Context, r Record) error
		func (h *TextHandler) WithAttrs(attrs []Attr) Handler
		func (h *TextHandler) WithGroup(name string) Handler
	
	# type Value struct {
		}
		
		* 日志value值

		func AnyValue(v any) Value
		func BoolValue(v bool) Value
		func DurationValue(v time.Duration) Value
		func Float64Value(v float64) Value
		func GroupValue(as ...Attr) Value
		func Int64Value(v int64) Value
		func IntValue(v int) Value
		func StringValue(value string) Value
		func TimeValue(v time.Time) Value
		func Uint64Value(v uint64) Value
		func (v Value) Any() any
		func (v Value) Bool() bool
		func (a Value) Duration() time.Duration
		func (v Value) Equal(w Value) bool
		func (v Value) Float64() float64
		func (v Value) Group() []Attr
		func (v Value) Int64() int64
		func (v Value) Kind() Kind
		func (v Value) LogValuer() LogValuer
		func (v Value) Resolve() (rv Value)
		func (v Value) String() string
		func (v Value) Time() time.Time
		func (v Value) Uint64() uint64

--------------
funcs
--------------

	func Debug(msg string, args ...any)
	func DebugContext(ctx context.Context, msg string, args ...any)
	func Error(msg string, args ...any)
	func ErrorContext(ctx context.Context, msg string, args ...any)
	func Info(msg string, args ...any)
	func InfoContext(ctx context.Context, msg string, args ...any)
	func Log(ctx context.Context, level Level, msg string, args ...any)
	func LogAttrs(ctx context.Context, level Level, msg string, attrs ...Attr)
	func Warn(msg string, args ...any)
	func WarnContext(ctx context.Context, msg string, args ...any)

	func NewLogLogger(h Handler, level Level) *log.Logger
		* 创建 log 包下的 logger
		
	func SetDefault(l *Logger)
		* 设置默认的logger，默认是 log.Default()
			var defaultLogger atomic.Value
			func init() {
				defaultLogger.Store(New(newDefaultHandler(loginternal.DefaultOutput)))
			}



---------------
用法
---------------
	# 作为默认的 logger 用
	
		package main

		import (
			"log/slog"
			"os"
		)

		func init() {
			slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
				AddSource: true, Level: slog.LevelDebug, ReplaceAttr: nil,
			})))
		}

		func main() {
			slog.Debug("Hello")
		}

