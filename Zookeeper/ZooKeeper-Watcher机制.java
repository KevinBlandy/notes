
--------------------------------
Watcher 机制					|
--------------------------------
	# ZK提供了一种订阅发布的功能 Watcher
		* 一对多,多个订阅监听一个主题,当主题有变化,会通知到所有的订阅者
	
	# ZK的事件
		节点的创建
		节点的修改
		节点的删除
		子节点变化
	
	# Watcher机制的过程
		1,客户端向服务端注册Watcher
		2,服务端事件发生触发Watcher
		3,客户端回调Watcher得到触发事件情况
	
	# Wather机制特点
		* 一次性触发
			* 事件发生 触发监听,一个 watcher event 就会被送到设置监听的客户端
			* 这种效果是一次性的,后续再次发生相同的事件,不会再次触发
		
		* 事件封装
			* ZK使用WatcherEvent对象来封装服务端事件并传递
			* WatcheEvent 包含了每一个事件的三个基本属性
				KeeperState 通知状态
					@Deprecated
					Unknown (-1),
					Disconnected (0),
					@Deprecated
					NoSyncConnected (1),
					SyncConnected (3),
					AuthFailed (4),
					ConnectedReadOnly (5),
					SaslAuthenticated(6),
					Expired (-112);

				EventType 事件类型
					None (-1),
					NodeCreated (1),
					NodeDeleted (2),
					NodeDataChanged (3),
					NodeChildrenChanged (4);

				Path 节点路径
					
			
		* event异步发送
			* Watcher通知事件从服务端发送到客户端是异步的
		
		* 先注册再触发
			* 客户端必须先在服务端注册了监听,才能收到服务端的事件触发通知
		

	# 通知状态和事件类型
		SyncConnected
			|-None
				*  客户端与服务端成功建立连接
			|-NodeCreated
				* 监听的节点被创建
			|-NodeDeleted
				* 监听的节点被删除
			|-NodeDataChanged
				* 监听节点的数据发生修改
			|-NodeChildrenChanged
				* 监听节点的子节点发生改变
			
		Disconnected
			|-None
				* 客户端与Zookeeper服务端断开连接
		Expired
			|-None
				* 会话超时
				* 通常也会收到 SessionExpiredException 异常
		AuthFailed
			|-None
				* 一般有两种情况
					1,使用错误的 schema 进行权限校验
					2,SASL 权限检查失败
				* 同时也会收到 AuthFailedException
		
		* 连接状态事件(EventType=None,Path=null),不需要客户端主动注册
		* 客户端只要有需要,直接处理即可



