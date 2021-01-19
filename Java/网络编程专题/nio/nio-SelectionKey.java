----------------------------
SelectionKey				|
----------------------------
	* ServerSocketChannel 或者 SocketChannel 通过 register() 方法向 Selector 注册事件的时候
	  register() 方法会创建一个 SelectionKey 对象,该对象是用来跟踪注册事件的句柄
	
	* 在 SelectionKey 有效期, Selector 会一直监控与它相关的事件
	  如果事件发生,就会把 SelectionKey 对象加入到: selected-keys 集合中
	
	* 在以下情况中,SelectionKey 对象会失效,也就说说 Selector 再也不会监控与它相关的事件了
		1,程序调用了 SelectionKey 的 cancel() 方法
		2,关闭与 SelectionKey 关联的 Channel
		3,与 SelectionKey 关联的 Selector 被关闭

----------------------------
SelectionKey 事件常量		|
----------------------------
	SelectionKey.OP_ACCEPT;
		* 标记连接就绪,表示至少有一个客户端连接,服务器可以接收这个连接
		* 16

	SelectionKey.OP_CONNECT;
		* 连接就绪事件,表示客户与服务器的连接已经建立成功了
		* 8

	SelectionKey.OP_READ;
		* 读就绪事件,表示输入流中已经有可读数据,可以执行读操作了
		* 1

	SelectionKey.OP_WRITE;
		* 写就绪事件,表示已经可以向输出流写数据了
		* 4

----------------------------
SelectionKey-api			|
----------------------------
	void cancel()
		* 使 SelectionKey 对象失效
		* 该方法把 SelectionKey 对象加入到与它关联的  Selector 对象的 cancelled=keys 集合中
		  程序下一次执行 Selector 的 select() 方法时,该方法会把 SelectionKey 从  Selector 对象的 all-keys,selected-keys.cancelled-keys 这仨集合中删除

	Object attach(Object ob)
		* 使 SelectionKey 关联一个附件对象

	Object attachment()
		* 返回  SelectionKey 关联的对象
		
	SelectableChannel channel()
		* 返回与之关联的 SelectableChannel 对象
		
	int interestOps()
		* 返回所有感兴趣的事件

	SelectionKey interestOps(int ops)
		* 添加一个感兴趣的事件

	boolean isAcceptable()
		* 接收连接事件是否就绪
	boolean isConnectable()
		* 连接就绪事件是否已经发生
	boolean isReadable()
		* 读事件
	boolean isWritable()
		* 写事件
		
	boolean isValid()
		* 判断当前 SelectionKey 是否有效
		* 当 SelectionKey 创建后,它就一直处于有效状态
		* 如果调用了 cancel() ,或者关闭了关联的 Channel 或者  Selector 关闭的时候,它就失效

	
	int readyOps()
		* 返回所有已经发生的事件
		* 假如返回值:SelectionKey.OP_WRITE | SelectionKey.OP_READ
		  意味着,读就绪和写就绪事件发生了,与之关联的 SocketChannel 对象可以进行读操作和写操作

	Selector selector()
		* 返回关联的 Selector 对象



