package io.springcloud.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

import javax.imageio.ImageIO;

public class AvatarUtils {

	/**
	 * 根据id生成用户头像
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static byte[] create(int id) throws IOException {
		// 每个小块块的大小
		int width = 20;
		// 5 x 5
		int grid = 5;
		// 四边填充
		int padding = width / 2;
		int size = width * grid + width;
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D _2d = img.createGraphics();
		_2d.setColor(new Color(240, 240, 240));
		_2d.fillRect(0, 0, size, size);
		_2d.setColor(randomColor(80, 200));
		char[] idchars = createIdent(id);
		int i = idchars.length;
		for (int x = 0; x < Math.ceil(grid / 2.0); x++) {
			for (int y = 0; y < grid; y++) {
				if (idchars[--i] < 53) {
					_2d.fillRect((padding + x * width), (padding + y * width), width, width);
					if (x < Math.floor(grid / 2)) {
						_2d.fillRect((padding + ((grid - 1) - x) * width), (padding + y * width), width, width);
					}
				}
			}
		}
		_2d.dispose();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(img, "png", byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	private static Color randomColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(Math.abs(bc - fc));
		int g = fc + random.nextInt(Math.abs(bc - fc));
		int b = fc + random.nextInt(Math.abs(bc - fc));
		return new Color(r, g, b);
	}

	private static char[] createIdent(int id) {
		BigInteger bi_content = new BigInteger((id + "").getBytes());
		BigInteger bi = new BigInteger(id + "identicon" + id, 36);
		bi = bi.xor(bi_content);
		return bi.toString(10).toCharArray();
	}
}


