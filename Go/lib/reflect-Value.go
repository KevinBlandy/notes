----------------------
����
----------------------


----------------------
type
----------------------
	# type Value struct
		* ��struct�������е��ֶ�ϸ�ڣ���¶�˺ܶ෽��

		func (v Value) Addr() Value 
			* ���ر����ĵ�ַ��ָ��

		func (v Value) Bool() bool
		func (v Value) Bytes() []byte
		func (v Value) CanAddr() bool
			* ֵ�Ƿ����ȡ��ַ
				fmt.Println(reflect.ValueOf(1).CanAddr())			// false
				val := 1
				fmt.Println(reflect.ValueOf(val).CanAddr())			// false
				fmt.Println(reflect.ValueOf(&val).CanAddr())		// false
				fmt.Println(reflect.ValueOf(&val).Elem().CanAddr())	// true


		func (v Value) CanSet() bool
			* �Ƿ��ǿ���ȡ��ַ�����ҿ����޸ĵ�

		func (v Value) Call(in []Value) []Value
			* ִ�����ֵ�����ֵ���ǿ�ִ�ж����׳��쳣
			* ������޲εĺ��������Դ��ݿ���Ƭ��make([]reflect.Value, 0)

		func (v Value) CallSlice(in []Value) []Value 
		func (v Value) Cap() int 
		func (v Value) Close() 
		func (v Value) Complex() complex128
		func (v Value) Elem() Value 
			* �����ָ�룬�򷵻�ָ��ָ��ı���
			* ���ص�Ҳ�Ǹñ�����Value

		func (v Value) Field(i int) Value 
		func (v Value) FieldByIndex(index []int) Value
		func (v Value) FieldByName(name string) Value
		func (v Value) FieldByNameFunc(match func(string) bool) Value
			* ���Ի�ȡ���ֶ�

		func (v Value) Float() float64 
		func (v Value) Index(i int) Value
			* ����ָ���±�����ݣ�������ǿɷ��ʵ����ͣ��׳��쳣

		func (v Value) Int() int64 
		func (v Value) CanInterface() bool 
			* �Ƿ���ת��Ϊinterface{}

		func (v Value) Interface() (i interface{})
			* ��ֵ��Ϊ interface{} ���أ�һ������ǿת
				val := reflect.ValueOf("H")
				var str string = val.Interface().(string)  // H
			
			* ���ת�������Ͳ���ȫ���ϣ���ֱ��panic������Ҫ�����ϸ���Ҫ����ָ�뻹��ֵ

		func (v Value) InterfaceData() [2]uintptr 
		func (v Value) IsNil() bool
			* �ж������Ƿ���nil

		func (v Value) IsValid() bool 
		func (v Value) IsZero() bool 
		func (v Value) Kind() Kind 
			* ����ֵ������
		
		func (v Value) Len() int 
			* �������ݵ�Length���ȣ������Ͼ���ִ�� len
			* ������ݲ����⼸�����׳��쳣��Array, Chan, Map, Slice, or String

		func (v Value) MapIndex(key Value) Value 
		func (v Value) MapKeys() []Value 

		func (v Value) Method(i int) Value 
			* ��ȡָ���ķ���
		func (v Value) NumMethod() int 
			* ��ȡstruct�ķ�������
		func (v Value) MethodByName(name string) Value 
			* ��ȡָ��������Value
		
		func (v Value) MapRange() *MapIter 
			* ��ȡ��MapIter

		func (v Value) NumField() int 
			* ����struct���ֶ�����
			
		func (v Value) OverflowComplex(x complex128) bool 
		func (v Value) OverflowFloat(x float64) bool 
		func (v Value) OverflowInt(x int64) bool 
		func (v Value) OverflowUint(x uint64) bool 
		func (v Value) Pointer() uintptr

		func (v Value) Recv() (x Value, ok bool)
		func (v Value) Send(x Value) 
			* �����ͨ�����ͣ����Խ��н��պͷ���

		func (v Value) Set(x Value)
			* ����ֵ�����ֵ��ƥ������ǵ�ַ����ȡ���׳��쳣

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
			* ��������Type����

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
		* Ԥ����ı���
			const (
				_             SelectDir = iota
				SelectSend              // case Chan <- Send
				SelectRecv              // case <-Chan:
				SelectDefault           // default
			)


----------------------
����
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
		* ��ָ���Ķ�ȡΪValue�������ص��Ǿ��������


	func Zero(typ Type) Value 
	func New(typ Type) Value 
	func NewAt(typ Type, p unsafe.Pointer) Value

