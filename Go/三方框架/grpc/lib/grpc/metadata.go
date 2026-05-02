-------------------------
元数据
-------------------------


-------------------------
var
-------------------------


-------------------------
type
-------------------------
	# type MD map[string][]string

		func FromIncomingContext(ctx context.Context) (MD, bool)
		func FromOutgoingContext(ctx context.Context) (MD, bool)
			* 根 In 或 Out 获取到全局上下文，Key 分别是：
				type mdIncomingKey struct{}
				type mdOutgoingKey struct{}】
			* 返回的结果是复制的

		func Join(mds ...MD) MD
		func New(m map[string]string) MD
		func Pairs(kv ...string) MD
		func (md MD) Append(k string, vals ...string)
		func (md MD) Copy() MD
		func (md MD) Delete(k string)
		func (md MD) Get(k string) []string
		func (md MD) Len() int
		func (md MD) Set(k string, vals ...string)

-------------------------
func
-------------------------

	func AppendToOutgoingContext(ctx context.Context, kv ...string) context.Context
	func DecodeKeyValue(k, v string) (string, string, error)deprecated
	func NewIncomingContext(ctx context.Context, md MD) context.Context
	func NewOutgoingContext(ctx context.Context, md MD) context.Context
	func ValueFromIncomingContext(ctx context.Context, key string) []string
