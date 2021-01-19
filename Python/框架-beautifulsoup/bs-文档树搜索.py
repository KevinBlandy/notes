----------------------------
过滤器						|
----------------------------
	* 字符串过滤
		soup.find_all('a')
		* 返回所偶 a 标签节点
	
	* 正则表达式过滤
		soup.find_all(re.compile('^b'))
		* 检索所有以b开头的节点.<body>和<br/>都会被检索到
	
	* 列表过滤
		soup.find_all(['a','b'])
		* 匹配a标签或者b标签
		* 只要是[]任意一个匹配就ok
	
	* True
		* True 可以匹配任何值,下面代码查找到所有的tag,但是不会返回字符串节点
		for tag in soup.find_all(True):
			print(tag.name)
	
	* 方法
		* 可以用一个方法作为参数进行过滤.该方法起码且必须有一个参数,就是当前遍历到的节点
		* 通过返回 bool 值还判断节点是够是需要的
			for tag in soup.find_all(lambda e : e.get('id')):
			print(tag.name)
			# 返回所有具备id属性的节点
	
	* 根据标签属性过滤
		* 关键字参数就是要过滤的属性
			soup.find_all('a',id='1')
			# 检索id=1的a标签
		
		* 关键字参数也可以是一个函数来进行过滤
			soup.find_all('a',id=lambda id : id=='2')
			# 检索id值为2的a标签
		
		* 也可以使用正则,列表,True
	
		* 有些tag属性在搜索不能使用,比如HTML5中的 data-*
			data_soup = BeautifulSoup('<div data-foo="value">foo!</div>')
			data_soup.find_all(data-foo="value")
			# SyntaxError: keyword can't be an expression

			但是可以通过 find_all() 方法的 attrs 参数定义一个字典参数来搜索包含特殊属性的tag:
			data_soup.find_all(attrs={"data-foo": "value"})
			# [<div data-foo="value">foo!</div>]
	
	* 根据class过滤
		* CSS类名的关键字 class 在Python中是保留字,使用 class 做参数会导致语法错误
		* 从Beautiful Soup的4.1.1版本开始,可以通过 class_ 参数搜索有指定CSS类名的tag
		* class_ 参数同样接受不同类型的 过滤器 ,字符串,正则表达式,方法或 True 
		* class 属性是 多值属性 .按照CSS类名搜索tag时,可以分别搜索tag中的每个CSS类名
			css_soup = BeautifulSoup('<p class="body strikeout"></p>')
			css_soup.find_all("p", class_="strikeout")
			# [<p class="body strikeout"></p>]

			css_soup.find_all("p", class_="body")
			# [<p class="body strikeout"></p>]

		* 搜索 class 属性时也可以通过CSS值完全匹配
			css_soup.find_all("p", class_="body strikeout")
	
	* 根据文本过滤
		soup.find_all(string="title")
		# ['title']
	
		* string 还可以与其它参数混合使用来过滤 
			soup.find_all("a", string="Elsie")
			# [<a href="http://example.com/elsie" class="sister" id="link1">Elsie</a>]
		* string 值也支持 bool,正则,[] 函数
	
	* 限制结果集
		soup.find_all("a",limit=2)
		* 仅仅检索前俩a标签
	
	* 仅仅检索直接子元素,通过 recursive  关键字控制
		soup.find_all("a",recursive=True)

	* 像调用 find_all() 一样调用tag
		* 对象和 tag 对象可以被当作一个方法来使用,这个方法的执行结果与调用这个对象的 find_all() 方法相同,下面两行代码是等价的
		soup.find_all("a")
		soup("a")

		soup.title.find_all(string=True)
		soup.title(string=True)

	* 使用 find() 仅仅检索一个结果集,跟 find_all() 一样,它返回的只有一个结果集

	* css选择器
		* BeautifulSoup 对象的 .select() 方法中传入字符串参数, 即可使用CSS选择器的语法找到tag
		* 就是跟css的选择器一样的选择模式
			soup.select("title")
			# [<title>The Dormouse's story</title>]

			soup.select("p nth-of-type(3)")
			# [<p class="story">...</p>]
		
		* 通过tag标签逐层查找( )
			soup.select("body a")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie"  id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]

			soup.select("html head title")
			# [<title>The Dormouse's story</title>]
		
		* 找到某个tag标签下的直接子标签 (>)
			soup.select("head > title")
			# [<title>The Dormouse's story</title>]

			soup.select("p > a")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie"  id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]

			soup.select("p > a:nth-of-type(2)")
			# [<a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>]

			soup.select("p > #link1")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>]

			soup.select("body > a")
			# []
		
		* 找到兄弟节点标签(~)
			soup.select("#link1 ~ .sister")
			# [<a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie"  id="link3">Tillie</a>]

			soup.select("#link1 + .sister")
			# [<a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>]
									

		* 通过CSS的类名查找(.)
			soup.select(".sister")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]

			soup.select("[class~=sister]")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]
		
		* 通过tag的id查找(#)
			soup.select("#link1")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>]

			soup.select("a#link2")
			# [<a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>]

		* 同时用多种CSS选择器查询元素(,)
			soup.select("#link1,#link2")
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>]

		* 通过是否存在某个属性来查找([])
			soup.select('a[href]')
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]
		
		* 通过属性的值来查找([])
			soup.select('a[href="http://example.com/elsie"]')
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>]

			soup.select('a[href^="http://example.com/"]')
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>,
			#  <a class="sister" href="http://example.com/lacie" id="link2">Lacie</a>,
			#  <a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]

			soup.select('a[href$="tillie"]')
			# [<a class="sister" href="http://example.com/tillie" id="link3">Tillie</a>]

			soup.select('a[href*=".com/el"]')
			# [<a class="sister" href="http://example.com/elsie" id="link1">Elsie</a>]
				