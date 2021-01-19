import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
public class Demo
{
	public static void main(String[] args) throws Exception
	{
//		selectAll();//查询所有name元素
//		selectSin();//查询指定元素
//		addSex();//添加"Sex"元素
//		modifySex();//修改指定元素
//		deleteSex();//删除指定元素 
		showAllElement();//遍历所有节点
	}
	public static void selectAll() throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,得到所有的name元素
		 *5,返回集合，遍历集合，得到每一个name元素。
		 * */
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("name");
		for(int x = 0;x < list.getLength();x++)
		{
			Node node = list.item(x);
			String value = node.getTextContent();
			System.out.println(value);
		}
	}
	public static void selectSin()throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,得到所有的name元素,返回的是一个集合
		 *5,通过集合的下标来获取指定的元素
		 *6,通过getTextContent();来获取具体的值！
		 * */
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("name");
		Node node = list.item(0);//获取第一个元素
		String value = node.getTextContent();
		System.out.println(value);
	}
	public static void addSex()throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,得到第一个p1
		 *		--得到所有的p1,使用item方法得到第一个
		 *5,创建Sex标签-createElement
		 *6,创建文本-createTextNode
		 *7,把文本添加到Sex下面-appendChild
		 *8,把这个Sex添加到第一个p1下面
		 *9,回写文件
		 *	--把内存中生成的标签节点写入磁盘文件
		 * */
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("p1");
		Node node = list.item(0);//得到第一个p1节点
		Element sex1 = document.createElement("sex");//创建标签
		Text text1 = document.createTextNode("女");//创建文本
		sex1.appendChild(text1);//把文本放入标签
		node.appendChild(sex1);//把标签放入节点
		/***以上操作只会影响内存，而不能影响磁盘xml文件***/
		//创建Transformer工厂类
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		//通过工厂类获得Transformer的对象
		Transformer transformer = transformerFactory.newTransformer();
		//通过transform来回写文件存入指定的目录
		transformer.transform(new DOMSource(document),new StreamResult("C:\\Demo.xml"));
	}
	public static void modifySex()throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,得到第一个sex--item方法
		 *5,修改sex方法中的值-setTextContent
		 *6,回写xml
		 **/
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("sex");
		Node sex = list.item(0);
		sex.setTextContent("男");
		/***以上操作只会影响内存，而不能影响磁盘xml文件***/
		//创建Transformer工厂类
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		//通过工厂类获得Transformer的对象
		Transformer transformer = transformerFactory.newTransformer();
		//通过transform来回写文件.存入指定的目录
		transformer.transform(new DOMSource(document),new StreamResult("C:\\Demo.xml"));
	}
	public static void deleteSex()throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,获取sex元素(标签)
		 *5,得到sex的父节点
		 *6,使用父节点，删除sex元素-removeChild();
		 *7,回写xml
		 **/
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		NodeList list = document.getElementsByTagName("sex");
		Node node = list.item(0);//得到第一个sex元素
		Node fuNode = node.getParentNode();//得到父节点
		fuNode.removeChild(node);//删除子节点
		/***以上操作只会影响内存，而不能影响磁盘xml文件***/
		//创建Transformer工厂类
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		//通过工厂类获得Transformer的对象
		Transformer transformer = transformerFactory.newTransformer();
		//通过transform来回写文件.存入指定的目录
		transformer.transform(new DOMSource(document),new StreamResult("C:\\Demo.xml"));
	}
	public static void showAllElement()throws Exception
	{
		/**
		 * 步骤
		 *1,创建解析器工厂
		 *2,根据解析器工厂创建解析器
		 *3,解析xml返回document
		 *4,得到跟节点
		 *5,得到跟节点的子节点
		 *6,得到跟节点的子节点的子节点
		 *... ...-- 递归
		 **/
		File file = new File("D:\\Demo.xml");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		list1(document);
		//编写一个方法(list1)来实现递归(遍历)的操作。
	}
	public static void list1(Node node)
	{
		//判断是元素类型的时候才进行打印
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			//打印node
			System.out.println(node.getNodeName());
		}
		//得到第一层子节点
		NodeList list = node.getChildNodes();
		//遍历list
		for(int x = 0;x < list.getLength();x++)
		{
			//得到每一个节点
			Node n = list.item(x);
			//继续得到n的子节点
			list1(n);
		}
	}
}


















