------------------------
udp 单播				|
------------------------
	# 点对点
		* 客户端发送数据的端口,其实就是接受服务端响应的端口
	

	
------------------------
Server					|
------------------------

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Server {
	public static void main(String[] args) throws Exception {

		try (DatagramSocket datagramSocket = new DatagramSocket(1024)) {

			while (true) {
					
				// 读取客户端数据
				byte[] buffer = new byte[1024];
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
 				datagramSocket.receive(datagramPacket);

				// 处理收到的客户端数据
				String ip = datagramPacket.getAddress().getHostAddress();
				String data = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				int port = datagramPacket.getPort();

				System.out.println("recv: ip=" + ip + ",port=" + port + ",data=" + data);

				// 响应客户端
				buffer = data.toUpperCase().getBytes();
				datagramSocket.send(new DatagramPacket(buffer, 0, buffer.length, new InetSocketAddress(ip, port)));
			}
		}
	}
}

------------------------
client					|
------------------------


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
	public static void main(String[] args) throws Exception {
		
		try(DatagramSocket datagramSocket = new DatagramSocket()){
			
			// 往服务端发送数据
			byte[] data = "Hello".getBytes();
			datagramSocket.send(new DatagramPacket(data, 0, data.length, new InetSocketAddress("127.0.0.1", 1024)));
			
			// 读取服务端响应的数据
			byte[] buffer = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
			datagramSocket.receive(datagramPacket);
			System.out.println("recv:" + new String(datagramPacket.getData(),0,datagramPacket.getLength()));
		}
	}
}
