----------------------------
Screen						|
----------------------------
	# 屏幕相关的对象
	# 构造函数
	# 静态方法
		Screen getPrimary()
			* 返回当前屏幕对象

		ObservableList<Screen> getScreens() 
			* 可能有多个屏幕

		ObservableList<Screen> getScreensForRectangle(double x, double y, double width, double height)
		ObservableList<Screen> getScreensForRectangle(Rectangle2D r)

	# 实例方法
		double getDpi()
		Rectangle2D getBounds()
			* 返回屏幕的最大信息, 包含了宽高,x,y坐标等等信息

		Rectangle2D getVisualBounds()
			* 返回屏幕的可视信息, 包含了宽高,x,y坐标等等信息
		


			
		

				

	