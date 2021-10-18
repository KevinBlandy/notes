---------------------------
ParserContext
---------------------------
	# 解析器上下文接口
		* 用于定义字符串表达式是不是模板，及模板开始与结束字符

		public interface ParserContext {
			boolean isTemplate();
			String getExpressionPrefix();
			String getExpressionSuffix();

			ParserContext TEMPLATE_EXPRESSION = new ParserContext() {
				/*
					是否是一个模板
				*/
				@Override
				public boolean isTemplate() {  
					return true;
				}
				/*
					表达式开始定界符
				*/
				@Override
				public String getExpressionPrefix() {
					return "#{";
				}
				/*
					表达式结束定界符
				*/
				@Override
				public String getExpressionSuffix() {
					return "}";
				}
			};
		}

	# TemplateParserContext 实现
		* 使用字符串模板，只有在定界符中的字符串，才是模板
			String retVal = expressionParser.parseExpression("Hello #{'World'}", new TemplateParserContext())
    										.getValue(evaluationContext, String.class);  
			