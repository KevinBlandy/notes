--------------------------------
Pipe							|
--------------------------------
	# jdk4以前管道流需要通过 PipedInputStream, PipedOutputStream 操作流来完成通信
	# jdk4以后, 提供了 Pipe, 通过操作缓冲区来通信
		* Pipe具备一个sourcechannel和一个sinkchannel
		* sinkchannel负责往管道写入数据,sourcechannel负责从管道读取数据
		
		* 千万不能同一个线程既读又写，因为读取操作会阻塞线程。同一个线程操作管道流会造成线程死锁
	
	# 构建
		//打开一个管道
		Pipe pipe = Pipe.open();
		//从管道获取写入数据的channel
		SinkChannel sinkChannel = pipe.sink();
		//从管道获取读取数据的channel
		SourceChannel sourceChannel = pipe.source();
	
	# 演示demo
		import java.io.IOException;
		import java.nio.ByteBuffer;
		import java.nio.channels.Pipe;
		import java.nio.channels.Pipe.SinkChannel;
		import java.nio.channels.Pipe.SourceChannel;
		import java.nio.charset.StandardCharsets;
		import java.util.Scanner;
		public class Main {
			public static void main(String[] args) throws Exception {
				//打开一个管道
				Pipe pipe = Pipe.open();
				//从管道获取写入数据的channel
				SinkChannel sinkChannel = pipe.sink();
				//从管道获取读取数据的channel
				SourceChannel sourceChannel = pipe.source();
				//新启动线程模拟读
				new Thread(() -> {
					try {
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						while(true) {
							//返回int，表示读取到了多少字节的数据，如果管道没有数据，该方法会阻塞
							sourceChannel.read(byteBuffer);
							//读取完毕后复位，由写改为读
							byteBuffer.flip();
							byte bytes[] = new byte[byteBuffer.remaining()]; 
							byteBuffer.get(bytes);
							System.out.println(Thread.currentThread().getName() + "-收到消息:" + new String(bytes,StandardCharsets.UTF_8));
							//清空缓冲区，由写改为读
							byteBuffer.clear();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}) .start();
				//当前线程模拟写
				try(Scanner scanner = new Scanner(System.in)){
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					while(true) {
						String line = scanner.nextLine();
						System.out.println(Thread.currentThread().getName() + "-发送消息:" + line);
						byteBuffer.put(line.getBytes(StandardCharsets.UTF_8));
						byteBuffer.flip();
						sinkChannel.write(byteBuffer);
						byteBuffer.clear();
					}
				}
			}
		}