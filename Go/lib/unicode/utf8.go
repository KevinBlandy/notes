-----------------
变量
-----------------
	RuneError = '\uFFFD'     // the "error" Rune or "Unicode replacement character"
	RuneSelf  = 0x80         // characters below RuneSelf are represented as themselves in a single byte.
	MaxRune   = '\U0010FFFF' // Maximum valid Unicode code point.
	UTFMax    = 4            // utf8字符的最大字节数

-----------------
type
-----------------

-----------------
模块方法
-----------------
	func AppendRune(p []byte, r rune) []byte
	func FullRune(p []byte) bool 
	func FullRuneInString(s string) bool 

	func DecodeRuneInString(s string) (r rune, size int)
		* 获取指定字符串中，第一个字符，并且返回它所占用的字节数量
			val := "Hello, 世界"
			for len(val) > 0 {
				char, size := utf8.DecodeRuneInString(val)
				fmt.Printf("%c=%d\n", char, size)		// H=1 ... 界=3
				val = val[size:]
			}
	func DecodeLastRuneInString(s string) (r rune, size int) 
		* 同上，逆向获取第一个字符，返回占用的字节数量


	func DecodeRune(p []byte) (r rune, size int) 
		* 从字节数组中，读取字符，并且返回它的长度

	func DecodeLastRune(p []byte) (r rune, size int)
		* 同上，逆向

	func RuneLen(r rune) int
		* 返回指定字符的字节大小
			fmt.Println(utf8.RuneLen('余')) // 3
			fmt.Println(utf8.RuneLen('y')) // 1

	func EncodeRune(p []byte, r rune) int 
		* 把字符r，转换为字节，写入到p中
			name := '余'
			bytes := make([]byte, utf8.RuneLen(name))
			size := utf8.EncodeRune(bytes, name)
			fmt.Println(size)		// 3
			fmt.Println(bytes)		// [228 189 153]

	func RuneCount(p []byte) int 
		* 返回字节数组中，字符的数量		

	func RuneCountInString(s string) (n int) 
		* 返回自定字符串中字符的数量

	func RuneStart(b byte) bool 
	func Valid(p []byte) bool
	func ValidString(s string) bool 
		* 判断是否是合法的Unicode编码
	
	func ValidRune(r rune) bool

