
import java.lang.reflect.*;
class Person 
{
	public String name;
	int id;
	private int age;
	Person(String name,int id,int gae)
	{
		this.name = name;
		this.id = id;
		this.age = age;
	}
	Person()
	{
		
	}
	private void pri()
	{
		System.out.println("这是private权限方法");
	}
	public static void sta()
	{
		System.out.println("这是一个静态的方法");
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
	public void show()
	{
		System.out.println("Im man");
	}
	public void display(String nation)
	{
		System.out.println("国籍是"+nation);
	}
	public String toString()
	{
		return "Person[name="+this.name+",age="+this.age+",id="+this.id+"]";
	}
}

public class Demo
{
	public static void main(String[] args) throws Exception
	{
//		test1();
//		test2();
//		test3();
//		test4();
//		test5();
		test6();
	}
	//正常的面向对象思维获取类成员
	public static void test1()
	{
		Person p = new Person();
		p.setAge(21);
		p.setName("kevin");
		System.out.println(p.toString());
		p.show();
		p.display("天朝"); 
	}
	//通过反射获取类成员
	public static void test2() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
	{
		//创建 cla  对应的运行时类Person类的对象。
		Class cla = Person.class;
		Person p = (Person)cla.newInstance();
		System.out.println(p);
		Field f1 = cla.getField("name");
		f1.set(p, "Litch");
		System.out.println(p);
		
		Field f2 = cla.getDeclaredField("age");
		f2.setAccessible(true);//访问私有变量
		f2.set(p, 22);
		System.out.println(p);
		
		
		Method m1 = cla.getMethod("show");//获取对象
		m1.invoke(p);
		Method m2 = cla.getMethod("display",String.class);
		m2.invoke(p,"美国");
	}
	public static void test3()
	{
		Person p = new Person();
		Class cl = p.getClass();//通过运行时类的对象。调用其getClass方法。返回其运行时候的类。
		System.out.println(cl);
	}
	//获取类加载器
	public static void test4() throws Exception
	{
		ClassLoader loader1 = ClassLoader.getSystemClassLoader();//获取类加载器
		System.out.println(loader1);
		ClassLoader loader2 = loader1.getParent();//获取它的父类
		System.out.println(loader2);
		ClassLoader loader3 = loader2.getParent();//获取核心类库加载器---直接是null。系统不会给你的！
		System.out.println(loader3);	
		Class cls = Person.class;
		ClassLoader loader4 = cls.getClassLoader();//获取加载自定义类，的类加载器。
		System.out.println(loader4);
		String className="java.lang.Object";	
		Class cla = Class.forName(className);	
		ClassLoader loader5 = cla.getClassLoader();//获取Object类的加载器
		System.out.println(loader5);				//很显然，Object是属于核心类。你拿不到加载器。返回的是个null。
	}
	//获取运行类的。对象，属性，并进行赋值操作等！
	public static void test5()throws Exception
	{
		Class cls = Person.class;
		Person p = (Person)cls.newInstance();//新建class文件对象
		//获取public属性的成员，进行赋值操作。
		Field name = cls.getField("name");//获取name属性。此属性的权限的是 public
		name.set(p,"Kevin");//指明对象时p。给它的name属性赋值.
		System.out.println(p);//打印这个对象
		//获取private属性的成员，进行赋值操作。
		Field age = cls.getDeclaredField("age");//获取age属性。注意这里的age属性private .获取方式跟上面有所不同
		age.setAccessible(true);//设置私有属性为可访问的。
		age.set(p, 21);//再给p对象的age属性赋值。如果不进过设置可访问。那么赋值会抛出异常，
		System.out.println(p);//打印这个对象
		//获取默认属性的成员，进行赋值操作。
		Field id = cls.getDeclaredField("id");  //此属性为默认权限。也要通过专门 获取private修饰的属性的方法来获取。
		id.setAccessible(true);	//也要进行设置，可访问。
		id.set(p,1);//对p对象的  id 进行赋值操作。同样也需要上一步的设置操作。不然会报错抛异常。
		System.out.println(p);//打印对象。因为P对象的类中已经覆写了toString();方法。所有会按照自定义方法来进行打印。
	}
	//获取运行类的。对象。方法。进行操作
	public static void test6()throws Exception
	{
		Class cls = Person.class;
		Person p = (Person)cls.newInstance();//根据Class方法。来创建对象
		Method m1 = cls.getMethod("show");//获取这个类中名字叫做show的方法。而且这个方法是个空参数。
		Object returnValue = m1.invoke(p);//运行这个方法。因为没有参数。那么就指明调用这个方法的对象就是了。不用再后面加上参数。如果你没有返回值。那么返回的就是一个null参数
		System.out.println(returnValue);//打印返回值是null。因为这个方法本身就是void无返回值
		Method m2 = cls.getMethod("toString");//获取这个有返回值的方法，没有参数
		String s = (String)m2.invoke(p);//运行的时候，会返回一个String类型的返回值
		System.out.println(s);//打印返回值。
		Method m3 = cls.getMethod("sta");//获取这个类中的静态方法(static)。空参数
		m3.invoke(Person.class);//因为是一个静态的。所有调用者可以直接是这个类！当然，也可以是对象。
		Method m4 = cls.getDeclaredMethod("pri");//获取这个类中的一个私有化方法 private。跟获取私有化成员属性一样！需要特定的方法。
		m4.setAccessible(true);//设置这个私有化方法。可以被访问。
		m4.invoke(p);//指定一个对象去运行这个私有化方法。
		//一般，如果不知道被调用的方法，或者属性的访问权限，那么就可以直接使用第二种。专门针对私有化成员的操作方法来进行操作！
	}
	//指定类的构造函数来实例化对象
	public static void test7()throws Exception
	{
		
	}
}