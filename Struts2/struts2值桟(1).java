————————————————————————————————
一,struts2ValueStack与OGNL入门	|
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
	> ValueStack主流应用,就是解决:把Action数据携带到jsp页面
————————————————————————————————
二,struts2ValueStack的内部结构	|
————————————————————————————————
	> ValueStack就是一个接口!
	 com.opensymphony.xwork2.util.ValueStack
	> 我们使用它,就是把它作为一个容器.用于携带数据到页面,在页面用OGNL表达式取出数据

	1,值桟详解
		★ ValueStack有一个实现类,叫做'OgnlValueStack'
		★ 每一个Action都有一个ValueStack
			* 一个Action,一个request,一个ValueStack是对应的.也就是说ValueStack生命周期跟request生命周期一样
		★ ValueStack中存储了当前Action对象,以及其他常用的WEB对象(request,response,appliction,session...)
		★ Struts2把ValueStack以'struts.valueStack'为名,把'ValueStack存储到了request域中'
			* 从request域中取出值桟的时候,尽量使用类名.常量:ServletActionContext.STRUTS_VALUESTACK_KEY
			* 你非要直接写struts.valueStack也行,就是怕你写错了而言
			
		
	2,ValueStack内部结构
		> 值桟由两部分组成
		★ ObjectStack	Struts把动作相关的对象压入ObjectStack中	—— List
		★ ContextMap	Stauts把各种映射关系(一些 Map 形式对象)压入ContextMap中
			* parameters:	该 Map 中包含当前请求的参数
			* request:		该 Map 中包含当前request对象中所有属性
			* session:		该 Map 中包含session对象中所有属性
			* application:	该 Map 中包含当前appliction对象中的所有属性
			* attr:			该 Map 按顺序来检索某个属性:request,session,application

	3,ValueStack中存在两个重要的属性
		★ root	-->	CompoundRoot	
			> 其实就是继承了 ArrayList
		★ context	-->	OgnlContext
			> 其实就是一个 Map
	4,context对应Map引入root对象
		> 就是说啊,context这个 Map 中还有 root 的引用!就是ValueStack另一个重要的属性!
		★ context中还存在request,session,application,attr,parameters对象引用
		★ OGNL表达式,访问root中数据的时候,不需要"#",访问request,session,application,attr,parameters对象数据,必须写#
	5,操作值桟,默认操作的就是root元素

	['重点']
		'其实,ValueStack里面的那个Context,就是OGNL里面的Context'

	['结论']
		'ValueStack'它有俩部分,一个是 List,一个是 Map
		在struts2中,List 就是root ,Map 就是OgnlContext
		默认情况下,在struts2中从ValueStack获取数据时,从root中获取!
	
—————————————————————————————————————————
二,值桟对象的创建以及跟ActionCotext的关系|
————————————————————————————————————————
	1,值桟对象是在请求时创建的,每次请求都在前端过滤器中创建新的.

	2,ActionContext 跟 (ValueStack)值桟的关系
		★ 'ActionContext中持有了值桟的引用'
		
		前端控制器(Filter)中的源码
			ActionContext ctx = ActionContext.getContext();
			if (ctx  != null)
			{
				stack = ctx.getValueStack();l
			}
	
	3,如何获取值桟对象(两种方式)
		★ request域中有值桟对象//从request中获取
			ValueStack vs = (ValueStack) ServletActionContext.getRequest().getAttribute(ServletActionContext.STRUTS_VALUESTACK_KEY);
		★ ActionContext中也有值桟对象//从ActionContext中获取
			ValueStack vs = ActionContext.getContext().getValueStack();
			* 推荐使用这个
		★ 还有一种方法 
		  ValueStack vs = ServletActionContext.getValueStack(ServletActionContext.getRequest())
			
	
		
		
	4,ValueStack内部有两部分,一个是 Map(OgnlContext) 一个是 List(root)
		★ 而且 Map 中还持有 List!
————————————————————————————————————
三,往值桟里面存入数据				|
————————————————————————————————————
	> 在struts2中,一般都是把数据存储在root,很少往context中存储
	> 先说往root中存!这root本身就一个 List,
	> 拓展:桟结构 -- 先进后出

1,往root中存储数据有两种方法
	★ push(Object obj);
		* 把数据存放到第一个元素的位置(栈顶).
		* 底层代码 add(0,Object),最后一个进来的永远都在最顶上
		* 这东西存进去后,是一个 Object

	★ set(String name,Object obj);
		* 先从root中获取 Map,如果 Map 不存在,就创建一个!再把传递的参数封装进去
		* 再调用上面的push(Map map);把这个Map压入栈顶
		* 底层是 HashMap 
		* 至始至终,root里面都只有一个 Map ,你下次再往里存东西的时候,还是上次同一个 Map,所以要注意!key相同会产生覆盖的问题!

————————————————————————————————————
四,从JSP页面中获取值桟数据			|
————————————————————————————————————		
		['重点']
		* 记住一个原则,根里面的数据不要#号,非根里面的数据,需要#号
		* 根,就是root....context需要#号!
获取root
		1,如果桟顶是 Map 集合,获取的时候,直接可以通过 Map 的key来获取
		★	<s:property value="key"/>

		2,如果栈顶是非 Map 集合数据	
		★	<s:property value="[0].top"/>
			* 如果value="[0]",只写0,那么它就从0角标开始挨个往下全部输出.['从0开始向下查找所有']
			* 如果value="[1]",只写1,那么就它从1角标开始挨个往下全部输出.['从1开始向下查找所有']
		3,针对于 Map 还可以自动查找
		★	<s:property value="key"/>
			* 其实就是,Map 不在栈顶.你输入之后还是会依次向下查找,找到为止.找不到就算了!

		['总结']
			Object 就是通过root容器的角标,来查找,Map 通过key来查找!
			push(Object obj);方法进去的数据,都是要靠角标来完成
			set(String key,Object value);方法进去的数据,可以靠角标,也可以靠key
		
获取context
		> 存了什么request,session,application...等等!
		> 其实这些都不是我们真正的WEB对象,都是被框架包装过的!
		> 这个不是根,要加#
		1,request数据			
			<s:property value="#request.key"/>
			* 从request域中获取指定key的值
		2,session数据			
			<s:property value="#session.key"/>
			* 从session域中获取指定key的值
		4,appliction数据		
			<s:property value="#application.key"/>
			* 从application域中获取指定key的值
		5,attr					
			<s:property value="#attr.key"/>
			* 由小到大,依次查找指定key的值.跟EL表达式全域查找一个德行!
		6,parameters
			* 用于获取请求参数
			<s:property value="#parameters.name[0]"/>
			* 获取指定名称的请求参数,因为请求参数有可能多多个值,所以需要声明下标
			* 如果是多个参数,而又不声明下标的话,那么就会把数组里面的所有值都列出来,用","号隔开!字符串化!

		
————————————————————————————————
五,值桟携带数据分析以及使用		|
————————————————————————————————

Action向JSP携带数据,都是什么样的数据？
	1,普通的文本数据,字符串!
		★ fieldError	校验数据的错误信息提示(这就一个哥儿是Map结构,带key value的)
			添加:this.addFieldError("key","value");
			获取:<s:fielderror fieldName="msg"/>

		★ actionError	业务逻辑错误,例如:用户名密码错误
			添加:this.addActionError();
			获取:<s:actionerror/>
		★ message		就纯粹的信息

			添加:this.addActionMessage();
			获取:<s:actionmessage>


	2,比较复杂的数据(分页的时候,给页面传递的PageBean)
	案例1
		Action 中存储数据
			ValueStack vs = ActionContext.getContext().getValueStack();
			List<User> list = new ArrayList<User>();
			list.add(new User("KevinBlandy","admin"));
			list.add(new User("LitchLeon","12345"));
			list.add(new User("LanechoCliche","abcde"));
			vs.push(list);
		页面中获取数据
			<s:iterator value="[1].top" var="user">//取名user			
  			用户名:<s:property value="#user.userName"/><br/>
  			密　码:<s:property value="#user.passWord"/><br/>
  			</s:iterator>
		★ 这里,我们使用了一个iterator标签来迭代集合
		★ 把集合中的每个元素起名叫user,那么这个user是存在context中的,不在root中!那么你要获取,就要加上#号!
		★ 如果在使用iterator进行迭代时,没有给迭代元素起名!那么就不加
			<s:iterator value="[1].top">
  			用户名:<s:property value="userName"/><br/>
  			密　码:<s:property value="passWord"/><br/>
  			</s:iterator>
		
————————————————————————————————
六,值桟中默认压入的数据分析		|
————————————————————————————————
	默认压入到我们ValueStack中的数据
	1,当前访问的的Action对象,会被压入到值桟!
		★ 在拦截器调用前就压入了
		★ Action如果想传递数据给JSP,只要把数据保存到成员变量.提供get/set方法就可以在JSP页面获取!
		★ 只要你的Action,提供了getXxx方法.那么该属性就会以:xxx为key,返回值为value的 Map 形式出现在值桟中
		★ <s:iterator value="list"...//进行遍历就是了,只要你的Action中有getList();这个方法,这个会自动往下找!你懂的!		
	2,对于模型驱动的Action
		★ ModelDriven,没忘记吧?Action内部的实例对象,不需要get/set方法!只需要实现接口,返回对象就可以完成赋值
		★ ModelDriven接口,有一个单独拦截器
		 <interceptor name="modelDriven" class="com.opensymphony.xwork2.interceptor.ModelDrivenInterceptor"/>//18罗汉之一
			* 在这个拦截器,将Model对象,压入了值桟:stack push(model);
				源码:略..自己去看吧,执行了压入值桟操作
			* 如果Action实现ModelDriven接口,指定默认栈顶对象就是Model对象(在Action上面)
			* 有趣的是,Action类的ModelDriven的接口方法:getModel(),因为有get,也会把返回的Model存入值桟!俩个其实是同一个对象
				['但是']:如果你在Action执行方法里面,对Model的数据进行修改,那么在值桟中就会不一样
					* 一个是在拦截器中,被框架调用了getModel(),把对象进行了拦截,放入值桟
					* 一个是到达了Action之后,被execute(),方法修改了值之后.再次被框架调用getModel(),以'model'的名称.存入值桟这个model其实就是这个对象!
					* 要注意的就是,ModelDriven这个接口的实现问题,genModel(),的数据也会出现在值桟中
			* 名称就是类的,全类名,值会全部显示在右边

	['总结']
	两个,一个就是Action,一个就是get方法里面的东西
————————————————————————————————
七,几大对象的体系图				|
————————————————————————————————
ActionContext
ValueStack
root			--> ArrayList
context			--> Map
OgnlContext

-------------------------------------

'ValueStack'		(俩root都是同一个)			'OgnlContext'
root ———|———————————————————————————————————————|—>root
		|										|
		|		(context直接包含了对面整个)		|		
context-|————————————————————————————————————'>'| context
	↑
	|
	|(持有ValueStacke的引用)
	|
	|
---------------
'ActionContext'|
---------------

ValueStack声明周期,一个request,一个Action,一个ValueStack

细节:
ActionContext 这个类,实现了 Serializable 接口并且里面有个神器
static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();
	> 线程容器,你懂的!

['注意']
	ActionContext是绑定到线程中的!你就能明白,为什么每次请求都会开启一个新线程!
	struts2是这样做的!每次请求来,拦截.我就给这个线程绑定一个ActionContext!而,ValueStack是绑定在ActionContext上的!只要请求存在,
	线程没结束!这东西就一直在!
['再次强调']
	root	存储的是Action相关的信息
	context 中存储的是映射关系,以及WEB开发中常见对象
		* request,session,application... ...

————————————————————————————————
八,值桟数据通过EL访问			|
————————————————————————————————
	为什么EL能访问值桟(ValueStack)中的数据.
	例如:
		vs.set("username","Kevin");
		<s:property values='userName'/>
		${userName}
		★ 这东西没有向仨大域对象中存储数据
		★ 但是俩输出都一样,都是Kevin
	
	['原因']
		struts2中所有使用的request,对象!已经是被包装增强过的request对象!
		传统:${userName}---仨大域,挨个查找!
		增强:${userName}--->request.get... ----> ValueStack

		★ 其实就是增强了request的 get方法!原来是仨大域对象中挨个查找!现在是先找request域,如果request域没有!直奔ValueStack!

