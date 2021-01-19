---------------------------
UDP发送						|
---------------------------
	/*
		发送udp
	*/
	try(DatagramSocket datagramSocket = new DatagramSocket()){
		byte[] data = "Hello".getBytes();
		DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length, new InetSocketAddress("127.0.0.1", 10086));
		datagramSocket.send(datagramPacket);
	}

	/*
		从指定端口发送数据,哪怕该端口已经被监听(端口重用)
	*/
	try(DatagramSocket datagramSocket = new DatagramSocket(null)){
		datagramSocket.setReuseAddress(true);						//允许端口重用
		datagramSocket.bind(new InetSocketAddress(1024));			//绑定要从本地的哪个端口发送
		byte[] data = "Hello".getBytes();
		// 设置数据,目的端口,目的ip
		DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length, new InetSocketAddress("127.0.0.1", 10086));
		datagramSocket.send(datagramPacket);
	}

---------------------------
UDP接收						|
---------------------------
	//创建udpsocket服务.建立端点,监听端口
	try(DatagramSocket datagramSocket = new DatagramSocket(10000)){
		while(true){
			//定义数据包，用于存储数据
			byte[] buffer = new byte[1024];//缓冲区
			DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
			//通过socket服务receive方法将收到的数据存入数据包中(阻塞方法)
			datagramSocket.receive(datagramPacket);
			//通过数据包的方法获取其中的数据
			String ip = datagramPacket.getAddress().getHostAddress();						//获取IP。
			String data = new String(datagramPacket.getData(),0,datagramPacket.getLength());//获取数据。
			int port = datagramPacket.getPort();											//获取端口。
		}
	}