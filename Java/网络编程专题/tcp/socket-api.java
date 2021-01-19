-----------------------------
¹¹Ôìº¯Êý					 |
-----------------------------
	public Socket()
	Socket(Proxy proxy) 
	Socket(String host, int port)throws UnknownHostException, IOException
	Socket(String host, int port, InetAddress localAddr,int localPort) throws IOException
	Socket(InetAddress address, int port) throws IOException
	Socket(InetAddress address, int port, InetAddress localAddr,int localPort) throws IOException
	Socket(Proxy proxy)

-----------------------------
api							 |
-----------------------------
	void bind(SocketAddress bindpoint)
	void close()
	void connect(SocketAddress endpoint)
	void connect(SocketAddress endpoint, int timeout)

	SocketChannel getChannel()
	InetAddress getInetAddress()
	InputStream getInputStream()
	boolean getKeepAlive()
	InetAddress getLocalAddress()
	int getLocalPort()
	SocketAddress getLocalSocketAddress()
	boolean getOOBInline() 
	OutputStream getOutputStream()
	int getPort()
	int getReceiveBufferSize()
	SocketAddress getRemoteSocketAddress()
	boolean getReuseAddress()
	int getSendBufferSize()
	int getSoLinger()
	int getSoTimeout()
	boolean getTcpNoDelay()
	int getTrafficClass()

	boolean isBound()
	boolean isClosed()
	boolean isConnected()
	boolean isInputShutdown()
	boolean isOutputShutdown() 

	void sendUrgentData (int data)
	void setKeepAlive(boolean on)
	void setOOBInline(boolean on)
	void setPerformancePreferences(int connectionTime,int latency,int bandwidth)
	void setReceiveBufferSize(int size)
	void setReuseAddress(boolean on) throws SocketException
	void setSendBufferSize(int size)
	void setSoLinger(boolean on, int linger)
	void setSoTimeout(int timeout)
	void setTcpNoDelay(boolean on)
	void setTrafficClass(int tc)
	void shutdownInput()
	void shutdownOutput()





