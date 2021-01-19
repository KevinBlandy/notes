------------------------
PropTypes
------------------------
	# React 也内置了类型检查的功能,要在组件的 props 上进行类型检查，只需配置特定的 propTypes 属性
		import PropTypes from 'prop-types';

		class Greeting extends React.Component {
		  render() {
			return (
			  <h1>Hello, {this.props.name}</h1>
			);
		  }
		}

		Greeting.propTypes = {
		  name: PropTypes.string  // 
		};
		
		* 当传入的 prop 值类型不正确时，JavaScript 控制台将会显示警告
		* 出于性能方面的考虑，propTypes 仅在开发模式下进行检查
	
	# 验证的例子
		import PropTypes from 'prop-types';

		MyComponent.propTypes = {
		  // 你可以将属性声明为 JS 原生类型，默认情况下
		  // 这些属性都是可选的。
		  optionalArray: PropTypes.array,
		  optionalBool: PropTypes.bool,
		  optionalFunc: PropTypes.func,
		  optionalNumber: PropTypes.number,
		  optionalObject: PropTypes.object,
		  optionalString: PropTypes.string,
		  optionalSymbol: PropTypes.symbol,

		  // 任何可被渲染的元素（包括数字、字符串、元素或数组）
		  // (或 Fragment) 也包含这些类型。
		  optionalNode: PropTypes.node,

		  // 一个 React 元素。
		  optionalElement: PropTypes.element,

		  // 一个 React 元素类型（即，MyComponent）。
		  optionalElementType: PropTypes.elementType,

		  // 你也可以声明 prop 为类的实例，这里使用
		  // JS 的 instanceof 操作符。
		  optionalMessage: PropTypes.instanceOf(Message),

		  // 你可以让你的 prop 只能是特定的值，指定它为
		  // 枚举类型。
		  optionalEnum: PropTypes.oneOf(['News', 'Photos']),

		  // 一个对象可以是几种类型中的任意一个类型
		  optionalUnion: PropTypes.oneOfType([
			PropTypes.string,
			PropTypes.number,
			PropTypes.instanceOf(Message)
		  ]),

		  // 可以指定一个数组由某一类型的元素组成
		  optionalArrayOf: PropTypes.arrayOf(PropTypes.number),

		  // 可以指定一个对象由某一类型的值组成
		  optionalObjectOf: PropTypes.objectOf(PropTypes.number),

		  // 可以指定一个对象由特定的类型值组成
		  optionalObjectWithShape: PropTypes.shape({
			color: PropTypes.string,
			fontSize: PropTypes.number
		  }),
		  
		  // An object with warnings on extra properties
		  optionalObjectWithStrictShape: PropTypes.exact({
			name: PropTypes.string,
			quantity: PropTypes.number
		  }),   

		  // 你可以在任何 PropTypes 属性后面加上 `isRequired` ，确保
		  // 这个 prop 没有被提供时，会打印警告信息。
		  requiredFunc: PropTypes.func.isRequired,

		  // 任意类型的数据
		  requiredAny: PropTypes.any.isRequired,

		  // 你可以指定一个自定义验证器。它在验证失败时应返回一个 Error 对象。
		  // 请不要使用 `console.warn` 或抛出异常，因为这在 `onOfType` 中不会起作用。
		  customProp: function(props, propName, componentName) {
			if (!/matchme/.test(props[propName])) {
			  return new Error(
				'Invalid prop `' + propName + '` supplied to' +
				' `' + componentName + '`. Validation failed.'
			  );
			}
		  },

		  // 你也可以提供一个自定义的 `arrayOf` 或 `objectOf` 验证器。
		  // 它应该在验证失败时返回一个 Error 对象。
		  // 验证器将验证数组或对象中的每个值。验证器的前两个参数
		  // 第一个是数组或对象本身
		  // 第二个是他们当前的键。
		  customArrayProp: PropTypes.arrayOf(function(propValue, key, componentName, location, propFullName) {
			if (!/matchme/.test(propValue[key])) {
			  return new Error(
				'Invalid prop `' + propFullName + '` supplied to' +
				' `' + componentName + '`. Validation failed.'
			  );
			}
		  })
		};

	# 限制单个元素
		* 可以通过 PropTypes.element 来确保传递给组件的 children 中只包含一个元素。

		import PropTypes from 'prop-types';
		class MyComponent extends React.Component {
		  render() {
			// 这必须只有一个元素，否则控制台会打印警告。
			const children = this.props.children;
			return (
			  <div>
				{children}
			  </div>
			);
		  }
		}

		MyComponent.propTypes = {
		  children: PropTypes.element.isRequired
		};
	
	# 默认 Prop 值
		* 可以通过配置特定的 defaultProps 属性来定义 props 的默认值：

		class Greeting extends React.Component {
		  render() {
			return (
			  <h1>Hello, {this.props.name}</h1>
			);
		  }
		}

		// 指定 props 的默认值：
		Greeting.defaultProps = {
		  name: 'Stranger'
		};

		// 渲染出 "Hello, Stranger"：
		ReactDOM.render(
		  <Greeting />,
		  document.getElementById('example')
		);