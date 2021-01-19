import java.util.*;
import java.util.Map.Entry;
class Student implements Comparable<Student>
{
	private String name;
	private int age;
	Student(String name,int age)
	{
		this.name = name;
		this.age = age;
	}
	public int compareTo(Student s)
	{
		int num = new Integer(this.age).compareTo(new Integer(s.getAge()));
		if(num==0)
		{
			return this.name.compareTo(s.name);
		}
		return num;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public String toString()
	{
		return name+":"+age;
	}
	public int hashCode()
	{
		return name.hashCode()+age*34;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Student))
		{
			throw new ClassCastException("类型不匹配");
		}
		Student s = (Student)obj;
		return this.name.equals(s.getName()) && this.age == s.getAge();
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		Map<Student,String> map = new HashMap<Student,String>();
		map.put(new Student("Kevin",21),"重庆");
		map.put(new Student("Litch",21),"四川");
		map.put(new Student("Rocco",24),"江西");
		map.put(new Student("Lenka",21),"广东");
		//----------第一种keySet
		Set<Student> set = map.keySet();
		Iterator<Student> it = set.iterator();
		while(it.hasNext())
		{
			System.out.println(map.get(it.next()));
		}
		//-----------第二种取出方式entrySet
		Set<Map.Entry<Student,String>> s = map.entrySet();
		Iterator<Entry<Student, String>> its = s.iterator();
		while(its.hasNext())
		{
			Map.Entry<Student, String> ma = (Map.Entry<Student, String>)its.next();
			System.out.println(ma.getKey()+"--"+ma.getValue());
		}
	}
}