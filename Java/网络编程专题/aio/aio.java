-------------------
AIO					|
-------------------
	# jdk7的产物
	# 才是真正的异步非阻塞IO,学习的是 Linux epoll 模式
	# API
		AsynchronousServerSocketChannel
			# 服务端 Channel
			 public abstract <A> void accept(A attachment,CompletionHandler<AsynchronousSocketChannel,? super A> handler);

		AsynchronousSocketChannel
			# 客户端 Channel
			public final <A> void read(ByteBuffer dst,A attachment,CompletionHandler<Integer,? super A> handler)
				* 

		AsynchronousChannelGroup
			# 线程组

		ExecutorService
			# 线程池,简单

		CompletionHandler<V,A>
			# 接口,俩抽象方法
				void completed(V result, A attachment);

				void failed(Throwable exc, A attachment);
	
	
	# 学习,参考资料
		https://www.ibm.com/developerworks/cn/java/j-lo-nio2/index.html

-------------------
AIO-Server			|
-------------------
	import java.net.InetSocketAddress;
	import java.nio.channels.AsynchronousChannelGroup;
	import java.nio.channels.AsynchronousServerSocketChannel;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	/**
	 * Created by Kevin on 2017/2/16 20:22.
	 */
	public class AioServer {
		//线程池
		private ExecutorService executorService;

		//线程组
		private AsynchronousChannelGroup asynchronousChannelGroup;

		//服务器通道
		private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

		public AioServer(int port){
			try {
				//实例化线程池
				executorService = Executors.newCachedThreadPool();
				//实例化线程组
				asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
				//创建服务通道
				asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
				//绑定本地端口
				asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
				System.out.println("服务端启动,port=" + port);
				//进行阻塞
				asynchronousServerSocketChannel.accept(this,new AioServerCompletionHandler());
				//一直阻塞 不让服务器停止
				Thread.sleep(Integer.MAX_VALUE);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
			return asynchronousServerSocketChannel;
		}

		public void setAsynchronousServerSocketChannel(AsynchronousServerSocketChannel asynchronousServerSocketChannel) {
			this.asynchronousServerSocketChannel = asynchronousServerSocketChannel;
		}
	}

-------------------
AIO-AioServerCompletionHandler|
-------------------
	import java.nio.ByteBuffer;
	import java.nio.channels.AsynchronousSocketChannel;
	import java.nio.channels.CompletionHandler;
	import java.util.concurrent.ExecutionException;

	/**
	 * Created by Kevin on 2017/2/16 20:27.
	 */
	public class AioServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

		/**
		 * 连接成功的时候执行的操作
		 * @param result
		 * @param attachment
		 */
		@Override
		public void completed(AsynchronousSocketChannel result, AioServer attachment) {
			//当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
			attachment.getAsynchronousServerSocketChannel().accept(attachment,this);
			read(result);
		}

		private void read(final AsynchronousSocketChannel asynchronousSocketChannel) {
			//构建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			asynchronousSocketChannel.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
				/**
				 * resultSize就是读取到的数据长度(字节数)
				 * @param resultSize
				 * @param attachment
				 */
				@Override
				public void completed(Integer resultSize, ByteBuffer attachment) {
					//进行读取之后,重置标识位
					attachment.flip();
					//获得读取的字节数
					System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
					//获取读取的数据
					String resultData = new String(attachment.array()).trim();
					System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
					String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
					write(asynchronousSocketChannel, response);
				}
				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					exc.printStackTrace();
				}
			});
		}

		private void write(AsynchronousSocketChannel asynchronousSocketChannel, String response) {
			try {
				ByteBuffer buf = ByteBuffer.allocate(1024);
				buf.put(response.getBytes());
				buf.flip();
				asynchronousSocketChannel.write(buf).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 异常时执行
		 * @param exc
		 * @param attachment
		 */
		@Override
		public void failed(Throwable exc, AioServer attachment) {
			exc.printStackTrace();
		}
	}


-------------------
AIO-AioClient		|
-------------------
	import java.io.UnsupportedEncodingException;
	import java.net.InetSocketAddress;
	import java.nio.ByteBuffer;
	import java.nio.channels.AsynchronousSocketChannel;
	import java.util.concurrent.ExecutionException;

	/**
	 * Created by Kevin on 2017/2/16 20:38.
	 */
	public class AioClient implements Runnable{
		private AsynchronousSocketChannel asc ;

		public AioClient() throws Exception {
			asc = AsynchronousSocketChannel.open();
		}

		public void connect(){
			asc.connect(new InetSocketAddress("127.0.0.1", 1024));
		}

		public void write(String request){
			try {
				asc.write(ByteBuffer.wrap(request.getBytes())).get();
				read();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void read() {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			try {
				asc.read(buf).get();
				buf.flip();
				byte[] respByte = new byte[buf.remaining()];
				buf.get(respByte);
				System.out.println(new String(respByte,"utf-8").trim());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(true){

			}
		}
		public static void main(String[] args) throws Exception {
			AioClient c1 = new AioClient();
			c1.connect();

			AioClient c2 = new AioClient();
			c2.connect();

			AioClient c3 = new AioClient();
			c3.connect();

			new Thread(c1, "c1").start();
			new Thread(c2, "c2").start();
			new Thread(c3, "c3").start();

			Thread.sleep(1000);

			c1.write("c1 aaa");
			c2.write("c2 bbbb");
			c3.write("c3 ccccc");
		}
	}