--------------------------------
State Hook
--------------------------------
	function App (props){
		const[count, setCount] = React.useState(0);  // [0, function(fiber, queue, action)){.....}]
		return (
			<div>
				<div>点击了： { count } 次 </div>
				<button onClick={ () => setCount(count + 1) }>点击我</button>
			</div>
		)
	}

	
	# 通过唯一的参数, 设置一个初始化值, 可以是任意数据
	# useState 会返回一对值: 初始值一个更新它的函数
	# 可以在一个组件中多次使用 State Hook
		* 多次调用 useState 的时候，能保证每次渲染时它们的调用顺序是不变的

	#  state 只在组件首次渲染的时候被创建
		* 在下一次重新渲染时，useState 返回给我们当前的 state
	
	