-----------------
unsafe
-----------------
	# ̫���ڵײ�İ�������������ʹ��

-----------------
����
-----------------

-----------------
type
-----------------
	# type ArbitraryType int
		func Slice(ptr *ArbitraryType, len IntegerType) []ArbitraryType
		func SliceData(slice []ArbitraryType) *ArbitraryType

	# type IntegerType int

	# type Pointer *ArbitraryType

		* ������C�е� void* ����ָ��
		* �����໥�Ƚϣ����Ժ�nil���бȽ�

		* �κ����͵�ָ�붼���Ա�ת��Ϊ Pointer
		* Pointer ���Ա�ת��Ϊ�κ����͵�ָ��
		* uintptr ���Ա�ת��Ϊ Pointer
		* Pointer ���Ա�ת��Ϊ uintptr

		func Add(ptr Pointer, len IntegerType) Pointer

-----------------
method
-----------------
	func Sizeof(x ArbitraryType) uintptr
		* ����x���ڴ��еĴ�С
		* ����x�����Ǳ��ʽ�����������������Ա��ʽ��ְ

	func Offsetof(x ArbitraryType) uintptr
		* ���ؽṹ���Ա���ڴ���λ�þ�����ʼ�����ֽ���
		* ���������ǽṹ���Ա

			type Member struct {
				Id       int64
				Name     string
				Birthday time.Time
			}
			fmt.Println(unsafe.Offsetof(Member{}.Id))       // 0
			fmt.Println(unsafe.Offsetof(Member{}.Name))     // 8
			fmt.Println(unsafe.Offsetof(Member{}.Birthday)) // 24

	func Alignof(x ArbitraryType) uintptr
		* ���ز���������Ҫ����ı���
	
	func String(ptr *byte, len IntegerType) string
	func StringData(str string) *byte


