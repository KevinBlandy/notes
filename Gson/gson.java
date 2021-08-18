------------------
Gson
------------------
	# �ȸ��json���л����
		* Fastjson3��2ͷ��©��, ��˭����ס
	
	# ����
		https://github.com/google/gson
		https://github.com/google/gson/blob/master/UserGuide.md
		https://www.javadoc.io/doc/com.google.code.gson/gson
	
	# Maven
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.6</version>
		</dependency>
	
	
	# ���Ŀ�
		Gson
		GsonBuilder
		JsonElement
			|-JsonArray
			|-JsonNull
			|-JsonObject
			|-JsonPrimitive
		JsonParser
		
----------------------------
Gson - ������������
----------------------------
	# ���л�
		Gson gson = new Gson();
		gson.toJson(1);						// 1
		gson.toJson(new Long(1));			// 1
		gson.toJson("Hello World");			// Hello World
		gson.toJson(null);					// null
		gson.toJson(new int[] {1, 2, 3});	// [1,2,3]
	
	# �����л�
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
Gson - ����
----------------------------
	
	# ͨ�� TypeToken ���ݷ���
		Type collectionType = new TypeToken<T>(){}.getType();
		
		* ����ķ���T, ����json�ķ����л�/���л���Java����

	# Demo
		
		// ����
		Type listType = new TypeToken<List<String>>() {}.getType();

		// ԭʼ����
		List<String> target = new LinkedList<String>();
		target.add("blah");

		Gson gson = new Gson();

		// ���л�ԭʼ����Ϊjson
		String json = gson.toJson(target, listType);

		// �����л��ַ���Ϊ����
		List<String> target2 = gson.fromJson(json, listType);

----------------------------
JsonParser
----------------------------
	# ���ڽ���json�ַ���Ϊ JsonElement
	# ��̬����
		static JsonElement parseString(String json)
		static JsonElement parseReader(Reader reader)
		static JsonElement parseReader(JsonReader reader)
	
----------------------------
JsonStreamParser
----------------------------
	# ��ʽ����
		public final class JsonStreamParser implements Iterator<JsonElement> 
	
	# ʵ������
		public JsonStreamParser(String json) 
		public JsonStreamParser(Reader reader)

		public JsonElement next() throws JsonParseException
		public boolean hasNext()
		public void remove()

		