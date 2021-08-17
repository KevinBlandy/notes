---------------------
reflect
---------------------

---------------------
����
---------------------

---------------------
type
---------------------
	# type ChanDir int
		const (
			RecvDir ChanDir             = 1 << iota // ֻдͨ��
			SendDir                                 // ֻ��ͨ��
			BothDir = RecvDir | SendDir             // ��дͨ��
		)

		* ͨ��������

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

		* ��������ָ���������ʲô���͵�����

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

		func (m Method) IsExported() bool
	
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
		* ��Ƭ�ĵز�ṹ

	# type StringHeader struct {
			Data uintptr
			Len  int
		}

		* �ַ����ĵĵײ�ṹ��һ��ָ�룬һ������
	
	# type StructField struct {
			Name string
			PkgPath string
			Type      Type      // field type
				* ����
			Tag       StructTag // field tag string
				* tag��������Java�е�ע��
			
			Offset    uintptr   // offset within struct, in bytes
			Index     []int     // index sequence for Type.FieldByIndex
			Anonymous bool      // is an embedded field
		}
		
		* �ṹ����ֶ�

		func (tag StructTag) Get(key string) string
			* ��ȡע�⣬��������ڷ��� ""
				v, _ := tag.Lookup(key)
				return v
			
		func (tag StructTag) Lookup(key string) (value string, ok bool)
			* ��ȡע��
		
		func (f StructField) IsExported() bool
			* �Ƿ��ǵ������ֶ�
	
	# type StructTag string
		* �ṹ����ֶ�ע��
	
	# type Type interface {
			Align() int
				* ���ص����ڴ�������һ��������ֵʱ���������ֽ���

			FieldAlign() int
				* ���ص���������Ϊ�ṹ����ֶ�ʱ���������ֽ���

			Method(int) Method
				* ���ظ����ͷ������еĵ�i��������i����[0, NumMethod())��Χ��ʱ��������panic
				* �Էǽӿ�����T��*T������ֵ��Type�ֶκ�Func�ֶ�����������δ�󶨺���״̬
				* �Խӿ����ͣ�����ֵ��Type�ֶ�����������ǩ����Func�ֶ�Ϊnil

			MethodByName(string) (Method, bool)
				* ���ݷ��������ظ����ͷ������еķ�����ʹ��һ������ֵ˵���Ƿ��ָ÷���
				* �Էǽӿ�����T��*T������ֵ��Type�ֶκ�Func�ֶ�����������δ�󶨺���״̬
				* �Խӿ����ͣ�����ֵ��Type�ֶ�����������ǩ����Func�ֶ�Ϊnil

			NumMethod() int
				* ���ظ����͵ķ������з�������Ŀ
				* �����ֶεķ����ᱻ���㣻�������͵ķ��������������ֶε�ͬ����������д�ķ�����ֻ��һ����
				* �����ֶε��µ����巽�����˳�

			Name() string
				* ���ظ�������������ڵ��������������δ�������ͻ᷵��""

			PkgPath() string
				* PkgPath�������͵İ�·��������ȷָ������import·������"encoding/base64"
				* �������Ϊ�ڽ�����(string, error)��δ��������(*T, struct{}, []int)���᷵��""

			Size() uintptr
				* ����Ҫ����һ�������͵�ֵ��Ҫ�����ֽڣ�����unsafe.Sizeof

			String() string
				* �������͵��ַ�����ʾ�����ַ������ܻ�ʹ�ö̰���������base64����"encoding/base64"��
				* Ҳ����֤ÿ�����͵��ַ�����ʾ��ͬ�����Ҫ�Ƚ����������Ƿ���ȣ���ֱ����Type���ͱȽϡ�

			Kind() Kind
				*  Kind���ظýӿڵľ������

			Implements(u Type) bool
				* ���������ʵ����u����Ľӿڣ��᷵����
			
			AssignableTo(u Type) bool
				* ��������͵�ֵ����ֱ�Ӹ�ֵ��u��������ͣ�������
			
			ConvertibleTo(u Type) bool
				* ������͵�ֵ����ת��Ϊu��������ͣ�������
			
			Comparable() bool
				* ��ǰ�����Ƿ���Ժ͵�ǰ���ͽ��� == �Ƚ�

			Bits() int
				* ���ظ����͵���λ������������͵�Kind����Int��Uint��Float��Complex����panic
			
			ChanDir() ChanDir
				* ����һ��channel���͵ķ������ͨ�����ͽ���panic
			
			IsVariadic() bool
				* ����������͵����һ�����������"..."��ʽ�Ĳ�����IsVariadic������
				* ��Ǻ������ͽ�panic
			
			Elem() Type
				* ���ظ����͵�Ԫ�����ͣ���������͵�Kind����Array��Chan��Map��Ptr��Slice����panic
			
			Field(i int) StructField
				* ����struct���͵ĵ�i���ֶε����ͣ���ǽṹ�����i����[0, NumField())�ڽ���panic

			FieldByIndex(index []int) StructField	
				* ������������ָ����Ƕ���ֶε����ͣ�
				* �ȼ�����������ÿ��ֵ��ʽ���ñ���������ǽṹ�彫��panic
				* �ڽṹ��A����index[0]���ԣ��ٴ������������index[1]����...

			FieldByName(name string) (StructField, bool)
				* ���ظ�������Ϊname���ֶΣ�����������ֶμ������ֶΣ���
				* ����ֵ˵���Ƿ��ҵ�����ǽṹ�彫panic
			
			FieldByNameFunc(match func(string) bool) (StructField, bool)
				* ���ظ����͵�һ���ֶ������㺯��match���ֶΣ�����ֵ˵���Ƿ��ҵ�����ǽṹ�彫��panic

			In(i int) Type
				* ����func���͵ĵ�i�����������ͣ���Ǻ�������i����[0, NumIn())�ڽ���panic
			
			Key() Type
				* ����map���͵ļ������͡����ӳ�����ͽ�panic
			
			Len() int
				* ��array���͵ĳ��ȣ�����������ͽ�panic
			
			NumField() int
				* ����struct���͵��ֶ����������ֶ�����һ���ֶΣ�����ǽṹ�����ͽ�panic

			NumIn() int
				* ����func���͵Ĳ���������������Ǻ���������panic

			NumOut() int
				* ����func���͵ķ���ֵ������������Ǻ���������panic
			
			Out(i int) Type
				* ����func���͵ĵ�i������ֵ�����ͣ���Ǻ�������i����[0, NumOut())�ڽ���panic
		}
		
		* ���ͣ�����ָ���ǣ������ݡ���ĳ�֡�����µ�ĳ�֡����͡�
			type User struct {
				// struct ����µ� User����
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
			* ����Ƭ���͵�Valueֵs�����һϵ��ֵ��x��Valueֵ���е�ֵ������ֱ�Ӹ�ֵ��s���е���Ƭ��Ԫ�����͡�
		
		func AppendSlice(s, t Value) Value
			* ����Append������������һ����Ƭ���͵�Valueֵ������Ƭt��ÿһ��ֵ��ӵ�s��
		
		func Indirect(v Value) Value
			* ���س���v���е�ָ��ָ���ֵ��Value�����v����nilָ�룬�᷵��Value��ֵ�����v������ָ�룬�᷵��v��

		func MakeChan(typ Type, buffer int) Value
			* MakeChan����һ��Ԫ������Ϊtyp����buffer�������ͨ�����͵�Valueֵ��
		
		func MakeFunc(typ Type, fn func(args []Value) (results []Value)) Value
			* MakeFunc����һ�����и������͡���װ����fn�ĺ�����Value��װ����������ʱ���ú����᣺
				- ���ṩ�����Ĳ���ת��ΪValue��Ƭ
				- ִ��results := fn(args)
				- ��results��ÿһ��result����������Ϊ����ֵ
			
		func MakeMap(typ Type) Value
			* MakeMap����һ���ض�ӳ�����͵�Valueֵ��
		
		func MakeMapWithSize(typ Type, n int) Value
		func MakeSlice(typ Type, len, cap int) Value
			* MakeSlice����һ���������Ԫ������Ϊtyp������len����cap����Ƭ���͵�Valueֵ��
		
		func New(typ Type) Value
			* New����һ��Value����ֵ����ֵ����һ��ָ������Ϊtyp�����������ֵ��ָ�룬����ֵ��TypeΪPtrTo(typ)��

		func NewAt(typ Type, p unsafe.Pointer) Value
			* NewAt����һ��Value����ֵ����ֵ����һ��ָ������Ϊtyp����ַΪp��ֵ��ָ�롣

		func Select(cases []SelectCase) (chosen int, recv Value, recvOK bool)
		func ValueOf(i interface{}) Value
		func Zero(typ Type) Value
			* Zero����һ����������typ����ֵ��Value��
			* ע�������ֵ��Value��Value��ֵ�������¡�Value��ֵ��ʾ�������κ�ֵ������Zero(TypeOf(42))����һ��KindΪInt��ֵΪ0��Value��Zero�ķ���ֵ��������Ҳ����Ѱַ��


		func (v Value) Addr() Value
			* ��������һ������ָ��v�����ߵ�ָ���Value��װ��
			* ���v.CanAddr()���ؼ٣����ñ�������panic��Addrһ�����ڻ�ȡ�ṹ���ֶε�ָ�������Ƭ��Ԫ�أ���Value��װ���Ա������Ҫָ�����ͽ����ߵķ�����

		func (v Value) Bool() bool
			* ����v���еĲ���ֵ�����v��Kind����Bool��panic

		func (v Value) Bytes() []byte
			* ����v���е�[]byte����ֵ�����v���е�ֵ�����Ͳ���[]byte��panic��

		func (v Value) Call(in []Value) []Value
			* ǰ�� v ��һ�� func��Ȼ����� v�������� in ��������һ�������� in[0]���ڶ����� in[1]���Դ�����
			* Call����ʹ������Ĳ���in����v���еĺ��������磬���len(in) == 3��v.Call(in)�������v(in[0], in[1], in[2])������Valueֵ��ʾ�����ֵ����
			* ���v��Kind����Func��panic�������غ���������������Value��װ����Ƭ����go����һ����ÿһ������ʵ�εĳ���ֵ���������ֱ�Ӹ�ֵ��������Ӧ������������͡����v����ֵ�ǿɱ����������Call���������д���һ������ɱ��������Ƭ������Ӧ�ɱ������ֵ�����������档

		func (v Value) CallSlice(in []Value) []Value
			* CallSlice����v���еĿɱ�����������Ὣ��Ƭ���͵�in[len(in)-1]���ĳ�Ա�������v�����Ŀɱ������
			* ���磬���len(in) == 3��v.Call(in)�������v(in[0], in[1], in[2])������Valueֵ��ʾ�����ֵ���ɱ���������Ŀɱ����λ���ṩһ����Ƭ����������Ŵ���"����Ƭ"�������v��Kind����Func����v�ĳ���ֵ���ǿɱ������������panic�������غ���������������Value��װ����Ƭ����go����һ����ÿһ������ʵ�εĳ���ֵ���������ֱ�Ӹ�ֵ��������Ӧ������������͡�

		func (v Value) CanAddr() bool
			* �����Ƿ���Ի�ȡv����ֵ��ָ�롣���Ի�ȡָ���ֵ����Ϊ��Ѱַ�ġ�
			* ���һ��ֵ����Ƭ���Ѱַ�����Ԫ�ء���Ѱַ�ṹ����ֶΡ����ָ������õõ��ģ���ֵ��Ϊ��Ѱַ�ġ�

		func (v Value) CanInterface() bool
			* ���CanInterface�����棬v���Բ�����panic�ĵ���Interface������

		func (v Value) CanSet() bool
			* ���v���е�ֵ���Ա��޸ģ�CanSet�ͻ᷵���档
			* ֻ��һ��Value����ֵ���Ա�Ѱַͬʱ�ֲ������Էǵ����ֶ�ʱ�����ſ��Ա��޸ġ����CanSet���ؼ٣�����Set���κ��޶����͵����ú�������SetBool��SetInt64������panic��

		func (v Value) Cap() int
			* ����v����ֵ�����������v��Kind����Array��Chan��Slice��panic

		func (v Value) Close()
			* �ر�v���е�ͨ�������v��Kind����Chan��panic

		func (v Value) Complex() complex128
		func (v Value) Convert(t Type) Value
			* Convert��v���е�ֵת��Ϊ����Ϊt��ֵ�������ظ�ֵ��Value��װ�����goת������֧������ת������panic��

		func (v Value) Elem() Value
			* ����ָ��ָ��Ķ���
				i := 1
				v := reflect.ValueOf(&i)
				v.Elem().SetInt(10)
				fmt.Println(i)
			
			* Elem����v���еĽӿڱ��ܵ�ֵ��Value��װ������v���е�ָ��ָ���ֵ��Value��װ��
			* ���v��Kind����Interface��Ptr��panic�����v���е�ֵΪnil���᷵��Value��ֵ��

		
		func (v Value) Field(i int) Value
			* ǰ�� v ��һ�� struct�����ص� i ���ֶΣ������Ҫ���ڱ���
			* ���ؽṹ��ĵ�i���ֶΣ���Value��װ�������v��Kind����Struct��i�����panic

		func (v Value) FieldByIndex(index []int) Value
			* ������������ָ����Ƕ���ֶε�Value��ʾ���ȼ����������е�ֵ��ʽ���ñ���������v��Kind��Struct����panic

		func (v Value) FieldByName(name string) Value
			* ǰ�� v ��һ�� struct�������ֶ���ֱ�Ӷ�λ����
			* ���ظ�������Ϊname���ֶΣ���Value��װ��������������ֶμ������ֶΣ������v��Kind����Struct��panic�����δ�ҵ��᷵��Value��ֵ��
		
		func (v Value) FieldByNameFunc(match func(string) bool) Value
			* ���ظ����͵�һ���ֶ�������match���ֶΣ���Value��װ��������������ֶμ������ֶΣ������v��Kind����Struct��panic�����δ�ҵ��᷵��Value��ֵ��

		func (v Value) Float() float64
			* ����v���еĸ���������ʾΪfloat64�������v��Kind����Float32��Float64��panic
		
		func (v Value) Index(i int) Value
			* ǰ�� v �� Array, Slice, String ֮һ�����ص� i ��Ԫ�أ���ҪҲ�����ڱ�����ע�ⲻ��Խ��
			* ����v����ֵ�ĵ�i��Ԫ�ء����v��Kind����Array��Chan��Slice��String������i���磬��panic

		func (v Value) Int() int64
			* ����v���е��з�����������ʾΪint64�������v��Kind����Int��Int8��Int16��Int32��Int64��panic

		func (v Value) Interface() (i interface{})
			* ת��Ϊ�ӿ�����
			* ����������v��ǰ���е�ֵ����ʾΪ/������interface{}���ͣ����ȼ��ڣ�

		func (v Value) InterfaceData() [2]uintptr
			* ����v���еĽӿ�����ֵ�����ݡ����v��Kind����Interface��panic

		func (v Value) IsNil() bool
			* �ж� v �ǲ��� nil��ֻ�� chan, func, interface, map, pointer, slice �����ã��������ͻ� panic
			* �������v��Value��ֵ����panic��

		func (v Value) IsValid() bool
			* IsValid����v�Ƿ����һ��ֵ�����v��Value��ֵ�᷵�ؼ٣���ʱv����IsValid��String��Kind֮��ķ������ᵼ��panic��
			* ������������ͷ�������Զ������Value��ֵ�����ĳ������/���������˷Ƿ���Value�������ĵ�������ʽ��˵�����������


		func (v Value) IsZero() bool
		func (v Value) Kind() Kind
			* Kind����v���е�ֵ�ķ��࣬���v��Value��ֵ������ֵΪInvalid

		func (v Value) Len() int
			* ����v����ֵ�ĳ��ȣ����v��Kind����Array��Chan��Slice��Map��String��panic
		
		func (v Value) MapIndex(key Value) Value
			* ǰ�� v �Ǹ� map�����ض�Ӧ value
			* ����v����ֵ��key����ֵΪ����Ӧ��ֵ��Value��װ�����v��Kind����Map��panic��
			* ���δ�ҵ���Ӧֵ����v����ֵ��nilӳ�䣬�᷵��Value��ֵ��key�ĳ���ֵ�������ֱ�Ӹ�ֵ��v����ֵ���͵ļ����͡�

		func (v Value) MapKeys() []Value
			* ǰ�� v �Ǹ� map���������� key ��ɵ�һ�� slice
			* ����һ������v����ֵ�����м���Value��װ����Ƭ������Ƭδ�������v��Kind����Map��panic�����v����ֵ��nil�����ؿ���Ƭ����nil����

		func (v Value) MapRange() *MapIter
		func (v Value) Method(i int) Value
			* ����v����ֵ���͵ĵ�i���������Ѱ󶨣���v�ĳ���ֵ�ģ�״̬�ĺ�����ʽ��Value��װ��
			* ����ֵ����Call����ʱ��Ӧ���������ߣ�����ֵ���еĺ�������ʹ��v�ĳ�������Ϊ�����ߣ�����һ�������������i���磬����v�ĳ���ֵ�ǽӿ����͵���ֵ��nil������panic��

		func (v Value) MethodByName(name string) Value
			* ����v����Ϊname�ķ������Ѱ󶨣���v�ĳ���ֵ�ģ�״̬�ĺ�����ʽ��Value��װ��
			* ����ֵ����Call����ʱ��Ӧ���������ߣ�����ֵ���еĺ�������ʹ��v�ĳ�������Ϊ�����ߣ�����һ�������������δ�ҵ��÷������᷵��һ��Value��ֵ��

		func (v Value) NumField() int
			*  ǰ�� v �Ǹ� struct�������ֶθ���
			* ����v���еĽṹ������ֵ���ֶ��������v��Kind����Struct��panic

		func (v Value) NumMethod() int
			* ����v����ֵ�ķ������ķ�����Ŀ��

		func (v Value) OverflowComplex(x complex128) bool
			* ����v���еĸ�������ʾΪcomplex64�������v��Kind����Complex64��Complex128��panic

		func (v Value) OverflowFloat(x float64) bool
			* ���v����ֵ�����Ͳ���������ı�ʾx���᷵���档���v��Kind����Float32��Float64��panic

		func (v Value) OverflowInt(x int64) bool
			* ���v����ֵ�����Ͳ���������ı�ʾx���᷵���档���v��Kind����Int��Int8��Int16��Int32��Int64��panic
		
		func (v Value) OverflowUint(x uint64) bool	
			* ���v����ֵ�����Ͳ���������ı�ʾx���᷵���档���v��Kind����Uint��Uintptr��Uint8��Uint16��Uint32��Uint64��panic
		
		func (v Value) Pointer() uintptr
			* ��v���е�ֵ��Ϊһ��ָ�뷵�ء�
			* ����������ֵ����unsafe.Pointer���ͣ��Ա������Ա����ʽ����unsafe��ȴ�õ�unsafe.Pointer���ͱ�ʾ��ָ�롣
			* ���v��Kind����Chan��Func��Map��Ptr��Slice��UnsafePointer��panic��
			* ���v��Kind��Func������ֵ�ǵײ�����ָ�룬�����������������ֲ�ͬ�ĺ�����ֻ�ܱ�֤���ҽ���v���к���������ֵnilʱ������ֵΪ0��
			* ���v��Kind��Slice������ֵ��ָ����Ƭ��һ��Ԫ�ص�ָ�롣������е���ƬΪnil������ֵΪ0��������е���Ƭû��Ԫ�ص�����nil������ֵ������0��

		func (v Value) Recv() (x Value, ok bool)
			* ������v���е�ͨ�����ղ�����һ��ֵ����Value��װ����
			* ���v��Kind����Chan��panic������������ֱ����ȡ��ֵ���������ֵx��Ӧ��ĳ�����͵�v���е�ͨ����ֵ��okΪ�棻�����Ϊͨ���رն����أ�xΪValue��ֵ��okΪ�١�

		func (v Value) Send(x Value)
			* ������v���е�ͨ������x���е�ֵ�����v��Kind����Chan������x�ĳ���ֵ����ֱ�Ӹ�ֵ��v����ͨ����Ԫ�����ͣ���panic��

		func (v Value) Set(x Value)
			* ��ֵ
			* ��v�ĳ���ֵ�޸�Ϊx�ĳ���ֵ�����v.CanSet()���ؼ٣���panic��x�ĳ���ֵ������ֱ�Ӹ���v����ֵ�����͡�
		
		func (v Value) SetBool(x bool)
			* ����v�ĳ���ֵ�����v��Kind����Bool����v.CanSet()���ؼ٣���panic��

		func (v Value) SetBytes(x []byte)
			* ����v�ĳ���ֵ�����v����ֵ����[]byte���ͻ���v.CanSet()���ؼ٣���panic��

		func (v Value) SetCap(n int)
			* �趨v����ֵ�����������v��Kind����Slice����n���磨С�ڳ��Ȼ򳬳���������������panic

		func (v Value) SetComplex(x complex128)
			* ����v�ĳ���ֵ�����v��Kind����Complex64��Complex128����v.CanSet()���ؼ٣���panic��

		func (v Value) SetFloat(x float64)
			* ����v�ĳ���ֵ�����v��Kind����Float32��Float64����v.CanSet()���ؼ٣���panic��

		func (v Value) SetInt(x int64)
			* ����v�ĳ���ֵ�����v��Kind����Int��Int8��Int16��Int32��Int64֮һ����v.CanSet()���ؼ٣���panic��

		func (v Value) SetLen(n int)
			* �趨v����ֵ�ĳ��ȡ����v��Kind����Slice����n���磨С����򳬳���������������panic

		func (v Value) SetMapIndex(key, elem Value)
			* ������v��ӳ�����ͳ���ֵ���/�޸ļ�ֵ�ԣ����val��Value��ֵ������ɾ����ֵ�ԡ�
			* ���v��Kind����Map������v�ĳ���ֵ��nil������panic��
			* key�ĳ���ֵ�������ֱ�Ӹ�ֵ��v����ֵ���͵ļ����͡�val�ĳ���ֵ�������ֱ�Ӹ�ֵ��v����ֵ���͵�ֵ���͡�


		func (v Value) SetPointer(x unsafe.Pointer)
			* ����v�ĳ���ֵ�����v��Kind����UnsafePointer����v.CanSet()���ؼ٣���panic��

		func (v Value) SetString(x string)
			* ����v�ĳ���ֵ�����v��Kind����String����v.CanSet()���ؼ٣���panic��
		
		func (v Value) SetUint(x uint64)
			* ����v�ĳ���ֵ�����v��Kind����Uint��Uintptr��Uint8��Uint16��Uint32��Uint64����v.CanSet()���ؼ٣���panic��
		
		func (v Value) Slice(i, j int) Value
			* ����v[i:j]��v���е���Ƭ������Ƭ��Value��װ�������v��Kind����Array��Slice��String��panic�����v��һ������Ѱַ�����飬�����������磬Ҳ��panic

		func (v Value) Slice3(i, j, k int) Value
			* ��Slice��3�����汾������v[i:j:k] �����v��Kind����Array��Slice��String��panic�����v��һ������Ѱַ�����飬�����������磬Ҳ��panic��

		func (v Value) String() string
			* ����v���е�ֵ���ַ�����ʾ����Ϊgo��String�����Ĺ�����Value��String�����Ƚ��ر�
			* ��������ȡv����ֵ�ķ�����ͬ��v��Kind��Stringʱ�����ظ��ַ�����v��Kind����StringʱҲ����panic���Ƿ��ظ�ʽΪ"<T value>"���ַ���������T��v����ֵ�����͡�


		func (v Value) TryRecv() (x Value, ok bool)
			* TryRecv���Դ�v���е�ͨ������һ��ֵ�����������������v��Kind����Chan��panic��
			* ��������ɹ����յ�һ��ֵ���᷵�ظ�ֵ����Value��װ����true����������������Ľ��յ�ֵ������Value��ֵ��false�������Ϊͨ���رն����أ�����ֵx�ǳ���ͨ��Ԫ�����͵���ֵ��Value��false��

		func (v Value) TrySend(x Value) bool
			* TrySend������v���е�ͨ������x���е�ֵ�����������������v��Kind����Chan��panic��
			* ����ɹ����ͻ᷵���棬���򷵻ؼ١�x�ĳ���ֵ�������ֱ�Ӹ�ֵ��v����ͨ����Ԫ�����͡�

		func (v Value) Type() Type
			* ����v���е�ֵ�����͵�Type��ʾ��

		func (v Value) Uint() uint64
			* ����v���е��޷�����������ʾΪuint64������v��Kind����Uint��Uintptr��Uint8��Uint16��Uint32��Uint64��panic

		func (v Value) UnsafeAddr() uintptr
			* ����ָ��v�������ݵĵ�ַ��ָ�루��ʾΪuintptr���������߼���;�����v����Ѱַ��panic��
	
	# type ValueError struct {
			Method string
			Kind   Kind
		}
		func (e *ValueError) Error() string
	

---------------------
func
---------------------
	func Copy(dst, src Value) int
		* ��src�е�ֵ������dst��ֱ��src���ľ�����dst��װ����Ҫ������߶���slice��array����Ԫ��������ͬ��
	
	func DeepEqual(x, y interface{}) bool
		* ��Ƚ�2������
		* �����ж�����ֵ�Ƿ����һ�£�����������ͬ���ڿ���ʱ����Ҫ�ǻ������ͣ���ʹ��==��������Ƚ�array��slice�ĳ�Ա��map�ļ�ֵ�ԣ��ṹ���ֶν�������ȶԡ�
		* map�ļ�ֵ�ԣ��Լ�ֻʹ��==����ֵ����������ȶԡ�
		* DeepEqual����������ȷ����ѭ�������͡���������ֻ�ж���nilʱ����ȣ�����Ƭ������nil��Ƭ�����ῼ��array��slice�ĳ��ȡ�map��ֵ������

	func Swapper(slice interface{}) func(i, j int)
	func VisibleFields(t Type) []StructField
		* ����һ���ṹ�����е����пɼ��ֶΣ����������ṹ��Ա�е��ֶΡ�
