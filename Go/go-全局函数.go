---------------------
ȫ�ֺ���
---------------------
	# �� builting ģ����


---------------------
ȫ�ֱ���
---------------------

---------------------
ȫ�ֺ���
---------------------
	func new(Type) *Type
		* ���ݲ������ͣ�����һ���µ�ֵ�����ص�������ָ��
	
	func make(t Type, size ...IntegerType) Type
		* ���ڴ�����map, slice, chan �ĺ���
		
	func len(v Type) int
		* ����ָ�����ݵĳ��ȣ������Ƭ��nil������0
	
	func cap(v Type) int
		* ����ָ�����ݵ�cap�������Ƭ��nil������0

	func append(slice []Type, elems ...Type) []Type
		* ��arr���棬���һ�����߶��val�������µļ���
		* �����Ƭ��nil����ᴴ��һ���µ���Ƭ�����������������
	
	func delete(m map[Type]Type1, key Type)
		* ��mapɾ��Ԫ�أ����Ԫ�ز����ڣ��������κβ���
		
	func copy(dst, src []Type) int
		* src �е�Ԫ�ؿ����� dst �У�����ֵΪ�����ɹ���Ԫ�ظ���
		* ���dstΪnil���򷵻�0������ִ��copy����
		* ��� src �� dst �����ͽض�
		* ��� src �� dst �̣���ֻ���� src �ǲ���
	
	func complex(r, i FloatType) ComplexType
	func real(c ComplexType) FloatType
	func imag(c ComplexType) FloatType
	func close(c chan<- Type)
	func panic(v interface{})
	func recover() interface{}
		
	func print(args ...Type)
	func println(args ...Type)
		* �����Ϣ����׼������

	func clear[T ~[]Type | ~map[Type]Type1](t T)
		* ��� slice�������� map
		* �������飬�������Ԫ�ض�����Ϊ0ֵ������ı䳤�Ⱥ�����
				arr := []string{"1", "2", "3"}
				clear(arr)
				fmt.Println(len(arr)) // 3
		* ���� map�����������Ԫ�أ����һ����map
				dict := map[string]any{
					"1": 1,
					"2": 2,
				}
				clear(dict)
				fmt.Println(len(dict)) // 0

	func max[T cmp.Ordered](x T, y ...T) T
	func min[T cmp.Ordered](x T, y ...T) T
		* ������С�����ֵ
	


