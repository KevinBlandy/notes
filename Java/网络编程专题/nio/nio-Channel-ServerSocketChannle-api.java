------------------------
ServerSocketChannel		|
------------------------
	# ServerSocketChannel 继承 SelectableChannel ,所以它也具备设置阻塞模式,注册事件的方法
	# 它是 ServerSocket 的代替类,所以它也有 accept() 方法

	# 它不具备 public 的构造函数,只能通过静态工厂方法创建
		ServerSocketChannel.open();

	# 每个 ServerSocketChannel 对象都和一个 ServerSocket 关联,可以通过api获取
		ServerSocket socket();

	# 创建 ServerSocketChannel 实例对象
		


------------------------
ServerSocketChannel-api	|
------------------------

	public static ServerSocketChannel open()

	ServerSocket socket();
		* 返回关联的 ServerSocket 对象,每个 ServerSocketChannel 都关联一个 ServerSocket

	<T> ServerSocketChannel setOption(SocketOption<T> name, T value)
	SocketChannel accept()
	SelectionKey register(Selector sel, int ops)
	SelectionKey register(Selector sel, int ops,Object att)
	ServerSocketChannel bind(SocketAddress local)
	ServerSocketChannel bind(SocketAddress local, int backlog)
	SocketAddress getLocalAddress() 
	int validOps()
		* 返回 ServerSocketChannel 能产生的事件
		* 固定返回:SelectionKey.OP_ACCEPT;(连接就绪事件)

	Object blockingLock()
	void close()
	<T> T getOption(SocketOption<T> name)
	boolean isBlocking()
	boolean isOpen()
	boolean isRegistered()
	SelectionKey keyFor(Selector sel)
	Set<SocketOption<?>> supportedOptions()
	SelectorProvider provider()
	SelectableChannel configureBlocking(boolean block)





