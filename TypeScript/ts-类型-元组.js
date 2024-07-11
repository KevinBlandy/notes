------------------------
元组
------------------------
	# TypeScript 特有的数据类型

		* 元组必须明确声明每个成员的类型

			var arr:[string, number, object] = ['1', 12, {}]
		
		* '成员类型写在方括号里面的就是元组，写在外面的就是数组'。
	
		* 元组的类型不能省略，否则 TS 会推断为某种类型的数组

			// a 的类型被推断为 (number | boolean)[]
			let a = [1, true];
		
		* 由于元组的成员是固定不变的，越界访问会报错
	
	# 可选成员
		* 元组成员的类型可以添加问号后缀（?），表示该成员是可选的。
		* 注意，只能加在最后一个成员上

			var arr:[string, number, object?] = ['1', 12]
		

	# 可以使用扩展运算符创建不限长度的元组
		
		type T_ARR = [string, number, ...string[]]
		var arr:T_ARR = ['1', 12, '3', '4', '5', '6']
		console.log(arr);

		* 扩展运算符（...）用在元组的任意位置都可以，它的后面只能是一个数组或元组。

			type t1 = [string, number, ...boolean[]];
			type t2 = [string, ...boolean[], number];
			type t3 = [...boolean[], string, number];


			// 不确定元组的成员类型和数量
			// 但是这样写，就失去了元组本身的意义
			type Tuple = [...any[]];
		
	
	# 成员命名
		* 可以给元组的成员命名，除了说明别无他用
			
			// 命名: 类型
			type T_RESULT = [name: string, age: number]

			let arr:T_RESULT = ['K', 29]

			const [name, age] = arr

			console.log(name, age); // K 29
	
	# 只读元组
		* 类似于只读数组，三种写法
			// readonly 声明
			type T_RESULT = readonly [name: string, age: number]
			let arr: T_RESULT = ['K', 29]

			// 泛型声明
			type t = Readonly<[number, string]>
			
			// 只读数组，也可以当作是元组
			let point = [3, 4] as const;
		
		* 跟数组一样，只读元组是元组的父类型，必要的情况下需要使用强制转换

			function foo ([x, y]: [number, number]){
				return x + y
			}
			function bar (arr:[number, number]){
				return arr[0] + arr[1];
			}

			const tuple = [1, 2] as const;

			console.log(foo(tuple))  // 报错 类型“readonly [1, 2]”的参数不能赋给类型“[number, number]”的参数。
			console.log(bar(tuple as [number, number]))     // 3

	
	# 成员数量的问题
		* 可以通过 length 来获取到元组成员的数量
		* TypeScript 也会推断出元组的成员数量（即元组长度），如果对元组的 length 属性进行了错误的判断，则会错误i

			function bar (arr:[number, number]){
				if( arr.length === 2){		// Ok
				}
				if( arr.length === 3){ // 错误
				}
			}
			
			// 包含了可选成员
			function bar (arr:[number, number?]){
				if( arr.length === 1){ // OK
				}
				if( arr.length === 2){  // Ok
				}
				if( arr.length === 3){ // 错误
				}
			}
		
		* 如果元组使用了扩展运算符，则不能计算出元组的实际长度

			// 这种情况下，length 写啥都行
			function bar (arr:[number, ...number[]]){
				if (arr.length === 1){

				}
				if (arr.length === 10010){

				}
			}
		
		* 使用扩展运算符解构数组的时候，TS 会认为这个数组的成员数量不能确定
		* 因此，使用扩展运算符传入函数参数，可能会导致形参和实参数量不一致的问题

			function bar (x: number, y: number){
				console.log(x, y)
			}

			const args = [1, 2]

			bar(...args); // 报错，TS 认为 args 数组的数量是不确定的。
			bar(...[1, 2]) //  OK，字面量是 Ok 的，它的长度固定

		* 可以把形参定义元组，这样长度就确定了，就可以调用
			
			// 元组长度是确定的
			const args:[number, number] = [1, 2]
			bar(...args); // Ok

			// 这种写法也可以
			const args = [1, 2] as const;


	# 元组可以通过方括号，读取成员类型
		？
	