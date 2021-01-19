讲SE的时候,已经讲过不多说了

泛型这个东西,其实在编译期状态的时候,会被擦除,增强for,之类的都是!虚拟机根本就不认识,都会被编译器给改成源代码,只有编译器认识我们的代码



1,泛型类:具有,一个或者多个类型变量的类,称之为泛型类
	class A<T>{}
2,在创建泛型类实例的时候,需要为其类型变量赋值
	A<String> a = new A<String>();
	* 如果创建实例的时候,不给类型变量赋值,那么会有一个警告!不会报错,是JDK的向下兼容5.0出现的新特性
3,泛型方法:具有一个或多个类型变量的方法,称之为泛型方法
	class A<T>
	{
		void T demo(){}//不是泛型方法
	}
	---
	public <T>T demo(T t1)//是泛型方法
	{
		
	}
	* 泛型方法与泛型类没啥关系,泛型方法不一定非要在泛型类中

4,泛型在类中或者方法中的使用
	> 成员类型
	> 返回值和参数类型
	> 局部变量的引用上
class A<T>
{
	private T bean;//可以在成员字段上使用
	public void T demo(T t)//可以在成员方法(返回值和参数)上使用
	{
		
	}
	public void test
	{
		T b = ...//可以在局部变量的引用类型上使用
		new T();//这个不行
	}
}

=================泛型的继承和实现
class A<T>(){}
class AA extends A<String>{}//AA不是泛型类,只是AA的父类是泛型类
5,继承泛型类
	1, 子类不是泛型类
	> 需要给父类传递类型常量
		class AA extends A<String>(){}//这个继承,就必须给父类传递类型常量
		你传递的类型常量,就会替换掉父类中的所有T
	2, 子类是泛型类
	> 子类是泛型类,可以给父类传递类型常量,也可以传递类型变量
		class AA<E> extends A<String>(){}//可以父类传递类型常量
		class AA<E> extends A<E>(){}//也可以给父类传递类型变量
		class AA<E1,E2> extens A<E1>(){}//这个也是可以的

===============通配符

使用场景,方法的参数


泛型的引用,和创建两端给出的泛型变量必须相同！

public static void println(List<?> list)
{
	list.add("测试");//错误
	Object obj = list.get(0);//正确
}
...这个东西就是通配符   '?'
通配符只能出现在引用端,不能出现在new的那一端.... ....
?表示一个不确定的类型,它的值会在调用的时候确定下来.

注意,当使用通配符的时候,对泛型类中的'参数为泛型'的方法起到了副作用,不能再使用
     泛型类中'返回值为泛型的方法',也挂了

好处
	可以使用泛型类型更加通用.尤其是在方法调用的时,形参使用通配符
	
<? extends Object>
<? super Object>
当通配符出现为边界通配符的时候,


6,反射泛型信息  ----------------------------------

Class 类是反射的鼻祖,什么反射都得从他开始
它有一个方法
Type	getGenericSuperclass();//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。 


Type 是一个接口,它有一个子类接口--ParameterizedType
Type ---- > ParameterizedType(我们得到这个Type,会强制转换成这个类型)
ParameterizedType
	> 参数化类型  其实就是 Demo<String>
	> 就是类,加上类型参数,类名和泛型
	> getActualTypeArguments()//获取真实的类型参数们,返回一个Type[] 
	> 上面的方法返回一个Type[]数组,那么这个数组就是我们需要的Class[]

示例代码
	> !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	Class c = (Class) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	> 这串代码写在你的泛型类里面
	> 最好写在构造函数,那么这个c,就是这个类的子类传递上来的泛型类型的Class对象
	> 如果子类传递String  ..那么这个c 就是String.class
	> 如果子类传递Integer ..那么这个c 就是Integer.class

获取子类传递的类形(跟上面一样)
	ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
	clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];