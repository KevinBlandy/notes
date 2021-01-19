package com.kevin.mail;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
public class Demo1
{
	public static void main(String[] args) throws Exception
	{
		test1();
		test2();
	}
	//发送普通邮件
	public static void test1()
	{
		//得到Session
		Properties prop = new Properties();
		//设置服务器主机名
		prop.setProperty("mail.host", "smtp.qq.com");
		//设置需要认证
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
	//发送带有附件的邮件
	public static void test2() throws IOException
	{
		//得到Session
		Properties prop = new Properties();
		//设置服务器主机名
		prop.setProperty("mail.host", "smtp.qq.com");
		//设置需要认证
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
			/***********************包含附件**************************************/
			/**
			 * 当发送包含附件的邮件时,邮件体就为多部件形式
			 * */
			//创建多部件主题
			MimeMultipart list = new MimeMultipart();
			//创建MimeBodyPart
			MimeBodyPart part1 = new MimeBodyPart();
			//设置部件的内容
			part1.setContent("这是一封带有附件的邮件","text/html;charset=utf-8");
			//把部主体部件添加到集合中
			list.addBodyPart(part1);
			//在创建一个MimeBodyPart
			MimeBodyPart part2 = new MimeBodyPart();
			//设置部件的内容 --- 附件文件
			part2.attachFile(new File("D:\\test.txt"));
			//显示在附件上,并且处理名称乱码问题
			part2.setFileName(MimeUtility.encodeText("文件名称.txt"));
			//再次把主主体部件,添加到集合中
			list.addBodyPart(part2);
			//设置给邮件作为邮件的内容
			msg.setContent(list);
			/******************************************************************/
			//发送邮件
			Transport.send(msg);
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
