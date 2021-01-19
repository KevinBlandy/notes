关于 JAVA IO

 '字节流'
	FileInputStream			BufferedInputStream  
	FileOutputStream		BufferedOutputStream
 '字符流'
	FileReader				BufferedReader
	FileWriter				BufferedWriter

	字符流 ---------------------------------------------写
IO流是用于操作数据的。数据的最常见体现形式就是：文件。
那么先以操作文件为主。
FileWriter fw = new FileWriter("Text.txt");
	|--创建一个 FileWriter对象.该对象一被初始化就必须要明确操作的文件。 
而且该文件会被创建到指定目录下。如果该目录下已经有同名文件。将会被覆盖。其实该步就是在明确数据要存放的目的地。
fw.write("abc");
	|--调用write方法将字符串写入到了流中。
fw.flush();
	|--刷新流对象中的缓冲中的数据，将数据刷到目的地中。
fw.colse();
	|--关闭流资源，但是关闭之前会刷新一次内部的缓冲中的数据。将数据刷到目的地中。
和flush();区别在于flush();刷新后。
流可以继续使用。
FileWriter fw = new FileWriter("java.txt",true);
	|--传递一个 true 参数。表示不覆盖已有的文件。并在已有文件的末尾处进行数据的续写。
	/r/n —— 换行
fw.writer(char[],int1,int2)
	|--往目标文件里面写入一个数组中。从int1开始到int2的数据。
	字符流 ---------------------------------------------读
FileReader fr = new FileReader("Text.txt")
	|--创建一个文件读取流对象。和指定名称的文件相关联。
要保证该文件是已经存在的，如果不存在会发生异常。这个异常是	FileNotFoundException.
fr.read();
	|--一次读一个字符。而且会自动往下读。读到末尾。返回  -1；
		这个方法返回的是。一个字符的 int 类型数字。需要把它(char)强制转换。才是读取的到的单个字符。 
fr.read(char[])
	|--定义一个字符数组。用于存储读到的字符。它返回的是读到的字符个数。 String(char[],int1,int2);

'------------------------缓冲区---------------------------'
	缓冲区的出现是为了提高流的操作效率而出现的。所以，在创建缓冲区之前。必须要先有流对象。
	只要用到缓冲区，就要记得刷新！缓冲区关闭，其实就是在关闭缓冲区中的流对象。
对应类：
newLine();
	|--跨平台的换行符。其实也可以用'\r\n'。不过只能在windows平台有效！而这个关键字可用于linux等各个平台

	BufferedWriter
	
	BufferedReader
readLine();
	|--该缓冲区提供了一个一次读一行的方法。方便于对文本数据的获取。当读到末尾。返回 null.
该方法返回的时候。只返回回车符前的数据内容。并不返回回车符！！！
不论是读一行，还是一次性获取多个字符。其实最终都是在硬盘上一个一个读取。所以最终使用的还是 read 方法。一次读一个的方法
	LineNumberReader _可以
		lnr.getLineNumber()//可以获取读取的行号
		lnr.setLineNumber(num)//设置初始的行号。读取的第一行从num+1开始标记。

"<装饰设计模式>"
	'当想要对已有对象进行功能增强时，可以定义类。将已有对象传入。基于已有的功能。并提供加强功能，那么该自定义类成为装饰类'
	'装饰类通常会通过构造方法接收被装饰的对象，并基于被装饰的对象功能。提供更强的功能'
字节流 ---------------------------------------------写
	OutputStream //抽象类。不能建立对象。只有建立子类对象
		|--ByteArrayOutputStream  FileOutputStream FilterOutputStream ObjectOutputStream OutputStream PipedOutputStream
		后缀名是父类。前缀名是功能
		getBytes();
			|--把字符串变成字节数组
		new String(byte,int,int);
			|--把一个字节符数组变成字符串。从 int 开始截取。截取 int 个。
		
availabale();
字节流 ---------------------------------------------读
	InputStream
		|--							获取键盘输入
availabale();
	|--返回读取文件的字节长度。
----------------------------------------------------------字节流缓冲区

FileInputStream
FileOutputStream
BufferedInputStream
BufferedOutputStream
FileReader
FileWriter
BufferedReader
BufferedWriter
------------------------------------------读取转换流   '字节流转换字符流'
	录入的是字节，输出的是字符！这个就是字节，转字符。
	通过把字节流，转换成字符流以后。那么读取字节，可以使用读取字符(读一行)的一些方法，可以提高效率。

转换流是属于字符流体系中的成员。
如果要涉及到编码转换。那么就要使用到转换流
InputStreamReader 
	|--字节流同向字符的桥梁
将字节流对象转换成字符流对象
InputStream in = System.in;//创建了一个字节流输入对象
InputStreamReader isr = new InputStreamReader(in);//把字节输入流转换成字符输入流
BufferedReader bufr = new BufferedReader(isr);//用字符输入流的装饰类来强化字符输入流的读取方式获得readLine();方法
bufr.readLine();
整合简化书写	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in,"utf-8"));
	|--键盘录入最常见的写法、通过转换，装饰后。可以直接读取一行的数据。比原始的 System.in 一次读一个字节更为的方便。
------------------------------------------写入转换流	'字符流转换成字节流'
	录入的是字符，输出的是字节！这个就是字符，转字节。
	通过把字符流，转换成字节流以后。那么读取字符，可以用读取字节流(缓冲区优化)一些方法，可以提高效率。

如果要涉及到编码转换。那么就要使用到转换流
OutputStreamWriter
	|--字符流通向字节流的桥梁
OutputStream out = System.out;							//创建了一个字符流输出对象
OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");	//把字节输出流，转换层字符输出流。该类可以修改字节流-->字符流的编码格式
BufferedWriter bufw = new BufferedWriter(osw);			//用字符输出流的装饰类来强化字符输出流的输出方式。获得newLine();方法
bufw.write();
bufw.newLine();
bufw.flush();
整合简化书写	BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
-----------------------------------------流操作的基本规律
最大的问题就是。流操作有很多，不知道该用哪一个？
通过两个明确来完成：
1,明确源和目的地。
	源：输入流。
	目的地：输出流。
2,操作的数据是否是纯文本。
	是纯文本：字符流。
	非纯文本：字节流。
3,当体系明确后，再明确要使用哪个具体的对象。
	通过设备来进行区分。
	源设备：内存，硬盘，键盘。
	目的设备：内存，硬盘，控制台。
--------------------------------------------File 类--------------------------------------------------
File 常见方法——————构造方法颇多，自己研究
File f = new File(目录/文件);
f.createNewFile();//创建f文件。如果成功就返回 true 如果文件(名字)存在则不创建，返回false。和输出流不一样。
f.exists();//判断初始化的路劲是否存在
	|--在判断文件。是否为目录，或者文件时。必须先判断。这个东西是否存在。通过 exists();判断。
f.canExcute();//判断这个文件是否可以被执行。
f.canWrite();//判断文件是否可读
f.canRead();//判断文件是否可写
f.isHidden();//判断文件是否为隐藏文件。
f.isAbsolute();//判断文件是否为绝对路径。
f.mkdir();//创建此目录(文件夹)。f只能是一级目录
f.mkdirs();//创建此目录(文件夹)。f可以是多级目录。
f.delete();//删除此目录。删除失败返回false.
	|--File 的删除方法不走回收站。
f.deleteOnExit();//在虚拟机停止运行(程序退出时)的时候。删除掉指定目录。就算是发生异常也会删除f目录。
f.isDirectory();//判断f是否为目录，如果目录不存在也返回false
f.isFile();//判断f是否是一个文件。如果文件不存在也返回false
	|--在判断文件。是否为目录，或者文件时。必须先判断。这个东西是否存在。通过 exists();判断。
separator();//[static]跨平台分隔符。增强了跨平台性。静态方法，直接类名调用。
	|--File f = new File("D:"+File.separator()+"abc"+File.separator()+"a.txt");
f.getName();//返回f路劲表示的文件或者目录名称。返回String类型！
f.getPath();//把f表示的路径转换成字符串。
f.getParent()://返回f的父目录(绝对路劲)名字符串。如果f没有指定目录。则返回null。 如果相对路劲中有上层目录那么该目录就是返回结果。
f.getAbsolutePath();//获取f表示的绝对路径。它返回的是字符串表达形式
f.getAbsoluteFile();//获取f表示的绝对路劲。注意，它返回的是一个 File类型的对象！
f.lastModified();//返回文件被修改的最后一次的时间。返回值是long。(毫秒值)
f.length();//返回f表示的目录或者文件的大小.返回值类型是long
f.renameTo(f1);//把f1路劲文件名字修改成f1路径文件名字。有点像剪切。！！！
f.createTempFile();//创建一个临时文件夹,一般都会同时设置deleteOnExit();

----------------
File.listRoots();[static]
	|--返回的是一个 File[] 类型的数组.是目前PC上所有的有效盘符路径。
f.list();
	|--返回的是一个String[]类型的字符串数组。里面包含了f目录下的所有文件以及目录名字(包含隐藏文件,不包含子目录文件)。如果f是文件的话。会抛出空指针异常。
f.list(匿名对象的文件过滤器);
	|--把f目录下符合过滤器规则(文件名)。的文件名添加到 String[] 类型的数组！并返回。
f.listFiles();
	|--返回的是f目录下的所有文件对象数组。 File[]
f.listFiles(匿名对象文件过滤器);
	|--把f目录下符合过滤器规则的文件(文件名).添加到 File[] 类型的数组中。并返回。
-----------------------------------------------
Properties
	是hashtable的子类。
	也就是说它具备map集合的特点。而且它里面存储的键值对都是字符串。
	是集合中与IO技术相结合的集合容器。
	该对象的特点：可以用于键值对形式的配置文件。
那么在加载数据时。要有固定格式
	键=值
	把info文件的键值数据存到集合中的操作
	1,用一个流和info文件关联
	2,读取一行数据，讲该行数据用"="进行切割
	3,"="左边作为键，右边作为值。存储到 Properties 集合中。
---
Properties p = new Properties();
p.getProperty(String key);
	|--根据指定的键，获取对应的值。
p.load(输入流);
	|--从一个输入流(配置文件的流)中获取键值对(加载进 Properties)。
p.store(输出流,注释信息)
	|--把内存中 Properties 的键值对。写进输出流。可以加上后面的注释信息。
p.store(输出流,"注释信息");
	|--把配置写进输出流(配置文件的流)。后面可以添加字符串类型的注释。
p.getProperty(key) 
    |--根据指定的键获取对应的值(String)。
p.list(输出打印流) 
     |--将属性列表输出到指定的输出流。
p.setProperty(键，值);
	|--根据指定的键位，修改指定的值。如果键不存在。就新建。
p.stringPropertyNames();
	|--返回此列表中的键集(不包含值)。返回的是 Set<String> 类型的集合。

--------------------------------------IO其他对象——打印流
该流，提供了打印方法。可以把各种数据类型的数据都原样打印。
PrintWriter out = new PrintWriter(System.out,true);
PrintWriter out = new PrintWriter(new File(),true);
PrintWriter
	|--字符打印流。构造函数可以接收的参数类型——
	1,File 对象	
	2,字符串路径
	3,字节输出流 OutputStream
	4,字符输出流 Writer
PrintStream
	|--字节打印流。构造函数可以接收的参数类型—— 
	1, File 对象
	2, 字符串路径
	3, 字节输出流
--------------------------------------合并流	SequenceInputStream
	它是 InputStream 的子类。具体操作方法如下。
Vector<FileInputStream> v = new Vector<FileInputStream>();//创建一个专门储存流对象的容器。
v.add(new FileInputStream("D:\\1.txt"));//把流对象添加到容器
v.add(new FileInputStream("D:\\2.txt"));//
v.add(new FileInputStream("D:\\3.txt"));//
Enumeration<FileInputStream> en = v.elements();//特有的迭代器取出对象
SequenceInputStream  sis = new SequenceInputStream(en);//把取出对象作为参数传递给这个合并流。剩下的就是明确目的，然后进行读写即可。
将多个流，合并在一起。向同一个目的输出。
--------------------------------------切割流
把源文件字节数据输出到不同的目的地。就是切割，可以通过上面的合并流来吧数据进行合并！

--------------------------------------		序列流
SequenceInputStream
	|--并没有对应的 OutputStream.
	可以串联其他流。
-------------------------------------		操作对象
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
------------------------------------		管道流
结合线程使用。输入输出直接进行连接。不建议使用单线程。
因为读取流先开启，那么流中一旦没有数据。读取流的阻塞式方法就会一直处于等待状态。死锁。
PipedInputStream	PipedOutputStream
------------------------------------		随机访问文件
RandomAccessFile 
	|--自成一派的工具类。直接继承 Object 。
	该类不算是IO体系中的子类。而是直接继承自 Object .但是它是IO包中的成员。'因为它具备读和写功能。'
内部封装了一个数组。而且通过指针通过对数组中的元素进行操作。可以通过 getFilePointer 获取指针位置。
同时也可以通过seek改变指针的位置。其实完成读写的原理。其实就是内部封装了字节输入流和输出流。通过构造函数可以看出
，该类只能操作文件。







=======================================			实际案例
1,把内存中的数据以 txt 文本响应给客户端下载
	StringBuilder sb = new StringBuilder();
	sb.append("hello");
	//读取流
	InputStream in = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
	//ISO编码,防止乱码
	String framName = new String((communityDTO.getCommunityName() +"_初始化信息.txt").getBytes("GBK"),"ISO-8859-1");
	//创建下载框文件名称
	String contentDisposition = "attachment;filename=" + framName;
	//创建响应头
	response.setHeader("Content-Type","text/plain");
	response.setHeader("Content-Disposition",contentDisposition );
	IOUtils.copy(in, response.getOutputStream());