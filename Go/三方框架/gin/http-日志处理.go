------------------------------
日志处理的核心类
------------------------------

	# 核心的接口
		* 日志的格式化处理器
			type LogFormatter func(params LogFormatterParams) string
		
		* 格式化参数
			type LogFormatterParams struct {
				Request *http.Request
					* 请求
				TimeStamp time.Time
					* 执行时间戳
				StatusCode int
					* 状态码
				Latency time.Duration
					* 执行时间
				ClientIP string
					* 客户端IP
				Method string
					* 请求方法
				Path string
					* 请求路径
				ErrorMessage string
					* 异常信息
				BodySize int
					* 请求体大小
				Keys map[string]interface{} // contains filtered or unexported fields
			}
			func (p *LogFormatterParams) IsOutputColor() bool
			func (p *LogFormatterParams) MethodColor() string
			func (p *LogFormatterParams) ResetColor() string
			func (p *LogFormatterParams) StatusCodeColor() string
		
		* 日志的配置
			type LoggerConfig struct {
				Formatter LogFormatter
					* 格式化处理器，默认 gin.defaultLogFormatter
				Output io.Writer
					* 输出目的地，默认	gin.DefaultWriter.
				SkipPaths []string
					* 要跳过的路径
			}

	# 提供的一些日志方法
		func Logger() HandlerFunc 
			* 返回默认配置的日志处理器
				return LoggerWithConfig(LoggerConfig{})
		func LoggerWithConfig(conf LoggerConfig) HandlerFunc
			* 根据配置，返回日志处理器
		func LoggerWithFormatter(f LogFormatter) HandlerFunc
			* 根据格式化Formater配置日期处理器
		func LoggerWithWriter(out io.Writer, notlogged ...string) HandlerFunc
			* 根据输出位置，和忽略路径返回处理器
		
	# 全局默认的debug日志输出方法
		var DebugPrintRouteFunc func(httpMethod, absolutePath, handlerName string, nuHandlers int)
			* 默认
				debugPrint("%-6s %-25s --> %s (%d handlers)\n", httpMethod, absolutePath, handlerName, nuHandlers)
	

	# 以中间件的形式添加到
		
		