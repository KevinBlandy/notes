1,注解,上次没认真听
	语法:	@注解名称
	作用:	替代web.xml文件(Servlet3.0中就可以使用注解来代替web.xml文件),一般注解都是由"框架"读取!
	注意:	同一个注解,不能在同一个目标上出现两次
2,注解的使用
	1,定义注解类(框架的工作--例如:Junit)
	2,使用注解(我们的工作)
		例如: @Override --重写注解,被这个注解标志的方法如果不是覆写父类的那么就会报错
	3,读取注解(框架的工作,通过反射)

3,定义注解类
	*  天下所有的注解都是 Annotation的子类
	@interface A
	{
		String nmae();
		int age();
	}

4,使用注解类的地方
	> 可以放置的地方
		类,
		类方法,
		构造器,
		类成员变量,
		方法参数,
		局部变量,
		包,  -- 不能直接放,要通过反射,不然要挂
	> 

5,注解的属性(重点)
	* 定义属性
	   > 格式:类型 属性名();	//这个格式打死不能变
	   > 例如:String demo();

	* 使用注解时,给属性赋值
	   > 格式:@注解名(属性1,=属性值,属性2=属性值)
	   > 例如:@A(name = "Kevin", age = 25)

	* 注解属性的默认值
	   > 格式:类型 属性名() fefault 值;
	   > 例如:String name() default "Kevin";//当使用带有默认值的属性,可以不用赋值

	* 名为value的属性的特权
	   > 格式:类型 value();//当这个属性的名称叫做value,那么在使用的时候就不用写名字,可以直接赋值
	   > 定义:String vale(); 
	   > 使用:@A1("value的值,可以省略掉名称")
	   > 但是如果一个注解存在多个属性,那么value不能被省略!也就是说只有一个属性值,且名称叫做value的时候,才能省略不写

	* 注解属性的类型
	   * 只能使用下列类型
	   > 8大基本数据类型(包装类型不能使用)
	   > String
	   > Enum		--	枚举类型
	   > Class		--	类类型
	   > 注解		--	注解
	   > 以上类型的一维数组--	特别要注意,千万是以上类型,而且是一维数组
	   	------------演示
	   @interface Ann//注解类
	   {
		int a();	//int类型
		String b();	//字符串类型
		MyEnum c();	//枚举类型
		Class d();	//Class类型
		MyAnn e();	//也是注解类型
		String[] f();	//数组类型
	   }
	   @Ann(		
		a=100,
		b="kevin",
		c=MyEnum.A,
		d=String.class,
		e=@MyAnn("这个注解的值"),
		f={"字符串","数组"}//注意注意注意....当只有一个元素的时候可以省略大括号
		)
	   public class Demo{}//使用注解类	

6,注解的作用目标限定
	--让注解只能作用在类上,而不能出现在方法上,或者反之,这个就是注解的作用限定
	* 在定义注解的时候,给注解添加一个注解	@Target()
	  > 详解 @Target 
		* 这个注解,只有一个属性,叫做:ElementType[] value();
		* 这个ElementType是枚举,注意是枚举类,在这个注解里面是以数组的形式存在的.而且名字叫做value,想必你已经懂了,只有一个且名字叫做value,那不用指定名字赋值了
		* 这个枚举有一些枚举对象:
			> TYPE			--	当你选择这个,就允许你出现在类,接口,枚举类上
			> FIELD			--	成员变量
			> METHOD		--	成员方法
			> PARAMETER		--	
			> CONSTRUCTOR		--	构造器
			> LOCAL_VARIABLE	--	
			> ANNOTATION_TYPE	--	注解...
			> PACKAGE		--	包(不能直接放...)
	  > 使用案例1:@Target(value={ElementType.TYPE})  //这个注解所注解的注解就只能使用在类上
	  > 使用案例2:@Target(value={ElementType.TYPE,ElementType.FIELD})//这个注解所注解的注解就能使用在类和方法上
	  > 反正,你想放哪里,就加个这个枚举的对象就是了,value也可以省略,如果只有一个,连{}都直接可以干掉

7,注解的保留策略
	  先给你说一下注释这个很熟悉的东西,注释其实只是存在于源码上,当被编译成class文件的时候就没了,不信你可以反编译一个class文件试试,你铺天盖地的注释,一个都没
	①,JAVA程序的几个状态
	  > 源代码中(SOURCE)
		> 注解可以只在这里,也就是只在源代码中存在,当编译时就被忽略了.这种注解没办法被反射,因为到class就没了,玩个毛啊
	  >字节码中(CLASS)
		> 注解在源代码中存在,在编译的时候,会把注解的信息放到class文件中,但JVM在加载类的时候,会忽略注解(加载到内存的时候已经没注解,所以也不能反射)
	  >虚拟机中(RUNTIME)
		> 注解在源代码,字节码文件中存在,并且在JVM加载类时,会把注解加载到内存
		* 它是唯一一个可以被反射的注解

	②,限定注解的保留策略
	  * 在定义注解的时候,添加一个注解:@Retention
	      > 只有一个属性,类型是:RetentionPolicy  属性名叫做:value  ...  呵呵哒,也就是说又不用写该死的属性名,value特权
		* 这个属性,又是一个讨厌的枚举类... ...
		> SOURCE		--	源码
		> CLASS		--	class文件
		> RUNTIME		--	运行的时候
	 
	  * 例如:@Retention(RetentionPolicy.RUNTIME)   //被这个注解所注解的注解就会存在于JVM中,Runtime嘛,就可以通过反射来操作

8,反射注解
  1,要求
	> 这个注解的保留策略,必须是RUNTIME.
  2,反射需要从作用目标上返回'
	> 类上的注解,需要使用Class来获取
	> 方法上的注解,需要使用Method来获取
	> 构造器上的注解需要Construcator来获取
	> 成员上的注解,需要使用Field来获取

	Class 是一个帮派
	Method,Construcator,Field--> 共同的父类就是 AccessibleObject
AccessibleObject 这个类是除了Class类意外其他三个类的父类,跟注解相关的方法..(也就是说这个方法存在于所有的子类,你懂的)
	> getAnnotation(注解类名称.class);
	* 谁调用了我,我会返回谁的注解(方法调用,我就返回方法的,类成员调用,我就返回类成员的...)
	> getAnnotationa();
	* 这个就是直接返回了目标上的所有注解,因为有时候注解或许不止一个.这也是为什么上面的方法要指定注解的class类型的愿意
Class 是独立派,他也有一个方法
	> getAnnotation(注解类名称.class);//返回元素上的注释
	* 我就返回我这个类上的注解
	> getAnnotationa();
	* 这个就是直接返回了目标上的所有注解,因为有时候注解或许不止一个.这也是为什么上面的方法要指定注解的class类型的愿意


代码....很长,很长...喜欢就看
--------------------------------
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
/**
 * 注解类
 * */
@Retention(RetentionPolicy.RUNTIME)//注意,这个很重要
@interface MyAnnoa
{
	String name();
	int age();
	String sex();
}
/**
 * 注解作用目标类
 * */
@MyAnnoa(age=1,name="A类",sex="男")
class TestA
{
	@MyAnnoa(age = 1, name = "A类fun1", sex = "女")
	public void fun1()
	{
		
	}
}
/**
 * 主函数咯
 * */
public class Demo
{
	public static void main(String[] args) throws Exception
	{
		demo1();
		demo2();
	}
	/**
	 * 反射类上的注解信息
	 * */
	public static void demo1()
	{
		//得到作用目标
		Class<TestA> a = TestA.class;
		//传递指定类型的注解class作为参数,返回的就是这个注解(可能有很多个注解... ...)
		MyAnnoa mya = a.getAnnotation(MyAnnoa.class);
		//打印类上注解的信息
		System.out.println(mya.name()+":"+mya.age()+":"+mya.sex());
	}
	/**
	 * 反射方法上的注解信息
	 * */
	public static void demo2()throws Exception
	{
		//得到方法所在的类对象
		Class<TestA> a = TestA.class;
		//得到指定的方法
		Method method = a.getMethod("fun1");
		//得到该方法的注解类
		MyAnnoa mya =  method.getAnnotation(MyAnnoa.class);
		//打印注解信息
		System.out.println(mya.name()+":"+mya.age()+":"+mya.sex());
	}
}
