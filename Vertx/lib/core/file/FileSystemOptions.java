----------------------------------
FileSystemOptions
----------------------------------
	# 打开文件系统的配置类： class FileSystemOptions 



----------------------------------
构造
----------------------------------
	public FileSystemOptions()
	public FileSystemOptions(FileSystemOptions other) 
	public FileSystemOptions(JsonObject json)

----------------------------------
实例
----------------------------------
	public JsonObject toJson() 

	public boolean isClassPathResolvingEnabled()
	public FileSystemOptions setClassPathResolvingEnabled(boolean classPathResolvingEnabled)
		* 是否开起classpath文件解析

	public boolean isFileCachingEnabled()
	public FileSystemOptions setFileCachingEnabled(boolean fileCachingEnabled)
		* 是否开起classpath文件缓存

	public String getFileCacheDir()
	public FileSystemOptions setFileCacheDir(String fileCacheDir)
		* 设置classpath文件缓存目录

	public String toString()

----------------------------------
静态
----------------------------------
	public static final boolean DEFAULT_FILE_CACHING_ENABLED = !Boolean.getBoolean(DISABLE_FILE_CACHING_PROP_NAME);
	public static final boolean DEFAULT_CLASS_PATH_RESOLVING_ENABLED = !Boolean.getBoolean(DISABLE_CP_RESOLVING_PROP_NAME);
	public static final String DEFAULT_FILE_CACHING_DIR = System.getProperty(CACHE_DIR_BASE_PROP_NAME, TMPDIR + File.separator + DEFAULT_CACHE_DIR_BASE);
