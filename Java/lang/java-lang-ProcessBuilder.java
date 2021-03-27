-----------------------------
ProcessBuilder				 |
-----------------------------
	# 子进程任务构建工厂类
	# 构造函数
		public ProcessBuilder(String... command)
		public ProcessBuilder(List<String> command)
		
	
-----------------------------
ProcessBuilder				 |
-----------------------------

-----------------------------
ProcessBuilder - 实例方法				 |
-----------------------------
	public ProcessBuilder command(String... command)	
	public ProcessBuilder command(List<String> command)
	public List<String> command()
		* 设置/返回执行命令
		* 返回 this

	public Map<String,String> environment() 

	public File directory()
	public ProcessBuilder directory(File directory)
		* 设置/返回工作目录

	Process start() throws Exception
		* 执行
	
	public ProcessBuilder redirectInput(Redirect source) 
	public ProcessBuilder redirectOutput(Redirect destination) 
	public ProcessBuilder redirectError(Redirect destination)
		* 属性默认值为false，子进程的标准输出和错误输出被发送给两个独立的流，这些流可以通过 Process.getInputStream() 和 Process.getErrorStream() 方法来访问。
		* 是否合并标准错误和标准输出；true为合并，false为不合


	public ProcessBuilder redirectInput(File file)
	public ProcessBuilder redirectOutput(File file)
	public ProcessBuilder redirectError(File file)

	public Redirect redirectInput()
	public Redirect redirectOutput()
	public Redirect redirectError()

	public ProcessBuilder inheritIO()

	public boolean redirectErrorStream() 
	public ProcessBuilder redirectErrorStream(boolean redirectErrorStream) 
	public Process start() throws IOException 


