------------------------------------
Generator							|
------------------------------------
	1,简介
	2,next 方法的参数
	3,for...of 循环
	4,Generator.prototype.throw()
	5,Generator.prototype.return()
	6,next(),throw(),return() 的共同点
	7,yield* 表达式
	8,作为对象属性的 Generator 函数
	9,Generator 函数的this
	10,含义
	11,应用

------------------------------------
Generator-简介						|
------------------------------------
	# 跟Python的生成器,迭代器一样的
	# 正确的定义姿势(*号在function 与函数名中间任意地方都可以)
		function * foo(x, y) { ··· }
		function *foo(x, y) { ··· }
		function* foo(x, y) { ··· }
		function*foo(x, y) { ··· }

	# 使用next()函数来操作生成器的结果

		function* foo(){
			yield 5;
			yield 6;
			return 7;
		}
		
		it = foo();
		
		console.log(it.next());	//{value: 5, done: false}
		console.log(it.next());	//{value: 6, done: false}
		console.log(it.next());	//{value: 7, done: true}
		console.log(it.next());	//{value: undefined, done: true}
	
	# 遍历器对象的next方法的运行逻辑如下
		1,遇到yield表达式，就暂停执行后面的操作,并将紧跟在yield后面的那个表达式的值,作为返回的对象的value属性值
		2,下一次调用next方法时,再继续往下执行,直到遇到下一个yield表达式
		3,如果没有再遇到新的yield表达式,就一直运行到函数结束,直到return语句为止,并将return语句后面的表达式的值,作为返回的对象的value属性值
		4,如果该函数没有return语句,则返回的对象的value属性值为undefined

		* 需要注意的是,yield表达式后面的表达式,只有当调用next方法,内部指针指向该语句时才会执行,因此等于为 JavaScript 提供了手动的"惰性求值"(Lazy Evaluation)的语法功能
			function* gen() {
				yield  123 + 456;
			}
			* yield后面的表达式123 + 456,不会立即求值,只会在next方法将指针移到这一句时,才会求值
		
	
	# return 与 yield
		* 生成器里面 return 只能执行一次,便会结束掉,而yield可以执行多次

	# Generator 函数可以不用yield表达式,这时就变成了一个单纯的暂缓执行函数
		function* f() {
			console.log('执行了！')
		}

		var generator = f();		//不会执行函数
		generator.next();			//调用next()方法才会执行函数
	
	# yield表达式只能用在 Generator 函数里面,用在其他地方都会报错
	# yield表达式如果用在另一个表达式之中,必须放在圆括号里面
		function* demo() {
			console.log('Hello' + yield); 		// SyntaxError
			console.log('Hello' + yield 123); 	// SyntaxError
			console.log('Hello' + (yield)); 	// OK
			console.log('Hello' + (yield 123)); // OK
		}

		* yield表达式'用作函数参数'或'放在赋值表达式的右边',可以不加括号
			function* demo() {
				foo(yield 'a', yield 'b');	// OK
				let input = yield;			// OK
			}

	# 与 Iterator 接口的关系
		* 任意一个对象的Symbol.iterator方法,等于'该对象的遍历器生成函数',调用该函数会返回该对象的一个遍历器对象
		  由于 'Generator 函数就是遍历器生成函数',因此可以把 Generator 赋值给对象的Symbol.iterator属性,从而使得该对象具有 Iterator 接口

			let obj = {
				name:'KevinBlandy',
				[Symbol.iterator]:function* (){
					for(let key of Reflect.ownKeys(this)){
						yield {[key]:Reflect.get(this,key)};
					}
				}
			}		 
			
			for(let item of obj){
				console.log(item);
			}
		
		* Generator 函数执行后,返回一个遍历器对象,该对象本身也具有Symbol.iterator属性,执行后返回自身
			function* foo(){
				yield 5;
			}	
			//执行生成器函数,返回一个遍历器对象
			it = foo();
			
			//该对象自己也有Symbol.iterator属性,执行后,返回 this
			console.log(it === it[Symbol.iterator]());		//true

------------------------------------
next 方法参数						|
------------------------------------
	# yield表达式本身没有返回值,或者说总是返回undefined
	# next方法可以带一个参数,该参数就会被当作上一个yield表达式的返回值
	# 跟 Python 生成器的 send() 方法一样
		function* foo(num){
			for(let x = 0 ;x < num ; x++){
				let param = yield x;
				console.log(`本次迭代参数${param}`);
			}
		}
		
		it = foo(3);
		
		console.log(it.next('我是参数1'));	//0			第一次迭代的参数,获取不到
		console.log(it.next('我是参数2'));	//1			本次迭代参数我是参数2
		console.log(it.next('我是参数3'));	//2			本次迭代参数我是参数3
		console.log(it.next('我是参数4'));	//undefined	本次迭代参数我是参数3
	
	# 可以通过这种方式,修改生成器的执行状态

------------------------------------
next 方法参数						|
------------------------------------
	# for...of循环可以自动遍历 Generator 函数时生成的Iterator对象,且此时不再需要调用next方法
		function* foo (num){
			while(num > 0){
				yield num;
				num -= 1;
			}
		}
		for(let item of foo(5)){
			console.log(item);
		}
		//5,4,3,2,1
	
	# 利用 Generator 函数和for...of循环,实现斐波那契数列
		function* fibonacci() {
			let [prev, curr] = [0, 1];
			for (;;) {
				[prev, curr] = [curr, prev + curr];
				yield curr;
			}
		}
		
		for (let n of fibonacci()) {
			if (n > 1000) {
				break;
			}
			console.log(n);
		}
	
	# 利用for...of循环,可以写出遍历任意对象(object)的方法,原生的 JavaScript 对象没有遍历接口,无法使用for...of循环
	  通过 Generator 函数为它加上这个接口,就可以用了
		//让Object的子对象都支持迭代
		Object.prototype[Symbol.iterator] = function* (){
			let keys = Reflect.ownKeys(this);
			for(let key of keys){
				yield [key,Reflect.get(this,key)];
			}
		}
		
		function Obj(){
			this.name = "KevinBlandy";
			this.age = 22;
		}
		
		for(let [key,value] of new Obj()){
			console.log(`${key} = ${value}`);
		}
		//name = KevinBlandy
		//age = 22
	
	# 除了for...of循环以外,扩展运算符(...),解构赋值和Array.from方法内部调用的,都是遍历器接口
		* 这意味着,它们都可以将 Generator 函数返回的 Iterator 对象,作为参数
		function* numbers () {
			yield 1
			yield 2
			return 3
			yield 4
		}
		
		// 扩展运算符
		[...numbers()] // [1, 2]
		
		// Array.from 方法
		Array.from(numbers()) // [1, 2]
		
		// 解构赋值
		let [x, y] = numbers();
		x // 1
		y // 2
		
		// for...of 循环
		for (let n of numbers()) {
			console.log(n)
		}
		// 1
		// 2
	

------------------------------------
Generator.prototype.throw()			|
------------------------------------
	# Generator 函数返回的遍历器对象,都有一个throw方法,可以在函数体外抛出错误,然后在 Generator 函数体内捕获
		var g = function* () {
			try {
				yield;
			} catch (e) {
				console.log('内部捕获', e);
			}
		};
		
		var i = g();
		i.next();
		
		try {
			i.throw('a');
			i.throw('b');
		} catch (e) {
			console.log('外部捕获', e);
		}
		// 内部捕获 a
		// 外部捕获 b

		* i连续抛出两个错误,第一个错误被 Generator 函数体内的catch语句捕获
		* i第二次抛出错误,由于 Generator 函数内部的catch语句已经执行过了,不会再捕捉到这个错误了,所以这个错误就被抛出了 Generator 函数体,被函数体外的catch语句捕获
	
	# throw方法可以接受一个参数,该参数会被catch语句接收,建议抛出Error对象的实例
		var g = function* () {
			try {
				yield;
			} catch (e) {
				console.log(e);
			}
		};
		
		var i = g();
		i.next();
		i.throw(new Error('出错了！'));
		// Error: 出错了！
	
	# 如果 Generator 函数内部没有部署try...catch代码块,那么throw方法抛出的错误,将被外部try...catch代码块捕获
		var g = function* () {
			yield;
		};
		
		var i = g();
		i.next();
		i.throw('异常了');		//Uncaught 异常了
	
	# throw方法被捕获以后,会附带执行下一条yield表达式,也就是说,会附带执行一次next方法
		var gen = function* gen(){
			try {
				yield console.log('a');
			} catch (e) {
				// ...
			}
			yield console.log('b');
			yield console.log('c');
		}
		
		var g = gen();
		g.next() 	// a
		g.throw() 	// b		执行了throw,被迭代器里面的catch了,还是会附带执行下一次 yield
		g.next() 	// c
		
		* 只要 Generator 函数内部部署了try...catch代码块,那么遍历器的throw方法抛出的错误,不影响下一次遍历
		* 'throw命令与throw方法是无关的,两者互不影响'
	
	# Generator 函数体外抛出的错误,可以在函数体内捕获,反过来,Generator 函数体内抛出的错误,也可以被函数体外的catch捕获
		function* foo() {
			var x = yield 3;
			var y = x.toUpperCase();
			yield y;
		}
		var it = foo();
		it.next(); // { value:3, done:false }
		try {
			it.next(42);
		} catch (err) {
			console.log(err);		//TypeError: x.toUpperCase is not a function
		}
	
	# 一旦 Generator 执行过程中抛出错误,且没有被内部捕获,就不会再执行下去了
		* 如果此后还调用next方法,将返回一个value属性等于undefined,done属性等于true的对象,即 JavaScript 引擎认为这个 Generator 已经运行结束了
			function* foo(){
				yield 5;
				throw e;
				yield 6;
			}
			it = foo();
			console.log(it.next());
			try{
				console.log(it.next());
			}catch(e){
				//skip
			}
			console.log(it.next());
			//{value: 5, done: false}
			//{value: undefined, done: true}
	
------------------------------------
Generator.prototype.return()		|
------------------------------------
	# Generator 函数返回的遍历器对象,还有一个return方法,可以返回给定的值,并且终结遍历 Generator 函数
		function* gen() {
			yield 1;
			yield 2;
			yield 3;
		}

		var g = gen();

		g.next()        // { value: 1, done: false }
		g.return('foo') // { value: "foo", done: true }
		g.next()        // { value: undefined, done: true }
	
		* 如果return方法调用时,不提供参数,则返回值的value属性为undefined
	
	# 如果 Generator 函数内部有try...finally代码块,那么return方法会推迟到finally代码块执行完再执行
		function* gen() {
			try{
				yield 1;
				yield 2;
				yield 3;
			}finally{
				console.log('最终执行');
			}
		}

		var g = gen();

		console.log(g.next())      	//{value: 1, done: false}
		//执行return,会先去执行 finally的代码
		console.log(g.return()) 	//最终执行	 { value: undefined, done: true }		
		console.log(g.next())       // {value: undefined, done: true}

------------------------------------
next(),throw(),return() 的共同点	|
------------------------------------
	# next(),throw(),return()这三个方法本质上是同一件事,可以放在一起理解
	# 它们的作用都是让 Generator 函数恢复执行,并且使用不同的语句替换yield表达式

------------------------------------
yield* 表达式						|
------------------------------------
	# 从语法角度看,如果yield表达式后面跟的是一个遍历器对象,需要在yield表达式后面加上星号,表明它返回的是一个遍历器对象,这被称为yield*表达式
	# 如果在 Generator 函数内部,调用另一个 Generator 函数,默认情况下是没有效果的
		function* foo() {
			yield 'a';
			yield 'b';
		}

		function* bar() {
			yield 'x';
			foo();
			yield 'y';
		}

		for (let v of bar()){
			console.log(v);
		}
		// "x"
		// "y"
		* foo和bar都是 Generator 函数,在bar里面调用foo,是不会有效果的
		* 这个就需要用到yield*表达式,用来在一个 Generator 函数里面执行另一个 Generator 函数
	
	# 在一个 Generator 函数里面执行另一个 Generator 函数
		function* foo() {
			yield 'a';
			yield 'b';
		}

		function* bar() {
			yield 'x';
			yield* foo();
			yield 'y';
		}

		// 等同于
		function* bar() {
			yield 'x';
			yield 'a';
			yield 'b';
			yield 'y';
		}

		// 等同于
		function* bar() {
			yield 'x';
			for (let v of foo()) {
				yield v;
			}
			yield 'y';
		}

		for (let v of bar()){
			console.log(v);
		}
		// "x"
		// "a"
		// "b"
		// "y"
	
	# 如果yield*后面跟着一个数组,由于数组原生支持遍历器,因此就会遍历数组成员
		let arr = [1,2,3];
		let arr = [1,2,3];
		function* foo(){
			yield '数组元素之前';
			yield *arr;
			yield '数组元素之后';
		}

		for(let item of foo()){
			console.log(item);
		}		 数组元素之前
		//1
		//2
		//3
		//数组元素之后
	
	# yield*后面的 Generator 函数(没有return语句时)等同于在 Generator 函数内部,部署一个for...of循环
		function* concat(iter1, iter2) {
			yield* iter1;
			yield* iter2;
		}
		// 等同于

		function* concat(iter1, iter2) {
			for (var value of iter1) {
				yield value;
			}
			for (var value of iter2) {
				yield value;
			}
		}
		
		* yield*后面的 Generator 函数(没有return语句时,不过是for...of的一种简写形式,完全可以用后者替代前者
		* 反之,在有return语句时,则需要用var value = yield* iterator的形式获取return语句的值
	
	# 际上,任何数据结构只要有 Iterator 接口,就可以被yield*遍历
	# 如果被代理的 Generator 函数有return语句,那么就可以向代理它的 Generator 函数返回数据
	   function* foo() {
			yield 2;
			yield 3;
			return "foo";
		}

		function* bar() {
			yield 1;
			var v = yield* foo();
			console.log("v: " + v);
			yield 4;
		}

		var it = bar();

		it.next()
		// {value: 1, done: false}
		it.next()
		// {value: 2, done: false}
		it.next()
		// {value: 3, done: false}
		it.next();
		// "v: foo"			//获取到了被代理的生成器返回值
		// {value: 4, done: false}
		it.next()
		// {value: undefined, done: true}
		--------------------------------
		function* genFuncWithReturn() {
			yield 'a';
			yield 'b';
			return 'The result';
		}
		function* logReturned(genObj) {
			let result = yield* genObj;
			console.log(result);
		}
		[...logReturned(genFuncWithReturn())]
		// The result
		// 值为 [ 'a', 'b' ]
	
	# yield*命令可以很方便地取出嵌套数组的所有成员
		function* iteratorTree(tree) {
			if (Array.isArray(tree)) {
				for(let i=0; i < tree.length; i++) {
					//遍历每个元素,如果是数组元素,则递归的yield
					yield* iteratorTree(tree[i]);
				}
			} else {
				//当前元素非数组元素,yield返回
				yield tree;
			}
		}

		const tree = [ 'a', ['b', 'c'], ['d', 'e'] ];

		for(let x of iteratorTree(tree)) {
			console.log(x);
		}
		// a
		// b
		// c
		// d
		// e
	
	# 稍微复杂的例子,使用yield*语句遍历完全二叉树
		// 下面是二叉树的构造函数，
		// 三个参数分别是左树、当前节点和右树
		function Tree(left, label, right) {
			this.left = left;
			this.label = label;
			this.right = right;
		}

		// 下面是中序（inorder）遍历函数。
		// 由于返回的是一个遍历器，所以要用generator函数。
		// 函数体内采用递归算法，所以左树和右树要用yield*遍历
		function* inorder(t) {
			if (t) {
				yield* inorder(t.left);
				yield t.label;
				yield* inorder(t.right);
			}
		}

		// 下面生成二叉树
		function make(array) {
			// 判断是否为叶节点
			if (array.length == 1) {
				return new Tree(null, array[0], null);
			}
			return new Tree(make(array[0]), array[1], make(array[2]));
		}

		let tree = make([[['a'], 'b', ['c']], 'd', [['e'], 'f', ['g']]]);

		// 遍历二叉树
		var result = [];
		
		for (let node of inorder(tree)) {
			result.push(node);
		}

		result
		// ['a', 'b', 'c', 'd', 'e', 'f', 'g']
	
------------------------------------
作为对象属性的 Generator 函数		|
------------------------------------
	# 如果一个对象的属性是 Generator 函数,可以简写成下面的形式
		let obj = {
			* myGeneratorMethod() {
				//···
			}
		};
	
		* 属性前面有一个星号,表示这个属性是一个 Generator 函数
		* 它的完整形式如下,与上面的写法是等价的

		let obj = {
			myGeneratorMethod: function* () {
				// ···
			}
		};

------------------------------------
Generator 函数的this				|
------------------------------------
	# Generator 函数总是返回一个遍历器,ES6 规定这个遍历器是 Generator 函数的实例(对象),也继承了 Generator 函数的prototype对象上的方法
		function* g() {}

		g.prototype.hello = function () {
			return 'hi!';
		};

		let obj = g();

		obj instanceof g	// true
		obj.hello()			// 'hi!'

		* Generator 函数g返回的遍历器obj,是g的实例,而且继承了g.prototype
		* 如果把g当作普通的构造函数,并不会生效,因为g返回的总是遍历器对象,而不是this对象
			function* g() {
			  this.a = 11;
			}

			let obj = g();
			obj.next();
			obj.a	 // undefined
			* Generator 函数g在this对象上面添加了一个属性a但是obj对象拿不到这个属性
		
		* Generator 函数也不能跟new命令一起用,会报错
			function* F() {
				yield this.x = 2;
				yield this.y = 3;
			}

			new F()
			// TypeError: F is not a constructor

	# 让 Generator 函数返回一个正常的对象实例,既可以用next方法,又可以获得正常的this
		function* F() {
			this.a = 1;
			yield this.b = 2;
			yield this.c = 3;
		}

		var obj = {};
		//使用call,改变生成器的context
		var f = F.call(obj);

		f.next();  // Object {value: 2, done: false}
		f.next();  // Object {value: 3, done: false}
		f.next();  // Object {value: undefined, done: true}

		obj.a // 1
		obj.b // 2
		obj.c // 3

		* 执行的是遍历器对象f,但是生成的对象实例是obj,有没有办法将这两个对象统一呢?
		* 一个办法就是将obj换成F.prototype
		* 再将F改成构造函数,就可以对它执行new命令了
			function* F() {
				this.a = 1;
				yield this.b = 2;
				yield this.c = 3;
			}
			var f = F.call(F.prototype);

			f.next();  // Object {value: 2, done: false}
			f.next();  // Object {value: 3, done: false}
			f.next();  // Object {value: undefined, done: true}

			f.a // 1
			f.b // 2
			---------------------------------------------------	
		   function* gen() {
				this.a = 1;
				yield this.b = 2;
				yield this.c = 3;
			}

			function F() {
				return gen.call(gen.prototype);
			}

			var f = new F();

			f.next();  // Object {value: 2, done: false}
			f.next();  // Object {value: 3, done: false}
			f.next();  // Object {value: undefined, done: true}

			f.a // 1
			f.b // 2
			f.c // 3

------------------------------------
含义								|
------------------------------------
	# Generator 与状态机
		* Generator 是实现状态机的最佳结构
		//普通函数实现
		var ticking = true;
        var clock = function() {
            if (ticking) {
                console.log('Tick!');
            } else {
                console.log('Tock!');
            }
            ticking = !ticking;
        }
		
		//生成器实现
        var clock = function* () {
            while (true) {
                console.log('Tick!');
                yield;
                console.log('Tock!');
                yield;
            }
        };
		* 少了用来保存状态的外部变量ticking,这样就更简洁,更安全(状态不会被非法篡改),更符合函数式编程的思想,在写法上也更优雅
		* Generator 之所以可以不用外部变量保存状态,是因为它本身就包含了一个状态信息,即目前是否处于暂停态
	
	# Generator 与协程
		* Emmmmmmmmmmmm,Python里面的协程
		* 遇到io操作则在当前线程的多个io任务之间切换....但js没得io任务啊
	
	# Generator 与上下文
		...

------------------------------------
应用								|
------------------------------------
	1,异步操作的同步化表达
		* 可以把异步操作写在yield表达式里面,等到调用next方法时再往后执行
		* 这实际上等同于不需要写回调函数了,因为异步操作的后续操作可以放在yield表达式下面,反正要等到调用next方法时再执行
		* 所以,Generator 函数的一个重要实际意义就是用来处理异步操作,改写回调函数
		   function* loadUI() {
				showLoadingScreen();
				yield loadUIDataAsynchronously();
				hideLoadingScreen();
			}

			var loader = loadUI();
			
			// 加载UI
			loader.next()

			// 卸载UI
			loader.next()
			* 第一次调用loadUI函数时,该函数不会执行,仅返回一个遍历器
			* 下一次对该遍历器调用next方法,则会显示Loading界面(showLoadingScreen),并且异步加载数据(loadUIDataAsynchronously)
			* 等到数据加载完成,再一次使用next方法,则会隐藏Loading界面,可以看到,这种写法的好处是所有Loading界面的逻辑,都被封装在一个函数,按部就班非常清晰
		
		* Ajax 是典型的异步操作,通过 Generator 函数部署 Ajax 操作,可以用同步的方式表达
		   function* main() {
				var result = yield request("http://some.url");
				var resp = JSON.parse(result);
				console.log(resp.value);
			}

			function request(url) {
				//makeAjaxCall函数中的next方法,必须加上response参数,因为yield表达式,本身是没有值的,总是等于undefined
				makeAjaxCall(url, function(response){
					it.next(response);
				});
			}

			var it = main();

			it.next();
			
			* 没看懂这个骚操作
		
		* 通过 Generator 函数逐行读取文本文件。
			function* numbers() {
				let file = new FileReader("numbers.txt");
				try {
					while(!file.eof) {
						yield parseInt(file.readLine(), 10);
					}
				} finally {
					file.close();
				}
			}
	
	2,控制流管理
		* 如果有一个多步操作非常耗时,采用回调函数,可能会写成下面这样
			step1(function (value1) {
				step2(value1, function(value2) {
					step3(value2, function(value3) {
						step4(value3, function(value4) {
							// Do something with value4
						});
					});
				});
			});

			* 采用 Promise 改写上面的代码
				Promise.resolve(step1)
				.then(step2)
				.then(step3)
				.then(step4)
				.then(function (value4) {
					// Do something with value4
				}, function (error) {
					// Handle any error from step1 through step4
				})
				.done();
			* 上面代码已经把回调函数,改成了直线执行的形式,但是加入了大量 Promise 的语法
			* Generator 函数可以进一步改善代码运行流程
				function* longRunningTask(value1) {
					try {
						var value2 = yield step1(value1);
						var value3 = yield step2(value2);
						var value4 = yield step3(value3);
						var value5 = yield step4(value4);
						// Do something with value4
					} catch (e) {
						// Handle any error from step1 through step4
					}
				}
				* 注意,上面这种做法,只适合同步操作,即所有的task都必须是同步的,不能有异步操作
				* 因为这里的代码一得到返回值,就继续往下执行,没有判断异步操作何时完成

		* 利用for...of循环会自动依次执行yield命令的特性,提供一种更一般的控制流管理的方法
			let steps = [step1Func, step2Func, step3Func];
			function* iterateSteps(steps){
				for (var i=0; i< steps.length; i++){
					var step = steps[i];
					yield step();
				}
			}
			* 数组steps封装了一个任务的多个步骤,Generator 函数iterateSteps则是依次为这些步骤加上yield命令
			* 将任务分解成步骤之后,还可以将项目分解成多个依次执行的任务
				let jobs = [job1, job2, job3];

				function* iterateJobs(jobs){
					for (var i=0; i< jobs.length; i++){
						var job = jobs[i];
						yield* iterateSteps(job.steps);
					}
				}
				* 数组jobs封装了一个项目的多个任务,Generator 函数iterateJobs则是依次为这些任务加上yield*命令
				* 最后,就可以用for...of循环一次性依次执行所有任务的所有步骤
					for (var step of iterateJobs(jobs)){
						console.log(step.id);
					}
			
				* 上面的做法只能用于所有步骤都是同步操作的情况，不能有异步操作的步骤
				* for...of的本质是一个while循环,所以上面的代码实质上执行的是下面的逻辑
					        var it = iterateJobs(jobs);
							var res = it.next();

							while (!res.done){
								var result = res.value;
								// ...
								res = it.next();
							}

	3,部署 Iterator 接口
		* 利用 Generator 函数,可以在任意对象上部署 Iterator 接口
		* 简单,不想写了,就是写个函数,可以对任何对象进行迭代
	
	4,作为数据结构
		* Generator 可以看作是数据结构,更确切地说,可以看作是一个数组结构,因为 Generator 函数可以返回一系列的值,这意味着它可以对任意表达式,提供类似数组的接口
			function* doStuff() {
				yield fs.readFile.bind(null, 'hello.txt');
				yield fs.readFile.bind(null, 'world.txt');
				yield fs.readFile.bind(null, 'and-such.txt');
			}
			* 上面代码就是依次返回三个函数,但是由于使用了 Generator 函数,导致可以像处理数组那样,处理这三个返回的函数
				for (task of doStuff()) {
					// task是一个函数，可以像回调函数那样使用它
				}

			* 如果用 ES5 表达,完全可以用数组模拟 Generator 的这种用法
				function doStuff() {
					return [
						fs.readFile.bind(null, 'hello.txt'),
						fs.readFile.bind(null, 'world.txt'),
						fs.readFile.bind(null, 'and-such.txt')
					];
				}
		
	
	
