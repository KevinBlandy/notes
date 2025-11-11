----------------------
变量
----------------------


----------------------
type
----------------------
	# type Value struct
		* 这struct隐藏所有的字段细节，暴露了很多方法

		func (v Value) Addr() Value 
			* 返回变量的地址，指针

		func (v Value) Bool() bool
		func (v Value) Bytes() []byte
		func (v Value) CanAddr() bool
			* 值是否可以取地址
				fmt.Println(reflect.ValueOf(1).CanAddr())			// false
				val := 1
				fmt.Println(reflect.ValueOf(val).CanAddr())			// false
				fmt.Println(reflect.ValueOf(&val).CanAddr())		// false
				fmt.Println(reflect.ValueOf(&val).Elem().CanAddr())	// true


		func (v Value) CanSet() bool
			* 是否是可以取地址，并且可以修改的

		func (v Value) Call(in []Value) []Value
			* 执行这个值，如果值不是可执行对象，抛出异常
			* 如果是无参的函数，可以传递空切片：make([]reflect.Value, 0)

		func (v Value) CallSlice(in []Value) []Value 
		func (v Value) Cap() int 
		func (v Value) Close() 
		func (v Value) Complex() complex128
		func (v Value) Elem() Value 
			* 如果是指针，则返回指针指向的变量
			* 返回的也是该变量的Value

		func (v Value) Field(i int) Value 
		func (v Value) FieldByIndex(index []int) Value
		func (v Value) FieldByName(name string) Value
		func (v Value) FieldByNameFunc(match func(string) bool) Value
			* 尝试获取到字段

		func (v Value) Float() float64 
		func (v Value) Index(i int) Value
			* 返回指定下标的数据，如果不是可访问的类型，抛出异常

		func (v Value) Int() int64 
		func (v Value) CanInterface() bool 
			* 是否能转换为interface{}

		func (v Value) Interface() (i interface{})
			* 把值作为 interface{} 返回，一般用于强转
				val := reflect.ValueOf("H")
				var str string = val.Interface().(string)  // H
			
			* 如果转换的类型不完全符合，则直接panic，类型要求极其严格，需要区分指针还是值

		func (v Value) InterfaceData() [2]uintptr 
		func (v Value) IsNil() bool
			* 判断类型是否是nil

		func (v Value) IsValid() bool 
		func (v Value) IsZero() bool 
		func (v Value) Kind() Kind 
			* 返回值的类型
		
		func (v Value) Len() int 
			* 返回数据的Length长度，本质上就是执行 len
			* 如果数据不是这几个，抛出异常：Array, Chan, Map, Slice, or String

		func (v Value) MapIndex(key Value) Value 
		func (v Value) MapKeys() []Value 

		func (v Value) Method(i int) Value 
			* 获取指定的方法
		func (v Value) NumMethod() int 
			* 获取struct的方法数量
		func (v Value) MethodByName(name string) Value 
			* 获取指定方法的Value
		
		func (v Value) MapRange() *MapIter 
			* 获取到MapIter

		func (v Value) NumField() int 
			* 返回struct的字段数量
			
		func (v Value) OverflowComplex(x complex128) bool 
		func (v Value) OverflowFloat(x float64) bool 
		func (v Value) OverflowInt(x int64) bool 
		func (v Value) OverflowUint(x uint64) bool 
		func (v Value) Pointer() uintptr

		func (v Value) Recv() (x Value, ok bool)
		func (v Value) Send(x Value) 
			* 如果是通道类型，可以进行接收和发送

		func (v Value) Set(x Value)
			* 更新值，如果值不匹配或者是地址不可取，抛出异常

		func (v Value) SetBool(x bool)
		func (v Value) SetBytes(x []byte)
		func (v Value) SetComplex(x complex128)
		func (v Value) SetFloat(x float64) 
		func (v Value) SetInt(x int64) 
		func (v Value) SetLen(n int) 
		func (v Value) SetCap(n int) 
		func (v Value) SetMapIndex(key, elem Value) 
		func (v Value) SetUint(x uint64)
		func (v Value) SetPointer(x unsafe.Pointer) 
		func (v Value) SetString(x string) 
		func (v Value) Slice(i, j int) Value
		func (v Value) Slice3(i, j, k int) Value 
		func (v Value) String() string 
		func (v Value) TryRecv() (x Value, ok bool)
		func (v Value) TrySend(x Value) bool
		func (v Value) Type() Type
			* 返回他的Type类型

		func (v Value) Uint() uint64 
		func (v Value) UnsafeAddr() uintptr 
		func (v Value) Convert(t Type) Value 
		func (v Value) SetIterKey(iter *MapIter)
		func (v Value) SetIterValue(iter *MapIter)
		func (v Value) UnsafePointer() unsafe.Pointer

	# type ValueError struct
		Method string
		Kind   Kind

		func (e *ValueError) Error() string 



	# type MapIter struct
		func (it *MapIter) Key() Value 
		func (it *MapIter) Value() Value
		func (it *MapIter) Next() bool

	
	# type StringHeader struct
		Data uintptr
		Len  int

	# type SliceHeader struct
		Data uintptr
		Len  int
		Cap  int

	# type SelectCase struct
		Dir  SelectDir
		Chan Value   
		Send Value    
		

		func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool) 
	
	# type SelectDir int
		* 预定义的变量
			const (
				_             SelectDir = iota
				SelectSend              // case Chan <- Send
				SelectRecv              // case <-Chan:
				SelectDefault           // default
			)


----------------------
方法
----------------------
	func Append(s Value, x ...Value) Value 
	func AppendSlice(s, t Value) Value 
	func Copy(dst, src Value) int 
	func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool)
	func MakeSlice(typ Type, len, cap int) Value 
	func MakeChan(typ Type, buffer int) Value 
	func MakeMap(typ Type) Value 
	func MakeMapWithSize(typ Type, n int) Value 

	func Indirect(v Value) Value
	func ValueOf(i interface{}) Value 
		* 把指定的读取为Value，它返回的是具体的类型


	func Zero(typ Type) Value 
	func New(typ Type) Value 
	func NewAt(typ Type, p unsafe.Pointer) Value

