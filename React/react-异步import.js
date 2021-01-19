----------------------------
异步import
----------------------------
	# 使用 React.lazy 导入其他的模块
		const OtherComponent = React.lazy(() => import('./OtherComponent'));
	
		* 当这个组件首次渲染的时候, 才会被导入
		* React.lazy 接受一个函数, 这个函数需要动态调用 import()
		* 这个函数必须返回一个 Promise, 该 Promise 需要 resolve 一个 defalut export 的 React 组件

	# 在 Suspense 组件中渲染 lazy 组件
		import React, { Suspense } from 'react';
		// 异步的导入组件
		const OtherComponent = React.lazy(() => import('./OtherComponent'));
		function MyComponent() {
		  return (
			<div>
			  <Suspense fallback={<div>Loading...</div>}>
				<OtherComponent />
			  </Suspense>
			</div>
		  );
		}

		* 可以通过 fallback 来做等待加载 lazy 组件时的优雅降级(如 loading 指示器等)
		*  Suspense 组件置于懒加载组件之上的任何位置, 甚至可以用一个 Suspense 组件包裹多个懒加载组件
	
		* 如果模块加载失败(如网络问题), 它会触发一个错误, 可以通过组件的异常捕获机制来处理


	
	# React.lazy 目前只支持默认导出(default exports)
		* 需要延迟加载非默认导出的模块, 可以通过新建一个中间模块来完成
		// ManyComponents.js
		export const MyComponent = /* ... */;		// 命名模块
		export const MyUnusedComponent = /* ... */;

		// MyComponent.js 中间模块，导入命名模块，并且以默认模块的方式暴露出去
		export { MyComponent as default } from "./ManyComponents.js";

		// MyApp.js
		import React, { lazy } from 'react';
		const MyComponent = lazy(() => import("./MyComponent.js")); // 延迟加载默认中间模块中的默认模块


		* 这能保证 tree shaking 不会出错, 并且不必引入不需要的组件
