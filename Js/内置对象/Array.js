------------------------
Array
------------------------
	# 数组
		* 可动态扩容，并且可以包含不同的数据类型。

		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array
	
	# 构造函数
		Array(element0, element1, /* … ,*/ elementN)
		Array(arrayLength)
			element
				* 根据给定的元素创建一个 JavaScript 数组，
			arrayLength
				* 如果传递给 Array 构造函数的唯一参数是介于 0 到 232 - 1（含）之间的整数，这将返回一个新的 JavaScript 数组，其 length 属性设置为该数字。

			let arr = new Array(1024)
			console.log(arr.length);        // 1024
			console.log(arr[0], arr[arr.length - 1]) // undefined undefined
	
	# 字面量形式初始化
		let arr  =  ["Hi", "Hi", 3];
	
	# 空元素占位
		
		* ES6 及其以后，空的元素是 undefined
			let arr = [1, , ,] // [1, undefined, undefined]
		
		* ES6之前的方法则会忽略这个空位，但具体的行为也会因方法而异
	
	# 类数组对象
		* 所谓类似数组的对象，本质特征只有一点，即必须有 length 属性。
	
	# 扩展运算符
		* 三个点（...），好比 rest 参数的逆运算，将一个数组转为用逗号分隔的参数序列。

			console.log(...[1, 2, 3])
			// 1 2 3

			console.log(1, ...[2, 3, 4], 5)
			// 1 2 3 4 5

			[...document.querySelectorAll('div')]
			// [<div>, <div>, <div>]
		
		* 只有函数调用时，扩展运算符才可以放在圆括号中，否则会报错。
		* 如果扩展运算符后面是一个空数组，则不产生任何效果。
			[...[], 1]	// [1]
		
		* 任何定义了遍历器（Iterator）接口的对象，都可以用扩展运算符转为真正的数组。反之，使用扩展运算符则会异常
			Number.prototype[Symbol.iterator] =  function * (){
				let i = 0;
				while (i  < this){
					yield i ++;
				}
			}
			console.log([...6]);  // [0, 1, 2, 3, 4, 5]		


	# 数组长度
		* 最玄学的东西，它不是只读的，如果修改 length 可以影响数组的长度
		
			let arr = [0, 1, 2, 3, 4];

			console.log(arr.length);		// 5
			arr.length = 3;					// 修改 lengh 为 3
			console.log(arr)				// [0, 1, 2]
			console.log(arr.length)			// 3
		
		* 如果将length设置为大于数组元素数的值，则新添加的元素都将以undefined填充

		* 越界访问不会异常，而是返回 undefined
			console.log([0, 1, 3][9]); // undefined
		
		* 越界设置值的话，数组会扩容，中间空出来的全部为 undefined
		* 数组最多可以包含 4294967295 个元素，超出就异常

------------------------
this
------------------------

	Array.prototype[@@unscopables]
	length

	Array.prototype[@@iterator]()
	Array.prototype.at(index)
		* 接受一个整数作为参数，返回对应位置的成员，并支持负索引。
		* 这个方法不仅可用于数组，也可用于字符串和类型数组（TypedArray）。
		* 如果参数位置超出了数组范围，at()返回undefined。

	Array.prototype.concat(value0, value1, /* … ,*/ valueN)
		* 把指定的元素添加到当前数组后返回新的数组，不会修改原数组。
		* 如果传入一个或多个数组，则 concat() 会把这些数组的每一项都添加到结果数组。
			let arr = [1, 2];
			let newArr = arr.concat([3, 4], 5, [6, 7]);
			console.log(newArr);        // [1, 2, 3, 4, 5, 6, 7]
			console.log(arr);           // [1, 2]

	Array.prototype.copyWithin(target, start, end)

		* 浅复制数组的一部分到同一数组中的另一个位置，并返回它，不会改变原数组的长度。

			target
				* 序列开始替换的目标位置，以 0 为起始的下标表示，且将被转换为整数
				* 负索引将从数组末尾开始计数——如果 target < 0，则实际是 target + array.length。
			
			start
				* 可选，要复制的元素序列的起始位置，以 0 为起始的下标表示。
			
			end 
				* 可选，要复制的元素序列的结束位置，以 0 为起始的下标表示。
			
		* 忽略超出数组边界、零长度及方向相反的索引范围。


	Array.prototype.entries()
		* 返回索引/值对的迭代器
			for (let [index, elem] of ['a', 'b'].entries()) {
			  console.log(index, elem);
			}

	Array.prototype.every(callbackFn, thisArg)
		* 测试一个数组内的所有元素是否都能通过指定函数的测试
			callbackFn
				* 为数组中的每个元素执行的函数，返回 boolean 值。
				* callbackFn(element, index, array)
					element 当前元素
					index	当前元素的索引
					array	当前数组
			thisArg
				* 执行 callbackFn 时用作 this 的值。


	Array.prototype.fill(value, start, end)
		* 用一个固定值填充一个数组中从起始索引（默认为 0）到终止索引（默认为 array.length）内的全部元素，返回修改后的数组。
		* 不会修改数组的长度
			value
				* 用来填充数组元素的值。注意所有数组中的元素都将是这个确定的值。
			
			start
				* 可选，从此开始填充。
			
			end 
				* 可选，填充至此。

	Array.prototype.filter(callbackFn, thisArg)
		* 过滤，返回通过了 callbackFn 测试的元素组成的数组，不会修改原数组。
			callbackFn
				* 判断函数，有三个参数 callbackFn(element, index, array)
				* 返回 boolean ，true 表示符合条件
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。

	Array.prototype.find(callbackFn, thisArg)
		* 返回数组中满足提供的测试函数的第一个元素的值，否则返回 undefined。
			callbackFn
				* 判断函数，有三个参数 callbackFn(element, index, array)
				* 返回 boolean ，true 表示找到了。
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。

	Array.prototype.findIndex(callbackFn, thisArg)
		* 返回数组中满足提供的测试函数的第一个元素的索引。若没有找到对应元素则返回 -1。
			callbackFn
				* 判断函数，有三个参数 callbackFn(element, index, array)
				* 返回 boolean ，true 表示找到了。
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。

	Array.prototype.findLast(callbackFn, thisArg)
		* 方法反向迭代数组，并返回满足提供的测试函数的第一个元素的值。如果没有找到对应元素，则返回 undefined。
		* 参数和 find 一样。


	Array.prototype.findLastIndex(callbackFn, thisArg)
		* 反向迭代数组，并返回满足所提供的测试函数的第一个元素的索引。若没有找到对应元素，则返回 -1。
		* 参数和 findIndex 一样。

	Array.prototype.flat(depth)
		* 将嵌套的数组“拉平”，变成一维的数组。该方法返回一个新数组，对原数据没有影响。
			[1, 2, [3, 4]].flat() // [1, 2, 3, 4]
			
			depth
				* 最大拉平的层级，默认只会拉平第一层嵌套的数组
				* 可以使用 Infinity 关键字，表示拉平所有嵌套的数组
		
		* 如果原数组有空位，flat()方法会跳过空位
	

	Array.prototype.flatMap(callbackFn, thisArg)
		* 对原数组的每个成员执行一个函数（相当于执行 Array.prototype.map()），然后对返回值组成的数组执行flat()方法。
		* 只能展开一层数组。
		* 该方法返回一个新数组，不改变原数组。
			callbackFn
				* 执行的函数，有三个参数
					element 当前元素
					index	当前元素的索引
					array	当前数组
			thisArg
				* 执行函数的 this 上下文


	Array.prototype.forEach(callbackFn, thisArg)
		* 迭代，遍历
			callbackFn
				* 遍历函数，有三个参数 callbackFn(element, index, array)
				* 应该无返回值，返回值会被丢弃。
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。


	Array.prototype.includes(searchElement, fromIndex)
		* 判断一个数组是否包含一个指定的值
			searchElement
				* 需要查找的值
			
			fromindex
				* 可选，开始搜索的索引（从零开始），会转换为整数。
		* 使用 === 判断相等
			let arr = [1, 2, 3]
			console.log(arr.includes(2), arr.includes('2')); // true false

	Array.prototype.indexOf(searchElement, fromIndex)
		* 回数组中第一次出现给定元素的下标，如果不存在则返回 -1。
			searchElement
				* 要搜索的元素
			
			fromIndex
				* 可选，开始搜索的索引

		* 使用 === 比较


	Array.prototype.join(separator)
		* 指定一个字符串来分隔数组的每个元素，默认为 ',' 逗号。
		* 注意，如果元素是 undefined 或 null 会被转换为空字符串

	Array.prototype.keys()	
		* 返回数组索引的迭代器

	Array.prototype.lastIndexOf(searchElement, fromIndex)
		* 返回数组中给定元素最后一次出现的索引，如果不存在则返回 -1。
		* 使用  === 运算。
			searchElement
				* 要搜索的元素
			fromIndex
				* 可选，如果指定了话，则从 fromIndex 开始向前搜索数组。

	Array.prototype.map(callbackFn, thisArg)
		* 对每个元素进行映射，处理，并返回，组成一个新数组返回，不会修改原数组。
			callbackFn
				* 映射函数，有三个参数 callbackFn(element, index, array)
				* 返回修改后的值。
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。

	Array.prototype.pop()
		* 从数组中删除并返回最后一个元素。如果在空数组上调用 pop()，它会返回 undefined。
			let arr = [];
			console.log(arr.pop(), arr);

	Array.prototype.push(element0, element1, /* … ,*/ elementN)
		* 添加一个或多个元素到数组末尾，会修改当前数组的长度。
		* 返回添加添加元素后的数组的长度。
	
	Array.prototype.reduce(callbackFn, initialValue)
		* 对数组中的每个元素按序执行一个提供的 reducer 函数，每一次运行 reducer 会将先前元素的计算结果作为参数传入，最后将其结果汇总为单个返回值。
			callbackFn
				* 为数组中每个元素执行的函数，有三个参数 callbackFn(accumulator, currentValue, currentIndex, array)
					accumulator		上一次调用 callbackFn 的结果，在第一次调用时，如果指定了 initialValue 则为指定的值，否则为 array[0] 的值。
					currentValue	当前元素，在第一次调用时，如果指定了 initialValue，则为 array[0] 的值，否则为 array[1]。
					currentIndex	当前元素的索引，在第一次调用时，如果指定了 initialValue 则为 0，否则为 1。
					array			当前数组

				* 其返回值将作为下一次调用 callbackFn 时的 accumulator 参数。对于最后一次调用，返回值将作为 reduce() 的返回值。该函数被调用时将传入以下参数：
			
			initialValue
				* 可选，第一次调用的初始值
		
		* 求和
			let arr = [1, 2, 3]
			let sum = arr.reduce((r, v, i, a) => r + v, 0);
			console.log(sum);  // 6
	
	Array.prototype.reduceRight(callbackFn, initialValue)
		* 和 reduce 一样，它是逆序，从右到左。

	Array.prototype.reverse()
		* 反转数组中元素的位置，改变了数组，并返回该数组的引用。

			let arr = [1, 2, 3, 5, 4];

			let reversedArr = arr.reverse();	
			console.log(reversedArr === arr);	// true，返回的数组就是原数组本身
			console.log(reversedArr);			// [4, 5, 3, 2, 1]
			console.log(arr);					// [4, 5, 3, 2, 1]

	Array.prototype.shift()
		* 移除索引为 0 （头部）的元素，并将后续元素的下标依次向前移动，然后返回被移除的元素。
		* 如果空数组（length 属性的值为 0），则返回 undefined。
		* 用来从头消费某个数组
			let arr = [1, 2, 3, 4, 5];
			let item = undefined;
			while ((item = arr.shift()) != undefined){
				console.log(item);
			}
			console.log(arr);  // []


	Array.prototype.slice(start, end)
		* 复制当前数组的一部分后作为新数组返回，不会修改原数组（有点像 golang 中的 slice）。
		* 从 start 提取到但不包括 end 的位置（左开右闭）。
			start
				* 可选，开始索引，从 0 开始，参数如果不是数值则会被转换为数组。
				* 如果索引是负数，则从数组末尾开始计算，如果 start < 0，则使用 start + array.length。
				* 如果 start < -array.length 或者省略了 start，则使用 0。
				* 如果 start >= array.length，则不提取任何元素。
			
			* end
				* 可选，结束索引，从 0 开始，参数如果不是数值则会被转换为数组。
				* 如果索引是负数，则从数组末尾开始计算，如果 end < 0，则使用 end + array.length。
				* 如果 end < -array.length，则使用 0。
				* 如果 end >= array.length 或者省略了 end，则使用 array.length，提取所有元素直到末尾。
				* 如果 end 在规范化后小于或等于 start，则不提取任何元素。
		

			let arr = [1, 2, 3]
			console.log(arr.slice());           // [1, 2, 3]
			console.log(arr.slice(0, 1))        // [1]
			console.log(arr.slice(0, 2))        // [1, 2]
			console.log(arr.slice(0, arr.length + 1))        // [1, 2, 3]
		

	Array.prototype.some(callbackFn, thisArg)
		* 测试数组中是否至少有一个元素通过了 callbackFn 函数的测试。
		* 如果在数组中找到一个元素使得提供的 callbackFn 函数返回 true，则返回 true；否则返回 false。它不会修改数组。
			callbackFn
				* 测试函数，有三个参数 callbackFn(element, index, array)
				* 返回 boolean 值
					element 当前元素
					index	当前元素的索引
					array	当前数组
			
			thisArg
				* 执行 callbackFn 时用作 this 的值。


	Array.prototype.sort(compareFn);
		* 对数组进行排序，排序函数 compareFn(a, b) 返回值应该是一个数字，其符号表示两个元素的相对顺序。
		* 如果 a 小于 b，返回值为负数，如果 a 大于 b，返回值为正数，如果两个元素相等，返回值为 0。NaN 被视为 0。

		* 如果省略该函数，数组元素会被转换为字符串，然后根据每个字符的 Unicode 码位值进行排序。
		* 返回 this 数组，即排序后的数组。
		
	Array.prototype.splice(start, deleteCount, item1, item2, /* …, */ itemN)
		* 移除或者替换已存在的元素和/或添加新的元素，会修改 this 数组。
		* 返回包含了删除元素的数组，如果没有删除元素则返回空数组。

			start
				* 可选，表示要开始改变数组的位置，它会被转换成整数。
			
			deleteCount
				* 可选，表示数组中要从 start 开始删除的元素数量。

			item
				* 可选，从 start 开始要加入到数组中的元素。
				* 如果不指定这个参数，splice() 只从数组中删除元素。


	Array.prototype.toLocaleString()

	Array.prototype.toReversed()
		* 对应 reverse()，用来颠倒数组成员的位置。
		* 返回新数组，不修改原数组

	Array.prototype.toSorted(compareFn)
		* 对应 sort()，用来对数组成员排序。
		* 返回新数组，不修改原数组

	Array.prototype.toSpliced(start, deleteCount, item1, item2, itemN)
		* 对应 splice()，用来在指定位置，删除指定数量的成员，并插入新成员。
		* 返回新数组，不修改原数组
	
	Array.prototype.with(index, value)
		* 对应 splice(index, 1, value)，用来将指定位置的成员替换为新的值。
		* 返回新数组，不修改原数组

	Array.prototype.toString()
		* 返回由数组中每个值的等效字符串拼接而成的一个逗号分隔的字符串。也就是说，对数组的每个值都会调用其toString()方法，以得到最终的字符串。

	Array.prototype.unshift(element1, element2, /* …, */ elementN)
		* 将给定的值插入到类数组的开头，返回插入后的数组长度。
			let arr = [4, 5];
			let newLen = arr.unshift(...[1, 2, 3]);
			console.log(newLen);        // 5
			console.log(arr);           // [1, 2, 3, 4, 5]


	Array.prototype.values()
		* 返回数组元素的迭代器


------------------------
static
------------------------
	Array.from(arrayLike, mapFn, thisArg)
		* 将两类对象转为真正的数组：类似数组的对象（array-like object）和可遍历（iterable）的对象（包括 Set 和 Map）
			arrayLike
				* 想要转换成数组的类数组或可迭代对象
			mapFn 
				* 用来对每个元素进行处理的函数，将处理后的返回值放入返回的数组。
				* 该函数有 2 个参数分别表示当前参数和索引
				* 该函数返回值会替换原成员
		
			thisArg
				* 执行 mapFn 时用作 this 的值，注意这个 this 值在箭头函数中不适用。

	Array.fromAsync()
	Array.isArray(value)
		* 确定一个值是否为数组，而不用管它是在哪个全局执行上下文中创建的。

	Array.of(element0, element1, /* … ,*/ elementN)
		* 用于将一组值，转换为数组。返回参数值组成的数组。如果没有参数，就返回一个空数组。
		* 这个方法的主要目的，是弥补数组构造函数Array()的不足，和替代在ES6之前常用的 Array.prototype.slice.call(arguments)，
	

