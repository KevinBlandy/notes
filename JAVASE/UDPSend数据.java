import java.io.IOException;
import java.net.*;
/*-------------------------发送端
 *通过UDP传输方式。将一段文字数据发送出去。
 *1，先建立udpsocket服务
 *2，提供数据并将数据封装到数据包中
 *3，通过 socket 服务的发送功能，将数据发送出去
 *4，关闭资源
 */
public class Send
{
	public static void main(String[] args) throws IOException
	{
		//创建udp服务,通过 DatagramSocet对象
		DatagramSocket ds = new DatagramSocket(8888);
		//确定数据，并封装成数据包
		byte[] buf = "KevinBlandy".getBytes();//String.getBytes();把字符串转换成字节数组。
		DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.132.245"),10000);
		//通过socket服务将已有的数据包通过 send方法。发送出去。
		ds.send(dp);//阻塞式方法。如果没数据就会一直等(线程机制)。
		//关闭资源
		ds.close();
	}
}


















































































