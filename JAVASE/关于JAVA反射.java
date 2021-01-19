Reflection反射。是被视为动态语言的关键。
Class 是 java.lang包下的一个类。
	创建一个类 ， 通过编译(javac.exe)生成对应的.class 文件。之后我们使用java.exe加载(jvm的类加载器)。
这个.class 文件。这个 class 加载到内存以后。就是一个运行时类。存放在缓存区。那么这个运行时类。
本身就是一个 Class 的实例。每一个运行时类，只加载一次。
	Object 中定义了一个类叫做 getClass();这是被所有类所继承的一个方法。这个方法返回的是一个 Class 类
Class cls = 对象名.getClass();
	|--那么就得到了这个对象的类。的对象！（就是这个类，的对象！可以对这个类进行一些操作-建立对象，获得方法，获得属性，以及对他们进行操作）；
这个类，是java反射的源头。实际上所谓反射从程序的运行结果来看也很好理解：可以通过对象，反射求出类的名称。
	'正常方式'
	引入需要的'包类'名称 - 通过 new 实例化 - 获得实例对象
	'反射方式'
	实例化对象 - getClass方法 - 得到完整的'包类'名称
--------------------------------------
实例化 Class 的三种方法。
1,调用运行时类.class 属性。
Class cls = Demo.class;
2,通过运行时类的对象，调用其getClass();方法
Demo d = new Demo();
Class cls = d.getClass();
3,调用 Class 的静态方法forName(className); 此方法会抛出一个类找不到异常。
String class name = "com.mysql.jdbc.Driver";
Class cls = Class.forName(name);
-----------------------------------------
Field //属性
Method //方法
Constructor //构造器
Superclass //父类
interface //接口
Annotation //注解

---------------------------------------'获取属性，操作'-------------------------------------------
Class c = 字节码文件名.class;	Class c = Demo.class;//获取字节码文件的Class对象。
c.newInstance();				Object obj = (Object)c.newInstance();//通过Class对象。来产生一个新的实例.
	|--创建此 Class 文件类的。一个新对象。返回类型就是该 Object 文件类类型。一般都会强转成 class 文件的类型。
-----------------------------------
c.getField(String);				Field str = c.getFidle(String);//获取public修饰的属性
								Field[] strs = c.getFileds();//返回的是一个数组。包含了该类所有权限为 public 的成员属性。包括父类的public属性。
	|--获取c这个类中。指定名称为 String的这个属性。返回的是一个 Field 也就是这个属性的对象。
		str.set(Object,value);//赋值操作
			|--对指定对象的这个值。进行赋值操作。
-------------------------------------------------------------------------private
c.getDeclaredField(String);		Field str = c.getDeclaredField(String);//获取private修饰的属性
								Field[] str = c.stsgetDeclaredFields()//获取所有的属性，包括private属性。
	|-获取c这个类中。指定名称为 String(private 的访问权限)的这个属性。返回的是一个 Field 也就是这个属性的对象。
		str.setAccessible(true);//设置这个private属性，为可以被访问
		str.set(Object,value);//赋值操作
			|--对指定对象的这个值。进行赋值操作。如果是 private 修饰的属性。那么必须要有上一步的设置动作。否则，赋值失败抛异常，
	'注意----'默认的访问权限。 protected 也要通过获取 private 属性的方法。来获取。为了保险起见。不管是任何权限的属性。都用这个
	方法。反正是不会错的！
-------------------------------------------'获取方法(函数)，操作'-------------------------------------------
Class c = 字节码文件名.class;	Class c = Demo.class;//获取字节码文件的Class对象。
c.newInstance();				Object obj = (Object)c.newInstance();//通过Class对象。来产生一个新的实例.
	|--创建此 Class 文件类的。一个新对象。返回类型就是该 Object 文件类类型。一般都会强转成 class 文件的类型。
------------------------------------
c.getMethod(String);			Method m = c.getMethod("方法名",String.class);//获取已经声明了public修饰,指定的名称以及参数类型的方法。不含隐式(未覆写)的父类继承的方法。
								Method[] m = c.getMethods();//返回的是该类所有的 public 修饰的方法集合数组。以及父类的public的方法。
	|--获取c这个类中的。指定名称为 String 的这个方法。返回的类型就是 Method .如果有参数，那么在后面声明参数类型，以及变量即可。
		返回值类型 变量名 = m.invok(Object,参数);//指定对象，去调用这个方法。
		|--指定一个对象，来调用这个方法。如果没有参数那就不用写。同理，没有返回值。那么变量 == null;
		如果这个方法是 static 修饰的静态方法。那么调用的时候可以 m.invok(类名.class);来进行调用。当然
		对象也是可以直接调用。有无返回值，还是同理如上。
--------------------------------------------------------------------------private
c.getDeclaredMethod(String);    Method m = c.getDeclaredMethod("方法名字",String.class);//获取指定名称和参数(因为可能重载)的，被ptivate修饰的方法。
								Method[] m = c.getDeclaredMethods()//返回的是该类所有的方法(包括private)。数组！不包含继承的方法。 
	|--或者c这个类中的。名字叫 String 的方法。可以是被私有化的！返回的是 Method 类型！也就是这个方法的对象！
		m.setAccessible(true);//这是这个 private方法，为可以被访问。
		Object returnValue = m.invoke(Object);//指定Object这个对象，去操作这个方法。
			|--指定 Object 这个类去操作这个方法。如果有参数，直接在后面加上参数即可！如果没有，那么变量 == null;
			'返回值是一个 Object 类型。是该方法运行后的结果。如果返回值是void则返回null'
			'如果这个方法是静态的'，那么 Object 可以改为 类名.class 或者直接写 null 。静态方法，无需对象调用。当然，用对象也是可以！
	'注意----'默认的访问权限， protected 也要通过获取 private 方法的，方法来进行获取。为了保险起见。不管是任何权限的方法。
	都建议用这个方法来进行获取操作。
-------------------------------------------'获取其他信息'-------------------------------------------
Class cls = 字节码文件名.class;   Class cls = Demo.class;//获取字节码文件的Class对象
Constructor[] cs = cls.getDeclaredConstructors();//返回的是这个类中所有的构造函数类型的数组。包括private构造函数
Class superClass = cls.getSuperclass();//返回的是该类的父类。也是一个 Class类型的对象！
Type ty = cls.getGenericSuperclass();//返回的是带泛型的父类。注意，返回类型不是Class.
Class[] intertfaces = cls.getInterfaces();//返回该类所实现的接口。 
Package pack = cls.getPackage();//获取该类所在的包。
Annotation anns = cls.getAnnotations();//获取该类的注解。
--------------------------------------------'动态代理'----------------------------------------------


public class Demo{
	public static void main
}