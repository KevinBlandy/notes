————————————————————————————————
一,Action需要掌握的东西			|
————————————————————————————————
1,实现一个Action最常用的方式
	> 从Action接口继承
	> 从ActionSupport继承[常用]
	> POJO类(没有实现或继承了任何借口,类的类)
2,DMI动态方法调用
	> !号调用,但是要记得在xml中设置常量
3,通配符
	> *_*,{1},{2}!不多说
4,接收参数的方法
	> 一般用属性,或者DomainModel来接受
	> 还有一种实现泛型接口...
5,简单的参数验证
	> addFieldError
	> 一般不用struts2的UI标签
6,访问WEB元素
	> 获取四大域对象的Map引用俩种方式
	* IOC	--	通过ActionContext得到
	* 依赖于Struts2框架	--	通过实现接口注入
	> 直接获取四大域对象的引用
	* IOC	--	通过ServletActionContext
	* 依赖于Struts2框架	--	通过实现接口注入
7,包含文件配置	
	> <include ...>标签,不多说
8,默认Action处理
	> <default-action-ref name="Action名称"></default-action-ref>

————————————————————————————————
二,Action五种预定义结果类型		|
————————————————————————————————
	在Action接口中定义了五大常量,以及一个execute();抽象方法
	SUCCESS	=	"success";	//数据处理成功
	NONE	=	"none";		//页面不跳转,retuen null;效果都一样!
	ERROR	=	"error";	//数据处理发生错误(错误页面)
	INPUT	=	"input";	//用户输入数据有误,通常用于表单数据校验
	LOGIN	=	"login";	//主要权限认证(登录页面)

————————————————————————————————
三,ActionSupport类				|
————————————————————————————————
	继承这个,是使用的最多的!
	Action的子类,实现了一大堆的接口,也就是有一大堆的方法
		Action
		Validateable
		ValidationAware
		TextPorvider
		LocaleProbvider
		Serializale
	> 支持校验,支持国际化,读取国际化信息

————————————————————————————————
四,Action类的,访问方式				|
————————————————————————————————
	所谓的动态方法的调用,此刻你应该回想一下
	BaseServlet  有没有？链接后面带上参数,也就是方法名
	其实在struts2中,调用一个Action的方法有两种方式
一,
		<action name="demo" class="com.kevin.demo.Demo" method="fun1" >
			<result name="success">/index.jsp</result>
		</action>
		> 在method 中指定你要调用的方法名称
		> 那么如果别人直接访问这个Action,就默认调用fun1();方法
		> http://localhost:808/WEB/demo.action

	动态方法调用
	如果Action中存在多个方法的时候,我们可以使用[!方法名]来调用指定的方法,

	public class Demo
	{
		public String fun1()
		{
			return "这是第一个方法";
		}
		public String fun2()
		{
			return "这是第二个方法";
		}
	}
	
	假如,上面的Action的访问URL路径为:http://localhost:808/WEB/demo.action
		> 调用fun1()
		* http://localhost:808/WEB/demo!fun1.action
		> 调用fun2()
		* http://localhost:808/WEB/demo!fun2.action
		> 如果只在URL中添加"!"感叹号,而不添加方法名！直接就是访问默认的方法!
		> 注意注意注意,在struts.xml尽量要有如下配置, 虽然听起来是默认为true,但是我栽了跟斗.建议还是主动设置
		<constant name="struts.enable.DynamicMethodInvocation" value="true"></constant>
		* 这个东西配置在default.properties
		如果想要关闭这种动态方法调用的机制,我们可以在struts.xml文件中进行常量的配置
		* <constant name="struts.enable.DynamlcMethodInvocation" value="false"></constant>
		

	其实这种动态调用,在struts2 1.0 版本的时候就不建议使用了！官方不建议使用的东西,就别用了。

	----------------------
二,通配符的问题
		<action name="test_*" class="com.kevin.demo.Demo" method="{1}" >
			<result name="success">/index.jsp</result>
		</action>
		> 其实你应该不难看出其中的意思,再回想一下MessageFormat你就应该知道了
		> text_* 这个*就可以由客户端传递过来,那么这个*号就会被赋值在{1}这个变量中
		> 如果有多个*存在,则挨个赋值{1}{2}{3}... ...
		> 这里吧 * 赋在了method上,就是说客户端可以自己定义要调用的方法
		> 而且,这个*还能出现在 class,result标签中。。。参数统一由客户端发过来,然后就看你怎么配置这些参数了

