--------------------------------
xpath 语法						|
--------------------------------
	nodename	
		* 选取此节点的所有子节点
	.	
		* 选取当前节点。
	..	
		* 选取当前节点的父节点

	@	
		* 选取属性
	/p
		* 选取根元素p
		
	//a//@href
		* 获取所有a标签中的href的属性值

	//a[@href='http://www.layui.com/']
		* 获取href值=http://www.layui.com/的a标签节点

	h3/text()
	p/text()
		* 获取当前标签下所有的h3/p子标签的标签体内容,返回[]
	
	/td[1]//text()
		* 获取当前标签下的第1个td的标签体内容
	/bookstore/book[last()]	
		* 选取属于 bookstore 子元素的最后一个 book 元素
	/bookstore/book[last()-1]
		* 选取属于 bookstore 子元素的倒数第二个 book 元素

