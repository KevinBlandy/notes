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
			* 处理panic异常， out 是指定异常信息的输出地，会包含请求dump等信息，默认是 stdout
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


----------------------
常用的一些中间件
----------------------
	# 最大消息体限制
		func MaxBody (size int64) func(ctx *gin.Context){
			return func(ctx *gin.Context) {
				ctx.Request.Body = http.MaxBytesReader(ctx.Writer, ctx.Request.Body , size)
				ctx.Next()
			}
		}
		
		* 对请求体进行读取的时候，大小超过这个限制，就会返回异常：
			errors.New("http: request body too large")
		* 如果ctx.Writer实现了 requestTooLarge() 方法的话，还会执行 requestTooLarge() 调用


	# 调用链超时控制
		func TimeOut (timeout time.Duration) func(*gin.Context) {
			return func(c *gin.Context) {
				ctx, cancel := context.WithTimeout(c.Request.Context(), timeout)
				defer cancel()

				c.Request = c.Request.WithContext(ctx)

				c.Next()
			}
		}

		* 它并不会限制当前请求的时间，仅仅是创建了一个超时ctx绑定到Request
		* 业务controller中，可以获取到这个ctx去执行其他的服务调用（服务调用：A => B => C）
			_, err := ctxhttp.Get(c.Request.Context(), http.DefaultClient, "https://www.google.com/")
			if err != nil {
				log.Fatalf("ctxhttp.Get err: %v", err)
			}

	
	# 读取响应/请求BODY中间件
		type LogWriter struct {
			gin.ResponseWriter
			Buffer *bytes.Buffer
		}
		func (l LogWriter) Write(data []byte) (int, error){
			if count, err := l.Buffer.Write(data); err != nil {
				return count, err
			}
			return l.ResponseWriter.Write(data)
		}

		// Log 记录请求响应日志等等信息
		func Log (ctx *gin.Context ){

			// 读取请求体
			var body, err = io.ReadAll(ctx.Request.Body)
			if err != nil {
				log.Printf("BODY读取异常：%s\n", err.Error())
			}

			// 重新设置请求体
			ctx.Request.Body = io.NopCloser(bytes.NewReader(body))

			// 记录响应日志
			var logWriter = &LogWriter {
				ResponseWriter: ctx.Writer,
				Buffer:    &bytes.Buffer{},
			}
			ctx.Writer = logWriter

			// 开始和结束时间
			var startTime = time.Now().Unix()
			ctx.Next()
			var endTime = time.Now().Unix()

			var method = ctx.Request.Method					// 请求方法
			var fullPath = ctx.FullPath()					// 映射路径
			var requestURI = ctx.Request.URL.RequestURI()	// 完整的URL，包括查询参数
			var status = ctx.Writer.Status()				// 响应状态码
			var responseBody = logWriter.Buffer.String()	// 字符串响应体，如果响应的不是字符串，则需要按需解码

			log.Printf("method=%s, path=%s, uri=%s, startTime=%d, endTime=%d, reqBody=%s, respBody=%s, status=%d\n",
				method, fullPath, requestURI, startTime, endTime, string(body), responseBody, status,
			)
		}
