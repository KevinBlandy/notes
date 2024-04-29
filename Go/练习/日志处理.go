package log

import (
	"context"
	"errors"
	"fmt"
	"gopkg.in/natefinch/lumberjack.v2"
	"log/slog"
	"os"
	"path/filepath"
	"time"
)

// Level 日志级别句柄，控制全局日志级别，默认级别为 Info
var Level = &slog.LevelVar{}

// logFile 日志文件
var logFile *lumberjack.Logger

func init() {

	logFile = &lumberjack.Logger{
		Filename:   filepath.Join("logs", "app.log"), // 在运行目录下创建 logs 目录
		MaxSize:    10,                               // Mb
		MaxAge:     10,                               // 最多保留天数
		MaxBackups: 100,                              // 最多保留备份数量
		LocalTime:  true,                             // 使用本地事件
		Compress:   true,                             // 压缩文件
	}

	// 日志选项
	options := &slog.HandlerOptions{
		AddSource:   false,
		Level:       Level,
		ReplaceAttr: dateTimeFormatter,
	}

	slog.SetDefault(slog.New(newCombinedHandler(Level, slog.NewJSONHandler(logFile, options), slog.NewTextHandler(os.Stdout, options))))
}

// dateTimeFormatter 日期格式化
func dateTimeFormatter(groups []string, a slog.Attr) slog.Attr {
	if a.Value.Kind() == slog.KindTime {
		return slog.String(a.Key, a.Value.Time().Format(time.DateTime))
	}
	return a
}

// Close 释放资源
func Close() {
	if err := logFile.Close(); err != nil {
		_, _ = fmt.Fprintf(os.Stderr, "logfile close error: %v\n", err.Error())
	}
}

// combinedHandler 组合 Handler
type combinedHandler struct {
	handlers []slog.Handler
	level    slog.Leveler
}

func newCombinedHandler(level slog.Leveler, handlers ...slog.Handler) *combinedHandler {
	return &combinedHandler{handlers: handlers, level: level}
}

func (c *combinedHandler) Enabled(_ context.Context, level slog.Level) bool {
	return level.Level() >= c.level.Level()
}

func (c *combinedHandler) Handle(ctx context.Context, record slog.Record) error {
	var errSlice []error
	for _, v := range c.handlers {
		if err := v.Handle(ctx, record); err != nil {
			errSlice = append(errSlice, err)
		}
	}
	if len(errSlice) > 0 {
		return errors.Join(errSlice...)
	}
	return nil
}
func (c *combinedHandler) WithAttrs(attrs []slog.Attr) slog.Handler {
	var handlers []slog.Handler
	for _, v := range c.handlers {
		handlers = append(handlers, v.WithAttrs(attrs))
	}
	return newCombinedHandler(c.level, handlers...)
}

func (c *combinedHandler) WithGroup(name string) slog.Handler {
	var handlers []slog.Handler
	for _, v := range c.handlers {
		handlers = append(handlers, v.WithGroup(name))
	}
	return newCombinedHandler(c.level, handlers...)
}


----------------------
使用
----------------------

	package main

	import (
		"log/slog"
		"app/log" // 导入 log 包，初始化 init
	)

	func main() {
		// 设置全局日志级别
		log.Level.Set(slog.LevelDebug)

		// 直接调用标准库输出日志
		slog.Info("Hi")

		// 关闭日志文件资源
		log.Close()
	}
