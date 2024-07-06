------------------
函数
------------------
	# 函数声明
		function functionName(arg0, arg1, ..., argN) {
		}

		* 函数名/参数名不能用 eval 或 arguments 作为名称
		* return 语句不带返回值，返回的是 undefined

		* 函数声明有变量提升
			    // 先调用，后声明。没问题
				console.log(sum(10, 10));
				function sum(num1, num2) {
				  return num1 + num2;
				}
		
		* 函数表达式没变量提升
			// 先调用，后声明不行。
			foo();      // TypeError: foo is not a function
			// 哪怕 foo 是用 var 声明的
			var foo = function(){}
		
		* 函数定义/调用的最后一个参数有允许有尾逗号（trailing comma）。

			function clownsEverywhere(
			  param1,
			  param2,
			) { /* ... */ }

			clownsEverywhere(
			  'foo',
			  'bar',
			);

	# 函数也是一个对象
		* 个函数都是Function类型的实例，而Function也有属性和方法，跟其他引用类型一样

		name

			* ECMAScript 6的所有函数对象都会暴露一个只读的 name 属性，其中包含关于函数的信息。
			* bind返回的函数，name 属性值会加上 bound 前缀。

			* 多数情况下，这个属性中保存的就是一个函数标识符，或者说是一个字符串化的变量名。
			* 即使函数没有名称，也会如实显示成空字符串
			* 如果它是使用 Function 构造函数创建的，则会标识成 "anonymous"：

	
	# 可以给参数设置默认值，指定定义在参数后面
		function log(x, y = 'World') {
		  console.log(x, y);
		}

		log('Hello') // Hello World
		log('Hello', 'China') // Hello China
		log('Hello', '') // Hello

		* 参数默认值可以是表达式，且该表达式是惰性求值的。
		* 指定了默认值以后，函数的length属性，将返回没有指定默认值的参数个数。也就是说，指定了默认值后，length属性将失真。

			(function (a) {}).length // 1
			(function (a = 5) {}).length // 0
			(function (a, b, c = 5) {}).length // 2
	
	# Rest 参数
		* rest 参数变量是一个数组（真正的数组，而不是 arguments 这种类似数组的怪胎），该变量将多余的参数放入数组中。
		* rest 参数只能是最后一个参数，否则会报错。
		
			function push(array, ...items) {}
		
		* 函数的length属性，不包括 rest 参数。
	
	# 严格模式
		* 只要函数参数使用了默认值、解构赋值、或者扩展运算符，那么函数内部就不能显式设定为严格模式，否则会报错。


	# 箭头函数
		* 箭头函数不能使 用 arguments、super 和 new.target，也不能用作构造函数。此外，箭头函数也没有 prototype 属性。
		* 不可以使用 yield 命令，因此不能用作 Generator 函数。

		* 一个参数，可以省略参数小括号，无参、多参不能省略参数小括号

		* 由于大括号被解释为代码块，所以如果箭头函数直接返回一个对象，必须在对象外面加上括号，否则会报错。
			const foo = () => ({bar: "1"});
			console.log(foo());  // {bar: '1'}
		
		* 如果只有一行语句，且不需要返回值，可以采用下面的写法，就不用写大括号了。
			let fn = () => void doesNotReturn();
		
		* this 的问题
			* 箭头函数没有的 this 上下文，内部的 this 指向是固定的。
			* this 始终指向最外层的 this（定义时的 this）。
			* 所以当然也就不能用 call()、apply()、bind() 这些方法去改变 this 的指向。
	
	# 尾调用优化的条件
		
		* 尾调用（Tail Call）是函数式编程的一个重要概念，就是指某个函数的最后一步是调用另一个函数。
		
			 1. 代码在严格模式下执行
			 2. 外部函数的返回值是对尾调用函数的调用
			 2. 尾调用函数返回后不需要执行额外的逻辑
			 3. 尾调用函数不是引用外部函数作用域中自由变量的闭包。

		 * “尾调用优化”对递归操作意义重大。

------------------
异步函数
------------------
	# ES2017 标准引入了 async 函数，使得异步操作变得更加方便。它是 Generator 函数的语法糖。

	# async 函数就是将 Generator 函数的星号（*）替换成 async，将 yield 替换成await，仅此而已。

		* async函数内部 return 语句返回的值，会成为 then 方法回调函数的参数。
		* await 命令后面是一个 Promise 对象，返回该对象的结果。如果不是 Promise 对象，就直接返回对应的值。


		async function run (){
			const r1 = await new Promise((resolve, reject) => {
				setTimeout(() => {
				   resolve('我是结果 1') 
				}, 1000);
			});

			const r2 = await new Promise((resolev, reject) => {
				setTimeout(() => {
					resolev("我是结果 2")
				}, 500);
			});

			return {r1, r2};
		}

		let ret = run();

		ret.then(r => console.log(r));  // {r1: '我是结果 1', r2: '我是结果 2'}
	

	# 异常处理机制
		* async 函数内部抛出错误，会导致返回的 Promise 对象变为 reject 状态。抛出的错误对象会被 catch 方法回调函数接收到。
		* 任何一个 await 语句后面的 Promise 对象变为 reject 状态，那么整个 async 函数都会中断执行。
			
			* 可以使用 try catch 来解决
				async function f() {
				  try {
					// 可以使用 try catch 语句来解决这个问题
					await Promise.reject('出错了');
				  } catch(e) {
				  }
				  return await Promise.resolve('hello world');
				}

				f()
				.then(v => console.log(v))
				// hello world
			
			* 也可以在 await 后面的 Promise 对象再跟一个catch方法，处理前面可能出现的错误。

				async function f() {
				  await Promise.reject('出错了')
					.catch(e => console.log(e));
				  return await Promise.resolve('hello world');
				}

				f()
				.then(v => console.log(v))
				// 出错了
				// hello world
		
	
	# 顶层 await
		* 早期的语法规定是，await命令只能出现在 async 函数内部，否则都会报错。
		* 从 ES2022 开始，允许在模块的顶层独立使用await命令。
	
			const data = await fetch('https://api.example.com');
		
		* 它的主要目的是使用 await 解决模块异步加载的问题。
