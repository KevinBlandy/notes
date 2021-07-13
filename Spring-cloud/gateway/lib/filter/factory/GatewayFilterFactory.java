---------------------------
GatewayFilterFactory
---------------------------
	# 过滤器工厂接口
		public interface GatewayFilterFactory<C> extends ShortcutConfigurable, Configurable<C>
	

---------------------------
this
---------------------------
	String NAME_KEY = "name";

	String VALUE_KEY = "value";


---------------------------
static
---------------------------
	default GatewayFilter apply(String routeId, Consumer<C> consumer) 
	default GatewayFilter apply(Consumer<C> consumer)
	default Class<C> getConfigClass()
	default C newConfig()
	GatewayFilter apply(C config);
	default GatewayFilter apply(String routeId, C config)
	default String name()
