import java.util.*;
public class Demo
{
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args)
	{
		ArrayList al = new ArrayList();
		al.add("JAVA01");
		al.add("JAVA01");
		al.add("JAVA02");
		al.add("JAVA02");
		al.add("JAVA03");
		al.add("JAVA03");
		al.add("JAVA04");
		al.add("JAVA04");
		al.add("JAVA05");
		sop(singleElements(al));
	}
	public static ArrayList singleElements(ArrayList al)
	{
		ArrayList newAl = new ArrayList();
		Iterator it = al.iterator();
		while (it.hasNext())
		{
			Object obj = it.next();
			if (!(newAl.contains(obj)))
			{
				newAl.add(obj);
			}
		}
			return newAl;
	}
}