
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {
    public static void main(String[] args) throws Exception{

        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.TRUE);
        // 绑定发送端口
        datagramChannel.bind(new InetSocketAddress(7725));


        ByteBuffer byteBuffer = ByteBuffer.allocate(65507);
        byteBuffer.put("Nio UDP".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();

        // 返回已经成功发送的字节数据, 可能一次性没发送完毕
        int count = datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 1024));

        System.out.println(count);
    }
}
