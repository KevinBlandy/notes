————————————————————
一,注解配置javaBean	|
————————————————————
	之前我们的Bean,都是采用基于xml进行配置,现在可以看看通过注解来进行配置,现在主键大行其道!
	> 如果注解配置的bean和xml配置的bean同时存在,id不同会生成不同的实例!xml生成一个,注解生成一个!
	> 如果同时存在注解,且xml中有配置存在的话!那么xml优先级比较大!

————————————————————
二,组建扫描器		|
————————————————————
	组建扫描(component scanning)
	> spring能够从classpath下自动扫描,侦测和实例化具有特定注解的组建
	特定组建包括如下(类注解,其实他们几个都是一样的.只是标识的地方不同而已) 
	@Component
		* 基本注解,标志了一个受spring管理的组建,一般标识在普通的javaBean上
	@Repository
		* 建议标识持久层组建
	@Service
		* 建议标识服务层(业务层)组建
	@Controller
		* 建议标识表现层组建
	> 对于扫描到的组建,spring'有默认的命名策略',使用非限定类名,第一个字母小写
	> 也可以在注解中通过value属性值标识组建的名称
		* @xxxx(value="自定义名称")//value可以省略,注解你懂的,我有病也是你懂的
		* 在获取的时候就可以通过自定义名称来获取,不定义,默认是简单类名首字母小写！这东西你可以理解为就是xml配置中的id属性
		* 需要注意的是,同一个扫描组建中,不能出现名称相同的注解!不然getBean("xxx")时异常!
	> 当在组建类上使用了特定的主键之后,还需要在spring的配置文件中声明<context:component-scan/>
	> 并且需要引入context命名空间

	<context:component-scan base-package="com.kevin.annotation" resource-pattern="controller/UserController.class" use-default-filters="">
		<context:exclude-filter type="annotation" expression=""/>
		<context:include-filter type="annotation" expression=""/>
	</context:component-scan>

	* base-package
		> 表示扫描的包,src目录下的包的全限定名
	* resource-pattern
		> 表示只扫描base-package包下的指定类,注意定义的格式!
		> 可以使用通配符 xxx/*.class											*/
	* 可以有多个context:include-filter或者context:exclude-filter子标签!
	> context:exclude-filter:表示要排除在外的目标类	<context:exclude-filter type="annotation" expression=""/>
	> context:include-filter:表示要包含的目标类		<context:include-filter type="annotation" expression=""/>
	* type:可以放置一些表达式,常用的有五种
	1,annotation
		> <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Component"/>
		> 所有标注了 @Component 注解的类,都不包含,不进行实例化!忽略
		> <context:include-filter type="assignable" expression="org.springframework.stereotype.Component"/>
		> 只对 @@Component 注解标识的类,才进行实例化处理!其他的全部忽略
		* 注意,要想这个标签生效,还需要在context:component-scan标签添加一个属性:use-default-filters="false",该值默认为true,要设置为false,这个配置才生效
	2,assinable
		> <context:exclude-filter type="assignable" expression="指定的类全类名"/>
		> 忽略所有指定类,的子类!可以指定接口,类的全类名(注意:父标签的use-default-filters属性应该为false,或者不写)
		> <context:include-filter type="assignable" expression="指定的类全类名"/>
		> 只实例化指定类的子类,可以指定接口,类的全类名(注意:父标签的use-default-filters属性应该为true)
	3,aspectj	不常用
	4,regex		不常用
	5,custom	不常用

————————————————————
二,关联关系			|
————————————————————
	其实<context:component-scan>元素还会自动注册,AutowiredAnnotationBaenPostProcessor 实例,该实例可以自动装配具有
	@Autowired 和 @Resource,@inject 注解的属性！

	> @Autowired: 该注解标识在成员字段上,IOC容器在装载的时候,会查找与这个成员对应的类型,并且实例化给他赋值!					 |@Autowired
		* 例如service层里面的dao,引用字段!就可以加上这个,容器就会自动找到dao的类,给你初始化,而且不需要提供字段的get/set方法: |private UserDao userDao;
		* 该注解自动装配具有兼容性的单个Bean属性
		* 可以放在构造器,字段,带有参数的方法!你放哪里?容器找到之后就给你放那里!如果找不到对应的属性的话,就会抛出异常!所有你的字段标识了该注解,那么字段对应的类也要'受到容器的管理'
		* 如果我们希望某一个属性,允许没有找到,可以设置 @Autowired 的一个属性为false
			> @Autowired(required=false)
			> 就是说,找得到就给我注进来!找不到就算了就是null,不抛异常!
		* 存在多个相同类型的类的时候!就是说有多个相同类型,找哪个?
			> 首先会根据被该注解标识的属性/方法参数..的名称!去寻找对应类(多个)上注解的value值(上面的笔记),进行匹配!谁匹配上了,就是谁! 
			> 如果都没找找到,抛出@Autowired异常!(说白了就是,被  标识的属性的变量名,尽量的跟对应类的注解的value值相同,这个value值其实就xml中的id,看上面的笔记)
			> 对于多个同类的情况,还有一个注解 @Qualifier(value="指定类名")
				* 这个注解跟 @Autowired 同时存在,vlaue值为其他bean的类名,首字母小写!这样也能找到指定的类
				* 例如:@Autowired 注解标识地方的是一个接口类型的参数!这个接口有N多个子类!都是属于这个接口类型的,容器不知道该找哪个?就可以用这个注解,来表示要找的类名,名称要小写!而且那个类也要有 @Autowired 注解!且value不写!
			> 当然,如果那个注解有value值,那么我们可以不用这个注解,用上面的方法,把变量名称改为那个类 @Autowired 注解的 value值也是可以找到的!
		* @Autowired 注解也可以应用在 数组类型的属性上,此时spring会把所有匹配的bean进行自动装配
		* @Autowired 注解也可以应用在 集合上面,spring读取该集合的类型信息(泛型),然后自动装配与之兼容的Bean
		* @Autowired 注解也可以应用在 Map 上面,spring会自动装配与Map值类型兼容的Bean,注意,这个时候,Bean的名称,是key,值就是那个Bean!
	> @Resource
		* 跟 @Autowired 功能类似,该注解,要求提供一个Bean名称的属性,如果改属性为null,就采用标注处变量或方法名作为Bean的名称
		* @Resource(name="xxx") = @Autowired(required=false) + @Qualifier(value="xxx")
	> @inject
		* 也是一样,也是按类型匹配注入,但是没有 reqired 属性!
	---建议使用 @Autowired

————————————————————————
三,其他属性的注解配置	|
————————————————————————

1,给普通的类成员用注解赋值
	@value(value="kevin")
	private String  name;
	
	* 都不用提供get/set方法!不过你特么都写这里了,为什么不直接写类上?

2,初始化以及销毁方法
	<bean ini-method="">
	@PsotConstruct
		//定义在初始化方法上
	<bean destory-method="">
	@PreDestory
		//定在销毁方法上

3,配置Bean的作用域
	@Scope("prototype")
	> 设置Bean的作用域,标注在类上
	* prototype	(非单例,不会随着IOC容器创建而创建,每次getBean都会返回新的实例)
	* request
	* session	
	* singleton(默认,单例的,随着IOC容器创建而创建)

————————————————————————
四,拓展					|
————————————————————————
	Spring 3.0以javaConfig为核心.提供使用java类定义Bean信息的方法

	@Configuration 指定 POJO类为Spring提供Bean定义信息
	@Bean 提供一个Bean定义信息

	* 如果某个类,配置特别复杂,基于xml和注解都不好使.就可以使用这种方式来进行!

	* 只要让Spring扫描到这个类就OK
	-------------------------------------
	@Configuration						//这个注解代表这个类就是一个配置类
	public class Factory
	{
		@Bean(name="car")				//代表这就是一个Bean,会注册到IOC
		public Car getCar()
		{
			retuen new Car(100,"宾利");
		}
		@Bean(name="Customer")			//这也是一个Bean,也会注册到IOC
		public Customer getCustomer()
		{
			return new Customer(100,"Kevin");
		}
	}

————————————————————————
五,XML和注解结合使用	|
————————————————————————
	在实际的开发中,spring对于注解和xml的选择.都有可能.甚至还有可能一起使用
		注解:自动注入,简单.都不用提供set方法
		XML :结构性更强,Bean更容易管理
	
	Bean,通过xml注册
	属性,通过注解注入

	<context:annotation-config/>
	* 在xml中配置这注解,就可以让那些存在于:类成员,方法上的注解生效
	* @Resource @PostConstruct @PreDestory @AutoWired

	<context:component-scan>
	* 当注册这个的时候,会自动的注册 <context:annotation-config/>
	* 这个注解是扫描类上的,同时也会让那些存在于:类,类成员,方法上的注解生效
	* 因为我们的类都是交给了xml进行管理,类上并不用注解.所以在合并开发的时候,只用上的一个(只对方法,类成员上的注解生效)!
