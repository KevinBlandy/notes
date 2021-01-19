大数据
所谓的大数据,就是大的字节数据.或者大的字符数据."标准SQL"中提供了如下类型来保存大数据类型
tinyblob	256B	-- 二进制(字节)
blob		64k
mediumblob	16M
longblob	4G
			-- 字符(MYSQL中没有,这是标准SQL定义的)
				tinyclob	256B
				clob		645KB
				mediumclob	16MB
				longclob	4G
			-- 这个才是MYSQL中定义的大数据字符类型
tinytext	256B
text		64KB
mediumtext	16MB
longtext	4G

一般,往MYSQL中存入过大数据的时候需要在在my.ini中最后添加配置
max.allowed packet=10485760
	表示设置MYSQL的数据存储大小


Blob blob = new SeriaBlob(byte[] bytes);
该对象用来操作大数据

存---------------------------------------------------------------
	public static void set()throws Exception 
	{
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into tab_bin values(?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 1);//数据库ID字段
		pstmt.setString(2, "测试.mp3");//名称
		/**
		 * 需要得到Blob对象
		 * 1,有文件,目标是Blob
		 * 3,把文件变成byte[]数组
		 * */
		File f = new File("D:\\Demo.mp3");
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
		byte[] bytes = new byte[in.available()];//创建文件大小的字节数组
		in.read(bytes);						
		Blob blob = new SerialBlob(bytes);
		pstmt.setBlob(3, blob);//二进制文件
		pstmt.executeUpdate();
	}
取---------------------------------------------------------------
	public static void get()throws Exception 
	{
		//获得连接对象
		Connection conn = JDBCUtils.getConnection();
		String sql = "select * from tab_bin";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//pstmt执行查询得到ResultSet
		ResultSet rs = pstmt.executeQuery();
		//获取rs中名为Date的列数据
		if(rs.next())
		{
			Blob blob = rs.getBlob("date");
			/**
			 * 把 blob变回硬盘上的文件
			 * */
			InputStream in = blob.getBinaryStream();
			BufferedOutputStream bufr = new BufferedOutputStream(new FileOutputStream(new File("C:\\test.mp3")));
			byte[] b = new byte[1024];
			int len = 0;
			while((len = in.read(b)) != -1)
			{
				bufr.write(b, 0, len);
				bufr.flush();
			}
		}
	}