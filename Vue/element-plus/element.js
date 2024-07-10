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
