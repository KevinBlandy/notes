/*
	单例设计模式之  饿汉式
*/
class Single
{
	private int num;
	private Single(){}
	void setNum(int num)
	{
		this.num = num;
	}
	int getNum()
	{
		return num;
	}
	private static Single s = new Single();
	public static Single getSingle()
	{
		return s;
	}
}	
class Demo
{
	public static void main(String[] args)
	{
		Single s = Single.getSingle();
		s.setNum(5);
		Single s1 = Single.getSingle();
		System.out.println(s1.getNum());
	}
}