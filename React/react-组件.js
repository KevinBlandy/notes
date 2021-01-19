--------------------
组件
--------------------
	# 函数组件
		function Welcome(props) {
		  return <h1>Hello, {props.name}</h1>;
		}

		* 函数名称要大写
	
	# 类组件
		class Foo extends React.Component {
		  constructor(props){
			super(props);
		  }
		  render (){
			return (
			  <div>
				${this.props.content}
			  </div>
			)
		  }
		}


----------------------------	
Fragments
----------------------------
	# 组件的 render, 只能返回一个节点, 但是一次性需要返回多个, 而又不能用html节点, 就用 Fragments
		render (){
			return (
				<React.Fragment>
					<td>Hello</td>
					<td>World</td>
				</React.Fragment>
			)
		}

		* Fragment 可以有 key 的声明
	 
	# 可以使用段语法 <> </>
		render (){
			return (
				<>
					<td>Hello</td>
					<td>World</td>
				</>
			)
		}

		* 注意, 段语法不支持 key 属性
		

----------------------------	
React.PureComponent
----------------------------
	# 组件的 shouldComponentUpdate(nextProps, nextState) 方法
		* 使用深比较, 如果数据被修改了, 就返回 true, 更新UI
		* 可能会带来一些性能上的问题

	#  React.PureComponent 用来代替手写 shouldComponentUpdate()
		* 它覆写了 shouldComponentUpdate() 的实现
		* 使用浅比较, 但是可能会带来其他的问题, 特别是  state 和 props 数据解构过于复杂得情况下
	

----------------------------	
不可变的数据
----------------------------
	# 对于一些数据: 对象, 数组, 应该避免更改操作
	# 对于数组
		handleClick() {
		  this.setState(state => ({
			words: [...state.words, 'marklar'],
		  }));
		};
		
		* 直接创建一个新得, 使用解构赋值把旧属性添加进去
		* 再添加新得数据
	
	# 对于对象
		function updateColorMap(colormap) {
		  return Object.assign({}, colormap, {right: 'blue'});
		}

		* 可以使用 ES6的 Object.assign({}, o1, o2); 把多个对象得属性合并为一个
		* 后面相同的属性覆盖前面的

