------------------------
匹配当前链接
------------------------

	# RouterLink 匹配当前链接
	
		* RouterLink 组件会为匹配当前路由的链接添加两个 CSS 类
		
			router-link-active
				* 匹配当前路由
			
			router-link-exact-active
				* 精准匹配
		
		* 有路由如下
		
			// 父路径
			path: '/user/:username',
			component: User,
			children: [
			{
				// 子路径
				path: 'role/:roleId',
				component: Role,
			}
			
			
			
			// 会添加 router-link-active class 
			<RouterLink to="/user/erina">
				User
			</RouterLink>
			
			// 会添加 router-link-active router-link-exact-active class
			<RouterLink to="/user/erina/role/admin">
				Role
			</RouterLink>
		
	
	# 链接在什么时候匹配当前路由？
		
		1. 它与当前路径匹配相同的路由记录（即配置的路由）。
		2. 它的 params 与当前路径的 params 相同。
		
		* 如果使用了嵌套路由，任何指向祖先路由的链接也会被认为是匹配当前路由的，只要相关的 params 匹配。
		* 路径不一定需要完全匹配。例如，使用 alias 仍然会被认为是匹配的，只要它解析到相同的路由记录和 params。
		* 如果一个路由有 redirect，在检查链接是否匹配当前路由时不会跟随重定向。
	
	
	# 自定义类名称
	
		* 局部修改，可以通过 RouterLink 组件的属性设置
		
			activeClass="border-indigo-500"
			exactActiveClass="border-indigo-700"
		
		* 全局修改，可以在创建路由器的时候设置
		
			const router = createRouter({
				linkActiveClass: 'border-indigo-500',
				linkExactActiveClass: 'border-indigo-700',
			})
		
