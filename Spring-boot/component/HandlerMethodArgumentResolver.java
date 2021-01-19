------------------------------
HandlerMethodArgumentResolver |	
------------------------------
	# 实现该接口可以自定义参数解析
		public interface HandlerMethodArgumentResolver {
			boolean supportsParameter(MethodParameter parameter);

			@Nullable
			Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception;
		}
	
	# 在mvc的配置中, 设置
	@Configuration
	public class WebMvcConfiguration implements WebMvcConfigurer {
		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
			// 添加自己的实现类
		}
	}
	
	