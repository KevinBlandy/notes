----------------------
Node			
----------------------
	# abstract class Node implements EventTarget, Styleable
	

	# 实例方法
		void setLayoutX(double value)
		void setLayoutY(double value)
			* 设置x/y轴的位置
		
		void setOpacity(double value)
		double getOpacity()
			* 设置组件的透明度, 0 - 1, 值越大越不透明
			
			
		boolean contains(double localX, double localY)
		boolean contains(Point2D localPoint)
			* 判断指定的坐标是否有组件存在
			* 必须要求x点有组件

		
	