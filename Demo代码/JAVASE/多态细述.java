abstract class Animal
{
	abstract void eat();
}
class Cat extends Animal
{
	public void eat()
	{
		System.out.println("Cat类覆写父类的抽象方法.是现自己的eat方式");
	}
	public void catchMose()
	{
		System.out.println("Cat is catchMose");
	}
}
class Dog extends Animal
{
	public void eat()
	{
		System.out.println("Dog类覆写父类的抽象方法。实现自己的eat方式");
	}
	public void play()
	{
		System.out.println("Dog is play");
	}
}
class Pig extends Animal
{
	public void eat()
	{
		System.out.println("Pig类覆写父类抽象方法。实现自己的eat方式");
	}
	public void sleep()
	{
		System.out.println("Pig is sleep");
	}
}
class Test
{
	public void getAnimal(Animal a)
	{
		if (a instanceof Cat)
		{
			Cat c = (Cat)a;
			c.catchMose();
		}
		else if (a instanceof Dog)
		{
			Dog d = (Dog)a;
			d.play();
		}
		else if (a instanceof Pig)
		{
			Pig p = (Pig)a;
			p.sleep();
		}
	}
}
public class Demo
{
	public static void main(String[] args)
	{
		Test t = new Test();
		t.getAnimal(new Pig());
	}
}









