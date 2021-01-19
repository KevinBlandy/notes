-----------------------------
Javafx						 |
-----------------------------
	# 参考地址
		https://openjfx.io/
		www.javafxchina.net/main/
		https://www.javafxdeveloper.com/
	
	# JavaFX架构和生态系统的高阶视图
		* 场景图(Scene Graph)
		* JavaFX功能的公开API(Java Public APIs for JavaFX Features)
		* 图形系统(Graphics System)
		* Glass窗体工具包(Glass Windowing Toolkit)
		* 多媒体和图像(Media and Images)
		* Web组件(Web Component)
		* CSS
		* UI控件(UI Controls)
		* 布局(Layout)
		* 2-D和3-D转换(2-D and 3-D Transformations)
		* 视觉特效(Visual Effects)
	
	# JavaFX应用程序基本结构
		* JavaFX应用程序的主类需要继承自 application.Application 类,覆写的start()方法是所有JavaFX应用程序的入口
		* JavaFX应用程序将UI容器定义为舞台(Stage)与场景(Scene),Stage类是JavaFX顶级容器,Scene类是所有内容的容器

		* 在JavaFX中,Scene中的内容会以由图形节点(Node)构成的分层场景图(Scene Graph)来展现
		* 结构图
			stage
				scene
					node


		* 当JavaFX应用程序是通过JavaFX Packager工具打包时,main()方法就不是必需的的了,因为JavaFX Package工具会将JavaFX Launcher嵌入到JAR文件中
		* 但是保留main()方法还是很有用的,这样可以运行不带有JavaFX Launcher的JAR文件,例如在使用某些没有将JavaFX工具完全集成进去的IDE时,另外嵌入了JavaFX代码的Swing应用程序仍需要main()方法
	
	# Hello World
		import javafx.application.Application;
		import javafx.stage.Stage;

		public class Main extends Application {
			public static void main(String[] args) {
				Applicationl.launch(args);
			}
			@Override
			public void start(Stage primaryStage) throws Exception {
				primaryStage.setTitle("Hello World!");
				primaryStage.show();
			}
		}
	
		