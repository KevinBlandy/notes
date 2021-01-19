----------------------------
scrapy启动脚本				|
----------------------------
	* scrapy 的启动可以通过命令来进行启动,也可以把命令写入脚本,通过运行脚本来启动

----------------------------
scrapy启动脚本				|
----------------------------
	from scrapy import cmdline
	cmdline.execute("scrapy crawl itcast -o test.json".split())


	* 启动该脚本,其实也是调用了scrapy去执行命令
	* 同理,如下写法也可以

	import os
	os.system("scrapy crawl itcast -o test.json")

