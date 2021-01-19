----------------------------
枚举处理					|
----------------------------
	# 默认的枚举处理是直接把枚举的名称序列化为字符串

		
	

--------------------------------
把枚举对象转换为自定义的数据类型|
--------------------------------
	# 定义Serialize和Deserialize
		import java.io.IOException;
		import java.lang.reflect.Type;
		import com.alibaba.fastjson.serializer.JSONSerializer;
		import io.javaweb.common.BaseEnum;
		public class EnumsSerialize implements com.alibaba.fastjson.serializer.ObjectSerializer {
			
			public EnumsSerialize() {
			}
			
			@Override
			public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)throws IOException {
				if(!(object instanceof BaseEnum)) {
					//抽象出来一个借口,必须要有 getValue() 方法,所有的枚举都应该实现这个方法
					throw new RuntimeException(object.getClass().getName() + " 未实现 io.javaweb.common.BaseEnum 接口");
				}
				serializer.out.writeInt(((BaseEnum)object).getValue());
			}
		}

		import java.lang.reflect.Type;
		import com.alibaba.fastjson.parser.DefaultJSONParser;
		import com.alibaba.fastjson.parser.JSONToken;
		import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

		import io.javaweb.common.BaseEnum;


		public class EnumDeserialize implements ObjectDeserializer {

			@SuppressWarnings("unchecked")
			@Override
			public <T> T deserialze(DefaultJSONParser parser, Type type, java.lang.Object fieldName) {
				
				Integer intValue = parser.parseObject(int.class);

				String typeName = type.getTypeName();
				
				try {
					
					Class<?> clazz = Class.forName(typeName);
					
					if(!clazz.isEnum()) {
						throw new IllegalArgumentException(clazz.getName() + " 非枚举");
					}
					
					if(!BaseEnum.class.isAssignableFrom(clazz)) {
						throw new IllegalArgumentException(clazz.getName() + " 未实现 io.javaweb.common.BaseEnum 接口");
					}
					
					BaseEnum[] constants = (BaseEnum[])clazz.getEnumConstants();
					
					for(BaseEnum constant : constants) {
						if(constant.getValue().equals(intValue)) {
							return (T) constant;
						}
					}
					
					throw new IllegalArgumentException("value " + intValue + ",在 " + clazz.getName() + " 中未定义");
					
				} catch (ClassNotFoundException | IllegalArgumentException  | SecurityException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}

			@Override
			public int getFastMatchToken() {
				return JSONToken.LITERAL_INT;
			}
		}

	



	# http 消息转换器的配置
		@Bean
		public HttpMessageConverters fastJsonHttpMessageConverter() {
			
			FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
			
			//实例化序列配置
			SerializeConfig serializeConfig = new SerializeConfig();
			//指定枚举,以及创建Serialize对象
			serializeConfig.put(BaseEntity.Status.class, new EnumsSerialize());
			
			FastJsonConfig fastJsonConfig = new FastJsonConfig();
			fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
			fastJsonConfig.setCharset(StandardCharsets.UTF_8);
			fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

			//设置到fastJsonConfig
			fastJsonConfig.setSerializeConfig(serializeConfig);

			fastJsonHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
			fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
			
			return new HttpMessageConverters(fastJsonHttpMessageConverter);
		} 
	
	# 注解式(最简单)
		* 在枚举字段上声明注解
			@JSONField(serializeUsing = EnumsSerialize.class,deserializeUsing = EnumDeserialize.class)


--------------------------------
序列化枚举为ordinal				|
--------------------------------
	# 全局设置
		JSON.DEFAULT_GENERATE_FEATURE &= ~SerializerFeature.WriteEnumUsingName.mask;
	
	# 特殊设置
		// 序列化枚举为 ordinal
		int serializerFeatures = JSON.DEFAULT_GENERATE_FEATURE & ~SerializerFeature.WriteEnumUsingName.mask;
		String text = JSON.toJSONString(object, serializerFeatures);

		// 序列化枚举为 name
		int serializerFeatures = JSON.DEFAULT_GENERATE_FEATURE | SerializerFeature.WriteEnumUsingName.mask;
		String text = JSON.toJSONString(object, serializerFeatures);

--------------------------------
Enum当做JavaBean序列化			|
--------------------------------
	# 在枚举类标识注解
		@JSONType(serializeEnumAsJavaBean = true)
	
	# 代码配置
		//创建序列化配置
		SerializeConfig serializeConfig = new SerializeConfig();
		//使用该 api 来指定枚举类.class,可变参数，仅仅对这一次的序列化有效
		serializeConfig.configEnumAsJavaBean(PreservationModel.Category.class);

		// 也可以全局设置，所有默认的序列化都有效
		// SerializeConfig.globalInstance.configEnumAsJavaBean(OrderType.class);
		

		//创建fastjson配置
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializeConfig(serializeConfig);
		
		PreservationModel preservationModel = new PreservationModel();
		preservationModel.setCategory(PreservationModel.Category.BASIS);
		preservationModel.setId(1);
		
		//在序列化对象时候,设置fastjsonConfig或者serializeConfig
		System.out.println(JSON.toJSONString(preservationModel, serializeConfig));