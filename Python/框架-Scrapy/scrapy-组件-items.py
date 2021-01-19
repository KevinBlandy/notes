------------------------
items					|
------------------------
	* 其实就是定义model
	* 简单的model定义
		import scrapy
		class WebspiderItem(scrapy.Item):
			name = scrapy.Field()		# 定义一个name属性
			age = scrapy.Field()		# 定义一个age属性
			gender = scrapy.Field()		# 定义一个gender属性
	

------------------------
ItemLoader				|
------------------------	
	* from scrapy.loader import ItemLoader
	* 提供了一种更为便捷的方式在spider中对 Item 进行填充
	* 构造函数
		ItemLoader(item=None, selector=None, response=None, parent=None, **context)

	* demo
		from scrapy.loader import ItemLoader
		from kevin.items import User

		def parse(self,response):
			# 通过Item实例和response来实例化loader
			loader = ItemLoader(item=User(),response=response)

			# 通过xpath对指定的属性进行赋值
			loader.add_xpath('name', '//div[@class="product_name"]') 
			loader.add_xpath('name', '//div[@class="product_title"]')
			loader.add_xpath('price', '//p[@id="price"]')

			# 通过css选择器对指定的属性进行赋值
			loader.add_css('stock', 'p#stock]')

			# 直接设置值
			loader.add_value('last_updated', 'today') 

			# 返回填充好值的item
			return loader.load_item()