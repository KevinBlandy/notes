----------------------------
Console						|
----------------------------
	* jdk1.6新增类库,用于读取屏幕输入
	* 构造
		Console console = System.console();


----------------------------
Console-实例方法/属性		|
----------------------------
	String readLine(String fmt, Object ... args)
		* 读取输入

	char[] readPassword(String fmt, Object ... args)
		* 读取密码输入,屏幕上是 *
	
