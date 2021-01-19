-------------------------
freemarker宏			 |
-------------------------
	# 依赖
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-taglibs</artifactId>
		</dependency>
		
		<dependency>
			<groupId>javax.servlet.jsp</groupId>
			<artifactId>javax.servlet.jsp-api</artifactId>
			<version>2.3.3</version>
			<scope>provided</scope>
		</dependency>

	
	# 复制 spring-security-taglibs.jar 中的文件: /META-INF/security.tld 到目录 /resources/tld/security.tld 下
	
	# 添加配置
		import java.util.Arrays;
		import javax.annotation.PostConstruct;

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

		import freemarker.ext.jsp.TaglibFactory;

		@Configuration
		public class FreemarkerConfiguration {

			@Autowired
			private FreeMarkerConfigurer configurer;

			@PostConstruct
			public void freeMarkerConfigurer() {
				TaglibFactory taglibFactory = configurer.getTaglibFactory();
				taglibFactory.setClasspathTlds(Arrays.asList("/tld/security.tld"));
				if (taglibFactory.getObjectWrapper() == null) {
					taglibFactory.setObjectWrapper(configurer.getConfiguration().getObjectWrapper());
				}
			}
		}


	# freemarker 页面引入使用
		<#assign security=JspTaglibs["/WEB-INF/classes/tld/security.tld"] />
	
		<@security.authorize access="hasRole('ADMIN')">
		</@security.authorize>

-------------------------
freemarker宏			 |
-------------------------
	<@security.authorize access="hasRole('ADMIN')">
	</@security.authorize>


	<@security.csrfInput/>