---------------------------
重定向和别名
---------------------------

	# 在路由中通过 redirect 属性定义重定向
	
		// 访问 home，重定向到 /
		{ 
			path: '/home', 
			redirect: '/'   // 目标路由
		}
		
		* 重定向到命名路由也可以
		
			redirect: { name: 'HomePage' } 
		
		* 注意，导航守卫并没有应用在跳转路由上，而仅仅应用在其目标上。
		* 即，在 /home 路由中添加 beforeEnter 守卫不会有任何效果。
		
		* 甚至可以通过一个方法返回要重定向的目标
	
			{
				// /search/screens -> /search?q=screens
				path: '/search/:searchText',
				redirect: to => {
					// 方法接收访问的目标路由作为参数
					// return 重定向的字符串路径/路径对象
					return { path: '/search', query: { q: to.params.searchText } }
				},
			},
			{
				path: '/search',
					
				// ...
			},
		
		* 定义了 redirect，可以省略 component 配置（除了嵌套路由）
		
			// 访问 parent 组件，默认重定向给到其嵌套的子组件的时候
			// parent 组件应该要有 component 配置
			'/parent -> redict -> /parent/index'
			
			'/parent/index'
	
	
	# 别名
		
		* 通过 alias 属性配置别名，通过别名访问 URL 不会改变
			
			// 用户访问 /home 时，URL 仍然是 /home，但会被匹配为用户正在访问 /。
			
			{ 
				path: '/', 
				component: Homepage, 
				// 别名配置
				alias: '/home' 
			}
			
		* 别名可以有多个，使用数组。
		
		* 通过别名可以自由地将 UI 结构映射到一个任意的 URL，不受嵌套影响
			{
				path: '/users',
				component: UsersLayout,
				children: [
					{ 
						path: '', 
						component: 
						UserList, 
						// 给一个嵌套的子组件，设置了别名
						// 注意，第一个别名是以 / 开头的根路径
						alias: ['/people', 'list'] 
					},
				],
			},
							
			
			* 访问如下 URL 都会渲染 UserList
				/users			// 父路由本身的 path
				/users/list		// 父路由本身的 path + 子路由别名
				/people			// 直接访问子路由别名
		
		* 如果路由有参数，在别名中也别忘记定义
			
			path: '/users/:id',
			alias: [
				'/:id', 
				''			// 没有别名的情况
			] 
			
			* 访问如下 URL 都会命中路由
				
				/users/24			// 父级路由 /users/:id + '' 别名
				/users/24/profile	// 父级路由 + /:id 别名
				/24					// /:id 别名

	# 检测重定向
		
		* 当在导航守卫中返回一个新的位置时，会触发一个新的导航，覆盖正在进行的导航。
		* 重定向不会阻止导航，而是创建一个新的导航。因此，通过读取路由地址中的 redirectedFrom 属性，对其进行不同的检查：


			await router.push('/my-profile')
			
			// 等待导航完成后
			if (router.currentRoute.value.redirectedFrom) {
				//  redirectedFrom 包含在重定向到当前地址之前，即最初想访问的地址。
			}
			