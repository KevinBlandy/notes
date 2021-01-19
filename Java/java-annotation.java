------------------------------
Annotation					  |
------------------------------
	# 天下所有注解的父类都是 Annotation
	
	# 可以标识的位置
		类
		类方法
		构造器
		类成员变量
		方法参数
		局部变量
		包				-- 不能直接放,要通过反射,不然要挂

	# 属性类型
	   八大基本数据类型
	   String
	   Enum
	   Class
	   可以是注解类型(注解类型里面类型就是另一个注解)
	   以上类型的一维数组类型(基本数据类型的包装类型也不能用)
	  
	
	# value 属性特权
		当注解仅仅只有一个属性,且名字就叫做Value的时候,可以省略名称,直接写值
	
	# 目标限定,和注解的保留策略限定
		 @Target 
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
		
		@Retention(RetentionPolicy.RUNTIME)
			* 大多数时候保留策略都是 'RUNTIME'

	# Demo
		@interface Ann{
			int a();		//int类型
			String b();		//字符串类型
			MyEnum c();		//枚举类型
			Class d();		//Class类型
			MyAnn e();		//也是注解类型
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
	
------------------------------
JAVA8特性					  |
------------------------------
	# 可重复注解
		* 示例
			@MyAnnotation("123456")
			@MyAnnotation("123457")
		
		* 定义
			@Repeatable
				Class<? extends Annotation> value();
				* 指定可以被重复注解的注解类
			

			@Repeatable(MyAnnotations.class)	//指定容器类
			@MyAnnotation{						//定义注解
				String value;
			}

			@MyAnnotations{						//定义容器类注解
				@MyAnnotation[] myAnnotation;
			}

		* 获取
			T[]  getAnnotationsByType(T.class);
				* 重复定义了N多注解,以数组形式返回



	# 用于类型的注解
		@Target 
			> TYPE			--	当你选择这个,就允许你出现在类,接口,枚举类上
			> FIELD			--	成员变量
			> METHOD		--	成员方法
			> PARAMETER		--	
			> CONSTRUCTOR		--	构造器
			> LOCAL_VARIABLE	--	
			> ANNOTATION_TYPE	--	注解...
			> PACKAGE		--	包(不能直接放...)
		新的
			
			
------------------------------
Inherited 					  |
------------------------------
	# 允许子类继承父类的注解
		
		import java.lang.annotation.*;
		@Inherited
		@Target(value = {ElementType.TYPE})
		@Retention(value = RetentionPolicy.RUNTIME)
		public @interface Foo {

		}
		
		@Foo
		public class Foo1 {
		}

		public class Foo2 extends Foo1 {
		}

		public class Main {
			public static void main(String[] args) {
				// true
				boolean result = Foo2.class.isAnnotationPresent(Foo.class);
				System.out.println(result);
			}
		}