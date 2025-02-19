---------------
Monetary
---------------


---------------
static
---------------
	static Collection<MonetaryAmountFactory<?>>	getAmountFactories()

	static Collection<MonetaryAmountFactory<?>>	getAmountFactories(MonetaryAmountFactoryQuery query)

	static <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(Class<T> amountType) 
		* 根据指定的 MonetaryAmount 类型实现，获取其 Factory 对象
		

	static MonetaryAmountFactory	getAmountFactory(MonetaryAmountFactoryQuery query)
	static Collection<Class<? extends MonetaryAmount>>	getAmountTypes()

	static Collection<CurrencyUnit>	getCurrencies(CurrencyQuery query)
	static Set<CurrencyUnit>	getCurrencies(Locale locale, String... providers)
	static Collection<CurrencyUnit>	getCurrencies(String... providers)

	static CurrencyUnit	getCurrency(CurrencyQuery query)
	static CurrencyUnit	getCurrency(Locale locale, String... providers)
	static CurrencyUnit	getCurrency(String currencyCode, String... providers)
		* 获取货币单位

		query
			* 查询
		locale
			* Local
		currencyCode
			* 货币代码
		
	static Set<String>	getCurrencyProviderNames()
	static MonetaryAmountFactory<?>	getDefaultAmountFactory()
	static Class<? extends MonetaryAmount>	getDefaultAmountType()
	static List<String>	getDefaultCurrencyProviderChain()

	static MonetaryRounding	getDefaultRounding()
	static List<String>	getDefaultRoundingProviderChain()
	static MonetaryRounding	getRounding(CurrencyUnit currencyUnit, String... providers)
	static MonetaryRounding	getRounding(RoundingQuery roundingQuery)
	static MonetaryRounding	getRounding(String roundingName, String... providers)
	static Set<String>	getRoundingNames(String... providers)
	static Set<String>	getRoundingProviderNames()
	static Collection<MonetaryRounding>	getRoundings(RoundingQuery roundingQuery)

	static boolean	isAvailable(MonetaryAmountFactoryQuery query)
	static boolean	isCurrencyAvailable(Locale locale, String... providers)
	static boolean	isCurrencyAvailable(String code, String... providers)
	static boolean	isRoundingAvailable(CurrencyUnit currencyUnit, String... providers)
	static boolean	isRoundingAvailable(RoundingQuery roundingQuery)
	static boolean	isRoundingAvailable(String roundingName, String... providers)
