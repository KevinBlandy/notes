--------------------------------
beautifulsoup-Tag			 	|
--------------------------------
	* <class 'bs4.element.Tag'>


--------------------------------
beautifulsoup-属性			 	|
--------------------------------
	name
		* 返回标签名称

	attrs
		* 返回属性dict
	
	contents 
		* 返回list,当前标签'所有直接子元素集合',包括文本节点(bs4.element.NavigableString)
		* '字符串节点没有该属性'
	
	children 
		* 返回迭代器,当前标签的'所有直接子元素'
	
	descendants
		* 返回当前标签的'所有子元素'
		* 递归
	
	string
		* 获取当前标签的文本标签
	
	strings
		* 获取当前标签下的所有文本标签,递归获取
	
	stripped_strings 
		* 同上.空白会被忽略掉

--------------------------------
beautifulsoup-方法			 	|
--------------------------------
	get(attr)
		* 获取指定名称的属性值,属性不存在返回None
	
	find_all(name , attrs , recursive , string , **kwargs )
		* 获取子标签中指定名称的标签对象,返回list
		* 参数可以是标签名称,也是可以是一个 正则对象(re),也可以是一个过滤器方法
		* 参数
			name
				* 以查找所有名字为 name 的tag,字符串对象会被自动忽略掉.
			string 
				* 通过 string 参数可以搜搜文档中的字符串内容,与 name 参数的可选值一样
			keyword 
				* 通过指定属性名和值来过滤
				* 值也可以是[]或者正则
				* 如值为 True,则表示无视值,属性存在就OK
			attrs
				* 属性值的 dict 表示
		* 通过limit关键字参数来限制检索的结果集长度,跟sql中的limit一样
		* 通过recursive关键字参数来控制是否要递归检索(包含多级子节点),默认为 True
	
	find()
		* 跟 find_all 一样,它返回的只有一个结果

	find_parents()
	find_parent() 
		* 搜索父级节点
	
	find_next_siblings()
	find_next_sibling()
		* 搜索兄弟节点
	
	find_previous_siblings()
	find_previous_sibling()
		* 搜索上/下一个解析节点
	
	find_all_next()
	find_next()
		
	find_all_previous() 
	find_previous()
		
	
	clear()
		* 清除当前标签中的文本节点(就是标签体)
	
	extract()
		* 移除当前节点,并且返回
		* 返回的节点是一个独立的html文档

	decompose()
		* 移除当前节点,并且从内存中销毁
	
	replace_with(new_tag)
		* 使用 new_tag 来代替当前tag,返回当前tag
	
	wrap(tag)
		* 使用tag来包装当前tag
		* 认个亲爸爸
	
	prettify()
		* 输出节点
	
	get_text()
		* 获取子节点下所有的文本节点信息
		* 参数可以传递一个分隔符,该分隔符会对结果进行分隔
		* 关键字参数
			strip
				* 如果该值为 True,会去除多余的空格.默认 False

	


	



			
			
	

