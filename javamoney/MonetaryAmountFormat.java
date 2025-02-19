----------------------------------
MonetaryAmountFormat
----------------------------------
	# 货币格式化接口
		interface MonetaryAmountFormat extends MonetaryQuery<String>
	
	AmountFormatContext getContext();
    default String format(MonetaryAmount amount);
    void print(Appendable appendable, MonetaryAmount amount) throws IOException;
    MonetaryAmount parse(CharSequence text) throws MonetaryParseException;
