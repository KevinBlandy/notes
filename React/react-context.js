------------------------------
context
------------------------------
	# Context 提供了一个无需为每层组件手动添加 props, 就能在组件树间进行数据传递的方法
		* 典型的场景就是: 登录后的用户信息, 主题信息, 是所有组件共享的
	
	
	# 通过 React.createContext 创建一个 Context
		const MyContext = React.createContext(defaultValue);

			* 通过参数, 指定一个默认值
			* 当 React 渲染一个订阅了这个 Context 对象的组件, 这个组件会从组件树中离自身最近的那个匹配的 Provider 中读取到当前的 context 值
				<MyContext.Provider value={/* 某个值 */}>

			* 只有当组件所处的树中没有匹配到 Provider 时, 其 defaultValue 参数才会生效
			* 将 undefined 传递给 Provider 的 value 时, 消费组件的 defaultValue 不会生效

			* 多个 Provider 也可以嵌套使用，里层的会覆盖外层的数据
			* 当 Provider 的 value 值发生变化时, 它内部的所有消费组件都会重新渲染
			* Provider 及其内部 consumer 组件都不受制于 shouldComponentUpdate 函数, 因此当 consumer 组件在其祖先组件退出更新的情况下也能更新
	
	# 在组件中, 通过 Class.contextType 来设置 Context
		class MyClass extends React.Component {
		  componentDidMount() {
			let value = this.context;
			/* 在组件挂载完成后，读取到 MyContext 组件的值 */
		  }
		  componentDidUpdate() {
			let value = this.context;
			/* ... */
		  }
		  componentWillUnmount() {
			let value = this.context;
			/* ... */
		  }
		  render() {
			let value = this.context;
			/* 基于 MyContext 组件的值进行渲染 */
		  }
		}
		// 设置组件的 contextType 为指定的 Context
		MyClass.contextType = MyContext;
		
		* 设置组件的 contextType 为指定的 Context
		* 这能让组件使用 this.context 来消费最近 Context 上的那个值, 可以在任何生命周期中访问到它, 包括 render 函数

		* 也可以使用实验性的 es6 语法来设置 Context
			class MyClass extends React.Component {
			  static contextType = MyContext; // 通过 static 设置组件的 contextType 为指定的 Context
			  render() {
				let value = this.context;
				/* 基于这个值进行渲染工作 */
			  }
			}
	
	
	# Consumer 订阅组件值变化
		* 组件能订阅到 context 变更, 可以在在函数式(函数作为子元素)组件中完成订阅 context
			<MyContext.Consumer>
			  {value => /* 基于 context 值进行渲染*/}
			</MyContext.Consumer>

		* 这个函数接收当前的 context 值, 返回一个 React 节点
		* 传递给函数的 value 值等同于往上组件树离这个 context 最近的 Provider 提供的 value 值
		* 如果没有对应的 Provider, value 参数等同于传递给 createContext() 的 defaultValue
	
		* 在创建Context的时候, 参数是对象, 属性有多少个, 那么在 Consumer 回调函数函数中, 属性就有多少个(解构赋值)
		* 可以利用这种方式, 完成在嵌套很深的组件树中更新 context
			const ThemeContext =  React.createContext({ // 俩属性
				theme: themes.light,		
				toggleTheme: () => {}	
			});
			<ThemeContext.Consumer>
            {
                ({theme, toggleTheme}) => { // 俩属性
                    return (
                        <button onClick={toggleTheme} style={{backgroundColor: theme.background}}>Toggle Theme</button>
                    )
                }
            }
			</ThemeContext.Consumer>
	
	# 总结
		* 定义
			使用 React.createContext(defaultValue); 创建默认值的 Context
			Provider	用来覆写默认的Context值
		
		* 使用
			设置组件类属性: contextType 值为指定的 Context, 在组件中通过 this.context 读取到值
			通过 Consumer, 定义函数体来读取到值



------------------------------
context - 注意事项
------------------------------
	# context 会使用参考标识(reference identity)来决定何时进行渲染, 这里可能会有一些陷阱
		* 当 provider 的父组件进行重渲染时, 可能会在 consumers 组件中触发意外的渲染

			class App extends React.Component {
			  render() {
				return (
				  <MyContext.Provider value={{something: 'something'}}>
					<Toolbar />
				  </MyContext.Provider>
				);
			  }
			}

			* 当每一次 Provider 重渲染时, 以下的代码会重渲染所有下面的 consumers 组件, 因为 value 属性总是被赋值为新的对象
	
	# 为了防止这种情况, 将 value 状态提升到父节点的 state 里
		class App extends React.Component {
		  constructor(props) {
			super(props);
			this.state = {
			  value: {something: 'something'},
			};
		  }

		  render() {
			return (
			  <Provider value={this.state.value}>
				<Toolbar />
			  </Provider>
			);
		  }
		}

	

------------------------------
context - api
------------------------------
	React.createContext

	Context.Provider
	Class.contextType
	Context.Consumer
	Context.displayName
