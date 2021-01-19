----------------------------------
Netty断线重连					  |
----------------------------------

----------------------------------
Netty Client启动的时候需要重连	  |
----------------------------------
	# Netty作者在stackoverflow的回答
		https://stackoverflow.com/questions/19739054/whats-the-best-way-to-reconnect-after-connection-closed-in-netty

		private void doConnect() {
			Bootstrap b = ...;
			b.connect().addListener((ChannelFuture f) -> {
				if (!f.isSuccess()) {
					long nextRetryDelay = nextRetryDelay(...);
					// 连接失败,在指定延迟后重试连接
					f.channel().eventLoop().schedule(nextRetryDelay, ..., () -> {
						doConnect();
					}); // 也可以放弃连接
				}
			});
		}

----------------------------------
在程序运行中连接断掉需要重连	  |
----------------------------------
	# 参考
		https://github.com/netty/netty/blob/master/example/src/main/java/io/netty/example/uptime/UptimeClientHandler.java
	


----------------------------------
Client							  |
----------------------------------
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Client {

    private final EventLoopGroup loop;

    private final InetSocketAddress inetSocketAddress;

    public Client(EventLoopGroup loop,InetSocketAddress inetSocketAddress) {
        this.loop = loop;
        this.inetSocketAddress = inetSocketAddress;
    }

    static class ConnectionListener implements ChannelFutureListener {
        private Client client;
        public ConnectionListener(Client client) {
            this.client = client;
        }
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                // 连接失败
                final EventLoop loop = channelFuture.channel().eventLoop();
                loop.schedule(new Runnable() {
                    @Override
                    public void run() {
                        // 在1s后尝试重新连接
                        client.createBootstrap(new Bootstrap(), loop);
                    }
                }, 1L, TimeUnit.SECONDS);
            }
        }
    }

    private Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
        if (bootstrap != null) {

            bootstrap.group(this.loop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(this.inetSocketAddress);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {

                    // 添加Handler处理在程序运行中连接断掉需要重连
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            final EventLoop eventLoop = ctx.channel().eventLoop();
                            eventLoop.schedule(new Runnable() {
                                @Override
                                public void run() {
                                    // 重试连接
                                    Client.this.createBootstrap(new Bootstrap(), eventLoop);
                                }
                            }, 1L, TimeUnit.SECONDS);
                            super.channelInactive(ctx);
                        }
                    });
                }
            });

            // 在连接的时候添加监听,处理启动的时候需要重连
            bootstrap.connect().addListener(new GenericFutureListener<ChannelFuture>() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        // 连接失败
                        final EventLoop loop = future.channel().eventLoop();
                        loop.schedule(new Runnable() {
                            @Override
                            public void run() {
                                // 在1s后尝试重新连接
                                Client.this.createBootstrap(new Bootstrap(), loop);
                            }
                        }, 1L, TimeUnit.SECONDS);
                    }
                }
            });
        }
        return bootstrap;
    }
    // 启动客户端
    public void run() {
        createBootstrap(new Bootstrap(), loop);
    }
}