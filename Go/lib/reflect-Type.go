----------------------
变量
----------------------
		
	

----------------------
type
----------------------
	# type Type interface
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
	
	# type Method struct {
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
	func TypeAssert[T any](v Value) (T, bool)

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

