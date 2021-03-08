----------------
logger
----------------

----------------
var
----------------
	const (
		Reset       = "\033[0m"
		Red         = "\033[31m"
		Green       = "\033[32m"
		Yellow      = "\033[33m"
		Blue        = "\033[34m"
		Magenta     = "\033[35m"
		Cyan        = "\033[36m"
		White       = "\033[37m"
		BlueBold    = "\033[34;1m"
		MagentaBold = "\033[35;1m"
		RedBold     = "\033[31;1m"
		YellowBold  = "\033[33;1m"
	)

	* 颜色


	var (
		Discard = New(log.New(ioutil.Discard, "", log.LstdFlags), Config{})
		Default = New(log.New(os.Stdout, "\r\n", log.LstdFlags), Config{
			SlowThreshold: 200 * time.Millisecond,
			LogLevel:      Warn,
			Colorful:      true,
		})
		Recorder = traceRecorder{Interface: Default, BeginAt: time.Now()}
	)
	
	* 预定义的Logger

----------------
type
----------------
	# type Config struct {
			SlowThreshold time.Duration
			Colorful      bool
			LogLevel      LogLevel
		}
	
	# type Interface interface {
			LogMode(LogLevel) Interface
			Info(context.Context, string, ...interface{})
			Warn(context.Context, string, ...interface{})
			Error(context.Context, string, ...interface{})
			Trace(ctx context.Context, begin time.Time, fc func() (string, int64), err error)
		}
		func New(writer Writer, config Config) Interface
	
	# type LogLevel int
		const (
			Silent LogLevel = iota + 1
			Error
			Warn
			Info
		)
	
	# type Writer interface {
			Printf(string, ...interface{})
		}

----------------
func
----------------
	func ExplainSQL(sql string, numericPlaceholder *regexp.Regexp, escaper string, ...) string
