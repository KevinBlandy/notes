------------------------
WebFluxConfigurer
------------------------
	# WebFlux的配置接口: WebFluxConfigurer 

		default void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {}

		default void addCorsMappings(CorsRegistry registry) {}

		default void configurePathMatching(PathMatchConfigurer configurer) {}

		default void addResourceHandlers(ResourceHandlerRegistry registry) {}

		default void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {}

		default void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {}
			* http消息编解码器的配置
			
		default void addFormatters(FormatterRegistry registry) {}

		@Nullable
		default Validator getValidator() {
			return null;
		}

		@Nullable
		default MessageCodesResolver getMessageCodesResolver() {
			return null;
		}

		@Nullable
		default WebSocketService getWebSocketService() {
			return null;
		}
		default void configureViewResolvers(ViewResolverRegistry registry) {}

	# 高级配置实现: DelegatingWebFluxConfiguration 
		|-WebFluxConfigurationSupport
			|-DelegatingWebFluxConfiguration
		
		* 可以自定义继承类，代替 WebFluxConfigurer 的实现
			@Configuration
			public class WebfluxConfiguration extends DelegatingWebFluxConfiguration {

			}


		

	