
import (
	"fmt"
	"net/http"
)

// Code 业务状态码
type Code struct {
	Code string
	HttpStatus int
	Message string
}

// SetMessage 复制当前Code，设置新的消息，一般用于自定义消息内容
func (c Code) SetMessage (message string) *Code {
	return &Code{
		Code:       c.Code,
		HttpStatus: c.HttpStatus,
		Message:    message,
	}
}

func (c Code) String () string {
	return c.Code
}

// MarshalJSON 序列化为字符串
func (c Code) MarshalJSON() ([]byte, error) {
	return []byte(fmt.Sprintf(`"%s"`, c.Code)), nil
}

func (c *Code) UnmarshalJSON(data []byte) error {
	// Response 一般不需要序列化
	//if len(data) == 0 {
	//	return nil
	//}
	//val := string(data)
	//switch{
	//	case strings.EqualFold(val, "ok"): *c = *CodeOk
	//}
	return nil
}


// 预定义系统中的业务状态码
var (
	// CodeOk 正常
	CodeOk = &Code{Code: "OK", HttpStatus: http.StatusOK, Message: "ok"}

	//CodeBadRequest 通用的客户端异常
	CodeBadRequest = &Code{Code: "BadRequest",HttpStatus: http.StatusBadRequest, Message: "非法请求"}

	// CodeNotFound 资源未找到
	CodeNotFound = &Code{Code: "NotFound",HttpStatus: http.StatusNotFound, Message: "请求资源不存在"}

	// CodeUnauthorized 资源未找到
	CodeUnauthorized = &Code{Code: "Unauthorized",HttpStatus: http.StatusUnauthorized, Message: "未授权"}

	// CodeForbidden 资源未找到
	CodeForbidden = &Code{Code: "Forbidden",HttpStatus: http.StatusForbidden, Message: "无权操作"}

	// CodeRequestEntityTooLarge 请求体大小超出限制
	CodeRequestEntityTooLarge = &Code{Code: "RequestEntityTooLarge",HttpStatus: http.StatusRequestEntityTooLarge, Message: "请求体大小超出限制"}

	// CodeServiceUnavailable 服务不可用
	CodeServiceUnavailable = &Code{Code: "ServiceUnavailable", HttpStatus: http.StatusServiceUnavailable, Message: "服务不可用"}

	// CodeServerError 服务器异常
	CodeServerError = &Code{Code: "ServerError",HttpStatus: http.StatusInternalServerError,  Message: "服务器异常"}
)

// Response 响应客户端的通用JSON
type Response struct {
	Success bool		`json:"success"`
	Data interface{}	`json:"data"`
	Code *Code			`json:"code"`
	Message string		`json:"message"`
}

var (
	// EmptyOkResponse 空响应
	EmptyOkResponse = OkResponse(nil)
)

// OkResponse 成功响应
func OkResponse (data interface{}) *Response {
	return &Response{
		Success: true,
		Code:    CodeOk,
		Data:    data,
		Message: CodeOk.Message,
	}
}
// FailResponse 异常响应
func FailResponse (code *Code) *Response {
	return &Response{
		Success: false,
		Code:    code,
		Data:    nil,
		Message: code.Message,
	}
}


// ------------------- 异常

// ServiceError 业务异常
type ServiceError struct {
	Message string			// 异常消息
	Cause error				// 捕获的异常信息
	HttpStatus int			// http状态码
	Response interface{}		// 响应客户端的数据
}
func (s ServiceError) Error () string {
	return s.Message
}

// SetCause 设置Catch的异常
func (s ServiceError) SetCause(err error) *ServiceError {
	s.Cause = err
	return &s
}

// NewServiceError 创建新的业务异常，指定原始异常，状态码，消息
func NewServiceError (code *Code) *ServiceError {
	return &ServiceError{
		Message: code.Message,
		Cause: nil,
		HttpStatus: code.HttpStatus,
		Response: &Response{
			Success: false,
			Code:    code,
			Message: code.Message,
		},
	}
}