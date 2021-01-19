import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
/**
 * 生成验证码图片
 * @author KevinBlandy
 * */
public class VerifyCode
{
	//宽
	private int width = 120;
	//高
	private int height = 30;
	//图片
	private BufferedImage bufferedImage;
	//文本信息
	private StringBuffer sb;
	//图片格式
	private String imageType = "JPEG";
	public VerifyCode()
	{
		createImage();
	}
	/**
	 * 设置图片宽高,默认高度:30像素,宽度:120像素(不建议使用)
	 * */
	public VerifyCode(int width,int height)
	{
		this.width = width;
		this.height = height;
		createImage();
	}
	/**
	 * 画出图片
	 * */
	private void createImage()
	{
		// 初始化内存中的图片
		bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		//初始化文本
		sb = new StringBuffer();
		// 步骤二 图片绘制背景颜色 ---通过绘图对象
		Graphics graphics = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
		// 绘制任何图形之前 都必须指定一个颜色
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		// 步骤三 绘制边框
		graphics.setColor(Color.WHITE);
		graphics.drawRect(0, 0, width - 1, height - 1);
		// 步骤四 四个随机数字
		Graphics2D graphics2d = (Graphics2D) graphics;
		// 设置输出字体
		graphics2d.setFont(new Font("宋体", Font.BOLD, 18));
		String words ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		Random random = new Random();// 生成随机数
		// 定义x坐标
		int x = 10;
		for (int i = 0; i < 4; i++)
		{
			// 随机颜色
			graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			// 旋转 -30 --- 30度
			int jiaodu = random.nextInt(60) - 30;
			// 换算弧度
			double theta = jiaodu * Math.PI / 180;
			// 生成一个随机数字
			int index = random.nextInt(words.length()); // 生成随机数 0 到 length - 1
			// 获得字母数字
			char c = words.charAt(index);	
			sb.append(c);
			// 将c 输出到图片
			graphics2d.rotate(theta, x, 20);
			graphics2d.drawString(String.valueOf(c), x, 20);
			graphics2d.rotate(-theta, x, 20);
			x += 30;
		}
		// 步骤五 绘制干扰线
		graphics.setColor(getRandColor(160, 200));
		int x1;
		int x2;
		int y1;
		int y2;
		for (int i = 0; i < 30; i++)
		{
			x1 = random.nextInt(width);
			x2 = random.nextInt(12);
			y1 = random.nextInt(height);
			y2 = random.nextInt(12);
			graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
		}
		// 将上面图片输出到浏览器 ImageIO
		graphics.dispose();// 释放资源
	}
	/**
	 * 获取随机颜色
	 * */
	private  Color getRandColor(int fc, int bc)
	{
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	/**
	 * 把图片保存到指定的输出流
	 * */
	public void save(OutputStream out) throws IOException
	{
		ImageIO.write(bufferedImage,imageType,out);
	}
	/**
	 * 获取验证码文本信息
	 * */
	public String getText()
	{
		return sb.toString();
	}
	/**
	 * 获取验证码图片对象
	 * */
	public BufferedImage getImage()
	{
		return bufferedImage;
	}
	/**
	 * 刷新一次
	 * */
	public void refreh()
	{
		createImage();
	}
}
