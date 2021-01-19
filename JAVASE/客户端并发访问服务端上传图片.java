/*
 *客户端：
 *1，服务端点
 *2，读取客户端已有的图片数据 
 *3，通过 socket 输出流发送给服务端
 *4，读取读服务端反馈信息
 * */
import java.io.*;
import java.net.*;
class PicClient
{
	public static void main(String[] args)throws Exception
	{
		if(args.length != 1)
		{
			System.out.println("请选择一个jpg格式的图片");
			return;
		}
		File file = new File(args[0]);
		if (!(file.exists() && file.isFile()))
		{
			System.out.println("该文件要么不存在，要么是目录");
			return;
		}
		if (!(file.getName().endsWith(".jpg")))
		{
			System.out.println("文件格式错误。不是jpg");
			return;
		}
		if (file.length() > 1024*1024*5)
		{
			System.out.println("文件过大。不接受");
			return;
		}

		Socket s = new Socket("192.168.131.166",10000);//创建socket服务。指向连接地址
		FileInputStream fis = new FileInputStream(file);//创建字节输入流。读取硬盘文件
		OutputStream out = s.getOutputStream();//获取socket中的输出流
		byte[] buf = new byte[1024];//创建缓冲区
		int len = 0;
		while((len = fis.read(buf)) != -1)
		{
			out.write(buf,0,len);
		}
		s.shutdownOutput();//结束标记
		InputStream in = s.getInputStream();
		byte[] bufIn = new byte[1024];
		int num = in.read(bufIn);
		System.out.println(new String(bufIn,0,num));
		fis.close();
		s.close();
	}
}
/*
	此服务端有局限性。当A客户端连接上，以后。被服务端获取到。服务端执行具体流程。如果此时B客户端连接进来。
	那么只有等待。客户A的逻辑事物处理完后。才会接收客户端B的处理请求。
	为了可以让多个客户端同时并发访问服务端
	那么服务端最好就是把每个客户端封装到一个单独的线程中。这也就可以同事处理多个客户端请求。

	如何定义线程？
	只要明确了每个客户端要在服务端执行的代码即可。将该代码存入run方法中。
*/
class PicThread implements Runnable
{
	private Socket s;
	PicThread(Socket s)
	{
		this.s = s;
	}
	public void run()
	{
		int counmt = 1;
		String ip = s.getInetAddress().getHostAddress();
		try
		{
			System.out.println(ip+"connected... ...");
			InputStream in = s.getInputStream();//获取客户端的socket中的读取流对象

			File file = new File("C:\\"+ip+"("+counmt+")"+".jpg");
			while(file.exists())
			{
				 file = new File("C:\\"+ip+"("+counmt+++")"+".jpg");
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = in.read(buf)) != -1)
			{
				fos.write(buf,0,len);
			}
			OutputStream out = s.getOutputStream();
			out.write("上传成功".getBytes());
			fos.close();
			s.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(ip+"上传失败");
		}
	}
}
class PicServer
{
	public static void main(String[] args)throws Exception
	{
		ServerSocket ss = new ServerSocket(10000);
		while(true)
		{
			Socket s = ss.accept();//拿到客户端Socket对象
			new Thread(new PicThread(s)).start();
		}
		//ss.close();
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		
	}
}