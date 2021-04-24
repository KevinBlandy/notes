--------------------------------
业务异常与响应body的设计
--------------------------------

import (
	"encoding/json"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
	"os"
)


// Status 业务状态
type Status struct {
	HttpStatus int			// HttpStatus http状态码
	Code string				// Code 业务状态码
	Message string			// Message 默认提示消息
}

// 预定义业务状态
var(
	CodeOk = &Status{HttpStatus: http.StatusOK, Code: "ok", Message: "ok"}
	CodeBadRequest = &Status{HttpStatus: http.StatusBadRequest, Code: "BadRequest", Message: "非法请求"}
	CodeServerError = &Status{HttpStatus: http.StatusInternalServerError, Code: "ServerError", Message: "服务器异常"}
)


// ResponseBody 响应json格式
type ResponseBody struct {
	Success bool		`json:"success,omitempty"`
	Code string			`json:"code,omitempty"`
	Data interface{}	`json:"data,omitempty"`
	Message string		`json:"message,omitempty"`
}
var (
	BodyOk = SuccessBody(nil)
)

// SuccessBody 获取成功响应体
func SuccessBody (data interface{}) *ResponseBody {
	return &ResponseBody{
		Success: true,
		Code:    CodeOk.Code,
		Data:    data,
		Message: CodeOk.Message,
	}
}
// FailBody 获取异常响应体
func FailBody (status Status) *ResponseBody {
	return &ResponseBody{
		Success: false,
		Code:    status.Code,
		Data:    nil,
		Message: status.Message,
	}
}
// FailBody1 获取异常响应体，自定义body
func FailBody1 (status Status, message string) *ResponseBody {
	status.Message = message
	return FailBody(status)
}

// ServiceError 业务异常
type ServiceError struct {
	Message string			// 异常消息
	Cause error				// 捕获的异常信息
	HttpStatus int			// http状态码
	ResponseBody interface{}	// 响应客户端的数据
}
func (s ServiceError) Error () string {
	return s.Message
}

// NewServiceError 创建新的业务异常，指定原始异常，状态码，消息
func NewServiceError (cause error, status Status, message string) *ServiceError {
	if message == "" {
		message = status.Message  // 默认使用状态码的默认提示消息
	}
	return &ServiceError {
		Message: message,
		Cause: cause,
		HttpStatus: status.HttpStatus,
		ResponseBody: ResponseBody {
			Success: false,
			Code:    status.Code,
			Message: message,
		},
	}
}
// NewServiceError1 创建新的业务异常，指定状态码
func NewServiceError1(status Status) *ServiceError {
	return NewServiceError(nil, status, "")
}
// NewServiceError2 创建先的业务异常，指定状态码，自定义异常信息
func NewServiceError2(status Status, message string) *ServiceError {
	return NewServiceError(nil, status, message)
}
// NewServiceError3 创建新的业务异常，指定原始异常，状态码
func NewServiceError3(cause error, status Status) *ServiceError {
	return NewServiceError(cause, status, "")
}

// FailResponse 响应业务异常信息，err 不能为空
func failResponse (ctx *gin.Context, err *ServiceError){
	log.Printf("业务异常：message=%s\n", err.Error())
	if err.Cause != nil {
		log.Printf("捕获异常：message=%s\n", err.Cause.Error())
	}

	var statusCode = err.HttpStatus
	var responseBody []byte

	if err.ResponseBody != nil {
		if jsonBytes, err := json.Marshal(err.ResponseBody); err != nil {
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
			log.Printf("系统异常：%s\n", err.Error())

			var HttpStatus = http.StatusInternalServerError

			// 详细的异常信息判断
			if os.IsNotExist(err) {			// 文件未找到
				HttpStatus = http.StatusNotFound
			} else if os.IsPermission(err) {		// 权限不足
				HttpStatus = http.StatusForbidden
			}

			ctx.Status(HttpStatus)
		}
	}
}
