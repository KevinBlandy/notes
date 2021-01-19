-------------------------------------
简单的打开摄像头,播放视频			 |
-------------------------------------
package io.javaweb.community.test;
import javax.swing.JFrame;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
/**
 * 读取视频信息在屏幕播放
 * @author KevinBlandy
 *
 */
public class VedioTest {
	
	public static void main(String[] args) throws Exception, InterruptedException {
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		grabber.start(); // 开始获取摄像头数据
		CanvasFrame canvas = new CanvasFrame("摄像头");// 新建一个窗口
		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setAlwaysOnTop(true);
		while (true) {
			if (!canvas.isDisplayable()) {// 窗口是否关闭
				grabber.stop();// 停止抓取
				System.exit(2);// 退出
			}
			canvas.showImage(grabber.grab());	// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
			Thread.sleep(50);					// 50毫秒刷新一次图像
		}
	}
}

-------------------------------------
简单的打开摄像头,视频保存本地		 |
-------------------------------------
/**
 * 按帧录制本机摄像头视频(边预览边录制,停止预览即停止录制)
 * 
 * @param outputFile
 *            -录制的文件路径，也可以是rtsp或者rtmp等流媒体服务器发布地址
 * @param frameRate
 *            - 视频帧率
 * @throws Exception
 * @throws InterruptedException
 * @throws org.bytedeco.javacv.FrameRecorder.Exception
 */
public static void recordCamera(String outputFile, double frameRate)
		throws Exception, InterruptedException, org.bytedeco.javacv.FrameRecorder.Exception {
	Loader.load(opencv_objdetect.class);
	FrameGrabber grabber = FrameGrabber.createDefault(0);// 本机摄像头默认0，这里使用javacv的抓取器，至于使用的是ffmpeg还是opencv，请自行查看源码
	grabber.start();// 开启抓取器
	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();// 转换器
	IplImage grabbedImage = converter.convert(grabber.grab());// 抓取一帧视频并将其转换为图像，至于用这个图像用来做什么？加水印，人脸识别等等自行添加
	int width = grabbedImage.width();
	int height = grabbedImage.height();

	FrameRecorder recorder = FrameRecorder.createDefault(outputFile, width, height);
	recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
	recorder.setFormat("flv");// 封装格式，如果是推送到rtmp就必须是flv封装格式
	recorder.setFrameRate(frameRate);

	recorder.start();// 开启录制器
	long startTime = 0;
	long videoTS = 0;
	CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setAlwaysOnTop(true);
	Frame rotatedFrame = converter.convert(grabbedImage);// 不知道为什么这里不做转换就不能推到rtmp
	while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
		rotatedFrame = converter.convert(grabbedImage);
		frame.showImage(rotatedFrame);
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
		videoTS = 1000 * (System.currentTimeMillis() - startTime);
		recorder.setTimestamp(videoTS);
		recorder.record(rotatedFrame);
		Thread.sleep(40);
	}
	frame.dispose();
	recorder.stop();
	recorder.release();
	grabber.stop();
}

-------------------------------------
把视频数据推送出去					 |
-------------------------------------
/**
 * 按帧录制视频
 * inputFile设置为服务器播放地址,outputFile设置为本地地址
 * 
 * @param inputFile-该地址可以是网络直播/录播地址,也可以是远程/本地文件路径
 * @param outputFile
 *            -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式
 * @throws FrameGrabber.Exception
 * @throws FrameRecorder.Exception
 * @throws org.bytedeco.javacv.FrameRecorder.Exception
 */
public static void frameRecord(String inputFile, String outputFile, int audioChannel)
		throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {

	boolean isStart = true;// 该变量建议设置为全局控制变量，用于控制录制结束
	// 获取视频源
	FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
	// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
	FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
	// 开始取视频源
	recordByFrame(grabber, recorder, isStart);
}

private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
	try {// 建议在线程中使用该方法
		grabber.start();
		recorder.start();
		Frame frame = null;
		while (status && (frame = grabber.grabFrame()) != null) {
			recorder.record(frame);
		}
		recorder.stop();
		grabber.stop();
	} finally {
		if (grabber != null) {
			grabber.stop();
		}
	}
}