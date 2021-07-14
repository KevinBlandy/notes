--------------------------
Codec
--------------------------
	# Http消息的编解码
		HttpMessageReader
			DecoderHttpMessageReader
		
		HttpMessageWriter
			EncoderHttpMessageWriter
	
	# 系统的编解码器配置接口
		ServerCodecConfigurer
			
			DefaultCodecs defaultCodecs();
			CustomCodecs customCodecs();
			void registerDefaults(boolean registerDefaults);
			List<HttpMessageReader<?>> getReaders();
			List<HttpMessageWriter<?>> getWriters();
			CodecConfigurer clone();

			
			ServerDefaultCodecs defaultCodecs();
			ServerCodecConfigurer clone();

