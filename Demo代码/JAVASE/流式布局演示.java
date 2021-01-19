/*
 * 流式布局演示
 * FlowLayout演示
 *1，继承JFrame
 *2,定义需要的组键
 *3，在构造函数中。初始化组键
 *4，添加组键
 *5,对窗体（顶层容器），属性进行设置。
 *6，显示窗体。setVisible(true);
 *-------注意----------
 *流式布局中组键默认是中间对齐。可以通过FlowLayout(intalign);来改变
 *不限制他所管理的组键大小，允许他们有最佳大小.
 *当容器被缩放时，组键的位置可能变化，但组键的大小不变。
 *
 * */
import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	//声明需要的组键
	JButton jb1,jb2,jb3,jb4,jb5,jb6;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	Demo()
	{
		//创建组键
		jb1 = new JButton("Kevin");
	//	jb1.setSize(500,500);
		jb2 = new JButton("Tony");
		jb3 = new JButton("Litch");
		jb4 = new JButton("Lenka");
		jb5 = new JButton("Rocco");
		jb6 = new JButton("Celedy");
		//添加组键
		this.add(jb1);
		this.add(jb2);
		this.add(jb3);
		this.add(jb4);
		this.add(jb5);
		this.add(jb6);
		//设置窗体属性
		this.setTitle("流式布局演示");
		this.setBounds(400, 300, 450, 300);//初始化窗体大小以及初始化窗体座标
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗体退出JVM随之退出。
		//显示窗体
		this.setVisible(true);
		//设置布局格式为-流式布局(默认是中对齐，现在设置的左对齐)
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//禁止用户改变窗体大小
		this.setResizable(false);
	}
}




