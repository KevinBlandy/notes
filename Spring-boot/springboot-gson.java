---------------------------------
Gson
---------------------------------
	# Maven
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-json</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
		</dependency>
	
	# 注解驱动, 不加载默认的Jackson
		@SpringBootApplication(exclude = { JacksonAutoConfiguration.class })

		* 自动装配类: GsonAutoConfiguration
	
	# 配置(配置类: GsonProperties)
		spring.mvc.converters.preferred-json-mapper=gson
		spring.gson.date-format=
		spring.gson.disable-html-escaping=
		spring.gson.disable-inner-class-serialization=
		spring.gson.enable-complex-map-key-serialization=
		spring.gson.exclude-fields-without-expose-annotation=
		spring.gson.field-naming-policy=
		spring.gson.generate-non-executable-json=
		spring.gson.lenient=
		spring.gson.long-serialization-policy=
		spring.gson.pretty-printing=
		spring.gson.serialize-nulls=
		

		* 参考文档
			https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#json-properties
	
	# 自定义配置, 只需要自己添加一个 GsonBuilder 实例到ioc就是路
		@Configuration
		public class GsonConfiguration {
			
			private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			@Bean
			public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) { // customizers 是读取到的配置文件中关于gson的配置

				GsonBuilder builder = new GsonBuilder();
				// 设置通过配置文件定义的属性
				customizers.forEach((c) -> c.customize(builder));
				
				/**
				 *自定义属性
				 */
				
				// 日期类型的格式化
				builder.registerTypeHierarchyAdapter(TemporalAccessor.class, new JsonSerializer<TemporalAccessor>() {
					@Override
					public JsonElement serialize(TemporalAccessor src, Type typeOfSrc, JsonSerializationContext context) {
						return new JsonPrimitive(DATE_TIME_FORMATTER.format(src));
					}
				});
			}
		}

	# SpringBoot 通过 GsonAutoConfiguration 类来自动的装载 Gson
		* 可以在组件中注入
			@Autowired
			Gson gson;
