--------------------------------
class 基本语法					|
--------------------------------
	1,简介
	2,严格模式
	3,constructor 方法
	4,类的实例对象
	5,Class 表达式
	6,不存在变量提升
	7,私有方法和私有属性
	8,this 的指向
	9,name 属性
	10,Class 的取值函数（getter）和存值函数（setter）
	11,Class 的 Generator 方法
	12,Class 的静态方法
	13,Class 的静态属性和实例属性
	14,new.target 属性

--------------------------------
class 简介						|
--------------------------------
	# 基本上,ES6 的class可以看作只是一个语法糖,它的绝大部分功能,ES5 都可以做到
		* 新的class写法只是让对象原型的写法更加清晰,更像面向对象编程的语法而已

        function Point(x, y) {
            this.x = x;
            this.y = y;
        }

        Point.prototype.toString = function () {
            return '(' + this.x + ', ' + this.y + ')';
        };

        var p = new Point(1, 2);

		//定义类
        class Point {
            constructor(x, y) {
                this.x = x;
                this.y = y;
            }

            toString() {
                return '(' + this.x + ', ' + this.y + ')';
            }
        }
		* constructor方法,这就是构造方法,而this关键字则代表实例对象
		* 定义"类"的方法的时候,前面不需要加上function这个关键字,直接把函数定义放进去了就可以了
		* 方法之间不需要逗号分隔,加了会报错
		* 类的数据类型就是函数,类本身就指向构造函数
			class Point {
				// ...
			}

			typeof Point // "function"
			Point === Point.prototype.constructor // true
	
	# 构造函数的prototype属性,在 ES6 的"类"上面继续存在
		* '事实上,类的所有方法都定义在类的prototype属性上面'
			class Point {
				constructor() {
					// ...
				}
				toString() {
					// ...
				}
				toValue() {
					// ...
				}
			}

			// 等同于

			Point.prototype = {
				constructor() {},
				toString() {},
				toValue() {},
			};
		* '在类的实例上面调用方法,其实就是调用原型上的方法'
			class B {}
			let b = new B();

			b.constructor === B.prototype.constructor // true	b是B类的实例,它的constructor方法就是B类原型的constructor方法
		
		*  类的方法都定义在prototype对象上面,所以类的新方法可以添加在prototype对象上面,Object.assign方法可以很方便地一次向类添加多个方法
			class Point {
				constructor(){
					// ...
				}
			}

			Object.assign(Point.prototype, {
				toString(){},
				toValue(){}
			});
		* prototype对象的constructor属性,直接指向"类"的本身,这与 ES5 的行为是一致的
			Point.prototype.constructor === Point // true
		
		* '类的内部所有定义的方法,都是不可枚举的,这一点与 ES5 的行为不一致'
		* 类的属性名,可以采用表达式
			let methodName = 'getArea';
			class Square {
				constructor(length) {
					// ...
				}

				[methodName]() {
					// ...
				}
			}
			
--------------------------------
constructor 方法				|
--------------------------------
	# constructor方法是类的默认方法,通过new命令生成对象实例时,自动调用该方法
	# 一个类必须有constructor方法,如果没有显式定义,一个空的constructor方法会被默认添加
	# onstructor方法默认返回实例对象(即this),完全可以指定返回另外一个对象
        class Foo {
            constructor() {
                return Object.create(null);
            }
        }

        new Foo() instanceof Foo
        // false
	
	# 类必须使用new调用,否则会报错,这是它跟普通构造函数的一个主要区别,后者不用new也可以执行

--------------------------------
类的实例对象					|
--------------------------------
	# 实例的属性除非显式定义在其本身(即定义在this对象上),否则都是定义在原型上(即定义在class上)
		* '只要不是通过this指令设置的属性,都是设置在原型对象上的'
			//定义类
			class Point {

				constructor(x, y) {
					this.x = x;
					this.y = y;
				}

				toString() {
					return '(' + this.x + ', ' + this.y + ')';
				}

			}

			var point = new Point(2, 3);

			point.toString() // (2, 3)

			point.hasOwnProperty('x')                       // true
			point.hasOwnProperty('y')                       // true
			point.hasOwnProperty('toString')                // false
			point.__proto__.hasOwnProperty('toString')      // true
	
	# 与 ES5 一样,类的所有实例共享一个原型对象
		var p1 = new Point(2,3);
		var p2 = new Point(3,2);

		p1.__proto__ === p2.__proto__
		//true
	
		* 这也意味着,可以通过实例的__proto__属性为"类"添加方法

--------------------------------
Class 表达式					|
--------------------------------
	# 与函数一样,类也可以使用表达式的形式定义
		const MyClass = class Me {
			getClassName() {
				return Me.name;
			}
		};
		* 需要注意的是,这个类的名字是MyClass而不是Me,Me只在 Class 的内部代码可用,指代当前类
			let inst = new MyClass();
			inst.getClassName() // Me
			Me.name // ReferenceError: Me is not defined
		
		* 如果类的内部没用到的话,可以省略Me,也就是可以写成下面的形式
			const MyClass = class { /* ... */ };
		
	# 采用 Class 表达式,可以写出立即执行的 Class
		let person = new class {
			constructor(name) {
				this.name = name;
			}

			sayName() {
				console.log(this.name);
			}
		}('张三');

		person.sayName(); // "张三"

--------------------------------
不存在变量提升					|
--------------------------------
	# 类不存在变量提升(hoist),这一点与 ES5 完全不同
		new Foo(); // ReferenceError
		class Foo {}

		* 这种规定的原因与继承有关,必须保证子类在父类之后定义
	
--------------------------------
私有方法和私有属性				|
--------------------------------
	# 现有的方法
		* 私有方法是常见需求,但 ES6 不提供,只能通过变通方法模拟实现

		1,一种做法是在命名上加以区别
			class Widget {
				// 公有方法
				foo (baz) {
					this._bar(baz);
				}

				// 私有方法
				_bar(baz) {
					return this.snaf = baz;
				}
				// ...
			}	
			* _bar方法前面的下划线,表示这是一个只限于内部使用的私有方法
			* 但是,这种命名是不保险的,在类的外部,还是可以调用到这个方法
		
		2,另一种方法就是索性将私有方法移出模块,因为模块内部的所有方法都是对外可见的
			class Widget {
				foo (baz) {
					bar.call(this, baz);
				}
				// ...
			}

			function bar(baz) {
				return this.snaf = baz;
			}
			* foo是公有方法,内部调用了bar.call(this, baz)
			* 这使得bar实际上成为了当前模块的私有方法
		
		3,还有一种方法是利用Symbol值的唯一,将私有方法的名字命名为一个Symbol值
			const bar = Symbol('bar');
			const snaf = Symbol('snaf');
			export default class myClass{
				// 公有方法
				foo(baz) {
					this[bar](baz);
				}
				// 私有方法
				[bar](baz) {
					return this[snaf] = baz;
				}
				// ...
			};
			* bar和snaf都是Symbol值,导致第三方无法获取到它们,因此达到了私有方法和私有属性的效果
		
	# 私有属性的提案
		* 与私有方法一样,ES6 不支持私有属性
		* 目前，有一个提案,为class加了私有属性,方法是在属性名之前,使用#表示
			 class Point {
				#x;
				constructor(x = 0) {
					#x = +x; // 写成 this.#x 亦可
				}

				get x() { return #x }
				set x(value) { #x = +value }
			}
			* #x就表示私有属性x,在Point类之外是读取不到这个属性的
			* 还可以看到,私有属性与实例的属性是可以同名的(比如,#x与get x())
		
		* 之所以要引入一个新的前缀#表示私有属性,而没有采用private关键字,是因为 JavaScript 是一门动态语言,使用独立的符号似乎是唯一的可靠方法
		* 能够准确地区分一种属性是否为私有属性,另外,Ruby 语言使用@表示私有属性,ES6 没有用这个符号而使用#,是因为@已经被留给了 Decorator
		* 该提案只规定了私有属性的写法,但是,很自然地,它也可以用来写私有方法
			class Foo {
				#a;
				#b;
				#sum() { return #a + #b; }
				printSum() { console.log(#sum()); }
				constructor(a, b) { #a = a; #b = b; }
			}
			* #sum()就是一个私有方法

		* 私有属性也可以设置 getter 和 setter 方法
				class Counter {
				#xValue = 0;

					get #x() { return #xValue; }
					set #x(value) {
						this.#xValue = value;
					}

					constructor() {
						super();
						// ...
					}
				}

--------------------------------
this 的指向						|
--------------------------------	
	# 类的方法内部如果含有this,它默认指向类的实例,但是,必须非常小心,一旦单独使用该方法,很可能报错
		class Logger {
            printName(name = 'there') {
                this.print(`Hello ${name}`);
            }

            print(text) {
                console.log(text);
            }
        }
        //创建logger对象
        const logger = new Logger();
        //通过解构赋值,把logger的printName函数赋值给变量printName
        const { printName } = logger;
        //执行该变量,异常
        printName(); // TypeError: Cannot read property 'print' of undefined

		* printName方法中的this,默认指向Logger类的实例
		* 如果将这个方法提取出来单独使用,this会指向该方法运行时所在的环境,因为找不到print方法而导致报错
	
		* 一个比较简单的解决方法是,在构造方法中绑定this,这样就不会找不到print方法了
		   class Logger {
				constructor() {
					this.printName = this.printName.bind(this);
				}

				// ...
			}
		
		* 另一种解决方法是使用箭头函数(箭头函数中的this,指向定义时的环境,而不是运行时)
			class Logger {
				constructor() {
					this.printName = (name = 'there') => {
						this.print(`Hello ${name}`);
					};
				}
				// ...
			}
		
		* 还有一种解决方法是使用Proxy,获取方法的时候,自动绑定this
			function selfish (target) {
				const cache = new WeakMap();
				const handler = {
					get (target, key) {
						const value = Reflect.get(target, key);
						if (typeof value !== 'function') {
							return value;
						}
						if (!cache.has(value)) {
							cache.set(value, value.bind(target));
						}
						return cache.get(value);
					}
				};
				const proxy = new Proxy(target, handler);
				return proxy;
			}

			const logger = selfish(new Logger());

--------------------------------
name 属性						|
--------------------------------
	# 由于本质上,ES6 的类只是 ES5 的构造函数的一层包装,所以函数的许多特性都被Class继承,包括name属性
		class Point {}
		Point.name // "Point"
		
		* name属性总是返回紧跟在class关键字后面的类名

--------------------------------
取值函数(getter)和存值函数(setter)|
--------------------------------
	# 与 ES5 一样，在"类"的内部可以使用get和set关键字,对某个属性设置存值函数和取值函数,拦截该属性的存取行为
		class MyClass {
			constructor() {
				// ...
			}
			get prop() {
				return 'getter';
			}
			set prop(value) {
				console.log('setter: '+value);
			}
		}

		let inst = new MyClass();

		inst.prop = 123;
		// setter: 123

		inst.prop
		// 'getter'
	
	# 存值函数和取值函数是设置在属性的 Descriptor 对象上的
		class CustomHTMLElement {
			constructor(element) {
				this.element = element;
			}

			get html() {
				return this.element.innerHTML;
			}

			set html(value) {
				this.element.innerHTML = value;
			}
		}

		var descriptor = Object.getOwnPropertyDescriptor(
			CustomHTMLElement.prototype, "html"
		);

		"get" in descriptor  // true
		"set" in descriptor  // true

		* 值函数和取值函数是定义在html属性的描述对象上面,这与 ES5 完全一致
	
--------------------------------
Class 的 Generator 方法			|
--------------------------------
	# 如果某个方法之前加上星号(*),就表示该方法是一个 Generator 函数
		class Foo {
			constructor(...args) {
				this.args = args;
			}
			* [Symbol.iterator]() {
				for (let arg of this.args) {
					yield arg;
				}
			}
		}

		for (let x of new Foo('hello', 'world')) {
			console.log(x);
		}
		// hello
		// world
	
	# Foo类的Symbol.iterator方法前有一个星号,表示该方法是一个 Generator 函数
	# Symbol.iterator方法返回一个Foo类的默认遍历器,for...of循环会自动调用这个遍历器

	
--------------------------------
Class 的静态方法				|
--------------------------------
	# 类相当于实例的原型,所有在类中定义的方法,都会被实例继承
	# 如果在一个方法前,加上static关键字,就表示该方法不会被实例继承,而是直接通过类来调用,这就称为"静态方法"
	    class Foo {
            static classMethod() {
                return 'hello';
            }
        }

        Foo.classMethod() // 'hello'

        var foo = new Foo();
        foo.classMethod()
        // TypeError: foo.classMethod is not a function
	
	# 如果静态方法包含this关键字,这个this指的是类,而不是实例
		class Foo {
			static bar () {
				this.baz();
			}
			static baz () {
				console.log('hello');
			}
			baz () {
				console.log('world');
			}
		}

		Foo.bar() // hello
		* 静态方法可以与非静态方法重名

	# 父类的静态方法,可以被子类继承
		class Foo {
			static classMethod() {
				return 'hello';
			}
		}

		class Bar extends Foo {
		}

		Bar.classMethod() // 'hello'
	
	# 静态方法也是可以从super对象上调用的
		class Foo {
			static classMethod() {
				return 'hello';
			}
		}

		class Bar extends Foo {
			static classMethod() {
				return super.classMethod() + ', too';
			}
		}

		Bar.classMethod() // "hello, too"
	
--------------------------------
Class 的静态属性和实例属性		|
--------------------------------
	# 静态属性指的是 Class 本身的属性,即Class.propName,而不是定义在实例对象(this)上的属性
		class Foo {
		}

		Foo.prop = 1;
		Foo.prop // 1
	
	# 目前,只有这种写法可行,因为 ES6 明确规定,Class 内部只有静态方法,没有静态属性
	# 目前有一个静态属性的提案,对实例属性和静态属性都规定了新的写法
	# 实例的类型属性
		class MyClass {
            myProp = 42;

            constructor() {
                console.log(this.myProp); // 42
            }
        }
		* myProp就是MyClass的实例属性,在MyClass的实例上,可以读取这个属性
		* 以前,我们定义实例属性,只能写在类的constructor方法里面
			class ReactCounter extends React.Component {
				constructor(props) {
					super(props);
					this.state = {
						count: 0
					};
				}
			}
			* 构造方法constructor里面,定义了this.state属性

		* 有了新的写法以后,可以不在constructor方法里面定义
			class ReactCounter extends React.Component {
				state = {
					count: 0
				};
			}
			* 这种写法比以前更清晰
		
		* 为了可读性的目的,对于那些在constructor里面已经定义的实例属性,新写法允许直接列出
			class ReactCounter extends React.Component {
				state;
				constructor(props) {
					super(props);
					this.state = {
						count: 0
					};
				}
			}

	# 类的静态属性
		* 类的静态属性只要在上面的实例属性写法前面,加上static关键字就可以了
			class MyClass {
				static myStaticProp = 42;

				constructor() {
					console.log(MyClass.myStaticProp); // 42
				}
			}
		* 同样的,这个新写法大大方便了静态属性的表达
			// 老写法
			class Foo {
				// ...
			}
			Foo.prop = 1;

			// 新写法
			class Foo {
				static prop = 1;
			}

--------------------------------
new.target 属性					|
--------------------------------
	# new是从构造函数生成实例对象的命令
	# ES6 为new命令引入了一个new.target属性,'该属性一般用在构造函数之中,返回new命令作用于的那个构造函数'
	# 如果构造函数不是通过new命令调用的new.target会返回undefined,因此这个属性可以用来确定构造函数是怎么调用的
		function Person(name) {
			if (new.target !== undefined) {
				this.name = name;
			} else {
				throw new Error('必须使用 new 命令生成实例');
			}
		}

		// 另一种写法
		function Person(name) {
			if (new.target === Person) {
				this.name = name;
			} else {
				throw new Error('必须使用 new 命令生成实例');
			}
		}

		var person = new Person('张三'); // 正确
		var notAPerson = Person.call(person, '张三');  // 报错

		* 上面代码确保构造函数只能通过new命令调用
	
	# Class 内部调用new.target,返回当前 Class
	   class Rectangle {
			constructor(length, width) {
				console.log(new.target === Rectangle);
				this.length = length;
				this.width = width;
			}
		}

		var obj = new Rectangle(3, 4); // 输出 true
	
	# 需要注意的是,子类继承父类时,new.target会返回子类
	   class Rectangle {
			constructor(length, width) {
				console.log(new.target === Rectangle);		//new.target会返回子类
				// ...
			}
		}

		class Square extends Rectangle {
			constructor(length) {
				super(length, length);
			}
		}

		var obj = new Square(3); // 输出 false

		* 利用这个特点,可以写出不能独立使用,必须继承后才能使用的类
		   class Shape {
				constructor() {
					if (new.target === Shape) {
						throw new Error('本类不能实例化');
					}
				}
			}

			class Rectangle extends Shape {
				constructor(length, width) {
					super();
					// ...
				}
			}

			var x = new Shape();  // 报错
			
			* Shape类不能被实例化,只能用于继承

	# 注意,在函数外部,使用new.target会报错
