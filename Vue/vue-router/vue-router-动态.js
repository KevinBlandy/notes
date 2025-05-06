------------------------
动态路由
------------------------
	# 通过 router.addRoute() 添加路由

		router.addRoute({ path: '/about', component: About })

		* 这只是新增路由，不会导航过去，新增后，仍然需要手动导航

		* 注意，如果在导航守卫内部添加或删除路由后，不应该调用 router.replace() 进行路由，而是通过返回新的位置来触发重定向

		* 添加一个名称/路径冲突的路由，会先删除路由，再添加路由。

		* 添加路由后，会返回一个删除函数，调用这个方法就会删除新增路由
			
			// 当路由没有名称时，这很有用。
			const removeRoute = router.addRoute(routeRecord)
			removeRoute() // 删除路由如果存在的话
		

		* 添加子路由，可以将父路由的 name 作为第一个参数传递给 addRoute
			// 添加子路由到 admin 下
			router.addRoute('admin', { path: 'settings', component: AdminSettings })

	# 通过 router.removeRoute() 移除路由

		* 通过路由名称删除路由

			// 删除路由
			router.removeRoute('about')

		* 路由被删除时，所有的别名和子路由也会被同时删除
		


	# 查看路由
		* Vue Router 提供了两个方法来查看现有的路由：

		router.hasRoute(name): boolean
			* 检查路由是否存在。

		router.getRoutes(): RouteRecordNormalized[]
			* 获取一个包含所有路由记录的数组。