--------------------
Js-Dom				|
--------------------
	* Dom:Document Object Model	文档对象模型
	* 文档:超文本文档(超文本标记文档)html,xml
	* 对象:提供了属性和方法
	* 模型:使用属性和方法操作超文本标记形文档
	* 可以使用js中dom里面提供的对象,使用这些对象的属性和方法,对标记型文档进行操作
	* 想要操作文档,就要把文档中的所有内容,都封装成对象
	* 一些常见对象
		document	整个HTML文档对象
		element		标签对象
		node		节点对象,是标签对象的父对象
		文本对象	不能单独存在,是依赖于标签
		属性对象	不能单独存在,是依赖于标签

	* DHTML -- 动态的HTML
	它其实是不是一种技术,而是多种技术的合体
		dom html css javacropt   就这些
		html		--	负责把数据进行封装
		dom			--	把标签封装成对象
		css			--	负责标签中数据的样式
		javacript	--	将三者进行融合,通过程序设计方式来完成动态效果的展现/操作

--------------------
Js-节点				|
--------------------
	元素节点
	文本节点
	属性节点
	注释节点


--------------------
Js-节点操作			|
--------------------
	# 获取节点
		getElementById();
			* 根据标签的ID值获取标签

		getElementsByName();
			* 根据标签名称获取节点,返回:HTMLCollection
			* demo
				var nodes = getElementsByName("div");
				nodes[0];
				nodes.item(0);

		getElementsByClassName();
			* 根据name属性值获取节点,返回:NodeList
			* demo
				var nodes = getElementsByClassName("foo");
				nodes[0];
				nodes.item(0);

	# 节点指针
		NodeList	childNodes;		
						* 返回所有子节点列表
		Node		firstChild;		
						* 指向在childNodes列表中的第一个节点
		Node		lastChild;		
						* 指向在ChildNodes列表中的最后一个节点
		Node		parentNode;		
						* 返回父节点
		Node		previousSibling;	
						* 指向前一个兄弟节点,如果不存在,返回null
		Node		nextSibling;		
						* 指向后一个兄弟节点,如果不存在,返回null
	
	# 节点操作
		* 创建节点
			createElement();
				* 创建THML节点
			createAttribute();
				* 创建属性节点
			createTextNode();
				* 创建文本节点
		
		* 插入节点
			appendChild();
				* 添加新的子节点
			insertBefor(新节点,子节点);
				* 在指定的子节点前插入一个新的节点
		
		* 替换节点
			replaceChild(新节点,旧节点);
				* 以新节点替换旧节点
		
		* 复制节点
			cloneNode(boolean);
				* 返回 克隆的节点,根据参数决定是否要复制子节点
			
		* 删除节点
			removeChild();
				* 删除指定的子节点
		
	# 属性操作
		* 获取属性
			getAttribute(属性名称);
				* 获取指定名称的属性

		* 设置属性
			setAttribute(属性名,属性值);
				* 设置属性

		* 删除属性
			removeAttribute(属性名称呼);
				* 删除属性


			
				
			
