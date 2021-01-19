-----------------
jsonpath		 |
-----------------
	# 类似于XPATH, 用于解析访问 json 
		* 如果json体非常的复杂, 嵌套很深的话, 一层层的去访问非常的难受,
		* 就可以选择使用这种快速查找的表达式
	
	
-----------------
JSONPath		 |
-----------------
	# 构造方法
		public JSONPath(String path)
		public JSONPath(String path, SerializeConfig serializeConfig, ParserConfig parserConfig)
	
	# 静态属性/方法
		public static Object eval(Object rootObject, String path)
		public static Object extract(String json, String path, ParserConfig config, int features, Feature... optionFeatures)
		public static Object extract(String json, String path) 
			* 根据path检索值
			* extract,按需计算, 性能会更好

		public static int size(Object rootObject, String path) 
			* 计算Size
			* Map非空元素个数, 对象非空元素个数, Collection的Size, 数组的长度
			* 其他无法求值返回-1

		public static Set<?> keySet(Object rootObject, String path)	
			* 获取, Map的KeySet, 对象非空属性的名称
			* 数组, Collection等不支持类型返回null

		public static boolean contains(Object rootObject, String path)
			* 是否包含, path中是否存在对象
			
		public static boolean containsValue(Object rootObject, String path, Object value)
			* 是否包含, path中是否存在指定值
			* 如果是集合或者数组, 在集合中查找value是否存在

		public static void arrayAdd(Object rootObject, String path, Object... values)
			 * 在数组或者集合中添加元素, 添加成功返回 true,失败返回 false

		public static boolean set(Object rootObject, String path, Object value)
			* 修改制定路径的值, 如果修改成功, 返回true, 否则返回false

		public static boolean remove(Object root, String path)
			* 删除指定path的元素, 删除成功返回 true,失败返回 false

		public static JSONPath compile(String path)
			* 编译一个jsonpath为对象

		public static Object read(String json, String path)
			* 从一个json字符串中, 根据指定的path读取为Json对象
		
		public static Map<String, Object> paths(Object javaObject)
		public static Map<String, Object> paths(Object javaObject, SerializeConfig config)
			* 返回指定Java对象的属性的所有json访问path

	# 实例方法
		* 实例方法跟上述的静态方法差不多, 只是不需要 path 参数
		* 因为构造的时候已经设置, 类似于 Pattern
	

		
-----------------
支持语法		 |
-----------------
	JSONPATH					描述
	$							根对象, 例如$.name
	[num]						数组访问, 其中num是数字,可以是负数, 例如:$[0].leader.departments[-1].name
	[num0,num1,num2...]			数组多个元素访问, 其中num是数字, 可以是负数, 返回数组中的多个元素,例如:$[0,3,-2,5]
	[start:end]					数组范围访问, 其中start和end是开始小表和结束下标, 可以是负数, 返回数组中的多个元素, 例如:$[0:5]
	[start:end :step]			数组范围访问, 其中start和end是开始小表和结束下标, 可以是负数,step是步长, 返回数组中的多个元素例如:$[0:5:2]
	[?(key)]					对象属性非空过滤, 例如:$.departs[?(name)]
	[key > 123]					数值类型对象属性比较过滤,例如:$.departs[id >= 123],比较操作符支持:=,!=,>,>=,<,<=
	[key like 'aa%']			字符串类型like过滤,例如:$.departs[name like 'sz*'], 通配符只支持:% 支持:not like
	[key rlike 'regexpr']		字符串类型正则匹配过滤,:例如departs[name like 'aa(.)*'], 正则语法为jdk的正则语法, 支持:not rlike
	[key in ('v0', 'v1')]		IN过滤, 支持字符串和数值类型,例如: $.departs[name in ('wenshao','Yako')] $.departs[id not in (101,102)]
	[key between 234 and 456]	BETWEEN过滤, 支持数值类型，支持not between 例如: $.departs[id between 101 and 201] $.departs[id not between 101 and 201]
	length() 或者 size()		数组长度, 例如$.values.size() 支持类型java.util.Map和java.util.Collection和数组
	keySet()					获取Map的keySet或者对象的非空属性名称, 例如$.val.keySet() 支持类型：Map和普通对象,不支持：Collection和数组(返回 null)
	.							属性访问, 例如$.name
	['key']						属性访问, 例如$['name']
	['key0','key1']				多个属性访问, 例如$['id','name']
	..							deepScan属性访问,例如$..name
	*							对象的所有属性, 例如$.leader.*
