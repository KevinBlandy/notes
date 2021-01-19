----------------------------
socket选项					|
----------------------------
	SocketOptions 常量
		TCP_NODELAY
			* 是否立即发送数据
		SO_RESUSEADDR
			* 是否允许重用socket所绑定的本地地址
		SO_TIMEOUT
			* 表示接收数据时的,等待超时时间
		SO_LINGER
			* 表示当执行Socket的close()方法时,是否立即关闭底层的socket
		SO_RCVBUF
			* 表示接收数据的缓冲区大小
		SO_SNDBUF
			* 表示发送数据的缓冲区大小
		SO_KEEPALIVE
			* 表示对于长时间处于空闲状态的Socket是否要自动把它关闭
		OOBINLINE
			* 表示是否支持发送一个字节的TCP紧急数据

	StandardSocketOptions 常量
		* 对于 nio 的定义
		

----------------------------
socket选项-详解				|
----------------------------
	SO_RESUSEADDR
		* 默认值为 false,表示不允许地址重用
		* socket api
			 void setReuseAddress(boolean on)
			 boolean getReuseAddress()
		
		* 当 Socket 执行 close()关闭的时候,如果网络上还有发送到这个Socket的数据,那么底层的Socket不会立即释放本地端口,而是会等待一段时间
		  确保收到了网络上发过来的延迟数据,然后再释放端口,Socket 收到这些延迟数据后,不会做任何处理,目的是:确保这些数据不会被其他碰巧绑定到同样端口的新进程接收到
		* 为了确保一个进程关闭socket后,即使它还没释放端口,同一个主机上的其他进程还可以即刻重用该端口,可以调用api设置 true
		* 需要注意的是,setReuseAddress(true),必须在Socket还没有绑定到一个本地端口之前调用,否则无效
		* 公用同一个端口的进程都必须调用:setReuseAddress(true)
	
	SO_TIMEOUT
		* 读取超时时间,单位是毫秒
		* socket api
			void setSoTimeout(int timeout) 
			int getSoTimeout()

		* 在 InputStream 执行 read() 数据的时候,如果没有数据就会等待
		* 如果超过 SO_TIMEOUT 时间还没有数据,就抛出异常
		* 该api必须在读取数据之前设置才有效,当抛出了:SocketTimeoutException,连接并没有断开,可以尝试再次的读取数据
		* 包括 accept() Api,超过该时间限制没有新的连接时也会异常
	
	SO_RCVBUF
		* 控制输入数据的缓冲区大小
		* socket api
			 void setReceiveBufferSize(int size)
			 int getReceiveBufferSize()
		* 如果传输大的连续的数据块(基于http或者ftp协议的通信)可以使用较大的缓冲区,这可以减少传输数据的次数,提高传输效率
		* 如果是交互频繁,且单次传输数据量比较小的通信方式(Telnet和网络游戏),则应该才用小的缓冲区,确保小批量的数据能够及时发送给对方
		* 这种设置缓冲区大小的原则,也同样适用于Socket的SO_SNDBUF选项
		* 如果底层不支持SO_RCVBUF/SO_SNDBUF选项,那么会抛出异常

	TCP_NODELAY
		* 默认值为 false,表示采用用Negale算法
		* socket api
			void setTcpNoDelay(boolean on) 
			boolean getTcpNoDelay()
		
		* 默认情况下,发送数据采用Negale算法,该算法指的是:发送方发送的数据 不会立即发出,而是先放在缓冲区内
		  缓冲区满了再发出,发送完一批数据后,会等待接收方对这批数据回应,然后再发送下一批数据
		* 这种算法通过减少传输数据的次数来提高通信效率,适用于:发送方需要发送大批量的数据,并且接收方会及时做出回应的场合
		* 如果发送方持续地发送小批量数据,并且接收方不一定会立即响应数据,那么Negale算法会使发送方运行慢(游戏,鼠标的移动都会发送数据到服务器,就不要才用Negale算法)
		* 如果socket底层不支持 TCP_NODELAY 会抛出 SocketException
	
	
	SO_LINGER
		* 控制socket的关闭行为
		* socket api
			void setSoLinger(boolean on, int linger)
			void setSoLinger(boolean on, int linger)
		* 默认情况下,socket执行close()时,该方法会立即 return,但底层socket实际上并不会立即关闭
		  它会延迟一段时间,直到发送完毕所有剩余数据,才会真正关闭socket,断开连接
		* 如果执行以下方法

			//注意,超时的单位是秒,而不是毫秒
			socket.setSoLinger(true, 3600);

			此时socket执行close(),该方法不会立即返回,而是进入阻塞状态
			同时底层的socket会尝试发送剩余数据,只有满足两个条件之一,close()方法才会 return
				1,底层socket已经发送完毕所有的数据
				2,尽管底层socket并没有发送完毕剩余的数据,但是超时,剩余未发送的数据会被丢弃
		
	SO_SNDBUF
		* 控制输出数据的缓冲区的大小
		* socket api
			void setSendBufferSize(int size);
			int getSendBufferSize();
		* 同上
	
	SO_KEEPALIVE
		* 是否保持连接,默认为false
		* socket api
			 void setKeepAlive(boolean on)
			 boolean getKeepAlive()
		* 如果该属性为true,则底层的tcp会监视该连接是否有效,如果连接处于空闲状态(两端都没有传递数据)超过了2小时
		  本地的tcp实现会发送一个数据包给远程的socket,如果远程的tcp没有回应,tcp实现就会持续尝试11分钟,直到收到响应为止
		  如果在12分钟内未收到响应,tcp实现就会自动关闭本地socket,断开连接
		* 在不同的网络平台上,tcp实现尝试与远程socket对话的时限会有所差别
		* 默认值为:false,表示tcp不会监视连接是否有效,不活动的客户端可能会永久存活小区,而不会注意到服务器已经崩溃

	OOBINLINE
		* 表示是否支持发送一个字节的紧急数据
		* socket api
			void setOOBInline(boolean on) 
			boolean getOOBInline()
		* 当该值为 true 的时候,表示支持发送一个字节的 tcp 紧急数据,
		  通过 Socket 类的 sendUrgentData (int data) 来发送一个字节的紧急数据
		* 该值默认为 false,在这种情况下,接收方收到紧急数据时不做任何处理,直接丢弃
		* 如果该值为 true,接收方会把收到的紧急数据与普通数据放在同样的队列中
		  值得注意的是,除非使用一些更高层的协议,否则接收方处理紧急数据的能力非常有限
		  当紧急数据到来时,接收方不会收到任何通知,因此很难区分普通数据与紧急数据,只好按照相同的方式去处理它们
		
----------------------------
socket服务类型选项			|
----------------------------
	* internet上传输数据分为不同的服务类型,它们有不同的'定价'
	* IP规定了4种服务类型,用来定型的描述服务的质量
		1,低成本	0x02(二进制倒数第2位=1)	发送成本低
		2,高可靠	0x04(二进制倒数第3位=1)	保证把数据可靠的发送到目的地
		3,高吞吐	0x08(二进制倒数第4位=1)	一次可以接收/发送大批量的数据
		4,最小延迟	0x10(二进制倒数第5位=1)	传输数据最快,把数据快速的送到目的地
	* 以上四种服务类型还可以进行组合,以获取不同的效果
	* 他们在 SocketOptions 都以常量的形式有定义
	* socket api
		void setTrafficClass(int tc)
		int getTrafficClass()
	* demo
		socket.setTrafficClass(0x02 | 0x10);	//低成本 + 最小延迟 模式
	
	
----------------------------
socket-连接时间/延迟/带宽	|
----------------------------
	* 设置连接时间,延迟,带宽之间的相对重要性
	* socket api
		void setPerformancePreferences(int connectionTime,int latency,int bandwidth)
	* 参数详解
		connectionTime	表示用最少的时间建立连接
		latency			表示最小延迟
		bandwidth		表示最高带宽
	* 该api用来设置3项指标之间相对重要性,可以为这些参数赋予任意整数
	  这些整数之间的相对大小就决定了相应参数的相对重要性
	  例:参数 *2,1,3就表示带宽最重要,其次是最少连接时间,最后是最小延迟
	 

	



		