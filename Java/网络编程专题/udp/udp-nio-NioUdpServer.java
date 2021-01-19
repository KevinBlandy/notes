----------------------------
非 selector					|
----------------------------
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class NioUdpServer {

    public static void main(String[] args) throws Exception {

        try(DatagramChannel datagramChannel = DatagramChannel.open()){

            datagramChannel.bind(new InetSocketAddress(1024));
            // 一些socket的选项
            datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.TRUE);

            // 预定义缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(65507);

            // 阻塞接收数据
            while (true){
                // 客户端地址信息
                InetSocketAddress inetSocketAddress = (InetSocketAddress) datagramChannel.receive(byteBuffer);
                String hostName = inetSocketAddress.getHostName();
                int port = inetSocketAddress.getPort();
                String hostString = inetSocketAddress.getHostString();
                System.out.println(hostName + "[" + hostString +"]:" + port);

                // 数据信息
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);
                byteBuffer.clear();
                System.out.println(new String(bytes, StandardCharsets.UTF_8));

				
				// 往客户端回写数据
				datagramChannel.send(ByteBuffer.wrap("Hello".getBytes()), inetSocketAddress);
            }
        }
    }
}


----------------------------
selector					|
----------------------------
	# udp的selector只有一个作用, 就是用它来轮询n个客户端/服务端
		* udp没有连接, 不需要去监听状态, 也不存在阻塞等待数据的情况
		* 如果只是一个客户端/服务端, 那么使用传统的:DatagramSocket 没区别

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NioUdpServer {

    public static void main(String[] args) throws Exception {

        Selector selector = Selector.open();
        DatagramChannel datagramChannel = DatagramChannel.open();

        try{
            datagramChannel.bind(new InetSocketAddress(1024));

            // 非阻塞模式
            datagramChannel.configureBlocking(false);
            // 一些socket的选项
            datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.TRUE);

            datagramChannel.register(selector, SelectionKey.OP_READ);

            // 预定义缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(65507);

            while (selector.select() > 0){
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()){// 读事件
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    try{
                        if(selectionKey.isReadable()){

                            DatagramChannel channel = (DatagramChannel) selectionKey.channel();

                            // 客户端地址信息
                            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.receive(byteBuffer);
                            String hostName = inetSocketAddress.getHostName();
                            int port = inetSocketAddress.getPort();
                            String hostString = inetSocketAddress.getHostString();
                            System.out.println(hostName + "[" + hostString +"]:" + port);

                            // 数据信息
                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            byteBuffer.clear();
                            System.out.println(new String(bytes, StandardCharsets.UTF_8));

                        }else if(selectionKey.isWritable()){
                        }else if(selectionKey.isAcceptable()){
                        }else if(selectionKey.isConnectable()){
                        }else{ }
                    }finally {
                        selectionKeyIterator.remove();
                    }
                }
            }

        }finally {
            selector.close();
            datagramChannel.close();
        }
    }
}
