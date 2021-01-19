
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 验证码的生成
 * 需要用到的类
 * Image ImageIO BufferedImage Icon ImageIncon
 * */
public class Demo
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		//得到图片缓冲区(宽度,高度，图片形式)
		BufferedImage bi = new BufferedImage(150,70,BufferedImage.TYPE_INT_BGR);
		//得到绘制环境(得到这图片的笔)
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		//设置绘制环境颜色(白色)
		g2.setColor(Color.WHITE);
		//设置坐标和大小(填充了整张图片,其实就是设置背景色)
		g2.fillRect(0, 0, 150,70);
		g2.setColor(Color.RED);
		g2.drawRect(0, 0, 150-1, 70-1);
		//设置字体格式，大小！
		g2.setFont(new Font("宋体",Font.BOLD,18));
		//设置颜色
		g2.setColor(Color.BLACK);
		//向图片上写字符串！
		g2.drawString("KevinBlandy",3,40);
		//保存图片--imagerIo
		ImageIO.write(bi, "JPEG", new FileOutputStream("D:/a.jpg"));
	}
}