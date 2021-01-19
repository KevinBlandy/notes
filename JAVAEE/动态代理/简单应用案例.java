package com.kevin.demo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 这个案例,需要掌握
 * */
public class Demo 
{
	public static void main(String[] args) 
	{
		Waiter waiter = new ManWaiter();	//目标对象
		waiter.serve();//普通的调用,只有一句输出:服务中...
		/** 对象增强 **/
		WaiterInvocationHandler wih = new WaiterInvocationHandler(waiter);//参数是被增强的对象
		Waiter w = (Waiter) Proxy.newProxyInstance(waiter.getClass().getClassLoader(), new Class[]{Waiter.class}, wih);
		w.serve();//增强后输出,有三句话输出
		//hello
		//服务中... ...
		//Bye
	}
}
/**
 * InvocationHandler实现类,该类对象作为参数传递给newProxyInstance方法作为参数
 * */
class WaiterInvocationHandler implements InvocationHandler
{
	private Waiter waiter;	//目标对象
	public WaiterInvocationHandler(Waiter waiter)
	{
		this.waiter = waiter;
	}
	//其实这个invoke就是增强的地点
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable
	{
		System.out.println("hello");
		this.waiter.serve();	//调用目标对象的目标方法
		System.out.println("Bye");
		return null;	
	}
}
