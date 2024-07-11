---------------------
TS 类型
---------------------
	# 类型和值
		* TypeScript 继承了 JavaScript 的类型，在这个基础上，定义了一套自己的类型系统。
		* TypeScript 需要分清楚“值”（value）和“类型”（type）。
		* TypeScript 代码只涉及类型，不涉及值。所有跟“值”相关的处理，都由 JavaScript 完成。
		* TypeScript 的编译过程，实际上就是把 “类型代码” 全部拿掉，只保留 “值代码”。

	# TS 中的类型，大多数都和 JS 中的类型一一对应
		boolean
		string
		number
		bigint
		symbol
		object			// 包含了数组和对象
		undefined		// 只包含一个值 undefined
		null			// 只包含一个值 null

		* 有类型的名称都是小写字母，如果大写开头的话在 JS 中表示构造函数了。
		* undefined 和 null 既可以作为值，也可以作为类型，取决于在哪里使用它们。
		* 上面 8 种基本类型是 TypeScript 类型系统的基础，复杂类型由它们组合而成。
			枚举
			字面量
	

	# 类型声明
		* 在变量标识符后面加 :<type>

			// 变量的类型声明
			let title:string = "Hi";
			
			// 函数参数、返回值的类型声明
			function foo (param: number) :void {
			}
		
		* 大多数情况下，TS 可以自动推断，包括函数的返回值类型
		* 如果尝试修改变量值的类型，则会异常。
			title = 1;  // 不能将类型“number”分配给类型“string”

--------------------------------------
type 定义类型别名
--------------------------------------
	# type 命令用来定义一个类型的别名。
		// Age 类型，是 number 类型的别名
		type Age = number;
		
		// 给 Age 类型，赋值 number 类型，没问题
		let age:Age = 55;
	
	# 通过一个作用域下（块级），别名全局唯一，不能重复定义
		* 也就是说，块级内部的类型别名，不会影响块儿外面。

		if (true) {
		  // 不影响下面的 T
		  type T = number;
		  let v:T = 5;
		} else {
		  // 不影响上面的 T
		  type T = string;
		  let v:T = 'hello';
		}
	

	# 支持使用表达式
		* 可以在定义一个别名时，使用另一个别名，即别名允许嵌套。

		type World = "world";
		type Greeting = `hello ${World}`;

		var x: Greeting = 'hello world'

		x = 'ff' // 错误
	
	# type 命令属于类型相关的代码，编译成 JavaScript 的时候，会被全部删除。


--------------------------------------
typeof 运算符
--------------------------------------
	# JavaScript 里面，typeof运算符只可能返回八种结果，而且都是字符串。
		typeof undefined;	// "undefined"
		typeof true;		// "boolean"
		typeof 1337;		// "number"
		typeof "foo";		// "string"
		typeof {};			// "object"
		typeof parseInt;	// "function"
		typeof Symbol();	// "symbol"
		typeof 127n			// "bigint"
	
	# TypeScript 中的 typeof，返回的不是字符串，而是该值的 TypeScript 类型 type。

		const a = { x: 0 };

		type T0 = typeof a;   // { x: number }
		type T1 = typeof a.x; // number

		* typeof 返回的是 TypeScript 类型，所以只能用在类型运算之中（即跟类型相关的代码之中），不能用在值运算。

			console.log(T0);          // 错误：“T0”仅表示类型，但在此处却作为值使用。
	
	# 在同一段代码中，可能同时出现 TS 和 JS 的 typeof，需要注意判断

		let val = 'Hi';

		// TS 的 typeof 返回的是类型
		let foo: typeof val = 'Hello'
		
		// JS 的 typeof 返回的是字符串
		console.log(typeof foo); // string

		
	# 在编译后，TS 的 typeof 都会被擦除

		* 由于编译时不会进行 JavaScript 的值运算，所以 TypeScript 规定，typeof 的参数只能是标识符，不能是需要运算的表达式。

			type T = typeof 1;			// 报错
			type T = typeof Date();		// 报错

			let now = new Date();
			type T = typeof now; // Ok
		
		* typeof 命令的参数不能是类型
			type T = typeof number; // 报错


--------------------------------------
undefined 和 null
--------------------------------------
	# undefined 和 null 既是值，又是类型。

	# 没有声明类型的 undefined 和 null 值的类型，默认推断为 any
		* 如果没有声明类型的变量，被赋值为 undefined 或null，则会被推断为 any。

			let a = undefined;   // any
			const d = null;      // any
		
		* 可以打开编译选项 strictNullChecks
		* 值为 undefined 的变量会被推断为undefined类型，值为 null 的变量会被推断为 null 类型。
			let a = undefined;   // undefined
			const d = null;      // null
		
	# 任何其他类型的变量都可以赋值为 undefined 或 null。
		* 打开编译选项 strictNullChecks 以后，undefined 和 null 只能赋值给自身（不能互相赋值），或者 any 类型和 unknown 类型的变量。
	
			// 打开 strictNullChecks

			let x:undefined = null; // 报错
			let y:null = undefined; // 报错

			
			// --- Ok
			let x:any     = undefined;
			let y:unknown = null;

--------------------------------------
包装对象类型与基本类型
--------------------------------------
	# JS 中存在三种包装类型
		* 其实是 5 种，但是另外 2 种（Symbol 和 BigInt）无法直接获取它们的包装对象
		* 即，不能把 Symbol 和 BigInt 当作构造函数使用

		Boolean
		String
		Number
		
		// val 就是字符串 'Hi' 的包装对象
		let val = new String('Hi');
		console.log(typeof val);        // object   
		console.log(val.repeat(2));     // HiHi
		
	# 由于包装对象的存在，导致每一个原始类型的值都有 '包装对象' 和 ' 字面量' 两种情况
		* 为了区分这两种情况，TypeScript 对五种原始类型分别提供了大写和小写两种类型（建议只使用小写类型，不使用大写类型）。

			Boolean / boolean
			String / string
			Number / number
			BigInt / bigint
			Symbol / symbol
		
		* 包装类型，可以赋值为包装类型、基本类型（字面量也一样）
		* 基本类型，只能赋值为基本类型
		
			// ------ 1
			let val1:String = new String('Hi');
			let val2:string = 'Hello';

			console.log(typeof val1);       // object
			console.log(typeof val2);       // string


			// 包装类型，可以赋值为包装类型、字面量
			val1 = 'Hello';
			val1 = new String(val1.repeat(2));

			// 字面量类型，不能被赋值为包装类型
			val2 = new String('o huo');  // 不能将类型 “String” 分配给类型 “string”。

				
			// ------ 2
			let val:string = new String('HiHiHi');  // 不能将类型 “String” 分配给类型“string”。
		
	# 由于 Symbol() 和 BigInt() 不能当作构造函数使用，因此没法获取其包装类型
		* 但是，也有奇技淫巧，可以获取其包装类型
			let a = Object(Symbol());
			let b = Object(BigInt());
		
			// Symbol和BigInt这两个类型虽然存在，但是完全没有使用的理由。
		
		* 目前来说，TS 中 symbol 和 Symbol 两种写法没有差异，bigint 和 BigInt 也是如此
		* 建议始终使用小写的 symbol 和 bigint，不使用大写的 Symbol 和 BigInt。

--------------------------------------
Object 与 object
--------------------------------------
	# TypeScript 的对象类型也有大写 Object 和小写 object 两种。

	# 大写 的Object 类型代表 JavaScript 语言里面的广义对象。

		* 除了 undefined 和 null 这两个值不能转为对象，其他任何值都可以赋值给 Object 类型。

			let obj:Object;
				 
			obj = true;
			obj = 'hi';
			obj = 1;
			obj = { foo: 123 };
			obj = [1, 2];
			obj = (a:number) => a + 1;

			obj = null;				// 错误
			obj = undefined;		// 错误
		
		* 空对象 {} 是Object类型的简写形式，所以使用 Object 时常常用空对象代替。
			
			let obj:{}
		
	# 小写的 object 类型代表 JavaScript 里面的狭义对象
		* 即可以用字面量表示的对象，只包含对象、数组和函数，不包括原始类型的值。

			let obj:object;
	 
			obj = { foo: 123 };
			obj = [1, 2];
			obj = (a:number) => a + 1;
			obj = true; // 报错
			obj = 'hi'; // 报错
			obj = 1; // 报错
	
	# 无论是大写的 Object 类型，还是小写的object类型，都只包含 JavaScript 内置 Object 原生的属性和方法
		* 自定义的属性和方法都不存在于这两个类型之中。

			let obj:object = {a: 1}
			console.log(obj.a);  // 类型“object”上不存在属性“a”

			let val:Object = {a: 1}
			console.log(val.a); // 类型“Object”上不存在属性“a”。

			console.log(obj.toString());        // [object Object]
			console.log(val.toString());        // [object Object]
	
	# 建议总是使用小写类型 object，不使用大写类型Object

--------------------------------------
值类型
--------------------------------------
	# 单个值也是一种类型，称为“值类型”。
	
		* 类似于 const ，值是固定的

		let val:'Hi' = 'Hi'
		val = 'Hello';      // 不能将类型“"Hello"”分配给类型“"Hi"”。

			* 等号左边的 'Hi'，它不是一个值，而是一个类型。
	
	# 如果 const 变量，没有声明类型的话，那么 TS 就会推断其为当前的 “值类型”
		// val 的类型就是 'Hi'
		let val = 'Hi'
	
		
		* 注意，如果 const 的值是对象，则不会推断其类型为 “值类型”
			// x 的类型是 { foo: number }
			const x = { foo: 1 };
	
	# 注意值类型中的子父关系
		
		const x:5 = 4 + 1; // 报错

		* 5，这里的 5 其实是一个类型名称，就叫做 5。
		* 它是数值，所以 '继承了 number'，算是 number 的子类型（5 是 number 的子类型）。

		* 后面的 4 + 1，是标准的 number 值，也就是 number 类型
		* '把父类型赋值给子类型，会报错，反之不会'，有点像是多态

			// 父类型，赋值给子类型
			let x:5 = 4+1;  // 不能将类型“number”分配给类型 “5”。
			// 5 不是 number 数值，而是类型

			// 子类型，赋值给父类型
			let y:number = x;   // 没问题
		
		* 可以通过断言，转换子类型为可以赋值给父类型的值
			let x:5 = (4+1) as 5; // 注意，as 后的  5 是类型，不是值
		

--------------------------------------
类型兼容
--------------------------------------
	# TypeScript 的类型存在兼容关系，某些类型可以兼容其他类型。

		type NumberOrStr = string | number

		// x 的类型可以是字符串或者数值
		let x:NumberOrStr = 0xFF;

		console.log(x); // 255
		
		//y 的类型只能是字符串
		let y:string = 'Hi';

		// 可以把 y 赋值给 x
		x = y;

		console.log(x); // Hi

		* 可以把 y 理解为 x 的子类型（subtype）
	
	# TS 中，凡是可以使用父类型的地方，都可以使用子类型，但是反过来不行。

		type Color = 'red' | 'blue'
		let x:Color = 'blue';

		let y:'red' = 'red';        

		x = y;      // Ok

