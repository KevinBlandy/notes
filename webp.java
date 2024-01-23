------------------------
webp
------------------------
	# 一种高效推荐的图片编码方式
		https://developers.google.com/speed/webp/faq?hl=zh-cn
		https://developers.google.com/speed/webp/download
		https://storage.googleapis.com/downloads.webmproject.org/releases/webp/index.html
	
	# linux下安装，后设置环境变量
		export PATH=$PATH:/root/webp

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

------------------------------------------------
启动新进程调用可执行文件进行转码
------------------------------------------------


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Webp 编码器，通过启动新进程调用本地编码器完成编码
 * 
 */

@Slf4j
public class WebpEncoder {
	
	static final Set<String> validImageTypes = new HashSet<>(Arrays.asList("jpeg", "png", "gif", "jpg"));
	
	/**
	 * 对文件进行 Webp 编码，输出在同一个目录
	 * @param src
	 * @return
	 */
	public static Path encode (Path src) {
		
		String srcFile = src.toString();
		
		String ext = FilenameUtils.getExtension(srcFile).toLowerCase();
		
		if (!validImageTypes.contains(ext)) {
			log.warn("{} 不能被编码为 web 文件");
			return null;
		}
		
		String baseName = FilenameUtils.getBaseName(srcFile);
	     
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        defaultExecutor.setWatchdog(new ExecuteWatchdog(TimeUnit.MINUTES.toMillis(3))); // 超时时间，3分钟
        defaultExecutor.setStreamHandler(new PumpStreamHandler(System.out, System.err)); // 进程输出到标准输出和标准错误

        // 命令行
        CommandLine commandLine = null;
        
        // 在同目录下创建同名 webp 文件
        Path webpFile = src.getParent().resolve(baseName + ".webp");
        
        log.info("Webp 编码：{} -> {}", srcFile, webpFile.toString());
        
        if (ext.equals("gif")) {
            // GIF
            commandLine = new CommandLine("gif2webp");
            commandLine.addArgument(srcFile); // 源文件
            commandLine.addArgument("-o"); 
            commandLine.addArgument(webpFile.toString()); // 输出文件
        } else {
            // 其他
            commandLine = new CommandLine("cwebp");
            commandLine.addArgument("-lossless"); // 无损压缩
            commandLine.addArgument(srcFile); // 源文件
            commandLine.addArgument("-o");
            commandLine.addArgument(webpFile.toString());// 输出文件
        }
        
        try {
            defaultExecutor.execute(commandLine);
        } catch (Throwable e) {
        	
            log.warn("WEBP编码异常：{}", e.getMessage());
            
            // 尝试删除编码文失败的文件
            try {
				Files.deleteIfExists(webpFile);
			} catch (IOException e1) {
			}
            return null;
        } 
        return webpFile;
	}
}
