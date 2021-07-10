--------------------
断路器
--------------------
	# 支持断路器机制，在出现异常的时候，执行的降级处理逻辑
	# 断路机制开启的前提：
		1, classpath必选断路器的实现(实现有很多)，例如: 
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
			</dependency>
		
		2, 必需开启断路器
			feign.circuitbreaker.enabled=true

	# 通过 @FeignClient 注解的 fallback 属性，指定降级逻辑实现
		* OrderClient
			import org.springframework.cloud.openfeign.FeignClient;
			import org.springframework.web.bind.annotation.RequestMapping;
			import org.springframework.web.bind.annotation.RequestMethod;

			@FeignClient(name = "order-service", fallback = OrderClientFallback.class) // fallback 指定实现类
			public interface OrderClient {

				@RequestMapping(value = "/demo", method = RequestMethod.GET)
				String demo();
			}


		* OrderClientFallback
			import org.springframework.stereotype.Component;

			@Component		// 需要添加到ioc
			public class OrderClientFallback implements OrderClient {

				// 降级处理
				@Override
				public String demo() {
					// 也可以采取抛出异常的策略，让当前服务的异常处理机制去处理
					return "远程调用异常了";
				}
			}
	
		
		* 客户端请求异常的时候，就会返回: 远程调用异常了
		
	
	# 如果需要返回异常原因，那么可以实现: public interface FallbackFactory<T>  接口
		* FallbackFactory
			public interface FallbackFactory<T> {
				T create(Throwable cause);
				final class Default<T> implements FallbackFactory<T> {
					final Log logger;
					final T constant;
					public Default(T constant) {
						this(constant, LogFactory.getLog(Default.class));
					}
					Default(T constant, Log logger) {
						this.constant = checkNotNull(constant, "fallback");
						this.logger = checkNotNull(logger, "logger");
					}
					@Override
					public T create(Throwable cause) {
						if (logger.isTraceEnabled()) {
							logger.trace("fallback due to: " + cause.getMessage(), cause);
						}
						return constant;
					}
					@Override
					public String toString() {
						return constant.toString();
					}
				}
			}
		
		
		* OrderClient
			import org.springframework.cloud.openfeign.FeignClient;
			import org.springframework.web.bind.annotation.RequestMapping;
			import org.springframework.web.bind.annotation.RequestMethod;

			@FeignClient(name = "order-service", fallbackFactory = OrderClientFallback.class)		// 通过 fallbackFactory 指定工厂类
			public interface OrderClient {

				@RequestMapping(value = "/demo", method = RequestMethod.GET)
				String demo();
			}


		
		* OrderClientFallback
			import org.springframework.cloud.openfeign.FallbackFactory;
			import org.springframework.stereotype.Component;

			@Component  // 加入IOC
			public class OrderClientFallback implements FallbackFactory<OrderClient> {

				// 出现异常时调用，返回Feign接口的的实现，会自动调用对应的实现方法

				@Override
				public OrderClient create(Throwable cause) { // cause 就是抛出的异常
					return new OrderClient() {
						@Override
						public String demo() {
							return "发生了异常：" + cause.getMessage();
						}
					};
				}
			}
			
			客户端调用返回异常： 发生了异常：[503] during [GET] to [http://order-service/demo] [OrderClient#demo()]: [Load balancer does not contain an instance for the service order-service]
		