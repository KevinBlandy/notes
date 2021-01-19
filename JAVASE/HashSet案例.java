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
	Person(){}
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
	public int hashCode()
	{
		return 60;
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
		hs.add(new Person("JAVA1",2));
		hs.add(new Person("JAVA1",3));
		hs.add(new Person("JAVA2",1));
		hs.add(new Person("JAVA3",2));
		hs.add(new Person("JAVA2",1));
		hs.add(new Person("JAVA1",2));
		Iterator it = hs.iterator();
		while(it.hasNext())
		{
			Person p = (Person)it.next();
			sop(p.getName()+"	"+p.getAge());
		}
	}
}