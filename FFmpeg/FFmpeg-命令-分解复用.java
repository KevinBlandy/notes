--------------------
分解复用
--------------------
	# 格式转换
		ffmpeg -i out.mp4 -vcodec copy -acodec copy out.flv
	
	# 抽取视频，忽略音频
		ffmpge -i f35.mov -vcodec copy -an out.h264
	
	# 抽取音频
		ffmpeg -i f35.mov -vn -acodec copy out.aac