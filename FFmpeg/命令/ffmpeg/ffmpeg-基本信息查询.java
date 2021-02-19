----------------------
基本信息查询
----------------------
	-version
		* 显示版本

		ffmpeg version 4.2.2 Copyright (c) 2000-2019 the FFmpeg developers
		built with gcc 9.2.1 (GCC) 20200122 
			* 编译使用的gcc版本
		configuration: --enable-gpl --enable-version3 --enable-sdl2 --enable-fontconfig --enable-gnutls --enable-iconv --enable-libass --enable-libdav1d --enable-libbluray --enable-libfreetype --enable-libmp3lame --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libopenjpeg --enable-libopus --enable-libshine --enable-libsnappy --enable-libsoxr --enable-libtheora --enable-libtwolame --enable-libvpx --enable-libwavpack --enable-libwebp --enable-libx264 --enable-libx265 --enable-libxml2 --enable-libzimg --enable-lzma --enable-zlib --enable-gmp --enable-libvidstab --enable-libvorbis --enable-libvo-amrwbenc --enable-libmysofa --enable-libspeex --enable-libxvid --enable-libaom --enable-libmfx --enable-amf --enable-ffnvcodec --enable-cuvid --enable-d3d11va --enable-nvenc --enable-nvdec --enable-dxva2 --enable-avisynth --enable-libopenmpt
			* 编译参数
		
		libavutil      56. 31.100 / 56. 31.100
		libavcodec     58. 54.100 / 58. 54.100
		libavformat    58. 29.100 / 58. 29.100
		libavdevice    58.  8.100 / 58.  8.100
		libavfilter     7. 57.100 /  7. 57.100
		libswscale      5.  5.100 /  5.  5.100
		libswresample   3.  5.100 /  3.  5.100
		libpostproc    55.  5.100 / 55.  5.100
	
	-demuxers 
		* 显示可用的 demuxers
	
	-muxers
		* 显示可用的 muxers
	
	-devices
		* 显示可用的设备
		Devices:
		 D. = Demuxing supported
		 .E = Muxing supported
		 --
		 D  dshow           DirectShow capture
		 D  gdigrab         GDI API Windows frame grabber
		 D  lavfi           Libavfilter virtual input device
		  E sdl,sdl2        SDL2 output device
		 D  vfwcap          VfW video capture
	
	-codecs
		* 显示可用的编解码器
		Codecs:
		 D..... = Decoding supported			// 解码器
		 .E.... = Encoding supported			// 编码器
		 ..V... = Video codec					// 视频编解码器
		 ..A... = Audio codec					// 音频编解码器
		 ..S... = Subtitle codec				// 字幕
		 ...I.. = Intra frame-only codec		// 帧类编解码器
		 ....L. = Lossy compression				// 有损压缩
		 .....S = Lossless compression			// 无损压缩
		-------
	
	-decoders
		* 显示可用的解码器
	
	-encoders
		* 显示可用的编码器
	
	-bsfs
		* 显示比特流filter
	
	-formats
		* 显示可用的格式
		File formats:
		 D. = Demuxing supported		// 解封装格式
		 .E = Muxing supported			// 封装格式
		 --
	
	-protocols
		* 显示可用的协议
	
	-filters
		* 显示可用的过滤器
		Filters:
		  T.. = Timeline support
		  .S. = Slice threading
		  ..C = Command support
		  A = Audio input/output
		  V = Video input/output
		  N = Dynamic number and/or type of input/output
		  | = Source or sink filter
	
	-pix_fmts
		* 显示可用的像素格式
	
	-sample_fmts
		* 显示可用的采样格式
	
	-layouts
		* 显示channel名称
	
	-colors
		* 显示识别的颜色名称
	

	
	
