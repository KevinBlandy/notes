/*
	简单事件处理机制
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Demo extends JFrame implements ActionListener
{
	//定一个一个panel
	JPanel mp = null;
	JButton jb1 = null;
	JButton jb2 = null;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	Demo()
	{
		mp = new JPanel();
		jb1 = new JButton("黑色");
		//指定action命令
		jb1.setActionCommand("黑色");
		jb2 = new JButton("红色");
		jb2.setActionCommand("红色");

		this.add(jb1,BorderLayout.NORTH);
		//mp.setBackground(Color.BLACK);
		this.add(mp);
		this.add(jb2,BorderLayout.SOUTH);
		//注册监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);

		this.setBounds(400,300,200,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	//对事件处理的方法
	public void actionPerformed(ActionEvent e)
	{
		//判断是哪个按钮被点击
		if (e.getActionCommand().equals("黑色"))
		{
			System.out.println("黑色摁钮");
			mp.setBackground(Color.BLACK);
		}
		else if (e.getActionCommand().equals("红色"))
		{
			System.out.println("红色摁钮");
			mp.setBackground(Color.RED);
		}
	}
}