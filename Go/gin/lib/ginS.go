-------------------
ginS
-------------------

-------------------
var
-------------------


-------------------
type
-------------------


-------------------
func
-------------------
	func Any(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func DELETE(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func GET(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func Group(relativePath string, handlers ...gin.HandlerFunc) *gin.RouterGroup
	func HEAD(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func Handle(httpMethod, relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func LoadHTMLFiles(files ...string)
	func LoadHTMLGlob(pattern string)
	func NoMethod(handlers ...gin.HandlerFunc)
	func NoRoute(handlers ...gin.HandlerFunc)
	func OPTIONS(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func PATCH(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func POST(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func PUT(relativePath string, handlers ...gin.HandlerFunc) gin.IRoutes
	func Routes() gin.RoutesInfo
	func Run(addr ...string) (err error)
	func RunFd(fd int) (err error)
	func RunTLS(addr, certFile, keyFile string) (err error)
	func RunUnix(file string) (err error)
	func SetHTMLTemplate(templ *template.Template)
	func Static(relativePath, root string) gin.IRoutes
	func StaticFS(relativePath string, fs http.FileSystem) gin.IRoutes
	func StaticFile(relativePath, filepath string) gin.IRoutes
	func Use(middlewares ...gin.HandlerFunc) gin.IRoutes