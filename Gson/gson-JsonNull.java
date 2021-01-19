---------------
JsonNull
---------------
	# 用于描述json的null属性
		* null 属性
			JsonObject jsonElement = JsonParser.parseString("{\"val\": null}").getAsJsonObject();
			System.out.println(jsonElement.get("val").isJsonNull()); // true

		* 单 null 字符串也是
			JsonElement jsonElement = JsonParser.parseString("null");
			System.out.println(jsonElement.getAsJsonNull()); // true
	
	# Null 属性
		final class JsonNull extends JsonElement

	# 提供唯一的静态实例对象
		public static final JsonNull INSTANCE = new JsonNull();
	