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
				* 类型
			Tag       StructTag // field tag string
				* tag，类似于Java中的注解
			
			Offset    uintptr   // offset within struct, in bytes
			Index     []int     // index sequence for Type.FieldByIndex
			Anonymous bool      // is an embedded field
		}
		
		* 结构体的字段

		func (tag StructTag) Get(key string) string
			* 获取注解，如果不存在返回 ""
				v, _ := tag.Lookup(key)
				return v
			
		func (tag StructTag) Lookup(key string) (value string, ok bool)
			* 获取注解
	
	# type StructTag string
		* 结构体的字段注解
	
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
				* 返回该类型public方法的个数

			Name() string
				* 返回实际类型的名称，不含包名

			PkgPath() string
				* 返回类型的包路径，import 路径，如果没有返回空字符串

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
				* 检查当前类型能不能做比较运算，其实就是看这个类型底层有没有绑定 typeAlg 的 equal 方法

			Bits() int
				* 以比特为单位返回类型的大小，不是所有类型都能调这个方法，不能调的会 panic

			ChanDir() ChanDir
				* 返回一个通道类型的方向，如果当前type不是通道，会抛出异常

			IsVariadic() bool
				* 返回当前类型的函数，最后一个参数是不是可变长参数。如果当前类型不是函数，则抛出异常

			Elem() Type
				* 返回当前类型的元素类型，如果类型不是Array、Chan、Map、Ptr或Slice，抛出异常

			Field(i int) StructField
				* 返回 struct 类型的第 i 个字段，不是 struct 会 panic，i 越界也会 panic

			FieldByIndex(index []int) StructField
				* 跟上边一样，不过是嵌套调用的，比如 [1, 2] 就是说返回当前 struct 的第1个struct 的第2个字段，适用于 struct 本身嵌套的类型

			FieldByName(name string) (StructField, bool)
				* 按名字找 struct 字段，第二个返回值 ok 表示有没有
			
			FieldByNameFunc(match func(string) bool) (StructField, bool)
				* 按函数名找 struct 字段，因为 struct 里也可能有类型是 func 
				 
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
			* 根据type创建实例，反射创建

		func NewAt(typ Type, p unsafe.Pointer) Value
		func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool)
		func ValueOf(i interface{}) Value
		func Zero(typ Type) Value

		func (v Value) Addr() Value
		func (v Value) Bool() bool
		func (v Value) Bytes() []byte
		func (v Value) Call(in []Value) []Value
			* 前提 v 是一个 func，然后调用 v，并传入 in 参数，第一个参数是 in[0]，第二个是 in[1]，以此类推

		func (v Value) CallSlice(in []Value) []Value
		func (v Value) CanAddr() bool
		func (v Value) CanInterface() bool
		func (v Value) CanSet() bool
		func (v Value) Cap() int
		func (v Value) Close()
		func (v Value) Complex() complex128
		func (v Value) Convert(t Type) Value
		func (v Value) Elem() Value
			* 返回指针指向的对象
				i := 1
				v := reflect.ValueOf(&i)
				v.Elem().SetInt(10)
				fmt.Println(i)
		
		func (v Value) Field(i int) Value
			* 前提 v 是一个 struct，返回第 i 个字段，这个主要用于遍历

		func (v Value) FieldByIndex(index []int) Value
		func (v Value) FieldByName(name string) Value
			*  前提 v 是一个 struct，根据字段名直接定位返回
		
		func (v Value) FieldByNameFunc(match func(string) bool) Value
		func (v Value) Float() float64
		func (v Value) Index(i int) Value
			* 前提 v 是 Array, Slice, String 之一，返回第 i 个元素，主要也是用于遍历，注意不能越界

		func (v Value) Int() int64
		func (v Value) Interface() (i interface{})
			* 转换为接口类型

		func (v Value) InterfaceData() [2]uintptr
		func (v Value) IsNil() bool
			* 判断 v 是不是 nil，只有 chan, func, interface, map, pointer, slice 可以用，其他类型会 panic

		func (v Value) IsValid() bool
			*  判断 v 是否合法，如果返回 false，那么除了 String() 以外的其他方法调用都会 panic，事前检查是必要的

		func (v Value) IsZero() bool
		func (v Value) Kind() Kind
		func (v Value) Len() int
		func (v Value) MapIndex(key Value) Value
			* 前提 v 是个 map，返回对应 value

		func (v Value) MapKeys() []Value
			* 前提 v 是个 map，返回所有 key 组成的一个 slice
			

		func (v Value) MapRange() *MapIter
		func (v Value) Method(i int) Value
		func (v Value) MethodByName(name string) Value
		func (v Value) NumField() int
			*  前提 v 是个 struct，返回字段个数

		func (v Value) NumMethod() int
		func (v Value) OverflowComplex(x complex128) bool
		func (v Value) OverflowFloat(x float64) bool
		func (v Value) OverflowInt(x int64) bool
		func (v Value) OverflowUint(x uint64) bool
		func (v Value) Pointer() uintptr
		func (v Value) Recv() (x Value, ok bool)
		func (v Value) Send(x Value)
		func (v Value) Set(x Value)
			* 赋值
		
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
			* 类型
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
		* 深比较2个数据

	func Swapper(slice interface{}) func(i, j int)