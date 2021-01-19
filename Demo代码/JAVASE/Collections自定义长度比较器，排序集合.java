import java.util.*;
class CollectionsDemo 
{
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	public static void main(String[] args) 
	{
		ArrayList al = new ArrayList();
		al.add("aa");
		al.add("loomn");
		al.add("lqw");
		al.add("mhg");
		al.add("hre");
		al.add("rg");
		al.add("qdw");
		al.add("dqwdq");
		System.out.println(al);
		Collections.sort(al,new mySort());
		System.out.println(al);
	}
}
//自定义比较器，按照字符串长度来进行排序
class mySort implements Comparator<String>
{
	public int compare(String s1,String s2)
	{
		if (s1.length() > s2.length())
		{
			return 1;
		}
		if(s1.length() < s2.length())
		{
			return -1;
		}
		return s1.compareTo(s2);
	}
}
