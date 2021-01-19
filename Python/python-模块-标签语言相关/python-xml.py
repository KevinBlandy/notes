------------------------
xml						|
------------------------
	* 该模块提供了大量操作xml的模块与类库
		xml.etree.ElementTree:树形XML元素API

		xml.dom:XML DOM API
		xml.dom.minidom:XML DOM最小生成树
		xml.dom.pulldom:构建部分DOM树的支持

		xml.sax:SAX2解析的支持
		xml.sax.handler:SAX处理器基类
		xml.sax.saxutils:SAX工具
		xml.sax.xmlreader:SAX解析器接口

		xml.parsers.expat:运用Expat快速解析XML


	* dom操作xml的api
	* 内置模块	
		xml.etree.ElementTree

	* 关于操作
		1,加载xml文件获取到 tree 对象
			xml.etree.ElementTree.ElementTree parse(file)

		2,从tree对象获取root节点
			getroot()
			* 节点支持使用 for 循环遍历
				root = getroot()
				for i in root:
					pass

		3,进行一些列的节点操作
			str tag
				* 返回标签名称

			dict attrib
				* 标签属性(dict)

			str text
				* 返回标签体
				* 赋值就是,设置标签体

			set(key,value)
				* 设置标签属性

			iter iter(tagname)
				* 可以获取到所有指定名称的子标签(不分层次),进行遍历操作
				* 返回的是迭代器

			findall(tagname)
				* 获取当前节点下的指定名称的所有一级节点
			
			find(tagname)
				* 获取当前节点下的指定名称的第一个一级节点
			
			remove(tagname)
				* 移除一级子节点

------------------------
xml-模块属性			|
------------------------

------------------------
xml-模块函数			|
------------------------

------------------------
xml-xml.etree.ElementTree.ElementTree|
------------------------
	* 树形XML元素API
	xml.etree.ElementTree.ElementTree parse(file)
		* 解析xml文件,返回对象
	
	xml.etree.ElementTree.Element getroot()
		* 返回根节点对象
		* 节点对象可遍历
		* 具备属性
			str tag
				* 返回标签名称

			dict attrib
				* 标签属性(dict)

			str text
				* 返回标签体
				* 赋值就是,设置标签体
		* 函数
			set(key,value)
				* 设置标签属性

			iter iter(tagname)
				* 可以获取到所有指定名称的子标签(不分层次),进行遍历操作
					for node in root.iter('ta'):
						text = node.text		# 获取文本节点的值
						node.text = "text"		# 设置节点文本
						node.set("key","value")	# 设置节点属性

			findall(tagname)
				* 获取当前节点下的指定名称的所有一级节点
			
			find(tagname)
				* 获取当前节点下的指定名称的第一个一级节点
			
			remove(tagname)
				* 移除一级子节点

	writr(file)
		* 把xml写入文件

------------------------
xml-SAX解析				|
------------------------
	from xml.parsers.expat import ParserCreate

	# 自定义 解析Handler处理里
	class DefaultSaxHandler(object):
		# 标签开始
		def start_element(self, name, attrs):
			print('sax:start_element: %s, attrs: %s' % (name, str(attrs)))

		# 标签结束
		def end_element(self, name):
			print('sax:end_element: %s' % name)
		
		# 文本信息
		def char_data(self, text):
			print('sax:char_data: %s' % text)

	xml = r'''<?xml version="1.0"?>
	<ol>
		<li><a href="/python">Python</a></li>
		<li><a href="/ruby">Ruby</a></li>
	</ol>
	'''
	handler = DefaultSaxHandler()
	# 创建解析器对象
	parser = ParserCreate()

	# 开始解析标签的事件
	parser.StartElementHandler = handler.start_element

	# 解析完毕标签事件
	parser.EndElementHandler = handler.end_element

	# 解析到标签体事件
	parser.CharacterDataHandler = handler.char_data

	# 开始解析
	parser.Parse(xml)

------------------------
xml-demo				|
------------------------

# 遍历========================================
import xml.etree.ElementTree as ElementTree

file = "E:\\pom.xml"

# 解析xml
xml  = ElementTree.parse(file)

# 获取根节点
root = xml.getroot()

# 获取根节点的名称
root.tag

# 节点遍历
for child in root:
    # 子节点标签名,标签属性
    print(child.tag,child.attrib)
    for i in child:


# 修改========================================
import xml.etree.ElementTree as ElementTree

file = "E:\\pom.xml"

# 解析xml
xml  = ElementTree.parse(file)

# 获取根节点
root = xml.getroot()

# 获取该节点下所有指定名称的节点(不分层次I)
for node in root.iter('name'):
    tag = node.tag              # 获取标签名称
    attr = node.attrib          # 获取属性dict
    text = node.text            # 获取标签值
    node.text = "yoyo"          # 设置标签值
    node.set("key","value")     # 设置标签属性

# 回写
xml.write(file)

# 删除========================================
import xml.etree.ElementTree as ElementTree

file = "E:\\pom.xml"

# 解析xml
xml  = ElementTree.parse(file)

# 获取根节点
root = xml.getroot()

# 获取该节点下所有指定名称的一级节点
for node in root.findall('name'):
    # 如果节点有 delete属性
    if('delete' in node.attrib):
        # 删除节点 
        root.remove(node)
        
# 回写
xml.write(file)