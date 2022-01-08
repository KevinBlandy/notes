---------------------
List
---------------------
	# 变量
		var (
			// ErrListNotFound is returned when the list not found.
			ErrListNotFound = errors.New("the list not found")

			// ErrIndexOutOfRange is returned when use LSet function set index out of range.
			ErrIndexOutOfRange = errors.New("index out of range")

			//ErrCount is returned when count is error.
			ErrCount = errors.New("err count")
		)

	# type
		func New() *List
		func (l *List) LPeek(key string) (item []byte, err error)
		func (l *List) LPop(key string) (item []byte, err error)
		func (l *List) LPush(key string, values ...[]byte) (size int, err error)
		func (l *List) LRange(key string, start, end int) (list [][]byte, err error)
		func (l *List) LRem(key string, count int, value []byte) (int, error)
		func (l *List) LRemNum(key string, count int, value []byte) (int, error)
		func (l *List) LSet(key string, index int, value []byte) error
		func (l *List) Ltrim(key string, start, end int) error
		func (l *List) RPeek(key string) (item []byte, size int, err error)
		func (l *List) RPop(key string) (item []byte, err error)
		func (l *List) RPush(key string, values ...[]byte) (size int, err error)
		func (l *List) Size(key string) (int, error)