-------------------
事件和监听器	   |
-------------------
	# 顶层的事件对象
		Event

-------------------
观察者			   |
-------------------
	# 顶层的监听器观察者
		Observable
			void addListener(InvalidationListener listener);
			void removeListener(InvalidationListener listener);
	
	# 系统预定义监听器
		InvalidationListener
			void invalidated(Observable observable);
		ChangeListener
			void changed<T>(ObservableValue<? extends T> observable, T oldValue, T newValue);
		