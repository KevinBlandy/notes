----------------------------
SocketChannel				|
----------------------------
	* SocketChannel 是 Socket 的替代类,但是它比 Socket 具有更多的功能
	* 它继承 SelectableChannel 实现了 ByteChannel
	* 没有公共的构造函数,需要通过静态的工厂方法来创建实例对象

----------------------------
SocketChannel 静态方法		|
----------------------------
	static SocketChannel open()
	SocketChannel open(SocketAddress remote)
		
		* open静态方法返回的实例是阻塞模式,需要手动的设置为非阻塞模式

----------------------------
SocketChannel 实例方法		|
----------------------------
	SocketChannel bind(SocketAddress local)
	boolean	connect(SocketAddress remote)
		* 建立远程连接
		* 如果 SocketChannel 处于非阻塞模式,如果立即连接成功,该方法返回 true
		  如果不能立即连接成功,返回 false,程序过会儿必须调用 finishConnection() 方法来完成连接
		* 如果 SocketChannel 处于阻塞模式,如果立即连接成功,该方法返回 true
		  如果不能连接成功,会进入阻塞状态,直到连接成功,或者出现 IO 异常


	boolean	finishConnect()
	SocketAddress getRemoteAddress()
	boolean	isConnected()
		* 底层的 Socket 是否已经建立了远程连接

	boolean	isConnectionPending()
		* 判断是否正在进行远程连接
		* 当远程连接操作已经开始,但是还没完成,返回 true,否则返回 false
		* 也就是说,底层 Socket 还没有开始连接,或者已经连接成功都会返回 false

	SocketChannel open()
	SocketChannel open(SocketAddress remote)

	int	read(ByteBuffer dst)
		* 把 Channel 中的数据读入到 dst Buffer 中
		* 下文 r 字节表示 Buffer 的 remaining() 值
		* 在阻塞模式下,read() 方法会争取读取到 r 个字节,如果流中的数据不足r个字节,就会进入阻塞状态
		  直到读取了r个字节,或者读到了数据流的末尾,或者出现了io异常
		* 在非阻塞模式下,read 方法奉行等读到多少数据,就读多少数据的原则
		  read 方法读取当前通道中的可读数据,有可能不足 r 个字节,或者为0个字节,
		  read 放总是返回,而不会等到读取了 r 个字节后再返回
		* read 方法返回实际上可读入的字节数,有可能为0,如果返回 -1 就表示读到了流末尾

	long read(ByteBuffer[] dsts)
	long read(ByteBuffer[] dsts, int offset, int length)

	SocketChannel setOption(SocketOption<T> name, T value)
	SocketChannel shutdownInput()
	SocketChannel shutdownOutput()
	Socket socket()
		* 返回 SocketChannel 关联的 Socket 对象

	int	validOps()
		* 返回 SocketChannel 支持的事件
		* 返回 SelectionKey.OP_CONNECT | SelectionKey.OP_READ |	SelectionKey.OP_WRITE

	int	write(ByteBuffer src)
		* 把 src 中的数据写入到 Channel
		* 在阻塞模式下,write 方法会争取输出r字节,如果底层网络缓冲区不能容纳r个字节,就会处于阻塞状态
		  直到输出了r个自己,或者出现了io异常
		* 在非阻塞模式下,write() 方法奉行能够输出多少数据,就输出多少数据的原则
		  有可能不足r个字节,或者为0个字节,write 方法总是立即返回,而不会等到输出r个字节后再返回
		* wirte 方法实际上输出的字节数,可能为0

	long write(ByteBuffer[] srcs)
	long write(ByteBuffer[] srcs, int offset, int length)

	Object blockingLock()
	SelectableChannel configureBlocking(boolean block)
		* 设置IO模式,默认为 true,表示阻塞模式

	boolean	isBlocking()
	boolean	isRegistered()
		* 是否已经注册

	SelectionKey keyFor(Selector sel)
		* 从指定的Selector获取到key

	SelectorProvider provider()
	SelectionKey register(Selector sel, int ops, Object att)
	SelectionKey register(Selector sel,int ops)
	void close()
	boolean isOpen()
	SocketAddress getLocalAddress()
	<T> T getOption(SocketOption<T> name)
	Set<SocketOption<?>> supportedOptions()








