--------------------------
参数传递
--------------------------

	# 通过属性（props），把参数传递给组件
	
		
		* 组件声明属性
	
			<p>我是主页，id： {{ props.id }}</p>
	
			const props = defineProps({
				id: String		// 接收 String 类型的 id
			});
		
		* props 设置为 true 时，route.params 将被设置为组件的 props。
	
			 {
				name: 'Home',
				path: '/:id',
				
				// 把 path 中的参数当作 props 传递给组件
				props: true,
				component: () => import('../views/Index.vue'),
			}
				
			
			// 访问 /10000 时，在 Index.vue 中的 props.id 即为 10000
		
		
		* 可以把 props 设置为对象，表示传递给组件的静态属性
			
			props: { id: '10000' }
		
		
		* 可以把 props 设置为函数，参数即访问的路由对象，返回对象即作为传递给组件的属性
		
			  props: route => ({ query: route.query.q })
			  
			  // 请求：/search?q=vue
			  // 返回：{query: 'vue'}
	
		
		* 对于命名视图，必须为每个命名视图定义 props 配置

			{
				path: '/user/:id',
				// 各个命名视图
				components: { 
					default: User, 
					sidebar: Sidebar 
				},
				// 对每个不同的视图进行单独的 props 配置
				props: { 
					default: true, 
					sidebar: false 
				}
			}
	
	
	# RouterView 传递属性
	
		* 主要是通过插槽
		
		<RouterView v-slot="{ Component }">
			// Component 即要渲染的路由组件
			<component
				:is="Component"
				// 定义传递给组件的属性值
				view-prop="value"
			/>
		</RouterView>
	
	