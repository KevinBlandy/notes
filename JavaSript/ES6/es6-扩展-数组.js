----------------------------
数组的扩展					|
----------------------------
	1,扩展运算符
	2,Array.from()
	3,Array.of()
	4,数组实例的 copyWithin()
	5,数组实例的 find() 和 findIndex()
	6,数组实例的 fill()
	7,数组实例的 entries(),keys() 和 values()
	8,数组实例的 includes()
	9,数组的空位

----------------------------
扩展运算符					|
----------------------------
	# 扩展运算符是三个.  (...),像rest参数的逆运算
		console.log(1,...[2,3])		//1 2 3
		* 就是把一个数组,解析为一个个的参数
	
	# 大都是用于函数参数
		function foo(...vs){
            let sum = 0;
            for(let x of vs){
                sum += x;
            }
            return sum;
        }
        console.log(foo(1,...[2,3]))		//6
	
	# 扩展运算符后面还可以放置表达式
		let x = true;
        const ARR = [...(x > 0 ? ['a'] : []), 'b',];
        log(ARR)        // ["a", "b"]

		* 如果扩展运算符后面是一个空数组，则不产生任何效果。
			[...[], 1]
			// [1]		
	
	# 替代函数的 apply 方法
		* 由于扩展运算符可以展开数组,所以不再需要apply方法,将数组转为函数的参数了
			// ES5 的写法
			function f(x, y, z) {
				// ...
			}
			var args = [0, 1, 2];
			//apply的第一个参数为 null 或者undifined,那么执行环境就是window/global
			//apply的第二个参数就是就是参数数组,这样就顺理成章的把参数作为一个传递过去了,并且把数组里面的每个选项解析到函数的每个形参
			f.apply(null, args);

			// ES6的写法
			function f(x, y, z) {
				// ...
			}
			let args = [0, 1, 2];
			f(...args);

			* demo
				 // ES5 的写法
				Math.max.apply(null, [14, 3, 77])
				// ES6 的写法
				Math.max(...[14, 3, 77])// 等同于Math.max(14, 3, 77);
		
				let arr = [1,2,3]
				arr.push(...'6555')		//字符串也是可以的
				console.log(arr);   //[1, 2, 3, "6", "5", "5", "5"]

				// ES5
				new (Date.bind.apply(Date, [null, 2015, 1, 1]))
				// ES6
				new Date(...[2015, 1, 1]);
	
	# 扩展运算还有可以用在字符串
		[...'hello']
		// [ "h", "e", "l", "l", "o" ]

		* 还能正确的识别Unicode字符
			'x\uD83D\uDE80y'.length // 4
			[...'x\uD83D\uDE80y'].length // 3
		* 正确返回字符串长度的函数
			function length(str) {
				return [...str].length;
			}

			length('x\uD83D\uDE80y') // 3
		* 凡是涉及到操作四个字节的 Unicode 字符的函数,都有这个问题,因此,最好都用扩展运算符改写
			let str = 'x\uD83D\uDE80y';

			str.split('').reverse().join('')
			// 'y\uDE80\uD83Dx'

			[...str].reverse().join('')
			// 'y\uD83D\uDE80x'
			* 如果不用扩展运算符,字符串的reverse操作就不正确
	
	# 实现了 Iterator  接口,都可以用扩展运算符转为真正的数组
		let nodeList = document.querySelectorAll('div');
		let array = [...nodeList];
		* uerySelectorAll方法返回的是一个nodeList对象,它不是数组,而是一个类似数组的对象
	
	# Map 和 Set 结构,Generator 函数
		* 扩展运算符内部调用的数据解构都是 Iterator 接口
		* 因此只要是实现了 Iterator 接口的对象,都可以使用扩展运算符
		* Map
			let map = new Map([
				[1, 'one'],
				[2, 'two'],
				[3, 'three'],
			]);

			let arr = [...map.keys()]; // [1, 2, 3]

		* Generator 函数运行后，返回一个遍历器对象，因此也可以使用扩展运算符。
			const go = function*(){
				yield 1;
				yield 2;
				yield 3;
			};
			[...go()] // [1, 2, 3]
		


	# 应用
		* 复制数组
			const a1 = [1, 2];
			// 写法一
			const a2 = [...a1];
				* 定义了一个a2的变量,然后[]把解构后的数据包裹起来,就是一个数组了

			// 写法二(这个写法有点意思了)
			const [...a2] = a1;
				* 定义了一个a2的变量,是一个解构体
				* 然后把a1的每个数据,都解构赋值到这个空的解构体
				* ...a2只要出现出现在左边,那么它最终的结果肯定就是一个数组

		
		* 合并数组
			// ES5
			[1, 2].concat(more)
			// ES6
			[1, 2, ...more]

			var arr1 = ['a', 'b'];
			var arr2 = ['c'];
			var arr3 = ['d', 'e'];

			// ES5合并数组
			arr1.concat(arr2, arr3);	// [ 'a', 'b', 'c', 'd', 'e' ]

			// ES6合并数组
			[...arr1, ...arr2, ...arr3]	// [ 'a', 'b', 'c', 'd', 'e' ]
		
		* 与解构赋值结合
			// ES5
			a = list[0],				//把第一个参数赋值给a
			rest = list.slice(1)		//从第二个参数开始的所有参数,赋值给rest

			// ES6
			[a, ...rest] = list			

			* 如果将扩展运算符用于数组赋值,只能放在参数的最后一位,否则会报错
				const [...butLast, last] = [1, 2, 3, 4, 5];
				// 报错

				const [first, ...middle, last] = [1, 2, 3, 4, 5];
				// 报错
		
			
--------------------------------
Array.from()					|
--------------------------------
	# Array.from方法用于将两类对象转为真正的数组
		* 类似数组的对象(array-like object)
			* 所谓的类数组对象,就是有 length 属性的对象,如果没有该属性,那么转换完毕后就是 []
		* 可遍历(iterable)的对象(包括 ES6 新增的数据结构 Set 和 Map)
		
		let arrayLike = {
            '0': 'a',
            '1': 'b',
            '2': 'c',
            length: 3
        };

        // ES5的写法
		//[]的sice()方法(截取),在arraLike的上下文中执行
        var arr1 = [].slice.call(arrayLike); // ['a', 'b', 'c']

        // ES6的写法
        let arr2 = Array.from(arrayLike); // ['a', 'b', 'c']
	
	# 见的类似数组的对象是 DOM 操作返回的 NodeList 集合,以及函数内部的arguments对象,Array.from都可以将它们转为真正的数组
		// NodeList对象
        let ps = document.querySelectorAll('p');
        Array.from(ps).filter(p => {
			//转换为数组对象后执行其filter方法
            return p.textContent.length > 100;
        });

        // 把arguments对象转换为数组
        function foo() {		
            var args = Array.from(arguments);
            // ...
        }
	
	# 只要是部署了 Iterator 接口的数据结构,Array.from都能将其转为数组
		Array.from('hello')
		// ['h', 'e', 'l', 'l', 'o']

		let namesSet = new Set(['a', 'b'])
		Array.from(namesSet) // ['a', 'b']
		* 字符串和 Set 结构都具有 Iterator 接口,因此可以被Array.from转为真正的数组
	
	# 如果参数是一个真正的数组，Array.from会返回一个一模一样的新数组。
		Array.from([1, 2, 3])	// [1, 2, 3]
	
	# 扩展运算符 ...,也可以把某些数据解构转换为数组
		// arguments对象
		function foo() {
			const args = [...arguments];
		}

		// NodeList对象
		[...document.querySelectorAll('div')]
	
		* 扩展运算符背后调用的是遍历器接口(Symbol.iterator)
		* 如果一个对象没有部署这个接口,就无法转换
	
	# Array.from方法还支持类似数组的对象
		* 所谓类似数组的对象,本质特征只有一点,即必须有length属性
		* 任何有length属性的对象,都可以通过Array.from方法转为数组,而此时扩展运算符就无法转换
		
		Array.from({ length: 3 });
		// [ undefined, undefined, undefined ]

		* 如果浏览器不支持该方法 Array.from,可以使用 Array.prototype.slice 方法代替
			function toArray(obj){
				return [].slice.call(obj);
			}
		
		* 演示,提取出一组dom节点的文本内容
			let spans = document.querySelectorAll('span.name');
			// map()
			let names1 = Array.prototype.map.call(spans, s => s.textContent);
			// Array.from()
			let names2 = Array.from(spans, s => s.textContent)
			
		
	
	# Array.from()可以将各种值转为真正的数组,并且还提供map功能
		* Array.from 的第二个参数还支持是一个方法,会把目标的每个成员都在该方法进行一次消费后返回
			Array.from([1, , 2, , 3], (n) => n || 0	)		//把 bool值为 false的成员转换为0
			// [1, 0, 2, 0, 3]
			
			function typesOf () {
				//该方法,返回每种数据的 type
				return Array.from(arguments, value => typeof value)
			}
			typesOf(null, [], NaN)
			// ['object', 'object', 'number']
		
		* 如果map(消费)函数里面用到了this关键字,还可以传入Array.from的第三个参数,用来绑定this
			
		
	# Array.from()的另一个应用是,将字符串转为数组,然后返回字符串的长度
		* 因为它能正确处理各种 Unicode 字符,可以避免 JavaScript 将大于\uFFFF的 Unicode 字符,算作两个字符的 bug
			function countSymbols(string) {
				return Array.from(string).length;
			}
	
----------------------------
Array.of					|
----------------------------
	# Array.of方法用于将一组值,转换为数组
		Array.of(3, 11, 8)	// [3,11,8]
		Array.of(3)			// [3]
		Array.of(3).length	// 1
		
	# 它存在的意义是为了弥补构造函数Array()的不足
		* 因为参数个数的不同,会导致Array()的行为有差异
			Array() // []
			Array(3) // [, , ,]
			Array(3, 11, 8) // [3, 11, 8]
			
			* 只有当参数个数不少于 2 个时,Array()才会返回由参数组成的新数组
			* 参数个数只有一个时,实际上该值是指定数组的长度
	
	# Array.of基本上可以用来替代Array()或new Array(),并且不存在由于参数不同而导致的重载,它的行为非常统一
	# Array.of总是返回参数值组成的数组,如果没有参数，就返回一个空数组
	# Array.of方法可以用下面的代码模拟实现
		function ArrayOf(){
			return [].slice.call(arguments);
		}
	
		function ArrayOf(..args){
			return args;
		}

----------------------------
数组实例方法-copyWithin()	|
----------------------------
	# 在当前数组内部,将指定位置的成员复制到其他位置(会覆盖原有成员),然后返回当前数组
		* 使用这个方法,会修改当前数组

	# 接受仨参数
		Array.prototype.copyWithin(target, start = 0, end = this.length)
		target(必需)	从该位置开始替换数据,如果为负值,表示倒数
		start(可选)		从该位置开始读取数据,默认为 0,如果为负值,表示倒数(不包含该下标)
		end(可选)		到该位置前停止读取数据,默认等于数组长度,如果为负值,表示倒数
		
		* 这三个参数都应该是数值,如果不是,会自动转为数值
		
		[1, 2, 3, 4, 5].copyWithin(0, 3)
		//从下标3开始复制一直到最后
		//然后把复制的内容,从下标0开始挨个粘贴覆盖
	

	# demos
		// 将3号位复制到0号位
		[1, 2, 3, 4, 5].copyWithin(0, 3, 4)
		// [4, 2, 3, 4, 5]

		// -2相当于3号位，-1相当于4号位
		[1, 2, 3, 4, 5].copyWithin(0, -2, -1)
		// [4, 2, 3, 4, 5]

		// 将3号位复制到0号位
		[].copyWithin.call({length: 5, 3: 1}, 0, 3)
		// {0: 1, 3: 1, length: 5}

		// 将2号位到数组结束，复制到0号位
		let i32a = new Int32Array([1, 2, 3, 4, 5]);
		i32a.copyWithin(0, 2);
		// Int32Array [3, 4, 5, 4, 5]

		// 对于没有部署 TypedArray 的 copyWithin 方法的平台
		// 需要采用下面的写法
		[].copyWithin.call(new Int32Array([1, 2, 3, 4, 5]), 0, 3, 4);
		// Int32Array [4, 2, 3, 4, 5]
	

--------------------------------
实例的 find() 和 findIndex()	|	
--------------------------------
	# find方法已经是具备的,找到返回,找不到返回 undefined
	
	# findIndex与find相似,返回第一个符合条件的数组成员的位置,如果所有成员都不符合条件,则返回-1
		* 参数也是跟 find() 一样
	
	# 这两个方法都可以接受第二个参数,用来绑定回调函数的this对象
		function f(v){
			return v > this.age;
		}

		let person = {name: 'John', age: 20};

		[10, 12, 26, 15].find(f, person);    // 26
	
	# 这两个方法都可以发现NaN,弥补了数组的indexOf方法的不足
		[NaN].indexOf(NaN)
		// -1

		[NaN].findIndex(y => Object.is(NaN, y))
		// 0
	
--------------------------------
实例的 fill()					|	
--------------------------------
	# fill方法使用给定值,填充满当前数组
		['a', 'b', 'c'].fill(7)
		// [7, 7, 7]

		new Array(3).fill(7)
		// [7, 7, 7]
	
	# 该方法还接收俩参数,分别表示填充的起始位置和结束位置
	# 填充类型为对象,那么复制的其实是同一个内存地址,非深拷贝
		new Array(5).fill({name:'Kevin'})
		* 5个元素指向的内存地址都是一样的
	

--------------------------------------------------------
数组实例的 entries(),keys() 和 values()					|	
--------------------------------------------------------
	# entries(),keys()和values()都是用于遍历数组
	# 它们都返回一个遍历器对象,可以用for...of循环进行遍历
	# 数序Map数据解构的就应该不陌生
	# keys()是对键名的遍历,values()是对键值的遍历,entries()是对键值对的遍历
		for (let index of ['a', 'b'].keys()) {
		  console.log(index);
		}
		// 0
		// 1

		for (let elem of ['a', 'b'].values()) {
		  console.log(elem);
		}
		// 'a'
		// 'b'

		for (let [index, elem] of ['a', 'b'].entries()) {
		  //解构赋值呢
		  console.log(index, elem);
		}
		// 0 "a"
		// 1 "b"
	
	# 也可以使用迭代器的 next() 进行遍历
		let letter = ['a', 'b', 'c'];

		let entries = letter.entries();

		console.log(entries.next().value); // [0, 'a']
		console.log(entries.next().value); // [1, 'b']
		console.log(entries.next().value); // [2, 'c']

------------------------------
数组实例的 includes()		  |
------------------------------
	# Array.prototype.includes方法返回一个布尔值,表示某个数组是否包含给定的值
	# 与字符串的includes方法类似
		[1, 2, 3].includes(2)     // true
		[1, 2, 3].includes(4)     // false
		[1, 2, NaN].includes(NaN) // true
	
	# 的第二个参数表示搜索的起始位置,默认为0
		* 如果第二个参数为负数,则表示倒数的位置
		* 如果这时它大于数组长度(比如第二个参数为-4,但数组长度为3),则会重置为从0开始
	
		[1, 2, 3].includes(3, 3);  // false
		[1, 2, 3].includes(3, -1); // true
	
	# 没有该方法之前,我们通常使用数组的indexOf方法,检查是否包含某个值
		if (arr.indexOf(el) !== -1) {
			// ...
		}
		* 这方法有俩缺点
			1,不够语义化,它的含义是找到参数值的第一个出现位置所以要去比较是否不等于-1,表达起来不够直观
			2,它内部使用严格相等运算符(===)进行判断,这会导致对NaN的误判
		
			[NaN].indexOf(NaN)
			// -1
			
			* includes使用的是不一样的判断算法,就没有这个问题
			[NaN].includes(NaN)
			// true
	
	# 另外,Map 和 Set 数据结构有一个has方法,需要注意与includes区分
		* Map 结构的has方法,是用来查找键名的
			Map.prototype.has(key)、WeakMap.prototype.has(key)
			Reflect.has(target, propertyKey)
		* Set 结构的has方法,是用来查找值的
			Set.prototype.has(value)
			WeakSet.prototype.has(value)
	
------------------------
数组空位				|
------------------------
	# 数组的空位指,数组的某一个位置没有任何值
		* Array构造函数返回的数组都是空位
		Array(3) // [, , ,]

	# 空位不是undefined,一个位置的值等于undefined,依然是有值的,空位是没有任何值,in运算符可以说明这一点
		0 in [undefined, undefined, undefined]	// true
		0 in [, , ,]							// false
	
	# ES5 对空位的处理,已经很不一致了,大多数情况下会忽略空位
		forEach(),filter(), reduce(), every() 和some()都会跳过空位
		map()会跳过空位,但会保留这个值
		join()和toString()会将空位视为undefined而undefined和null会被处理成空字符串
	
		// forEach方法
		[,'a'].forEach((x,i) => console.log(i)); // 1

		// filter方法
		['a',,'b'].filter(x => true) // ['a','b']

		// every方法
		[,'a'].every(x => x==='a') // true

		// reduce方法
		[1,,2].reduce((x,y) => return x+y) // 3

		// some方法
		[,'a'].some(x => x !== 'a') // false

		// map方法
		[,'a'].map(x => 1) // [,1]

		// join方法
		[,'a',undefined,null].join('#') // "#a##"

		// toString方法
		[,'a',undefined,null].toString() // ",a,,"
	
	
	# ES6 则是明确将空位转为undefined
		* Array.from方法会将数组的空位,转为undefined,也就是说,这个方法不会忽略空位
			Array.from(['a',,'b'])
			// [ "a", undefined, "b" ]
		
		* 扩展运算符(...)也会将空位转为undefined
			[...['a',,'b']]
			// [ "a", undefined, "b" ]
		
		* copyWithin()会连空位一起拷贝
			[,'a','b',,].copyWithin(2,0) // [,"a",,"a"]
		
		* fill()会将空位视为正常的数组位置
			new Array(3).fill('a') // ["a","a","a"]
		
		* for...of循环也会遍历空位
			let arr = [, ,];
			for (let i of arr) {
			  console.log(1);
			}
			// 1
			// 1
		
			* 数组arr有两个空位,for...of并没有忽略它们
			* 如果改成map方法遍历,空位是会跳过的
		
		* entries(),keys(),values(),find()和findIndex()会将空位处理成undefined
			// entries()
			[...[,'a'].entries()] // [[0,undefined], [1,"a"]]

			// keys()
			[...[,'a'].keys()] // [0,1]

			// values()
			[...[,'a'].values()] // [undefined,"a"]

			// find()
			[,'a'].find(x => true) // undefined

			// findIndex()
			[,'a'].findIndex(x => true) // 0
		
	
	# '由于空位的处理规则非常不统一,所以建议避免出现空位'
