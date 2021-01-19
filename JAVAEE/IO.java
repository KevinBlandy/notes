---------------------------
IO							|
----------------------------
	# JAVA IO 四大体系
		字节流抽象类:
			InputStream	OutputStream
				FileInputStream
				FileOutputStream
				BufferedInputStream
				BufferedOutputStream
		字符流抽象类:
			Reader		Writer
				FileReader
				FileWriter
				BufferedReader
				BufferedWriter
		* 由这个四个类派生出来的子类名称都是由其父类名作为子类名的后缀
	 
	
----------------------------
FileInputStream				|
----------------------------
	# 文件字节流读取
	FileInputStream fileInputStream = new FileInputStream(PATH);
	int len = 0;
	byte[] buf = new byte[1024];
	while (((len = fileInputStream.read(buf)) != -1)){
		System.out.println(new String(buf,0,len,"GBK"));
	}
	fileInputStream.close();

----------------------------
BufferedInputStream			|
----------------------------
	# 文件字节流的 Buffer 装饰流,带缓冲区的,可以提高效率
	FileInputStream fileInputStream = new FileInputStream(PATH);
	BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,1024);	//构建缓冲区流,指定缓冲区大小
	int len = 0;
	byte[] buf = new byte[1024];
	while (((len = bufferedInputStream.read(buf)) != -1)){
		System.out.println(new String(buf,0,len,"GBK"));
	}
	bufferedInputStream.close();

----------------------------
FileOutputStream			|
----------------------------
	# 文件字节流输出
	FileOutputStream fileOutputStream = new FileOutputStream("E:\\IOTEST.java");
	fileOutputStream.write(new String("696615").getBytes());		//N多重载方法,可以直接写入一个字节,或者是一个字节数组中的指定数据
	fileOutputStream.flush();
	fileOutputStream.close();
	
----------------------------
BufferedOutputStream		|
----------------------------
	# 文件字节流输出的 Buffer 装饰流,带缓冲区的,可以提高效率
	FileOutputStream fileOutputStream = new FileOutputStream("E:\\IOTEST.java");
	BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream,1024);
	bufferedOutputStream.write(new String("余文朋").getBytes());
	bufferedOutputStream.flush();
	bufferedOutputStream.close();

----------------------------
FileReader					|
----------------------------
	FileReader reader = new FileReader("D:\\demo.txt");
	reader.read();
		* 一次读一个字符。而且会自动往下读。读到末尾。返回  -1；
		  这个方法返回的是。一个字符的 int 类型数字。需要把它(char)强制转换。才是读取的到的单个字符。 
	reader.read(char[]);
		* 从文件中读取字符,存入字符数组.返回的是'本次读取的字符个数',如果读到末尾,返回 -1
	/*******************************/
	int ch = 0;						
	while((ch = reader.read()) != -1){
		//把每次读取到的数据转换为Char字符
		System.out.println((char)ch);
	}
	
	//通常定义1024的倍数
	char[] buf = new char[1024 * 1024];
	int len = 0;
	//每次读取到的字符个数
	while((len = reader.read(buf)) != -1){
		System.out.println(new String(buf,0,len));
	}
----------------------------
BufferedReader				|
----------------------------
	BufferedReader reader = new BufferedReader(new FileReader(),int length);
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	# length,可以设置缓冲区的大小
	reader.readLine();
		* 读取一行数据(并不包含回车符),当读到末尾。返回 null.

----------------------------
FileWriter					|
----------------------------
	FileWriter writer = new FileWriter("D:\\text.txt",true);
	# 创建一个文本输出流'true',参数表示.不覆盖原来的数据.在该文件的末尾接着写入!否则,直接就全部覆写
	writer.write("abc");				
		* 往流中个输入一个字符串
	writer.flush();
		* 刷新流
	writer.close();
		* 关闭流
	writer.writer(char[] buf,int index,int length)
		* 往流中写入一个字节数组,从index下标处开始,一共length个字节.
	
	# 必须要执行 flush ,才会把字符刷出到目标文件
		
----------------------------
BufferedWriter				|
----------------------------
	BufferedWriter writer = new BufferedWriter(new FileWriter(),int length);
	# length,可以设置缓冲区的大小
	writer.newLine();
		* 从下一行开始写
		* 该方法是跨平台的 。Linux,Windows 都是可以的
	
----------------------------
InputStreamReader			|
----------------------------
	# 字符流到字节流的桥梁
	# 构造需要的是字节'读取'流,最终读取到的是字符流
	# '当涉及到字符编码转换的时候,它就有用了'

	InputStream in = System.in;							//创建了一个字节流输入对象
	InputStreamReader isr = new InputStreamReader(in);	//把字节输入流转换成字符输入流
	BufferedReader bufr = new BufferedReader(isr);		//用字符输入流的装饰类来强化字符输入流的读取方式获得readLine();方法
	bufr.readLine();

	整合简化书写	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in,"utf-8"));

----------------------------
OutputStreamWriter			|
----------------------------
	# 字节流到字符流的桥梁
	# 构造需要的是字节'输出'流,最终输出的是字符流
	# '当涉及到字符编码转换的时候,它就有用了'

	OutputStream out = System.out;									//创建了一个字节输出流对象
	OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");	//把字节输出流，转换为字符输出流。该类可以修改字节流-->字符流的编码格式
	BufferedWriter bufw = new BufferedWriter(osw);					//用字符输出流的装饰类来强化字符输出流的输出方式。获得newLine();方法
	bufw.write();
	bufw.newLine();
	bufw.flush();

	整合简化书写	BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));

----------------------------
打印流						|
----------------------------
	PrintWriter
	PrintStream

----------------------------
管道流						|
----------------------------
	PipedInputStream	
	PipedOutputStream

----------------------------
序列化流					|
----------------------------
	ObjectInputStream

	ObjectOutputStream

	可以直接操作对象的流。
	将堆内存中的对象。存储在硬盘上。而不至于在程序结束，堆内存被释放的时候。所有对象被当作垃圾回收。
	对象的持久化(序列化)存储。
	要想对象被这个类所操作。必须实现一个接口 Serializable ！这个接口中没有任何的方法！就是传说中的'标记接口'.
	ObjectInputStream 
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("文件"));
		|--ois.readObject();//读取出来的都是Object 类型！需要做强制类型转换。
	ObjectOutputStream 
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("文件"));
		|--oos.write("对象");//把被序列化的(实现了 Serializable )的对象存储到指定的文件中。
	不想被序列化的办法-------------------
	transient --关键字
		|--如果一个对象的属性用  transient 来修饰。那么该属性不会被虚拟机做默认的，序列化工作。
	！！！！！！！！！！静态不能被序列化。静态不存在于堆内存。不能被序列化！

----------------------------
合并流						|
----------------------------
	SequenceInputStream
	# 它是 InputStream 的子类。具体操作方法如下。

	Vector<FileInputStream> v = new Vector<FileInputStream>();//创建一个专门储存流对象的容器。
	v.add(new FileInputStream("D:\\1.txt"));	//把流对象添加到容器
	v.add(new FileInputStream("D:\\2.txt"));	
	v.add(new FileInputStream("D:\\3.txt"));
	Enumeration<FileInputStream> en = v.elements();//特有的迭代器取出对象
	SequenceInputStream  sis = new SequenceInputStream(en);//把取出对象作为参数传递给这个合并流。剩下的就是明确目的，然后进行读写即可。

	#将多个流，合并在一起。向同一个目的输出。

----------------------------
RandomAccessFile			|
----------------------------
	# 并非是IO体系中的成员,内部封装了数组。既能读,也能写
	# 方法
		getFilePointer()	
			* 返回文件记录指针的当前位置
		seek(long pos)	
			* 将文件记录指针定位到pos的位置
		length();
			* 获取文件的长度
		readLine();
			* 从指针位置读取当前行,只会读指针这一行后面的数据

	# 读取Demo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(60);			//设置指针位置
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = randomAccessFile.read(buf)) != -1){
            System.out.println(new String(buf,0,len,"GBK"));
        }
        randomAccessFile.close();
	
	# 写入Demo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(randomAccessFile.length());					//移到指针的最后
        randomAccessFile.write("//哈哈哈,这个是追加的哟".getBytes("GBK"));	//写入数据
	

----------------------------
ByteArrayOutputStream		|
----------------------------
	# 操作内存的字节数据流,可以往里面玩儿命写入字节数据到该流
	# 方法
		void writeTo(OutputStream out);
			* 把当前流中的数据都写入到一个输出流

----------------------------
IO异常的专业处理			|
----------------------------
	# IOException

		



