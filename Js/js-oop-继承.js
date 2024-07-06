--------------------------
继承
--------------------------
	# ES6类支持单继承。使用 extends 关键字，就可以继承任何拥有 [[Construct]] 和原型的对象。
		
		* 不仅可以继承一个类，也可以继承普通的构造函数（保持向后兼容）
			function Super (){
				this.nameVal = "Super Val";
			}

			class Foo extends Super {}
			console.log(new Foo().nameVal);  // Super Val
	
		* extends关键字也可以在类表达式中使用
			let Bar = class extends Foo {} // 是有效的语法。
		
		* Object.getPrototypeOf() 方法可以用来从子类上获取父类。

			class Point { /*...*/ }

			class ColorPoint extends Point { /*...*/ }

			Object.getPrototypeOf(ColorPoint) === Point
			// true
	

	
	# super 关键字
		* 子类的方法可以通过 super 关键字引用它们的原型，即 super 指向当前对象的原型对象。
		* 这个关键字只能在子类中使用，而且仅限于 '类构造函数'、'实例方法' 和 '静态方法内部'
	
		* 在类构造函数中使用 super 可以调用父类构造函数。
		* 在静态方法中可以通过 super 调用继承的类上定义的静态方法
		* 由于 super 指向父类的原型对象，所以定义在父类实例上的方法或属性，是无法通过 super 调用的。

		* ES6给类 '构造函数' 和 '静态方法' 添加了内部特性 [[HomeObject]]
		* 这个特性是一个指针，指向定义该方法的对象。
		* 这个指针是自动赋值的，而且只能在JavaScript引擎内部访问。super 始终会定义为 [[HomeObject]] 的原型。
		
		* 在子类中显式定义了构造函数，则要么必须在其中调用 super()，要么必须在其中返回一个对象。

		* JavaScript 引擎内部
			* super.foo 等同于
				Object.getPrototypeOf(this).foo	// foo 是属性
				Object.getPrototypeOf(this).foo.call(this)// foo 是方法
	
	# 私有属性和私有方法的继承
		* 父类所有的属性和方法，都会被子类继承，除了私有的属性和方法。
		* 私有属性只能在定义它的 class 里面使用。
	
	# 静态属性和静态方法的继承
		* 父类的静态属性和静态方法，也会被子类继承。
	
	# new.target 关键字
		* new.target 保存通过 new 关键字调用的类或函数
		* 如果构造函数不是通过 new 命令或 Reflect.construct() 调用的，new.target 会返回 undefined，因此这个属性可以用来确定构造函数是怎么调用的。
		* Class 内部调用 new.target，返回当前 Class。
		* 子类继承父类时，new.target 会返回子类。
		* 在函数外部，使用 new.target 会报错。

		* 通过在实例化时检测new.target是不是抽象基类，可以阻止对抽象基类的实例化：
 
			class SuperClass {
				constructor(){
					console.log(this, new.target);
				}
			}

			class SubClass1 extends SuperClass{}
			class SubClass2 extends SuperClass{}

			new SubClass1();        // SubClass1 {}class SubClass1 extends SuperClass{}
			new SubClass2();        // SubClass2 {} class SubClass2 extends SuperClass{}
	
	