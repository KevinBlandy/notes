----------------------------
Beetl-Spring MVC 简单集成	|
----------------------------
	1,添加 spring 配置
		<bean id="beetlConfig" class="org.beetl.ext.spring.BeetlGroupUtilConfiguration" init-method="init"/>

		<bean id="viewResolver" class="org.beetl.ext.spring.BeetlSpringViewResolver">
				<property name="contentType" value="text/html;charset=UTF-8"/>
				<property name="suffix" value=".html"/>
		</bean>
		
		* beetl不建议是用视图解析器的preffix来指定目录,而是通过classpath下的.beetl.properties,有可能导致Layout布局无法使用
		* suffix 可以配置在 视图解析器中,无碍
		* 同其他集成方式一样，模板的配置将放在beetl.properties中。
		* 获取 GroupTemplate
			BeetlGroupUtilConfiguration config = (BeetlGroupUtilConfiguration) this.getApplicationContext().getBean("beetlConfig");
			GroupTemplate group = config.getGroupTemplate();

	2,添加properties配置
		RESOURCE.root=/WEB-INF/templates/
	
	3,Controller
		ModelAndView modelAndView = new ModelAndView("index/index");
        return modelAndView;

----------------------------
Beetl-Spring MVC 高级集成	|
----------------------------
	* spring集成还允许注册被spring容器管理的Function，Tag等，也还允许配置多个视图解析器等功能
		<bean name="beetlConfig" class="org.beetl.ext.spring.BeetlGroupUtilConfiguration" init-method="init">
			<!--- 配置文件地址 -->
			<property name="configFileResource" value="/WEB-INF/beetl.properties"/>

			<!-- functions -->
			<property name="functions">
				<map>
					<entry key="testFunction" value-ref="testFunction"/>
				</map>
			</property>
			<property name="functionPackages">
				<map>
					<entry key="fp" value-ref="testFunctionPackage"/>
				</map>
			</property>
			
			<!-- tags -->
			<property name="tagFactorys">
				<map>
					<entry key="html.output" value-ref="testTagFactory"/>
					<entry key="html.output2" value-ref="testTagFactory2"/>
				</map>
			</property>
		</bean>

		<bean name="testTagFactory" class="org.beetl.ext.spring.SpringBeanTagFactory">
			<property name="name" value="testTag"/>
		</bean>
		<bean name="testTagFactory2" class="org.beetl.ext.spring.SpringBeanTagFactory">
			<property name="name" value="testTag2"/>
		</bean>


		<bean name="beetlViewResolver" class="org.beetl.ext.spring.BeetlSpringViewResolver">
			<!-- 如果系统中有多个 BeetlGroupUtilConfiguration 必须要显示的指定其中一个 -->
			<property name="config" ref="beetlConfig"/>
			<property name="contentType" value="text/html;charset=UTF-8"/>
		</bean>
	
		# BeetlGroupUtilConfiguration 属性详解

		configFileResource 
			# 属性指定了配置文件所在路径，如果不指定，则默认在classpath下

		functions 
			# 指定了被spring容器管理的function，key为注册的方法名，value-ref 指定的bean的名称

		functionPackages
			# 指定了被spring容器管理的functionPackage，key为注册的方法包名，value-ref 指定的bean的名称

		tagFactorys 
			# 注册tag类
			# 'key是tag类的名称,也就是在模板中使用的标签名'
			# value-ref指向一个org.beetl.ext.spring.SpringBeanTagFactory实例，
			# 该实例是一个Spring管理的Bean。有一个name属性.'属性name对应的bean就是tag类'。
			# 需要注意，由于Tag是有状态的，因此，必须申明Scope为 "prototype"。如代码:
				@Service
				@Scope("prototype")
				public class TestTag extends Tag {

				}
		
		typeFormats
			# 同functions，参数是 Map, Format>，其中key为类型Class

		formats
			# 同functions，参数是 Map，其中key为格式化函数名

		virtualClassAttributes
			# 同functions，参数Map, VirtualClassAttribute>，其中key为类型Class
			# 配置的就是指定类的虚拟属性

		virtualAttributeEvals 
			# 类型为List,配置的是'一些类的虚拟属性'

		resourceLoader
			# 资源加载器 ，值是 实现ResourceLoader的一个Bean

		errorHandler 
			# 错误处理，值是实现ErrorHandler的一个Bean

		sharedVars
			# 同 functions ，类型是Map，可以在此设置共享变量

		configProperties
			# 类型是Properties，可以覆盖配置文件的某些属性


--------------------------------
Beetl-Spring MVC 多个视图解析器	|
--------------------------------
	1,servlet.xml配置	
		<bean id="beetlConfig" class="org.beetl.ext.spring.BeetlGroupUtilConfiguration" init-method="init"/>
		<!-- beetl -->
		<bean id="viewResolver" class="org.beetl.ext.spring.BeetlSpringViewResolver">
			<property name="viewNames">
				<list>
					<value>/templates/***/</value>
				</list>
			</property>
			<property name="config" ref="beetlConfig"/>
			<property name="contentType" value="text/html;charset=UTF-8"/>
			<property name="suffix" value=".html"/>
			<property name="order" value="0"/>
		</bean>
		
		<!-- jsp -->
		<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<property name="prefix" value="/WEB-INF/views/"/>
			<property name="suffix" value=".jsp"/>
			<property name="order" value="256"/>
		</bean>
	
	2,Controller
		@RequestMapping(value = "test",method = RequestMethod.GET)
		@Auth(required = false)
		public ModelAndView modelAndView(){
			return new ModelAndView("/templates/test/test");
		}
	3,beetl.properties
		RESOURCE.root=/WEB-INF/
	
	4,目录结构
		
		WEB-INF
			|-templates		
				|-test
					|-test.html
			|-views
				|-..
