--------------------------------
消息压缩						|
--------------------------------
	# 设置一个zip的压缩编码和解码器
		pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
		pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
	
	# 支持的压缩类型
		ZLIB
		GZIP
		NONE
		ZLIB_OR_NONE

--------------------------------
客户端							|
--------------------------------


--------------------------------
服务端							|
--------------------------------
