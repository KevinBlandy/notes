------------------------
beautifulsoup			|
------------------------
	* 安装
		pip install beautifulsoup4

	* 文档
		http://beautifulsoup.readthedocs.io/zh_CN/latest/
		https://www.crummy.com/software/BeautifulSoup/bs4/doc.zh/
	

------------------------
beautifulsoup-开始解析	|
------------------------
	from bs4 import BeautifulSoup
	
	# 可以传递html文件
	soup = BeautifulSoup(open("index.html"),'html.parser')
	
	# 可以直接构造html字符串
	soup = BeautifulSoup("<html>data</html>",'html.parser')

--------------------------
beautifulsoup-基本四大对象|
--------------------------
	tag
		* 表示标签

	NavigableString
		* 表示标签体

	BeautifulSoup
		* 表示整个html文档对象

	Comment
		* 表示注释<!--注释-->
		* 它属于 NavigableString 的子类
	
	* 还有一些其他的对象
		CData 
			* xml中的 <![CDATA[]]>
		ProcessingInstruction 
		Declaratio
		Doctype 
			* HTML中的 <!DOCTYPE html>
		