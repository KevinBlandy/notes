————————————————————————————————
一,struts2拦截器				|
————————————————————————————————
	> struts2,拦截器.源自Spring的AOP思想
	> 而,AOP的底层使用的是动态代理 Proxy,憋忘了啊
	> strut2中拦截器使用责任链模式
		> 责任链模式里,很多对象由一个对象对其下家的引用而连接起来形成一条链
		> 责任链每一个节点,都可以继续调用下一个节点.也可以阻止流程继续执行
	> struts2中默认使用的是默认的defaultStack拦截器桟
		> 在这里拦截器桟中,使用了18个拦截器,简单说!struts2默认情况下!加载了18个拦截器器!
	
	> 这东西我已经理解了!哈哈哈,你们傻逼了吧?
	> 哈哈,我懂你们不懂的感觉就是爽!

	* 我拿大白话给你解释一下:
	你可以对一个Action添加功能,也可以删除已经添加的功能！而且都不用去改源码!加点配置!
	切面,横着切进去!就这样!跟WEB中的filter有点像

	'创建拦截器的三个方法'
	1,实现 Interceptor 接口
		* 这是最原始的接口,任何拦截器都要实现它!它有三个方法,init,destory...interceptor
	2,继承 AbstractInterceptor 抽象类
		* 这是 Interceptor 的实现类,空实现了init,destory方法!我们只需要覆写 interceptor 
	3,继承 MethodFilterInterceptor 抽象类
		* 这是 Interceptor 的实现类,它有一个 doInterceptor方法.可以只拦截指定的对象
————————————————————————————————
二,struts2怎么使用拦截器		|
————————————————————————————————
	1,创建自定义拦截器类,实现Interceptor接口
		* com.opensymphony.xwork2.interceptor.Interceptor
	2,覆写仨方法
		> init();//初始化立即调用
		> intercept();//业务调用,也就是真正拦截的方法
		> destroy();//销毁调用
	3,在xml中声明 Interceptor
	4,那么在访问该Action的时候,就会先从上往下先执行拦截器!
	5,这是一个递归调用的过程

['拦截器定义结构']
<package>
	//拦截器定义
	<interceptors>
		<interceptor name="" class=""/>	//自定义拦截器1
		<interceptor name="" class="">	//自定义拦截器2(带参数)
			<param name="参数">值</param>
		</interceptor>
		//拦截器栈
		<interceptor-stack name="">		//自定义拦截器桟(其中可以引用其他拦截器或者拦截器桟,包括继承下来的)
			<interceptor-ref name=""/>		
			<interceptor-ref name=""/> 
		</interceptor-stack>
	</interceptors>
	//Action
	<action name="" class="" method="">
		<interceptor-ref name=""/>	//引用某个拦截器,或者是拦截器栈
		<result name="login">/login.jsp</result>
	</action>
</package>

['针对性拦截']
	> 有时候一个Action中有N多个处理器方法!例如:login,serch!有时候我们需要为它们设置不同的权限!
	> 所以这就涉及到了一个问题:拦截器,只拦截Action中指定的方法
	1,Action类不再实现 Interceptor 接口,而是继承其下的抽象类,很显然它也是实现了 Interceptor 接口
	* 'MethodFilterInterceptor'
	* import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor
	* 只有一个方法:doInterceptor(Invocation invocaton);
	* 这个方法就是拦截方法.
	2,在struts.xml中声明
	<interceptor name="" class="">									//自定义的方法拦截器
			<param name="includeMethods">add,update,delete</param>	//会被此拦截器的方法,是个数组,可以定义多个!
			<param name="excludeMethods">login,quite</param>		//不会被此拦截器拦截的方法
	</interceptor>

	总结:只有在'includeMethods',属性中声明的方法才会拦截.不然不会执行拦截操作!'就算你没写,或者你写到了excludeMethods里面,都不会被执行'
	* 如果你是个逗逼.两边都写,那么肯定会被调用!
	* 什么?你想写一个不存在的方法?那你要想到Action请求方法不存在会报错的... ...

['注意']
	
	> 自己配置拦截器栈,还有把默认的拦截器栈配置进来(自己继承struts-default或者继承已经继承了struts-default的包,就可以使用默认的拦截器桟defaultStack)
	  只要在定义包的时候,继承struts-default包,那么defaultStack就是默认的拦截器
	  '当包中的某个action显示的指定了某个拦截器,则默认的拦截器不会起作用'
	  '而很多功能都是定义在默认的拦截器桟中的..例如:参数封装等等........'
	  拦截器栈中的各个拦截器的顺序很重要,'从上往下执行'!
	  一般都是建议,'先引用默认的拦截器栈,再引用自己的'
	> 拦截器栈中可以嵌套拦截器栈,在action中引用的时候,拦截器栈还是单个拦截器的引用标签一模一样
	> 对于一特殊需求,我们可以把defaultStack桟中的拦截器,手动的赋值到Action中.然后进行插入/删除/替换等操作!
	
————————————————————————————————
三,intercept,拦截方法			|
————————————————————————————————
	//这个就是接口中的方法,也就是传说中的拦截方法
	public String intercept(ActionInvocation invocation) throws Exception 
	{
		
		return invocation.invoke();
	}
	> 这个方法返回值会被result或者是其他的拦截器收到!不确定,或许他后面有人,或许也前面有人!

['invocation']
	invoke();//放行,其实就是调用下一个拦截器或者是Action.不管,有啥就是啥!得到一个String字符串
	getAction();//返回一个Object,其实就是得到你拦截的那个Action对象!如果Action是继承了ActionSupport,那就进行强转!
	
————————————————————————————————
四,struts2拦截器原理			|		
————————————————————————————————
	源码执行流程
	1.在StrutsPrepareAndExecuteFilter中查找
		* 在doFilter方法内有一句话 execute.executeAction (request, response, mapping) 执行Action操作.
		
	2.在executeAction执行过程中会访问Dispatcher类中的serviceAction，在这个方法中会创建一个
		* ActionProxy proxy = config.getContainer().getInstance(ActionProxyFactory.class).createActionProxy(namespace, name, method, extraContext, true, false);
		* 这就是我们的Action的代理对象
		
	3.查看ActionInvocation，查看其实现类 DefaultActionInvocation.
				
	在其invoke方法中
	if (interceptors.hasNext())
	{
		//判断是否有下一个拦截器.
		final InterceptorMapping interceptor = interceptors.next(); //得到一个拦截器
		String interceptorMsg = "interceptor: " + interceptor.getName();
		UtilTimerStack.push(interceptorMsg);
		try 
		{
			resultCode = interceptor.getInterceptor().intercept(DefaultActionInvocation.this); 
			//调用得到的拦截器的拦截方法.将本类对象传递到拦截器中。
		}
		finally
		{
			UtilTimerStack.pop(interceptorMsg);
		}
	} 
				
		通过源代码分析，发现在DefaultActionInvocation中就是通过递归完成所有的拦截调用操作.
		
		'关于interceptor与Filter区别:'
			1、拦截器是基于java反射机制的，而过滤器是基于函数回调的。
			2、过滤器依赖于servlet容器，而拦截器不依赖于servlet容器。
			3、拦截器只能对Action请求起作用，而过滤器则可以对几乎所有请求起作用。
			4、拦截器可以访问Action上下文、值栈里的对象，而过滤器不能。
			5、在Action的生命周期中，拦截器可以多次调用，而过滤器只能在容器初始化时被调用一次。
		


————————————————————————————————
五,struts2拦截器案例			|
————————————————————————————————
			不写了,简直就是日了狗了!
