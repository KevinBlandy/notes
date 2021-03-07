package sex.poppy.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 62进制编码
 * 
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
	public static Long decode(String str) {
		if (!PATTERN.matcher(str).matches()) {
			return null;
		}
		long value = 0;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			value += (long) (indexOf(chars[i]) * Math.pow(SCALE, chars.length - i - 1));
		}
		return value;
	}
	
	/**
	 * 编码为62进制字符串，如果结果小于6个字符串长度，则在前面添加0
	 * @param val
	 * @return
	 */
	public static String encodePad0(long val) {
		String retVal = encode(val);
		return retVal == null ? null : StringUtils.leftPad(retVal, 6, "0");
		
	}
	
	/**
	 * 解码62进制字符串，会先把前面填充的0移除
	 * @param str
	 * @return
	 */
	public static Long decodePad0(String str) {
		str = str.replace("^0*", "");  // TODO BUG，如果是 "000000"，会被替换为 ""
		return decode(str);
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
	
	public static void main(String[] args) {
		for (long i = 0; i < 10000000; i ++) {
			String val = encodePad0(i);
			System.out.println(val + ":" + decodePad0(val));
		}
	}
}
