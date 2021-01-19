-----------------
裁剪合并
-----------------
	# 裁剪开头10秒
		ffmpeg -i in.mp4 -ss 00:00:00 -t 10 out.ts
	
	# 拼接
		ffmpeg -f concat -i inputs.txt out.flv
		
		inputs.txt
			* 是一个文件列表，一行一个文件，以file开头
				file '1.ts'
				file '2.tx'
	

