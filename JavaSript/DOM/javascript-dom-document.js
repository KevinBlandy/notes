-----------------------
document				|
-----------------------

-----------------------
属性					|
-----------------------
	all			
		* 提供对文档中多有HTML元素的访问,火狐不支持,返回数组
	anchors
		* 返回对文档中所有 Anchor 对象的引用。
	forms		
		* 返回文档中所有Form对象引用,返回数组
	images
		* 返回所有imgae对象,返回数组
	body		
		* 提供对<body>元素的直接访问
	links
		* 返回所有links的引用,返回数组
	body	
		* 提供对 <body> 元素的直接访问。
		* 对于定义了框架集的文档，该属性引用最外层的 <frameset>。
	cookie	
		* 设置或返回与当前文档有关的所有 cookie。
	domain	
		* 返回当前文档的域名。
	lastModified	
		* 返回文档被最后修改的日期和时间。
	referrer	
		* 返回载入当前文档的文档的 URL。
	title	
		* 返回当前文档的标题。
	URL	
		* 返回当前文档的 URL。

-----------------------
方法					|
-----------------------
	querySelectorAll();
		* 根据表达式检索一组节点
		* 表达式
			* 标签+属性名称
				'input.error'
					* 返回 class 值为 error 的 input 框
			* id + 标签名称
				'#results td'
					* 返回 id 为 results 元素下的所有 td 元素
			* 多个规则
				'.hightClass','.lowClass'
					* 允许有多个匹配规则,使用 | 
			* 事件规则
				'td:hover'
					* 返回当前 hover 的td
					* 根据事件检索

			* 根据标签名称和指定的k,v
				<meta name="csrf-token" content="123456">
				document.querySelector('meta[name="csrf-token"]');
				document.querySelector('meta[name]');


	querySelector();
		* 跟进表达式检索一个节点

	getElementById();
		* 根据标签的ID值获取标签

	getElementsByTagName();
		* 根据标签名称获取节点,返回:HTMLCollection
		* 允许使用通配符: * ,代表所有的节点信息
		* demo
			var nodes = getElementsByTagName("div");
			nodes[0];
			nodes.item(0);

	getElementsByName();
		* 根据name属性值获取节点,返回:NodeList
		* demo
			var nodes = getElementsByName("foo");
			nodes[0];
			nodes.item(0);
	
	createElement();
		* 创建一个HTML节点

	createTextNode();
		* 创建一个文本节点

	createAttribute();
		* 创建一个指定名称的属性节点对象

	createComment();
		* 创建注释节点


	write();
		* 向HTML文档输出内容,会覆盖原来的
	close()	
		* 关闭用 document.open() 方法打开的输出流，并显示选定的数据。
	open()	
		* 打开一个流，以收集来自任何 document.write() 或 document.writeln() 方法的输出。
	write()	
		* 向文档写 HTML 表达式 或 JavaScript 代码。
	writeln()
		* 等同于 write() 方法，不同的是在每个表达式之后写一个换行符。
	

