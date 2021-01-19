---------------------
reflect
---------------------

---------------------
变量
---------------------

---------------------
type
---------------------
	# type ChanDir int
		const (
			RecvDir ChanDir             = 1 << iota // 只写通道
			SendDir                                 // 只读通道
			BothDir = RecvDir | SendDir             // 读写通道
		)

		* 通道的类型

		func (d ChanDir) String() string
	
	# type Kind uint
		const (
			Invalid Kind = iota
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
			Chan
			Func
			Interface
			Map
			Ptr
			Slice
			String
			Struct
			UnsafePointer
		)

		func (k Kind) String() string
	
	# type MapIter struct {}
		func (it *MapIter) Key() Value
		func (it *MapIter) Next() bool
		func (it *MapIter) Value() Value
	
	# type Method struct {
			Name    string
			PkgPath string

			Type  Type  // method type
			Func  Value // func with receiver as first argument
			Index int   // index for Type.Method
		}
	
	# type SelectCase struct {
			Dir  SelectDir // direction of case
			Chan Value     // channel to use (for send or receive)
			Send Value     // value to send (for send)
		}
	
	# type SelectDir int
		const (
			SelectSend    SelectDir // case Chan <- Send
			SelectRecv              // case <-Chan:
			SelectDefault           // default
		)
	
	# type SliceHeader struct {
			Data uintptr
			Len  int
			Cap  int
		}
		* 切片的地层结构

	# type StringHeader struct {
			Data uintptr
			Len  int
		}

		* 字符串的的底层结构，一个指针，一个长度
	
	# type StructField struct {
			Name string
			PkgPath string
			Type      Type      // field type
			Tag       StructTag // field tag string
			Offset    uintptr   // offset within struct, in bytes
			Index     []int     // index sequence for Type.FieldByIndex
			Anonymous bool      // is an embedded field
		}
		func (tag StructTag) Get(key string) string
		func (tag StructTag) Lookup(key string) (value string, ok bool)
	
	# type Type interface {
			Align() int
				* 返回创建改类型时，需要分配的内存大小

			FieldAlign() int
				* 该类型在结构中作为字段使用时，返回它的对齐方式
				
			Method(int) Method
				* 获取这个类型的第n个方法，如果不存在抛出异常

			MethodByName(string) (Method, bool)
				* 根据方法名称获取方法

			NumMethod() int
				* 返回该类型方法的个数

			Name() string
				* 返回实际类型的名称，会包含包名

			PkgPath() string
				* 返回类型的包路径，如果没有返回空字符串

			Size() uintptr
				* 存储该类型需要的空间大小，类似于unsafe.Sizeof

			String() string
				* 以字符串形式返回

			Kind() Kind
				* 返回类型Kind枚举

			Implements(u Type) bool
				* 是否实现了指定的类型u

			AssignableTo(u Type) bool
				* 当前类型是否可以赋值给u

			ConvertibleTo(u Type) bool
				* 是否可以转换为指定的类型u

			Comparable() bool
				* 是否是可比较的

			Bits() int
				* 以比特为单位返回类型的大小

			ChanDir() ChanDir
				* 返回一个通道类型的方向，如果当前type不是通道，会抛出异常

			IsVariadic() bool
				* 返回当前类型的函数，最后一个参数是不是可变长参数。如果当前类型不是函数，则抛出异常

			Elem() Type
				* 返回当前类型的元素类型，如果类型不是Array、Chan、Map、Ptr或Slice，抛出异常

			Field(i int) StructField
			FieldByIndex(index []int) StructField
			FieldByName(name string) (StructField, bool)
			FieldByNameFunc(match func(string) bool) (StructField, bool)
				* 返回当前类型结构体中的字段信息
				 
			In(i int) Type
				* 返回当前类型函数，指定下标参数的类型，如果当前类型不是函数，则抛出异常

			Key() Type
				* 返回当前元素类型的Key类型，如果当前元素类型不是Map，则抛出异常
				
			Len() int
				* 返回当前类型的长度，如果当前类型并不是数组，则异常

			NumField() int
				* 返回当前类型的字段数量，如果类型不是struct，则异常

			NumIn() int
			NumOut() int
				* 返回当前函数类型的形参/返回参数，数量，如果类型不是方法，则异常

			Out(i int) Type
				* 返回当前函数类型，返回值类型的第i个类型，如果类型不是方法，异常
		}
		

		func ArrayOf(count int, elem Type) Type
		func ChanOf(dir ChanDir, t Type) Type
		func FuncOf(in, out []Type, variadic bool) Type
		func MapOf(key, elem Type) Type
		func PtrTo(t Type) Type
		func SliceOf(t Type) Type
		func StructOf(fields []StructField) Type
		func TypeOf(i interface{}) Type
	
	# type Value struct {}
		func Append(s Value, x ...Value) Value
		func AppendSlice(s, t Value) Value
		func Indirect(v Value) Value
		func MakeChan(typ Type, buffer int) Value
		func MakeFunc(typ Type, fn func(args []Value) (results []Value)) Value
		func MakeMap(typ Type) Value
		func MakeMapWithSize(typ Type, n int) Value
		func MakeSlice(typ Type, len, cap int) Value
		func New(typ Type) Value
		func NewAt(typ Type, p unsafe.Pointer) Value
		func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool)
		func ValueOf(i interface{}) Value
		func Zero(typ Type) Value

		func (v Value) Addr() Value
		func (v Value) Bool() bool
		func (v Value) Bytes() []byte
		func (v Value) Call(in []Value) []Value
		func (v Value) CallSlice(in []Value) []Value
		func (v Value) CanAddr() bool
		func (v Value) CanInterface() bool
		func (v Value) CanSet() bool
		func (v Value) Cap() int
		func (v Value) Close()
		func (v Value) Complex() complex128
		func (v Value) Convert(t Type) Value
		func (v Value) Elem() Value
		func (v Value) Field(i int) Value
		func (v Value) FieldByIndex(index []int) Value
		func (v Value) FieldByName(name string) Value
		func (v Value) FieldByNameFunc(match func(string) bool) Value
		func (v Value) Float() float64
		func (v Value) Index(i int) Value
		func (v Value) Int() int64
		func (v Value) Interface() (i interface{})
		func (v Value) InterfaceData() [2]uintptr
		func (v Value) IsNil() bool
		func (v Value) IsValid() bool
		func (v Value) IsZero() bool
		func (v Value) Kind() Kind
		func (v Value) Len() int
		func (v Value) MapIndex(key Value) Value
		func (v Value) MapKeys() []Value
		func (v Value) MapRange() *MapIter
		func (v Value) Method(i int) Value
		func (v Value) MethodByName(name string) Value
		func (v Value) NumField() int
		func (v Value) NumMethod() int
		func (v Value) OverflowComplex(x complex128) bool
		func (v Value) OverflowFloat(x float64) bool
		func (v Value) OverflowInt(x int64) bool
		func (v Value) OverflowUint(x uint64) bool
		func (v Value) Pointer() uintptr
		func (v Value) Recv() (x Value, ok bool)
		func (v Value) Send(x Value)
		func (v Value) Set(x Value)
		func (v Value) SetBool(x bool)
		func (v Value) SetBytes(x []byte)
		func (v Value) SetCap(n int)
		func (v Value) SetComplex(x complex128)
		func (v Value) SetFloat(x float64)
		func (v Value) SetInt(x int64)
		func (v Value) SetLen(n int)
		func (v Value) SetMapIndex(key, elem Value)
		func (v Value) SetPointer(x unsafe.Pointer)
		func (v Value) SetString(x string)
		func (v Value) SetUint(x uint64)
		func (v Value) Slice(i, j int) Value
		func (v Value) Slice3(i, j, k int) Value
		func (v Value) String() string
		func (v Value) TryRecv() (x Value, ok bool)
		func (v Value) TrySend(x Value) bool
		func (v Value) Type() Type
		func (v Value) Uint() uint64
		func (v Value) UnsafeAddr() uintptr
	
	# type ValueError struct {
			Method string
			Kind   Kind
		}
		func (e *ValueError) Error() string
	

---------------------
func
---------------------
	func Copy(dst, src Value) int
	func DeepEqual(x, y interface{}) bool
	func Swapper(slice interface{}) func(i, j int)