————————————————————————
一,AspectJ介绍			|
————————————————————————
AspectJ
	> Java社区里最完整流行的AOP框架
	> 在Spring2.0以上版本中,可以使用基于AspectJ注解或基于xml配置的AOP
	> 实际上,spring自己也有一个AOP框架的实现,但是这个AspectJ更值得我们推荐去使用
	
————————————————————————
二,基于注解的方式		|
————————————————————————
	> 在spring应用要用到AspectJ注解,必须要在classpath下包含AspectJ的类库
		* aspectj.weaver.jar
		* aopalliance.jar
		* spring-aspects.jar

	> 要在springIOC容器中启用AspectJ注解支持,只要在Bean文件中跟元素下定义一个空的XML元素
		* <aop:aspectj-autoproxy/>
		* <aop:aspectj-autoproxy proxy-target-class="true" />
			* proxy-target-class 默认值为 false,表示使用JDK的动态代理,更改为"ture",表示使用CGLib动态代理
			* 不过即使proxy-target-class设置为false，如果目标类没有声明接口，则spring将自动使用CGLib动态代理。 
			
	> 当springIOC容器侦测到Bean配置文件中的<aop:aspectj-autoproxy>元素时,会自动与Aspectj切面匹配的Bean创建代理
		* 友情提醒:因为这里是注解配置,如果Bean也是注解配置,所以务必要使用到扫描器,记得配置啊

一,切面类(我的理解就是,切面类就是包含了增强方法的类)
	> 1,在类上添加两个注解
	@Aspect
	@Component
	@Order(1)
		> 第一个注解就是标志该类是一个'增强类',专业的就叫切面,横切关注点
		> 切面类,必须交给IOC容器管理,所以也要打码@Component
		> 第三个是为了设置运行优先级的,在下面有仔细解释(目标方法存在多个相同通知的试试,谁的值大,谁的通知就先执行)

二,在切面类中创建方法,也就是传说中的增强方法(也就是特么的通知)!
	> 这个方法会根据注解的特性,在某一个时间,'配合目标方法'执行!
	通知注解
	@Before("execution(public int com.kevin.Demo.demo(int,int))")
		> 前置通知,被这个注解标识的方法,会在execution指定的'目标方法执行前执行'!
		> 如果指定的方法不存在,也不会抛出异常(xml配置测试过,注解没试过,八成一样的)
		> 注意execution赋值的格式:权限 返回值 包名..类名.方法名(方法签名)			[注意,没有形参]
		> execution还支持'通配符',例如:@Before("execution(public int com.kevin.Demo.*(int,int))")
			* 注意,上面通配符只对,名称不一样.返回值,方法签名都必须符合execution表达式的函数奏效(下面有具体的一个表达式总结)
		> 前置通知没有办法阻止目标方法的执行

	@After("execution(public int com.kevin.aop.imple.ArithmeticCalculator.add(int,int))")
		> 后置通知,同上啦,只不过这个是在execution指定方法执行后执行
		> 不论目标方法'是否抛出异常,都会执行'
		> 这个通知'不能访问目标方法的执行结果',也就是目标方法return的结果!

	@AfterReturning(...)
		> 返回通知,配置同上.在目标方法成功return之后执行
		> 这个通知,在目标方法出现异常后不会执行
		> 这个通知,是'可以获取到方法的返回值'的!
		  1,该注解上添加一个属性,注意,有多个注解属性存在!value属性的特权失效,此时必须指出value变量名
			@AfterReturning(value="execution(...)",returning="result")	//returning的属性就是一个变量名,可以自定义
		  2,在该注解标识的方法中,添加一个形参(方法签名)					
			public void advice(Object result)							//形参的名称要注意保持跟注解returning属性名称一样
		  3,在该方法(通知)中,这个result,就是目标方法的返回值,注意!是Object类型的

	@AfterThrowing(...)
		> 异常通知,配置同上.在目标方法'抛出异常后执行'
		> 这个通知可以获取到目标方法出现的异常对象(信息)
		  1,需要在注解上添加一个属性
		  	@AfterThrowing(value="execution(...)",throwing="ex")	//throwing的属性就是一个变量名,可以自己定义
		  2,获取目标的方法出现的异常
			public void exce(Exception ex){}						//需要注意的是,形参的变量的名称要跟throwing属性保持一致
		  3,这个形参,就是目标方法发生的异常对象,在这个通知'方法'里面可以获取到!
		注意:
		* 如果这个(通知)方法的形参确切指出是哪种异常,那么该'通知'(方法),只会在目标方法抛出,指定异常及其子类异常,后才执行
		  public void exce(NullPointerException ex)	
		> 此处形参指定了异常类型是空指针异常,那么只有目标方法出现空指针异常的时候,该注解才会执行!
		> 出现其他异常,该通知(方法)不会执行
		* 有必要说明的是,如果目标方法对异常进行了trycatch处理!也不会执行,其实就是没异常,千万别混!
		* 通知(方法),在目标方法抛出异常前执行,但是要记住的是!异常还是会抛出来的!别愣啊大兄弟!

	@Around(...)	
		> 环绕通知,类似于动态代理的全过程,这个通知会有一个特殊的形参:ProceedingJoinPoint
		> 它可以控制'是否执行目标方法'
		> 环绕通知必须有有返回值,而且这个返回值会替代目标方法的返回值(动态代理InvocationHandler接口的那个抽象方法... ...)
		> 如果是返回类型不匹配,那么会抛出异常,例如:目标方法的返回值类型是String,而环绕通知返回值是int.

		--------------直接上代码
		@Around("...")
		public Object around(ProceedingJoinPoint pj)
		{
			Object obj = null;
			String methodName = pj.getSignature().getName();//目标方法名称
			Object[] params = pj.getArgs();					//目标方法参数	
			try 
			{
				//获取类
				Object = pj.getTarget();  
				//此处可以作为前置通知有点像doFilter方法
				obj = pj.proceed();//正儿八经的调用目标方法,得到返回结果
				//此处可以作为后置增强(通知)
			} 
			catch (Throwable e)
			{
				//此处可以作为异常通知
				e.printStackTrace();
			}
			return obj;		//返回目标方法执行后的结果
		}
		* 注意,在调用目标方法的时候,如果被这个环绕通知挂上钩!那么返回值是由这个环绕通知决定的,如果返回值,不跟目标方法的返回值一样!那么就会抛出异常
		* ProceedingJoinPoint 的 一些方法
			Object[] getArgs
				返回目标方法的参数
			Signature getSignature
				返回目标方法的签名
			Object getTarget
				返回被织入增强处理的目标对象
			Object getThis
				返回AOP框架为目标对象生成的代理对象
			Class[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
				返回拦截方法的'参数的类型'.

* 很显然,这些所谓的'通知',都是在@Aspect类中的!如果不是.....我也不知道,没试过!哈哈哈哈
* 再次对通配符的问题进行描述,比较重要
	> 上面演示了一个通配符,其实很多地方都支持通配符
	@Before("execution(* com.kevin.*.*(..))")
	* 别怕,这个代表:任意修饰符,任意返回值,指定包下的任意类的任意方法,而且是任意的方法签名...
	* 反正比较灵活,自己看着办!
* 一个切面(类),可以配置多个通知
* 一个目标方法被多个同类型的'通知'指向!都会执行!执行顺序?没研究过!
* 其实还有一个引入通知(就不讲了,很少使用)
	> 引入通知是一种特殊的通知类型,它通过为接口提供实现类!允许对象动态地实现接口,就像对象已经在运行时扩展了实现类一样!


三:重用切点表达式
		> 其实很简单,别被吓着!
		> 就是上面有很多通知你都看到了,他们的value属性很多都是一样的(上面的全是一样的)!一个切面(类)中如果多个通知(方法 )的value值相同!我们可以这样搞
			1,在当前切面(类)中创建一个方法(暂时成为:快捷方法),不要写任何的逻辑代码,添加一个注解.看下面
				@Pointcut(value="很长的一个的指定的方法")
				public void demo(){}			//没必要写任何逻辑代码,纯粹就是为了简化注解的属性配置
			2,就可以把,在本切面(类)中任何的通知(方法)上,的value属性修改为"demo()"
				@Before("demo()")				//这时候就不用再写辣么长的指定方法了,直接写上面的方法名称
				public void beforeMethod(JoinPoint join)
		> 你以为完了?No,更叼的就是,其！他！类(切面)！也！可！以！用！
			@Before("配置了快捷方法的类.快捷方法()")				//这时候就可以使用其他类中的快捷方法,当然,如果'太远',我估计要加包名,我没试过.哈哈哈
			public void beforeMethod(JoinPoint join) 

四,所谓的连接点
	> 其实就是可以在'增强方法'(通知),中获取目标方法运行时传递的参数,以及目标方法的方法名称的一个对象
	> 其实很简单,只需要在'增强方法',也就是被'通知'注解标识的方法中添加一个参数:JoinPoint类型的参数,通过这个对象就可以获取到目标方法的参数,方法名称
	示例代码:
	@通知注解(.....)//通知类
	public void advice(JoinPoint join)
	{
		String methodName = join.getSignature().getName();//获取目标方法的名称
		LObject[] params = join.getArgs();//获取目标方法的参数
	}
	
————————————————————————
三,切面的优先级			|
————————————————————————
	> 切面,就是一个类了,你也了解了!
	> 如果一个目标方法,出现了多个切面,里面都有对它的相同属性的通知(方法)!谁在前?谁在后?
	> 指定切面的优先级很简单
	* 给切面类,再加上一个注解
	@Order(1)
	> 值越小,优先级越高!
	> 不能是小数,可以使负数


————————————————————————
四,基于xml配置的方式	|
————————————————————————
	> 除了使用AspectJ注解声明切面,Spring也支持在Bean配置文件中声明切面,这种声明式通过aop schema中的xml元素完成的
	> 正常情况下:基于注解的声明要优先于基于xml的声明
	> 通过Aspectj注解,切面可以与Aspectj兼容,而基于xml的配置则是Spring专有的,由于Aspectj得到越来越多的Aop框架支持,所以注解风格编写的切面将会有更多重用的机会
	> 切面类,与普通Bean一样的配置格式,配置在xml中...然后,看下面

<!-- 配置AOP -->
<aop:config>
	<!-- 配置切点表达式 -->	<!--这东西吧,就是目标方法,可以存在多个,这里同样也支持通配符-->
	<aop:pointcut expression="execution(public int com.kevin.aop.byxml.ArithmeticCalculator.add(int, int))" id="pointcut"/>
	<!-- 配置切面类 -->	<!-- 就是一个类,ref切面类bean标签的id值,至于order,就是优先级咯 -->
	<aop:aspect ref="aspect1" order="1">	<!-- 切面类也要在xml中以普通bean的形式注册,然后这里ref指向它id就OK -->
		<!-- 配置前置通知 -->
		<aop:before method="beforeMethod" pointcut-ref="pointcut"/>	<!--pointcut-ref就上面的配置,目标方法!某个aop:pointcut的id值  -->
		<!-- 配置后置通知 -->
		<aop:after method="afterMethdo" pointcut-ref="pointcut"/>
		<!-- 配置返回通知 -->
		<aop:after-returning method="atferReturn" pointcut-ref="pointcut" returning="result"/>	<!--returning就是method方法中代表返回值(Object)的参数名,如果xml中配置的名称跟方法中的名称不相同,抛出异常  -->
		<!-- 配置异常通知 -->
		<aop:after-throwing method="exce" pointcut-ref="pointcut" throwing="ex"/>	<!-- throwing,就是menth方法中异常参数的变量名,如果不一样,就抛出异常 -->
		<!-- 配置环绕通知 -->			
		<aop:around method="around" pointcut-ref="pointcut"/>	<!-- 环绕通知,用得很少,几乎不用配 -->
	</aop:aspect>
	<!-- 配置切面类 --><!-- 这是另一个类,同上配置,也就是想说,aop:config里面可以配置多个切面类 -->
	<aop:aspect ref="aspect2" order="2">
		<aop:before method="befero" pointcut-ref="pointcut"/>
		<aop:after method="after" pointcut-ref="pointcut"/>
	</aop:aspect>
</aop:config>

	> 结构体现
		<aop:fonfig>
			|--可以是多个目标方法
			|--可以是多个切面类
				|--可以使多个通知配置
		</aop:fonfig>
	['注意']
		卧槽,这问题特么差点要我老命了!
		aspect 中的的切点,会按照从上到下的顺序来执行,你信么？起先我也不信!但是,好像是真的！
		
————————————————————————
五,值得注意的问题		|
————————————————————————
1,关于那个 Spectj表达式	
	> 在通知中通过value属性定义切点(Spectj表达式)
	通过execution函数,可以定义切点的方法切入
	语法
		execution(<访问修饰符>包路径<返回类型><方法名>(<参数>)<异常>)
	例如
		匹配所有类的public方法
			execution(public * *(..))
		匹配指定包下所有类方法
			execution(* com.kevin.*(..))	//只检索Kevin包下的类
			execution(* com.kevin..*(..))	//检索Kevin包下的类,及其所有子包的类
		匹配指定类所有方法
			execution(* com.kevin.action.*(..))
		匹配所有save开头的方法
			execution(*save*(..))
		匹配指定类型的子类
			... ...
		匹配包含了指定注解的方法
			@annotation(xxxxx....Permission)
	
2,关于Advisor 和 Aspect 的区别
	Advisor:Spring 传统意义上的切面:支持1个切点和一个通知的组合
		* 继承指定的接口,就能完成'指定的通知'!继承这个可以在方法前用,继承那个可以在方法后用.一大堆.
		* 还要在xml中配置,你要乐意自己去看教程,这是很传统的东西,一般很少用!

	ASPECT :可以支持多个切点和多个通知的组合
		* 一个类,可以定义多个方法.当然,就4这个笔记讲的这些东西咯!
————————————————————————
六,高级知识				|
————————————————————————
	# AOP中获取处理的方法对象

	private void beforeTransationHandle(JoinPoint point) throws Exception{
		//拦截的实体对象
		Object target = point.getTarget();
		//拦截的方法名称
		String methodName = point.getSignature().getName();
		//拦截的方法参数
		Object[] args = point.getArgs();
		//拦截的方法参数类型
		Class[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
		//获取到的目标方法
		Method method = null;
		try {
			//通过反射获得拦截的method
			method = target.getClass().getMethod(methodName, parameterTypes);
			//如果是桥则要获得实际拦截的method
			if(method.isBridge()){
				for(int i = 0; i < args.length; i++){
					//获得泛型类型
					Class genClazz = getSuperClassGenricType(target.getClass(),0);
					//根据实际参数类型替换parameterType中的类型
					if(args[i].getClass().isAssignableFrom(genClazz)){
						parameterTypes[i] = genClazz;
					}
				}
				//获得parameterType参数类型的方法
				method = target.getClass().getMethod(methodName, parameterTypes);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public Class getSuperClassGenricType(Class clazz, int index) {
			Type genType = clazz.getGenericSuperclass();        // 得到泛型父类
			if (!(genType instanceof ParameterizedType)) {
				return Object.class;
			}
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (!(params[index] instanceof Class)) {
				return Object.class;
			}
			return (Class) params[index];
		}


————————————————————————
六,切点表达式			|
————————————————————————
	
	# 指定类的子类
		within(com.tedi.zhsq.common.base.BaseService+)
			* expression="within(com.tedi.zhsq.common.base.BaseService+)"

			* 指定类及其的所有的子类的方法,都会执行
			* 适用于面向接口开发的项目,且抽象出了 BaseService,AbstractService 的项目
						BaseService(高级抽象)
							|
							|
					 ---------------
					|				|
					(实现)			(继承)
				AbstractService	  UserService
					|				|
					|				|
					|			(最终实现)
					 -------- UserServiceImpl
		
	
	# 指定包和类
		execution(* com.tedi.community.web.service..*(..))
			* 指定包下('含多级子包')的所有类的所有方法.
			* 可以把'Base'系列的抽象类,都写在 service.base包中,子类继承后,父类方法也会有AOP
		
		execution(* com.tedi.community.web.service.*.*(..))
			* 指定包下('不含子包')的所有类的所有方法.
		

		
