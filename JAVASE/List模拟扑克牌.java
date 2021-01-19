import java.util.*;
public class PukeDemo
{
	public static void main(String[] args)
	{
		ArrayList<String> puke = getNewPuke();
		Collections.shuffle(puke);
		ArrayList<String> kevin = new ArrayList<String>();
		ArrayList<String> Rocco = new ArrayList<String>();
		ArrayList<String> Litch = new ArrayList<String>();
		ArrayList<String> three = new ArrayList<String>();
		for (int x = 0;x < puke.size() ;x++ )
		{
			if(x > puke.size() - 4)
			{
				three.add(puke.get(x));
			}
			else if (x%3 == 0)
			{
				kevin.add(puke.get(x));
			}
			else if (x%3==1)
			{
				Rocco.add(puke.get(x));
			}
			else if(x%3==2)
			{
				Litch.add(puke.get(x));
			}
		}
		System.out.println(kevin);
		System.out.println(Rocco);
		System.out.println(Litch);
		System.out.println(three);
	}
	public static ArrayList<String> getNewPuke()
	{
		ArrayList<String> al = new ArrayList<String>();
		String[] colors = {"黑桃","红心","梅花","方块"};
		String[] numbers = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		for(String color : colors)
		{
			for(String num : numbers)
			{
				al.add(color.concat(num));
			}
		}
		al.add("小王");
		al.add("大王");
		return al;
	}
}