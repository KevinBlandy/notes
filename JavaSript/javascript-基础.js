----------------------------
基本概念					|
----------------------------

----------------------------
变量						|
----------------------------
	# 声明
		* 先声明后使用
			var x ;
			x = 1;

		* 同时声明N个
			var x = 1,y = 2 ,z = "哈哈";
	
	# 变量作用域
		* 全局变量
			> 在方法外部声明的变量
			> 在方法内部,但是没有声明 var 的变量(该方法必须要执行,该变量才会成为全局变量.如果未执行,直接使用变量会异常)

		* 局部变量
			> 在方法内部,使用 var 声明的变量
		
	
	# 变量提升
		* 在函数体内部先使用后声明的变量
		* 先赋值,后声明
			x = 4;
			var x;
		* 先声明,后赋值
			var x ;
			x = 4;
		* Demo
			var scope = "global";
			function test(){
				/*
					输出undefined而不是global	,在这里等于把 scope 的声明提前了:var scope;
					函数体内只要是有任何地方声明了 scope 则函数体内部任何地方访问的 scope 都是函数内部的,而不是全局的
				*/
				console.log(scope);		
				var scope = "scope";
				console.log(scope);
			}
	
	# 重新声明变量,值不会丢失
		var x = 5;
		var x;
		alert(x);		//5
	
	# 删除变量
		delete 变量名称
		* 删除成功会返回boolean
		* 不是任何变量都可以被删除
		* 仅仅是对象属性变量才可以白删除
	
	# 作为属性的变量
		var x = 1;		//该变量不可删除
		z = 1;			//该变量可以删除
		this.y = 1;		//该变量可以删除

		delete x ;		//false 变量并没有被删除
		delete z ;		//true 变量被删除
		delete z ;		//true 变量被删除

	

----------------------------
数据类型					|
----------------------------
	# 数据类型
		--------------------基本数据类型
		String
			* 字符串,在这里是属于基本数据类型
			* 字符串是可以双引号,或者单引号
				var x = '1';
				var x = "1";
			* 可以使用下标来访问值
				var x = "123";
				x[0];

		Number
			* 数字,包含了整数与浮点数
			* 浮点数精度,最高17位小数
				> 小数相加的判断,尽量不要去做
				var a = 0.1;
				var b = 0.2;
				var c = ((a + b) == 3);		//false

			* NaN
				> 非数字类型
				> var x = "哈哈哈" / 5;
			* Infinity
				> 正无穷
				> var x = 1 / 0;
			* -Infinity
				> 负无穷
				> var x = -1/0;
			* 各个进制的表现形式
				> var x = 5;		//10进制
				> var x = 07;		//8进制,0开头
				> var x = 0x8;		//16进制,0x开头
			* 科学计数法
				> var y=123e5;      // 12300000
				> var z=123e-5;     // 0.00123
			
		Boolean
			* boolean
			* 要么 true,要么 false
			* 以下值都会被算为 false
				> undefined
				> null
				> 0
				> -0		
				> NaN			
				> ""			//空字符串
		
		Undefined
			* 未定义
				var x ; 
				console.log(x);

		--------------------引用数据类型
		# 以下数据类型都是属于 Object类型
		# typeof(test);  // object 
		Null
			* Null,表示空值,也属于Object类型
			* 值为 null
				var x = null;
		Array
			* 集合对象
		
		Date
			* 日期对象
			
		RegExp
			* 正则对象
			
		Object
			* 对象
			* JSON对象
				var kevin = {"name":"kevin","age":23};
		... ...

	# 查看数据类型
		typeof(test);
			* 查看指定变量的数据类型
				boolean
				number
				function
				object

	# 类型自动转换
		

	# 创建指定数据类型的变量
		var a = new String();
		var x = new Number();
		var y = new Boolean();
		var c = new Array();
		var z = new Object();

----------------------------
运算						|
----------------------------
	# 基本的加减乘除都跟JAVA一样
	# 自增,自减,取余也一样
	# +=,-=,*=,/=,%=也一样
	# 也支,三元运算
	# 不同点
		== 
			* 表示判断,会发生类型转换
			* 123 == "123"		//true
			* "10" == 10.0		//true
			* true == 1			//true
		===
			* 表示全等于,不会发生类型转换
			* 如果类型不同,一定是 false
			* 123 === "123"		//false
	
	# 数据类型自动转换
		* 当两个类型不一样的数据,进行 == 比较的时候,会发生数据类型转换
		* "+","-","*","/".运算都可以把字符串转换为Number
		* "%",取模也是可以的,不过值会发生改变,就是取模值,要注意

		1,隐式转换为 Number
			var x = "123";
			x =+x;
				* 此时的x,已经是数值类型的1,并没有使用到其他的方法

			var x = "123";
			var b = x * 1;
				* 此时的b,也是发生了一个隐式转换,结果为123!
				* "*","/".都可以把字符串转换为Number
				* "%",取模也是可以的,不过值会发生改变,就是取模值,要注意
		
		2,隐式转换为字符串
			var a = 123;
			a = a+"";
			* JAVA中也允许这种方式,呵呵!
			* "+","-",都可以把Number转换为字符串

		3,隐式转换为 Booealn 类型
			var a = 123;
			a = !a;			//返回false
			a = !!a;		//返回true
			* 是不是特别扯?JavaScript就这个德行.
		
		4,显示转换
			Number("3");
			String(false);
			Boolean([]);
			Object(3);			//new Number(3);

	
----------------------------
语句						|
----------------------------
	# 大部分跟JAVA一样,以下列举不一样,或者是特殊的
	# 循环体
		* while /do while 循环跟JAVA一个德行
		* break,continue 也跟JAVA一个德行
		* 变量循环
			for(var x = 5 ; x < 10 ;x ++){
				console.log(x);
			}
			for(;;){	//死循环了
			}
		
		* foeach循环
			> 可以用来遍历数组,集合
			var user = ["1","2","3"];
			user.forEach(function(value, index, array){
				/*
					vallue	:当前值
					index	:下标
					array	:当前遍历的数组/集合
				*/
				console.log(value);
			});

		* 对象遍历
			var user = {"name":"kevin","age":18};
			for(var x in user) {
				//x				:表示对象的属性
				//对象[属性]	:获取指定的属性值
				console.log(x);
				console.log(user[x]);
			}

			* 循环体中x的var关键字可以省略
			* 这种循环会把对象中的所有属性都遍历出来(包括原型对象的属性)

	# 对象相关的判断	
		* instanceof
			> 跟JAVA一样,可以判断变量的类型
			> 任何对象都是 Object,所以他们 instanceof Object 值都是 true
		
		* in
			> 用于判断指定属性,是否存在于指定的对象
			> 返回 boolean,该操作符会去原型对象中检索属性
			> 属性名称以字符串表示
			> Demo
				var user = new User();
				console.log("name" in user);