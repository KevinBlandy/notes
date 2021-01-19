import java.util.UUID;

/**
 * UUID
 * */
public class Demo
{
	public static void main(String[] args)
	{
		run();
	}
	public static void run()
	{
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString();
		s = s.replace("-", "");//因为生成出来会有"-"这个符号。所以替换成空字符串
		System.out.println(s);
	}
}