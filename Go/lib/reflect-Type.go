----------------------
变量
----------------------
		
	

----------------------
type
----------------------
	# type Type interface
		Align() int
		FieldAlign() int
		Method(int) Method
			* 获取指定的方法

		MethodByName(string) (Method, bool)
			* 根据名称获取方法

		NumMethod() int
			* 方法的数量

		Name() string
			* 获取类型的名称

		PkgPath() string
		Size() uintptr
		String() string
		Kind() Kind
		Implements(u Type) bool
		AssignableTo(u Type) bool
		ConvertibleTo(u Type) bool
		Comparable() bool
		Bits() int
		ChanDir() ChanDir
		IsVariadic() bool
		Elem() Type
		Field(i int) StructField
		FieldByIndex(index []int) StructField
		FieldByName(name string) (StructField, bool)
		FieldByNameFunc(match func(string) bool) (StructField, bool)
			* 根据下标，获取到指定的结构体字段

		In(i int) Type
		Key() Type
		Len() int
		NumField() int
			* 字段的数量

		NumIn() int
		NumOut() int
		Out(i int) Type
	
	# type Method struct
		type Method struct {
			Name    string
			PkgPath string

			Type  Type
			Func  Value
			Index int 
		}

		* 方法对象
	
	
	# type Kind uint
		* 数据类型标识
		* 预定义了的变量
			const (
				Invalid Kind = iota	空值
				Bool
				Int
				Int8
				Int16
				Int32
				Int64
				Uint
				Uint8
				Uint16
				Uint32
				Uint64
				Uintptr
				Float32
				Float64
				Complex64
				Complex128
				Array
				Chan				Channel
				Func				函数
				Interface			接口
				Map
				Ptr
				Slice
				String
				Struct
				UnsafePointer
			)

		func (k Kind) String() string

	# type ChanDir int
		* 标识通道类型的方向

	# type StructField struct 
		PkgPath string
		Type      Type			字段类型
		Tag       StructTag		字段上面的tag
		Offset    uintptr 
		Index     []int			
		Anonymous bool			是否是匿名的

		* 结构体字段
	
	# type StructTag string
		* 结构体上的标识

		func (tag StructTag) Get(key string) string 
		func (tag StructTag) Lookup(key string) (value string, ok bool)





----------------------
方法
----------------------
	func DeepEqual(x, y interface{}) bool
	func MakeFunc(typ Type, fn func(args []Value) (results []Value)) Value 
	func Swapper(slice interface{}) func(i, j int)

	func PtrTo(t Type) Type 

	func TypeOf(i interface{}) Type
		* 返回指定数据的Type，它返回的是实际的类型
			var w, _ = os.Open("C:\\temp.txt")
			var t = reflect.TypeOf(w) // *os.File


	func ChanOf(dir ChanDir, t Type) Type
	func MapOf(key, elem Type) Type
	func FuncOf(in, out []Type, variadic bool) Type
	func SliceOf(t Type) Type
	func StructOf(fields []StructField) Type
	func ArrayOf(count int, elem Type) Type

