------------------------
Pipe					|
------------------------
	# Java NIO 管道是2个线程之间的单向数据连接。
	# Pipe有一个'source'通道和一个'sink'通道。
	# 数据会被写到sink通道，从source通道读取。
	# 原理图
						------- Pipe -------
		Thread-A ----> |	sink	source	| ----> Thread-B
						-------------------- 

------------------------
Pipe-写入				|
------------------------
	//创建管道
	Pipe pipe = Pipe.open();
	//获取管道中的 sink 通道
	Pipe.SinkChannel sinkChannel = pipe.sink();
	//准备数据
	String newData = "New String to write to file..." + System.currentTimeMillis();
	//构建缓冲区
	ByteBuffer buf = ByteBuffer.allocate(48);
	//清空缓冲区
	buf.clear();
	//填充数据
	buf.put(newData.getBytes());
	//复位,因为要准备读取缓冲区里面的数据写入到管道
	buf.flip();
	//读取缓冲区的数据写入到管道
	while(buf.hasRemaining()) {
		sinkChannel.write(buf);
	}

------------------------
Pipe-读取				|
------------------------
	//创建管道
	Pipe pipe = Pipe.open();
	//从管道获取 source 通道
	Pipe.SourceChannel sourceChannel = pipe.source();
	//创建缓冲区
	ByteBuffer buf = ByteBuffer.allocate(48);
	//把管道的数据写入缓冲区,返回读取到的字节长度
	int bytesRead = sourceChannel.read(buf);


------------------------
Pipe-API				|
------------------------


------------------------
Pipe-Demo				|
------------------------

import java.nio.channels.Pipe; 
import java.nio.ByteBuffer;
import java.util.Scanner;
/**
	通道Demo
*/
public class Demo{
	public static void main(String[] args)throws Exception{
		//打开通道
		Pipe pipe = Pipe.open();
		//构建线程写入
		new Thread(){
			public void run(){
				try{
					//从通道获取 sikin 管道
					Pipe.SinkChannel sinkChannel = pipe.sink();
					Scanner scanner = new Scanner(System.in);
					String data = null;
					while(true){
						data = scanner.next();
						//创建Buffer
						ByteBuffer buf = ByteBuffer.allocate(1024);
						buf.clear();
						buf.put(data.getBytes());
						buf.flip();
						while(buf.hasRemaining()) {
							sinkChannel.write(buf);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();

		//构建线程读取
		new Thread(){
			public void run(){
				try{
					//从通道获取 source 管道
					Pipe.SourceChannel sourceChannel = pipe.source();
					//创建缓冲区
					ByteBuffer buf = ByteBuffer.allocate(1024);
					int len = 0;
					while((len = sourceChannel.read(buf)) != -1){
						buf.flip();
						while(buf.hasRemaining()){
							System.out.print((char)buf.get());
						}
						System.out.println();
						buf.clear();
						len = sourceChannel.read(buf);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
}
	
