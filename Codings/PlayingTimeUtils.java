import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 
 * 播放时长工具
 * @author Administrator
 *
 */
public class PlayingTimeUtils {
	
	// 一小时60分钟
	private static final int MINUTES_PER_HOUR = 60;
	
	// 一分钟60秒
	private static final int SECONDS_PER_MINUTE = 60;
	
	// 一小时3600秒
	private  static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	
	/**
	 * 把播放时间表达式转换为秒
	 * @param expression	HH:mm:ss，最大：999:59:59，最小：00:00:00
	 * @return
	 */
	public static int encodeToSeconds(String expression) {
		String[] results = expression.split(":");
		if (results.length > 0 && results.length < 4) {
			
			int flag = 0;
			
			int seconds = 0;
			int minute = 0;
			int hour = 0; 
			
			for (int i = results.length - 1;  i >= 0; i --) {
				if (flag == 0) {
					seconds = parseTimeInt(results[i], 59);
				} else if (flag == 1) {
					minute = parseTimeInt(results[i], 59);
				} else if (flag == 2) {
					hour = parseTimeInt(results[i], 999);
				}
				
				flag ++;
			}
			
			int total = hour * SECONDS_PER_HOUR;
	        total += minute * SECONDS_PER_MINUTE;
	        total += seconds;
	        return total;
		}
		throw new IllegalArgumentException("播放时长表达式不合法。必须是：HH:mm:ss，例如（01:21:15），并且不能大于：999:59:59");
	}

	
	private static int parseTimeInt(String val, int max) {
		try {
			int retVal = Integer.parseInt(val);
			if (retVal < 0) {
				throw new IllegalArgumentException("表达式中的数值，不能为负数");
			} 
			if (retVal > max) {
				throw new IllegalArgumentException("表达式中的数值，不能大于：" + max);
			}
			return retVal;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("表达式中存在非数值的字符");
		}
	}
	
	/**
	 * 把秒，转换为播放时间字符串
	 * @param seconds
	 * @return
	 */
	public static String formart(int seconds) {
		int hour = 0;
		int minte = 0;
		while (seconds >= 60) { // 每次递增1分钟
			minte ++;
			if (minte == 60) {
				minte = 0;
				hour ++;
			}
			seconds -= 60;
		}
		return Stream.of(hour, minte, seconds).map(i -> i > 9 ? "" + i : "0" + i).collect(Collectors.joining(":"));
	}
	
	public static void main(String[] args) {
		int seconds = encodeToSeconds("01:59");
		System.out.println(seconds);
		System.out.println(formart(seconds));
	}
}
