-----------------
ģ�鳣��
-----------------

-----------------
type
-----------------
	# Interface
		type Interface interface {
			Len() int
			Less(i, j int) bool
			Swap(i, j int)
		}

		* ����ӿڣ�ʵ��������ӿں󣬾;߱��ˡ��������ԡ�

	# type StringSlice []string
	# type Float64Slice []float64
	# type IntSlice []int
		* ��Щ���Ͷ�ʵ���� sort.Interface ����ӿڣ�������������


-----------------
����
-----------------
	func Sort(data Interface)
		* ��ָ�������ݽ�������
	func Reverse(data Interface) Interface 
		* ��ת����
	func IsSorted(data Interface) bool
		* �ж��Ƿ��������
	
	func Search(n int, f func(int) bool) int 
		* 2��������ʵ�֣� �±귶Χ�� 0 - n��ͨ�� f �ж��Ƿ����У����ؾ����±�
	
	func Find(n int, cmp func(int) int) (i int, found bool)
		* ������Search�����Ǹ����ã�������һ������Ĳ���ֵ�������Ƿ��ҵ���ȵ�ֵ��
		
	func SearchInts(a []int, x int) int 
	func SearchFloat64s(a []float64, x float64) int 
	func SearchStrings(a []string, x string) int 
	
	func Slice(slice interface{}, less func(i, j int) bool) 
		* ��ָ������Ƭ�������򣬲���֤������ȶ��ԡ�

	func SliceStable(slice interface{}, less func(i, j int) bool)
		* ��ָ������Ƭ�������򣬱�֤������ȶ���

	func SliceIsSorted(slice interface{}, less func(i, j int) bool) bool
		* ������Ƭ�Ƿ�������ģ����slice���Ǹ�����Ƭ�������׳��쳣

	func Ints(a []int)
	func Float64s(a []float64) 
	func Strings(a []string)
		* ��ָ������Ƭ��������

	func IntsAreSorted(a []int) bool 
	func Float64sAreSorted(a []float64) bool 
	func StringsAreSorted(a []string) bool 
		* �ж�ָ������Ƭ���Ƿ��������
	
	
