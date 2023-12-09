------------------------
密封类
------------------------
	# JDK 17 后新增的三个关键字
		sealed：（封闭）用于修饰类/接口，代表这个类/接口为密封类/接口；
		non-sealed：（非封闭）用于修饰类/接口，代表这个类/接口为非密封类/接口；
		permits：（允许）用于 extends 和 implements 之后，指定能够继承或实现封闭类的子类/接口；
	

	# 总结
		1. sealed		声明类/接口是一个密封类
		2. non-sealed	声明类/接口是一个非密封类

		2. permits		如果是密封类，那么设置允许继承、实现当前类的类
			* 如果当前类是 sealed 密封类，直接子类也必须要声明当前类是 sealed 还是 non-sealed


		public sealed interface Foo permits Bar, Zoo{}
			// sealed	密封类
			// permits	Bar 和  Zoo 可以实现此接口

		public non-sealed class Bar implements Foo {}
			// non-sealed	非密封类
		
		public sealed class Zoo implements Foo permits Qoo {}
			// sealed	密封类
			// permits	Qoo 可以实现此接口
		
		public non-sealed class Qoo extends Zoo {}
			// non-sealed	非密封类
		
		public class Koo extends Bar{}
			// 继承 非密封类 Bar，不需要声明 sealed 或者是 non-sealed

