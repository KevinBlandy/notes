-------------------
Node类型			|
-------------------
	


------------------
属性				|
------------------
	# 特殊的节点具备特定的属性
		<img/>
			* src
				* document.getElementsByTagName("IMG")[0].src = "";
		<form/>
			* action
			* method
		<p/>
			* textContent
		...

	# 节点相关
		NodeList		childNodes;		
							* 返回所有子节点列表

		Node			firstChild;		
							* 指向在childNodes列表中的第一个节点

		Node			lastChild;		
							* 指向在ChildNodes列表中的最后一个节点

		Node			parentNode;		
							* 返回一个给定节点的父节点

		Node			previousSibling;	
							* 指向前一个兄弟节点,如果不存在,返回null

		Node			nextSibling;		
							* 指向后一个兄弟节点,如果不存在,返回null
	
	# 属性相关
		
		String			nodeName		
							* 返回节点的名称,根据节点的类型而定义

		String			nodeValue		
							* 返回节点的值,根据节点的类型而定义

		String			value
							* 同上....感觉没啥不一样

		Document		ownerDocument	
							* 指向这个节点所属的文档

		String			className
							* ClassName

		Number			nodeType;			
							* 返回节点的类型常量之一
								节点类型:
								标签形节点		---		1
								属性节点		---		2
								文本型极点		---		3 [注意空白文本,浏览器会解析]
								注释型节点		---		8
								document节点	---		9

		NamedNodeMap	attributes
							* 包含了代表一个元素的特性的Attr对象,仅用于Element节点
		
		String			id
							* 返回ID值
		String			innerHTML
							* 返回元素内容,包括HTML标签
		String			innerText
							* 返回文本内容,不包含HTML标签
	# 样式
	CSSStyleDeclaration	style
							* 返回元素的style属性
		CSSClassList	classList
							* 返回 class 属性对象的数组


------------------
方法				|
------------------			
	# 子节点操作
		hasChildNodes();	
			* 是否有子节点,返回Boolean值
			* 就算是空白的字符串,也算是有子节点

		appendChild();
			* 添加一个子节点
		
		insertBefor(新节点,子节点);
			* 在指定子节点前插入新的节点
		
		replaceChild(新子节点,旧子节点);
			* 使用新元素,替换指定的旧节点

		removeChild();
			* 删除指定的子节点

	
	# 其他节点操作
		cloneNode(boolean)
			* 返回当前节点的克隆节点,根据参数决定是否要克隆其所有子节点

	# 属性操作
		
		setAttributeNode();
			* 设置一个属性节点对象

		removeAttributeNode()	
			* 移除指定的属性节点，并返回被移除的节点。

		getAttribute();
			* 获取指定名称属性的属性值
		
		setAttribute(属性名,属性值);
			* 设置属性名和属性值
		
		removeAttribute();
			* 删除指定名称的属性值
		
		hasAttribute();
			* 判断节点是否具备指定的属性