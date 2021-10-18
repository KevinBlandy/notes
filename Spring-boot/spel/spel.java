-------------
spel
-------------
	# Spring表达式语言全称为 "Spring Expression Language"，缩写为"SpEL"
	# 地址
		https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions
	
	# SpEL使用4个步骤
		1. 创建解析器 SpEL使用ExpressionParser接口表示解析器，提供SpelExpressionParser默认实现；
		2. 解析表达式 使用ExpressionParser的parseExpression来解析相应的表达式为Expression对象。
		3. 构造上下文 准备比如变量定义等等表达式需要的上下文数据。
		4. 求值 通过Expression接口的getValue方法根据上下文获得表达式值。

	    // 执行上下文
    	EvaluationContext evaluationContext = new StandardEvaluationContext();
    	evaluationContext.setVariable("param", "World");
    	
    	// 解析器
    	ExpressionParser expressionParser = new SpelExpressionParser();
    	
    	// 解析SpEL表达式
    	Expression expression = expressionParser.parseExpression("'Hello'.concat(#param)");
    	
    	// 在上下文环境中执行表达式，获取结果
    	String result = expression.getValue(evaluationContext, String.class);
    	System.out.println(result); // HelloWorld
	
--------------------------
模板表达式
--------------------------
	
	