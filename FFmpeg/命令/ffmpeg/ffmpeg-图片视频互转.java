------------------------
图片视频互转
------------------------
	# 视频转换为图片
		ffmpeg -i demo.flv -r 1 -f image2 image-%3d.jpeg
	
	# 图片转换为视频
		ffmpeg -i image-%3d.jpeg out.mp4
	
