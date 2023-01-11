import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 文件工具类
 * 
 * @author KevinBlandy
 *
 */
public class ZipUtils {

	/**
	 * 压缩指定的文件，可以包含目录。输出到本地磁盘。
	 * 
	 * @param files
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	public static Path zip(Set<Path> files, Path zipFile) throws IOException {
		try (OutputStream zipOutputStream = Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW)) {
			zip(files, zipOutputStream);
		}
		return zipFile;
	}
	
	/**
	 * 压缩指定的文件，可以包含目录。输出到本地磁盘。输出到out流。
	 * @param files
	 * @param out
	 * @throws IOException
	 */
	public static void zip(Set<Path> files, OutputStream out) throws IOException {
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
			for (Path file : files) {
				if (Files.isDirectory(file)) {
					writeFolder(file, zipOutputStream);
				} else {
					writeFile(file, zipOutputStream);
				}
			}
			zipOutputStream.closeEntry();
		}
	}

	// 写入文件
	private static void writeFile(Path file, ZipOutputStream zipOutputStream) throws IOException {
		try (InputStream inputStream = Files.newInputStream(file)) {
			// 创建一个压缩项，指定名称
			ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
			// 添加到压缩流
			zipOutputStream.putNextEntry(zipEntry);
			// 写入数据
			inputStream.transferTo(zipOutputStream);
			zipOutputStream.flush();
		}
	}

	// 写入目录
	private static void writeFolder(Path folder, ZipOutputStream zipOutputStream) throws IOException {

		String folderName = folder.getFileName().toString() + "/";

		Files.walkFileTree(folder, new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				ZipEntry zipEntry = dir.equals(folder) ? new ZipEntry(dir.getFileName().toString() + "/")
						: new ZipEntry(folderName + folder.relativize(dir).toString() + "/");

				zipOutputStream.putNextEntry(zipEntry);
				zipOutputStream.flush();
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				// 开始遍历文件
				try (InputStream inputStream = Files.newInputStream(file)) {

					// 创建一个压缩项，指定名称
//					String fileName = StringUtils.replace(folderName + folder.relativize(file).toString(),
//							WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

					String fileName = folderName + folder.relativize(file).toString();

					// 添加到压缩流
					zipOutputStream.putNextEntry(new ZipEntry(fileName));
					// 写入数据
					inputStream.transferTo(zipOutputStream);

					zipOutputStream.flush();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * 解压文件
	 * 
	 * @param file      压缩文件
	 * @param targetDir 解压文件输出的目录
	 * @throws IOException
	 */
	public static void unZip(Path file, Path targetDir) throws IOException {
		if (!Files.exists(targetDir)) {
			Files.createDirectories(targetDir);
		}
		// 创建zip对象
		try (ZipFile zipFile = new ZipFile(file.toFile())) {
			// 读取zip流
			try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(file))) {
				ZipEntry zipEntry = null;
				// 遍历每一个zip项
				while ((zipEntry = zipInputStream.getNextEntry()) != null) {
					// 获取zip项目名称
					String entryName = zipEntry.getName();
					// 构建绝对路径
					Path entryFile = targetDir.resolve(entryName);
					if (zipEntry.isDirectory() && !Files.isDirectory(entryFile)) { // 文件夹
						Files.createDirectories(entryFile);
					} else { // 文件
						// 读取zip项数据流
						try (InputStream zipEntryInputStream = zipFile.getInputStream(zipEntry)) {
							// 尝试创建其父目录
							if (!Files.isDirectory(entryFile.getParent())) {
								Files.createDirectories(entryFile.getParent());	
							}
							try (OutputStream fileOutputStream = Files.newOutputStream(entryFile,
									StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING)) {
								zipEntryInputStream.transferTo(fileOutputStream);
								fileOutputStream.flush();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 压缩指定的目录
	 * 
	 * @param folder
	 * @param zipFile
	 * @return
	 * @throws IOException
	 */
	public static Path zip(Path folder, Path zipFile) throws IOException {

		if (!Files.isDirectory(folder)) {
			throw new IllegalArgumentException(folder.toString() + " 不是合法的文件夹");
		}

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW))) {
			Files.walkFileTree(folder, new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

					if (dir.equals(folder)) {
						return FileVisitResult.CONTINUE;
					}

					// 写入目录
					try {
						zipOutputStream.putNextEntry(new ZipEntry(folder.relativize(dir).toString() + "/"));
						zipOutputStream.flush();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// 开始遍历文件
					try (InputStream inputStream = Files.newInputStream(file)) {

						// 创建一个压缩项，指定名称
						String fileName = folder.relativize(file).toString();

						// 添加到压缩流
						zipOutputStream.putNextEntry(new ZipEntry(fileName));
						// 写入数据
						inputStream.transferTo(zipOutputStream);

						zipOutputStream.flush();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
			zipOutputStream.closeEntry();
		}

		return zipFile;
	}
}
