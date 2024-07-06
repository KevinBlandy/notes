---------------------------
oop
---------------------------
	# 面向对象

---------------------------
prototype
---------------------------
	# prototype 属性
		* 每一个 function （类） 都有一个 prototype 属性，该属性指向一个对象
		* 该对象的所有属性和方法,都可以被该类的所有对象共享
		* prototype 对象的构造器，就是类构造器
		* 使用 Object.getPrototypeOf(obj) 方法获取对象的原型对象

			class User {
				foo (){
					console.log('Foo 运行');
				}
			}

			// 获取类的原型对象
			let prototype = User.prototype;

			console.log(prototype.constructor === User);  // true

			// 给原型对象新增属性/方法
			prototype.bar = function (){
				console.log('Bar 运行');
				this.foo();
			}

			let u1 = new User();
			
			// 对象的原型对象和类的是同一个
			console.log(prototype === Object.getPrototypeOf(u1)); // true

			u1.bar();

			// Bar 运行
			// oo 运行

			console.log(u1.bar === new User().bar); // true
			
		
	# 原型属性覆盖
		* 实例的同名属性会覆盖原型的同名属性。
		* 如果非要访问原型对象上的属性，可以先 delete 对象的属性，再访问。

			class User {}

			User.prototype.foo = 123;

			let u = new User();
			console.log(u.foo);     // 123
			// 覆盖原型属
			u.foo = 456;
			console.log(u.foo);     // 456

			// 删除对象属性
			delete u.foo;
			console.log(u.foo);     // 123
					

	# 判断实例的属性值是否是原型对象的属性值
		* 实例的 hasOwnProperty() 方法返回一个布尔值，表示对象自有属性（而不是继承来的属性，即不访问原型）中是否具有指定的属性。
		* in 操作符，不仅仅会检查实例对象，还会去检查原型对是否具备指定属性值（只要通过对象可以访问，in操作符就返回true）。

			class User {}
			User.prototype.foo = 123;

			let u = new User();
			console.log(u.hasOwnProperty('foo'), 'foo' in u);     // false true
			u.foo = 123;
			console.log(u.hasOwnProperty('foo'), 'foo' in u);     // true true
	

	# 原型与实例的关系 
		* 使用原型对象的 isPrototypeOf(var obj) 判断实例是否存在于原型对象中 
		* 如果 obj 不是对象，或者 this 原型对象不出现在 obj 的原型链中，则该方法返回 false

			class Foo {}
			class User {}
			console.log(User.prototype.isPrototypeOf(new User()));    // true
			console.log(User.prototype.isPrototypeOf(new Foo()));    // false
		
		* instanceof 检查对象(左)的原型链中是否包含指定构造函数(右、即类)的原型

			console.log(new Object() instanceof Object);      // true

	
	
	# 原型覆盖
		* 在严格模式下，直接修改 prototype 属性会异常

			"use strict";
			class Foo {}
			Foo.prototype = {} // Cannot assign to read only property 'prototype' of function 'class Foo {}'


			function Foo() {}
			let newProt = {};
			Foo.prototype = newProt;
			console.log(Object.getPrototypeOf(new Foo()) === newProt);  // true
		
		
		* 这种方法会改变原型对象的构造函数，可以手动给对象设置构造函数来解决
			function Foo() {}
			Foo.prototype = {
				constructor: Foo	
			}
		
		* 手动设置 constructor 属性的方法有个弊端，就是这个 constructor 属性会被 in 枚举出来

			for (let i in Foo.prototype){
				console.log(i);  // constructor
			}
		
		* 可以使用 Object.defineProperty() 方法来为原型对象定义  constructor 属性，并且禁止该属性被枚举

			Object.defineProperty(Foo.prototype,'constructor',{
				enumerable:false,		//不可被枚举
				value: Foo				// 值为构造函数
			}); 

