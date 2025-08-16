----------------------
����
----------------------
		
	

----------------------
type
----------------------
	# type Type interface
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
			* �����ֶεķ����ᱻ���㣻�������͵ķ��������������ֶε�ͬ��������
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
	
	# type Method struct {
			Name    string
			PkgPath string

			Type  Type
			Func  Value
			Index int 
		}

		* ��������
	
	
	# type Kind uint
		* �������ͱ�ʶ
		* Ԥ�����˵ı���
			const (
				Invalid Kind = iota	��ֵ
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
				Func				����
				Interface			�ӿ�
				Map
				Ptr
				Slice
				String
				Struct
				UnsafePointer
			)

		func (k Kind) String() string

	# type ChanDir int
		* ��ʶͨ�����͵ķ���

	# type StructField struct 
		PkgPath string
		Type      Type			�ֶ�����
		Tag       StructTag		�ֶ������tag
		Offset    uintptr 
		Index     []int			
		Anonymous bool			�Ƿ���������

		* �ṹ���ֶ�
	
	# type StructTag string
		* �ṹ���ϵı�ʶ

		func (tag StructTag) Get(key string) string 
		func (tag StructTag) Lookup(key string) (value string, ok bool)





----------------------
����
----------------------
	func DeepEqual(x, y interface{}) bool
	func MakeFunc(typ Type, fn func(args []Value) (results []Value)) Value 
	func Swapper(slice interface{}) func(i, j int)
	func TypeAssert[T any](v Value) (T, bool)

	func PtrTo(t Type) Type 

	func TypeOf(i interface{}) Type
		* ����ָ�����ݵ�Type�������ص���ʵ�ʵ�����
			var w, _ = os.Open("C:\\temp.txt")
			var t = reflect.TypeOf(w) // *os.File


	func ChanOf(dir ChanDir, t Type) Type
	func MapOf(key, elem Type) Type
	func FuncOf(in, out []Type, variadic bool) Type
	func SliceOf(t Type) Type
	func StructOf(fields []StructField) Type
	func ArrayOf(count int, elem Type) Type

