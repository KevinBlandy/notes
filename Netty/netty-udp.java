-------------------------------
udp								|
-------------------------------
	# 类库
		DatagramPacket
		DatagramPacketEncoder
		DatagramPacketDecoder
	
	# 资瓷的配置
		// 开启广播
		bootstrap.option(ChannelOption.SO_BROADCAST, true);
		// 设置UDP读缓冲区为2M
		bootstrap.option(ChannelOption.SO_RCVBUF, 2048 * 1024);
		// 设置UDP写缓冲区为1M
		bootstrap.option(ChannelOption.SO_SNDBUF, 1024 * 1024);

-------------------------------
DatagramPacket					|
-------------------------------
	# 继承:DefaultAddressedEnvelope 实现 ByteBufHolder
	# Netty 对udp消息的封装
		DatagramPacket(ByteBuf data, InetSocketAddress recipient)
		DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender)

		* data 数据
		* recipient 接收方信息
		* sender 发送方信息
	
	# 方法
		DatagramPacket copy()
		DatagramPacket duplicate()
		DatagramPacket retainedDuplicate()
		DatagramPacket replace(ByteBuf content)
		DatagramPacket retain()
		DatagramPacket retain(int increment)
		DatagramPacket touch()
		DatagramPacket touch(Object hint)

-------------------------------
DatagramPacketDecoder			|
-------------------------------
	# UDP消息的解码器
		DatagramPacketDecoder(MessageToMessageDecoder<ByteBuf> decoder)
		

-------------------------------
DatagramPacketEncoder			|
-------------------------------
	# UDP消息的编码器
		DatagramPacketEncoder(MessageToMessageEncoder<? super M> encoder)
	

-------------------------------
UDP服务器						|
-------------------------------
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class Server {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioDatagramChannel.class);
			bootstrap.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					
					ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
							
							// 收到的消息
							ByteBuf byteBuf = msg.content();
							
							// 发送方的信息
							InetSocketAddress inetSocketAddress = msg.sender();
							
							System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
							System.out.println(inetSocketAddress);
							
							// 响应客户端
							DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer("我已经收到了",StandardCharsets.UTF_8), inetSocketAddress);
							ctx.writeAndFlush(datagramPacket);
						}
					});
				}
			});
			
			// 绑定监听 
			ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(1024)).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}

-------------------------------
UDP客户端						|
-------------------------------
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class Clien {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup);
			bootstrap.channel(NioDatagramChannel.class);
			bootstrap.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
							// 收到的消息
							ByteBuf byteBuf = msg.content();
							
							// 发送方的信息
							InetSocketAddress inetSocketAddress = msg.sender();
							
							System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
							System.out.println(inetSocketAddress);
						}
					});
				}
			});
			
			// 绑定本地端口(如果设置为0则是随机端口)
			ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(0)).sync();
			
			// 发送数据
			DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer("Hello World!", StandardCharsets.UTF_8), new InetSocketAddress("127.0.0.1", 1024));
			channelFuture.channel().writeAndFlush(datagramPacket);
			
			channelFuture.channel().closeFuture().sync();
		}finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
