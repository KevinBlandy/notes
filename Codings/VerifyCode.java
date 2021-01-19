package com.kevinblandy.simple.webchat.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by KevinBlandy on 2017/1/10 14:29 验证码生成类
 */
public class VerifyCode {
	private int w = 70;
	private int h = 30;
	private Random r = new Random();
	private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
	private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNOPQRSTUVWXYZ";
	private Color bgColor = new Color(255, 255, 255);
	private String text;

	public VerifyCode() {
	}

	/**
	 * 构造初始化图片宽高 默认 30 x 70
	 * 
	 * @param width
	 * @param height
	 */
	public VerifyCode(int width, int height) {
		if (width <= 0 || width > 100) {
			width = 70;
		}
		if (height <= 0 || height > 100) {
			height = 30;
		}
		this.w = width;
		this.h = height;
	}

	private Color randomColor() {
		int red = r.nextInt(150);
		int green = r.nextInt(150);
		int blue = r.nextInt(150);
		return new Color(red, green, blue);
	}

	private Font randomFont() {
		int index = r.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int style = r.nextInt(4);
		int size = r.nextInt(4) + 24;
		return new Font(fontName, style, size);
	}

	private void drawLine(BufferedImage image) {
		int num = 3;
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		for (int x = 0; x < num; x++) {
			int x1 = r.nextInt(w);
			int y1 = r.nextInt(h);
			int x2 = r.nextInt(w);
			int y2 = r.nextInt(h);
			g2.setStroke(new BasicStroke(1.5F));
			g2.setColor(Color.BLUE);
			g2.drawLine(x1, y1, x2, y2);
		}
	}

	private char randomChar() {
		int index = r.nextInt(codes.length());
		return codes.charAt(index);
	}

	private BufferedImage createImage() {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(this.bgColor);
		g2.fillRect(0, 0, w, h);
		return image;
	}

	public BufferedImage getImage() {
		BufferedImage image = createImage();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < 4; x++) {
			String s = randomChar() + "";
			sb.append(s);
			float f = x * 1.0F * w / 4;
			g2.setFont(randomFont());
			g2.setColor(randomColor());
			g2.drawString(s, f, h - 5);
		}
		this.text = sb.toString();
		drawLine(image);
		return image;
	}

	public String getText() {
		return text;
	}

	public static void output(BufferedImage image, OutputStream out) throws IOException {
		ImageIO.write(image, "JPEG", out);
	}
}
