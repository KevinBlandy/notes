-------------------------------
Video							|
-------------------------------
	# 标签
		<video>
	# 子标签
		<source>
		<track>

	# 属性
		autoplay
			* 如果出现该属性，则视频在就绪后马上播放
			* 值:autoplay

		controls
			* 如果出现该属性，则向用户显示控件，比如播放按钮。
			* 值:controls

		height
			* 设置视频播放器的高度。
		
		width
			* 设置播放器的宽度

		loop
			* 如果出现该属性，则当媒介文件完成播放后再次开始播放。
			* 循环嘛
			* 值:loop
		
		muted
			* 如果出现该属性,就禁音
		
		poster
			* 指定视频正在下载时显示的图像，直到用户点击播放按钮。
			* 值:URL
		
		preload
			* 出现该属性，则视频在页面加载时进行加载，并预备播放。如果使用 "autoplay"，则忽略该属性。
			* 值:auto,metadata,none
		src
			* 视频地址
			* 值:URL

		autobuffer
			* 设置为浏览器缓冲方式，不设置autoply才有效
		
		paused
			* 是否是暂停状态

	# 方法
		play();
			* 开始播放
		pause();
			* 暂停
		load();
			* 将全部属性回复默认值，视频恢复重新开始状态
		canPlayType();
			* 判断浏览器是否支持指定的类型的视频格式
			* 返回值
				空字符串:不支持
				Maybe	:可能支持
				Probably:完全支持

	# 事件
		
