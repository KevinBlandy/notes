-------------------------
严格模式
-------------------------
	# React.StrictMode 是一个用来突出显示应用程序中潜在问题的工具
	# 严格模式检查仅在开发模式下运行,不会影响生产构建

		import React from 'react';

		function ExampleApplication() {
		  return (
			<div>
			  <Header />
			  <React.StrictMode>
				<div>
				  <ComponentOne />
				  <ComponentTwo />
				</div>
			  </React.StrictMode>
			  <Footer />
			</div>
		  );
		}
	
	# StrictMode 目前有助于
		识别不安全的生命周期
		关于使用过时字符串 ref API 的警告
		关于使用废弃的 findDOMNode 方法的警告
		检测意外的副作用
		检测过时的 context API
	
