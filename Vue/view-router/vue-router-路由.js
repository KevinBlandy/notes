----------------------
路由
----------------------
	# 路由对象

	# 组件中的当前路由对象
		
----------------------
捕获所有或 404 路由
----------------------
	# 可以使用自定义的 '路径参数' 正则表达式，在 '路径参数' 后面的括号中加入 '正则表达式'

		// 将匹配所有内容并将其放在 `route.params.pathMatch` 数组参数中
		{ path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound },

		* 在 NotFound 组件中获取路径参数

			// 请求路由 /p1/p2/p3/not/found

			import { useRoute } from 'vue-router'
			const params = useRoute().params;
			console.log(params);  // {"pathMatch":["p1","p2","p3","not","found"]}
		
	#  也可以指定路由的前缀

		// 将匹配以 `/user-` 开头的所有内容，并将其放在 `route.params.afterUser` 下
		{ path: '/user-:afterUser(.*)', component: UserGeneric },
	

----------------------
PATH 路径参数
----------------------
	# PATH 路径参数
		* 在路由中可以使用 <:param>定义多个 path 参数
			{
				name: 'User',
				path: '/user/:group/id/:userId',
				component: User,
			}
		
		* 在路由页面中，可以通过路由获取到这个参数
			// 路由地址：/user/admin/id/1000
			
			<template>
				<div>{{ $route.params.group }}</div>
				<div>{{ $route.params.userId }}</div>
			</template>

			<script setup>
				import {useRoute} from 'vue-router'
				const route = useRoute();
				console.log(route.params);
				// {group: 'admin', userId: '1000'}
			</script>
	
	# 路由参数的变化
		* 当把路由从 '/user/admin/id/1000' 切换到 '/user/admin/id/1001' 时，相同的组件实例将被重复使用（复用）。
		* 意味着组件的生命周期钩子不会被调用。

		* 要监听路由参数的变化，可以使用 watch 监听

			import { watch } from 'vue'
			import { useRoute } from 'vue-router'

			// 当前路由
			const route = useRoute();

			// 监听路由中 userId 路径参数的变化
			watch(() => route.params.userId, (newUserId, oldUserId) => {
				console.log(`userId 参数变化：${newUserId} - ${oldUserId}`);
			});
	
	# 使用正则来匹配路由
		* 可以在括号中为参数指定一个自定义的正则

			const routes = [
				// /:orderId -> 仅匹配数字
				{ path: '/:orderId(\\d+)' },

				// /:productName -> 匹配其他任何内容
				{ path: '/:productName' },
			]

		* 通过这种方式，可以在一个 url 下，定义两个路由，根据 path 参数，跳转不同的路由
	
----------------------
大小写敏感与严格模式
----------------------
	# 默认情况下，所有路由不区分大小写，且尾部斜线可有可无。
		* '/users' 可以匹配 '/users'、'/users/'、甚至 '/Users/'。

	# 这种行为可以通过 strict （严格匹配）和 sensitive （大小写敏感）选项来修改。
	# 全局路由配置
		const router = createRouter({
			routes: [...]
		  strict: true,
		});


	# 当前路由配置
		{ path: '/users/:id', sensitive: true },

----------------------
嵌套路由
----------------------
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
		

		
