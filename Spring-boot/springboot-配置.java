----------------------------
Spring-Boot ��������		|
----------------------------
	# ���������
		* һ��Ӧ�ö�����һ�� XxxxApplication ��,�þ�һ�� main ����,���Ҹ��౻ @SpringBootApplication ��ע
		* @SpringBootApplication ����һ�����ע��,��������µ�ע��
			* @Configuration 
			* @EnableAutoConfiguration 
			* @ComponentScan 

		* spring boot �����Զ�ɨ���ע���ʶ�����ڰ�,ͬ����,�Լ��Ӱ���Bean
			* �����JPA��Ŀ,������ɨ���ע@Entity��ʵ����

		* �����������õ�λ���� grouId + arctifactId ��ϵİ�����
	
	# �ر��ض����Զ�����
		* @SpringBootApplication ��һ������
			Class<?>[] exclude() default {};

		* �������е� Class ����,�ᱻ�ر��Զ�����
			@SpringBootApplication(exclude = {DataSourceConfiguration.class});
	
	# ����Banner
		* spring ������ʱ�����һ��Ĭ�ϵ�����ͼ��
			  .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			  '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::        (v1.5.1.RELEASE)
		* �� src/main/resources ���½��ļ�:banner.txt
		* ����վ,������ϲ������ʽ:http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20 
		* ���Ƶ� banner.txt��,��������
		* �ر�banner,�����ŷ���
			1,��main��������޸�
				SpringApplication springApplication = new SpringApplication(SampleController.class);
				//����BannerΪ�ر�ģʽ
				springApplication.setBannerMode(Banner.Mode.OFF);
				springApplication.run(args);

			2,ʹ�� fluent API �����޸�
				new SpringApplicationBuilder(SampleController.class).bannerMode(Banner.Mode.OFF).run(args);
		* ����,Banner�����ǿ���ʹ��һЩ����ı��� ${name}
				
				
	# �����ļ�
		* spring boot ʹ��һ��ȫ�ֵ������ļ�:application.properties / application.yml
		* ���ļ�Ӧ������ src/main/resources Ŀ¼,������classpath:/config Ŀ¼��
		* ��ѡ������(�ܶ�)
			server.port				//�˿�
			server.context-path		//������·��


	# ʹ�� xml ����
		* spring boot �ᳫ��xml����,������ʵ����Ŀ��,���������������
		* ʹ�� @ImportResource ������xml����,��ע���� spring ��ע��
		* @ImportResource({"classpath:xxx.xml","classpath:yyy.xml"});
				
	
	# ���ֵ������
		* ��ʱ��������Ŀ��Ҫ���������һЩֵ,���ǲ������ֶ���ȥд����
		* RandomValuePropertySource �����������ɲ�������Ҫ�ĸ��ֲ�ͬ���͵����ֵ���Ӷ���ȥ���ڴ��������ɵ��鷳��
		* RandomValuePropertySource �����������ֺ��ַ��������ֵ����Ͱ��� int �� long�������޶����ֵĴ�С��Χ��
		* �������ļ�����"random."��Ϊǰ׺����������
			user.id=${random.value}
			user.count=${random.int}
			user.max=${random.long}
			user.number=${random.int(100)}
			user.range=${random.int[100, 1000]}

----------------------------
Spring-Boot �ⲿ����		|
----------------------------
	# spring boot ��������Աʹ�������ļ���YAML �ļ������������������в������������ȼ���ͬ������ֵ��
	# Spring Boot �ṩ�� SpringApplication ������������� application.properties �ļ�����ȡ��������ֵ��SpringApplication ���������λ���������ļ���
		1,��ǰĿ¼��"/config"��Ŀ¼��
		2,��ǰĿ¼��
		3,classpath �е�"/config"����
		4,classpath
		* �����˳��Ҳ��ʾ�˸�λ���ϰ����������ļ������ȼ�(�Ӹߵ���)

	# �������ȼ�˳��Ƚϸ��ӡ��������ȼ��Ӹߵ��͵�˳��
		1,�����в�����
		2,ͨ�� System.getProperties() ��ȡ�� Java ϵͳ������
		3,����ϵͳ����������
		4,�� java:comp/env �õ��� JNDI ���ԡ�
		5,ͨ�� RandomValuePropertySource ���ɵġ�random.*�����ԡ�
		6,Ӧ�� Jar �ļ�֮��������ļ���
		7,Ӧ�� Jar �ļ��ڲ��������ļ���
		8,��Ӧ������ Java �ࣨ����"@Configuration"ע��� Java �ࣩ��ͨ��"@PropertySourceע������������"�ļ���
		9,ͨ�� "SpringApplication.setDefaultPrope"ties"������Ĭ�����ԡ�

	# �����в�������
		* spring boot ���Ի���jar������,�����jar������������������Ӳ���
			java -jar xxx.jar --server.port=9090
			java -jar xxx.jar --spring.profiles.active=remote
		* SpringApplication ��Ĭ�ϻ����"--"��ͷ�������в���ת����Ӧ���п���ʹ�õ����ò������� "--name=Alex" ���������ò��� "name" ��ֵΪ "Alex"��
		* �������Ҫ������ܣ�����ͨ��  SpringApplication.setAddCommandLineProperties(false) ���ý��������в�����

	# ������������
		* ��Ӧ�ó�����,ֱ��ʹ�� @Value("${key}"),�Ϳ��Զ�ȡ�� application.properties �е�����
	
	# ���Ͱ�ȫ������
		* ʹ�� @ConfigurationProperties ��һ��bean�������Խ��й���
		* Demo
			1,�� application.properties ���������
				user.name=kevin

			2,ʹ��ע��,Ĭ�ϵ�ʹ�� application.properties
				@ConfigurationProperties(prefix="user")
				class User{
					private String name;
				}

	# ��־����
		* spring boot ֧�� java.util.Logging,Log4j,lo4j2��logback��Ϊ��־���
		* Ĭ��,ʹ��logback��Ϊ��־���

		* Ĭ�ϵ������ļ�����(������ʹ��,�������,��log4j2�����Ѻ�)
			> ������־�ļ���ַ
				logging.file=d:/mylog/log.log
			> ������־�������
				logging.level.root=INFO
				logging.level.org.springframework.web=DEBUG		
				

		* �����ⲿ�ļ�������(�Ƽ�ʹ��)
			> spring boot ��Ĭ�ϼ���:classpath:logback-spring.xml����classpath:logback-spring.groovy

			> ʹ���Զ��������ļ�,ָ�������ļ��ĵ�ַ
				logging.config=classpath:logback-xxx.xml
				* ע�⣺��Ҫʹ��logback���������������spring boot��������ȫʵ����
				* �����ļ������ַ�ʽ����

			> 1,ʹ�û���spring boot������
				<?xml version="1.0" encoding="UTF-8"?>
					<configuration>
					<include resource="org/springframework/boot/logging/logback/base.xml"/>
					<logger name="org.springframework.web" level="DEBUG"/>
				</configuration>

			> 2,�Զ��������
				* ��,�Լ�ȥ��logback������
								
			
	# profile ��������
		* ��ͬ�Ļ���,ʹ�ò�ͬ��profile
		* ÿ��profile�ĸ�ʽ�̶�
			application-{name}.properties

		* application.properties�ļ��м���ָ�����ļ�
			spring.profiles.active={name}

	# �������ⲿ�����ļ�
		spring.profiles.include[0]=datasource
			|-application-datasource.properties
		spring.profiles.include[1]=redis
			|-application-redis.properties

	# bootstrap.yml
		* bootstrap.yml(bootstrap.properties)������������ʱִ��,Ӧ���ڸ�������������Ϣ��ȡ

		* ����ʹ��������application.yml��ʹ�õ�������

		* application.yml(application.properties) Ӧ�ó�������������Ϣ,�����������ú�������ģ������ʹ�õĹ���������

		* bootstrap.yml ���� application.yml ����

		* ����ͨ������������bootstrap
			spring.cloud.bootstrap.enabled=false
	

------------------------
�໷������				|
------------------------
	# application.properties�����ļ��ᱻSpringBootӦ���Զ�����, ������һ������˳��
		1. ��ǰĿ¼��/config��Ŀ¼��
		2. ��ǰĿ¼��
		3. classpathĿ¼��/config��Ŀ¼��
		4. classpathĿ¼��

		* private static final String DEFAULT_SEARCH_LOCATIONS = "classpath:/,classpath:/config/,file:./,file:./config/";
		* �б�ߵ�����, �����б�͵�����
	
	# ͨ��Environment���� spring.config.name �����Զ���applicaiton.properties�ļ�������
		ConfigFileApplicationListener.CONFIG_NAME_PROPERTY = "spring.config.name";
	
	# ͨ��Environment���� spring.config.location �Զ��� applicaiton.properties �ļ���λ��
		ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY = "spring.config.location";

	# ͨ��Environment���� spring.config.additional-location �Զ��� applicaiton.properties �ļ���λ��
		ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY="spring.config.additional-location";

		* spring.config.location �Ḳ��Ĭ�ϵ�����·��
		* ��ֻ��׷��, ׷�ӵ�λ������Ĭ�ϵ�λ��֮ǰ
	
	# �⼸������Ҫ��Ӧ������֮ǰ����, ������Ҫ�������õ�'ϵͳ��������'����'ϵͳ����'����'�����в���'�����ȶ�ȡ


	# ָ�������ļ����ļ���
		* ��������,�Ϳ��������������ļ���һ����Ҫ�洢����Ŀ��
		* ���Դ洢�ڱ��ش���, ʹ����������
			ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY="spring.config.additional-location";
		
		* �����ڴ�����������, ����ж��ֵ, ʹ�ö��ŷָ�
			String configLocation = "file:${user.home}" + File.separator + "config" + File.separator;
			System.setProperty(ConfigFileApplicationListener.CONFIG_ADDITIONAL_LOCATION_PROPERTY, configLocation);


			System.setProperty("spring.config.additional-location", "optional:file:${user.home}/config/,optional:file:${user.home}/config-dev/");

		
		* ֧��ʹ�� spel ���ʽ
		* ������Ŀ�����в�Ҫ�洢�����������������, ��������������, ͨ�� 'spring.config.additional-location' �ƶ��洢�������������Ĵ�����

	
	# Ҳ����ͨ��������ָ������������ļ�����Ŀ¼
		spring.config.additional-location="optional:file:${user.home}\\config"

		* �������Ҫ������ boostrap.yml �вŻ���Ч����Ҫ���spring-cloud-context
			<dependency> 
				<groupId>org.springframework.cloud</groupId> 
				<artifactId>spring-cloud-context</artifactId> 
			</dependency>
		
	
	# springboot2.4����ͨ�� EnvironmentPostProcessor ����ɶ�̬�����ļ�����չ

		

------------------------
Environment				|
------------------------
	# org.springframework.core.env.Environment
	# �ö�����IOC,��ʾ��ǰspring������������Ϣ
		
		@Autowired
		private Environment env;
		
		//��ȡ��ǰ����ĵ��ļ�����,Ҳ����:spring.profiles.active ָ�����ļ�
		String[] profiles = env.getActiveProfiles();

		//���Դ������л�ȡ����
		String dbPass = env.getProperty("bbs.dbPassowrd");