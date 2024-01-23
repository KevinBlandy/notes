package app.test;

import java.time.DayOfWeek;

public class ApplicationMainTest {

	public static void main(String[] args) throws Exception {
		DayOfWeek val = DayOfWeek.SUNDAY;
		
		// 旧版 -> 匹配，如果不 break 会从匹配到的元素往下依次执行
		switch (val) {
			case SUNDAY: {
				System.out.println("原始:周日");
			}
			case MONDAY: {
				System.out.println("原始:周一");
			}
			default:
				break;
		}
		
		// 新版， -> 匹配，自动 break
		// 可以使用代码块
		switch (val) {
			case SUNDAY -> {
				System.out.println("新版：周日");
			}
			case MONDAY -> {
				System.out.println("新版：周一");
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + val);
		}
		
		// 使用 yield 返回 case 结果，自动 break
		// 可以使用代码块 {}
		String v = switch (val) {
		case SUNDAY: yield "周日";
		case MONDAY: {
			yield "周一";
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + val);
		};
		System.out.println("yield：" + v);
		
		
		// 使用 -> 返回 case 结果，自动 break
		// -> 后面只能跟一条语句
		String ret = switch (val) {
			case SUNDAY -> "周日";
			case MONDAY -> "周一";
			default -> throw new IllegalArgumentException("Unexpected value: " + val);
		};
		
		System.out.println("->：" + ret);

		// -> 和 yield 不能混用
	}
}

// 输出如下
原始:周日
原始:周一
新版：周日
yield：周日
->：周日
