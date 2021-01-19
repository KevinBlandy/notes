import java.io.*;
import java.util.*;
public class Demo
{
	static Scanner s = new Scanner(System.in);
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args)
	{
		start();
	}
	public static void start()
	{
		sop("**************欢迎使用日记系统**************");
		sop("输入write[开始写日记回车保存]quite[退出系统]look[查看日记]");
		while (true)
		{	
			String s1 = s.next();
			if(s1.equals("write"))
			{
				sop("覆盖上次的日记?[yes |   no]");
				String s2 = s.next();
				if(s2.equals("yes"))
				{
					start1();
				}
				else if(s2.equals("no"))
				{
					start2();
				}
				else
				{
					sop("输入错误");
				}
				
			}
			else if(s1.equals("look"))
			{
				look();
			}
			else if(s1.equals("quite"))
			{
				sop("欢迎下次使用");
				break;
			}
			else
			{
				sop("输入有误，请重新输入");
			}
		}
	}
	public static void start1()
	{
		BufferedWriter bufw = null;
		Scanner s = new Scanner(System.in);
		try
		{
			bufw = new BufferedWriter(new FileWriter("D:\\日记.txt"));
			sop("请开始输入日记内容吧.输入exit退出记录系统");
			while(true)
			{
				String riji = s.next();
				if(riji.equals("exit"))
				{
					break;
				}
				bufw.write(riji);
				bufw.newLine();
				bufw.flush();
			}
			sop("记录完毕。退出记录系统");
			start();
		}
		catch (IOException e)
		{
			sop(e.toString());
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
				sop(e.toString());
			}
		}
	}
	public static void start2()
	{
		BufferedWriter bufw = null;
		Scanner s = new Scanner(System.in);
		try
		{
			bufw = new BufferedWriter(new FileWriter("D:\\日记.txt",true));
			sop("请开始输入日记内容吧.输入exit退出记录系统");
			while(true)
			{	
				String riji = s.next();
				if(riji.equals("exit"))
				{
					break;
				}
				bufw.write(riji);
				bufw.newLine();
				bufw.flush();
			}
			sop("记录完毕。退出记录系统。");
			start();
		}
		catch (IOException e)
		{
			sop(e.toString());
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
				sop(e.toString());
			}
		}
	}
	public static void look()
	{
		BufferedReader bufr = null;
		try
		{
			bufr = new BufferedReader(new FileReader("D:\\日记.txt"));
			String s = null;
			while ((s = bufr.readLine()) != null)
			{
				sop(s);
			}
			sop("--------日记显示完毕---------");
			start();
		}
		catch (IOException e)
		{
			sop(e.toString());
		}
		finally
		{
			try
			{
				if (bufr != null)
				{
					bufr.close();
				}
			}
			catch (IOException e)
			{
				sop(e.toString());
			}
		}
	}
}