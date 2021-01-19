---------------------------
mvc的支持
---------------------------
	# 通过注解开启
		@EnableSpringDataWebSupport
	
	# 开启后, spring容器会自动配置几个组件
		DomainClassConverter
		HandlerMethodArgumentResolver
	

------------------------------
HandlerMethodArgumentResolver
------------------------------
	# 可以自动请求参数中的分页排序信息
	
	# 在请求接口中定义 Pageable 形参
		@GetMapping("/user")
		@ResponseBody
		public Object user (Pageable pageable){}

		* 请求参数
			page		页码, 默认0
			size		每页显示数量, 默认20
			sort		排序
				* sort=属性名称,排序策略
					http://localhost/user?sort=id,asc&sort=name,desc
				
				* 也可以仅定义多个属性, 默认排序策略为: asc
					http://localhost/user?sort=id,name
					http://localhost/user?sort=id&sort=name
				
	# 通过 @PageableDefault 注解修改默认值
		@PageableDefault
			|-int value() default 10;
			|-int size() default 10;
			|-int page() default 0;
			|-String[] sort() default {};
			|-Direction direction() default Direction.ASC;
	
	
		* 添加到分页参数上
			public Object user (@PageableDefault(page = 0, size = 10) Pageable pageable)
	
