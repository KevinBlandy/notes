/*
	服务端将客户端传送的数据。转换成大写并返回。
	而且客户端可以进行不断的发送。当客户端输入over时。转换结束。
分析：
客户端：
	客户端既然是操作设备上的数据，那么就可以使用IO，并按照IO的操作规律来思考。
  源：键盘录入
目的：网络输出流(网络设备)，而且操作的是文本数据。那就可以选择字符流。
步骤：
1，建立服务。
2，获取键盘录入
3，讲数据发送给服务端
4，获取服务端返回的大写数据
5，结束，关闭资源
*/
import java.io.*;
import java.net.*;
class TransClient
{
	public static void main(String[] args)throws Exception
	{
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
	}
}
/*
服务端：
源：socket 读取流
目的：socket 输出流
都是文本流。用装饰类。
*/
class TransServer
{
	public static void main(String[] args)throws Exception
	{
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
	}
}
class Demo1 
{
	public static void main(String[] args) 
	{
		
	}
}
//该例会出现的问题
/*
	现象：客户端和服务端都在莫名的等待。
	因为：客户端和服务端都有阻塞式方法。这些方法没有读到标记。就会一直处于等待状态。导致两端都在等待。
*/
