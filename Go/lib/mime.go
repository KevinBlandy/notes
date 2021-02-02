-----------------------
mine
-----------------------

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

	func ExtensionsByType(typ string) ([]string, error)

	func FormatMediaType(t string, param map[string]string) string

	func ParseMediaType(v string) (mediatype string, params map[string]string, err error)

	func TypeByExtension(ext string) string
		* 根据文件后缀，返回ContentType