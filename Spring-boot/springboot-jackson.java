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