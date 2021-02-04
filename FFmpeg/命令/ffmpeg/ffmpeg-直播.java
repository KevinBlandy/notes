----------------------
直播
----------------------
	# 推流
		ffmpeg -re -i out.mp4 -c copy -f flv rtmp://host/live/streamName
	
	# 拉流
		ffmpeg -i rtmp://host/live/streamName -c copy dump.flv
	