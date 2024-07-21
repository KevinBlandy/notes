-----------------------
Object
-----------------------
	# 类似于 Java 中的 Object

	# 构造函数

		Object(value)

		* 另外，Object构造函数作为一个工厂方法，能够根据传入值的类型返回相应原始值包装类型的实例。

			let obj = new Object("some text");
			console.log(obj instanceof String);   // true

			// 传给Object的是字符串，则会创建一个String的实例。
			// 如果是数值，则会创建Number的实例。
			// 布尔值则会得到Boolean的实例。
		

-----------------------
实例
-----------------------
	Object.prototype.constructor
	Object.prototype.__proto__已弃用
	Object.prototype.__defineGetter__()已弃用
	Object.prototype.__defineSetter__()已弃用
	Object.prototype.__lookupGetter__()已弃用
	Object.prototype.__lookupSetter__()已弃用

	Object.prototype.hasOwnProperty(prop)
		* 返回一个布尔值，表示对象自有属性（而不是继承来的属性）中是否具有指定的属性 prop。

	Object.prototype.isPrototypeOf(object)
		* 检查一个对象 object 是否存在于另一个对象的原型链中。
		* 在传入参数 object 的 [[Prototype]] 指向调用它的对象 this 时返回 true 。
	
	Object.prototype.propertyIsEnumerable()
	Object.prototype.toLocaleString()
	Object.prototype.toString()
	Object.prototype.valueOf()

-----------------------
静态
-----------------------
	Object.assign(target, ...sources)
		* 将源对象（source）的所有可枚举、非继承属性复制到目标对象（target），浅拷贝。
		* 后面的同名属性会覆盖前面的

	Object.create(proto, propertiesObject)
		* 以一个现有对象作为原型，创建一个新对象。
		proto
			* 新创建对象的原型对象。

		propertiesObject
			* 可选，如果该参数被指定且不为 undefined，则该传入对象可枚举的自有属性将为新创建的对象添加具有对应属性名称的属性描述符。
			* 这些属性对应于 Object.defineProperties() 的第二个参数。

	Object.defineProperties(obj, props)
		* defineProperty 的升级版，可以一次性定义多个属性值
			obj
				* 在其上定义或修改属性的对象。
			
			props
				* 一个对象，其中每个 key 表示要定义或修改的属性的名称，每个值是描述该属性的对象。


	Object.defineProperty(obj, prop, descriptor)
		* 给指定的 obj 对象设置一个 prop 属性，该属性指定 descriptor 等描述

			obj
				* 要定义属性的对象。

			prop
				* 一个字符串或 Symbol，指定了要定义或修改的属性键。

			descriptor
				* 要定义或修改的属性的描述符，是一个对象。有如下属性。
			
				configurable: false
					* 是否可以从对象中删除以及其特性（除了 value 和 writable）是否可以更改。

				enumerable: false
					* 该属性是否可以被枚举出来
					* 是否可以被 Object.assign() 或展开运算符所考虑。
					* 对于非 Symbol 属性，它还定义了属性是否会在 for...in 循环和 Object.keys() 中显示。
				
				value: undefined
					* 属性的值
				
				writable: false
					* 属性是否可以被修改，即通过 = 赋值
					* 再次调用 Object.defineProperty() 并修改任何非 writable 属性会导致错误：

				get: undefined
					* 用作属性 getter 的函数，如果没有 getter 则为 undefined。
		
				set: undefined
					* 用作属性 setter 的函数，如果没有 setter 则为 undefined。
		
		* 如果描述符没有 value、writable、get 和 set 键中的任何一个，它将被视为数据描述符。
		* 如果描述符同时具有 [value 或 writable] 和 [get 或 set] 键，则会抛出异常。

	Object.entries(obj)
		* 返回一个数组，包含给定对象自有\可枚举\非 Symbol 属性的键值对。
		* 非字符串属性 KEY 会被转换为字符串输出。

			onst obj = { foo: "bar", baz: 42 };
			console.log(Object.entries(obj)); // [ ['foo', 'bar'], ['baz', 42] ]
		
		* 该方法的一个用处是配合 URLSearchParams 对象，将查询字符串转为对象。
			Object.fromEntries(new URLSearchParams('foo=bar&baz=qux'))
			// { foo: "bar", baz: "qux" }

	Object.freeze(obj)
		* 冻结一个对象
		* 被冻结的对象不能再被更改：不能添加新的属性，不能移除现有的属性，不能更改它们的可枚举性、可配置性、可写性或值，对象的原型也不能被重新指定。

	Object.fromEntries(iterable)
		* 将键值对列表转换为一个对象，是 entries 的逆向操作
			iterable
				* 包含对象列表的可迭代对象，例如 Array 或者 Map。每个对象都要有两个属性。
					[0] 表示属性键的字符串或者 Symbol。
					[1] 属性值。
				* 该对象被实现为二元数组，第一个元素是属性键，第二个元素是属性值。

	Object.getOwnPropertyDescriptor(obj, prop)
		* 返回给定对象上指定 propr 的描述符对象，不检索原型链。

	Object.getOwnPropertyDescriptors(obj)
		* 返回给定对象的所有自有属性描述符，不检索原型链。
		* 返回对象的 key 为 obj 的属性名称，值为属性的描述符。

	Object.getOwnPropertyNames()
	Object.getOwnPropertySymbols(obj)
		* 返回一个包含给定对象所有自有 Symbol 属性的数组。

	Object.getPrototypeOf(obj)
		* 返回 obj 对象的原型对象

	Object.groupBy()
	Object.hasOwn(obj, prop)
		* 判断 prop 是否为 obj 自身的属性。
		* 和对象实例的 hasOwnProperty() 方法一样

	Object.is(value1, value2)
		* 比较两个参数是否为相同的值。
		* ES5 比较两个值是否相等,只有两个运算符
			* 相等运算符(==)和严格相等运算符(===)
			* 它们都有缺点,前者会自动转换数据类型,后者的NaN不等于自身,以及+0等于-0
	
		* javaScript 缺乏一种运算,在所有环境中,只要两个值是一样的,它们就应该相等
		* ES6 提出"Same-value equality"(同值相等)算法,用来解决这个问题
		* Object.is就是部署这个算法的新方法,它用来比较两个值是否严格相等,'与严格比较运算符(===)的行为基本一致'
			
			Object.is('foo', 'foo')
			// true
			Object.is({}, {})
			// false
		
		* 不同之处只有两个:一是+0不等于-0，二是NaN等于自身
				+0 === -0 		//true
				NaN === NaN 	// false
			
				Object.is(+0, -0) 	// false
				Object.is(NaN, NaN) // true

	Object.isExtensible()
	Object.isFrozen()
	Object.isSealed()
	Object.keys(obj)
		* 返回一个数组，成员是参数对象自身的（不含继承的）所有可遍历（enumerable）属性的键名。

	Object.preventExtensions(obj)
		* 防止新属性被添加到对象中（即防止该对象被扩展）。它还可以防止对象的原型被重新指定。
			obj
				* 将要变得不可扩展的对象。

	Object.seal()
	Object.Object.setPrototypeOf(obj, prototype)
		* 将一个指定对象 obj 的原型（即内部的 [[Prototype]] 属性）设置为另一个对象 prototype 或者 null。
		* Object.setPrototypeOf() 可能会严重影响代码性能，不建议使用 setPrototypeOf 来代替 extends。
	
	Object.values(obj)
		* 返回一个给定对象的自有\可枚举\非 Symbol 属性的值组成的数组。
		* 非字符串属性 KEY 会被转换为字符串输出。

			const obj = { foo: 'bar', baz: 42 };
			Object.values(obj)
			// ["bar", 42]
