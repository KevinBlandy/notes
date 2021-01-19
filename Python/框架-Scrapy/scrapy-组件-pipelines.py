------------------------
pipelines				|
------------------------
	* 默认预定义代码
		class WebspiderPipeline(object):
		def process_item(self, item, spider):
			return item
	
	* 该组件(模块)用来定义N多个 Pipelines 

	* 配置在settings.py中
		ITEM_PIPELINES = {
		   'webspider.pipelines.WebspiderPipeline': 300,
		}
	
	* Pipelines可以直接继承自object
	

------------------------
pipelines-方法			|
------------------------
	* 必须实现的的方法(处理数据的方法),参数固定
		def process_item(self, item, spider):
			return item

		* item,传递过来的数据
		* spider,爬虫实例对象
	
	* 在爬虫关闭的时候,执行的方法,非必须
		def close_spider(self,spider):
			print("爬虫关闭:%s"%(spider))
		
		* spider,爬虫实例对象
	
	* 在爬虫开启的时候,执行的方法,非必须
		def open_spider(self, spider):
			print("爬虫开启:%s"%(spider))
		
		* spider,爬虫实例对象
	
	* 未知
		from_crawler(self,crawler)
	


	
------------------------
scrapy.pipelines.images	|
------------------------
	* scrapy.pipelines.images.ImaagePiPeline

	IMAGES_STROE = "/"
	
	def get_media_requests(self,item,info):
		yield scray.Request(item['url'])
	
	def item_completed(self,result,item,info):
		image_path = [x['path'] for ok ,x in result if ok ]
		return item


------------------------
scrapy.pipelines.files	|
------------------------


------------------------
scrapy.pipelines.media	|
------------------------


