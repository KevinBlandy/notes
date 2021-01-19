----------------------------
Formattable					|
----------------------------
	* 是一个接口,只有一个抽象方法
		 void formatTo(Formatter formatter, int flags, int width, int precision);

	* 如果类实现了该接口,那么在使用字符串(%s) format 实例的时候会调用该方法(否则会调用 toString())

