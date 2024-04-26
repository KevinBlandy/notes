
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.util.Assert;

/**
 * 
 * 红包工具类
 * 
 */
public class RedEnvelopeUtils {

	/**
	 * 最低金额 0.01 元
	 */
	static final BigDecimal MINIMUM_AMOUNT = new BigDecimal("0.01");

	/**
	 * 100 常量
	 */
	static final BigDecimal HUNDRED = new BigDecimal("100");

	/**
	 * 2 常量
	 */
	static final BigDecimal TWO = new BigDecimal("2");

	/**
	 * 计算红包的随机金额。最后一个红包，直接返回剩余金额
	 * 
	 * @param amounts
	 * @param count
	 * @return
	 */
	protected static BigDecimal randomAmounts(BigDecimal amounts, int count) {

		// 最后1个红包，返回总金额
		if (count == 1) {
			return amounts;
		}

		// 计算出红包的最大金额
		// 最大金额 = (总金额 / 总个数)(平均值舍掉小数) * 2
		BigDecimal maxAmounts = amounts.divide(new BigDecimal(count), 2, RoundingMode.DOWN).multiply(TWO);
		
		// 随机金额比例： 1% - 99%
		BigDecimal rate = new BigDecimal(ThreadLocalRandom.current().nextInt(1, 100)).divide(HUNDRED);
		
		// 随机获取金额，最多保 2 位小数，多余舍掉
		BigDecimal finalAmounts = maxAmounts.multiply(rate).setScale(2, RoundingMode.DOWN);

		// 如果不足 1 分，则最低得到 1 分
		return finalAmounts.compareTo(MINIMUM_AMOUNT) < 0 ? MINIMUM_AMOUNT : finalAmounts;
	}

	/**
	 * 计算红包分布
	 * 
	 * @param amounts
	 * @param count
	 * @return
	 */
	public static List<BigDecimal> scatter(BigDecimal amounts, int count) {

		Objects.requireNonNull(amounts);

		Assert.isTrue(count > 0, "红包数量不能小于 1");
		Assert.isTrue(amounts.compareTo(MINIMUM_AMOUNT) >= 0 && amounts.scale() <= 2, "红包金额不能小于 1 分钱，且精度不能低于分");
		Assert.isTrue(amounts.multiply(HUNDRED).intValueExact() >= count, String.format("非法的红包参数：%s 元不能分给 %s 个人", amounts.toPlainString(), count));

		List<BigDecimal> ret = new ArrayList<>(count);

		while (count > 0 && amounts.compareTo(BigDecimal.ZERO) > 0 ) {

			BigDecimal randAmounts = randomAmounts(amounts, count);

			ret.add(randAmounts);
			
			amounts = amounts.subtract(randAmounts);
			
			count--;
		}

		return ret;
	}
}
