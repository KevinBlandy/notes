package Demo;
import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args) throws IOException
	{
		Properties prop = new Properties();
		File file = new File("D:\\Connt.ini");//创建配置文件
		if(!(file.exists()))//判断目标文件是否存在。如果不存在就建立、确保下面的IO流不会报异常
		{
			file.createNewFile();
		}
		FileInputStream fis = new FileInputStream(file);
		prop.load(fis);//获取配置文件信息
		int count = 0;
		String value = prop.getProperty("time");//根据time键。获取对应的值。
		if(value!=null)
		{
			count = Integer.parseInt(value);
			if(count >= 5)
			{
				System.out.println("你好，您的免费使用次数已到。拿钱");.//满足了使用次数的操作。
				return;
			}
		}
		count++;
		prop.setProperty("time",count+"");//修改指定键的值。
		FileOutputStream fos = new FileOutputStream(file);
		prop.store(fos,"注释信息");//写入输出流(配置文件输出流)--源文件已经被修改过。
		fos.close();
		fis.close();
	}
}