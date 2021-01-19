----------------------------
Reflect						|
----------------------------
	# 顾名思义,反射相关的一个静态类
	# Reflect对象与Proxy对象一样,也是 ES6 为了操作对象而提供的新 API,Reflect对象的设计目的有这样几个
		1,将Object对象的一些明显属于语言内部的方法(比如Object.defineProperty),放到Reflect对象上
			* 现阶段,某些方法同时在Object和Reflect对象上部署,未来的新方法将只部署在Reflect对象上
			* 也就是说,从Reflect对象上可以拿到语言内部的方法
		
		2,修改某些Object方法的返回结果,让其变得更合理
			* Object.defineProperty(obj, name, desc)在无法定义属性时,会抛出一个错误
			* Reflect.defineProperty(obj, name, desc)则会返回false
		
		3, 让Object操作都变成函数行为
			* 某些Object操作是命令式,比如name in obj和delete obj[name]
			* 而Reflect.has(obj, name)和Reflect.deleteProperty(obj, name)让它们变成了函数行为
		
		4,Reflect对象的方法与Proxy对象的方法一一对应,只要是Proxy对象的方法,就能在Reflect对象上找到对应的方法
			* 这就让Proxy对象可以方便地调用对应的Reflect方法,完成默认行为,作为修改行为的基础
			* 也就是说,不管Proxy怎么修改默认行为,你总可以在Reflect上获取默认行为

----------------------------
Reflect	- 方法				|
----------------------------
	# 一共13个静态方法('与Proxy对象的方法一一对应')
	# 大部分与Object.xxx 相同的方法都有一个不同点
		* Object.xxx 第一个参数不是对象,不会报错
		* Reflect.xxx 第一个参数不是对象,会报错

	apply(target, thisArg, args)
		* 等同于Function.prototype.apply.call(func, thisArg, args),用于绑定this对象后执行给定函数
		* 一般来说,如果要绑定一个函数的this对象,可以这样写fn.apply(obj, args)
			* 但是如果函数定义了自己的apply方法,就只能写成Function.prototype.apply.call(fn, obj, args)
			* 采用Reflect对象可以简化这种操作

	construct(target, args)
		* 方法等同于new target(...args),这提供了一种不使用new,来调用构造函数的方法
			function Greeting(name) {
			  this.name = name;
			}
			// new 的写法
			const instance = new Greeting('张三');
			// Reflect.construct 的写法
			const instance = Reflect.construct(Greeting, ['张三']);

	get(target, name, receiver)
		* 方法查找并返回target对象的name属性,如果没有该属性,则返回undefined
		* 如果name属性部署了读取函数(getter),则读取函数的this绑定receive
		* 如果第一个参数不是对象,Reflect.get方法会报错

	set(target, name, value, receiver)
		* 设置target对象的name属性等于value
		* 如果name属性设置了赋值函数,则赋值函数的this绑定receiver
		* 如果 Proxy 对象和 Reflect 对象联合使用,前者拦截赋值操作,后者完成赋值的默认行为,而且传入了 receiver,那么Reflect.set会触发Proxy.defineProperty拦截

	defineProperty(target, name, desc)
		* 方法基本等同于Object.defineProperty,用来为对象定义属性
		* 未来,后者会被逐渐废除,请从现在开始就使用Reflect.defineProperty代替它
		* 如果Reflect.defineProperty的第一个参数不是对象,就会抛出错误


	deleteProperty(target, name)
		* 等同于delete obj[name],用于删除对象的属性
		* 如果删除成功,或者被删除的属性不存在,返回true
		* 删除失败,被删除的属性依然存在,返回false

	has(target, name)
		* 对应name in obj里面的in运算符

	ownKeys(target)
		* 返回对象的所有属性,基本等同于Object.getOwnPropertyNames与Object.getOwnPropertySymbols之和

	isExtensible(target)
		* 对应Object.isExtensible,返回一个布尔值,表示当前对象是否可扩展

	preventExtensions(target)
		* 对应Object.preventExtensions 方法,用于让一个对象变为不可扩展
		* 它返回一个布尔值,表示是否操作成功

	getOwnPropertyDescriptor(target, name)
		* 基本等同于Object.getOwnPropertyDescriptor,用于得到指定属性的描述对象,将来会替代掉后者
		
	getPrototypeOf(target)
		* 用于读取对象的__proto__属性,对应Object.getPrototypeOf(obj)
		* Reflect.getPrototypeOf和Object.getPrototypeOf的一个区别是,如果参数不是对象,Object.getPrototypeOf会将这个参数转为对象,然后再运行,而Reflect.getPrototypeOf会报错

	setPrototypeOf(target, prototype)
		* 用于设置对象的__proto__属性,返回第一个参数对象,对应Object.setPrototypeOf(obj, newProto)
		* 如果第一个参数不是对象,Object.setPrototypeOf会返回第一个参数本身,而Reflect.setPrototypeOf会报错
		* 如果第一个参数是undefined或null,Object.setPrototypeOf和Reflect.setPrototypeOf都会报错
	
	