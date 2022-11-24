------------------------
webp
------------------------
	# 一种高效推荐的图片编码方式
		https://developers.google.com/speed/webp/download

		https://storage.googleapis.com/downloads.webmproject.org/releases/webp/index.html
	

------------------------
Java
------------------------
	# 添加依赖
		<dependency>
			<groupId>org.sejda.imageio</groupId>
			<artifactId>webp-imageio</artifactId>
			<version>${webp-imageio.version}</version>
		</dependency>
		
		* https://github.com/sejda-pdf/webp-imageio
		* 这玩意儿现在还不支持GIF

	# 编码
		import java.awt.image.BufferedImage;
		import java.io.File;
		import java.io.IOException;
		import java.io.InputStream;

		import javax.imageio.IIOImage;
		import javax.imageio.ImageIO;
		import javax.imageio.ImageWriteParam;
		import javax.imageio.ImageWriter;
		import javax.imageio.stream.FileImageOutputStream;

		import com.luciad.imageio.webp.WebPWriteParam;

		public class Webp {

			/**
			 * 编码为WEBP
			 * 
			 * @param in   输入文件
			 * @param file 输出文件
			 * @throws IOException
			 */
			public void encode(InputStream in, File file) throws IOException {

				// 读取图片文件
				BufferedImage image = ImageIO.read(in);

				// 获取 WEBP writer
				ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

				WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
				// 压缩方式，以指定的压缩类型和质量设置进行压缩
				writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				// 压缩质量，无损压缩
				writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);

				try (FileImageOutputStream outputStream = new FileImageOutputStream(file)) {
					writer.setOutput(outputStream);
					writer.write(null, new IIOImage(image, null, null), writeParam);
				}
			}
		}
