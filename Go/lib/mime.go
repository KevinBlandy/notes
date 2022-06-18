-----------------------
mime
-----------------------
	# ContentType���͵İ�
	# Ԥ����ĺ�׺������
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
		* ����ļ���׺��content-Typeӳ�䵽ϵͳ
			mime.AddExtensionType(".foo", "application/foo")
		* ��׺��������.��ͷ�������쳣

	func ExtensionsByType(typ string) ([]string, error)
		* ����ContentType��ȡ��������µ������ļ���׺

	func FormatMediaType(t string, param map[string]string) string
		* ��contentType t���и�ʽ�������չ淶��Ӳ���param
			var val = mime.FormatMediaType("application/json", map[string]string{
				"charset": "utf-8",
			})
			fmt.Println(val)  // application/json; charset=utf-8
		
		* ��������ȷ�Ľ��� attachment
			mediaType := mime.FormatMediaType("attachment", map[string]string{"filename": "��ƿ÷.pdf"})
			log.Println(mediaType) 
			// attachment; filename*=utf-8''%E9%87%91%E7%93%B6%E6%A2%85.pdf
			

			// ��������
			ctx.Header("Content-Disposition", mime.FormatMediaType("attachment", map[string]string{"filename": stat.Name()}))
			

	func ParseMediaType(v string) (mediatype string, params map[string]string, err error)
		* ����ContentType������ContentType���������쳣

	func TypeByExtension(ext string) string
		* �����ļ���׺������ContentType