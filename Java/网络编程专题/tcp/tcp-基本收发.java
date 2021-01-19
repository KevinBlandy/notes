----------------------------
Server						|
----------------------------

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Server {
	
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	private static class Handler implements Runnable {
		private Socket socket;
		public Handler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				PrintStream printStream = new PrintStream(this.socket.getOutputStream());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),DEFAULT_CHARSET));
				while(true) {
					String line = bufferedReader.readLine();
					System.out.println("recv:" + line);
					
					printStream.println(line.toUpperCase());
					
					if(line.equalsIgnoreCase("bye")) {
						break;
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		try(ServerSocket serverSocket = new ServerSocket()){
			
			serverSocket.bind(new InetSocketAddress("0.0.0.0",1024));
			serverSocket.setReuseAddress(true);
			
			while(true) {
				
				Socket socket = serverSocket.accept();
				new Thread(new Handler(socket)).start();
			}
		}
	}
}

----------------------------
Client						|
----------------------------
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
	
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	public static void main(String[] args) throws Exception {
		
		try (Socket socket = new Socket()) {
			
			socket.setSoTimeout(3000); // 超时时间
			socket.connect(new InetSocketAddress("127.0.0.1", 1024), 3000); // 发起连接,设置超时时间

			PrintStream printStream = new PrintStream(socket.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),DEFAULT_CHARSET));
			
			try(Scanner scanner = new Scanner(System.in)){
				
				while (true) {
					
					// 读取标准输入
					String line = scanner.nextLine();
					
					System.out.println("send:" + line);
					
					printStream.println(line);
					
					// 读取服务端响应
					String response = bufferedReader.readLine();
					
					System.out.println("recv:" + response);
					
					if("bye".equalsIgnoreCase(response)) {
						break;
					}
				}
			}
		}
	}
}
