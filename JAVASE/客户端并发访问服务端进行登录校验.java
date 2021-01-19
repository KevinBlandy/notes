import java.io.*;
import java.net.*;
/*
客户端通过键盘录入用户名。服务端对这个用户名进行校验
如果该用户存在。
	在服务端显示XXX已登录
	在客户端显示XXX欢迎登录
如果用户不存在
	在服务器端显示XXX尝试登录
	并在客户端显示XXX该用户不存在
最多尝试登录三次。
*/
class LoginClient
{
	public static void main(String[] args)throws Exception
	{
		Socket s = new Socket("192.168.131.166",10001);
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);
		BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
		for (int x = 0;x < 3 ;x++ )
		{
			String line = bufr.readLine();
			if (line == null)
			{
				break;
			}
			out.println(line);
			String info = bufIn.readLine();
			System.out.println("info---"+info);
			if (info.contains("欢迎"))
			{
				break;
			}
		}
		bufr.close();
		s.close();
	}
}
class UserThread implements Runnable
{
	private Socket s;
	UserThread(Socket s)
	{
		this.s = s;
	}
	public void run()
	{
		String ip = s.getInetAddress().getHostAddress();
		System.out.println(ip+"connected.... ....");
		try
		{
			for (int x = 0;x < 3 ;x++ )
			{
				BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String name = bufIn.readLine();
				if (name == null)
				{
					break;
				}
				BufferedReader bufr = new BufferedReader(new FileReader("D:\\User.txt"));
				PrintWriter out = new PrintWriter(s.getOutputStream(),true);
				String line = null;
				boolean flag = false;
				while ((line = bufr.readLine()) != null)
				{
					if (line.equals(name))
					{
						flag = true;
						break;
					}
				}
				if (flag)
				{
					System.out.println(name+"，已登录");
					out.println(name+",欢迎登录");
					break;
				}
				else
				{
					System.out.println(name+"，尝试登录");
					out.println(name+",用户不存在");
					break;
				}
			}
			s.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(ip+"校验失败");
		}
	}
}
class LoginServer
{
	public static void main(String[] args)throws Exception
	{
		ServerSocket ss = new ServerSocket(10001);
		while (true)
		{
			Socket s = ss.accept();
			new Thread(new UserThread(s)).start();
		}
	}
}
class UserLoginDemo
{
	public static void main(String[] args)
	{
		
	}
}