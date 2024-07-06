--------------------
fetch				|
--------------------
	# 文档
		https://developer.mozilla.org/zh-CN/docs/Web/API/Fetch_API

	# 语法
		Promise<Response> fetch(input[, init]);
			* input 即 URL
			* 可选的配置对象

			* 返回 Promise
			* 服务器返回 400,500 错误码时并不会 reject,只有网络错误这些导致请求不能完成时,fetch 才会被 reject
	
	# options 
		credentials
			* 默认仨值
				"omit",			//不发送
				"same-origin"	//同域发送
				"include"		//总是发送
			* Fetch 请求默认是不带 cookie 的,需要设置 credentials: 'include' 
			* fetch(url, {credentials: 'include'})

		body
			* 请求体数据,可以是FormData,URLSearchParams或者字符串
			* 如果是GET请求,不允许有该参数

		method
			* 以字符串形式指定请求方法

		headers
			* 对象，请求头，可以是 Headers 对象或者带有字符串值的对象字面量

		mode
			* 关于跨域的一些配置
				"same-origin"	
					* 只有来自同域的请求才能成功,其它的均将被拒绝
				"cors"			
					* 允许不同域的请求,但要求有正确的 CORS 头信息
				"cors-with-forced-preflight" 
					* 在执行真正的调用前先执行preflight check
				"no-cors" 
					* 目前这种模式是无法执行的。
				"navigate"

		cache
			* 表示处理缓存的策略
				default：默认值，先在缓存里面寻找匹配的请求。
				no-store：直接请求远程服务器，并且不更新缓存。
				reload：直接请求远程服务器，并且更新缓存。
				no-cache：将服务器资源跟本地缓存进行比较，有新的版本才使用服务器资源，否则使用缓存。
				force-cache：缓存优先，只有不存在缓存的情况下，才请求远程服务器。
				only-if-cached：只检查缓存，如果缓存里面不存在，将返回504错误。

		redirect
			* 表示发生重定向时,有三个选项
			follow		跟随
			error:		发生错误
			manual		需要用户手动跟随

		integrity	
			* ntegrity属性指定一个哈希值，用于检查 HTTP 回应传回的数据是否等于这个预先设定的哈希值。
				fetch('http://site.com/file', {
				  integrity: 'sha256-abcdef'
				});

		referrer
			* 用于设定fetch()请求的referer标头
			* 这个属性可以为任意字符串，也可以设为空字符串（即不发送referer标头）。
				fetch('/page', {
				  referrer: ''
				});

		referrerPolicy
			* 指定请求所使用的 referrer policy，枚举字符串
	
		keepalive
			* 是否保持连接
		
		signal
			* 一个 AbortSignal 对象实例；允许你通过 AbortController 与 fetch 请求进行通信，并在需要时中止请求。
	
		priority
			* 相对于其他同类型的请求的 fetch 请求的优先级。必须是以下字符串之一：
				high/low/auto

--------------------
Headers				|
--------------------
	# Headers接口是一个简单的多映射的名-值表
		let reqHeaders = new Headers();
		reqHeaders.append("Content-Type", "text/plain");
		reqHeaders.append("X-Custom-Header", "ProcessThisImmediately");
	
	# 也可以通过构造函数传一个多维数组或者json来创建
		reqHeaders = new Headers({
			"Content-Type": "text/plain",
			"Content-Length": content.length.toString(),
			"X-Custom-Header": "ProcessThisImmediately",
		});
		
	# Headers 对象有一个特殊的 guard 属性
		* 这个属性没有暴露给Web,但是它影响到哪些内容可以在Headers对象中被改变
	
		"none"				默认的
		"request"			从Request中获得的Headers只读
		"request-no-cors"	从不同域的Request中获得的Headers只读
		"response"			从Response获得的Headers只读
		"immutable"			在ServiceWorkers中最常用的,所有的Headers都只读

	
	# Headers实例的api

		has(key)
			* 判断指定的请求头是否存在
		set(key,value)
			* 设置新的请求头,会覆盖掉同名的
		append(key,value)
			* 设置新的请求头,如果存在同名,不会覆盖,会添加
		get(key)
			* 获取指定名称请求头的值
		getAll(key)
			* 获取指定名称请求头的所有值,返回[]
		delete(key)
			* 删除指定的请求头
		keys()
			* 返回一个遍历器，可以依次遍历所有键名。
		values()
			* 返回一个遍历器，可以依次遍历所有键值。
		entries()
			* 返回一个遍历器，可以依次遍历所有键值对（[key, value]）。
		forEach()
			* 依次遍历标头，每个标头都会执行一次参数函数。
		getSetCookie()

--------------------
Request				|
--------------------
	# Request 接口定义了通过 HTTP 请求资源的 request 格式，构造函数和 fetch 参数一样。
		* 语法
			Request(url, options)
			
			* 构造函数和 fetch 参数一样。
	
			var req = new Request("/index.html");
			console.log(req.method); 	// "GET"
			console.log(req.url); 		// "http://example.com/index.html"
		
		* 也可以将一个建好的Request对象传给构造函数,这样将复制出一个新的Request
			var copy = new Request(req);
			console.log(copy.method);	// "GET"
			console.log(copy.url);		// "http://example.com/index.html"
	

	# 属性
		body
		bodyUsed
		cache
		credentials
		destination
		headers
		integrity
		method
		mode
		redirect
		referrer
		referrerPolicy
		signal
		url
	
	# 方法
		arrayBuffer()
		blob()
		clone()
		formData()
		json()
		text()

--------------------
Response			|
--------------------
	# Response有个接收两个可选参数的构造器,第一个参数是返回的body,第二个参数是一个json,设置status、statusText以及headers

		let myResponse = new Response(body, init);

			body
				* 响应体，可以是如下类型
					Blob
					BufferSource
					FormData
					ReadableStream
					URLSearchParams
					USVString
			
			init
				* 配置类
				status
					* response 的状态码，例如：200.
				statusText
					* 和状态码关联的状态消息，例如：OK.
				headers
					* 加到 response 上的任何 headers，包含了一个 Headers 对象或满足对象语法的 ByteString key/value 对。




	# 属性

		body
			* 露出的底层接口，返回一个 ReadableStream 对象，供用户操作
			* 它可以用来分块读取内容，应用之一就是显示下载的进度。
				const response = await fetch('flower.jpg');
				const reader = response.body.getReader();
				while(true) {
				  const {done, value} = await reader.read();

				  if (done) {
					break;
				  }
				  console.log(`Received ${value.length} bytes`)
				}
		
		bodyUsed
			* 表示该 body 是否被使用过。
		
		headers
			* 只读属性 headers 包含与响应关联的Headers对象。
		
		ok
			* 是否成功,根据http状态码决定(200-299）)
		
		redirected
			* 是否来自一个重定向，如果是的话，它的 URL 列表将会有多个条目。
		status
			*  Response 的状态码
		
		statusText
			*  Response 状态码一致的状态信息（例如，OK 对应 200）。
		type
			* 类型,枚举字符串值
				"basic"		正常的,同域的请求,包含所有的headers除开"Set-Cookie"和"Set-Cookie2"
				"cors"		Response从一个合法的跨域请求获得,一部分header和body可读。
				"error"		网络错误:Response的status是0,Headers是空的并且不可写,当Response是从Response.error()中得到时,就是这种类型
				"opaque"	Response从"no-cors"请求了跨域资源,依靠Server端来做限制。
				"error"		类型会导致fetch()函数的Promise被reject并回调出一个TypeError
				"opaqueredirect"	如果fetch()请求的redirect属性设为manual，就会返回这个值，详见请求部分。
		url
			* Response 的 URL。


	# 方法，都返回 Promise
		arrayBuffer()
		blob()
		clone()
			* 克隆，可以实现多次读取 Body

		formData()
		json()
		text()

	
	# 静态方法
		error()
		json()
		redirect()
	

--------------------
Demo				|
--------------------
	let requestBody = new URLSearchParams();
	requestBody.set('account','KevinBlandy');
	requestBody.set('password','123456');
	
	fetch(new Request('/login',{
		method:'POST',
		headers:new Headers({
			'X-Requested-With':'XMLHttpRequest'
		}),
		body:requestBody
	})).then(function(response){
		if(response.ok){
			response.json().then(function(rep){
				console.log(rep);
			});
		}
	}).catch(function(error){
		console.log(error);
	});


	fetch("/index", {
		method: "POST", 
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: new URLSearchParams("name=foo&age=22")
	})
	.then(resp => {})