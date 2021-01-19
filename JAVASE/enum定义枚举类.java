enum Season implements Info //，枚举类实现接口
{	
	//创建对象，多个对象之间用,隔开。最后一个用;结尾。
	SPRING("春天","春暖花开")
	{
		public void show()
		{
			System.out.println("春天");
		}
	},
	SUMMER("夏天","夏日炎炎")
	{
		public void show()
		{
			System.out.println("夏天");
		}
	},
	AUTUME("秋天","秋高气爽")
	{
		public void show()
		{
			System.out.println("秋天");
		}
	},
	WINTER("冬天","天寒地冻")
	{
		public void show()
		{
			System.out.println("冬天");
		}
	};
	private final String name ;
	private final String desc;
	private Season(String name,String desc)
	{
		this.name = name;
		this.desc = desc;
	}

	public String getName() 
	{
		return name;
	}
	public String getDesc() 
	{
		return desc;
	}
	public void get()
	{
		System.out.println(this.name);
	}
}
interface Info//定义了一个接口
{
	abstract void show();
}
public class Demo
{
	public static void main(String[] args)
	{
		//获取该枚举类的所有对象.以该类的数组形式反悔。
		Season[] s1 = Season.values();
		for(Season s : s1)
		{
			System.out.println(s);
		}
		//获取指定名称的对象。要求传入的参数，也就是对象名要求是存在的。否则之间报错。
		Season s2 = Season.valueOf("SPRING");
		s2.show();
		s2.get();
		Season s3 = Season.valueOf("SUMMER");
		s3.get();
	}
}