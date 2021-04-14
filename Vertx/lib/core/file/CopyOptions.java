--------------------
CopyOptions
--------------------
	# 复制文件配置： class CopyOptions 


--------------------
构造
--------------------
	public CopyOptions()
	public CopyOptions(CopyOptions other)
	public CopyOptions(JsonObject json)

	public boolean isReplaceExisting()
	public CopyOptions setReplaceExisting(boolean replaceExisting)
		* 如果目标文件已经存在，是否替换

	public boolean isCopyAttributes()
	public CopyOptions setCopyAttributes(boolean copyAttributes) 
		* 是否复制文件属性

	public boolean isAtomicMove()
	public CopyOptions setAtomicMove(boolean atomicMove)
		* 是否院子移动

	public boolean isNofollowLinks()
	public CopyOptions setNofollowLinks(boolean nofollowLinks)
		* 是否跟随链接

--------------------
实例
--------------------
	

--------------------
静态
--------------------
	public static final boolean DEFAULT_REPLACE_EXISTING = false;
	public static final boolean DEFAULT_COPY_ATTRIBUTES = false;
	public static final boolean DEFAULT_ATOMIC_MOVE = false;
	public static final boolean DEFAULT_NOFOLLOW_LINKS = false;
