----------------------------
Proxy						|
----------------------------
	1,概述
	2,Proxy 实例的方法
	3,Proxy.revocable()
	4,this 问题
	5,实例:Web 服务的客户端


----------------------------
概述						|
----------------------------
	# 跟Java的Proxy一样,AOP编程
	# 创建Proxy实例对象

		new Proxy(target,handler)

		* target,目标对象(被增强对象)
		* handler,代理控制器(一个对象)
	
	# Hello World
		let obj = {name:'Kevin',age:23};
		let proxyObj = new Proxy(obj,{
			//读取属性时拦截
			get:function(target,key,receiver){
				console.log(`获取属性:${key}`);
				return Reflect.get(target, key, receiver);
			},
			//设置属性时拦截
			set:function(target, key, value, receiver){
				console.log(`设置属性:${key},${value}`);
				return Reflect.set(target, key, value, receiver);
			}
		});
		
		proxyObj.name;			//获取属性:name
		proxyObj.age = 25;		//设置属性:age,25

	
	# 如果handler没有设置任何拦截,那就等同于直接通向原对象
		let obj = {name:'Kevin',age:23};
		let proxyObj = new Proxy(obj,{});
		* handler是一个空对象,没有任何拦截效果,访问proxyObj就等同于访问obj

--------------------------------
Proxy.revocable					|
--------------------------------
	# Proxy.revocable方法返回一个可取消的 Proxy 实例
		* Proxy.revocable方法返回一个对象,该对象的proxy属性是Proxy实例,revoke属性是一个函数
			let target = {};
			let handler = {};

			let {proxy, revoke} = Proxy.revocable(target, handler);

			proxy.foo = 123;
			proxy.foo // 123

			revoke();
			proxy.foo // TypeError: Revoked
			//上面代码中,当执行revoke函数之后,再访问Proxy实例,就会抛出一个错误

--------------------------------
this							|
--------------------------------
	# 目标对象内部的this关键字会指向 Proxy 代理
		const target = {
			m: function () {
				console.log(this === proxy);
			}
		};
		
		const handler = {};
		const proxy = new Proxy(target, handler);
		target.m() // false
		proxy.m()  // true
	
	# 有些原生对象的内部属性,只有通过正确的this才能拿到,所以 Proxy 也无法代理这些原生对象的属性
		const target = new Date();
		
		const handler = {};
		
		const proxy = new Proxy(target, handler);
		
		console.log(target.getDate());		//29(日)
		console.log(proxy.getDate());		// TypeError: this is not a Date object.
	
		* getDate方法只能在Date对象实例上面拿到,如果this不是Date对象实例就会报错
		* 这时,this绑定原始对象,就可以解决这个问题
			const target = new Date('2015-01-01');
			const handler = {
				get(target, prop) {
					if (prop === 'getDate') {
						//如果发现是执行 getDate 方法,则绑定这个函数的上下文为原始对象
						return target.getDate.bind(target);
					}
					return Reflect.get(target, prop);
				}
			};
			const proxy = new Proxy(target, handler);
			
			proxy.getDate() // 1

--------------------------------
Proxy 支持的拦截操作一览(13 种)	|
--------------------------------
	# 其实就是 hanlder 的属性
	
	get(target, propKey, receiver)
		* 拦截对象属性的读取,比如proxy.foo和proxy['foo']
		* 'get方法的第三个参数receiver,总是为当前的 Proxy 实例'
			const proxy = new Proxy({}, {
				get: function(target, property, receiver) {
					return receiver;
				}
			});
			proxy.getReceiver === proxy // true
		* 如果一个属性不可配置(configurable)和不可写(writable),则该属性不能被代理,通过 Proxy 对象访问该属性会报错
	
	set(target, propKey, value, receiver)
		* 拦截对象属性的设置,比如proxy.foo = v 或 proxy['foo'] = v
		* 如果目标对象自身的某个属性,不可写或不可配置,那么set方法将不起作用
		* 返回布尔值
		
	has(target, propKey)
		* 拦截propKey in proxy的操作
		* 这个方法会生效,典型的操作就是in运算符
		* 原对象不可配置或者禁止扩展,这时has拦截会报错
		* has方法拦截的是HasProperty操作,而不是HasOwnProperty操作,即has方法不判断一个属性是对象自身的属性,还是继承的属性
		* 虽然for...in循环也用到了in运算符,但是has拦截对for...in循环不生效
		* 返回布尔值
		
	deleteProperty(target, propKey)
		* 拦截delete proxy[propKey]的操作
		* 如果这个方法抛出错误或者返回false,当前属性就无法被delete命令删除
		* 目标对象自身的不可配置(configurable)的属性,不能被deleteProperty方法删除,否则报错
		* 返回一个布尔值
		
	ownKeys(target)
		* 拦截Object.getOwnPropertyNames(proxy),Object.getOwnPropertySymbols(proxy),Object.keys(proxy)
		* 返回一个数组,该方法返回目标对象所有自身的属性的属性名,而Object.keys()的返回结果仅包括目标对象自身的可遍历属性
		* ownKeys方法返回的数组成员,只能是字符串或 Symbol 值,如果有其他类型的值,或者返回的根本不是数组,就会报错
		* 如果目标对象自身包含不可配置的属性,则该属性必须被ownKeys方法返回,否则报错
		* 具体来说,拦截以下操作
			Object.getOwnPropertyNames()
			Object.getOwnPropertySymbols()
			Object.keys()
			* 使用Object.keys方法时,有三类属性会被ownKeys方法自动过滤,不会返回
				目标对象上不存在的属性
				属性名为 Symbol 值
				不可遍历(enumerable)的属性
		
	getOwnPropertyDescriptor(target, propKey)
		* 拦截Object.getOwnPropertyDescriptor(proxy, propKey)
		* 返回属性的描述对象
		
	defineProperty(target, propKey, propDesc)
		* 拦截Object.defineProperty(proxy, propKey, propDesc）,Object.defineProperties(proxy, propDescs)
		* defineProperty方法返回false,会导致添加新属性会抛出错误
		* 如果目标对象不可扩展(extensible),则defineProperty不能增加目标对象上不存在的属性,否则会报错
		* 如果目标对象的某个属性不可写(writable)或不可配置(configurable),则defineProperty方法不得改变这两个设置
		* 返回布尔值
				
	preventExtensions(target)
		* 拦截Object.preventExtensions(proxy)
		* 该方法必须返回一个布尔值,否则会被自动转为布尔值
		* 返回布尔值
		
	getPrototypeOf(target)
		* 拦截Object.getPrototypeOf(proxy)
		* 具体来说,拦截下面这些操作
			Object.prototype.__proto__
			Object.prototype.isPrototypeOf()
			Object.getPrototypeOf()
			Reflect.getPrototypeOf()
			instanceof
		* getPrototypeOf方法的返回值必须是对象或者null,否则报错
		* 如果目标对象不可扩展(extensible), getPrototypeOf方法必须返回目标对象的原型对象
		* 返回一个对象
			
	isExtensible(target)
		* 拦截Object.isExtensible(proxy)
		* 该方法只能返回布尔值,否则返回值会被自动转为布尔值
		* 这个方法有一个强限制,它的返回值必须与目标对象的isExtensible属性保持一致,否则就会抛出错误
		* 返个布尔值
			
	setPrototypeOf(target, proto)
		* 拦截Object.setPrototypeOf(proxy, proto)
		* 该方法只能返回布尔值,否则会被自动转为布尔值
		* 如果目标对象不可扩展(extensible),setPrototypeOf方法不得改变目标对象的原型
		* 返回布尔值,如果目标对象是函数,那么还有两种额外操作可以拦截
		
	apply(target, object, args)
		* 拦截 Proxy 实例作为函数调用的操作,比如proxy(...args),proxy.call(object, ...args),proxy.apply(...)
		* 三个参数,分别是目标对象,目标对象的上下文对象(this),目标对象的参数数组
		* 直接调用Reflect.apply方法,也会被拦截
		
	construct(target, args)
		* 拦截 Proxy 实例作为构造函数调用的操作,比如new proxy(...args)
		* 接受两个参数,target:目标对象,args:构建函数的参数对象
		* construct方法返回的必须是一个对象,否则会报错
