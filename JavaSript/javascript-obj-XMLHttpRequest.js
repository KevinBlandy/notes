---------------------------
XMLHttpRequest				|
---------------------------
	# 创建
		function createXMLHttpRequest(){
				try{
					//大多数浏览器
					return new XMLHttpRequest();
				}catch(e){
					try{
						return new ActiveXObject("Msxml2.XMLHTTP");//IE6.0
					}catch(e){
						try{
							return new ActiveXObject("Microsoft.XMLHTTP");//IE5.5以及更早版本
						}catch(e){
							alert("为了更好的体验,请立即更换浏览器访问本站");
							throw e;
						}
					}
				}
			}
		
	# 实例方法
		open(method,url,async)
			* 打开一个连接,
			* 第一个参数,以字符串的形式指定请求方式
			* 第二个参数,是字符串,URL,
			* 第三个参数boolean,是否为异步(默认true)
		setRequestHeader(header,value)
			* 设置请求头,第一个参数是 请求头的key,第二个参数是请求头的value
		send()
			* 设置请求体,如果是GET请求,则该方法应该设置为null(解决浏览器兼容问题)
			* 参数可以是URL编码后的字符串,也可以是 FormData 对象
		
		getResponseHeader(name)
			* 获取指定名称的响应头

		getAllResponseHeaders()
			* 获取所有的响应头
		

	# 实例属性
		readyState
			* 对象状态码
				> 0状态:刚创建,还没有调用oppen();方法
				> 1状态:请求开始,调用了oppen();方法,但还没调用send方法
				> 2状态:请求发送完成,调用完了send方法
				> 3状态:服务器已经开始响应,但不表示响应结束了
				> 4状态:服务器响应结束(通常我们只关心这个状态...)
		status
			* HTTP响应码
		
		timeout
			* 超时时间,单位(ms)
			* 默认 120 s
		
		responseType	
			* 声明服务端响应的数据类型
			* 可选值
				arraybuffer,
				blog,	

		responseXML
			* 返回服务器响应的XML数据

		responseText
			* 返回服务器响应的TEXT数据

		withCredentials
			* 在跨域请求的时候,是否把Cookie值也发送到跨域服务器
			* 该属性应该在 open() 方法执行之前设置
		
		upload
			* 当提交的表单是文件表单的时候,该属性会存在
			* 该属性可以监听一个上传事件:progress
				if(xhr.upload){
					//监听上传属性的上传事件,每次上传事件都会执行 progressHandlingFunction
					xhr.upload.addEventListener('progress',progressHandlingFunction, false);
					//xhr.upload.onprogress = function(){}			也可以
				}
			* Event属性
					total;		//获取上传文件的总(所有)大小
					loaded;		//获取已经上传的文件大小

					function progressHandlingFunction(event) {
						event.total;		//获取上传文件的总大小
						event.loaded;		//获取已经上传的文件大小
						//获取进度的百分比值
						var percent  = (event.loaded / event.total) * 100;
						//四舍五入保留两位小数
						percent = percent.toFixed(2);
					}
	
		statusText
			* http状态的描述文字
		
		onload
			* 就是readyState = 4 时会执行的回调,它可以代替这种判断
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200){}
			
			* 使用 onload
				let xhr = new XMLHttpRequest();
				xhr.onload = function () {
					// 得到客户端的响应
					let json = JSON.parse(xhr.responseText);
				};
				xhr.open('GET', '/foo', true);
				xhr.send(null);

	# 事件
		onreadyStatechange
			* 当异步对象的状态发生改变的时候调用
			* 当使用 async=false 时，不用编写 onreadystatechange 函数,把代码放到 send() 语句后面即可
				
		
---------------------------
XMLHttpRequest-GET			|
---------------------------
	var xmlHttp = createXMLHttpRequest();
	xmlHttp.open("GET", "/test", true);
	xmlHttp.send(null);//GET请求没有请求体,但是也要给出null,不然FireFox可能无法发送
	xmlHttp.onreadystatechange = function(){
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
			//获取服务端的响应
			var text = xmlHttp.responseText;
		}
	}

---------------------------
XMLHttpRequest-POST			|
---------------------------
	var xmlHttp = createXMLHttpRequest();
	xmlHttp.open("POST", "/test", true);		
	//当请求为POST的时候,需要手动添加请头
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");	
	//请求体
	xmlHttp.send("userName=kevin&passWord=123456");				
	xmlHttp.onreadystatechange = function()	{					
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200){	
			//获取服务端的响应
			var text = xmlHttp.responseText;
		}
	}

---------------------------
XMLHttpRequest-二进制		|
---------------------------
			// 打开加载动画
			const index = layer.load(1, {
  				shade: [0.1,'#fff']
			});
			
			const xhr = new XMLHttpRequest();
			xhr.open('GET', '/download?file=' + encodeURIComponent(file));
			xhr.send(null);
			// 设置服务端的响应类型
			xhr.responseType = "blob";
			// 监听下载
			xhr.addEventListener('progress', event => {
				// 计算出百分比
				const percent  = ((event.loaded / event.total) * 100).toFixed(2);
				console.log(`下载进度：${percent}`);
			}, false);
			xhr.onreadystatechange = event => {
				if(xhr.readyState == 4){
					if (xhr.status == 200){
						
						// 获取ContentType
						const contentType = xhr.getResponseHeader('Content-Type');
						
						// 文件名称
						const fileName = xhr.getResponseHeader('Content-Disposition').split(';')[1].split('=')[1];
						
						// 创建一个a标签用于下载
						const donwLoadLink = document.createElement('a');
						donwLoadLink.download = fileName;
						donwLoadLink.href = URL.createObjectURL(xhr.response);
						
						// 触发下载事件，IO到磁盘
						donwLoadLink.click();
						
						// 释放内存中的资源
						URL.revokeObjectURL(donwLoadLink.href);
						
						// 关闭加载动画
						layer.close(index);
					} else if (response.status == 404){
						alert(`文件：${file} 不存在`);
					} else if (response.status == 500){
						alert('系统异常');
					}
				}
			}
		}