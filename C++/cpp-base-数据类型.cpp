----------------------
数据类型
----------------------
	# 基本数据类型

		* 布尔类型

			bool
				* 表示真/假值，最小尺寸未定义（通常实现为1字节）

		* 字符类型

			char
				* 基本字符类型，至少 8 位，可以理解为 byte

			wchar_t
				* 宽字符，至少16位，编码由编译器决定（如 Windows 下 UTF-16，Linux 下 UTF-3）

			char16_t
				* Unicode字符(UTF-16)，至少16位

			char32_t
				* Unicode字符(UTF-32)，至少32位

		* 整数类型

			short
				* 至少16位

			int
				* 至少16位(通常 32 位在现代系统)，通常被设计为机器的最自然大小(32位或64位)

			long
				* 至少32位

			long long
				* 至少64位(C++11引入)

		* 浮点类型：

			float
				* 单精度 32 位，至少 6 位有效数字

			double
				* 双精度 64 位，至少 10 位有效数字

			long double
				* 扩展精度 96 或 128 位，至少 10 位有效数字(通常更多)

	# 符号
		* 除去 bool 和扩展的字符类（ char 以外）型外，其他类型可以划分为带符号、不带符号的类型。
		* 默认是带符号的，即: signed
		* 可以设置为不带符号的: unsigned
		* 需要注意的是 char 到底是 unsigned char 还是 signed chart，标准没有明确规定，由编译器决定。
	
	# 字面量
		* 整形字面量
	
			* 十进制
				* 总是 signed
				* 其类型是 int \ long \ long long 中尺寸最小的那个。
				* 严格来说，十进制的字面值不会是负数（尽管可以存储在 signed 中）。对于 -110 来说， 110 才是字面量，负号的作用是对字面量取负。
			
			* 其他进制
				* 二进制 0b 开头：0b110/0B110
				* 八进制 0 开头： 07
				* 十六进制 0x 开头：0xFF/0xff
				
				* 可能是 signed 也可能是 unsigned
					* 编译器会根据字面量的值选择 能表示该值的最小类型，优先选择带符号类型，但若数值超出所有带符号类型的表示范围，则选择无符号类型。

				* 类型是下列尺寸中最小的一个： 
					int / unsigned int
					long / unsigned long
					long long / unsigned long long 
			
			* 如果字面量大到了没有合适的类型来存储，就会产生错误
		
		* 浮点字面量
			
			* 默认是 double
		
		* 字符和字符串
			
			* 单引号表示字符 'A'
			* 双引号表示字符串 "ABC"

			    // 字符串字面量值，可以分多行写
				std::cout << "1"
					"2"
					"3"
					"4"
				<< std::endl;  // 输出 1234
		
		* bool 类型
			true/false
		

	# 指定字面量的类型
		* 字符和字符串通过前缀指定字面量的类型

			u'a'			unicode 16 字符			char16_t
			U'a'			unicode 32 字符			char32_t
			L'a'			宽字符					wchar_t
			u8"a"			UTF8(仅用于字符串字面常量)	char
		
		* 整形字面值后缀
	
			u/U			unsigned
			l/L			long
			ll/LL		long long
		
		* 浮点字面值后缀
			f/F		float
			l/L		long double
	

----------------------
类型转换
----------------------
	# 转换
		
		* bool 值会被转换为 0/1，反过则是，非 0 表示 true 其他表示 false

		* 当给 unsigned 无符号数设置超出范围的值时，结果是值对无符号类型表示的总数的取模。
			* 具体来说，转换后的值等于该负数加上无符号类型的最大值加 1（即模数）。

		* 当给 signed 有符号数设置超出范围的值时，结果是未定义的，此时程序可能继续执行，也肯能崩溃，也可能产生垃圾数据。

	
	# 有符号和无符号运算中的自动转换
		
		* 表达式中对 unsigned 和 signed 进行运算的时候，会自动把 signed 转换为 unsigned
	
			unsigned u = 10;	// 无符号类型
			int i =  -42;		// 有符号类型，是个负数
			std::cout << u + i << std::endl;    // 4294967264

			* u 在运算前，先转换为了和 i 一样的类型，即 unsigned int
		
		* 对 unsigned 进行减运算时，结果都会被转换为 unsigned 即保证结果不可能为负数

			unsigned x = 10;
			unsigned y = 5;
			std::cout << y - x << std::endl;  // 4294967291

		
		* 特别要注意在循环上使用 unsigned 的坑
			
			for (unsigned int i = 10; i >=0; i --){
				std::cout << i << std::endl;
			}


			1. 当 [condition] 判断 i 等于 0 的时候。符合条件，执行代码块。
			2. 执行 [expression] 表达式，i - 1。问题来了。
				* i （0）是无符号的，执行 -1 操作，会变成 -1
				* 但是 unsigned 不允许有负数，会把 -1 变成正数，即 4294967295
				* i 最终的值是正数
			3. 继续执行 [condition] 判断， i >= 0，条件成立，继续执行
			4. 死循环


		* 尽量不要混用有符号数和无符号数，把握不住。
	

