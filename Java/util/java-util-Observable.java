------------------
Observable
------------------
	# Java观察者模式的实现
		* 维护了2个核心的成员变量
			private boolean changed = false;
			private Vector<Observer> obs;
		
		* 都是同步操作

	# 构造方法
		public Observable()
	
	# 实例方法
		public synchronized void addObserver(Observer o) 
		public synchronized void deleteObserver(Observer o)
		public void notifyObservers()
		public void notifyObservers(Object arg)
			* 在notify前，需要先手动设置setChanged
			* 标识数据已经改变，否则notify不会生效
			* 在完成notify后，会默认调用 clearChanged，清空状态
		
		public synchronized void deleteObservers()

		protected synchronized void setChanged()
		protected synchronized void clearChanged()
			* 设置和清除“已改变”状态

		public synchronized boolean hasChanged()
		public synchronized int countObservers()
	
	# Observer 接口
		public interface Observer {
			void update(Observable o, Object arg);
		}
