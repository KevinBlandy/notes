--------------------------------
beautifulsoup-Tag			 	|
--------------------------------
	* 这个tag与原生解析器里面的tag一个意思,都是指:标签
		from bs4 import BeautifulSoup
		soup = BeautifulSoup(open('index.html'),'html.parser')
		print(soup.a)		# 打印出从上往下解析到的第一个a标签字符串
		print(type(soup.a))	# <class 'bs4.element.Tag'>

--------------------------------
beautifulsoup-属性			 	|
--------------------------------
	name
		* 该属性值是字符串,就是当前标签的字符串名称
		* 可以修改该属性,但会影响所有通过当前beautifulsoup生成的HTML文档
			soup = BeautifulSoup(open('index.html'),'html.parser')
			soup.a.name = 'b'
			print(soup)	# <b href="http://javaweb.io">KevinBlandy2</b>

	attrs
		* 返回 dict,是当前标签的所有属性值
			soup.div.attrs		# {'name': 'name属性', 'data-options': 'data-options属性', 'id': 'id属性'}

		* 也可以直接以[]方式进行访问,如果属性不存在,抛出异常
			soup.div['class']
			soup.div['data-options']

		* tag的属性值可以被修改
			soup.div['name'] = '新的name属性'
			# <div name="新的name属性">
		
		* tag的属性值可以被删除
			del tag['name']
			# <div><div>
		
		* 如果属性可以有多个值(根据html标准),那么返回的是一个list
			<div class="c1 c2"></div>
			soup.div.attrs
			# {'class': ['c1', 'c2']}		/  只有一个值也是list:{'class': ['c1']}
			
			* 多值属性也允许增删(反正就是用操作list的方法进行操作就是了)
				soup.div.attrs['class'].append('c3')
				# <div class="c1 c2 c3"></div>

				soup.div.attrs['class'].remove('c1')
				# <div class="c2"></div>

			* 如果转换的文档是XML格式,那么tag中不包含多值属性
				xml_soup = BeautifulSoup('<p class="body strikeout"></p>', 'xml')
				xml_soup.p['class']
				# 'body strikeout'

		* 也可以通过 get() 方法来获取属性值,属性不存在返回None
			get(attr)

--------------------------------
beautifulsoup-标签体		 	|
--------------------------------
	* <div>我就是标签体<div>
	* 通过 tag.string 来获取
		soup.div.string
		# 我就是标签体
		* 会被封装为:<class 'bs4.element.NavigableString'> 类
	
	* tag中包含的字符串不能编辑,但是可以通过 replace_with() 被替换成其它的字符串
		soup.div.string.replace_with('新的标签体')
		# <div>新的标签体</div>

		* 其实也可以直接替换
		soup.div.string = '新的标签体'
	

	

	
	

	

