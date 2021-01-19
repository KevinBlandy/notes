------------------------
Selector				|
------------------------
	* 只要 ServerSocketChannel 或者 SocketChannel 向 Selector 注册了特定的事件
	  Selector 就会监控这些事件是否发生,SelectableChannel 的 register() 方法负责注册事件
	  该方法返回一个 SelectionKey 对象,该对象是用于跟踪这些被注册事件的句柄
	
	* 一个 Selector 会包含3种类型的 SelectionKey 集合
		all-keys
			* 当前所有向 Selector 注册 SelectionKey 集合
			* Selector 的 keys() 方法返回该集合
		
		slected-keys
			* 相关事件已经被 Selector 捕获,的 SelectionKey 集合
			* Selector 的 selectedKeys() 方法返回该集合
		
		cancelled-keys
			* 已经被取消的 SelectionKey 的集合
			* Selector 没有提供方法这种集合的方法
	
	* 当 SelectableChannel 执行 register() 方法时,该方法会新建一个 SelectionKey ,并把它加入到 Selector 的 all-keys 集合中
	
	* 如果关闭了 SelectionKey 对象关联的 Channel 对象,或者调用了 SelectionKey 对象的 cancel() 方法
	  这个 SelectionKey 对象就会被加入到 cancelled-key 集合中,表示这个 SelectionKey 已经被取消
	  程序执行下一次 Selector 的 select() 方法时,被取消的 SelectionKey 对象将从所有集合中删除
	
	* 在 Selector 执行 select() 方法的时候,如果与 SelectionKey 相关的事件发生了,这个 SelectionKey 就会被加入到:selected-keys 集合中
	  程序直接调用 selected-keys 集合的 remove() 方法,或者调用它的 Iterator 的remove() 方法都可以从 selected-keys 集合中删除一个 SelectionKey 对象
	  程序不允许,直接通过集合集合的 remove() 方法,删除 all-keys 集合中的 SelectionKey,如果程序视图这么做,会抛出异常:UnsupportedOperationException

------------------------
Selector-api			|
------------------------
	public static Selector open()

	void close()
	boolean isOpen()
		* 判断 Selector 是否处于打开状态
		* 创建了 Selector 实例后,就是打开状态,调用了 close() 方法,就进入了关闭状态

	Set<SelectionKey> keys()　
		* 返回 Selector 的 all-keys 集合,包含了 Selector 关联的 SelectionKey 丢下

	SelectorProvider provider()
	int select()
	int select(long timeout)
		* select() 方法才用阻塞的工作方式,返回相关事件已经发生的 SelectionKey 对象数目
		* 如果一个都没有,则会进入阻塞,直到出现以下情况之一,才用 select() 方法中返回
			1,至少有一个 SelectionKey 的相关事件已经发生
			2,其他线程调用了 Selector 的 wakeup() 方法,导致 select() 方法的线程,立即从 select() 方法返回
			3,当前执行 select()方法的线程,被其他线程中断
			4,超出了等待时间,单位是毫秒,如果等待超时,就正常返回,不会抛出异常
			  如果调用了是没有超时参数的select(),该方法的线程就会进入阻塞状态,永远不会因为超时而中断

	Set<SelectionKey> selectedKeys()
	int selectNow()
		* 返回相关事件已经发送的  SelectionKey 对象数目
		* 该方法采用非阻塞的工作方式,返回当前相关事件已经发生的 SelectionKey 对象数目
		* 如果没有,立即返回0
	

	Selector wakeup()
		* 唤醒执行 Selector 的 select() 方法(同样适用于 select(long timeOut))的线程
		* 线程A执行了 Selector 对象的 wakeup() 方法,如果线程B正在执行同一个 Selector 对象的 select() 方法时
		  或者B线程过一会儿执行这个 Selector 对象的 select() 方法,那么B线程在执行select() 方法时,会立即从 select() 方法中返回
		* wakeup() 只能唤醒执行select()方法的线程B一次,如果线程B在执行select()方法时被唤醒,以后再执行select()还会被阻塞
		  除非线程A再次执行 Selector 对象的 wakeup() 方法
		 



	