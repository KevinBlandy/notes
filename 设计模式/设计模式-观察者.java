----------------------
观察者
----------------------
	# 主题 Observable, 观察者 Observer
		* 总的来说就是，多个观察者注册到一个主题
		* 主题通过广播，来对所有的观察者推送数据
	
	# Demo
		import java.util.Observable;
		import java.util.Observer;

		class ObservableImpl extends Observable {
			@Override
			public synchronized void setChanged() {
				super.setChanged();
			}
			@Override
			public synchronized void clearChanged() {
				super.clearChanged();
			}
		}

		public class Main {
			public static void main(String[] args) throws InterruptedException {
				
				ObservableImpl observable = new ObservableImpl();
				
				Observer ob1 = new Observer() {
					@Override
					public void update(Observable o, Object arg) {
						System.out.println("我是ob1，我收到了消息:" + arg);
						o.deleteObserver(this); // ob1 收到一次消息后，就取消注册，不再监听
					}
				};
				Observer ob2 = new Observer() {
					@Override
					public void update(Observable o, Object arg) {
						System.out.println("我是ob2，我收到了消息:" + arg);
					}
				};
				
				// 添加观察者
				observable.addObserver(ob1);
				observable.addObserver(ob2);
				
				// 设置“状态已经改变”
				observable.setChanged();
				// 广播数据
				observable.notifyObservers("第一条数据");
				
				// 设置“状态已经改变”
				observable.setChanged();
				// 广播数据
				observable.notifyObservers("第二条数据");
			}
		}