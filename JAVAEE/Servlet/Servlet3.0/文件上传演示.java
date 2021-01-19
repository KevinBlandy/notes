package com.kevin.demo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 * Servlet3.0之,上传演示
 * */
@WebServlet(urlPatterns="/NewServlet")
@MultipartConfig
public class NewServlet extends HttpServlet 
{
	/**
	 * 必须是doPost方法 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");//处理乱码问题,主要是要截取字符串(文件名称)的原因
		//1,getParameter();可以使用了.获取普通表单项
		String username = request.getParameter("username");
		//2,获取文件表单项,表单字段
		Part p = request.getPart("pic");//参数就是文件表单字段的名称,再啰嗦一次,绝对不是文件的名称
		//3,从p中获取需要的数据
		System.out.println(p.getContentType());//获取文件类型
		System.out.println(p.getSize());//获取上传文件的大小,字节数
		System.out.println(p.getName());//获取上传文件字段的名称
		String fileName = p.getHeader("Content-Disposition");//获取指定请求头信息,包含了上传文件的名称..注意,这里就是文件的名称
		//文件名称截取
		int index = fileName.lastIndexOf("filename=\"")+10;
		fileName.substring(index, fileName.length()-1);
		//4,保存文件
		p.write("c:\\demo.jpg");
	}
}
