----------------------
函数
----------------------
	# 需要在声明函数时，给出参数的类型和返回值的类型。

		function foo (param: string) :void {
			console.log(param);
		}
		
		* 如果返回值可以推断的话，则可以不写
			
			// 推断返回值类型为 string
			const foo = (param:string) =>  param.repeat(2)
			
			// 没有 return 则推断返回类型为 void
			const foo = () => {}
	
	# 变量类型的函数，有 2 种声明方式

		// 写法一，和 function 关键字类型
		const hello = function (txt: string) {
			console.log('hello ' + txt);
		}

		// 写法二，有点妖
		// :(txt: string) => void // 其实就是指定了函数的参数名称/类型、返回值类型
		const hello: (txt: string) => void = function (txt) {
			console.log('hello ' + txt);
		};

		* 写法二
			* 参数的类型写在箭头左侧，返回值的类型写在箭头右侧。
			* 函数的参数要放在圆括号里面，不放会报错。
			* 函数参数类型里面的参数名（上例是 txt）是必须的。

				type MyFunc = (string, number) => number;
				// 不写参数名称的话会被理解为：(string: any, number: any) => number

			* 函数参数类型里面的参数名与实际参数名，可以不一致。
				
				// 类型参数名称叫 arg
				type FUNC_TYPE = (arg:any) => number;

				let foo:FUNC_TYPE
				
				// 函数参数名称叫 num
				foo = (num) =>  {
					return Number(num);
				}

				console.log(foo('10086'));
		
			* 函数的实际参数个数，可以少于类型指定的参数个数，但是不能多
			* 即 TypeScript 允许省略参数。
				
				// 类型定义了 any 类型的 p 参数
				// 实际函数定义是没有参数的
				const foo:(p:any) => void  =  () => {};
		
	# 函数类型还可以采用对象的写法
		// add 变量的类型定义就是一个对象
		let add:{
		  (x:number, y:number): number
		}; 
		 
		add = function (x, y) {
		  return x + y;
		};
			
		* 对象方式的函数类型定义如下
			// 中间是 : 而不是  =>
			{
			  (参数列表): 返回值
			}
		
		* 这种写法平时很少用，但是非常合适用在一个场合：函数本身存在属性。

			function f(x:number) {
			  console.log(x);
			}
			
			// 万物皆对象，函数也是对象
			// 可以给函数设置属性
			f.version = '1.0';

			* f 完全就是一个对象，类型就要使用对象的写法。

				let foo: {
				  (x:number): void;		// 函数本身的参数和返回值类型
				  version: string		// 函数对象身上的属性和类型
				} = f;
	
	# 函数类型也可以使用 Interface 来声明
		* 就是对象写法的翻版
		
		// interface 命令定义了接口 Handler
		// 这个接口的类型就是一个用对象表示的函数。
		interface Handler {
			(req:any, resp:any) : void;
		}

		const action: Handler = (request, response) => {
			console.log(request, response);
		}

		action('', '');


	# 参数少的可以赋值给参数多，反之则不行
		let foo = (p1:string, p2:number) => {};
		let bar = (p1:string) => {};

		foo = bar   // Ok
		bar = foo  // 错误

		* 也好理解，参数可以少，但是不能多。
		* 但是也要保住参数类型的顺序、以及返回值类型是一致的
			let foo = (p1:number, p2:number) => {};
			let bar = (p1:string) => {};

			foo = bar   // 错误，foo 第一个类型是 number，bar 第一个类型是 string

		
	# TS 中 Function 类型表示函数，任何函数都属于这个类型。

		let foo:Function

		foo = () => {};
			foo = function(p:any){
			return p
		}
	

		* Function 类型的值都可以直接执行。
		* Function 类型的函数可以接受任意数量的参数，每个参数的类型都是any，返回值的类型也是any
		* 也就是说没有任何约束，所以不建议使用这个类型，给出函数详细的类型声明会更好。
	

	# 箭头函数
		* 类型写在箭头函数的定义里面，与使用箭头函数表示函数类型，写法有所不同。

			// 类型声明写在箭头函数的定义里面
			// 返回值类型，写在箭头左侧，参数括号后面
			const func = () :void =>  {}
			
			// 使用箭头函数表示函数类型
			// fn 的返回值类型要写在箭头右侧，而不是写在参数列表的圆括号后面。
			function greet(fn:(a:string) => void ) :void {
				fn('world');
			}
	

		* 看一个例子
			// 未给箭头函数定义类型
			const users = ['KK', 'LL', 'BB'].map((name) => {
				return {name}
			});

			console.log(JSON.stringify(users)); // [{"name":"KK"},{"name":"LL"},{"name":"BB"}]

			// 给箭头函数定义类型
			type User = {name: string}

			const users = ['KK', 'LL', 'BB'].map(
				// name:string 定义了参数的类型，其实这里的类型 :string 可以省略，可以从 map 方法的上下文推断出来
				// :User 定义了返回值类型，是 User 类型，即有 name: string 属性的对象
				// ({name}) 返回 User 类型的对象
				(name:string) :User => ({name}) 
			);

			console.log(JSON.stringify(users)); 
		
	# 可选参数
		* 如果参数可选，可以使用 ? 号修饰
			function foo (p?:number) :void {
				console.log(p);
			}
			foo()  // undefined
		
		* 可选参数只能是最后一个参数
			const func = (p1?:any, p2:number) => void {} // 错误，必选参数不能位于可选参数后。
	
	# 默认值
		* 基本上就是 JS 的语法
			const foo = (p = 'defaultVal', p1:boolean = false) => {}
		
		* 默认值参数可以推断出类型，所以可以省略类型参数。
		* 可选参数和默认值，不能一起用。
		* 如果默认值参数不是最后一个，那么不能省略，必须使用 undefined 来触发默认值。
	
	# 参数解构
		* 解构参数的类型声明如下
			function foo ([a, b, c]: [number, string, any]){}

			function bar ({name, title}: {name: string, title: any}){}
		
		* 可以使用 TYPE 指令来封装解构参数的类型

			type BAR_PARAMS_TYPE = {name: string, title: any}
			const func = ({name, title}: BAR_PARAMS_TYPE) => void console.log(name, title);
	
	# Rest 参数
		* 数组

			function rest (...args:string[]){
				console.log(args);
			}

			rest('a', 'b', 'c');  // ['a', 'b', 'c']
		
		* 元组，需要准确地声明出每个参数的类型

			function rest (...args:[string, number, boolean]){
				console.log(args);
			}


			// 参数不能多，也不能少，类型也必须要匹配
			rest('a', 1, true);  // ['a', 1, true]
		

		* 元组可以使用 ? 声明可选参数，但必须是最后一个

			const foo = (...args:[number, number, string?]) => void console.log(args);

			foo(1, 2, 'Hi');    // [1, 2, 'Hi']
			foo(1, 2);          // [1, 2]

		* rest 参数类型可以嵌套，rest 类型

			const foo = (p1: number, ...args: [string, ...number[]]) => {}
			foo(1, 'a', 1, 1, 1, 1, 1);
		
		* rest 参数可以与变量解构结合使用。
			function repeat(
				...[str, times]: [string, number]
			) :string {
				return str.repeat(times);
			}

			// 等同于
			function repeat(
				  str: string,
				  times: number
			) :string {
				return str.repeat(times);
			}
	
	# readonly 只读参数 
		* 在参数类型前面加上 readonly 关键字，表示这是只读参数。
		* 函数内部不能修改这个参数

			function foo (arr: readonly number[]){
				// OK
				console.log(arr[0]);
				// 错误
				arr[0] = 12;
			}
	
		* 注意，readonly 关键字目前只允许用在 '数组' 和 '元组' 类型的参数前面，如果用在其他类型的参数前面，就会报错。
	

	# void 返回值类型
		* void 类型表示函数没有返回值
			// TS 定义返回值类型为 void
			const func = () :void =>  {}
			console.log(func());
			
			// JS 也支持这种语法
			const foo = () => void {};
			console.log(foo());
		
		* void 类型的返回值可以返回 null/undefined，返回其他类型就会报错
			* 如果打开了strictNullChecks编译选项，那么 void 类型只允许返回 undefined。

		
		* void 类型只是表示该函数的返回值没有利用价值，或者说不应该使用该函数的返回值
		* 只要不用到返回值（使用返回值进行计算），就不会报错

			// void 类型
			type VOID_RETUNR = () => void

			// 有返回值
			const fun: VOID_RETUNR = () => 'Hi';

			// Ok
			console.log(fun());

			// 调用了返回值的方法
			// 类型“void”上不存在属性“repeat”。
			console.log(fun().repeat(3));
			

		* 除了函数，其他变量声明为 void 类型没有多大意义
		* 因为只能赋值为 undefined 或者 null（假定没有打开s trictNullChecks) 。


	# never 类型的返回值
		* 表示某个函数肯定不会返回值，即函数不会正常执行结束。
			
			// 抛出异常
			function thx() :never{
				throw new Error('e')
			}
			
			// 死循环执行
			const sing = function():never {
			  while (true) {
				console.log('sing');
			  }
			};

		* 如果函数确实有返回值，但是在某些情况下可能会抛出异常，则按照正常定义返回值类型即可
			function sometimesThrow():number { // 返回值类型为 number
			  if (Math.random() > 0.5) {
				return 100;
			  }
			  // 异常
			  throw new Error('Something went wrong');
			}

			const result = sometimesThrow();


			* never 是 TypeScript 的唯一一个底层类型，所有其他类型都包括了never。
			* 所以，number|never 等同于number。这就是说，函数的返回值无论是什么类型，都可能包含了抛出错误的情况。
		
	
	# 重载
		* TypeScript 对于“函数重载”的类型声明方法是，逐一定义每一种情况的类型。

			// 类型描述：声明函的重载类型 1
			function reverse(str:string):string;
			// 类型描述：声明函的重载类型 2
			function reverse(items:any[]):any[];
			// 函数的具体实现
			function reverse(arg: string | any[]) :string | any[] {
				if (typeof arg === 'string'){
					return arg.split('').reverse().join(',');
				}
				return arg.slice().reverse();
			}

			console.log(reverse('123'))             // 3,2,1
			console.log(reverse(['a', 'b', 'c']))   // ['c', 'b', 'a']

		
		* 把可能出现的参数类型、返回值类型都定义在函数的具体实现中。
		* 重载的各个类型描述与函数的具体实现之间，不能有其他代码，否则报错。

		* 函数的具体实现里面，有完整的类型声明。但是，函数实际调用的类型，以前面的类型声明为准。
	
		* 函数重载的每个类型声明之间，以及类型声明与函数实现的类型之间，不能有冲突。
		* 类型最宽的声明应该放在最后面，防止覆盖其他类型声明。
		
		* 对象的方法也可以使用重载。
		* 函数重载，也可以用对象表示。

		* 少用，没啥卵用。
	
	# 构造函数
		* 构造函数的类型写法，就是在参数列表前面加上new命令。
			
			// 构造函数
			class Animal {
			  numLegs:number = 4;
			}

			// 构造函数类型
			type AnimalConstructor = new () => Animal;

			function create(c:AnimalConstructor):Animal {
			  return new c();
			}

			const a = create(Animal);
		
		* 构造函数可以采用对象形式
			//  F 就是构造函数类型
			type F = {
			  // 在参数列表前加上 new
			  new (s:string): object;
			};
		
		* 既是构造函数，又可以当作普通函数使用，比如Date()
			type F = {
			  // 构造函数
			  new (s:string): object;
			  // 当作普通函数
			  (n?:number): number;
			}
		
					
