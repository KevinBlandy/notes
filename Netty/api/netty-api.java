	
--------------------
handler体系			|
--------------------
ChannelHandler
	* 最顶层hanlder

	|-ChannelHandlerAdapter
	|-ChannelInboundHandler
		* 读处理接口

		|-ChannelInboundHandlerAdapter
			|-ByteToMessageDecoder
				|-ReplayingDecoder<S> 
				|-LineBasedFrameDecoder
				|-LengthFieldBasedFrameDecoder
				|-DelimiterBasedFrameDecoder
				|-FixedLengthFrameDecoder
				|-SslHandler
					* 实现了 ChannelOutboundHandler
			|-MessageToMessageDecoder
				|-StringDecoder
			|-ChannelInitializer
			|-SimpleChannelInboundHandler<I>
				* 实现类型的强制转换,并且自动释放buf

	|-ChannelOutboundHandler
		* 写处理接口

		|-ChannelOutboundHandlerAdapter
			|-MessageToByteEncoder<I>
			|-MessageToMessageEncoder<I>
				|-LengthFieldPrepender
				|-StringEncoder
	
	|-ChannelDuplexHandler
		* 实现了读写接口
		* 继承:ChannelInboundHandlerAdapter,实现:ChannelOutboundHandler

		|-ChunkedWriteHandler
		|-CombinedChannelDuplexHandler

			
--------------------
buf体系				|
--------------------
ReferenceCounted
	|-ByteBuf
		|-CompositeByteBuf
	|-ByteBufHolder
	|-FileRegion
		|-DefaultFileRegion
ByteBufProcessor
ByteBufAllocator
Unpooled
ByteBufUtil


--------------------
其他				|
--------------------
ChunkedInput
	|-ChunkedFile
	|-ChunkedNioFile
	|-ChunkedStream
	|-ChunkedNioStream	
