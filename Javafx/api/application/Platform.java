----------------------
Platform
----------------------
	# Platform 一个静态工具类, 平台!

	# 静态方法
		void runLater(Runnable runnable)
			* 添加一个任务到队列
			* 注意, 它并不会新启动一个线程来执行, 而是使用UI线程: JavaFX Application Thread 来执行的
			* 如果用它执行大量的任务, 可能导致UI界面阻塞
			* 一般用于执行更新组件属性之类的任务

		boolean isFxApplicationThread()

		void exit()
			* 退出应用

		ReadOnlyBooleanProperty accessibilityActiveProperty()

		boolean isAccessibilityActive()

		boolean isSupported(ConditionalFeature feature)
			* 判断当前环境是否支持一些特性, 枚举
				GRAPHICS
				CONTROLS
				MEDIA
				WEB
				SWT
				SWING
				FXML
				SCENE3D
				EFFECT
				SHAPE_CLIP
				INPUT_METHOD
				TRANSPARENT_WINDOW
				UNIFIED_WINDOW
				TWO_LEVEL_FOCUS
				VIRTUAL_KEYBOARD
				INPUT_TOUCH
				INPUT_MULTITOUCH
				INPUT_POINTER
	
		boolean isImplicitExit()
		void setImplicitExit(boolean implicitExit)
			* 如果该值为 true, 那么在关掉程序的最后一个窗口的时候会, 程序就会退出
			* 如果该值为 false, 不会退出程序, 必须要手动的调用 Platform.exit() 方法来退出程序
		


	
