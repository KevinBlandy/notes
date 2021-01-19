/*
把字符串反转操作
1，将字符串变成数组
2，对数组反转
3，将数组变成字符串
*/
public class Demo
{
	public static void sop(String s)
	{
		System.out.println(s);
	}
	public static void main(String[] args)
	{
		String s = "KevinBlandy";
		sop("["+s+"]");
		sop("["+getString(s)+"]");
		String s1 = getString(s);
		sop("["+getString(s1)+"]");//再度把s1进行反转操作
	}
	public static String getString(String s)
	{
		char[] chs = s.toCharArray();
		reverse(chs);
		return new String(chs);
	}
	private static void reverse(char[] arr)
	{
		for(int start = 0,end = arr.length-1;start<end;start++,end--)
		{	
			swap(arr,start,end);
		}
	}
	private static void swap(char[] arr,int x,int y)
	{
		char temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
}