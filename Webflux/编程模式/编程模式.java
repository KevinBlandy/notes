--------------------------
编程模式
--------------------------
	# 需要编写的核心组件
		RouterFunction
			* 路由映射接口
		
		RouterFunctions
			* 路由映射工厂类

		HandlerFunction
			* 请求处理接口
		
		HandlerFilterFunction
			* 处理器过滤器接口
		

		
--------------------------
创建
--------------------------
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.web.reactive.function.server.*;
	import reactor.core.publisher.Mono;

	@Configuration
	public class DemoRouter implements HandlerFunction {

		@Bean
		public RouterFunction<ServerResponse> router (){
			return RouterFunctions.route()
					// 指定方法以及处理器
					.GET("/router", this)
					// 添加过滤器
					.filter((request, next) -> next.handle(request))
					.build();
		}
		@Override
		public Mono<ServerResponse> handle (ServerRequest request){
			return ServerResponse.ok().bodyValue("Success");
		}
	}