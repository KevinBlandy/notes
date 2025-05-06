-----------------------
命名
-----------------------
	# 路由命名
	
		* 可以通过 name 属性给路由命名
		
			const routes = [
				{
					path: '/user/:username',
					// 路由名称
					name: 'profile', 
					component: User
				}
			]
			
		
		* 路由跳转的时候，通过 name 属性来指定路由名称
			
			// 创建一个指向 /user/yee 的链接
			<router-link :to="{ name: 'profile', params: { username: 'yee' } }">
				User profile
			</router-link>
			
			//  api 跳转的时候也通过 name 属性
			router.push({ name: 'user', params: { username: 'yee' } })
		
		* 好处
			* 没有硬编码的 URL。
			* params 的自动编码/解码。
			* 绕过路径排序，例如展示一个匹配相同路径但排序较低的路由。
		
		
		* 注意：所有路由的命名都必须是唯一的。如果为多条路由添加相同的命名，路由器只会保留最后那一条。

		* 命名支持使用 Symbol。


