/*
  去除字符串两端的空格 
  反转字符串
  反转谋一部分字符串
 */
public class Demo
{
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args)
	{
		String str = "   abcde    ";
		sop("["+str+"]");	
		//str = myTrim(str);
		//sop("["+str+"]");
		str = fanZhuan(str);
		sop("["+str+"]");
		str = fanZhuan(str,4,6);
		sop("["+str+"]");
	}
	public static String myTrim(String str)//去除空格
	{
		int start = 0,end = str.length() - 1;
		while(start <= end && str.charAt(start)==' ')
		{
			start++;
		}
		while(start <= end && str.charAt(end)==' ')
		{
			end--;
		}
		return str.substring(start,end+1);
	}
	public static String fanZhuan(String str)//反转字符串
	{
		char[] ch = str.toCharArray();
		reverse(ch);
		return new String(ch);
	}
	private static void reverse(char[] ch)//遍历字符数组
	{
		for(int start = 0,end = ch.length-1;start < end;start++,end--)
		{
			swap(ch,start,end);
		}
	}
	public static void swap(char[] ch,int x,int y)//交换数组成员
	{
		char temp = ch[x];
		ch[x] = ch[y];
		ch[y] = temp;
	}
	public static String fanZhuan(String str ,int start,int end)//只反转部分字符。函数重载
	{
		char[] ch = str.toCharArray();
		reverse(ch,start,end);
		return new String(ch);
	}
	private static void reverse(char[] ch,int x,int y)//遍历部分字符数组。函数重载
	{
		for(int start = x,end = y-1;start < end;start++,end--)
		{
			swap(ch,start,end);
		}
	}
}
































