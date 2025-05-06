------------------------
Vue - router
------------------------
	# 官方的路由
		https://router.vuejs.org/zh/
	
	
	# 安装
		pnpm i vue-router
		pnpm add vue-router@4
	
	
	# 创建 Router
		
		* 在 src/routers 目录下创建 index.js 文件
	
	# 对于 History 路由，需要服务器配合处理，把路由权限都交给前端，否则可能 404
		* Nginx 配置

			location / {
				try_files $uri $uri/ /index.html;
			}
		

------------------------
Vue - router 基本用法
------------------------
	# 在 src/views 目录下创建视图组件
		'src/views/home/Home.vue'

	# 在 src/routes 目录下创建路由文件 index.js
	
		import { createRouter, createWebHistory } from "vue-router"

		// 导入路由视图组件
		import Home from '@/views/home/Home.vue'

		// 路由定义
		const routes = [{
			// 路由名
			name: 'Home',
			// 路由路径
			path: '/',
			// 路由组件
			component: Home,
		}]

		export default createRouter({
			// 使用 Hash 路由
			history: createWebHistory(),
			// 路由定义
			routes,
		});
	
	# 在 main.js 中导入路由插件，并载入到 app

		import { createApp } from 'vue'
		import './style.css'
		import App from './App.vue'
		// 路由
		import router from '@/routes/index.js'

		const app = createApp(App);
		// 载入路由插件
		app.use(router);
		app.mount('#app');
	
	# 访问路由器和当前路由
		* 在需要路由器的地方，都可以导入创建的 router 实例
			// 路由器
			import router from '@/routes/index.js';

		* 如果是使用选项式 API，可以在 JavaScript 中如下访问这两个属性：
			
			// 路由器
			this.$router
			// 当前路由
			this.$route
		
		* 组合式 API 可以使用组件

			import router from '@/routes/index.js'
			import { onMounted } from 'vue'
			import { useRoute, useRouter } from 'vue-router'

			const route = useRoute();

			onMounted(() => {
				// 当前路由对象
				console.log(route);
				// 路由器对象
				console.log(useRouter() === router);  // true
			});
	
	