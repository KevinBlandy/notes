----------------
Jackson
----------------
	# LocalDate处理
		@Data
		class Obj {
			// 对象添加这俩注解
			@JsonSerialize(using = LocalDateTimeSerializer.class) // 指定序列化类
			@JsonDeserialize(using = LocalDateTimeDeserializer.class) // 指定反序列化类
			private LocalDateTime time;
		}

		// 序列化类
		class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
			public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
			@Override
			public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
				arg1.writeString(arg0.format(DATE_TIME_FORMATTER));
			}
		}

		// 反序列化类
		class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
			public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
			@Override
			public LocalDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException {
				return LocalDateTime.parse(arg0.getText(), DATE_TIME_FORMATTER);
			}
		}


		public class SpringTest {
			public static void main(String[] args) throws Exception {
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				Obj obj = new Obj();
				obj.setTime(LocalDateTime.now());
				
				String c = objectMapper.writeValueAsString(obj);
				System.out.println("json:" + c);
				
				Obj o = objectMapper.readValue(c, new TypeReference<Obj>() {});
				System.out.println("obj:" + o);
			}
		}
	
	# 最佳方式
		import java.io.IOException;
		import java.time.ZoneId;
		import java.time.ZonedDateTime;
		import java.time.format.DateTimeFormatter;

		import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;

		import com.fasterxml.jackson.core.JacksonException;
		import com.fasterxml.jackson.core.JsonGenerator;
		import com.fasterxml.jackson.core.JsonParser;
		import com.fasterxml.jackson.databind.DeserializationContext;
		import com.fasterxml.jackson.databind.SerializerProvider;
		import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
		import com.fasterxml.jackson.databind.ser.std.StdSerializer;
		import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
		import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
		import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
		import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


		@Configuration
		public class JacksonConfiguration {
			
			private DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			private DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			@Bean
			public Jackson2ObjectMapperBuilderCustomizer localDateTimeCustomizer() {
				
				return builder -> {
					
					builder.deserializers(new LocalDateDeserializer(dateFormatter));
					builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
					
					builder.serializers(new LocalDateSerializer(dateFormatter));
					builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
				};
			}
			
			@Bean
			public Jackson2ObjectMapperBuilderCustomizer zonedDateTimeCustomizer() {
				
				return builder -> {
					builder.serializers(new StdSerializer<>(ZonedDateTime.class) {
						private static final long serialVersionUID = 7267001379918924374L;
						@Override
						public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
							gen.writeString(value.format(dateTimeFormatter));
						}
					});
					builder.deserializers(new StdDeserializer<>(ZonedDateTime.class) {
						private static final long serialVersionUID = 154543596456722486L;
						@Override
						public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
							return ZonedDateTime.parse(p.getText(), dateTimeFormatter.withZone(ZoneId.systemDefault()));
						}
					});
				};
			}
		}

------------------
一些配置
------------------
	# 全局序列化 Long 为 String ，避免js客户端精度丢失问题
		@Bean
		public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
			return builder -> {
				builder.serializerByType(Long.class, ToStringSerializer.instance);
			};
		}


------------------------------------
自定义注解处理
------------------------------------
	1. 创建自定义注解
		import static java.lang.annotation.ElementType.FIELD;
		import static java.lang.annotation.RetentionPolicy.RUNTIME;

		import java.lang.annotation.Retention;
		import java.lang.annotation.Target;

		import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
		import com.fasterxml.jackson.databind.annotation.JsonSerialize;

		@Retention(RUNTIME)
		@Target(FIELD)
		// 很关键，必须使用 @JacksonAnnotationsInside 元注解来标注自定义注解
		@JacksonAnnotationsInside				
		// 很关键字，设置 Serialize 处理器
		@JsonSerialize(using = DynamicResouceHostSerialize.class)
		public @interface DynamicResouceHost {
			
		}
	
	2. 在需要的地方，使用该注解，则会自动调用 @JsonSerialize 指定的处理器来进行处理


------------------------------------
ContextualSerializer
------------------------------------
	# 可以根据字段动态创建 JsonSerializer

		public interface ContextualSerializer{
			public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)throws JsonMappingException;
		}

		// 获取到字段上的注解
		Serial annotation = property.getAnnotation(Serial.class);
		// 返回默认的 JsonSerializer
		prov.findValueSerializer(property.getType(), property);
