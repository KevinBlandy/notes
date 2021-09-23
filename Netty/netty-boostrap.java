----------------------------
Netty����˵�����			|
----------------------------
	# ����˵�һ�����ô���
		// �������ӵ��̳߳�
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// ����IO�¼����̳߳�
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// ��������˶���
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// �����̳߳�
			serverBootstrap.group(bossGroup, workerGroup);
			// ����ioģʽ
			serverBootstrap.channel(NioServerSocketChannel.class);
			// �����������Ͷ˿�
			serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0", 1024));
			// ������־handler
			serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
			// ���ÿͻ���handlder
			serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				// ��ʼ����Ϣ����
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());
				}
			});
			// ���÷��������
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
			serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);

			// ���ÿͻ�������
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			// �󶨵����õĶ˿ں�����,����ͬ��(����)����������
			ChannelFuture channelFuture = serverBootstrap.bind().sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

----------------------------
ChannelInitializer			|
----------------------------
	# ChannelInitializer ����Ҳ��һ��ChannelHandler,������������� handlers ֮����Զ��� ChannelPipeline ��ɾ���Լ�


----------------------------
����
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
		 * ZIPѹ��
		 */
//		channelPipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//		channelPipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
		
		/**
		 * ��վ����
		 */
		channelPipeline.addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 4, 0, Boolean.FALSE));
		channelPipeline.addLast(new MessageToByteEncoder<String>() {
			@Override
			protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
				out.writeBytes(msg.getBytes());
			}
		});
		
		/**
		 * �����¼�
		 */
		channelPipeline.addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				LOGGER.info("�µ�����: channelId={}, remoteAddr={}", ctx.channel().id().asLongText(), ctx.channel().remoteAddress());
				super.channelActive(ctx);
			}

			@Override
			public void channelInactive(ChannelHandlerContext ctx) throws Exception {
				LOGGER.info("channel ���ӶϿ�: id={}", ctx.channel().id().asLongText());
				super.channelInactive(ctx);
			}
		});
		
		
		/**
		 * ����
		 */
		channelPipeline.addLast(new IdleStateHandler(6, 0, 0, TimeUnit.SECONDS));
		channelPipeline.addLast(new SimpleUserEventChannelHandler<IdleStateEvent>() {
			@Override
			protected void eventReceived(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
				if (evt.state() == IdleState.READER_IDLE) {
					LOGGER.info("channel����ʱ: first={}, channelId={}", evt.isFirst(), ctx.channel().id().asLongText());
					// д��֪ͨ�󣬹ر�����
					ctx.channel().writeAndFlush("�ͻ��˶�ȡ��ʱ")
						.addListener(ChannelFutureListener.CLOSE);
				}				
			}
		});
		
		/**
		 * ��վ����
		 */
		channelPipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 0, Boolean.TRUE));
		channelPipeline.addLast(new ByteToMessageDecoder() {
			@Override
			protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
				int messageSize = in.readInt(); // ��ȡ����
				byte[] data = new byte[messageSize];
				in.readBytes(data);
				out.add(new String(data, StandardCharsets.UTF_8));
			}
		});

		/**
		 * ҵ����
		 */
		channelPipeline.addLast(new SimpleChannelInboundHandler<String>() {
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
				LOGGER.info("�յ��ͻ�����Ϣ��:{}", msg);
				Channel channel = ctx.channel();
				if (channel.isActive()) {
					channel.writeAndFlush("���յ��������Ϣ[" + msg + "]:" + UUID.randomUUID().toString());
				}
			}
		});
		
		/**
		 * �쳣����
		 */
		channelPipeline.addLast(new ChannelInboundHandlerAdapter() {
			
			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

				LOGGER.error("ϵͳ�쳣:{}", cause.getMessage());

				ctx.channel().writeAndFlush("ϵͳ�쳣:" + cause.getMessage())
						.addListener(ChannelFutureListener.CLOSE);
			}
		});
	}
}


// go �ͻ���

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
����Ϊ��������Ϊ
`


func main() {
	addr, err := net.ResolveTCPAddr("tcp4", "127.0.0.1:1024")
	if err != nil {
		log.Fatalf("TCP��ַ�����쳣: err=%s\n", err.Error())
	}

	conn, err := net.DialTCP("tcp4", nil, addr)
	if err != nil {
		log.Fatalf("����TCP�����쳣: err=%s\n", err.Error())
	}

	go func() {
		for {
			// ��ȡheader
			sizeBuffer := make([]byte, 4)
			_, err := io.ReadFull(conn, sizeBuffer)
			if err != nil {
				if err == io.EOF {
					log.Println("�����Ѿ��Ͽ�")
				} else {
					log.Fatalf("��ȡ�쳣: err=%s\n", err.Error())
				}
				break
			}

			// ��ȡbody
			size := binary.BigEndian.Uint32(sizeBuffer)
			dataBuffer := make([]byte, size, size)
			_, err = io.ReadFull(conn, dataBuffer)
			if err != nil {
				if err == io.EOF {
					log.Println("�����Ѿ��Ͽ�")
				} else {
					log.Fatalf("��ȡ�쳣: err=%s\n", err.Error())
				}
				break
			}

			log.Printf("�յ���������Ϣ: %s\n", string(dataBuffer))
		}
	}()


	scanner := bufio.NewScanner(os.Stdin)
	scanner.Split(bufio.ScanLines)
	for scanner.Scan(){
		if err := scanner.Err(); err != nil {
			if err != io.EOF {
				log.Fatalf("scannerɨ���쳣: err=%s\n", err.Error())
			}
			break
		}

		data := scanner.Text()

		// д��header
		sizeBuffer := make([]byte, 4, 4)
		binary.BigEndian.PutUint32(sizeBuffer, uint32(len(data)))
		conn.Write(sizeBuffer) 

		// д��body
		conn.Write([]byte(data))
	}
}
