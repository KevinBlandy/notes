
@Component
@Service
@Controller
@Repository
@Scope
@Transactional

@Configuration
	# 属性
		* String value() default "";
	# 当bean变得复杂,难以配置的时候,可以使用该注解标识一个类,标识当前类是一个配置类
	# 该类下的所有方法都是工厂方法,这些方法返回的对象,会被加入IOC容器

@Bean
	# 属性
		* String[] name() default {};		
		* Autowire autowire() default Autowire.NO;
		* String initMethod() default "";
		* String destroyMethod() default AbstractBeanDefinition.INFER_METHOD;
	# 标识在 @Configuration 标识类的方法上,表示当前方法的返回对象会被存入IOC,bean的名称就是方法名
	# 如果是在 @Configuration 中多次调用带 @Beam 的方法,返回的对象都是IOC中那一个唯一的


@Conditional
	# 属性
		* Class<? extends Condition>[] value();

	# 一般都是配合 @Configuration 进行使用
	# 属性是一个 Class <clazz extends Condition> 数组,Condition 该接口有个 返回 boolean 的方法
		* 如果该方法返回 true,那么就会实例化标注的bean
		* 返回 false 则忽略
	# 这句代码可以标注在类上面，表示该类下面的所有@Bean都会启用配置.也可以单独的标注在方法上

@ComponentScan
	# 属性
		很多,查阅API
	# 使用注解的方法去扫描加载类


@EnableAspectJAutoProxy
	# 属性
	# 使用注解的方式,开启对AspectJ代理的支持

@PostConstruct
	# 标识在bean的方法上,在该方法执行构造函数(创建对象)后执行

@PreDestroy
	# 标识在bean的方法上,在容器销毁之前执行

@Profile
	# 属性
		String[] value();
	# :指定组件在哪个环境的情况下才能被注册到容器中,不指定,任何环境下都能注册这个组件
	# 可以标识在类,方法上,用于指定当前环境,如果符合配置(下),则当前类生效
		* spring.profiles.active

@CrossOrigin
	# 跨域支持

@DependsOn
	# 标识在 Bean 组件上,表示该组件依赖于该注解指定的组件(该组件会被优先的加载到mvc)

@Primary
	# 自动装配时当出现多个Bean候选者时,被注解为 @Primary 的Bean将作为首选者,否则将抛出异常 



