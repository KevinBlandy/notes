-------------------------
application的三种启动方式|
-------------------------
	# 继承 Application, 从main方法启动
		public class Main extends Application {
			public static void main(String[] args) {
				launch(args);  // 调用 Application 的静态方法，启动
			}
			@Override  
			public void start(Stage primaryStage) throws Exception {
				primaryStage.setTitle("Hello World!");
				primaryStage.show();
			}
		}
	
	# 通过main函数调用 Application 实现类
		// 继承 Application
		public class ApplicationImpl extends Application {
			@Override
				primaryStage.setTitle("Hello World!");
				primaryStage.show();
			}
		}

		public class Main  {
			public static void main(String[] args) {
				// 在main方法中指定实现类的class
				Application.launch(ApplicationImpl.class, args);
			}
		}
		

-------------------------
application的生命周期	 |
-------------------------
	# 在启动前执行
		void init()
	
	# 在关闭前执行
		void stop()
	

-------------------------
ui 线程					 |
-------------------------
	# 启动线程: JavaFX-Launcher
		* 主线程启动的子线程, 用于执行 init() 方法的线程


	# UI线程: JavaFX Application Thread
		* 为了不阻塞, JavaFX-Launcher线程会启动一个UI线程来启动javafx
	

	

