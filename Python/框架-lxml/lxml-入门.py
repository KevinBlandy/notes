-------------------------
lxml					 |
-------------------------
	* 它仅仅只是一个第三方库,算不上框架,提供了强大的xml操作api
	* from lxml import etree 


-------------------------
lxml-etree 模块 函数	 |
-------------------------
	HTML(text, parser=None, base_url=None)
		* 通过html文本构造一个 Element 对象

	XML(text, parser=None, base_url=None)
		* 通过xml文本构造一个 Element 对象

	tostring(element_or_tree,
			encoding=None, method="xml", 
			xml_declaration=None, 
			pretty_print=False, 
			with_tail=True, 
			standalone=None, doctype=None, exclusive=False, with_comments=True, inclusive_ns_prefixes=None)

		* 以字符串形式输出指定节点对象
	
	 SubElement(_parent, _tag, attrib=None, nsmap=None, **_extra)
		* 添加指定名称的子节点到节点对象,返回子节点对象
		* 参数
			_parent 父级节点对象
			_tag	子节点名称(字符串)
		* 关键字参数
			attrib	指定子标签的属性值

	XPath()
		* 创建一个xpath对象,可以通过该对象来对标签文本进行检索操作
		* demo	
			xpath = etree.XPath("//text()")
			print(xpath(etree.XML('<i>Hello</i>')))	# ['Hello']
	
	fromstring(xml_str)
		* 把指定的xml文本解析为:Element 对象
	
	parse(path)
		* 读取指定的文件,解析为 Element 对象

-------------------------
lxml-etree-实例属性,方法 |
-------------------------
	tag
		* 返回标签名称

	text
		* 标签体
	
	attrib
		* 标签的属性dict
	
	tail
		* 自关闭标签后的文本

	append(e)
		* 添加一个Element对象到当前对象的子节点
	
	set(k,v)
		* 设置标签的属性值
	
	get(k)
		* 获取标签指定名称的属性值
	
	items()
		* 返回标签的属性[(k,v)]
	
	iter()
		* 返回子标签迭代器(递归)
		* 也可以传递标签名称作为参数,来过滤要迭代的子标签
	
	xpath()
		* 根据xpath表达式检索数据,返回[]

	iterfind()
		* 返回满足匹配的节点列表,返回迭代器,支持xpath表达式
	findall()
		* 返回满足匹配的节点列表,支持xpath表达式
	find()
		* 返回满足匹配的第一个,支持xpath表达式
	findtext()
		* 返回第一个满足匹配条件的.text内容,支持xpath表达式

	

-------------------------
lxml-etree 基本操作		 |
-------------------------
	* 生成(创建)空xml节点对象
		root = etree.Element("root")
		print(etree.tostring(root, pretty_print=True))
	
	* 生成子节点
		from lxml import etree
		root = etree.Element("root")

		root.append(etree.Element("child1"))        # 直接通过实例对象的append方法添加一个Element子标签对象

		child2 = etree.SubElement(root, "child2")   # 通过etree模块的SubElement来添加子标签
		child2 = etree.SubElement(root, "child3")
		print(etree.tostring(root))
	
	* 带内容的xml节点
		from lxml import etree
		root = etree.Element("root")
		root.text = "Hello World"	# 通过节点对象的text属性来获取/设置标签体
		print(etree.tostring(root))
	
	* 属性生成
		from lxml import etree
		root = etree.Element("root", name = "Kevin")    # 在构造函数传递关键字参数来设置属性
		root.set("hello","huhu")                        # 通过节点对象的 set(key,value) 来设置属性
		root.text = "Hello World"                       # 设置节点的标签体
		print(etree.tostring(root))
	
	* 获取属性
		from lxml import etree
		root = etree.Element("root", name = "Kevin")   
		print(root.get('name'))		# 通过get()方法来获取指定节点对象的属性,如果属性不存在返回 None

		from lxml import etree
		root = etree.Element("root", name = "Kevin",age="15")
		print(root.attrib)			# 通过 attrib 属性来获取节点属性的dict
		print(root.items())			# 通过 items() 方法返回节点属性的[(key,value),(key,value)]
	

	* 特殊内容
		from lxml import etree
		html = etree.Element("html")
		body = etree.Element("body")
		body.text = 'Hello'
		br = etree.Element("br")
		br.tail = "KevinBlandy"     # 在自关闭标签后添加的文本
		body.append(br)
		html.append(body)
		print(etree.tostring(html))
		# <html><body>Hello<br/>KevinBlandy</body></html>

	* 节点遍历
		for element in root.iter():	
			print(element.tag, element.text)

		for element in root.iter("child"):	# 指定节点名称来过滤子节点
			print(element.tag, element.text)
	
	* 节点查找
		iterfind()
			* 返回满足匹配的节点列表,返回迭代器
		findall()
			* 返回满足匹配的节点列表
		find()
			* 返回满足匹配的第一个
		findtext()
			* 返回第一个满足匹配条件的.text内容

		* 他们都支持xpath表达式