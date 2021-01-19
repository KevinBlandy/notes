
class Single
{
	private int num;
	 int getNum()
	{
		return num;
	}
	 void setNum(int num)
	{
		this.num = num;	
	}
	private Single(){}
	private static Single s = null;
	public static Single getSingle()
	{
		if (s==null)
		{
			synchronzied(Single.class)
			{
				if(s==null)
				{
					s = new Single();
				}
			}
		}
		return s;
	}
}
class Demo1
{
	public static void main(String[] args)
	{
		Single s = Single.getSingle();
		s.setNum(5);
		Single s1 = Single.getSingle();
		System.out.println(s1.getNum());
	}
}