---------------------------
dom属性
---------------------------
	dangerouslySetInnerHTML 
		* React 为浏览器 DOM 提供 innerHTML 的替换方案
		* 可以直接在 React 中设置 HTML, 设置 dangerouslySetInnerHTML 时, 需要向其传递包含 key 为 __html 的对象
		* 可能会导致XSS问题, 慎用
			function App(props){
			  return (
				<div dangerouslySetInnerHTML = {{ __html: props.content }} />
			  );
			}

			<App content={'<h1>HelloWorld</h1>'}></App>
		
