-------------------
生成器
-------------------
	# Generator，生成器是ECMAScript 6新增的一个极为灵活的结构，拥有在一个函数块内暂停和恢复代码执行的能力。
		* 使用生成器可以自定义迭代器和实现协程。
	
	# 生成器的形式
		* 形式是一个函数，函数名称前面加一个星号（＊）表示它是一个生成器。只要是可以定义函数的地方，就可以定义生成器。

			function *foo(x, y) { ··· }  // * 可以在 function 和 函数名称之前任意位置。
			let foo = function * (){}
		
		* 注意，箭头函数不能用来定义生成器函数。
		* 生成器对象实现了 Iterable 接口，它们默认的迭代器是自引用的：
			function* foo(){
				yield 5;
			}	
			//执行生成器函数,返回一个遍历器对象
			it = foo();
			
			//该对象自己也有 Symbol.iterator 属性,执行后,返回 this
			console.log(it === it[Symbol.iterator]());		//true

	
	# yield 关键字
		* 生成器里面 return 只能执行一次,便会结束掉,而 yield 可以执行多次，yield 只能在生成器中使用，否则异常。
		* yield 表达式如果用在另一个表达式之中,必须放在圆括号里面。
			function* demo() {
				console.log('Hello' + yield); 		// SyntaxError
				console.log('Hello' + yield 123); 	// SyntaxError
				console.log('Hello' + (yield)); 	// OK
				console.log('Hello' + (yield 123)); // OK
			}
	
		* yield 表达式'用作函数参数' 或 '放在赋值表达式的右边',可以不加括号
			function* demo() {
				foo(yield 'a', yield 'b');	// OK
				let input = yield;			// OK
			}
		
		* yield 表达式后面的表达式不会立即执行，只有当调用 next 方法，内部指针指向该语句时才会执行
		* 可以使用星号增强 yield 的行为，让它能够迭代一个可迭代对象
			function * gen (){
				yield*[1, 2, 3]
				yield 4
				yield * [5, 6] 
			}
			console.log(Array.from(gen())) // [1, 2, 3, 4, 5, 6]


	# 使用 next() 函数来操作生成器的结果
		* 调用生成器函数会产生一个生成器对象。
		* 生成器对象一开始处于暂停执行（suspended）的状态，与迭代器相似，生成器对象也实现了Iterator接口，因此具有next()方法。
		* 调用这个方法会让生成器开始或恢复执行，只会在初次调用 next() 方法后生成器方法才会开始执行

			function * foo(){
				yield 5;
				yield 6;
				return 7;
			}
			
			let it = foo();
			
			console.log(it.next());	//{value: 5, done: false}
			console.log(it.next());	//{value: 6, done: false}
			console.log(it.next());	//{value: 7, done: true}
			console.log(it.next());	//{value: undefined, done: true}
		

	# next() 方法参数
		* yield 表达式本身没有返回值,或者说总是返回 undefined
		* next() 方法可以带一个参数,该参数就会被当作上一个 yield 表达式的返回值

			function* foo(num){
				for(let x = 0 ;x < num ; x++){
					let param = yield x;
					console.log(`本次迭代参数${param}`);
				}
			}
			
			let it = foo(3);
			
			console.log(it.next('我是参数1'));	//0			// 第一次迭代的参数,获取不到
			console.log(it.next('我是参数2'));	//1			本次迭代参数我是参数2
			console.log(it.next('我是参数3'));	//2			本次迭代参数我是参数3
			console.log(it.next('我是参数4'));	//undefined	本次迭代参数我是参数3
		
		* 可以通过这种方式,修改生成器的执行状态
	

	# return() 方法提前终止生成器
		* 与迭代器类似，生成器也支持“可关闭”的概念。
		* return()方法会强制生成器进入关闭状态。提供给return()方法的值，就是终止迭代器对象的值：

			function * gen(){
				yield 5
			}
			let it = gen();
			console.log(it.return(-1));  // {value: -1, done: true}
		
		* 如果函数内部有 try-catch-finally 代码块，那么 return 方法会推迟到 finally 代码块执行完再执行。
		* 调用 return() 后，就无法恢复了，不能继续迭代。这点和迭代器不同。
	
	# throw() 抛出异常到生成器中
		* 如果错误未被处理，生成器就会关闭
			function * gen(){
				try {
					yield 1;
				}catch (e){
					console.log(`gen 捕获到异常 ${e}`);
				} 
			}
			let it = gen();

			it.next();  
			console.log(it.throw('-1'));  // gen 捕获到异常 -1
			// {value: undefined, done: true}
			it.throw('-2');  // index.js:15 Uncaught -2
			
			* 第一次 throw 被生成器内部捕获了，而且生成器已经停止了。
			* 第二次 throw ，由于生成器已经停止，所以会在当前执行行的地方抛出异常。

		
		* 如果生成器对象还没有开始执行，那么调用 throw() 抛出的错误不会在函数内部被捕获，因为这相当于在函数块外部抛出了错误
		* throw 方法被捕获以后，会附带（继续）执行下一条 yield 表达式,也就是说，会附带执行一次 next() 方法

		* Generator 执行过程中抛出错误，且没有被内部捕获，就不会再执行下去了
		
			function* foo(){
				yield 5;
				throw e;        // 抛出异常
				yield 6;
			}
			let it = foo();
			console.log(it.next()); // {value: 5, done: false}
			try{
				console.log(it.next());
			}catch(e){
				//执行时虽然捕获了这个异常，但是生成器仍然停止了
			}
			console.log(it.next()); // {value: undefined, done: true}
		
	
	# 生成器中的 this
		* Generator 函数总是返回一个遍历器，ES6 规定这个遍历器是 Generator 函数的实例，也继承了 Generator 函数的prototype对象上的方法。
	
	# 异步 Generator 函数
		* 异步 Generator 函数就是 async 函数与 Generator 函数的结合。
			
			// async 返回 Promose
			async function* gen() {
			  yield 'hello';
			}

			const genObj = gen();
			genObj.next()
				.then(x => console.log(x));
				// { value: 'hello', done: false }
		
		* 异步 Generator 函数内部，能够同时使用 await 和 yield 命令。
		* 可以这样理解，await 命令用于将外部操作产生的值输入函数内部（等待响应），yield 命令用于将函数内部的值输出。



		

