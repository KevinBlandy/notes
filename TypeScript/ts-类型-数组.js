-------------------
数组
-------------------
	# TypeScript 数组有一个根本特征：所有成员的类型必须相同。

	# 指定数组元素的类型
		// 一种类型
		let arr:number[] = [1, 2, 3]

		// 多种类型
		let arr:(number|string)[] = [1, 2, 3]

		// 圆括号是必须的，否则因为竖杠 | 的优先级低于 []，TypeScript 会把 number | string [] 理解成 number 和 string[] 的联合类型。

		
	# 使用 TS 内置的 Array 接口（泛型）

		let arr:Array<number> = [1, 2, 3];

		let arr:Array<number|string>;
	

	# 类型推断
		* 如果数组变量没有声明类型，TypeScript 就会推断数组成员的类型。

		* 空数组，默认会推断数组类型是any[]
			// 推断为 any[]
			const arr = [];
		
			* 后续 push 数据到这个数组的时候，会因为数据不同而修改其推断 type

				// arr:number[]
				arr.push(1);

				// arr:(number|string)[]
				arr.push('Hi');
		
		* 如果不是空数组，则其类型推断不会随着数据的添加而更新
	
	# 只读数组
		* 在数组类型前面加上 readonly 关键字，表示声明只读数组。
		
			const arr:readonly number[] = [1, 2, 3]

			arr[1] = 12; // 报错
			arr.push(4); // 报错
			delete arr[2];// 报错
		
		* 只读数组，是数组的父类型

			* readonly number[] 与 number[] 是两种不一样的类型，后者是前者的子类型。
			* 因为只读数组没有 pop()、push() 之类会改变原数组的方法，所以 number[] 的方法数量要多于 readonly number[]。
			* 所以，把只读数组，当数组用可能会出问题

				function foo(arr:string[]){}
				const readOnlyArr:readonly string[] = ['1', '2']

				foo(readOnlyArr);  // 类型“readonly string[]”的参数不能赋给类型“string[]”的参数。


			* 可以使用强制转换，也就是断言来解决这个问题
				foo(readOnlyArr as string[]); 
			
		
		* readonly 关键字不能与数组的泛型写法一起使用

			const arr:readonly Array<number> = [1, 2, 3]  // 仅允许对数组和元组字面量类型使用 "readonly" 类型修饰符。
		
		* typeScript 提供了两个专门的泛型，用来生成只读数组的类型。

			// ReadonlyArray 数组，泛型是数组成员类型
			const a1:ReadonlyArray<number> = [0, 1];

			// Readonly 类型，泛型是数组
			const a2:Readonly<number[]> = [0, 1];
		
		* 只读数组还有一种声明方法，就是使用 “const 断言”。

			const arr = [0, 1] as const;

			arr[0] = [2]; // 报错 

	# 多维数组
		* 使用 T[][] 的形式，表示二维数组，T 是最底层数组成员的类型。
		
		// 最底层（存储数据的数组）的类型是 number
		var multi:number[][] =[[1,2,3], [23,24,25]];

	# 允许使用方括号读取数组成员的类型
		？

