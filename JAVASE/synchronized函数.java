class Bank
{
	Object obj = new Object();
	private int sum;
	public synchronized void add(int n)
	{
		sum = sum + n;
		try{Thread.sleep(20);}catch(Exception e){}
		System.out.println("sum"+sum);
	}
}
class user implements Runnable
{
	private Bank b = new Bank();
	public void run()
	{
		for (int x = 0;x < 3 ;x++ )
		{
			//try{Thread.sleep(20);}catch(Exception e){}
			b.add(100);
		}
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		user u = new user();
		Thread t1 = new Thread(u);
		Thread t2 = new Thread(u);
		t1.start();
		t2.start();
	}
}