-------------------
导航
-------------------

	# 首先是获取到路由器对象
		
		* 在模板中
		
			$router
		
		* 在 API 中

			import { useRouter } from 'vue-router';
			const router = useRouter();
		
			// 甚至是直接导入创建的 router 对象都行
			import router from '@/routes/index.js';
	
	
	# push 方法
		
		* 这个方法会向 history 栈添加一个新的记录，所以，当用户点击浏览器后退按钮时，会回到之前的 URL。
		* 使用 <router-link :to="{...}"> 时，内部会调用这个方法.
			
			* 所以 to 属性的参数和 push 的参数一样。
		
		* 该方法的参数可以是一个字符串路径，或者一个描述地址的对象。例如
		
			// 字符串路径
			router.push('/users/eduardo')

			// 带有路径的对象
			router.push({ path: '/users/eduardo' })

			// 命名的路由，并加上参数，让路由建立 url
			router.push({ name: 'user', params: { username: 'eduardo' } })

			// 带查询参数，结果是 /register?plan=private
			router.push({ path: '/register', query: { plan: 'private' } })

			// 带 hash，结果是 /about#team
			router.push({ path: '/about', hash: '#team' })
		
		* name 属性和 path 属性相互冲突，只能提供一个
			
			* 使用 path 属性的话，要自己处理 url 编码问题。
			* 使用 path 属性的话，params 会被忽略。
		
	
		* params 中的参数，都将被自动字符串化，且进行正确的编码。
		* 要删除 params 中的参数，可以把它的值设置为 null 或空字符串 ""。
		
		
	
	# replace 方法
		
		* 用类似于 router.push，唯一不同的是，它在导航时不会向 history 添加新记录。
		* 在 router-link 中使用，是通过添加 replace 属性来实现的
			<router-link :to="{...}" replace>
		
		* 也可以直接在传递给 router.push 的参数对象中增加一个属性 replace: true 
		
			router.push({ path: '/home', replace: true })
			// 相当于
			router.replace({ path: '/home' })
	
	
	# 导航结果
	
		* 所有其他导航方法都会返回一个 Promise，可以知道导航是成功还是失败。
			
		
		* 如果导航成功，Promise 的 resolve 值是 falsy（undefined）
		* 如果导航失败，Promise 的 resolve 值是 Navigation Failure
		
			const navigationResult = await router.push('/my-profile')

			if (navigationResult) {
			  // 导航被阻止，结果不是 undefined
			} else {
			  // 导航成功 （包括重新导航的情况）
			}
		
		
		* 导航失败的 Navigation Failure 会有一些额外的属性，可以获取失败的原因
		* 会暴露 to 和 from 属性，以反映失败导航的当前位置和目标位置。
	
		
		* 可以使用 isNavigationFailure 函数配合 NavigationFailureType 枚举进行原因判断：
	
			import { NavigationFailureType, isNavigationFailure } from 'vue-router'

			// 试图离开未保存的编辑文本界面
			const failure = await router.push('/articles/2')
			
			// 判断类型是否是 NavigationFailureType.aborted
			if (isNavigationFailure(failure, NavigationFailureType.aborted)) {
				// 给用户显示一个小通知
				showToast('You have unsaved changes, discard and leave anyway?')
			}
			
			* 如果忽略第二个参数 isNavigationFailure(failure)，那么就只会检查这个 failure 是不是一个 Navigation Failure。
		
		* NavigationFailureType 总共有三种不同的类型
			
			* aborted：在导航守卫中返回 false 中断了本次导航。
			* cancelled：在当前导航完成之前又有了一个新的导航。比如，在等待导航守卫的过程中又调用了 router.push。
			* duplicated：导航被阻止，因为已经在目标位置了。

		
		* 可以用 router.afterEach() 导航守卫检测全局导航故障
			
			router.afterEach((to, from, failure) => {
				if (failure) {
					// 导航失败，发送统计信息
					sendToAnalytics(to, from, failure)
				}
			})
		
		
