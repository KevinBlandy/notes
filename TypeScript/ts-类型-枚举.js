-------------------------
enum 枚举
-------------------------
	# TypeScript 新增的一种数据结构和类型。
		
		// 有 Java 那味儿了
		enum Color {
			Red,     // 0
			Green,   // 1
			Blue     // 2
		}

		const color: Color = Color.Blue

		console.log(color);          // 2
		console.log(typeof color);  // number

		// 编译后的 JS 如下

		let Color = {
		  Red: 0,
		  Green: 1,
		  Blue: 2
		};
			
		
		* 一个成员的值默认为整数0，第二个为1，第三个为2，以此类推。
		* 可以通过 . 导航访问枚举，或者是 [] 访问
			
			// 类型可以推断
			const color  = Color['Blue']
		
		* 枚举变量，其类型可以枚举，也可以是 number
			const color: number  = Color['Blue']
		
		* Enum 成员值都是只读的，不能重新赋值。
		* 为了让这一点更醒目，通常会在 enum 关键字前面加上 const 修饰，表示这是常量，不能再次赋值（还可以提高性能）。

		* 官方建议谨慎使用 Enum 结构，因为它不仅仅是类型，还会为编译后的代码加入一个对象。
		* 由于 Enum 结构编译后是一个对象，所以不能有与它同名的变量（包括对象、函数、类等）。
			enum Color {
				Red,     // 0
				Green,   // 1
				Blue     // 2
			}

			const Color: any = 12;  // 错误
		
		* Enum 结构可以被对象的as const断言替代。

			const Color = {
				Red: 'red',         // 可以使用字符串作为枚举值
				Blud: 'blue',
				Pink: 'pink'
			} as const			// 使用 as const 断言，它的属性无法修改

			const color =   Color.Blud;			
		

	# Enum 成员的值
		* 枚举成员的默认值是从 0 开始的。
		* 也可以显示地给枚举成员赋值，成员的值可以是任意数值，但不能是大整数（Bigint）。

			enum Color {
				Red = 90,
				Pink = 90,			// 成员的值是相同的也可以
				Green = 0.5,		// 小数也可以
				Blue = 7n // 报错
			}
		
		* 如果只设定第一个成员的值，后面成员的值就会从这个值开始递增。
			enum Color {
				Red = 90,
				Green,
				Blue = 99,
				Pink,
			}

			console.log(Color.Green);       // 91
			console.log(Color.Pink);        // 100
		
		* Enum 成员的值也可以使用计算式。

			enum DataUnit {
				BYTE = 1 << 0,
				KB = 1 << 10,
				MB = 1 << 20,
				GB = 1 << 30,
			}


			for (let key in DataUnit){
				console.log(`${key} = ${DataUnit[key]}`);
			}

			/*
				BYTE = 1
				demo.ts:10 KB = 1024
				demo.ts:10 MB = 1048576
				demo.ts:10 GB = 1073741824
			*/
		
		* 不能使用表达式赋值
			enum MyEnum {
			  A = 'one',
			  B = ['T', 'w', 'o'].join('') // 报错
			}
				
	# 同名 Enum 的合并
		* 多个同名的 Enum 结构会自动合并。
		* Enum 结构合并时，只允许其中一个的首成员省略初始值，否则报错。

			enum Foo {
			  A,			// 允许首个成员忽略初始值
			}

			enum Foo {
			  B, // 报错
			}

		* 同名 Enum 合并时，不能有同名成员，否则报错。
		* 所有定义必须同为 const 枚举或者非 const 枚举，不允许混合使用。
	
	# 字符串枚举
		* Enum 也可以用作一组相关字符串的集合。

			enum Direction {
			  Up = 'UP',
			  Down = 'DOWN',
			  Left = 'LEFT',
			  Right = 'RIGHT',
			}
		
		* 注意，字符串枚举的所有成员值，都必须显式设置。
		* 如果没有设置，成员值默认为数值，且位置必须在字符串成员之前。

			enum Foo {
			  A, // 0
			  B = 'hello',
			  C // 报错，数值成员在字符串成员之后了
			}
		
			// Enum 成员可以是字符串和数值混合赋值。
		
		* 除了数值和字符串，Enum 成员不允许使用其他值（比如 Symbol 值）。
		
		* 变量类型如果是字符串 Enum，就不能再赋值为字符串，跟数值 Enum 不一样。

			enum Foo {
				Bar = 1,
				Zoo = 'Hi',
			}

			let f1 = Foo.Bar
			f1 = 1;  // Ok

			let f2 = Foo.Zoo
			f2 = 'Hi' // 错误
		
		* 所以，如果函数的参数类型是字符串 Enum，传参时就不能直接传入字符串，而要传入 Enum 成员。
		
			function func (_: Foo){}

			func(Foo.Zoo) // ok
			func('Hi') // 错误
		

	# keyof 运算符
		* keyof 运算符可以取出 Enum 结构的所有成员名，作为联合类型返回。
			enum Foo {
				Bar = 1,
				Zoo = 'Hi',
			}

			//  "Bar" | "Zoo"
			type T = keyof typeof Foo

			const t: T = 'Bar'
		
		* 可以使用in运算符返回 Enum 所有的成员值
			/*
				type T = {
					1: any;
					Hi: any;
				}
				
			*/
			type T =  { [key in Foo]: any };
		
	
	# 反向映射
		* 数值 Enum 存在反向映射，即可以通过成员值获得成员名。
			enum Weekdays {
				Monday = 1,
				Tuesday,
				Wednesday,
				Thursday,
				Friday,
				Saturday,
				Sunday
			}

			console.log(Weekdays[3]) // Wednesday
			console.log(typeof Weekdays[3]);  // string
