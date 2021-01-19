----------------------------
oshi
----------------------------
	# Github
		https://github.com/oshi/oshi
	
	# maven
		<!-- https://mvnrepository.com/artifact/com.github.oshi/oshi-core -->
		<dependency>
			<groupId>com.github.oshi</groupId>
			<artifactId>oshi-core</artifactId>
			<version>4.4.2</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/net.java.dev.jna/jna -->
		<dependency>
			<groupId>net.java.dev.jna</groupId>
			<artifactId>jna</artifactId>
			<version>5.5.0</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform -->
		<dependency>
			<groupId>net.java.dev.jna</groupId>
			<artifactId>jna-platform</artifactId>
			<version>5.5.0</version>
		</dependency>
	
	# 初始化
		// 判断是否支持当前的操作系统
		if (PlatformEnum.UNKNOWN.equals(SystemInfo.getCurrentPlatformEnum())) {
    		System.out.println("未知的操作系统");
    		return ;
    	}

    	// 初始化系统信息
        SystemInfo systemInfo = new SystemInfo();


----------------------------
oshi - 获取CPU信息
----------------------------

----------------------------
oshi - 获取内存信息
----------------------------


----------------------------
oshi - 获取硬盘信息
----------------------------


----------------------------
oshi - 获取操作系统信息
----------------------------

----------------------------
oshi - 获取网络信息
----------------------------

----------------------------
oshi - 获取JVM信息
----------------------------