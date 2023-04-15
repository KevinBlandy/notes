package log

import (
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

var (
	// App app全局日志记录器
	app *zap.Logger

	level zap.AtomicLevel
)

// newLogFileWriter 创建新的日志输出文件
func newLogFileWriter(file string, maxSize, maxAge, maxBackups int, localTime, compress bool) *lumberjack.Logger {
	return &lumberjack.Logger{
		Filename:   file,
		MaxSize:    maxSize,
		MaxAge:     maxAge,
		MaxBackups: maxBackups,
		LocalTime:  localTime,
		Compress:   compress,
	}
}

// newLogger 创建新的logger
func newLogger(level zap.AtomicLevel, logWriter io.Writer, errorWriter io.Writer) *zap.Logger {
	encodeConfig := zap.NewProductionEncoderConfig()
	encodeConfig.MessageKey = "message"
	encodeConfig.TimeKey = "time"
	encodeConfig.EncodeLevel = func(level zapcore.Level, encoder zapcore.PrimitiveArrayEncoder) {
		encoder.AppendString(strings.ToUpper(level.String()))
	}
	encodeConfig.CallerKey = "go"
	encodeConfig.EncodeTime = func(time time.Time, encoder zapcore.PrimitiveArrayEncoder) {
		encoder.AppendString(time.Format("2006-01-02 15:04:05"))
	}

	// json编码器
	jsonEncoder := zapcore.NewJSONEncoder(encodeConfig)

	// 控制台编码器
	consoleEncoder := zapcore.NewConsoleEncoder(encodeConfig)

	core := zapcore.NewTee(
		// json编码输出到文件
		zapcore.NewCore(jsonEncoder, zapcore.AddSync(logWriter), level),
		// console编码输出到控制台
		zapcore.NewCore(consoleEncoder, os.Stdout, level),
		// 异常级别的日志，以json编码输出到异常日志
		zapcore.NewCore(jsonEncoder, zapcore.AddSync(errorWriter), zap.LevelEnablerFunc(func(level zapcore.Level) bool {
			return level >= zap.ErrorLevel
		})),
	)

	// 日志记录器的一些选项
	options := []zap.Option{
		zap.AddCaller(),                       // 日志中添加调用信息
		zap.AddCallerSkip(1),                  // 跳过一层调用栈
		zap.AddStacktrace(zapcore.ErrorLevel), // 异常级别以上，添加调用栈信息
		//zap.Hooks(ErrorEvent),
	}
	return zap.New(core, options...)
}

// Initialization 初始化日志组件
func Initialization() func() {
	// AppLogWriter App全局日志输出
	appLogWriter := newLogFileWriter(filepath.Join("logs", "app.log"), 100, 0, 10, true, true)

	// ErrorLogWriter 异常日志输出
	errorLogWriter := newLogFileWriter(filepath.Join("logs", "error.log"), 100, 0, 10, true, true)

	level = zap.NewAtomicLevelAt(zap.DebugLevel)
	app = newLogger(level, appLogWriter, errorLogWriter)

	return func() {
		if err := app.Sync(); err != nil {
			_, _ = fmt.Fprintf(os.Stderr, "日志同步异常: %s\n", err.Error())
		}
		var CloseLogger = func(logger *lumberjack.Logger) {
			if err := logger.Close(); err != nil {
				_, _ = fmt.Fprintf(os.Stderr, "日志文件关闭异常, logger=%s, err=%s\n", logger.Filename, err.Error())
			}
		}
		CloseLogger(appLogWriter)
		CloseLogger(errorLogWriter)
	}
}

func Debug(msg string, field ...zap.Field) {
	app.Debug(msg, field...)
}
func Info(msg string, field ...zap.Field) {
	app.Info(msg, field...)
}
func Warn(msg string, field ...zap.Field) {
	app.Warn(msg, field...)
}
func Error(msg string, field ...zap.Field) {
	app.Error(msg, field...)
}
func Panic(msg string, field ...zap.Field) {
	app.Panic(msg, field...)
}
func DPanic(msg string, field ...zap.Field) {
	app.DPanic(msg, field...)
}
func Fatal(msg string, field ...zap.Field) {
	app.Fatal(msg, field...)
}
func Level() zapcore.Level {
	return level.Level()
}
func SetLevel(newLevel zapcore.Level) {
	level.SetLevel(newLevel)
}
