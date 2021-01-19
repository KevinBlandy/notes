------------------------------------
解构赋值							|
------------------------------------
	1,数组的解构赋值
	2,对象的解构赋值
	3,字符串的解构赋值
	4,数值和布尔值的解构赋值
	5,函数参数的解构赋值
	6,圆括号问题
	7,用途

------------------------------------
解构赋值							|
------------------------------------
	# 一种赋值的语法而已
		let [a,b,c] = [1,2,3];

	#　如果等号的右边不是数组(或者严格地说,不是可遍历的结构,参见《Iterator》一章),那么将会报错
		* 以下都会报错
			let [foo] = 1;
			let [foo] = false;
			let [foo] = NaN;
			let [foo] = undefined;
			let [foo] = null;
			let [foo] = {};
	
	# 对于 Set 结构,也可以使用数组的解构赋值
		let [x, y, z] = new Set(['a', 'b', 'c']);
		x // "a"
	
	# 只要某种数据结构具有 Iterator 接口,都可以采用数组形式的解构赋值
		function* fibs() {
			let a = 0;
			let b = 1;
			while (true) {
				yield a;
				[a, b] = [b, a + b];
			}
		}

		let [first, second, third, fourth, fifth, sixth] = fibs();
		console.log(sixth) // 5

	# 这种写法属于 "模式匹配"，只要等号两边的模式相同，左边的变量就会被赋予对应的值。

	# 复杂的案例
		* 多维数组嵌套
			let [foo, [[bar], baz]] = [1, [[2], 3]];
				foo // 1
				bar // 2
				baz // 3

		* 使用空元素占位
			let [ , , third] = ["foo", "bar", "baz"];
				third // "baz"

			let [x, , y] = [1, 2, 3];
				x // 1
				y // 3

		* 对数组进行解构赋值
			* 数组解构失败,会是空数组
			* 数组只能在最后出现(使用三个点(...)标识),前面的都匹配完毕后,剩余的所有数据都会被匹配进数组
			let [head, ...tail] = [1, 2, 3, 4];
				head // 1
				tail // [2, 3, 4]

			let [x, y, ...z] = ['a'];
				x // "a"
				y // undefined
				z // []
	
	# 如果解构不成功,变量的值就等于undefined。
		* 以下两种情况都属于解构不成功,foo的值都会等于undefined。
			let [foo] = [];
			let [bar, foo] = [1];
	
	# 另一种情况是不完全解构,即等号左边的模式,只匹配一部分的等号右边的数组
		* 这种情况下,解构依然可以成功
			let [x, y] = [1, 2, 3];
			x // 1
			y // 2

			let [a, [b], d] = [1, [2, 3], 4];
			a // 1
			b // 2
			d // 4
	
	# 默认值
		* 允许使用 = 来设置默认值
			let [foo = true] = [];
			foo // true
		
			let [x, y = 'b'] = ['a']; 				// x='a', y='b'
			let [x, y = 'b'] = ['a', undefined]; 	// x='a', y='b'

		* ES6 内部使用严格相等运算符(===),判断一个位置是否有值
		* 所以,只有当一个数组成员严格等于undefined,默认值才会生效
			let [x = 1] = [undefined];
			x // 1

			let [x = 1] = [null];
			x // null

		* 如果默认值是一个表达式,那么这个表达式是惰性求值的,即只有在用到的时候,才会求值
			let func = function(){
				console.log(9);
			}
			//x 可以成功的被析构赋值,所以 func() 根本就不会执行
			let [x = func()] = [1]
			console.log(x);		//1
		

		* 默认值可以引用解构赋值的其他变量,但该变量必须已经声明
			let [x = 1, y = x] = [];     // x=1; y=1
			let [x = 1, y = x] = [2];    // x=2; y=2
			let [x = 1, y = x] = [1, 2]; // x=1; y=2
			let [x = y, y = 1] = [];     // ReferenceError: y is not defined

			* 最后一个会异常,因为 x = y的时候,y还没有声明
		
------------------------------------
对象的解构赋值						|
------------------------------------
	# 对象的解构赋值(把一个对象的属性值,赋值到变量)
		let {foo,bar,none} = {foo:"aaa",bar:"bbb"};
		foor		//aaa
		bar			//bbb
		none		//undefined

		* 对象的解构与数组有一个重要的不同,数组的元素是按次序排列的,变量的取值由它的位置决定
		* 而对象的属性没有次序,变量必须与属性同名,才能取到正确的值
		* 变量没有对应的同名属性(解构失败),等于undefined
	
	# 如果变量名与属性名不一致,必须写成下面这样
		//找一个名称叫做 foo的变量,把它的值赋给baz
		let { foo: baz } = { foo: 'aaa', bar: 'bbb' };
		baz // "aaa"
	
		let obj = { first: 'hello', last: 'world' };
		
		let { first: f, last: l } = obj;
		f // 'hello'
		l // 'world'

		* 也就是说,对象的解构赋值的内部机制,是先找到同名属性,然后再赋给对应的变量
		* 真正被赋值的是后者,而不是前者
			let { foo: baz } = { foo: "aaa", bar: "bbb" };
			baz // "aaa"
			foo // error: foo is not defined
			
			* 上面代码中，foo是匹配的模式,baz才是变量。真正被赋值的是变量baz,而不是模式foo
		
		* 那么对象解构赋值,如果匹配模式和变量名称一样,可以简写
			let {foo:foo,bar:bar,none:none} = {foo:"aaa",bar:"bbb"};
							↓(简写)
			let {foo,bar,none} = {foo:"aaa",bar:"bbb"};
	
	# 与数组一样,解构赋值也可以用于嵌套的对象
		let obj = {
		  p: [
			'Hello',
			{ y: 'World' }
		  ]
		};

		let { p: [x, { y }] } = obj;
		x // "Hello"
		y // "World"
	
		* 注意,这里的p,是一个模式,不是变量,如果p也要作为变量赋值
			let obj = {
				p: [
					'Hello',
					{ y: 'World' }
				]
			};

			let { p, p: [x, { y }] } = obj;
			x // "Hello"
			y // "World"
			p // ["Hello", {y: "World"}]
	
	# 再来一个嵌套例子
		let obj = {};
		let arr = [];
		({ foo: obj.prop, bar: arr[0] } = { foo: 123, bar: true });
		console.log(obj);		//{prop: 123}
		console.log(arr);		//[true]

	# 对象的解构也可以指定默认值
		var {x = 3} = {};
	    x // 3
	
	    var {x, y = 5} = {x: 1};
	    x // 1
	    y // 5
	
	    var {x: y = 3} = {};
	    y // 3
	
	    var {x: y = 3} = {x: 5};
	    y // 5
	
	    var { message: msg = 'Something went wrong' } = {};
	    msg // "Something went wrong"
	
		* 默认值生效的条件是,对象的属性值严格等于undefined(===)

			var {x = 3} = {x: undefined};
			x // 3

			var {x = 3} = {x: null};
			x // null
	
	# 如果解构模式是嵌套的对象,而且子对象所在的父属性不存在,那么将会报错	
		let {foo: {bar}} = {baz: 'baz'};		//alarm.html:173 Uncaught TypeError: Cannot destructure property `bar` of 'undefined' or 'null'.

		* 从{baz:'ba'}中获取一个叫做foo的属性对象,再从该对象里面获取 bar属性赋值给bar
		* 但 foor 属性根本不存在,值是 undefined,从undefined里面找出bar属性,就异常了
	
	# 如果要将一个已经声明的变量用于解构赋值,必须非常小心
			// 错误的写法
			let x;
			{x} = {x: 1};
			// SyntaxError: syntax error

		* JavaScript 引擎会将{x}理解成一个代码块,从而发生语法错误
		* 只有不将大括号写在行首,避免 JavaScript 将其解释为代码块,才能解决这个问题
			// 正确的写法
			let x;
			({x} = {x: 1});
	
	# 关于圆括号与解构赋值的关系
		({} = [true, false]);
		({} = 'abc');
		({} = []);
		
		* 上面的语法稀奇古怪,毫无意义,但是却是合法的
	
	# 对象的解构赋值,可以很方便地将现有对象的方法,赋值到某个变量
		let { log, sin, cos } = Math;
	
	# 由于数组本质是特殊的对象,因此可以对数组进行对象属性的解构。
		let arr = [1, 2, 3];
		//把第0个单位,赋值给first
		//把最后一个单位,赋值给last
		let {0 : first, [arr.length - 1] : last} = arr;
		first // 1
		last // 3

		* 上面代码对数组进行对象解构,数组arr的0键对应的值是1,[arr.length - 1]就是2键,对应的值是3
		* 方括号这种写法,属于"属性名表达式"(参见《对象的扩展》一章)		???

------------------------------------
字符串的解构赋值					|
------------------------------------
	# 字符串也可以解构赋值,因为字符串被转换成了一个类似数组的对象
		const [a, b, c, d, e] = 'hello';
		a // "h"
		b // "e"
		c // "l"
		d // "l"
		e // "o"
	
	# 类似数组的对象都有一个length属性,因此还可以对这个属性解构赋值。
		let {length : len} = 'hello';
		len // 5
	
------------------------------------
数值和布尔值的解构赋值				|
------------------------------------
	# 解构赋值时,如果等号右边是数值和布尔值,则会先转为对象
		let {toString: s} = 123;
		s === Number.prototype.toString // true

		let {toString: s} = true;
		s === Boolean.prototype.toString // true
	
	# 解构赋值的规则是,只要等号右边的值不是对象或数组,就先将其转为对象
	  由于undefined和null无法转为对象,所以对它们进行解构赋值,都会报错
		let { prop: x } = undefined; // TypeError
		let { prop: y } = null; // TypeError
	

------------------------------------
函数参数的解构赋值					|
------------------------------------
	# 函数的参数也可以使用解构赋值
		function add([x, y]){
		  return x + y;
		}

		add([1, 2]); // 3

		* 上面代码中,函数add的参数表面上是一个数组但在传入参数的那一刻,数组参数就被解构成变量x和y
		* 对于函数内部的代码来说,它们能感受到的参数就是x和y
	
	# 另一个例子,一看就懂
		let result = [[1, 2], [3, 4]].map(function([x,y]){return x + y});
        console.log(result);		// [3, 7]
	
	# 函数参数的解构也可以使用默认值
		function move({x = 0, y = 0} = {}) {
			return [x, y];
		}

		move({x: 3, y: 8}); // [3, 8]
		move({x: 3}); // [3, 0]
		move({}); // [0, 0]
		move(); // [0, 0]

		* move的参数是一个对象,通过对这个对象进行解构,得到变量x和y的值
		* 如果解构失败,x和y等于默认值
		* 下面的写法会得到不一样的结果
			function move({x, y} = { x: 0, y: 0 }) {
			  return [x, y];
			}

			move({x: 3, y: 8}); // [3, 8]
			move({x: 3}); // [3, undefined]
			move({}); // [undefined, undefined]
			move(); // [0, 0]

			* move的参数指定默认值,而不是为变量x和y指定默认值
			* 所以会得到与前一种写法不同的结果

	# undefined就会触发函数参数的默认值
		[1, undefined, 3].map((x = 'yes') => x);
		// [ 1, 'yes', 3 ]
	
	# 我的理解
		* 其实就是python里面的 **传参

		---------------------------------------------
		function test({name,age}={name:'Kevin',age:23}){
			console.log(name);		//Kevin
			console.log(age);		//23
		}
		test()
		* 很简单
		---------------------------------------------
		function test({name,age}){
			console.log(name);		//undefined
			console.log(age);		//undefined
		}
		test({})
		* 如果不传值那么就等于 {name,age} = undefined,就会抛出异常
		* 如果传了值之后,
		---------------------------------------------
		function test({name,age} = {name:'Kevin',age:23}){
			console.log(name);		//Litch
			console.log(age);		//undefined
		}
		test({name:'Litch'})
		* test()执行的时候传递的参数,会替代 {name:'Kevin',age:23} 它
		* 由于test()执行传递的参数没有age属性,所以age为 undefined
		---------------------------------------------
		function test({name:name,age:age = 27} = {name:'Kevin',age:23}){
			console.log(name);		//Litch
			console.log(age);		//27
		}
		test({name:'Litch'})
		* 同上,不过形参里面的age,已经具备了默认值了:27,所以在undefined的时候,会使用默认值
		---------------------------------------------


------------------------------------
圆括号的问题						|
------------------------------------
	# 解构赋值虽然很方便,但是解析起来并不容易,对于编译器来说,一个式子到底是模式,还是表达式,
	  没有办法从一开始就知道,必须解析到(或解析不到)等号才能知道

	# 由此带来的问题是,如果模式中出现圆括号怎么处理,ES6 的规则是,只要有可能导致解构的歧义,就不得使用圆括号

	# 但是,这条规则实际上不那么容易辨别,处理起来相当麻烦,因此,建议只要有可能,就不要在模式中放置圆括号

	# 不能使用圆括号的情况
		1,变量声明语句
			let [(a)] = [1];

			let {x: (c)} = {};
			let ({x: c}) = {};
			let {(x: c)} = {};
			let {(x): c} = {};

			let { o: ({ p: p }) } = { o: { p: 2 } };

			* 上面 6 个语句都会报错,因为它们都是变量声明语句,模式不能使用圆括号
	
		2,参数函数
			// 报错
			function f([(z)]) { return z; }
			// 报错
			function f([z,(x)]) { return x; }

			* 函数参数也属于变量声明,因此不能带有圆括号
	
		3,赋值语句模式
			// 全部报错
			({ p: a }) = { p: 42 };
			([a]) = [5];

			* 将整个模式放在圆括号之中，导致报错

			// 报错
			[({ p: a }), { x: c }] = [{}, {}];
			* 将一部分模式放在圆括号之中,导致报错
	
	# 可以使用圆括号的情况

		* 可以使用圆括号的情况只有一种:赋值语句的非模式部分,可以使用圆括号

		[(b)] = [3];				// 正确
		({ p: (d) } = {});			// 正确
		[(parseInt.prop)] = [3];	// 正确

		* 上面三行语句都可以正确执行,因为首先它们都是赋值语句,而不是声明语句
		* 其次它们的圆括号都不属于模式的一部分
			第一行语句中,模式是取数组的第一个成员,跟圆括号无关
			第二行语句中,模式是p,而不是d
			第三行语句与第一行语句的性质一致


------------------------------------
用途								|
------------------------------------
	1,变量值快速交换
		let x = 1;
		let y = 1;

		[x,y] = [y,x]
	
	2,从函数返回多个值
		function example() {
		  return [1, 2, 3];
		}
		let [a, b, c] = example();

		// 返回一个对象
		function example() {
			return {
				foo: 1,
				bar: 2
			};
		}
		let { foo, bar } = example();

		* 函数只能返回一个值,如果要返回多个值,只能将它们放在数组或对象里返回
		* 有了解构赋值,取出这些值就非常方便
	
	3,函数参数的定义
		// 参数是一组有次序的值
		function f([x, y, z]) { ... }
		f([1, 2, 3]);

		// 参数是一组无次序的值
		function f({x, y, z}) { ... }
		f({z: 3, y: 2, x: 1});

		* 解构赋值可以方便地将一组参数与变量名对应起来
	
	4,提取json数据
		
		let jsonData = {
		  id: 42,
		  status: "OK",
		  data: [867, 5309]
		};

		let { id, status, data: number } = jsonData;
		console.log(id, status, number);	// 42, "OK", [867, 5309]

		* 解构赋值对提取 JSON 对象中的数据,尤其有用
	
	5,函数参数的默认值
		jQuery.ajax = function (url, {
			async = true,
			beforeSend = function () {},
			cache = true,
			complete = function () {},
			crossDomain = false,
			global = true,
			// ... more config
		}) {
			// ... do stuff
		};

		* 预定义函数的一些默认值
		* 指定参数的默认值,就避免了在函数体内部再写var foo = config.foo || 'default foo';这样的语句
	
	6,遍历Map结构
		const map = new Map();
		map.set('first', 'hello');
		map.set('second', 'world');
		for (let entry of map) {
		  console.log(entry);
		}
		// ["first", "hello"]
		// ["second", "world"]
		for (let [key, value] of map) {
		  console.log(key + " is " + value);
		}
		//first is hello
		//index.html:20 second is world

		* 任何部署了 Iterator 接口的对象,都可以用for...of循环遍历
		* Map 结构原生支持 Iterator 接口,配合变量的解构赋值,获取键名和键值就非常方便
		* 如果只想获取键名,或者只想获取键值,可以写成下面这样
			// 获取键名
			for (let [key] of map) {
			  // ...
			}

			// 获取键值
			for (let [,value] of map) {
			  // ...
			}

	7,输入模块的指定方法

		const { SourceMapConsumer, SourceNode } = require("source-map");

		* 加载模块时,往往需要指定输入哪些方法
		* 解构赋值使得输入语句非常清晰
