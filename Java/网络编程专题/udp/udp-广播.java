------------------------
udp 广播				|
------------------------
	# 往所有节点发送消息
		* 理论来说会往所有的设备发送广播数据
		* 但是会被路由器拦截,最终只能对局域网里面的设备进行广播
	

	# 受限广播地址: 255.255.255.255
	# C网广播地址一般是:xxx.xxx.xxx.255 (192.168.1.255)
	
	# 广播地址的运算
		ip			192.168.127.7
			
		子网掩码	255.255.255.0

		网络地址	192.168.124.0
		
		广播地址	192.168.124.255
	

------------------------
udp 广播				|
------------------------
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Broadcast {
	public static void main(String[] args) throws Exception {
		
		try(DatagramSocket datagramSocket = new DatagramSocket()){
			byte[] buffer = "Hello".getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(buffer, 0,buffer.length);
			// 设置广播地址
			datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
			// 设置端口
			datagramPacket.setPort(1024);
			// 发送广播消息
			datagramSocket.send(datagramPacket);
		}
	}
}