------------------------
Modifier				|
------------------------
	* 一个工具类,可以检查类的字段,方法,构造函数的访问级别
	* Method Field Class Constructor 都几乎具备方法来获取访问级别
		int getModifiers()


------------------------
Modifier-静态方法		|
------------------------
	boolean isAbstract(int mode)
		* 是否是抽象的

	boolean isFinal(int model)
		* 是否被 final 修饰

	boolean isNative(int model)
		* 是否是本地方法
	
	boolean isInterface(int model)
		* 是否是接口
	
	

	...
	String toString(int model)
		* 把指定的model,以String形式返回
	

