-----------------------
gzip
-----------------------
	# 添加Gzip中间件
		import (
			"github.com/gin-contrib/gzip"
			"github.com/gin-gonic/gin"
		)

		engine := gin.New()

		// 添加gzip中间件
		engine.Use(gzip.Gzip(gzip.DefaultCompression))
	

--------------------------
var
--------------------------
	const (
		BestCompression    = gzip.BestCompression
		BestSpeed          = gzip.BestSpeed
		DefaultCompression = gzip.DefaultCompression
		NoCompression      = gzip.NoCompression
	)
	var (
		DefaultExcludedExtentions = NewExcludedExtensions([]string{
			".png", ".gif", ".jpeg", ".jpg",
		})
		DefaultOptions = &Options{
			ExcludedExtensions: DefaultExcludedExtentions,
		}
	)
--------------------------
type
--------------------------
	# type ExcludedExtensions map[string]bool
		
		* 指定要压缩请求的后缀

		func NewExcludedExtensions(extensions []string) ExcludedExtensions
		func (e ExcludedExtensions) Contains(target string) bool
	
	# type ExcludedPaths []string
		
		* 指定要压缩请求的路径
		
		func NewExcludedPaths(paths []string) ExcludedPaths
		func (e ExcludedPaths) Contains(requestURI string) bool
	
	# type ExcludedPathesRegexs []*regexp.Regexp
		
		* 指定要压缩请求的路径正则

		func NewExcludedPathesRegexs(regexs []string) ExcludedPathesRegexs 
		func (e ExcludedPathesRegexs) Contains(requestURI string) bool

	# type Option func(*Options)
		
		* 压缩配置项方法
		
		func WithDecompressFn(decompressFn func(c *gin.Context)) Option
		func WithExcludedExtensions(args []string) Option
		func WithExcludedPaths(args []string) Option
		func WithExcludedPathsRegexs(args []string) Option
	
	# type Options struct {
			ExcludedExtensions   ExcludedExtensions
			ExcludedPaths        ExcludedPaths
			ExcludedPathesRegexs ExcludedPathesRegexs
			DecompressFn         func(c *gin.Context)
		}

		* 压缩配置项

--------------------------
func
--------------------------
	func DefaultDecompressHandle(c *gin.Context)
	func Gzip(level int, options ...Option) gin.HandlerFunc

			
