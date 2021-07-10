---------------------
继承支持
---------------------
	# 继承支持
		* 通俗理解就是，服务提供者者和消费者都使用同一个 @FeignClient 接口
		* 提供者提供实现，消费者不需要实现
		
		* Spring 官方也不建议这么整，很耦合

	# Demo
		
		* 公共接口，只声明接口和URI，双方都依赖
			public interface UserService {
				@RequestMapping(method = RequestMethod.GET, value ="/users/{id}")
				User getUser(@PathVariable("id") long id);
			}
		
		* 服务提供者实现
			@RestController
			public class UserResource implements UserService {
				// TODO 实现
			}
		

		* 服务调用者，直接继承公共接口，声明服务
			package project.user;

			@FeignClient("users")
			public interface UserClient extends UserService {

			}
					
	
