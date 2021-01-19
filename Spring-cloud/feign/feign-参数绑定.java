--------------------------------
feign参数绑定					|
--------------------------------
	# 定义Demo
		@FeignClient("USER-SERVICE")
		public interface UserFegin {
			
			@GetMapping("/user/{id}")
			UserEntity m1(@PathVariable("id")Integer userId);
				* rest参数
			
			@GetMapping("/user")
			UserEntity m2(@RequestParam("id")Integer userId);
				* form 表单参数
			
			@GetMapping("/user")
			UserEntity m3(@RequestHeader("h")String header);
				* 带请求头参数
			
			@PostMapping("/user")
			UserEntity m4(UserEntity userEntity);
				* 请求数据封装为对象
		}
		
	
	 # feign接口必须要通过:@RequestHeader,@RequestParam,@RequestBody 来绑定请求参数
		* 而且必须通过 value 属性来设置参数的名称
		