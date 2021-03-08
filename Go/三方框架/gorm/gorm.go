-------------------------
gorm
-------------------------
	# Grom
		https://gorm.io/zh_CN/
		https://github.com/go-gorm/gorm
		https://gorm.io/zh_CN/docs/models.html
	
	# 参考学习
		https://www.bilibili.com/video/av88602528/
	
-------------------------
gorm 一些点
-------------------------
	* find/first之类的封装操作会根据封装的对象，来确定目标表，封装操作一定要是操作链的最后一步

	* find/fist操作也可以封装成map,切片，需要手动绑定目标表

	* 可以通过绑定Table来确定目标表，也可以通过 Model 来绑定

	* 各种条件 Where/Or/Not 可以用结构体，Map，字符串作为条件，也可以直接放ID的切片作为条件

