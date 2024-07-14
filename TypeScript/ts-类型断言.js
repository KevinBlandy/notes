---------------------
类型断言
---------------------
	# TS 提供了“类型断言”这样一种手段，允许开发者在代码中“断言”某个值的类型，告诉编译器此处的值是什么类型。
		* TS 一旦发现存在类型断言，就不再对该值进行类型推断，而是直接采用断言给出的类型。
		* 这样虽然削弱了 TS 类型系统的严格性，但是为开发者带来了方便，毕竟开发者比编译器更了解自己的代码。

		* 总之，类型断言并不是真的改变一个值的类型，而是提示编译器，应该如何处理这个值。
	
	
	# 类型断言有两种语法。
		// 语法一：<类型>值
		<Type> value

		// 语法二：值 as 类型，推荐使用
		value as Type

		// 语法一
		let bar:T = <T>foo;

		// 语法二
		let bar:T = foo as T;
	
		
		* 用例

			// 正确，将类型改成与等号左边一致
			const p0:{ x: number } =
			  { x: 0, y: 0 } as { x: number };

			// 正确，断言使得等号右边的类型是左边类型的子类型，子类型可以赋值给父类型
			// 同时因为存在类型断言，就没有严格字面量检查了，所以不报错。
			const p1:{ x: number } =
			  { x: 0, y: 0 } as { x: number; y: number };
			
	
	# 类型断言不应滥用
		* 它改变了 TypeScript 的类型检查，很可能埋下错误的隐患。
		* 类型断言可以让错误的代码通过编译。
	
		* 类型断言的一大用处是，指定 unknown 类型的变量的具体类型。

			const value:unknown = 'Hello World';
	
			const s1:string = value; // 报错

			// unknown 类型的变量 value 不能直接赋值给其他类型的变量，但是可以将它断言为其他类型，这样就可以赋值给别的变量了。
			const s2:string = value as string; // 正确
	
	# 类型断言的条件
		* 类型断言的使用前提是，值的 '实际类型' 与 '断言的类型' 必须满足一个条件。

			expr as T

			// expr是实际的值，T是类型断言，它们必须满足下面的条件：
				// expr是T的子类型
				// 或者T是expr的子类型。
		
		* 类型断言要求实际的类型与断言的类型兼容
			* 实际类型可以断言为一个更加宽泛的类型（父类型），也可以断言为一个更加精确的类型（子类型）
			* 但不能断言为一个完全无关的类型。
		

		* 果真的要断言成一个完全无关的类型，也是可以做到的。
			// 或者写成 <T><unknown>expr
			expr as unknown as T

			// expr连续进行了两次类型断言，第一次断言为unknown类型，
			// 第二次断言为T类型。这样的话，expr就可以断言成任意类型T，而不报错。
		
	
	# as const 断言
		* 如果没有声明变量类型
			
			* let 命令声明的变量，会被类型推断为 TypeScript 内置的基本类型之一。
			* const 命令声明的变量，则被推断为值类型常量。
			
			// 类型推断为基本类型 string
			let s1 = 'JavaScript';

			// 类型推断为字符串 "JavaScript"
			// 
			const s2 = 'JavaScript';
		
		* TS 提供了一种特殊的类型断言 as const，用于告诉编译器，推断类型时，可以将这个值推断为常量
		* 即把 let 变量断言为 const 变量，从而把内置的基本类型变更为值类型。

			type color = 'red' | 'blud'

			function foo(_: color){}

			let val = 'red';
			foo(val);		// 错误

			// 变量使用 as const 断言，即可
			let val = 'red' as const;
		
		* 使用了as const断言以后，let 变量就不能再改变值了。
		* as const断言只能用于字面量，不能用于变量。
			let s = 'JavaScript';
			setLang(s as const); // 报错
		
		* as const也不能用于表达式。
			let s = ('Java' + 'Script') as const; // 报错
		
		* as const也可以写成前置的形式。
			// 后置形式
			expr as const

			// 前置形式
			<const>expr
		
		* as const断言可以用于整个对象，也可以用于对象的单个属性，这时它的类型缩小效果是不一样的。

			const v1 = {
				x: 1,
				y: 2,
			}; // 类型是 { x: number; y: number; }

			const v2 = {
				x: 1 as const,  // 变成常量了
				y: 2,
			}; // 类型是 { x: 1; y: number; }

			const v3 = {
				x: 1,
				y: 2,
			} as const; // 类型是 { readonly x: 1; readonly y: 2; }
	
		* 总之，as const会将字面量的类型断言为不可变类型，缩小成 TS 允许的最小类型。

		
			// a1 的类型推断为 number[]
			const a1 = [1, 2, 3];

			// a2 的类型推断为 readonly [1, 2, 3]
			// 变成了只读元组
			const a2 = [1, 2, 3] as const;
		
			* 很适合用于函数的 rest 参数。
			
				function foo (x: number, y: number){}

				let arr = [1, 2]
				foo(...arr); // 错误

				let constArr = [1, 2] as const
				foo(...constArr);  // Ok
			
		* Enum 成员也可以使用as const断言。
			enum Foo {
				X,
				Y,
			}
			let e1 = Foo.X;            // 类型为 Foo
			let e2 = Foo.X as const;   // 类型为 Foo.X
			

			// 变量e2的类型被推断为 Enum 的某个成员，这意味着它不能变更为其他成员。
		
	
	# 非空断言
		* 对于那些可能为空的变量（即可能等于undefined或null），TS 提供了非空断言
			* 变量名后面加上感叹号!，保证这些变量不会为空
		
		* '只有在确定一个表达式的值不为空时才能使用'。
		
		* 非空断言还是比较有用
		
			// getElementById()有可能返回空值null，即变量root可能为空
			const root = document.getElementById('root');

			// 编译报错
			root.addEventListener('click', e => {
			  /* ... */
			});
			

			// 使用非空断言的话，就可以
			const root = document.getElementById('root')!;
		
			// 声明在变量使用时也可以
			root!.addEventListener('hover', e => {
				console.log(e);
			});
	
		* 非空断言还可以用于赋值断言
			// TypeScript 有一个编译设置，要求类的属性必须初始化（即有初始值），如果不对属性赋值就会报错
			class Point {
			  x:number; // 未赋值，报错
			  y:number; // 未赋值，报错

			  constructor(x:number, y:number) {
				// ...
			  }
			}

			// 使用非空断言，表示这两个属性肯定会有值，这样就不会报错了。
			class Point {
			  x!:number; // 正确
			  y!:number; // 正确

			  constructor(x:number, y:number) {
				// ...
			  }
			}

	
	# 断言函数 
		* 断言函数是一种特殊函数，用于保证函数参数符合某种类型（不符合的话就会抛出异常）。

			function isString(value: unknown): asserts value is string {
				if (typeof value !== 'string')
					throw new Error('Not a string');
			}

			// asserts value is string 其中asserts和is都是关键词，value是函数的参数名，string 是函数参数的预期类型。
			// 该函数用来断言参数value的类型是string，如果达不到要求，程序就会在这里中断。
		
		* 函数返回值的断言写法，只是用来更清晰地表达函数意图，'真正的检查是需要开发者自己部署的'
			
			// 下面的代码也可以通过编译
			function isString(value:unknown):asserts value is string {
			  // 实际判断的类型是 number
			  if (typeof value !== 'number')
				throw new Error('Not a number');
			}
		
			// 示 TypeScript 并不会检查断言与实际的类型检查是否一致。
		

		* 断言函数的 asserts 语句等同于 void 类型，所以如果返回除了undefined和null以外的值，都会报错。
	
		* 如果要断言参数非空，可以使用工具类型 NonNullable<T>
			function assertIsDefined<T>(
				value: T
			): asserts value is NonNullable<T> {
				if (value === undefined || value === null) {
					throw new Error(`${value} is not defined`)
				}
			}
					
			// 工具类型 NonNullable<T> 对应类型T去除空类型后的剩余类型。



