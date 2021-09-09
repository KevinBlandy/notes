----------------------------
java-nio					|
----------------------------
	* 参考资料
		http://ifeve.com/overview/




----------------------------
关键组件					|
----------------------------
	ServerSocketChannel	
		* ServerSocket 代替类,支持阻塞通信与非阻塞通信

	SocketChannel
		* Socket 代替类,支持阻塞通信与非阻塞通信

	Selector
		* 为 ServerSocketChannel	监控接收连接就绪事件
		* 为 SocketChannel			监控连接就绪,读就绪,写就绪事件

	SelectionKey
		* 代表 ServerSocketChannel 和 SocketChannel 向 Selector 注册事件的句柄
		* 当一个 SelectionKey 对象位于 Selector 对象的 selected-keys 集合中时,就表示与这个 SelectionKey 对象相关的事件发生了

	
----------------------------
Channel 体系图				|
----------------------------
				|----------Channel-------------|
		SelectableChannel				(interface)ByteChannel
		|				|----------------------|
ServerSocketChannel	SocketChannel


----------------------------
SelectionKey 事件常量		|
----------------------------
	SelectionKey.OP_ACCEPT;
		* 标记连接就绪,表示至少有一个客户端连接,服务器可以接收这个连接

	SelectionKey.OP_CONNECT;
		* 连接就绪事件,表示客户与服务器的连接已经建立成功了

	SelectionKey.OP_READ;
		* 读就绪事件,表示输入流中已经有可读数据,可以执行读操作了

	SelectionKey.OP_WRITE;
		* 写就绪事件,表示已经可以向输出流写数据了
	
----------------------------
Server
----------------------------
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		// selector
		Selector selector = Selector.open();
		
		// server
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress("0.0.0.0", 1024));
	
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while (selector.select() > 0) {
			Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();;
			while (selectionKeyIterator.hasNext()) {
				SelectionKey selectionKey =  selectionKeyIterator.next();
				if (selectionKey.isAcceptable()) {
					// new connection
					SocketChannel socketChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					
				} else if (selectionKey.isReadable()) {
					
				} else if (selectionKey.isWritable()) {
					
				} else if (selectionKey.isConnectable()) {
				} else {
					
				}
				selectionKeyIterator.remove();
			}
		}
	}
}