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
				* 返回当从内存中申请一个该类型值时，会对齐的字节数

			FieldAlign() int
				* 返回当该类型作为结构体的字段时，会对齐的字节数

			Method(int) Method
				* 返回该类型方法集中的第i个方法，i不在[0, NumMethod())范围内时，将导致panic
				* 对非接口类型T或*T，返回值的Type字段和Func字段描述方法的未绑定函数状态
				* 对接口类型，返回值的Type字段描述方法的签名，Func字段为nil

			MethodByName(string) (Method, bool)
				* 根据方法名返回该类型方法集中的方法，使用一个布尔值说明是否发现该方法
				* 对非接口类型T或*T，返回值的Type字段和Func字段描述方法的未绑定函数状态
				* 对接口类型，返回值的Type字段描述方法的签名，Func字段为nil

			NumMethod() int
				* 返回该类型的方法集中方法的数目
				* 匿名字段的方法会被计算；主体类型的方法会屏蔽匿名字段的同名方法；
				* 匿名字段导致的歧义方法会滤除

			Name() string
				* 返回该类型在自身包内的类型名，如果是未命名类型会返回""

			PkgPath() string
				* PkgPath返回类型的包路径，即明确指定包的import路径，如"encoding/base64"
				* 如果类型为内建类型(string, error)或未命名类型(*T, struct{}, []int)，会返回""

			Size() uintptr
				* 返回要保存一个该类型的值需要多少字节；类似unsafe.Sizeof

			String() string
				* 返回类型的字符串表示。该字符串可能会使用短包名（如用base64代替"encoding/base64"）
				* 也不保证每个类型的字符串表示不同。如果要比较两个类型是否相等，请直接用Type类型比较。

			Kind() Kind
				*  Kind返回该接口的具体分类

			Implements(u Type) bool
				* 如果该类型实现了u代表的接口，会返回真
			
			AssignableTo(u Type) bool
				* 如果该类型的值可以直接赋值给u代表的类型，返回真
			
			ConvertibleTo(u Type) bool
				* 如该类型的值可以转换为u代表的类型，返回真
			
			Comparable() bool
				* 当前类型是否可以和当前类型进行 == 比较

			Bits() int
				* 返回该类型的字位数。如果该类型的Kind不是Int、Uint、Float或Complex，会panic
			
			ChanDir() ChanDir
				* 返回一个channel类型的方向，如非通道类型将会panic
			
			IsVariadic() bool
				* 如果函数类型的最后一个输入参数是"..."形式的参数，IsVariadic返回真
				* 如非函数类型将panic
			
			Elem() Type
				* 返回该类型的元素类型，如果该类型的Kind不是Array、Chan、Map、Ptr或Slice，会panic
			
			Field(i int) StructField
				* 返回struct类型的第i个字段的类型，如非结构体或者i不在[0, NumField())内将会panic

			FieldByIndex(index []int) StructField	
				* 返回索引序列指定的嵌套字段的类型，
				* 等价于用索引中每个值链式调用本方法，如非结构体将会panic
				* 在结构体A中找index[0]属性，再从这个属性中找index[1]属性...

			FieldByName(name string) (StructField, bool)
				* 返回该类型名为name的字段（会查找匿名字段及其子字段），
				* 布尔值说明是否找到，如非结构体将panic
			
			FieldByNameFunc(match func(string) bool) (StructField, bool)
				* 返回该类型第一个字段名满足函数match的字段，布尔值说明是否找到，如非结构体将会panic

			In(i int) Type
				* 返回func类型的第i个参数的类型，如非函数或者i不在[0, NumIn())内将会panic
			
			Key() Type
				* 返回map类型的键的类型。如非映射类型将panic
			
			Len() int
				* 回array类型的长度，如非数组类型将panic
			
			NumField() int
				* 返回struct类型的字段数（匿名字段算作一个字段），如非结构体类型将panic

			NumIn() int
				* 返回func类型的参数个数，如果不是函数，将会panic

			NumOut() int
				* 返回func类型的返回值个数，如果不是函数，将会panic
			
			Out(i int) Type
				* 返回func类型的第i个返回值的类型，如非函数或者i不在[0, NumOut())内将会panic
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