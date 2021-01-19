import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
class Demo{
	public static void main(String args[])throws Exception
	{
		System.out.println(test());
		System.out.println(test1());
	}
	//º”√‹
	public static String test() throws UnsupportedEncodingException
	{
		BASE64Encoder en = new BASE64Encoder();
		String s = en.encode(new String("”‡Œƒ≈Û").getBytes("utf-8"));
		return s;
	}
	//Ω‚√‹
	public static String test1() throws UnsupportedEncodingException, IOException
	{
		BASE64Decoder de = new BASE64Decoder();
		byte[] s = de.decodeBuffer(test());
		return new String(s,"utf-8");
	}
}