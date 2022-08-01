----------------
driver
----------------
	# ��������Ҳ����sql�ӿڵ�ʵ�֣���ͨ��Ҳ��ù���ʵ

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
		* value��ʾ���ݿ��ֵ�����Ϳ�����:
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

		* Value ʵ�ַ����Ķ�����ò�Ҫ��ָ�����
		* ��ָ����յĻ�����SQLִ��ʱ��ռλ����ֵ����ָ�룬����쳣������Ϊ��δʵ�� Valuer �ӿڣ�
		* ��ֵ���յĻ�����Ĭ�ϴ���ָ�뷽����Ҳ����˵����ʱռλ����������ָ�뻹��ֵ���Ͷ����Ա��ɹ���ӳ��

----------------
func
----------------
	func IsScanValue(v interface{}) bool
	func IsValue(v interface{}) bool

