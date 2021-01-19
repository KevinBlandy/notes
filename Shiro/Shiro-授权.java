------------------------
授权					|
------------------------
	# 角色的校验方式
		1,代码
			Subject subject = SecurityUtils.getSubject();
			if(subject.hasRole(“admin”)) {
				//有权限
			} else {
				//无权限
			}
		
		2,注解式子
			@RequiresRoles("admin")
			public void hello() {
				//有权限
			}
		
		3,JSP标签
			<shiro:hasRole name="admin">
				<!— 有权限 —>
			</shiro:hasRole>
		
	
	# 授权
		* 在ini中配置用户的信息和授权角色
			[user]
			kevin=123456,admin,manager
		
		* 判断用户是否有指定的角色
			boolean subject().hasRole("admin")
				* 是否有指定的角色
			boolean subject().hasAllRoles(Arrays.asList("admin", "manager"))
				* 是否有指定的所有角色
			boolean[] subject().hasRoles(Arrays.asList("role1", "role2", "role3"))
				* 对所有角色进行校验,返回的是一个校验结果集
			
			subject().checkRole("admin");
				* 断言有指定的角色,如果没有抛出异常
			subject().checkRoles("admin", "manager");
				* 断言有指定的所有角色,如果任意一个没有,抛出异常
			
	
	# 基于资源的访问控制(显示角色)
		* ini配置
			[users]
			Kevin=123,role1,role2
			Litch=123,role1

			[roles]
			role1=user:create,user:update
			role2=user:create,user:delete

			* 用户:密码,角色1,角色2
			* 角色名称:权限1,权限2			(资源标识符:操作)

		* 断言
			subject().isPermitted("user:create")
				* 判断是否具备指定的权限
			subject().isPermittedAll("user:update", "user:delete")
				* 判断是否具备指定的所有权限
			subject().isPermitted("user:view")
				* 判断是否不具备指定的权限
			
			subject().checkPermission("user:create")
				* 断言有权限
			subject().checkPermissions("user:delete", "user:update")
				* 断言有指定的所有权限
			

		* 字符串通配符权限
			* "资源标识符:操作:对象实例ID" 即对哪个资源的哪个实例可以进行什么操作
			* 单个资源,单个权限
				subject().checkPermissions("system:user:update");	//用户拥有资源"system:user"的"update"权限
			
			* 单个资源多个权限
				role=system:user:update,system:user:delete
				subject().checkPermissions("system:user:update", "system:user:delete");	//用户拥有资源“system:user”的“update”和“delete”权限
			
				role2="system:user:update,delete"
				subject().checkPermissions("system:user:update,delete");

				* 通过"system:user:update,delete" 验证 "system:user:update, system:user:delete"是没问题的
				* 但是反过来是规则不成立