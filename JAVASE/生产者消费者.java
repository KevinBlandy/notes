class Do
{
	private boolean flag = false;
	private String name;
	private int age;
	Do(){}
	Do(String name,int age)
	{
		this.name = name;
		this.age = age;
	}
	public boolean getFlag()
	{
		return this.flag;
	}
	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}
	public String getName()
	{
		return this.name;
	}
	public int getAge()
	{
		return this.age;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
}
class Rece implements Runnable
{
	private boolean flag = true;
	private Do d ;
	Rece(Do d)
	{
		this.d = d;
	}
	public void run()
	{
		while (true)
		{
			synchronized(d)
			{
				if(d.getFlag())
				{
					try{d.wait();}catch(Exception e){}
				}
				if(flag)
				{
					d.setName("Kevin");
					d.setAge(21);
					flag = false;
				}
				else
				{
					d.setName("Rocco");
					d.setAge(24);
					flag = true;
				}
				d.setFlag(true);
				d.notify();
			}	
		}
	}
}
class Send implements Runnable
{
	private Do d ;
	Send(Do d)
	{
		this.d = d;
	}
	public void run()
	{
		while (true)
		{
			synchronized(d)
			{
				if(!d.getFlag())
				{
					try{d.wait();}catch(Exception e){}
				}
				try{Thread.sleep(1000);}catch(Exception e){}
				System.out.println(d.getName()+":"+d.getAge());
				d.setFlag(false);
				d.notify();
			}
		}
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		Do d = new Do();
		new Thread(new Rece(d)).start();
		new Thread(new Send(d)).start();
	}
}