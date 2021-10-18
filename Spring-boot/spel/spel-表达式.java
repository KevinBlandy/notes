-----------------------
字面量表达式
-----------------------
	# 字符串
		* 单引号，双引号都行

		String retVal = expressionParser.parseExpression("'Hello'.concat(\"World\")").getValue(String.class);

	# NULL
    	Object nullObj = expressionParser.parseExpression("null").getValue(Object.class);
	
	# 数值
	# Boolean


-----------------------
算数表达式
-----------------------
	# 支持的表达式
		+
		-
		*
		/(DIV)
		%(MOD)
		^

-----------------------
关系表达式
-----------------------
	# 支持的表达式
		==(EQ)
		!=(NE)
		>(GT)
		<(GE)
		>=(LT)
		<=(LE)
		between
			* between 右边操作数必须是列表类型，且只能包含2个元素。第一个元素为开始，第二个元素为结束
			* 区间运算是包含边界值的: 即 val >=list.get(0) && val <=list.get(1)。

			expressionParser.parseExpression("1 between {1, 2}").getValue(Boolean.class);

-----------------------
逻辑表达式
-----------------------
	# 支持的表达式
		* 不支持 Java中的 && 和 ||

		and
		or
		not(!)

-----------------------
字符串表达式
-----------------------
	# + / concat 拼接
		expressionParser.parseExpression("'hello' + 'world'.concat('!')").getValue(String.class);  // helloworld!
	
	# [] 访问下标字符
		expressionParser.parseExpression("'hello'[0]").getValue(String.class);  // h!
	
	# 转换为数组
		Expression exp = expressionParser.parseExpression("'Hello World'.bytes");  // // invokes 'getBytes()' 
		byte[] bytes = (byte[]) exp.getValue();

-----------------------
三目运算
-----------------------
	# conditon ? val1 : val2

	# val1?:val2
		* 当val1为非null时则返回val1，当val1为null时则返回val2

		expressionParser.parseExpression("null ?: false").getValue(Boolean.class);  // false

-----------------------
正则
-----------------------
	# [str] matches [regex]
		expressionParser.parseExpression("'18523570888' matches  '1[3-9]\\d{9}'").getValue(Boolean.class);  // false
	

-----------------------
优先表达式
-----------------------
	# 使用 () 可以优先计算

-----------------------
类表达式
-----------------------
	# "T(Type)" 来表示类类型
		* "Type"必须是类全限定名，"java.lang"包除外，即该包下的类可以不指定包名
			expressionParser.parseExpression("T(java.lang.String)").getValue(Class.class);  // class java.lang.String
			Class<org.springframework.expression.EvaluationContext> retVal = 
				expressionParser.parseExpression("T(org.springframework.expression.EvaluationContext)").getValue(Class.class);  // interface org.springframework.expression.EvaluationContext


		* 使用类类型表达式还可以进行访问类静态方法及类静态字段
			expressionParser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);  // 2147483647
			expressionParser.parseExpression("T(Integer).parseInt('10086')").getValue(int.class);  // 10086
	
	# 类实例化: "new Class"
		* Class 必须是全限定名，但java.lang包内的类型除外，如 String, Integer (跟 Java一样)
			expressionParser.parseExpression("new java.util.Date()").getValue(Date.class);  // Mon Oct 18 15:52:26 CST 2021
		
	
	# instanceof 判断类型
		expressionParser.parseExpression("'str' instanceof T(String)").getValue(boolean.class);  // true
	
	# 变量定义及引用
		* 变量定义通过EvaluationContext接口的setVariable(variableName, value)方法定义
		* 在表达式中使用"#variableName"引用
		* 使用"#root"引用根对象
		* 使用"#this"引用当前上下文对象
	
	# 自定义函数
		* 目前只支持类静态方法注册为自定义函数
		* 用StandardEvaluationContext的registerFunction方法进行注册自定义函数，其实完全可以使用setVariable代替，两者其实本质是一样的
			StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
			evaluationContext.setVariable("toInt", Integer.class.getDeclaredMethod("parseInt", String.class));

			ExpressionParser expressionParser = new SpelExpressionParser();
			Object retVal = expressionParser.parseExpression("#toInt('99')").getValue(evaluationContext, Integer.class);  // 99
	
	# 赋值
		* 允许给自定义变量赋值，也允许给根对象赋值
		* 使用 "#variableName=value" 
	
	# 对象属性存取
		* 使用如 "." 访问属性
		* SpEL对于属性名首字母是不区分大小写的

			// 执行上下文
			StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
			evaluationContext.setVariable("user", User.builder().id(1).userName("KevinBlandy").build());
			
			// 解析器
			ExpressionParser expressionParser = new SpelExpressionParser();
			
			// 在上下文环境中执行表达式，获取结果
			Object retVal = expressionParser.parseExpression("#user.UserName").getValue(evaluationContext, String.class);  // KevinBlandy
		
		* Groovy的安全导航运算符: ?. 防止空指针
			expressionParser.parseExpression("#foo?.name").getValue(evaluationContext, String.class);  // null (foo为null)
		
		* 支持通过 = 对属性设置值
		
	# 对象方法调用
		* 跟java调用一样
			expressionParser.parseExpression("#user.say()").getValue(evaluationContext, String.class);  // KevinBlandy
	

	# Spring Bean 引用
		* 支持使用"@"符号来引用Bean
		* 需要使用BeanResolver接口实现来查找Bean， Spring提供 BeanFactoryResolver 实现
			// context
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();  
			ctx.refresh();  
			ExpressionParser parser = new SpelExpressionParser();  
			StandardEvaluationContext context = new StandardEvaluationContext();  
			context.setBeanResolver(new BeanFactoryResolver(ctx));  
			Properties result1 = parser.parseExpression("@systemProperties").getValue(context, Properties.class);  
			Assert.assertEquals(System.getProperties(), result1);  


-----------------------
集合相关表达式
-----------------------
	# LIST
		* 对于字面量表达式列表，SpEL会使用java.util.Collections.unmodifiableList方法将列表设置为不可修改。
			expressionParser.parseExpression("{1, '2', 3}").getValue(evaluationContext, List.class);  // [1, 2, 3]
		
		* 列表中只要有一个不是字面量表达式，将只返回原始List，
		* 可以修改
			List<List<Integer>> retVal = expressionParser.parseExpression("{{0 + 1, 0 + 2}, {0 + 3, 0 + 4}}").getValue(evaluationContext, List.class);  // [1, 2, 3]
			retVal.add(Arrays.asList(5, 6)); // [[1, 2], [3, 4], [5, 6]]
		
	
	# 数组
		* 一维数组并初始化  
			expressionParser.parseExpression("{1, '2', 3}").getValue(evaluationContext, int[].class);  // [1, 2, 3]
		
		* 多维数组定义，并初始化(定义必须初始化，跟Java意义)
			expressionParser.parseExpression("new int[2]{1, 2}").getValue(int[].class);
		
	# 集合/Map访问，修改
		* 集合用 [index]，集合元素访问是通过Iterator遍历来定位元素位置的
		* Map用 [key]

		* 可以直接使用 = 修改数据
	

	# 集合投影
		* 根据集合中的元素中通过选择来构造另一个集合，该集合和原集合具有相同数量的元素
		* 使用 "(list|map).![投影表达式]"来进行投影运算
		* 在表达式中使用: #this 访问当前元素

			expressionParser.parseExpression("{1, 2, 3}.![#this + 1]").getValue(evaluationContext, int[].class);  // [2, 3, 4
		
		* 如果集合元素是对象，那么可以省略 #this，直接访问属性

		* 在Map中，使用: key/value 访问key和value值(迭代的是Entry)，Map投影最终只能得到List结果
			expressionParser.parseExpression("{'name': 'kevin'}.![value]").getValue(evaluationContext, String[].class);  // [kevin]
		


	# 集合选择
		* 根据原集合通过条件表达式选择出满足条件的元素并构造为新的集合
		* 使用 "(list|map).?[选择表达式]" 来进行过滤
		* 选择表达式结果必须是boolean类型, true 会加入结果集， false 不会

		* 访问规则和投影一样
	
		* map过滤后，结果集还是map
			expressionParser.parseExpression("{'name': 'kevin', 'foo': 'bar'}.?[key == 'name']").getValue(evaluationContext, Map.class);  // {name=kevin}

