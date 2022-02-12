---------------------
ContentDisposition
---------------------
	# Content-Disposition Header值的构造器		

	# 维护的变量
			@Nullable
			private final String type;

			@Nullable
			private final String name;

			@Nullable
			private final String filename;

			@Nullable
			private final Charset charset;

			@Nullable
			private final Long size;

			@Nullable
			private final ZonedDateTime creationDate;

			@Nullable
			private final ZonedDateTime modificationDate;

			@Nullable
			private final ZonedDateTime readDate;
	
	# 静态方法
		public static Builder attachment()
		public static Builder formData()
		public static Builder inline()

	
	# 使用
		response.setContentType(contentType);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(path.getFileName().toString(), StandardCharsets.UTF_8).build().toString());
		Files.copy(path, response.getOutputStream());