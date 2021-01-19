------------------------
CSP机制					|
-----------------------
	# 参考地址
		http://www.ruanyifeng.com/blog/2016/09/csp.html
		https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CSP
	
	# 还可以通过 meta 标签来限制
		<meta http-equiv="Content-Security-Policy" content="script-src 'self'; object-src 'none'; style-src cdn.example.org third-party.org; child-src https:">
	
	# 通过HttpHeader来限制
		Content-Security-Policy
	
	# 可限制的资源列表
		script-src		外部脚本
		style-src		样式表
		img-src			图像
		media-src		媒体文件（音频和视频）
		font-src		字体文件
		object-src		插件（比如 Flash）
		child-src		框架
		frame-ancestors	嵌入的外部资源（比如<frame>、<iframe>、<embed>和<applet>）
		connect-src		HTTP 连接（通过 XHR、WebSockets、EventSource等）
		worker-src		worker脚本
		manifest-src	manifest 文件
		form-action		Form表单的action
		frame-src		使用元素(如<frame>和<iframe>)加载的嵌套浏览上下文指定有效来源

	# default-src 默认值
		* default-src用来设置上面各个选项的默认值
			Content-Security-Policy: default-src 'self'
		
		* 如果同时设置某个单项限制,那么会覆盖默认的值
	
	# URL 限制
		frame-ancestors	限制嵌入框架的网页
		base-uri		限制<base#href>
		form-action		限制<form#action>
	
	
	# 其他限制
		block-all-mixed-content		HTTPS 网页不得加载 HTTP 资源（浏览器已经默认开启）
		upgrade-insecure-requests	自动将网页上所有加载外部资源的 HTTP 链接换成 HTTPS 协议
		plugin-types				限制可以使用的插件格式
		sandbox						浏览器行为的限制，比如不能有弹出窗口等。
	
	# report-uri 报告
		* report-uri就用来告诉浏览器，应该把注入行为报告给哪个网址
			report-uri /csp_report_parser;
		
		* 浏览器会使用POST方法，发送一个JSON对象
			{
			  "csp-report": {
				"document-uri": "http://example.org/page.html",	//发生违规的文档的URI。
				"referrer": "http://evil.example.com/",	//referrer
				"blocked-uri": "http://evil.example.com/evil.js",	//被CSP阻止的资源URI。如果被阻止的URI来自不同的源而非文档URI，那么被阻止的资源URI会被删减，仅保留协议，主机和端口号。
				"violated-directive": "script-src 'self' https://apis.google.com", //违反的策略名称。
				"original-policy": "script-src 'self' https://apis.google.com; report-uri http://example.org/my_amazing_csp_report_parser" // 在 Content-Security-Policy HTTP 头部中指明的原始策略。
			  }
			}

	# Content-Security-Policy-Report-Only
		* 不执行限制选项，只是记录违反限制的行为
		* 因为只记录,所以它必须与report-uri选项配合使用
			Content-Security-Policy-Report-Only: default-src 'self'; ...; report-uri /my_amazing_csp_report_parser;
	
	# 可以限制的选项值
		主机名: 
			example.org
			https://example.com:443

		路径名: 
			example.org/resources/js/

		通配符: 
			*.example.org,	表示任意协议
			*://*.example.com:* 任意子域名,任意端口
		协议名:
			https:
			data:
		关键字: 'self' 当前域名，需要加引号
		关键字: 'none' 禁止加载任何外部资源，需要加引号
	
		* 可以有多个值,使用空格分割,每个选项使用;分割
			Content-Security-Policy: default-src 'none'; img-src 'self' data:; script-src 'self'

		* 如果同一个限制选项使用多次,只有第一次会生效

	# script-src 的特殊值
		* 注意，下面这些值都必须放在单引号里面

		'unsafe-inline':允许执行页面内嵌的<script>标签和事件监听函数

		unsafe-eval:允许将字符串当作代码执行，比如使用eval、setTimeout、setInterval和Function等函数。

		nonce值:每次HTTP回应给出一个授权token，页面内嵌脚本必须有这个token，才会执行
			Content-Security-Policy: script-src 'nonce-EDNnf03nceIOfn39fn3e9h3sdfa'
			<script type="text/javascript" nonce="EDNnf03nceIOfn39fn3e9h3sdfa">
				console.log('内联代码执行');
			</script>

		hash值:列出允许执行的脚本代码的Hash值，页面内嵌脚本的哈希值只有吻合的情况下，才能执行。
			Content-Security-Policy: script-src 'sha256-qznLcsROx4GACP2dm0UCKCzCG-HiZ1guq6ZZDob_Tng='
		
		* nonce和hash还可以用在style-src
	
	# script-src和object-src是必设的，除非设置了default-src
		* object-src必设是因为 Flash 里面可以执行外部脚本
	
	# script-src不能使用unsafe-inline关键字（除非伴随一个nonce值），也不能允许设置data:URL。
		<img src="x" onerror="evil()">					//unsafe-inline 会导致它执行
		<script src="data:text/javascript,evil()"></script>	//允许 data:协议,会导致它执行
	
	# 需要注意jsonp也有可能导致xss
		<script
			src="/path/jsonp?callback=alert(document.domain)//">
		</script>


------------------------
CSP机制	总结			|
-----------------------
	# http头名称
		Content-Security-Policy
	
	# 可以设置的选项
		script-src		外部脚本
		style-src		样式表
		img-src			图像
		media-src		媒体文件（音频和视频）
		font-src		字体文件
		object-src		插件（比如 Flash）
		child-src		框架
		frame-ancestors	嵌入的外部资源（比如<frame>、<iframe>、<embed>和<applet>）
		connect-src		HTTP 连接（通过 XHR、WebSockets、EventSource等）
		worker-src		worker脚本
		manifest-src	manifest 文件
		default-src		所有选项的默认选项(可以被覆盖)
		form-action		Form表单的action

	# 可以设置的值
		* 主机名: 
			example.org
			https://example.com:443

		* 路径名: 
			example.org/resources/js/

		* 通配符: 
			*.example.org,				//表示任意协议
			*:/\/\*.example.com:*			//任意协议,任意子域名,任意端口

		* 协议名:
			https:
			data:

		* 关键字: 'self' 当前域名，需要加引号
		* 关键字: 'none' 禁止加载任何外部资源，需要加引号
	
	# script-src 的特殊值
		* 注意，下面这些值都必须放在单引号里面

		'unsafe-inline':允许执行页面内嵌的<script>标签和事件监听函数

		unsafe-eval:允许将字符串当作代码执行，比如使用eval、setTimeout、setInterval和Function等函数。

		nonce值:每次HTTP回应给出一个授权token，页面内嵌脚本必须有这个token，才会执行
			Content-Security-Policy: script-src 'nonce-EDNnf03nceIOfn39fn3e9h3sdfa'
			<script type="text/javascript" nonce="EDNnf03nceIOfn39fn3e9h3sdfa">
				console.log('内联代码执行');
			</script>

		hash值:列出允许执行的脚本代码的Hash值，页面内嵌脚本的哈希值只有吻合的情况下，才能执行。
			Content-Security-Policy: script-src 'sha256-qznLcsROx4GACP2dm0UCKCzCG-HiZ1guq6ZZDob_Tng='
		
		* nonce和hash还可以用在style-src
	
	# 其他限制选项(没有值)
		block-all-mixed-content		HTTPS 网页不得加载 HTTP 资源(浏览器已经默认开启)
		upgrade-insecure-requests	自动将网页上所有加载外部资源的 HTTP 链接换成 HTTPS 协议
		plugin-types				限制可以使用的插件格式
		sandbox						浏览器行为的限制,比如不能有弹出窗口等

	
	# report-uri 报告
		* report-uri就用来告诉浏览器,应该把注入行为报告给哪个网址
			report-uri /csp_report_parser;
		
		* 浏览器会使用POST方法，发送一个JSON对象
			{
			  "csp-report": {
				"document-uri": "http://example.org/page.html",	//发生违规的文档的URI。
				"referrer": "http://evil.example.com/",	//referrer
				"blocked-uri": "http://evil.example.com/evil.js",	//被CSP阻止的资源URI。如果被阻止的URI来自不同的源而非文档URI，那么被阻止的资源URI会被删减，仅保留协议，主机和端口号。
				"violated-directive": "script-src 'self' https://apis.google.com", //违反的策略名称。
				"original-policy": "script-src 'self' https://apis.google.com; report-uri http://example.org/my_amazing_csp_report_parser" // 在 Content-Security-Policy HTTP 头部中指明的原始策略。
			  }
			}