----------------------------
java-nio					|
----------------------------
	* �ο�����
		http://ifeve.com/overview/




----------------------------
�ؼ����					|
----------------------------
	ServerSocketChannel	
		* ServerSocket ������,֧������ͨ���������ͨ��

	SocketChannel
		* Socket ������,֧������ͨ���������ͨ��

	Selector
		* Ϊ ServerSocketChannel	��ؽ������Ӿ����¼�
		* Ϊ SocketChannel			������Ӿ���,������,д�����¼�

	SelectionKey
		* ���� ServerSocketChannel �� SocketChannel �� Selector ע���¼��ľ��
		* ��һ�� SelectionKey ����λ�� Selector ����� selected-keys ������ʱ,�ͱ�ʾ����� SelectionKey ������ص��¼�������

	
----------------------------
Channel ��ϵͼ				|
----------------------------
				|----------Channel-------------|
		SelectableChannel				(interface)ByteChannel
		|				|----------------------|
ServerSocketChannel	SocketChannel


----------------------------
SelectionKey �¼�����		|
----------------------------
	SelectionKey.OP_ACCEPT;
		* ������Ӿ���,��ʾ������һ���ͻ�������,���������Խ����������

	SelectionKey.OP_CONNECT;
		* ���Ӿ����¼�,��ʾ�ͻ���������������Ѿ������ɹ���

	SelectionKey.OP_READ;
		* �������¼�,��ʾ���������Ѿ��пɶ�����,����ִ�ж�������

	SelectionKey.OP_WRITE;
		* д�����¼�,��ʾ�Ѿ������������д������
	
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