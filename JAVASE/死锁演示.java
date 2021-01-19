
/*
 *两个，或者两个以上的线程。在争夺资源的过程中。发生的一种相互等待的现象。 
 */
public class DieLockDemo extends Thread
{
	private boolean flag;
	public DieLockDemo(boolean flag)
	{
		this.flag = flag;
	}
	public void run()
	{
		if(flag)
		{
			synchronized(MyLock.objA)
			{
				System.out.println("IF语句，OBJA锁");
				synchronized(MyLock.objB)
				{
					System.out.println("IF语句，OBJB锁");
				}
			}
		}
		else
		{
			synchronized(MyLock.objB)
			{
				System.out.println("ELSE语句，OBJB锁");
				synchronized(MyLock.objA)
				{
					System.out.println("ELSE语句，OBJA锁");
				}
			}
		}
	}
	public static void main(String[] args)
	{
		DieLockDemo d1 = new DieLockDemo(true);
		DieLockDemo d2 = new DieLockDemo(false);
		d1.start();
		d2.start();
	}
}
class MyLock //创建锁对象
{
	public static final Object objA = new Object();
	public static final Object objB = new Object();
}

