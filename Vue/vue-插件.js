-----------------------
插件
-----------------------
	
	# 为 Vue 添加全局功能的工具
	
		import { createApp } from 'vue'
		
		
		// 定义插件对象
		const myPlugin = {
			install(app, options) {
				// 配置此应用
			}
		}

		const app = createApp({})

		// myPlugin 可以是方法或者对象
		app.use(myPlugin, {
			/* 可选的选项 */
		})
		
		* 插件可以是一个拥有 install() 方法的对象，也可以直接是一个安装函数本身。
		
		* 安装函数会接收到安装它的应用实例和传递给 app.use() 的额外选项作为参数：
			app.use((app, options) => {
				// options 即注时传递的选项
				console.log(options); // {"applictionName": "Vue"}
				
			}, {applictionName: 'Vue'});
	
	# 插件主要的场景如下：
	
		* 通过 app.component() 和 app.directive() 注册一到多个全局组件或自定义指令。
		* 通过 app.provide() 使一个资源可被注入进整个应用。
		* 向 app.config.globalProperties 中添加一些全局实例属性或方法
		* 上面的情况都包含了
	
	
	