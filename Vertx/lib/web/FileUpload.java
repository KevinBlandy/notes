----------------------
FileUpload
----------------------
	# 上传文件的接口： interface FileUpload

	String name();
	String uploadedFileName();
		* 返回上传文件临时IO的磁盘路径

	String fileName();
	long size();
	String contentType();
	String contentTransferEncoding();
	String charSet();
	boolean cancel();
