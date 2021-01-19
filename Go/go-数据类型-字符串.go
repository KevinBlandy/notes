----------------------
字符
----------------------
	# 字符使用单引号
		name  := '余'

		* 一般使用 byte(int8), runne(int32) 来表示一个字符
		* 默认是： runne(int32)
	
	# Unicode的支持
		* Unicode至少占用2个字节，一般使用 int16 或者 int32表示
			var ch1 int32 = '\u0041'
			var ch2 int32 = '\u03B2'
			
		* 在书写 Unicode 字符时，需要在 16 进制数之前加上前缀\u或者\U
		*  '\u0041'			4个字节
		*  '\U00101234'		8个字节


----------------------
字符串
----------------------
	# strin方法
		* 如果参数是字符串，则是多此一举
		* 如果参数是数字，则试图把数据转换为unicode编码的字符串
			fmt.Println(string(65)) // "A", not "65"
			fmt.Println(string(0x4eac)) // "京"

	# 基本的字符串使用双引号包裹
		var val string = "Hello"
	
	# 转意字符
		\\
		\r
		\n
		\t
		\'
		\"
	
	# 原始字符串，使用反引号包裹
		var val = `
			Hello \t \r \n 
			\\\
		`
		* 特殊字符不需要转义
		* 原始怎么样，输出便是怎么样
	

	# 字符串可以直接使用 + 进行拼接
		var name = "Hello" + " World"
	
	# 获取字符串的字节长度，使用全局函数 len
		 var size int = len("Hello")
		
	# 获取字符长度，使用"unicode/utf8"包中的方法
		import (
			"fmt"
			"unicode/utf8"
		)
		func main() {
			str := "你好啊，Go语言"
			fmt.Println(utf8.RuneCountInString(str)) // 8
		}


	# 字符串与字节数组字符数组的转换
		* 字符串和节数组
			name := "Go语言"
			nameBytes := []byte(name)
			fmt.Println(nameBytes) // [71 111 232 175 173 232 168 128]

			nameStr := string(nameBytes)
			fmt.Println(nameStr) // Go语言
		
		* 字符串和字符数组
			name := "Go语言"
			nameChars := []rune(name)
			fmt.Println(nameChars) // [71 111 35821 35328]

			nameStr := string(nameChars)
			fmt.Println(nameStr) // Go语言
		
		* 字符串以指定的编码格式转换为字节数组
			TODO

		* 把字节数组以指定的格式转换为字符串
			TODO
		
		* unicode/utf8包也提供了UTF8和Unicode之间的转换。
		* 字符串是不可变数据，不能直接修改，只有转换为byte/rune数组，进行修改后再转换为新的字符串

	# 字符串的遍历
		* 使用 len() 遍历字节
			name := "Hello Go语言"
				for i := 0; i < len(name); i++ {
				fmt.Printf("%c \n", name[i])
			}

			* 遍历到了非ascii编码，会出现乱码

		* 使用 range 迭代每一个字符
			name := "Hello Go语言"，key是小标，value是rune
			for i, c := range name {
				fmt.Printf("index=%d char=%c \n", i, c)
			}


	# 字符串的切片后，返回的还是字符串
		str := "Hello World"
		sub := str[1:]
		fmt.Printf("%T=%s\n", sub, sub)  // string=ello World
	
	# 字符串的比较，可以使用 >,<,==
		* 比较的本质上是挨着比较每一个字节，直到比较出结果
	


----------------------
相关类
----------------------
	# 操作字符串的类
	# 有四个包，对字符串很重要，它们提供了很多字符串的操作方法
		bytes
		strings
		strconv
		unicode
	
