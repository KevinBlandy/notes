
-----------------------
Gson问题
-----------------------
	# 如果是使用Gson作为springboot的序列化工具，那么需要配置

	@Configuration
	public class GsonConfiguration {

		private static class SpringfoxJsonToGsonAdapter implements JsonSerializer<Json> {
			@Override
			public JsonElement serialize(Json json, Type type, JsonSerializationContext context) {
				return JsonParser.parseString(json.value());
			}
		}

		@Bean
		public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {

			
			GsonBuilder builder = new GsonBuilder();

			// 配置属性
			customizers.forEach((c) -> c.customize(builder));

			// 配置JSON序列
			builder.registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter());

			return builder;
		}
	}