package com.kevin.demo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class ProxyFactory {
	private Object targetObject;//目标对象
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
				Object result = method.invoke(targetObject, args);//调用目标对象的目标方法
				return result;//返回目标对象方法执行的结果
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
