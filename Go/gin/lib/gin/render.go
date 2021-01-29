-------------------
render
-------------------
	# 核心的Render接口，定义了2个方法
		// 响应数据到客户端
		Render(http.ResponseWriter) error
		// 设置CoontentType
		WriteContentType(w http.ResponseWriter)
	
	# Render接口的实现
		

-------------------
var
-------------------

-------------------
type
-------------------
	# type AsciiJSON struct {
			Data interface{}
		}
		func (r AsciiJSON) Render(w http.ResponseWriter) (err error)
		func (r AsciiJSON) WriteContentType(w http.ResponseWriter)
	
	# type Data struct {
			ContentType string
			Data        []byte
		}
		func (r Data) Render(w http.ResponseWriter) (err error)
		func (r Data) WriteContentType(w http.ResponseWriter)
	
	# type Delims struct {
			// Left delimiter, defaults to {{.
			Left string
			// Right delimiter, defaults to }}.
			Right string
		}
	
	# type HTML struct {
			Template *template.Template
			Name     string
			Data     interface{}
		}
		func (r HTML) Render(w http.ResponseWriter) error
		func (r HTML) WriteContentType(w http.ResponseWriter)
	
	# type HTMLDebug struct {
			Files   []string
			Glob    string
			Delims  Delims
			FuncMap template.FuncMap
		}
		func (r HTMLDebug) Instance(name string, data interface{}) Render
	
	# type HTMLProduction struct {
			Template *template.Template
			Delims   Delims
		}
		func (r HTMLProduction) Instance(name string, data interface{}) Render
	
	# type HTMLRender interface {
			Instance(string, interface{}) Render
		}
	
	# type IndentedJSON struct {
			Data interface{}
		}
		func (r IndentedJSON) Render(w http.ResponseWriter) error
		func (r IndentedJSON) WriteContentType(w http.ResponseWriter)
	
	# type JSON struct {
			Data interface{}
		}
		func (r JSON) Render(w http.ResponseWriter) (err error)
		func (r JSON) WriteContentType(w http.ResponseWriter)
	
	# type JsonpJSON struct {
			Callback string
			Data     interface{}
		}
		func (r JsonpJSON) Render(w http.ResponseWriter) (err error)
		func (r JsonpJSON) WriteContentType(w http.ResponseWriter)
	
	# type MsgPack struct {
			Data interface{}
		}
		func (r MsgPack) Render(w http.ResponseWriter) error
		func (r MsgPack) WriteContentType(w http.ResponseWriter)
	
	# type ProtoBuf struct {
			Data interface{}
		}
		func (r ProtoBuf) Render(w http.ResponseWriter) error
		func (r ProtoBuf) WriteContentType(w http.ResponseWriter)
	
	# type PureJSON struct {
			Data interface{}
		}
		func (r PureJSON) Render(w http.ResponseWriter) error
		func (r PureJSON) WriteContentType(w http.ResponseWriter)
	
	# type Reader struct {
			ContentType   string
			ContentLength int64
			Reader        io.Reader
			Headers       map[string]string
		}
		func (r Reader) Render(w http.ResponseWriter) (err error)
		func (r Reader) WriteContentType(w http.ResponseWriter)
	
	# type Redirect struct {
			Code     int
			Request  *http.Request
			Location string
		}
		func (r Redirect) Render(w http.ResponseWriter) error
		func (r Redirect) WriteContentType(http.ResponseWriter)
	
	# type Render interface {
			// Render writes data with custom ContentType.
			Render(http.ResponseWriter) error
			// WriteContentType writes custom ContentType.
			WriteContentType(w http.ResponseWriter)
		}
	
	# type SecureJSON struct {
			Prefix string
			Data   interface{}
		}
		func (r SecureJSON) Render(w http.ResponseWriter) error
		func (r SecureJSON) WriteContentType(w http.ResponseWriter)
	
	# type SecureJSONPrefix string

	# type String struct {
			Format string
			Data   []interface{}
		}
		func (r String) Render(w http.ResponseWriter) error
		func (r String) WriteContentType(w http.ResponseWriter)
	
	# type XML struct {
			Data interface{}
		}
		func (r XML) Render(w http.ResponseWriter) error
		func (r XML) WriteContentType(w http.ResponseWriter)
	
	# type YAML struct {
			Data interface{}
		}
		func (r YAML) Render(w http.ResponseWriter) error
		func (r YAML) WriteContentType(w http.ResponseWriter)
	
-------------------
func
-------------------
	func WriteJSON(w http.ResponseWriter, obj interface{}) error
	func WriteMsgPack(w http.ResponseWriter, obj interface{}) error
	func WriteString(w http.ResponseWriter, format string, data []interface{}) (err error)