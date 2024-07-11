-----------------------
symbol
-----------------------
	# 每一个 Symbol 值都是独一无二的，与其他任何值都不相等。
		console.log(Symbol() === Symbol());  // false
	
	# unique symbol
		* TS 中的 symbol 类型表示所有 Symbol 类型的值，但是无法表示某一个具体的 Symbol 值。
		* TS 设计了 symbol 的一个子类型 unique symbol，表示单个的、某个具体的 Symbol 值。
		* unique symbol 表示单个值，且不能修改，所以只能用const命令声明，不能用let声明。

			const val:unique symbol = Symbol('unique');
		
		* const 命令为变量赋值 Symbol 值时，变量类型默认就是 unique symbol

			// 可以省略类型
			const val = Symbol('unique');
		
		* 每个 unique symbol 都是不相等的，所以不能相互赋值
			
			const a:unique symbol = Symbol();
			const b:unique symbol = a; // 错误，不能将类型“typeof a”分配给类型“typeof b”。
		
			// 硬要的话，只能这样
			const a:unique symbol = Symbol();
			const b:typeof a = a; // Ok

			console.log(a === b); // true

		* 相同参数的 Symbol.for() 方法会返回相同的 Symbol 值
			
			* TS 目前无法识别这种情况，所以可能出现多个 unique symbol 类型的变量等于同一个 Symbol 值的情况。

			const a:unique symbol = Symbol.for('foo');
			const b:unique symbol = Symbol.for('foo');
		
			// 这里会报错，但是输出的结果仍然是 true
			console.log(a === b); // true
		
		* 简单地理解 'unique symbol' 表示这个类型（'unique symbol'）的 symbol 仅此一个，就是初始化的这个。
		
	# unique symbol 类型是 symbol 类型的子类型
		* 可以将前者赋值给后者，但是反过来就不行。
	
			const a:unique symbol = Symbol();
			const b:symbol = a; // Ok
			const c:unique symbol = b; // 报错
	
	# unique symbol 类型的一个作用，就是用作属性名
		* 这可以保证不会跟其他属性名冲突

			const attr:unique symbol = Symbol('foo');
			const prop:symbol = Symbol('bar');		// 非 unique symbol 类型
			const action = Symbol('Zoo');

			interface Obj {
				[attr]: any,        // Ok
				[prop]: any         // 错误 接口中的计算属性名称必须引用必须引用类型为文本类型或 "unique symbol" 的表达式。
				[action]: () => {}  // Ok
			}
	
	# unique symbol类型也可以用作类（class）的属性值
		* 但只能赋值给类的 readonly static 属性。

			class Foo {
				static readonly foo:unique symbol = Symbol('CLASS STATIC')
			}
			console.log(Foo.foo)  // Symbol(CLASS STATIC)
	
	# 类型推断
		* Symbol 值的变量，没指定类型的话，会推断为 Symbol
			
			// 类型推断为 symbol
			let val = Symbol();
		
		* const 命令声明的变量推断为 unique symbol
			// 类型为 unique symbol
			const val = Symbol();
		
		* 如果 let/const 互相赋值，则推断类型为 symbol
	
			let x = Symbol();
			// let -> const，类型为 symbol
			const y = x;
		
			const x = Symbol();
			// const -> let，类型为 symbol
			let y = x;

		* 猜测，const 互相赋值，应该是推断为 unique symbol
