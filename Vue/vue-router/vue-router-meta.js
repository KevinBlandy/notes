----------------------
元信息
----------------------
	# 可以在路由上定义一些元信息

		path: ':id',
		component: PostsDetail
		// 自定义元对象
		meta: { requiresAuth: false }
	


	# 访问元信息

		* 首先，一个导航可能会匹配到多个路由（嵌套）
		* 于是，可以通过路由对象的 matched 数组，来访问匹配到的所有路由。
		* 然后，再迭代每个路由访问其 meta 信息。

			router.beforeEach((to, from) => {
				for (const route of to.matched){
					// 访问每个路由的 meta
					console.log(route.meta);
				}
			});


		* 访问路由对象的 meta 属性
		* 它是一个非递归合并所有 meta 字段（从父字段到子字段）的方法。

			router.beforeEach((to, from) => {
				// 所有匹配路由的 meta 的合并对象
				console.log(to.meta);
			});



