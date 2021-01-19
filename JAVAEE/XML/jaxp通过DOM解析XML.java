JAVAXP(java API for xml Processing)
是JAVASE的一部分。由一下几个包以及其子包组成
org.w3c.dom
	提供DMO方式解析XML的标准接口
org.xml.sax
	提供sax方式解析xml的标准接口
javax.xml
	提供了解析XML文档的类
-----------------
javax.xml.parsers包中定义了几个工厂类。我们可以调用这些工厂类
得到对XML稳定进行解析的DOM和SAX解析器对象。

-----针对DOM
DocumentBuilder//解析器类
DocumentBuilderFactory//解析器工厂
-----针对SAX
SAXParser//解析器类
SAXParserFactory//解析器工厂
-------------------
DocumentBuilder
	|--抽象类，不能创建对象。要通过它的工厂类 DocumentBuilderFactory 类的 newDocumentBuilder();方法获得它的对象
	|--DocumentBuilderFactory 也是一个抽象类。可以通过它的 newInstance();方法来获取 DocumentBuilderFactory 的实例。
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder doc = factory.newDocumentBuilder();
--
parse(file);
	|--file是xml文档的路径。也可以把file换成 String 类型的表示路径字符串。 
*	|--返回的是一个 Document 接口！可以理解就是xml的对象
		|--Document 如果 Document 中没有找到需要的方法。可以去它的父类 Node 中去寻找！
*			|--getElementsByTagName(String tagname)
				|--返回指定名称元素 的(NodeList 对象)集合！
								|--getLength();
									|--返回节点数.int
								|--item(int index);
									|--返回集合中的第index个项.返回类型是 Node
																			|--appendChild(Node newChild);
																				将节点 newChild 添加到此节点的子节点列表的末尾。如果 newChild 已经存在于树中，则首先移除它。返回的就是这个添加的 Node
																			|--node.getTextContent();
																				以 String 返回此节点及其后代的文本内容。
*			|--createElement(String tagName);
				创建指定类的元素。返回的是一个 Element 对象。创建标签
*			|--createTextNode(String data);
				创建给定指定字符串的 Text 节点。 返回 Text 对象。创建文本
*			|--appendChild(Node newChild);
				把文本添加到标签下面
*			|--getParentNode();
				获取父节点
NodeList list
|--getLength();
	得到集合的长度
|--item(index);
	得到指定位置的元素
for (int x=0;x<list.getLength() ;x++ )
{
	Node node = list.item(x);
	String value = node.getTextContent();
	//此属性返回此节点及其后代的文本内容。
}
----
DocumentBuilder -- 用DOM解析xml的对象
Document -- xml文件对象
NodeList -- 节点对象的集合
Node -- 节点对象
---
	
***************************************jaxp操作***************************************
								/****示例Demo.xml****/
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<person>
<p1>
	<name>Kevin</name>
	<age>21</age>
	<sex>girl</sex>
</p1>
<p1>
	<name>Litch</name>
	<age>22</age>
	<sex>boy</sex>
</p1>
</person>
***************************************查询所有节点***************************************
例：
	查询xml中所有name元素的值
	步骤
	1,创建解析器工厂
	2,根据解析器工厂创建解析器
	3,解析xml返回document
	4,得到所有的name元素
	5,返回集合，遍历集合，得到每一个name元素。
----------------JAVA代码实例
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
/**
 * 实现jaxp操作xml
 * */
public class TestJaxp 
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException 
	{
		File file = new File("D:\\person.xml");
		//创建解析器公厂
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		//创建解析器
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		//解析xml返回Document
		Document document = builder.parse(file);//文件路径
		//得到所有的name元素的集合
		NodeList list = document.getElementsByTagName("name");
		//遍历集合
		for(int x = 0;x < list.getLength();x++)
		{
			Node name = list.item(x);//获得节点对象
			String value = name.getTextContent();//获取这个节点的内容
			System.out.println(value);//打印到控制台
		}
	}
}
***************************************查询指定节点***************************************
步骤：
	1,创建解析器工厂
	2,根据解析器工厂返回解析器
	3,解析xml返回 Document 对象
	4,得到所有的name元素,返回的是一个集合
	5,通过集合的下标来获取指定的元素
	6,通过getTextContent();来获取具体的值！
----------------JAVA代码实例
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
***************************************添加节点(元素)***************************************
使用jaxp添加节点
	*** 在第一个p1一下面添加<sex>男</sex>操作
步骤
	1,创建解析器工厂
	2,根据解析器工厂创建解析器
	3,解析xml返回document
	4,得到第一个p1
			--得到所有的p1,使用item方法得到第一个
	5,创建Sex标签-createElement
	6,创建文本-createTextNode
	7,把文本添加到Sex下面-appendChild
	8,把这个Sex添加到第一个p1下面
	9,回写文件
		--把内存中生成的标签节点写入磁盘文件
----------------JAVA代码实例
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
	//通过transform来回写文件,存放至指定目录
	transformer.transform(new DOMSource(document),new StreamResult("C:\\Demo.xml"));
}

***************************************修改节点(元素)***************************************
修改第一个p1下面的sex。改为男。
步骤：
	1,创建解析器工厂
	2,根据解析器工厂创建解析器
	3,解析xml返回document
	4,得到第一个sex--item方法
	5,修改sex方法中的值-setTextContent
	6,回写xml
----------------JAVA代码实例
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
	sex.setTextContent("男");//修改节点
	/***以上操作只会影响内存，而不能影响磁盘xml文件***/
	//创建Transformer工厂类
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	//通过工厂类获得Transformer的对象
	Transformer transformer = transformerFactory.newTransformer();
	//通过transform来回写文件.存入指定的目录
	transformer.transform(new DOMSource(document),new StreamResult("C:\\Demo.xml"));
	
}
***************************************删除节点(元素)***************************************
删除第一个sex元素
步骤
	1,创建解析器工厂
	2,根据解析器工厂创建解析器
	3,解析xml返回document
	4,获取sex元素(标签)
	5,得到sex的父节点
	6,使用父节点，删除sex元素-removeChild();
	7,回写xml
----------------JAVA代码实例
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
***************************************遍历节点(元素)***************************************
递归操作
步骤
	1,创建解析器工厂
	2,根据解析器工厂创建解析器
	3,解析xml返回document
	4,得到跟节点
	5,得到跟节点的子节点
	6,得到跟节点的子节点的子节点
		 ... ...-- 递归
----------------JAVA代码实例
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
	//否则会把标签之间的空格/ 回车之类的全部打印出来
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