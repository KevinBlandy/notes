/*
 *BorderLayout(边界布局)演示
 *1，继承JFrame
 *2,定义需要的组键
 *3，在构造函数中。初始化组键
 *4，添加组键
 *5,对窗体（顶层容器），属性进行设置。
 *6，显示窗体。setVisible(true);
 *---------注意事项------------
 *1,不是五个部分都必须添加。
 *2，中部组键会自动调节大小。
 *3,JFrame,JDialog默认布局管理器就是BorderLayout。
 * */
import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	//定义组键
	JButton jb1,jb2,jb3,jb4,jb5;//定义了五个JButton摁钮。
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	Demo()
	{
		//创建组建
		jb1 = new JButton("中");
		jb2 = new JButton("北");
		jb3 = new JButton("东");
		jb4 = new JButton("南");
		jb5 = new JButton("西");
		//添加组键
		this.add(jb1,BorderLayout.CENTER);
		this.add(jb2,BorderLayout.NORTH);
		this.add(jb3,BorderLayout.EAST);
		this.add(jb4,BorderLayout.SOUTH);
		this.add(jb5,BorderLayout.WEST);
		this.setTitle("边界布局演示");//创建主窗体的标题
		this.setBounds(200, 300, 400, 200);//初始化主窗体的大小，以及初始座标
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置主窗体退出，JVM随之退出
		this.setVisible(true);//让窗体显示。
	}
}
