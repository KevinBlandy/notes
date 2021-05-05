import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"os"
)


// Code 业务状态
type Code struct {
	HttpStatus int			// HttpStatus http状态码
	Code string				// Code 业务状态码
	Message string			// Message 默认提示消息
}

// 预定义业务状态
var(
	//CodeOk 成功
	CodeOk = &Code{HttpStatus: http.StatusOK, Code: "ok", Message: "Ok"}
	//CodeBadRequest 通用的客户端异常
	CodeBadRequest = &Code{HttpStatus: http.StatusBadRequest, Code: "BadRequest", Message: "非法请求"}
	// CodeServiceUnavailable 服务不可用
	CodeServiceUnavailable = &Code{HttpStatus: http.StatusServiceUnavailable, Code: "ServiceUnavailable", Message: "服务不可用"}
	// CodeServerError 服务器异常
	CodeServerError = &Code{HttpStatus: http.StatusInternalServerError, Code: "ServerError", Message: "服务器异常"}
)


// Body 响应json格式
type Body struct {
	Success bool		`json:"success"`
	Code string			`json:"code"`
	Data interface{}	`json:"data"`
	Message string		`json:"message"`
}

// Success 获取成功响应体
func Success (data interface{}) *Body {
	return &Body{
		Success: true,
		Code:    CodeOk.Code,
		Data:    data,
		Message: CodeOk.Message,
	}
}
// Fail 获取异常响应体
func Fail (code *Code) *Body {
	return &Body{
		Success: false,
		Code:    code.Code,
		Data:    nil,
		Message: code.Message,
	}
}
// Fail1 获取异常响应体，自定义提示消息
func Fail1 (code *Code, message string) *Body {
	code.Message = message
	return Fail(code)
}

// ServiceError 业务异常
type ServiceError struct {
	Message string			// 异常消息
	Cause error				// 捕获的异常信息
	HttpStatus int			// http状态码
	Body interface{}	// 响应客户端的数据
}
func (s ServiceError) Error () string {
	return s.Message
}

// NewServiceError 创建新的业务异常，指定原始异常，状态码，消息
func NewServiceError (cause error, code *Code, message string) *ServiceError {
	if message == "" {
		message = code.Message  // 默认使用状态码的默认提示消息
	}
	return &ServiceError{
		Message: message,
		Cause: cause,
		HttpStatus: code.HttpStatus,
		Body: &Body{
			Success: false,
			Code:    code.Code,
			Message: message,
		},
	}
}
// NewServiceError1 创建新的业务异常，指定状态码
func NewServiceError1(code *Code) *ServiceError {
	return NewServiceError(nil, code, "")
}
// NewServiceError2 创建先的业务异常，指定状态码，自定义异常信息
func NewServiceError2(code *Code, message string) *ServiceError {
	return NewServiceError(nil, code, message)
}
// NewServiceError3 创建新的业务异常，指定原始异常，状态码
func NewServiceError3(cause error, code *Code) *ServiceError {
	return NewServiceError(cause, code, "")
}

// FailResponse 响应业务异常信息，err 不能为空
func failResponse (ctx *gin.Context, err *ServiceError){
	log.Printf("业务异常：%s\n", err.Error())
	if err.Cause != nil {
		log.Printf("捕获异常：%s\n", err.Cause.Error())
	}

	var statusCode = err.HttpStatus
	var responseBody []byte

	if err.Body != nil {
		if jsonBytes, err := json.Marshal(err.Body); err != nil {
			log.Printf("JSON编码异常：%s\n", err.Error())
			statusCode = http.StatusInternalServerError
		} else {
			responseBody = jsonBytes
		}
	}

	ctx.Status(statusCode)
	// 响应Body
	if responseBody != nil {
		ctx.Header("Content-Type", "application/json; charset=utf-8")
		if _ ,err := ctx.Writer.Write(responseBody); err != nil {
			log.Printf("响应JSON信息异常：%s\n", err.Error())
		}
	}
}

// ErrorResponse 响应未知异常，err如果为空，则不会做任何处理
func ErrorResponse (ctx *gin.Context, err error) {
	if err == nil {
		return
	}
	switch rawError := err.(type) {
		case ServiceError, *ServiceError:{
			if err, ok := rawError.(*ServiceError) ; ok {
				failResponse(ctx, err)
			}  else if err, ok := rawError.(ServiceError) ; ok {
				failResponse(ctx, &err)
			}
		}
		default: {
			// 其他异常，仅仅日志输出异常信息
			log.Printf("异常：%s\n", err.Error())

			var httpStatus = http.StatusInternalServerError

			// 详细的异常信息判断
			if os.IsNotExist(err) {			// 文件未找到
				httpStatus = http.StatusNotFound
			} else if os.IsPermission(err) {		// 权限不足
				httpStatus = http.StatusForbidden
			} else if err.Error() == "http: request body too large" {		// 消息体过大
				httpStatus = http.StatusRequestEntityTooLarge
			}

			ctx.Status(httpStatus)
		}
	}
}

