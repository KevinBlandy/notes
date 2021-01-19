----------------------------
Iterator					|
----------------------------
	1,Iterator(遍历器)的概念
	2,默认 Iterator 接口
	3,调用 Iterator 接口的场合
	4,字符串的 Iterator 接口
	5,Iterator 接口与 Generator 函数
	6,遍历器对象的 return(),throw()
	7,for...of 循环

----------------------------
Iterator(遍历器)的概念		|
----------------------------
	# Iterator 的作用有三个
		* 一是为各种数据结构,提供一个统一的,简便的访问接口
		* 二是使得数据结构的成员能够按某种次序排列
		* 三是 ES6 创造了一种新的遍历命令for...of循环，Iterator 接口主要供for...of消费
	#Iterator 的遍历过程是这样的
		1,创建一个指针对象，指向当前数据结构的起始位置。也就是说，遍历器对象本质上,就是一个指针对象
		2,第一次调用指针对象的next方法，可以将指针指向数据结构的第一个成员
		3,第二次调用指针对象的next方法，指针就指向数据结构的第二个成员
		4,不断调用指针对象的next方法，直到它指向数据结构的结束位置
	# 每一次调用next方法,都会返回数据结构的当前成员的信息
	# 具体来说,就是返回一个包含value和done两个属性的对象
		* 其中,value属性是当前成员的值
		* done属性是一个布尔值,表示遍历是否结束
	
	# 模拟next方法返回值的例子
		function makeIterator(array) {
			var nextIndex = 0;
			return {
				next: function() {
					if(nextIndex < array.length){
						return {value: array[nextIndex++], done: false};
					}else{
						return {value: undefined, done: true};
					}
				}
			};
		}
		
		var it = makeIterator(['a', 'b']);
		
		console.log(it.next());	// { value: "a", done: false }
		console.log(it.next());	// { value: "b", done: false }
		console.log(it.next());	// { value: undefined, done: true }
	
		* 对于遍历器对象来说,done: false和value: undefined属性都是可以省略的
		* 因此上面的makeIterator函数可以简写成下面的形式

		function makeIterator(array) {
			var nextIndex = 0;
			return {
				next: function() {
					if(nextIndex < array.length ){
						//省略done,表示还未完成
						return {value: array[nextIndex++]}
					}else{
						//省略value,表示最后一个元素
						return {done: true};
					}
				}
			};
		}

	# 无限运行的遍历器对象的例子
		function idMaker(){
			let id = 1;
			return {
				next:function(){
					return {
						value:id++,
						done:false
					}
				}
			}
		}
		let it = idMaker();
		
		console.log(it.next());
		console.log(it.next());
		console.log(it.next());
		console.log(it.next());
		console.log(it.next());


----------------------------	
默认 Iterator 接口			|
----------------------------
	# 当使用for...of循环遍历某种数据结构时,该循环会自动去寻找 Iterator 接口
	# 一种数据结构只要部署了 Iterator 接口,就称这种数据结构是"可遍历的"(iterable)
	# 默认的 Iterator 接口部署在数据结构的Symbol.iterator属性,
		* 或者说,一个数据结构只要具有Symbol.iterator属性,就可以认为是"可遍历"
		* Symbol.iterator属性本身是一个函数，就是当前数据结构默认的遍历器生成函数,执行这个函数,就会返回一个遍历器
	# 原生具备 Iterator 接口的数据结构如下
		Array
		Map
		Set
		String
		TypedArray
		函数的 arguments 对象
		NodeList 对象
		
		* Array 的 iterator
			let arr = [1,2];
			it = arr[Symbol.iterator]()
			
			console.log(it.next())		//{value: 1, done: false}
			console.log(it.next())		//{value: 2, done: false}
			console.log(it.next())		//{value: undefined, done: true}

	# 其他数据结构, Iterator 接口，都需要自己在Symbol.iterator属性上面部署,这样才会被for...of循环遍历
		* 原型链上的对象具有该方法也可
		let me = {
			name:'KevinBlandy',
			age:23,
			skill:['java','Python','Javascript'],
			[Symbol.iterator](){
				let i = -1;
				let keys = Reflect.ownKeys(this);
				let _obj = this;
				return {
					next:function(){
						i += 1;
						if(i < keys.length){
							return {value:{[keys[i]]:Reflect.get(_obj,keys[i])}}
						}else{
							return  {done:true}
						}
					}
				}
			}
		}		
		for(let x of me){
			console.log(x)
		}
		//{name: "KevinBlandy"}
		//{age: 23}
		//{skill: Array(3)}
		//{Symbol(Symbol.iterator): ƒ}
	
	# 部署 Iterator 接口,有一个简便方法,就是Symbol.iterator方法直接引用数组的 Iterator 接口
		* 类似数组的对象调用数组的Symbol.iterator方法
		let iterable = {
			0: 'a',
			1: 'b',
			2: 'c',
			length: 3,
			[Symbol.iterator]: Array.prototype[Symbol.iterator]
		};
		for (let item of iterable) {
			console.log(item); // 'a', 'b', 'c'
		}
		
		* 普通对象部署数组的Symbol.iterator方法,并无效果
		let iterable = {
			a: 'a',
			b: 'b',
			c: 'c',
			length: 3,
			[Symbol.iterator]: Array.prototype[Symbol.iterator]
		};
		for (let item of iterable) {
			console.log(item); // undefined, undefined, undefined
		}
	
	# 如果Symbol.iterator方法对应的不是遍历器生成函数(即会返回一个遍历器对象),解释引擎将会报错
		var obj = {};
		obj[Symbol.iterator] = () => 1;
		[...obj] // TypeError: [] is not a function
	
	# 遍历器接口,数据结构就可以用for...of循环遍历,也可以使用while循环遍历
		var $iterator = ITERABLE[Symbol.iterator]();
		var $result = $iterator.next();
		while (!$result.done) {
			var x = $result.value;
			// ...
			$result = $iterator.next();
		}
		* ITERABLE代表某种可遍历的数据结构,$iterator是它的遍历器对象
		* 遍历器对象每次移动指针(next方法),都检查一下返回值的done属性,如果遍历还没结束,就移动遍历器对象的指针到下一步(next方法),不断循环

----------------------------	
调用 Iterator 接口的场合	|
----------------------------
	1,解构赋值
		* 对数组和 Set 结构进行解构赋值时,会默认调用Symbol.iterator方法
			let set = new Set().add('a').add('b').add('c');
			let [x,y] = set;
			// x='a'; y='b'
			let [first, ...rest] = set;
			// first='a'; rest=['b','c'];
	
	2,扩展运算符
		* 扩展运算符(...)也会调用默认的 Iterator 接口
			// 例一
			var str = 'hello';
			[...str] //  ['h','e','l','l','o']

			// 例二
			let arr = ['b', 'c'];
			['a', ...arr, 'd']
			// ['a', 'b', 'c', 'd']
		
		* 实际上,这提供了一种简便机制,可以将任何部署了 Iterator 接口的数据结构,转为数组
		* 也就是说,只要某个数据结构部署了 Iterator 接口,就可以对它使用扩展运算符,将其转为数组
			let arr = [...iterable];
	
	3,yield*
		* yield*后面跟的是一个可遍历的结构,它会调用该结构的遍历器接口

		let generator = function* () {
			yield 1;
			yield* [2,3,4];
			yield 5;
		};
		
		var iterator = generator();
		
		iterator.next() // { value: 1, done: false }
		iterator.next() // { value: 2, done: false }
		iterator.next() // { value: 3, done: false }
		iterator.next() // { value: 4, done: false }
		iterator.next() // { value: 5, done: false }
		iterator.next() // { value: undefined, done: true }
	
	4,其他场合
		* 由于数组的遍历会调用遍历器接口,所以任何接受数组作为参数的场合,其实都调用了遍历器接口
			for...of
			Array.from()
			Map(), Set(), WeakMap(), WeakSet()
				(比如new Map([['a',1],['b',2]]))
			Promise.all()
			Promise.race()

----------------------------	
字符串的 Iterator 接口		|
----------------------------
	# 字符串是一个类似数组的对象,也原生具有 Iterator 接口
		var someString = "hi";
		
		typeof someString[Symbol.iterator]	// "function"
	
		var iterator = someString[Symbol.iterator]();
	
		iterator.next()  // { value: "h", done: false }
		iterator.next()  // { value: "i", done: false }
		iterator.next()  // { value: undefined, done: true }

	# 可以覆盖原生的Symbol.iterator方法,达到修改遍历器行为的目的
		var str = new String("hi");
		[...str] // ["h", "i"]
		str[Symbol.iterator] = function() {
			return {
				next: function() {
					if (this._first) {
						this._first = false;
						return { value: "bye", done: false };
					} else {
						return { done: true };
					}
				},
				_first: true
			};
		};
		[...str] 	// ["bye"]
		str 		// "hi"
	
--------------------------------
Iterator 接口与 Generator 函数	|
--------------------------------
	# Symbol.iterator方法的最简单实现,是使用 Generator 函数	
		let myIterable = {
			[Symbol.iterator]: function* () {
				yield 1;
				yield 2;
				yield 3;
			}
		}
		[...myIterable] // [1, 2, 3]

		// 或者采用下面的简洁写法
		let obj = {
			* [Symbol.iterator]() {
				yield 'hello';
				yield 'world';
			}
		};

		for (let x of obj) {
			console.log(x);
		}
		// "hello"
		// "world"
	

--------------------------------
遍历器对象的 return(),throw()	|
--------------------------------
	# 遍历器对象除了具有next方法,还可以具有return方法和throw方法(可选)
	# return方法的使用场合是,如果for...of循环提前退出(通常是因为出错,或者有break语句或continue语句,就会调用return方法
		* 如果一个对象在完成遍历前,需要清理或释放资源,就可以部署return方法
		
		function readLinesSync(file) {
			return {
				[Symbol.iterator]() {
					return {
						next() {
							return { done: false };
						},
						return() {
							file.close();
							return { done: true };
						}
					};
				},
			};
		}

		* 函数readLinesSync接受一个文件对象作为参数,返回一个遍历器对象,其中除了next方法,还部署了return方法
		* 下面的三种情况,都会触发执行return方法

		// 情况一		输出文件的第一行以后,就会执行return方法,关闭这个文件
		for (let line of readLinesSync(fileName)) {
			console.log(line);
			break;
		}

		// 情况二		出所有行以后,执行return方法,关闭该文件
		for (let line of readLinesSync(fileName)) {
			console.log(line);
			continue;
		}

		// 情况三		会在执行return方法关闭文件之后,再抛出错误。
		for (let line of readLinesSync(fileName)) {
			console.log(line);
			throw new Error();
		}
		
		* throw方法主要是配合 Generator 函数使用,一般的遍历器对象用不到这个方法

--------------------------------
for...of 循环					|
--------------------------------
	# 一个数据结构只要部署了Symbol.iterator属性,就被视为具有 iterator 接口,就可以用for...of循环遍历它的成员
	# 也就是说,for...of循环内部调用的是数据结构的Symbol.iterator方法。
	# for...of循环可以使用的范围包括数组,Set 和 Map 结构,某些类似数组的对象(比如arguments对象DOM NodeList 对象),后文的 Generator 对象,以及字符串

	# 数组
		* 数组原生具备iterator接口(即默认部署了Symbol.iterator属性),for...of循环本质上就是调用这个接口产生的遍历器
			const arr = ['red', 'green', 'blue'];
			for(let v of arr) {
				console.log(v); // red green blue
			}
			const obj = {};
			obj[Symbol.iterator] = arr[Symbol.iterator].bind(arr);
			for(let v of obj) {
				console.log(v); // red green blue
			}
	
	# Set 和 Map 结构
		* Set 和 Map 结构也原生具有 Iterator 接口,可以直接使用for...of循环
			var engines = new Set(["Gecko", "Trident", "Webkit", "Webkit"]);
			for (var e of engines) {
				console.log(e);
			}
			// Gecko
			// Trident
			// Webkit
			
			var es6 = new Map();
			es6.set("edition", 6);
			es6.set("committee", "TC39");
			es6.set("standard", "ECMA-262");
			for (var [name, value] of es6) {
				console.log(name + ": " + value);
			}
			// edition: 6
			// committee: TC39
			// standard: ECMA-262
	
	# 计算生成的数据结构
	# 类似数组的对象
		* 类似数组的对象包括好几类
		* 并不是所有类似数组的对象都具有 Iterator 接口,一个简便的解决方法,就是使用Array.from方法将其转为数组
			let arrayLike = { length: 2, 0: 'a', 1: 'b' };
			// 报错
			for (let x of arrayLike) {
				console.log(x);
			}

			// 正确
			for (let x of Array.from(arrayLike)) {
				console.log(x);
			}
	# 对象
		* 对于普通的对象，for...of结构不能直接使用,会报错,必须部署了 Iterator 接口后才能使用
		* 一种解决方法是,使用Object.keys方法将对象的键名生成一个数组,然后遍历这个数组
			for (var key of Object.keys(someObject)) {
				console.log(key + ': ' + someObject[key]);
			}
		* 另一个方法是使用 Generator 函数将对象重新包装一下
			function* entries(obj) {
				for (let key of Object.keys(obj)) {
					yield [key, obj[key]];
				}
			}

			for (let [key, value] of entries(obj)) {
				console.log(key, '->', value);
			}
			// a -> 1
			// b -> 2

--------------------------------
与for in 比较					|
--------------------------------
	# for ..in 是用来遍历对象属性
	# for of 依赖于 Symbol.iterator,用来遍历成员