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
)

var (
	// appLogWriter 运行日志输出，所有级别的日志都会往这里面输出
	appLogWriter io.Writer
	// warnLogWriter 警告日志专用输出
	warnLogWriter io.Writer
	// errorLogWriter 异常日志专用输出
	errorLogWriter io.Writer
	// fatalLogWriter 异常退出日志专用输出
	fatalLogWriter io.Writer
	// panicLogWriter 崩溃日志专用输出
	panicLogWriter io.Writer
)



// Init 初始化日志系统
func Init (){
	// 初始化日志输出目的地
	appLogWriter =   io.MultiWriter(newFileLogWriter("logs/app.%Y%m%d%H%M", 	"logs/app.log"), os.Stdout)
	warnLogWriter =  newFileLogWriter("logs/warn.%Y%m%d%H%M", 	"logs/warn.log")
	errorLogWriter = newFileLogWriter("logs/error.%Y%m%d%H%M", 	"logs/error.log")
	fatalLogWriter = newFileLogWriter("logs/fatal.%Y%m%d%H%M", 	"logs/fatal.log")
	panicLogWriter = newFileLogWriter("logs/panic.%Y%m%d%H%M", 	"logs/panic.log")

	// 默认日志
	Default = newLogger(func() *logrus.Entry {
		return logrus.NewEntry(logrus.New()).WithField(loggerName, "default")
	}, logrus.DebugLevel)

	//SQL日志记录器
	Sql = newLogger(func() *logrus.Entry {
		return logrus.NewEntry(logrus.New()).WithField(loggerName, "sql")
	}, logrus.DebugLevel)

	// TODO 可以针对不同的日志记录器，设置不同的输出目的地

	// 添加到集合
	Loggers[(Default.Data[loggerName]).(string)] = Default
	Loggers[(Sql.Data[loggerName]).(string)] = Sql
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
func newLogger (creator func() *logrus.Entry, level logrus.Level) *logrus.Entry {
	var logger = creator()
	logger.Logger.SetFormatter(&logrus.JSONFormatter{
		TimestampFormat:   "2006-01-02 15:04:05",
		DisableHTMLEscape: true,
	})
	logger.Logger.SetReportCaller(true)
	logger.Logger.SetLevel(level)
	logger.Logger.SetOutput(appLogWriter) // 所有Logger都往 appLogWriter 输出
	logger.Logger.Hooks.Add(errHook)
	return logger
}

// newFileLogWriter 创建新的日志输出文件
func newFileLogWriter(logFile, linkFile string) *rotatelogs.RotateLogs {
	var logWriter, err = rotatelogs.New(
		logFile,														// 日志文件名称格式化
		rotatelogs.ForceNewFile(),										// 强制创建新文件
		rotatelogs.WithClock(rotatelogs.Local),							// 获取时间函数
		rotatelogs.WithLocation(time.Local),							// 时区
		rotatelogs.WithLinkName(linkFile),								// 最新的日志文件软连接
		rotatelogs.WithMaxAge(-1),										// 文件无限期保存
		rotatelogs.WithRotationCount(10),								// 除了当前输出文件，历史文件最多保存10个
		rotatelogs.WithRotationSize(1024 * 1024 * 100),					// 单个文件最多100MB，就会切割
		rotatelogs.WithRotationTime(time.Hour * 24),					// 单个文件最多24个小时，就会切割
		//rotatelogs.WithHandler(rotatelogs.HandlerFunc(func(event rotatelogs.Event) {
		//	if rotatelogs.FileRotatedEventType == event.Type() {
		//	}
		//})),
	)
	if err != nil {
		log.Fatalf("创建日志Writer异常: %s\n", err.Error())
	}
	return logWriter
}

