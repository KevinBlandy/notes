
----------------------------
java12
----------------------------
	# 增强的 switch 表达式
		switch (val) {
			case "1" -> System.out.println();
			case "2", "3" -> System.out.println();
			default -> System.out.println();
		}

		var reult = switch (val) {
			case "1" -> "1";
			case "2", "3" -> "1";
			default -> "1";
		};

		* 支持这种 switch 返回结果
		* 对于需要返回值的switch expression, 要么正常返回值要么抛出异常
	
	# 引入 JVM 常量 API
		* java.lang.constant
			ConstantDesc
				|-Double
				|-DynamicConstantDesc
					|-EnumDesc
					|-VarHandleDesc
				|-Float
				|-Integer
				|-Long
				|-String
				|-ClassDesc
				|-MethodHandleDesc
				|-MethodTypeDesc
	
	
	# 新增Api
		String 
			<R> R transform(Function<? super String, ? extends R> f)
				* 把字符串流转换为其他的对象

			String indent(int n)
				* 调整每一行的缩进
		
		Files
			long mismatch(Path path, Path path2)
				* 查找并返回内容中第一个不匹配字节的位置
				* 如果找不到返回 -1
		
		Collectors
			static <T, R1, R2, R> Collector<T, ?, R> teeing(Collector<? super T, ?, R1> downstream1,
																  Collector<? super T, ?, R2> downstream2,
																  BiFunction<? super R1, ? super R2, R> merger)
				
				* 用于聚合两个downstream的结果
		
		CompletionStage
			exceptionallyAsync
			exceptionallyCompose
			exceptionallyComposeAsync
		
