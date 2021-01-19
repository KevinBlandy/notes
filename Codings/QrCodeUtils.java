


/**
需要依赖
<dependency>
	<groupId>com.google.zxing</groupId>
	<artifactId>core</artifactId>
	<version>3.4.0</version>
</dependency>
<dependency>
	<groupId>com.google.zxing</groupId>
	<artifactId>javase</artifactId>
	<version>3.4.0</version>
</dependency>
**/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.lang.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * 二维码工具
 * 
 * @author Administrator
 *
 */
public class QrCodeUtils {

	/**
	 * 生成二维码
	 * @param width		宽度
	 * @param height	高度
	 * @param content	内容
	 * @param out		输出
	 * @param logo		二维码中间的logo
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void qrCode(int width, int height, String content, OutputStream out, @Nullable InputStream logo) throws WriterException, IOException {
		
		String format = "png";// 图像类型
		
		Map<EncodeHintType, Object> hints = new HashMap<>(3);
		
		// 内容编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置二维码边的空度，非负数
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

		if (logo == null) { // 不需要logo
			MatrixToImageWriter.writeToStream(bitMatrix, format, out);
		} else {
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
			BufferedImage bufferedImage = LogoMatrix(MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig), logo);
			ImageIO.write(bufferedImage, format, out);
		}
	}


	/**
	 * 识别二维码
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String QRReader(InputStream qrCode) throws IOException, NotFoundException {
		MultiFormatReader formatReader = new MultiFormatReader();
		// 读取指定的二维码文件
		BufferedImage bufferedImage = ImageIO.read(qrCode);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
		Map hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
//		System.out.println("解析结果：" + result.toString());
//		System.out.println("二维码格式类型：" + result.getBarcodeFormat());
//		System.out.println("二维码文本内容：" + result.getText());
		bufferedImage.flush();
		return result.getText();
	}

	/**
	 * 二维码添加logo
	 * 
	 * @param matrixImage 源二维码图片
	 * @param logo    		logo图片
	 * @return 返回带有logo的二维码图片
	 */
	public static BufferedImage LogoMatrix(BufferedImage matrixImage, InputStream logo) throws IOException {
		/**
		 * 读取二维码图片，并构建绘图对象
		 */
		Graphics2D g2 = matrixImage.createGraphics();

		int matrixWidth = matrixImage.getWidth();
		int matrixHeigh = matrixImage.getHeight();

		/**
		 * 读取Logo图片
		 */
		BufferedImage logoImage = ImageIO.read(logo);

		// 开始绘制图片
		g2.drawImage(logoImage, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);// 绘制
		BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);// 设置笔画对象
		
		// 指定弧度的圆角矩形
		RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
		g2.setColor(Color.white);
		g2.draw(round);// 绘制圆弧矩形

		// 设置logo 有一道灰色边框
		BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke2);// 设置笔画对象
		RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
		
		g2.setColor(new Color(128, 128, 128));
		g2.draw(round2);// 绘制圆弧矩形

		g2.dispose();
		matrixImage.flush();
		return matrixImage;
	}
}
