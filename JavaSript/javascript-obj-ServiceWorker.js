
----------------------------
ServiceWorer				|
----------------------------
	# 参考
		https://developer.mozilla.org/zh-CN/docs/Web/API/Service_Worker_API
		https://worktile.com/blog/tech/tech-service-worker

	# 谷歌的Demo
		https://github.com/GoogleChrome/samples/tree/gh-pages/service-worker/basic

	# 注册一个sw
		if ('serviceWorker' in navigator) {
			window.addEventListener('load', function() {
				navigator.serviceWorker.register('/sw.js');
			});
		}
		
		* register 返回一个 Promise 对象
		* 没有必要争第一次打开页面就要缓存资源, 正确的做法是, 页面加载完以后sw的事
	
	# sw的限制
		* 不能使用同步的API(XMLHttpRequest,localStorage ...), 可以使用 Fetch
		* 可以使用 self 关键字来指向 this
		* 只能使用 https 或者 localhost
		* 不能访问DOM, 完全异步
		* 可以访问Cache和IndexDB

	# sw的作用域不同, 监听的 fetch 请求也是不一样的
		* 假设的sw文件放在根目录下位于 '/sw/sw.js' 路径的话, 那么你sw就只能监听 '/sw/*'下面的请求
		* 如果想要监听所有请求有两个办法, 一个是将sw.js放在根目录下

		* 或者是在注册是时候设置scope, 
			navigator.serviceWorker.register('/static/service-worker.js', {scope: '/'}).then(registration => {
				console.log('Registered events at scope: ', registration.scope);
			}).catch(err => {
				console.error(err)
			});
		* 并且这种方式, 需要服务器响应该js(WS)的文件时候, 添加一个http头来限制SW可以监听到请求的路径
			Service-Worker-Allowed: /
	


----------------------------
ServiceWorer 事件和生命周期	|
----------------------------
	# 生命周期
		register 
			* 这个是由 client 端发起, 注册一个 serviceWorker, 这需要一个专门处理sw逻辑的文件

		Parsed 
			* 注册完成，解析成功，尚未安装

		installing 
			* 注册中，此时 sw 中会触发 install 事件，
			* 需知 sw 中都是事件触发的方式进行的逻辑调用，如果事件里有 event.waitUntil() 则会等待传入的 Promise 完成才会成功

		installed(waiting) 
			* 注册完成，但是页面被旧的 Service Worker 脚本控制,
			* 所以当前的脚本尚未激活处于等待中，可以通过 self.skipWaiting() 跳过等待。

		activating 
			* 安装后要等待激活，也就是 activated 事件，只要 register 成功后就会触发 install ，但不会立即触发 activated
			* 如果事件里有 event.waitUntil() 则会等待这个 Promise 完成才会成功，这时可以调用 Clients.claim() 接管所有页面。

		activated 
			* 在 activated 之后就可以开始对 client 的请求进行拦截处理，sw 发起请求用的是 fetch api，XHR无法使用

		fetch 
			* 激活以后开始对网页中发起的请求进行拦截处理

		terminate
			* 这一步是浏览器自身的判断处理，当 sw 长时间不用之后，处于闲置状态，浏览器会把该 sw 暂停，直到再次使用

		update
			* 浏览器会自动检测 sw 文件的更新，当有更新时会下载并 install，但页面中还是老的 sw 在控制，只有当用户新开窗口后新的 sw 才能激活控制页面

		redundant
			* 安装失败, 或者激活失败, 或者被新的 Service Worker 替代掉

	# sw可以监听的事件
		self.addEventListener('install', (event) => {
			
		});

	install
		* 主要的任务是, 抓取资源进行缓存

	activate
		* 遍历缓存, 清除过期的资源

	fetch
		* 拦截请求, 查询缓存或者网络, 返回请求的资源
		


----------------------------
ServiceWorer API			|
----------------------------
	# 它是: navigator 对象的一个属性

	register(js, options)
		* 注册一个ws, 返回的是 Promise
		* 第1个参数是js的路径
		* 第2个参数是配置对象
			scpe
				* 监听请求的scope

		* Promise参数的属性和方法
			scope	
				* 监听请求的scope

	getRegistration(js)
		* 获取已经注册到的ws, 返回Promise
		* Promise参数的属性和方法
			unregister()
				* 取消注册(注册)
