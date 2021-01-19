package com.tedi.park.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiFunction;
/**
 * 
 * @author KevinBlandy
 *
 */
public class FileUtils {
	
	/**
	 * 重命名指定文件夹下的所有文件,或者文件夹
	 * @param source
	 * @param func
	 * @throws IOException
	 */
	public static void renameDir(String sourceDir,BiFunction<String,Boolean,String> func) throws IOException {
		File file = new File(sourceDir);
		File[] subFiles = file.listFiles();
		if(subFiles != null && subFiles.length > 0) {
			for(File subFile : subFiles) {
				boolean isDirectory = subFile.isDirectory();
				if(isDirectory) {
					renameDir(subFile.getAbsolutePath(),func);
				}
				subFile.renameTo(new File(subFile.getParentFile(), func.apply(subFile.getName(), isDirectory)));
			}
		}
	}

	/**
	 * copy整个文件夹
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void copyDir(String sourceDir, String targetDir) throws IOException {
		Path sourcePath = Paths.get(sourceDir);
		Path targetPath = Paths.get(targetDir);
		Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				Files.copy(dir, targetPath.resolve(sourcePath.relativize(dir)));
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
				return FileVisitResult.CONTINUE;
			}
		});
	}


	/**
	 * 对文件按照指定大小进行分片，在文件所在目录生成分片后的文件块儿
	 * @param file
	 * @param chunkSize
	 * @throws IOException
	 */
	public static void chunkFile(Path file, long chunkSize) throws IOException {
		if (Files.notExists(file) || Files.isDirectory(file)) {
			throw new IllegalArgumentException("文件不存在:" + file);
		}
		if (chunkSize < 1) {
			throw new IllegalArgumentException("分片大小不能小于1个字节:" + chunkSize);
		}
		// 原始文件大小
		final long fileSize = Files.size(file);
		// 分片数量
		final long numberOfChunk = fileSize % chunkSize == 0 ? fileSize / chunkSize : (fileSize / chunkSize) + 1;
		// 原始文件名称
		final String fileName = file.getFileName().toString();
		// 读取原始文件
		try(FileChannel fileChannel = FileChannel.open(file, EnumSet.of(StandardOpenOption.READ))){
			for (int i = 0; i < numberOfChunk; i++) {
				long start = i * chunkSize;
				long end = start + chunkSize;
				if (end > fileSize) {
					end = fileSize;
				}
				// 分片文件名称
				Path chunkFile = Paths.get(fileName + "-" + (i + 1));
				try (FileChannel chunkFileChannel = FileChannel.open(file.resolveSibling(chunkFile), 
										EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))){
					// 返回写入的数据长度
					fileChannel.transferTo(start, end - start, chunkFileChannel);
				}
			}
		}
	}
	
	/**
	 * 把多个文件合并为一个文件
	 * @param file			目标文件	
	 * @param chunkFiles	分片文件
	 * @throws IOException 
	 */
	public static void mergeFile (Path file, Path ... chunkFiles) throws IOException {
		if (chunkFiles == null || chunkFiles.length == 0) {
			throw new IllegalArgumentException("分片文件不能为空");
		}
		try (FileChannel fileChannel = FileChannel.open(file, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))){
			for (Path chunkFile : chunkFiles) {
				try(FileChannel chunkChannel = FileChannel.open(chunkFile, EnumSet.of(StandardOpenOption.READ))){
					chunkChannel.transferTo(0, chunkChannel.size(), fileChannel);
				}
			}
		}
	}
}
