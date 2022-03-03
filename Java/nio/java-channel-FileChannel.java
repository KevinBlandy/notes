--------------------------
FileChannel				  |
--------------------------
	# Java NIO�е�FileChannel��һ�����ӵ��ļ���ͨ��������ͨ���ļ�ͨ����д�ļ���
	# FileChannel�޷�����Ϊ������ģʽ��������������'����ģʽ��'
	# ��ȡ FileChannel
		* FileChannel ��̬����
			FileChannel.open(Path path, OpenOption... options);
			FileChannel.open(Path path, Set<? extends OpenOption> options,FileAttribute<?>... attrs)
		* ������ķ���
			* InputStream,OutputStream,RandomAccessFile
			* getChannel(); ����,���Ǵ�����Щ������

	# ��ʹ�� FileChannel ֮ǰ�������ȴ�������Ҫͨ��ʹ��һ�� InputStream , OutputStream �� RandomAccessFile ����ȡһ��FileChannelʵ��
		* getChannel(); ����,���Ǵ�����Щ������
			RandomAccessFile file = new RandomAccessFile("E:\\Main.java", "rw");
			FileChannel fromChanne = file.getChannel();

		* �� InputStream ��ȡ Channel
			ReadableByteChannel readableChannel = Channels.newChannel(InputStream in);
		* �� OutputStream ��ȡ Channel
			

		
	
	# �� FileChannel �ж�ȡ����
		ByteBuffer buf = ByteBuffer.allocate(48);		//׼��һ��48�ֽڵ�buffer
		int bytesRead = inChannel.read(buf);			//��ͨ���е����ݶ���buffer��
		*��read()�������ص�intֵ��ʾ���ж����ֽڱ�������Buffer�С��������-1����ʾ�����ļ�ĩβ��

	# �� FileChannel д����
		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();					//����buffer,ָ���ƶ���0
		buf.put(newData.getBytes());	//�������
		buf.flip();						//�޸Ķ�/дģʽ,ָ���Ƶ�0
		while(buf.hasRemaining()) {
			channel.write(buf);
		}
		* ʹ��FileChannel.write()������FileChannelд���ݣ��÷����Ĳ�����һ��Buffer
		* ע��FileChannel.write()����whileѭ���е��õġ�
		* ��Ϊ�޷���֤write()����һ������FileChannelд������ֽڣ������Ҫ�ظ�����write()������ֱ��Buffer���Ѿ�û����δд��ͨ�����ֽڡ�
	
	# �ر� FileChannel
		channel.close();
		* ����FileChannel����뽫��ر�

	# ��ʾһ�����ļ��л�ȡ Channel ��ʵ��
		RandomAccessFile file = new RandomAccessFile("E:\\Main.java","rw");		
        FileChannel fileChannel = file.getChannel();		//���ļ���ȡ��
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);	//����1MB�Ļ�����
        int len = fileChannel.read(byteBuffer);				//ͨ���ܵ�������д�뻺����
        while (len != -1){							
            byteBuffer.flip();								//��λ,��ʼ��ȡ����
            while (byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();								//���ݶ�ȡ���,��λ�û�����׼��д��
            len = fileChannel.read(byteBuffer);				//ͨ���ܵ�������д�뻺����
        }
        file.close();		//�ر� RandomAccessFile ��رն�Ӧ�� FileChannel

-------------------------------
scatter							|
-------------------------------
	# Scattering Reads��ָ���ݴ�һ��channel��ȡ�����buffer�С�����ͼ������
					|-> Buffer
		Channel ->	|-> Buffer
					|-> Buffer
	
	# read()��������buffer�������е�˳�򽫴�channel�ж�ȡ������д�뵽buffer����һ��buffer��д����channel����������һ��buffer��д��
	# Scattering Reads'���ƶ���һ��bufferǰ������������ǰ��buffer'����Ҳ��ζ�����������ڶ�̬��Ϣ(����ע����Ϣ��С���̶�)��
	# ���仰˵�����������Ϣͷ����Ϣ�壬��Ϣͷ���������䣨���� 128byte����Scattering Reads��������������

	# ����Demo
		ByteBuffer header = ByteBuffer.allocate(128);		//һ��Buffere
		ByteBuffer body   = ByteBuffer.allocate(1024);		//��һ��Buffere
		ByteBuffer[] bufferArray = { header, body };		//������Buffer�ϲ�Ϊһ��Buffer����
		channel.read(bufferArray);							//��Channle�ж�ȡ���ݵ���ЩBuffer
	
-------------------------------
gather							|
-------------------------------
	# Gathering Writes��ָ���ݴӶ��bufferд�뵽ͬһ��channel������ͼ������
					<-| Buffer
		Channel <-	<-| Buffer
					<-| Buffer

	# write()�����ᰴ��buffer�������е�˳�򣬽�����д�뵽channel��ע��ֻ��position��limit֮������ݲŻᱻд�롣
	# ���һ��buffer������Ϊ128byte�����ǽ�������58byte�����ݣ���ô��58byte�����ݽ���д�뵽channel�С�
	# �����Scattering Reads�෴��Gathering Writes�ܽϺõĴ���̬��Ϣ��

	# ����Demo
		ByteBuffer header = ByteBuffer.allocate(128);		//һ��Buffer
		ByteBuffer body   = ByteBuffer.allocate(1024);		//��һ��Buffer
		ByteBuffer[] bufferArray = { header, body };		//������Buffer�ϲ�Ϊһ������
		channel.write(bufferArray);							//�����ݴ�Buffre��д�뵽channel

-------------------------------
 ͨ��֮������ݴ���				|
-------------------------------
	# �������ͨ������һ���� FileChannel ���������ֱ�ӽ����ݴ�һ��channel,���䵽����һ��channel��

	# ��Ŀ�� Channel �����ݶ�ȡ�� this Channel ��
		* Demo
			RandomAccessFile fromFile = new RandomAccessFile("E:\\Main.java", "rw");			
			FileChannel      fromChannel = fromFile.getChannel();				//ԴChnnel
			RandomAccessFile toFile = new RandomAccessFile("E:\\Main1.java", "rw");
			FileChannel      toChannel = toFile.getChannel();					//Ŀ��Chnnel
			long position = 0;													//ָ��Ϊ0
			long count = fromChannel.size();									//��ȡ����Դchannel���ܴ�С��������
			toChannel.transferFrom(fromChannel, position, count);				//ִ�����Դ���

		* ��Դͨ����ʣ��ռ�С�� count ���ֽڣ�����������ֽ���ҪС��������ֽ�����		
		* ��SoketChannel��ʵ���У�'SocketChannelֻ�ᴫ��˿�׼���õ�����(���ܲ���count�ֽ�)'��	//SocketChannel������
		* ���,SocketChannel ���ܲ��Ὣ�������������(count���ֽ�) ȫ�����䵽FileChannel�С�

	# �� this Channel �е�����д�뵽 Ŀ�� Channel ��
		* Demo
			RandomAccessFile fromFile = new RandomAccessFile("E:\\Main.java", "rw");
			FileChannel      fromChanne = fromFile.getChannel();
			RandomAccessFile toFile = new RandomAccessFile("E:\\ooooo.java", "rw");
			FileChannel      toChannel = toFile.getChannel();
			long position = 0;
			long count = fromChanne.size();
			fromChanne.transferTo(position, count, toChannel);
		* ���˵��÷�����FileChannel����һ���⣬�����Ķ�һ����
		* ����SocketChannel��������transferTo()������ͬ�����ڡ�
		* 'SocketChannel��һֱ��������ֱ��Ŀ��buffer������'��

	# �㿽��һ����ֻ�ܴ���2147483647�ֽ����ݣ�Ҳ����2GB - 1KB
		@GetMapping("/download")
		public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {

			// ���ص��ļ�3.67 GB
			Path file = Paths.get("E:\\��Ӱ\\NothingtoLose\\NothingtoLose.mkv"); //

			String contentType = Files.probeContentType(file);
			if (contentType == null) {
				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
			}

			try (FileChannel fileChannel = FileChannel.open(file)) {
				
				long size = fileChannel.size();
				
				log.info("�ļ���С: {}", size);

				response.setContentType(contentType);
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
						.filename(file.getFileName().toString(), StandardCharsets.UTF_8).build().toString());
				response.setContentLengthLong(size);

				/**
				 * transferTo һ�������ֻ�ܴ���2147483647�ֽ����ݡ�
				 * ������Ҫ��ε���
				 */
				
				long position = 0; 
				
				while (size > position) {
					long count = fileChannel.transferTo(position, size - position, Channels.newChannel(response.getOutputStream()));
					if (count > 0) {
						position += count;
					}
				}
			}
		}

-------------------------------
FileChannel-�ڴ��ļ�ӳ��		|
-------------------------------
	# MappedByteBuffer map(MapMode mode,long position, long size);


-------------------------------
FileChannel-API					|
-------------------------------
	int read(ByteBuffer buffer);
		* �����ݴ� FileChannel ��ȡ��Buffer�С�
		* read()�������ص�intֵ��ʾ���ж����ֽڱ�������Buffer�С��������-1����ʾ�����ļ�ĩβ��
	int write(ByteBuffer src);
		* �� src �е�����д�뵽 FileChannel ��
	long transferFrom(ReadableByteChannel src,long position, long count);
		* �� src ͨ���е�����д�뵽 this ͨ��,�� position ��ʼд,д count ����
		* ���� long,��ʾд���˶�������
	
	long transferTo(ReadableByteChannel src,long position, long count);
		* �� this ͨ���е�����д�뵽 src ͨ��,�� position ��ʼд,д count ����
		* ���� long,��ʾд���˶�������

	long position();
		* ��ȡFileChannel�ĵ�ǰλ��
		* �����λ���������ļ�������֮��Ȼ����ͼ���ļ�ͨ���ж�ȡ���ݣ�������������-1 ���� �ļ�������־��

	void position(long position);
		* ����FileChannel��λ��
	
	long size();
		* ���ع����ļ��Ĵ�С
	
	FileChannel truncate(long size);
		* ��ȡһ���ļ�����ȡ�ļ�ʱ���ļ�����ָ�����Ⱥ���Ĳ��ֽ���ɾ��
		* size(�ֽ�)���,�ͽ�ȡ���,����Ķ���Ҫ
	
	void force(boolean flag);
		* ��ͨ������δд����̵�����ǿ��д��������
		* �������ܷ���Ŀ��ǣ�����ϵͳ�Ὣ���ݻ������ڴ��У������޷���֤д�뵽FileChannel�������һ���ἴʱд�������ϡ�Ҫ��֤��һ�㣬��Ҫ����force()������
		* boolean���͵Ĳ�����ָ���Ƿ�ͬʱ���ļ�Ԫ���ݣ�Ȩ����Ϣ�ȣ�д�������ϡ�
	
	channel.close();
		* �ر� Channel

	
	FileLock tryLock(long position, long size, boolean shared)
	FileLock fileChannel.tryLock();
		* �õ��ò�������,ֱ�ӻ�ȡ��,�����������,����null
		* ��ȡ�ļ�������
			position
			size
				* ��������ָ��Ҫ�����Ĳ��֣�����ֻ���ļ��Ĳ������ݼ���
			shared
				* ָ���Ƿ��ǹ�������
				* ���ָ��Ϊ�����������������̿ɶ����ļ������н��̾�����д���ļ������ĳ������ͼ�Դ��ļ�����д���������׳��쳣��
		* demo
			FileLock lock = fileChanne.lock();		//��ȡ�ļ���
			lock.release();							//�ͷ���
	
	FileLock lock(long position, long size, boolean shared)throws IOException;
	FileLock fileChannel.lock(); 
		* ͬ��
		* �õ��û�����,ֱ����ȡ����

	MappedByteBuffer map(MapMode mode,long position, long size)
		* �ѵ�ǰChannelӳ�䵽�ڴ�
		* mode ָ����ʽ(ֻ��,��д....),position ָ���ļ��ܵ��Ŀ�ʼλ��,size ָ������λ��
		* MappedByteBuffer �� ByteBuffer ������

-------------------------------
FileChannel-�ڲ���̬��			|
-------------------------------
	MapMode
		READ_ONLY
			* ֻ��,���д�������쳣

		READ_WRITE
			* ��д,�κ��޸Ķ�����ĳ��ʱ�䱻д���ļ�ϵͳ

		PRIVATE
			* ��д,�����������д�뵽�ļ�ϵͳ
