----------------------------
Gateway
----------------------------
	# 地址
		https://github.com/spring-cloud/spring-cloud-gateway
		https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/

	
	# Maven
		<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-gateway -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway</artifactId>
			<version>3.0.3</version>
		</dependency>
		
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-loadbalancer</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.ben-manes.caffeine</groupId>
            <artifactId>caffeine</artifactId>
        </dependency>
		
		* 在使用Nacos的时候，如果不添加 openfeign和loadbalancer 可能会导致服务 503 不可用

----------------------------
核心概念
----------------------------
	# Router
		* 它由: ID, 目标URI, 谓词集合, 过滤器集合 组成
		* 如果聚合谓词为真，则匹配路由
	
	# 谓词
		* Predicate 用来判断是否执行当前路由
	
	# 过滤器
		* 在执行路由的时候，会执行的过滤操作


----------------------------
Gateway
----------------------------
	# 把Router中的URI设置为服务，并且开启负载均衡
		lb://<service-name>
	





----------------------------
常用的配置
----------------------------
spring:
  cloud:
    gateway:
      routes:
        - id: user_router
          uri: lb://user-service
          predicates:
            - Path=/api/user/**
          filters:
            - RewritePath=/api/user/?(?<segment>.*), /$\{segment}
            - name: RequestSize
              args:
                maxSize: 10KB
