--------------------------
MonetaryConversions
--------------------------


    public static CurrencyConversion getConversion(CurrencyUnit termCurrency, String... providers)
    public static CurrencyConversion getConversion(String termCurrencyCode, String... providers)
    public static CurrencyConversion getConversion(ConversionQuery conversionQuery)

    public static boolean isConversionAvailable(ConversionQuery conversionQuery){
    public static boolean isConversionAvailable(String currencyCode, String... providers)
    public static boolean isConversionAvailable(CurrencyUnit termCurrency, String... providers)

    public static ExchangeRateProvider getExchangeRateProvider(String... providers)
	public static ExchangeRateProvider getExchangeRateProvider(ExchangeRateProviderSupplier provider, ExchangeRateProviderSupplier... providers)
    public static ExchangeRateProvider getExchangeRateProvider(ConversionQuery conversionQuery)

    public static boolean isExchangeRateProviderAvailable(ConversionQuery conversionQuery)
    public static Collection<String> getConversionProviderNames()
    public static List<String> getDefaultConversionProviderChain()
