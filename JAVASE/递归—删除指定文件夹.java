import java.io.*;
public class Demo
{
	public static void main(String[] args)
	{
		File f = new File("D:\\新建文件夹");
		remove(f);
		//System.out.println(f.delete());
	}
	public static void remove(File dir)
	{
		File[] files = dir.listFiles();
		for (int x = 0;x < files.length ;x++ )
		{
			if( !files[x].isHidden() && files[x].isDirectory())
			{
				remove(files[x]);
			}
			else
			{
				System.out.println(files[x].toString()+"|"+files[x].delete());
			}
		}
		System.out.println(dir+"::"+dir.delete());
	}
}