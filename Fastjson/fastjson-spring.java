--------------------
FastJson-整合springmvc|
--------------------
	# 普通转换器
		FastJsonHttpMessageConverter
		FastJsonHttpMessageConverter4


	  <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
                <property name="fastJsonConfig">
                    <bean class="com.alibaba.fastjson.support.config.FastJsonConfig">
                        <property name="charset" value="UTF-8"/>
                        <property name="serializerFeatures">
                            <array>
                                <!-- 输出null字段 -->
                                <value>WriteMapNullValue</value>
                                <!-- 输出key使用双引号 -->
                                <value>QuoteFieldNames</value>
                                <!-- 空集合输出[] -->
                                <value>WriteNullListAsEmpty</value>
                            </array>
                        </property>
                        <!-- 日期格式 -->
                        <property name="dateFormat" value="yyyy-MM-dd HH:mm:ss"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

	# 代码配置
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

		fastJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

		return new HttpMessageConverters(fastJsonHttpMessageConverter);
	
--------------------
FastJson-跨域支持	|
--------------------
	# 添加 JSONPResponseBodyAdvice
		@Bean
		public JSONPResponseBodyAdvice jSONPResponseBodyAdvice() {
			return new JSONPResponseBodyAdvice();
		}
	
	# Jsonp接口方法，添加注解 @ResponseJSONP
		@RequestMapping("/test")
		@Controller
		public class TestController {
			
			@GetMapping("/jsonp")
			@ResponseJSONP(callback = "callback")
			@ResponseBody
			public Object jsonp() {
				return Message.success("Hello");
			}
		}

		* 该注解就一个属性：callback ，用于指定客户端提供的回调参数