package com.kevin.demo;
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
