-------------------------
类
-------------------------
	# 实例属性
		* 可以声明在 class 顶层，如果有初始化值，那么类型可以推断
		* 如果不指定初始值，也不指定类型，则推断为 any
			class Foo {
				name = '';			// 推断为 string
				age: number;		// 指定为 number
				foo;				// 推断为 any
			}
		
		* 如果打开了 strictPropertyInitialization 编译选项，则类实例属性必须要有初始值，否则报错
		* 可以使用非空断言，来避免错误

			class Point {
			  x!: number;
			  y!: number;
			}
			// 感叹号，表示这两个属性肯定不会为空，所以 TypeScript 就不报错了
	
		* 属性名前面加上 readonly 修饰符，就表示该属性是只读的。实例对象不能修改这个属性。
		* readonly 的初始值可以写在类中，也可以写在构造函数中（优先级最高）。

			class Foo {
				readonly name = 'CONST';
				readonly foo: string
				constructor(){
					this.foo = 'Hi'
				}
			}
		
	# 方法
		* 类的方法就是普通函数，类型声明方式与函数一致。
		* 构造方法不能声明返回值类型，否则报错，因为它总是返回实例对象。
	
	# Getter/Setter 方法
		* 如果某个属性只有get方法，没有set方法，那么该属性自动成为只读属性。

			let obj = {
				get foo (){
					return "hi";
				}
			}

			obj.foo = 12;  // 无法为“foo”赋值，因为它是只读属性。

			
		* get/set 方法的返回值和参数类型可以是兼容的（TS 5.1 之前必须一模一样，不然报错）

			let obj = {
				get foo (){
					return "hi";
				},
				set foo (fooVal: number | string){		// 参数是 number 或者 string，兼容了 getter 的返回值 string
					console.log(fooVal);
				}
			}
	
		* get方法与set方法的可访问性必须一致，要么都为公开方法，要么都为私有方法。
	
	# 属性索引
		* 类允许定义属性索引

			class MyClass {
				// 字符串属性名称，可以是 boolean 值，也可以是函数
				[s:string]: boolean | ((s:string) => boolean);
			
				// 符合上面定义的属性索引
				get(s:string) {
					return this[s] as boolean;
				}
			}
		
			
			// [s:string] 表示所有属性名类型为字符串的属性，它们的属性值要么是布尔值，要么是返回布尔值的函数
		

		* 注意，由于类的方法是一种特殊属性（属性值为函数的属性），所以属性索引的类型定义也涵盖了方法。
		* 如果一个对象同时定义了 '属性索引' 和 '方法'，那么前者必须包含后者的类型。

			class MyClass {
				// 只能包含 key 为字符串，值为 boolean 的属性
				[s:string]: boolean;
				// 这个函数不符合上面定义的类型索引
				f() { // 报错
					return true;
				}
			}

			class Foo {
				// 属性字符串为 key，值为 boolean 或者是函数（参数为字符串、值为 boolean）
				[prop: string]: boolean | ((key: string) => boolean),

				// 符合
				bar(key: string) :boolean {
					return key ? true : false
				}
				// 符合
				foo = true

				// 符合
				get zoo () :boolean{
					return true;
				}
			}
		
	
	# 类的 interface 接口
		* 类可以使用 implements 来实现 interface 或者是 type 以表示当前类实现了这个规范
		* 类可以扩展出接口中不存在的字段
			
			// 使用 type 声明也可以
			interface Config {
				key: boolean | string | number
			}

			class AppConfig implements Config {
			  key: string | number | boolean = "keys"
			}
		
		
		* interface 只是指定检查条件，并不能代替 class 自身的类型声明

			interface A {
				get(name: string): boolean;
			}

			class B implements A {
				get(s) { // 参数“s”隐式具有“any”类型。
					return true;
				}
			}				
		
			// 虽然实现了 A 接口，但是 参数 s 仍然需要手动声明其类型（保证和接口的一致）
			// 不声明的话，它就是 any 类型，和接口不一致了
			// B 类依然需要声明参数 s 的类型，以实现接口

				get(s: string) { // s 的类型是 any
					return true;
				}
		
		* 注意可选属性的问题
			interface Foo {
				x: number,
				y?: number		// 接口声明了可选属性
			}

			class Bar implements Foo {
				x = 12
								// 类实现了接口，不声明可选属性，合法
			}

			const bar = new Bar();

			// 但是，如果尝试访问类实例中未声明的可选属性，则会异常
			bar.y = 123;  // 类型“typeof Bar”上不存在属性“y”

			// 解决办法就是，在类中也需要声明接口中的可选属性

			class Bar implements Foo {
				x = 12
				y?: number		// 可选属性
			}
		
		
		* implements 甚至可以实现一个类，这时，后面的类将被当作接口。

			class Car {
				id: number = 1;
				move(): void { };
			}

			class MyCar implements Car {
				id = 2; // 不可省略
				move(): void { };   // 不可省略
			}

			// TS 把Car视为一个接口，要求MyCar实现Car里面的每一个属性和方法，否则就会报错。
			// 不能因为Car类已经实现过一次，而在MyCar类省略属性或方法
			// 这不是继承，不是继承
		
		* 实现多个接口
			* 类可以实现多个接口（其实是接受多重限制），每个接口之间使用逗号分隔。
			* 可以是类，直接 implements 多个 interface 

				// 父类实现了一个接口
				class Car implements MotorVehicle {
				}
				
				// 子类，实现了三个接口（父类一个，自己两个）
				class SecretCar extends Car implements Flyable, Swimmable {
				}

			* 也可以是一个 interface extends 多个 interface ，最后类 implements 这个 interface		
				
				interface MotorVehicle {...}
				interface Flyable {...}
				interface Swimmable {...}
				
				// 这个接口，实现了三个接口
				interface SuperCar extends MotorVehicle,Flyable, Swimmable {
				}
				
				// 类实现一个接口，相当于实现了四个
				class SecretCar implements SuperCar {
				}
			
			* 注意，多重实现时（即一个接口同时实现多个接口），不同接口不能有互相冲突的属性。

				interface Flyable {
				  foo:number;  // foo 属性
				}

				interface Swimmable {
				  foo:string;	// foo 属性冲突
				}
			

	# 类与接口的合并
		* TS 不允许两个同名的类，但是允许和类同名的接口，接口会被合并进类中。
			interface Foo {
				name: string        // 接口的属性
				age: number
			}

			class Foo {
				address: string[];      // 类的属性
				constructor(address: string[]){
					this.address = address  // 初始化类的属性
					this.name = "KK"        // 初始化接口的属性
				}
			}

			// 初始化类的属性
			const foo:Foo = new Foo(['CN', 'CQ']);

			// 初始化接口的属性
			foo.age = 25;

			console.log(JSON.stringify(foo));  // {"address":["CN","CQ"],"name":"KK","age":25}
	
	# Class 类型 
		* TS 的类本身就是一种类型，但是它代表该类的实例类型，而不是 class 的自身类型。
		* 对于引用实例对象的变量来说，既可以声明类型为 Class，也可以声明类型为 Interface，因为两者都代表实例对象的类型。
		
			interface Writer {
				writeTo: (val: any) => number | never
			}

			class TCPWriter implements Writer {
				writeTo (val: any) :number | never {
					if (!val){
						throw new Error('val 不能为空');
					}
					return 1;
				}
			}
			
			// 使用 接口 引用
			const writer:Writer  = new TCPWriter();
			// 使用 类 引用
			const writer:TCPWriter = new TCPWriter();
			// 使用 接口或类 引用
			const writer:Writer | TCPWriter = new TCPWriter();
		
		* 如果使用接口引用，那么实例只能访问到接口中定义的类型

		* 作为类型使用时，类名只能表示实例的类型，不能表示类的自身类型。

			class Point {
				x: number;
				y: number;

				constructor(x: number, y: number) {
					this.x = x;
					this.y = y;
				}
			}

			// 错误
			function createPoint (
				PointClass: Point,			// 这是一个 Point 类型的实例对象，而不是 Point 类本身（构造函数）
				x: number,
				y: number
			) {
				// 并不是 Point class 对象
				return new PointClass(x, y); 
			}
		
		* 要获得一个类的自身类型，一个简便的方法就是使用 typeof 运算符。

				function createPoint(
					PointClass: typeof Point,		// 要一个 Point “类型”
					x: number,
					y: number
				): Point {
					// 创建这个类型的实例
					return new PointClass(x, y);
				}
		
		* 类的自身类型可以写成构造函数的形式。

			function createPoint(
				PointClass: new(x: number, y:number) => Point,	// new，表示是一个构造函数，创建的实例是 Point 类型
				x: number,
				y: number
			): Point {
				return new PointClass(x, y);
			}
		
		* 写成对象也中

			function createPoint(
				PointClass: {
					new(x: number, y: number): Point
				},
				x: number,
				y: number
			): Point {
				return new PointClass(x, y);
			}
		
		* 甚至，类的自身类型就是一个构造函数，可以单独定义一个接口来表示。
			// 类自身的构造函数类型
			interface PointConstructor {
				new(x: number, y: number): Point
			}

			function createPoint(
				PointClass: PointConstructor,
				x: number,
				y: number
			): Point {
				return new PointClass(x, y);
			}
	
	# 结构类型原则
		* 一个对象只要满足 Class 的实例结构，就跟该 Class 属于同一个类型（鸭式辫型法）。

			class Foo {
				x!: number
				y!: number
			}
			function bar (val: Foo){
				console.log(val instanceof Foo);
			}

			let obj = {
				x: 123, 
				y: 321, 
				other: '我是多余的属性' // 多一个也没问题
			}

			bar(obj);  // false

			// 如果把 obj 写成字面量对象，直接作为调用参数就不行，此时字面量对象的属性必须和 Foo 类中的属性完美契合
		
		* 如果两个类的实例结构相同，那么这两个类就是兼容的，可以用在对方的使用场合。

		* 简单立即类型原则就是，只要 A 类具有 B 类的结构（不考虑静态成员和构造方法），哪怕还有额外的属性和方法，TypeScript 也认为 A 兼容 B 的类型。
		* 但是要注意 instanceof 判断，此时可能不准确。

		* 空类不包含任何成员，任何其他类都可以看作与空类结构相同。
			class Empty {}

			function fn(x:Empty) {}

			fn({});
			fn(window);
			fn(fn);

					
		* 如果类中存在 private 成员或 protected 成员，那么确定兼容关系时，TS 要求 private 成员和 protected 成员来自同一个类，这意味着两个类需要存在继承关系。

			// 情况一
			class A {
				private name = 'a';
			}

			class B extends A {
			}

			const a: A = new B();

			// 情况二
			class A {
				protected name = 'a';
			}

			class B extends A {
				protected name = 'b';
			}

			const a: A = new B();

			// A和B都有私有成员（或保护成员）name，这时只有在 B 继承 A 的情况下（class B extends A），B 才兼容 A。
	
	# 类继承
		* 根据结构类型原则，子类也可以用于类型为基/父类的场合。
		* 子类的同名方法不能与基类的类型定义相冲突。

		* 子类可以覆写、提升父类中的同名的 protected 属性的访问权限为 public ，但是不能定义同名的 private 属性。
			
	
		* extends 关键字后面不一定是类名，可以是一个表达式，只要它的类型是构造函数就可以了。


	# 可访问性修饰符
		* 有三种，都写在属性名称前面

			public			// 无权限修饰符的默认值，公开的，谁都可以访问
			private
			protected
		

		private 
			* 只能用在当前类的内部，类的实例和子类都不能使用该成员。
			* 注意，子类不能定义父类私有成员的同名成员。
		
			* 严格地说，private 定义的私有成员，并不是真正意义的私有成员
				1. 编译为 JS 后， private 关键字会被擦除
				2. TS 对于私有成员没有严格地禁止，使用方括号就可以访问到
			
			* 但是，ES2022 发布了 JS 真正意义上的私有属性，即 # 开头的属性
			* 因此，不建议使用 TS 的 private ，而使用 js 定义的标准
				class Parent {
					#name: string		// 私有属性
					constructor(){
						this.#name = 'private name'    
					}
				}
		
			* 构造方法也可以私有，通过静态的工厂方法获取内部创建的实例
		
		protected 
			* 只能在类的内部使用该成员，实例在外面无法使用该成员，但是子类内部可以使用。
			* 子类不仅可以拿到父类的保护成员，还可以定义同名成员。
	
	
	# 实例属性的简写形式
		
		* 一般来说，实例属性的值，是通过构造方法传入的。
		* 如果参数过多的话，就比较麻烦

			class Point {
				// 声名两个属性
				x: number;
				y: number;
				
				// 构造函数中初始化两个属性
				constructor(x: number, y: number) {
					this.x = x;
					this.y = y;
				}
			}
		
		* TS 就提供了一种简写形式。

			class Point {
				constructor(
					public x: number, 
					protected y: number,
					private readonly z: string,
					readonly f: any
				) {}
			}

			// TS 会自动根据构造函数中参数的声名修饰，生成对应的成员变量
		
	
	# 顶层属性的处理方法
		* 对于类的顶层属性，TypeScript '早期' 的处理方法，与后来的 ES2022 标准不一致。

		* TS 从3.7版开始，引入了编译设置 useDefineForClassFields 这个设置设为true，则采用 ES2022 标准的处理方法，否则采用 TypeScript 早期的处理方法。
		* 它的默认值与 target 属性有关，如果输出目标设为 ES2022 或者更高，那么 useDefineForClassFields 的默认值为 true，否则为false。
						
		* 第二种情况与类的继承有关，子类声明的顶层属性在父类完成初始化。
		* 要避免这种问题，可以将所有顶层属性的初始化，都放到构造方法里面。

		* 对于类的继承，还有另一种解决方法，就是使用declare命令，去声明子类顶层属性的类型，告诉 TypeScript 这些属性的初始化由父类实现。

			class DogHouse extends AnimalHouse {
			  declare resident: Dog;

			  constructor(dog:Dog) {
				super(dog);
			  }
			}
	
	# 静态成员 
		* 类的内部可以使用 static 关键字，定义静态成员，只能通过类本身使用的成员，不能通过实例对象使用。
		* static 关键字前面可以使用 public private protected 修饰符。
		
		* 静态私有属性也可以用 ES6 语法的 # 前缀表示
			class MyClass {
				static #x = 0;
			}
		
		* public 和 protected 的静态成员可以被继承。
	
	
	# 泛型类
		* 类也可以写成泛型，使用类型参数。
	
			class Box<Type> {
			  contents: Type;

			  constructor(value:Type) {
				this.contents = value;
			  }
			}

			const b:Box<string> = new Box('hello!');
		
		* 静态成员不能使用泛型的类型参数
	
	# 抽象类，抽象成员
		* 在类的定义前面，加上关键字 abstract 表示该类不能被实例化，只能当作其他类的模板。

			abstract class Foo {
				abstract exec(): void;
				abstract foo: string
				abstract handle: (arg: any) => string
			}

			const foo = new Foo(); // 异常
		
		* 抽象类只能当作基类使用，用来在它的基础上定义子类。
		* 抽象类可以继承其他抽象类。
		* 子类必须要，抽象类中的所有 abstract 成员（即属性名和方法名有abstract关键字），子类才能进行实例化
		* 一些点
			（1）抽象成员只能存在于抽象类，不能存在于普通类。
			（2）抽象成员不能有具体实现的代码。也就是说，已经实现好的成员前面不能加abstract关键字。
			（3）抽象成员前也不能有 private 修饰符，否则无法在子类中实现该成员。
			（4）一个子类最多只能继承一个抽象类。
	
	# this 问题
		* 方法中的 this 指向调用时的对象。

		* 有些场合需要明确地指出 this 类型，但是 JavaScript 函数通常不带有 this 参数

		* TS 允许函数增加一个名为 this 的参数，放在参数列表的第一位，用来描述函数内部的 this 关键字的类型。
		* 编译后这个参数会被删除。
 

		* 调用方法的时候，如果传递的 this 参数和声明的类型不一致，则会错误

			class A {
				name = 'A';

				getName(this: A) {			// getName 只能被 A 类实例调用
					return this.name;
				}
			}

			const a = new A();
			const b = a.getName;

			b() // 报错，此时上下文是全局对象

		* this 参数的类型可以声明为各种对象。

			function foo(
				this: { name: string }			// 要求 this 是一个有 name 属性，且值为 string 类型的对象
			) {
				this.name = 'Jack';
				this.name = 0; // 报错，this 的 name 属性为 string，不能赋值为 number
			}

			foo.call({ name: 123 }); // 报错，name 属性是有了，但是值类型为 number
		
		* TS 提供了一个 noImplicitThis 编译选项。如果打开了这个设置项，如果 this 的值推断为 any 类型，就会报错。

			// noImplicitThis 打开

			class Rectangle {
				constructor(
					public width: number,
					public height: number
				) { }

				getAreaFunction() {
					// 这个函数里面用到了 this，但是这个 this 跟 Rectangle 这个类没关系，它的类型推断为 any，所以就报错了。
					return function () {
						return this.width * this.height; // 报错
					};
				}
			}
		
		* 在类的内部，this本身也可以当作类型使用，表示当前类的实例对象。
			
			class Box {
				contents: string = '';

				set(value: string): this { // 返回 this 类型的变量
					this.contents = value;
					return this;
				}
			}
		
		
		* 注意，this 类型不允许应用于静态成员。
			class A {
			  // this 类型表示实例对象，但是静态成员拿不到实例对象。
			  static a:this; // 报错
			}
		
		* 有些方法返回一个布尔值，表示当前的 this 是否属于某种类型。
		* 这时，这些方法的返回值类型可以写成 this is Type 的形式，其中用到了 is 运算符。
					
			class FileSystemObject {
				isFile(): this is FileRep {
					return this instanceof FileRep;
				}

				isDirectory(): this is Directory {
					return this instanceof Directory;
				}

				// ...
			}

			// 上述两个方法的返回值类型都是布尔值，写成this is Type的形式，可以精确表示返回值。
