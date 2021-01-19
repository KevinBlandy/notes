date	:2015年12月28日 18:29:06
author	:KevinBlandy
version	:1.0

————————————————————————————————
一,初识struts2框架				|
————————————————————————————————
1,前言
	what?
		什么是框架?框架能干啥?拿来吃?呵呵,开个玩笑!框架就是,'实现了部分功能的代码'(半成品)!使用框架简化企业级软件开发!提高效率
	how?
		学习框架,要清楚的知道框架能干啥?哪些工作需要自己写代码实现!千万不能死记代码!要学会查,搜!知道框架原理!
2,关于struts2
	首先,这个东西是Apache的产品,开源,且免费的
	struts2,是struts1的下一代产品!但是,很重要的一个东西就是!struts2跟struts1其实没多大关系!struts2其实是在struts1和webwork技术基础上进行了合并,这就是struts2!
	struts2 = struts1 + webwork
	> struts2是一个标准的MVC框架(很优秀),'只能应用于WEB应用层!'
		* hiberante 框架可以应用于桌面程序,Spring框架也可以应用于桌面程序!
	> 使用struts2框架,可以简化我们的WEB开发,并降低程序的耦合度!
	> 类似于struts2的产品
		* Struts1(已经没人用)
		* Webwork(struts2前身)
		* Jsf(sum,没人用,很悲催的东西)
		* SpringMVC(渐渐的火起来的,可以跟struts2比肩市场占有率的,得学)
	> 常见框架组合
		ssh = struts2 + spring + hibernate
		ssi = struts2 + spring + ibatis/mybatis
	* x-word,是webwork的核心!而struts2就是来自webwork,所以struts2底层就是它.
3,版本问题
	此笔记使用的是:struts-2.3.15.1-all
		> 15.1之前的版本千万别用,因为被爆出过'惊人的漏洞',后面的版本就被修复了.
4,目录结构
	apps	:applications,里面主要是struts2的一些案例程序(war包,千万别说不知道)
	docs	:帮助文档
	lib		:应用jar包,以及插件
	src		:框架源码
		> core		:struts2源代码
		> xwor-core	:底层使用了xword的源码,这就是xwrok的源码
	...
5,部份jar包详解
	> sam...开头的几个包
	  * 他们是反射的替代方案,是JAVA小巧便捷的字节码操作框架,能动态的的生成和改变java代码
	> commons-fileupload
	  * 一看就知道上传文件的jar包
	> commons-io
	  * 主要是对本地文件的一些读取和操作的包
	> commons-lang
	  * 主要是基础的文件包
	> commons-logging
	  * 日志包
	> freemarker
	  * 是生成html,xml,java代码等,的集合工具包
	> javassist
	  * 使java字节码操作更加简便,是编译和操作java字节码的类库
	> log4j
	  * 日志
	> ognl
	  * struts独有的标签库,非常强大和好用
	> struts-core
	  * struts2的核心包
	> xwork-core
	  * struts跟webwork整合而来的,非常重要
	
————————————————————————————————
二,struts2框架知识体系			|
————————————————————————————————
['重点']1,struts2配置
			> action/package配置
				* 默认包
				* 根路径包
			> 几个默认的属性值
				* ActionSupport
				* execute
				* success
			> action访问规则
			> 动态方法调用以及通配符
				* XML配置限制
		2,struts2结果类型
			> dispatcher
			> chain
			> redirect
			> redirectAction
			> stream
['重点']3,struts2处理请求参数
			> Action作为Model
			> javaBean作为Model
				* 表单要求
			> 实现ModelDriven
				* 拦截器,实现原理.必须实例化
		4,struts2的类型转换
			> 局部转换(两种)
				* 对应Action或者JavaBean路径下创建文件指出属性名对应转换器全限定名
			> 全局转换
				> 继承DefaultTypeConverter
					* 一个方法,进出皆是
				> 继承StrutsTypeConverter
					* 俩方法,进出分别
				* 配置文件不同,名称限定.且key为类型全限定名
		5,struts2的校验
			> 手动校验
				* 指定校验方法	继承ActionSuppost覆写validateXxx()
				* 校验整个Action	覆写validate()
			> 系统校验(xml)
				* 指定校验方法
					* 类-方法-validation.xml
				* 校验整个Action
					* 类-validation.xml
		6,struts2国际化
			> 全局国际化
				* properties在src目录下,在xml中注册
			> 局部国际化
				* 在包中,Action,JSP也能访问!不需要注册
				* Action 中getText();...
			> 国际化动态文本
				* 通配符{0}
['重点']7,struts2拦截器
			> 系统拦截器
				* 默认18个拦截器桟,第一个是异常拦截器.他们会完成属性注入,文件上传等.显示声明后,默认的会失效
			> 自定义拦截器
				* 实现接口Interceptor
					覆写仨方法,一个初始化,一个销毁,一个拦截方法
				* 继承抽象类AbstractInterceptor
					空实现了生命周期方法,只用覆写一个
				* 继承抽象类MethodFilterInterceptor
					在xml配置中配置参数,也就是配置要拦截的方法,可以实现
			> 注册拦截器
		8,struts2文件上传与下载
			> 客户端要求
				* 指定头,file字段,
			> Actin要求
				* 提供与表单file字段name属性一样的 xxxFile ,以及String类型的名词和类型字段!系统通过拦截器自动注入!
				* 多文件上传,就是把类型改为集合或者数组就好
				* 全局文件上传大小限制,在xml中进行配置.
				* 局部文件上传限制:大小,类型,后缀名
					* 在Action的拦截器配置中,为拦截器提供参数
				* 上传异常跳转视图
					*  input
			> 下载xml配置 
				* stream 类型视图,Action 提供几个get方法:文件名称/文件类型... ...读取流
				* 如果请求参数中有中文,要注意乱码处理,相应的下载文件名中有中文也要注意乱码处理.GBK打回,ISO-8859-1生成
['重点']9,struts2中OGNL,与ValueStack	
		10,struts2防止表单重复提交
			* 表单页面提供<s:tonken/>标签
			* 导入一个拦截器,这个拦截器不在18个默认的拦截器内,需要自己导入
			* 其实这个东西可以自己定义一个!
		11,struts2中ajax插件

————————————————————————————————
三,HelloWorld					|
————————————————————————————————
1,导入jar包
	struts2开发中,一般情况下,最少导入的jar包去参考aaps目录下的:struts2-blank示例程序中copy
		> 直接把后缀改成rar,解压就是了!就是一个WEB应用程序!你如果不知道在WEB应用程序怎么找到jar包!右上角x!
		> 因为版本的问题,会导致jar包的个数,大小不一样!反正!直接copy这里面的代码,没问题!

2,在web.xml中配置前端控制器(核心控制器)
	* 就一个,filter过滤器
	> 这东西代表struts2框架生效
	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>/\*</url-pattern>
	</filter-mapping>
	
	* 它默认只拦截请求,不拦截转发
		> 例如,别人访问的index.jsp中,有一个转发.action.拦截器是不鸟你的
		> 你别忘记了:<filter-mapping>里面是可以何止拦截类型的
		* <dispatcher>REQUEST</dispatcher><!-- 如果不声明,那么默认拦截的是REQUEST -->
2,添加配置文件
	struts.xml(名称必须,放置在classes目录下)
	> 这个是struts2框架的配置文件,核心配置!为了让struts框架的流程可以执行!
	> 还是去那个struts2-blank.war中去偷

	... ...不写了,略!没啥意思!

————————————————————————————————
四,流程分析						|
————————————————————————————————
1,服务器启动,加载web.xml文件,就会加载web.xml中配置的struts2前端控制器,那么此时struts2就可以使用了!
	* StrutsPrepareAndExecuteFilter中的init()方法会读取类路径下默认的配置文件struts.xml完成初始化操作
	* 注意:
	> struts2读取到struts.xml的内容后,以javabean的形式存放在内存中,以后struts对用户每次请求处理将使用内存中的数据,而不是每次都读取struts.xml文件
2,拦截器,会拦截所有请求!
3,会使用资源路径,去struts2.xml中查找对应的的action类!
4,执行
