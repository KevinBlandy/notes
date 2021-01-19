----------------------------
config						|
----------------------------
	# ÎÄµµ
		https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html
	
	# Maven
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-client</artifactId>
        </dependency>
	
	# ¼Ü¹¹
						 <--------->	Service1
						|
		GIT <----> ConfigServer			
						|
						 <--------->	Service2
	

