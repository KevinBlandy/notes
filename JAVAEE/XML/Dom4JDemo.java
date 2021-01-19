import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/**
 * 使用Dom4j操作xml演示
 * */
public class Dom4jDemo 
{
	private static File file = new File("D:\\Demo.xml");
	public static void main(String[] args) throws Exception
	{
//		serch();
//		getFirst();
//		getLast();
//		add();
//		delete();
		getProp();
	}
	/**
	 * 查询所有指定元素中的值(所有name元素的值)
	 * */
	public static void serch()throws Exception
	{
		//1,创建解析器
		SAXReader reader = new SAXReader();
		//2,解析文本
		Document document = reader.read(file);
		//3,得到跟节点
		Element ele = document.getRootElement();
		//4,得到所有的指定名称的标签
		List<Element> list = ele.elements("p");
		//5,遍历所有的标签
		for(Element e : list)
		{
			//获取标签下的指定名称的标签再次进行遍历
			List<Element> l = e.elements("name");
			for(Element ee : l)
			{
				System.out.println(ee.getText());
			}
		}
	}
	/**
	 * 获取指定元素的第一个值(name)
	 * */
	public static void getFirst()throws Exception
	{
		//1,创建解析器
		SAXReader reader = new SAXReader();
		//2,解析文本对象
		Document document = reader.read(file);
		//3,获取跟元素
		Element root = document.getRootElement();
		//3,获取第一个name元素的父节点
		Element ele = root.element("p");
		//4,获取第一个name元素
		Element name = ele.element("name");
		//5,获取第一个name元素的值
		System.out.println(name.getText());
	}
	/**
	 * 获取指定元素的最后一个值(name)
	 * */
	public static void getLast()throws Exception
	{
		//1,创建解析器对象
		SAXReader reader = new SAXReader();
		//2,解析xml文件
		Document document = reader.read(file);
		//3,获取跟节点 
		Element root = document.getRootElement();
		//3,的多所有的p节点
		List<Element> list = root.elements("p");
		//3,获取最后一个p节点
		Element ele = list.get(list.size()-1);
		//,获取最后一个p节点的,name节点
		Element  name = ele.element("name");
		//3,获取最后一个name节点的值
		System.out.println(name.getText());
	}
	/**
	 * 添加节点操作(添加一个gender标签)
	 * */
	public static void add()throws Exception
	{
		//前面几步骤都一样.省略注释了
		Document document = new SAXReader().read(file);
		//获取跟节点
		Element element = document.getRootElement();
		//获取要添加的节点对象,,也就是第二个p节点
		Element ele = (Element) element.elements("p").get(1);
		//添加一个名称gender的子标签
		Element newEle = ele.addElement("gender");
		//为新标签添加文本
		newEle.setText("男");
		/**注意,回写又来了,增删操作一定要记得回写**/
		//创建文本格式,带缩进的
		OutputFormat format = OutputFormat.createPrettyPrint();
		//创建回写类对象
		XMLWriter wr = new XMLWriter(new FileOutputStream(file),format);
		//把内存中的Document回写到文件中
		wr.write(document);
		wr.close();//关闭资源
	}
	/**
	 * 删除节点的操作(删除刚上一个添加的gender)
	 * */
	public static void delete()throws Exception
	{
		//前面几步骤都一样.省略注释了
		Document document = new SAXReader().read(file);
		//获取根节点
		Element element = document.getRootElement();
		//获取第二个p节点
		Element ele = (Element)element.elements("p").get(1);
		//获取要删除的节点
		Element gen = ele.element("gender");
		//用父节点去删除指定节点
		ele.remove(gen);
		//回写操作
		write(document,file);
	}
	/**
	 * 获取指定标签的属性值(第一个P标签上的名为ID的属性值)
	 * */
	public static void getProp()throws Exception
	{
		//调用方法来进行操作了
		Document document = getDocument(file);
		//获得根节点
		Element ele = document.getRootElement();
		//获得第一个P节点
		Element p = ele.element("p");
		//得到该节点的属性值
		String id = p.attributeValue("id");
		System.out.println(id);
	}
	/**
	 * 封装专门获取Document对象方法
	 * */
	public static Document getDocument(File file)throws Exception
	{
		return new SAXReader().read(file);
	}
	/**
	 * 封装专门执行回写操作的类
	 * */
	public static void write(Document doc,File f)throws Exception
	{
		XMLWriter writer = new XMLWriter(new FileOutputStream(f),OutputFormat.createPrettyPrint());
		writer.write(doc);
		writer.close();
	}
}
 