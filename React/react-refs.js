-------------------
refs
-------------------
	
	# 基本的创建, 绑定, 访问
		class MyCompnent extends React.Component {
		  constructor(props){
			super(props);
			// 创建ref
			this.myRef = React.createRef();
		  }
		  onClick = () => {
			// 通过 ref的current属性，获取到绑定的元素
			const node = this.myRef.current; 
			console.log(node);  // <div>REF</div>
		  }
		  render (){
			return (
			  // 绑定div元素到ref属性
			  <div ref={this.myRef} onClick={this.onClick}>
				REF
			  </div>
			)
		  }
		}

	# ref 的值根据节点的类型而有所不同
		* 当 ref 属性用于 HTML 元素时，current属性	是DOM元素
			<div ref={this.myRef1} ></div> // <div>....

		* 当 ref 属性用于自定义 class 组件时，current属性是组件的实例 
			<Foo ref={this.myRef2}></Foo>  //Foo {....}
		
	
	# 生命周期
		* React 会在组件挂载时给 current 属性传入 DOM 元素，并在组件卸载时传入 null 值。
		* ref 会在 componentDidMount 或 componentDidUpdate 生命周期钩子触发前更新。

	
	# Refs 与函数组件
		* 不能在函数组件上使用 ref 属性，因为他们没有实例
		* 如果要在函数组件中使用 ref，可以使用 forwardRef（可与 useImperativeHandle 结合使用）
		* 或者可以将该组件转化为 class 组件
		
		* 在函数组件中, 也可以使用 ref，必须先使用 React.useRef(null) 函数初始化创建
			function Test () {
			  // 创建ref
			  const ref = useRef(null);			  // 这里必须声明 textInput，这样 ref 才可以引用它
			  function onClick(){
				// 访问ref
				ref.current.focus();
			  }
			  return (
				<div>
				  // 绑定ref
				  <input ref={ref} placeholder="请输入"></input><br/>
				  <button onClick={onClick}>点击我获得焦点</button>
				</div>
			  )
			}


			ReactDOM.render(<Test/>, element);
	

	# 回调 Refs
		* React 支持另一种设置 refs 的方式，称为“回调 refs”。
		* 它能更精细地控制何时 refs 被设置和解除

		* 跟 createRef() 创建的 ref 属性不一样
		* 回调 Refs 会传递一个函数。这个函数中接受 React 组件实例或 HTML DOM 元素作为参数，以使它们能在其他地方被存储和访问

			class App extends React.Component {
			  constructor(props){
				super(props);
				
				// 创建一个ref，其实就是声明一个函数，参数就已经是 dom元素或者组件实例对象，不需要通过 current 获取
				this.setTextRef = node => {
				  this.textElement = node;
				}
			  }
			  onClick = () => {
				if (this.textElement){
				  this.textElement.focus();
				}
			  }
			  render (){
				return (
				  <div>
					  // 绑定 ref，会执行回调函数
					  <input ref={this.setTextRef}></input><br/>
					  <button onClick={this.onClick}>获取焦点</button>
				  </div>
				)
			  }
			}

		* React 将在组件挂载时，会调用 ref 回调函数并传入 DOM 元素，当卸载时调用它并传入 null。
		* 在 componentDidMount 或 componentDidUpdate 触发前，React 会保证 refs 一定是最新的。
	
		
		* 可以在组件间传递回调形式的 refs，就像可以传递通过 React.createRef() 创建的对象 refs 一样。
			function Sub (props){
			  return(
				// 设置ref，为父级组件给的 inputRef 属性
				<input ref={props.inputRef}></input>
			  )
			}

			class App extends React.Component {
			  onClick = () => {
				if(this.inputRef){
				  this.inputRef.focus()
				} 
			  }
			  render (){
				return (
				  <div>
					<Sub inputRef={element => this.inputRef = element}></Sub>
					<button onClick={this.onClick}>点击</button>
				  </div>
				)
			  }
			}
		
		* 如果 ref 回调函数是以内联函数的方式定义的，在更新过程中它会被执行两次，第一次传入参数 null，然后第二次会传入参数 DOM 元素。
		* 这是因为在每次渲染时会创建一个新的函数实例，所以 React 清空旧的 ref 并且设置新的。
		* 通过将 ref 的回调函数定义成 class 的绑定函数的方式可以避免上述问题，但是大多数情况下它是无关紧要的。

-------------------
refs转发
-------------------
	# 将 DOM Refs 暴露给父组件
		* 通常不建议这样做，因为它会打破组件的封装

		* 如果使用 16.3 或更高版本的 React, 这种情况下推荐使用 refs 转发
		* Ref 转发使组件可以像暴露自己的 ref 一样暴露子组件的 ref

		* 版本过低的话, 使用这个方法: https://gist.github.com/gaearon/1a018a023347fe1c2476073330cc5509
	

	# 创建转发 React.forwardRef((props, ref) => {return ....});
		const FancyButton = React.forwardRef((props, ref) => {
		  return (
			// 把父级组件的ref，绑定到button节点
			<button ref={ref} {...props}></button>
		  )
		})

		class App extends React.Component {
		  constructor(props){
			super(props);
			// 创建ref
			this.ref = React.createRef();
		  }
		  onClick = () => {
			// 读取到子组件的ref
			const node = this.ref.current;
			console.log(node); // <button>点击我</button>
		  }
		  render(){
			// 把ref给子组件
			return <FancyButton ref={this.ref} onClick={this.onClick}>点击我</FancyButton>
		  }
		}

		ReactDOM.render(<App></App>, element);

		* React.forwardRef 参数是一个函数, 该函数第一个参数就是父组件给的 props, 第二个参数就是 ref
		* 该函数返回的是可以组件, 也可以是html, ref 都可以进行绑定
	
--------------------------
在高阶组件中转发 refs
--------------------------


