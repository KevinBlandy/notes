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

		* 类别，类别是指这个数据是什么类型的数据

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
				* 匿名字段的方法会被计算；主体类型的方法会屏蔽匿名字段的同名方法（覆写的方法，只算一个）
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
		
		* 类型，类型指的是，“数据”是某种“类别”下的某种“类型”
			type User struct {
				// struct 类别下的 User类型
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
			* 向切片类型的Value值s中添加一系列值，x等Value值持有的值必须能直接赋值给s持有的切片的元素类型。
		
		func AppendSlice(s, t Value) Value
			* 类似Append函数，但接受一个切片类型的Value值。将切片t的每一个值添加到s。
		
		func Indirect(v Value) Value
			* 返回持有v持有的指针指向的值的Value。如果v持有nil指针，会返回Value零值；如果v不持有指针，会返回v。

		func MakeChan(typ Type, buffer int) Value
			* MakeChan创建一个元素类型为typ、有buffer个缓存的通道类型的Value值。
		
		func MakeFunc(typ Type, fn func(args []Value) (results []Value)) Value
			* MakeFunc返回一个具有给定类型、包装函数fn的函数的Value封装。当被调用时，该函数会：
				- 将提供给它的参数转化为Value切片
				- 执行results := fn(args)
				- 将results中每一个result依次排列作为返回值
			
		func MakeMap(typ Type) Value
			* MakeMap创建一个特定映射类型的Value值。
		
		func MakeMapWithSize(typ Type, n int) Value
		func MakeSlice(typ Type, len, cap int) Value
			* MakeSlice创建一个新申请的元素类型为typ，长度len容量cap的切片类型的Value值。
		
		func New(typ Type) Value
			* New返回一个Value类型值，该值持有一个指向类型为typ的新申请的零值的指针，返回值的Type为PtrTo(typ)。

		func NewAt(typ Type, p unsafe.Pointer) Value
			* NewAt返回一个Value类型值，该值持有一个指向类型为typ、地址为p的值的指针。

		func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool)
		func ValueOf(i interface{}) Value
		func Zero(typ Type) Value
			* Zero返回一个持有类型typ的零值的Value。
			* 注意持有零值的Value和Value零值是两回事。Value零值表示不持有任何值。例如Zero(TypeOf(42))返回一个Kind为Int、值为0的Value。Zero的返回值不能设置也不会寻址。


		func (v Value) Addr() Value
			* 函数返回一个持有指向v持有者的指针的Value封装。
			* 如果v.CanAddr()返回假，调用本方法会panic。Addr一般用于获取结构体字段的指针或者切片的元素（的Value封装）以便调用需要指针类型接收者的方法。

		func (v Value) Bool() bool
			* 返回v持有的布尔值，如果v的Kind不是Bool会panic

		func (v Value) Bytes() []byte
			* 返回v持有的[]byte类型值。如果v持有的值的类型不是[]byte会panic。

		func (v Value) Call(in []Value) []Value
			* 前提 v 是一个 func，然后调用 v，并传入 in 参数，第一个参数是 in[0]，第二个是 in[1]，以此类推
			* Call方法使用输入的参数in调用v持有的函数。例如，如果len(in) == 3，v.Call(in)代表调用v(in[0], in[1], in[2])（其中Value值表示其持有值）。
			* 如果v的Kind不是Func会panic。它返回函数所有输出结果的Value封装的切片。和go代码一样，每一个输入实参的持有值都必须可以直接赋值给函数对应输入参数的类型。如果v持有值是可变参数函数，Call方法会自行创建一个代表可变参数的切片，将对应可变参数的值都拷贝到里面。

		func (v Value) CallSlice(in []Value) []Value
			* CallSlice调用v持有的可变参数函数，会将切片类型的in[len(in)-1]（的成员）分配给v的最后的可变参数。
			* 例如，如果len(in) == 3，v.Call(in)代表调用v(in[0], in[1], in[2])（其中Value值表示其持有值，可变参数函数的可变参数位置提供一个切片并跟三个点号代表"解切片"）。如果v的Kind不是Func或者v的持有值不是可变参数函数，会panic。它返回函数所有输出结果的Value封装的切片。和go代码一样，每一个输入实参的持有值都必须可以直接赋值给函数对应输入参数的类型。

		func (v Value) CanAddr() bool
			* 返回是否可以获取v持有值的指针。可以获取指针的值被称为可寻址的。
			* 如果一个值是切片或可寻址数组的元素、可寻址结构体的字段、或从指针解引用得到的，该值即为可寻址的。

		func (v Value) CanInterface() bool
			* 如果CanInterface返回真，v可以不导致panic的调用Interface方法。

		func (v Value) CanSet() bool
			* 如果v持有的值可以被修改，CanSet就会返回真。
			* 只有一个Value持有值可以被寻址同时又不是来自非导出字段时，它才可以被修改。如果CanSet返回假，调用Set或任何限定类型的设置函数（如SetBool、SetInt64）都会panic。

		func (v Value) Cap() int
			* 返回v持有值的容量，如果v的Kind不是Array、Chan、Slice会panic

		func (v Value) Close()
			* 关闭v持有的通道，如果v的Kind不是Chan会panic

		func (v Value) Complex() complex128
		func (v Value) Convert(t Type) Value
			* Convert将v持有的值转换为类型为t的值，并返回该值的Value封装。如果go转换规则不支持这种转换，会panic。

		func (v Value) Elem() Value
			* 返回指针指向的对象
				i := 1
				v := reflect.ValueOf(&i)
				v.Elem().SetInt(10)
				fmt.Println(i)
			
			* Elem返回v持有的接口保管的值的Value封装，或者v持有的指针指向的值的Value封装。
			* 如果v的Kind不是Interface或Ptr会panic；如果v持有的值为nil，会返回Value零值。

		
		func (v Value) Field(i int) Value
			* 前提 v 是一个 struct，返回第 i 个字段，这个主要用于遍历
			* 返回结构体的第i个字段（的Value封装）。如果v的Kind不是Struct或i出界会panic

		func (v Value) FieldByIndex(index []int) Value
			* 返回索引序列指定的嵌套字段的Value表示，等价于用索引中的值链式调用本方法，如v的Kind非Struct将会panic

		func (v Value) FieldByName(name string) Value
			* 前提 v 是一个 struct，根据字段名直接定位返回
			* 返回该类型名为name的字段（的Value封装）（会查找匿名字段及其子字段），如果v的Kind不是Struct会panic；如果未找到会返回Value零值。
		
		func (v Value) FieldByNameFunc(match func(string) bool) Value
			* 返回该类型第一个字段名满足match的字段（的Value封装）（会查找匿名字段及其子字段），如果v的Kind不是Struct会panic；如果未找到会返回Value零值。

		func (v Value) Float() float64
			* 返回v持有的浮点数（表示为float64），如果v的Kind不是Float32、Float64会panic
		
		func (v Value) Index(i int) Value
			* 前提 v 是 Array, Slice, String 之一，返回第 i 个元素，主要也是用于遍历，注意不能越界
			* 返回v持有值的第i个元素。如果v的Kind不是Array、Chan、Slice、String，或者i出界，会panic

		func (v Value) Int() int64
			* 返回v持有的有符号整数（表示为int64），如果v的Kind不是Int、Int8、Int16、Int32、Int64会panic

		func (v Value) Interface() (i interface{})
			* 转换为接口类型
			* 本方法返回v当前持有的值（表示为/保管在interface{}类型），等价于：

		func (v Value) InterfaceData() [2]uintptr
			* 返回v持有的接口类型值的数据。如果v的Kind不是Interface会panic

		func (v Value) IsNil() bool
			* 判断 v 是不是 nil，只有 chan, func, interface, map, pointer, slice 可以用，其他类型会 panic
			* 但是如果v是Value零值，会panic。

		func (v Value) IsValid() bool
			* IsValid返回v是否持有一个值。如果v是Value零值会返回假，此时v除了IsValid、String、Kind之外的方法都会导致panic。
			* 绝大多数函数和方法都永远不返回Value零值。如果某个函数/方法返回了非法的Value，它的文档必须显式的说明具体情况。


		func (v Value) IsZero() bool
		func (v Value) Kind() Kind
			* Kind返回v持有的值的分类，如果v是Value零值，返回值为Invalid

		func (v Value) Len() int
			* 返回v持有值的长度，如果v的Kind不是Array、Chan、Slice、Map、String会panic
		
		func (v Value) MapIndex(key Value) Value
			* 前提 v 是个 map，返回对应 value
			* 返回v持有值里key持有值为键对应的值的Value封装。如果v的Kind不是Map会panic。
			* 如果未找到对应值或者v持有值是nil映射，会返回Value零值。key的持有值必须可以直接赋值给v持有值类型的键类型。

		func (v Value) MapKeys() []Value
			* 前提 v 是个 map，返回所有 key 组成的一个 slice
			* 返回一个包含v持有值中所有键的Value封装的切片，该切片未排序。如果v的Kind不是Map会panic。如果v持有值是nil，返回空切片（非nil）。

		func (v Value) MapRange() *MapIter
		func (v Value) Method(i int) Value
			* 返回v持有值类型的第i个方法的已绑定（到v的持有值的）状态的函数形式的Value封装。
			* 返回值调用Call方法时不应包含接收者；返回值持有的函数总是使用v的持有者作为接收者（即第一个参数）。如果i出界，或者v的持有值是接口类型的零值（nil），会panic。

		func (v Value) MethodByName(name string) Value
			* 返回v的名为name的方法的已绑定（到v的持有值的）状态的函数形式的Value封装。
			* 返回值调用Call方法时不应包含接收者；返回值持有的函数总是使用v的持有者作为接收者（即第一个参数）。如果未找到该方法，会返回一个Value零值。

		func (v Value) NumField() int
			*  前提 v 是个 struct，返回字段个数
			* 返回v持有的结构体类型值的字段数，如果v的Kind不是Struct会panic

		func (v Value) NumMethod() int
			* 返回v持有值的方法集的方法数目。

		func (v Value) OverflowComplex(x complex128) bool
			* 返回v持有的复数（表示为complex64），如果v的Kind不是Complex64、Complex128会panic

		func (v Value) OverflowFloat(x float64) bool
			* 如果v持有值的类型不能无溢出的表示x，会返回真。如果v的Kind不是Float32、Float64会panic

		func (v Value) OverflowInt(x int64) bool
			* 如果v持有值的类型不能无溢出的表示x，会返回真。如果v的Kind不是Int、Int8、Int16、Int32、Int64会panic
		
		func (v Value) OverflowUint(x uint64) bool	
			* 如果v持有值的类型不能无溢出的表示x，会返回真。如果v的Kind不是Uint、Uintptr、Uint8、Uint16、Uint32、Uint64会panic
		
		func (v Value) Pointer() uintptr
			* 将v持有的值作为一个指针返回。
			* 本方法返回值不是unsafe.Pointer类型，以避免程序员不显式导入unsafe包却得到unsafe.Pointer类型表示的指针。
			* 如果v的Kind不是Chan、Func、Map、Ptr、Slice或UnsafePointer会panic。
			* 如果v的Kind是Func，返回值是底层代码的指针，但并不足以用于区分不同的函数；只能保证当且仅当v持有函数类型零值nil时，返回值为0。
			* 如果v的Kind是Slice，返回值是指向切片第一个元素的指针。如果持有的切片为nil，返回值为0；如果持有的切片没有元素但不是nil，返回值不会是0。

		func (v Value) Recv() (x Value, ok bool)
			* 方法从v持有的通道接收并返回一个值（的Value封装）。
			* 如果v的Kind不是Chan会panic。方法会阻塞直到获取到值。如果返回值x对应于某个发送到v持有的通道的值，ok为真；如果因为通道关闭而返回，x为Value零值而ok为假。

		func (v Value) Send(x Value)
			* 方法向v持有的通道发送x持有的值。如果v的Kind不是Chan，或者x的持有值不能直接赋值给v持有通道的元素类型，会panic。

		func (v Value) Set(x Value)
			* 赋值
			* 将v的持有值修改为x的持有值。如果v.CanSet()返回假，会panic。x的持有值必须能直接赋给v持有值的类型。
		
		func (v Value) SetBool(x bool)
			* 设置v的持有值。如果v的Kind不是Bool或者v.CanSet()返回假，会panic。

		func (v Value) SetBytes(x []byte)
			* 设置v的持有值。如果v持有值不是[]byte类型或者v.CanSet()返回假，会panic。

		func (v Value) SetCap(n int)
			* 设定v持有值的容量。如果v的Kind不是Slice或者n出界（小于长度或超出容量），将导致panic

		func (v Value) SetComplex(x complex128)
			* 设置v的持有值。如果v的Kind不是Complex64、Complex128或者v.CanSet()返回假，会panic。

		func (v Value) SetFloat(x float64)
			* 设置v的持有值。如果v的Kind不是Float32、Float64或者v.CanSet()返回假，会panic。

		func (v Value) SetInt(x int64)
			* 设置v的持有值。如果v的Kind不是Int、Int8、Int16、Int32、Int64之一或者v.CanSet()返回假，会panic。

		func (v Value) SetLen(n int)
			* 设定v持有值的长度。如果v的Kind不是Slice或者n出界（小于零或超出容量），将导致panic

		func (v Value) SetMapIndex(key, elem Value)
			* 用来给v的映射类型持有值添加/修改键值对，如果val是Value零值，则是删除键值对。
			* 如果v的Kind不是Map，或者v的持有值是nil，将会panic。
			* key的持有值必须可以直接赋值给v持有值类型的键类型。val的持有值必须可以直接赋值给v持有值类型的值类型。


		func (v Value) SetPointer(x unsafe.Pointer)
			* 设置v的持有值。如果v的Kind不是UnsafePointer或者v.CanSet()返回假，会panic。

		func (v Value) SetString(x string)
			* 设置v的持有值。如果v的Kind不是String或者v.CanSet()返回假，会panic。
		
		func (v Value) SetUint(x uint64)
			* 设置v的持有值。如果v的Kind不是Uint、Uintptr、Uint8、Uint16、Uint32、Uint64或者v.CanSet()返回假，会panic。
		
		func (v Value) Slice(i, j int) Value
			* 返回v[i:j]（v持有的切片的子切片的Value封装）；如果v的Kind不是Array、Slice或String会panic。如果v是一个不可寻址的数组，或者索引出界，也会panic

		func (v Value) Slice3(i, j, k int) Value
			* 是Slice的3参数版本，返回v[i:j:k] ；如果v的Kind不是Array、Slice或String会panic。如果v是一个不可寻址的数组，或者索引出界，也会panic。

		func (v Value) String() string
			* 返回v持有的值的字符串表示。因为go的String方法的惯例，Value的String方法比较特别。
			* 和其他获取v持有值的方法不同：v的Kind是String时，返回该字符串；v的Kind不是String时也不会panic而是返回格式为"<T value>"的字符串，其中T是v持有值的类型。


		func (v Value) TryRecv() (x Value, ok bool)
			* TryRecv尝试从v持有的通道接收一个值，但不会阻塞。如果v的Kind不是Chan会panic。
			* 如果方法成功接收到一个值，会返回该值（的Value封装）和true；如果不能无阻塞的接收到值，返回Value零值和false；如果因为通道关闭而返回，返回值x是持有通道元素类型的零值的Value和false。

		func (v Value) TrySend(x Value) bool
			* TrySend尝试向v持有的通道发送x持有的值，但不会阻塞。如果v的Kind不是Chan会panic。
			* 如果成功发送会返回真，否则返回假。x的持有值必须可以直接赋值给v持有通道的元素类型。

		func (v Value) Type() Type
			* 返回v持有的值的类型的Type表示。

		func (v Value) Uint() uint64
			* 返回v持有的无符号整数（表示为uint64），如v的Kind不是Uint、Uintptr、Uint8、Uint16、Uint32、Uint64会panic

		func (v Value) UnsafeAddr() uintptr
			* 返回指向v持有数据的地址的指针（表示为uintptr）以用作高级用途，如果v不可寻址会panic。
	
	# type ValueError struct {
			Method string
			Kind   Kind
		}
		func (e *ValueError) Error() string
	

---------------------
func
---------------------
	func Copy(dst, src Value) int
		* 将src中的值拷贝到dst，直到src被耗尽或者dst被装满，要求这二者都是slice或array，且元素类型相同。
	
	func DeepEqual(x, y interface{}) bool
		* 深比较2个数据
		* 用来判断两个值是否深度一致：除了类型相同；在可以时（主要是基本类型）会使用==；但还会比较array、slice的成员，map的键值对，结构体字段进行深入比对。
		* map的键值对，对键只使用==，但值会继续往深层比对。
		* DeepEqual函数可以正确处理循环的类型。函数类型只有都会nil时才相等；空切片不等于nil切片；还会考虑array、slice的长度、map键值对数。

	func Swapper(slice interface{}) func(i, j int)