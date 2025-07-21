-----------------
stdexcept
-----------------
	# 标准异常定义
	
		https://en.cppreference.com/w/cpp/header/stdexcept.html


-----------------
简介
-----------------

	namespace std {
		class logic_error;
			* 程序逻辑错误
			
		class domain_error;
			* 逻辑错误：参数对应的结果值不存在
			
		class invalid_argument;
			* 逻辑错误：无效参数
			
		class length_error;
			* 逻辑错误：视图创建一个超出类型最大长度的对象
			
		class out_of_range;
			* 逻辑错误：使用一个超出有效范围的值
		
		class runtime_error;
			* 运行时异常，只有在运行时才能检测出的问题

		class range_error;
			* 运行时错误：生成的结果超出了有意义的值域范围			
			
		class overflow_error;
			* 运行时异常：计算上溢
			
		class underflow_error;
			* 运行时异常；计算下溢
	}
