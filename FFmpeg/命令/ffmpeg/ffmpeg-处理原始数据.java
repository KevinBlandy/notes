-------------------
处理原始数据
-------------------
	# 提取yuv数据
		ffmpeg -i demo.mp4 -an -c:v rawvideo -pix_fmt yuv420p out.yuv
	
	# 提取pcm数据
		ffmepg -i demo.mp4 -ar 44100 -ac2 -f s16le -vn out.pcm
	


