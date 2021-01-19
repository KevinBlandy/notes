-----------------------
整合spring				|
-----------------------
	# 需要依赖
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-context-support -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
			<version>${spring.version}</version>
		</dependency>

	
	# xml配置
	   <!-- 配置FreeMark视图 -->
		<bean id="freeMarkerViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
			<property name="contentType" value="text/html;charset=UTF-8"/>      
			<property name="viewClass" value="org.springframework.web.servlet.view.freemarker.FreeMarkerView"/>
			<property name="suffix" value=".ftl"/>

			<!-- 缓存freemarker -->
			<property name="cache" value="true"/>

			<!-- 是否暴露各种属性 -->
			<property name="exposeSessionAttributes" value="true"/>
			<property name="exposeRequestAttributes" value="true"/>     
			<property name="exposeSpringMacroHelpers" value="true"/>

			<!-- request引用 -->
			<property name="requestContextAttribute" value="request"/>
			<property name="order" value="0"/>
		</bean>
		 
		
		<!-- freemarker配置文件 -->
		<bean id="freemarkConfig" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
			<!-- 一些配置，例如:datetime_format=yyyy-MM-dd HH:mm:ss -->
			<property name="location" value="classpath:freemark.properties"/>
		</bean>
		 
		<bean id="fmXmlEscape" class="freemarker.template.utility.XmlEscape"/>
		 
		<bean id="FreeMarkerConfigurer" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
			<property name="templateLoaderPaths">
				<array>
					<value>/WEB-INF/templates/include</value>
					<value>/WEB-INF/templates/macro</value>
					<value>/WEB-INF/templates/views</value>
				</array>
			</property>
			<property name="defaultEncoding" value="UTF-8"/>
			<property name="freemarkerSettings" ref="freemarkConfig"/>
			<property name="freemarkerVariables">
				<map>
					<!-- 配置自定义函数等 -->
					<entry key="xml_escape" value-ref="fmXmlEscape"/>
				</map>
			</property>
		</bean>

