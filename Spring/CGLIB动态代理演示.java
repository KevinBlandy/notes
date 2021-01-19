package com.kevin.main;
import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
/**
 * cglib动态代理演示,只能对类生成代理
 * */
public class CGLIB <T>implements MethodInterceptor
{
	private T t;
	/**
	 * 唯一构造
	 * */
	public CGLIB(T t)
	{
		this.t = t;
	}
	/**
	 * 使用cglib生成代理
	 * */
	public T createProxy()
	{
		//创建对象
		Enhancer enhancer = new Enhancer();
		//继承父类
		enhancer.setSuperclass(t.getClass());
		//设置回调,其实就是传递一个实现了指定接口的对象..可以由本类继承,直接传递this
		enhancer.setCallback(this);
		//创建代理
		return (T) enhancer.create();
	}
	/**
	 * 代理方式
	 * */
	public Object intercept(Object proxy, Method method, Object[] args,MethodProxy methodProxy) throws Throwable
	{
		System.out.println("前置增强");
		Object result =  methodProxy.invokeSuper(proxy, args);
		System.out.println("后置增强");
		return result;
	}
}

