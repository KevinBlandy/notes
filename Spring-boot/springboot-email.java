-------------------------------
email							|
-------------------------------
	# 导入依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
	



#--------------------------------------
# email	config					
#-------------------------------------

spring:
  mail:
    host: smtp.exmail.qq.com
    username: no-reply@javaweb.io
    password: 123456789
    sender: Javaweb开发者社区
    port: 465
    default-encoding: UTF-8
    protocol: smtps
    properties:
      mail:
        smtp:
          connectiontimeout: 5000
          timeout: 3000
          writetimeout: 5000
          auth: true
          starttls:
            enable: true
            required: true


#--------------------------------------
# MailService					
#-------------------------------------


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeUtility;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.sender}")
    private String sender;

    public void sendHTMLMail(String to,String title,String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(MimeUtility.encodeText(sender) + "<" + this.username + ">"));
        helper.setTo(to);
        helper.setSubject(title);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}


#--------------------------------------
# 自己创建邮件发送对象来发送				
#-------------------------------------
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailTest {
	public static void main(String[] args) throws Exception {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		
		javaMailSenderImpl.setDefaultEncoding("utf-8");

		javaMailSenderImpl.setHost("smtp.163.com");				// host
		javaMailSenderImpl.setPort(465);						// 端口
		javaMailSenderImpl.setUsername("10086@qq.com");			// 账户
		javaMailSenderImpl.setPassword("123456");				// 密码
		javaMailSenderImpl.setProtocol("smtps");				// 协议
				
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");				// 配置项
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.starttls.required", "true");
		
		javaMailSenderImpl.setJavaMailProperties(properties);
		
		// 创建邮件消息
		MimeMessage message = javaMailSenderImpl.createMimeMessage();
		
		// 通过Helper 设置邮件消息内容
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(new InternetAddress(MimeUtility.encodeText("赵信") + "<10086@qq.com>"));
		helper.setTo("10010@qq.com");
		helper.setSubject("你好啊");
		helper.setText("<h3>我是 Judy</h3>", true);
		
		// 添加附件，指定名称，流，ContentType
		helper.addAttachment("ruby.db", () -> Files.newInputStream(Paths.get("D:\\ruby.db")), "	application/octet-stream");
		
		// 发送邮件
		javaMailSenderImpl.send(message);
	}
}