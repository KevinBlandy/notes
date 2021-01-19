import java.io.IOException;
import java.net.*;
public class Receive
{
	public static void main(String[] args) throws IOException
	{
		DatagramSocket ds = new DatagramSocket(10005);
		while(true){
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf, buf.length);
			ds.receive(dp);
			String ip = dp.getAddress().getHostAddress();
			String data = new String(dp.getData(),0,dp.getLength());
			System.out.println(ip+":"+data);
		}
	}
}
//------------------------------------Ω” ’∂À
import java.net.*;
import java.io.*;
public class Send
{
	public static void main(String[] args) throws IOException
	{
		DatagramSocket ds = new DatagramSocket();
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while((line=bufr.readLine())!=null)
		{
			if(line.equals("886"))
			{
				break;
			}
			byte[] buf = line.getBytes();
			DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.131.174"), 10005);
			ds.send(dp);
		}
		ds.close();
	}
}
//-------------∑¢ÀÕ∂À