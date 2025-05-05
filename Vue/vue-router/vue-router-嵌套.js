-----------------------
嵌套路由
-----------------------

	# 在路由视图中，还可以嵌套 <router-view>，这需要在路由中配置 children

		{
			name: 'User',
			path: '/user/:id',
			component: User,
			children: [{
				name: 'UserProFile',
				path: 'profile',
				component: Profile
			},{
				name: 'UserCenter',
				path: 'center',
				component: Center
			}]
		},

		
		* 注意，以 '/' 开头的嵌套路径将被视为根路径。
		* 也就是说，你在 children 下使用了 '/' 路由，这表示组件嵌套，而不是嵌套的 URL。

		* children 下的路由可以无限嵌套。
	
	# 默认路由渲染

		* 可以提供一个空路径（path: ''）的路由，作为默认路由渲染

			const routes = [
			  {
				path: '/user/:id',
				component: User,
				children: [
				  // 当 /user/:id 匹配成功
				  // UserHome 将被渲染到 User 的 <router-view> 内部
				  { path: '', component: UserHome },

				  // ...其他子路由
				],
			  },
			]
	
	
	# 嵌套的命名路由
		
		* 如果只想导航到父级路由，而不导航到嵌套路由，可以给父路由命名
		
		  {
				name: 'Profile',		// 命名父路由
				path: '/user/:id',
				component: () =>  import('../views/User.vue'),
				children: [
					{
						path: '',			// 子路由的路径是 ''，是父路由的默认路由
						name: 'UserPost',		// 命名子路由
						component: () => import('../views/UserPost.vue'),
					}
				],
			}
				
			
			* 子路由的 path 是 ''，也就是说它是父路由的默认路由
			* 当直接访问 /user/10010 时，会渲染子路由
				<RouterLink :to="{path: '/user/10010'}">去 User 组件（渲染组件）</RouterLink>
			
			* 如果通过命名路由跳转到父路由，则不会渲染子路由
				<RouterLink :to="{name: 'Profile', params: {id: '10010'}}">去 User 组件（不渲染子路由）</RouterLink>
		
		* 总之：通过命名路由导航时，可以精确控制要渲染到哪个层级
			* 使用子路由名称 → 渲染嵌套的路由（父路由一定渲染，爹永远存在）
			* 使用父路由名称 → 只显示父级内容
		
			* 重新加载页面（包括F5，和在新窗口中打开）将始终显示嵌套的子路由，因为它被视为指向路径 /user/:id 的导航，而不是命名路由
	
	
	# 忽略父组件 
		
		* 可以把具有公共路径前缀的路由分组在一起或使用更高级的功能（统一设置拦截、共享元信息）。
		
			const routes = [
				{
					// 统一的前缀
					path: '/admin',		
					// 子路由定义
					children: [
						// 默认路由
						{ path: '', component: AdminOverview },
						{ path: 'users', component: AdminUserList },
						{ path: 'users/:id', component: AdminUserDetails },
					], 
				},
			]
		
		
		* 很简单，不给父级路由设置组件就行，顶级 <router-view> 将跳过父级并仅使用子路由组件。
	
	