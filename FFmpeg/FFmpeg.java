---------------------
FFmpeg
---------------------
	# FFmpeg
		* C���Ա�д������Ƶ�������

	# FFmpeg ����
		http://ffmpeg.org/
		https://github.com/FFmpeg/FFmpeg
	
	# �ĵ�
		https://www.quarkbook.com/wp-content/uploads/2019/10/ffmpeg%E7%BF%BB%E8%AF%91%E6%96%87%E6%A1%A3.pdf
		https://github.com/xdsnet/other-doc-cn-ffmpeg
		http://qt.digitser.net/ffmpeg/4.2.2/zh-CN/index.html

		https://www.bookstack.cn/read/other-doc-cn-ffmpeg/README.md

		https://github.com/0voice/ffmpeg_develop_doc
	
	# ����Ƶ����
		https://www.bilibili.com/video/av54248249/
		
	# Golang
		https://github.com/giorgisio/goav
		https://github.com/xfrr/goffmpeg
		https://github.com/nareix/joy4
		https://github.com/nareix/joy5
	
	# �汾����
		Static
			* ÿ����exe��������ܴ���ص�Dll�Ѿ������뵽exe����ȥ��
		Shared	
			* ����Ӧ�ó����⻹��һЩDll������˵avcodec-54.dll֮���
			* Shared�����exe�����С�����������е�ʱ�򣬵���Ӧ��Dll�е��ù���
		Dev
			* ���ڿ����ģ���������˿��ļ�xxx.lib�Լ�ͷ�ļ�xxx.h������汾������exe�ļ�
	
	# ����
		ffmpeg.exe
			* ����ת���Ӧ�ó���
	
		ffplay.exe
			* ffplay�����ڲ��ŵ�Ӧ��
	
		ffprobe.exe
			* ���ڲ鿴�ļ���ʽ��Ӧ�ó���
		
	
	# ģ�����
		AVFormat
		AVCodec
		AVFilter
		AVDevice
		AVUtil
		swresample
		swscale

---------------------------
FFmpeg - ����Ƶ��������
---------------------------
	1. �����ļ� (demuxer)
	2. �������ݰ� (decoer)
	3. ���������֡ 
	4. �������ݰ� (encoder)
	5. ����ļ� (muxer)


---------------------------
FFmpeg - ����
---------------------------
	# �����Ƶ�ļ��ϲ�
		ffmpeg -f concat -i filelist.txt -c copy file.mp4

		filelist.txt �ļ�����
			file '1.mp4'
			file '.mp4'
	
	# ��Ļ�ϲ�
		ffmpeg -i my.mkv -vf subtitles=my.ass my2.mkv
		