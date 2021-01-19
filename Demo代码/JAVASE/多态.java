
abstract class Student
{
	public abstract void study();
	public void sleep()
	{
		System.out.println("ÌÉ×ÅË¯");
	}
}
class BaseStudent extends Student
{
	public void study()
	{
		System.out.println("sthudy");
	}
	public void sleep()
	{
		System.out.println("×ø×ÅË¯");
	}
}
class AdvStudent extends Student
{
	public void study()
	{
		System.out.println("adv study");
	}
}
class DoStudent
{
	public void doSome(Student a)
	{
		a.study();
		a.sleep();
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		DoStudent ds = new DoStudent();
		ds.doSome(new BaseStudent());
		ds.doSome(new AdvStudent());
	/*	BaseStudent bs = new BaseStudent();
		bs.study();
		bs.sleep();
		AdvStudent as = new AdvStudent();
		as.study();
		as.sleep();
	}
	public void doSome(Student a)
	{
		a.study();
		a.sleep();
	}*/
	}
}