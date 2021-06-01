----------------------------
var
----------------------------
	const (
		// DebugLevel logs are typically voluminous, and are usually disabled in
		// production.
		DebugLevel = zapcore.DebugLevel
		// InfoLevel is the default logging priority.
		InfoLevel = zapcore.InfoLevel
		// WarnLevel logs are more important than Info, but don't need individual
		// human review.
		WarnLevel = zapcore.WarnLevel
		// ErrorLevel logs are high-priority. If an application is running smoothly,
		// it shouldn't generate any error-level logs.
		ErrorLevel = zapcore.ErrorLevel
		// DPanicLevel logs are particularly important errors. In development the
		// logger panics after writing the message.
		DPanicLevel = zapcore.DPanicLevel
		// PanicLevel logs a message, then panics.
		PanicLevel = zapcore.PanicLevel
		// FatalLevel logs a message, then calls os.Exit(1).
		FatalLevel = zapcore.FatalLevel
	)

	* 日志级别


----------------------------
struct
----------------------------
	# type AtomicLevel struct {
			// contains filtered or unexported fields
		}
		func NewAtomicLevel() AtomicLevel	
			* 创建新的动态日志级别，默认级别是 InfoLevel

		func NewAtomicLevelAt(l zapcore.Level) AtomicLevel
			* 创建新的动态日志级别，并且设置级别

		func (lvl AtomicLevel) Enabled(l zapcore.Level) bool
		func (lvl AtomicLevel) Level() zapcore.Level
			* 获取日志级别

		func (lvl AtomicLevel) MarshalText() (text []byte, err error)
		func (lvl AtomicLevel) ServeHTTP(w http.ResponseWriter, r *http.Request)
			* 提供了查询/修改日志级别的API

		func (lvl AtomicLevel) SetLevel(l zapcore.Level)
			* 设置日志级别

		func (lvl AtomicLevel) String() string
		func (lvl *AtomicLevel) UnmarshalText(text []byte) error
		
		* 实现了 LevelEnabler 接口
		* 原子可更改的动态日志记录级别，可以在运行时安全地更改记录器树（根记录器和通过添加上下文创建的任何子代）的日志级别。
		* l本身是一个http.Handler，它提供JSON端点来更改其级别
		* 必须使用 NewAtomicLevel 构造函数创建AtomicLevels才能分配其内部原子指针

	
	# type Config struct {
			Level AtomicLevel `json:"level" yaml:"level"`
				* 设置日志级别
			
			Development bool `json:"development" yaml:"development"`
				* 设置为开发模式，这会改变DPanicLevel的行为，并且更自由地使用stacktraces

			DisableCaller bool `json:"disableCaller" yaml:"disableCaller"`
				* 禁止在日志中输出调用函数的文件名称和行号。
				* 默认true

			DisableStacktrace bool `json:"disableStacktrace" yaml:"disableStacktrace"`
				* 禁用堆栈跟踪，
				* 默认情况下，会捕获开发模式中的WarnLevel及以上级别的日志，以及生产环境中的ErrorLevel及以上级别的日志

			Sampling *SamplingConfig `json:"sampling" yaml:"samencoderConfigpling"`	
				* SamplingConfig

			Encoding string `json:"encoding" yaml:"encoding"`
				* 设置编码器类型，枚举值：json, console

			EncoderConfig zapcore.EncoderConfig `json:"encoderConfig" yaml:""`
				* 编码器配置
			
			OutputPaths []string `json:"outputPaths" yaml:"outputPaths"`	
				* 可以配置多个输出路径，路径可以是文件路径和stdout（标准输出）；
			
			ErrorOutputPaths []string `json:"errorOutputPaths" yaml:"errorOutputPaths"`
				* 错误输出路径，也可以是多个；

			InitialFields map[string]interface{} `json:"initialFields" yaml:"initialFields"`
				* 每条日志中都会输出这些值
		}
		
		* 日志的配置信息
			
		func NewDevelopmentConfig() Config
		func NewProductionConfig() Config
		func (cfg Config) Build(opts ...Option) (*Logger, error)
	
	# type Field = zapcore.Field

		* 用于输出的字段
		* zap为了提高性能、减少内存分配次数，没有使用反射，而且默认的Logger只支持强类型的、结构化的日志。
		* 必须使用zap提供的方法记录字段
		
		func Any(key string, value interface{}) Field
			* 输出任何数据

		func Array(key string, val zapcore.ArrayMarshaler) Field
		func Binary(key string, val []byte) Field
			* 输出二进制字段

		func Bool(key string, val bool) Field
			* 输出普通bool字段
		func Boolp(key string, val *bool) Field
			* 输出bool指针字段
		func Bools(key string, bs []bool) Field
			* 输出bool切片
		
		func ByteString(key string, val []byte) Field
		func ByteStrings(key string, bss [][]byte) Field
		func Complex128(key string, val complex128) Field
		func Complex128p(key string, val *complex128) Field
		func Complex128s(key string, nums []complex128) Field
		func Complex64(key string, val complex64) Field
		func Complex64p(key string, val *complex64) Field
		func Complex64s(key string, nums []complex64) Field
		func Duration(key string, val time.Duration) Field
		func Durationp(key string, val *time.Duration) Field
		func Durations(key string, ds []time.Duration) Field
		func Error(err error) Field
		func Errors(key string, errs []error) Field
		func Float32(key string, val float32) Field
		func Float32p(key string, val *float32) Field
		func Float32s(key string, nums []float32) Field
		func Float64(key string, val float64) Field
		func Float64p(key string, val *float64) Field
		func Float64s(key string, nums []float64) Field
		func Inline(val zapcore.ObjectMarshaler) Field
		func Int(key string, val int) Field
		func Int16(key string, val int16) Field
		func Int16p(key string, val *int16) Field
		func Int16s(key string, nums []int16) Field
		func Int32(key string, val int32) Field
		func Int32p(key string, val *int32) Field
		func Int32s(key string, nums []int32) Field
		func Int64(key string, val int64) Field
		func Int64p(key string, val *int64) Field
		func Int64s(key string, nums []int64) Field
		func Int8(key string, val int8) Field
		func Int8p(key string, val *int8) Field
		func Int8s(key string, nums []int8) Field
		func Intp(key string, val *int) Field
		func Ints(key string, nums []int) Field
		func NamedError(key string, err error) Field
		func Namespace(key string) Field
		func Object(key string, val zapcore.ObjectMarshaler) Field
		func Reflect(key string, val interface{}) Field
		func Skip() Field
		func Stack(key string) Field
		func StackSkip(key string, skip int) Field
		func String(key string, val string) Field
		func Stringer(key string, val fmt.Stringer) Field
		func Stringp(key string, val *string) Field
		func Strings(key string, ss []string) Field
		func Time(key string, val time.Time) Field
		func Timep(key string, val *time.Time) Field
		func Times(key string, ts []time.Time) Field
		func Uint(key string, val uint) Field
		func Uint16(key string, val uint16) Field
		func Uint16p(key string, val *uint16) Field
		func Uint16s(key string, nums []uint16) Field
		func Uint32(key string, val uint32) Field
		func Uint32p(key string, val *uint32) Field
		func Uint32s(key string, nums []uint32) Field
		func Uint64(key string, val uint64) Field
		func Uint64p(key string, val *uint64) Field
		func Uint64s(key string, nums []uint64) Field
		func Uint8(key string, val uint8) Field
		func Uint8p(key string, val *uint8) Field
		func Uint8s(key string, nums []uint8) Field
		func Uintp(key string, val *uint) Field
		func Uintptr(key string, val uintptr) Field
		func Uintptrp(key string, val *uintptr) Field
		func Uintptrs(key string, us []uintptr) Field
		func Uints(key string, nums []uint) Field
	
	# type LevelEnablerFunc func(zapcore.Level) bool
		func (f LevelEnablerFunc) Enabled(lvl zapcore.Level) bool

		* LevelEnabler的实现方法
	
	# type Logger struct {
			// contains filtered or unexported fields
		}
		func L() *Logger
			* 获取全局标准的logger
			* 全局的Logger默认并不会记录日志！它是一个无实际效果的Logger。

		func New(core zapcore.Core, options ...Option) *Logger
			* 创建新的 Logger，需要自己定制化

		func NewDevelopment(options ...Option) (*Logger, error)
			* 适合在开发环境中使用

		func NewExample(options ...Option) *Logger
			* 适合用在测试代码中

		func NewNop() *Logger
			* 创建一个空实现的logger
			
		func NewProduction(options ...Option) (*Logger, error)
			* 使用option创建新的logger, 适合用在生成环境
			* 默认记录调用函数信息、日期和时间等

		func (log *Logger) Check(lvl zapcore.Level, msg string) *zapcore.CheckedEntry
		func (log *Logger) Core() zapcore.Core
		func (log *Logger) DPanic(msg string, fields ...Field)
		func (log *Logger) Debug(msg string, fields ...Field)
		func (log *Logger) Error(msg string, fields ...Field)
		func (log *Logger) Fatal(msg string, fields ...Field)
		func (log *Logger) Info(msg string, fields ...Field)
		func (log *Logger) Named(s string) *Logger
			* copy当前日志记录器的所有信息生成新的，并且给设置一个名称
			* 如果已经有名称的话，则添加.分割

		func (log *Logger) Panic(msg string, fields ...Field)
		func (log *Logger) Sugar() *SugaredLogger
			* 从当前Logger创建一个SugaredLogger

		func (log *Logger) Sync() error
		func (log *Logger) Warn(msg string, fields ...Field)
		func (log *Logger) With(fields ...Field) *Logger
			* 

		func (log *Logger) WithOptions(opts ...Option) *Logger
	
	# type Option interface {
			// contains filtered or unexported methods
		}
	
		* 日志的配置信息

		func AddCaller() Option
			* 添加日志输出的文件信息
			* 本质上是执行了:  WithCaller(true)

		func AddCallerSkip(skip int) Option
		func AddStacktrace(lvl zapcore.LevelEnabler) Option
			* 添加调用栈信息
			* 该函数指定lvl和之上的级别都需要输出调用堆栈：

		func Development() Option
		func ErrorOutput(w zapcore.WriteSyncer) Option
		func Fields(fs ...Field) Option
			* 预设一堆输出字段，这个logger输出都会带上这个

		func Hooks(hooks ...func(zapcore.Entry) error) Option
			* 添加回调

		func IncreaseLevel(lvl zapcore.LevelEnabler) Option
		func OnFatal(action zapcore.CheckWriteAction) Option
			* 设置在 Fatal logger时的处理方法

		func WithCaller(enabled bool) Option	
			* 添加日志输出的文件信息

		func WrapCore(f func(zapcore.Core) zapcore.Core) Option
	
	# type SamplingConfig struct {
			Initial    int                                           `json:"initial" yaml:"initial"`
			Thereafter int                                           `json:"thereafter" yaml:"thereafter"`
			Hook       func(zapcore.Entry, zapcore.SamplingDecision) `json:"-" yaml:"-"`
		}

		* 采样配置

	
	# type Sink interface {
			zapcore.WriteSyncer
			io.Closer
		}
	
	# type SugaredLogger struct {
			// contains filtered or unexported fields
		}
		
		* "加糖"的日志记录器，就是支持 fmt 格式化输出的常规记录器

		func S() *SugaredLogger
			* 获取全局的SugaredLogger
			* 全局的Logger默认并不会记录日志！它是一个无实际效果的Logger。

		func (s *SugaredLogger) DPanic(args ...interface{})
		func (s *SugaredLogger) DPanicf(template string, args ...interface{})
		func (s *SugaredLogger) DPanicw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Debug(args ...interface{})
		func (s *SugaredLogger) Debugf(template string, args ...interface{})
		func (s *SugaredLogger) Debugw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Desugar() *Logger
		func (s *SugaredLogger) Error(args ...interface{})
		func (s *SugaredLogger) Errorf(template string, args ...interface{})
		func (s *SugaredLogger) Errorw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Fatal(args ...interface{})
		func (s *SugaredLogger) Fatalf(template string, args ...interface{})
		func (s *SugaredLogger) Fatalw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Info(args ...interface{})
		func (s *SugaredLogger) Infof(template string, args ...interface{})
		func (s *SugaredLogger) Infow(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Named(name string) *SugaredLogger
		func (s *SugaredLogger) Panic(args ...interface{})
		func (s *SugaredLogger) Panicf(template string, args ...interface{})
		func (s *SugaredLogger) Panicw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Sync() error
		func (s *SugaredLogger) Warn(args ...interface{})
		func (s *SugaredLogger) Warnf(template string, args ...interface{})
		func (s *SugaredLogger) Warnw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) With(args ...interface{}) *SugaredLogger

----------------------------
func
----------------------------
	func CombineWriteSyncers(writers ...zapcore.WriteSyncer) zapcore.WriteSyncer
	func LevelFlag(name string, defaultLevel zapcore.Level, usage string) *zapcore.Level

	func NewDevelopmentEncoderConfig() zapcore.EncoderConfig
		* 创建新的编码器配置，开发环境

	func NewProductionEncoderConfig() zapcore.EncoderConfig
		* 创建新的编码器配置，生产环境
		
	func NewStdLog(l *Logger) *log.Logger
		* 包装指定的logger，为标准库的logger

	func NewStdLogAt(l *Logger, level zapcore.Level) (*log.Logger, error)
		* 让标准接口以level级别写入内部的*zap.Logger。

	func Open(paths ...string) (zapcore.WriteSyncer, func(), error)
	func RedirectStdLog(l *Logger) func()
		* 把指定Logger的输出重定向到标准的logger(全局的)
		* 返回 fanc() 用于取消

	func RedirectStdLogAt(l *Logger, level zapcore.Level) (func(), error)
	func RegisterEncoder(name string, constructor func(zapcore.EncoderConfig) (zapcore.Encoder, error)) error
	func RegisterSink(scheme string, factory func(*url.URL) (Sink, error)) error

	func ReplaceGlobals(logger *Logger) func()
		* ReplaceGlobals 替换全局Logger和SugredLogger，并返回一个函数来恢复原来的值。它对并发使用是安全的。
		* 将logger设置为全局的Logger，该函数返回一个无参函数，用于恢复全局Logger设置