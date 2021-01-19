package com.kevin.demo;
/**
 * 目标是,让目标对象和增强内容,都可以切换... ...
 * */
public class Test 
{
	public static void main(String[] args)
	{
		show();
	}
	/**
	 * 动态代理工厂的演示
	 * */
	public static void show()
	{
		Waiter w = new ManWaiter();//被增强对象,目前只有一句输出
		/**
		 * 前置增强方法 ,其实是一个接口这里写的是匿名类
		 * */
		BeforeAdvice be = new BeforeAdvice()
		{
			public void befor() 
			{
				System.out.println("前置增强:你好,欢迎光临");	
			}
		};
		/**
		 * 后置增强方法 ,其实是一个接口这里写的是匿名类
		 * */
		AfterAdvice af = new AfterAdvice()
		{
			public void after() 
			{
				System.out.println("后置增强: 再见,滚");
			}
		};
		ProxyFactory factory = new ProxyFactory();//得到工厂类对象
		factory.setBeforeAdvice(be);//设置前置增强
		factory.setTargetObject(w);//设置增强的对象
		factory.setAfterAdvice(af);//设置后置增强
		Waiter newWaiter = (Waiter) factory.createProxy();//获得增强的代理对象
		newWaiter.serve();//增强后的输出,三句话了....
	}
}
