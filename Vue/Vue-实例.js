
# 实例
	* 有的 Vue 组件都是 Vue 实例,并且接受相同的选项对象 (一些根实例特有的选项除外)
	* 只有当实例被创建时 data 中存在的属性才是响应式的
		vm.b = 'hi'		//添加了一个新的属性b,对 b 的改动将不会触发任何视图的更新
	
	* Object.freeze(),会阻止修改现有的属性,也意味着响应系统无法再追踪变化,而且会抛出异常
		<div id="app">
			<p @click="message = '新值'">{{message}}</p>
		</div>
		let obj = {
				message : '旧值'
		}
		Object.freeze(obj);		//冻结属性对象
		let vue = new Vue({
			el:'#app',
			data:obj,
		});
		
	* 除了数据属性,Vue 实例还暴露了一些有用的实例属性与方法,它们都有前缀 $,以便与用户定义的属性区分开来
		vm.$data;
		vm.$el;
		vm.$watch
		(更多参考API)
	
	* 生命周期钩子函数,这些函数里面的this指向vue实例
		created		//创建的时候执行
		mounted
		updated
		destroyed
	
	* 不要在选项或者回调上使用箭头函数
		* 箭头函数的this,指向定义时的上下文,而不是运行时的
	
