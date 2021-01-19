
------------------------
Portal
------------------------
	# Portal 提供了一种将子节点渲染到存在于父组件以外的 DOM 节点的优秀的方案
		ReactDOM.createPortal(child, container)

		* 第一个参数（child）是任何可渲染的 React 子元素，例如一个元素，字符串或 fragment
		* 第二个参数（container）是一个 DOM 元素。
	
		render() {
		  // React 并*没有*创建一个新的 div。它只是把子元素渲染到 `domNode` 中。
		  // `domNode` 是一个可以在任何位置的有效 DOM 节点。
		  return ReactDOM.createPortal(
			this.props.children, // React 
			domNode				//  DOM节点
		  );
		}
	
		
		* 典型用例：当父组件有 overflow: hidden 或 z-index 样式时，但需要子组件能够在视觉上“跳出”其容器。（例如，对话框、悬浮卡以及提示框）
	

------------------------
Portal 事件冒泡
------------------------
	* portal 可以被放置在 DOM 树中的任何地方，它其他方面，其行为和普通的 React 子节点行为一致
	* portal 仍存在于 React 树， 且与 DOM 树 中的位置无关，那么无论其子节点是否是 portal，像 context 这样的功能特性都是不变的。
	* 包括事件冒泡。一个从 portal 内部触发的事件会一直冒泡至包含 React 树的祖先，即便这些元素并不是 DOM 树 中的祖先



	import React from 'react';
	import ReactDOM from 'react-dom';

	// const element = document.querySelector('#root');
	// -------------------
	const appRoot = document.getElementById('app-root');
	const modalRoot = document.getElementById('modal-root');

	class Modal extends React.Component {
		constructor(props){
			super(props);
			this.el = document.createElement('div');
		}
		componentDidMount(){
			// 在 Modal 的所有子元素被挂载后，
			// 这个 portal 元素会被嵌入到 DOM 树中，
			// 这意味着子元素将被挂载到一个分离的 DOM 节点中。
			// 如果要求子组件在挂载时可以立刻接入 DOM 树，
			// 例如衡量一个 DOM 节点，
			// 或者在后代节点中使用 ‘autoFocus’，
			// 则需添加 state 到 Modal 中，
			// 仅当 Modal 被插入 DOM 树中才能渲染子元素。
			modalRoot.appendChild(this.el);
		}
		componentWillUnmount(){
			modalRoot.removeChild(this.el);
		}
		render (){
			return ReactDOM.createPortal(
				this.props.children,
				this.el
			);
		}
	}  

	function Child (props){
		return (
			// 这个按钮的点击事件会冒泡到父元素
			// 因为这里没有定义 'onClick' 属性
			<div>
				<button>Click</button>
			</div>
		)
	}

	class Parent extends React.Component {
		constructor(props){
			super(props);
			this.state = {
				clicks: 0
			}
		}
		handlerClick = () => {
			 // 当子元素里的按钮被点击时，
			// 这个将会被触发更新父元素的 state，
			// 即使这个按钮在 DOM 中不是直接关联的后代
			this.setState((state) => {
				return {
					clicks: state.clicks + 1
				}
			});
		}
		render (){
			return (
				<div onClick={this.handlerClick}>
					<p>点击次数: {this.state.clicks}</p>
					<Modal>
						<Child />
					</Modal>
				</div>
			)
		}
	}


	ReactDOM.render(<Parent />, appRoot);

	* 通俗理解就是说, ReactDOM.createPortal 把节点挂载到了其他的node节点
	* 虽然html结构上, node节点之间不是父子关系, 但是在组件上是父子关系, 子组件的事件一样会冒泡到父级父级组件


