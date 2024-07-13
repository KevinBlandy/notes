-------------------
interface
-------------------
	# interface 是对象的模板，可以看作是一种类型约定，接口。

		* 使用 interface 定义接口
	
			interface Person {
			  firstName: string;
			  lastName: string;
			  age: number;
			}
		
		* 任何部署了这三个属性的对象，都可以看作是实现了该接口

			const kk:Person = {
				firstName: 'K',
				lastName: 'V',
				age: 23
			}
	
		* 括号运算符可以取出 interface 某个属性的类型。
			
			type T = Person['firstName']
		
		* interface 描述的是类的对外接口，也就是实例的公开属性和公开方法，不能定义私有的属性和方法。
		
	# interface 可以表示对象的各种语法，它的成员有 5 种形式。
		
		* 对象形式
			
			interface Point {
			  x: number;
			  y: number;
			}

			* 支持使用 ? 定义可选
			* 支持使用 readonly 定义只读（定义在属性名称前面）
		
		* 对象的属性索引

			interface A {
			  [prop: string]: number;
			}
			
			* 属性索引共有 string、number 和 symbol 三种类型。
		
		* 对象的方法
			// 写法一
			interface A {
			  f(x: boolean): string;
			}

			// 写法二
			interface B {
			  f: (x: boolean) => string;
			}

			// 写法三，对象形式
			interface C {
			  f: { (x: boolean): string };
			}
		
			* 属性名可以采用表达式

				const f = 'f';

				interface A {
				  [f](x: boolean): string;
				}
			
			* 类型方法可以重载，但是实现要提供所有方法的实现
				interface A {
				  f(): number;
				  f(x: boolean): boolean;
				  f(x: string, y: string): string;
				}
		
		* 函数，interface 也可以用来声明独立的函数。
	
			interface Add {
				(x: number, y: number): number;
			}
			const func: Add = (x, y) => x + y;
		

		* 构造函数，interface 内部可以使用new关键字，表示构造函数。

			interface ErrorConstructor {
				   // 参数           // 构造的对象类型
			  new (message?: string): Error;
			}
		
	
	# 继承 interface
		* interface 可以使用 extends 关键字，继承其他 interface。

			interface Animal {
				name: string
				walk (): void
			}
			
			// 会从 Animal 中拷贝属性
			interface Cat extends Animal {
				miao: () => void
			}

		* interface 允许多重继承
			* 子接口的属性会覆盖父接口的同名属性。
			* 子/父接口同名属性必须是类型兼容的，不能有冲突，否则会报错。
				// 子父冲突
				interface Foo {
				  id: string;
				}
				interface Bar extends Foo {
				  id: number; // 报错
				}
			
				// 父父冲突
				interface Foo {
				  id: string;
				}

				interface Bar {
				  id: number;
				}

				// 报错
				interface Baz extends Foo, Bar {
				  type: string;
				}
		
	# interface 继承 type
		
		* interface 可以继承type命令定义的对象类型（只能是对象，否则 interface 无法继承）。
	
			type Country = {
			  name: string;
			  capital: string;
			}

			interface CountryWithPop extends Country {
			  population: number;  // 新增了一个属性
			}
	
	# interface 继承 class
		* interface 还可以继承 class，即继承该类的所有成员。

			class A {
			  x:string = '';

			  y(): boolean {
				return true;
			  }
			}

			interface B extends A {
			  z: number
			}

			// B 继承了A，因此 B 就具有属性 x、y() 和 z。
			// 实现B接口的对象就需要实现这些属性。
		
		* interface 也可以继承有 private 和 protected 成员的类，但是意义不大。
	
	# 接口合并
		* 多个同名接口会合并成一个接口。

			interface Box {
			  height: number;
			  width: number;
			}

			interface Box {
			  length: number;
			}
		
			
			// 两个Box接口会合并成一个接口，同时有height、width和length三个属性。
		
		* 同名接口合并时，同一个属性如果有多个类型声明，彼此不能有类型冲突。

			interface A {
			  a: number;
			}

			interface A {
			  a: string; // 报错
			}
		
		* 同名接口合并时，如果同名方法有不同的类型声明，那么会发生函数重载。
		* 且，后面的定义比前面的定义具有更高的优先级。
		* 有一个例外。同名方法之中，如果有一个参数是字面量类型，字面量类型有更高的优先级。
			interface A {
				// 字面量类型的泛型
			  f(x:'foo'): boolean;
			}
		
		* 如果两个 interface 组成的联合类型存在同名属性，那么该属性的类型也是联合类型。
			interface Circle {
			  area: bigint; // bigint 类型的 area
			}

			interface Rectangle {
			  area: number; // number 类型的 area
			}

			declare const s: Circle | Rectangle;

			s.area;   // bigint | number
		
	# interface 与 type 的异同
		* interface命令与type命令作用类似，都可以表示对象类型。
		* 几乎所有的 interface 命令都可以改写为 type 命令。

		* interface 与 type 的区别有下面几点。
			1. type 能够表示非对象类型，而 interface 只能表示对象类型（包括数组、函数等）。
			2. interface 可以继承其他类型，type 不支持继承。
			3. 同名 interface 会自动合并，同名 type 则会报错（TS 不允许使用 type 多次定义同一个类型）
			4. interface 不能包含属性映射（mapping），type 可以。
			5. this 关键字只能用于interface。
			6. type 可以扩展原始数据类型，interface 不行。
			7. interface 无法表达某些复杂类型（比如交叉类型和联合类型），但是 type 可以。

		* 继承方面
			
			* interface 通过继承扩展属性，而 type 通过 & 运算。
			* interface 可以继承 type/type 也可以继承 interface。

				
				// type 继承 interface
				interface Foo {
					foo: string
				}
				type Bar = Foo & {bar: string}
				
				// interface 继承 type
				interface Zoo extends Bar {}
		
	