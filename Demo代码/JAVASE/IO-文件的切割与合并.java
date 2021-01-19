import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args) throws IOException
	{
		splitFile();	//切割文件
		merge();		//合并文件
	}
	public static void splitFile() throws IOException
	{
		FileInputStream fis = new FileInputStream("D:\\aaa.ppt");//一个字节读取流与源文件相关联。
		FileOutputStream fos = null;//声明一个字节输出流。
		int num = 1;//声明一个变量，保证文件的独立性，不被覆盖
		byte[] buf = new byte[1024*1024];//缓冲区
		int len = 0;//读取的数据长度
		while((len = fis.read(buf)) != -1)
		{
			fos = new FileOutputStream("C:\\"+(num++)+".part");//创建一个文件（被切割的碎片）
			fos.write(buf,0,len);//写出文件
			fos.close();
		}
		fis.close();
	}
	public static void merge()throws IOException
	{
		Vector<InputStream> v = new Vector<InputStream>();//创建容器
		v.add(new FileInputStream("D:\\1.part"));//把碎片的读取流添加到容器
		v.add(new FileInputStream("D:\\2.part"));
		v.add(new FileInputStream("D:\\3.part"));
		v.add(new FileInputStream("D:\\4.part"));
		v.add(new FileInputStream("D:\\5.part"));
		v.add(new FileInputStream("D:\\6.part"));
		v.add(new FileInputStream("D:\\7.part"));
		v.add(new FileInputStream("D:\\8.part"));
		Enumeration<InputStream> en = v.elements();//容器读取迭代器
		SequenceInputStream  sis = new SequenceInputStream(en);//把迭代器作为参数传递给合并流
		
		OutputStream fos = new FileOutputStream("C:\\text.ppt");//创建目的文件
		byte[] buf = new byte[1024];//缓冲区
		int len = 0;
		while((len = sis.read(buf)) != -1)
		{
			fos.write(buf,0,len);//写入数据
			fos.flush();//刷新
		}
		fos.close();
		sis.close();
	}
}