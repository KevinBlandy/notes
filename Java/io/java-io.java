----------------------
IO��ϵ				  |
----------------------
	InputStream
		ByteArrayInputStream 
		FileInputStream 
		FilterInputStream 
		ObjectInputStream 
		PipedInputStream 
		SequenceInputStream 
		FilterInputStream
			* InputStream ��wrapper�࣬�����Զ���ʵ��

	OutputStream
		ByteArrayOutputStream
		FileOutputStream
		ObjectOutputStream
		PipedOutputStream 
		FilterOutputStream
			* OutputStream ��wrapper�࣬�����Զ���ʵ��

	Reader
		FileReader
		BufferedReader

	Writer
		FileWriter
		BufferedWriter

---------------------------
IO							|
----------------------------
	# JAVA IO �Ĵ���ϵ
		�ֽ���������:
			InputStream	OutputStream
				FileInputStream
				FileOutputStream
				BufferedInputStream
				BufferedOutputStream
		�ַ���������:
			Reader		Writer
				FileReader
				FileWriter
				BufferedReader
				BufferedWriter
		* ������ĸ��������������������ƶ������丸������Ϊ�������ĺ�׺
	 
	
----------------------------
FileInputStream				|
----------------------------
	# �ļ��ֽ�����ȡ
	FileInputStream fileInputStream = new FileInputStream(PATH);
	int len = 0;
	byte[] buf = new byte[1024];
	while (((len = fileInputStream.read(buf)) != -1)){		//��ÿ�ζ�ȡ�����ֽڴ���but,���ҷ���len��ʾ��ȡ�˶����ֽ�,�������ĩβ,����-1
		System.out.println(new String(buf,0,len,"GBK"));
	}
	fileInputStream.close();

----------------------------
BufferedInputStream			|
----------------------------
	# �ļ��ֽ����� Buffer װ����,����������,�������Ч��
	FileInputStream fileInputStream = new FileInputStream(PATH);
	BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,1024);	//������������,ָ����������С
	int len = 0;
	byte[] buf = new byte[1024];
	while (((len = bufferedInputStream.read(buf)) != -1)){
		System.out.println(new String(buf,0,len,"GBK"));
	}
	bufferedInputStream.close();

----------------------------
FileOutputStream			|
----------------------------
	# �ļ��ֽ������
	FileOutputStream fileOutputStream = new FileOutputStream("E:\\IOTEST.java");
		* ֱ�����ַ���ָ���ļ���ַ
	FileOutputStream fileOutputStream = FileOutputStream(File file, boolean append) 
		* ��һ��������File����,�ڶ���������ʾ��ǰ���Ƿ�����'ĩβ��ӵķ�ʽд��'
	fileOutputStream.write(new String("696615").getBytes());		//N�����ط���,����ֱ��д��һ���ֽ�,������һ���ֽ������е�ָ������
	fileOutputStream.flush();
	fileOutputStream.close();
	
----------------------------
BufferedOutputStream		|
----------------------------
	# �ļ��ֽ�������� Buffer װ����,����������,�������Ч��
	FileOutputStream fileOutputStream = new FileOutputStream("E:\\IOTEST.java");
	BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream,1024);
	bufferedOutputStream.write(new String("������").getBytes());
	bufferedOutputStream.flush();
	bufferedOutputStream.close();

----------------------------
FileReader					|
----------------------------
	FileReader reader = new FileReader("D:\\demo.txt");
	reader.read();
		* һ�ζ�һ���ַ������һ��Զ����¶�������ĩβ������  -1��
		  ����������ص��ǡ�һ���ַ��� int �������֡���Ҫ����(char)ǿ��ת�������Ƕ�ȡ�ĵ��ĵ����ַ��� 
	reader.read(char[]);
		* ���ļ��ж�ȡ�ַ�,�����ַ�����.���ص���'���ζ�ȡ���ַ�����',�������ĩβ,���� -1
	/*******************************/
	int ch = 0;						
	while((ch = reader.read()) != -1){
		//��ÿ�ζ�ȡ��������ת��ΪChar�ַ�
		System.out.println((char)ch);
	}
	
	//ͨ������1024�ı���
	char[] buf = new char[1024 * 1024];
	int len = 0;
	//ÿ�ζ�ȡ�����ַ�����
	while((len = reader.read(buf)) != -1){
		System.out.println(new String(buf,0,len));
	}
----------------------------
BufferedReader				|
----------------------------
	BufferedReader reader = new BufferedReader(new FileReader(),int length);
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	# length,�������û������Ĵ�С
	reader.readLine();
		* ��ȡһ������(���������س���),������ĩβ������ null.
	
	LineNumberReader
		* BufferedReader ������,���п��Զ�д�кŵ���
			getLineNumber()//���Ի�ȡ��ȡ���к�
			setLineNumber(num)//���ó�ʼ���к�,��ȡ�ĵ�һ�д�num+1��ʼ���

----------------------------
FileWriter					|
----------------------------
	FileWriter writer = new FileWriter("D:\\text.txt",true);
	# ����һ���ı������'true',������ʾ.������ԭ��������.�ڸ��ļ���ĩβ����д��!����,ֱ�Ӿ�ȫ����д
	writer.write("abc");				
		* �����и�����һ���ַ���
	writer.flush();
		* ˢ����
	writer.close();
		* �ر���
	writer.writer(char[] buf,int index,int length)
		* ������д��һ���ֽ�����,��index�±괦��ʼ,һ��length���ֽ�.
	
	# ����Ҫִ�� flush ,�Ż���ַ�ˢ����Ŀ���ļ�
		
----------------------------
BufferedWriter				|
----------------------------
	BufferedWriter writer = new BufferedWriter(new FileWriter(),int length);
	# length,�������û������Ĵ�С
	writer.newLine();
		* ����һ�п�ʼд
		* �÷����ǿ�ƽ̨�� ��Linux,Windows ���ǿ��Ե�
	
----------------------------
InputStreamReader			|
----------------------------
	# �ַ������ֽ���������
	# ������Ҫ�����ֽ�'��ȡ'��,���ն�ȡ�������ַ���
	# '���漰���ַ�����ת����ʱ��,����������'

	InputStream in = System.in;							//������һ���ֽ����������
	InputStreamReader isr = new InputStreamReader(in);	//���ֽ�������ת�����ַ�������
	BufferedReader bufr = new BufferedReader(isr);		//���ַ���������װ������ǿ���ַ��������Ķ�ȡ��ʽ���readLine();����
	bufr.readLine();

	���ϼ���д	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in,"utf-8"));

----------------------------
OutputStreamWriter			|
----------------------------
	# �ֽ������ַ���������
	# ������Ҫ�����ֽ�'���'��,������������ַ���
	# '���漰���ַ�����ת����ʱ��,����������'

	OutputStream out = System.out;									//������һ���ֽ����������
	OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");	//���ֽ��������ת��Ϊ�ַ����������������޸��ֽ���-->�ַ����ı����ʽ
	BufferedWriter bufw = new BufferedWriter(osw);					//���ַ��������װ������ǿ���ַ�������������ʽ�����newLine();����
	bufw.write();
	bufw.newLine();
	bufw.flush();

	���ϼ���д	BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));

----------------------------
��ӡ��						|
----------------------------
	PrintWriter
		PrintWriter(File file)
		PrintWriter(File file, String csn)
		PrintWriter(OutputStream out)
		PrintWriter(OutputStream out, boolean autoFlush)
		PrintWriter(Writer out)
		PrintWriter(Writer out, boolean autoFlush)
		PrintWriter(String fileName)
		PrintWriter(String fileName, String csn)
		PrintWriter(OutputStream out, boolean autoFlush, Charset charset) 

	PrintStream
		PrintStream(File file)
		PrintStream(File file, String csn)
		PrintStream(OutputStream out)
		PrintStream(OutputStream out, boolean autoFlush)
		PrintStream(OutputStream out, boolean autoFlush, String encoding)
		PrintStream(String fileName)
		PrintStream(String fileName, String csn)


	autoFlush: �Ƿ��Զ�ˢ��



	* ��������������Դ,ֻ��������Ŀ��
	* Ϊ�������������˹���
	* ��Զ�����׳�IOException,���ǿ����׳������쳣
	* ������ӡ��������ȫһ��

----------------------------
�ܵ���						|
----------------------------
	PipedInputStream	
	PipedOutputStream
	PipedReader
	PipedWriter

	* ����߳�ʹ��,�������ֱ�ӽ�������,������ʹ�õ��߳�,��Ϊ��ȡ���ȿ���,��ô����һ��û������,��ȡ��������ʽ�����ͻ�һֱ���ڵȴ�״̬-����


----------------------------
���л���					|
----------------------------
	ObjectInputStream

	ObjectOutputStream

	����ֱ�Ӳ������������
	�����ڴ��еĶ��󡣴洢��Ӳ���ϡ����������ڳ�����������ڴ汻�ͷŵ�ʱ�����ж��󱻵����������ա�
	����ĳ־û�(���л�)�洢��
	Ҫ����������������������ʵ��һ���ӿ� Serializable ������ӿ���û���κεķ��������Ǵ�˵�е�'��ǽӿ�'.
	ObjectInputStream 
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("�ļ�"));
		|--ois.readObject();//��ȡ�����Ķ���Object ���ͣ���Ҫ��ǿ������ת����
	ObjectOutputStream 
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("�ļ�"));
		|--oos.write("����");//�ѱ����л���(ʵ���� Serializable )�Ķ���洢��ָ�����ļ��С�
	���뱻���л��İ취-------------------
	transient --�ؼ���
		|--���һ�������������  transient �����Ρ���ô�����Բ��ᱻ�������Ĭ�ϵģ����л�������
	����������������������̬���ܱ����л�����̬�������ڶ��ڴ档���ܱ����л���

----------------------------
�ϲ���						|
----------------------------
	SequenceInputStream
	# ���� InputStream �����ࡣ��������������¡�

	Vector<FileInputStream> v = new Vector<FileInputStream>();//����һ��ר�Ŵ����������������
	v.add(new FileInputStream("D:\\1.txt"));	//����������ӵ�����
	v.add(new FileInputStream("D:\\2.txt"));	
	v.add(new FileInputStream("D:\\3.txt"));
	Enumeration<FileInputStream> en = v.elements();//���еĵ�����ȡ������
	SequenceInputStream  sis = new SequenceInputStream(en);//��ȡ��������Ϊ�������ݸ�����ϲ�����ʣ�µľ�����ȷĿ�ģ�Ȼ����ж�д���ɡ�

	#����������ϲ���һ����ͬһ��Ŀ�������

----------------------------
RandomAccessFile			|
----------------------------
	# ������IO��ϵ�еĳ�Ա,�ڲ���װ�����顣���ܶ�,Ҳ��д
	# ����
		getFilePointer()	
			* �����ļ���¼ָ��ĵ�ǰλ��
		seek(long pos)	
			* ���ļ���¼ָ�붨λ��pos��λ��
		length();
			* ��ȡ�ļ��ĳ���
		readLine();
			* ��ָ��λ�ö�ȡ��ǰ��,ֻ���ָ����һ�к��������

	# ��ȡDemo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(60);			//����ָ��λ��
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = randomAccessFile.read(buf)) != -1){
            System.out.println(new String(buf,0,len,"GBK"));
        }
        randomAccessFile.close();
	
	# д��Demo
		RandomAccessFile randomAccessFile = new RandomAccessFile(PATH,"rw");
        randomAccessFile.seek(randomAccessFile.length());					//�Ƶ�ָ������
        randomAccessFile.write("//������,�����׷�ӵ�Ӵ".getBytes("GBK"));	//д������
	

----------------------------
ByteArrayOutputStream		|
----------------------------
	# �����ڴ���ֽ�������,���������������д���ֽ����ݵ�����
	# ����Ҫ�ر���Դ����
	# ����
		void writeTo(OutputStream out);
			* �ѵ�ǰ���е����ݶ�д�뵽һ�������
		
		String toString(Charset charset)
			* ���ֽ�����, ��ָ������ת��Ϊ�ַ���
	
	#��Demo
		* ���ڴ��е��ֽ�,��Ӧ���ͻ���
			InputStream in = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
			IOUtils.copy(in, response.getOutputStream());
----------------------------
IO�쳣��רҵ����			|
----------------------------
	# IOException

		



