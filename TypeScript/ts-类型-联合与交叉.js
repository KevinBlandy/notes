--------------------------
联合类型
--------------------------
	# 联合类型（union types）指的是多个类型组成的一个新类型，使用符号 | 表示。

		let x :string|number;  // 既可以是字符串、也可以是 number

		x = '1';		// ok
		x = 0xFF;		// ok
	
	
	# 可以和值类型结合，类似于枚举
		// 竖杠方在第一个成员前面也中
		let color: | 'blue' | 'red' | 'pink' | boolean = 'blue';

		color = 'pink';
		color = true
		console.log(typeof color);  // boolean
		color = 123  // 不能将类型“123”分配给类型“boolean | "blue" | "red" | "pink"”。
	
		
	# 对于有多种类型联合起来的类型，在读取该变量的时候，需要类型缩小。
		
		* 可能存在多种类型的变量，不能直接调用某个类型的方法
		
			function foo(id: number|string){
				id.toUpperCase(); //  错误：类型“number”上不存在属性“toUpperCase”。
			}
		
		* 需要进行类型判断
		
			function foo(id: number|string){
				if (typeof id === 'number'){

				} else if (typeof id === 'string'){
					id.toUpperCase(); // Ok
				} else {
					// ... 其他
				}
			}
		
	
		
--------------------------
交叉类型
--------------------------
	# 指的多个类型组成的一个新类型，使用符号 & 表示。

	# 交叉类型的主要用途是表示对象的合成
		
		* 表示某个类型，具有多个对象属性的并集
		
			let obj: {name: string} & {age: number}

			obj = {
				name: "KK",
				age: 34,
			}
	
		* 还可以用来为对象类型添加新属性

			type A = { foo: number };

			type B = A & { bar: number };
	
	# 对于基本类型，基本用不上，用了也不能用
		
		let x:number & string;
		
		* 不可能有一个对象又是 number 又是 string
		* 所以，x 其实是一个 never 类型

			let x: never
	
