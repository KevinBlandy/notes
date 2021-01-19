----------------------------
修改tag的名称和属性			|
----------------------------
	* 直接修改其属性和标签名称
		soup = BeautifulSoup('<b class="boldest">Extremely bold</b>')
		tag = soup.b

		# 修改标签的名称
		tag.name = "blockquote"
		# 修改标签的class属性值
		tag['class'] = 'verybold'
		# 修改标签的id值
		tag['id'] = 1
		# 标签已经被修改
		# <blockquote class="verybold" id="1">Extremely bold</blockquote>
		
		# 删除指定的属性值
		del tag['class']
		del tag['id']
		# 标签属性已经被删除
		# <blockquote>Extremely bold</blockquote>
	
	* 修改 .string
		soup = BeautifulSoup('<a href="http://example.com/">I linked to <i>example.com</i></a>')
		tag = soup.a
		# 修改a标签中的文本节点
		tag.string = "New link text."
		# 文本节点已经被修改
		# <a href="http://example.com/">New link text.</a>

		* 如果当前的tag包含了其它tag,那么给它的 .string 属性赋值会覆盖掉原有的所有内容包括子tag
	
	* 使用 append() 添加文本节点的内容
		soup = BeautifulSoup("<a>Foo</a>")
		# 往a标签的文本节点添加内容
		soup.a.append("Bar")
		
		# a标签中的文本节点已经添加
		# <html><head></head><body><a>FooBar</a></body></html>
		soup.a.contents
		# [u'Foo', u'Bar'] //没想通,为啥会以[]形式返回

		* append() 的参数也可以是 NavigableString() 或者其子类的实例对象
			soup.a.append(NavigableString("bar"))

	* 添加注释
		from bs4 import Comment
		# 创建注释对象,注意第二个参数表示是一个注释
		new_comment = soup.new_string("Nice to see you.", Comment)
		# 添加到节点
		tag.append(new_comment)
		print(tag)
		# <b>Hello there<!--Nice to see you.--></b>
		print(tag.contents)
		# ['Hello', ' there', 'Nice to see you.']
	
	* 通过 new_tag() 添加tag 
		soup = BeautifulSoup("<b></b>")
		# 获取b标签
		original_tag = soup.b
		
		# 创建新的a标签,并且初始化 href 属性
		new_tag = soup.new_tag("a", href="http://www.example.com")
		# 添加a标签到b标签中
		original_tag.append(new_tag)
		print(original_tag)
		# <b><a href="http://www.example.com"></a></b>
		# 修改a标签的文本节点值
		new_tag.string = "Link text."
		print(original_tag)
		# <b><a href="http://www.example.com">Link text.</a></b>

		* new_tag() 第一个参数作为tag的name,是必填,其它参数选填
				
	* 通过 insert() 插入新的元素
		markup = '<a href="http://example.com/">KevinBlandy<i>example.com</i></a>'
		soup = BeautifulSoup(markup, "html.parser")
		tag = soup.a
		
		# 往a标签插入一个文本节点
		tag.insert(1, "Python Developer")
		print(tag)
		# <a href="http://example.com/">KevinBlandyPython Developer<i>example.com</i></a>
		print(tag.contents)
		# ['KevinBlandy', 'Python Developer', <i>example.com</i>]
	
	* insert_before() 和 insert_after()在节点之前/之后插入新的元素
		soup = BeautifulSoup("<b>stop</b>")
		# 创建i节点
		tag = soup.new_tag("i")
		# 设置i节点的文本属性
		tag.string = "Don't"
		# 在b节点的文本节点之前插入i节点
		soup.b.string.insert_before(tag)
		print(soup.b)
		# <b><i>Don't</i>stop</b>
		
		# 在b节点下的i节点之后插入一个 ever 文本节点
		soup.b.i.insert_after(soup.new_string(" ever "))
		print(soup.b)
		# <b><i>Don't</i> ever stop</b>
		print(soup.b.contents)
		# [<i>Don't</i>, u' ever ', u'stop']
	
	* 使用 clear() 方法移除当前tag的文本内容
		markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
		soup = BeautifulSoup(markup)
		tag = soup.a
	
		tag.clear()
		print(tag)
		# <a href="http://example.com/"></a>		//a标签之中的文本内容已经被清除掉
	
	* 使用 extract()方法将当前tag移除文档树,并作为方法结果返回

		markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
		soup = BeautifulSoup(markup)
		a_tag = soup.a
		
		# 移除i标签,并且返回
		i_tag = soup.i.extract()
		print(a_tag)
		# <a href="http://example.com/">I linked to</a>	//i标签已经被移除
		print(i_tag)
		# <i>example.com</i>							//这是已经被移除的i标签,它已经是独立的一个文档
		print(i_tag.parent)
		# None
		
		* 这个方法实际上产生了2个文档树:
			一个是用来解析原始文档的 BeautifulSoup 对象,、
			一个是被移除并且返回的tag.被移除并返回的tag可以继续调用 extract 方法


	* 使用 decompose() 方法将当前节点移除文档树并完全销毁
		markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
		soup = BeautifulSoup(markup)
		a_tag = soup.a
		# 移除 i 标签
		soup.i.decompose()
		print(a_tag)
		# <a href="http://example.com/">I linked to</a>		//i标签已经被移除
	
	* 使用 replace_with()方法移除文档树中的某段内容,并用新tag或文本节点替代它
		markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
		soup = BeautifulSoup(markup)
		a_tag = soup.a
		
		# 创建新的b标签
		new_tag = soup.new_tag("b")
		# 创建b标签的标签体
		new_tag.string = "example.net"
		# 移除i标签,并且使用b标签来代替它
		a_tag.i.replace_with(new_tag)
		print(a_tag)
		# <a href="http://example.com/">I linked to <b>example.net</b></a>
		
		* replace_with() 方法返回被替代的tag或文本节点,可以用来浏览或添加到文档树其它地方
	
	* 使用 wrap() 方法可以对指定的tag元素进行包装 ,并返回包装后的结果
		soup = BeautifulSoup("<p>I wish I was bold.</p>")
		# 通过一个新建的b标签,来包装p节点的标签体(文本节点)
		soup.p.string.wrap(soup.new_tag("b"))
		# <b>I wish I was bold.</b>
		
		# 通过新建一个 div 节点来包装 p 节点
		soup.p.wrap(soup.new_tag("div"))
		# <div><p><b>I wish I was bold.</b></p></div>
		
		* 该方法在 Beautiful Soup 4.0.5 中添加
	
	* 使用 unwrap()移除tag内的所有tag标签,该方法常被用来进行标记的解包
		markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
		soup = BeautifulSoup(markup)
		a_tag = soup.a
		# 移除a标签中的i标签
		a_tag.i.unwrap()
		print(a_tag)
		# <a href="http://example.com/">I linked to example.com</a>

		* 与 replace_with() 方法相同, unwrap() 方法返回被移除的tag