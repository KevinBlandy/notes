----------------------------
BeautifulSoup				|
----------------------------
	* BeautifulSoup 对象表示的是一个文档的全部内容
	* 大部分时候,可以把它当作 Tag 对象,它支持 遍历文档树 和 搜索文档树 中描述的大部分的方法
	* BeautifulSoup 对象并不是真正的HTML或XML的tag,所以它没有name(仅有一个其实)和attribute属性.

----------------------------
BeautifulSoup-属性			|
----------------------------
	name 
		* 该属性值是固定的:"[document]"

----------------------------
BeautifulSoup-注释等其他元素|
----------------------------
	* 注释类型是 Comment,是一个特殊的 NavigableString 
		markup = "<b><!--Hey, buddy. Want to buy a used parser?--></b>"
		soup = BeautifulSoup(markup,'html.parser')

		comment = soup.b.string
		print(type(comment))
		# <class 'bs4.element.Comment'>
	
	* 它出现在HTML文档中时, Comment 对象会使用特殊的格式输出:
		print(soup.b.prettify())
		# <b>
		#  <!--Hey, buddy. Want to buy a used parser?-->
		# </b>
	
	* Beautiful Soup中定义的其它类型都可能会出现在XML的文档中:
			CData 
			ProcessingInstruction 
			Declaratio
			Doctype 
			* 与 Comment 对象类似,这些类都是 NavigableString 的子类
			* 只是添加了一些额外的方法
	
	* xml中的cdata
		from bs4 import CData
		# 创建cdata对象
		cdata = CData("A CDATA block")
		# 替换原来的注释对象
		comment.replace_with(cdata)	# replace_with 是父类:NavigableString 的方法
		print(soup.b.prettify())
		# <b>
		#  <![CDATA[A CDATA block]]>
		# </b>

