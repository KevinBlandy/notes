----------------------------------
Promise
----------------------------------
	
	# 构造函数
		new Promise(executor)
			executor
				* 在构造函数中执行的 function。
				* 它接收两个函数作为参数：
					resolveFunc
					rejectFunc
				
				* executor 中抛出的任何错误都会导致 Promise 被拒绝，并且返回值将被忽略。
	



----------------------------------
this
----------------------------------
	Promise.prototype.then(onFulfilled, onRejected)
		* 在执行完成后的回调，参数都是可选的
			onFulfilled
				* 成功回调
			onRejected
				* 失败回调
			
		* then 方法返回的是一个新的 Promise 实例
	
	Promise.prototype.catch(onRejected)	
		* 指定发生错误时的回调函数。
		* 如果异步操作抛出错误，状态就会变为 rejected，就会调用 catch() 方法指定的回调函数，处理这个错误。
		* 而且，then() 方法指定的回调函数，如果运行中抛出错误，也会被 catch() 方法捕获
	
			onRejected
				* 失败回调
			
		* catch() 方法返回的还是一个 Promise 对象
		
		* Promise 对象的错误具有“冒泡”性质，会一直向后传递，直到被捕获为止。也就是说，错误总是会被下一个catch语句捕获。
		* 如果没有使 用catch() 方法指定错误处理的回调函数，Promise 对象抛出的错误不会传递/影响到外层代码，即不会有任何反应（会在控制台输出而已）。
			const someAsyncThing = function() {
				return new Promise(function(resolve, reject) {
				  // 下面一行会报错，因为x没有声明
				  resolve(x + 2);
				  // // Uncaught (in promise) ReferenceError: x is not defined
				});
			  };
			  
			  someAsyncThing().then(function() {
				console.log('everything is great');
			  });
			  
			  setTimeout(() => { console.log(123) }, 2000);
			  // 123

	Promise.prototype.finally(onFinally)
		* 最终都会执行

			onFinally
				* 最后都会执行的函数，它的返回值将被忽略。
				* 调用该函数时不带任何参数。
		
		* finally方法总是会返回原来的值。
			// resolve 的值是 undefined
			Promise.resolve(2).then(() => {}, () => {})

			// resolve 的值是 2
			Promise.resolve(2).finally(() => {})

			// reject 的值是 undefined
			Promise.reject(3).then(() => {}, () => {})

			// reject 的值是 3
			Promise.reject(3).finally(() => {})

----------------------------------
Event
----------------------------------
	unhandledRejection
		* Node 中的东西
		* 可以监听到 Promise 中，未捕获的 reject 错误

----------------------------------
static
----------------------------------

	Promise[@@species]

	Promise.all(iterable)	
		* 用于将多个 Promise 实例，包装成一个新的 Promise 实例。
		* 如果元素不失 Promise ，则会调用 resolve 解析为 Promise
		* 返回 Promise。
			* 只有所有 Promise 都成功则成功，所有 Promise 的返回值组成一个数组，传递给回调函数。
			* 任意失败，则失败，第一个被 reject 的实例的返回值，会传递给回调函数。
		
		* 如果参数的 Promise 实例，自己定义了catch方法，那么它一旦被 rejected，并不会触发 Promise.all() 的 catch 方法。

	Promise.allSettled(iterable)
		* 用来确定一组异步操作是否都结束了（不管成功或失败）。包含了 “fulfilled” 和 “rejected” 两种情况。
		* 该方法返回的新的 Promise 实例，一旦发生状态变更，状态总是fulfilled，不会变成rejected。
		* 返回 Promise 回调函数会接收到一个数组作为参数，该数组的每个成员对应前面数组的每个 Promise 对象。
			* 每个成员是一个对象，对象的格式是固定的，对应异步操作的结果。
			// 异步操作成功时
			{status: 'fulfilled', value: value}

			// 异步操作失败时
			{status: 'rejected', reason: reason}
	
		
	Promise.any(iterable)
		* 只要参数实例有一个变成 fulfilled 状态，包装实例就会变成 fulfilled 状态
		* 如果所有参数实例都变成 rejected 状态，包装实例就会变成 rejected 状态。
		* 有点像 race，但是有区别，主要是在 rejected 上
			* any	：所有实例都 rejected，返回值才 rejected
			* race	:任意实例 rejected，返回值就 rejected

	Promise.race(iterable)
		* 竞赛，只参数 Promose 中任意一个的状态发生了改变，即返回的 Promise 的状态。

	Promise.reject(reason)
		* 返回一个 Reject 状态的 Promise

			reason
				* 该 Promise 对象被拒绝的原因。
		
		* Promise.reject()方法的参数，会原封不动地作为 reject 的理由，变成后续方法的参数。
			Promise.reject("异常了").catch(r => console.log(r)); // 异常了

	Promise.resolve(value)
		* 把一个值解析为 Promose
			value
				* 也可以是一个 Promise 对象或一个 thenable 对象。
			
		* value 参数分为 4 种情况
			1. 参数是一个 Promise 实例
				* 如果参数是 Promise 实例，那么Promise.resolve将不做任何修改、原封不动地返回这个实例。
			
			2. 参数是一个thenable对象 （具有  then()(resolve, reject)  方法的对象）
				* 会将这个对象转为 Promise 对象，然后就立即执行thenable对象的then()方法。
			
			3. 参数不是具有 then() 方法的对象，或根本就不是对象
				* 如果参数是一个原始值，或者是一个不具有then()方法的对象，则Promise.resolve()方法返回一个新的 Promise 对象，状态为 resolved。
			
			4. 不带有任何参数
				* 直接返回一个 resolved 状态的 Promise 对象。
 
	Promise.withResolvers()
		* 返回一个对象，其包含一个新的 Promise 对象和两个函数，用于 resolve 或 reject 它，对应于传入给 Promise() 构造函数执行器的两个参数。
		* 返回对象
			// {promise: Promise, resolve: ƒ, reject: ƒ}

			promise
				* 一个 Promise 对象。
			resolve
				* 一个函数，用于 resolve 该 Promise。
			reject
				* 一个函数，用于 reject 该 Promise。
		
		* 类似于如下代码
			let resolve, reject;
			const promise = new Promise((res, rej) => {
			  resolve = res;
			  reject = rej;
			});