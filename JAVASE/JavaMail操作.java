package com.kevin.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Demo
{
	public static void main(String[] args) 
	{
		test1();
	}
	public static void test1()
	{
		//得到Session
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.smtp.auth", "true");
		Authenticator auth = new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				PasswordAuthentication pass = new PasswordAuthentication("747692844@qq.com","Direct19931209");
				return pass;
			}
		};
		Session session = Session.getInstance(prop,auth);
		//创建MimeMessage
		MimeMessage msg = new MimeMessage(session);
		try 
		{
			//设置发件人信息
			msg.setFrom(new InternetAddress("747692844@qq.com"));
			//设置收件人信息
			msg.setRecipients(RecipientType.TO, "948593493@qq.com");
			//设置抄送
			msg.setRecipients(RecipientType.CC, "747692844@qq.com");
			//设置秘送
			msg.setRecipients(RecipientType.BCC,"10086@qq.com");
			msg.setSubject("这是来自KevinBlandy的测试邮件");//设置标题
			msg.setContent("这就是一封垃圾邮件,哈哈","text/html;charset=utf-8");//设置正文以及类型,编码
			//发送邮件
			Transport.send(msg);
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
