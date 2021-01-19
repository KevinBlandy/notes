-----------------------------
局部变量类型推断
-----------------------------
	# java10的新特性

	# 只能出现在局部变量中

	# 字面量的定义
		var val = "Hello World";
	
	# 接收其他方法的返回值
		public static void main(String[] args) throws Exception {
			var retVal = foo();
		}
		
		public static String foo() {
			return "";
		}
	
	# 循环中的定义
		List<Integer> list = List.of(1, 2, 3);
		for (var item : list) {
			System.out.println(item);
		}
		for (var x = 0 ;x < list.size() ;x ++) {
			var item = list.get(x);
			System.out.println(item);
		}
	
	# 泛型中的使用
		var list = new ArrayList<>();

		* 如果后面的语句, 不添加泛型的话, 那么这个默认为: Object
	
