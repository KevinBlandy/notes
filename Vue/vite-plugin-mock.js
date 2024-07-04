------------------------
vite plugin
------------------------
	# vite-plugin-mock
		* https://github.com/vbenjs/vite-plugin-mock
	
	# 安装
		pnpm install vite-plugin-mock -D

	
	# 插件配置
		
		* 核心代码


		* 配置在 vite.config.js 下的 plugins 中。

			import { UserConfigExport, ConfigEnv } from 'vite'

			import { viteMockServe } from 'vite-plugin-mock'
			import vue from '@vitejs/plugin-vue'

			export default ({ command }: ConfigEnv): UserConfigExport => {
			  return {
				plugins: [
				  vue(),
				  viteMockServe({
					mockPath: 'mock',
					enable: true,
				  }),
				],
			  }
			}