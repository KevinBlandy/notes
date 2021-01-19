import java.util.*;
class Person
{
	private String name;
	private int age;
	Person(String name,int age)
	{
		this.name = name;
		this.age = age;
	}
	public String getName()
	{
		return name;
	}
	public int getAge()
	{
		return age;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Person))
		{
			return false;
		}
		Person p = (Person)obj;
		return this.name.equals(p.name) && this.age == p.age;
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
		ArrayList al = new ArrayList();
		al.add(new Person("Kevin",21));	//
		al.add(new Person("Kevin",22));
		al.add(new Person("Kevin",23));
		al.add(new Person("Kevin",21));
		al.add(new Person("Kevin",20));
		al.add(new Person("Kevin",20));
		al.add(new Person("Kevin",23));
		Iterator it = al.iterator();
		while(it.hasNext())
		{
			Person p = (Person)it.next();
			sop(p.getName()+"----"+p.getAge());
		}
		sop("================================");
		al = singleElements(al);
		Iterator bt = al.iterator();
		while(bt.hasNext())
		{
			Person p = (Person)bt.next();
			sop(p.getName()+"---"+p.getAge());
		}
		
	}
	public static ArrayList singleElements(ArrayList al)
	{
		ArrayList newAl = new ArrayList();
		Iterator it = al.iterator();
		while(it.hasNext())
		{
			Object obj = it.next();
			if(!(newAl.contains(obj)))
			{
				newAl.add(obj);
			}
		}
		return newAl;
	}
}