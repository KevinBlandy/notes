import java.util.Calendar;
import java.util.Scanner;
import java.io.*;
public class Demo
{
	public static void main(String[] args)throws Exception
	{
		System.out.println();
		Scanner s = new Scanner(System.in);
		int year = s.nextInt();
		if(year%4==0 && year%100!=0 || year%400 == 0)
		{
			System.out.println("»ÚƒÍ");
		}
		else
		{
			System.out.println("∑«»ÚƒÍ");
		}
		get(year);
	}
	public static void get(int s)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, s);
		calendar.set(Calendar.MONTH, 2);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	}
}