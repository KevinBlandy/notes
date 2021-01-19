/*class MainBoard
{
	public void run()
	{
		System.out.println("主板运行");
	}
	public void useNetCard(NetCard c)
	{
		c.open();
		c.close();
	}
}
class NetCard
{
	public void open()
	{
		System.out.println("网卡开启");
	}
	public void close()
	{
		System.out.println("网卡关闭");
	}
}*/
interface PCI
{
	public void open();
	public void close();
}
class MainBoard
{
	public void run()
	{
		System.out.println("主板运行");
	}
	public void usePCI(PCI p)//接口型引用指向自己的子类对象！
	{
		if (p!=null)
		{
			p.open();
			p.close();
		}
	}
}
class NetCard implements PCI
{
	public void open()
	{
		System.out.println("网卡开启");
	}
	public void close()
	{
		System.out.println("网卡关闭");
	}
}
class SoundCard implements PCI
{
	public void open()
	{
		System.out.println("声卡运行");
	}
	public void close()
	{
		System.out.println("声卡关闭");
	}

}
public class Demo
{
	public static void main(String[] args)
	{
		MainBoard mb = new MainBoard();
		mb.run();
		mb.usePCI(null);
		mb.usePCI(new NetCard());
		mb.usePCI(new SoundCard());
	}
}