import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	JTextArea jta;//多行文本框组建
	JPanel jp;//容器
	JComboBox jcb;//下拉框
	JTextField jtf;//文本框
	JButton jb;//摁钮
	JScrollPane jsc;//滚动条
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	Demo()
	{
		jta = new JTextArea();
		jta.append("来自凯文布兰迪-2015年9月13日 13:26:01");
		jsc = new JScrollPane(jta);//创建滚动条。并且把文本框作为构造参数参加去进去。
		jp = new JPanel();
		String[] s = {"Kevin","Rocco","Lenka","Licth"};
		jcb = new JComboBox(s);
		jtf = new JTextField(10);
		jb = new JButton("摁钮");
		//设置布局
		//添加组建
		jp.add(jcb);
		jp.add(jtf);
		jp.add(jb);
		this.add(jsc);//把滚动条加进去。因为滚动条已经修饰过了多行文本框类。
		//把容器添加到主窗体中去。并且指明容器在边界布局中的位置
		this.add(jp,BorderLayout.SOUTH);
		//设置主窗体属性
		this.setBounds(400,300,300,300);
		//更改标题栏图标
		this.setIconImage(new ImageIcon("Images/2.jpg").getImage());
		this.setTitle("余文朋");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}