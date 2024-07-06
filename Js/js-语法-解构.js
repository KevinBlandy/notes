-----------------------
解构
-----------------------
	# 按照一定模式，从数组和对象中提取值，对变量进行赋值，这被称为解构（Destructuring）。

	# 函数的参数也可以使用解构赋值
		function add ([x, y]){
			return x + y;
		}
		console.log(add([1, 2]));  // 3


-----------------------
可遍历的结构
-----------------------
	# 如果等号的右边不是数组（或者严格地说，不是可遍历的结构）就会异常。
		* 事实上，只要某种数据结构具有 Iterator 接口，都可以采用数组形式的解构赋值。

	# 只要等号两边的模式（位置）相同，左边的变量就会被赋予对应的值。
		let [a, [b, c, [d, [e]]]] = [1, [2, 3, [4, [5]]]];
		console.log(a, b, c, d, e); //1 2 3 4 5
	
	# 常见的用法
		* 可以留空位
			let [a, ,b] = [1, 2, 3]
			console.log(a, b); // 1 3
		
		* 使用 ... 扩展符，解构剩余的元素到数组中（扩展符只能是最后一个元素）
			let [a, ...b] = [1, 2, 3]
			console.log(a, b); // 1 [2, 3]
		
		* 解构不成功的值为 undefined，数组为空数组
			let [a, b, ...c] = [1]
			console.log(a, b, c); // 1 undefined []

	# 默认值
		* 允许指定默认值
			let [a, b = 2] = [1]
			console.log(a, b); // 1 2
		
		* 内部使用 === 判断位置是否有值，只有成员 === undefined 时，才会用默认值
			let [a, b = 2, c = 3] = [1, undefined, null]
			console.log(a, b, c); // 1 2 null
		
		* 默认只可以用表达式，但表达式是惰性的，但是只有在 “成员为 undefined”，需要设置默认值的时候，才会进行计算
			const defaultVal = () => 2;
			let [a, b = defaultVal()] = [1]
			console.log(a, b); // 1 2
		
		* 可以引用其他的变量值，但是要求变量必须是已经声明了的
			const defaultVal = 2;
			let [a, b= defaultVal] = [1]
			console.log(a, b); // 1 2

-----------------------
对象
-----------------------
	# 类似于数组，但是位置无次序。变量必须与属性同名，才能取到正确的值。
		* 对象解构，可以解构到继承的属性。

	# 如果解构失败，则值为 undefined
		let {title, age} = {title: "foo"};
		console.log(title, age); // foo undefined
	
	# 变量和属性名称不一样，则需要明确指定名称
		let { title: myTitle, age: myAge } = {title: "foo"};
		console.log(myTitle, myAge); 
		// foo undefined

		* 匹配模式在前（变量名称）在前面，赋值的变量在后面。
	
	# 解构嵌套的对象
		const obj = {
			title: "我是 Js",
			langs: [{
				name: "java",
				site: "java.com"
			}, {
				name: "go",
				site: "golang.dev"
			}]
		}

		let {title, langs, langs: [{name}, {site}]} = obj;

		console.log(langs); // [...]
		console.log(name);  // java
		console.log(site);  // golang.dev
	
		* 如果嵌套的字段不存在，则会导致异常

			const obj = {
				foo : {
					val: "1",
				},
			}

			const {foo, foo: {val, notExists,	// 嵌套字段不存在，返回 undefined
				 notExists: {errorFiled}}  // 如果对不存在的字段，还继续进行结构，则会异常：TypeError: Cannot read properties of undefined (reading 'errorFiled')
			} = obj;
		
	
	# 可以指定默认值
		const obj = {
			foo: 1
		}

		const {foo, bar = 2, notExists: v = 12} = obj;
		console.log(foo);   // 1
		console.log(bar);   // 2
		console.log(v);     // 12

		* 同样，默认值生效的条件是解构的字段值为 undefined
	

	
	# 对已声明的变量进行解构会异常

		let x;
		{x} = {x: 'hi'};  // SyntaxError: Unexpected token '=' (at index.js:2:5)
		console.log(x);

		* 主要的原因是最开始的大括号 {}, 被当成了代码块，直接赋值给一个代码块是非法的。
		* 解决办法就是，式用小括号进行包裹（不要把大括号方在行首），让整个解构变成一个表达式。
			let x;
			({x} = {x: 'hi'}); 
			console.log(x);  // hi
		
		* 数组不存在这个问题，因为数组的 [] 没有歧义
			let x = 1, y = 2;
			[x, y ] = [y , x];
			console.log(x, y)  // 21
	
	
	# 数组也是特殊对象，因此可以用来当作对象解构
		let arr = [0, 1, 2, 3, 4];

		let {0: first, [arr.length-1]: last, length} = arr;

		console.log(first);     // 0
		console.log(last);      // 4
		console.log(length);    // 5
	
	# 对数值和bool值进行解构，会先把值转换为对象
		let {toString: s} = 123;
		s === Number.prototype.toString // true

		let {toString: s} = true;
		s === Boolean.prototype.toString // true
	
		* 由于 undefined 和 null 无法转为对象，所以对它们进行解构赋值，都会报错。
	
	# 支持扩展运算符
		* 解构赋值必须是最后一个参数，否则会报错。
			const {a, ...foo} = {a: 1, b: 2, c: 3};
				console.log(a);         // 1
				console.log(foo);       // {b: 2, c: 3}  // 扩展解构
