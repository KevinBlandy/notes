-----------------------------
Object
-----------------------------
	# 类似于 Java 中的 Object，是所有对象的父类
		* 严格来讲，ECMA-262 中对象的行为不一定适合 JavaScript 中的其他对象。
		* 比如浏览器环境中的 BOM 和 DOM 对象，都是由宿主环境定义和提供的宿主对象。而宿主对象不受 ECMA-262 约束，所以它们可能会也可能不会继承 Object。

		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Object
	
	# 创建 Object 
		let obj = new Object();
		let obj = new Object;  // 也灵，但强烈不推荐
	
	# 属性
		constructor
			* 用于创建当前对象的函数。对 Object 来说这个属性的值就是 Object() 函数。
		
		hasOwnProperty(propertyName)
			* 用于判断当前对象实例（不是原型）上是否存在给定的属性。
			* 要检查的属性名必须是字符串（如 o.hasOwnProperty("name")）或 Symbol。
		
		isPrototypeOf(object)
			* 用于判断当前对象是否为另一个对象的原型。
		
		propertyIsEnumerable(propertyName)
			* 用于判断给定的属性是否可以使用 for-in 语句枚举。
			* 与 hasOwnProperty() 一样，属性名必须是字符串。
		
		toLocaleString()
			* 返回对象的字符串表示，该字符串反映对象所在的本地化执行环境。
		
		toString()
			* 返回对象的字符串表示。❑
			
		valueOf()
			* 返回对象对应的字符串、数值或布尔值表示。
			* 通常与 toString() 的返回值相同。

