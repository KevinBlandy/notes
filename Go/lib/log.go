---------------------
log
---------------------

---------------------
变量
---------------------
	# 在日志记录器天可以添加的文本信息
		const (
			Ldate         = 1 << iota     // 当地时区中的日期：2009/01/23
			Ltime                         // 当地时区的时间：01:23:23
			Lmicroseconds                 // 微秒分辨率：01：23：23.123123。假设Ltime。
			Llongfile                     // 完整的文件路径和行号：/a/b/c/d.go:23
			Lshortfile                    // 最终文件名元素和行号：d.go：23 覆盖Llongfile
			LUTC                          // 如果设置了Ldate或Ltime，请使用UTC而不是本地时区
			Lmsgprefix                    // 将“前缀”从行的开头移至消息之前
			LstdFlags     = Ldate | Ltime // 标准记录器的初始值
		)

---------------------
type
---------------------
	# type Logger struct 
		func New(out io.Writer, prefix string, flag int) *Logger
			* 创建新的日志记录器
			* 指定输出流，前缀，标识
		
		func Default() *Logger
			* 返回默认的loggger，也就是 var std = New(os.Stderr, "", LstdFlags)

		func (l *Logger) Fatal(v ...interface{})
		func (l *Logger) Fatalf(format string, v ...interface{})
		func (l *Logger) Fatalln(v ...interface{})
			* 相当于调用 Print()/Printf()/Println() 之后调用 os.Exit(1) 

		func (l *Logger) Flags() int
			* 返回标准记录器的输出标志

		func (l *Logger) Output(calldepth int, s string) error
			* 输出一个日志信息
			* s 指定日志信息，如果s不是换行符结尾，那么logger会自己添加一个记录器
			* 如果设置了Llongfile/Lshortfile，那么calldepth 就是在计算文件名和行号时要跳过的帧数；值为1时，将为Output的调用者打印详细信息。

		func (l *Logger) Panic(v ...interface{})
		func (l *Logger) Panicf(format string, v ...interface{})
		func (l *Logger) Panicln(v ...interface{})
			* 相当于 Print()/Printf()/Println() 之后调用panic()。

		func (l *Logger) Prefix() string
			* 返回设置的前缀信息

		func (l *Logger) Print(v ...interface{})
		func (l *Logger) Printf(format string, v ...interface{})
		func (l *Logger) Println(v ...interface{})
			* 输出日志

		func (l *Logger) SetFlags(flag int)
			* 重新设置标识
		func (l *Logger) SetOutput(w io.Writer)
			* 重新设置输出Writer
		func (l *Logger) SetPrefix(prefix string)
			* 获取设置的前缀信息
		func (l *Logger) Writer() io.Writer
			* 返回日志的输出Writer

---------------------
方法
---------------------
	func Fatal(v ...interface{})
	func Fatalf(format string, v ...interface{})
	func Fatalln(v ...interface{})
	func Flags() int
	func Output(calldepth int, s string) error
	func Panic(v ...interface{})
	func Panicf(format string, v ...interface{})
	func Panicln(v ...interface{})
	func Prefix() string
	func Print(v ...interface{})
	func Printf(format string, v ...interface{})
	func Println(v ...interface{})
	func SetFlags(flag int)
	func SetOutput(w io.Writer)
	func SetPrefix(prefix string)
	func Writer() io.Writer


	* 全局默认的日志记录器，输出到标准异常流
		var std = New(os.Stderr, "", LstdFlags)
