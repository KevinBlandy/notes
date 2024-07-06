--------------------------
class
--------------------------
	# 定义，可以是生命式、也可以是表达式
		// 类声明
		class Person {}
		// 类表达式
		const Animal = class {};
	
		* 类没有函数的变量提升
		* 函数受函数作用域限制，而类受块作用域限制
	
	# class 只是 function 的语法糖
		
		class Foo {}

		console.log(typeof Foo);  // function
		console.log(Foo.prototype);     // {}
		console.log(Foo.prototype.constructor === Foo);     // true
		console.log(new Foo() instanceof Foo);  // true
		console.log(Object.getPrototypeOf(new Foo()) === Foo.prototype);    // true
	

	# 构造函数
		* 使用 constructor 定义，不是必须的，如果没的话，会有一个默认的
		* 使 new 调用类的构造函数会执行如下操作。
			1. 在内存中创建一个新对象。
			2. 这个新对象内部的 [[Prototype]] 指针被赋值为构造函数的 prototype 属性。
			3. 构造函数内部 的this 被赋值为这个新对象（即 this 指向新对象）。
			4. 执行构造函数内部的代码（给新对象添加属性）。
			5. 如果构造函数返回非空对象，则返回该对象；否则，返回刚创建的新对象。
		
		* 类构造函数和普通的构造函数区别是，类构造函数只能用 new 调用。

			class Foo {}
			let foo = new Foo();
			let bar = new foo.constructor();  // 通过实例获取到类的构造函数，并且通过 new 调用
			console.log(bar instanceof Foo);  // true
			foo.constructor();  // 直接调用 foo，异常： Class constructor Foo cannot be invoked without 'new'
		
		* 在构造函数中通过 this 添加的属性是当前对象自有属性，不会在原型上共享。
		* 构造函数默认返回 this 对象，但你也可以返回任意对象。
	
	# 方法
		* 类中定义的方法作为原型方法，同于对象属性，因此可以使用字符串、符号或计算的值作为 key

			class Foo {
				run(){}
				['te st'](){}
				[Symbol.for('bar')](){}
			}
			new Foo().run();
			new Foo()['run']();
			new Foo()[Symbol.for('bar')]();
	
	# 实例属性
		* 在以前， 类的属性只能通过 this 定义在构造函数中
		* 现在，可以直接定义在类中

			class Foo {
				bar = 'Hi';
				foo;
				constructor(){
					console.log(this.bar);  // Hi
				}
			}

			let foo = new Foo();

			console.log(foo.bar);  // Hi

		* 注意，这种写法定义的属性是实例对象自身的属性，而不是定义在实例对象的原型上面。
					
	# Getter/Setter 属性

		* 持获 getter 和 setter，语法与行为跟普通对象一样：

			class Foo {
				get bar (){
					return this._bar;
				}
				set bar (barVal){
					this._bar = barVal;
				}
			}
			let f = new Foo();
			f.bar = 'Hi';
			console.log(f.bar); // Hi
	
	# 静态方法
		 * 如果在一个方法前加上 static 关键字，就表示该方法不会被实例继承，而是直接通过类来调用，这就称为"静态方法"。
		 * 在静态成员中， this 引用类自身。

			class Foo {
				hi(){
					console.log(this);
				}
				static hi(){
					console.log(this);
				}
			}

			new Foo().hi();      // Foo {}
			Foo.hi(); // class Foo { ... }
	
		
		* 父类的静态方法，可以被子类静态继承。
	
	# 静态属性
		* 以前，并不显式支持在类中添加静态成员，但在类定义外部，可以手动添加。

				class Foo {}
				Foo.OuterName = "123";
				console.log(Foo.OuterName);         // 123
				console.log(new Foo().OuterName);  // undefined
		
		* 现在，也可以通过 static 关键字添加静态属性了，同样，也可以被子类静态继承
			class Foo {
				static foo = 'Hello';
			}

			let foo = new Foo();

			console.log(foo.foo);  // undefined
			console.log(Foo.foo); // Hello


	# 迭代器与生成器
		* 类定义语法支持在原型和类本身上定义生成器方法

			class Foo {
				* it(){yield "hi"}
				*[Symbol.for('foo')](){yield "HiHiHi"}
				static *it(){ yield "hello"}
			}
			console.log(new Foo().it().next());      // {value: 'hi', done: false}
			console.log(Foo.it().next());           // {value: 'hello', done: false}
			console.log(new Foo()[Symbol.for('foo')]().next()); // {value: 'HiHiHi', done: false}
	
	
	# 私有属性
		* ES2022正式为class添加了私有属性，方法是在属性名之前使用#表示。
		* '#' 只能在类的内部使用 

			class User {

				#name = '';
				#age;

				constructor(){
					this.#name = 'Foo'
					this.#age = 12;
				}
				getName(){
					return this.#name;
				}
				getAge(){
					return this.#age;
				}
				say(){
					console.log(`Hi im ${this.getName()} / ${this.getAge()}`);  
				}
			}

			let user = new User();
			user.say(); // Hi im Foo / 12

			console.log(user.name);  //undefined

			console.log(user.#name);  // SyntaxError: Private field '#name' must be declared in an enclosing class (at index.js:29:17)
		
		* 还可以用于定义私有方法
		   #say(){
				console.log(`Hi im ${this.getName()} / ${this.getAge()}`);  
			}
	
		* 私有属性不限于从 this 引用，只要是在类的内部，实例也可以引用私有属性。

			class User {
				// 私有属性
				#name;
				constructor(name){
					this.#name = name;
				}

				// 静态方法
				static userName (user){
					// 方法 user 参数的 name 私有属性
					return user.#name;
				}
			}

			let user = new User("嗨嗨嗨");

			console.log(User.userName(user));  // 嗨嗨嗨
	
		* 还支持私有的静态属性、方法

			class User {
				static #name = 'Hi'
				static #age = '26'

				static #privateSay() {
					return User.#name + " - " + User.#age;
				}

				static say (){
					// 调用静态方法
					return User.#privateSay();
				}
			}
			const r = User.say();
			console.log(r);  // Hi - 26
		
		
		* in 运算符判断某个对象是否有私有属性。它不会报错，而是返回一个布尔值。
		* 而且，判断所针对的私有属性，一定要先声明，否则会报错。
			
			class User {
				#name = '';
				constructor(){ }
				test (){
					if(#name in this){
						console.log('有 #name 属性');  // 有 #name 属性
					} 
					if(#age in this){
						// 没声明 #age 属性，会异常
						console.log('有 #age 属性');  // SyntaxError: Private field '#age' must be declared in an enclosing class (at index.js:8:11)
					}
				}
			}

			new User().test();
		

	# 静态块
		* ES2022 引入了静态块（static block），允许在类的内部设置一个代码块，在类生成时运行且只运行一次，主要作用是对静态属性进行初始化。
		* 以后，新建类的实例时，这个块就不运行了。
		* 每个静态块中只能访问之前声明的静态属性，且不能有 return 语句。

			class Foo {
				static {
					console.log('static 块 1 执行');
				}
				static {
					console.log('static 块 2 执行');
				}
				static {
					console.log('static 块 3 执行');
				}
				constructor(){
					console.log('实例创建...');
				}
			}

			new Foo();
			new Foo();
			new Foo();

			/*
				static 块 1 执行
				static 块 2 执行
				static 块 3 执行
				实例创建...
				实例创建...
				实例创建..
			*/
		

