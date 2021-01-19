import java.io.*;
import java.util.*;
public class Demo
{
	public static void main(String[] args) throws IOException
	{
		File f = new File("D:\\Info.ini");
		BufferedInputStream bufi = new BufferedInputStream(new FileInputStream(f));
		Properties prop = new Properties();
		prop.load(bufi);
		String str = prop.getProperty("Time");
		int num = 0;
		if(str!=null)
		{
			num = Integer.parseInt(str);
			if(num >= 10)
			{
				System.out.println("使用次数已达"+num+"次");
				return;
			}
		}
		num++;
		prop.setProperty("Time",num+"");
		BufferedOutputStream bufo = new BufferedOutputStream(new FileOutputStream(f));
		prop.store(bufo, "KevinBlandy");
		bufi.close();
		bufo.close();
	}
}