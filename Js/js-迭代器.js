-------------------
迭代器
-------------------
	# Iterable 可迭代对象，即实现了 Iterable 接口，而且可以通过迭代器 Iterator 消费。
		* 如果对象原型链上的父类实现了Iterable接口，那这个对象也就实现了这个接口。

	# Iterator 接口
		* 任何实现 Iterable 接口的数据结构都可以被实现 Iterator 接口的结构“消费”（consume）。
		* 任何实现 Iterable 接口的对象都有一个 Symbol.iterator 属性，这个属性引用默认迭代器

	
	# Symbol.iterator 默认的迭代属性
		* 当使用 for...of 循环遍历某种数据结构时,该循环会自动去寻找其 Iterator 接口
		* 默认的 Iterator 接口部署在数据结构的 Symbol.iterator 属性,
		* 或者说 一个数据结构只要具 有 Symbol.iterator 属性,就可以认为是 “可迭代对象”

		* Symbol.iterator 属性值本身是一个函数，就是当前数据结构默认的遍历器生成函数，执行这个函数，就会返回一个遍历器
		
	# Iterator 协议
		* Iterator API使用 next()方法在可迭代对象中遍历数据。
		* 每次成功调用next()，都会返回一个 IteratorResult 对象，其中包含迭代器返回的下一个值。
		* IteratorResult 包含两个属性： done 和 value


			{
				next: function(){			// 迭代下一个值
					return {
						value: "val",		// 表示迭代值（done为false），或者 undefined（done为true）
						done: true			// 表示是否还可以再次调用next()取得下一个值
					}
				},
				return: function(){		// 在迭代器提前关闭时执行的逻辑（break/continue/return/throw）
					return {done: true}
				},
			}

			* return 方法可选，必须返回一个有效的 IteratorResult 对象。简单情况下，可以只返回 {done: true }
			* 如果迭代器没有关闭，则还可以继续从上次离开的地方继续迭代。
			

		
		* 不同迭代器的实例相互之间没有联系，只会独立地遍历可迭代对象。
			let arr = [1, 2, 3];

			let it1 = arr[Symbol.iterator]();
			let it2 = arr[Symbol.iterator]();

			console.log(it1.next());        // {value: 1, done: false}
			console.log(it2.next());        // {value: 1, done: false}
			console.log(it1.next());        // {value: 2, done: false}
			console.log(it2.next());        // {value: 2, done: false}
			console.log(it1.next());        // {value: 3, done: false}
			console.log(it2.next());        // {value: 3, done: false}
		
		* 迭代器并不使用快照，而是用游标来记录可迭代对象，可迭代对象在迭代期间被修改了，那么迭代器也会反映相应的变化。


-------------------
异步迭代
-------------------
	# ES2018 引入了“异步遍历器”（Async Iterator），为异步操作提供原生的遍历器接口，即value和done这两个属性都是异步产生。
		* 异步遍历器的最大的语法特点，就是调用遍历器的 next 方法，返回的是一个 Promise 对象。

		asyncIterator
		  .next()		// 调用next方法以后，返回一个 Promise 对象
			  .then(	// 回调函数的参数，具有value和done两个属性的对象，跟同步遍历器是一样的。
				({ value, done }) => /* ... */
			  );
	
	# Symbol.asyncIterator 是默认的异步迭代器属性
		
	
	# 新引入了 for await...of 循环，用于遍历异步的 Iterator 接口。

		async function f() {
		  // 一旦 resolve，就把得到的值（x）传入for...of的循环体。
		  for await (const x of createAsyncIterable(['a', 'b'])) {
			console.log(x);
		  }
		}
		// a
		// b
		
		* 如果 next 方法返回的 Promise 对象被 reject，for await...of 就会报错，要用 try...catch 捕捉。
		* for await...of 循环也可以用于同步遍历器。



	# yield* 语句 
		* yield* 语句也可以跟一个异步遍历器。
		* 与同步 Generator 函数一样，for await...of 循环会展开 yield*。
	

	# Demo

		async function* g(){
			yield new Promise((resolve, reject) => {
				setTimeout(() => resolve('第一个结果'), 700);
			});
			yield new Promise((resolve, reject) => {
				setTimeout(() => resolve('第二个结果'), 300);
			});
			yield new Promise((resolve, reject) => {
				setTimeout(() => resolve('第三个结果'), 600);
			});
		}

		for await (const val of g()){
			console.log(val);
		}

		// 第一个结果
		// 第二个结果
		// 第三个结果
