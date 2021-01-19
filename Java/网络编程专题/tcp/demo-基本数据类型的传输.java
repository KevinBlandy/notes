
--------------------------------
基本数据类型的传输				|
--------------------------------
	# 其实就是借助了 ByteBuffer 的api完成基本数据类型和java基础数据类型的转换


--------------------------------
Server							|
--------------------------------

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Server {

	public static void main(String[] args) throws Exception {

		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 1024));
			serverSocket.setReuseAddress(true);

			for (;;) {
				try (Socket socket = serverSocket.accept()) {
					
					InputStream inputStream = socket.getInputStream();
					
					byte[] bytes = new byte[1024];
					
					int length = inputStream.read(bytes);
					
					if (length != -1) {
						
						// 使用 ByteBuffer的api完成基本数据类型和字节数据的转换
						ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, length);
						
						byte byteValue = byteBuffer.get();
						
						char charValue = byteBuffer.getChar();

						int intValue = byteBuffer.getInt();

						boolean booleanValue = byteBuffer.get() == 1;

						long longValue = byteBuffer.getLong();

						float floatValue = byteBuffer.getFloat();

						double doubleValue = byteBuffer.getDouble();

						// 读取到的角标
						int position = byteBuffer.position();
						// 下一个角标开始,直到最后一个字节,都是字符串数据
						String stringValue = new String(bytes, position, (length - position) - 1);
						
						System.out.println("收到客户端的数据:"
								+ "byte="+byteValue + "\n"
								+ "char="+charValue + "\n"
								+ "int="+intValue + "\n"
								+ "boolean="+booleanValue + "\n"
								+ "long="+longValue + "\n"
								+ "floatValue="+floatValue + "\n"
								+ "double="+doubleValue + "\n"
								+ "String="+stringValue + "\n"
								);
					}
				}
			}
		}
	}
}


--------------------------------
Client							|
--------------------------------
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {
	
	public static void main(String[] args) throws Exception {
		
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("127.0.0.1", 1024)); // 发起连接

			OutputStream outputStream = socket.getOutputStream();
			
			// 使用 ByteBuffer的api完成基本数据类型和字节数据的转换
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			byteBuffer.put((byte)1);
			byteBuffer.putChar('C');
			byteBuffer.putInt(3);
			byteBuffer.put((byte)1);
			byteBuffer.putLong((long)5);
			byteBuffer.putFloat((float)6.1);
			byteBuffer.putDouble(6.2);
			byteBuffer.put("KevinBlandy".getBytes());
			
			outputStream.write(byteBuffer.array(),0,byteBuffer.position() + 1);
		}
	}
}
