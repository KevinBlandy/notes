--------------------
指定文档解析器		|
--------------------


--------------------
编码				|
--------------------

	* UnicodeDammit 
		* UnicodeDammit 是BS内置库, 主要用来猜测文档编码	
			from bs4 import UnicodeDammit
			dammit = UnicodeDammit("Sacr\xc3\xa9 bleu!")
			print(dammit.unicode_markup)
			# Sacré bleu!
			dammit.original_encoding
			# 'utf-8'

		* 如果Python中安装了 chardet 或 cchardet 那么编码检测功能的准确率将大大提高. 
		* 输入的字符越多,检测结果越精确
		* 如果事先猜测到一些可能编码, 那么可以将猜测的编码作为参数(list),这样将优先检测这些编码:
			dammit = UnicodeDammit("Sacr\xe9 bleu!", ["latin-1", "iso-8859-1"])
			print(dammit.unicode_markup)
			# Sacré bleu!
			dammit.original_encoding
			# 'latin-1'
	
--------------------
比较对象是否相同	|
--------------------
	* 两个 NavigableString 或 Tag 对象具有相同的HTML或XML结构时, Beautiful Soup就判断这两个对象相同.
		markup = "<p>I want <b>pizza</b> and more <b>pizza</b>!</p>"
		soup = BeautifulSoup(markup, 'html.parser')
		first_b, second_b = soup.find_all('b')
		print (first_b == second_b)
		# True
		print (first_b.previous_element == second_b.previous_element)
		# False
		# 2个 <b> 标签在 BS 中是相同的, 尽管他们在文档树的不同位置, 但是具有相同的表象: “<b>pizza</b>”

		print (first_b is second_b)
		# False
		* 如果想判断两个对象是否严格的指向同一个对象可以通过 is 来判断

------------------------
复制Beautiful Soup对象	|
------------------------
	* copy.copy() 方法可以复制任意 Tag 或 NavigableString 对象

		import copy
		p_copy = copy.copy(soup.p)
		print (p_copy)
		# <p>I want <b>pizza</b> and more <b>pizza</b>!</p>
		# 复制后的对象跟与对象是相等的, 但指向不同的内存地址

		print soup.p == p_copy
		# True

		print soup.p is p_copy
		# False

	* 源对象和复制对象的区别是源对象在文档树中, 而复制后的对象是独立的还没有添加到文档树中.
	* 复制后对象的效果跟调用了 extract() 方法相同.
		print (p_copy.parent)
		# None
		# 这是因为相等的对象不能同时插入相同的位置

------------------------
解析部分文档			|
------------------------
	* SoupStrainer 类可以定义文档的某段内容,这样搜索文档时就不必先解析整篇文档
	* 只会解析在 SoupStrainer 中定义过的文档.
	* 创建一个 SoupStrainer 对象并作为 parse_only 参数给 BeautifulSoup 的构造方法即可.
		from bs4 import SoupStrainer
		
		# 仅仅解析a标签
		only_a_tags = SoupStrainer("a")	
		
		# 仅仅解析id=link2的标签
		only_tags_with_id_link2 = SoupStrainer(id="link2")
		
		def is_short_string(string):
			return len(string) < 10
		
		# 仅仅解析方法返回 true 的标签
		only_short_strings = SoupStrainer(string=is_short_string)

		* SoupStrainer 类接受与典型搜索方法相同的参数:name , attrs , recursive , string , **kwargs 。
	
	* BeautifulSoup(html_doc, "html.parser", parse_only=only_a_tags)