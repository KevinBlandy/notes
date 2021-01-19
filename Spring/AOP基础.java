————————————————————————
一,AOP					|
————————————————————————
	基本就不介绍了,文字性的东西,你也不爱看!去百度吧,很多!
	AOP,是一个横向抽取的技术,AOP并不是由spring定义的,而是一个叫做'AOP联盟',的组织来定义的

	AOP的底层原理就是:动态代理(JDK)
		* JDK的动态代理,对实现了接口的类生成代理
	
	Spring 的AOP中采用了种
		1,动态代理(jdk)
			* 只能对实现了接口的类生成代理
			* Proxy 不写了
		2,CGlib代理机制:
			* 可以对没有实现接口的类生成代理
			* CGLIB是一个开源项目,是一个强大的高性能的,高质量的Code生成类.可以在运行时期扩展java类与java实现接口
			  Hibernate支持它来实现PO字节码的动态生成、
			* Hibernate生成持久化类的javassit包!
			* CGLIB,生成代理机制,其实生成了一个真实对象的子类!
			现在的CGLIB,不用引入!框架已经集成

	Aspecj 是一个基于java的AOP框架,Spring2.0开始,SrpingAOP引入对Aspect的支持,
	Aspecj拓展了JAVA语言,提供了一个专门的编译器,在编译时提供横向代码的织入!

	['结论']
		如果你的类实现了接口,那么它就使用JDK的动态代理来生成代理对象,
		如果你的类没有实现任何接口.那么就使用CGLIB来生成代理对象
		框架能够自动的判断

		程序中应该有限对接口创建代理,便于程序解耦维护
		标记为final的方法不能被代理,因为无法进行覆盖
			JDK,是针对接口生成的子类.接口中的方法不能使用final修饰
			CGLIB是针对目标类生产子类,因为类或者方法不能使用final

————————————————————————
二,AOP术语				|
————————————————————————
	连接点(Joinpoint):所谓连接点就是指那些被拦截到的点,在spring中这些点指的是方法,因为spring只支持方法类型的连接点!
	切入点(Pointcut) :所谓切入点是指我们要对哪些Joinpoint进行拦截的定义
	通知/增强(Advice):所谓通知是指拦截到Joinpoint之后所在的事情就是通知
						1,前置通知
						2,后置通知
						3,异常通知
						4,最终通知
						5,环绕通知
	引介(Introduction):引介是一种特殊的通知在不修改类代码的前提下,Introduction可以在运行期为类动态地添加一些方法或者Field
	目标对象(Target) :代理的目标对象
	织入(Weaving)	 :是指把增强引用到目标对象来创建新的代理的过程
						Spring 采用动态织入,而Aspectj采用编译时期织入和类装载期织入
	代理(Proxy)		 :一个类被AOP织入增强后,就会产生一个结果代理类
	切面(Aspect)	 :是切入点和通知(引介)的结合

————————————————————————
三,AOP术语粗暴理解		|
————————————————————————
	连接点	:就是被增强的'目标方法些'!
	切入点	:就是'真正被增强的方法'!
	通知	:'增强的点',方法执行前增强,方法执行后增强!异常后增强!环绕增强!
	引介	:'类级别'的增强,动态的给指定类添加一个方法,或者是一个字段
	目标对象:'被增强的对象'
	织入	:把'增强应用到目标对象的过程'!
	切面	:

————————————————————————
四,Spring 中的传统AOP	|
————————————————————————
	* 不用重点去记,一般实际都不是使用传统AOP开发,都是使用ASPECTJ
	AOP联盟为'通知'-- Advice 定义了 org.aopalliance.aop.interface.Adice
	Spring 按照 Advice 在目标方法的连接点位置,可以分为五个类
		1,前置通知
			* MthodBeforeAdvice
			* 在目标方法执行前执行增强
		3,后置通知
			* AfterReturningAdvice
			* 在目标方法执行后执行增强
		4,环绕通知
			* MehtodInterceptor
			* 在目标方法执行前后实施增强
		5,异常抛出通知
			* ThrowsAdvice
			* 在方法抛出异常后实施增强
		引介通知 ['不讲']
			* IntroductionInterceptor
			* 在目标类中添加一些新的方法和属性
	Spring 中的切面类型
		Advisor :就是 Spring 传统AOP中的一个切面,都是有一个切点和一个通知组合
			* Aspect	:也是框架中的一个切面,可以允许多个切点,和多个通知

		Advisor:一般切面,它本身就是一个切面,对目标类所有方法进行拦截,也就是不带有切面的切面
		PointcutAdvisor:代表具有切点的切面,可以指定拦截目标类哪些方法
		IntroductionAdvisor:代表引介切面,针对引介通知使用切面('了解')
	
	开发步骤
	1,导入相应的jar包
		* spring-aop-3.2.0.RELEASE.jar//Spring的jar包
		* com.springsource.org.aopalliance-1.0.0.jar//AOP联盟的包
	2,编写'被增强的类',尽量的可以实现接口
	3,编写增强类,实现指定的接口
	4,在xml中配置,自动代理
		* 生成代理,Spring 基于 ProxyFactoryBean类,底层会自动的判断用JDK还是CGLIB(实现接口与否)
		<bean id="customerDaoProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
			<property name="target" ref="customer"/>								//被增强的类
			<property name="proxyInterfaces" value="com.kevin.dao.CustomerDao"/>	//被增强类实现的接口全限定名
			<property name="interceptorNames" value="beforAdvice"/>					//使用哪个增强类
		</bean>


————————————————————————
五,自动代理				|
————————————————————————
	
	自动创建代理
		BeanNameAutoProxyCreator
			* 根据Bean名称创建代理
		DefaultAdvisorAutoPrxoyCreator
			* 根据Advisor本身包含信息创建代理
		AnnotationAwareAspectJAutoProxyCreator
			* 基于Bean中的Aspectj注解进行自动代理
	
