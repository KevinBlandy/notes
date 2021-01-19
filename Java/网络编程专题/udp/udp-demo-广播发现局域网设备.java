
------------------------------
局域网的设备发现			  |
------------------------------
	# 要求所有设备都监听一个UDP端口,监听广播信息
	# 收到广播消息后,从消息里面获取到服务器的ip,端口等信息
	# 再往服务器发送自身的设备id,ip,提供的服务(协议类型),端口等等信息

------------------------------
扫描						  |
------------------------------

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Scanner {
	
	// 节点描述
	private static class Endpoint {
		private String id;
		private int port;
		private String ip;
		public Endpoint(String id, int port, String ip) {
			super();
			this.id = id;
			this.port = port;
			this.ip = ip;
		}
		@Override
		public String toString() {
			return "Endpoint [id=" + id + ", port=" + port + ", ip=" + ip + "]";
		}
	}
	
	// 监听服务
	private static class Listenner implements Runnable  {
		private int port;
		private CountDownLatch countDownLatch;
		private volatile boolean done;
		private List<Endpoint> endpoints;
		public Listenner(int port,CountDownLatch countDownLatch) {
			this.port = port;
			this.countDownLatch = countDownLatch;
			this.done = false;
			this.endpoints = new LinkedList<>();
		}

		@Override
		public void run() {
			DatagramSocket datagramSocket = null;
			try {
				
				this.countDownLatch.countDown();
				
				datagramSocket = new DatagramSocket(null);
				datagramSocket.setReuseAddress(true);
				datagramSocket.bind(new InetSocketAddress("0.0.0.0", this.port));
				
				while(!this.done) {
					byte[] data = new byte[1024];
					DatagramPacket datagramPacket = new DatagramPacket(data,data.length);
					datagramSocket.receive(datagramPacket);
					String[] response = new String(datagramPacket.getData(),0,datagramPacket.getLength()).split(":");
					this.endpoints.add(new Endpoint(response[0], Integer.parseInt(response[1]),datagramPacket.getAddress().getHostAddress()));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(datagramSocket != null) {
					datagramSocket.close();
				}
			}
		}
		
		public List<Endpoint> endpoints(){
			this.done = true;
			return this.endpoints;
		}
	}
	
	public static void broadcast(int port,byte[] data) {
		
		DatagramSocket datagramSocket = null;
		try {
			
			datagramSocket = new DatagramSocket();
			
			DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
			datagramPacket.setAddress(InetAddress.getByName("255.255.255.255"));
			datagramPacket.setPort(port);
			
			datagramSocket.send(datagramPacket);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}
	
	public static void main(String[] args) throws Exception{

		// 监听服务
		int port = 10086;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		Listenner listenner = new Listenner(port, countDownLatch);
		Thread thread = new Thread(listenner);
		thread.setDaemon(true);		// 守护线程
		thread.start();
		
		countDownLatch.await();

		// 广播消息
		byte[] data = ("port=" + port).getBytes();
		broadcast(1024, data);
		
		System.in.read();	
		
		// 读取广播后的响应
		List<Endpoint> endpoints = listenner.endpoints();
		for(Endpoint endpoint : endpoints) {
			System.out.println(endpoint);
		}
	}
}





------------------------------
服务端						  |
------------------------------
import socket

id = '0xFF'
port = 1024

server = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
server.bind(('0.0.0.0', port))

while True:
    data, client = server.recvfrom(1024)
    
    print('收到数据:%s %s'%(data,client))
    
    # 从数据中获取到报告服务的端口
    serverPort = int(data.decode().split('=')[1])
    response = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
    # 向服务端报告自己的id和提供服务的端口
    response.sendto((id + ':' + str(port)).encode(),(client[0],serverPort))
    
    


