/*
	模版方法
*/
abstract class Tool
{
	void getTime()
	{
		long start = System.currentTimeMillis();
		test();
		long end = System.currentTimeMillis();
		System.out.println("运行完毕这段程序花费了"+(end-start)+"毫秒");
	}
	abstract void test();
}
class Test extends Tool
{
	void test()
	{
		for (int x = 0;x < 10000 ;x++ )
		{
			System.out.println(x+" ");
		}
	}
}

class Demo2 
{
	public static void main(String[] agrs)
	{
		Test t = new Test();
		t.getTime();
	}
}