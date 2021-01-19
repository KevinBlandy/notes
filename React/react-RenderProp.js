-------------------------
Render Prop
-------------------------
	# 一种在 React 组件之间使用一个值为函数的 prop 共享代码的简单技术
		* 具有 render prop 的组件接受一个函数, 该函数返回一个 React 元素并调用它而不是实现自己的渲染逻辑

		* 通俗的说就是, 组件提供了一些计算功能（数据）, 但是不知道要把这些数据给哪些子组件用，写死？这不好。
		* 于是要求, 谁用我这个组件，就必须提供一个函数。这个函数返回一个组件。参数就是这个组件需要的数据。
		* 那好了，我直接调用这函数，我计算出的数据都给它。它渲染啥，本组件不关心。
		

	# 可以使用带有 render prop 的常规组件来实现大多数高阶组件 (HOC)


		

-------------------------
Render Prop demo
-------------------------
	import React from 'react';
	import ReactDOM from 'react-dom';

	const element = document.querySelector('#root');

	// ------------------------------------------------------------

	class Image extends React.Component {
	  render (){
		const mouse = this.props;
		return (
		  <img src="/favicon.ico" style={{ position: 'absolute', left: mouse.x, top: mouse.y }} alt={"React Logo"}/>
		)
	  }
	}

	class MouseWithImage extends React.Component {
	  constructor(props){
		super(props);
		this.state = {
		  x: 0,
		  y: 0
		}
	  }
	  onMouseMove = (event) => {
		this.setState({
		  x: event.clientX,
		  y: event.clientY
		});
	  }
	  render (){
		return (
		  <div style={{ height: '100vh' }} onMouseMove={this.onMouseMove}>
			  {/*
				我反正提供了x，y的计算。这里不写死某个组件。
				谁调用我，给我个函数（属性名称随便），我把xy给你。你自己爱渲染啥，就渲染啥。
				提高了组件的灵活性 & 复用性。
				<Image {...this.state}></Image>
			   */}
			{this.props.render(this.state)}
		  </div>
		)
	  }
	}

	class MouseTracker  extends React.Component {
	  render (){
		return (
		  <>
			<h1>请移动鼠标</h1>
			<MouseWithImage render={mouse => {
			  return <Image {...mouse}/>
			}}/>
		  </>
		)
	  }
	}

	ReactDOM.render(<MouseTracker/>, element);


	# Render Prop 中的 Render仅仅只是一个名字
		* 可以用任何自己喜欢的属性名称来代替它（本质上就是把函数通过 props 传递给子组件而已）
		* 甚至可以用 children 来传递
			 <div style={{ height: '100vh' }} onMouseMove={this.onMouseMove}>
				{this.props.children(this.state)}
			 </div>

			  <>
				<h1>请移动鼠标</h1>
				  <MouseWithImage>
					{
					  mouse => {
						return <Image {...mouse}/>
					  }
					}
				  </MouseWithImage>
			  </>
	
	# 将 Render Props 与 React.PureComponent 一起使用时要小心
		* render 方法里创建函数，那么使用 render prop 会让 React.PureComponent 失效。
		* 因为浅比较 props 的时候总会得到 false，并且在这种情况下每一个 render 对于 render prop 将会生成一个新的值。
			class Mouse extends React.PureComponent { // 继承 PureComponent
			  // 与上面相同的代码......
			}

			class MouseTracker extends React.Component {
			  render() {
				return (
				  <div>
					<h1>Move the mouse around!</h1>

					{/*
					  每个渲染的 `render` prop的值将会是不同的。因为每次都是新的一个函数被创建了
						Mouse 组件通过浅比较 ==，始终返回 false
					*/}
					<Mouse render={mouse => (
					  <Cat mouse={mouse} />
					)}/>
				  </div>
				);
			  }
			}		
		* 解决这个问题，可以定义一个 prop 作为实例方法
			class MouseTracker extends React.Component {
			  // 定义为实例方法，`this.renderTheCat`始终
			  // 当我们在渲染中使用它时，它始终指向的是相同的函数
			  renderTheCat(mouse) {
				return <Cat mouse={mouse} />;
			  }

			  render() {
				return (
				  <div>
					<h1>Move the mouse around!</h1>
					<Mouse render={this.renderTheCat} />
				  </div>
				);
			  }
			}