package myjava;
interface Inter
{
	void method();
}
class Test 
{
	static class Inner implements Inter   //这个是有名字的内部类
	{
		public void method()
		{
			System.out.println("有名字的内部类");
		}
	}
	static public Inter function()
	{
		return new Inter(){				//这个ruturn后面是一个匿名的内部类。
			public void method()
			{
				System.out.println("匿名内部类");
			}
		};
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		new Test.Inner().method();//有名字的内部类方法调用
		Test.function().method();//匿名内部类的方法调用
		//首先。这个代码。很显然是Test类在调用一个static成员。而且。
		//后面跟上了.method();很显然，返回类型是一个对象。因为在调用这个方法。
	}
}