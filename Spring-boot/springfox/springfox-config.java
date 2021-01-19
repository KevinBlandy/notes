--------------------------
springfox 配置
--------------------------
	ApiInfo
	Docket

--------------------------
springfox mvc 的配置
--------------------------
	@Configuration
	public class WebMvcConfigration implements WebMvcConfigurer {
		
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/swagger-ui/**")
					.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
					.resourceChain(false);
		}

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/swagger-ui/")
					.setViewName("forward:/swagger-ui/index.html");
		}
	}




----------------------------------
swagger-Configuration
----------------------------------
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket docket() {

		ApiInfo apiInfo = new ApiInfoBuilder().version("0.0.0.1") // 版本号
				.contact(new Contact("KevinBlandy", "https://springboot.io", "kevinblandy.cn@gmail.com")) // 联系人
				.description("用开测试的") // 描述
				.extensions(null) // 添加多个扩展
				.license("许可证") // api许可证
				.licenseUrl("https://springboot.io?license") // api许可证地址
				.termsOfServiceUrl("https://springboot.io/service") // 服务条款地址
				.title("swagger 接口文档") // 标题
				.build();

		// 根据文档类型创建 Docket
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Swagger 接口文档")
				// api info
				.apiInfo(apiInfo)
				// 启用
				.enable(true)
				// 扫描包和过滤
				.select().apis(RequestHandlerSelectors.basePackage("io.springboot.swagger.controller"))
				.paths(PathSelectors.any()).build();

	}
}