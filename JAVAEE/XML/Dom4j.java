Dom4j
	这个要认真学,这个是实际开发中应用得比较多的！
	它是由这个Dom4j组织提供的解析xml组键
	它集合了Dom和Sax两种解析方法,可以进行增删改操作

Dom4j,并不是JAVASE的类库,也就是说我们需要导包！
Dom4j-1.6.1.jar

1,创建解析器对象
SAXReader reader = new SAXReader();
1,解析xml文件得到 Document(不是w3c包下的) 对象
Document document = reader.read(new File("xml文件"));//可以是字节流,或者直接是文件路径




Document --方法介绍(是 Node 的子接口)
*  Element		getRootElement();//获取根节点元素,返回的是Element
	
Element --方法介绍(是 Node 的子接口)
*  Element		getParent();//返回父节点
*  Element		addElement();//添加一个子标签,返回的就是这个新添加的标签
*  Element		element();//返回该节点下面的第一个节点
*  Element  	element(String name);//返回指定名称的第一个子节点
*  List			elements();//返回这个节点的所有子节点,List集合
*  List			elements(String name);//返回这个节点下的所有指定名称的标签对象的结合List
*  String		getText();//返回这个标签的文本值		
*  不明			setTest();//给标签设置文本值
*  不明			remove(Document doc);//把指定节点从自己子标签中删除(一定要记得遵循,删除必须是由父节点来执行)
*  String		attributeValue(String name);//返回的是该节点指定名称属性的值,如果没有返回null

XMLWriter --方法介绍(用于回写XML文件的类,常规类,可以直接创建对象使用)
	XMLWriter wr = new XMLWriter(OutputStream out,OutputFormat format);
	* out,其实就是你要操作的文件
	* OutputFormat,就是格式化类,让写入的文件带有缩进等符号,
	> 它有两个静态方法可以返回该类对象
		-- createPrettyPrint();//返回的对象是漂亮的带缩进的
		-- createCompactFormat();//返回的对象是压缩的,不带缩进的
*  void			write(Document doc);//把指定的Document对象写入到硬盘中(路径在创建回写对象的时候指定了)
*  void			close();//关闭资

----------------------Dom4j对XPath的支持
使用Dom4j,支持 XPath 的操作,可以直接获取到某个元素
其实	XPath 就是一个表达式

形式体现
*  /AAA/BBB/CCC:表示一层--AAA下面的BBB下面的CCC
*  //BBB:只要名称是BBB都可以得到,与层次无关
*  /*:表示所有元素
*  /AAA/BBB[1]:表示AAA下面的BBB下面的第一个元素
*  /AAA/BBB[last()]:表示AAA下面的BBB下面的最后一个元素
*  //@id:获取所有id属性
*  //BBB[@ID]:只要出现在BBB元素上的ID熟悉,都获取
*  //BBB[@id='b1']：元素名称是BBB,而且上面有id熟悉,而且值为b1
*/
操作的具体步骤
*  默认的情况下Dom4j是不支持xpath的！如果需要使用
*  需要支持xpath的jar包
	jaxen-1.1-bete-6.jar
*  Dom4j里面提供了两个方法来支持xpath
List<Node>		selectNodes();//获取多个节点,传递你的xpath表达式
Node			selectSingleNode();//获取单个节点,传递xpath表达式
-----例
public static void getAll()throws Exception
{
	Document document = new SAXReader().read(file);
	List<Node> ele = document.selectNodes("//name");
	System.out.println(ele.get(1).getText());
}
