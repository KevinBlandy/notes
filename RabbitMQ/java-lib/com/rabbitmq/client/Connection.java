---------------------------------------
Connection
---------------------------------------
	# interface Connection extends ShutdownNotifier, Closeable

---------------------------------------
static
---------------------------------------


---------------------------------------
this
---------------------------------------
	public abstract InetAddress getAddress()
	public abstract String getId()
	public abstract void close(int arg0, String arg1)
	public abstract void close()
	public abstract void close(int arg0)
	public abstract void close(int arg0, String arg1, int arg2)
	public abstract int getPort()
	public abstract void abort(int arg0, String arg1)
	public abstract void abort(int arg0)
	public abstract void abort(int arg0, String arg1, int arg2)
	public abstract void abort()
	public abstract Map getClientProperties()
	public abstract String getClientProvidedName()
	public abstract void addBlockedListener(BlockedListener arg0)
	public abstract BlockedListener addBlockedListener(BlockedCallback arg0, UnblockedCallback arg1)
	public abstract ExceptionHandler getExceptionHandler()
	public abstract Map getServerProperties()
	public abstract boolean removeBlockedListener(BlockedListener arg0)
	public abstract void clearBlockedListeners()
	public abstract int getHeartbeat()
	public abstract Channel createChannel()
	public abstract Channel createChannel(int arg0)
	public abstract int getFrameMax()
	public Optional openChannel(int arg0)
	public Optional openChannel()
	public abstract void setId(String arg0)
	public abstract int getChannelMax()
