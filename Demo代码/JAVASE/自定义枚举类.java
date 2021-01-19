class Season//季节类
{
	//枚举类，跟单例有点相似。
	//1,提供类的数学，声明final,private
	private final String name ;//名字
	private final String desc;//秒速
	private Season(String name,String desc)
	{
		//2在构造器中进行初始化。一旦初始化不能被修改。
		this.name = name;
		this.desc = desc;
	}
	//3创建枚举类的对象
	public static final Season SPRING = new Season("春天","春暖花开");
	public static final Season SUMMER = new Season("夏天","夏日炎炎");
	public static final Season AUTUME = new Season("秋天","秋高气爽");
	public static final Season WINTER = new Season("冬天","天寒地冻");
	//4通过公共方法来调用属性。
	public String getName() 
	{
		return name;
	}
	public String getDesc() 
	{
		return desc;
	}
	public void show()
	{
		System.out.println("这里一个季节");
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		Season s = Season.SPRING;
		System.out.println(s.getDesc());
	}
}