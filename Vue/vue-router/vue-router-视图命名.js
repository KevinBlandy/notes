-----------------------------
视图命名
-----------------------------
	
	# 如果想同时 (同级) 展示多个视图，而不是嵌套展示，则可以给视图命名
		
		* 通过 router-view 的 name 属性设置视图名称
	
			<router-view class="view left-sidebar" name="LeftSidebar" />
			<router-view class="view main-content" />
			<router-view class="view right-sidebar" name="RightSidebar" />
		
		* 没有设置的话，默认的视图名称为 default
	
	# 定义路由时候，通过 components 属性为各个视图设置组件
	
		
		* components 对象的 key 就是视图名称，value 就是视图组件
		
	
		const router = createRouter({
			history: createWebHashHistory(),
			routes: [
				{
					path: '/',
					components: {
						// default 视图组件
						default: Home,
						// LeftSidebar 视图组件
						LeftSidebar,
						// RightSidebar 视图组件
						RightSidebar,
					},
				},
			],
		})
	
	
	# 支持嵌套命名视图
		