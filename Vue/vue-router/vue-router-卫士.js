----------------------
卫士
----------------------
	# 一种拦截器机制
		
		* 前置拦截器 -> 解析拦截器 -> 后置拦截器
	
	# 可以是全局的，单个路由独享的，或者组件级的
		
		* 全局的：定义在路由器上
		* 单个路由的：定义在单个路由上
		* 组件级的：定义在组件内
	
	
	# 可以在导航守卫内使用 inject() 方法
		
		* 在 app.provide() 中提供的所有内容都可以在 router.beforeEach()、router.beforeResolve()、router.afterEach() 内获取到。

	
	# 完整的导航解析流程

		* 导航被触发。
		* 在失活的组件里调用 beforeRouteLeave 守卫。
		* 调用全局的 beforeEach 守卫。
		* 在重用的组件里调用 beforeRouteUpdate 守卫(2.2+)。
		* 在路由配置里调用 beforeEnter。
		* 解析异步路由组件。
		* 在被激活的组件里调用 beforeRouteEnter。
		* 调用全局的 beforeResolve 守卫(2.5+)。
		* 导航被确认。
		* 调用全局的 afterEach 钩子。
		* 触发 DOM 更新。
		* 调用 beforeRouteEnter 守卫中传给 next 的回调函数，创建好的组件实例会作为回调函数的参数传入。

----------------------
前置拦截器
----------------------
	# 全局式

		router.beforeEach((to, from) => {
			console.log(`从 ${from.fullPath} 来，到 ${to.fullPath} 去`);
			return true;
		});

		* 可以注册多个，按照创建顺序调用。
		* 拦截器是异步解析执行，此时导航在所有拦截器 resolve 完之前一直处于等待中。
		* 拦截器可以是异步的
			
			router.beforeEach(async (to, from) => {
				// 发起异步请求，阻塞直到结果
				const respnse = await fetch('http://localhost/test');
				const content = await respnse.json();
				console.log(content);

				// TODO 根据响应进行判断

				return true
			});
		
		* 支持第三个参数 next，不推荐用，将来可能会被删除

		* 可以的返回值
			
			* undefined：没任何返回，不拦截
			* bool：返回 false 则取消导航（URL 地址会重置到 from 路由对应的地址）
			* 路由对象：用于重定向到返回的路由，对象即 router.push 的参数，例如： {name: 'Login'}
		
	
	# 路由独享

		* 通过 beforeEnter 定义

			path: '/users/:id',
			component: UserDetails,
			beforeEnter: (to, from) => {
				// 拒绝导航
				return false
			}
		
		* 注意，这只会在只在从一个不同的路由进入当前路由时触发。
		* params、query 、hash 变化时不会触发（例如：从 '/users/2' 导航到 '/users/3'）。
			
		
		* beforeEnter 的值也可以是一个数组，包含多个函数。

		* 在嵌套路由下，在具有相同父级的子路由之间移动时，父级路由的 beforeEnter 不会被触发

			path: '/user',
			beforeEnter() {
				// 当路由在/user/list 和 /user/details 之间移动时
				// 这个 beforeEnter 不会触发
			},
			children: [
				{ path: 'list', component: UserList },
				{ path: 'details', component: UserDetails },
			],


----------------------
解析拦截器
----------------------

	# 每次导航都会触发

		* 解析拦截器刚好会在导航被确认之前、所有组件内守卫和异步路由组件被解析之后调用

			router.beforeResolve(to => {
				console.log(`Go ${to.fullPath}`);
			});
		
		* 返回值类和前置拦截器一样
		
		* 是获取数据或执行任何其他操作（在确认用户可以进入页面时要执行的操作）的理想位置。

----------------------
后置拦截器
----------------------
	# 总会执行
		
		* 它不会接受 next 函数也不会改变导航本身。
		* 第三个参数可以判断是否导航成功。

			router.afterEach((to, from, failure) => {
				if (!failure) {
					// 没有异常
				}
			})
		

----------------------
组件内
----------------------

	# 组件内的守卫定义

		* 更新之后：onBeforeRouteUpdate
		* 离开之前：onBeforeRouteLeave

		import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router'
		import { ref } from 'vue'

		// 与 beforeRouteLeave 相同，无法访问 `this`
		onBeforeRouteLeave((to, from) => {
			const answer = window.confirm('Do you really want to leave? you have unsaved changes!')
			// 取消导航并停留在同一页面上
			if (!answer) return false
		});

		const userData = ref()

		// 与 beforeRouteUpdate 相同，无法访问 `this`
		onBeforeRouteUpdate(async (to, from) => {
			//仅当 id 更改时才获取用户，例如仅 query 或 hash 值已更改
			if (to.params.id !== from.params.id) {
				userData.value = await fetchUser(to.params.id)
			}
		});
	
