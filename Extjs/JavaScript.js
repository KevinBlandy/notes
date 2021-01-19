------------------------
JavaScript				|
------------------------
	# 没错,这是JS,必须恶补

------------------------
数据类型				|
------------------------
	Number
		var a = 10;
		var b = 1.2;
		var c = .6;		//其实就是0.6
		var d = 070;	//0开头,注意,是八进制
		var e = 0XFF;	//0x开头,注意是十六进制
		var f = 1/0;	//正无穷
		var g = window.parseInt("10sdqwdq");		//结果是10
		var h = window.parseInt("dqwdqwdq");		//结果是NaN

	String

	Boolean
	
	Object
		var date = new Date();		//日期对象
		var arr = new Array();		//数组对象
		var obj = {name:"KevinBlandy",age:"23"};
		# 所有象通用方法(类似Java中Object的方法)

		constructor
			* 返回对象所在的构造器

		hasOwnProperty
			* 判断该属性是否是自己本身的
			obj.hasOwnProperty("name");

		isPrototypeOf

		propertyIsEnumerable

		toLocalString

		toSource

		toString

		valueOf

	
	Undefind
		* 变量声明了,但是没有被赋值,其实也等同于 null

	Null
		* 表示一个空对象的引用
	
	Function
		* 函数类型,是 Js 中比较特殊,且诡异的一种类型
		
		function fun (){
			console.log("fun...");
		}

------------------------
变量					|
------------------------
	# 全局
		* 声明在方法外部
		* JS不建议使用全局变量(查找的时候效率比较低,而且在大型程序中也许不安全)

	# 局部
		* 声明在方法内部


------------------------
数组					|
------------------------
	# 数组也是JS中的一种对象,比较常用的对象
	# 可以存储不同类型的数据,长度可变
	# 常用方法
		push
		pop
		shift
		unshift
		splice
		slice
		concat
		join
		sort
		reverse

	# 新的循环遍历
		var arr = [1,2,3,4,5,6,7,8];
		arr.forEach(function(item){
			console.log(item);
		});
	# 过滤
		var arr = [1,2,3,4,5,6,7,8];
		//过滤掉小余5的数字
		var newArr = arr.filter(function(item){
			if(item > 5){
				return true;
			}
			return false;
		});

------------------------
函数					|
------------------------
	# 函数本身也是一种数据类型
	# 创建函数的三种方法
		function fun (x , y ){
			return x + y;
		};
		var fun = function(x ,y){
			return x + y;
		};

		* 以上两种的主要区别,'JS解释的时机不同'
		* 第一种,JS解释器去直接就会去找,其他的代码,都是从上往下顺序执行
		* 通俗理解:第一种写法,不管在哪里调用方法,都可以找到成功调用.第二种.如果调用写在了函数前面,那么就会undefind


		var fun = new Function('x','y','return x + y;');
		* 第三种不常用

	# arguments
		* 每个函数内部都会有一个 arguments 对象
		* arguments,是一个数组,封装了函数的形参值
		* 方法
			var current = aguments.callee();
			* 表示引用当前函数本身
	
	# this 关键字
		* this,在JS里面,总是指向调用者.谁调用,this 就指向谁
		
		
	# call,apply 的使用
		* 这俩函数主要用于底层,用于绑定函数的作用域
		* call
			var color = "red";
			var obj = {color:"yello"};

			function showColor(x,y){
				alert(this.color);
			}

			showColor();				//结果是red
			showColor.call(window,5,10);//结果是red
			showColor.call(obj,5,10);	//结果是yello
		
		* apply
			* 跟 call一样,只是说,传递的参数是数组
			showColor();					//结果是red
			showColor.call(window,[5,10]);	//结果是red
			showColor.call(obj,[5,10]);		//结果是yello
	# 块
		* 在JS里面其实没有块的概念
		(function(){
			alert("执行");
		})();
	
	# 闭包
		* 执行环境
		* 作用域链
		* 垃圾回收


------------------------
面向对象				|
------------------------
	# 对于高级语言,JAVA,肯定精通OOP编程思想
	# Json对象的使用
		var obj = {name:"Kevin",age:23};

		obj.sex = "男";		//新增属性
		obj.age = 24;		//修改属性
		delete obj.name;	//删除属性

		for(var attr in obj){
			//枚举对象属性
			console.log(arr + ":" + obj[attr]);
		}

	# 面向对象的概念
		//定义一个类
		var Persion = function(name,age){
			this.name = name;
			this.age = age;
		
			//私有属性,命名一般以 _ 开头
			var _sex = "男";
			//获取值
			this.getSex = function(){
				return _sex;
			}
			//设置值
			this.setSex = function(sex){
				_sex = sex;
			}
		}
		//实例化一个对象
		var p = new Persion("KevinBlandy",23);
		p.setSex("男");
		
		//拓展一个属性
		Persion.prototype.id = 10;
		//拓展一个方法
		Persion.prototype.method = function(){
			alert(this.age);
		}
		
		//更为快捷的属性/方法拓展方式
		//但是要注意,扩展原型对象的时候,这堆代码一定要放在原型对象前面
		Persion.prototype = {
			//原型对象的构造器总是指向当前对象的模版
			constructor:Persion,
			id:10,
			method:function(){
				alert(this.age);
			}
		}
			
	# 单体模式
		var Ext = Ext || {};
		* 如果这个EXT存在,那么直接赋值,不存在就赋值为一个空对象


		
