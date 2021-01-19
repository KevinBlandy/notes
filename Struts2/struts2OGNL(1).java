————————————————————————————————
1,struts2ValueStack与OGNL入门	|
————————————————————————————————
	> 这是struts2的难点,是整个知识体系中最难的一部分了
	> OGNL是啥?valuestack又是啥?
	> 入门级别的介绍
		> ognl-是对象导航图语言 Object-Graph Navigation Language 
			* 功能强大,比EL表达式更强大
			* 它是一个单独的项目,只是说struts2集成了它.千万别以为它是struts2的东西!struts2使用它作为默认的语言
			* 它能完成五大类功能
				1,支持对象方法的调用,如:xxx.demo();
				2,支持类静态方法的调用,和值的访问
				3,访问OGNL上下文(OGNL Context)和ActionContext	(这里重点掌握ValueStack值桟)
				4,支持赋值操作和表达式串联
				5,操作对象集合
		> valuestack-值桟,纯粹就是直接翻译过来的
			* 从技术角度来说,ValueStack就是一个接口
			* 从使用角度来说,ValueStack就是一个容器
			* 我们使用ValueStack,最大的作用就是把Action相关的数据,以及WEB相关的对象,携带到页面上
			  简单说,在struts2中,ValueStack把Action中的数据携带到JSP页面上进行展示!
			  '我知道你在想session,request,application...但是我们建议,使用这个框架的时候,尽量别去用那些东西'
	> 为什么要把ValueStack一起讲.他们有关系主要是
		* ValueStack负责装
		* OGNL负责取
	
————————————————————————————————
二,struts2中OGNL				|
————————————————————————————————
	> OGNL在强调一次,别人是一个单独的项目咯!struts2只是捡现成把人家拖框架里来了,"结合sturts2的标签使用"
		* 你你不用struts2框架,也能玩这个.只要导入jar包
		* ognl-3.0.6.jar	(stuts2已经默认导进来了)
	
	> OGNL有一个上下文对象,OgnlContext!可以设置root与非root.
	OgnlContext context = new OgnlContext();
		> OgnlContext本质上就是一个 Map 集合
		> context.setRoot(Object obj);//设置根
	* 根中的数据,不需要加上#来获取,非根中的数据需要添加#来获取!
	* 它可以单独拿出来用,直接写JAVA类就可以使用

	> stuts2对OGNL进行了一定的包装
		* 学习struts2中使用OGNL时,就要知道谁是OGNL,谁是root谁是非root

————————————————————————————————
二,struts2中OGNL演示			|
————————————————————————————————
['struts2中使用OGNL表达式,需要结合struts2的标签使用']
<%@ taglib prefix="s" uri="/struts-tags" %>

1,支持对象方法的调用,如:xxx.demo();
	<s:property value="'abc'.length()"/>
		* 输出3,这就是调用了"abc"字符串对象的length()方法
2,支持类静态方法的调用,和值的访问
	<s:property value="@java.lang.Math@max(15,10)"/>
		* 使用静态方法访问,必须在xml中进行配置.默认值是 false
		* <constant name="struts.ognl.allowStaticMethodAccess" value="true"/>
		* 静态成员的获取是不用去xml配置常量的,任何时候都支持
3,访问OGNL上下文(OGNL Context)和ActionContext	(这里重点掌握ValueStack值桟)

4,支持赋值操作和表达式串联

5,操作对象集合

6,Math类特殊支持
	 <s:property value="@@max(1,2)"/>
	 * 直接俩艾特符号,加上方法名！
	 * 注意,只能使用Math里面的方法,这个只能使用在Math这个类上


————————————————————————————————
二,struts2中OGNL常见使用		|
————————————————————————————————
	> 其实就仨符号:#,$,%

【#】
	★ #代表ActionContext上下文,ActionContext.getContext();
		* <s:property value="#request.name"/>
		* ActionContext.getContext().getRequest().getAttribute("name");
		* session,application同样
		* # == ActionContext.getContext();		# 号就是获取ActionContext对象
	★ 不加 # 号,就在root中查找
		* <s:property value="name">
		* 查询元素的时候,从root栈顶元素开始查找.如果访问指定桟中元素
		* <s:property value="[1].name"/>	//访问第二个元素的name属性
		* <s:property value="[1].top"/>		//访问第二个元素对象
	★ 投影映射('结合复杂的对象遍历')['理解']
		* 所谓投影映射其实,在遍历集合的时候,只拿出我想要数据!也就是对数据进行过滤
		* <s:iterator value="p.{name}" var="pname">
			<s:property value="#pname"/>
		  </s:iterator>
		  * 就是只拿出p这个集合对象中的元素的name属性
		  <s:iterator value="p.{?#thi.age > 20}" var="p">
			<s:property value="#p.name"/>
		  </s:iterator>
		  * 只会取出年龄大于20的的对象.
		   <s:iterator value="p.{?#thi.age > 20}.{name}" var="p">
			<s:property value="#p"/>
		  </s:iterator>
		  * 只会取出年龄大于20的对象的name属性
	★ 使用 # 构造 Map 集合	['掌握']
		* 这东西主要是配合struts的标签用
		* <s:iterator value="#{'name':'kevin','age':22}" var="stu">
			<s:property value="#stu.key"/>		//输出为kevin
			<s:property value="#stu.value"/>	//输出为22
		  </s:iteratpr>
	★ 使用 # 构建 List 集合
		* * 这东西主要是配合struts的标签用
		* <s:iterator value="{'aa','bb','cc'}" var="arr">
			<s:property value="#arr"/>		//输出为:aa,bb,cc
		  </s:iteratpr>
【%】
	★ 百分号作用:用于设定,当前是否要解析其为OGNL表达式
		* <s:property value="#request.userName"/>		//从request域中取出usetName的值
		* <s:property value="%{#request.userName}"/>	//从request域中取出usetName的值
		* <s:property value="%{'#request.userName'}"/>	//原样输出,值是:#request.userName

		* %{表达式}		里面的表达式会被当作OGNL解析
		* %{'表达式'}	里面的表达式不会被当作OGNL解析,当作字符串输出

		* <s:property value=""/>!中的value,一般默认会被当作OGNL表达式,但是有些不会写解析!所以那时候就可以考虑使用%来设置解析


【$】
	★ 在配置文件中去获取 ValueStack(值桟)中的数据
		* 也就是在配置文件中使用OGNL表达式
		1,struts.xml
			<result type="stream">
				<param name="contentType">${contentType}</param>
			</result>
			* 当前Action中的contenType方法,Action会被默认压入值桟.而且它的getxxx方法都会被默认的压入值桟.所以!里面就有xxx方法
		2,校验文件中使用
			* ${min},${max},${length}... ...
			* 校验文件中,获取值桟数据
		3,在国际化文件中使用
			* 在poperties中: username=${#request.username}
			* 在JSP页面中  : <s:text name="username"/>

【'总结'】
	#:获取数据
	%:是否是OGNL表达式
	$:在配置文件中使用OGNL

	
