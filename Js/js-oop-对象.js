---------------------
对象
---------------------


----------------------
Object 上操作对象的方法
----------------------

	# Object.is() 对象比较
		* ES5 比较两个值是否相等，只有两个运算符（== 和 ===）
		* 前者会自动转换数据类型，后者的 NaN 不等于自身，以及+0等于-0
		
		* Object.is 与严格比较运算符（===）的行为基本一致
			
			Object.is('foo', 'foo')
			// true
			Object.is({}, {})
			// false
		
		* 不同之处只有两个:+0不等于-0，NaN 等于自身

			+0 === -0 		//true
			NaN === NaN 	// false
		
			Object.is(+0, -0) 	// false
			Object.is(NaN, NaN) // true
	
	# Object.assign(target, ...sources) 合并对象属性
		* 将源对象（source）的所有可枚举、非继承属性复制到目标对象（target），浅拷贝。
			const target = { a: 1 };
			const source1 = { b: 2 };
			const source2 = { c: 3 };
			
			Object.assign(target, source1, source2);
			console.log(target);	// {a:1, b:2, c:3}

		* '如果目标对象与源对象有同名属性，或多个源对象有同名属性，则后面的属性会覆盖前面的属性'
		* '如果只有一个参数，Object.assign 会直接返回该参数'
		* 如果该参数不是对象,则会先转成对象,然后返回
			typeof Object.assign(2) // "object"

		* 由于undefined和null无法转成对象,所以如果它们作为参数,就会报错
			Object.assign(undefined) // 报错
			Object.assign(null) // 报错
	
		* 这些参数都会转成对象，如果无法转成对象，就会跳过，这意味着，如果 undefined 和 null 不在首参数，就不会报错
			let obj = {a: 1};
			Object.assign(obj, undefined) === obj	// true
			Object.assign(obj, null) === obj		// true
		
		* 其他类型的值(即数值,字符串和布尔值)不在首参数，也不会报错
		* 但是，除了字符串会以数组形式，拷贝入目标对象，其他值都不会产生效果
			const v1 = 'abc';
			const v2 = true;
			const v3 = 10;
			
			const obj = Object.assign({}, v1, v2, v3);
			console.log(obj); // { "0": "a", "1": "b", "2": "c" }

			* 上面代码中,布尔值,数值,字符串分别转成对应的包装对象
			* 可以看到它们的原始值都在包装对象的内部属性[[PrimitiveValue]]上面,这个属性是不会被Object.assign拷贝的
			* 只有字符串的包装对象,会产生可枚举的实义属性,那些属性则会被拷贝

		* Symbol 属性,也会被拷贝
			Object.assign({ a: 'b' }, { [Symbol('c')]: 'd' })
			// { a: 'b', Symbol(c): 'd' }
		
		* 对于嵌套的对象，遇到同名属性，Object.assign的处理方法是全量替换
			const target = { a: { b: 'c', d: 'e' } };
			const source = { a: { b: 'hello' } };
			Object.assign(target, source);
			console.log(target);// { a: { b: 'hello' } }

			* target对象的a属性被source对象的a属性整个替换掉了
		
		* Object.assign可以用来处理数组,但是会把数组视为对象（视为属性名为 0,1,2... 的对象）
			Object.assign([1, 2, 3], [4, 5]);  // 源数组的 0 号属性4覆盖了目标数组的 0 号属性1
			// [4, 5, 3]

		* Object.assign 只能进行值的复制，如果要复制的值是一个取值函数,那么将求值后再复制
			const source = {
				get foo() { return 1 }
			};
			const target = {};
			
			Object.assign(target, source)// { foo: 1 }


---------------------
对象属性
---------------------
	# 属性的简洁表示法
	
		* 属性名为变量名属性值为变量的值

			const foo = 'bar';
			const baz = {foo};
			console.log(baz); // {foo: "bar"}
		
			// 等同于
			const baz = {foo: foo};	
		
		* 简洁写法的属性名总是字符串

			const obj = {class () {}};
			// 等同于
			var obj = {
				'class': function() {}
			};
		
		* 如果某个方法的值是一个 Generator 函数,前面需要加上星号
			const obj = {
				* m() {
					yield 'hello world';
				}
			};
	
	# 属性名表达式
		* 如果属性名称是表达式，需要定义在方框之中

			let propKey = 'foo';
			let obj = {
				[propKey]: true,		// foo:true
				['a' + 'bc']: 123		// abc:123
			};
			console.log(obj);		//{foo: true, abc: 123}

			* [] 会把表达式计算出来的结果作为属性名称
			* 方法名称也可以用表达式
		
		* 表达式如果是一个对象,默认情况下会自动将对象转为字符串'[object Object]'


	# 属性名表达式与简洁表示法,不能同时使用,会报错

		// 报错
		const foo = 'bar';
		const bar = 'abc';
		const baz = { [foo] };

		// 正确
		const foo = 'bar';
		const baz = { [foo]: 'abc'};
	

	# 可枚举
		* 对象的每个属性都有一个描述对象（Descriptor），用来控制该属性的行为
		* Object.getOwnPropertyDescriptor 方法可以获取该属性的描述对象
		* 描述对象的 enumerable 属性,称为"可枚举性",如果该属性为false，就表示某些操作会忽略当前属性
			for...in循环	：只遍历对象自身的和继承的可枚举的属性
			Object.keys()	：返回对象自身的所有可枚举的属性的键名
			JSON.stringify()：只串行化对象自身的可枚举的属性
			Object.assign()	：忽略 enumerable 为 false 的属性，只拷贝对象自身的可枚举
			
			Object.getOwnPropertyDescriptor(Object.prototype, 'toString').enumerable // false
			Object.getOwnPropertyDescriptor([], 'length').enumerable	// false
			

		* 另外，ES6 规定,所有 Class 的原型的方法都是不可枚举的
			Object.getOwnPropertyDescriptor(class {foo() {}}.prototype, 'foo').enumerable 	// false
		

		* 总的来说，操作中引入继承的属性会让问题复杂化，大多数时候,我们只关心对象自身的属性
		 * 尽量不要用 for...in 循环，而用 Object.keys() 代替
		 
	
	# 可遍历属性，ES6 一共有 5 种方法可以遍历对象的属性
		1. for...in // 所有，不含 Symbol
			* for...in循环遍历对象自身的和继承的可枚举属性（不含 Symbol 属性）

		2. Object.keys(obj) // 自身、可枚举、不含 Symbol
			* Object.keys返回一个数组，包括对象自身的（不含继承的）所有可枚举属性（不含 Symbol 属性）的键名

		3. Object.getOwnPropertyNames(obj) // 自身、不含 Symbol
			* Object.getOwnPropertyNames返回一个数组，包含对象自身的所有属性（不含 Symbol 属性，但是包括不可枚举属性）的键名

		4. Object.getOwnPropertySymbols(obj) // 自身、Symbol
			* Object.getOwnPropertySymbols返回一个数组，包含对象自身的所有 Symbol 属性的键名

		5. Reflect.ownKeys(obj)			// 所有
			* Reflect.ownKeys 返回一个数组，包含对象自身的所有键名，不管键名是 Symbol 或字符串，也不管是否可枚举

		* 以上的 5 种方法遍历对象的键名，都遵守同样的属性遍历的次序规则
			1. 首先遍历所有数值键,按照数值升序排列
			2. 其次遍历所有字符串键,按照加入时间升序排列
			3. 最后遍历所有 Symbol 键，按照加入时间升序排列
		
			Reflect.ownKeys({ [Symbol()]:0, b:0, 10:0, 2:0, a:0 })
			// ['2', '10', 'b', 'a', Symbol()]
			
			// Reflect.ownKeys方法返回一个数组,包含了参数对象的所有属性
			// 这个数组的属性次序是这样的，首先是数值属性2和10，其次是字符串属性b和a,最后是 Symbol 属性

	# 扩展运算符
		* 类似于数组的扩展运算符
		* 用于从一个对象取值，相当于将目标对象自身的所有可遍历的（enumerable）、但尚未被读取的属性，分配到指定的对象上面。
		* 所有的 KEY 和它们的值，都会拷贝到新对象上面。
			const {a, ...foo} = {a: 1, b: 2, c: 3};
			console.log(a);         // 1
			console.log(foo);       // {b: 2, c: 3}  // 扩展解构
		
	

		

