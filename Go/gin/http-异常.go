--------------
异常处理
--------------
	# 相关接口
		* 异常信息集合，是个私有type，提供了公共的方法
			type errorMsgs []*Error
			
			func (a errorMsgs) ByType(typ ErrorType) errorMsgs
			func (a errorMsgs) Last() *Error 
			func (a errorMsgs) Errors() []string
			func (a errorMsgs) JSON() interface{} 
			func (a errorMsgs) MarshalJSON() ([]byte, error)
			func (a errorMsgs) String() string

		* 异常信息
			type Error struct {
				Err  error
				Type ErrorType
				Meta interface{}
			}

			func (msg Error) Error() string
			func (msg *Error) IsType(flags ErrorType) bool
			func (msg *Error) JSON() interface{}
			func (msg *Error) MarshalJSON() ([]byte, error)
			func (msg *Error) SetMeta(data interface{}) *Error
			func (msg *Error) SetType(flags ErrorType) *Error
		
		* 预定义类型的类型
			type ErrorType uint64
			const (
				ErrorTypeBind ErrorType = 1 << 63			// Context.Bind()失败
				ErrorTypeRender ErrorType = 1 << 62			// Context.Render() 失败
				ErrorTypePrivate ErrorType = 1 << 0			// 私有错误?
				ErrorTypePublic ErrorType = 1 << 1			// 公共错误?
				ErrorTypeAny ErrorType = 1<<64 - 1			// 表示任何其他错误
				ErrorTypeNu = 2								// 表示任何其他错误
			)

	# 预定义的处理方法
		func ErrorLogger() HandlerFunc
			* 任意异常处理器，会把异常解析为JSON响应给客户端
				return ErrorLoggerT(ErrorTypeAny)
		func ErrorLoggerT(typ ErrorType) HandlerFunc
			* 仅仅处理指定异常