---------------------
Gson
---------------------
	# ���캯��
		public Gson() 
	
	# ʵ������
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
			* ��ָ���Ķ���, ת��ΪjsonElement
			* �����json�ַ���, ��ôת����Ķ���һ����: JsonPrimitive
			* ��Ҫ���ַ���, ����Ϊjson��, һ��Ҫ�� -> JsonParser

		String toString()

---------------------
GsonBuilder
---------------------
	# Gson�Ĺ���Builder
	# ���캯��
		GsonBuilder()
	
	# ʵ������
		addDeserializationExclusionStrategy(ExclusionStrategy strategy)
		addSerializationExclusionStrategy(ExclusionStrategy strategy)
			* �������л�/�����л�ʱ�ֶ��ų��Ĳ���
			* �����л�/�����л�ʱ��Ҫĳ�ֶ�, Ҳ���Կ��Բ���@Expore����
			* ���󷽷�
				 boolean shouldSkipField(FieldAttributes f);
				 boolean shouldSkipClass(Class<?> clazz);
		setExclusionStrategies(ExclusionStrategy... strategies)
			* ͬ��, ������һ������Ӷ��, ���Ҳ��������л�,�����л�

		GsonBuilder disableHtmlEscaping()
			* ��ֹhtml����(ʵ�������� Unicode ����, ��html�ַ����б���)
			* Ĭ�ϻ��html���б���

		disableInnerClassSerialization()
			* ���л�ʱ, ������������, ���ڲ���, �򲻻����л�(���Ǿ�̬���ڲ���)
				public class GsonTest {
					private Foo foo = new Foo();  // Gson.toJson(new GsonTest()), foo���Բ��ᱻ���л�
					public class Foo { ... } // �ڲ���
				}
			* Ĭ�ϻ����л��ڲ���

		enableComplexMapKeySerialization()
			* ��Map��keyΪ���Ӷ���ʱ,��Ҫ�����÷���
				Map<Object, Object> config = new HashMap<>();
				config.put(Collections.singletonMap("name", "key"), "value");
			* û���������key���л�Ϊ�ַ�����Ĭ�ϣ�
				{"{name=key}":"value"}
			* �����󣬻������map���л�Ϊһ����ά���飬��map�е�ÿһ��entry������һ������Ϊ2���飬key��[0]��value��[1]
				[[{"name":"key"},"value"]]


		excludeFieldsWithModifiers(int... modifiers)
			* Ĭ�����л���ʱ��, �������:transient, static �ֶ�
			* ����Ҫ�������ֶ�, ͨ�� Modifier ����

		excludeFieldsWithoutExposeAnnotation()
			* ���û�б�ʶ @Expose(serialize = true, deserialize = true) , �򲻻���з����л������л�

		generateNonExecutableJson()
			* ���ɲ���ִ�е�json
			* ��ǰ�����4���ַ�: )]}'

		registerTypeAdapter(Type type, Object typeAdapter)
			* Ϊָ��������, �������л�/�����л�/����Ĵ�������
			* typeAdapterֻ����������
				JsonSerializer
					public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context);
				JsonDeserializer
					public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				TypeAdapter
					public abstract void write(JsonWriter out, T value) throws IOException;
					public abstract T read(JsonReader in) throws IOException;
				InstanceCreator
					public T createInstance(Type type);
					

		registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter)
			* ͬ��, �����������ע��� baseType ����, ��������Ҳ��Ч

		registerTypeAdapterFactory(TypeAdapterFactory factory)
			* Ϊָ��������, �������л�/�����л����ԵĹ�����
			* ��һ�����󷽷�
				<T> TypeAdapter<T> create(Gson gson, TypeToken<T> type);
		
		serializeNulls()
			* ���л� null �ֶ�
			* Ĭ�ϲ������л� null �ֶ�

		serializeSpecialFloatingPointValues()
			* �Ƿ��������л���json��׼�ж���ĸ�������, ��������õĻ�, ���л��Ǳ�׼�ж��������, ���׳��쳣
				���� java �е�: Float.POSITIVE_INFINITY // ���ֵ�ǲ��ܷ��� JSON ��׼��
			
			* ����ֻ��Ӱ�����л����, ����Ӱ�췴���еĽ��
		
			
		setDateFormat(int style)
		setDateFormat(int dateStyle, int timeStyle)
		setDateFormat(String pattern)
			* ���� Date �������ڵĸ�ʽ��
			* Date ��Ĭ�������, �ᱻ���л�Ϊ�ַ���: "createdDate": "Apr 21, 2020 10:50:45 AM"
			* LocalDateTime ��Ĭ�������, �ᱻ���л�Ϊ���֡���֡��ĸ�ʽ:
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
			* �����ֶ����ƵĲ���, ö��
				FieldNamingPolicy
					IDENTITY						�ֶ�������ɶ, ����ɶ
					UPPER_CAMEL_CASE				����ĸ��д
					UPPER_CAMEL_CASE_WITH_SPACES	����ĸ��д ���ո�ָ�
					LOWER_CASE_WITH_UNDERSCORES		������ĸСд �»��߷ָ�
					LOWER_CASE_WITH_DASHES			������ĸСд �л��߷ָ�
					LOWER_CASE_WITH_DOTS			�����ַ�Сд ��ָ�

		setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy)
			* ����Gson�ֶν�������, �����ֶ� -> json�ֶ�����
			* FieldNamingStrategy �� �ӿ�, Ψһ���󷽷�
				String translateName(Field f);

		setLenient()
			* ����json������, �Ƿ�ʼ����ģʽ
			* Ĭ�����ϸ�ģʽ, ����ģʽ, ���Խ������ֲ��Ǻܱ�׼��json�ַ���

		setLongSerializationPolicy(LongSerializationPolicy serializationPolicy)
			* ö��, ���� long/Long ���͵����л�����
				DEFAULT
				STRING
			* �����ǿ��ǵ�js֮�������, ����Java��long��������, ���ܻᶪʧ����, ���Կ��Կ����� String

		setPrettyPrinting()
			* ���л���, ��ʽ��json�ַ���

		setVersion(double ignoreVersionsAfter)
			* ���ð汾��,
			* �ڶ���,�����ֶ��ϱ�ʶ�İ汾�� @Since(value), ֻ��С�ڵ������ֵ�ĲŻᱻ���л�

		create()


---------------------
JsonElement
---------------------
	# ������
	# ʵ������
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
	
	# ʵ����
		JsonArray
		JsonNull
		JsonObject
		JsonPrimitive
		