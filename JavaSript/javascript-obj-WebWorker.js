--------------------------------
Worker							|
--------------------------------
	# Hello World
		let worker = new Worker('./foo.js');
		worker.onmessage = function(event){
			let data = event.data;
			console.log(data);
		}
	
		---- foo.js
		let x = 0;

		for(let i = 0 ;i < 10000; i++){
			x +=i ;
		}

		postMessage(x);
	
	# Worker线程中允许使用的API
		* 部分api不允许在worker线程中使用,包括alert等等...
		

--------------------------------
Worker	实例方法				|
--------------------------------
	void postMessage(JSObject message);
		* 发送执行完毕的数据,任意值

	void terminate();
		* 停止worker对象

--------------------------------
Worker	实例对象				|
--------------------------------

	onmessage
		* 一个事件监听函数,每当拥有 message 属性的 MessageEvent 从 worker 中冒泡出来时就会执行该函数,事件的 data 属性存有消息内容

	onerror
		* 一个事件监听函数,每当类型为  error 的  ErrorEvent 从 worker 中冒泡出来时就会执行该函数

	* Event对象包含三属性
		data
			* 线程执行的结果
		message
			* 一个可读性良好的错误信息
		filename
			* 产生错误的脚本文件名
		lineno