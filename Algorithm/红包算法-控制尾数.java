
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;


public class RedPacketHelper {
	
	/**
	 * 计算红包的随机金额。最后一个红包，直接返回剩余金额
	 * @param amount	总金额
	 * @param number	红包总个数
	 * @return
	 */
	protected static Long randomAmount(Long amount, Integer number) {
		/** ------------ 参数校验开始 ------------- **/
		if (amount < 1) {
			throw new IllegalArgumentException("红包金额不能小于1分钱：");
		}
		if (number < 1) {
			throw new IllegalArgumentException("红包个数不能小于1个");
		}
		if (amount < number) {
			if (number != null) {
				throw new IllegalArgumentException(String.format("非法的红包参数，%s 元不能分给 %s 个人", format(fenToYuan(amount)), number));
			}
		}
		/** ------------ 参数校验结束 ------------- **/
		
		// 最后1个红包，返回总金额
		if (number == 1) {
			return amount;
		}
		
        // 计算出红包的最大金额
        // 最大金额 = (总金额 / 总个数)(平均值舍掉小数) * 2
        BigDecimal maxValue = new BigDecimal(amount).divide(new BigDecimal(number), 0, RoundingMode.DOWN).multiply(new BigDecimal(2));
        
        // 获取 0% - 99% 随机概率值
        // 0.00 - 0.99
        BigDecimal random = new BigDecimal(ThreadLocalRandom.current().nextInt(100)).divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY);
        
        // 随机获取金额(四舍五入掉小数位)
        long randomValue = maxValue.multiply(random, MathContext.UNLIMITED).longValue();

        return randomValue == 0 ? 1: randomValue;
	}
	
	/**
	 * 计算出红包的金额分布
	 * @param amount	金额，最低1元。单位是分
	 * @param number	红包个数。最低1个
	 * @param mines		雷点号。如果为null，表示随机雷点
	 */
	public static LinkedList<Long> scatter(Long amount, Integer number, Integer[] mines) {

		/** ------------ 参数校验开始 ------------- **/
		if (amount < 100) {
			throw new IllegalArgumentException("红包金额不能小于1块钱");
		}
		if (number < 1) {
			throw new IllegalArgumentException("红包个数不能小于1个");
		}
		if (mines != null ) {
			if (mines.length >= number) {
				throw new IllegalArgumentException("雷点数量必须小于发包数量");
			}
			for (Integer mine : mines) {
				if (mine < 0 || mine > 9) {
					throw new IllegalArgumentException("雷点号只能是：0 - 9"); 
				}
			}
		}
		/** ------------ 参数校验结束 ------------- **/
		
		LinkedList<Long> scatter = new LinkedList<>();
		
		
		// 需要手动的控制雷点
		if (mines != null) {
			
			for (Integer mine : mines) {
				
				// 计算出随机金额
				Long randomAmount = randomAmount(amount, number);
				
				// 尾号
				Integer last = (int) (randomAmount % 10);
				
				// 雷点补偿
				if (mine !=- last) {
					randomAmount += mine - last;
					/**
					 *  如果雷号是0，并且红包金额小于1角(0.01 - 0.09)，上面的计算会导致红包金额直接为0。
					 *  处理方式： 直接处理为1毛钱
					 */
					if (randomAmount == 0) {
						randomAmount = 10L;
					}
				}
				
				// 雷号添加到队列头
				scatter.addFirst(randomAmount);
				
				// 总金额减少
				amount -= randomAmount;
				// 红包总数量减少
				number --;
			}
		}
		
		// 随机生成普通的金额
		while (number > 0) {
			Long randomAmount = randomAmount(amount, number);
			scatter.addLast(randomAmount);
			amount -= randomAmount;
			number --;
		}
		
		return scatter;
	}
	
    public static Double fenToYuan(Long fen) {
    	try {
    		return new BigDecimal(fen).divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY).doubleValue();
    	}catch (ArithmeticException e) {
    		throw e;
		}
    }
    
    public static String format(Double value) {
    	return new DecimalFormat("#.##").format(value);
    }
	
	public static void main(String[] args) {
		for (int x = 0 ;x < 1000000 ;x ++) {
			
			// Long amount = ThreadLocalRandom.current().nextLong(100, 100 * 1000);
			
			// 随机 1 - 999块钱，整数金额
			Long amount = ThreadLocalRandom.current().nextLong(1, 1000) * 100;
			// 随机2 - 10个包
			Integer number = ThreadLocalRandom.current().nextInt(2, 10);
			
			// 随机雷点（0 - (包数 - 1)）
			Integer[] mines = new Integer[ThreadLocalRandom.current().nextInt(number - 1)];
			
			for (int i = 0; i < mines.length ; i ++) {
				mines[i] = ThreadLocalRandom.current().nextInt(10);
			}
			
			LinkedList<Long> result = scatter(amount, number, mines);
			System.out.print("总金额：" + fenToYuan(amount).longValue() + " 元 总个数：" + number);
			
			System.out.print(" 雷点分布：[");
			for (Integer mine : mines) {
				System.out.print(mine + ", ");
			}
		
			System.out.print("]");
			System.out.print(" 红包分布：[");
			for (Long val : result) {
				System.out.print(format(fenToYuan(val)) + ", ");
				if (val <= 0) {
					throw new RuntimeException("异常：红包出现了0或者更小的金额");
				}
			}
			
			Long total = result.stream().reduce((t, u) -> {
				return t + u;
			}).get();
			
			boolean success = total.equals(amount);
			
			if (!success) {
				throw new RuntimeException("异常：金额不匹配");
			}
			
			System.out.println("] 总金额是否匹配：" + success);
		}
	}
}

