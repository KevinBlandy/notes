不多说,直接代码
javax.xml.parsers包中的

SAX是基于事件驱动,边读边解析
SAX不能做增删改操作,只能查询

SAXParser		
SAXParserFactory

跟DOM解析一个德行, SAXParser 抽象类,也是要通过抽象工厂类来获得实例...算了直接代码吧！你能看懂
SAXParser sp = SAXParserFactory.newInstance().newSAXParser();

SAXParser 方法简介
void		parse(File f,DefaultHandler dh);//解析xml文件
			*  File 就是xm,文件的路径
			*  DefaultHandler 是一个事件处理器
void		parse(InputSource is, DefaultHandler dh);//解析xml文件,跟楼上重载出现 

DefaultHandler 方法简介(几乎都是自动执行)
void		startElement(String uri, String localName, String qName, Attributes attributes);//接收元素开始的通知
			> 首先,这个方法只要解析到开始标签的时候的时候执行
			* qName	:表示标签的名称
			* 
void		characters(char[] ch, int start, int length);//接收元素中字符数据的通知。 
			> 当执行到文本的时候就会执行这个方法
			* 其实这三个参数,是 String 的一个构造方法 new String(char[] ch,int,length);
			* 可以返回文本内容
void		endElement(String uri, String localName, String qName);//接收元素结束的通知
			> 当执行到结束标签的时候就会执行这个方法
			* 返回标签的名称
--给个总结还是
当解析到开始标签的时候,执行 startElement();方法,参数qName:返回标签名称
当解析到文本内容的时候,执行 characters();方法,通过String的构造方法返回内容
当解析到结束标签的时候,执行endElement();方法,参数qName,返回标签名称

解析步骤
	1,创建解析器工厂
	2,创建解析器
	3,执行parse();方法
	4,DefaultHandler 需要我们自己写一个类去继承它。然后覆写掉那三个方法

得到所有指定元素值的方法
	> 在自定义类中创建一个标记 boolean flag = false;
	> 在开始方法中用 equals 来判断得到的标签是不是你需要的标签,如果是 flag = true
	> 在characters(); 判断flga是不是true,如果是的话很显然就是你需要的标签的值.然后你就要可以操作这个值,放容器也好.直接打印也好
	> 当执行到结束的时候,把flag = false.
获取指定的第一个标签的值
	> 定义一个成员变量 idx = 1;
	> 在结束方法的时候 idx+1 idx+=
	> 想要打印出第一个指定元素的值
		*  在characters方法里面判断
		--判断flag = true && idx == 1
		那就操作内容
