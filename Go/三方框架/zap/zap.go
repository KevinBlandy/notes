--------------------
zap
--------------------
	# Uber开源的日志框架，好像性能很牛逼
		https://github.com/uber-go/zap

	
	# 基本的logger, 安全, 快速
		logger, err := zap.NewProduction()
		if err != nil {
			log.Fatalf("logger create err: %s\n", err.Error())
		}
		logger.Info("Hello", zap.String("name", "world"))
		// {"level":"info","ts":1622097413.9677129,"caller":"go-project/main.go:14","msg":"Hello","name":"world"}
	
	# SugaredLogger
		* 支持结构化和printf样式的日志

		logger, err := zap.NewProduction()
		if err != nil {
			log.Fatalf("logger create err: %s\n", err.Error())
		}
		sugar := logger.Sugar()
		sugar.Info("---")
		sugar.Infof("Hello %s", "world")
	
	# 默认情况下，记录器是无缓冲的
		* 由于zap的低级API允许缓冲，因此在退出进程之前调用Sync是一个好习惯
			logger, err := zap.NewProduction()
			defer func() {
				if err := logger.Sync(); err != nil {
					fmt.Printf("logger sync err: %s\n", err.Error())
				}
			}()
		
	
	# 日志纪录的层级关系
		* 可以使用 zap.Namespace(key string) Field 构建一个命名空间，后续的Field都记录在此命名空间中

		* 输出的时候设置命名空间
			  logger := zap.NewExample()
			  defer logger.Sync()

			  logger.Info("tracked some metrics",
				zap.Namespace("metrics"),  // 命名空间
				zap.Int("counter", 1),
			  )
			  // {"level":"info","msg":"tracked some metrics","metrics":{"counter":1}}
		
		* 创建新的logger时设置命名空间
			  logger2 := logger.With(
				zap.Namespace("metrics"),
				zap.Int("counter", 1),
			  )
			  logger2.Info("tracked some metrics")
			  // {"level":"info","msg":"tracked some metrics","metrics":{"counter":1}}

	
	# 全局logger
		* zap提供了两个全局的Logger，一个是*zap.Logger，可调用zap.L()获得；另一个是*zap.SugaredLogger，可调用zap.S()获得。
		* 需要注意的是，全局的Logger默认并不会记录日志！它是一个无实际效果的Logger。
			// go.uber.org/zap/global.go
			var (
			  _globalMu sync.RWMutex
			  _globalL  = NewNop()
			  _globalS  = _globalL.Sugar()
			)
		
		* 可以使用ReplaceGlobals(logger *Logger) func()将logger设置为全局的Logger，该函数返回一个无参函数，用于恢复全局Logger设置
			func main() {
			  zap.L().Info("global Logger before")
			  zap.S().Info("global SugaredLogger before")  // 没有输出

			  logger := zap.NewExample()
			  defer logger.Sync()

			  zap.ReplaceGlobals(logger)				// 使用指定的logger代替全局的

			  zap.L().Info("global Logger after")
			  zap.S().Info("global SugaredLogger after")
			}


	
	# 创建
		import (
			"fmt"
			"go.uber.org/zap"
			"go.uber.org/zap/zapcore"
			"os"
			"strings"
			"time"
		)


		func main() {

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

			// 消息编码器
			jsonEncode := zapcore.NewJSONEncoder(encodeConfig)

			jsonEncode.OpenNamespace("foo")
			jsonEncode.AddString("subFoo", "SubFoo")

			// 日志输出目的地
			writer := zapcore.NewMultiWriteSyncer(zapcore.AddSync(os.Stdout))

			// 动态的日志级别
			level := zap.NewAtomicLevelAt(zapcore.DebugLevel)

			// 创建核心配置
			core := zapcore.NewCore(jsonEncode, writer, level)

			// 日志记录器的一些选项
			options := []zap.Option {
				zap.AddCaller(),		// 日志中添加调用信息
				zap.AddStacktrace(zapcore.ErrorLevel),  // 异常级别以上，添加调用栈信息
				zap.Hooks(func(entry zapcore.Entry) error {  // 添加钩子函数
					fmt.Println(entry.Stack)
					return nil
				}),
			}

			// 创建日志记录器, 设置名称
			logger := zap.New(core, options...).Named("root")

			defer logger.Sync() // 最终刷出缓冲的日志

			logger.Error("Hello")
		}