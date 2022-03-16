-----------------
����
-----------------
	RuneError = '\uFFFD'     // the "error" Rune or "Unicode replacement character"
	RuneSelf  = 0x80         // characters below RuneSelf are represented as themselves in a single byte.
	MaxRune   = '\U0010FFFF' // Maximum valid Unicode code point.
	UTFMax    = 4            // utf8�ַ�������ֽ���

-----------------
type
-----------------

-----------------
ģ�鷽��
-----------------
	func AppendRune(p []byte, r rune) []byte
	func FullRune(p []byte) bool 
	func FullRuneInString(s string) bool 

	func DecodeRuneInString(s string) (r rune, size int)
		* ��ȡָ���ַ����У���һ���ַ������ҷ�������ռ�õ��ֽ�����
			val := "Hello, ����"
			for len(val) > 0 {
				char, size := utf8.DecodeRuneInString(val)
				fmt.Printf("%c=%d\n", char, size)		// H=1 ... ��=3
				val = val[size:]
			}
	func DecodeLastRuneInString(s string) (r rune, size int) 
		* ͬ�ϣ������ȡ��һ���ַ�������ռ�õ��ֽ�����


	func DecodeRune(p []byte) (r rune, size int) 
		* ���ֽ������У���ȡ�ַ������ҷ������ĳ���

	func DecodeLastRune(p []byte) (r rune, size int)
		* ͬ�ϣ�����

	func RuneLen(r rune) int
		* ����ָ���ַ����ֽڴ�С
			fmt.Println(utf8.RuneLen('��')) // 3
			fmt.Println(utf8.RuneLen('y')) // 1

	func EncodeRune(p []byte, r rune) int 
		* ���ַ�r��ת��Ϊ�ֽڣ�д�뵽p��
			name := '��'
			bytes := make([]byte, utf8.RuneLen(name))
			size := utf8.EncodeRune(bytes, name)
			fmt.Println(size)		// 3
			fmt.Println(bytes)		// [228 189 153]

	func RuneCount(p []byte) int 
		* �����ֽ������У��ַ�������		

	func RuneCountInString(s string) (n int) 
		* �����Զ��ַ������ַ�������

	func RuneStart(b byte) bool 
	func Valid(p []byte) bool
	func ValidString(s string) bool 
		* �ж��Ƿ��ǺϷ���Unicode����
	
	func ValidRune(r rune) bool

