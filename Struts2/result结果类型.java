————————————————————————————————
一,Result结果类型		|
————————————————————————————————
<action ...>
	<result type="结果类型" ...>
		
	</result>
</action>
* type:确定了结果类型是转发还是重定向,还是什么.type有很多的值
* 在strut-defautl.xml,中的struts-default包下能找到!
* 在result还可以添加:default="true"属性,表示该Action中result的默认值就是该result!
	> 这东西是定义在default.xml中的东西!
————————————————————————————————
二,需要掌握的结果类型		|
————————————————————————————————
请求转发(默认)
	dispatcher
	* 一般用于从一个action跳转到页面(jsp,html)
请求转发
	chain
	* 跟楼上的区别是,它一般用于从一个action跳转到另一个action
重定向
	redirect		
	* <result type="redirect">/add.jsp?username=KevinBlandy</result>
重定向
	redirectAction	
	* 跟楼上的区别就是,他是重定向要某个action
	* <result name="success" type="redirectAction">action名称?参数名称=参数</result>
		> 如果没参数,那就不同带!参数可以使用OGNL表达式从ValueStack中获取
	* 如果重定向的Action不在此包中(你需要了解一下这个:我们要为这个类的俩属性设值)
		> redirectAction是一个类,这个类在:org.apache.struts2.dispatcher包下
		> setActionName(String actionName);//redirectAction的action属性
		> setNamespace(String namespace);//redirectAction的namespace属性
	<result name="success" type="redirectAction">
	 	<param name="actionName">其它包下的Action名称</param>		<!-- redirectAction类中的actionName属性 -->
		<param name="namespace">其他包的namespace</param>		<!-- redirectAction类中的namespace属性 -->
	</result>
	* 浏览器地址栏会发生变化  
下载 
	stream
	* 代表,服务器端返回的是一个(字节)流,一般用于下载!


————————————————————————————————
三,只需要了解的结果类型			|
————————————————————————————————
1,plainText
	> 用的不是很多,偶尔用用
	> 原样输出源代码,jsp的源代码,不会被jsp引擎解析,也不会被浏览器解析
	> 你点击页面的查看源码跟页面显示的源码是一摸一样,、
	> plainText 也可以输出WEB-INF目录下的文件
	* <result type="plainText">/WEB-INF/web.xml</result>
	> 假如页面中含有中文,可能会出现乱码问题!文件存放的编码,浏览器解析的编码不一致
	* 如果要处理输出页面的乱码问题(你需要了解一下这个:我们要为这个类的俩属性设值)
		> plainText是一个类,,这个类在:org.apache.struts2.dispatcher包下(根据上面猜的,没有实际验证,建议自己去找一下)
		> setLocation(Sting location);//plainText的location属性,其实就是要输出的页面
		> setCharSet(String charSet);//字符集编码
	 * <result type="plainText">
		<param name="location">/WEB-INF/web.xml</param>		
		<param name="charSet">UTF-8</param>
	   </result>
[以下这俩就是模板技术的东西... ...]
2,freemarker
	
3,velocity
————————————————————————————————
四,局部结果页面跟全局结果页面	|
————————————————————————————————

1,全局结果页面
package result共享	(一个package中共享)

	* 这个东西定义在一个package里面
	* 这个东西的返回值(result),处理方式
	* 可以被该package里面的所有action共享
	* 如果action自己的result name属性跟全局共享的一样,那么优先执行自己的
	<global-results>
		<result name="all" type="redirect">/index.jsp</result>
	</global-results>

2,全局结果集共享	

	* 我们定义一个包,去继承 struts-default
	* 然后,只要是继承了这个包,都能共享我们的 global-results
	* 因为这个包继承了struts-default,所以,继承这个包的包,也是可以使用struts-default的东西
	<package name="base" extends="struts-default">
		<global-results>
			<result name="all" type="redirect">/all.jsp</result>
		</global-results>
	</package>
	<package name="manager" namespace="/demo" extends="base">
		...这个包因为继承了base包,所以这个包内的Action可以共享base包中的全局result
	</package>

6,动态结果集
<action...>
	<result>${url}</result>
</action>

	> 其实就是在Action中定义一个String类型名为url的属性,
	> 我们通过Action的程序判断,来给url赋值.
	> 在该Action的result中,就可以通过这种形势吧url的数据去除了,当作一个url进行处理
	> ${url},就是在读值栈里面的内容
---代码
	public String demo()
	{
		if("1".equals(userName))
		{
			url = "/WEB-INF/jsps/manager.jsp";
		}
		else
		{
			url = "/WEB-INF/jsps/success.jsp";
		}
		return "test";
	}

7,带参数的结果集
> 重定向,也就是客户端跳转的时候才用这个！服务端跳转参数直接放域对象
<action ..>
	<result type="redirect">/user/demo.jsp?p=${p}</result>
</action>

这东西吧,也是从值栈里面取,就Action里面定义个类就好

每次request请求,都只有一个值栈,另一次请求,会给请求创建新的！