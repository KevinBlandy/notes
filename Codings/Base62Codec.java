
import java.util.regex.Pattern;

/**
 * 62进制编码
 * @author Administrator
 *
 */
public class Base62Codec {

	// 原始字符
	private static char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	// 62 进制
	private static int SCALE = 62;

	// 6位编码最大值
	private static final long MAX_VALUE = 56800235583L;

	// 字符正则，最大6位长度
	private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,6}$");

	public static void main(String[] args) {
		for (long i = 0; i < 1000000; i += 10000) {
			String val = encode(i);
			System.out.println(val + ":" + decode(val));
		}
		System.out.println(decode("zzzzzz"));
	}


	/**
	 * 数字转换为62进制，如果小于0或者大于最大值，返回null
	 * @param num
	 * @return
	 */
	public static String encode(long num) {
		if (num < 0 || num > MAX_VALUE) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		int remainder;
		while (num > SCALE - 1) {
			remainder = Long.valueOf(num % SCALE).intValue();
			stringBuilder.append(CHARS[remainder]);
			num = num / SCALE;
		}
		stringBuilder.append(CHARS[(int) num]);
		return stringBuilder.reverse().toString();
	}

	/**
	 * 62进制转换为数值，如果字符串大于6个长度或者非法，返回-1
	 * @param str
	 * @return
	 */
	public static long decode(String str) {
		if(!PATTERN.matcher(str).matches()) {
			return -1;
		}
		long value = 0;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			value += (long) (indexOf(chars[i]) * Math.pow(SCALE, chars.length - i - 1));
		}
		return value;
	}

	private static int indexOf(char ch) {
		int low = 0;
		int high = CHARS.length - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			char midVal = CHARS[mid];
			if (midVal < ch) {
				low = mid + 1;
			} else if (midVal > ch) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -(low + 1);
	}
}
