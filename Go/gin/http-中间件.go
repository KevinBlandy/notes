----------------------
req 中间件
----------------------
	# 类似于拦截器，本质上也是一个处理方法
		type HandlerFunc func(*Context)
	
	# 预定义的中间件
		func BasicAuth(accounts Accounts) HandlerFunc
		func BasicAuthForRealm(accounts Accounts, realm string) HandlerFunc
			* auth认证，如果认证成功会把用户信息放在ctx中，如果 realm 为""，则默认使用 "Authorization Required"。
			* key是 const AuthUserKey = "user"
			
		func Bind(val interface{}) HandlerFunc
			* 把数据绑定到val，如果绑定成功，就会把val设置到context中
			* 默认的Key是	const BindKey = "_gin-gonic/gin/bindkey"

		func ErrorLogger() HandlerFunc
			* 任意异常处理器，会把异常解析为JSON响应给客户端
				return ErrorLoggerT(ErrorTypeAny)
		func ErrorLoggerT(typ ErrorType) HandlerFunc
			* 仅仅处理指定异常

		func Logger() HandlerFunc
		func LoggerWithConfig(conf LoggerConfig) HandlerFunc
		func LoggerWithFormatter(f LogFormatter) HandlerFunc
		func LoggerWithWriter(out io.Writer, notlogged ...string) HandlerFunc
			* 获取日志信息

		func Recovery() HandlerFunc
		func RecoveryWithWriter(out io.Writer) HandlerFunc
			* 处理panic异常
			* 如果是 broken connection，则忽略，反之则响应客户端500

		func WrapF(f http.HandlerFunc) HandlerFunc
			* 把http.HandlerFunc 包装成 HandlerFunc
				return func(c *Context) {
					f(c.Writer, c.Request)
				}

		func WrapH(h http.Handler) HandlerFunc
			* 包装成 HandlerFunc
				return func(c *Context) {
					h.ServeHTTP(c.Writer, c.Request)
				}
	
	# 添加中间件以及执行流程
		engine := gin.New()
		
		// 添加全局的
		engine.Use(func(context *gin.Context) {
			fmt.Println("第一个开始....")
			// 执行下一个
			context.Next()
			fmt.Println("第一个结束....")
		})
		engine.Use(func(context *gin.Context) {
			fmt.Println("第二个开始....")
			// 执行下一个
			context.Next()
			fmt.Println("第二个结束....")
		})
		engine.GET("/", func(context *gin.Context) {
			context.Writer.Write([]byte("Hello"))
		})

		
		// 为指定的URI添加
		engine.GET("/", func(context *gin.Context) {
			// 第一个中间件
			context.Next()
		}, func(context *gin.Context) {
			// 第二个中间件
			context.Next()
		}, func(context *gin.Context) {
			// 第三个中间件 TODO
		})

		// 为指定的Group添加
		g := engine.Group("/user", func(context *gin.Context) {
			fmt.Println("第一个")
			context.Next()
		}, func(context *gin.Context) {
			fmt.Println("第二个")
			context.Next()
		})

		server := http.Server{Addr: ":8080", Handler: engine}
		server.ListenAndServe()




		* 输出日志
			第一个开始....
			第二个开始....
			第二个结束....
			第一个结束....