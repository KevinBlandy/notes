--------------------
FastJson			|
--------------------
	# 阿里巴巴的神器



--------------------
FastJson			|
--------------------
	主要类(静态)
		com.alibaba.fastjson.JSON
	

	1,把对象序列化为json字符串
		String json = JSON.toJSONString(Object obj,SerializerFeature ....serializerFeature);
		String json = JSON.toJSONString(Object obj, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本

	2,把json,序列化为javaBean
		T t = parseObject(String text, Class<T> clazz); 

	3,把json,序列化为javaBean集合
		List<T>  ts = parseArray(String text, Class<T> clazz); 
	
	4,把JSON字符串转换为JSON对象
		JSONObject JSONObject.parseObject(String json)

	5,把JSON数组转换为JSON对象
		JSONArray JSONArray.parse(String jsonArray)

--------------------
FastJson-注解		|
--------------------
	@JSONField
		name
			* 指定该字段的json key名称

		format
			* 标注在Bean的字段属性上,以字符串的形式指定格式化形式
		
		serialize
			* true/false,是否序列化该字段
		
		ordinal
			* 排序
			*  字段越小,则字段排在越前面
		
		

	

