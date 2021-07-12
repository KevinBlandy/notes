--------------------------
Filter
--------------------------
	# 接口定义: GatewayFilter

		public interface GatewayFilter extends ShortcutConfigurable {
			String NAME_KEY = "name";
			String VALUE_KEY = "value";
			Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
		}


---------------------
GlobalFilter
---------------------
	# 接口定义: GlobalFilter
		public interface GlobalFilter {
			Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
		}
		
	# GlobalFilter 的功能其实和GatewayFilter是相同的，只是GlobalFilter的作用域是所有的路由配置，而不是绑定在指定的路由配置上。
	# 多个GlobalFilter可以通过 @Orde r或者 getOrder() 方法指定每个 GlobalFilter 的执行顺序
		* pre类型过滤器如果order值越小，那么它就应该在pre过滤器链的顶层，在请求之前最先执行
		* post类型过滤器如果order值越小，那么它就应该在pre过滤器链的底层，在请求之后最先执行
	

	# 内建的GlobalFilter
		ForwardRoutingFilter		重定向
		LoadBalancerClientFilter	负载均衡
		NettyRoutingFilter			Netty的HTTP客户端的路由
		NettyWriteResponseFilter	Netty响应进行写操作
		RouteToRequestUrlFilter		基于路由配置更新URL
		WebsocketRoutingFilter		Websocket请求转发到下游
	
	# 通过为所有路由都添加过滤器
		spring:
		  cloud:
			gateway:
			  default-filters:
			    - AddResponseHeader=X-Response-Default-Red, Default-Blue
			    - PrefixPath=/httpbin
		
