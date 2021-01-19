--------------------------------
Set 和 Map 数据结构				|
--------------------------------
	1,Set
	2,WeakSet
	3,Map
	4,WeakMap

--------------------------------
Set								|
--------------------------------
	# ES6 提供了新的数据结构 Set
		* 它类似于数组,但是成员的值都是唯一的,没有重复的值
		* 跟java的set差不多是一个德行
	
	# Set 本身是一个构造函数,用来生成 Set 数据结构
		const s = new Set();
		[2, 3, 5, 4, 5, 2, 2].forEach(x => s.add(x));
		for (let i of s) {
			console.log(i);
		}
		// 2 3 5 4		没有重复的
	
	# Set 函数可以接受一个数组(或者具有 iterable 接口的其他数据结构)作为参数,用来初始化
		// 例一
		const set = new Set([1, 2, 3, 4, 4]);
		[...set]			//还可以去除数组的重复成员
		// [1, 2, 3, 4]
		
		// 例二
		const items = new Set([1, 2, 3, 4, 5, 5, 5, 5]);
		items.size // 5
		
		// 例三
		const set = new Set(document.querySelectorAll('div'));
		set.size // 56
		
		// 类似于
		const set = new Set();
		document.querySelectorAll('div').forEach(div => set.add(div));
		
		set.size // 56
			
		* 例一和例二都是Set函数接受数组作为参数,例三是接受类似数组的对象作为参数
	
	# 向 Set 加入值的时候,不会发生类型转换,所以5和"5"是两个不同的值
		* Set 内部判断两个值是否不同,使用的算法叫做"Same-value-zero equality"
		* 它类似于精确相等运算符(===),主要的区别是NaN等于自身,而精确相等运算符认为NaN不等于自身
	
		let set = new Set();
		let a = NaN;
		let b = NaN;
		set.add(a);
		set.add(b);
		console.log(set); // Set(1) {NaN}
	
		* Set 实例添加了两个NaN,但是只能加入一个,这表明,在 Set 内部,两个NaN是相等

--------------------------------
Set 实例的属性和方法			|
--------------------------------
	# 操作数据系列的方法

		Set.prototype.constructor
			* 构造函数,默认就是Set函数
		Set.prototype.size
			* 返回Set实例的成员总数
		
		
		add(value)
			* 添加某个值,返回 Set 结构本身
		delete(value)
			* 删除某个值,返回一个布尔值,表示删除是否成功
		has(value)
			* 返回一个布尔值,表示该值是否为Set的成员
		clear()
			* 清除所有成员,没有返回值

	# 遍历数据的方法
		keys()
			* 返回键名的遍历器
		values()
			* 返回键值的遍历器
		entries()
			* 返回键值对的遍历器
		forEach()
			* 使用回调函数遍历每个成员
		
		* 以上前仨方法都是返回迭代器
		* 由于 Set 结构没有键名,只有键值(或者说键名和键值是同一个值),所以keys方法和values方法的行为完全一致
		* entries方法返回的遍历器,同时包括键名和键值,所以每次输出一个数组,它的两个成员完全相等

	# Set的遍历顺序就是插入顺序,这个特性有时非常有用,比如使用 Set 保存一个回调函数列表,调用时就能保证按照添加顺序调用
	# Set 结构的实例默认可遍历,它的默认遍历器生成函数就是它的values方法
		Set.prototype[Symbol.iterator] === Set.prototype.values
		// true
		* 这意味着,可以省略values方法,直接用for...of循环遍历 Set

	# Set 结构的实例与数组一样,也拥有forEach方法,用于对每个成员执行某种操作,没有返回值
		set = new Set([1, 4, 9]);
		set.forEach((value, key) => console.log(key + ' : ' + value))
		// 1 : 1
		// 4 : 4
		// 9 : 9
	
	# 遍历应用
		* 扩展运算符(...)内部使用for...of循环,所以也可以用于 Set 结构
			let set = new Set(['red', 'green', 'blue']);
			let arr = [...set];
			// ['red', 'green', 'blue']
		
		* 扩展运算符和 Set 结构相结合,就可以去除数组的重复成员
			let arr = [3, 5, 2, 2, 5, 5];
			let unique = [...new Set(arr)];
			// [3, 5, 2]
		
		* 数组的map和filter方法也可以间接用于 Set 了
			let set = new Set([1, 2, 3]);
			set = new Set([...set].map(x => x * 2));
			// 返回Set结构：{2, 4, 6}

			let set = new Set([1, 2, 3, 4, 5]);
			set = new Set([...set].filter(x => (x % 2) == 0));
			// 返回Set结构：{2, 4}
		
		* 用 Set 可以很容易地实现并集(Union),交集(Intersect)和差集(Difference)
			let a = new Set([1, 2, 3]);
			let b = new Set([4, 3, 2]);
		
			// 并集
			let union = new Set([...a, ...b]);
			// Set {1, 2, 3, 4}
		
			// 交集
			let intersect = new Set([...a].filter(x => b.has(x)));
			// set {2, 3}
		
			// 差集
			let difference = new Set([...a].filter(x => !b.has(x)));
			// Set {1}
		
		* 如果想在遍历操作中,同步改变原来的 Set 结构,目前没有直接的方法,但是有两种变通方法
			* 一种是利用原 Set 结构映射出一个新的结构,然后赋值给原来的 Set 结构
			* 另一种是利用Array.from方法
			// 方法一
			let set = new Set([1, 2, 3]);
			set = new Set([...set].map(val => val * 2));
			// set的值是2, 4, 6

			// 方法二
			let set = new Set([1, 2, 3]);
			set = new Set(Array.from(set, val => val * 2));
			// set的值是2, 4, 6
		

--------------------------------
WeakSet							|
--------------------------------
	# WeakSet 结构与 Set 类似,也是不重复的值的集合,但是,它与 Set 有两个区别
		1,WeakSet 的成员只能是对象,而不能是其他类型的值
			const ws = new WeakSet();
			ws.add(1)
			// TypeError: Invalid value used in weak set
			ws.add(Symbol())
			// TypeError: invalid value used in weak set
		
		2,WeakSet 中的对象都是弱引用
			* 即垃圾回收机制不考虑 WeakSet 对该对象的引用
			* 也就是说,如果其他对象都不再引用该对象,那么垃圾回收机制会自动回收该对象所占用的内存,不考虑该对象还存在于 WeakSet 之中
	
	# WeakSet 的成员是不适合引用的
		* 因为它会随时消失,另外,由于 WeakSet 内部有多少个成员,取决于垃圾回收机制有没有运行,运行前后很可能成员个数是不一样的
		* 而垃圾回收机制何时运行是不可预测的,因此 ES6 规定 WeakSet 不可遍历
	
	# 语法
		* WeakSet 是一个构造函数,可以使用new命令,创建 WeakSet 数据结构
			const ws = new WeakSet();
		
		* WeakSet 可以接受一个数组或类似数组的对象作为参数
			* 实际上,任何具有 Iterable 接口的对象,都可以作为 WeakSet 的参数
			* 该数组的所有成员,都会自动成为 WeakSet 实例对象的成员
			const a = [[1, 2], [3, 4]];
			const ws = new WeakSet(a);
			// WeakSet {[1, 2], [3, 4]}

			* '是数组的成员成为 WeakSet 的成员,而不是数组本身,这意味着,数组的成员只能是对象'
			const b = [3, 4];		//数据成员非对象,报错
			const ws = new WeakSet(b);
			// Uncaught TypeError: Invalid value used in weak set(…)

		* WeakSet 结构有以下三个方法
			WeakSet.prototype.add(value)
				* 向 WeakSet 实例添加一个新成员

			WeakSet.prototype.delete(value)
				* 清除 WeakSet 实例的指定成员

			WeakSet.prototype.has(value)
				* 返回一个布尔值,表示某个值是否在 WeakSet 实例之中
			
		* WeakSet 没有size属性,没有办法遍历它的成员

	
	# WeakSet 的一个用处,是储存 DOM 节点,而不用担心这些节点从文档移除时,会引发内存泄漏
	# demo
		const foos = new WeakSet()
		
		class Foo {
			constructor() {
				foos.add(this)
			}
			method () {
				if (!foos.has(this)) {
					throw new TypeError('Foo.prototype.method 只能在Foo的实例上调用！');
				}
			}
		}

		* 上面代码保证了Foo的实例方法,只能在Foo的实例上调用
		* 这里使用 WeakSet 的好处是,foos对实例的引用,不会被计入内存回收机制,所以删除实例的时候,不用考虑foos,也不会出现内存泄漏
	

--------------------------------
Map								|
--------------------------------
	# JavaScript 的对象(Object),本质上是键值对的集合(Hash 结构),但是传统上只能用字符串当作键,这给它的使用带来了很大的限制
		const data = {};
		const element = document.getElementById('myDiv');
		
		//本意是使用 dom 节点对象作为键
		data[element] = 'metadata';
		//实际上会被转换为字符串
		data['[object HTMLDivElement]'] // "metadata"
	
	# ES6 提供了 Map 数据结构
		* 它类似于对象,也是键值对的集合
		* "键"的范围不限于字符串,各种类型的值(包括对象)都可以当作键
		* Object 结构提供了"字符串—值"的对应,Map 结构提供了"值—值"的对应,是一种更完善的 Hash 结构实现
		* 如果需要"键值对"的数据结构,Map 比 Object 更合适
		* 如果对同一个键多次赋值,后面的值将覆盖前面的值
		* 如果读取一个不存在的键,返回undefined
		* Map 的键实际上是跟内存地址绑定的,只要内存地址不一样,就视为两个键
		* 如果 Map 的键是一个简单类型的值(数字,字符串,布尔值),则只要两个值严格相等,Map 将其视为一个键
			* 0和-0就是一个键
			* 布尔值true和字符串true则是两个不同的键
			* undefined和null也是两个不同的键,虽然NaN不严格相等于自身,但 Map 将其视为同一个键(null和undefined也可以作为键)

		const m = new Map();
		const o = {p: 'Hello World'};
		m.set(o, 'content');
		m.get(o); 		// "content"
		m.has(o);		// true
		m.delete(o);	// true
		m.has(o);		// false
	
	# Map的构造,支持key-value的数据格式
		const map = new Map([
			['name', '张三'],
			['title', 'Author']
		]);

		map.size // 2
		map.has('name') // true
		map.get('name') // "张三"
		map.has('title') // true
		map.get('title') // "Author"

		* 实际上执行的是下面的算法

		[['name', '张三'],['title', 'Author']].forEach(i => void map.set(i[0],i[1]));

	
	# 事实上,不仅仅是数组,任何具有 Iterator 接口,且每个成员都是一个双元素的数组的数据结构(详见《Iterator》一章)都可以当作Map构造函数的参数
		* 这就是说,Set和Map都可以用来生成新的 Map
		const set = new Set([
			['foo', 1],
			['bar', 2]
		]);
		//使用值为kv结构的set来构建map
		const m1 = new Map(set);
		m1.get('foo') // 1
		
		//使用已经存在的map来构建map
		const m2 = new Map([['baz', 3]]);
		const m3 = new Map(m2);
		m3.get('baz') // 3
	
	# 实例的属性和操作方法
		size
			* 返回成员数量
	
		set(k,v)
			* 添加一个键值
			* 该方法返回 this,所以可以使用链式调用
				let map = new Map()
				.set(1, 'a')
				.set(2, 'b')
				.set(3, 'c');
		
		get(k)
			* get方法读取key对应的键值,如果找不到key,返回undefined
		
		has(k)
			* 判断是否有key
		
		delete(k)
			* 删除指定的Key,删除成功返回 true,删除失败返回 false
		
		clear()
			* 清空
		
		keys()
			* 返回键名的遍历器
		values()
			* 返回键值的遍历器
		entries()
			* 返回所有成员的遍历器
				for (let item of map.entries()) {
					console.log(item[0], item[1]);
				}
			* Map 结构的默认遍历器接口(Symbol.iterator属性)就是entries方法
				for (let [key, value] of map) {
					console.log(key, value);
				}
		forEach()
			* 遍历 Map 的所有成员
			* Map 的遍历顺序就是插入顺序
				map.forEach(function(value, key, map) {
				  console.log("Key: %s, Value: %s", key, value);
				});
		
	
	# Map 结构转为数组结构,比较快速的方法是使用扩展运算符(...)
		const map = new Map([
			[1, 'one'],
			[2, 'two'],
			[3, 'three'],
		]);
		[...map.keys()]
		// [1, 2, 3]
		[...map.values()]
		// ['one', 'two', 'three']
		[...map.entries()]
		// [[1,'one'], [2, 'two'], [3, 'three']]
		[...map]
		// [[1,'one'], [2, 'two'], [3, 'three']]
	
	# 结合数组的map方法,filter方法,可以实现 Map 的遍历和过滤(Map 本身没有map和filter方法)
		const map0 = new Map()
			.set(1, 'a')
			.set(2, 'b')
			.set(3, 'c');

		const map1 = new Map(
			[...map0].filter(([k, v]) => k < 3)
		);
		// 产生 Map 结构 {1 => 'a', 2 => 'b'}

		const map2 = new Map(
			[...map0].map(([k, v]) => [k * 2, '_' + v])
		);
		// 产生 Map 结构 {2 => '_a', 4 => '_b', 6 => '_c'}
	
	# 与其他数据结构的互相转换
		* Map 转为数组,扩展运算符
			const myMap = new Map()
			  .set(true, 7)
			  .set({foo: 3}, ['abc']);
			[...myMap]
			// [ [ true, 7 ], [ { foo: 3 }, [ 'abc' ] 
		
		* 数组 转为 Map
			new Map([
			  [true, 7],
			  [{foo: 3}, ['abc']]
			])
		
		* Map 转为对象
			* 如果所有 Map 的键都是字符串,它可以无损地转为对象
			function strMapToObj(strMap) {
				let obj = Object.create(null);
				for (let [k,v] of strMap) {
					obj[k] = v;
				}
				return obj;
			}
			const myMap = new Map()
			.set('yes', true)
			.set('no', false);
			strMapToObj(myMap)
		// { yes: true, no: false }
	
		* 对象转为map
			function objToStrMap(obj) {
				let strMap = new Map();
				for (let k of Object.keys(obj)) {
					strMap.set(k, obj[k]);
				}
				return strMap;
			}
			objToStrMap({yes: true, no: false})
			// Map {"yes" => true, "no" => false}
		
		* Map 转为 JSON
			* Map 转为 JSON 要区分两种情况
			* 一种情况是,Map 的键名都是字符串,这时可以选择转为对象 JSON
				function strMapToJson(strMap) {
					return JSON.stringify(strMapToObj(strMap));
				}
				let myMap = new Map().set('yes', true).set('no', false);
				strMapToJson(myMap)
				// '{"yes":true,"no":false}'
			* 另一种情况是,Map 的键名有非字符串,这时可以选择转为数组 JSON
				function mapToArrayJson(map) {
					return JSON.stringify([...map]);
				}
				let myMap = new Map().set(true, 7).set({foo: 3}, ['abc']);
				mapToArrayJson(myMap)
				// '[[true,7],[{"foo":3},["abc"]]]'
		
		* JSON 转为 Map
			* JSON 转为 Map,正常情况下,所有键名都是字符串
				function jsonToStrMap(jsonStr) {
					return objToStrMap(JSON.parse(jsonStr));
				}
				jsonToStrMap('{"yes": true, "no": false}')
				// Map {'yes' => true, 'no' => false}
			
			* 有一种特殊情况,整个 JSON 就是一个数组,且每个数组成员本身,又是一个有两个成员的数组
			* 这时,它可以一一对应地转为 Map,这往往是 Map 转为数组 JSON 的逆操作
				function jsonToMap(jsonStr) {
					return new Map(JSON.parse(jsonStr));
				}
				jsonToMap('[[true,7],[{"foo":3},["abc"]]]')
				// Map {true => 7, Object {foo: 3} => ['abc']}
		
------------------------------------
WeakMap								|
------------------------------------
	# WeakMap结构与Map结构类似,也是用于生成键值对的集合
	# WeakMap与Map的区别有两点
		1,WeakMap只接受对象作为键名(null除外),不接受其他类型的值作为键名
			const map = new WeakMap();
			map.set(1, 2)
			// TypeError: 1 is not an object!					使用数字,报错
			map.set(Symbol(), 2)	
			// TypeError: Invalid value used as weak map key	使用 Symbol, 报错
			map.set(null, 2)
			// TypeError: Invalid value used as weak map key	使用null,报错
		
		2,WeakMap的键名所指向的对象,不计入垃圾回收机制
			* 如果你要往对象上添加数据,又不想干扰垃圾回收机制,就可以使用 WeakMap
			* 一个典型应用场景是,在网页的 DOM 元素上添加数据,就可以使用WeakMap结构,当该 DOM 元素被清除,其所对应的WeakMap记录就会自动被移除
				const wm = new WeakMap();
				const element = document.getElementById('example');
				wm.set(element, 'some information');
				wm.get(element) // "some information"
			
	
	# WeakMap的专用场合就是,它的键所对应的对象m可能会在将来消失,WeakMap结构有助于防止内存泄漏
	# 注意,WeakMap 弱引用的只是键名,而不是键值,键值依然是正常引用
		const wm = new WeakMap();
        let key = {};
        let obj = {foo: 1};

        wm.set(key, obj);
        obj = null;
        wm.get(key)
        // Object {foo: 1}
        //键值obj是正常引用,所以,即使在 WeakMap 外部消除了obj的引用,WeakMap 内部的引用依然存在
	

	# WeakMap 的语法
		* WeakMap 与 Map 在 API 上的区别主要是两个
			* 一是没有遍历操作(即没有key(),values()和entries()方法),也没有size属性
			* 二是无法清空,即不支持clear方法
		
		* 因此,WeakMap只有四个方法可用:get(),set(),has(),delete()
	
	# WeakMap 的示例
		..
	
	# 用途
		* WeakMap 应用的典型场合就是 DOM 节点作为键名

			let myElement = document.getElementById('logo');

			let myWeakmap = new WeakMap();

			myWeakmap.set(myElement, {timesClicked: 0});

			myElement.addEventListener('click', function() {
				let logoData = myWeakmap.get(myElement);
				logoData.timesClicked++;
			}, false);
					
			/**
				myElement是一个 DOM 节点,每当发生click事件,就更新一下状态
				我们将这个状态作为键值放在 WeakMap 里,对应的键名就是myElement,一旦这个 DOM 节点删除,该状态就会自动消失,不存在内存泄漏风险
			**/
		
		* WeakMap 的另一个用处是部署私有属性
			const _counter = new WeakMap();
			const _action = new WeakMap();

			class Countdown {
				constructor(counter, action) {
					_counter.set(this, counter);
					_action.set(this, action);
				}
				dec() {
					let counter = _counter.get(this);
					if (counter < 1) return;
					counter--;
					_counter.set(this, counter);
					if (counter === 0) {
						_action.get(this)();
					}
				}
			}

			const c = new Countdown(2, () => console.log('DONE'));

			c.dec()
			c.dec()
			// DONE

			//Countdown类的两个内部属性_counter和_action,是实例的弱引用,所以如果删除实例,它们也就随之消失,不会造成内存泄漏。
				


		
		
			

			

