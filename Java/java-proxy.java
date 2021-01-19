
---------------------------
获取动态代理对象			|
---------------------------
	Object obj = Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) 
		1,classLoader
			需要动态生成一个类,这个类也需要加载到方法区中,谁来加载?就是classLoader
		2,interfaces
			其实就是Class数组,要实现的接口们
		3,Invocationhandler
			调用处理器..
	
	# Class 对象的 getInterfaces(); 可以获取到类所有实现的接口

---------------------------
InvocationHandler			|
---------------------------
	# 接口
	# 唯一抽象方法
		Object invoke(Object proxy, Method method, Object[] args)  
		
		# proxy,	当前执行的对象
		# method,	为执行的方法
		# args,		为执行方法参数
	

---------------------------
Demo	1					|
---------------------------
	import java.lang.reflect.InvocationHandler;
	import java.lang.reflect.Method;
	import java.lang.reflect.Proxy;
	//测试接口A
	interface A{
		public void a();
		public void aa();
		public Object aaa(String s1,int i);
	}
	//测试接口B
	interface B{
		public void b();
		public void bb();
	}
	//测试主方法
	public class Demo {
		public static void main(String[] args){
			/**
			 * 1,classLoader
			 * 三大参数,这个方法需要三个参数动态生成一个类,这个类实现了A,B接口,然后创建这个类的对象
			 * 需要生成一个类,这个类也需要加载到方法区中,谁来加载?就是classLoader
			 * 2,interfaces
			 * 其实就是Class数组,要实现的接口们
			 * 3,Invocationhandler
			 * 调用处理器..
			 * */
			InvocationHandler h = new InvocationHandler(){
				public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
					return "xxx";
				}	
			};
			//使用三大参数创建代理对象
			Object obj = Proxy.newProxyInstance(Demo1.class.getClassLoader(), new Class[]{A.class,B.class}, h);
			A a = (A)obj;					//强转成功,很显然是已经实现了A接口
			Object o = a.aaa("hello",100);	//其实就是调了invoke方法
			System.out.println(o);			//o.就是返回xxx
			B b = (B)obj;					//强转成功,很显然是已经实现了B接口
		}
	}

---------------------------
Demo	2					|
---------------------------
	import java.lang.reflect.InvocationHandler;
	import java.lang.reflect.Method;
	import java.lang.reflect.Proxy;
	public class ProxyFactory {
		private Object targetObject;	//目标对象
		/**
		 * 生成代理对象,最为重要的一个方法
		 * 也是最为复杂的一个方法
		 * 动！态！代！理
		 */
		public Object createProxy(){
			//获取类加载器
			ClassLoader loader = this.getClass().getClassLoader();
			//获取目标对象所实现的所有接口(注意啊)
			Class[] interfaces = targetObject.getClass().getInterfaces();
			//InvocationHandler
			InvocationHandler h = new InvocationHandler(){
				public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
					try{
						//在执行之前可以做一些操作
						Object result = method.invoke(targetObject, args);	//调用目标对象的目标方法
						//在执行之后可以做一些操作
						return result;										//返回目标对象方法执行的结果					
					}catch(Exception e){
						//在异常的时候可以做一些操作
						return null;
					}
									
				}
			};
			//得到代理对象
			Object obj = Proxy.newProxyInstance(loader, interfaces, h);
			//返之
			return obj;
		}
		//获取增强对象
		public Object getTargetObject()					{
			return targetObject;
		}
		//设置增强对象
		public void setTargetObject(Object targetObject) {
			this.targetObject = targetObject;
		}
	}
