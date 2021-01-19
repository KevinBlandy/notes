/*
	演示客户端和服务端
一
	1，客户端：浏览器
	2，服务端：自定义
二
	1，客户端：浏览器
	2，服务端：Tomcat服务器
三
	1，客户端：自定义
	2，服务端：Tomcat服务器
*/
import java.io.*;
import java.net.*;
class ServerDemo
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(11000);
		Socket s = ss.accept();
		System.out.println(s.getInetAddress().getHostAddress());
		InputStream in = s.getInputStream();
		byte[] buf = new byte[1024];
		int len = in.read(buf);
		System.out.println(new String(buf,0,len));
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);
		out.println("<font color='read' size='70'>你好，客户端</font>");
		s.close();
		ss.close();
	}
}
//浏览器。输入本机IP地址:端口号

//192.168.131.166
//GET / HTTP/1.1
//Host: 192.168.131.166:11000
//Connection: keep-alive
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0
/*.8
User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko)
Chrome/32.0.1700.107 Safari/537.36
Accept-Encoding: gzip,deflate,sdch
Accept-Language: zh-CN,zh;q=0.8,en;q=0.6*/
