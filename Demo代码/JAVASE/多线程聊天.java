import java.io.*;
import java.net.*;

/*
 * 一个聊天程序
 * 	接收数据，发送数据。两个部分通过多线程来实现。
 * 因为接收和发送，动作不一致。所以，要定义两个run方法。而且这个两个方法要封装到不同的类中。
 * 
 */
class Send implements Runnable
{
	private DatagramSocket ds;
	Send(DatagramSocket ds){
		this.ds = ds;
	}
	public void run(){
		try{
			BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while((line = bufr.readLine())!=null){
				if("886".equals(line)){
					break;
				}
				byte[] buf = line.getBytes();
				DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getByName("192.168.131.174"),10002);
				ds.send(dp);
			}
		}
		catch(Exception e){
			throw new RuntimeException("发送端失败");
		}
	}
}
class Rece implements Runnable
{
	private DatagramSocket ds;
	Rece(DatagramSocket ds){
		this.ds = ds;
	}
	public void run(){
		try{
			while(true){
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				ds.receive(dp);//将接收到的数据。存储到定义好的数据包当中。
				String ip = dp.getAddress().getHostAddress();
				String data = new String(dp.getData(),0,dp.getLength());
				System.out.println(ip+":"+data);
			}
		}
		catch(Exception e){
			throw new RuntimeException("接收端失败");
		}
	}
}
public class Demo{
	public static void main(String[] args) throws SocketException{
		DatagramSocket sendSocket = new DatagramSocket();
		DatagramSocket receSocket = new DatagramSocket(10002);
		new Thread(new Send(sendSocket)).start();
		new Thread(new Rece(receSocket)).start();
	}
}






