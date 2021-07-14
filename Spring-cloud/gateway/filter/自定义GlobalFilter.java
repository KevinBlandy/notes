

-------------------
自定义GlobalFilter
-------------------
	# GlobalFilter 接口
		* org.springframework.cloud.gateway.filter.GlobalFilter
			public interface GlobalFilter {
				Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
			}    
	
		* 只需要实现它，并且添加到IOC即可
	
	

-------------------
前置Filter
-------------------
@Bean
public GlobalFilter customGlobalFilter() {
    return (exchange, chain) -> exchange.getPrincipal()
        .map(Principal::getName)
        .defaultIfEmpty("Default User")
        .map(userName -> {
          //adds header to proxied request
          exchange.getRequest().mutate().header("CUSTOM-REQUEST-HEADER", userName).build();
          return exchange;
        })
        .flatMap(chain::filter);
}

-------------------
后置Filter
-------------------
@Bean
public GlobalFilter customGlobalPostFilter() {
    return (exchange, chain) -> chain.filter(exchange)
        .then(Mono.just(exchange))
        .map(serverWebExchange -> {
          //adds header to response
          serverWebExchange.getResponse().getHeaders().set("CUSTOM-RESPONSE-HEADER",
              HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ? "It worked": "It did not work");
          return serverWebExchange;
        })
        .then();
}