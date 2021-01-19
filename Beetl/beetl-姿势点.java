-----------------------
Beetl-目录				|
-----------------------
	1,基本用法
	2,高级功能
	3,WEB集成
	5,附录

-----------------------
Beetl-基本用法			|
-----------------------
	# 模板资源加载器
		* 字符串模版资源加载器
		* 文件资源模版加载器
		* Classpath资源模版加载器
		* WebApp资源模版加载器
		* 自定义资源模版加载器
	
	# 定界符与占位符
	# 注释
	# 临时变量定义
	# 全局变量定义
	# 共享变量
	# 模板变量	
	# 引用属性
	# 属性赋值
	# 算数表达式
	# 逻辑表达式
	# 循环语句
		* for-in
		* for(exp;exp;exp)
		* while
		* else for
	# 条件语句
		* if else
		* switch-calse
		* select-case
	# try-catch
	# 虚拟属性
	# 函数调用
	# 安全输出
		* 变量是否存在
		* 安全输出表达式
	# 格式化
	# 标签函数			
	# HTML标签		
	# 绑定变量的HTML标签 
	# 直接调用JAVA方法和属性
	# 严格MVC控制
	# 指令
	# 类型声明
	# 错误处理
	# beetl小工具
	# 琐碎功能

-----------------------
Beetl-高级功能			|
-----------------------
	# 配置 GroupTemplate
	# 自定义方法
		* 实现Function
		* 使用普通JAVA类
		* 使用模版文件作为方法
	# 自定义格式化函数
	# 自定义标签			
		* 标签函数			
	# 自定义虚拟属性
	# 使用额外的资源加载器
	# 自定义资源加载器		'★'
	# 使用CompositeResourceLoader	'★'
	# 自定义错误处理器		'★'
	# 自定义安全管理器		'★'
	# 注册全局共享变量
	# 布局					
	# 性能优化
	# 分布式缓存模版		'★'
	# 定制输出				'★'
	# 定制模版引擎			'★'
	# 直接运行Beetl脚本		'★'

-----------------------
Beetl-WEB集成			|
-----------------------
	# Web提供全局变量
	# 集成技术开发指南
	# Servlet集成
	# Springmvc集成
	# Spring boot集成
	# jodd集成
	# Jfinal3.0&Jfinal2.0集成方案
	# Nutz集成
	# Struts2集成
	# 直接WEB中运行Beetl模版
	# 整合Ajx的局部渲染技术
	# 在页面输出错误提示信息


-----------------------
Beetl-附录				|
-----------------------


-----------------------
Beetl-常用内置方法		|
-----------------------
	# 常用的
		date 
			* 返回一个java.util.Date类型的变量，如 date() 返回一个当前时间(对应java的java.util.Date);
			* ${date( "2011-1-1" , "yyyy-MM-dd" )} 返回指定日期
		print 
			* 打印一个对象 print(user.name);
		println 
			* 打印一个对象以及回车换行符号，回车换号符号使用的是模板本身的，而不是本地系统的.
			* 如果仅仅打印一个换行符，则直接调用println() 即可
		nvl 
			* 函数nvl，如果对象为null，则返回第二个参数，否则，返回自己 nvl(user,"不存在")
		isEmpty 
			* 判断变量或者表达式是否为空，变量不存在，变量为null，变量是空字符串，变量是空集合，变量是空数组，此函数都将返回true
		isNotEmpty 
			* 同上，判断对象是否不为空
		has 
			* 变量名为参数，判断是否存在此全局变量，如 has(userList),类似于1.x版本的exist("userList"),但不需要输入引号了
		assert 
			* 如果表达式为false，则抛出异常
		trunc 
			* 截取数字，保留指定的小数位，如trunc(12.456,2) 输出是12.45
		decode	
			* 一个简化的if else 结构，
			* decode(a,1,"a=1",2,"a=2","不知道了")},
			* 如果a是1，这decode输出"a=1",如果a是2，则输出"a==2", 如果是其他值，则输出"不知道了"
			* 第一个值为原始值,后面偶数位的都是比较值,奇数位都是前偶数匹配后的输出值
		debug 
			* 在控制台输出debug指定的对象以及所在模板文件以及模板中的行数，
			* 如debug(1),则输出1 [在3行@/org/beetl/core/lab/hello.txt],
			* 也可以输出多个，如debug("hi",a),则输出hi,a=123,[在3行@/org/beetl/core/lab/hello.txt]
		parseInt 
			* 将数字或者字符解析为整形 如 parseInt("123");
		parseLong 
			* 将数字或者字符解析为长整形，parseInt(123.12);
		parseDouble 
			* 将数字或者字符解析为浮点类型 如parseDouble("1.23")
		range 
			* 接收三个参数，初始值，结束值，还有步增（可以不需要，则默认为1），
			* 返回一个Iterator，常用于循环中
				for(var i in range(1,5)) {
					print(i)			//将依次打印1234.
				}
		flush 
			* 强制io输出。

		json
			* 将对象转成json字符串，如 var data = json(userList) 
			* 可以跟一个序列化规则 如,var data = json(userList,"[*].id:i"),
			* 具体参考 https://git.oschina.net/xiandafu/beetl-json

		pageCtx
			* 仅仅在web开发中，设置一个变量，然后可以在页面渲染过程中，调用此api获取，
			* 如pageCtx("title","用户添加页面")，在其后任何地方，可以pageCtx("title") 获取该变量

		type.new 
			* 创建一个对象实例，如 var user = type.new("com.xx.User"); 
			* 如果配置了IMPORT_PACKAGE，则可以省略包名，type.new("User")

		type.name 
			* 返回一个实例的名字
			* var userClassName = type.name(user),返回"User"
	# 字符串相关
		strutil.startWith 
			* 字符串是否以为指定的字符串开头
			* ${ strutil.startWith("hello","he")} 输出是true
		strutil.endWith 
			* 字符串是否以指定的字符串结尾
			* ${ strutil.endWith("hello","o")} 输出是true
		strutil.length 
			* 获取字符串的长度
			* ${ strutil. length ("hello")},输出是5
		strutil.subString
			* 字符串切割,从第几位到最后以为
			*  ${ strutil.subString ("hello",1)},输出是“ello”
		strutil.subStringTo $
			* 字符串切割,从第几位到第几位
			* { strutil.subStringTo ("hello",1,2)},输出是“e”
		strutil.split 
			* 把一个字符串,以啥为间隔进行切分
			* ${ strutil.split ("hello,joeli",",")},输出是数组，有俩个元素，第一个是hello，第二个是joelli”
		strutil.contain 
			* 判断指定字符串是否包含字符
			* ${ strutil.contain ("hello","el")},输出是true
		strutil.toUpperCase 
			* 大写转换
			* ${ strutil.toUpperCase ("hello")},输出是HELLO
		strutil.toLowerCase 
			* 小写转换
			* ${ strutil.toLowerCase ("Hello")},输出是hello
		strutil.replace 
			* 替换,第一个参数是原始字符串,第二个是替换目标,第三个是替代项
			* ${ strutil.replace ("Hello","lo","loooo")},输出是helloooo
		strutil.format 
			* 格式化 MessageFormat ,懂了没?
			* ${ strutil.format ("hello,{0}, my age is {1}","joeli",15)},输出是hello,joelli, my age is 15. 

	# 数组相关方法
		...自己看官网
	
	# 正则表达式相关方法
		reg.match(str,regex) 
			* str为需要处理的字符串，regex是表达式
		reg.replace(str,regex,replace)
			* str为需要处理的字符串，regex是表达式，替换的字符串替换字符串
		reg.find(str,regex) 
			* 返回找到的符合表达式的第一个字符串，否则返回空字符串
		reg.findList(str,regex) 
			* 找到所有符合表达式的字符串，否则返回空列表
		reg.split(str,regex)
			* 对字符串进行切分，返回列表
		reg.split(str,regex,limit)
			* 同上，limit是最多返回个数