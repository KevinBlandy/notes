------------------------
RandomAccessFile		|
------------------------
	# ���IO
	# ���췽��
		RandomAccessFile(File file, String mode)
		RandomAccessFile(String name, String mode)

		* file/name Ŀ���ļ�
		* mode ioģʽ:"r","w"���

	# ʵ������
		FileChannel getChannel()
			* ��ȡ������channel

		FileDescriptor getFD()

		long getFilePointer()
			* �����ļ���¼ָ��ĵ�ǰλ��

		void seek(long pos)
			* ���ļ���¼ָ�붨λ��pos��λ��

		long length()
			* ��ȡ�ļ��ĳ���
		
		int read() 
			* ��ȡһ���ֽڵ�����

		int read(byte b[])
			* ��ȡ���ݵ� byte[]
		
		void readFully(byte b[]) 
		
		int read(byte b[], int off, int len)
			* ��ȡ���ݵ� byte[]
			* ��byte[]��off��ʼд��,д��len����
		
		String readLine();
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
	
	# �����ļ��ı仯
		// ���ܴ�дȨ��("w"),��Ȼ��������û��д�����ݵ����ļ�
		try(RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\log.log", "r")){
			// ���һ��ָ���λ��,Ĭ�ϴ�ͷ��ʼ
			long lastPointer = 0;
			// ÿһ�ж�ȡ��������
			String line = null;
			// ���������ļ�
			while(true) {
				// �����һ�ζ�ȡ�������ݿ�ʼ��ȡ
				randomAccessFile.seek(lastPointer);
				// ��ȡһ��,�������з�ֹͣ,���������з�
				while((line = randomAccessFile.readLine()) != null) {
					System.out.print(line + "\n");
				}
				// ��ȡ��Ϻ�,��¼���һ�ζ�ȡ��ָ��λ��
				lastPointer = randomAccessFile.getFilePointer();
			}
		}