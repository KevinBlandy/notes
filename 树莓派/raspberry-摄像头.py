------------------------
sci摄像头				|
------------------------
	# 摄像头命令软件
		raspistill [options] 
		* 文档
			https://www.raspberrypi.org/app/uploads/2013/07/RaspiCam-Documentation.pdf

	# 图像参数与命令
		-?,  --help             : 帮助文档
		-w,  --width            : 设置图像宽度 <尺寸>
		-h, --height            : 设置图像高度 <尺寸>
		-q, --quality           : 设置jpeg品质 <0到100>
		-r, --raw               : 增加raw原始拜尔数据到JPEG元数据
		-o, --output            : 输出文件名 <文件名>，如果要写到stdout，使用`-o -`，如果不特别指定，图像文件不会被保存
		-l, --latest            : 链接最近的完整图像到指定文件 <文件名>
		-v, --verbose           : 在运行摄像头时输出详细信息
		-t, --timeout           : 拍照和关闭时的延时指定，未指定时默认是5s
		-th, --thumb            : 设置预览图（小图）参数（包括X、Y、品质）或者不设置
		-d, --demo              : 运行一个demo模式（cycle through range of camera options, no capture），括号里的我可以理解成循环测试模式吗？
		-e, --encoding          : 编码来输出指定格式文件 （jpg, bmp, gif, png）
		-x, --exif              :将可交换图像文件应用到捕获中（格式：`key=value`）或者不设置
		-tl, --timelapse        : 间隔拍摄，每<ms>拍摄一张图片
		-fp, --fullpreview      : 用静态捕捉分辨率运行预览（可能会减小预览帧率）
		-k, --keypress          : 按键触发，按`ENTER`键拍照，按`X`然后`ENTER`键退出
		-s, -signal             : 信号触发，等待另一个进程信号来拍摄一张照片
		-g, -gl                 : 绘制预览到文本，而不是使用视频渲染组件（啥意思？）
		-gc, --glcapture        : 捕获GL帧buffer而不是摄像机图像
		-set, --settings        : 检索摄像机设置值然后写出到stdout
		-cs, --camselect        : 选择摄像头设备 <数字>，默认0
		-bm, --burst            : 运行burst capture mode
	
	# 预览参数与命令
		-p, --preview           : 预览窗口大小设置 <x,y,w,h>
		-f, --fullscreen        : 全屏幕预览模式
		-op, --opacity          : 预览窗口透明度 （0-255）
		-n, --nopreview         : 不显示预览窗口

	# 预览参数与命令
		-gs, -glscene           : GL屏幕尺寸等参数
		-gw, -glwin             : GL窗口参数
	
	# 图像参数与命令2
		-sh, --sharpness        : 设置图像锐度 （-100 到 100）
		-co, --contrast         : 设置图像对比度 （-100 到 100）
		-br, --brightness       : 设置图像亮度 （0 到 100）
		-sa, --saturation       : 设置图像饱和度 （-100 到 100）
		-ISO, --ISO             : 设置摄像头感光度
		-vs, --vstab            : Turn on video stabilisation 开启摄像头防抖模式（是这个意思吗？）
		-ev, --ev               : 设置EV补偿
		-ex, --exposure         : 设置曝光模式（参见提示）
		-awb, --awb             : 设置AWB模式（参见提示）
		-ifx, --imxfx           : 设置图像效应（参见提示）
		-cfx, --colfx           : 设置色彩效应（参见提示）
		-mm, --metering         : 设置测光模式（参见提示）
		-rot, --rotation        : 设置图像旋转 （0到359）
		-hf, --hflip            : 设置水平翻转 horizontal flip
		-vf, --vflip            : 设置垂直翻转 vertical flip
		-roi, --roi             : 设置interest区域 （啥叫interset?）
		--ss, --shutter         : 设置快门速度，微秒
		--awbg, --awbgains      : 设置AWB阈值， AWB模式必须关闭
		--drc, --drc            : 设置DRC水平

	# 提示
		曝光模式选项
		auto                    : 自动曝光模式
		night                   : 夜间拍摄模式
		nightpreview            : 夜间预览拍摄模式
		backlight               : 逆光拍摄模式
		spotlight               : 聚光灯拍摄模式
		sports                  : 运动拍摄模式
		snow                    : 雪景拍摄模式
		beach                   : 海滩拍摄模式
		verylong                : 长时间曝光拍摄模式
		fixedfps                : 帧约束拍摄模式
		antishake               : 防抖模式
		fireworks               : 烟火拍摄模式

		自动白平衡选项
		off                     : 关闭白平衡测算
		auto                    : 自动模式（默认）
		sun                     : 日光模式
		cloud                   : 多云模式
		shade                   : 阴影模式
		tungsten                : 钨灯模式
		fluorescent             : 荧光灯模式
		incandescent            : 白炽灯模式
		flash                   : 闪光模式
		horizon                 : 地平线模式

		图像特效选项
		none                    : 无特效（默认）
		negative                : 反色图像
		solarise                : 曝光过度图像
		posterize               : 色调图像
		whiteboard              : 白板特效
		blackboard              : 黑板特效
		sketch                  : 素描风格特效
		denoise                 : 降噪图像
		emboss                  : 浮雕图像
		oilpaint                : 油画风格特效
		hatch                   : 草图特效
		gpen                    : 马克笔特效
		pastel                  : 柔化风格特效
		watercolour             : 水彩风格特效
		film                    : 胶片颗粒风格特效
		blur                    : 模糊图像
		saturation              : 色彩饱和图像
		colourswap              : 暂未可用
		washedout               : 暂未可用
		posterise               : 暂未可用
		colourpoint             : 暂未可用
		colourbalance           : 暂未可用
		cartoon                 : 暂未可用

		测光模式选项
		average                 : 全画面平衡测光
		spot                    : 点测光
		backlit                 : 模拟背光图像
		matrix                  : 阵列测光

	# 常用命令:
		 1 常用命令：
		 2 # 两秒钟（时间单位为毫秒）延迟后拍摄一张照片，并保存为 image.jpg
		 3 raspistill -t 2000 -o image.jpg
		 4 
		 5 # 拍摄一张自定义大小的照片。
		 6 raspistill -t 2000 -o image.jpg -w 640 -h 480
		 7 
		 8 # 降低图像质量，减小文件尺寸
		 9 raspistill -t 2000 -o image.jpg -q 5
		10 
		11 # 强制使预览窗口出现在坐标为 100,100 的位置，并且尺寸为宽 300 和高 200 像素。
		12 raspistill -t 2000 -o image.jpg -p 100,100,300,200
		13 
		14 # 禁用预览窗口
		15 raspistill -t 2000 -o image.jpg -n
		16 
		17 # 将图像保存为 PNG 文件（无损压缩格式，但是要比 JPEG 速度慢）。注意，当选择图像编码时，文件扩展名将被忽略。
		18 raspistill -t 2000 -o image.png –e png
		19 
		20 # 向 JPEG 文件中添加一些 EXIF 信息。该命令将会把作者名称标签设置为 Dreamcolor，GPS 海拔高度为 123.5米。
		21 raspistill -t 2000 -o image.jpg -x IFD0.Artist=Dreamcolor -x GPS.GPSAltitude=1235/10
		22 
		23 # 设置浮雕风格图像特效
		24 raspistill -t 2000 -o image.jpg -ifx emboss
		25 
		26 # 设置 YUV 图像的 U 和 V 通道为指定的值（128:128 为黑白图像）
		27 raspistill -t 2000 -o image.jpg -cfx 128:128
		28 
		29 # 仅显示两秒钟预览图像，而不对图像进行保存。
		30 raspistill -t 2000
		31 
		32 # 间隔获取图片，在 10 分钟（10 分钟 = 600000 毫秒）的时间里，每 10 秒获取一张，并且命名为 image_number_1_today.jpg，image_number_2_today.jpg... 的形式。
		33 raspistill -t 600000 -tl 10000 -o image_num_%d_today.jpg
		34 
		35 # 获取一张照片并发送至标准输出设备
		36 raspistill -t 2000 -o -
		37 
		38 # 获取一张照片并保存为一个文件
		39 raspistill -t 2000 -o - > my_file.jpg

