------------------------
Symbol					|
------------------------
	1,概述
	2,作为属性名的 Symbol
	3,实例:消除魔术字符串
	4,属性名的遍历
	5,Symbol.for()，Symbol.keyFor()
	6,实例：模块的 Singleton 模式
	7,内置的 Symbol 值

------------------------
概述					|
------------------------
	# ES5 的对象属性名都是字符串,这容易造成属性名的冲突
		* 你使用了一个他人提供的对象,但又想为这个对象添加新的方法(mixin 模式),新方法的名字就有可能与现有方法产生冲突
		* 如有一种机制,保证每个属性的名字都是独一无二的就好了,这样就从根本上防止属性名的冲突
		* 这就是 ES6 引入Symbol的原因
	
	# ES6 引入了一种新的原始数据类型Symbol,表示独一无二的值
		* 它是 JavaScript 语言的第七种数据类型
			undefined,null,Boolean,String,Number,Object
	
	# Symbol 值通过Symbol函数生成,这就是说,对象的属性名现在可以有两种类型
		* 一种是原来就有的字符串
		* 一种就是新增的 Symbol 类型
			* 凡是属性名属于 Symbol 类型,就都是独一无二的,可以保证不会与其他属性名产生冲突
	
			let s = Symbol();
			typeof s // "symbol"
		
			* 上面代码中,变量s就是一个独一无二的值
			* typeof运算符的结果,表明变量s是 Symbol 数据类型
			* 而不是字符串之类的其他类型
	
	# Symbol函数前不能使用new命令,否则会报错
		* 是因为生成的 Symbol 是一个原始类型的值,不是对象
		* 也就是说,由于 Symbol 值不是对象,所以不能添加属性
		* 基本上,它是一种类似于字符串的数据类型
	
	# Symbol函数可以接受一个字符串作为参数
		* 表示对 Symbol 实例的描述
		* 主要是为了在控制台显示,或者转为字符串时,比较容易区分
			let s1 = Symbol('foo');
			let s2 = Symbol('bar');
			
			s1 // Symbol(foo)
			s2 // Symbol(bar)
			
			s1.toString() // "Symbol(foo)"
			s2.toString() // "Symbol(bar)"
			
			* 如果不加参数,它们在控制台的输出都是Symbol(),不利于区分
	
	# 如果 Symbol 的参数是一个对象,就会调用该对象的toString方法,将其转为字符串,然后才生成一个 Symbol 值
		const obj = {
			toString() {
				return 'abc';
			}
		};
		const sym = Symbol(obj);
		
		console.log(sym); // Symbol(abc)
	
	# Symbol函数的参数只是表示对当前 Symbol 值的描述
		* 因此相同参数的Symbol函数的返回值是不相等的
		// 没有参数的情况
		let s1 = Symbol();
		let s2 = Symbol();
		console.log(s1 === s2); // false
		// 有参数的情况
		let s1 = Symbol('foo');
		let s2 = Symbol('foo');
		console.log(s1 === s2); // false
	
	# Symbol 值不能与其他类型的值进行运算,会报错
		let sym = Symbol('My symbol');
		"your symbol is " + sym
		// TypeError: can't convert symbol to string
		`your symbol is ${sym}`
		// TypeError: can't convert symbol to string
		
		* Symbol 值可以显式转为字符串,不能直接转换为数值
			let sym = Symbol('My symbol');
			String(sym) // 'Symbol(My symbol)'
			sym.toString() // 'Symbol(My symbol)'
		
		* 也可以转换为 bool值
			let sym = Symbol();
			Boolean(sym); 	// true
			!sym  			// false
			if (sym) {
				// ...
			}
			Number(sym); 	// TypeError
			sym + 2; 		// TypeError
			
------------------------
作为属性名的 Symbol		|
------------------------
	# 由于每一个 Symbol 值都是不相等的,这意味着 Symbol 值可以作为标识符，用于对象的属性名,就能保证不会出现同名的属性
		* 这对于一个对象由多个模块构成的情况非常有用,能防止某一个键被不小心改写或覆盖
		let mySymbol = Symbol();
		// 第一种写法
		let a = {};
		a[mySymbol] = 'Hello!';
		// 第二种写法
		let a = {
		  [mySymbol]: 'Hello!'
		};
		// 第三种写法
		let a = {};
		Object.defineProperty(a, mySymbol, { value: 'Hello!' });
		// 以上写法都得到同样结果
		a[mySymbol] // "Hello!"

	# Symbol 值作为对象属性名时,不能用点运算符
		const mySymbol = Symbol();
		const a = {};

		a.mySymbol = 'Hello!';
		a[mySymbol] // undefined
		a['mySymbol'] // "Hello!"

		* 点运算符后面总是字符串,所以不会读取mySymbol作为标识名所指代的那个值,导致a的属性名实际上是一个字符串,而不是一个 Symbol 值
		* 同理,在对象的内部,使用 Symbol 值定义属性时,Symbol 值必须放在方括号之中
			let s = Symbol();
			let obj = {
				[s]: function (arg) { ... }
			};
			obj[s](123);
			* 采用增强的对象写法,上面代码的obj对象可以写得更简洁一些
				let obj = {
					[s](arg) { ... }
				};

	
	# Symbol 类型还可以用于定义一组常量,保证这组常量的值都是不相等的
		log.levels = {
			DEBUG: Symbol('debug'),
			INFO: Symbol('info'),
			WARN: Symbol('warn')
		};
		log(log.levels.DEBUG, 'debug message');
		log(log.levels.INFO, 'info message');
		--------------------------------------
		const COLOR_RED    = Symbol();
		const COLOR_GREEN  = Symbol();
		
		function getComplement(color) {
			switch (color) {
				case COLOR_RED:
					return COLOR_GREEN;
				case COLOR_GREEN:
					return COLOR_RED;
				default:
					throw new Error('Undefined color');
			}
		}
	
		* 常量使用 Symbol 值最大的好处,就是其他任何值都不可能有相同的值了,因此可以保证上面的switch语句会按设计的方式工作
	
	# 还有一点需要注意,Symbol 值作为属性名时,该属性还是公开属性,不是私有属性

------------------------
消除魔术字符串			|
------------------------
	# 魔术字符串指的是,在代码之中多次出现,与代码形成强耦合的某一个具体的字符串或者数值
		* 风格良好的代码,应该尽量消除魔术字符串,改由含义清晰的变量代替
	
	# 就是把 Symbol 作为唯一的常量使用

------------------------
属性名遍历				|
------------------------
	# Symbol 作为属性名,该属性不会出现在for...in,for...of循环中,也不会被Object.keys(),Object.getOwnPropertyNames(),JSON.stringify()返回
	# 但是,它也不是私有属性,有一个 Object.getOwnPropertySymbols 方法,可以获取指定对象的所有 Symbol 属性名
		const name = Symbol("name");
		let obj = {
			[name]:'KevinBlandy'
		}
		let symbols = Object.getOwnPropertySymbols(obj);
		for (let symbol of symbols){
			console.log(symbol);			//Symbol(name)
			console.log(obj[symbol]);		//KevinBlandy
		}
	
	# 使用Object.getOwnPropertyNames方法得不到Symbol属性名,需要使用Object.getOwnPropertySymbols方法
	# Reflect.ownKeys方法可以返回所有类型的键名(数组),包括常规键名和 Symbol 键名
		const name = Symbol("name");
		let obj = {
			[name]:'KevinBlandy',
			name:'Litch'
		}
		let keys = Reflect.ownKeys(obj);
		for(let key of keys){
			console.log(key);
			console.log(obj[key]);
		}
	
	# 由于以 Symbol 值作为名称的属性,不会被常规方法遍历得到,我们可以利用这个特性,为对象定义一些非私有的,但又希望只用于内部的方法
		...看完了 class 以后再来看
	

------------------------------------
Symbol.for(),Symbol.keyFor()		|
------------------------------------
	# 有时,我们希望重新使用同一个 Symbol 值,Symbol.for方法可以做到这一点
		* 它接受一个字符串作为参数,然后搜索有没有以该参数作为名称的 Symbol 值
		* 如果有,就返回这个 Symbol 值,否则就新建并返回一个以该字符串为名称的 Symbol 值
			let s1 = Symbol.for('foo');
			let s2 = Symbol.for('foo');
			console.log(s1 === s2); // true
		
		* Symbol.for()与Symbol()这两种写法,都会生成新的 Symbol
		* 它们的区别是,前者会被登记在全局环境中供搜索,后者不会
		* Symbol.for()不会每次调用就返回一个新的 Symbol 类型的值,而是会先检查给定的key是否已经存在,如果不存在才会新建一个值
		* 调用Symbol("cat")30 次,会返回 30 个不同的 Symbol 值,调用30次 Symbol.for(),返回同一个对象
	

	# Symbol.keyFor方法返回一个已登记的 Symbol 类型值的key
		let s1 = Symbol.for("foo");			//登记Symbol(不存在则创建)
		console.log(Symbol.keyFor(s1))		// "foo"			//获取已经登记的
		
		let s2 = Symbol("foo");			
		console.log(Symbol.keyFor(s2))		// undefined		//未登记

		* 登记,其实就是在 Symbol.for 中进行注册
	
	# Symbol.for为 Symbol 值登记的名字,是全局环境的,可以在不同的 iframe 或 service worker 中取到同一个值
		iframe = document.createElement('iframe');
		iframe.src = String(window.location);
		document.body.appendChild(iframe);
		iframe.contentWindow.Symbol.for('foo') === Symbol.for('foo')
		// true


------------------------------------
模块的 Singleton 模式				|
------------------------------------
	# Singleton 模式指的是调用一个类,任何时候返回的都是同一个实例
	...

------------------------------------
内置的 Symbol 值 					|
------------------------------------
	# 除了定义自己使用的 Symbol 值以外,ES6 还提供了 11 个内置的 Symbol 值,指向语言内部使用的方法
	
	# Symbol.hasInstance
		* 对象的 Symbol.hasInstance 属性,指向一个内部方法,当其他对象使用instanceof运算符,判断是否为该对象的实例时,会调用这个方法
		* foo instanceof Foo 在语言内部,实际调用的是 Foo[Symbol.hasInstance](foo)

			class MyClass {
				[Symbol.hasInstance](foo) {
					console.log(foo)		//[1, 2, 3] instalce 传递进来来进行比较的值
					return foo instanceof Array;
				}
			}
			let isArray =  [1, 2, 3]  instanceof new MyClass() ;
			console.log(isArray)	// true
			-----------------------
			class Even {
				static [Symbol.hasInstance](obj) {
					return Number(obj) % 2 === 0;
				}
			}

			// 等同于
			const Even = {
				[Symbol.hasInstance](obj) {
					return Number(obj) % 2 === 0;
				}
			};

			1 instanceof Even // false
			2 instanceof Even // true
			12345 instanceof Even // false
			----------------------------
			let obj = {
				[Symbol.hasInstance]:function(foo){
					return foo == 'Hello';
				}
			}
			
			console.log('Hello' instanceof obj);//true
			console.log('World' instanceof obj);//false
	
	# Symbol.isConcatSpreadable
		* 对象的Symbol.isConcatSpreadable属性等于一个布尔值,表示该对象用于Array.prototype.concat()时,是否可以展开
			let arr1 = ['c', 'd'];
			['a', 'b'].concat(arr1, 'e') // ['a', 'b', 'c', 'd', 'e']
			arr1[Symbol.isConcatSpreadable] // undefined

			* '数组的默认行为是可以展开'
			* Symbol.isConcatSpreadable 默认等 于undefined,该属性等于true时，也有展开的效果
		
			let arr2 = ['c', 'd'];
			arr2[Symbol.isConcatSpreadable] = false;
			['a', 'b'].concat(arr2, 'e') // ['a', 'b', ['c','d'], 'e']

			* arr2设置不允许展开,就不会展开,而是当作一个整体
	
		* '类似数组的对象正好相反,默认不展开',它的Symbol.isConcatSpreadable属性设为true,才可以展开
			let obj = {
				length: 2,
				0: 'c',
				1: 'd'
			};
			['a', 'b'].concat(obj, 'e') // ['a', 'b', obj, 'e']
			* 在concat里面不会展开,而是作为一个整体Object加入到数组里面
			
			obj[Symbol.isConcatSpreadable] = true;
			['a', 'b'].concat(obj, 'e') // ['a', 'b', 'c', 'd', 'e']
			* 设置展开后,会把自身属性值作为新的数组元素挨个加入到数组
		
		* Symbol.isConcatSpreadable属性也可以定义在类里面
			class A1 extends Array {
				constructor(args) {
					super(args);
					//在构造函数里面初始化 isConcatSpreadable 值
					this[Symbol.isConcatSpreadable] = true;
				}
			}
			class A2 extends Array {
				constructor(args) {
					super(args);
				}
				//提供 Symbol.isConcatSpreadable 的读取器 
				get [Symbol.isConcatSpreadable] () {
					return false;
				}
			}
			let a1 = new A1();
			a1[0] = 3;
			a1[1] = 4;
			
			let a2 = new A2();
			a2[0] = 5;
			a2[1] = 6;
			
			[1, 2].concat(a1).concat(a2)
			// [1, 2, 3, 4, [5, 6]]				a2不允许展开
	
			* 注意,Symbol.isConcatSpreadable的位置差异,A1是定义在实例上,A2是定义在类本身,效果相同
		
	
	# Symbol.species
		* 对象的Symbol.species属性,指向一个构造函数,创建衍生对象时,会使用该属性
			class MyArray extends Array {
			}
			
			const a = new MyArray(1, 2, 3);
			
			const b = a.map(x => x);
			
			const c = a.filter(x => x > 1);
			
			b instanceof MyArray // true
			c instanceof MyArray // true
		
			* 子类MyArray继承了父类Array
			* a是MyArray的实例,b和c是a的衍生对象,你可能会认为,b和c都是调用数组方法生成的,所以应该是数组(Array的实例)
			* '但实际上它们也是MyArray的实例'
			
			* Symbol.species属性就是为了解决如上问题而提供的,现在,我们可以为MyArray设置Symbol.species属性	
				class MyArray extends Array {
					static get [Symbol.species]() { 
						return Array;		//创建衍生对象的时候,使用return的函数作为构造函数
					}
				}
				const a = new MyArray();
				const b = a.map(x => x);

				b instanceof MyArray		// false
				b instanceof Array			 // true
				
			* 由于定义了Symbol.species属性,'创建衍生对象时就会使用这个属性返回的函数,作为构造函数'
			* 这个例子也说明,'定义Symbol.species属性要采用get取值器'
			* 默认的Symbol.species属性等同于下面的写法
				static get [Symbol.species]() {
					return this;
				}

			* 小demo
				class T1 extends Promise {
				}
				class T2 extends Promise {
					static get [Symbol.species]() {
						return Promise;
					}
				}
				new T1(r => r()).then(v => v) instanceof T1 // true
				new T2(r => r()).then(v => v) instanceof T2 // false

			
			* Symbol.species的作用在于,实例对象在运行过程中,需要再次调用自身的构造函数时,会调用该属性指定的构造函数
			* 它主要的用途是,有些类库是在基类的基础上修改的,那么子类使用继承的方法时,作者可能希望返回基类的实例,而不是子类的实例
	
	# Symbol.match 
		* 对象的Symbol.match属性,指向一个函数,当执行str.match(myObject)时,如果该属性存在,会调用它,返回该方法的返回值
		* 被 str 的match执行时,执行该函数
				
			String.prototype.match(regexp);
			
			// 等同于
			regexp[Symbol.match](this);

			class MyMatcher {
				[Symbol.match](string) {
					return 'hello world'.indexOf(string);
				}
			}

			'e'.match(new MyMatcher()) // 1
	
	# Symbol.replace
		* 对象的Symbol.replace属性,指向一个方法,当该对象被String.prototype.replace方法调用时,会返回该方法的返回值
			String.prototype.replace(searchValue, replaceValue)
			// 等同于
			searchValue[Symbol.replace](this, replaceValue)
			----------------------------
			const x = {};
			x[Symbol.replace] = (...s) => console.log(s);
			'Hello'.replace(x, 'World') // ["Hello", "World"]

			* Symbol.replace方法会收到两个参数,第一个参数是replace方法正在作用的对象
			* 上面例子是Hello,第二个参数是替换后的值,上面例子是World
	
	# Symbol.search
		* 对象的Symbol.search属性,指向一个方法,当该对象被String.prototype.search方法调用时,会返回该方法的返回值
			String.prototype.search(regexp)
			// 等同于
			regexp[Symbol.search](this)

			class MySearch {
				constructor(value) {
					this.value = value;
				}
				[Symbol.search](string) {
					return string.indexOf(this.value);
				}
			}
			'foobar'.search(new MySearch('foo')) // 0
	
	# Symbol.split
		* 对象的Symbol.split属性,指向一个方法,当该对象被String.prototype.split方法调用时,会返回该方法的返回值
			String.prototype.split(separator, limit)
			// 等同于
			separator[Symbol.split](this, limit)
			-------------------------
			class MySplitter {
				constructor(value) {
					this.value = value;
				}
				[Symbol.split](string) {
					let index = string.indexOf(this.value);
					if (index === -1) {
						return string;
					}
					return [
						string.substr(0, index),
						string.substr(index + this.value.length)
					];
				}
			}
			'foobar'.split(new MySplitter('foo'))
			// ['', 'bar']
			'foobar'.split(new MySplitter('bar'))
			// ['foo', '']
			'foobar'.split(new MySplitter('baz'))
			// 'foobar'

			* 使用 split 重新定义字符串的 split 行为
	
	# Symbol.iterator
		* 对象的Symbol.iterator属性,指向该对象的默认遍历器方法
			const myIterable = {};
			myIterable[Symbol.iterator] = function* () {
				yield 1;
				yield 2;
				yield 3;
			};
			[...myIterable] //对该对象进迭代 [1, 2, 3]
		
		* 对象进行for...of循环时,会调用Symbol.iterator方法,返回该对象的默认遍历
			class Collection {
				*[Symbol.iterator]() {
					let i = 0;
					while(this[i] !== undefined) {
						yield this[i];
						++i;
					}
				}
			}

			let myCollection = new Collection();
			myCollection[0] = 1;
			myCollection[1] = 2;
			
			for(let value of myCollection) {
				console.log(value);
			}
			// 1
			// 2
	
	# Symbol.toPrimitive
		* 对象的Symbol.toPrimitive属性,指向一个方法
		* 该对象被转为原始类型的值时,会调用这个方法,返回该对象对应的原始类型值
		* Symbol.toPrimitive被调用时,会接受一个字符串参数,表示当前运算的模式,一共有三种模式
			Number	:该场合需要转成数值
			String	:该场合需要转成字符串
			Default	:该场合可以转成数值,也可以转成字符串
			
			let obj = {
				[Symbol.toPrimitive](hint) {
					switch (hint) {
						case 'number':
							return 123;
						case 'string':
							return 'str';
						case 'default':
							return 'default';
						default:
							throw new Error();
					}
				}
			};
			
			2 * obj 			// 246
			3 + obj 			// '3default'
			obj == 'default' 	// true
			String(obj) 		// 'str'
		
	# Symbol.toStringTag
		* 对象的Symbol.toStringTag属性.指向一个方法
		* 在该对象上面调用Object.prototype.toString方法时,如果这个属性存在,它的返回值会出现在toString方法返回的字符串之中
		* 表示对象的类型,也就是说,这个属性可以用来定制[object Object]或[object Array]中object后面的那个字符串
			// 例一
			({[Symbol.toStringTag]: 'Foo'}.toString())
			// "[object Foo]"

			// 例二
			class Collection {
				get [Symbol.toStringTag]() {
					return 'xxx';
				}
			}
			let x = new Collection();
			Object.prototype.toString.call(x) // "[object xxx]"

		* ES6 新增内置对象的Symbol.toStringTag属性值如下
			JSON[Symbol.toStringTag]：'JSON'
			Math[Symbol.toStringTag]：'Math'
			Module 对象M[Symbol.toStringTag]：'Module'
			ArrayBuffer.prototype[Symbol.toStringTag]：'ArrayBuffer'
			DataView.prototype[Symbol.toStringTag]：'DataView'
			Map.prototype[Symbol.toStringTag]：'Map'
			Promise.prototype[Symbol.toStringTag]：'Promise'
			Set.prototype[Symbol.toStringTag]：'Set'
			%TypedArray%.prototype[Symbol.toStringTag]：'Uint8Array'
			WeakMap.prototype[Symbol.toStringTag]：'WeakMap'
			WeakSet.prototype[Symbol.toStringTag]：'WeakSet'
			%MapIteratorPrototype%[Symbol.toStringTag]：'Map Iterator'
			%SetIteratorPrototype%[Symbol.toStringTag]：'Set Iterator'
			%StringIteratorPrototype%[Symbol.toStringTag]：'String Iterator'
			Symbol.prototype[Symbol.toStringTag]：'Symbol'
			Generator.prototype[Symbol.toStringTag]：'Generator'
			GeneratorFunction.prototype[Symbol.toStringTag]：'GeneratorFunction'
	
	# Symbol.unscopables
		* 对象的Symbol.unscopables属性,指向一个对象
		* 该对象指定了使用with关键字时,哪些属性会被with环境排除
			Array.prototype[Symbol.unscopables]
			// {
			//   copyWithin: true,
			//   entries: true,
			//   fill: true,
			//   find: true,
			//   findIndex: true,
			//   includes: true,
			//   keys: true
			// }

			Object.keys(Array.prototype[Symbol.unscopables])
			// ['copyWithin', 'entries', 'fill', 'find', 'findIndex', 'includes', 'keys']
		
			* 上面代码说明,数组有 7 个属性,会被with命令排除
			--------------------------------------------
			// 没有 unscopables 时
			class MyClass {
				foo() { return 1; }
			}

			var foo = function () { return 2; };
			
			with (MyClass.prototype) {
				foo(); // 1		该foo来自于 MyClass.prototype 的上下文
			}

			// 有 unscopables 时
			class MyClass {
				foo() { return 1; }
				get [Symbol.unscopables]() {
					//设置了with排除 foo
					return { foo: true };
				}
			}

			var foo = function () { return 2; };
			
			with (MyClass.prototype) {
				//该foo来自于上层作用域(全局)
				foo(); // 2
			}

			* 通过指定Symbol.unscopables属性,使得with语法块不会在当前作用域寻找foo属性,即foo将指向外层作用域的变量