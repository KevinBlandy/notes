import java.io.*;
import java.net.*;
class Out implements Runnable
{
	public void run()
	{
		try
		{
			DatagramSocket ds = new DatagramSocket();
			BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while ((line = bufr.readLine()) != null)
			{
				if (line.equals("quite"))
				{
					break;
				}
				byte[] buf = line.getBytes();
				DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.132.245"),10000);
				ds.send(dp);
			}
		}
		catch(Exception e)
		{
			System.out.println("数据发送异常");
		}
	}
}
class In implements Runnable
{
	public void run()
	{
		try
		{
			DatagramSocket ds = new DatagramSocket(10000);
			while (true)
			{
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ds.receive(dp);
				String ip = dp.getAddress().getHostAddress();
				int port = dp.getPort();
				String data = new String(dp.getData(),0,dp.getLength());
				System.out.println("来自["+ip+"]的消息");
				System.out.println(data);
			}
		}
		catch(Exception e)
		{
			System.out.println("数据接收异常");
		}
	}
}
public class Send
{
	public static void main(String[] args)throws Exception
	{
		Out o = new Out();
		In i = new In();
		Thread t1 = new Thread(o);
		Thread t2 = new Thread(i);
		t1.start();
		t2.start();
	}
}