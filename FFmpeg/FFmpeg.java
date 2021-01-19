---------------------
FFmpeg
---------------------
	# FFmpeg
		* C语言编写的音视频处理软件

	# FFmpeg 官网
		http://ffmpeg.org/
		https://github.com/FFmpeg/FFmpeg
	
	# 文档
		https://www.quarkbook.com/wp-content/uploads/2019/10/ffmpeg%E7%BF%BB%E8%AF%91%E6%96%87%E6%A1%A3.pdf
		https://github.com/xdsnet/other-doc-cn-ffmpeg
		http://qt.digitser.net/ffmpeg/4.2.2/zh-CN/index.html

		https://www.bookstack.cn/read/other-doc-cn-ffmpeg/README.md
	
	# 音视频入门
		https://www.bilibili.com/video/av54248249/
		
	# Golang
		https://github.com/giorgisio/goav
		https://github.com/xfrr/goffmpeg
		https://github.com/nareix/joy4
		https://github.com/nareix/joy5
	
	# 版本区别
		Static
			* 每个个exe的体积都很大，相关的Dll已经被编译到exe里面去了
		Shared	
			* 出了应用程序外还有一些Dll，比如说avcodec-54.dll之类的
			* Shared里面的exe体积很小，他们在运行的时候，到相应的Dll中调用功能
		Dev
			* 用于开发的，里面包含了库文件xxx.lib以及头文件xxx.h，这个版本不包含exe文件
	
	# 程序
		ffmpeg.exe
			* 用于转码的应用程序
	
		ffplay.exe
			* ffplay是用于播放的应用
	
		ffprobe.exe
			* 用于查看文件格式的应用程序

---------------------------
FFmpeg - 音视频处理流程
---------------------------
	1. 输入文件 (demuxer)
	2. 编码数据包 (decoer)
	3. 解码后数据帧 
	4. 编码数据包 (encoder)
	5. 输出文件 (muxer)
