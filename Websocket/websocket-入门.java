--------------------------------
WebSocket						|
--------------------------------
	# HTML5的新东西,属于JS编程的范畴
	# WebSocket并不仅仅用于浏览器和服务器通信,也可以用于两个程序
	# 官方提供的 websocket 接口 规范
		<dependency>
			<groupId>javax.websocket</groupId>
			<artifactId>javax.websocket-api</artifactId>
			<version>1.1</version>
		</dependency>

	# 注意, Tomcat8 系列已经支持WebSocket,并且自己的lib目录下已经存在socket-api.jar,实际发布的时候,该依赖应该是provided

	# 检测浏览器是否支持WebSocket
		function loadDemo() {  
			if (window.WebSocket) {  
				//支持  
			} else {  
				//不支持
			}  
		}  
	

--------------------------------
WebSocket 请求头/响应头			|
--------------------------------
	# 公共的
		Upgrade:WebSocket
			* 表示这是一个特殊的 HTTP 请求，请求的目的就是要将客户端和服务器端的通讯协议从 HTTP 协议升级到 WebSocket 协议。

		Connection: Upgrade
			* 依然是固定的，告诉客户端即将升级的是 Websocket 协议，而不是mozillasocket，lurnarsocket或者shitsocket。

		Sec-WebSocket-Protocol
			*  子协议。
		
		Sec-WebSocket-Extensions
			& 扩展
	
	# 客户端
		Sec-WebSocket-Key
			* 是一个 Base64 encode 的值，这个是浏览器随机生成的
			* 告诉服务器：泥煤，不要忽悠窝，我要验证尼是不是真的是Websocket助理。

		Sec-WebSocket-Version
			* 是告诉服务器所使用的 Websocket Draft（协议版本）
			* 在最初的时候，Websocket协议还在 Draft 阶段，各种奇奇怪怪的协议都有，而且还有很多期奇奇怪怪不同的东西，什么Firefox和Chrome用的不是一个版本之类的，当初Websocket协议太多可是一个大难题。。不过现在还好，已经定下来啦~大家都使用的一个东西
		Origin:htt://www.kevinBlandy.com/socket								*/
			* 很显然,目标socket地址
	
	# 服务端
		WebSocket-Accept
			* 这个则是经过服务器确认，并且加密过后的 Sec-WebSocket-Key 。 


--------------------------------
WebSocket - 协议				|
--------------------------------
	# 主要类型的帧
		1,控制帧
			* 关闭帧
			* ping帧(最大125字节)
			* pong帧(最大125字节)

		2,数据帧
			* 文本形
			* 二进制形
		2,部分帧
	

		
