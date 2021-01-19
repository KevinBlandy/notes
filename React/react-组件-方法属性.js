--------------------
组件 - state & props
--------------------
	# 区别
		state
			* 是组件内部维护的数据
			* 它的更改只能有自己完成

		props
			* 是父组件给我的数据
			* 我只能读, 不能改, 修改操作只能调用父组件的回调函数来修改
	
	# 修改操作, 必须要调用函数, 不能直接赋值
		this.state.key = value;			// 错误，无效
		this.setState({key: value});	// 正确
	
	# state的更新可能是异步的
		* 如果修改的数据, 依赖于旧的数据, 可能会出现BUG
			this.setState({
			  counter: this.state.counter + this.props.increment,
			});
				
		* 要解决这个问题, 可以让 setState() 接收一个函数而不是一个对象
			this.setState((state, props) => {
				return {
					counter: state.counter + props.increment
				}
			});

			* 上一个修改后的state和props会作为参数, 
			* 不能直接修改参数, 返回一个带有新值的 state 对象
	
	# 这俩属性也是实例的唯一的俩属性


--------------------
组件 - 生命周期方法
--------------------
	componentDidMount();
		* 在组件已经被渲染到 DOM 中后运行
		* 一般在这里通过网络请求, 获取到数据

		* 在里直接调用 setState(), 它将触发额外渲染, 此渲染会发生在浏览器更新屏幕之前(即使在 render() 两次调用的情况下, 用户也不会看到中间状态)
		* 谨慎使用该模式, 因为它会导致性能问题, 最好还是在 constructor() 中初始化 state

	componentWillUnmount();
		* 会在组件卸载及销毁之前直接调用
		* 在这里清理资源, 不应该调用 setState(), 因为该组件将永远不会重新渲
		* 组件实例卸载后, 将永远不会再挂载它
	
	componentDidCatch(error, info)
		error	抛出的错误。
		info	带有 componentStack key 的对象, 其中包含有关组件引发错误的栈信息

		* 渲染出现异常的时候会调用
		* 它应该用于记录错误之类的情况
	
	getSnapshotBeforeUpdate(prevProps, prevState)
		* 最近一次渲染输出(提交到 DOM 节点)之前调用
		* 它使得组件能在发生更改之前从 DOM 中捕获一些信息(例如, 滚动位置), 此生命周期的任何返回值将作为参数传递给 componentDidUpdate()

	shouldComponentUpdate(nextProps, nextState)
		prevProps	更新之后的props
		prevState	更新之后的state

		* 在数据被更新之前调用, 根据返回值判断 React 组件的输出是否受当前 state 或 props 更改的影响
		* 默认行为是 state 每次发生变化组件都会重新渲染(没事儿别去瞎jb改), 返回值默认为 true

		* 如果此方法返回 false, 则不会调用 componentDidUpdate(), render(); 
		* 返回 false 并不会阻止子组件在 state 更改时重新渲染

		* 可以控制是否要重新渲染, 用来优化性能的一种手段

		* 首次渲染或使用 forceUpdate() 时不会调用该方法

	componentDidUpdate(prevProps, prevState, snapshot)
		prevProps	更新之前的props
		prevState	更新之前的state
		snapshot	如果用户实现了 getSnapshotBeforeUpdate() 方法, 那么该参数就是此方法的返回值, 反之为 undefined

		* 在数据更新后会被立即调用
		* 首次渲染不会执行此方法

		* 可以在 componentDidUpdate() 中直接调用 setState(),但注意它必须被包裹在一个条件语句里
		* 否则会导致死循环(更新了数据->触发事件->更新了数据->触发事件.....)
			componentDidUpdate(prevProps) {
			  // 典型用法（不要忘记比较 props）：
			  if (this.props.userID !== prevProps.userID) {
				this.fetchData(this.props.userID); // 重新加载数据
			  }
			}


	
	static getDerivedStateFromError(erro)
		* 渲染出现异常的时候会调用
		* 参数为抛出的异常, 并返回一个值以更新 state
		* 它应该用来更新ui

	static getDerivedStateFromProps(props, state)
		
	
	# 当组件实例被创建并插入 DOM 中时，其生命周期调用顺序如下：
		constructor()
		static getDerivedStateFromProps()
		render()
		componentDidMount()
	
	# 当组件的 props 或 state 发生变化时会触发更新, 组件更新的生命周期调用顺序如下
		static getDerivedStateFromProps()
		shouldComponentUpdate()
		render()
		getSnapshotBeforeUpdate()
		componentDidUpdate()

--------------------
组件 - 其他方法
--------------------
	constructor(props)
		* 构造函数
		* 如果不初始化 state 或不进行方法绑定, 则不需要为 React 组件实现构造函数
		* 它对于 state 的操作, 只能是初始化赋值
			this.state = {k: v}
		
		* 避免将 props 的值复制给 state, 这是一个常见的错误, 虽然从代码上来说好像没问题
	
	setState(updater, [callback])
		* 构造函数中不能执行这个方法

		* callback为可选的回调函数, 它将在 setState 完成合并并重新渲染组件后执行(建议使用componentDidUpdate()来代替)


	forceUpdate(callback)
		* forceUpdate() 将致使组件调用 render() 方法
		* 此操作会跳过该组件的 shouldComponentUpdate(), 但其子组件会触发正常的生命周期方法, 包括 shouldComponentUpdate() 方法
		* 如果标记发生变化，React 仍将只更新 DOM
		* 应该避免使用 forceUpdate(), 尽量在 render() 中使用 this.props 和 this.state

--------------------
组件 - 类属性
--------------------
	defaultProps 
		* 以为 Class 组件添加默认 props
		* 一般用于 props 未声明, 但又不能为 null 的情况
		class CustomButton extends React.Component {
		}
		CustomButton.defaultProps = {
		  color: 'blue'
		};
		
		<CustomButton /> ; // props.color 将设置为 'blue'
		 <CustomButton color={null} /> ; // props.color 将保持是 null
	
	displayName
		* 调试用, 给组件起名字, 没了

--------------------
组件 - 方法的this
--------------------
	# 在构造函数中先进行绑定
		constructor(props){
			super(props);
			this.handler = this.handler.bind(this);
		}
		handler (event){
			console.log(this);
		}
		<div onClick={this.handler}></div>

	# 在调用的时候进行绑定
		handler (event){
			console.log(this);
		}
		<div onClick={this.handler.bind(this)}></div>
	
	# 写成箭头函数(es6的东西)
		handler = (event) => {
			console.log(this);
		}
		<div onClick={this.handler}></div>
	
	# 使用箭头函数调用
		handler (e) {
			console.log(event, id, this);
		}
		<button onClick={(e) => this.handler(e)}>
		</button>

--------------------
组件 - 方法传参数
--------------------
	# 使用lambda执行
		handler (e, arg1) {
			console.log(event, arg1);
		}
		<button onClick={(e) => this.deleteRow(e, '其他参数')}>Delete Row</button>

		* lambda 的参数就是 event 事件对象, 可以把这个参数在任意位置传递给目标函数
	
	# 使用 bind 执行
		handler (arg, event) {
			console.log(arg, event);
		}
		<button onClick={this.deleteRow.bind(this, id)}>Delete Row</button>
		
		* 事件对象, 默认在最后一个参数的位置