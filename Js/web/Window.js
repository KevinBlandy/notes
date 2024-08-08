----------------------------------------
window extends EventTarget
----------------------------------------
	# Window
		* window 对象被复用为 ECMAScript 的 Global 对象，所以通过 var 声明的所有全局变量和函数都会变成 window 对象的属性和方法。
		* JavaScript 中有很多对象都暴露在全局作用域中，比如 location 和 navigator ，因而它们也是 window 对象的属性。


--------------------
this
--------------------
	crypto 
		* 返回当前窗口的作用域的 Crypto 对象。
	
	localStorage
		* 返回本地 Storage 对象，可以持久存储数据。
	
	sessionStorage 
		* 返回基于会话的 Storage 对象，页面关闭就会清空
	
	indexedDB 
		* 只读属性，返回 IDBFactory 对象


	setTimeout(functionRef, delay, param1, param2, /* … ,*/ paramN)
		* 设置一个定时器，一旦定时器到期，就会执行一个函数或指定的代码片段。
			
			functionRef
				* 要执行的函数
			
			delay
				* 毫秒，延迟时间，不指定的为 0。
			
			param
				* 运行方法时传递给方法的参数。
		
		* 返回值 timeoutID 是一个正整数，表示由 setTimeout() 调用创建的定时器的编号。这个值可以传递给 clearTimeout() 来取消该定时器。

	setInterval(func, [delay, arg1, arg2, ...]);
		* 每经过指定 delay 毫秒后执行一次 func，第一次调用发生在 delay 毫秒之后。
			
			func
				* 要执行方法
			
			delay
				* 毫秒，延迟时间，不指定的为 0。
			
			arg
				* 运行时传递给 func 的参数。
	
		* 返回值 intervalID 是一个非零数值，表示定时器的 ID，这个值可以用来作为 clearInterval() 的参数来清除对应的定时器。

	dispatchEvent(event)
		* 调度指定的事件

		event
			* 被调度的 Event。其 Event.target 属性为当前的 EventTarget。
		
	matchMedia(mediaQueryString)
		* https://developer.mozilla.org/zh-CN/docs/Web/API/Window/matchMedia
		* 返回一个新的 MediaQueryList 对象，表示指定的媒体查询字符串解析后的结果。
		
		mediaQueryString
			* 用于媒体查询解析的字符串。
		

--------------------
static 
--------------------

