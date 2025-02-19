-------------------------------
CurrencyConversion
-------------------------------
	# 货币兑换接口
		public interface CurrencyConversion extends MonetaryOperator, CurrencySupplier 



-------------------------------
this
-------------------------------
    ConversionContext getContext();
    ExchangeRate getExchangeRate(MonetaryAmount sourceAmount);
    ExchangeRateProvider getExchangeRateProvider();