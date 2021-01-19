--------------------------------
ChannelFutureListener			|
--------------------------------
	# interface ChannelFutureListener extends GenericFutureListener<ChannelFuture>
	# 回调事件监听器
	# 预定义了N多的事件回调的快捷实现
		CLOSE
			* 完成后关闭连接
		CLOSE_ON_FAILURE
			* 如果抛出了异常,关闭连接
		FIRE_EXCEPTION_ON_FAILURE

--------------------------------
方法							|
--------------------------------
	public void operationComplete(ChannelFuture future) 
		* 覆写完成操作的事件