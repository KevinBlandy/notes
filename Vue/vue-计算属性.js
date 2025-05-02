-------------------------------
计算属性
-------------------------------
	# 使用 computed 方法来创建计算属性
	
		* omputed() 接收一个 getter 函数，返回值为一个计算属性 ref。
		
			<button @click="greeting='Hello'">
				{{ upper }}		// 访问 upper 计算属性
			</button>
			
			import { ref, computed, onMounted} from 'vue'
			
			// ref 对象
			const greeting = ref('hi');

			// upper 计算属性
			const upper = computed(() => {
				// 返回转换为大写后的 ref 对象
				// 在 ref 对象值变化后，该计算属性也会变化
				return greeting.value.toUpperCase();
			});
			
			onMounted(() => {
				// 其他一般的 ref 类似，可以通过 upper.value 访问计算结果。
				console.log(upper.value);  // HI
			});


		
		* 计算属性值会基于其响应式依赖被缓存，只有响应式依赖更新后才会重新计算。
		
			// now 这个计算属性永远不会更新，因为没有访问到响应式依赖
			const now = computed(() => Date.now())
	
	# 可写计算属性
		* 计算属性默认是只读的，尝试修改一个计算属性时，会有警告。
		
		* 非要写的话，可以通过同时提供 getter 和 setter 来创建
		
			<button @click="upper = 'Hello'">  // 在点击事件中给计算属性赋值
				{{ upper }}
			</button>
			
			import { ref, computed, onMounted} from 'vue'

			const greeting = ref('hi');

			const upper = computed({
				get(){
					// 访问响应式属性
					return greeting.value.toUpperCase();
				},
				set(val){
					// 接收设置的值，修改响应式属性
					greeting.value = val;
				} 
			});
	
	# 获取上一个值

		* 可以通过访问计算属性的 getter 的第一个参数来获取计算属性返回的上一个值
		
		const upper = computed((preVal) => {
			console.log(preVal);  // 访问上一个值
			return greeting.value.toUpperCase();
		});
	
	# 最佳实践
		* Getter 不应有副作用
			* 不要改变其他状态、在 getter 中做异步请求或者更改 DOM！
			*  Getter 的职责应该仅为计算和返回根据其他值派生一个的值
	
		* 避免直接修改计算属性值
			* 计属性的返回值应该被视为只读的，并且永远不应该被更改——应该更新它所依赖的源状态以触发新的计算。
