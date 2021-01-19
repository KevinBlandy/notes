import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 使用java的SAX方法来操作xml文档
 * */
public class SAXParserDemo 
{
	private static File file = new File("D://Demo.xml");
	public static void main(String[] args) throws Exception 
	{
		test();
	}
	public static void test()throws Exception 
	{
		//1创建解析工厂类
		SAXParser sp = SAXParserFactory.newInstance().newSAXParser();//
		//2,创建自定义类
		MyDefaultHandler mh = new MyDefaultHandler();
		//2解析xml文件
		sp.parse(file, mh);
	}
}
//自定义DefaultHandler类
class MyDefaultHandler extends DefaultHandler
{
	/**
	 * 当解析到一个开始标签的时候执行
	 * */
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException 
	{
		System.out.print("<"+qName+">");
	}
	/**
	 * 当执行到文本内容的时候,包括空格和换行
	 * */
	public void endElement(String uri, String localName, String qName)throws SAXException 
	{
		System.out.print("</"+qName+">");
	}
	/**
	 * 当执行到结束标签的时候执行这个方法
	 * */
	public void characters(char[] ch, int start, int length)throws SAXException 
	{
		System.out.print(new String(ch,start,length));
	}
}
