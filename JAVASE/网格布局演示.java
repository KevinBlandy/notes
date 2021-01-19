/*
 * 网格布局演示
 * 		把容器分成行多个行，列。组键被填充到每个网格中。(计算器)
 * GrildLayout布局演示
 *1，继承JFrame
 *2,定义需要的组键
 *3，在构造函数中。初始化组键
 *4，添加组键
 *5,对窗体（顶层容器），属性进行设置。
 *6，显示窗体。setVisible(true); 
 *-------注意--------
 *1，组键的相对位置不会随着容器的缩放而变化。但是大小会发生变化。
 *2，所有组键的大小相同。
 *3，可以通过构造函数 GridLayout(rows,cols,hgap,vgap);来设置行数，列数，以及行间隙宽和列间隙宽
 * */
import java.awt.*;

import javax.swing.*;
public class Demo extends JFrame
{
	//可以用数组来存储需要的组键
	JButton[] jbs = new JButton[9];
	int size = 9;//定义数组长度。也就是存储的按钮个数
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	public Demo()
	{
		//创建组键，用for循环来初始化他们。
		for(int x = 0;x < size;x++)
		{
			jbs[x] = new JButton(String.valueOf(x));
		}
		//设置网格布局.参数代表的是行数，和列数。以及行间距，和列间距
		this.setLayout(new GridLayout(3,3,10,10));
		//添加组键
		for(int x = 0;x < size ;x++)
		{
			this.add(jbs[x]);
		}
		//设置窗体属性
		this.setTitle("网格布局演示");//设置窗体标题
		this.setBounds(300, 400, 600, 600);//初始化大小。以及初始化坐标
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗体JVM随之退出
		this.setResizable(false);//禁止用户缩放窗体(改变窗体大小)
		this.setVisible(true);//显示窗体
	}
}
