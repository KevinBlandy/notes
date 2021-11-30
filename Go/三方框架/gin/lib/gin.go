------------------------
gin
------------------------

------------------------
var
------------------------
	const EnvGinMode = "GIN_MODE"
	const (
		DebugMode = "debug"			// debug模式
		ReleaseMode = "release"		// 正式
		TestMode = "test"			// 测试
	)

	var DefaultWriter io.Writer = os.Stdout
	var DefaultErrorWriter io.Writer = os.Stderr
		* 默认的日志输出目的地


	const (
		MIMEJSON              = binding.MIMEJSON
		MIMEHTML              = binding.MIMEHTML
		MIMEXML               = binding.MIMEXML
		MIMEXML2              = binding.MIMEXML2
		MIMEPlain             = binding.MIMEPlain
		MIMEPOSTForm          = binding.MIMEPOSTForm
		MIMEMultipartPOSTForm = binding.MIMEMultipartPOSTForm
		MIMEYAML              = binding.MIMEYAML
		BodyBytesKey          = "_gin-gonic/gin/bodybyteskey"
	)

	var DebugPrintRouteFunc func(httpMethod, absolutePath, handlerName string, nuHandlers int)
		* Debug日志的输出格式化默认方法，默认
			debugPrint("%-6s %-25s --> %s (%d handlers)\n", httpMethod, absolutePath, handlerName, nuHandlers)
	


------------------------
type
------------------------
	# type Accounts map[string]string
		* auth认证的账户密码
	
	# type Context struct {
			Request *http.Request	
				* reuqest
			Writer  ResponseWriter
				* response
			Params Params
				* 请求参数键值对
			Keys map[string]interface{}
				* 请求参数keys
			Errors errorMsgs	// type errorMsgs []*Error
				* 异常消息，这是一个切片

			Accepted []string	
				* 客户端accept头
		}
		
		* 请求体上下文

		func (c *Context) Abort()
		func (c *Context) AbortWithError(code int, err error) *Error
		func (c *Context) AbortWithStatus(code int)
		func (c *Context) AbortWithStatusJSON(code int, jsonObj interface{})
			* 响应客户端，并且终止后续的Handler链调用
			* 本质上就是修改了了context中的index值
				const abortIndex int8 = math.MaxInt8 / 2
			* WithError 会把异常添加到Context的Errors异常切片中



		func (c *Context) AsciiJSON(code int, obj interface{})
			* 响应json数据，会把非ascii字符转义

		func (c *Context) Bind(obj interface{}) error
			* 绑定请求参数到obj，会根据ContentType解析数据


		func (c *Context) BindHeader(obj interface{}) error
		func (c *Context) BindJSON(obj interface{}) error
		func (c *Context) BindQuery(obj interface{}) error
		func (c *Context) BindUri(obj interface{}) error
		func (c *Context) BindWith(obj interface{}, b binding.Binding) error
		func (c *Context) BindXML(obj interface{}) error
		func (c *Context) BindYAML(obj interface{}) error
			* 

		func (c *Context) ClientIP() string
			* 获取客户端的IP地址
			* 会尝试读取代理头 X-Forwarded-For, X-Real-Ip, X-Appengine-Remote-Addr

		func (c *Context) ContentType() string
		func (c *Context) Cookie(name string) (string, error)
		func (c *Context) Copy() *Context
			* ctx资源，会在请求结束后就释放掉，如果需要在一些异步任务中处理请求
			* 那么可以复制一个context过去

		func (c *Context) Data(code int, contentType string, data []byte)
		func (c *Context) DataFromReader(code int, contentLength int64, contentType string, reader io.Reader, ...)
			* 从指定的Reader读取数据响应给客户端
		
		func (c *Context) Deadline() (deadline time.Time, ok bool)
		func (c *Context) DefaultPostForm(key, defaultValue string) string
		func (c *Context) DefaultQuery(key, defaultValue string) string
			* 获取查询参数，如果不存在，返回默认值
		
		func (c *Context) Done() <-chan struct{}
		func (c *Context) Err() error

		func (c *Context) Error(err error) *Error
			* 把一个ee添加到 Erros
			* 如果err不不是 gin.Error，则会包装为Error

		func (c *Context) File(filepath string)
			*  文件以高效的方式将指定的文件写入体流中
				http.ServeFile(c.Writer, c.Request, filepath)

		func (c *Context) FileAttachment(filepath, filename string)
			* 给客户端响应一个文件下载流
			* filepath指定文件的路径，filenam指定文件的名称

		func (c *Context) FileFromFS(filepath string, fs http.FileSystem)
		func (c *Context) FormFile(name string) (*multipart.FileHeader, error)
			* 获取单个的上传文件
		
		func (c *Context) FullPath() string
			* 获取完整的Router路径，它不是请求路径，而是映射路径
				router.GET("/user/:id", func(c *gin.Context) {
					c.FullPath() == "/user/:id" // true
				})

		func (c *Context) Get(key string) (value interface{}, exists bool)
			* 获取ctx中存储的数据

		func (c *Context) GetBool(key string) (b bool)
		func (c *Context) GetDuration(key string) (d time.Duration)
		func (c *Context) GetFloat64(key string) (f64 float64)
		func (c *Context) GetHeader(key string) string
		func (c *Context) GetInt(key string) (i int)
		func (c *Context) GetInt64(key string) (i64 int64)
		func (c *Context) GetPostForm(key string) (string, bool)
		func (c *Context) GetPostFormArray(key string) ([]string, bool)
		func (c *Context) GetPostFormMap(key string) (map[string]string, bool)
		func (c *Context) GetQuery(key string) (string, bool)
		func (c *Context) GetQueryArray(key string) ([]string, bool)
		func (c *Context) GetQueryMap(key string) (map[string]string, bool)
			* 获取map形式的query数据
			* 对查询字符串的规格有特殊要求
				/post?ids[a]=1234&ids[b]=hello
				ids := c.QueryMap("ids") => {"a":"1234", "b": "hello"}


		func (c *Context) GetRawData() ([]byte, error)
			* 获取body

		func (c *Context) GetString(key string) (s string)
		func (c *Context) GetStringMap(key string) (sm map[string]interface{})
		func (c *Context) GetStringMapString(key string) (sms map[string]string)
		func (c *Context) GetStringMapStringSlice(key string) (smss map[string][]string)
		func (c *Context) GetStringSlice(key string) (ss []string)
		func (c *Context) GetTime(key string) (t time.Time)

		func (c *Context) HTML(code int, name string, obj interface{})
			* 渲染html，指定状态码，模版引擎文件相对路径，填充对象
				c.HTML(http.StatusOK, "index.tmpl", gin.H{
					"title": "Main website",
				})

		func (c *Context) Handler() HandlerFunc
			* 返回当前Handler
		func (c *Context) HandlerName() string
			* 返回当前Handler的名字
		func (c *Context) HandlerNames() []string
			* 返回执行链的方法名称，如果是匿名方法，则是fun数字结尾， xxx.func1...xxx.funcn

		func (c *Context) Header(key, value string)	
			* 设置Header，如果 value是空字符串，则会删除Header

		func (c *Context) IndentedJSON(code int, obj interface{})
		func (c *Context) IsAborted() bool
			* 判断是否被中断了
				return c.index >= abortIndex

		func (c *Context) IsWebsocket() bool
			* 判断当前请求是否是websocket
			* 本质上是通过判断Header实现的
				Connection upgrade
				Upgrade websocket
			
		func (c *Context) JSON(code int, obj interface{})
		func (c *Context) JSONP(code int, obj interface{})
			* 响应json或者jsonp数据，会对html字符进行unicode编码
			* 回调函数名称的参数是callback
				 /JSONP?callback=x  => x({\"foo\":\"bar\"})

		func (c *Context) MultipartForm() (*multipart.Form, error)
			* 返回multipart.Form请求体

		func (c *Context) MustBindWith(obj interface{}, b binding.Binding) error
			* 使用指定的绑定接口b，把数据绑定到obj
			* 返回是否成功

		func (c *Context) MustGet(key string) interface{}
			*  调用 Get() 获取参数值，如果不存在，panic

		func (c *Context) Negotiate(code int, config Negotiate)
		func (c *Context) NegotiateFormat(offered ...string) string

		func (c *Context) Next()
			* 调用HandlerFunc链表下一个处理器，并且执行

		func (c *Context) Param(key string) string
			* 获取路由参数，也即是URI参数， :可选参数, *必选参数
				/user/:name				=> c.Param("name")		
				/user/:name/*action		=> c.Param("action")

		func (c *Context) PostForm(key string) string
		func (c *Context) PostFormArray(key string) []string
		func (c *Context) PostFormMap(key string) map[string]string
			* 从请求表单中获取map格式的数据
			* 对请求体的字符串有特殊要求
				names[first]=thinkerou&names[second]=tianou
				names := c.PostFormMap("names") => {"first": "thinkerou", "second": "tianou"}
		
		func (c *Context) ProtoBuf(code int, obj interface{})
		func (c *Context) PureJSON(code int, obj interface{})
			* 不对json进行任何编码，最纯粹的json 

		func (c *Context) Query(key string) string
		func (c *Context) QueryArray(key string) []string
		func (c *Context) QueryMap(key string) map[string]string
			* 获取查询参数

		func (c *Context) Redirect(code int, location string)
			* 重定向，指定状态码和路径，可以是相对，也可以是绝对

		func (c *Context) Render(code int, r render.Render)
			* 自定义Render渲染

		func (c *Context) SSEvent(name string, message interface{})
		func (c *Context) SaveUploadedFile(file *multipart.FileHeader, dst string) error
			* 把文件存储到指定的路径
		
		func (c *Context) SecureJSON(code int, obj interface{})
			* 防止 json 劫持。如果给定的结构是数组值，则默认预置 "while(1)," 到响应体。

		func (c *Context) Set(key string, value interface{})
			* 设置数据到context

		func (c *Context) SetAccepted(formats ...string)
		func (c *Context) SetCookie(name, value string, maxAge int, path, domain string, secure, httpOnly bool)
		func (c *Context) SetSameSite(samesite http.SameSite)
		func (c *Context) ShouldBind(obj interface{}) error
			* 自动绑定参数到obj，注意，这个只能调用一次，body读完就没了

		func (c *Context) ShouldBindBodyWith(obj interface{}, bb binding.BindingBody) (err error)
			* 尝试把请求体绑定到obj，bb指定body的类型，返回err表示是否成功
			* 这个牛逼之处在于一个Body可以进行多次绑定
			* 会在绑定之前将 body 存储到Context上下文中， 这会对性能造成轻微影响，如果调用一次就能完成绑定的话，那就不要用这个方法。
			* Context中的key是: BodyBytesKey          = "_gin-gonic/gin/bodybyteskey"


		func (c *Context) ShouldBindHeader(obj interface{}) error
		func (c *Context) ShouldBindJSON(obj interface{}) error
		func (c *Context) ShouldBindQuery(obj interface{}) error
			* 仅仅把绑定查询参数到obj
		
		func (c *Context) ShouldBindUri(obj interface{}) error
		func (c *Context) ShouldBindWith(obj interface{}, b binding.Binding) error
		func (c *Context) ShouldBindXML(obj interface{}) error
		func (c *Context) ShouldBindYAML(obj interface{}) error

		func (c *Context) Status(code int)
		func (c *Context) Stream(step func(w io.Writer) bool) bool
			* 获取客户端的输出流，可以自己通过writer往客户端输出数据，step返回后，会自动的flush
			* 返回的bool表示，是否客户端主动断开了链接

		func (c *Context) String(code int, format string, values ...interface{})
		func (c *Context) Value(key interface{}) interface{}
			* 从ctx中获取值，如果不存在，返回nil

		func (c *Context) XML(code int, obj interface{})
		func (c *Context) YAML(code int, obj interface{})
	
	# type Engine struct {
			RouterGroup
				* 实现了 RouterGroup
			RedirectTrailingSlash bool
				* 是否自动重定向
				* 例如，请求了/foo/，但只有/foo的路径存在，客户端被重定向到/foo，GET请求的http状态码为301。其他请求方法，则是307。

			RedirectFixedPath bool
				* 是否尝试修复当前请求路径，也就是在开启的情况下，gin 会尽可能的帮你找到一个相似的路由规则并在内部重定向过去
				* 主要是对当前的请求路径进行格式清除（删除多余的斜杠）和不区分大小写的路由查找等。

			HandleMethodNotAllowed bool
				* 是否开起请求方法校验，如果方法不匹配会返回 405
				* 如果不开起，在方法不匹配的情况下，只返回 404 

			ForwardedByClientIP    bool
				* 如果开启，则尽可能的返回真实的客户端 IP，先从 X-Forwarded-For 取值，如果没有再从 X-Real-Ip
				
			AppEngine bool
				* 如果启用，它将插入一些以'X-AppEngine...'开头的header，以便更好地与PaaS集成。

			UseRawPath bool
				* 如果启用, 将会使用 url.RawPath 来查询参数，不开启则还是按 url.Path 去获取

			UnescapePathValues bool
				* 是否对路径值进行转义处理

			MaxMultipartMemory int64
				* multipart请求的时候，占用的最大内存，默认是 32 MiB
			
			RemoveExtraSlash bool
			HTMLRender render.HTMLRender
			FuncMap    template.FuncMap
		}
		func Default() *Engine
			* 使用默认的
		
		func New() *Engine
			* 创建新的引擎

		func (engine *Engine) Delims(left, right string) *Engine
			* 使用自定义的模板引擎分隔符

		func (engine *Engine) HandleContext(c *Context)
			* 用于重定向路由
				r.GET("/test", func(c *gin.Context) {
					c.Request.URL.Path = "/test2"		// 重新设置当前的URI
					r.HandleContext(c)					// 执行重定向路由
				})

		func (engine *Engine) LoadHTMLFiles(files ...string)
			* 加载指定的模板引擎，加载后才能在context使用

		func (engine *Engine) LoadHTMLGlob(pattern string)
			* 设置全局的模板引擎加载前缀
			* 指定目录下的所有文件
				router.LoadHTMLGlob("templates/*")
			* 指定目录下的所有目录的所有文件
				router.LoadHTMLGlob("templates/**/*")

		func (engine *Engine) NoMethod(handlers ...HandlerFunc)
		func (engine *Engine) NoRoute(handlers ...HandlerFunc)
			* 添加处理405/404的handler到各自的处理器链
			* 默认了俩处理器，响应text异常信息
				

		func (engine *Engine) Routes() (routes RoutesInfo)
		func (engine *Engine) Run(addr ...string) (err error)
		func (engine *Engine) RunFd(fd int) (err error)
		func (engine *Engine) RunListener(listener net.Listener) (err error)
		func (engine *Engine) RunTLS(addr, certFile, keyFile string) (err error)
		func (engine *Engine) RunUnix(file string) (err error)
		func (engine *Engine) SecureJsonPrefix(prefix string) *Engine
		func (engine *Engine) ServeHTTP(w http.ResponseWriter, req *http.Request)
		func (engine *Engine) SetFuncMap(funcMap template.FuncMap)
			* 自定义模板引擎方法

		func (engine *Engine) SetHTMLTemplate(templ *template.Template)
			* 使用自己定义的模板加载器

		func (engine *Engine) Use(middleware ...HandlerFunc) IRoutes
			* 添加一个或者多个
	
	# type errorMsgs []*Error
		
		* 异常信息集合，是个私有type，提供了公共的方法
		
		func (a errorMsgs) ByType(typ ErrorType) errorMsgs
		func (a errorMsgs) Last() *Error 
		func (a errorMsgs) Errors() []string
		func (a errorMsgs) JSON() interface{} 
		func (a errorMsgs) MarshalJSON() ([]byte, error)
		func (a errorMsgs) String() string


	# type Error struct {
			Err  error
			Type ErrorType
			Meta interface{}
		}
		
		* 异常信息

		func (msg Error) Error() string
		func (msg *Error) IsType(flags ErrorType) bool
		func (msg *Error) JSON() interface{}
		func (msg *Error) MarshalJSON() ([]byte, error)
		func (msg *Error) SetMeta(data interface{}) *Error
		func (msg *Error) SetType(flags ErrorType) *Error
	
	# type ErrorType uint64
		const (
			ErrorTypeBind ErrorType = 1 << 63			// Context.Bind()失败
			ErrorTypeRender ErrorType = 1 << 62			// Context.Render() 失败
			ErrorTypePrivate ErrorType = 1 << 0			// 私有错误?
			ErrorTypePublic ErrorType = 1 << 1			// 公共错误?
			ErrorTypeAny ErrorType = 1<<64 - 1			// 表示任何其他错误
			ErrorTypeNu = 2								// 表示任何其他错误
		)

		* 异常的类型

	# type H map[string]interface{}
		func (h H) MarshalXML(e *xml.Encoder, start xml.StartElement) error

		* 快捷的数据对象，由map构成，key是字符串，value是任意对象
	
	# type HandlerFunc func(*Context)

		* 执行器

		func BasicAuth(accounts Accounts) HandlerFunc
		func BasicAuthForRealm(accounts Accounts, realm string) HandlerFunc
		func Bind(val interface{}) HandlerFunc
		func ErrorLogger() HandlerFunc
		func ErrorLoggerT(typ ErrorType) HandlerFunc
		func Logger() HandlerFunc
		func LoggerWithConfig(conf LoggerConfig) HandlerFunc
		func LoggerWithFormatter(f LogFormatter) HandlerFunc
		func LoggerWithWriter(out io.Writer, notlogged ...string) HandlerFunc
		func Recovery() HandlerFunc
		func RecoveryWithWriter(out io.Writer) HandlerFunc

		func WrapF(f http.HandlerFunc) HandlerFunc
		func WrapH(h http.Handler) HandlerFunc
			* 把标准库的hanlder转换为gin的handlerFunc
	
	# type HandlersChain []HandlerFunc

		* 执行器处理链
		func (c HandlersChain) Last() HandlerFunc
	
	# type IRouter interface {
			IRoutes
			Group(string, ...HandlerFunc) *RouterGroup
		}
	# type IRoutes interface {
			Use(...HandlerFunc) IRoutes
			Handle(string, string, ...HandlerFunc) IRoutes
			Any(string, ...HandlerFunc) IRoutes
			GET(string, ...HandlerFunc) IRoutes
			POST(string, ...HandlerFunc) IRoutes
			DELETE(string, ...HandlerFunc) IRoutes
			PATCH(string, ...HandlerFunc) IRoutes
			PUT(string, ...HandlerFunc) IRoutes
			OPTIONS(string, ...HandlerFunc) IRoutes
			HEAD(string, ...HandlerFunc) IRoutes
			StaticFile(string, string) IRoutes
			Static(string, string) IRoutes
			StaticFS(string, http.FileSystem) IRoutes
		}
		* 单个路由，跟路由组差不多


	# type LogFormatter func(params LogFormatterParams) string
		* 日志格式化方法

	# type LogFormatterParams struct {
			Request *http.Request
			TimeStamp time.Time
			StatusCode int
			Latency time.Duration
			ClientIP string
			Method string
			Path string
			ErrorMessage string
			BodySize int
			Keys map[string]interface{} // contains filtered or unexported fields
		}
		
		* 日志的格式化参数

		func (p *LogFormatterParams) IsOutputColor() bool
		func (p *LogFormatterParams) MethodColor() string
		func (p *LogFormatterParams) ResetColor() string
		func (p *LogFormatterParams) StatusCodeColor() string

	# type LoggerConfig struct {
			Formatter LogFormatter
				* 格式化处理器
			Output io.Writer
				* 输出目的地
			SkipPaths []string
				* 要跳过的路径
		}

		* 日志记录器

	# type Negotiate struct {
			Offered  []string		// 支持的ContenType列表
			HTMLName string			// HTML模板名称
			HTMLData interface{}	// HTMLL数据
			JSONData interface{}	// JSON数据
			XMLData  interface{}	// XML数据
			YAMLData interface{}	// YAML数据
			Data     interface{}	// 普通数据
		}
		
		* 与客户端的ContentType协商


	# type Param struct {
			Key   string
			Value string
		}
	
		type Params []Param
		func (ps Params) ByName(name string) (va string)
		func (ps Params) Get(name string) (string, bool)

	# type ResponseWriter interface {
			http.ResponseWriter
			http.Hijacker
			http.Flusher
			http.CloseNotifier
			Status() int
				* 返回状态码
			Size() int
				* 返回已经响应客户端的body大小
			WriteString(string) (int, error)
				* 写入字符串到客户端
			Written() bool
				* 如果客户端已经响应了数据，则返回 true
			WriteHeaderNow()
				* 强制响应状态码和header
			Pusher() http.Pusher
				* 尝试获取http2的pusher
		}

		* 响应客户端
	
	# type RouteInfo struct {
			Method      string
			Path        string
			Handler     string
			HandlerFunc HandlerFunc
		}
	
	# type RoutesInfo []RouteInfo
	
	# type RouterGroup struct {
			Handlers HandlersChain
				* handler执行链
		}
		
		* 路由组
		
		func (group *RouterGroup) BasePath() string
			* 获取当前路由组的basepath

		func (group *RouterGroup) Group(relativePath string, handlers ...HandlerFunc) *RouterGroup
			* 根据当前路由组，创建返回一个路由组

		func (group *RouterGroup) HEAD(relativePath string, handlers ...HandlerFunc) IRoutes
		
		func (group *RouterGroup) Any(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) Handle(httpMethod, relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) OPTIONS(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) PATCH(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) POST(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) PUT(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) DELETE(relativePath string, handlers ...HandlerFunc) IRoutes
		func (group *RouterGroup) GET(relativePath string, handlers ...HandlerFunc) IRoutes
			* 各种http方法的处理

		func (group *RouterGroup) Static(relativePath, root string) IRoutes
			* 指定目录，映射静态文件
				router.Static("/static", "/var/www")
				router.Static("/assets", "./assets")
			
		func (group *RouterGroup) StaticFS(relativePath string, fs http.FileSystem) IRoutes
			* 指定fs映射静态文件
				router.StaticFS("/static", gin.Dir("C:\\", true))
				router.StaticFS("/more_static", http.Dir("my_file_system"))

		func (group *RouterGroup) StaticFile(relativePath, filepath string) IRoutes
			* 指定静态文件的映射
				router.StaticFile("/favicon.ico", "./resources/favicon.ico")

		func (group *RouterGroup) Use(middleware ...HandlerFunc) IRoutes
			* 添加全局的中间件
	
	
		

------------------------
func
------------------------
	func CreateTestContext(w http.ResponseWriter) (c *Context, r *Engine)
	func Dir(root string, listDirectory bool) http.FileSystem
		* 返回 http.FileSystem，listDirectory 指定是否要列出目录
	
	func DisableBindValidation()
	func DisableConsoleColor()
		* 禁用控制台颜色，将日志写入文件时不需要控制台颜色。

	func EnableJsonDecoderDisallowUnknownFields()
	func EnableJsonDecoderUseNumber()
	func ForceConsoleColor()
		* 强制在控制台输出带颜色的日志

	func IsDebugging() bool
		 * 是否是DEBUG模式

	func Mode() string
	func SetMode(value string)
		* 读取设置运行模式