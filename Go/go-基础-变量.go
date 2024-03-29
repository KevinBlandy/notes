-------------------------
变量
-------------------------
	# Go语言中的局部变量如果声明了不使用，会异常

	# 基本数据类型
		类型			默认值		内存大小								说明
		[有符号]----------------------------------------------------------------------------------------------------
		int				0			32位系统上是int32，64位系统上是int64	有符号数字常量的默认类型	
		int8						1字节	
		int16						2字节
		int32						4字节
		int64						8字节

		[无符号]----------------------------------------------------------------------------------------------------
		uint						32位系统上是uint32，64位系统上是uint64	无符号数字常量的默认类型
		unit8						1个字节
		unit16						2个字节
		unit32						4个字节
		unit64						8个字节
		
		[无符号整形，指针]------------------------------------------------------------------------------------------
		unitptr			nil			32位系统是4字节，64系统是8字节

		[浮点数，遵循 IEEE 754标准]---------------------------------------------------------------------------------
		float32						4个字节									最大值，math.MaxFloat32=3.4028234663852886e+38
		float64						8个字节									最大值，math.MaxFloat64=1.7976931348623157e+308，默认的小数常量类型
		
		[复数]------------------------------------------------------------------------------------------
		complex128					16个字节								实部和虚部都是8个字节
		complex64					8个字节									实部和虚部都是4个字节

		[boolean]------------------------------------------------------------------------------------------
		bool			false												

		[ascii字符]------------------------------------------------------------------------------------------
		byte			0			1个字节									它其实是uint8的别名，可以用来标识一个ascii码，使用单引号包裹

		[unicode字符]------------------------------------------------------------------------------------------
		rune						4个字节									它其实是int32的别名，可以用来表示一个utf8字符，使用单引号包裹

		[字符串]------------------------------------------------------------------------------------------
		string			""													使用双引号，或者反引号包裹

		[error]------------------------------------------------------------------------------------------
		error

		* 使用的时候，一般用 int,uint 这类默认了长度的变量， 不要用unit32之类指定了长度的类型，怕导致系统移植困难。

	

	# 变量类型之间的数据转换
		* 这里面没有隐式转换，必须强制转换
		* 类型不一样，不能直接赋值，需要转换
			v1 := 1
			var v2 int64 = v1	// cannot use v1 (type int) as type int64 in assignment

		* 类型名称本身就是一个函数，可以强转其他类型，前提是允许转换
			var val int32 = 10;
			val1 := int64(val) // 小转大，不需要担心经度丢失
		
		* 大转小，如果溢出，会抛出异常
			val := int8(128)  // constant 128 overflows int8
	

	# 进制的表示
		* 2进制，使用0b开头	
			0b110 => 6

		* 8进制，使用0开头
			077 => 63

		* 16进制，使用0x开头
			0xFF => 255
	

	# 变量声明初始化
		* 使用var声明类型，并且初始化
			var name type = exression;
		
			* 类型和表达式，可以省略其中一个，不能都省略
		
		* 同时声明多个变量，都是相同类型，然后再赋值
			var a, b, c int
			a, b, c = 1, 2, 3

			var a, b, c  = 1, 2, 3

		
		* 同时声明多个变量，都是不同类型，不赋值
			var  (
				a int
				b bool
				c string
			)

		* 同时声明多个变量，都是不同类型，需要进行初始化
			var b, f, s = true, 2.3 "fout";
		
		* 象牙符声明和初始化局部变量
			name := exression
			i, j := true, 123

			* 会自动的根据exression推断类型
			* 只能在局部使用，支持同时声明并初始化多个
			
			* 海象运算符允许声明已经存在的变量，会进行修改
				a := false;    // a 变量值为 false
				c, a := 1, true // 海象运算符会重置 a 的值
				fmt.Println(c)		// 1
				fmt.Println(a)		// true
			
			* := 至少声明一个新的变量，否则无法编译通过
				c, a := 1, true 
				c, a := 2, false  // no new variables on left side of :=

	
	# 匿名变量
		* 下划线: _ 
		* 它表示忽略这个变量，用不着，它不会分配内存
	
	# 常量的声明初始化
		* 这些都是常量
			true
			false
			iota
			nil			
		
		* 常量使用 const 声明，声明的同时，必须赋值，一旦初始化后不能修改
		* 声明的方式跟var差不多
			const val = 1
			const val int =  1

		* 一次性声明多个常量，不同值
			const  (
				a = 1
				b = true
				c = "Hello"
			)
		
		* 一次性声明多个常量，相同值
			const  (
				a = 1
				b		// 值跟上面相同，是1
				c = 2
				d		// 值跟上面相同，是2
			)
			* 声明多个常量，如果不初始化，则值和上面初始化的常量一样
		
		* 常量的右值，可以是一个编译期运算的常量值
			const val = 1 << 1
			
			// 异常，右边不是编译期能算出来的数据，是要运行时才能算出来的
			const val = os.Getenv("JAVA_HOME") // onst initializer os.Getenv("JAVA_HOME") is not a constant
		
		* Go的常量const，只支持数字，字符串和布尔，及他们类型的表达式
		* 因为const是属于编译时期的常量，即在编译时期就可以完全确定取值的常量。
		* 切片，数组，正则表达式等等需要在运行时分配空间和执行若干运算才能赋值的变量则不能用作常量
			const folders  = []string {}  // const initializer []string literal is not a constant

		
	# iota 常量计数器，只能在常量表达式中使用
		* 在const关键字出现的时候会初始值为0，const中每增加一行（必须是新增一行）常量声明 ，iota 会进行 +1 递增
			const (
				b = iota		// 0
				c				// 1
				d				// 2
				e = iota		// 3
				f				// 4
			)
			const (
				a = true		// 这里的  iota 值初始化为 0
				b = iota		// 1
				c				// 2
				d				// 3
				e = iota		// 4
				f				// 5
			)
			const (
				a = iota		// 0
				b				// 1
				_				// 匿名变量的声明，也会让 iota 自增
				c				// 3
			)
			const (
				a, b = iota + 1, iota +  2  	// 此时 iota的值是 0
				c, d = iota + 1, iota +  2		// 此时 iota的值是 1
			)
			fmt.Println(a, b, c, d) // 1 2 2 3
		
		* 如果后面的const赋值表达式是一样，可以省略
			const (
				a = iota << 1	// iota(0) << 1	，后面俩变量的表达式没写，也会按照这个计算来
				b				// iota(1) << 1
				c				// iota(2) << 1
				d = iota + 5	// iota(3) + 5	，后面俩变量的表达式没写，也会按照这个计算来
				e				// iota(4) + 5
				f				// iota(5) + 5
			)

			func main() {
				fmt.Println(a, b, c)		// 0 2 4
				fmt.Println(d, e, f)		// 8 9 10
			}	


		* 可以用来实现枚举
		* 可以使用匿名变量忽略0值的 iota 常量项
		* 通过位运算，来实现关系
			type EventType int
			const (
				sing EventType = 1 << iota
				dancing
				rap
				basketball
			)
			func main() {
				like := basketball | dancing | rap | sing
				log.Printf("%08b\n", like)		//  00001111
				if like & sing != 0 {
					log.Println("喜欢唱")
				}
				if like & rap != 0 {
					log.Println("喜欢rap")
				}
			}
		

		* 实现一个数据大小
			type ByteSize float64
			const (
				_           = iota // 通过赋予空白标识符来忽略第一个值
				KB ByteSize = 1 << (10 * iota)
				MB
				GB
				TB
				PB
				EB
				ZB
				YB
			)
			func (b ByteSize) String() string {
				switch {
				case b >= YB:
					return fmt.Sprintf("%.2fYB", b/YB)
				case b >= ZB:
					return fmt.Sprintf("%.2fZB", b/ZB)
				case b >= EB:
					return fmt.Sprintf("%.2fEB", b/EB)
				case b >= PB:
					return fmt.Sprintf("%.2fPB", b/PB)
				case b >= TB:
					return fmt.Sprintf("%.2fTB", b/TB)
				case b >= GB:
					return fmt.Sprintf("%.2fGB", b/GB)
				case b >= MB:
					return fmt.Sprintf("%.2fMB", b/MB)
				case b >= KB:
					return fmt.Sprintf("%.2fKB", b/KB)
				}
				return fmt.Sprintf("%.2fB", b)
			}

	# 作用域与初始化时间
		* 函数内部声明的变量，是局部变量，只能在函数内部访问，函数执行的时候才初始化
		* 函数外部声明的变量，包级别的声明，对于该包内的所有源文件都可见，这些变量的初始化在main函数开始之前就初始化了
	
		* 如果包里面的变量名称，是以大写开头，则表示可以被其他包下的源文件引用
		* 反之，如果以小写开头，只能被同包下的源文件引用
	
	

	

	
