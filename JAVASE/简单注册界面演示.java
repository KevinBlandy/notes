import java.awt.*;
import javax.swing.*;
class  Demo extends JFrame
{
	JPanel jp1,jp2,jp3;
	JLabel jlb1,jlb2;
	JButton jb1,jb2;
	JCheckBox jcb1,jcb2,jcb3;
	JRadioButton jrb1,jrb2;
	ButtonGroup bg;
	public static void main(String[] args) 
	{
		Demo d = new Demo();
	}
	Demo()
	{
		//创建容器
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		//创建Label
		jlb1 = new JLabel("擅长");
		jlb2 = new JLabel("性别");
		//创建按钮
		jb1 = new JButton("注册");
		jb2 = new JButton("退出");
		//创建复选框
		jcb1 = new JCheckBox("JAVA");
		jcb2 = new JCheckBox("C++");
		jcb3 = new JCheckBox("C");
		//创建单选框(单选框要放入ButtonGroup管理)
		jrb1 = new JRadioButton("男");
		jrb2 = new JRadioButton("女");
		bg = new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		//设置布局管理
		this.setLayout(new GridLayout(3,1));
		//添加组键
		jp1.add(jlb1);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(jcb3);
		jp2.add(jlb2);
		jp2.add(jrb1);
		jp2.add(jrb2);
		jp3.add(jb1);
		jp3.add(jb2);
		//添加到主窗体
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		//设置主窗体属性
		this.setTitle("注册页面");
		this.setBounds(300,200,300,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
