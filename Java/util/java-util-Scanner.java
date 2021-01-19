--------------------
Scanner				|
--------------------
	# 扫描器
	# 我用得最多的就是读取屏幕的输入(会阻塞线程)
		* 还可以用于读取文件,字符等等....
	# 构造方法
		Scanner(InputStream in);
		Scanner(Readable source);
		Scanner(InputStream source, String charsetName)	
		Scanner(File source)
	
	# jdk1.5以前读取屏幕输入
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.readLine();

--------------------
Scanner	- 方法		|
--------------------
	int nextInt();			
	String next();
	String line nextLine();
	boolean hasNext()
	useDelimiter(String pattern);
		* 使用指定的正则去分割
	useLocale(Locale locale)
		* 使用指定的语言环境
	