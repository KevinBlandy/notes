	public  void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		/**
		 * 上传三部曲
		 * 1,得到工厂类
		 * 2,通过工厂类创建解析器 
		 * 3,解析 request得到FileItem集合
		 * 4,遍历集合调用其API完成文件的保存
		 * */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		try 
		{
			List<FileItem> list = sfu.parseRequest(request);
			FileItem fil = list.get(2);
			/********************/
			//得到根路径,就是文件保存的路径
			String root = this.getServletContext().getRealPath("/WEB-INF/files");
			/**
			 * 生成两级目录
			 * 1,得到文件名称
			 * 2,得到hachCode
			 * 3,转换成16禁止
			 * 4,获取前两个字母用来生成目录
			 * */
			//获取文件名
			String fileName = fil.getName();
			//处理文件名的绝对路径问题(浏览器差异)
			int index = fileName.lastIndexOf("\\");
			if(index != -1)
			{
				fileName = fileName.substring(index+1);
			}
			//给文件名称添加UUID前缀,处理同名问题
			String saveName = GetUUID.getUUID()+"_"+fileName;
			//获得hashCode
			int hashCode = saveName.hashCode();
			//转换十六进制
			String hex = Integer.toHexString(hashCode);
			//取十六进制前两个字符与root连接一起作为完成目录路径
			File dirFile = new File(root,hex.charAt(0)+"/"+hex.charAt(1));
			// 创建目录链
			dirFile.mkdirs();
			//创建目标目录文件
			File destFile = new File(dirFile,saveName);
			//保存
			try 
			{
				fil.write(destFile);
			} 
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
			/********************/
		} 
		catch (FileUploadException e) 
		{
			e.printStackTrace();
		} 
	}