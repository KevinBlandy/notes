import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args)
	{
		File f = new File("F:\\笔记");
		File[] files = f.listFiles();
		ArrayList al = new ArrayList();
		String e = ".sql";
		int num = 0;
		for(File fie : files)
		{
			String s = fie.getName();
			if(s.endsWith(e))
			{
				num++;
				al.add(fie);
			}
		}
		System.out.println(e+"类型的文件一共有"+num+"个,分别如下");
		for(int x = 0;x < al.size();x++)
		{
			File fil = (File)al.get(x);
			System.out.print(fil.getName()+"	");
		}
	}
}