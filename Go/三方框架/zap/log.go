import (
	"bytes"
	"encoding/json"
	"fmt"
	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
	"gopkg.in/natefinch/lumberjack.v2"
	"io"
	"os"
	"path/filepath"
	"strings"
	"time"
)

var Loggers = make(map[string] *zap.Logger)

// loggers
var (
	// App app全局日志记录器
	App *zap.Logger
)

// writers
var (
	// AppLogWriter App全局日志输出
	AppLogWriter = NewLogFileWriter(filepath.Join("log", "app.log"), 100, 0, 10, true, true)

	// ErrorLogWriter 异常日志输出
	ErrorLogWriter = NewLogFileWriter(filepath.Join("log", "error", "error.log"), 100, 0, 10, true, true)
)



// Close 释放所有资源
func Close () {
	for name, logger := range Loggers {
		if err := logger.Sync(); err != nil {
			fmt.Fprintf(os.Stderr, "logger同步异常, logger=%s, err=%s\n", name, err.Error())
		}
	}
	var CloseLogger = func(logger *lumberjack.Logger) (){
		if err := logger.Close(); err != nil {
			fmt.Fprintf(os.Stderr, "日志文件关闭异常, logger=%s, err=%s\n", logger.Filename, err.Error())
		}
	}
	CloseLogger(AppLogWriter)
	CloseLogger(ErrorLogWriter)
}


// ErrorEvent 异常日志时间处理
func ErrorEvent (entry zapcore.Entry) error {
	if entry.Level < zapcore.ErrorLevel {
		return nil
	}

	var writer io.Writer

	switch entry.Level {
		case zapcore.ErrorLevel, zapcore.PanicLevel, zapcore.DPanicLevel, zapcore.FatalLevel: writer = ErrorLogWriter
		// TODO 可以让不同级别的日志，输出到不同的文件
		default: return nil
	}

	// 编码为JSON
	jsonData, err := json.Marshal(entry)
	if err != nil {
		return err
	}

	buffer := bytes.NewBuffer(jsonData)
	buffer.WriteString("\n")  // 写入换行符

	// 输出到日志文件
	if _, err := buffer.WriteTo(writer); err != nil {
		return err
	}

	go func() {
		// TODO 发送邮件
		// var content = buffer.String()
	}()

	return nil
}

// NewLogFileWriter 创建新的日志输出文件
func NewLogFileWriter(file string, maxSize, maxAge, maxBackups int, localTime, compress bool) *lumberjack.Logger {
	return &lumberjack.Logger {
		Filename:   file,
		MaxSize:    maxSize,
		MaxAge:     maxAge,
		MaxBackups: maxBackups,
		LocalTime:  localTime,
		Compress:   compress,
	}
}

// NewLogger 创建新的logger
func NewLogger (name string, level zap.AtomicLevel, writer zapcore.WriteSyncer) *zap.Logger {
	// 消息编码器配置
	encodeConfig := zap.NewProductionEncoderConfig()
	encodeConfig.MessageKey = "message"
	encodeConfig.TimeKey = "time"
	encodeConfig.EncodeLevel = func(level zapcore.Level, encoder zapcore.PrimitiveArrayEncoder) {
		encoder.AppendString(strings.ToUpper(level.String()))
	}
	encodeConfig.CallerKey = "file"
	// 时间格式化
	encodeConfig.EncodeTime = func(time time.Time, encoder zapcore.PrimitiveArrayEncoder) {
		encoder.AppendString(time.Format("2006-01-02 15:04:05"))
	}

	// 创建核心配置
	core := zapcore.NewCore(zapcore.NewJSONEncoder(encodeConfig), writer, level)

	// 日志记录器的一些选项
	options := []zap.Option {
		zap.AddCaller(),						// 日志中添加调用信息
		zap.AddStacktrace(zapcore.ErrorLevel),  // 异常级别以上，添加调用栈信息
		zap.Hooks(ErrorEvent),
	}

	// 创建日志记录器, 设置名称
	return zap.New(core, options...).Named(name)
}


func Init (){
	var appLogFile = zapcore.Lock(zapcore.AddSync(AppLogWriter))
	// App日志，输出到标准输出和文件
	App = NewLogger("app", zap.NewAtomicLevelAt(zapcore.DebugLevel), zapcore.NewMultiWriteSyncer(zapcore.AddSync(os.Stdout), appLogFile))

	Loggers["app"] = App
}