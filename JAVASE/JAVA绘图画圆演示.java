/*
	JAVA绘图案例
*/
import java.awt.*;
import javax.swing.*;
public class Demo extends JFrame
{
	MyJpanel mp = null;
	public static void main(String[] args)
	{
		Demo d = new Demo();
	}
	public Demo()
	{
		mp = new MyJpanel();
		this.add(mp);
		this.setBounds(400,300,400,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
//定义一个面板类。继承JPnel。用于绘图和显示绘图的区域。
class MyJpanel extends JPanel
{
	//覆写JPanel里面的一个比较重要的方法--paint
	//Graphice 是绘图的重要类。可以理解成一支画笔。
	public void paint(Graphics g)
	{
		 //调用父类函数完成初始化
		 super.paint(g);//这句话不能少。
		 System.out.println("被调用");
//		 //画圆圈。
//		 g.drawOval(10,10,30,30);
//		 //画直线
//		 g.drawLine(20,20,40,40);
//		 //画矩形边框
//		 g.drawRect(30, 30, 40, 40);
//		 //画填充矩形
//		 g.setColor(Color.BLUE);//设置填充颜色
//		 g.fillRect(50, 50, 40, 60);
		 //画图片
//		 Image im = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.jpg"));
//		 g.drawImage(im, 0, 0, 1024, 738,this);
		 //画不同的字体
		 g.setColor(Color.green);//设置字体颜色
		 g.setFont(new Font("华文彩云",Font.BOLD,30));//设置字体的字体样式，粗细，大小
		 g.drawString("剑舞红颜笑", 50, 50);
	}
}