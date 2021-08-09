----------------------------
Spring-Boot ע��			|
----------------------------
	@EnableAutoConfiguration
		# ����
			Class<?>[] exclude() default {};
			String[] excludeName() default {};

		# �� spring boot ������·���е� jar ������,Ϊ��ǰ��Ŀ�����Զ�����
		# ����
			* ����� spring-boot-starter-web ����,�ͻ��Զ����Tomcat��springmvc�����Զ�������
			* ����� spring-boot-statter-data-jpa ����,�ͻ��Զ�����JPA��ص�����
	
	@Configuration (spring)
		# ��@Configurationע���ʶ���࣬ͨ����Ϊһ�������࣬���������һ��xml�ļ�����ʾ�ڸ����н�����BeanԪ���ݣ�
		# ������������Spring����application-context.xml�������ļ����� @Bean ��ǩ���������ڸ�xml�ļ��У�������һ��beanʵ���� 

		boolean proxyBeanMethods() default true;
			* Ĭ��Ϊtrue,��ʾcglib��Ϊ @Configuration ����һ��������
			* @Configuration �ɴ�������ڲ���������һЩ��ʶ @Bean �ķ������ͻ᳢�Դ�IOC��ȥ��ȡʵ����������ÿ�ζ�ִ����� @Bean ����

	
	@SpringBootApplication
		# ��������
			Class<?>[] exclude() default {};
				* �������е� Class ����,�ᱻ�ر��Զ�����

		# ����һ�����ע��,�����һ�µ�ע��
			* @SpringBootConfiguration		
				* �յ�ע��,��ע���ϱ�ʶ��:@Configuration
			* @EnableAutoConfiguration 
			* @ComponentScan (��ɨ��·��)
		# spring boot �����Զ�ɨ���ע���ʶ�����ڰ�,ͬ����,�Լ��Ӱ���Bean
			* �����JPA��Ŀ,������ɨ���ע@Entity��ʵ����
		# �����������õ�λ���� grouId + arctifactId ��ϵİ�����
	

	@ConfigurationProperties
		# ����
			prefix
				* ָ��properties������ͳһ��ǰ׺
			ignoreUnknownFields 
				* �Ƿ����δƥ�䵽������

		# ��ע���ʶ��ĳ��bean,���ڰ��ⲿ�����ļ��е�����,ע�뵽bean��������
		# �����Ա�ע�� @Configuration ���е�ĳ��������,�÷���(�÷���ͬʱҲҪ��ע@Beanע��Żᱻspring����)���ض��������ֵҲ�����ɸ�ע��ע��(prefix����ָ��ǰ׺)
	

	@EnableConfigurationProperties
		# ����
			Class<?>[] value() default {};
		# ��һ�� �� @ConfigurationProperties ��ʶ����,���뵽IOC����
	
			
	@ServletComponentScan
		# �� SpringBootApplication ��ʹ��@ServletComponentScan
		# ע���Servlet��Filter��Listener ����ֱ��ͨ�� @WebServlet��@WebFilter��@WebListener ע���Զ�ע�ᣬ�����������롣
	


	@AutoConfigureAfter
		# ֻ����������
			Class<?>[] value() default {};
			String[] name() default {};
		
		# �����Զ�����������,��ʾ���Զ���������Ҫ������ָ�����Զ�������������֮��
		# ���� Mybatis ���Զ�������,��Ҫ������Դ�Զ�������֮��
			@AutoConfigureAfter(DataSourceAutoConfiguration.class)
			public class MybatisAutoConfiguration {
		
	@AutoConfigureBefore
		# ͬ��,ͨ����ע��ָ����������,Ӧ��Ҫ�ڵ�ǰ��֮������
	

	@ImportResource
		# ����
			String[] value() default {};
			String[] locations() default {};
			Class<? extends BeanDefinitionReader> reader() default BeanDefinitionReader.class;
	
		# ͨ����ע�⵼��spring��xml�����ļ�
		# ֧�ִ��ļ�ϵͳ����classpath�¼���
	
	
	@PropertySource
		# �����ⲿ�����ļ�
			String name() default "";
			String[] value();
			boolean ignoreResourceNotFound() default false;
			String encoding() default "";
			Class<? extends PropertySourceFactory> factory() default PropertySourceFactory.class;

		# ����ǩ����һ��
			<context:property-placeholder location="classpath:jdbc.properties" />

		# ֧�ִӲ�ͬ��Դ��������
			@PropertySource(value = { "file:c:/application.properties","classpath:app.properties"})

	@Profile
		# ֻ��һ������
		# ���Ա�ʶ����,������,ָ��һ�����߶������������ļ���
			String[] value();
		# ����������
			spring.profiles.active=dev
		# ʹ��demo
			@Profile("dev")  // ֻ�м�����dev�����ļ�ʱ,�Ż���ظ�controller
			@RestController
			public class ProdController
		
		# �ڳ�����, ͨ���������жϵ�ǰ�Ļ���
			@Autowired
			Environment environment;

			// ����2�����л���
			Profiles profiles = Profiles.of("dev", "test");

			// �жϵ�ǰ����, �Ƿ���ָ�������л�����
			boolean accept = environment.acceptsProfiles(profiles);
	
	@RequestPart
		# ��mutipart�����У����� @RequestParam ��ʶmutipart�е�������
		# �������Զ��ĸ���ContentType��װ����Ϊ����
			test (@RequestPart("file") MultipartFile file,  @RequestPart("json") RequestBody jsonBody)
			
		String value() default "";
		String name() default "";
		boolean required() default true;
	
	