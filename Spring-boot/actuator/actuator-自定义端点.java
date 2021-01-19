------------------------------------
自定义端点							
------------------------------------
	# 在 Bean 上标识 @Endpoint 注解
		* 在该类中中，使用 @ReadOperation, @WriteOperation 或 @DeleteOperation 注解的任何方法都将自动通过JMX暴露（在web应用程序中，也可以通过HTTP暴露）


------------------------------------
注解
------------------------------------
	@Endpoint
		String id() default "";
		boolean enableByDefault() default true;

	@ReadOperation
	@WriteOperation
	@DeleteOperation

	@WebEndpoint
	@JmxEndpoint

	@EndpointWebExtension
	@EndpointJmxExtension

	@ServletEndpoint
	@ControllerEndpoint
	@RestControllerEndpoint

	@Selector

------------------------------------
 接口	
------------------------------------
	EndpointFilter