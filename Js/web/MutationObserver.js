-----------------
MutationObserver
-----------------
	# MutationObserver 接口，可以在DOM被修改时异步执行回调。
		* 使用MutationObserver可以观察整个文档、DOM树的一部分，或某个元素。
		* 此外还可以观察元素属性、子节点、文本，或者前三者任意组合的变化。

	# 构造函数
		MutationObserver(callback);
	
		callback
			* 一个回调函数，每当被指定的节点或子树以及配置项有 DOM 变动时会被调用。
			* 回调函数拥有两个参数：function callback(mutationList, observer) 
				一个是描述所有被触发改动的 MutationRecord 对象数组
				一个是调用该函数的 MutationObserver 对象。
		

-----------------
this
-----------------
	disconnect()
		* 提前终止执行回调，不仅会停止此后变化事件的回调，也会抛弃已经加入任务队列要异步执行的回调。

	observe(target[, options])()
		* 关联要监听的 Dom。
		* 多次调用 observe() 方法，可以复用一个 MutationObserver 对象观察多个不同的目标节点。

		
	takeRecords()
		* 清空记录队列，取出并返回其中的所有MutationRecord实
	