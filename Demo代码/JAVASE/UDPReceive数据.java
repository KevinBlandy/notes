import java.net.*;
import java.io.IOException;
/*------------------接收端.
 * 	      定义接收端的时候，通常都会监听一个端口。其实就是给这个接收网络应用程序定义数字标示。
 * 	      方便于明确哪些数据过来该应用程序处理。
 *
 * 1，定义udpsocket服务
 * 2，定义一个数据包，因为要存储接收到的字节数据。因为数据包对象中有更多功能可以提取字节数据中的不同数据信息。
 * 3，通过socket服务的receive方法将收到的数据存入已定义好的数据包中，
 * 4，通过数据包对象的特有功能，将这些不同的数据取出，打印在控制台上。
 * 5，关闭资源。
 */
public class Receive
{
	public static void main(String[] args)throws IOException
	{
		//创建udpsocket服务.建立端点
		DatagramSocket ds = new DatagramSocket(10000);
		while(true)
		{
			//定义数据包，用于存储数据
			byte[] buf = new byte[1024];//缓冲区
			DatagramPacket dp = new DatagramPacket(buf,buf.length);
			//通过socket服务receive方法将收到的数据存入数据包中
			ds.receive(dp);
			//通过数据包的方法获取其中的数据
			String ip = dp.getAddress().getHostAddress();//获取IP。
			String data = new String(dp.getData(),0,dp.getLength());//获取数据。
			int port = dp.getPort();//获取端口。
			System.out.println(ip+"::"+data+"::"+port);
			//关闭资源
			//ds.close();
		}
		//ds.close();
	}
}
