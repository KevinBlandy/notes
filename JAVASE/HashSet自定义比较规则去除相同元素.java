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
		if (!(obj instanceof Person))
		{
			return false;
		}
		Person p = (Person)obj;
		return this.name.equals(p.name) && this.age == p.age;
	}
	public int hashCode()
	{
		return name.hashCode() + age;
		//return 10;
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
		HashSet hs = new HashSet();
		hs.add(new Person("Kevin",21));
		hs.add(new Person("Rocco",24));
		hs.add(new Person("Kevin",21));
		hs.add(new Person("Rocco",24));
		hs.add(new Person("Tony",21));
//		
		sop(hs.contains(new Person("Kevin",21)));
		sop(hs.remove(new Person("Kevin",21)));
		
		Iterator it = hs.iterator();
		while(it.hasNext())
		{
			Person p = (Person)it.next();
			sop(p.getName()+"	"+p.getAge());
		}
	}
}