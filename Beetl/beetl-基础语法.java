------------------------
Beet 基本语法			|
------------------------
	# 定界符与占位符号
		* beetl模版语言类似JS语言和习俗,只要把Beetl语言放入定界符号即可
		* 默认的是 <%  %>,占位符用于静态文本里嵌入，占位符用于输出
			<%
				var a = 2;
				var b = 3;
				var result = a + b;
			%>
			hello 2+3 = ${result}
		* 定界符和占位符 通常还有别的选择，如下定界符
			@ 和回车换行 (此时,模板配置DELIMITER_STATEMENT_END= 或者 DELIMITER_STATEMENT_END=null 都可以)
			#	: 和回车换行
			<!--: 和 -->
			<!--# 和 -->
			<? 和 ?>
		* 定界符号里是表达式，如果表达式跟定界符或者占位符有冲突，可以在用 “\” 符号
			@for(user in users){
				il is ${user.name}\@163.com
			@}
			${[1,2,3]} //输出一个json列表
			${ {key:1,value:2 \}  } //输出一个json map，} 需要加上\
	
	# 注释
		* Beetl语法类似js语法，所以注释上也同js一样： 单行注释采用//
		* 多行注视采用/**/
	
	# 临时变量定义
		* 在模板中定义的变量成为临时变量，这类似js中采用var 定义的变量
			var x = 3;
			var b =3,c = "abc",d=true,e=null;
			var f = [1,2,3];
			var g = {key1:a:key2:b};
			var y = x + b;
	
	# 全局变量定义
		* 全局变量是通过template.binding传入的变量,这些变量能在模板的任何一个地方，包括子模板都能访问到。
			template.binding("list",service.getUserList());
			//在模板里
			<% 
				for(user in list){
			%>
				hello,${user.name};
			<% 
				} 
			%>
	
	# 共享变量
		* 共享变量指在所有模板中都可以引用的变量，
		* 过 groupTemplate.setSharedVars(Map sharedVars)传入的变量,这些变量能在 所有模板 的任何一个地方
			//.....
			GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
			//构建共享变量,Map
			Map<String,Object> shared = new HashMap<String,Object>();
			//存入数据
			shared.put("name", "beetl");
			/*
				设置共享变量
			*/
			groupTemplate.setSharedVars(shared);
			//获取模版1
			Template template = groupTemplate.getTemplate("/org/beetl/sample/s0208/t1.txt");
			//渲染模版一
			String str = template.render();
			System.out.println(str);
			//获取目标2
			template = groupTemplate.getTemplate("/org/beetl/sample/s0208/t2.txt");
			//渲染模版2
			str = template.render();
			System.out.println(str);


			//t1.txt
			hi,${name}
			//t2.txt
			hello,${name}

	# 模板变量
		* 模板变量是一种特殊的变量，即可以将模板中任何一段的输出赋值到该变量，并允许稍后在其他地方使用
			<%
				var content = {
					var c = "1234";
					print(c);
			%>
			模板其他内容：
			<% 
				}; 
			%>
			* 第2行定义了一个模板变量content = { …} ; 此变量跟临时变量一样，可以在其他地方使用，
			* 最常见的用法是用于复杂的布局。请参考高级用法布局
	
	# 引用属性

		* 属性引用是模板中的重要一部分,beetl支持属性同javascript的支持方式一样

		* Beetl支持通过"."号来访问对象的的属性,如果javascript一样。如果User对象有个getName()方法，那么在模板中，可以通过${xxx.name}来访问

		* 如果模板变量是数组或者List类,这可以通过[] 来访问,如${userList[0]}

		* 如果模板变量是Map类,这可以通过[]来访问,如${map["name"]},如果key值是字符串
		  也可以使用${map.name}.但不建议这么使用,因为会让模板阅读者误以为是一个Pojo对象

		* Beetl也支持Generic Get方式,即如果对象有一个public Object get(String key)方法
		  可以通过”.”号或者[]来访问,譬如 ${activityRecord.name}或者${activityRecord["name"] }都将调用activityRecord的 get(String key)方法。
		  如果对象既有具体属性,又有Generic get(这种模型设计方式是不值得鼓励),则以具体属性优先级高.
		 
		* Beetl也可以通过[]来引用属性，如${user["name"]} 相当于${user.name}.这跟javascript保持一致。
		  但建议不这么做，因为容易让阅读模板的人误认为这是一个Map类型
		 
		* Beetl 还可以定位额外的对象属性,而无需更改java对象,这叫虚拟属性
		  如,对于所有集合,数组,都有共同的虚拟属性size.
		  虚拟属性是".~"+虚拟1属性名

		template.binding("list",service.getUserList());
		template.binding("pageMap",service.getPage());

		//在模板里
		总共 ${list.~size}
		<%
			for(user in list){
		%>
		hello,${user.name};
		<% 
			} 
		%>

		当前页${pageMap['page']},总共${pageMap["total"]}

	# 属性赋值
		* Beetl2.7.0 开始支持对象赋值
			var user = ....
			user.name="joelli";
			user.friends[0] = getNewUser();
			user.map["name"] = "joelli";

	# 算数表达式
		* Beetl支持类似javascript的算术表达式和条件表达式，如+ - * / % 以及（），以及自增++，自减--
			<%
				var a = 1;
				var b = "hi";
				var c = a++;
				var d = a+100.232;
				var e = (d+12)*a;
				var f = 122228833330322.1112h
			%>
		* Beetl里定义的临时变量类型默认对应的java是Int型或者double类型,对于模板常用情况说,已经够了
		* 如果需要定义长精度类型(对应java的BigDecimal,则需要在数字末尾加上h以表示这是长精度BigDecimal,其后的计算和输出以及逻辑表达式都将按照长精度类型来考虑
	
	# 逻辑表达式
		* Beetl支持类似Javascript,java的条件表达式 如>，\<，==，!=，>= , \<= 以及 !, 还有&&和 || ，还有三元表达式等，如下例子
			<%
				var a = 1;
				var b=="good";
				var c = null;
				if(a !=1 && b=="good" && c==null){
					
				}
			%>
		* 三元表达式如果只考虑true条件对应的值的话，可以做简化,如下俩行效果是一样的。
			<%
			 var  a = 1 ;
			%>
			${a==1 ? "ok":''}
			${a==1 ? "ok"}
	
	# 循环语句
		* Beetl支持丰富的循环方式，如for-in,for(exp;exp;exp)，以及while循环，以及循环控制语句break;continue; 
		* 另外，如果没有进入for循环体，还可以执行else for指定的语句。
		-----------------------------------------------
		for in 
			* 循环遍历支持遍历集合对象,对于List和数组以及 Iterator 来说,对象就是集合对象.
			* 对于Map来说，对象就是Map.entry

			<%	//遍历集合/数组/Ierator
				for(user in userList){
					print(userLP.index);
					print(user.name);
				}
			%>
			<%	//遍历map
				for(entry in map){
					var key = entry.key;
					var value = entry.value;
					print(value.name);
				}
			%>

			* 第三行代码userLP是Beetl隐含定义的变量，能在循环体内使用。其命名规范是item名称后加上LP，他提供了当前循环的信息，如
				userLP.index	当前的索引，从1开始
				userLP.size		集合的长度
				userLP.first	是否是第一个
				userLP.last		是否是最后一个
				userLP.even		索引是否是偶数
				userLP.odd		索引是否是奇数
		
		-----------------------------------------------
		for(exp;exp;exp) 
			* 对于渲染逻辑更为常见的是经典的for循环语句
			<%
				var a = [1,2,3];
				for(var i=0;i<a.~size;i++){
						print(a[i]);
				}
			%>
		
		-----------------------------------------------
		while
			* 对于渲染逻辑同样常见的有的while循环语句
			<%
				var i = 0;
				while(i<5){
					print(i);
					i++;
				}
			%>
		
		-----------------------------------------------
		elsefor
			* 不同于通常程序语言，如果没有进入循环体，则不需额外的处理，
			* 模板渲染逻辑更常见情况是如果没有进入循环体，还需要做点什么
			* 因此，对于for循环来说，还有elsefor 用来表达如果循环体没有进入，则执行elsefor 后的语句
			<%
				var list = [];
				for(item in list){
					print(item.name);		//空集合,不会执行循环体
				}elsefor{
					print("未有记录");
				}
			%>
	
	# 条件语句
		-----------------------------------------------
		if else
			* 和JS一样,支持 if else
				<%
					var a =true;
					var b = 1;
					if(a && b==1){

					}else if(a){

					}else{

					}
				%>
			
		-----------------------------------------------
		switch-case
			* 跟JS一样
			* switch变量可以支持任何类型，而不像js那样只能是整形
				<%
					var b = 1;
					switch(b){
						case 0:
								print("it's 0");
								break;
						case 1:
								print("it's 1");
								break;
						default:
								print("error");
					}
				%>
		
		-----------------------------------------------
		select-case
			* select-case 是switch case的增强版。他允许case 里有逻辑表达式，
			* 同时，也不需要每个case都break一下，默认遇到合乎条件的case执行后就退出。
				<%
					var b = 1;
					select(b){
						case 0,1:	//如果是0或者1
								print("it's small int");
						case 2,3:	//如果是2或者3
								print("it's big int");
						default:
								print("error");
					}
				%>
			* select 后也不需要一个变量，这样case 后的逻辑表达式将决定执行哪个case.
				<%
					select {
						case boolExp,orBoolExp2:		//表达式格式
								doSomething();
					}
				%>
				<%
					var b = 1;
					select{
						case b<1,b>10:
								print("it's out of range");
								break;
						case b==1:
								print("it's 1");
								break;
						default:
								print("error");
				}
				%>
	
	# try-catch
		* 通常模板渲染逻辑很少用到try-catch 但考虑到渲染逻辑复杂性，以及模板也有不可控的地方，
		* 所以提供try catch，在渲染失败的时候仍然能保证输出正常
			<%
				try{
						callOtherSystemView()
				}catch(error){
						print("暂时无数据");
				}
			%>
		* error代表了一个异常，你可以通过 error.message 来获取可能的错误信息
		* 也可以省略catch部分，这样出现异常，不做任何操作
	
	# 虚拟属性
		* 虚拟属性也是对象的属性，但是虚拟的，非模型对象的真实属性，这样的好处是当模板需要额外的用于显示的属性的时候但又不想更改模型，便可以采用这种办法 
		* 如beetl内置的虚拟属性.~size 针对了数组以及集合类型。
			${user.gender}
			${user.~genderShowName}
			~genderShowName 是虚拟属性，其内部实现根据boolean变量gender来显示性别
			如何完成虚拟属性，请参考高级用法
	
	# 函数调用
		* Beetl内置了少量实用函数，可以在Beetl任何地方调用。如下例子是调用date 函数，不传参数情况下，返回当前日期
			<%
				var date = date();
				var len = strutil.length("cbd");
				println("len="+len);
			%>
		* 注意函数名支持namespace方式，因此代码第3行调用的函数是strutil.length
		* 定义beetl的方法非常容易，有三种方法
			实现Function类的call方法，并添加到配置文件里，或者显示的通过代码注册registerFunction(name,yourFunction)
			可以直接调用registerFunctionPackage(namespace,yourJavaObject),这时候yourJavaObject里的所有public方法都将注册为Beetl方法，方法名是namespace+"."+方法名
			可以直接写模板文件并且以html作为后缀，放到root/functions目录下，这样此模板文件自动注册为一个函数，其函数名是该模板文件名。
	
	# 安全输出
		* 安全输出是任何一个模板引擎必须重视的问题，否则，将极大困扰模板开发者。
		* Beetl中，如果要输出的模板变量为null，则beetl将不做输出，这点不同于JSP，JSP输出null，也不同于Feemarker，如果没有用!,它会报错.
		* 模板中还有俩种情况会导致模板输出异常
			1,有时候模板变量并不存在（譬如子模板里）
			2,模板变量为null，但输出的是此变量的一个属性，如${user.wife.name} //根本就没妻子,哪里来的妻子的名字
		* 针对前俩种种情况，可以在变量引用后加上 "!" 以提醒beetl这是一个安全输出的变量。
		* 如${user.wife.name! },即使user不存在，或者user为null，或者user.wife为null，或者user.wife.name为null beetl都不将输出
		* 可以在!后增加一个常量（字符串，数字类型等），或者另外一个变量，方法，本地调用，作为默认输出
			${user.wife.name! "单身"}
			//如果user为null，或者user.wife为null，或者user.wife.name为null，输出”单身”
			${user.birthday!@System.constants.DefaultBir}
			//表示如果user为null，或者user. birthday为null，输出System.constants.DefaultBir
		* 还有一种情况很少发生，但也有可能，输出模板变量发生的任何异常，如变量内部抛出的一个异常这需要使用格式${!(变量)},这样，在变量引用发生任何异常情况下，都不作输出
			${!(user.name)}
			//beetl将会调用user.getName()方法，如果发生异常，beetl将会忽略此异常，继续渲染
		* 值得注意的是，在变量后加上!不仅仅可以应用于占位符输出(但主要是应用于占位符输出)，也可以用于表达式中
			<%
				var k = user.name!'N/A' + user.age!;	//如果user为null，则k值将为N/A
			%>
			<%
				${k}
			%>
		* 在有些模板里，可能整个模板都需要安全输出，也可能模板的部分需要安全输出，使用者不必为每一个表达式使用！，可以使用beetl的安全指示符号来完成安全输出 
			<%
				//开启安全输出
				DIRECTIVE SAFE_OUTPUT_OPEN;
			%>
			${user.wife.name}
			模板其他内容，均能安全输出……
			<%
				//关闭安全输出。
				DIRECTIVE SAFE_OUTPUT_CLOSE;
			%>
		* Beetl不建议每一个页面都使用DIRECTIVE SAFE_OUTPUT_OPEN，这样，如果如果真有不期望的错误，不容易及时发现
		* 安全输出意味着beetl会有额外的代码检测值是否存在或者是否为null，性能会略差点。所以建议及时关闭安全输出（这不是必须的，但页面所有地方是安全输出，可能不容易发现错误）
		* 在for-in 循环中 ，也可以为集合变量增加安全输出指示符号，这样，如果集合变量为null，也可以不进入循环体
			<%
				var list = null;
				for(item in list!){

				}elsefor{
						print("no data");
				}
			%>
		* 变量是否存在
			<%
				if(has(flag)){
						print("flag变量存在，可以访问")
				}
			%>
		* 如果需要判断变量是否存在，如果存在，还有其他判断条件，通常都这么写
			<%
				//如果flag不存在，或者flag存在，但值是0，都将执行if语句
				if(has(flag) || flag ==0){
					//code
				}
			%>
		* 但是,有更为简便的方法是直接用安全输出
			<%
				//'flag!0' 取值是这样的，如果flag不存在，则为0，如果存在，则取值flag的值，类似三元表达式 has(flag)?falg:0
				if(flag!0 == 0){
						//code
				}
			%>

		* 安全输出表达式, 安全输出表达式可以包括
			字符串常量,如 ${user.count!"无结果"}
			boolean常量 ${user.count!false}
			数字常量，仅限于正数，因为如果是负数，则类似减号，容易误用，因此，如果需要表示负数，请用括号，如${user.count!(-1)}
			class直接调用，如${user.count!@User.DEFAULT_NUM}
			方法调用，如 ${user.count!getDefault() }
			属性引用，如 ${user.count!user.maxCount }
			任何表达式，需要用括号
	
	# 格式化
		* 几乎所有的模板语言都支持格式化
			<% var date = date(); %>
			Today is ${date,dateFormat="yyyy-MM-dd"}.
			Today is ${date,dateFormat}
			salary is ${salary,numberFormat="##.##"}
		* 格式化函数只需要一个字符串作为参数放在=号后面，
		* 如果没有为格式化函数输入参数，则使用默认值，dateFormat格式化函数默认值是local
		* Beetl也允许为指定的java class设定格式化函数，
		* 譬如已经内置了对java.util.Date,java.sql.Date 设置了了格式化函数，因此上面的例子可以简化为
			${date,"yyyy-MM-dd"}
		* Beetl针对日期和数字类型提供的默认的格式化函数，在org/beetl/core/beetl-default.properties里，注册了
			##内置的格式化函数
			FT.dateFormat =  org.beetl.ext.format.DateFormat
			FT.numberFormat =  org.beetl.ext.format.NumberFormat
			##内置的默认格式化函数
			FTC.java.util.Date = org.beetl.ext.format.DateFormat
			FTC.java.sql.Date = org.beetl.ext.format.DateFormat
			FTC.java.sql.Time = org.beetl.ext.format.DateFormat
			FTC.java.sql.Timestamp = org.beetl.ext.format.DateFormat
			FTC.java.lang.Short = org.beetl.ext.format.NumberFormat
			FTC.java.lang.Long = org.beetl.ext.format.NumberFormat
			FTC.java.lang.Integer = org.beetl.ext.format.NumberFormat
			FTC.java.lang.Float = org.beetl.ext.format.NumberFormat
			FTC.java.lang.Double = org.beetl.ext.format.NumberFormat
			FTC.java.math.BigInteger = org.beetl.ext.format.NumberFormat
			FTC.java.math.BigDecimal = org.beetl.ext.format.NumberFormat
			FTC.java.util.concurrent.atomic.AtomicLong = org.beetl.ext.format.NumberFormat
			FTC.java.util.concurrent.atomic.AtomicInteger = org.beetl.ext.format.NumberFormat
	
	# 标签函数	
		* 所谓标签函数，即允许处理模板文件里的一块内容，功能等于同jsp tag。
		* Beetl内置的layout标签
			//index.html
			<%
				layout("/inc/layout.html",{title:'主题'}){
			%>
			Hello,this is main part
			<% 
				} 
			%>

			//layout.html
			title is ${title}
			body content ${layoutContent}
			footer

			* 第1行变量title来自于layout标签函数的参数
			* 第2行layoutContent 是layout标签体{}渲染后的结果
			* 关于layout标签，参考高级主题布局

		* Beetl内置了另外一个标签是include,允许 include 另外一个模板文件
			<%
				include("/inc/header.html"){}
			%>
		* 在标签中，{} 内容将依据标签的实现而执行，layout标签将执行{}中的内容，而include标签则忽略标签体内容。

		* 关于如何实现标签函数，请参考高级主题,如下是一个简单的的标签函数：

			public class CompressTag extends Tag{
					@Override
					public void render(){
							BodyContent  content = getBodyContent();
							String content = content.getBody();
							String zip = compress(conent);
							ctx.byteWriter.write(zip);
					}
			}
	
	# HTML标签	
		* Beetl 也支持HTML tag形式的标签， 区分beetl的html tag 与 标准html tag。
		* 如设定HTML_TAG_FLAG=#，则如下html tag将被beetl解析
			<#footer style=”simple”/>
				<#richeditor id=”rid” path="${ctxPath}/upload" name=”rname”  maxlength=”${maxlength}”> ${html} …其他模板内容   </#richdeitor>
			<#html:input  id=’aaaa’ />

			* 如对于标签footer,Beetl默认会寻找WebRoot/htmltag/footer.tag(可以通过配置文件修改路径和后缀) ,内容如下:
				<% 
					if(style==’simple’){ 
				%>
					请联系我 ${session.user.name}
				<% 
					}else{ 
				%>
					请联系我 ${session.user.name},phone:${session.user.phone}
				<% 
					}
				%>
	
	# 绑定变量的HTML标签
		
	# 直接调用java方法和属性
		* 可以通过符号@来表明后面表达式调用是java风格
		* 可以调用对象的方法，属性
			${@user.getMaxFriend(“lucy”)}
			${@user.maxFriend[0].getName()}
			${@com.xxxx.constants.Order.getMaxNum()}
			${@com.xxxx.User$Gender.MAN}
			<%
				var max = @com.xxxx.constants.Order.MAX_NUM;
				var c =1;
				var d = @user.getAge(c);
			%>
		* 可以调用instance的public方法和属性，
		* 也可以调用静态类的属性和方法 ,需要加一个 @ 指示此调用是直接调用class，其后的表达式是java风格的。
		* GroupTemplate可以配置为不允许直接调用Class，具体请参考配置文件.
		* 也可以通过安全管理器配置到底哪些类Beetl不允许调用，具体请参考高级用法。
		* 默认情况，java.lang.Runtime,和 java.lang.Process不允许在模板里调用。你自己的安全管理器也许可以配置为不能直接访问DAO类（避免了以前jsp可以访问任意代码带来的危害）
		* 请按照java规范写类名和方法名，属性名。这样便于beetl识别到底调用的是哪个类，哪个方法。否则会抛出错误
		* 可以省略包名，只用类名。beetl将搜索包路径找到合适的类（需要设置配置"IMPORT_PACKAGE=包名.;包名."，包名后需要跟一个".", 或者调用"Configuration.addPkg")方法具体请参考附件配置文件说明
		* 内部类（包括枚举）访问同java一样，如User类有个内部枚举类Gender，访问是User$Gender
		* 表达式是java风格，但参数仍然是beetl表达式，比如 @user.sayHello(user.name).这里user.sayHello是java调用，user.name 仍然是beetl表达式
	
	# 严格MVC控制
		* 如果在配置文件中设置了严格MVC，则以下语法将不在模板文件里允许，否则将报出STRICK_MVC 错误
			* 定义变量，为变量赋值,如var a = 12是非法的
			* 算术表达式 如${user.age+12}是非法的
			* 除了只允许布尔以外，不允许逻辑表达式和方法调用 如if(user.gender==1)是非法的
			* 方法调用，如${subString(string,1)}是非法的
			* Class方法和属性调用，如${@user.getName()}是非法的
			* 严格的MVC，非常有助于逻辑与视图的分离，特别当逻辑与视图是由俩个团队来完成的。如果你嗜好严格MVC，可以调用groupTemplate.enableStrict()
			* 通过重载 AntlrProgramBuilder ，可以按照自己的方法控制到底哪些语法是不允许在模板引擎中出现的，但这已经超出了Beetl模板的基础使用
	
	# 指令
		* 指令格式: DIRECTIVE 指令名 指令参数（可选） 
		* Beetl目前支持安全输出指令，分别是
			DIRECTIVE SAFE_OUTPUT_OPEN ; 打开安全输出功能，此指令后的所有表达式都具有安全输出功能，
			DIRECTIVE SAFE_OUTPUT_CLOSE ; 关闭安全输出功能。详情参考安全输出
			DIRECTIVE DYNAMIC varName1,varName2 …指示后面的变量是动态类型，Beetl应该考虑为Object. 也可以省略后面的变量名，则表示模板里所有变量都是Object
				<% DIRECTIVE DYNAMIC idList;
				for(value in idList) .....
				* DYNAMIC 通常用在组件模板里，因为组件模板可以接收任何类型的对象。如列表控件，可以接收任何含有id和 value属性的对象。
				* 注意 DYNAMIC 后的变量名也允许用引号，这主要是兼容Beetl1.x版本
		* Beetl1.x 指令都是大写，当前版本也允许小写，如 directive dynamic idList
	
	# 类型声明
		* Beetl 本质上还是强类型的模板引擎，即模板每个变量类型是特定的，在模板运行过程中，beetl 会根据全局变量自动推测出模板中各种变量和表达式类型。
		* 也可以通过类型申明来说明beetl全局变量的类型
			<%
			/*
			 * @type (List<User> idList,User user)
			 */
			for(value in idList) .....
		* 类型申明必须放到多行注释里
		* 格式是@type( … ),里面的申明类似java方法的参数申明。
		* 正如你看到的类型申明是在注释里，也就表明了这在Beetl模板引擎中不是必须的，或者你只需要申明一部分即可，之所以提供可选的类型说明，是因为
			* 提高一点性能
			* 最重要的是，提高了模板的可维护性。可以让模板维护者知道变量类型，也可以让未来的ide插件根据类型声明来提供属性提示，重构等高级功能
			* 需要注意的是，如果在类型声明里提供的是类名，而不是类全路径，这样必须在配置文件里申明类的搜索路径(（需要设置配置IMPORT_PACKAGE=包名.;包名.，或者调用Configuration.addPkg))，
			* 默认的搜索路径有java.util. 和 java.lang.
	
	# 错误处理
		* Beetl能较为详细的显示错误原因，包括错误行数，错误符号，错误内容附近的模板内容，以及错误原因，如果有异常，还包括异常和异常信息。 
		* 默认情况下，仅仅在控制台显示，
			<%
				var a = 1;
				var b = a/0;
			%>
			* 运行此模板后，错误提示如下
				DIV_ZERO_ERROR:0 位于3行 资源:/org/beetl/sample/s0125/error1.txt
				1|<%
				2|  var a = 1;
				3|  var b = a/0;
				4| %>
			
		* 默认的错误处理器仅仅像后台打印错误，并没有抛出异常，
		* 如果需要在render错误时候抛出异常到控制层，则可以使用org.beetl.core.ReThrowConsoleErrorHandler。不仅打印异常，还抛出BeetlException
		* 可以自定义异常处理器，比如把错误输出到 作为渲染结果一部分输出，或者输出更美观的html内容等，具体参考高级用法
		* 可以在配置文件不设置异常，这样Beetl引擎将不处理异常，用户可以在外部来处理（可以在外部调用ErrorHandler子类来显示异常）
	
	# Beetl小工具
		* BeetlKit 提供了一些便利的方法让你立刻能使用Beetl模板引擎。提供了如下方法
			public static String render(String template, Map<String, Object> paras) 渲染模板，使用paras参数，渲染结果作为字符串返回
			public static void renderTo(String template, Writer writer, Map<String, Object> paras) 渲染模板，使用paras参数，渲染结果作为字符串返回
			public static void execute(String script, Map<String, Object> paras) 执行某个脚本
			public static Map execute(String script, Map<String, Object> paras, String[] locals) 执行某个脚本，将locals指定的变量名和模板执行后相应值放入到返回的Map里
			public static Map executeAndReturnRootScopeVars(String script) 执行某个脚本，返回所有顶级scope的所有变量和值
			public static String testTemplate(String template, String initValue) 渲染模板template，其变量来源于intValue脚本运行的结果，其所有顶级Scope的变量都将作为template的变量
			public static String testTemplate(String template, String initValue) 渲染模板template，其变量来源于intValue脚本运行的结果，其所有顶级Scope的变量都将作为template的变量
			String template = "var a=1,c=2+1;";
			Map result = executeAndReturnRootScopeVars(template);
			System.out.println(result);
			//输出结果是{c=3, a=1}
		* BeetlKit　不要用于线上系统。仅仅作为体验Beetl功能而提供的，如果需要在线上使用这些功能，请参考该类源码自行扩展

	# 琐碎功能
		* 对齐:我发现别的模板语言要是做到对齐，非常困难,使用Beetl你完全不用担心，比如velocty，stringtemlate，freemarker例子都出现了不对齐的情况，影响了美观，Beetl完全无需担心输出对齐
		* Escape:可以使用\ 做escape 符号，如\$monkey\$ 将作为一个普通的文本，输出为$monkey$.再如为了在后加上美元符号（占位符恰好又是美元符号）可以用这俩种方式hello,it’s $money$\$, 或者Hello,it’s $money+"\$"$ 。如果要输出\符号本生，则需要用俩个\,这点与javascript，java 语义一致.


------------------------
Beet 高级功能			|
------------------------
	