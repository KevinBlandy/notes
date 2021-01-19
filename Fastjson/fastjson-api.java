
-----------------------------------
普通								|
-----------------------------------
	com.alibaba.fastjson.JSON
		# 静态类,大多数静态方法
			String toJSONString(Object obj);
				* 把对象转换为JSON
			String toJSONString(Object obj, boolean prettyFormat); 
				* 把对象转换为JSON,并且格式化
			T parseObject(String text, Class<T> clazz); 
				* 把JSON字符串范序列化为对象
			List<T>  parseArray(String text, Class<T> clazz); 
				* 把JSON字符串反序列化为List集合


# 构造一个排序的JSONObject 对象
	new JSONObject(true);
	//源码
	public JSONObject(boolean ordered){
        this(DEFAULT_INITIAL_CAPACITY, ordered);
    }
	public JSONObject(int initialCapacity, boolean ordered){
        if (ordered) {
			//如果是需要排序的话,创建的就是 LinkedHashMap 对象
            map = new LinkedHashMap<String, Object>(initialCapacity);
        } else {
            map = new HashMap<String, Object>(initialCapacity);
        }
    }

-----------------------------------
反序列化泛型						|
-----------------------------------
	T obj = JSON.parseObject("json str", new TypeReference<T>() {});
		* 空实现一个 TypeReference 实例对象,该对象的泛型就是最终序列化的泛型结果

-----------------------------------
Spring 相关							|
-----------------------------------
FastJsonHttpMessageConverter
	* 普通的HTTP消息转换器

FastJsonHttpMessageConverter4
	* 同上

FastJsonpHttpMessageConverter4
	* 支持跨域的HTTP消息转换器
	* 方法
		setFastJsonConfig(FastJsonConfig fastJsonConfig);
			* 设置Config
		supportedMediaTypes(List<MediaType> supportedMediaTypes);
			* 设置支持的数据格式

FastJsonConfig
	* 关于转换中的一些配置
	setSerializerFeatures(SerializerFeature..serializerFeatures);
		SerializerFeature.QuoteFieldNames	
			* 输出key时是否使用双引号,默认为true	
		SerializerFeature.UseSingleQuotes	
			* 使用单引号而不是双引号,默认为false	
		SerializerFeature.WriteEnumUsingToString	
			* Enum输出name()或者original,默认为false	
			* 如果该属性为true,则枚举字段输出 toString() 的返回值
		SerializerFeature.UseISO8601DateFormat	
			* Date使用ISO8601格式输出，默认为false	
		
		SerializerFeature.WriteMapNullValue	
			* 是否输出值为null的普通字段和object字段,默认为false
		SerializerFeature.WriteNullListAsEmpty	
			* List字段如果为null,输出为[],而非null	
		SerializerFeature.WriteNullStringAsEmpty	
			* 字符类型字段如果为null,输出为"",而非null	
		SerializerFeature.WriteNullNumberAsZero	
			* 数值字段如果为null,输出为0,而非null	
		SerializerFeature.WriteNullBooleanAsFalse	
			* Boolean字段如果为null,输出为false,而非null	
		
		SerializerFeature.SkipTransientField	
			* 如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true	
		SerializerFeature.SortField	
			* 按字段名称排序后输出。默认为false	
		SerializerFeature.WriteTabAsSpecial	
			* 把\t做转义输出，默认为false	不推荐
		SerializerFeature.PrettyFormat	
			* 结果是否格式化,默认为false	
		SerializerFeature.WriteClassName	
			* 序列化时写入类型信息，默认为false。反序列化是需用到	
		SerializerFeature.DisableCircularReferenceDetect	
			* 消除对同一对象循环引用的问题，默认为false	
		SerializerFeature.WriteSlashAsSpecial	
			* 对斜杠’/’进行转义	
		SerializerFeature.BrowserCompatible	
			* 将中文都会序列化为\uXXXX格式，字节数会多一些，但是能兼容IE 6，默认为false	
		SerializerFeature.WriteDateUseDateFormat	
			* 全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);	
		SerializerFeature.DisableCheckSpecialChar	
			* 一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false	
		SerializerFeature.NotWriteRootClassName	
			* 含义	
		SerializerFeature.BeanToArray	
			* 将对象转为array输出	
		SerializerFeature.WriteNonStringKeyAsString		
			* 如果key不为String 则转换为String 比如Map的key为Integer
		SerializerFeature.NotWriteDefaultValue		
		SerializerFeature.BrowserSecure		
		SerializerFeature.IgnoreNonFieldGetter		
		SerializerFeature.WriteEnumUsingName	

FastJsonpResponseBodyAdvice
	# JSON切面增强
	# 一般配合 FastJsonpHttpMessageConverter4 使用,用来定义JSONP的请求的参数
