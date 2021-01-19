	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		/**
		 * 两个头,一个流
		 * 1,Content-Type
		 * 2,Content-Disposition
		 * 流,下载文件的数据
		 * */
		//目标文件
		String fileName = "D:\\PureCodec20150603.exe";
		//目标文件对象
		File f = new File(fileName);
		//通过文件名称获取文件类型
		String contentType = this.getServletContext().getMimeType(fileName);
		//对文件名进行ISO编码处理,防止客户端下载框中名称乱码
		String framName = new String("安装包.exe".getBytes("GBK"),"ISO-8859-1");
		//创建下载框文件名称
		String contentDisposition = "attachment;filename=" + framName;
		//创建响应头
		response.setHeader("Content-Type",contentType);
		response.setHeader("Content-Disposition",contentDisposition );
		//获取通向客户端的字节流对象
		OutputStream out = response.getOutputStream();
		//与文件建立字节流对象
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
		IOUtils.copy(in, out);//IO工具包里面的东西,会自动把in的数据读取到out
		in.close();
	}