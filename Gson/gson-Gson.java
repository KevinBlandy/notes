---------------------
Gson
---------------------
	# 构造函数
		public Gson() 
	
	# 实例方法
		Excluder excluder()
		FieldNamingStrategy fieldNamingStrategy()

		<T> T fromJson(JsonElement json, Class<T> classOfT)
		<T> T fromJson(JsonElement json, Type typeOfT)
		<T> T fromJson(JsonReader reader, Type typeOfT)
		<T> T fromJson(Reader json, Class<T> classOfT)
		<T> T fromJson(Reader json, Type typeOfT)
		<T> T fromJson(String json, Class<T> classOfT)
		<T> T fromJson(String json, Type typeOfT)

		<T> TypeAdapter<T> getAdapter(TypeToken<T> type)
		<T> TypeAdapter<T> getAdapter(Class<T> type)
		<T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type)
		boolean htmlSafe()
		GsonBuilder newBuilder()
		JsonReader newJsonReader(Reader reader)
		JsonWriter newJsonWriter(Writer writer)
		boolean serializeNulls()

		String toJson(JsonElement jsonElement)
		void toJson(JsonElement jsonElement, JsonWriter writer)
		void toJson(JsonElement jsonElement, Appendable writer)
		String toJson(Object src)
		void toJson(Object src, Appendable writer)
		String toJson(Object src, Type typeOfSrc)
		void toJson(Object src, Type typeOfSrc, JsonWriter writer)
		void toJson(Object src, Type typeOfSrc, Appendable writer)

		JsonElement toJsonTree(Object src)
		JsonElement toJsonTree(Object src, Type typeOfSrc)
			* 把指定的对象, 转换为jsonElement
			* 如果是json字符串, 那么转换后的对象一定是: JsonPrimitive
			* 需要把字符串, 解析为json树, 一定要用 -> JsonParser

		String toString()

---------------------
GsonBuilder
---------------------
	# Gson的构造Builder
	# 构造函数
		GsonBuilder()
	
	# 实例方法
		addDeserializationExclusionStrategy(ExclusionStrategy strategy)
		addSerializationExclusionStrategy(ExclusionStrategy strategy)
			* 设置序列化/反序列化时字段排除的策略
			* 如序列化/反序列化时不要某字段, 也可以可以采用@Expore代替
			* 抽象方法
				 boolean shouldSkipField(FieldAttributes f);
				 boolean shouldSkipClass(Class<?> clazz);
		setExclusionStrategies(ExclusionStrategy... strategies)
			* 同上, 它可以一次性添加多个, 并且不区分序列化,反序列化

		GsonBuilder disableHtmlEscaping()
			* 禁止html编码(实际上是用 Unicode 编码, 对html字符进行编码)
			* 默认会对html进行编码

		disableInnerClassSerialization()
			* 序列化时, 如果对象的属性, 是内部类, 则不会序列化(不是静态的内部类)
				public class GsonTest {
					private Foo foo = new Foo();  // Gson.toJson(new GsonTest()), foo属性不会被序列化
					public class Foo { ... } // 内部类
				}
			* 默认会序列化内部类

		enableComplexMapKeySerialization()
			* 当Map的key为复杂对象时,需要开启该方法
				Map<Object, Object> config = new HashMap<>();
				config.put(Collections.singletonMap("name", "key"), "value");
			* 没开启，会把key序列化为字符串（默认）
				{"{name=key}":"value"}
			* 开启后，会把这种map序列化为一个二维数组，把map中的每一个entry都当作一个长度为2数组，key是[0]，value是[1]
				[[{"name":"key"},"value"]]


		excludeFieldsWithModifiers(int... modifiers)
			* 默认序列化的时候, 不会包含:transient, static 字段
			* 设置要包含的字段, 通过 Modifier 定义

		excludeFieldsWithoutExposeAnnotation()
			* 如果没有标识 @Expose(serialize = true, deserialize = true) , 则不会进行反序列化和序列化

		generateNonExecutableJson()
			* 生成不可执行的json
			* 在前面多了4个字符: )]}'

		registerTypeAdapter(Type type, Object typeAdapter)
			* 为指定的类型, 定制序列化/反序列化策略
			* typeAdapter需要 实现 JsonSerializer 或 JsonDeserializer 接口
				JsonSerializer
					public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context);
				JsonDeserializer
					public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)

		registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter)
			* 同上, 但是这个方法注册的 baseType 类型, 对其子类也生效

		registerTypeAdapterFactory(TypeAdapterFactory factory)
			* 为指定的类型, 定制序列化/反序列化策略的工厂类
			* 就一个抽象方法
				<T> TypeAdapter<T> create(Gson gson, TypeToken<T> type);
		
		serializeNulls()
			* 序列化 null 字段
			* 默认不会序列化 null 字段

		serializeSpecialFloatingPointValues()
			* 是否允许序列化非json标准中定义的浮点数据, 如果不设置的话, 序列化非标准中定义的数据, 会抛出异常
				例如 java 中的: Float.POSITIVE_INFINITY // 这个值是不能符合 JSON 标准的
			
			* 方法只会影响序列化结果, 不会影响反序列的结果
		
			
		setDateFormat(int style)
		setDateFormat(int dateStyle, int timeStyle)
		setDateFormat(String pattern)
			* 设置 Date 类型日期的格式化
			* Date 的默认情况下, 会被序列化为字符串: "createdDate": "Apr 21, 2020 10:50:45 AM"
			* LocalDateTime 的默认情况下, 会被序列化为这种‘奇怪’的格式:
				"createdDate": {
					"date": {
					  "year": 2020,
					  "month": 4,
					  "day": 21
					},
					"time": {
					  "hour": 10,
					  "minute": 49,
					  "second": 10,
					  "nano": 876000000
					}
				  }

		setFieldNamingPolicy(FieldNamingPolicy namingConvention)
			* 设置字段名称的策略, 枚举
				FieldNamingPolicy
					IDENTITY						字段名称是啥, 就是啥
					UPPER_CAMEL_CASE				首字母大写
					UPPER_CAMEL_CASE_WITH_SPACES	首字母大写 并空格分割
					LOWER_CASE_WITH_UNDERSCORES		所有字母小写 下划线分割
					LOWER_CASE_WITH_DASHES			所有字母小写 中划线分割
					LOWER_CASE_WITH_DOTS			所有字符小写 点分割

		setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy)
			* 设置Gson字段解析策略, 对象字段 -> json字段名称
			* FieldNamingStrategy 是 接口, 唯一抽象方法
				String translateName(Field f);

		setLenient()
			* 解析json的试试, 是否开始宽松模式
			* 默认是严格模式, 宽松模式, 可以解析部分不是很标准的json字符串

		setLongSerializationPolicy(LongSerializationPolicy serializationPolicy)
			* 枚举, 设置 long/Long 类型的序列化策略
				DEFAULT
				STRING
			* 可能是考虑到js之类的语言, 处理Java的long类型数据, 可能会丢失经度, 所以可以考虑用 String

		setPrettyPrinting()
			* 序列化后, 格式化json字符串

		setVersion(double ignoreVersionsAfter)
			* 设置版本号,
			* 在对象,对象字段上标识的版本号 @Since(value), 只有小于等于这个值的才会被序列化

		create()


---------------------
JsonElement
---------------------
	# 抽象类
	# 实例方法
		JsonElement deepCopy();
		boolean isJsonArray()
		boolean isJsonObject()
		boolean isJsonPrimitive()
		boolean isJsonNull()
		JsonObject getAsJsonObject()
		JsonArray getAsJsonArray()
		JsonPrimitive getAsJsonPrimitive()
		JsonNull getAsJsonNull()
		boolean getAsBoolean()
		Number getAsNumber()
		String getAsString()
		double getAsDouble()
		float getAsFloat()
		long getAsLong()
		int getAsInt()
		byte getAsByte()
		BigDecimal getAsBigDecimal()
		BigInteger getAsBigInteger()
		short getAsShort()
		String toString()
	
	# 实现类
		JsonArray
		JsonNull
		JsonObject
		JsonPrimitive
		