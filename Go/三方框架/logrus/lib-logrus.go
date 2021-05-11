--------------------
var
--------------------
	const (
		FieldKeyMsg         = "msg"			// 消息
		FieldKeyLevel       = "level"		// 日志等级
		FieldKeyTime        = "time"		// 日志时间
		FieldKeyLogrusError = "logrus_error"// 日志内部错误信息
		FieldKeyFunc        = "func"		// 调用日志的函数
		FieldKeyFile        = "file"		// 调用日志输出的文件
	)
	
	* 默认的字段名称

	var AllLevels = []Level{
		PanicLevel,
		FatalLevel,
		ErrorLevel,
		WarnLevel,
		InfoLevel,
		DebugLevel,
		TraceLevel,
	}
	
	* 日志级别

	var ErrorKey = "error"


--------------------
type
--------------------
	# type BufferPool interface {
			Put(*bytes.Buffer)
			Get() *bytes.Buffer
		}
	# type Entry struct {
			Logger *Logger
				* 日志对象
			Data Fields
				* 输出的数据
			Time time.Time
				* 时间
			Level Level
				* 日志级别
			Caller *runtime.Frame
				* 执行栈
			Message string
				* 消息
			Buffer *bytes.Buffer
			Context context.Context
		}

		* 实现了Logge接口

		func NewEntry(logger *Logger) *Entry
		func WithContext(ctx context.Context) *Entry
		func WithError(err error) *Entry
		func WithField(key string, value interface{}) *Entry
		func WithFields(fields Fields) *Entry
		func WithTime(t time.Time) *Entry
		func (entry *Entry) Bytes() ([]byte, error)
			* 获取格式化后的日志信息

		func (entry *Entry) Debug(args ...interface{})
		func (entry *Entry) Debugf(format string, args ...interface{})
		func (entry *Entry) Debugln(args ...interface{})
		func (entry *Entry) Dup() *Entry
		func (entry *Entry) Error(args ...interface{})
		func (entry *Entry) Errorf(format string, args ...interface{})
		func (entry *Entry) Errorln(args ...interface{})
		func (entry *Entry) Fatal(args ...interface{})
		func (entry *Entry) Fatalf(format string, args ...interface{})
		func (entry *Entry) Fatalln(args ...interface{})
		func (entry Entry) HasCaller() (has bool)
		func (entry *Entry) Info(args ...interface{})
		func (entry *Entry) Infof(format string, args ...interface{})
		func (entry *Entry) Infoln(args ...interface{})
		func (entry *Entry) Log(level Level, args ...interface{})
		func (entry *Entry) Logf(level Level, format string, args ...interface{})
		func (entry *Entry) Logln(level Level, args ...interface{})
		func (entry *Entry) Panic(args ...interface{})
		func (entry *Entry) Panicf(format string, args ...interface{})
		func (entry *Entry) Panicln(args ...interface{})
		func (entry *Entry) Print(args ...interface{})
		func (entry *Entry) Printf(format string, args ...interface{})
		func (entry *Entry) Println(args ...interface{})
		func (entry *Entry) String() (string, error)
		func (entry *Entry) Trace(args ...interface{})
		func (entry *Entry) Tracef(format string, args ...interface{})
		func (entry *Entry) Traceln(args ...interface{})
		func (entry *Entry) Warn(args ...interface{})
		func (entry *Entry) Warnf(format string, args ...interface{})
		func (entry *Entry) Warning(args ...interface{})
		func (entry *Entry) Warningf(format string, args ...interface{})
		func (entry *Entry) Warningln(args ...interface{})
		func (entry *Entry) Warnln(args ...interface{})
		func (entry *Entry) WithContext(ctx context.Context) *Entry
		func (entry *Entry) WithError(err error) *Entry
		func (entry *Entry) WithField(key string, value interface{}) *Entry
		func (entry *Entry) WithFields(fields Fields) *Entry
		func (entry *Entry) WithTime(t time.Time) *Entry
		func (entry *Entry) Writer() *io.PipeWriter
		func (entry *Entry) WriterLevel(level Level) *io.PipeWriter

	# type Ext1FieldLogger interface {
			FieldLogger
			Tracef(format string, args ...interface{})
			Trace(args ...interface{})
			Traceln(args ...interface{})
		}
	# type FieldLogger interface {
			WithField(key string, value interface{}) *Entry
			WithFields(fields Fields) *Entry
			WithError(err error) *Entry

			Debugf(format string, args ...interface{})
			Infof(format string, args ...interface{})
			Printf(format string, args ...interface{})
			Warnf(format string, args ...interface{})
			Warningf(format string, args ...interface{})
			Errorf(format string, args ...interface{})
			Fatalf(format string, args ...interface{})
			Panicf(format string, args ...interface{})

			Debug(args ...interface{})
			Info(args ...interface{})
			Print(args ...interface{})
			Warn(args ...interface{})
			Warning(args ...interface{})
			Error(args ...interface{})
			Fatal(args ...interface{})
			Panic(args ...interface{})

			Debugln(args ...interface{})
			Infoln(args ...interface{})
			Println(args ...interface{})
			Warnln(args ...interface{})
			Warningln(args ...interface{})
			Errorln(args ...interface{})
			Fatalln(args ...interface{})
			Panicln(args ...interface{})
		}
	# type FieldMap map[fieldKey]string
	# type Fields map[string]interface{}

	# type Formatter interface {
			Format(*Entry) ([]byte, error)
		}
		* 格式化接口

	# type Hook interface {
			Levels() []Level
				* 返回感兴趣的日志级别，输出其他日志时不会触发钩子
			Fire(*Entry) error
				* 触发钩子事件，日志输出前调用
		}
		
		* 回调钩子接口

	# type JSONFormatter struct {
			TimestampFormat string		// 日期格式化
			DisableTimestamp bool		// 是否禁用时间戳
			DisableHTMLEscape bool		// 是否禁用HTML编码
			DataKey string				// 输出日期的KEY
			FieldMap FieldMap			// 输出字段的Map
			CallerPrettyfier func(*runtime.Frame) (function string, file string) // 对输出栈信息的优化
			PrettyPrint bool
		}
		
		* JSON格式化的实现

		func (f *JSONFormatter) Format(entry *Entry) ([]byte, error)

	# type Level uint32
		func GetLevel() Level
		func ParseLevel(lvl string) (Level, error)
		func (level Level) MarshalText() ([]byte, error)
		func (level Level) String() string
		func (level *Level) UnmarshalText(text []byte) error
	
	# type LevelHooks map[Level][]Hook
		func (hooks LevelHooks) Add(hook Hook)
		func (hooks LevelHooks) Fire(level Level, entry *Entry) error
	
	# type LogFunction func() []interface{}
	
	# type Logger struct {
			Out io.Writer
			Hooks LevelHooks
			Formatter Formatter
			ReportCaller bool
			Level Level
			ExitFunc exitFunc
		}
		func New() *Logger
			* 创建新的logger
		
		func StandardLogger() *Logger
			* 返回默认的logger
				std = New()

		func (logger *Logger) AddHook(hook Hook)
		func (logger *Logger) Debug(args ...interface{})
		func (logger *Logger) DebugFn(fn LogFunction)
		func (logger *Logger) Debugf(format string, args ...interface{})
		func (logger *Logger) Debugln(args ...interface{})
		func (logger *Logger) Error(args ...interface{})
		func (logger *Logger) ErrorFn(fn LogFunction)
		func (logger *Logger) Errorf(format string, args ...interface{})
		func (logger *Logger) Errorln(args ...interface{})
		func (logger *Logger) Exit(code int)
			* 退出应用

		func (logger *Logger) Fatal(args ...interface{})
		func (logger *Logger) FatalFn(fn LogFunction)
		func (logger *Logger) Fatalf(format string, args ...interface{})
		func (logger *Logger) Fatalln(args ...interface{})
		func (logger *Logger) GetLevel() Level
			* 返回日志级别

		func (logger *Logger) Info(args ...interface{})
		func (logger *Logger) InfoFn(fn LogFunction)
		func (logger *Logger) Infof(format string, args ...interface{})
		func (logger *Logger) Infoln(args ...interface{})
		func (logger *Logger) IsLevelEnabled(level Level) bool
		func (logger *Logger) Log(level Level, args ...interface{})
		func (logger *Logger) LogFn(level Level, fn LogFunction)
		func (logger *Logger) Logf(level Level, format string, args ...interface{})
		func (logger *Logger) Logln(level Level, args ...interface{})
		func (logger *Logger) Panic(args ...interface{})
		func (logger *Logger) PanicFn(fn LogFunction)
		func (logger *Logger) Panicf(format string, args ...interface{})
		func (logger *Logger) Panicln(args ...interface{})
		func (logger *Logger) Print(args ...interface{})
		func (logger *Logger) PrintFn(fn LogFunction)
		func (logger *Logger) Printf(format string, args ...interface{})
		func (logger *Logger) Println(args ...interface{})
		func (logger *Logger) ReplaceHooks(hooks LevelHooks) LevelHooks
		func (logger *Logger) SetFormatter(formatter Formatter)
			* 设置格式化实现

		func (logger *Logger) SetLevel(level Level)
			* 设置日志级别

		func (logger *Logger) SetNoLock()
			* 默认的logger在并发写的时候是被mutex保护的，比如当同时调用hook和写log时mutex就会被请求
			* 有另外一种情况，文件是以appending mode打开的， 此时的并发操作就是安全的，可以用logger.SetNoLock()来关闭它。

		func (logger *Logger) SetOutput(output io.Writer)
			* 设置日志的输出目的地
		func (logger *Logger) SetReportCaller(reportCaller bool)
			* 在输出日志中添加文件名和方法信息
			* 会增加性能开销

		func (logger *Logger) Trace(args ...interface{})
		func (logger *Logger) TraceFn(fn LogFunction)
		func (logger *Logger) Tracef(format string, args ...interface{})
		func (logger *Logger) Traceln(args ...interface{})
		func (logger *Logger) Warn(args ...interface{})
		func (logger *Logger) WarnFn(fn LogFunction)
		func (logger *Logger) Warnf(format string, args ...interface{})
		func (logger *Logger) Warning(args ...interface{})
		func (logger *Logger) WarningFn(fn LogFunction)
		func (logger *Logger) Warningf(format string, args ...interface{})
		func (logger *Logger) Warningln(args ...interface{})
		func (logger *Logger) Warnln(args ...interface{})
		func (logger *Logger) WithContext(ctx context.Context) *Entry
		func (logger *Logger) WithError(err error) *Entry

		func (logger *Logger) WithField(key string, value interface{}) *Entry
		func (logger *Logger) WithFields(fields Fields) *Entry
			* 添加Key/Value键值对
				log.WithField("a", "b").Info("Hello")
				// level=info msg=Hello a=b
			* 这个方法返回的entry，每次执行日志输出都会带上这个键值对
			
		func (logger *Logger) WithTime(t time.Time) *Entry
		func (logger *Logger) Writer() *io.PipeWriter
		func (logger *Logger) WriterLevel(level Level) *io.PipeWriter

	# type MutexWrap struct {
		}
		func (mw *MutexWrap) Disable()
		func (mw *MutexWrap) Lock()
		func (mw *MutexWrap) Unlock()
	
	# type StdLogger interface {
			Print(...interface{})
			Printf(string, ...interface{})
			Println(...interface{})

			Fatal(...interface{})
			Fatalf(string, ...interface{})
			Fatalln(...interface{})

			Panic(...interface{})
			Panicf(string, ...interface{})
			Panicln(...interface{})
		}
	
	# type TextFormatter struct {
			ForceColors bool
			DisableColors bool
			ForceQuote bool
			DisableQuote bool
			EnvironmentOverrideColors bool
			DisableTimestamp bool
			FullTimestamp bool
			TimestampFormat string
			DisableSorting bool
			SortingFunc func([]string)
			DisableLevelTruncation bool
			PadLevelText bool
			QuoteEmptyFields bool
			FieldMap FieldMap
			CallerPrettyfier func(*runtime.Frame) (function string, file string)
		}
		
		* 文本格式化的实现

		func (f *TextFormatter) Format(entry *Entry) ([]byte, error)
	
--------------------
method
--------------------
	func AddHook(hook Hook)
	func Debug(args ...interface{})
	func DebugFn(fn LogFunction)
	func Debugf(format string, args ...interface{})
	func Debugln(args ...interface{})
	func DeferExitHandler(handler func())
	func Error(args ...interface{})
	func ErrorFn(fn LogFunction)
	func Errorf(format string, args ...interface{})
	func Errorln(args ...interface{})
	func Exit(code int)
	func Fatal(args ...interface{})
	func FatalFn(fn LogFunction)
	func Fatalf(format string, args ...interface{})
	func Fatalln(args ...interface{})
	func Info(args ...interface{})
	func InfoFn(fn LogFunction)
	func Infof(format string, args ...interface{})
	func Infoln(args ...interface{})
	func IsLevelEnabled(level Level) bool
	func Panic(args ...interface{})
	func PanicFn(fn LogFunction)
	func Panicf(format string, args ...interface{})
	func Panicln(args ...interface{})
	func Print(args ...interface{})
	func PrintFn(fn LogFunction)
	func Printf(format string, args ...interface{})
	func Println(args ...interface{})
	func RegisterExitHandler(handler func())
	func SetBufferPool(bp BufferPool)
	func SetFormatter(formatter Formatter)
	func SetLevel(level Level)
	func SetOutput(out io.Writer)
	func SetReportCaller(include bool)
	func Trace(args ...interface{})
	func TraceFn(fn LogFunction)
	func Tracef(format string, args ...interface{})
	func Traceln(args ...interface{})
	func Warn(args ...interface{})
	func WarnFn(fn LogFunction)
	func Warnf(format string, args ...interface{})
	func Warning(args ...interface{})
	func WarningFn(fn LogFunction)
	func Warningf(format string, args ...interface{})
	func Warningln(args ...interface{})
	func Warnln(args ...interface{})
		* 这些方法都是默认的logger实现