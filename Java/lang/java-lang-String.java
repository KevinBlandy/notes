------------------------
String					|
------------------------
	# 字符串

------------------------
静态方法				|
------------------------
	String format(String format, Object... args) 
		# 格式化指定的字符串
	
	String join(CharSequence delimiter, CharSequence... elements) ;
		# 把多个 elements 用 delimiter 符号连接起来,组成新的字符串返回

------------------------
实例方法				|
------------------------
	native String intern();
		* 返回常量池中的某字符串,如果常量池中已经存在该字符串,则返回常量池中该对象的引用
		* 否则
			* JDK6及其以前是, 先复制到字符串池(永久代中), 然后返回字符串池中的字符串引用
			* JDK7及其以后是, 只在字符串池中记录该字符串对象第一次出现的引用, 然后返回(不需要复制到永久代了, 因为字符串常量池已经被移到了堆中)
				jdk1.7之后, 常量池不仅仅可以存储对象, 还可以存储对象的引用, 会直接将字符串的地址存储在常量池
			
		
			//  1.1在堆中创建"1"字符串对象
			//  1.2字符串常量池引用"1"字符串对象
			//  1.3s引用指向堆中"1"字符串对象
			String s = new String("1");

			// 2. 发现字符串常量池中已经存在"1"字符串对象，直接返回字符串常量池中对堆的引用(但没有接收)-->s引用还是指向着堆中的对象
			s.intern();

			// 3. 发现字符串常量池已经保存了该对象的引用了，直接返回字符串常量池对堆中字符串的引用
			String s2 = "1";

			// 4. s指向的是堆中对象的引用，s2指向的是在字符串常量池对堆中对象的引用
			System.out.println(s == s2);// false	

			---------------------------------------------
			 // 1. 在堆中首先创建了两个“1”对象
			// 1.1 +号运算符解析成stringBuilder，最后toString()，最终在堆中创建出"11"对象
			// 1.2 注意：此时"11"对象并没有在字符串常量池中保存引用
			String s3 = new String("1") + new String("1");

			// 2. 发现"11"对象并没有在字符串常量池中存在，于是将"11"对象在字符串常量池中保存当前字符串的引用，并返回当前字符串的引用
			s3.intern();

			// 3. 发现字符串常量池已经存在引用了，直接返回(拿到的也是与s3相同指向的引用)
			String s4 = "11";
			System.out.println(s3 == s4); // true
		
		* JDK6 及其以前, 字符串常量池在永久区中
		* JDK7 及其以后, 字符串常量池被移到了堆中
	
	boolean isEmpty()
		* 是否是空字符串
	
	boolean isBlank()
		* 判断字符串是否全是空白字符
	
	String strip()
	String trim()
		* 去除首尾的空格
		* 这俩方法体一摸一样...
	
	String stripLeading()
	String stripTrailing()
		* 去除首尾的空格
	
	String repeat(int count)
		* 把当前字符串重复多少次后返回
	
	Stream<String> lines()
		* 以Stream的形式返回, 字符中的每一行
	

	<R> R transform(Function<? super String, ? extends R> f)
		* 把字符串流转换为其他的对象

	String indent(int n)
		* 调整每一行的缩进