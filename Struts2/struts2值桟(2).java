————————————————————————————————
一,再谈值桟问题					|
————————————————————————————————
	* 这东西是一个难点,也是struts2的核心所在!
	* 只要是一个MVC框架,必须解决的问题就是:'数据的存和取'
	
	* struts2利用值桟存数据,所以值桟是一个存储数据的内存结构!
	* 把数据存放值桟中,在页面使用OGNL进行显示

	* ValueStack 是一个接口,struts2中使用的是它的实现类 OgnlValueStack
	* OGNL,根本就不是struts2的东西,它是一个对象导航图语言.可以跟据对象来操作对象的对象
	  你也可以把它理解为,另一个框架.struts2直接就把人拿过来用了.

————————————————————————————————
二,值桟 --	生命周期			|
————————————————————————————————	
	1,获取值桟
		ValueStack valueStack = ActionContext.getContext().getValueStack();
			* ActionContext 可以直接获取值桟,它有值桟的引用.
		ValueStack valueStack = ServletActionContext.getValueStack(ServletActionContext.getRequest())
		ValueStack valueStack = (ValueStack) ServletActionContext.getRequest().getAttribute(ServletActionContext.STRUTS_VALUESTACK_KEY);
			* 值桟,还存放在request域中.
	2,上面的值桟,其实都是同一个!相信你也应该注意,有一个方法是从request域中获取的,
	  很显然,值桟的生命周期,跟request,action是一样的!单次请求有效!
		
————————————————————————————————
三,值桟 --	内存结构			|
————————————————————————————————	
	1,ValueStack 是一个接口,但是我们再struts2中!使用的是一个实现类叫做:OgnlValueStack
	2,ValueStack 有两部分内存区域,也就是两个内存结构.一个 Map ,一个 ArrayList
	['context']
		* OgnlContext(OGNL)
		* 又称之为Map桟,其实是人家ognl本身就有的东西
		* 源码中 OgnlContext 其实是 implements Map,所以它是一个 Map 集合
		* 内部重要key,分析
			1,_root
				这个key为_root,的值,就是就是'root '.也就是说'context'持有了对root的引用
			2,_values
				这个key叫做_values,的值.又是一个 Map(HashMap),
				request,session,application,response,等都在这个 Map 里面
				请求参数,以及访问的Action,以及一些映射(配置)信息,也都在这个里面
			
	['root']	
		* CompoundRoot(xwork)
		* 又称之为对象桟
		* 源码中 CompoundRoot 其实是 extends ArrayList,所以是一个 ArrayList 集合

————————————————————————————————
四,值桟 --	数据存储			|
————————————————————————————————
	['CompoundRoot']
		valueStack.push(Object obj);
			* 把一个对象存入了对象桟的栈顶,你也可以通过直接获取'对象桟的引用',valueStack.getRoot(),通过操作 List 把对象存入桟尾!
	['OgnlContext']
		
————————————————————————————————
五,值桟 --	数据取出			|
————————————————————————————————
	['CompoundRoot']
		valueStack.peek();
			* 获取对象桟,栈顶的数据
	['OgnlContext']

————————————————————————————————
六,值桟 --	结构草图			|
————————————————————————————————
ValueStack(值桟)
	context(implements Map)
		* _root
			就是引用了ValueStack中的另一个结构	
		* _values(HashMap)				
			1, 存放了Servlet容器的内容(request,response,session,appliction -- 其实都是被包装过的,他们本身也是Map结构)
			2, 当前请求的Action
	root(extends ArrayList)
		* ValueStackAction(当前请求的Action)
		* DefaultTextProvider(国际化)

	OGNL本身自带的OgnlContext,也就是context.被struts2进行了包装,又给按了一个root在外面!于是就形成了:valueStack!

————————————————————————————————
七,值桟 --	常用API				|
————————————————————————————————
	ValueStack valueStack = ActionContext.getContext().getValueStack();
	* 获取方法有三个,这里不详说.自己看上面

['操作对象桟']
	valueStack.push(Object obj);
			* 把一个对象,放如到'root'中,栈顶的位置.root,也就是一个桟数据结构的容器,实际执行的是:root(0,o);
	valueStack.peek();
			* 获取的就是对象桟,栈顶的元素.实际执行的是:root.peek();这里面直接 return get(0);
	valueStack.pop()
			* 移除栈顶的第一个元素
	valueStack.setParameter(String key,Object value);
			* 把栈顶对象的key属性,赋值为value
			* 可以利用这个方法,直接改变对象桟中栈顶对象属性的值
	valueStack.getRoot();
			* 返回的就是 List<Object>,就是对象桟的引用.你可以自己把对象添加到栈顶,也可以添加到桟底
			* 你得到引用,可以手动的获取/存储,栈顶还是桟末的数据!

['操作Map桟']
	可以通过操作Servlet域对象.来对 Map 桟的'_values'的数据进行存入/取出操作!
	
————————————————————————————————
八,值桟 --	默认压入数据				|
————————————————————————————————
	* 灰常牛逼的手绘值桟结构图
	* 来自于<s:debug/>标签
	------------------------------------------------------------------------------------------
	Object								Property Name	Property Value								
	------------------------------------------------------------------------------------------
										container		There is no read method for container
										errorMessages	[]
										actionErrors	[]
	com.kevin.action.ValueStackAction	actionMessages	[]
										fieldErrors		{}
										texts			null
										valueStack		null
										locale			zh_CN
										errors			{}
	------------------------------------------------------------------------------------------
	com.opensymphony.xwork2.DefaultTextProvider	texts	null										
	------------------------------------------------------------------------------------------

	* 其实ValueStackAction这个Action,继承了ActionSupport!那么上图中的所有 Property Name 项目.全是来自于本类以及父类的get方法
	['总结']
		只要是Action中存在的getXxx();方法.就会被值桟压入到,CompoundRoot栈顶!
			* Property Name 就是 getXxxx()的xxx
			* value 就是getXxxx()返回值!
		把对象放入对象桟中,可以直接使用<s:property value="属性名"/>直接访问
		如果对象桟中出现多个相同的属性,那么这个标签value属性会从上往下开始寻找,找到即止!


	

	
	
	

			
	