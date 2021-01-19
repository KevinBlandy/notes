import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
/**
 * 使用JAXP来实现对XML文件的CURD
 * */
public class DocumentBuilderDemo
{
	private static File file = new File("D:\\Demo.xml");
	public static void main(String[] args) throws Exception
	{
//		serch1();
//		select();
//		add();
//		change();
//		delete();
//		show();
	}
	/**
	 * 查询操作(查询所有)
	 * */
	public static void serch1() throws Exception
	{
		//1,创建解析器对象
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document = db.parse(file);
		//得到所有的指定名称的标签对象集合
		NodeList list = document.getElementsByTagName("name");
		for(int x = 0;x < list.getLength();x++)
		{
			//挨个获取标签
			Node node = list.item(x);
			//获取标签里面的内容
			String str = node.getTextContent();
			System.out.println(str);
		}
	}
	/**
	 * 查询操作(指定查询)
	 * */
	public static void select()throws Exception
	{
		//1,创建解析器
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document = db.parse(file);
		//3,得到所有指定名称的元素
		NodeList list = document.getElementsByTagName("name");
		//使用下标来获得第一个元素
		Node node = list.item(0);
		System.out.println("第一个name元素的值是:"+node.getTextContent());
	}
	/**
	 * 添加节点操作
	 * */
	public static void add()throws Exception
	{
		//1,创建解析器对象
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document = db.parse(file);
		//3,获取需要操作的节点对象(第一个节点)
		Node node = document.getElementsByTagName("p").item(0);
		//4,创建需要被添加的标签对象
		Element ele = document.createElement("gender");
		//5,创建文本
		Text text = document.createTextNode("男");
		//6把文本添加到标签中
		ele.appendChild(text);
		//7,把标签添加到节点对象中
		node.appendChild(ele);
		/**---以上操作都是在内存中进行,并没有写在文件中,下面进行回写操作--**/
		//8,得到回写类(通过工厂)
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		//9,执行回写(把内存中的Dcoument对象传递作为参数对象的参数)
		tf.transform(new DOMSource(document), new StreamResult(file));
	}
	/**
	 * 修改节点内容
	 * */
	public static void change()throws Exception
	{
		//1,创建解析器对象
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document =db.parse(file);
		//3,获取要修改的节点对象(第一个名为gender的标签)
		Node node = document.getElementsByTagName("gender").item(0);
		//4,对指定节点的值进行修改
		node.setTextContent("女");
		//5,很重要---执行回写
		thrans(document,file);
	}
	/**
	 * 删除指定节点操作
	 * */
	public static void delete()throws Exception
	{
		//1,获取解析器对象
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document = db.parse(file);
		//3,获取需要被删除的节点(也就是第一个名为gender的标签)
		Node node = document.getElementsByTagName("gender").item(0);
		//4,获取被删除节点的父节点
		Node parNode = node.getParentNode();
		//5,通过父节点来删除子节点
		parNode.removeChild(node);
		//回写,很重要
		thrans(document,file);
	}
	/**
	 * 遍历节点
	 * */
	public static void show()throws Exception
	{
		//1,得到解析器对象
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//2,解析xml文件
		Document document = db.parse(file);
		//3,调用专门遍历Document的方法进行遍历
		list(document);
	}
	/**
	 * 递归遍历节点方法
	 * */
	public static void list(Node node)
	{
		//判断类型,不打印空格以及回车
		if(node.getNodeType() == Node.ELEMENT_NODE)
		{
			//打印元素名称
			System.out.println(node.getNodeName());
		}
		//得到第一层子节点
		NodeList list = node.getChildNodes();
		//遍历
		for(int x = 0;x < list.getLength();x++)
		{
			Node n = list.item(x);
			list(n);
		}
	}
	/**
	 * 数据回写类
	 * */
	public static void thrans(Document doc,File fie)throws Exception
	{
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.transform(new DOMSource(doc), new StreamResult(fie));
	}
}
/*	测试的XML文件原型,请放置在D盘,取名Demo(也可以修改源代码)

<?xml version="1.0" encoding="UTF-8"?>
<person>
	<p>
		<name>kevin</name>
		<age>21</age>
	</p>
	<p>
		<name>Litch</name>
		<age>22</age>
	</p>
</person>

*/