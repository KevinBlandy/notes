-------------------
es6变量声明			|
-------------------
	# ES5 只有两种声明变量的方法: var 命令和 function 命令
	# ES6 除了添加 let 和 const 命令,还有 import 命令和 class 命令,所以ES6 一共有 6 种声明变量的方法

-------------------
let					|
-------------------
	# 该关键字声明的变量,仅仅在当前代码块有效
		//代码
		{
			var y = 5;
			let x = 5;
		}
		console.log(y);
		console.log(x);
		//执行结果
		5											//变量y可以被访问
		Uncaught ReferenceError: x is not defined	//变量x未定义

		* for循环中的变量,就非常适合使用 let,当前的i只在本轮循环有效,所以每一次循环的i其实都是一个新的变量
		* 每一轮循环的变量i都是重新声明的,那它怎么知道上一轮循环的值,从而计算出本轮循环的值
		  这是因为 JavaScript 引擎内部会记住上一轮循环的值,初始化本轮的变量i时,就在上一轮循环的基础上进行计算
	
	# for循环中的作用域
		for(let x = 0 ;x < 10 ;x ++){
			let x = 'KevinBlandy';
			console.log(x);
		}
		* 执行结果是打印了10次 'KevinBlandy'
		* 说明 for 循环中的 x 与 循环体中声明的 x 在不同的作用域
	
	# 不会存在变量提升
		* 在变量声明之前,使用 var 声明的变量,它的值为 undefined
			console.log(x);		//undefined
			var x = 5;
		
		* let 声明的变量,必须要先声明在使用
			console.log(x);		
			let x = 5;

			Uncaught ReferenceError: x is not defined
	
	# 暂时性死区
		* 只要块级作用域内存在let命令,它所声明的变量就"绑定"这个区域,不再受外部的影响
			var temp = 5;
			{
				temp = 6;           //Uncaught ReferenceError: temp is not defined
				let temp;
			}

			* 存在全局变量temp,但是块级作用域内let又声明了一个局部变量temp,导致后者绑定这个块级作用域
			* 所以在let声明变量前,对temp赋值会报错
	
		* ES6 明确规定,如果区块中存在let和const命令,这个区块对这些命令声明的变量,从一开始就形成了封闭作用域,'凡是在声明之前就使用这些变量,就会报错'
		* 总之,在代码块内,使用let命令声明变量之前,该变量都是不可用的.
		* 这在语法上,称为"暂时性死区"(temporal dead zone,简称 TDZ)

		* "暂时性死区"也意味着typeof不再是一个百分之百安全的操作
			typeof x                    //Uncaught ReferenceError: x is not defined
			let x = 5;
			console.log(typeof xxx)       //undefined

			* 因为 x 被let声明,所以之前都是属于 'x' 的死区,在声明之前使用都会抛出异常
			* xxx 是一个根本没有被声明过的变量,使用 typeof ,结果返回,undefined
			* '在没有let之前,typeof运算符是百分之百安全的,永远不会报错,现在这一点不成立了'
		
		* 一些不容易被发现的'死区'
			------------------------------------------------
			function bar(x = y, y = 2) {
				return [x, y];
			}
			bar(); // 报错Uncaught ReferenceError: y is not defined

			* x = y的时候,y还没有被声明,y属于死区
			* 如果 (x = 2,y = x),就ok,因为x已经被声明,可以被赋值给后面的y
			------------------------------------------------
			var x = x;		//ok
			let x = x;		// ReferenceError: x is not defined

			* let 与 var不同,let声明会报错,也是因为死区的问题,x等于的那个x还没有被声明,就用来赋值
		
		* 总之,暂时性死区的本质就是,只要一进入当前作用域,所要使用的变量就已经存在了,但是不可获取
		* 只有等到声明变量的那一行代码出现,才可以获取和使用该变量.(先声明,后使用)
	
	# 不允许重复声明
		* let不允许在相同作用域内,重复声明同一个变量
			{
				let x = 4;
				let x = 5;      //Uncaught SyntaxError: Identifier 'x' has already been declared
			}
		* 那么在方法中,也就不允许对形参进行 let 声明
			function test(x) {
				let x = 4;
			}
			test(5);     //Uncaught SyntaxError: Identifier 'x' has already been declared
	
-------------------
块级作用域			|
-------------------
	# 为啥要有块级作用域
		* ES5 只有全局作用域和函数作用域,没有块级作用域,这带来很多不合理的场景
			var tmp = new Date();
			function f() {
				//在变量声明之前使用,该值为 undefined
				console.log(tmp);
				if (false) {
					//函数块里面声明了变量
					var tmp = 'hello world';
				}
			}
			f(); // undefined

			* if代码块的外部使用外层的tmp变量,内部使用内层的tmp变量(变量提升)
			* 但是,函数f执行后,输出结果为undefined,原因在于变量提升,导致内层的tmp变量覆盖了外层的tmp变量
			------------------------------------------------
			var s = 'hello';
			for (var i = 0; i < s.length; i++) {
				console.log(s[i]);
			}
			console.log(i); // 5
			* 变量i只用来控制循环,但是循环结束后,它并没有消失,泄露成了全局变量
	
	# ES6中的块级作用域
		* let 实际上为js新增了块级作用域
		* es6允许块级作用域的任意嵌套
		* 父级作用域不能访问子作用域的数据,子作用域可以访问父级作用域的变量
		* 内层作用域可以定义外层作用域的同名变量
		* 块级作用域的出现,实际上使得获得广泛应用的立即执行函数表达式(IIFE)不再必要了	
			// IIFE 写法
			(function () {
			  var tmp = ...;
			  ...
			}());

			// 块级作用域写法
			{
			  let tmp = ...;
			  ...
			}
	

	# 块级作用域与函数声明
		* 函数是否可以在块级作用域中声明,这是一个令人混淆的问题
		* ES5,规定,函数只能在顶层作用域中和函数作用域中声明,不能在块级作用域中声明
			// 情况一
			if (true) {
			  function f() {}
			}

			// 情况二
			try {
			  function f() {}
			} catch(e) {
			  // ...
			}

			* 上述两种声明方式,在es5中都是非法的
			* 但是,浏览器没有遵守这个规定,为了兼容以前的旧代码,还是支持在块级作用域之中声明函数,因此上面两种情况实际都能运行,不会报错
		
		* ES6 引入了块级作用域,明确允许在块级作用域之中声明函数
		* ES6 规定,块级作用域之中,函数声明语句的行为类似于let,在块级作用域之外不可引用
		
		* 浏览器特不一样的地方
			function f() { console.log('I am outside!'); }
			(function () {
				if (false) {
					// 重复声明一次函数f
					function f() { console.log('I am inside!'); }
				}
				f();
			}());
		
			* 该代码在es5环境下会输出:I am inside!
			* 因为ES5'变量提升'的问题,实际上代码是
				// ES5 环境
				function f() { console.log('I am outside!'); }
				(function () {
					//函数声明被提升到顶部
					function f() { console.log('I am inside!'); }
					if (false) {
					}
					f();
				}());

			* 如果在es6模式下运行该代码会抛出异常,因为：
			  如果改变了块级作用域内声明的函数的处理规则,显然会对老代码产生很大影响.
			  为了减轻因此产生的不兼容问题,ES6 在附录 B里面规定,浏览器的实现可以不遵守上面的规定,有自己的行为方式
			 
			* 浏览器自己的行为方式
				1,'允许在块级作用域内声明函数'
				2,'函数声明类似于var,即会提升到全局作用域或函数作用域的头部'
				3,'同时,函数声明还会提升到所在的块级作用域的头部'

				* 这三条规则只对 ES6 的浏览器实现有效,其他环境的实现不用遵守,还是将块级作用域的函数声明当作let处理

			* 上面的代码在符合 ES6 的浏览器中,会报错,因为实际运行的是下面的代码
				// 浏览器的 ES6 环境
				function f() { console.log('I am outside!'); }
				(function () {
					var f = undefined;
					if (false) {
						function f() { console.log('I am inside!'); }
					}

					f();
				}());
				// Uncaught TypeError: f is not a function

							
		* 考虑到环境导致的行为差异太大,应该避免在块级作用域内声明函数,如果确实需要,也应该写成函数表达式,而不是函数声明语句
			// 函数声明语句
			{
				let a = 'secret';
				function f() {
					return a;
				}
			}

			// 函数表达式
			{
				let a = 'secret';
				let f = function () {
					return a;
				};
			}
		
		
		* 另外,还有一个需要注意的地方.ES6 的块级作用域允许声明函数的规则,只在使用大括号的情况下成立,如果没有使用大括号,就会报错
			// 不报错
			'use strict';
			if (true) {
			  function f() {}
			}

			// 报错
			'use strict';
			if (true)
			  function f() {}

-------------------
const				|
-------------------
	# const声明一个只读的常量,一旦声明,常量的值就不能改变
	# const声明的变量不得改变值,这意味着,const一旦声明变量,就必须立即初始化
	# const的作用域与let命令相同,只在声明所在的块级作用域内有效
	# const命令声明的常量也是'不提升',同样存在暂时性死区,只能在声明的位置后面使用
	# const声明的常量,也与let一样不可重复声明	
		var message = "Hello!";
		let age = 25;

		// 以下两行都会报错
		const message = "Goodbye!";
		const age = 30;

	# const实际上保证的,并不是变量的值不得改动,而是变量指向的那个内存地址不得改动
	# 如果非要冻结一个对象的所有属性,可以使用 Object.freeze


---------------------
顶层对象的属性		 |
---------------------
	# 在浏览器环境中顶层对象即 window,在Node指的是 global 对象
	# ES5之中,顶层对象的属性与全局变量是等价的
		window.a = 1;
		console.log(a)			// 1

		a = 2;
		console.log(window.a)	// 2
	
	# ES6 为了改变这一点,为了保持兼容性, var 命令和 function 命令声明的全局变量,依旧是顶层对象的属性
	# 另一方面规定,let命令,const命令,class命令声明的全局变量,不属于顶层对象的属性
	# 也就是说,从 ES6 开始,全局变量将逐步与顶层对象的属性脱钩

---------------------
global对象			 |
---------------------
	...
