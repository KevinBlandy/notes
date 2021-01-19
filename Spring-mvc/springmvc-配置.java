<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans.xsd
			http://www.springframework.org/schema/mvc
			http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
			http://www.springframework.org/schema/context
			http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder file-encoding="UTF-8" location="classpath*:application-mvc.properties"/>

    <context:component-scan base-package="com.kevin.blog.controller" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg index="0" value="UTF-8"/>
            </bean>
            <bean class="com.alibaba.fastjson.support.spring.FastJsonpHttpMessageConverter4">
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
                <property name="fastJsonConfig">
                    <bean class="com.alibaba.fastjson.support.config.FastJsonConfig">
                        <property name="charset" value="UTF-8"/>
                        <property name="serializerFeatures">
                            <array>
                                <!-- 输出null字段 -->
                                <value>WriteMapNullValue</value>
                                <!-- 输出key使用双引号 -->
                                <value>QuoteFieldNames</value>
                                <!-- 空集合输出[] -->
                                <value>WriteNullListAsEmpty</value>
                                <!-- 输出格式化 -->
                                <value>PrettyFormat</value>
                            </array>
                        </property>
                        <!-- 日期格式 -->
                        <property name="dateFormat" value="yyyy-MM-dd HH:mm:ss"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!-- jsonp -->
    <bean class="com.alibaba.fastjson.support.spring.FastJsonpResponseBodyAdvice">
        <constructor-arg>
            <list>
                <value>callback</value>
                <value>jsonp</value>
            </list>
        </constructor-arg>
    </bean>

    <!-- file upload-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <!-- 500MB -->
        <property name="maxUploadSize" value="524288000"/>
        <property name="defaultEncoding" value="UTF-8"/>
        <!-- 100MB-->
        <property name="maxInMemorySize" value="104857600"/>
        <property name="uploadTempDir" value="/WEB-INF/temp"/>
    </bean>


    <mvc:resources mapping="/static/**" location="/static/"/>
    <mvc:resources mapping="/resource/**" location="/resource/"/>

    <!--View-->
    <bean id="beetlConfig" class="org.beetl.ext.spring.BeetlGroupUtilConfiguration" init-method="init"/>

    <bean id="viewResolver" class="org.beetl.ext.spring.BeetlSpringViewResolver">
        <property name="contentType" value="text/html;charset=UTF-8"/>
        <property name="suffix" value=".html"/>
    </bean>
</beans>