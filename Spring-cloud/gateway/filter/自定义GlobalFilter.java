

-------------------
自定义GlobalFilter
-------------------
	# GlobalFilter 接口
		* org.springframework.cloud.gateway.filter.GlobalFilter
			public interface GlobalFilter {
				Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
			}    
	
		* 只需要实现它，并且添加到IOC即可
	
	
