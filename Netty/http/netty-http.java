------------------------------------
http								|
------------------------------------
	# ¿‡ø‚
		HttpServerExpectContinueHandler
		HttpObjectDecoder
			|-HttpRequestDecoder
			|-HttpResponseDecoder
			|-RtspDecoder
		HttpServerCodec
		HttpContentCompressor
		HttpUtil
		HttpObjectAggregator
			|-HttpServerUpgradeHandler
		FullHttpRequest
			|-DefaultFullHttpRequest
		FullHttpResponse
			|-DefaultFullHttpResponse


------------------------------------
http - server
------------------------------------
