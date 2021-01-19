import java.util.*;
class Duilie
{
	private LinkedList link;
	Duilie()
	{
		link = new LinkedList();
	}
	public void myAdd(Object obj)
	{
		link.addFirst(obj);
	}
	public Object myGet()
	{
		return link.removeLast();
	}
	public boolean isNull()
	{
		return link.isEmpty();
	}
}
public class Demo
{
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args)
	{
		Duilie li = new Duilie();
		li.myAdd("JAVA01");
		li.myAdd("JAVA02");
		li.myAdd("JAVA03");
		li.myAdd("JAVA04");
		li.myAdd("JAVA05");
		sop(li.isNull());
		sop(li.myGet());
		while (!(li.isNull()))
		{
			sop(li.myGet());
		}
	}		

}
