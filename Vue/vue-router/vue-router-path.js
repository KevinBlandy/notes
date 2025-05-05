----------------------
路由
----------------------
	# PATH 路径参数
		* 在路由中可以使用 <:param>定义多个 path 参数
			{
				name: 'User',
				path: '/user/:group/id/:userId',
				component: User,
			}
		
		* 在路由页面中，可以通过当前路由对象的 params 获取到这个参数
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
		
		* 使用 使用 beforeRouteUpdate 导航守卫也可以
	
			<template>
			   <p>用户 ID：{{ $route.params.id }}</p> 
			   <button @click="updateParam">点击更换 ID</button>
			</template>

			<script setup>

			import { onBeforeRouteUpdate, useRouter, useRoute } from 'vue-router';

			// 当前路由
			const route = useRoute();

			// 路由器
			const router = useRouter();

			// 监听路由变更事件
			onBeforeRouteUpdate(async (to, from) => {
				// 路由发生了变化
				console.log(to);        // {fullPath: '/user/88888', hash: '', query: {…}, name: 'Profile', path: '/user/88888', …}
				console.log(from);      // {fullPath: '/user/1001', path: '/user/1001', query: {…}, hash: '', name: 'Profile',…}
			});

			const updateParam = () => {
				router.replace(
					{
						// 更新 params 参数中的 id 值为 
						params: {...route.params, id: '88888'}
					}
				);
			}

			</script>
	
	# 使用正则来匹配路由
		* 可以在括号中为参数指定一个自定义的正则
		
			// 通过这种方式，可以在一个 url 下，定义两个路由，根据 path 参数，跳转不同的路由
			const routes = [
				// /:orderId -> 仅匹配数字
				{ path: '/:orderId(\\d+)' },

				// /:productName -> 匹配其他任何内容
				{ path: '/:productName' },
			]

				
		* 也可以指定路由的前缀

			// 将匹配以 `/user-` 开头的所有内容，并将其放在 `route.params.afterUser` 下
			{ path: '/user-:afterUser(.*)', component: UserGeneric },
		
		* 使用正则定义 404 路由，匹配所有
		
			// 将匹配所有内容并将其放在 `route.params.pathMatch` 数组参数中
			{ path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound },

			// 请求路由 /p1/p2/p3/not/found
			// 在 NotFound 组件中获取路径参数

			import { useRoute } from 'vue-router'
			const params = useRoute().params;
			
			
			// 注意， params 参数的值是一个数组
			console.log(params);  // {"pathMatch":["p1","p2","p3","not","found"]}
		
		* 表达式
			+	1 个或多个
			?	0 个或 1 个
			*	0 个或多个
	
	
	# 严格模式和大小写敏感
		* 严格是指的是，能匹配带有或不带有尾部斜线的路由。
		* 通过 strict （严格匹配）和 sensitive （大小写敏感）选项来修改。
		
		* 全局路由配置
			const router = createRouter({
				routes: [...]
			  strict: true,
			});


		* 当前路由配置
			{ path: '/users/:id', sensitive: true },
