
----------------------------
零拷贝						|
----------------------------
	# 在操作系统支持的情况下,通过该方法传输数据并不需要将源数据从内核态拷贝到用户态,再从用户态拷贝到目标通道的内核态
	# 同时也避免了两次用户态和内核态间的上下文切换,也即使用了"零拷贝"

----------------------------
文件与网络的零拷贝			|
----------------------------
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NIOClient {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("192.168.0.2",1234));
		
		// 随机io
		RandomAccessFile randomAccessFile = new RandomAccessFile(NIOClient.class.getClassLoader().getResource("test.txt").getFile(),"rw");
		
		// 文件channel
		FileChannel fileChannel = randomAccessFile.getChannel();
		// 把文件channle直接输出到网络channle
		fileChannel.transferTo(0, fileChannel.size(), socketChannel);
		
		// 资源关闭
		fileChannel.close();
		randomAccessFile.close();
		socketChannel.close();
	}
}