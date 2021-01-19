UDP传输，构建步骤
UDP传输对象		DatagramSocket
封装数据对象	DatagramPacket	//一般用于UDP连接

TCP客户端对象	Socket
TCP服务器对象	ServerSocket
操作IP对象		InetAddress
-------------------------发送端  //数据每次最大是 64kb
DatagramPacket dp = new DatagramPacket(字节数组,长度,IP,端口); 
	|--打包数据准备发送
 *通过UDP传输方式。将一段文字数据发送出去。
 *1，先建立udpsocket服务
 *2，提供数据并将数据封装到数据包中
 *3，通过 socket 服务的发送功能，将数据发送出去
 *4，关闭资源
-------------------------接收端
DatagramPacket dp = new DatagramPacket(字节数组,长度);
	|--接收数据，准备拆包
 * 	      定义接收端的时候，通常都会监听一个端口。其实就是给这个接收网络应用程序定义数字标示。
 * 	      方便于明确哪些数据过来该应用程序处理。
 *
 * 1，定义udpsocket服务
 * 2，定义一个数据包，因为要存储接收到的字节数据。因为数据包对象中有更多功能可以提取字节数据中的不同数据信息。
 * 3，通过socket服务的receive方法将收到的数据存入已定义好的数据包中，
 * 4，通过数据包对象的特有功能，将这些不同的数据取出，打印在控制台上。
 * 5，关闭资源。
DatagramPacket dp = new DatagramPacket();//事实上并没有空构造函数
dp.getAddres();//返回此数据的IP地址
dp.getData();//返回数据缓冲区。也就是里面的数据 byte[]
dp.getPort();//获取数据发送的端口号
dp.getLength();//获取该数据的长度。
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
TCP传输，构建步骤
tcp是面向连接的。在该对象建立的时。就要去连接指定主机。
因为tcp是面向连接的。所以在建立 Socket 服务时。就要有服务端存在并连接成功。形成通路后，在该通道传输数据。
1,tcp分客户端和服务端
2,客户端对应的对象是 Socket
  服务端对应的对象是 ServerSocket

 
-------------------------客户端		Socket
 
客户端：
1，建立 socket 服务。指定要连接主机端口。
	|--Socket s = new Socket(IP,端口);
2，获取 socket 中的输出流，将数据写到该流中。通过网络发送给服务端。
	|--OutputStream os = s.getOutputStream();	//此输出流是 OutputStream();是抽象的。
3，获取 socket 流中的输入流，讲服务端反馈的数据获取到，并打印。
	|--InputStream is = s.getInputStream();		//此输入流是 InputStream();是抽象的。
4，关闭客户端资源。
	s.close();

-------------------------服务端		ServerSocket
1,创建服务，并监听一个端口
	|-ServerSocket ss = new ServerSocket(端口);
2,获取一个连接过来的客户端对象
	|--Socket s = ss.accept();//阻塞式方法，没有连接就会一只等待。
3,客户端如果有发送数据，那么服务端要使用对应的客户端对象。并获取该客户端对象的读取流，来读取发过来的数据
	|--InputStream is = s.getInputStream();
4,如果服务端需要向客户端返回数据，那么同理也要需要获取到客户的输出流，来向客户端输出数据	
	|--OutputStream os = s.getOutputStream();
4,关闭服务端(可选的，不一定要关闭。一般服务器都是7*24小时开启)

-------------------------------
URL/URI(表示范围比较大)
URL url = new URL("url地址");
url.getProtocol();
	|--获取url的协议名称。返回 String 类型。
url.getHost();
	|--获取url的主机名(域名)。返回 String 类型。
url.getPath();
	|--获取url的路径。返回 String 类型。
url.getPort()
	|--返回此URL的端口号。 int 类型。如果未定义端口号就返回 -1.
url.getFile()
	|--获取url的文件名。返回的文件部分(不包含参数)。 String 类型。如果没有文件名。则返回控字符串！！！不是 null.
url.getQuery()
	|--获取url的查询部分。如果没有，则返回 null.获取的是路径和参数。
url.openConnection();
	|--连接这个url。返回的是一个 URLConnection 对象。能够解析文件头(可以这么理解).
								  |--
								  getInputStream();
									|--返回从此打开的连接读取的输入流。 在读取返回的输入流时，如果在数据可供读取之前达到读入超时时间，则会抛出 SocketTimeoutException。
								  getOutputStream();
									|--返回写入到此连接的输出流。
关于域名
	DNS服务器
	浏览器先会在本机寻找域名解析。如果没有再尝试连接DNS服务器来进行域名解析成IP！

------------------------
UDP-服务端				|
------------------------
	DatagramSocket ds = new DatagramSocket(10005);
	while(true){
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		ds.receive(dp);
		String ip = dp.getAddress().getHostAddress();
		String data = new String(dp.getData(),0,dp.getLength());
		System.out.println(ip+":"+data);
	}

------------------------
UDP-客户端				|
------------------------
	DatagramSocket ds = new DatagramSocket();
	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
	String line = null;
	while((line=bufr.readLine())!=null){
		if(line.equals("886")){
			break;
		}
		byte[] buf = line.getBytes();
		DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.131.174"), 10005);
		ds.send(dp);
	}
	ds.close();


------------------------
TCP-服务端				|
------------------------
		ServerSocket ss = new ServerSocket(10005);
		Socket s = ss.accept();
		String ip = s.getInetAddress().getHostAddress();
		System.out.println(ip+"------连接ing");
		//读取socket读取流中的数据
		BufferedReader bufin = new BufferedReader(new InputStreamReader(s.getInputStream()));
		//目的 socket输出流。将大写数据写入到socket输出流，并发送给客户端
		BufferedWriter bufout = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		String line = null;
		while ((line = bufin.readLine()) != null)
		{
			bufout.write(line.toUpperCase());
			bufout.newLine();
			bufout.flush();
		}
		s.close();
		ss.close();
------------------------
TCP-客户端				|
------------------------
		Socket s = new Socket("192.168.126.156",10005);
		//定义读取键盘数据的流对象
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		//定义目的地。讲数据写入 socket输出流。发给客户端
		BufferedWriter bufwOut = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		//定义一个 socket读取流。读取服务端返回的大写信息。
		BufferedReader bufin = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String line = null;
		while((line = bufr.readLine()) != null)
		{
			if("over".equals(line))
			{
				break;
			}
			bufwOut.write(line);
			bufwOut.newLine();
			bufwOut.flush();
			String str = bufin.readLine();
			System.out.println("服务器"+str);
		}
		bufr.close();
		s.close();