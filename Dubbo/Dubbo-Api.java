-----------------------
Dubbo-配置API			|
-----------------------
	com.alibaba.dubbo.config.ServiceConfig
	com.alibaba.dubbo.config.ReferenceConfig
	com.alibaba.dubbo.config.ProtocolConfig
	com.alibaba.dubbo.config.RegistryConfig
	com.alibaba.dubbo.config.MonitorConfig
	com.alibaba.dubbo.config.ApplicationConfig
	com.alibaba.dubbo.config.ModuleConfig
	com.alibaba.dubbo.config.ProviderConfig
	com.alibaba.dubbo.config.ConsumerConfig
	com.alibaba.dubbo.config.MethodConfig
	com.alibaba.dubbo.config.ArgumentConfig

-----------------------
Dubbo-注解API			|
-----------------------
	com.alibaba.dubbo.config.annotation.Service
		* 服务提供方注解
		* xml配置
			<!-- 扫描注解包路径，多个包用逗号分隔，不填pacakge表示扫描当前ApplicationContext中所有的类 -->
			<dubbo:annotation package="com.foo.bar.service" />
		* 代码
			import com.alibaba.dubbo.config.annotation.Service;
			 
			@Service(version="1.0.0")
			public class FooServiceImpl implements FooService {
			 
			}
	com.alibaba.dubbo.config.annotation.Reference
		* 消费方注解
		* xml配置
			<!-- 扫描注解包路径，多个包用逗号分隔，不填pacakge表示扫描当前ApplicationContext中所有的类 -->
			<dubbo:annotation package="com.foo.bar.action" />
		* 代码
			import com.alibaba.dubbo.config.annotation.Reference;
			import org.springframework.stereotype.Component;
			@Component
			public class BarAction {
				@Reference(version="1.0.0")
				private FooService fooService;
			 
			}
	
	# 由spring加载服务/引用服务
	
		<dubbo:annotation />
		<context:component-scan base-package="com.foo.bar.service">
			<context:include-filter type="annotation" expression="com.alibaba.dubbo.config.annotation.Service" />
		</context:component-scan>
		
		* 等价于:<dubbo:annotation package="com.foo.bar.service" />
	



-----------------------
Dubbo-模型API			|
-----------------------
	com.alibaba.dubbo.common.URL
	com.alibaba.dubbo.rpc.RpcException

-----------------------
Dubbo-上下文API			|
-----------------------
	com.alibaba.dubbo.rpc.RpcContext

-----------------------
Dubbo-服务API			|
-----------------------
	com.alibaba.dubbo.rpc.service.GenericService
	com.alibaba.dubbo.rpc.service.GenericException
		参见：泛化引用 & 泛化实现
	com.alibaba.dubbo.rpc.service.EchoService
		参见：回声测试
