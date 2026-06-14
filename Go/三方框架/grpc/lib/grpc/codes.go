----------------------
codes
----------------------

----------------------
var
----------------------

----------------------
type
----------------------
	# type Code uint32
		* 状态码
		
		func (c Code) String() string
		func (c *Code) UnmarshalJSON(b []byte) error

		const (
			OK Code = 0
			Canceled Code = 1
			Unknown Code = 2
			InvalidArgument Code = 3
			DeadlineExceeded Code = 4
			NotFound Code = 5
			AlreadyExists Code = 6
			PermissionDenied Code = 7
			ResourceExhausted Code = 8
			FailedPrecondition Code = 9
			Aborted Code = 10
			OutOfRange Code = 11
			Unimplemented Code = 12
			Internal Code = 13
			Unavailable Code = 14
			DataLoss Code = 15
			Unauthenticated Code = 16
		)

----------------------
func
----------------------
