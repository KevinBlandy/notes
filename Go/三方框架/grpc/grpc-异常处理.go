-----------------------
异常处理
-----------------------
	# 靠的是状态码和状态机制
		* 状态码表示不同的状态。
		* 状态描述了某个状态码的具体细节，可以有更多的信息，并且可以转换为 error 形式。
		* 同时状态可以从一个 error 中进行恢复。

	# 状态码
		type Code uint32
		const (
			OK Code = 0
				* 正常
			Canceled Code = 1
				* 已取消
			Unknown Code = 2
				* 未知
			InvalidArgument Code = 3
				* 参数错误
			DeadlineExceeded Code = 4
				* 超时
			NotFound Code = 5
				* 没找到
			AlreadyExists Code = 6
				* 已存在
			PermissionDenied Code = 7
				* 无权限
			ResourceExhausted Code = 8
				* 协议错误 流量控制资源已达限
			FailedPrecondition Code = 9
			Aborted Code = 10
			OutOfRange Code = 11
			Unimplemented Code = 12
				* 协议错误 客户端使用的压缩机制服务器不支持。
			Internal Code = 13
				* 协议错误 无法解压缩，但压缩算法受支持
				* 流量控制协议违规
				* 解析响应协议缓冲区时出错
				* 解析请求协议缓冲区时出错
			Unavailable Code = 14
			DataLoss Code = 15
			Unauthenticated Code = 16
				* 未认证
		)
	
	# 状态，通过 status 包下的 Status 来进行描述

		type Status = status.Status

		func Convert(err error) *Status
			* 把异常信息解析为状态，FromError 便捷方法，忽略了 bool 值
				s, _ := FromError(err)

		func FromContextError(err error) *Status
			* 把 Context 异常解析为状态。
			* 如果 err 为空，返回 New(codes.Unknown, err.Error())
			* 如果是 context.DeadlineExceeded 返回 New(codes.DeadlineExceeded, err.Error())
			* 如果是 context.Canceled 返回 New(codes.Canceled, err.Error())
			* 都不是，则返回 New(codes.Unknown, err.Error())

		func FromError(err error) (s *Status, ok bool)
			* FromError 返回 err 的 Status 表示形式。
			* 如果 err 不为空，但是不是 Status，则返回 New(codes.Unknown, err.Error())
			* 如果 err 为空，返回 nil， ok 为 true
			* 如果 err 实现了 GRPCStatus() *Status 接口，就会调用接口方法进行返回

		func FromProto(s *spb.Status) *Status

		func New(c codes.Code, msg string) *Status
		func Newf(c codes.Code, format string, a ...any) *Status
			* 构建新的状态

		func Code(err error) codes.Code
			* 从错误中解析出状态码，常用于逻辑判断
				if status.Code(err) != codes.InvalidArgument {
					log.Printf("Received unexpected error: %v", err)
					continue
				}
	
		func (s *Status) Code() codes.Code
			* 返回状态码，如果 Status 为空会返回 OK

		func (s *Status) Details() []any
			* 获取状态中的异常信息，如果 Status 为空会返回 nil

		func (s *Status) Err() error
			* 返回状态表示的异常，如果 status 为空或状态是 OK 则返回 nil

		func (s *Status) Message() string
			* 返回提示消息，如果 status 为空返回空字符串

		func (s *Status) Proto() *spb.Status
		func (s *Status) String() string
		func (s *Status) WithDetails(details ...protoadapt.MessageV1) (*Status, error)
			* 设置状态详情，如果状态是 OK，则会返回异常。
				// 构建异常状态
				st := status.New(codes.ResourceExhausted, "Request limit exceeded.")
				// 设置异常详情
				ds, err := st.WithDetails(
					&epb.QuotaFailure{
						Violations: []*epb.QuotaFailure_Violation{{
							Subject:     fmt.Sprintf("name:%s", in.Name),
							Description: "Limit one greeting per person",
						}},
					},
				)
				// 设置详情异常
				if err != nil {
					return nil, st.Err()
				}
				// 返回异常信息
				return nil, ds.Err()
		
		func Code(err error) codes.Code
			* 从 err 中解析出状态码，如果为空则返回 codes.OK，反之则调用 Convert(err).Code() 进行返回

		func Error(c codes.Code, msg string) error
		func ErrorProto(s *spb.Status) error
		func Errorf(c codes.Code, format string, a ...any) error
			* 创建异常状态的便捷方法
			* 本质上这三个方法底层分别调用 New/Newf/FromProto 先构造 Status
			* 然后调用 Err() 返回其异常信息
		
