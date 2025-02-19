-----------------------------
JavaMoney
-----------------------------
	# JSR-354 定义了一套标准的API用来解决和货币相关的问题

	# 官方
		http://javamoney.org/
		https://github.com/JavaMoney/

	# Maven
		https://mvnrepository.com/artifact/org.javamoney/moneta


		<!-- https://mvnrepository.com/artifact/org.javamoney.lib/javamoney-lib -->
		<dependency>
			<groupId>org.javamoney.lib</groupId>
			<artifactId>javamoney-lib</artifactId>
			<version>1.1</version>
			<type>pom</type>
		</dependency>



-----------------------------
核心 API
-----------------------------

	
CurrencyUnit
	* 表示货币单位
MonetaryAmount
	* 表示货币金额，提供了 3 个实现
MonetaryOperator
	* 对货币的操作，返回新的货币
CurrencyConversion
	* 货币兑换，本身实现了 MonetaryOperator 接口
MonetaryFormat
	* 货币格式化



-----------------------------
Demo
-----------------------------
		// 根据金额、货币类型创建货币
		var money = Money.of(new BigDecimal("123.89999"), Monetary.getCurrency("CNY"));
		System.out.println(money);	// CNY 123.89999

		// 向上取整，保留 2 位小数
		money = money.with(MonetaryOperators.rounding(RoundingMode.DOWN, 2));

		// money.with(Monetary.getRounding(RoundingQueryBuilder.of().setScale(2).set(RoundingMode.DOWN).build())); // 也可以

		System.out.println(money);	// CNY 123.89
		
		// 货币汇率转换 SPI 实现提供者的名称
		for (var providerName : MonetaryConversions.getConversionProviderNames()){
			System.out.println("conversionProviderName:" + providerName);
			/*
				conversionProviderName:ECB-HIST90
				conversionProviderName:IMF
				conversionProviderName:IDENT
				conversionProviderName:ECB-HIST
				conversionProviderName:ECB
				conversionProviderName:IMF-HIST
			*/
		}
		
		// 获取指定的货币兑换器，即要转换什么类型的货币
		CurrencyConversion conversion = MonetaryConversions.getConversion(Monetary.getCurrency("USD"));
		
		// 转换为美元
		money = money.with(conversion);
		System.out.println(money); // USD 17.013003
		
		// 根据 local 获取格式化器
		var formatter = MonetaryFormats.getAmountFormat(Locale.CHINA);
		System.out.println(formatter.format(money));  // USD17.01