-----------------------
BodyHandler
-----------------------
	# 请求体处理器接口： interface BodyHandler extends Handler<RoutingContext> 

-----------------------
this
-----------------------
	BodyHandler setHandleFileUploads(boolean handleFileUploads)
	BodyHandler setBodyLimit(long bodyLimit)
		* 设置消息体限制
		* 如果尝试发送大于最大大小的正文Request Entity Too Large，则会发送HTTP状态码413
	
	BodyHandler setUploadsDirectory(String uploadsDirectory)
		* 设置上传文件的临时IO目录

	BodyHandler setMergeFormAttributes(boolean mergeFormAttributes)
		* 默认情况下，处理程序会把body参数合并到请求参数

	BodyHandler setDeleteUploadedFilesOnEnd(boolean deleteUploadedFilesOnEnd)
		* 设置是否在结束时自动删除上传的临时文件

	BodyHandler setPreallocateBodyBuffer(boolean isPreallocateBodyBuffer)
		

-----------------------
static
-----------------------
	long DEFAULT_BODY_LIMIT = -1;
		* 最大body体积，复数不限制

	String DEFAULT_UPLOADS_DIRECTORY = "file-uploads";
		* 默认文件上传的临时文件夹名称

	boolean DEFAULT_MERGE_FORM_ATTRIBUTES = true;
		* 合并表单参数

	boolean DEFAULT_DELETE_UPLOADED_FILES_ON_END = false;
		* 在结束的时候，删除IO到磁盘的临时文件

	boolean DEFAULT_PREALLOCATE_BODY_BUFFER = false;
		* 是否根据HTTP请求头的内容长度预分配主体缓冲区大小的默认值。
	
	static BodyHandler create()
	static BodyHandler create(boolean handleFileUploads)
	static BodyHandler create(String uploadDirectory)

