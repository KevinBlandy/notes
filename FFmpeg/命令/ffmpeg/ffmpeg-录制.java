----------------
录制
----------------
	ffmpeg -f avfoundation -i 1 -r 30 out.yuv

	-f 指定采集数据的库，不同操作系统下不一样
		mac: avfoundation
		linux:
		windows
	
	-i 输入设备
		1 表示屏幕的索引值

	-r 帧率

	out.yuv 输出格式

		