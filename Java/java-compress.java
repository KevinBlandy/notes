-----------------------------
压缩算法					 |
-----------------------------
	ZipOutputStream
	ZipInputStream

	GZIPOutputStream
	GZIPInputStream


-----------------------------
zip压缩与解压缩
-----------------------------
	/**
	 * 解压文件 
	 * @param file		压缩文件
	 * @param targetDir	解压文件输出的目录
	 * @throws IOException 
	 */
	public static void unPacket(Path file, Path targetDir) throws IOException {
		if (!Files.exists(targetDir)) {
			Files.createDirectories(targetDir);
		}
		// 创建zip对象
		ZipFile zipFile = new ZipFile(file.toFile());
		try {
			// 读取zip流
			try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(file))){
				ZipEntry zipEntry = null;
				// 遍历每一个zip项
				while ((zipEntry = zipInputStream.getNextEntry()) != null) {
					// 获取zip项目名称
					String entryName = zipEntry.getName();
					// 构建绝对路径
					Path entryFile = targetDir.resolve(entryName);
					if(zipEntry.isDirectory()) {	// 文件夹
						if (!Files.isDirectory(entryFile)) {
							Files.createDirectories(entryFile);
						}
					} else {							// 文件
						// 读取zip项数据流
						try(InputStream zipEntryInputStream = zipFile.getInputStream(zipEntry)){
							try(OutputStream fileOutputStream = Files.newOutputStream(entryFile, StandardOpenOption.CREATE_NEW)){
								byte[] buffer = new byte[4096];
								int length = 0;
								while ((length = zipEntryInputStream.read(buffer)) != -1) {
									fileOutputStream.write(buffer, 0, length);
								}
								fileOutputStream.flush();
							}
						}
					}
				}
			}
		} finally {
			zipFile.close();
		}
	}

	/**
	 * 压缩指定的文件
	 * @param files				目标文件
	 * @param zipFile			生成的压缩文件
	 * @throws IOException
	 */
	public static void packet(Path[] files, Path zipFile) throws IOException {
		OutputStream outputStream = Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW);
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		try {
			for (Path file : files) {
				if (Files.isDirectory(file)) {
					continue;
				}
				try (InputStream inputStream = Files.newInputStream(file)) {
					// 创建一个压缩项，指定名称
					ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
					// 添加到压缩流
					zipOutputStream.putNextEntry(zipEntry);
					// 设置注释
					zipOutputStream.setComment("我是注释");
					// 写入数据
					int len = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((len = inputStream.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}
					zipOutputStream.flush();
				}
			}
			// 完成所有压缩项的添加
			zipOutputStream.closeEntry();
		} finally {
			zipOutputStream.close();
			outputStream.close();
		}
	}
	
	/**
	 * 压缩指定的目录 
	 * @param folder
	 * @param zipFile
	 * @throws IOException
	 */
	public static void packet(Path folder, Path zipFile) throws IOException {
		if (!Files.isDirectory(folder)) {
			throw new IllegalArgumentException(folder.toString() + " 不是合法的文件夹");
		}
		OutputStream outputStream = Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW);
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		
		LinkedList<String> path = new LinkedList<>();
		
		try {
			Files.walkFileTree(folder, new FileVisitor<Path>() {
	
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					
					if (!dir.equals(folder)) {
						// 开始遍历目录
						String folder = dir.getFileName().toString();
						path.addLast(folder);
						// 写入目录 
						ZipEntry zipEntry = new ZipEntry(path.stream().collect(Collectors.joining("/", "", "/")));
						try {
							zipOutputStream.putNextEntry(zipEntry);
							zipOutputStream.flush();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
					return FileVisitResult.CONTINUE;
				}
	
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					// 开始遍历文件
					try (InputStream inputStream = Files.newInputStream(file)) {
						
						// 创建一个压缩项，指定名称
						String fileName = path.size() > 0 
								? path.stream().collect(Collectors.joining("/", "", "")) + "/" + file.getFileName().toString()
								: file.getFileName().toString();
						
						ZipEntry zipEntry = new ZipEntry(fileName);
						
						// 添加到压缩流
						zipOutputStream.putNextEntry(zipEntry);
						// 写入数据
						int len = 0;
						byte[] buffer = new byte[1024 * 10];
						while ((len = inputStream.read(buffer)) > 0) {
							zipOutputStream.write(buffer, 0, len);
						}
						
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
					// 结束遍历目录
					if (!path.isEmpty()) {
						path.removeLast();
					}
					return FileVisitResult.CONTINUE;
				}
			});
			zipOutputStream.closeEntry();
		} finally {
			zipOutputStream.close();
			outputStream.close();
		}
	}

------------------------------------------
 GIZ压缩/解压缩							  |
------------------------------------------
    /**
     * 压缩
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] gZip(byte[] data) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        GZIPOutputStream gzipOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(data);
            gzipOutputStream.finish();
            bytes = byteArrayOutputStream.toByteArray();
        } finally {

        }
        return bytes;
    }

	/**
     * 解压缩
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] unGZip(byte[] data) throws IOException {
        byte[] bytes = null;
        ByteArrayInputStream byteArrayInputStream = null;
        GZIPInputStream gzipInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(data);
            gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] buf = new byte[1024];
            int num = -1;
            byteArrayOutputStream = new ByteArrayOutputStream();
            while ((num = gzipInputStream.read(buf, 0, buf.length)) != -1)
            {
                byteArrayOutputStream.write(buf, 0, num);
            }
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
        } finally {
            byteArrayInputStream.close();
            gzipInputStream.close();
            byteArrayOutputStream.close();
        }
        return bytes;
    }
