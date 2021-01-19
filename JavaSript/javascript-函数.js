----------------------------
基本概念					|
----------------------------
	# 函数的类型
		Function

	# 关于函数重载
		* 首先要明确一个概念,重载的概念
			* 方法名相同,但是方法名不同.
		* JavaScript中没有函数重载这个概念,也不支持
		* JavaScript的原则是,就近原则.谁近调谁
			调用语句前面定义了两个同名函数,一个是 基本定义方式,一个是匿名定义方式,则匿名定义方式优先
		* 但是我们可以通过一些操作,来模拟函数重载

		1,在函数内部有一个:arguments数组,它封装了来自调用者传递的参数
		2,我们可以获取到这个数组,然后,通过传递的参数进行判断.来模拟重载的操作
			function demo(a,b,c)
			{
				for(var x = 0;x < arguments.length ;x ++)
				{
					document.write(arguments[x]);
				}
			}

	# 解析顺序
		1,优先找匿名函数
		2,其次找function函数
			* 多个同名function,最后一个会覆盖以上的所有
	
	# 属性
		length
			* 返回函数的形参数量
		
		name
			* 返回函数的名称
	
	# 可以给函数自定义属性/访问
		function test(){}
		test.name = "test";		//设置
		test.name;				//访问

----------------------------
定义方式					|
----------------------------
	# 基本定义方式
		function test(){}
		* 这种方式定义的函数,可以在任何可见的地方调用
	
	# 匿名函数,ECMA推荐方式
		var test = function(){}
		* 该函数以变量的形式存在
		* 如果调用代码在前,而函数定义代码在后,则会调用失败
		* 该函数一般用于参数传递/事件监听
			arr.forEach(function(val){
			
			});

			window.onLoad = function(){
			
			}
	
	# 另类的定义
		var method = new Function("参数列表","方法体");
		* 用的少,理解就好.需要使用到js里面的内置对象: Function
		*	var method = new Function("参数列表","方法体");//也被称之为动态函数
				var method = new Function("x,y","return x+y");
				alert(method(1,2));			//弹窗值为3
		*	这个东西的强大之处在于这个体现形式
		*	var canshu = "x,y";
		*	var body = "return x+y";
		*	var method = new Function(canshu,body);
		* 如果调用代码在前,而函数定义代码在后,则会调用失败

	# 区别
					function 语句		函数直接量(匿名)	Funtion 构造函数
		--------------------------------------------------------------------
		兼容		完全				js1.2以上			js1.1以上
		--------------------------------------------------------------------
		形式		句子				表达式				表达式
		--------------------------------------------------------------------
		名称		有名				匿名				匿名
		--------------------------------------------------------------------
		性质		静态				静态				动态
		--------------------------------------------------------------------
		解析时机	优先解析			顺序解析			顺序解析
		--------------------------------------------------------------------
		作用域		具有函数作用域		具有函数作用域		顶级函数(顶级作用域)
		--------------------------------------------------------------------
	
	# 静态与动态
		静态
			* 当解析到函数定义代码后,就会立即创建函数对象,存在内存中,供调用
			* 占内存,效率高,适合多次调用
		
		动态
			* 每次调用的时候,才会去创建该函数
			* 不占内存,效率低,适合单次调用
	
	# 作用域
		function 语句和 函数直接量的作用域都是一样,只有 Function构造函数的作用域是全局的

----------------------------
参数与 arguments 对象		|
----------------------------
	# 函数体内都有一个 隐形 的变量:arguments
	# 该变量本质是一个数组,里面存储了函数调用传入的参数
	# JS函数的参数是非常灵活的,形参个数可以跟实参个数不一样
	# 获取函数形参的长度
		* 函数名.length
			function test(a,b,c){}
			console.log(test.length);			//3
	# 获取函数实参的长度
		* 函数内部使用 arguments的length属性,来获取
			function test(){
				console.log(arguments.length);
			}

	# arguments 与 递归
		arguments.callee;
			* 该属性,会返回当前函数本身的引用
			* 可以获取到当前函数,来执行递归操作
			* 递归
				function test(a){
					if(a === 100) return a;
					return arguments.callee(++a);
				}
				console.log(test(1));		//100
			
	# 获取当前函数的名称
		arguments.caler;

----------------------------
caller						|
----------------------------
	# 用来获取到,调用当前函数的函数
		function b(){
			a();
		}
		function a(){
			var caller = a.caller;
			console.log(caller);
		}
		b();	//b函数对象
		a();	//null

----------------------------
this 对象					|
----------------------------
	# this 对象是在运行的时候基于函数的执行环境绑定的
	# 在全局函数中,this 指向 window 对象
	# 对象函数中的 this,则指向对象本身
		* 谁调用,this 指向谁
	
----------------------------
call,apply/bind高级函数			|
----------------------------
	# 每个函数,都包含两个非继承而来的方法
		* 只要你是个函数,就有这俩方法
			call(obj,p1,p2...);
			apply(obj,[p1,p2]);
		* call  后面是1-n个参数
		* apply 后面是一个数组

	# call 和 apply 都是为了改变某个函数运行时的 context 即上下文而存在的
	# '换句话说,就是为了改变函数体内部 this 的指向,第一个参数传递啥,this就指向啥'。
	# 这俩函数作用
		* 传递参数
		* 扩充函数依赖以运行的作用域
	
	# 执行环境的切换Demo
		//全局变量
		window.color = 'red';
		var obj = {
			//对象变量
			color :'blue'
		}
		function showColor(){
			//打印 this 的color
			console.log(this.color);
		}
		showColor();			//red ,this 指向当前 window
		showColor.call(obj);	//blue,通过 call,把showColor函数的执行环境指向了 obj 对象,此时,showColor的 this 就是obj
	
	# bind
		* 跟apply和call差不多,不过它是返回新的函数
		* bind 方法返回的函数并不包含: prototype 属性
		var y = 5;
		var o = {y:15};
		function f (x){
			return x + this.y;
		}
		f(5);			//10
		
		//绑定到指定的对象上,返回新的函数
		var bf = f.bind(o);
		bf(5);			//20

		

----------------------------
执行环境的概念				|
----------------------------
	# 执行环境定义了变量或函数是否有权访问其他数据,决定了他们各自的行为
	# 每一个执行环境都有一个与之关联的变量对象,环境中定义的所有变量和函数都保存在这个对象中
		* 这个变量,无法通过JS代码来获取
	# 图解执行环境
		 -------------------
		|var x = 10;		|
		|var y = 20;		|		保存在变量
		|function a (){		|	==================> var obj;
		|	//TODO			|
		|}					|
		 -------------------
	
	# Demo
		var color1 = 'blue';
		function changeColor(){
			// changeColor 可以访问 c1,c2
			var color2 = 'red';
			function swapColor(){
				//swapColor 可以访问 c1,c2,c3
				var color3 = color2;
				color2 = color1;
				color1 = color3;
			}
			swapColor();
		}
		changeColor();
		//只能访问c1
		println(color1);	
	
----------------------------
垃圾回收机制				|
----------------------------
	# JS具备垃圾回收机制,开发不用关心内存分配与回收
	# 垃圾回收
		离开作用域的值,将会被标记-可回收,在垃圾收集期间被删除。标记清除是目前主流的垃圾收集算法
	
----------------------------
块儿级作用域				|
----------------------------
	# JS 没有块作用域的概念
	# 演示
		function test(){
			for(var x = 0; x < 10; x++){
			}
			console.log(x);		//输出10,是可以访问到x的
		}	
		test();

		if(((x = 5)  == 4)){
			console.log(x);
		}
		console.log(x);			//输出 5
	
	# 解决
		* 使用匿名执行作用域,该作用域中的变量,执行完毕后会被立即回收
		function test(){
			(function(){
				for(var x = 0;x < 10 ;x ++){
					console.log(x);
				}
			})();
			console.log(x);		// x is not defined
		}
		test();
	
	# 语法格式
		(function(){
			//执行体
		})();

----------------------------
闭包						|
----------------------------
	# 闭包
		var x = "1";
		var obj = {
			x : '0',
			test : function(){
				return function(){
					return this.x;
				}
			}
		}
		var fun = obj.test();	
		console.log(fun());		//1

		var x = "1";
		var obj = {
			x : '0',				//定义变量O,保存当前对象
			test : function(){
				var o = this;
				return function(){
					return o.x;		//返回当前对象的x
				}
			}
		}
		var fun = obj.test();
		console.log(fun());		//0
	
	# 说白了,一个函数的返回值,是一个另一个函数.另一个函数可以访问该函数的变量
	# 可以用来保护变量





















