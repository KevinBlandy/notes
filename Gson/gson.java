------------------
Gson
------------------
	# 谷歌的json序列化框架
		* Fastjson3天2头的漏洞, 这谁顶得住
	
	# 官网
		https://github.com/google/gson
		https://github.com/google/gson/blob/master/UserGuide.md
		https://www.javadoc.io/doc/com.google.code.gson/gson
	
	# Maven
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.6</version>
		</dependency>
	
	
	# 核心库
		Gson
		GsonBuilder
		JsonElement
			|-JsonArray
			|-JsonNull
			|-JsonObject
			|-JsonPrimitive
		JsonParser
		
----------------------------
Gson - 基本数据类型
----------------------------
	# 序列化
		Gson gson = new Gson();
		gson.toJson(1);						// 1
		gson.toJson(new Long(1));			// 1
		gson.toJson("Hello World");			// Hello World
		gson.toJson(null);					// null
		gson.toJson(new int[] {1, 2, 3});	// [1,2,3]
	
	# 反序列化
		Gson gson = new Gson();
		int			retVal = gson.fromJson("1", int.class);				// 1
		Integer		retVal = gson.fromJson("1", Integer.class);			// 1
		Long		retVal = gson.fromJson("1", Long.class);			// 1
		Boolean		retVal = gson.fromJson("false", Boolean.class);		// false
		String		retVal = gson.fromJson("\"abc\"", String.class);	// abc
		String		retVal = gson.fromJson("123", String.class);		// 123
		String[]	retVal = gson.fromJson("[\"abc\"]", String[].class);// ["abc"]
		int[]		retVal = gson.fromJson("[1, 2, 3]", int[].class);	// [1, 2, 3]
	
	
----------------------------
Gson - 泛型
----------------------------
	
	# 通过 TypeToken 传递泛型
		Type collectionType = new TypeToken<T>(){}.getType();
		
		* 定义的泛型T, 就是json的反序列化/序列化的Java类型

	# Demo
		
		// 泛型
		Type listType = new TypeToken<List<String>>() {}.getType();

		// 原始对象
		List<String> target = new LinkedList<String>();
		target.add("blah");

		Gson gson = new Gson();

		// 序列化原始对象为json
		String json = gson.toJson(target, listType);

		// 返序列化字符串为对象
		List<String> target2 = gson.fromJson(json, listType);

----------------------------
JsonParser
----------------------------
	# 用于解析json字符串为 JsonElement
	# 静态方法
		static JsonElement parseString(String json)
		static JsonElement parseReader(Reader reader)
		static JsonElement parseReader(JsonReader reader)
	
		