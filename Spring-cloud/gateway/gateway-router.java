
------------------------
路由
------------------------
	# 一个路由配置本质上就是 RouteDefinitionLocator 接口的实现
		public interface RouteDefinitionLocator {
			Flux<RouteDefinition> getRouteDefinitions();
		}
	
	# 为路由添加元数据
		spring:
		  cloud:
			gateway:
			  routes:
			    - id: route_with_metadata
				  uri: https://example.org
				  metadata: # 元数据信息
				    optionName: "OptionValue"
				    compositeObject:
					  name: "value"
				    iAmNumber: 1