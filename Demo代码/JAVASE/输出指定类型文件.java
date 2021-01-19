/*
1,对指定的目录进行递归
2,获取递归过程所有的java文件路劲
3,将这些路劲存储到集合中
4,将集合中的数据写入到一个文件中
*/
import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args)throws IOException
	{
		//创建用于保存记录的文件夹
		File fi = new File("D:\\TXT文件集合.txt");
		if(fi.exists())
		{
			System.out.println("文件已存在，重新设置");
			throw new RuntimeException();
		}
		else
		{
			fi.createNewFile();
		}
		//创建需要被遍历查找的文件夹
		File f = new File("D:\\Program Files");
		//创建集合容器用于保存已经找到的File类型的文件
		ArrayList<File> al = new ArrayList<File>();
		fileToList(f,al);
		writeToFile(al,fi);
		
	}
	public static void writeToFile(List<File> list,File f)throws IOException
	{
		BufferedWriter bufw = null;
		try
		{
			bufw = new BufferedWriter(new FileWriter(f));
			for (File fis : list )
			{
				String str = fis.getAbsolutePath();
				bufw.write(str);
				bufw.newLine();
				bufw.flush();
			}
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if(bufw != null)
				{
					bufw.close();
				}
			}
			catch (IOException e)
			{
				throw e;
			}
		}
	}
	public static void fileToList(File dir,List<File> list)
	{
		File[] files = dir.listFiles();
		for (File file : files )
		{
			if (file.isDirectory())
			{
				fileToList(file,list);
			}
			else
			{
				if(file.getName().endsWith(".txt"))
				{
					list.add(file);
				}
			}
		}
	}
}