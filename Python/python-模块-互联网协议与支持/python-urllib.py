----------------------------
urllib						|
----------------------------
	* 它是一个包,具有模块
		request
		parse

	* 一系列用于操作URL的功能
	* 可以非常方便地抓取URL内容

----------------------------
urllib-属性					|
----------------------------

----------------------------
urllib-函数					|
----------------------------

----------------------------
urllib-request				|
----------------------------
	* 用于发起HTTP请求的模块
	* 方法
		urlopen(url, data=None, timeout=socket._GLOBAL_DEFAULT_TIMEOUT,*, cafile=None, capath=None, cadefault=False, context=None)
			* 打开连接,返回实例对象(http.client.HTTPResponse)
			* 参数是可以一个目标 URL
			* 或者是一个 Request 对象
			* url: 可以是一个字符串类型的URL,或者是 request.Request 对象
			* 关键字参数:
				data		# 请求体,字节类型
				timeout		# 超时时间
		
		urlretrieve(url,path,call,data)
			* 打开url,并且把响应的数据保存到path
			* 可以用于下载图片/视频的连接
			* 参数
				url 资源路径
				path 下载路径
				call 回调,可以通过该函数获取到文件大小,已下载大小等数据
				data post到服务器提交的数据
		
		Request(url)
			* 创建一个request对象,可以设置请求头等信息

		OpenerDirector()
			* 创建一个 opener 对象

		install_opener(opener)
			* 用来创建(全局)默认opener
			* 调用 urlopen 将使用该api install 的 opener

		build_opener(*handlers)
			* 创建一个 空的opener
		
------------------------------------
urllib-request-opener & handler		|
------------------------------------
	* opener 可以有n多个handler,hander用于处理不同的请求和应用场景
	* opener 
		* 创建
			opener = request.OpenerDirector()
				* 通过构造函数创建,返回没有任何handler的一个opener

			opener = request.build_opener(*handler)
				* request 对象直接创建,会默认添加一些handler
	
		* 方法
			add_handler(handler)
				* 添加handler
			
			open(fullurl, data=None, timeout=socket._GLOBAL_DEFAULT_TIMEOUT)
				* 执行请求
				* 参数
					fullurl
						* 可以是字符串的url,也可以是 Request 实例对象
					
	* handler
		* 系统预定义处理器(request)类
			AbstractHTTPHandler
			ProxyDigestAuthHandler
			HTTPDigestAuthHandler
			AbstractDigestAuthHandler
			AbstractBasicAuthHandler
			HTTPSHandler
				* https handler
			HTTPRedirectHandler
			CacheFTPHandler
			BaseHandler
			ProxyHandler({'http':'183.17.123.152:9000'})		
				* 代理handler,通过代理服务器发送http服务
				* 参数为dict:(不传递,或者传递空{},表示不代理)
						key		表示代理的协议
						value	ip:port
				* 具备密码的代理服务器
					ProxyHandler({'http':'kevin:123@183.17.123.152:9000'})

			HTTPDefaultErrorHandler
			HTTPBasicAuthHandler
				* tomcat的manager页面,就需要身份验证,就可以用这个
				* demo
					# 创建密码管理器
					realm = request.HTTPPasswordMgrWithDefaultRealm()
					# 设置域/站点ip/账户/密码 到密码管理器
					realm.add_password(None,'192.168.21.52','root','123456')
					# 创建http验证handler
					handler = request.HTTPBasicAuthHandler(realm)
					# 创建一个opener
					opener = request.build_opener(handler)
					# 打开url
					with opener.open('http://192.168.21.52/admin') as response:
						print(response.read())

			ProxyBasicAuthHandler
				* 代理授权验证,作用就是用来处理需要用户密码验证的代理服务器
				* 一般不怎么建议使用,直接使用 ProxyHandler 就行了
				* demo
					# 创建密码管理器
					realm = HTTPPasswordMgrWithDefaultRealm()
					# 密码管理器,设置代理服务器 域/ip:端口/用户名/密码
					realm.add_password(None,'183.17.123.152:9000','kevin','123')
					# 添加密码管理器到 ProxyBasicAuthHandler
					proxyAuthHandler = ProxyBasicAuthHandler(realm)
					# 创建 opener
					opener = request.build_opener(proxyAuthHandler)
					with opener.open('http://javaweb.io') as response:
						print(response.read())


			HTTPHandler
				* http handler
			FileHandler
			FTPHandler
			CacheFTPHandler
			DataHandler
			UnknownHandler
			HTTPCookieProcessor
				* BaseHandler 的子类,用于处理服务器端响应的cookie数据
		
		* 部分handler通用的构造函数参数
			debuglevel
				* 如果该值为1/True,则表示开启DEBUG模式,会打印出一些日志信息
	
	* Realm
		|-HTTPPasswordMgr
			|-HTTPPasswordMgrWithDefaultRealm
				* 默认的密码管理对象,用于保存和http请求相关的授权信息
				* 一般会用在两个地方:
					* ProxyBasicAuthHandler - 授权代理的处理器
					* HTTPBasicAuthHandler	- 验证web客户端的授权处理器

	* simple demo
		# 手动创建opener
		opener = request.OpenerDirector()
		#
		# 手动添加一个 HTTPHandler,可以处理http请求
		opener.add_handler(request.HTTPHandler())


		# 通过request build 一个opener,会有一些默认的handler
		opener = request.build_opener(request.HTTPHandler(debuglevel=1),request.HTTPSHandler(debuglevel=True))

		# 构建请求信息
		req = request.Request('http://www.baidu.com')

		# 通过 opener 打开
		with opener.open(req) as response:
			print(response.read())
	
	* ip代理 demo
		from urllib import request
		# 通过request build 一个opener,会有一些默认的handler
		opener = request.build_opener()
		# 创建一个代理handler,指定代理的协议,代理服务器的ip与端口
		proxyHandler = request.ProxyHandler({'http':'112.114.93.39:8118'})

		'''
			如果代理服务器需要账户名/密码授权:{'协议':'用户名:密码@ip:port'}
			ProxyHandler({'http':'kevin:123@183.17.123.152:9000'})
		'''

		# 添加代理handler到opener
		opener.add_handler(proxyHandler)
		# 构建请求信息
		req = request.Request('http://javaweb.io')
		# 通过 opener 打开
		with opener.open(req) as response:
			print(response.read())
	
	* 站点身份验证 demo
		from urllib import request
		# 创建密码管理器
		realm = request.HTTPPasswordMgrWithDefaultRealm()
		# 设置域/站点ip/账户/密码 到密码管理器
		realm.add_password(None,'192.168.21.52','root','123456')
		# 创建http验证handler
		handler = request.HTTPBasicAuthHandler(realm)
		# 创建一个opener
		opener = request.build_opener(handler)
		# 打开url
		with opener.open('http://192.168.21.52/admin') as response:
			print(response.read())

-----------------------------------------------
cookielib & request.HTTPCookieProcessor			|
------------------------------------------------
	* cookielib 是独立的库,需要单独道导入,主要作用就是用来保存Cookie
		* python2.7中模块名: cookielib
		* python3.x中模块名: http.cookiejar	(from http import cookiejar)

	* cookielib 模块主要的对象
		CookieJar(常用)
			* 管理HTTP cookie 值,存储http请求生成的cookie,向发起的http请求添加cookie对象
			* cookie都存储在内存中,CookieJar 实例被垃圾回收后,cookie也会消失  
			
		FileCookieJar(filename,delayload=None,policy=None)
			* CookieJar 的子类,会把cookie信息持久化到硬盘
			* 构造参数
				filename	存储cookie的文件名
				delayload	bool值,是否支持延迟加载(需要的时候才回去读取cookie文件)

		MozillaCookieJar
			* FileCookieJar 子类,创建与 Mozilla内核浏览器兼容的 FileCookieJar 实例

		LWPCookieJar
			* FileCookieJar 子类,创建与 libwww-per 标准的 Set-Cookie3 兼容的 FileCookieJar 实例
	
	* HTTPCookieProcessor 是 urllib.request 的对象(Handler系列),作用就是保存服务器响应的cookie

	* cookie处理 demo
		
		from urllib import request
		from http import cookiejar
		# 创建 cookie 对象,用于保存cookie信息
		cookie = cookiejar.CookieJar()
		# 创建 HTTPCookieProcessor 对象(Cookie处理器对象)
		# 返回的就是一个处理器对象
		cookieHandler = request.HTTPCookieProcessor(cookie)
		# 创建opener
		opener = request.build_opener()
		# 添加cookie处理器对象到opener
		opener.add_handler(cookieHandler)
		with opener.open('http://javaweb.io') as response:
			print(cookie)
			# <CookieJar[<Cookie JSESSIONID=121BA7EE1190B9358F80E219F5DD3EEB for javaweb.io/>]>
		
		# 此时,该 opener 已经有了该cookie,如果该 opener 再次发起请求,会携带cookie

	* 登录 javaweb.io 实战
		from urllib import request,parse
		from http import cookiejar
		cookie = cookiejar.CookieJar()
		cookieHandler = request.HTTPCookieProcessor(cookie)
		opener = request.build_opener()
		opener.add_handler(cookieHandler)
		# 打开页面,获取服务器的cookie
		with opener.open('http://javaweb.io') as response:
			# 使用该cookie重定向到登录页面(多余操作)
			with opener.open('http://javaweb.io/login') as response:
				# 通过该cookie读取验证码数据
				with opener.open('http://javaweb.io/verifycode') as response:
					# 输入通过后台日志看到的验证码信息
					verifyCode = input('验证码:')
					req = request.Request('http://javaweb.io/login')
					requestBody = parse.urlencode({
						'name': 'root',
						'pass': 'root',
						'verifyCode': verifyCode,
					})
					# 通过该cookie执行登录
					with opener.open(req,data=bytes(requestBody,'utf_8')) as response:
						# 登录ok,因为该cookie已经具备登录凭证,可以进入主页
						with opener.open('http://javaweb.io') as response:
							print(response.read().decode())
		
----------------------------
urllib-HTTPResponse			|
----------------------------
	* Http响应对象,也就是 request.open() 后返回的对象
	* 实例属性
		status
			* HTTP状态码
		reason
			* 信息

	* 方法
		bytes read()
			* 读取响应体

		bytes readline()
			* 读取一行数据

		int getcode()
			* 返回HTTP响应码

		str geturl()
			* 返回请求的URL

		list readlines()
			* 读取所有行数据

		list getheaders()
			* 获取响应头
			* 是个 tuple 的 list,第一个参数是头名,第二个参数是值
				[('Server', 'nginx/1.13.0'), ('Date', 'Fri, 14 Jul 2017 04:09:01 GMT'), ('Content-Type', 'text/html'), ('Content-Length', '306'), ('Connection', 'close'), ('Accept-Ranges', 'bytes'), ('ETag', 'W/"306-1499759074000"'), ('Last-Modified', 'Tue, 11 Jul 2017 07:44:34 GMT')]

		None close()
			* 关闭资源

----------------------------
urllib-request.Request		|
----------------------------
	* 用于模拟浏览器发起HTTP请求
	* 创建实例对象
		request.Request(url)
			* 通过目标地址,url 创建请求实例对象
	
	* 方法
		add_header(k,v)
			* 添加请求头

----------------------------
urllib-parse				|
----------------------------
	* 可以把参数解析为URL编码的模块
	* 方法
		urlencode(query, doseq, safe, encoding, errors, quote_via)
			* 把参数解析为URL编码的字符串
			* query参数可以是[(k,v)],也可以是{k:v}
		
		parse_qs(qs, keep_blank_values, strict_parsing, encoding, errors)
			* 把URL编码的字符解析为[],列表中的每个元素都是一个 tuple
			* 就是把key value 请求体转换为[(key,value)]
		
		quote(string, safe='/', encoding=None, errors=None)
			* 把一个带汉字的url,进行url编码
		
		unquote(string, encoding='utf-8', errors='replace')
			* 把一个url编码的字符串,转换为汉字url

	* demo
		parse.urlencode([
			('username', 'Kevin'),
			('password', 'Keivn'),
			('entry', 'mweibo'),
			('client_id', ''),
			('savestate', '1'),
			('ec', ''),
			('pagerefer', 'https://passport.weibo.cn/signin/welcome?entry=mweibo&r=http%3A%2F%2Fm.weibo.cn%2F')
		])
	
	* 使用 parse.quote 处理中文url(对中文进行url编码)

		r = parse.quote('http://www.javaweb.io/你好我是KevinBlandy/')
		print(r)
		# http%3A//www.javaweb.io/%E4%BD%A0%E5%A5%BD%E6%88%91%E6%98%AFKevinBlandy/
		# 连 http:// 的:也被转义了,这个显然是不行的

		r = parse.quote('http://www.javaweb.io/你好我是KevinBlandy/',safe='://')
		print(r)
		# http://www.javaweb.io/%E4%BD%A0%E5%A5%BD%E6%88%91%E6%98%AFKevinBlandy/

		* 通过关键字参数 safe 来指定哪些字符串不用转义

			
		
----------------------------
urllib-demo					|
----------------------------
# 简单的GET请求
from urllib import request
with request.urlopen('http://springboot.io') as response:
    
    # 响应码
    response_code = response.status
    
    # 异常原因
    response_reason = response.reason
    
    # 响应头集合
    response_headers = response.getheaders()
    
    # 响应体,字节
    response_body = response.read()


# 带参数的GET请求
from urllib import request
client = request.Request("http://springboot.io")
# 添加请求头
client.add_header("Origin", "http://springboot.io")

with request.urlopen(client) as response:
    # 响应码
    response_code = response.status
    
    # 异常原因
    response_reason = response.reason
    
    # 响应头集合
    response_headers = response.getheaders()
    
    # 响应体,字节
    response_body = response.read()


# POST请求
from urllib import request,parse

client = request.Request("http://springboot.io")
# 添加请求头
client.add_header("Origin", "http://springboot.io")

# 请求体,可以是JSON字符串
data = parse.urlencode([
    ('key','value')
])

with request.urlopen(client,data=data.encode(encoding='utf_8', errors='strict')) as response:
    # 响应码
    response_code = response.status
    
    # 异常原因
    response_reason = response.reason
    
    # 响应头集合
    response_headers = response.getheaders()
    
    # 响应体,字节
    response_body = response.read()