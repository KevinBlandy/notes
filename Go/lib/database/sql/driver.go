----------------
driver
----------------
	# 驱动包，也就是sql接口的实现，普通玩家不用管其实

----------------
var
----------------
	var Bool boolType
	var DefaultParameterConverter defaultConverter
	var ErrBadConn = errors.New("driver: bad connection")
	var ErrRemoveArgument = errors.New("driver: remove argument from query")
	var ErrSkip = errors.New("driver: skip fast-path; continue as if unimplemented")
	var Int32 int32Type
	var ResultNoRows noRows
	var String stringType

----------------
type
----------------
	# type Value interface{}
		* value表示数据库的值，类型可以是:
			int64
			float64
			bool
			[]byte
			string
			time.Time

	# type Valuer interface {
			// Value returns a driver Value.
			// Value must not panic.
			Value() (Value, error)
		}

		* Value 实现方法的对象，最好不要用指针接受
		* 用指针接收的话，在SQL执行时，占位参数值不是指针，则会异常（会认为是未实现 Valuer 接口）
		* 用值接收的话，会默认带有指针方法，也就是说，此时占位参数不管是指针还是值类型都可以被成功的映射

----------------
func
----------------
	func IsScanValue(v interface{}) bool
	func IsValue(v interface{}) bool

