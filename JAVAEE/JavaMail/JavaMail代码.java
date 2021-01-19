1,导包
mail.jar
activation.jar
-----------核心类
1,Session(得到这个最麻烦)
  * 跟HttpSession不一样
  * 如果你得到了它,表示已经与服务器连接上了,与JDBC的Connection作用相似
  得到Session,需要使用Session.getInstance(Properties prop,Authenticator auth);
  -- Properties	-- 属性文件
     Properties prop = new Properties();
     至少需要设置两样东西:prop.setProperty("mail.host","smtp.163.com");//设置服务器主机名
			 :prop.setProperty("mail.smtp.auth","ture");//设置需要认证

  -- Authenticator(抽象类,需要自己实现)  -- 做认证的,提供这个对象必须要包含用户名信息
	getPasswordAuthentication();主要实现这个方法
	//匿名内部类提现形式
	Authenticator auth = new Authenticator()
	{
		protected PasswordAuthentication getPasswordAuthentication()
		{
			PassWordAuthenticationd pass = new PasswordAuthentication("userName","passWord");
			return pass;
		}
	}
	PassWordAuthenticationd pass = new PasswordAuthentication("userName","passWord");
	//创建这个对象的时候,构造器中的第一个参数表示的是用户名,第二个参数表示的是密码
	【最终表现形式】:
	Session session = Session.getInstance(prop,auth);
2,MimeMessage
  * 表示一个邮件对象,可以调用它的setFrom()之类的方法,设置发件人,设置收件人,设置标题,设置正文
  * MimeMessage msg = new MimeMessage(session);//需要Session对象来创建这个对象
  msg.setFrom(Address address);//【设置发件人信息】
	 -- Address是个抽象类,我们可以创建它熟悉的实现类对象-InternetAddress()
    代码 -- setFrom(new InternetAddress("747692844@qq.com"));
  msg.setRecipients(type,address);//【设置收件人信息】
	 -- RecipientType.TO  第一个参数其实是一个静态成员变量表示是收件人
	 -- 第二个表示的是一个收件地址
    代码 -- msg.setRecipients(RecipientType.TO, "948593493@qq.com");
  msg.setRecipients(RecipientType.CC, "747692844@qq.com");//【设置抄送】
	 -- RecipientType.CC  是一个类成员静态字段表示抄送
    代码 -- msg.setRecipients(RecipientType.CC, "747692844@qq.com")
  msg.setRecipients(RecipientType.CC,"10086@qq.com");//【设置密送】
         -- RecipientType.BCC  是一个类成员静态字段表示密送
    代码 --  msg.setRecipients(RecipientType.BCC,"10086@qq.com");
  msg.setSubject("这是来自KevinBlandy的测试邮件");//【设置标题】
  msg.setContent("这就是一封垃圾邮件,哈哈","text/html;charset=utf-8");//【设置正文以及类型,编码】
3,Transport
  * 它只有一个功能,就是发送邮件
  Transport.send(msg);//发送邮件,直接静态方法,无需创建对象,需要提供MimeMessage对象
  

-----------Transport需要MimeMessage,MimeMessage需要session-----------
发送带有附件的邮件
 * 当发送包含附件的邮件时,邮件体就为多部件形式
	1,创建一个多部件的邮件内容
	MimeMultipart	--其实就是一个集合,用来装载多个主体部件
	2,我们需要创建两个主体部件,一个是文本内容,另一个是附件
	MimeBodyPart    --表示一个部件
	3,把MimeMultipart设置给MimeMessage的内容
