------------------------
字符串
------------------------
	# 需要导入标准库
		#include <string>
		using std::string;

		* 因为某些历史原因，也为了与 C 兼容，所以 C++ 语言中的字符串字面值并不是标准库类型 string 的对象。
		* 切记，字符串字面值与string是不同的类型。
	
	# 初始化
 
		string s1;				// 空字符串 ""
		string s2 = s1;	
		string s3 = "Hello";
		string s4 = {"Hello"};
		string s5 {"Hello"};
		string s6 ("Hello");
		string s7(2, 'h');  // hh
			* 第二个参数是 char, 表示要重复的字符
		
	# 值的初始化

		* 对基本类型（如 int、float）成员：初始化为 0。
		* 对类类型成员：调用它的默认构造函数。
		
	# 转义
		* 使用 \ 进行转义
		* 注意，如果 \ 后面跟着的八进制数字超过 3 个，则只有前三个数字与 / 构成转移序列
			* "\1234" 表示两个字符，即八进制的 123 和字符 4
		
		* 而，十六进制 \x 会用到后面跟到的所有数字
			* "\x1234" 表示一个 18 位（两个字节）的字符
			* 大多数机器的 char 会占 8 位，所以上面这个例子可能会报错
		
------------------------
字符串操作、运算
------------------------
	# 比较
		
		* 支持使用 ==、>、<、>=、<=、!= 进行比较，大小写敏感，排序是根据字典中的顺序。

			string s1;
			string s2 = s1;

			cout << (s1 == s2) << endl; // 1
			cout << (s1 > s2) << endl;  // 0
			cout << (s2 > s1) << endl;  // 0
			cout << (s1 != s2) << endl; // 0
	
	# 支持使用 [] 根据索引访、修改问每个字节

		string s = "Hello";

		for (int i = 0; i < int(s.size()); i ++ ){
			cout << s[i] << endl;
		}

		* 越界访问会导致意外，千万要注意
		* 索引如果是带符号的，会被强制转换为无符号的 string::size_type
	
	# 使用 + 拼接

		string s1 = "Hello";
		string s2 = s1 + " CPP";
		cout << s2;  // Hello CPP

		* 注意，不能直接拼接字面量，也就是说 + 的两侧，必须要保证有一个对象是 string

			// 非法
			string s = "123" + "456";  // error: invalid operands of types 'const char [4]' and 'const char [4]' to binary 'operator+'
			
			// 合法，两侧有一个是 string
			string s = "123" + string("456");   // 123456
	
	# 可以使用输入/输出
	
		* 从标准输入时，会忽略前面的空白，并且遇到空白就停止
		
		string s;
		// 从 stdin 读取到 s
		cin >> s;
		// 输出 s 到 stdout
		cout << s;

	# size() 方法获取字符串长度
		
		* 返回的是字节数量，类型是 string::size_type , 无符号的整数

		auto size = string("你好").size(); // 6
		

	# empty() 方法空字符串判断
		
		cout << string("").empty();  // 1
		cout << string(" ").empty(); // 0


			
