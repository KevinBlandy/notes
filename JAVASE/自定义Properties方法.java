import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args)throws IOException
	{
		//setAndGet();
		//method1();
		loadDemo();
		
	}
	public static void loadDemo() throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("D:\\info.txt");
		prop.load(fis);
		//System.out.println(prop);
		//prop.list(System.out);
		prop.setProperty("Rocco","23");//改变内存的结果
		FileOutputStream fos = new FileOutputStream("D:\\info.txt");
		//prop.list(System.out);
		prop.store(fos, "test");
		prop.list(System.out);
		fos.close();
		fis.close();
	}
	public static void method1()throws IOException
	{
		BufferedReader bufr = new BufferedReader(new FileReader("D:\\info.txt"));
		//BufferedWriter bufw = new BufferedWriter(new FileWriter("D:\\info.txt"));
		String line = null;
		Properties prop = new Properties();
		while((line = bufr.readLine())!=null)
		{
			//System.out.println(line);
			String[] arr = line.split("=");
			prop.setProperty(arr[0],arr[1]);
		}
		bufr.close();
		System.out.println(prop);
	}
	//设置和获取元素
	public static void setAndGet()
	{
		Properties prop = new Properties();
		prop.setProperty("Kevin", "21");
		prop.setProperty("Lich", "21");
	//	System.out.println(prop);
	//	String s =prop.getProperty("Kevin");
	//	System.out.println(s);
		prop.setProperty("Litch", "22");
		Set<String> names = prop.stringPropertyNames();
		for(String name : names)
		{
			System.out.println(name+":"+prop.getProperty(name));
		}
	}
}