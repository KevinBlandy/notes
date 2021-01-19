----------------------
Window				  |
----------------------
	# 实现了接口: EventTarget

	# 构造函数
		protected Window()
	
	# protected 方法
		void setScene(Scene value)
		void show()
		<T extends Event> void setEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler)

	# 实例方法
		<T extends Event> void addEventFilter(final EventType<T> eventType, final EventHandler<? super T> eventFilter)
		<T extends Event> void addEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler)
		EventDispatchChain buildEventDispatchChain(EventDispatchChain tail)
		void centerOnScreen()
		ObjectProperty<EventDispatcher> eventDispatcherProperty()
		void fireEvent(Event event)
		ReadOnlyBooleanProperty focusedProperty()
		EventDispatcher getEventDispatcher()
		EventHandler<WindowEvent> getOnCloseRequest()
		EventHandler<WindowEvent> getOnHidden()
		EventHandler<WindowEvent> getOnHiding()
		EventHandler<WindowEvent> getOnShowing()
		EventHandler<WindowEvent> getOnShown()
		
		ObservableMap<Object, Object> getProperties()
		Scene getScene()
		Object getUserData()
		boolean hasProperties()
		ReadOnlyDoubleProperty heightProperty()
			
		void hide()
		boolean isFocused()
		boolean isShowing()
		ObjectProperty<EventHandler<WindowEvent>> onCloseRequestProperty()
		ObjectProperty<EventHandler<WindowEvent>> onHiddenProperty()
		ObjectProperty<EventHandler<WindowEvent>> onHidingProperty()
		ObjectProperty<EventHandler<WindowEvent>> onShowingProperty()
		ObjectProperty<EventHandler<WindowEvent>> onShownProperty()
		DoubleProperty opacityProperty()
		<T extends Event> void removeEventFilter(final EventType<T> eventType, final EventHandler<? super T> eventFilter)
		<T extends Event> void removeEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler)
		void requestFocus()
		ReadOnlyObjectProperty<Scene> sceneProperty() 
		void setEventDispatcher(EventDispatcher value)
		void setOnCloseRequest(EventHandler<WindowEvent> value)
		void setOnHidden(EventHandler<WindowEvent> value)
		void setOnHiding(EventHandler<WindowEvent> value)
		void setOnShowing(EventHandler<WindowEvent> value)
		void setOnShown(EventHandler<WindowEvent> value)

		void setOpacity(double value)
		double getOpacity()
			* 透明度, 取值范围: 0f..1f ,0 是完全透明

		void setUserData(Object value)
		void setWidth(double value)
		ReadOnlyBooleanProperty showingProperty() 
		void sizeToScene()
		ReadOnlyDoubleProperty widthProperty()
		
		
		ReadOnlyDoubleProperty xProperty()
		ReadOnlyDoubleProperty yProperty()
			* xy,坐标的property
		void setX(double value)
		void setY(double value)
		double getX()
		double getY()
			* 设置/读取x,y坐标
			* x 屏幕的横向, 左上角是0
			* y 屏幕的纵向, 坐上角是0
	
		void setHeight(double value)
		double getHeight()
		void setWidth(double value)
		double getWidth()
			* 读取/设置宽高参数
			* 只有在窗口 show() 了之后, 才能读取倒 宽高参数