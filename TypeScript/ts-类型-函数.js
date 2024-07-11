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
