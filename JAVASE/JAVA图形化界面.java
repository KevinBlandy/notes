两个包
java.Awt.*;
	依赖于平台。需要调用本地系统方法实现功能。属重量级控件。
javax.Swing.*;
	在AWT的基础上，建立的一套图形界面系统。其中提供了更多的组件。而且完全由JAVA实现。增强了移植性。属轻量级控件！
某种意义上，它是完全基基 AWT进行的！对系统的依赖性比较低。增强了可移植性！还在原基础上，增加了N多组件。
一般开发都选择Swing。
--------------------------------------
AWT		sun公司开发
 ↓
swing	sun公司优化(一般JAVA开发选择这个)
 ↓
SWT		IMB开发
 ↓
JFace	IMB优化

-----------------------------------------
		
布局管理
FlowLayout(流式布局)
	默认组建都是剧中的。添加的时候是从左到右顺序排序。
	Panel 默认的布局管理器。
BorderLayout(边界管理器)
可以在构造函数中设置组键的对齐方向。默认是中对齐
	东,南,西,北,中
	Frame 默认的布局管理器。
GridLayout (网格布局管理器)
可以在构造函数中设置行数还有列数。以及行间距，和列间距。
	规则的矩阵
CardLayout (卡片布局管理器)
	选项卡
GridBagLayout(网格包布局管理器)
    非规则的矩阵

当界面复杂之后。会同时存在多个布局模式。就会需要到面板组键 JPanel
这东西呢。可以这么去理解。如果界面元素少。那么就可以直接把组键添加到主界面中。选择好布局方式就好。
但是，界面复杂之后。需要多种布局方式，就可以把组键放到 JPanel 中。在 JPanel 中定义好组键以及布局方式。
最后再把 JPanel 添加到 主界面中即可。
----------------------------------------主窗体
		Component

1,创建 Frame 窗体。
2,对窗体进行基本设置。
	|--大小，位置，布局。
3,定义组件。
4,将组件通过窗体的add方法添加到窗体中。
5,让窗体显示。通过 setVisible(true);方法。
Frame f = new Frame("窗体名称");
Component 提供了两个'绘图'相关最重要的方法
paint(Graphics g); 
	|--绘制组建的外观
repaint();
	|--刷新组建的外观
f.setSize(x,y);
	|--初始化窗体的大小。
f.setTitle(String);
	|--设置窗体标题。
f.setLocation(x,y);
	|-初始化加载窗体的位置。
f.setBounds(a,b,c,d);
	|--直接初始化窗体大小&窗体的初始化位置。
	a=距离屏幕上边距 b=距离屏幕左边距 c=窗体的x轴长度 d=窗体的y轴长度
f.setVisible(boolean);
	|--是否可见
f.setResizable(boolean);
	|--设置窗体是否可以被用户进行缩放(大小改变)! 默认为 true 。禁止为 false 。
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	|--设置主窗体退出，JVM随之退出
f.add("组件");
	|--向f窗体中添加组件。
f.setLayout(new 布局格式);
	|--设置窗体的布局格式
Button b = new Button("name");
	|--创建一个名字叫做name的按钮
addWindowListener();
	|--窗口监听器。用于注册到事件源。
---------------------------------------容器类
JPanel
JSplitPane   -- JSplitPane jsp = new JSplitPane(拆分方向，包含的组建);
	|--jsp.setOneTouchExpandable(true);//在边界线上添加收起/展开摁钮。
JToolBar	--工具条组键
--------------------------------------一些组键
JTextField //--文本框
JPasswordField //--密码框
JLabel //--标签
JCheckBox //--复选框
JRadioButton //--单选框
JComboBox //--下拉框组键
JList //--列表框组键
JScrollPane //--滚动窗格组键
JTextArea //--多行文本框组键
JTabbedPane //--选项卡窗格(页签组键)
JMenuBar //菜单条组键(树干)
JMenu //菜单组键(树枝).里面可以嵌套JMenu.
JMenuItem //菜单项组建(树叶)

-------------------事件监听机制------------------

事件监听机制的特点：
1,事件源
	|--就是awt包或者swing包中那些图形界面组件
2,事件
	|--每一个事件源都有自己的特有的对应事件和共性事件。
3,监听器
	|--将可以触发某一个事件的动作(不止一个动作)。都已经封装到了监听器中。
4,事件处理
--前面三种都JAVA中都已经定义好了！我们只需要获取其对象使用即可！
  我们要做的。其实就是做事件处理。也就是做最重要的部分。
------------鼠标键盘事件


