---------------------------
html						|
---------------------------
	* html 标签解析器


---------------------------
html-HTMLParser				|
---------------------------
	* html.parser.HTMLParser
	* 利用 HTMLParser,可以把网页中的文本,图像等解析出来
	* HTML本质上是XML的子集,但是HTML的语法没有XML那么严格,所以不能用标准的DOM或SAX来解析HTML
	* demo
		from html.parser import HTMLParser
		from html.entities import name2codepoint

		# 自定义类,继承 HTMLParser
		class MyHTMLParser(HTMLParser):
			def handle_starttag(self, tag, attrs):
				print('<%s>' % tag)
			
			def handle_endtag(self, tag):
				print('</%s>' % tag)
			
			def handle_startendtag(self, tag, attrs):
				print('<%s/>' % tag)

			def handle_data(self, data):
				print(data)

			def handle_comment(self, data):
				print('<!--', data, '-->')

			def handle_entityref(self, name):
				print('&%s;' % name)

			def handle_charref(self, name):
				print('&#%s;' % name)

		parser = MyHTMLParser()
		# feed()方法可以多次调用，也就是不一定一次把整个HTML字符串都塞进去，可以一部分一部分塞进去。
		parser.feed('''<html>
		<head></head>
		<body>
		<!-- test html parser -->
			<p>Some <a href=\"#\">html</a> HTML&nbsp;tutorial...<br>END</p>
		</body></html>''')
