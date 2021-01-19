import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	//定义组键
	JPanel jp1,jp2;
	JLabel jlb1,jlb2;
	JComboBox jcb;
	JList jlist;
	JScrollPane jsp;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	public Demo()
	{
		// 创建容器
		jp1 = new JPanel();
		jp2 = new JPanel();
		//创建Label标签
		jlb1 = new JLabel("籍贯");
		jlb2 = new JLabel("常驻");
		//创建下拉框 !!!注意创方式
		String[] jg = {"重庆","北京","上海","天津"};
		jcb = new JComboBox(jg);
		//创建列表框组键
		String[] dd = {"马尔代夫","故宫","陕西皇陵","海南三亚","泰国","巴黎"};
		jlist = new JList(dd);
		//设置列表框展示的个数
		jlist.setVisibleRowCount(3);
		//把需要被滚动展示的列表框组键。添加到滚动窗组键中。
		jsp = new JScrollPane(jlist);
		//添加到容器并设置布局 
		this.setLayout(new GridLayout(3,1));
		jp1.add(jlb1);
		jp1.add(jcb);
		jp2.add(jlb2);
		jp2.add(jsp);
		this.add(jp1);
		this.add(jp2);
		//设置主窗体的大小。
		this.setTitle("注册页面");
		this.setBounds(300,200,300,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}