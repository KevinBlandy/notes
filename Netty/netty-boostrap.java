----------------------------
Netty服务端的配置			|
----------------------------
	# 服务端的一般配置代码
		// 处理连接的线程池
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 处理IO事件的线程池
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 创建服务端对象
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 设置线程池
			serverBootstrap.group(bossGroup, workerGroup);
			// 设置io模式
			serverBootstrap.channel(NioServerSocketChannel.class);
			// 监听的网卡和端口
			serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0", 1024));
			// 设置日志handler
			serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
			// 设置客户端handlder
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				// 初始化信息设置
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());
				}
			});
			// 设置服务端属性
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
			serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);

			// 设置客户端属性
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			// 绑定到设置的端口和网卡,并且同步(阻塞)的启动服务
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

----------------------------
ChannelInitializer			|
----------------------------
	# ChannelInitializer 自身也是一个ChannelHandler,在添加完其他的 handlers 之后会自动从 ChannelPipeline 中删除自己


----------------------------
常用
----------------------------
package io.springcloud.test.socket;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.SimpleUserEventChannelHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * @author KevinBlandy
 *
 */
/*

+--------------+-----+
|length (4byte)|data |
+--------------+-----+

*/
public class SocketServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup bossGroup = null;
		EventLoopGroup workerGroup = null;
		Class<? extends ServerSocketChannel> serverSocketChannel = null;
		
		if (Epoll.isAvailable()) {
			bossGroup = new EpollEventLoopGroup();
			workerGroup = new EpollEventLoopGroup();
			serverSocketChannel = EpollServerSocketChannel.class;
		} else {
			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();
			serverSocketChannel = NioServerSocketChannel.class;
		}
		
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			serverBootstrap.channel(serverSocketChannel);
			serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0", 1024));
			serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					SocketServer.initChannel(socketChannel.pipeline());
				}
			});
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
			serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);

			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);

			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	private static void initChannel (ChannelPipeline channelPipeline) {
		
		// TODO SSL
		
		/**
		 * ZIP压缩
		 */
//		channelPipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//		channelPipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		
		/**
		 * 出站编码
		 */
		channelPipeline.addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 4, 0, Boolean.FALSE));
		channelPipeline.addLast(new MessageToByteEncoder<String>() {
			@Override
			protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
				out.writeBytes(msg.getBytes());
			}
		});
		
		/**
		 * 连接事件
		 */
		channelPipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				LOGGER.info("新的连接: channelId={}, remoteAddr={}", ctx.channel().id().asLongText(), ctx.channel().remoteAddress());
				super.channelActive(ctx);
			}

			@Override
			public void channelInactive(ChannelHandlerContext ctx) throws Exception {
				LOGGER.info("channel 连接断开: id={}", ctx.channel().id().asLongText());
				super.channelInactive(ctx);
			}
		});
		
		
		/**
		 * 心跳
		 */
		channelPipeline.addLast(new IdleStateHandler(6, 0, 0, TimeUnit.SECONDS));
		channelPipeline.addLast(new SimpleUserEventChannelHandler<IdleStateEvent>() {
			@Override
			protected void eventReceived(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
				if (evt.state() == IdleState.READER_IDLE) {
					LOGGER.info("channel读超时: first={}, channelId={}", evt.isFirst(), ctx.channel().id().asLongText());
					// 写入通知后，关闭连接
					ctx.channel().writeAndFlush("客户端读取超时")
						.addListener(ChannelFutureListener.CLOSE);
				}				
			}
		});
		
		/**
		 * 入站编码
		 */
		channelPipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 0, Boolean.TRUE));
		channelPipeline.addLast(new ByteToMessageDecoder() {
			@Override
			protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
				int messageSize = in.readInt(); // 读取长度
				byte[] data = new byte[messageSize];
				in.readBytes(data);
				out.add(new String(data, StandardCharsets.UTF_8));
			}
		});

		/**
		 * 业务处理
		 */
		channelPipeline.addLast(new SimpleChannelInboundHandler<String>() {
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
				LOGGER.info("收到客户端消息了:{}", msg);
				Channel channel = ctx.channel();
				if (channel.isActive()) {
					channel.writeAndFlush("我收到了你的消息[" + msg + "]:" + UUID.randomUUID().toString());
				}
			}
		});
		
		/**
		 * 异常处理
		 */
		channelPipeline.addLast(new ChannelInboundHandlerAdapter() {
			
			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

				LOGGER.error("系统异常:{}", cause.getMessage());

				ctx.channel().writeAndFlush("系统异常:" + cause.getMessage())
						.addListener(ChannelFutureListener.CLOSE);
			}
		});
	}
}


// go 客户端

package main

import (
	"bufio"
	"encoding/binary"
	"io"
	"log"
	"math/rand"
	"net"
	"os"
	"time"
)

// 182.92.103.206

func init(){
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.Ldate | log.Ltime | log.Llongfile)
	rand.Seed(time.Now().UTC().UnixNano())
}

const Content =
`
你以为的是你以为
`


func main() {
	addr, err := net.ResolveTCPAddr("tcp4", "127.0.0.1:1024")
	if err != nil {
		log.Fatalf("TCP地址解析异常: err=%s\n", err.Error())
	}

	conn, err := net.DialTCP("tcp4", nil, addr)
	if err != nil {
		log.Fatalf("创建TCP链接异常: err=%s\n", err.Error())
	}

	go func() {
		for {
			// 读取header
			sizeBuffer := make([]byte, 4)
			_, err := io.ReadFull(conn, sizeBuffer)
			if err != nil {
				if err == io.EOF {
					log.Println("链接已经断开")
				} else {
					log.Fatalf("读取异常: err=%s\n", err.Error())
				}
				break
			}

			// 读取body
			size := binary.BigEndian.Uint32(sizeBuffer)
			dataBuffer := make([]byte, size, size)
			_, err = io.ReadFull(conn, dataBuffer)
			if err != nil {
				if err == io.EOF {
					log.Println("链接已经断开")
				} else {
					log.Fatalf("读取异常: err=%s\n", err.Error())
				}
				break
			}

			log.Printf("收到服务器消息: %s\n", string(dataBuffer))
		}
	}()


	scanner := bufio.NewScanner(os.Stdin)
	scanner.Split(bufio.ScanLines)
	for scanner.Scan(){
		if err := scanner.Err(); err != nil {
			if err != io.EOF {
				log.Fatalf("scanner扫描异常: err=%s\n", err.Error())
			}
			break
		}

		data := scanner.Text()

		// 写入header
		sizeBuffer := make([]byte, 4, 4)
		binary.BigEndian.PutUint32(sizeBuffer, uint32(len(data)))
		conn.Write(sizeBuffer) 

		// 写入body
		conn.Write([]byte(data))
	}
}
