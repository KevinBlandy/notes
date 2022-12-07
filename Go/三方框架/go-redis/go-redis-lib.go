----------------
go-redis
----------------

----------------
var
----------------
	const KeepTTL = -1
	const Nil = proto.Nil
	const TxFailedErr = proto.RedisError("redis: transaction failed")
	var ErrClosed = pool.ErrClosed

----------------
type
----------------

----------------
func
----------------
	func HasErrorPrefix(err error, prefix string) bool
	func NewDialer(opt *Options) func(context.Context, string, string) (net.Conn, error)
	func SetLogger(logger internal.Logging)
	func Version() string
