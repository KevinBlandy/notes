--------------------
Scene				|
--------------------
	# Scene implements EventTarget

	# 构造函数
		public Scene(@NamedArg("root") Parent root)
		public Scene(@NamedArg("root") Parent root, @NamedArg("width") double width, @NamedArg("height") double height)
		public Scene(@NamedArg("root") Parent root, @NamedArg(value="width", defaultValue="-1") double width, @NamedArg(value="height", defaultValue="-1") double height, @NamedArg("depthBuffer") boolean depthBuffer)
		public Scene(@NamedArg("root") Parent root, @NamedArg(value="width", defaultValue="-1") double width, @NamedArg(value="height", defaultValue="-1") double height, @NamedArg("depthBuffer") boolean depthBuffer, @NamedArg(value="antiAliasing", defaultValue="DISABLED") SceneAntialiasing antiAliasing)
		public Scene(@NamedArg("root") Parent root, @NamedArg("width") double width, @NamedArg("height") double height, @NamedArg(value="fill", defaultValue="WHITE") Paint fill)
		public Scene(@NamedArg("root") Parent root, @NamedArg(value="fill", defaultValue="WHITE") Paint fill)\
	
	# 静态方法

	# 实例方法
		void setCursor(Cursor value)
			* 设置鼠标指向的时候的样式
			* Cursor 是一个抽象类, 已经预定义了N多样式
				DEFAULT
				CROSSHAIR
				TEXT
				WAIT
				SW_RESIZE
				SE_RESIZE
				NW_RESIZE
				NE_RESIZE
				N_RESIZE
				S_RESIZE
				W_RESIZE
				E_RESIZE
				OPEN_HAND
				CLOSED_HAND
				HAND
				MOVE
				DISAPPEAR
				H_RESIZE
				V_RESIZE
				NONE
		* Cursor 还具备一个静态方法: Cursor cursor(final String identifier), 可以加载图片作为鼠标的样式
			URL url = getClass().getClassLoader().getResource("favicon.ico");
			String path = url.toExternalForm();
			scene.setCursor(Cursor.cursor(path));

			
