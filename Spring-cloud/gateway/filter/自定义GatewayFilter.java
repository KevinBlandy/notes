--------------------------
GatewayFilter
--------------------------
	# 需要实现接口: org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory
		
		* 这个接口抽象方法实在是太多了，建议继承抽象类
			org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory

		* 泛型参数是一个配置类
	
	# 所有的抽象方法
		String NAME_KEY = "name";
		String VALUE_KEY = "value";

		GatewayFilter apply(String routeId, Consumer<C> consumer)
		GatewayFilter apply(Consumer<C> consumer)
			* 执行Filter方法

		Class<C> getConfigClass()
		C newConfig()
			* 获取配置类信息，以及创建配置类实例

		String name()
			* 返回过滤器名称

		ShortcutType shortcutType()
			* 枚举
				DEFAULT
				GATHER_LIST
				GATHER_LIST_TAIL_FLAG
			
		List<String> shortcutFieldOrder()
		String shortcutFieldPrefix()
			* 简化配置

		
	# 步骤
		1, 实现GatewayFilterFactory接口或者继承AbstractGatewayFilterFactory。
		2, 对应的子类注册到Spring的容器。
		3, 路由配置中的 filters 属性添加对应 GatewayFilter 配置，注意一下，过滤器名称由 GatewayFilterFactory#name() 决定。
	
	# 配置类
		* 预定义的Filter配置类都是内部类存在的
	
	# 简化配置
		* 配置方法
			ShortcutType shortcutType()
				* 枚举
					DEFAULT
					GATHER_LIST
					GATHER_LIST_TAIL_FLAG
				
			List<String> shortcutFieldOrder()
			String shortcutFieldPrefix()
				* 简化配置
	
	# 网关命名
		* 类名称使用 GatewayFilterFactory 结尾。例如: SomethingGatewayFilterFactory
		* 如果符合规范命名，那么不需要复写 name() 方法，则该过滤器名称就是前缀: Something

		* 如果类命名不规范，则通过复写 name() 方法来定义过滤器名称
		

--------------------------
Demo
--------------------------
	# DemoFilter
		import org.springframework.cloud.gateway.filter.GatewayFilter;
		import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
		import org.springframework.http.server.reactive.ServerHttpRequest;
		import org.springframework.stereotype.Component;

		@Component
		public class DemoFilter extends AbstractGatewayFilterFactory<DemoFilter.Config> {

			public DemoFilter(){
				// 通过构造函数设置配置类
				super(Config.class);
			}

			@Override
			public GatewayFilter apply(DemoFilter.Config config) {
				return (exchange, chain) -> {

					// 前置执行
					ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
						httpHeaders.set(config.getHeaderName(), config.getHeaderValue());
					}).build();

					return chain.filter(exchange.mutate().request(request).build());
					//  return chain.filter(exchange.mutate().request(request).build()).then(Mono.fromRunnable(() -> {
					   // 后置执行
					// }));
				};
			}

			// Filter名字
			@Override
			public String name() {
				return "Demo";
			}

			/**
			 * 配置类，属性可以通过配置args注入
			 */
			public static class Config {
				private String headerName;
				private String headerValue;
				public String getHeaderName() {
					return headerName;
				}

				public void setHeaderName(String headerName) {
					this.headerName = headerName;
				}

				public String getHeaderValue() {
					return headerValue;
				}

				public void setHeaderValue(String headerValue) {
					this.headerValue = headerValue;
				}
			}
		}
	
	# 配置
	      filters:
            - name: RequestSize
              args:
                maxSize: 10KB
            - name: Demo
              args:
                headerName: "foo-name"
                headerValue: "foo-value"