------------------------
子节点					|
------------------------
	* 操作文档树最简单的方法就是告诉它你想获取的tag的name
		soup.head.title
		# 获取文档中 head 标签下的 titile 标签
		
		* 通过这种方式,只能获取到指定名称的第一个标签
	
	* 获取文档中的所有标签,需要使用 find_all(),返回list
		soup.find_all('a')
	
	* 根据属性获取子节点信息
		contents 
			* 返回list,当前标签'所有直接子元素集合',包括文本节点(bs4.element.NavigableString)
			* '字符串节点没有该属性'
		
		children 
			* 返回迭代器,当前标签的'所有直接子元素'
		
		descendants
			* 返回当前标签的'所有子元素'
			* 递归
	
	* 如果tag只有一个 NavigableString 类型子节点,那么这个tag可以使用 .string 得到子节点
		soup.head.title.string

		* 如果tag只有一个子标签,这个tag也可以直接通过 .string 获取到唯一子标签的标签体
			<head><title>Title</title></head>
			soup.head.string
			# Title
	
	* 通过 strings 获取所有的子文本信息,递归获取
		soup.strings

		* 返回的是迭代器
		* 使用 stripped_strings 可以忽略掉所有的空白信息


------------------------
父节点					|
------------------------
	* 通过 .parent 属性来获取某个元素的父节点
		title = soup.head.title
		title.parent
		# <head><title>Title</title></head>
	
	* 文档节点,也有父节点属性
		title = soup.head.title.string
		title.parent
		# <title>Title</title>
	
	* 文档的顶层节点比如<html>的父节点是 BeautifulSoup 对象
		html_tag = soup.html
		type(html_tag.parent)
		# <class 'bs4.BeautifulSoup'>
	
		* BeautifulSoup 对象的 .parent 是None:
		print(soup.parent)
		# None
	
	* 通过元素的 .parents 属性可以递归得到元素的所有父辈节点
		for i in soup.a.parents:
			print(i.name)
		
		#div
		# body
		# html
		# [document]

------------------------
兄弟节点				|
------------------------
	* 使用 .next_sibling 和 .previous_sibling 属性来查询兄弟节点
		title = soup.head.title
		meta = soup.head.meta
		print(title.next_sibling)
		print(meta.previous_sibling)

		# <meta charset="utf-8"/>
		# <title>Title</title>
		
		* title 没有上一个兄弟标签,meta没有下一个兄弟标签,强行获取返回 None
		* 空白/换行也会被计算为节点
	
	* 通过 .next_siblings 和 .previous_siblings 获取所有的兄弟节点,返回迭代器
		for i in soup.head.title.next_siblings:
			print(i)
		
------------------------
回退和前进				|
------------------------
	* 通过 .next_element 和 .previous_element 来访问上/下一个被解析的节点
	* 通过 .next_elements 和 .previous_elements 来访问上/下被解析的内容,它是一个迭代器
	
	* 他们有点像父子节点,实质大不同.
	* <html><body><body></html>  -> <body></body>



		


				

	
		
		
