import java.util.*;
class Student implements Comparable
{
	private String name;
	private int age;
	Student(String name,int age)
	{
		this.name = name;
		this.age = age;
	}
	public int compareTo(Object obj)
	{
		if(!(obj instanceof Student))
		{
			throw new RuntimeException("不是学生对象");
		}
		else
		{
			Student stu = (Student)obj;
			if(this.age > stu.age)
			{
				return 1;
			}
			else if(this.age == stu.age)
			{
				return this.name.compareTo(stu.name);
			}
			else
			{
				return -1;
			}
		}
	}
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
}

public class Demo1
{
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args)
	{
		TreeSet ts = new TreeSet();
		ts.add(new Student("Kevin",21));
		ts.add(new Student("Rocco",24));
		ts.add(new Student("Kevin",21));
		ts.add(new Student("Rocco",24));
		ts.add(new Student("Tony",21));
		Iterator it = ts.descendingIterator();
		while(it.hasNext())
		{
			Student stu = (Student)it.next();
			sop(stu.getName()+"	"+stu.getAge());
			//sop(it.next());
		}
	}
}