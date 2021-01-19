import java.io.*;
public class Demo
{
	public static void main(String[] args)
	{
		File f = new File("D:\\Program Files");
		showDir(f);
	}
	public static void showDir(File dir)
	{
		System.out.println(dir);
		File[] files = dir.listFiles();
		for (int x = 0 ; x<files.length ;x++ )
		{
			if(files[x].isDirectory())
			{
				showDir(files[x]);
			}
			else
			{
				System.out.println(files[x]);
			}
		}
	}
}