---------------------------------------
ShutdownNotifier
---------------------------------------
	# public interface ShutdownNotifier

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------


	public abstract boolean isOpen()
		* 判断链接是否是打开状态
		* 不建议使用这个方法，实现类都是加锁的，性能很低

	public abstract void removeShutdownListener(ShutdownListener listener)
	public abstract void addShutdownListener(ShutdownListener listener)
		* 添加/删除关闭监听
		* 如果 链接 已经是关闭状态，则会被立即调用

	public abstract void notifyListeners()
		* 主动触发监听器

	public abstract ShutdownSignalException getCloseReason()
		* 获取连接关闭的原因