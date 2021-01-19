JAVA解析xml文档
JAXP是属于JAVASE的一部分,下面是解析的相关类
org.w3c.dom	--	提供DOM方式解析XML的标准接口
org.xml.sax	--	提供SAX方式解析XML的标准接口
javax.xml	--	提供了解析XML文档的类

javax.xml.parsers
	> 针对于DOM解析
	DocumentBuilder
	DocumentBuilderFactory
	> 针对于SAX解析	//暂不做介绍
	SAXParser
	SAXParserFactory
-----------------------------------
	DocumentBuilder 是一个抽象类,想要获取它的实例需要它的工厂类 DocumentBuilderFactory 类实例来获取！
但是 DocumentBuilderFactory 也是一个抽象类！
非常恶心。这时我就需要先通过 DocumentBuilderFactory 的静态方法newInstance();来获取工厂类对象！

1,获取解析器对象
DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();//希望你能看懂
2,常用方法介绍
Document	parse(File f);//解析指定的xm.文件,返回一个Document对象,Dcument会在稍后介绍
Document	parse(String url);//解析指定路径的xml文件,返回一个Document对象
Document	parse(InputStream in);//解析指定文件,返回一个Document对象.重载形式出现还有一些,需要自己查询API不再一一列举

3,Document 方法简介
Document,其实就是代表了xml这个文档对象！(位于:org.w3c.dom包下,千万别乱导),是一个接口。继承于 Node 接口！
如果在 Document 中没有找到需要的方法,可以尝试去 Node 里面去寻找
			getElementById(String elementId);//
NodeList	getElementsByTagName(String tagname);//返回的是这个文档对象中的所有指定名的标签,返回的是一个 NodeList集合 
Element		createElement(String tagName);//创建一个标签
Text		createTextNode(String data);//创建文本
Node		appendChild(Node newChild);//来自父类Node的方法,把这个文本标签添加到标签后面 
Node		removeChild(Node oldChild);//从子节点列表中移除 oldChild 所指示的子节点，并将其返回。 
Node		getParentNode();//返回此节点的父节点,如果刚创建节点且尚未添加到树，或如果已经从树中移除了它，此值为 null。 
Node		getFirstChild();//返回这个节点的第一个子节点,如果没就返回null
Node		getLastChild();//返回这个极点的最后一个节点,如果没有就返回null
Node		cloneNode();//返回此节点的副本，即允当节点的一般复制构造方法。

4,Node 简介
String		getTextContent();//得到这个元素中的值
Node		getParentElement();//返回父节点元素
String		getValue();// 如果这是一个 Text 节点，则返回此节点的值，否则返回此节点的直接子节点值。
void		setTextContent(String stt);//修改(设置)此节点的值
Node		removeChild(Node node);//删除子节点,返回这个被删除的节点
NodeList	getChildNodes();//获取第一个层子节点,所谓的第一层就是第一层,不会返回第一层的子节点,只返回第一层

5,NodeList 简介
int			getLength();//返回列表中的节点数
Node		item(int inde);//返回集合中的第index个节点对象

6,Transformer 回写XML文件类(会用就行,死代码直复制使用都可以)
没错,这个B也是一个抽象类。也有一个工厂类来获取 TransformerFactory
呵呵哒,这个工厂类也是他妈的一个抽象类,玩毛啊！跟 DocumentBuilder 一个德行
Transformer ts = TransformerFactory.newInstance().newTransformer();//应该能看懂
void		transform(Source s,Result r);//回写类对象,把指定的Document对象写入到指定的xml文件中
			> Source 是一个接口,用它的子类对象 DOMSource.
			* DOMSource ds = new DOMSource(Document doc);//传递一个Document对象
			> Result 也一个接口,用它的子类对象	StreamResult
			* StreamResult rs = new StreamResult(new File("xml文件地址"));//传递一个文件地址,也可以直接写url路径
		