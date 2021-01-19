--------------------------------
Effect Hook
--------------------------------
	
	function App (props){
		const[count, setCount] = React.useState(0);
		React.useEffect(() => {    // 相当于 componentDidMount 和 componentDidUpdate:
			window.document.title = `点击了 ${count} 次`;
			return () => {
				console.log('每次更新都会执行我');
			}
		});
		return (
			<div>
				<div>点击了： { count } 次 </div>
				<button onClick={ () => setCount(count + 1) }>点击我</button>
			</div>
		)
	}
	
	# 它跟 class 组件中的 componentDidMount、componentDidUpdate 具有相同的用途，只不过被合并成了一个 API	
		* 它在第一次渲染之后和每次更新之后都会执行
		* 每次运行 effect 的同时，DOM 都已经更新完毕。

		* 与 componentDidMount 或 componentDidUpdate 不同，使用 useEffect 调度的 effect 不会阻塞浏览器更新屏幕，这让应用看起来响应更快
		* 大多数情况下，effect 不需要同步地执行。在个别情况下（例如测量布局），有单独的 useLayoutEffect Hook 供使用，其 API 与 useEffect 相同。
	
	# 如果 effect 返回一个函数，React 将会在执行清除操作时调用它。和 componentWillUnmount 具有相同的用途
		* 这是 effect 可选的清除机制。每个 effect 都可以返回一个清除函数。
		* effect 在每次渲染的时候都会执行。（但是componentWillUnmount只有组件在被销毁的时候才会执行一次）
			React.useEffect(() => {
				return () => {
					console.log('每次更新都会执行我');
				}
			});
		
		* 如果不需要清除逻辑，那么就不用返回这个函数
	
	
	# 通过跳过 Effect 进行性能优化
		* 每次渲染后都执行清理或者执行 effect 可能会导致性能问题
		* 在 class 组件中，可以通过在 componentDidUpdate 中添加对 prevProps 或 prevState 的比较逻辑解决


		* 在 useEffect 的 Hook API 中。如果某些特定值在两次重渲染之间没有发生变化，可以通知 React 跳过对 effect 的调用
		* 只要传递数组作为 useEffect 的第二个可选参数即可
			React.useEffect(() => {
			  document.title = `You clicked ${count} times`;
			}, [count]);	// 仅在 count 更改时更新
		
		* 如果数组中有多个元素，即使只有一个元素发生变化，React 也会执行 effect。
		
		* 如果想执行只运行一次的 effect（仅在组件挂载和卸载时执行），可以传递一个空数组（[]）作为第二个参数。
		* 这就告诉 React ， effect 不依赖于 props 或 state 中的任何值，所以它永远都不需要重复执行。


	# 总结
		* React.useEffect(); 方法的形参函数, 会在组件被加载, 更新后执行
		* 如果这个形参函数, 返回了一个函数, 那么这个函数会在组件每次更新后执行