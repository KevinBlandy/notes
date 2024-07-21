--------------------------
any
--------------------------
	# 表示没有任何限制，该类型的变量可以赋予任意类型的值。
		let val:any
		val = 1;
		val = 'Hi';
		val = {};

		* 实际上，设置类型为 any 后，ts 就会关闭它的类型检查。基本上失去了使用 TypeScript 的意义。
	
	# 如果 TS 无法推断出变量的类型，则会认为该变量就是 any
		
		function foo(v1, v2){
			return v1 + v2;
		}
		console.log(foo(1, 2));         // 3
		console.log(foo('1', '2'));     // 12

		* v1，v2 参数以及 foo 的返回值都会推断为 any
	
	
	# 变量污染
		* 把 any 类型的变量赋值给其他类型，不会报错

			let anyVal:any = 'Hi'		// any 类型
			let intVal:number = 12;		// number 类型
		
			intVal = anyVal;			// 把 any 类型赋值给 number 类型

			console.log(intVal); // Hi
		
	
	# unknown 类型
		* 主要是为了解决 any 类型 “污染” 其他变量的问题，可以视为严格版的any。
			let anyVal:unknown = 'Hi'

		* 所有类型的值都可以分配给 unknown 类型，和 any 一样。
		* 但是有几个限制
			1. unknown 类型的变量，不能直接赋值给其他类型的变量（除了 any 类型和 unknown 类型）。
			2. 不能直接调用 unknown 类型变量的方法和属性。
			
				let obj:unknown = {foo: 'Hi', bar(){}}

				obj.bar();  // “obj” 的类型为“unknown”
				obj.foo;  // “obj” 的类型为“unknown”

				let func:unknown = () => {};
				func(); // “func”的类型为 “unknown”。
			
				let val:unknown = 'Hi';
				console.log(val);   // Hi
				val.trim();     // “val”的类型为 “unknown”。
		
			3. unknown类型变量能够进行的运算是有限的，除下面以外的运算符，都会报错
				==
				===
				!=
				!==
				||
				&&
				?
				!
				typeof
				instanceof

		* 范围缩小后，可以安全地使用 unknown
		* 简单来说，就是在代码中可以确定 unknown 类型的变量是某个具体类型的变量后，就可以当作某个类型的变量使。 
			
			// typeof 计算
			let val:unknown = ' Hi ';
			if (typeof val === 'string'){
				console.log(val.trim());  // Hi
			}

			// instanceof 计算
			class MyArray extends Array{
				constructor(val:number){
					super(val)
				}
			}
			
			const arr:unknown = new MyArray(3);
			console.log(arr.length);  // “arr”的 类型为 “unknown”。

			if (arr instanceof Array){
				console.log(arr.length);        // 3
			}

			// 使用 as 指定其类型
			let val:unknown;

			val = () => console.log('Hi');

			(val as Function)();


	# never 类型
		* “空类型” 的概念，即该类型为空，不包含任何值。
		* 不能赋给它任何值，否则都会报错。
			
			let x:never
			x = 1; // 不能将类型“number”分配给类型 “never”。
		
		
		* 例如，没有返回值的函数，就可以把返回值类型定义为 never
		* 联合类型中，处理所有可能的类型之后，剩余的情况就属于never类型。
			
			function fn(x:string|number) {
			  if (typeof x === 'string') {
				// ...
			  } else if (typeof x === 'number') {
				// ...
			  } else {
				x; // never 类型
			  }
			}
		
		* never，可以赋值给任意其他类型。
			function f():never {
			  throw new Error('Error');
			}

			let v1:number = f(); // 不报错
			let v2:string = f(); // 不报错
			let v3:boolean = f(); // 不报错

			* 认真感受这个方法，函数 f() 会抛出错误，不可能有任何返回值。
			* 所以返回值类型可以写成 never。
			* 而，never 可以赋值给其他任何变量。
		
		* never 类型可以赋值给任意其他类型呢？这也跟集合论有关，空集是任何集合的子集。
		* TypeScript 就相应规定，任何类型都包含了 never 类型。因此，never 类型是任何其他类型所共有的，TypeScript 把这种情况称为“底层类型”（bottom type）。
		* 总之，TypeScript 有两个 “顶层类型”（any和unknown），但是 “底层类型” 只有never唯一一个。
