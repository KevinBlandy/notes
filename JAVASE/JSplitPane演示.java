import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	//定义组建
	JSplitPane jsp;
	JList jlist;
	JLabel jl1;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	Demo()
	{
		//创建组建
		String[] words = {"Kevin","tony","Rocco","Litch","Lenka"};
		jlist = new JList(words);
		jl1 = new JLabel(new ImageIcon("images/2.jpg"));
		//创建一个拆分窗体的对象。参数列表表示。水平拆分，后面是被组建。
		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jlist,jl1);
		//设置布局管理器。因为默认布局就是我们所需要的布局。所以不必设置
		jsp.setOneTouchExpandable(true);
		//添加组建
		this.add(jsp);
		this.setBounds(400,200,300,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	//	this.setResizable(false);
	}
}