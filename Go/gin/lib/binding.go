
------------------------
binding
------------------------


------------------------
var
------------------------
	const (
		MIMEJSON              = "application/json"
		MIMEHTML              = "text/html"
		MIMEXML               = "application/xml"
		MIMEXML2              = "text/xml"
		MIMEPlain             = "text/plain"
		MIMEPOSTForm          = "application/x-www-form-urlencoded"
		MIMEMultipartPOSTForm = "multipart/form-data"
		MIMEPROTOBUF          = "application/x-protobuf"
		MIMEMSGPACK           = "application/x-msgpack"
		MIMEMSGPACK2          = "application/msgpack"
		MIMEYAML              = "application/x-yaml"
	)

	var (
		JSON          = jsonBinding{}
		XML           = xmlBinding{}
		Form          = formBinding{}
		Query         = queryBinding{}
		FormPost      = formPostBinding{}
		FormMultipart = formMultipartBinding{}
		ProtoBuf      = protobufBinding{}
		MsgPack       = msgpackBinding{}
		YAML          = yamlBinding{}
		Uri           = uriBinding{}
		Header        = headerBinding{}
	)

	var EnableDecoderDisallowUnknownFields = false
	var EnableDecoderUseNumber = false

	var Validator StructValidator = &defaultValidator{}


------------------------
type
------------------------
	# type Binding interface {
			Name() string
			Bind(*http.Request, interface{}) error
		}
		func Default(method, contentType string) Binding
	
	# type BindingBody interface {
			Binding
			BindBody([]byte, interface{}) error
		}
	
	# type BindingUri interface {
			Name() string
			BindUri(map[string][]string, interface{}) error
		}
	
	# type StructValidator interface {
			ValidateStruct(interface{}) error
			Engine() interface{}
		}
		
		
	
------------------------
func
------------------------
