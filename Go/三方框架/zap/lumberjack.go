--------------------
lumberjack
--------------------
	# 日志切割框架
		https://github.com/natefinch/lumberjack
		https://pkg.go.dev/gopkg.in/natefinch/lumberjack.v2
	
		import "gopkg.in/natefinch/lumberjack.v2"
	
--------------------
type
--------------------	

	type Logger struct {
		Filename string `json:"filename" yaml:"filename"`
			* 日志输出文件
			* 如果为空，则会在临时文件路径使用: <processname>-lumberjack.log 输出
			* 备份的日志也会在同一个目录中保存

		MaxSize int `json:"maxsize" yaml:"maxsize"`
			* 单个日志文件的最大体积，超过这个体积就会被切割，单位是Mb
			* 默认是 100Mb

		MaxAge int `json:"maxage" yaml:"maxage"`
			* 日志保留的最大天数(只保留最近多少天的日志)
			* 默认不会删除旧文件

		MaxBackups int `json:"maxbackups" yaml:"maxbackups"`
			* 最大保留的备份文件数量
			* 如果设置了MaxAge，则超过MaxAge都会被删除，而无视MaxBackups

		LocalTime bool `json:"localtime" yaml:"localtime"`
			* 是否使用本地时间，默认使用UTC时间

		Compress bool `json:"compress" yaml:"compress"`
			* 是否要压缩文件，默认不压缩
	}

	func (l *Logger) Close() error
		* 关闭资源 

	func (l *Logger) Rotate() error
		* 马上切割文件输出
		
	func (l *Logger) Write(p []byte) (n int, err error)
		* 写入日志信息