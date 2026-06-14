----------------------------
status
----------------------------
	# 状态码

----------------------------
var
----------------------------

----------------------------
type
----------------------------
	# type Status = status.Status

		* RPC 状态描述，底层是 internal/status 包下的 Status

		func Convert(err error) *Status
			* 转换异常为 Status
		func FromContextError(err error) *Status
		func FromError(err error) (s *Status, ok bool)
			* FromError 返回 err 的 Status 表示形式。
		func FromProto(s *spb.Status) *Status
		func New(c codes.Code, msg string) *Status
		func Newf(c codes.Code, format string, a ...any) *Status

		func (s *Status) Code() codes.Code
		func (s *Status) Details() []any
		func (s *Status) Err() error
		func (s *Status) Message() string
		func (s *Status) Proto() *spb.Status
		func (s *Status) String() string
		func (s *Status) WithDetails(details ...protoadapt.MessageV1) (*Status, error)

----------------------------
func
----------------------------

	func Code(err error) codes.Code
		* 从错误中解析出状态码

	func Error(c codes.Code, msg string) error
	func ErrorProto(s *spb.Status) error
	func Errorf(c codes.Code, format string, a ...any) error
		* 构建错误，指定状态码和 fmt 提示消息