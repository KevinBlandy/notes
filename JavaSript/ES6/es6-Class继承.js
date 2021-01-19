--------------------------------
class 继承						|
--------------------------------
	1,简介
	2,Object.getPrototypeOf()
	3,super 关键字
	4,类的 prototype 属性和__proto__属性
	5,原生构造函数的继承
	6,Mixin 模式的实现

--------------------------------
class 简介						|
--------------------------------
	# Class 可以通过extends关键字实现继承,这比 ES5 的通过修改原型链实现继承,要清晰和方便很多
		class Point {
		}
		class ColorPoint extends Point {
		}

	# 子类必须在constructor方法中调用super方法,否则新建实例时会报错
		* 这是因为子类没有自己的this对象,而是继承父类的this对象,然后对其进行加工
		* 如果不调用super方法,子类就得不到this对象
			class Point {}
			class ColorPoint extends Point {
				constructor() {
					
				}
			}
			let cp = new ColorPoint(); // ReferenceError

	# ES5 的继承,实质是先创造子类的实例对象this,然后再将父类的方法添加到this上面(Parent.apply(this))
		* ES6 的继承机制完全不同,实质是先创造父类的实例对象this(所以必须先调用super方法),然后再用子类的构造函数修改this
		* 如果子类没有定义constructor方法,这个方法会被默认添加,代码如下
			class ColorPoint extends Point {
			}
			// 等同于
			class ColorPoint extends Point {
				constructor(...args) {
					super(...args);
				}
			}
			* 不管有没有显式定义,任何一个子类都有constructor方法
	
	# 在子类的构造函数中,只有调用super之后,才可以使用this关键字,否则会报错
		* 这是因为子类实例的构建,是基于对父类实例加工,只有super方法才能返回父类实例
			class Parent{
				constructor(){
					
				}
			}
			class Sub extends Parent{
				constructor(){
					this.name = 'KevinBlandy';
					super();			//super 必须在使用 this 之前调用
				}
			}
			new Sub();
			//Uncaught ReferenceError: Must call super constructor in derived class before accessing 'this' or returning from derived constructor
	
	# instanceof 运算
		class Parent{}
		class Sub extends Parent{}
		let sub = new Sub();
		console.log(sub instanceof Parent);		//true
		console.log(sub instanceof Sub);		//true

	# 父类的静态方法,也会被子类继承

--------------------------------
Object.getPrototypeOf()			|
--------------------------------
	# Object.getPrototypeOf方法可以用来从子类上获取父类
		
		Object.getPrototypeOf(ColorPoint) === Point
		// true
		* 因此,可以使用这个方法判断,一个类是否继承了另一个类
	
	 # Object.getPrototypeOf(),获取的是指定对象的直接父类
		class Parent{}
		class Sub extends Parent{}
		class Ssub extends Sub{}
		
		let ssub = Object.getPrototypeOf(new Ssub());
		console.log(ssub);//Sub {constructor: ƒ}

--------------------------------
super 关键字					|
--------------------------------
	# super这个关键字,既可以当作函数使用,也可以当作对象使用
	# 在这两种情况下,它的用法完全不同
		1,第一种情况,super作为函数调用时
			* 代表父类的构造函数,ES6 要求,子类的构造函数必须执行一次super函数
			* 是必须的,否则 JavaScript 引擎会报错
			* super虽然代表了父类构造函数,但是返回的是子类B的实例,即super内部的this指的是子类
			* 因此super()在这里相当于:父类.prototype.constructor.call(this)
				class A {
					constructor() {
						console.log(new.target.name);
					}
				}
				class B extends A {
					constructor() {
						super();
					}
				}
				new A() // A
				new B() // B
		
			* 作为函数时,super()只能用在子类的构造函数之中,用在其他地方就会报错
				class A {}
				class B extends A {
					m() {
						super(); // 报错
					}
				}
		
		2,super作为对象
			* 在普通方法中,指向父类的原型对象
			* 在静态方法中,指向父类
			* super指向父类的原型对象,所以定义在父类实例上的方法或属性,是无法通过super调用的
				class A {
					constructor() {
						this.p = 2;
					}
				}

				class B extends A {
					get m() {
						return super.p;
					}
				}

				let b = new B();
				console.log(b.m); // undefined
			
			* 在子类普通方法中通过super调用父类的方法时,方法内部的this指向当前的子类实例
			* this指向子类实例,所以如果通过super对某个属性赋值,这时super就是this,赋值的属性会变成子类实例的属性
				class Parent{
				}	
				class Sub extends Parent{
					foo(){
						//对super设置属性,其实就是对this设置属性
						super.name = '123456';
					}
				}
				
				let sub = new Sub();
				sub.foo();
				console.log(sub.name);
			
			* 如果super作为对象,用在静态方法之中,这时super将指向父类,而不是父类的原型对象
				class Parent {
					static myMethod(msg) {
						console.log('static', msg);
					}
					myMethod(msg) {
						console.log('instance', msg);
					}
				}

				class Child extends Parent {
					static myMethod(msg) {
						super.myMethod(msg);
					}
					myMethod(msg) {
						super.myMethod(msg);
					}
				}
				Child.myMethod(1);			// static 1			执行静态的myMethod
				let child = new Child();
				child.myMethod(2);			// instance 2		执行实例的myMethod

				* super在静态方法之中指向父类,在普通方法之中指向父类的原型对象

			* 在子类的静态方法中通过super调用父类的方法时,方法内部的this指向当前的子类,而不是子类的实例
			* 使用super的时候,必须显式指定是作为函数,还是作为对象使用,否则会报错
				console.log(super); // 报错
	
	# 由于对象总是继承其他对象的,所以可以在任意一个对象中,使用super关键字
		var obj = {
			toString() {
				return "MyObject: " + super.toString();
			}
		};

		obj.toString(); // MyObject: [object Object]

------------------------------------
类的 prototype 属性和__proto__属性	|
------------------------------------
	# 大多数浏览器的 ES5 实现之中,每一个对象都有__proto__属性,指向对应的构造函数的prototype属性
	# Class 作为构造函数的语法糖,同时有prototype属性和__proto__属性,因此同时存在两条继承链
		1,子类的__proto__属性,表示构造函数的继承，总是指向父类
		2,子类prototype属性的__proto__属性,表示方法的继承,总是指向父类的prototype属性
			class A {}
			class B extends A {}
			B.__proto__ === A						// true	子类B的__proto__属性指向父类A
			B.prototype.__proto__ === A.prototype	// true	子类B的prototype属性的__proto__属性指向父类A的prototype属性
	
	# 类的继承是按照下面的模式实现的
		class A {}
		class B {}
		
		// B 的实例继承 A 的实例
		Object.setPrototypeOf(B.prototype, A.prototype);
		// B 继承 A 的静态属性
		Object.setPrototypeOf(B, A);
		const b = new B();
	
	# 这两条继承链,可以这样理解
		* 作为一个对象,子类(B)的原型(__proto__属性)是父类(A)
		* 作为一个构造函数,子类(B)的原型对象(prototype属性)是父类的原型对象(prototype属性)的实例
			Object.create(A.prototype);
			// 等同于
			B.prototype.__proto__ = A.prototype;
	
	# extends 的继承目标
		* extends关键字后面可以跟多种类型的值
			class B extends A {
			}
			
			* 只要是一个有prototype属性的函数,就能被B继承
			* 由于函数都有prototype属性(除了Function.prototype函数),因此A可以是任意函数
		
		* 三种特殊情况
			1,子类继承Object类
				class A extends Object {
				}

				A.__proto__ === Object // true
				A.prototype.__proto__ === Object.prototype // true

				* 这种情况下,A其实就是构造函数Object的复制,A的实例就是Object的实例
			
			2,不存在任何继承
				class A {
				}

				A.__proto__ === Function.prototype			// true
				A.prototype.__proto__ === Object.prototype	// true

				* A作为一个基类(即不存在任何继承),就是一个普通函数,所以直接继承Function.prototype
				* 但是,A调用后返回一个空对象(即Object实例),所以A.prototype.__proto__指向构造函数(Object)的prototype属性
			
			3,子类继承null
				class A extends null {
				}

				A.__proto__ === Function.prototype		// true
				A.prototype.__proto__ === undefined		// true
			
				* 这种情况与第二种情况非常像
				* A也是一个普通函数,所以直接继承Function.prototype
				* 但是,A调用后返回的对象不继承任何方法,所以它的__proto__指向Function.prototype,即实质上执行了下面的代码
					class C extends null {
						constructor() { return Object.create(null); }
					}
		
	# 实例的 __proto__ 属性
		* 子类实例的__proto__属性的__proto__属性,指向父类实例的__proto__属性
		* 也就是说,子类的原型的原型,是父类的原型
			var p1 = new Point(2, 3);
			var p2 = new ColorPoint(2, 3, 'red');

			p2.__proto__ === p1.__proto__			// false
			p2.__proto__.__proto__ === p1.__proto__ // true

			* ColorPoint继承了Point,导致前者原型的原型是后者的原型
			* 因此通过子类实例的__proto__.__proto__属性,可以修改父类实例的行为
				p2.__proto__.__proto__.printName = function () {
					console.log('Ha');
				};
				p1.printName() // "Ha"
				
				* ColorPoint 的实例p2上向Point类添加方法,结果影响到了Point的实例p1


----------------------------
原生构造函数的继承			|
----------------------------
	# 原生构造函数是指语言内置的构造函数,通常用来生成数据结构
	# ECMAScript 的原生构造函数大致有下面这些
		Boolean()
		Number()
		String()
		Array()
		Date()
		Function()
		RegExp()
		Error()
		Object()
		
	# 以前,这些原生构造函数是无法继承的,比如,不能自己定义一个Array的子类
	# ES6 允许继承原生构造函数定义子类,因为 ES6 是先新建父类的实例对象this,然后再用子类的构造函数修饰this,使得父类的所有行为都可以继承
		class MyArray extends Array {
			constructor(...args) {
				super(...args);
			}
		}

		var arr = new MyArray();
		arr[0] = 12;
		arr.length 	// 1

		arr.length = 0;
		arr[0] 		// undefined
	
	# extends关键字不仅可以用来继承类,还可以用来继承原生的构造函数,因此可以在原生数据结构的基础上,定义自己的数据结构
		* 定义了一个带版本功能的数组
			class VersionedArray extends Array {
				constructor() {
					super();
					//创建 history 二维数组
					this.history = [[]];
				}
				commit() {
					//记录当前[]中的所有数据到histrory
					this.history.push(this.slice());
				}
				revert() {
					//把当前history中的所有数据写入到当前[]
					//移除 从0 到 length,然后用history的最后一个[]中的元素填充到当前[]
					this.splice(0, this.length, ...this.history[this.history.length - 1]);
				}
			}

			var x = new VersionedArray();

			x.push(1);
			x.push(2);
			x			// [1, 2]
			x.history	// [[]]

			x.commit();
			x.history	// [[], [1, 2]]

			x.push(3);
			x			// [1, 2, 3]
			x.history	// [[], [1, 2]]

			x.revert();
			x			// [1, 2]	
		
		* 自定义Error子类的例子,可以用来定制报错时的行为
			class ExtendableError extends Error {
				constructor(message) {
					super();
					this.message = message;
					this.stack = (new Error()).stack;
					this.name = this.constructor.name;
				}
			}

			class MyError extends ExtendableError {
				constructor(m) {
					super(m);
				}
			}

			var myerror = new MyError('ll');
			myerror.message 			// "ll"
			myerror instanceof Error 	// true
			myerror.name 				// "MyError"
			myerror.stack
			
			// Error
			//		     at MyError.ExtendableError
			//		     ...
	
	# 注意,继承Object的子类,有一个行为差异
		class NewObj extends Object{
			constructor(){
				super(...arguments);
			}
		}
		var o = new NewObj({attr: true});
		o.attr === true				// false

		* NewObj继承了Object,但是无法通过super方法向父类Object传参
		* 因为 ES6 改变了Object构造函数的行为,一旦发现Object方法不是通过new Object()这种形式调用,ES6 规定Object构造函数会忽略参数

----------------------------
Mixin 模式的实现			|
----------------------------
	# Mixin 指的是多个对象合成一个新的对象,新对象具有各个组成成员的接口
		const a = {
			a: 'a'
		};
		const b = {
			b: 'b'
		};
		const c = {...a, ...b}; // {a: 'a', b: 'b'}
		
		* c对象是a对象和b对象的合成,具有两者的接口
	
	# 更完备的实现,将多个类的接口"混入"(mix in)另一个类
		function mix(...mixins) {
			class Mix {}
			
			for (let mixin of mixins) {
				copyProperties(Mix, mixin); 					// 拷贝实例属性
				copyProperties(Mix.prototype, mixin.prototype); // 拷贝原型属性
			}
			
			return Mix;
		}

		function copyProperties(target, source) {
			for (let key of Reflect.ownKeys(source)) {
				if ( key !== "constructor"
				  && key !== "prototype"
				  && key !== "name"
				) {
					let desc = Object.getOwnPropertyDescriptor(source, key);
					Object.defineProperty(target, key, desc);
				}
			}
		}

		* 上面代码的mix函数,可以将多个对象合成为一个类
		* 使用的时候,只要继承这个类即可

		class DistributedEdit extends mix(Loggable, Serializable) {
			// ...
		}