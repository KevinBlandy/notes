------------------
jsx
------------------
	# jsx
		* 语法糖而已, 最终会编译成js代码, 在 {} 里面可以执行任意的js表达式, 或者函数调用
		* JSX 会编译为 React.createElement 调用形式, 所以 React 库也必须引入

		* 自定义的组件, 必须以大写开头, 否则引擎会当作html标签
	
	# 各种合法的表达式
		const name = 'React';
		const element = <h1>Hello, {name}</h1>;

		function foo(user) {
		  if (user) {
			return <h1>Hello, {user)}!</h1>;
		  }
		  return <h1>no body</h1>;
		}
	
	
	# 属性赋值, 用驼峰
		const element = <div tabIndex="0"></div>;
		const element = <div tabIndex={index}></div>;
	
	
	# 布尔类型, Null 以及 Undefined 将会忽略
		<div />
		<div></div>
		<div>{false}</div>
		<div>{null}</div>
		<div>{undefined}</div>
		<div>{true}</div>

		* false, null, undefined, and true 是合法的子元素, 但它们并不会被渲染
		* 以上的 JSX 表达式渲染结果相同

		* 果想渲染 false,true,null,undefined 等值, 需要先将它们转换为字符串
			<div>
				My JavaScript variable is {String(myVariable)}.
			</div>

	# props默认值为true
		<MyTextBox autocomplete />
		<MyTextBox autocomplete={true} />

		* 如果属性只声明, 没赋值, 则默认为true, 上面俩一样
	
	# 属性展开
		function App1() {
			return <Greeting firstName="Ben" lastName="Hector" />;
		}
		function App2() {
			const props = {firstName: 'Ben', lastName: 'Hector'};
			return <Greeting {...props} />;
		}

		* es6挺简单的东西, 解构赋值
		
		* 可以过滤掉某些属性先
			const { kind, ...other } = props;
			return <button {...other} />;  // button有keind属性以外的所有属性
		
		* 连 children 属性, 都可以通过这种方式来传递
			function Foo(props){
				return <div {...props}></div>
			}
			ReactDOM.render(<Foo>Hello</Foo>, element); // <div>Hello</div>

------------------
jsx - 条件渲染
------------------
	# 于依据特定条件来渲染其他的 React 元素
		{ true &&
			<div>Hello</div>
		}

		*  && 之前的表达式必须是boolean值,(哪怕是数字0, 也会触发渲染)
	
	# 复杂的条件
		* 用js代码控制
	
	# 三目运算
		{user.length > 0
			? <div>{user.length} 个人</div>
			: <div>没有人</div>
		}
	
	# 阻止条件渲染, 返回 null
		function User(props){
			if (props.user){
				return <h1>{props.user}</h1>
			}
			return null;
		}

		* 返回 null 并不会影响组件的生命周期, 该被调用的还是会被调用


------------------
jsx - 循环渲染
------------------
	# 使用 map(item, index) 进行遍历
		<ul>
			{val.map((item, index) => {
				return <li key={index}>{item}</li>
			})}
		</ul>
	

------------------
jsx - 子元素
------------------
	# 通过 props.children 属性访问子元素
		function App(props){
		  return <div>{props.children}</div>
		}
		<App>Helo</App>
	
	# 子元素也可以一个函数
		function App(props){
		  const val = []
		  for (let x = 0 ;x < 10 ;x ++){
			val.push(props.children(x));
		  }
		  return <ul>{val.map((item, index) => {
			return <li key={index}>{item}</li>
		  })}</ul>
		}
		<App>{(number) => number * 2}</App>
			
