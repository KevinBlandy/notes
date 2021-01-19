import java.awt.*;

import javax.swing.*;
public class  Demo extends JFrame
{
	JPanel jp1,jp2,jp3;
	JLabel jlb1,jlb2;
	JTextField jt;
	JPasswordField jp;
	JButton jb1,jb2;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	public Demo()
	{
		jp1 = new JPanel(new FlowLayout());
		jp2 = new JPanel(new FlowLayout());
		jp3 = new JPanel(new GridLayout(1,2,10,10));
		
		jlb1 = new JLabel("用户名");//Lable标签
		jlb2 = new JLabel("密   码");
		
		jt = new JTextField(10);//文本框只能放置10个字符
		jp = new JPasswordField(10);//密码框
		
		jb1 = new JButton("登录");//摁钮
		jb2 = new JButton("退出");
		//设置布局管理
		this.setLayout(new GridLayout(3,1));
		//加入各个组键
		jp1.add(jlb1);
		jp1.add(jt);
		jp2.add(jlb2);
		jp2.add(jp);
		jp3.add(jb1);
		jp3.add(jb2);
		//加入到JFrame
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		//设置主窗体属性
		this.setTitle("登录系统");
		this.setBounds(400,200 , 300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}