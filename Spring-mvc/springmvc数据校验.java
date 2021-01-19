

数据校验
	@InitBinder 
	* 被该注解标识的方法,可以对WebDataBinder对象进行初始化
	* WebDataBinder是DataBinder的子类,用于完成由表单字段到JavaBean属性的绑定
	* 该方法不能有返回值,'必须是void'
	* InitBinder方法的参数通常是WebDataBinder
		@InitBinder
		public void inteBinder(WebDataBinder binder)
		{
			
		}

	* 可以进行数据的'类型转换,格式化,校验'.
	* WebDataBinder 部分方法
		setDisallowedFields("name");	//指定处理方法中POJO的哪个属性,不被赋值.就是这个设置了以后,那么处理方法形参中的User的name字段.不会被赋值.就算是客户端提交了name参数
	

	////////////////////////////


SpringMVC校验数据
	* 注解校验
	* 错误消息
	* 错误消息转发

	JSR 303
		* JSR 303 是JavaBean,数据合法性校验的标准框架.已经包含在JAVAEE6.0中
		* JSR 303 通过在javaBean属性上标注类似于 @NotNull,@Max 等标准的注解指定校验规则.并通过标准的验证接口对Bean进行验证
		* '注解都会有一个:message属性,当校验失败的时候.可以通过 getDefaultMessage 获取到这个提示信息'
	
		@Null				必须为null
		@NotNull			不能为null
		@AssertTrue			必须为true
		@AssertFalse		必须为false
		@Min(value)			必须是一个数字,且必须大于等于value
		@Max(value)			必须是一个数字,且必须小于等于value
		@DecimalMin(value)	..
		@DecimalMax(value)	..
		@Size(max,min)		必须是一个数字,而且值在max和min之间
		@Digits(integer,fraction)..
		@Past				必须是一个过去的日期,只能比现在早
		@Future				必须是一个未到的日期,只能比现在晚
		@Pattern(value)		正则... ...

		@Length(min=6,max=20)		//字符串长度.最小6位,最大20位.message表示提示信息

	HibernateValidator 是JSR303 的一个参考实现,除了支持所有标准的校验注解外,还支持一些其他的扩展注解
		@Email				必须是一个邮箱表达式
		@Length				字符串长度必须在指定的范围内
		@NotEmpty			被注释的字符串必须非空
		@Range				被注释的字符串必须在合适的范围内

	1,SpringMVC的<mvc:annotation-driven />,会默认的装配好一个'LocalValidatorFactoryBean',
	  标注 @Vliad 注解,就可以让SpringMVC 在完成数据绑定后执行数据校验工作.
	
	2,在已经标注了JSR303注解的表单/命名对象前,标注一个 @Vliad,框架就会在请求参数绑定前,根据注解.来进行校验
	3,框架是:通过,对处理方法签名的规约来保存校验结果.
		* 前一个表单/命令对象的校验结果保存到随后的形参中,这个保存校验结果的形参必须是'BindingResult'或'Errors'类型.这俩类都在:org...validation包下
	

	校验步骤
		1,在配置文件中,添加<mvc:annotation-driven />配置!
		2,加入Hibernate validator验证框架,添加jar包
		3,在javaBean的属性上添加注解
		4,在目标方法javaBeand类型的前面加上 @Valid 注解
			public void demo(@Valid User user BindingResult result)
			{}
			* 校验错误信息,就会存入reulst
			* 必须挨着,中间不能有其他的元素
		
		* 怎么判断是否有错误?
			if(result.hasErrors())
			{
				List<ObjectError> errors = result.getAllErrors();
				for (ObjectError e : errors )
				{
					System.out.println(e.getDefaultMessage());
				}
			}
		* 怎么在JSP页面显示
			model.addAttribute("errors",errors);	//存入request域
			<form:errors path="userName"/>
		* 错误消息国际化

		我你去吗的,太恶心了!有这些功夫.老子自己写校验代码都写出来了!
		
=========================

	<!-- 注解驱动 -->
	<mvc:annotation-driven validator="validate"/>
	<!-- 校验器 -->
	<bean id="validate" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
		<!-- JSP303校验器,Hibernate -->
		<property name="providerClass"/>
		<!-- 错误信息资源文件,不指定则默认加载classpath:ValidationMessages.properties -->
		<property name="validationMessageSource" ref="messageSource"/>
	</bean>
	<!-- 错误信息资源文件 -->
	<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
		<!-- 资源文件名 -->
		<property name="basename">
			<list>
				<value>classpath:ValidationErrorMessage</value>
			</list>
		</property>
		<!-- 资源文件编码格式 -->
		<property name="fileEncodings" value="utf-8"/>
		<!-- 对资源文件内容缓存时间,单位为秒 -->
		<property name="cacheSeconds" value="120"/>
	</bean>
	
	在POJO中添加校验规则
	

	分组校验
		* 每个Controller方法使用不同的校验规则
	定义多个校验分组(定义一个JAVA接口),不需要定义任何方法,仅仅是对不同的校验规则进行分组

	public interface ValidGroupA
	{
		//仅仅校验字段长度
	}
	public interface ValidGroupB
	{
		//仅仅校验字符非空
	}

	在POJO的校验注解有一个属性:group={ValidGroupA.class}	//标注这个校验属于哪个分组?可以定义多个分组
	在Controller方法中指定分组校验
	@Validated(value={ValidGroupA.class})User user
	//指定使用这个分组的校验




----------------------------------校验实战
	1,导入相关的依赖
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>5.2.4.Final</version>
		</dependency>
	
	
	2,
		在实体bean上添加注解
