-----------------------
Reflect
-----------------------
	# 反射 API
		* 它提供拦截 JavaScript 操作的方法。这些方法与 proxy handler 的方法相同。
		* Reflect 不是一个函数对象，因此它是不可构造的。
	
		* Reflect 对象提供的静态方法，与 proxy handler 方法的命名相同。
----------------------
static
----------------------
	Reflect.apply(target, thisArgument, argumentsList)
		* 等同于 Function.prototype.apply.call(func, thisArg, args)，用于绑定 this 对象后执行给定函数。

	Reflect.construct(target, argumentsList[, newTarget])
		* 等同 于new target(...args)，这提供了一种不使用 new，来调用构造函数的方法。

	Reflect.defineProperty(target, propertyKey, attributes)
		* 基本等同于 Object.defineProperty，用来为对象定义属性。
		* 未来，后者会被逐渐废除，请从现在开始就使用 Reflect.defineProperty 代替它。

	Reflect.deleteProperty(target, propertyKey)
		* 等同于 delete obj[name]，用于删除对象的属性。

	Reflect.get(target, propertyKey[, receiver])
		* 查找并返回 target 对象的 propertyKey 属性，如果没有该属性，则返回 undefined。
		* 如果 propertyKey 属性部署了读取函数（getter），则读取函数的 this 绑定receiver
		
	Reflect.getOwnPropertyDescriptor(target, propertyKey)
		* 基本等同于 Object.getOwnPropertyDescriptor，用于得到指定属性的描述对象，将来会替代掉后者。

	Reflect.getPrototypeOf(target)
		* 用于读取对象的__proto__属性，对应 Object.getPrototypeOf(obj)。

	Reflect.has(target, propertyKey)
		* 对应 name in obj 里面的 in 运算符。

	Reflect.isExtensible(target)
		* 对应 Object.isExtensible，返回一个布尔值，表示当前对象是否可扩展。

	Reflect.ownKeys(target)
		* 用于返回对象的所有属性，基本等同于 Object.getOwnPropertyNames 与 Object.getOwnPropertySymbols 之和。

	Reflect.preventExtensions(target)
		* 对应 Object.preventExtensions 方法，用于让一个对象变为不可扩展。它返回一个布尔值，表示是否操作成功。

	Reflect.set(target, propertyKey, value[, receiver])
		* 设置 target 对象的 propertyKey 属性等于value。
		* 如果 propertyKey 属性设置了赋值（Setter）函数，则赋值函数的 this 绑定 receiver。

	Reflect.setPrototypeOf(target, prototype)
		* 用于设置目标对象的原型（prototype），对应 Object.setPrototypeOf(obj, newProto) 方法。
		* 它返回一个布尔值，表示是否设置成功。

