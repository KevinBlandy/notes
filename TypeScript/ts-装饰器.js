------------------
装饰器
------------------
	# 装饰器（Decorator）是一种语法结构，用来在定义时修改类（class）的行为。
		* 2022 年通过成为标准的。
		* 在此之前，TypeScript 早在2014年就支持装饰器，不过使用的是旧语法。
	

	# 装饰器语法，类似于 Python
		1. 第一个字符（或者说前缀）是@，后面是一个表达式。
		2. @后面的表达式，必须是一个函数（或者执行后可以得到一个函数）。
		3. 这个函数接受所修饰对象的一些相关值作为参数。
		4. 这个函数要么不返回值，要么返回一个新对象取代所修饰的目标对象。
	
	# 装饰器，本身是一个函数
		* 装饰着设计模式，类似于拦截器，一般只用来为类添加某种特定行为。
		* 装饰器有多种形式，基本上只要在@符号后面添加表达式都是可以的

			// 直接使用函数
			@myFunc

			// 直接调用一个方法，返回函数
			@myFuncFactory(arg1, arg2)

			// 访问实例属性，是一个函数
			@libraryModule.prop
			// 调用实例的方法，返回一个函数
			@someObj.method(123)
			
			// 表达式，结果是一个函数
			@(wrap(dict['prop']))
		

			// -- 示例
			@frozen			// 类上有一个装饰器
			class Foo {

				// method 方法上有俩装饰器
				@configurable(false)
				@enumerable(true)
				method() { }
				
				// expensiveMethod 方法上有一个装饰器
				@throttle(500)
				expensiveMethod() { }
			}
		
	# 装饰器的结构
		* 装饰器函数的类型定义如下

			type Decorator = (
				value: DecoratedValue,
				context: {
					kind: string;
					name: string | symbol;
					addInitializer?(initializer: () => void): void;
					static?: boolean;
					private?: boolean;
					access: {
						get?(): unknown;
						set?(value: unknown): void;
					};
				}
			) => void | ReplacementValue;

			value
				* 所装饰的对象。
			context
				* 上下文信息，TS 提供的原生对象
				* 它的类型其实是 ClassMethodDecoratorContext
					kind
						* 表示所装饰对象的类型（也表示了装饰器的类型），可能取以下的值。
							'class'
							'method'
							'getter'
							'setter'
							'field'
							'accessor'
					name
						* 字符串或者 Symbol 值，所装饰对象的名字，比如类名、属性名等。
					
					addInitializer
						* 添加 initializer 的函数，用来添加类的初始化逻辑，没有返回值。
					
					private
						* 所装饰的对象是否为类的私有成员。
					
					static
						* 所装饰的对象是否为类的静态成员。
					
					access
						* 对象，包含了某个值的 get 和 set 方法。
			
	# 类装饰器
		* 类装饰器的类型描述

			type ClassDecorator = (
			  value: Function,			// 当前类本身
			  context: {
				kind: 'class';
				name: string | undefined;
				addInitializer(initializer: () => void): void;
			  }
			) => Function | void;

	
	# 方法装饰器
		* 方法装饰器类型
			
			type ClassMethodDecorator = (
			  value: Function,			// 方法本身
			  context: {
				kind: 'method';		
				name: string | symbol;
				static: boolean;
				private: boolean;
				access: { get: () => unknown };
				addInitializer(initializer: () => void): void;
			  }
			) => Function | void;
		

	
	# 属性装饰器
		* 属性装饰器类型

			type ClassFieldDecorator = (
			  value: undefined,
			  context: {
				kind: 'field';
				name: string | symbol;
				static: boolean;
				private: boolean;
				access: { get: () => unknown, set: (value: unknown) => void };
				addInitializer(initializer: () => void): void;
			  }
			) => (initialValue: unknown) => unknown | void;


	# getter 装饰器，setter 装饰器 			
	
	# accessor 装饰器 

	# 装饰器的执行顺序 

