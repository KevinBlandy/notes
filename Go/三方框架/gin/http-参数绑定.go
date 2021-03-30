-----------------------
绑定
-----------------------
	# Gin目前支持JSON、XML、YAML和标准表单值的绑定

	# 绑定接口的定义
		type Binding interface {
			Name() string
				* 名称
			Bind(*http.Request, interface{}) error
				* 绑定request
		}
	
		type BindingBody interface {
			Binding
			BindBody([]byte, interface{}) error
				* 绑定body
		}
		type BindingUri interface {
			Name() string
			BindUri(map[string][]string, interface{}) error
				* 绑定uri
		}
	
	# 预定义的ContentType
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

	# 预定义的绑定接口实现
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

	# 相关的方法
		func Default(method, contentType string) Binding
			* 根据请求方法和contentType获取Binding实现
			* 如果是GET或者未匹配到ContentType则返回 Form


	# MustBind
		func (c *Context) Bind(obj interface{}) error 
			* 根据contentType来选择绑定器
		
		func (c *Context) BindHeader(obj interface{}) error
		func (c *Context) BindJSON(obj interface{}) error
		func (c *Context) BindQuery(obj interface{}) error
		func (c *Context) BindUri(obj interface{}) error
		func (c *Context) BindXML(obj interface{}) error
		func (c *Context) BindYAML(obj interface{}) error

		func (c *Context) BindWith(obj interface{}, b binding.Binding) error
			* 使用指定的绑定器

		* 这些方法属于 MustBindWith 的具体调用
			func (c *Context) MustBindWith(obj interface{}, b binding.Binding) error
		
		* 如果发生绑定错误，则请求终止，并触发 c.AbortWithError(400, err).SetType(ErrorTypeBind)。
		* 响应状态码被设置为 400 并且 Content-Type 被设置为 text/plain; charset=utf-8。
		* 如果在此之后尝试设置响应状态码，Gin会输出日志 
			[GIN-debug] [WARNING] Headers were already written. Wanted to override status code 400 with 422。 
		* 如果您希望更好地控制绑定，考虑使用 ShouldBind 等效方法
	
	# ShouldBindWith
		func (c *Context) ShouldBind(obj interface{}) error
			* 根据contentType来选择绑定器

		func (c *Context) ShouldBindHeader(obj interface{}) error
		func (c *Context) ShouldBindJSON(obj interface{}) error
		func (c *Context) ShouldBindQuery(obj interface{}) error
		func (c *Context) ShouldBindUri(obj interface{}) error
		func (c *Context) ShouldBindXML(obj interface{}) error
		func (c *Context) ShouldBindYAML(obj interface{}) error

		* 这些方法属于 ShouldBindWith 的具体调用。 
			func (c *Context) ShouldBindWith(obj interface{}, b binding.Binding) error
		
		* 如果发生绑定错误，Gin 会返回错误并由开发者处理错误和请求。


		
		func (c *Context) ShouldBindBodyWith(obj interface{}, bb binding.BindingBody) (err error)
			* 尝试把请求体绑定到obj，bb指定body的类型，返回err表示是否成功
			* 这个牛逼之处在于一个Body可以进行多次绑定
			* 会在绑定之前将 body 存储到上下文中， 这会对性能造成轻微影响，如果调用一次就能完成绑定的话，那就不要用这个方法。

-----------------------
验证
-----------------------
	# Gin使用 go-playground/validator.v8 进行验证
		https://github.com/go-playground/validator
		https://pkg.go.dev/gopkg.in/go-playground/validator.v8



-----------------------
一些demo
-----------------------
	import (
		"github.com/gin-gonic/gin"
		"log"
		"net/http"
		"streamer/validate"
	)

	type LoginRequest struct {
		Account string `json:"account" form:"account" binding:"required,gte=6,lte=50"`
		Password string `json:"password" form:"password" binding:"required,gte=6,lte=50"`
	}

	func Login (c *gin.Context) {
		var body = new(LoginRequest)
		if err := c.ShouldBind(body); err != nil {
			c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "bad body"})
			return
		}
		log.Println(validate.Validator.Struct(body))

		c.JSON(http.StatusOK, gin.H{"msg": "ok"})
	}
