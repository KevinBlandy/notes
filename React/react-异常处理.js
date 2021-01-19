----------------------
异常处理
----------------------
	# 组件的2个异常处理方法
		componentDidCatch(error, info)
			error	抛出的错误。
			info	带有 componentStack key 的对象, 其中包含有关组件引发错误的栈信息

			* 渲染出现异常的时候会调用
			* 它应该用于记录错误之类的情况
	
		static getDerivedStateFromError(erro)
			* 渲染出现异常的时候会调用
			* 参数为抛出的异常, 并返回一个值以更新 state
			* 它应该用来更新ui
	
	# 当组件实现了 这两个生命周期方法中的任意一个(或两个)时, 那么它就变成一个错误边界
		class ErrorBoundary extends React.Component {
		  constructor(props) {
			super(props);
			this.state = { hasError: false };
		  }
		  static getDerivedStateFromError(error) {
			// 更新 state 使下一次渲染能够显示降级后的 UI
			return { hasError: true };
		  }
		  componentDidCatch(error, errorInfo) {
			// 你同样可以将错误日志上报给服务器
			logErrorToMyService(error, errorInfo);
		  }

		  render() {
			if (this.state.hasError) {
			  // 你可以自定义降级后的 UI 并渲染
			  return <h1>出现了一些问题</h1>;
			}

			return this.props.children; 
		  }
		}
		

		<ErrorBoundary>
		  <MyWidget />
		</ErrorBoundary>
	
	# 自 React 16 起, 任何未被错误边界捕获的错误将会导致整个 React 组件树被卸载

	# 错误边界无法捕获事件处理器内部的错误

		* 如果需要在事件处理器内部捕获错误, 使用普通的 JavaScript try / catch 语句

		class MyComponent extends React.Component {
		  constructor(props) {
			super(props);
			this.state = { error: null };
			this.handleClick = this.handleClick.bind(this);
		  }

		  handleClick() {
			try {
			  // 执行操作，如有错误则会抛出
			} catch (error) {
			  this.setState({ error });
			}
		  }

		  render() {
			if (this.state.error) {
			  return <h1>Caught an error.</h1>
			}
			return <button onClick={this.handleClick}>Click Me</button>
		  }
		}
