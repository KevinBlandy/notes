--------------------------
泛型
--------------------------
	# TypeScript 引入了“泛型”（generics）

	# 泛型方法
		* 函数名后面尖括号的部分 <T>，就是类型参数，参数要放在一对尖括号（<>）里面。
			
			function foo <T> (val:T ) :T[]{ // 泛型参数、泛型返回值
				let ret: T = val;		// 泛型变量
				return [ret];
			}

			// 方法调用时指定泛型
			console.log(foo<number>(5));  // [5]
			// 如果类型可以推断出来，那么可以省略泛型声名
			console.log(typeof foo(5)); // object
	
		* 调用泛型方法时，声名的泛型参数，即 TS 中的类型。

			function comb<T>(arr1: T[], arr2: T[]): T[] {
				return arr1.concat(arr2);
			}

			comb([1, 2], ['a', 'b'])			 // 报错
			comb<number|string>([1, 2], ['a', 'b']) // Ok

		* 如果有多个泛型参数，使用逗号分割，且建议单字母、大写命名：T\U\R

			function map<T, R>(arr: T[], func :(arg: T) => R) :R[] {
				return arr.map(func);
			}

			const ret = map([1, 2, 3], val => `[${val * val}]`);

			console.log(ret);  // ) ['[1]', '[4]', '[9]']
	
		* 变量形式定义的函数，泛型有下面两种写法
			// 写法一
			let myId: <T> (arg: T) => T = id;

			// 写法二
			let myId: { <T>(arg: T): T } = id;
	
	# 泛型接口
		* interface 也可以采用泛型的写法。
			
			// 泛型接口
			interface Foo <T> {
				content: T // 使用泛型类型
			}

			// 声名变量的时候，指定泛型的实际类型
			const foo: Foo<String | number> = {
				content: 'Hi'
			};

			console.log(foo.content);           // hi
			foo.content = 123;
			console.log(foo.content);           // 123
	
		* 继承时，指定泛型
			
			// 泛型接口		
			interface Comparator<T> {
				compareTo(val: T) :number;
			}
			
			// 类实现类型的时候，指定泛型
			class Vue implements Comparator<Vue> {

				constructor(private version: number){}
				
				// 实现接口方法
				compareTo(val: Vue): number {
					if (this.version > val.version){
						return 1;
					} else if (this.version < val.version){
						return -1;
					} 
					return 0;
				}
			}

			let ret = new Vue(2).compareTo(new Vue(2))
			console.log(ret); // -1
		
		* 泛型接口还有一种写法
			// Fn 的泛型是 Type
			interface Fn {
				// 注意，泛型不是全局的，只能在这个函数上使用
				<Type>(arg: Type): Type; // 传递 Type 返回 Type
			}

			// 函数的泛型是 Type
			function id<Type>(arg: Type): Type {
				return arg;			// 传递 Type 返回 Type
			}

			// 可以直接把 id 赋值给 Fn
			let myId: Fn = id;
		
	
	# 类的泛型
		* 泛型类的类型参数写在类名后面。
			// ---
			class Pair<K, V> {
				key!: K;
				value!: V;
			}

			
			// ---
			class A<T> {
				value!: T;
			}
			
			// 继承时指定泛型
			class B extends A<any> {
				value: any = 'Hi';
			}
		
		* 也可以用在类表达式。
			// 指定泛型 T
			const Container = class <T> {
				// 构造函数需要 T 类型
				constructor(public readonly data: T) { }
			};
			
			// 创建实例的时候指定具体的类型
			const a = new Container<boolean>(true);
			const b = new Container<number>(0);
		
			
			// --------
			// C 需要一个 NumType 类型
			class C <NumType> {
				// 泛型属性
				value!: NumType;
				// 泛型方法
				add!: (x: NumType, y: NumType) => NumType;
			}

			let foo = new C<number>();

			foo.value = 0;
			foo.add = function (x, y) {
				return x + y;
			};

		* JavaScript 的类本质上是一个构造函数，因此也可以把泛型类写成构造函数。
			
			// type 定义	 一个构造函数，支持任意数量的参数，返回值类型是泛型
			type MyClass<T> = new (...args: any[]) => T;

			// 或者
			interface MyClass<T> {
				new(...args: any[]): T;
			}

			// 用法实例，泛型函数，泛型为 T
			function createInstance <T> ( 
				AnyClass: MyClass<T>,			// 指定 AnyClass 的类型为 MyClass<T>，把当前函数的类型 T，传递给 MyClass 作为具体的泛型
				...args: any[]
			): T {
				// 调用 AnyClass ，创建实例
				return new AnyClass(...args);
			}
		
		* 注意，泛型类描述的是类的实例，不包括静态属性和静态方法
	
	# 类型别名的泛型写法
		* type 命令定义的类型别名，也可以使用泛型。
			
			// Nullable 类型有一个泛型 T
			type Nullable<T> = T | null | undefined

			const val: Nullable<String> = 'Hi';

			console.log(val);  // Hi
			
			
			// ------
			type Container <T> = { value: T };

			const a: Container<number> = { value: 0 };
			const b: Container<string> = { value: 'b' };
		
		* 泛型可以自引用/嵌套

			type Tree<T> = {
				val: T,
				// 递归引用自己
				sub: Tree<T>[] | null
			}

			class Menu implements Tree<String> {
				constructor(public val: string, public sub: Menu[] | null) { }
			}

			const menu = new Menu('一级菜单', [new Menu('二级菜单', [new Menu('二级菜单', null)])]);

			console.log(menu);

			// { "val": "一级菜单", "sub": [{ "val": "二级菜单", "sub": [{ "val": "二级菜单", "sub": null }] }] }
		
	# 类型参数的默认值
		* 类型参数可以设置默认值。使用时，如果没有给出类型参数的值，就会使用默认值。
			
			// 泛型参数 T 的默认类型为 string
			function getFirst<T = string>(
				arr: T[]
			): T {
				return arr[0];
			}
			

			getFirst([1, 2, 3]) // 正确，注意此时的泛型是 number，因为 TS 可以推断出来
		
		* 类型参数的默认值，往往用在类中

			// Generic 的泛型 T，默认类型是 string
			class Generic<T = string> {

				list: T[] = []

				add(t: T) {
					this.list.push(t)
				}
			}

			// 创建实例的时候，未指定泛型，也没法推断
			// 所以，默认的泛型类型为 string
			const g = new Generic();

			g.add(4) // 报错，因为 add 的泛型参数，默认为 string
			g.add('hello') // 正确
		
		* 如果有多个类型参数，可选参数必须在必选参数之后。
			<T = boolean, U> // 错误
			<T, U = boolean> // 正确
		
	
	# 数组的泛型
		* 数组类型有一种表示方法是 Array<T>，即 TS 提供的泛型数组

			const intArr: Array<number> = [1, 2, 3]
		
		* 事实上 number[] string[] 只是 Array<number> Array<string> 的简写形式。

		* TypeScript 内部，Array 是一个泛型接口，类型定义基本是下面的样子。

			interface Array<Type> {
			  length: number;
			  pop(): Type|undefined;
			  push(...items:Type[]): number;
			}
		
		* 除了数组，还有其他的一些容器类，如 Map<K, V>、Set<T> 和 Promise<T>。
	
	# 类型参数的约束条件
		* TypeScript 提供了一种语法，允许在类型参数上面写明约束条件。
			
			// 要求泛型 T  必须满足 {lengt: number}
			function comp <T extends { length: number }>(
				a: T,
				b: T
			) {
				if (a.length >= b.length) {
					return a;
				}
				return b;
			}
			
			// OR ----------
			interface Lengtable {
				length: number
			}

			function comp <T extends Lengtable> (
				a: T,
				b: T
			) {
				if (a.length >= b.length) {
					return a;
				}
				return b;
			}

			const arr:Lengtable = [1, 2, 3, 4]

			console.log(comp(arr, '123'))  // [1, 2, 3, 4]
		
		* 可以理解为通过 extends 指定泛型必须继承自某个类型

		* 类型参数可以同时设置约束条件和默认值，前提是默认值必须满足约束条件。
			
			// Fn 是一个数组类型，第一个元素是 A，第二个元素是 B
			// 泛型 A 必须继承于 string
			// 泛型 B 必须继承于 string，如果未指定泛型 B 的话，泛型 B 的实际数据是 'world'
			type Fn<A extends string, B extends string = 'world'> = [A, B];	

			// 继承 Fn 泛型，指定第一个元素 A 的实际值为 'hello'
			// 未指定第二个泛型，所以它默认值为 'world'
			type Result = Fn<'hello'> // ["hello", "world"]

			const arr:Result = ['hello', 'world']
		
		* 如果有多个类型参数的约束条件，可以引用其他参数。
				
				// 泛型 U 要求继承自前面的泛型 T
				<T, U extends T> 
				
				// 泛型 T 要求继承自后面的泛型 U
				<T extends U, U>
				
				// 约束条件不能引用类型参数自身
				<T extends T>               // 报错
				// 约束条件不能环形引用
				<T extends U, U extends T>  // 报错
		
	
	# 使用注意点
		1. 尽量少用泛型。
		2. 类型参数越少越好，越简单。
		3. 类型参数需要出现两次，如果类型参数在定义后只出现一次，那么很可能是不必要的。
		4. 泛型可以嵌套。
