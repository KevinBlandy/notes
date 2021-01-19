----------------------------
requests					|
----------------------------
	# http 库
	# 文档地址
		http://docs.python-requests.org/zh_CN/latest/user/quickstart.html
		http://cn.python-requests.org/zh_CN/latest/

	# 安装
		pip install requests

----------------------------
requests	 执行请求		|
----------------------------
	# 执行各种方法的请求
		requests.get()
		requests.post()
		requests.delete()
		requests.put()
		requests.heade()
		requests.options()
		requests.patch()
	
	# 执行请求的参数
		第一个参数
			* 就是url
		params
			* 是一个 dict,在get请求下,会被编码为URL编码
			* 序列化为请求参数,添加到uri后面
			* None 不会被添加到参数
			* 参数值可以是一个数组
				payload = {'key1': 'value1', 'key2': ['value2', 'value3']}
				http://xxx.org/get?key1=value1&key2=value2&key2=value3
		stream
			* bool 值,响应是否是一个数据流
			* 如果该值为 True,那么可以通过response的raw属性获取到服务器的原始套接字响应
			* 默认情况下,当进行网络请求后,响应体会立即被下载,可以通过设置 stream=True 改变这个行为,推迟下载响应体直到访问 response.content 属性
			* 如果stream=True,Requests 无法将连接释放回连接池,除非消耗了所有的数据,或者调用了 Response.close 这样会带来连接效率低下的问题
			* 应该考虑使用 with 语句发送请求,这样可以保证请求一定会被关闭
				with requests.get('http://xxx.org/get', stream=True) as response:
					pass

		headers 
			* 参数是一个 dict ,表示自定义的请求头 
			*  header 值必须是 string,bytestring 或者 unicode,尽管传递 unicode header 也是允许的,但不建议这样做
		
		data
			* 请求体,在 post 的时候,会被编码为表单
			* 它一般应该是 dict ,或者 tuple(多个key),或者字符串(自己的字符串格式)
				requestBody = (('key1', 'value1'), ('key1', 'value2'))
				# key1=value1&key1=value2
			
		json
			* 把请求体编码为JSON字符串
	
		files
			* ContentType = multipart/form-data 时的请求体
			* 下面有单独的笔记
		
		cookies
			* 设置cookie
			* 参数是一个 dict

		allow_redirects
			* 是否允许重定向
			*  bool 值,如果该值为 True,则会自动的重新请求 302/1 的结果
		
		timeout
			* 设置连接超时时间,单位为秒
			* 超时后会抛出异常:requests.exceptions.Timeout
			* 它可以是一个 tuple,这样的话第一个参数表示连接超时时间,第二个参数表示读取数据超时时间
			* 如果该值为 None,则表示永远等待
		
		verify
			* 可以是一个 bool 值,表示是否验证 ssl (https) 的有效性
			* 也可以是一个 string,表示包含可信任 CA 证书文件的文件夹路径
			* 如果设为文件夹路径,文件夹必须通过 OpenSSL 提供的 c_rehash 工具处理

		cert
			* 参数是一个 tuple,表示客户端证书的地址
			* [0] = cert,[1] = key
			* 本地证书的私有 key 必须是解密状态,目前，Requests 不支持使用加密的 key(例如 .keystory文件)
		
		proxies
			* 设置代理
		
		auth
			* BasicAuth 验证方式
			* 参数是一个:HTTPBasicAuth 实例
				from requests.auth import HTTPBasicAuth
				auth = HTTPBasicAuth('fake@example.com', 'not_a_real_password')
			
----------------------------
requests	 处理响应		|
----------------------------
	# 获取到响应
		response = request.get(....)
			
	# 属性
		status_code
			* 状态码
			* 该模块内置了状态码的枚举
				requests.codes.ok
				...
		text
			* 服务器响应的字符数据
		encoding 
			* 服务端响应字符串数据的编码
			* 可以先修改该属性,再去获取 text 属性(访问text 都将会使用 encoding 的新值)

		content
			* 返回的二进制数据(响应体)
			* 服务端响应了一张图片
				image = Image.open(BytesIO(response.content))

		json()
			* 以JSON形式编码响应体
		raw
			* 服务器的原始套接字响应
			* 必须确保在初始请求中设置了 stream=True 

		iter_content()
			* 流下载的时候,推荐使用的,它对于raw,帮我处理了很多东西
			* 把文本流保存到文件
				chunk_size = 1024 # 数据块儿的大小,可以自由的根据应用来定义
				with open(filename, 'wb') as fd:
					for chunk in response.iter_content(chunk_size):
						fd.write(chunk)
		iter_lines()
			*  ?

		raise_for_status()
			* 在40x,50x情况下,用于抛出异常,其他情况下该方法返回 None

		headers
			* 获取到响应头,返回的是一个 dict
		
		cookies
			* 获取响应的Cookie,返回的是一个 dict
		
		history
			* 返回的是一个 list,每一个元素都是一个 response 对象,为了完成请求而创建了这些对象
			* 一般'重定向时候'可以通过该属性获取到执行的重定向列表
			* 这个对象列表按照从最老到最近的请求进行排序
		
		url
			* 返回请求的url

		close()
			* 关闭与客户端的连接

----------------------------
multipart/form-data	请求	|
----------------------------
	# 带文件参数的请求
		requestBody = {
			# 普通参数项(值,None)
			'phone':(None,'18545478548'),
			# 文件参数项 (文件名称,二进制文件对象,文件的Content-Type)
			'attachment':  open('C:\\Users\\Administrator\\Desktop\\222.mp4', 'rb')
		}

		# 执行请求
		response = requests.post('http://xxxx/upload', files = requestBody)

		# 服务器响应JSON
		print(response.json())

		* 自己添加header时,不要覆写 'Content-Type' 属性

	# 文件表单项的构建可以更加的复杂,自己定义文件的名称,ContentType,额外的header
		(filename, fileobj, contentype, custom_headers)

		requestBody = {
			'attachment': ('222.mp4',open('C:\\Users\\Administrator\\Desktop\\222.mp4', 'rb'),'audio/mp4', {'Expires': '0'})
		}
	
	# 带多个同名参数(文件)的请求
		requestBody = [
			('name',(None,'KevinBlandy0')),
			('name',(None,'KevinBlandy1')),
			('name',(None,'KevinBlandy2')),
			('files',('file1.jpg',open('C:\\Users\\Administrator\\Desktop\\QQ图片20180703102725.jpg', 'rb'))),
			('files',('file2.jpg',open('C:\\Users\\Administrator\\Desktop\\微信图片_20180716103554.png', 'rb')))
		]

----------------------------
文件下载					|
----------------------------
import requests
# 文件下载地址
url = 'http://xxx.down'
# 本地文件地址
file = 'D:\\魔法师.mp4'
# 最大数据块的大小
chunkSize = 1024
with requests.get(url, stream=True) as response:
    with open(file, 'wb') as file:
        for chunk in response.iter_content(chunkSize):
            file.write(chunk)
