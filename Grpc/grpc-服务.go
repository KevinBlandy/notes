----------------------------
GRPC 服务
----------------------------

----------------------------
GRPC 服务类型
----------------------------
	# 一元服务
		rpc SayHello(HelloRequest) returns (HelloResponse);

		* 一个请求，一个响应
	

	# 服务器流
		rpc LotsOfReplies(HelloRequest) returns (stream HelloResponse);
		
		* 客户端从服务器流中读取数据，直到数据读取完毕

	# 客户端流
		rpc LotsOfGreetings(stream HelloRequest) returns (HelloResponse);
		
		* 服务器从客户端流中读取数据，直到读取完毕然后响应。

	# 双向流
	
		rpc BidiHello(stream HelloRequest) returns (stream HelloResponse);
	
			* 双全工通信，双方都是流式
	
----------------------------
元数据
----------------------------
	# 元信息
		* 元数据是关于特定 RPC 调用的信息（例如身份验证详细信息）
		* 采用键值对列表的形式，其中 Key 是字符串，值通常是字符串，但也可以是二进制数据。
		
		* 键值不区分大小写，由 ASCII 字母、数字和特殊字符 -、_、.组成，并且不能以 grpc- 开头（这是 gRPC 本身的保留字）。
		* 二进制值 Key 以 -bin 结尾，而 ASCII 值 Key 不以 -bin 结尾。

