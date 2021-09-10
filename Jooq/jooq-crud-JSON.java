-----------------------
JSON
-----------------------
	# ���Ŀ�
		org.jooq.JSON
			* ʵ��ӳ�����JSON�ֶΣ������Ͼ��Ƿ�װ��һ���ַ��� String 

		org.jooq.JSONB
	
-----------------------
���ò���
-----------------------
	# jsonpath ��ȡ����
		// json {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}
		
		// JSON�ֶΣ�JSONPath���ʽ
		Field<JSON> skill = jsonValue(ADMIN.CONFIG, "$.skill");
		
		// SELECT json_extract(`springcloud.io`.`admin`.`config`, '$.skill') FROM `springcloud.io`.`admin` WHERE `springcloud.io`.`admin`.`id` = 5
		String result = dslContext
					.select(skill)  
					.from(ADMIN)
					.where(ADMIN.ID.eq(UInteger.valueOf(5)))
					.fetchOneInto(String.class)
					;
		System.out.println(result);  // ["Java", "Python", "Javascript"]
	
	# ����jsonpath�ж������Ƿ����
		// json {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}
		// SELECT 1 FROM `springcloud.io`.`admin` WHERE json_contains_path(`springcloud.io`.`admin`.`config`, 'one', '$.Skill')
		String result = dslContext
					.select(one())  
					.from(ADMIN)
					.where(jsonExists(ADMIN.CONFIG, "$.Skill"))
					.fetchOneInto(String.class)
					;
		System.out.println(result);  // 1

	# ����JSON�ֶ�
		
	
-----------------------
DSL ���庯��
-----------------------
	public static JSONValueOnStep<JSON> jsonValue(Field<JSON> json, String path)
	public static JSONValueOnStep<JSON> jsonValue(Field<JSON> json, Field<String> path)
	public static JSONValueOnStep<JSONB> jsonbValue(Field<JSONB> json, String path)
	public static JSONValueOnStep<JSONB> jsonbValue(Field<JSONB> json, Field<String> path)
		* ����path��ȡJSON����

	public static JSONArrayNullStep<JSON> jsonArray(Field<?>... fields)
	public static JSONArrayNullStep<JSON> jsonArray(Collection<? extends Field<?>> fields)
	public static JSONArrayNullStep<JSONB> jsonbArray(Field<?>... fields)
	public static JSONArrayNullStep<JSONB> jsonbArray(Collection<? extends Field<?>> fields)
		* JSON�������

	public static JSONEntryValueStep key(String key)
	public static JSONEntryValueStep key(Field<String> key)

	public static <T> JSONEntry<T> jsonEntry(Field<T> value)
	public static <T> JSONEntry<T> jsonEntry(String key, Field<T> value)
	public static <T> JSONEntry<T> jsonEntry(Field<String> key, Field<T> value)
	public static <T> JSONEntry<T> jsonEntry(Field<String> key, T value)
		* JSON������ÿһ��KEY/VALUE
		
	public static JSONObjectNullStep<JSON> jsonObject(String key, Field<?> value)
	public static JSONObjectNullStep<JSON> jsonObject(Field<String> key, Field<?> value)
	public static JSONObjectNullStep<JSON> jsonObject()
	public static JSONObjectNullStep<JSON> jsonObject(Field<?>... entries)
	public static JSONObjectNullStep<JSON> jsonObject(JSONEntry<?>... entries)
	public static JSONObjectNullStep<JSON> jsonObject(Collection<? extends JSONEntry<?>> entries)
	public static JSONObjectNullStep<JSONB> jsonbObject(Field<?>... entries)
	public static JSONObjectNullStep<JSONB> jsonbObject(JSONEntry<?>... entries)
	public static JSONObjectNullStep<JSONB> jsonbObject(Collection<? extends JSONEntry<?>> entries)
		* JSON�������

	public static JSONArrayAggOrderByStep<JSON> jsonArrayAgg(Field<?> value)
	public static JSONArrayAggOrderByStep<JSONB> jsonbArrayAgg(Field<?> value)
	public static JSONObjectAggNullStep<JSON> jsonObjectAgg(Field<?> value)
	public static JSONObjectAggNullStep<JSON> jsonObjectAgg(String key, Field<?> value)
	public static JSONObjectAggNullStep<JSON> jsonObjectAgg(Field<String> key, Field<?> value)
	public static JSONObjectAggNullStep<JSON> jsonObjectAgg(JSONEntry<?> entry)
	public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(Field<?> field)
	public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(String key, Field<?> value)
	public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(Field<String> key, Field<?> value)
	public static JSONObjectAggNullStep<JSONB> jsonbObjectAgg(JSONEntry<?> entry)

	public static JSONExistsOnStep jsonExists(Field<JSON> field, String path)
	public static JSONExistsOnStep jsonExists(Field<JSON> field, Field<String> path)
	public static JSONExistsOnStep jsonbExists(Field<JSONB> field, String path)
	public static JSONExistsOnStep jsonbExists(Field<JSONB> field, Field<String> path)
		* ����path�ж��Ƿ����
			
	public static JSONTableColumnsFirstStep jsonTable(JSON json, String path)
	public static JSONTableColumnsFirstStep jsonbTable(JSONB json, String path)
	public static JSONTableColumnsFirstStep jsonTable(Field<JSON> json, Field<String> path) 
	public static JSONTableColumnsFirstStep jsonbTable(Field<JSONB> json, Field<String> path)