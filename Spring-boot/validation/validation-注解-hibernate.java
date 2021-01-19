-------------------------
Hibernate 提供的注解
-------------------------
	@CodePointLength
		* 验证字符序列的代码点长度是否介于包含的最小值和最大值之间

		int min() default 0;
		int max() default Integer.MAX_VALUE;
		NormalizationStrategy normalizationStrategy() default NormalizationStrategy.NONE;
			* 枚举
				NONE
				NFD
				NFC
				NFKD
				NFKC

	@Range
		* 值必须在指定范围内

	@URL
		* 验证字符串是一个URL

		String protocol() default "";
			* 允许的协议
		String host() default "";
			* 允许的HOST
		int port() default -1;
			* 允许的端口
		String regexp() default ".*";
			* 验证的正则
		Pattern.Flag[] flags() default { };
			* 正则的Flag

	@Length
		* 验证字符串长度必须在指定范围内

		int min() default 0;
		int max() default Integer.MAX_VALUE;