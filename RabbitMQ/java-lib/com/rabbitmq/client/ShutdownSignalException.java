---------------------------------------
ShutdownSignalException
---------------------------------------
	# public class ShutdownSignalException extends RuntimeException implements SensibleClone<ShutdownSignalException>
		* 实现了 RuntimeException

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------
	public ShutdownSignalException(boolean arg0, boolean arg1, Method arg2, Object arg3)
	public ShutdownSignalException(boolean arg0, boolean arg1, Method arg2, Object arg3, String arg4, Throwable arg5)


	public boolean isInitiatedByApplication()
	public volatile SensibleClone sensibleClone()
	public ShutdownSignalException sensibleClone()
	public Object getReference()
	public Method getReason()
	public boolean isHardError()
		* 判断是否有异常