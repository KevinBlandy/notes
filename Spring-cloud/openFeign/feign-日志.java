---------------------
Feign 日志
---------------------
	# 可以通过配置为feign添加日志记录器
		

	# 开启DEBUG日志，默认的日志记录器名称就是: 客户端的接口的完整类名
		logging.level.com.demo.user.client.OrderClient=DEBUG
		
		
		* 只有在 DEBUG 级别，feign才会输出日志信息
		*  com.demo.user.client.OrderClient 就是接口类名
	
	# 通过代码配置日志的输出选项

		import feign.Logger;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;

		@Configuration
		public class FeignConfiguration {

			// 日志的输出内容
			@Bean
			public Logger.Level feignLoggerLevel() {
				return Logger.Level.FULL;
			}
		}

		* 日志输出内容枚举可选值
			NONE	无日志记录（默认）。
			BASIC	只记录请求方法和 URL 以及响应状态码和执行时间。
			HEADERS	记录基本信息以及请求和响应头。
			FULL	记录请求和响应的标头、正文和元数
					

		* 输出的日志
			2021-07-10 16:01:32.635 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] ---> GET http://order-service/demo HTTP/1.1
			2021-07-10 16:01:32.636 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] ---> END HTTP (0-byte body)
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] <--- HTTP/1.1 200 OK (141ms)
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] connection: keep-alive
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] content-length: 7
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] content-type: text/plain;charset=utf-8
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] date: Sat, 10 Jul 2021 08:01:32 GMT
			2021-07-10 16:01:32.780 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] 
			2021-07-10 16:01:32.781 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] success
			2021-07-10 16:01:32.781 DEBUG 14416 --- [pool-1-thread-1] com.demo.user.client.OrderClient         : [OrderClient#demo] <--- END HTTP (7-byte body)