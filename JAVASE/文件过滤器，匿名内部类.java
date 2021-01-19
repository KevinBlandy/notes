
public static void demo3()
	{
		File dir = new File("D:\\MyJava");
		String[] arr = dir.list(new FilenameFilter()
		{
			public boolean accept(File dir,String name)
			{
				return name.endsWith(".java");
			}
		});
		sop(arr.length);
		for(String name : arr)
		{
			sop(name);
		}
	}