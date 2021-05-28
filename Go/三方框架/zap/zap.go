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
		
