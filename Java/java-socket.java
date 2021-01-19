---------------------------
Netty-UDP发送				|
---------------------------

	//创建udp服务,通过 DatagramSocet对象,绑定本机端口
	DatagramSocket ds = new DatagramSocket(8888);
	//确定数据，并封装成数据包
	byte[] buf = "KevinBlandy".getBytes();//String.getBytes();把字符串转换成字节数组。
	//构建数据包,指定数据,长度,地址,端口
	DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.132.245"),10000);
	//通过socket服务将已有的数据包通过 send方法。发送出去。
	ds.send(dp);//阻塞式方法。如果没数据就会一直等(线程机制)。
	//关闭资源
	ds.close();

	/*
		从指定端口发送数据,哪怕该端口已经被监听(端口重用)
	*/
	DatagramSocket socket = new DatagramSocket(null);	//该值应该设置为null
	socket.setReuseAddress(true);						//该值应该为true
	socket.bind(new InetSocketAddress(1024));			//绑定要从本地的哪个端口发送
	byte[] bytes = hexStringToBytes(message);
	DatagramPacket p = new DatagramPacket(bytes, 0, bytes.length, new InetSocketAddress(ip, Integer.parseInt(port)));
	socket.send(p);

---------------------------
Netty-UDP接收				|
---------------------------
	//创建udpsocket服务.建立端点,监听端口
	DatagramSocket ds = new DatagramSocket(10000);
	while(true){
		//定义数据包，用于存储数据
		byte[] buf = new byte[1024];//缓冲区
		DatagramPacket dp = new DatagramPacket(buf,buf.length);
		//通过socket服务receive方法将收到的数据存入数据包中(阻塞方法)
		ds.receive(dp);
		//通过数据包的方法获取其中的数据
		String ip = dp.getAddress().getHostAddress();			//获取IP。
		String data = new String(dp.getData(),0,dp.getLength());//获取数据。
		int port = dp.getPort();								//获取端口。
		System.out.println(ip+"::"+data+"::"+port);
		//ds.close();		关闭资源
	}


---------------------------
Netty-TCP发送				|
---------------------------
	Socket s = new Socket("120.76.182.243",8080);
	OutputStream os = s.getOutputStream();		//此输出流是 OutputStream();是抽象的。
	InputStream is = s.getInputStream();		//此输入流是 InputStream();是抽象的。

---------------------------
Netty-TCP接收				|
---------------------------
	ServerSocket ss = new ServerSocket(8888);
	while(true){
		Socket s = ss.accept();						//线程阻塞
		InputStream is = s.getInputStream();		//获取 InputStream
		OutputStream os = s.getOutputStream();		//获取 OutputStream
	}
	