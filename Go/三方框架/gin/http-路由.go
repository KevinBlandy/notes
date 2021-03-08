----------------------
req路由设置
----------------------
	# 路由相关接口
		* 执行器，和执行器链
			type HandlerFunc func(*Context)
			type HandlersChain []HandlerFunc
		
		* IRouter定义了所有路由的处理器接口，包括单个路由器和组路由器。
			type IRouter interface {
				IRoutes
				Group(string, ...HandlerFunc) *RouterGroup
			}

		* IRoutes定义了所有的路由处理接口。
			type IRoutes interface {
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
		
		* RouterGroup是内部用来配置路由的，一个RouterGroup与一个前缀和一个处理程序数组（中间件）。
			type RouterGroup struct {
				Handlers HandlersChain
					* handler执行链
			}

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
				
			func (group *RouterGroup) StaticFS(relativePath string, fs http.FileSystem) IRoutes
				* 指定fs映射静态文件
					engine.Group("/").StaticFS("/static", gin.Dir("C:\\", true))

			func (group *RouterGroup) StaticFile(relativePath, filepath string) IRoutes
				* 指定静态文件

			func (group *RouterGroup) Use(middleware ...HandlerFunc) IRoutes
		
		* 路由信息
			type RouteInfo struct {
					Method      string
					Path        string
					Handler     string
					HandlerFunc HandlerFunc
				}
			type RoutesInfo []RouteInfo
			
					
	# 群组路由
		engine := gin.Default()
		user := engine.Group("/user")
		user.GET("/login", func(context *gin.Context) {
			context.Writer.Write([]byte(context.Request.URL.String()))
		})
		user.GET("/submit", func(context *gin.Context) {
			context.Writer.Write([]byte(context.Request.URL.String()))
		})
		user.GET("/read", func(context *gin.Context) {
			context.Writer.Write([]byte(context.Request.URL.String()))
		})

