------------------------
vite plugin
------------------------
	# vite-plugin-svg-icons
		* https://github.com/vbenjs/vite-plugin-svg-icons
	
	# 安装
		
		pnpm install vite-plugin-svg-icons -D
			// -D 表示开发环境的依赖，不需要打包到生产环境中

		pnpm install fast-glob -D
			// 高版本的话，还需要这个依赖
	
	# 配置

		* 核心代码
			 createSvgIconsPlugin({
				// src/assets/icons 就是存放icon图标的位置
				iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
				symbolId: 'icon-[dir]-[name]'
			  })
		 
		 * 配置在 vite.config.js 中的 plugins 数组中
		
			import { defineConfig } from 'vite';
			import vue from '@vitejs/plugin-vue';
			import { createSvgIconsPlugin } from 'vite-plugin-svg-icons';
			import path from 'path';

			// https://vitejs.dev/config/
			export default defineConfig({
			  plugins: [vue(), createSvgIconsPlugin({
				iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
				symbolId: 'icon-[dir]-[name]'
			  })],
			})
		
		* 在 main.js 中导入
			import 'virtual:svg-icons-register';


	# 使用
		1. 在矢量图标库选择图标
			* 矢量图标库：https://www.iconfont.cn/
		
		2. 下载svg
			* 存储到 'src/assets/icons' 目录下
		
		3. 在组件中使用
	
			<!--图标容器-->
			<svg>
			  <use xlink:href="#icon-foo-071_对话"></use>
			</svg>
			  <!--图标容器-->
			  <svg>
			  <use xlink:href="#icon-071_奖杯"></use>
			</svg>
				
			* 使用 usr 标签表示 SVG 图片。
			* 使用 xlink:href 属性引入 icon，不需要指定文件后缀名称。
			* xlink:href 属性必须以 '#icon-' 开头。

			* 如果icons图标在子目录中，则在 xlink:href 属性中通过 '-' 分割。
				"#icon-foo-071_对话" // icons 地址为 => src/assets/icons/foo/071_对话.svg
		

	
	# 可用的属性
		fill
			* 可以设置 SVG 图标的颜色。
			