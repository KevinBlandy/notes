----------------------
element-plus
----------------------
	# 官网
		https://element-plus.org/zh-CN/
	
	# 安装
		pnpm install element-plus
	

	# 在 main.js 中整个导入

		import { createApp } from 'vue'
		import ElementPlus from 'element-plus' 
		import 'element-plus/dist/index.css'

		import App from './App.vue'

		const app = createApp(App);

		app.use(ElementPlus);
		app.mount('#app');


	# 安装 icon	
		
		* 安装

			pnpm install @element-plus/icons-vue
		
		* 在 main.js 中使用

			import * as ElementPlusIconsVue from '@element-plus/icons-vue'

			const app = createApp(App)
			for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
			  app.component(key, component)
			}