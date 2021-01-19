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
	* 变量名为参数，判断是否存在此'全局变量'，如 has(userList),类似于1.x版本的exist("userList"),但不需要输入引号了

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