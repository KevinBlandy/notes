-----------------------
mime
-----------------------
	# ContentType类型的包
	# 预定义的后缀和类型
		".css":  "text/css; charset=utf-8",
		".gif":  "image/gif",
		".htm":  "text/html; charset=utf-8",
		".html": "text/html; charset=utf-8",
		".jpeg": "image/jpeg",
		".jpg":  "image/jpeg",
		".js":   "text/javascript; charset=utf-8",
		".json": "application/json",
		".mjs":  "text/javascript; charset=utf-8",
		".pdf":  "application/pdf",
		".png":  "image/png",
		".svg":  "image/svg+xml",
		".wasm": "application/wasm",
		".webp": "image/webp",
		".xml":  "text/xml; charset=utf-8",

-----------------------
var
-----------------------
	const (
		// BEncoding represents Base64 encoding scheme as defined by RFC 2045.
		BEncoding = WordEncoder('b')
		// QEncoding represents the Q-encoding scheme as defined by RFC 2047.
		QEncoding = WordEncoder('q')
	)
	
	var ErrInvalidMediaParameter = errors.New("mime: invalid media parameter")

-----------------------
type
-----------------------

-----------------------
func
-----------------------
	func AddExtensionType(ext, typ string) error
		* 添加文件后缀和content-Type映射到系统
			mime.AddExtensionType(".foo", "application/foo")
		* 后缀，必须以.开头，否则异常

	func ExtensionsByType(typ string) ([]string, error)
		* 根据ContentType获取这个类型下的所有文件后缀

	func FormatMediaType(t string, param map[string]string) string
		* 对contentType t进行格式化，按照规范添加参数param
			var val = mime.FormatMediaType("application/json", map[string]string{
				"charset": "utf-8",
			})
			fmt.Println(val)  // application/json; charset=utf-8
		
		* 它可以正确的解析 attachment
			mediaType := mime.FormatMediaType("attachment", map[string]string{"filename": "金瓶梅.pdf"})
			log.Println(mediaType) 
			// attachment; filename*=utf-8''%E9%87%91%E7%93%B6%E6%A2%85.pdf
			

			// 用于下载
			ctx.Header("Content-Disposition", mime.FormatMediaType("attachment", map[string]string{"filename": stat.Name()}))
			

	func ParseMediaType(v string) (mediatype string, params map[string]string, err error)
		* 解析ContentType，返回ContentType，参数，异常

	func TypeByExtension(ext string) string
		* 根据文件后缀，返回ContentType