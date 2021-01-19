----------------------------
ES6-对象					|
----------------------------
	1,属性的简洁表示法
	2,属性名表达式
	3,方法的 name 属性
	4,Object.is()
	5,Object.assign()
	6,属性的可枚举性和遍历
	7,Object.getOwnPropertyDescriptors()
	8,__proto__属性,Object.setPrototypeOf(),Object.getPrototypeOf()
	9,super 关键字
	10,Object.keys(),Object.values()，Object.entries()
	11,对象的扩展运算符

----------------------------
属性的简洁表示法			|
----------------------------
	# S6 允许直接写入变量和函数,作为对象的属性和方法
		const foo = 'bar';
		const baz = {foo};
		console.log(baz); // {foo: "bar"}
	
		// 等同于
		const baz = {foo: foo};	
	
	# ES6 允许在对象之中,直接写变量
		* 这时,属性名为变量名, 属性值为变量的值
		function f(x, y) {
			return {x, y};
		}
		// 等同于
		function f(x, y) {
			return {x: x, y: y};
		}
		f(1, 2) // Object {x: 1, y: 2}
	
	# 除了属性,方法也可以简写
		const o = {
			method() {
				return "Hello!";
			}
		};
		// 等同于
		const o = {
			method: function() {
				return "Hello!";
			}
		};
		* demo
			let birth = '2000/01/01';
			const Person = {
				name: 'KevinBlandy',
				//等同于birth: birth
				birth,
				// 等同于hello: function ()...
				hello() { console.log('我的名字是', this.name); }
			};
	
			
			//用于函数的返回值
			function foo(){
				let name = "Kevin";
				let age = 23;
				return {name,age};
			}
			console.log(foo());		//{name: "Kevin", age: 23}
	
	# CommonJS 模块输出一组变量,就非常合适使用简洁写法
		let ms = {};
		function getItem (key) {
			return key in ms ? ms[key] : null;
		};
		function setItem (key, value) {
			ms[key] = value;
		};
		function clear () {
			ms = {};
		};
		
		module.exports = { getItem, setItem, clear };
		// 等同于
		module.exports = {
			getItem: getItem,
			setItem: setItem,
			clear: clear
		};
	
	# 属性的赋值器(setter)和取值器(getter),事实上也是采用这种写法
		const cart = {
			_wheels: 4,
		
			get wheels () {
				return this._wheels;
			},
		
			set wheels (value) {
				if (value < this._wheels) {
					throw new Error('数值太小了！');
				}
				this._wheels = value;
			}
		}

	# 简洁写法的属性名总是字符串,这会导致一些看上去比较奇怪的结果
		const obj = {
			//在这里class是字符串,所以不会因为它属于关键字,而导致语法解析报错
			class () {}
		};
		// 等同于
		var obj = {
			'class': function() {}
		};
	
	# 如果某个方法的值是一个 Generator 函数,前面需要加上星号
		const obj = {
			* m() {
				yield 'hello world';
			}
		};

----------------------------
属性名表达式				|
----------------------------
	# JavaScript 两种定义对象属性的方法
		// 方法一	直接用标识符作为属性名
		obj.foo = true;

		// 方法二	用表达式作为属性名,这时要将表达式放在方括号之内
		obj['a' + 'bc'] = 123;
	
	# S5使用字面量方式定义对象(使用大括号)在 ES5 中只能使用方法一(标识符)定义属性
		var obj = {
			foo: true,
			abc: 123
		};
	
	# ES6 允许字面量定义对象时,用方法二(表达式)作为对象的属性名,即把表达式放在方括号内
		let propKey = 'foo';
		let obj = {
			[propKey]: true,		//foo:true
			['a' + 'bc']: 123		//abc:123
		};
		console.log(obj);		//{foo: true, abc: 123}
		----------------------------------------------------	
		let lastWord = 'last word';
		const a = {
			'first word': 'hello',
			[lastWord]: 'world'
		};
		a['first word'] // "hello"
		a[lastWord] 	// "world"
		a['last word'] 	// "world"
		console.log(a);		//{first word: "hello", last word: "world"}
	
	# 表达式还可以用于定义方法名
		let test = 'test';
		let obj = {
			['h' + 'ello']() {		//通过 []来计算,函数名
				console.log('hi');
			},
			[test](){
				console.log("test");
			}
		};
		obj.hello() // hi
		obj.test();	//test
	
	# 属性名表达式与简洁表示法,不能同时使用,会报错
		// 报错
		const foo = 'bar';
		const bar = 'abc';
		const baz = { [foo] };

		// 正确
		const foo = 'bar';
		const baz = { [foo]: 'abc'};
	
	# 属性名表达式如果是一个对象,默认情况下会自动将对象转为字符串'[object Object]'
		const keyA = {a: 1};
		const keyB = {b: 2};

		const myObject = {
			[keyA]: 'valueA',
			[keyB]: 'valueB'
		};

		console.log(myObject); // Object {[object Object]: "valueB"}

-----------------------------------
方法的name属性						|
-----------------------------------
	# 函数的name属性,返回函数名,对象方法也是函数,因此也有name属性
		const person = {
			sayName() {
				console.log('hello!');
			},
		};
		person.sayName.name;   // "sayName"
	
	# 如果对象的方法使用了取值函数(getter)和存值函数(setter)
		* name属性不是在该方法上面,而是'该方法的属性的描述对象'的get和set属性上面,返回值是方法名前加上get和set
		const obj = {
			get foo() {},
			set foo(x) {}
		};
		
		obj.foo.name
		TypeError: Cannot read property 'name' of undefined
		
		const descriptor = Object.getOwnPropertyDescriptor(obj, 'foo');
		console.log(descriptor.get.name); // "get foo"
		console.log(descriptor.set.name); // "set foo"
	
	# 有两种特殊情况
		* bind方法创造的函数,name属性返回bound加上原函数的名字
			var doSomething = function() {
					// ...
			};
			doSomething.bind().name // "bound doSomething"
			
		* Function构造函数创造的函数,name属性返回anonymous
			(new Function()).name // "anonymous"
	
	# 如果对象的方法是一个 Symbol 值,那么name属性返回的是这个 Symbol 值的描述
		const key1 = Symbol('description');
		const key2 = Symbol();
		let obj = {
			[key1]() {},
			[key2]() {},
		};
		console.log(obj[key1].name); // "[description]"
		console.log(obj[key2].name); // ""
		//key1对应的 Symbol 值有描述,key2没有
	
------------------------------------
Object.is()							|
------------------------------------
	# ES5 比较两个值是否相等,只有两个运算符
		* 相等运算符(==)和严格相等运算符(===)
		* 它们都有缺点,前者会自动转换数据类型,后者的NaN不等于自身,以及+0等于-0
	
	# javaScript 缺乏一种运算,在所有环境中,只要两个值是一样的,它们就应该相等
		* ES6 提出"Same-value equality"(同值相等)算法,用来解决这个问题
		* Object.is就是部署这个算法的新方法,它用来比较两个值是否严格相等,'与严格比较运算符(===)的行为基本一致'
		
		Object.is('foo', 'foo')
		// true
		Object.is({}, {})
		// false
	
		* 不同之处只有两个:一是+0不等于-0,二是NaN等于自身
			+0 === -0 		//true
			NaN === NaN 	// false
		
			Object.is(+0, -0) 	// false
			Object.is(NaN, NaN) // true
	
	# ES5 可以通过下面的代码,部署Object.is
		Object.defineProperty(Object, 'is', {
			value: function(x, y) {
				if (x === y) {
					// 针对+0 不等于 -0的情况
					return x !== 0 || 1 / x === 1 / y;
				}
				// 针对NaN的情况
				return x !== x && y !== y;
			},
			configurable: true,
			enumerable: false,
			writable: true
		});

------------------------------------
Object.assign						|
------------------------------------
	# Object.assign 方法用于对象的合并,将源对象(source)的所有可枚举属性,复制到目标对象(target)
		* Object.assign方法的第一个参数是目标对象,后面的参数(一个或者多个)都是源对象
			const target = { a: 1 };
			const source1 = { b: 2 };
			const source2 = { c: 3 };
			
			Object.assign(target, source1, source2);
			console.log(target);	// {a:1, b:2, c:3}

		* '如果目标对象与源对象有同名属性,或多个源对象有同名属性,则后面的属性会覆盖前面的属性'
		* '如果只有一个参数,Object.assign会直接返回该参数'
		* 如果该参数不是对象,则会先转成对象,然后返回
			typeof Object.assign(2) // "object"

		* 由于undefined和null无法转成对象,所以如果它们作为参数,就会报错
			Object.assign(undefined) // 报错
			Object.assign(null) // 报错
	
		* 这些参数都会转成对象,如果无法转成对象,就会跳过,这意味着,如果undefined和null不在首参数,就不会报错
			let obj = {a: 1};
			Object.assign(obj, undefined) === obj	// true
			Object.assign(obj, null) === obj		// true
		
		* 其他类型的值(即数值,字符串和布尔值)不在首参数,也不会报错,但是,除了字符串会以数组形式,拷贝入目标对象,其他值都不会产生效果
			const v1 = 'abc';
			const v2 = true;
			const v3 = 10;
			
			const obj = Object.assign({}, v1, v2, v3);
			console.log(obj); // { "0": "a", "1": "b", "2": "c" }

			* 上面代码中,布尔值,数值,字符串分别转成对应的包装对象
			* 可以看到它们的原始值都在包装对象的内部属性[[PrimitiveValue]]上面,这个属性是不会被Object.assign拷贝的
			* 只有字符串的包装对象,会产生可枚举的实义属性,那些属性则会被拷贝

		* Object.assign拷贝的属性是有限制的,只拷贝源对象的自身属性(不拷贝继承属性),也'不拷贝不可枚举的属性(enumerable: false)'
		
		* 属性名为 Symbol 值的属性,也会被Object.assign拷贝
			Object.assign({ a: 'b' }, { [Symbol('c')]: 'd' })
			// { a: 'b', Symbol(c): 'd' }
		
	# 注意点
		1,浅拷贝
			* Object.assign方法实行的是浅拷贝,而不是深拷贝
			* 也就是说,如果源对象某个属性的值是对象,那么目标对象拷贝得到的是这个对象的引用
		
		2,同名属性的替换
			* 对于嵌套的对象,一旦遇到同名属性,Object.assign的处理方法是替换,而不是添加
				const target = { a: { b: 'c', d: 'e' } };
				const source = { a: { b: 'hello' } };
				Object.assign(target, source);
				console.log(target);// { a: { b: 'hello' } }

				* target对象的a属性被source对象的a属性整个替换掉了
			
		3,数组的处理
			* Object.assign可以用来处理数组,但是会把数组视为对象
				Object.assign([1, 2, 3], [4, 5]);
				// [4, 5, 3]
				
				* object.assign把数组视为属性名为 0,1,2 的对象
				* 因此源数组的 0 号属性4覆盖了目标数组的 0 号属性1

		4,取值函数的处理
			* Object.assign只能进行值的复制,如果要复制的值是一个取值函数,那么将求值后再复制
				const source = {
					get foo() { return 1 }
				};
				const target = {};
				
				Object.assign(target, source)// { foo: 1 }
			* source对象的foo属性是一个取值函数,Object.assign不会复制这个取值函数,只会拿到值以后,将这个值复制过去

	# 常见用途
		1,为对象添加属性
			class Point {
				constructor(x, y) {
					Object.assign(this, {x, y});
				}
			}
			
			* 通过Object.assign方法,将x属性和y属性添加到Point类的对象实例
		
		2,为对象添加方法
			* 给 SomeClass 类的原型对象添加俩方法
			Object.assign(SomeClass.prototype, {
				someMethod(arg1, arg2) {
				},
				anotherMethod() {
				}
			});

			// 等同于下面的写法
			SomeClass.prototype.someMethod = function (arg1, arg2) {
			};
			SomeClass.prototype.anotherMethod = function () {
			};
		
		3,克隆对象
			* 将原始对象拷贝到一个空对象,就得到了原始对象的克隆,但是不会把源对象的原型对象的属性添加过去
				function clone(origin) {
					return Object.assign({}, origin);
				}

			* 如果想要保持继承链,可以采用下面的代码
				function clone(origin) {
					//获取原型对象
					let originProto = Object.getPrototypeOf(origin);
					//使用Object.create来创建对象,先继承自其原型对象,然后再把源对象自己的属性继承过来
					return Object.assign(Object.create(originProto), origin);
				}
		
		4,合并多个对象
			const merge = (target, ...sources) => Object.assign(target, ...sources);
			const merge = (...sources) => Object.assign({}, ...sources);
		
		5,为属性指定默认值
			const DEFAULTS = {
				logLevel: 0,
				outputFormat: 'html'
			};
			function processContent(options) {
				//为 options 这个对象,添加 DEFAULTS对象的所有属性后,把对象赋值给 options
				options = Object.assign({}, DEFAULTS, options);
				console.log(options);
			}

------------------------------------
属性的可枚举性和遍历				|
------------------------------------
	# 可枚举
		* 对象的每个属性都有一个描述对象(Descriptor),用来控制该属性的行为
		* Object.getOwnPropertyDescriptor 方法可以获取该属性的描述对象
		* 描述对象的enumerable属性,称为"可枚举性",如果该属性为false,就表示某些操作会忽略当前属性
			for...in循环	:只遍历对象自身的和继承的可枚举的属性
			Object.keys()	:返回对象自身的所有可枚举的属性的键名
			JSON.stringify():只串行化对象自身的可枚举的属性
			Object.assign()	:忽略enumerable为false的属性，只拷贝对象自身的可枚举
			
			Object.getOwnPropertyDescriptor(Object.prototype, 'toString').enumerable
			// false

			Object.getOwnPropertyDescriptor([], 'length').enumerable
			// false

		* 另外,ES6 规定,所有 Class 的原型的方法都是不可枚举的
			Object.getOwnPropertyDescriptor(class {foo() {}}.prototype, 'foo').enumerable
			// false

		* 总的来说,操作中引入继承的属性会让问题复杂化,大多数时候,我们只关心对象自身的属性
		  所以,尽量不要用for...in循环,而用Object.keys()代替
		 
	
	# 属性的遍历,ES6 一共有 5 种方法可以遍历对象的属性
		1,for...in
			* for...in循环遍历对象自身的和继承的可枚举属性(不含 Symbol 属性)

		2,Object.keys(obj)
			* Object.keys返回一个数组,包括对象自身的(不含继承的)所有可枚举属性(不含 Symbol 属性)的键名

		3,Object.getOwnPropertyNames(obj)
			* Object.getOwnPropertyNames返回一个数组,包含对象自身的所有属性(不含 Symbol 属性,但是包括不可枚举属性)的键名

		4,Object.getOwnPropertySymbols(obj)
			* Object.getOwnPropertySymbols返回一个数组,包含对象自身的所有 Symbol 属性的键名

		5,Reflect.ownKeys(obj)
			* Reflect.ownKeys返回一个数组,包含对象自身的所有键名,不管键名是 Symbol 或字符串,也不管是否可枚举

		* 以上的 5 种方法遍历对象的键名,都遵守同样的属性遍历的次序规则
			首先遍历所有数值键,按照数值升序排列
			其次遍历所有字符串键,按照加入时间升序排列
			最后遍历所有 Symbol 键，按照加入时间升序排列
		
			Reflect.ownKeys({ [Symbol()]:0, b:0, 10:0, 2:0, a:0 })
			// ['2', '10', 'b', 'a', Symbol()]
			
			* Reflect.ownKeys方法返回一个数组,包含了参数对象的所有属性
			* 这个数组的属性次序是这样的,首先是数值属性2和10,其次是字符串属性b和a,最后是 Symbol 属性

------------------------------------	
Object.getOwnPropertyDescriptors()	|
------------------------------------
	# Object.getOwnPropertyDescriptors方法,返回指定对象所有自身属性(非继承属性)的描述对象
	# 该方法的实现非常容易
		function getOwnPropertyDescriptors(obj) {
			const result = {};
			for (let key of Reflect.ownKeys(obj)) {
				result[key] = Object.getOwnPropertyDescriptor(obj, key);
			}
			return result;
		}

	# 该方法的引入目的,主要是为了解决Object.assign()无法正确拷贝get属性和set属性的问题
		const source = {
			set foo(value) {
				console.log(value);
			}
		};
		const target1 = {};
		Object.assign(target1, source);
		console.log(Object.getOwnPropertyDescriptor(target1, 'foo'));
		//{value: undefined, writable: true, enumerable: true, configurable: true}
	
	# Object.getOwnPropertyDescriptors 方法配合Object.defineProperties 方法,就可以实现正确拷贝
		const source = {
			set foo(value) {
				console.log(value);
			}
		};
		const target2 = {};
		//给 target2 对象创建N多属性,来自于 source 对象的N多属性
		Object.defineProperties(target2, Object.getOwnPropertyDescriptors(source));
		//可以正确的获取到get/set属性
		Object.getOwnPropertyDescriptor(target2, 'foo');
		
		//合并成一句话
		const shallowMerge = (target, source) => Object.defineProperties(target,Object.getOwnPropertyDescriptors(source));
	
	# Object.getOwnPropertyDescriptors方法的另一个用处,是配合Object.create方法,将对象属性克隆到一个新对象
		//对obj对象进行clone
		function clone(obj){
			return Object.create(Object.getPrototypeOf(obj),Object.getOwnPropertyDescriptors(obj));
		}
		
		// 或者
		const shallowClone = (obj) => Object.create(Object.getPrototypeOf(obj),Object.getOwnPropertyDescriptors(obj));
	
	# Object.getOwnPropertyDescriptors方法可以实现一个对象继承另一个对象
		const obj = Object.create(
			prot,								//原型对象
			Object.getOwnPropertyDescriptors({	//从某个对象继承属性
				foo: 123,
			})
		);
	
	# Object.getOwnPropertyDescriptors也可以用来实现 Mixin(混入)模式
		....
	
----------------------------------------------------------------
__proto__属性,Object.setPrototypeOf(),Object.getPrototypeOf() 	|
----------------------------------------------------------------
	# __proto__属性
		* 用来读取或设置当前对象的prototype对象,目前,所有浏览器(包括 IE11)都部署了这个属性
		* '尽量不要使用这个属性,不是一个正式的对外的 API,只是由于浏览器广泛支持,才被加入了 ES6,标准明确规定,只有浏览器必须部署这个属性,其他运行环境不一定需要部署'
		* 使用api来设置,读取,创建
			Object.setPrototypeOf()
				* 设置原型对象
			Object.getPrototypeOf()
				* 获取原型对象
			Object.create()
				* 创建对象
	
	# Object.setPrototypeOf()
		* 作用与__proto__相同,用来设置一个对象的原型对象
		* 返回参数对象本身,它是 ES6 正式推荐的设置原型对象的方法
		* 相当于
			function (obj, proto) {
				obj.__proto__ = proto;
				return obj;
			}
		
		* 如果第一个参数不是对象,会自动转为对象,但是由于返回的还是第一个参数,所以这个操作不会产生任何效果
		* 由于undefined和null无法转为对象,所以如果第一个参数是undefined或null,就会报错
	

	# Object.getPrototypeOf() 
		* 读取一个对象的原型对象
		* 如果参数不是对象,会被自动转为对象,如果参数是undefined或null,它们无法转为对象,所以会报错
	
--------------------------------
super 关键字					|
--------------------------------
	# this关键字总是指向函数所在的当前对象
	# ES6 又新增了另一个类似的关键字 super,指向当前对象的原型对象
		const proto = {
		  foo: 'hello'
		};

		const obj = {
		  foo: 'world',
		  find() {
			return super.foo;
		  }
		};

		Object.setPrototypeOf(obj, proto);
		obj.find() // "hello"
		* 对象obj的find方法之中,通过super.foo引用了原型对象proto的foo属性
	
	# 'super关键字表示原型对象时,只能用在对象的方法之中,用在其他地方都会报错'
		// 报错
		const obj = {
			foo: super.foo
		}
		
		// 报错
		const obj = {
			foo: () => super.foo
		}
		
		// 报错
		const obj = {
			foo: function () {
				return super.foo
			}
		}
		* 上面三种super的用法都会报错,因为对于 JavaScript 引擎来说,这里的super都没有用在对象的方法之中
		* 第一种写法是super用在属性里面
		* 第二种和第三种写法是super用在一个函数里面,然后'赋值给foo属性'(其实还是属性,不过这个属性是个方法)
		* '目前,只有对象方法的简写法可以让 JavaScript 引擎确认,定义的是对象的方法'

	# JavaScript 引擎内部,super 是这样的
		* Object.getPrototypeOf(this).foo					(属性)
		* Object.getPrototypeOf(this).foo.call(this)		(方法)
			

----------------------------------------------
Object.keys(),Object.values(),Object.entries()|
----------------------------------------------
	# Object.keys()
		* 返回一个数组,成员是参数对象自身的(不含继承的)所有可遍历(enumerable)属性的键名
	
	# Object.values()
		* 返回一个数组,成员是参数对象自身的(不含继承的)所有可遍历(enumerable)属性的键值
		* 返回数组的成员顺序,与本笔记的的《属性的遍历》部分介绍的排列规则一致
		* Object.values 会过滤属性名为 Symbol 值的属性
		* 如果Object.values方法的参数是一个字符串,会返回各个字符组成的一个数组
		* 如果参数不是对象,Object.values会先将其转为对象,由于数值和布尔值的包装对象,都不会为实例添加非继承的属性,所以,Object.values会返回空数组

	# Object.entries()
		* 法返回一个数组,成员是参数对象自身的(不含继承的)所有可遍历(enumerable)属性的键值对数组
		* 除了返回值不一样,该方法的行为与Object.values基本一致
		* 如果原对象的属性名是一个 Symbol 值,该属性会被忽略
		* Object.entries的基本用途是遍历对象的属性
			let obj = { one: 1, two: 2 };
			for (let [k, v] of Object.entries(obj)) {
				console.log(
					 `${JSON.stringify(k)}: ${JSON.stringify(v)}`
				);
			}
		* Object.entries方法的另一个用处是,将对象转为真正的Map结构
			const obj = { foo: 'bar', baz: 42 };
			const map = new Map(Object.entries(obj));
			log(map); // Map { foo: "bar", baz: 42 }
		* 自己实现Object.entries方法
			// Generator函数的版本
			function* entries(obj) {
				for (let key of Object.keys(obj)) {
					yield [key, obj[key]];
				}
			}
			
			// 非Generator函数的版本
			function entries(obj) {
				let arr = [];
				for (let key of Object.keys(obj)) {
					arr.push([key, obj[key]]);
				}
				return arr;
			}

--------------------------------
对象的扩展运算符				|
--------------------------------
	# 解构赋值
		* 对象的解构赋值用于从一个对象取值,相当于将目标对象自身的所有可遍历的(enumerable)但尚未被读取的属性,分配到指定的对象上面,所有的键和它们的值,都会拷贝到新对象上面
			let { x, y, ...z } = { x: 1, y: 2, a: 3, b: 4 };
			console.log(x); // 1
			console.log(y) // 2
			console.log(z) // { a: 3, b: 4 }
			

			* x和y都能成功的被赋值
			* 当z没有的与之匹配的数据项的时候,会把所有的数据都匹配到z里面,形成一个对象(解构赋值)
			
		* 解构赋值要求等号右边是一个对象,所以如果等号右边是undefined或null,就会报错,因为它们无法转为对象
		* 解构赋值必须是最后一个参数,否则会报错
		* 解构赋值的拷贝是浅拷贝,即如果一个键的值是复合类型的值(数组,对象,函数),a那么解构赋值拷贝的是这个值的引用,而不是这个值的副本
		* 扩展运算符的解构赋值,不能复制继承自原型对象的属性('原型对象,不能被解构赋值')
			let o1 = { a: 1 };
			let o2 = { b: 2 };
			//设置o2的原型对象为o1
			o2.__proto__ = o1;
			//把o2解构赋值到o3
			let { ...o3 } = o2;
			//打印o3,没有o1(原型对象)的属性
			log(o3)         // { b: 2 }
			//打印o3原型对象里面的a,不存在
			log(o3.a)    
			--------------------------------------
			//创建一个对象,并且设置原型对象
			const o = Object.create({ x: 1, y: 2 });
			//设置对象的z属性
			o.z = 3;
			//把对象解构赋值
			let { x, ...{ y, z } } = o;

			x // 1
			y // undefined      原型对象的数据未解构赋值成功
			z // 3
			
			'浏览器抛出异常	Uncaught SyntaxError: `...` must be followed by an identifier in declaration contexts'
		
		* 解构赋值的一个用处,是扩展某个函数的参数,引入其他操作
			function baseFunction({ a, b }) {
				console.log(a);
				console.log(b);
			}
			function wrapperFunction({ x, y, ...restConfig }) {
				// 使用 x 和 y 参数进行操作
				// 其余参数传给原始函数
				return baseFunction(restConfig);
			}

	# 扩展运算符
		 * 对象的扩展运算符(...)用于取出参数对象的所有可遍历属性,拷贝到当前对象之中
			let z = { a: 3, b: 4 };
			let n = { ...z };		//把z对象,解构赋值解析为一个个的属性,赋值给了n
			n // { a: 3, b: 4 }

			* 这等同于使用Object.assign方法
			let aClone = { ...a };
			// 等同于
			let aClone = Object.assign({}, a);

		* 上面的例子只是拷贝了对象实例的属性,如果想完整克隆一个对象,还拷贝对象原型的属性,可以采用下面的写法
			 // 写法一
			const clone1 = {
				//设置当前对象的prototype为obj的prototype
				__proto__: Object.getPrototypeOf(obj),
				...obj
			};

			// 写法二
			const clone2 = Object.assign(
				//创建一个对象,该对象的原型对象就是obj的prototype
				Object.create(Object.getPrototypeOf(obj)),
				obj
			);

			// 写法三
			const clone3 = Object.create(
				//创建一个对象,原型对象继承自obj的原型对象,然后属性来自于obj
				Object.getPrototypeOf(obj),
				Object.getOwnPropertyDescriptors(obj)
			)
			
			* 上面代码中,写法一的__proto__属性在非浏览器的环境不一定部署,因此'推荐使用写法二和写法三'
	
			* 扩展运算符可以用于合并两个对象
				let a = {name:'Kevin'}
				let b = {age:23}

				let ab = { ...a, ...b };
				let ab = Object.assign({}, a, b);
				//{name: "Kevin", age: 23}
	
			* 如果用户自定义的属性,放在扩展运算符后面,则扩展运算符内部的同名属性会被覆盖掉
				let a = {x:'x的值'}
				let aWithOverrides1 = { ...a, x: 1, y: 2 };		//如果a对象里有x/y的属性值,那么会被后面的x/y覆盖
				// 等同于
				 let aWithOverrides2 = { ...a, ...{ x: 1, y: 2 } };
				 // 等同于
				 let x = 1, y = 2, aWithOverrides3 = { ...a, x, y };
				 // 等同于
				 let aWithOverrides4 = Object.assign({}, a, { x: 1, y: 2 });
				//a对象的x属性和y属性,拷贝到新对象后会被覆盖掉
				console.log(aWithOverrides1);       //{x: 1, y: 2}
			
			* 用来修改现有对象部分的属性就很方便了
				let newVersion = {
					//旧对象
					...previousVersion,
					//覆盖旧对象里面的name属性
					name: 'New Name' // Override the name property
				};
			
			* 如果把自定义属性放在扩展运算符前面,就变成了设置新对象的默认属性值('反正后面的属性,会覆盖前面的属性')
				 let a = {x:6}
				let aWithDefaults1 = { x: 1, y: 2, ...a };		//a对象里面的x属性会覆盖前面的x属性
				// 等同even if property keys don’t clash, because objects record insertion order:

				let aWithDefaults2 = Object.assign({}, { x: 1, y: 2 }, a);
				// 等同于
				let aWithDefaults3 = Object.assign({ x: 1, y: 2 }, a);

				console.log(aWithDefaults1);
				console.log(aWithDefaults2);
				console.log(aWithDefaults3);
			
			* 与数组的扩展运算符一样,对象的扩展运算符后面可以跟表达式
				const obj = {
					...(x > 1 ? {a: 1} : {}),
					b: 2,
				};
			
			* 如果扩展运算符后面是一个空对象,则没有任何效果
				 let {...x} = {...{}, a: 1}
				console.log(x);		//{a: 1}
			
			* 如果扩展运算符的参数是null或undefined,这两个值会被忽略,不会报错
				let emptyObject = { ...null, ...undefined }; // 不报错
			
			* 扩展运算符的参数对象之中,如果有取值函数get,这个函数是会执行的
				// 并不会抛出错误，因为 x 属性只是被定义，但没执行
				let aWithXGetter = {
					...a,
					get x() {
						throw new Error('not throw yet');
					}
				};
				// 会抛出错误，因为 x 属性被执行了
				let runtimeError = {
					...a,
					...{
						get x() {
							throw new Error('throw now');
						}
					}
				};