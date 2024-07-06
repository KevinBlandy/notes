-----------------
Proxy
-----------------
	# 创建一个对象的代理，从而实现基本操作的拦截和自定义（如属性查找、赋值、枚举、函数调用等）。
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Proxy

	# 构造器
		new Proxy(target, handler)
		
			target
				* 要使用 Proxy 包装的目标对象（可以是任何类型的对象，包括原生数组，函数，甚至另一个代理）。
			
			handler
				* 一个对象，其属性是定义了在对代理执行操作时的行为的函数。
	


-----------------
this
-----------------
	# Proxy 代理的情况下，目标对象内部的 this 关键字会指向 Proxy 代理。

		const target = {
			m () {
				console.log(this === proxy);
			}
		};

		const handler = {};

		const proxy = new Proxy(target, handler);

		target.m() // false
		proxy.m()  // true

	# 有些原生对象的内部属性，只有通过正确的 this 才能拿到，所以 Proxy 也无法代理这些原生对象的属性。

		const target = new Date();
		const handler = {};
		const proxy = new Proxy(target, handler);

		proxy.getDate();
		// TypeError: this is not a Date object.

		* getDate()方法只能在Date对象实例上面拿到，如果this不是Date对象实例就会报错。
		* 这时，this 绑定原始对象，就可以解决这个问题。

			const target = new Date('2015-01-01');
			const handler = {
			  get(target, prop) {
				if (prop === 'getDate') {
					// 绑定 this 到原始的对象
				  return target.getDate.bind(target);
				}
				return Reflect.get(target, prop);
			  }
			};
			const proxy = new Proxy(target, handler);

			proxy.getDate() // 1
		
	# Proxy 拦截函数内部的 this，指向的是 handler 对象。
		const handler = {
			name: 'Hanadler Interceptor',
			get(){
				console.log(this.name);
			}
		}

		new Proxy({}, handler).foo;  // Hanadler Interceptor

-----------------
handler
-----------------
	handler.apply(target, thisArg, argumentsList)
		* 拦截函数的调用。

			target
				* 目标函数，必须是可被调用的，它必须是一个函数对象。
			
			thisArg
				* 被调用时的上下文对象。
			
			argumentsList
				* 被调用时的参数数组。

		* 可以返回任何值。

	handler.construct(target, args, newTarget)
		* 拦截 Proxy 实例作为构造函数调用的操作，比如 new proxy(...args)。
		* nstruct() 方法返回的必须是一个对象，否则会报错。
			
	handler.defineProperty(target, propKey, propDesc)
		* 拦截 Object.defineProperty(proxy, propKey, propDesc）、Object.defineProperties(proxy, propDescs)，返回一个布尔值。

	handler.deleteProperty(target, propKey)
		* 拦截 delete proxy[propKey] 的操作，返回一个布尔值。

	handler.get(target, propKey, receiver)
		* 拦截对象属性的读取，比如proxy.foo和proxy['foo']。

	handler.getOwnPropertyDescriptor(target, propKey)
		* 拦截 Object.getOwnPropertyDescriptor(proxy, propKey)，返回属性的描述对象。

	handler.getPrototypeOf(target)
		* 拦截 Object.getPrototypeOf(proxy)，返回一个对象。

	handler.has(target, propKey)
		* 拦截 propKey in proxy 的操作，返回一个布尔值。

	handler.isExtensible(target)
		* 拦截 Object.isExtensible(proxy)，返回一个布尔值。

	handler.ownKeys(target)
		* 拦截 Object.getOwnPropertyNames(proxy)、Object.getOwnPropertySymbols(proxy)、Object.keys(proxy)、for...in循环，返回一个数组。
		* 该方法返回目标对象所有自身的属性的属性名，而 Object.keys() 的返回结果仅包括目标对象自身的可遍历属性。

	handler.preventExtensions(target)
		* 拦截 Object.preventExtensions(proxy)，返回一个布尔值。

	handler.set(target, propKey, value, receiver)
		* 拦截对象属性的设置，比如 proxy.foo = v 或 proxy['foo'] = v，返回一个布尔值。

	handler.setPrototypeOf(target, proto)
		* 拦截 Object.setPrototypeOf(proxy, proto)，返回一个布尔值。如果目标对象是函数，那么还有两种额外操作可以拦截。

-----------------
static
-----------------

	Proxy.revocable(target, handler);
		* 创建一个可撤销的代理对象，参数和构造函数没任何区别
		* 返回一个包含了代理对象本身和它的撤销方法的可撤销 Proxy 对象。
		* 其结构为： {"proxy": proxy, "revoke": revoke}
			* proxy 表示新生成的代理对象本身，和用一般方式 new Proxy(target, handler) 创建的代理对象没什么不同，只是它可以被撤销掉。
			* revoke 撤销方法，调用的时候不需要加任何参数，就可以撤销掉和它一起生成的那个代理对象，即 proxy。

		* 撤销代理之后再调用代理会抛出TypeError。
