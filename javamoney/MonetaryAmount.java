-------------------------
MonetaryAmount
-------------------------
	# 表示金额
		public interface MonetaryAmount extends CurrencySupplier, NumberSupplier, Comparable<MonetaryAmount>

-------------------------
this
-------------------------
	MonetaryContext getContext();
		* 获取上下文数据

	default <R> R query(MonetaryQuery<R> query);
	default MonetaryAmount with(MonetaryOperator operator);
		* 应用操作，返回新的货币实例

	MonetaryAmountFactory<? extends MonetaryAmount> getFactory();
		* 获取创建货币金额新实例的工厂

	boolean isGreaterThan(MonetaryAmount amount);
	boolean isGreaterThanOrEqualTo(MonetaryAmount amount);
	boolean isLessThan(MonetaryAmount amount);
	boolean isLessThanOrEqualTo(MonetaryAmount amt);
	boolean isEqualTo(MonetaryAmount amount);
		* 比较

	default boolean isNegative();

	default boolean isNegativeOrZero();
	default boolean isPositive();
	default boolean isPositiveOrZero();
	default boolean isZero();
		* 判断

	int signum();

	MonetaryAmount add(MonetaryAmount augend);
		* 加

	MonetaryAmount subtract(MonetaryAmount subtrahend);
	MonetaryAmount multiply(long multiplicand);

	MonetaryAmount multiply(double multiplicand);

	MonetaryAmount multiply(Number multiplicand);

	MonetaryAmount divide(long divisor);
	MonetaryAmount divide(double divisor);

	MonetaryAmount divide(Number divisor);
	MonetaryAmount remainder(long divisor);

	MonetaryAmount remainder(double divisor);

	MonetaryAmount remainder(Number divisor);

	MonetaryAmount[] divideAndRemainder(long divisor);

	MonetaryAmount[] divideAndRemainder(double divisor);
	MonetaryAmount[] divideAndRemainder(Number divisor);

	MonetaryAmount divideToIntegralValue(long divisor);
	MonetaryAmount divideToIntegralValue(double divisor);

	MonetaryAmount divideToIntegralValue(Number divisor);

	MonetaryAmount scaleByPowerOfTen(int power);

	MonetaryAmount abs();
	MonetaryAmount negate();

	MonetaryAmount plus();

	MonetaryAmount stripTrailingZeros();
		* 返回一个在数值上等于此值的货币金额，去掉末尾多余的 0
	



-------------------------
MonetaryAmount 实现
-------------------------
	public final class FastMoney implements MonetaryAmount, Comparable<MonetaryAmount>, Serializable 
		* 为性能而优化的数字表示，它表示的货币数量是一个 long 类型的数字
	public final class Money implements MonetaryAmount, Comparable<MonetaryAmount>, Serializable
		* 内部基于java.math.BigDecimal来执行算术操作，该实现能够支持任意的precision和scale。
	public final class RoundedMoney implements MonetaryAmount, Comparable<MonetaryAmount>, Serializable 
		* 支持在每个操作之后隐式地进行舍入


