------------------------
vite
------------------------
	# 可以先安装 pnpm
		npm install -g pnpm

	# 创建 Vue
		pnpm create vite
	
	# '@' alias 相对路径映射

		// vite.config.js

		import { defineConfig } from 'vite'
		import vue from '@vitejs/plugin-vue'
		import path from 'path'

		// https://vitejs.dev/config/
		export default defineConfig({
		  plugins: [vue()],
		  resolve: {
			alias: {
			  "@": path.resolve('./src'),
			}
		  }
		});


------------------------
vite 环境变量
------------------------
	
	# 在项目根目录下创建环境文件，例如：
		.env.dev
		.env.pro
		.env.test
	
	
	# 在环境文件中，设置变量属性
		* 必须以 VITE_ 为前缀，才可以暴露给外部读取
		
		# Dev 环境配置

		MODE_ENV = "Dev"
		VITE_APP_TITLE = "My Project"
		VITE_BASE_API = "/dev-api"
	
	
	# 配置运行命令
		* 在 pagckage.json 的 scripts 对象中添加属性。
		* 主要是通过 '--mode <env>' 参数来指定环境。

		  "scripts": {
			"dev": "vite",
			// 开发时，指定环境
			// "dev": "vite --mode test",
			"build": "vite build",
			"preview": "vite preview",
			
			// 新增运行命令，通过 --mode 指定环境属性
			"build:test": "vite build --mode test",
			"build:pro": "vite build --mode pro",
			"build:dev": "vite build --mode dev"
		  },
		
	# 获取环境变量
		* 通过 import.meta.evn 获取环境变量

		const env = import.meta.env;
		console.log(env);

		// {"VITE_APP_TITLE":"[test] My Project","VITE_BASE_API":"/test-api","BASE_URL":"/","MODE":"test","DEV":true,"PROD":false,"SSR":false}
	

