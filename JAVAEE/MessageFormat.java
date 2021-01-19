MessageFormat
MessageFormat
	content = MessageFormat.format(content, form.getCode());//把content中的{},都替换成form.getCode();

--
String s = MessageFormat.format("{0}获{1}错误","用户名","或者密码");//可变换参数,需要指定模板中的占位符的值,有几个占位符就提供几个参数
		System.out.println(s);//s == 用户名或者密码错误
		//{0},{1},{2}
format();
	这个方法中的第一个参数就是包含了占位符的字符串！
	关于占位符就是:{0}{1}{2}{3}... ...可以有多个！
	那么在执行这个方法的时候.这个方法就会把参数一,也就是模板中的这些个{0}{1}{2}都依次替换成后面的参数
	按照顺序来.后面的参数是个可变参数,可以给出多个值
