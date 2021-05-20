package log


// 所有的日志记录器，所有级别日志都往 appLogWriter 输出日志
// 所有日志记录器的“异常”级别日志，都会往不同的专门日志通道输出

import (
	"github.com/lestrrat-go/file-rotatelogs"
	"github.com/sirupsen/logrus"
	"io"
	"log"
	"os"
	"time"
)


func init (){
	log.Default().SetFlags(log.Ldate | log.Ltime | log.Llongfile)
}

// loggerName 日志记录器名称
const loggerName = "logger"

// Loggers 所有日志记录器
var Loggers = map[string] *logrus.Entry {}

var (
	// Default 默认的全局记录器
	Default *logrus.Entry

	// Sql SQL日志记录器
	Sql *logrus.Entry

	// Access 访问日志
	Access *logrus.Entry
)

var (
	// appLogWriter 运行日志输出，所有级别的日志都会往这里面输出
	appLogWriter io.Writer

	// accessLogWriter 访问日志专用输出
	accessLogWriter io.Writer

	// warnLogWriter 警告日志专用输出
	warnLogWriter io.Writer
	// errorLogWriter 异常日志专用输出
	errorLogWriter io.Writer
	// fatalLogWriter 异常退出日志专用输出
	fatalLogWriter io.Writer
	// panicLogWriter 崩溃日志专用输出
	panicLogWriter io.Writer

)


// fileLogOptions 创建文件日志的配置
func fileLogOptions (linkName string, maxAge time.Duration, rotationCount uint, rotationSize int64, rotationTime time.Duration, handlerFunc rotatelogs.HandlerFunc) []rotatelogs.Option {
	return []rotatelogs.Option{rotatelogs.ForceNewFile(), // 强制创建新文件
		rotatelogs.WithClock(rotatelogs.Local),         // 获取时间函数
		rotatelogs.WithLocation(time.Local),            // 时区
		rotatelogs.WithLinkName(linkName),				// 链接文件
		rotatelogs.WithMaxAge(maxAge),                  // 文件保存时间
		rotatelogs.WithRotationCount(rotationCount),    // 历史文件数量
		rotatelogs.WithRotationSize(rotationSize), 		// 文件体积，超过后会被切割
		rotatelogs.WithRotationTime(rotationTime),    	// 单个文件最多时间，超过这个时间会被切割
		rotatelogs.WithHandler(handlerFunc),			// 监听器
	}
}

func intiWriter (){
	appLogWriter	=	newFileLogWriter("logs/app.%Y%m%d%H%M", 	fileLogOptions("log/app.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)
	appLogWriter = io.MultiWriter(accessLogWriter, os.Stdout)

	accessLogWriter	=	newFileLogWriter("logs/access.%Y%m%d%H%M", 	fileLogOptions("logs/access.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)

	// 异常日志
	warnLogWriter	=	newFileLogWriter("logs/warn.%Y%m%d%H%M", 	fileLogOptions("logs/warn.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)
	errorLogWriter	=	newFileLogWriter("logs/error.%Y%m%d%H%M", 	fileLogOptions("logs/error.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)
	fatalLogWriter	=	newFileLogWriter("logs/fatal.%Y%m%d%H%M", 	fileLogOptions("logs/fatal.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)
	panicLogWriter	=	newFileLogWriter("logs/panic.%Y%m%d%H%M", 	fileLogOptions("logs/panic.log", -1, 10, 1024 * 1024 * 100, time.Hour * 24, nil)...)

}


// Init 初始化日志系统
func Init (){
	// 初始化日志输出目的地
	intiWriter()

	// 默认日志，输出到app
	Default = newLogger(appLogWriter, "default", logrus.DebugLevel, errHook)

	//SQL日志记录器，输出到app
	Sql = newLogger(appLogWriter, "sql", logrus.DebugLevel, errHook)

	// 访问日志，输出到app和accessLog
	Access = newLogger(io.MultiWriter(accessLogWriter, appLogWriter), "access", logrus.DebugLevel, errHook)

	// 添加到集合
	Loggers[(Default.Data[loggerName]).(string)] = Default
	Loggers[(Sql.Data[loggerName]).(string)] = Sql
	Loggers[(Access.Data[loggerName]).(string)] = Access
}

// errorLogHook 监听“warn”，“error”，“fatal”，“panic”日志，
// 往专门的日志通道推送日志，并且发送邮件警告
type errorLogHook struct {
	levels []logrus.Level
}
func (h errorLogHook) Levels() []logrus.Level {
	return h.levels
}
func (h errorLogHook) Fire(entry *logrus.Entry) error {
	data, err := entry.Bytes()
	if err != nil {
		return err
	}

	var writer io.Writer

	switch entry.Level {
		case logrus.WarnLevel:  writer = warnLogWriter
		case logrus.ErrorLevel: writer = errorLogWriter
		case logrus.FatalLevel: writer = fatalLogWriter
		case logrus.PanicLevel: writer = panicLogWriter
		default: {}
	}

	// TODO 发送通知邮件

	// 标准异常流输出
	// _, err = os.Stderr.Write(data) // 马上会在标准流输出，不需要重复往异常流输出
	_, err = writer.Write(data)
	return err
}
var errHook = &errorLogHook{levels: []logrus.Level {logrus.WarnLevel, logrus.ErrorLevel, logrus.FatalLevel, logrus.PanicLevel}}


// newLogger 创建新的日志记录器
func newLogger(out io.Writer, name string, level logrus.Level, hooks... logrus.Hook) *logrus.Entry {
	logger := logrus.NewEntry(logrus.New()).WithField(loggerName, name)
	logger.Logger.SetFormatter(&logrus.JSONFormatter{
		TimestampFormat:   "2006-01-02 15:04:05",
		DisableHTMLEscape: true,
	})
	logger.Logger.SetReportCaller(true)
	logger.Logger.SetLevel(level)
	logger.Logger.SetOutput(out)
	for _, hook := range hooks {
		logger.Logger.Hooks.Add(hook) // 记录异常日志
	}
	return logger
}

// newFileLogWriter 创建新的日志输出文件
func newFileLogWriter(logFile string, options... rotatelogs.Option) *rotatelogs.RotateLogs {
	var logWriter, err = rotatelogs.New(
		logFile,														// 日志文件名称格式化
		options...
	)
	if err != nil {
		log.Fatalf("创建日志Writer异常: %s\n", err.Error())
	}
	return logWriter
}

