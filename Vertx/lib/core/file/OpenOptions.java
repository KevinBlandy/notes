-----------------------
OpenOptions
-----------------------
	# 文件的打开配置： class OpenOptions 


-----------------------
构造
-----------------------	
	public OpenOptions()
	public OpenOptions(OpenOptions other)
	public OpenOptions(JsonObject json) 

	public String getPerms()
	public OpenOptions setPerms(String perms)
		* 设置权限，在新建的时候会用到

	public boolean isRead()
	public OpenOptions setRead(boolean read)
	public boolean isWrite()
	public OpenOptions setWrite(boolean write)
		* 打开读写空值

	public boolean isCreate()
	public OpenOptions setCreate(boolean create)
		* 如果文件不存在则创建

	public boolean isCreateNew()
	public OpenOptions setCreateNew(boolean createNew)
		* 文件必须是不存在的，否则异常

	public boolean isDeleteOnClose()
	public OpenOptions setDeleteOnClose(boolean deleteOnClose)
		* 在文件关闭的时候，删除文件

	public boolean isTruncateExisting()
	public OpenOptions setTruncateExisting(boolean truncateExisting)
		* 如果文件已经存在，则截断为0

	public boolean isSparse()
	public OpenOptions setSparse(boolean sparse)

	public boolean isSync()
	public OpenOptions setSync(boolean sync)

	public boolean isDsync()
	public OpenOptions setDsync(boolean dsync)

	public boolean isAppend()
	public OpenOptions setAppend(boolean append)
		* 添加到末尾

-----------------------
实例
-----------------------

-----------------------
静态
-----------------------
  public static final String DEFAULT_PERMS = null;
  public static final boolean DEFAULT_READ = true;
  public static final boolean DEFAULT_WRITE = true;
  public static final boolean DEFAULT_CREATE = true;
  public static final boolean DEFAULT_CREATENEW = false;
  public static final boolean DEFAULT_DSYNC = false;
  public static final boolean DEFAULT_SYNC = false;
  public static final boolean DEFAULT_DELETEONCLOSE = false;
  public static final boolean DEFAULT_TRUNCATEEXISTING = false;
  public static final boolean DEFAULT_SPARSE = false;

  public static final boolean DEFAULT_APPEND = false;