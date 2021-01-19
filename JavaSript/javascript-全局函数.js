--------------------
Index				|
--------------------
	typeof(test);
		* 查看指定变量的数据类型

	encodeURI()
		* 把字符串编码为 URI。
		* 可以把中文,空格,进行URI编码
		* 该方法不会对 ASCII 字母和数字进行编码，也不会对这些 ASCII 标点符号进行编码： - _ . ! ~ * ' ( ) 。

	encodeURIComponent()	
		* 把字符串编码为 URI 组件。
		* 不仅仅是中文,空格,把符号也给转码了
			:
			//
			?
	
	decodeURI()	
		* 解码某个编码的 URI。

	decodeURIComponent()	
		* 解码一个编码的 URI 组件。

	
	eval()	
		* 计算 JavaScript 字符串，并把它作为脚本代码来执行。
		* 把字符串当作JAVA脚本执行
			eval("var user = {name:'KevinBlandy'}");
			println(user.name);
		* 就是一个JS解析器,很叼
			var str = "{name:'KevinBlandy',age:18}";
			var obj = eval("("+str+")");

	isFinite()
		* 当参数不是 NaN,Infinity,-Infinity	的时候返回 true

	isNaN()
		* 检查某个值是否是数字。
		* 如果你是数字,返回 false
		* 如果你不是数字,返回 true
		* 数字类型的字符串,也被认为是数字


	Number()
		* 把对象的值转换为数字,如果参数不标准,就会导致转换失败
		* 如果转换失败,返回 NaN

	parseInt()
		* 解析一个字符串并返回一个整数。
		* 任何0x/0x都被会解析为16进制
		* 对于小数,会直接去掉小数
		* 对于表达不规范的数值字符串,如果第一个字符串是数值,那么就会进行解析,直到遇到非数值字符串,或者字符串末尾.
		* 如果转换失败,返回 NaN
	
	parseFloat()
		* 解析一个字符串并返回一个浮点数。
		* 同上

	String()
		*　把对象的值转换为字符串。

	
	escape()	
		* 对字符串进行编码。

	unescape()
		*对由 escape() 编码的字符串进行解码。
	
