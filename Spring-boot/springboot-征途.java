

-----------------------
扫描第三方依赖包		|
-----------------------
	* 需要在 @SpringBootApplication 类上添加注解,来指定你要扫描哪些包
	* @ComponentScan(basePackages = {"com.test","xin.suen"})
	* 注意,当添加该注解的时候.那么系统只会扫描指定路径下的包

