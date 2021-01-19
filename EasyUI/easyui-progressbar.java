----------------------------
easyui-progressbar			|
----------------------------
	# 进度条
	# 属性名
		easyui-progressbar

----------------------------
easyui-progressbar属性		|
----------------------------
	width
		* 设置进度条宽度
	
	height
		* 设置进度条高度

	value
		* 进度条的值最大100,最小0
	
	text
		* 进度条显示的文本.默认显示的就是上面的进度值.不建议修改

----------------------------
easyui-progressbar事件		|
----------------------------
	onChange
		* 在值更改的时候触发。 
		* 有两个参数:newValue,oldValue
		* demo
			$('#p').progressbar({
				onChange: function(value){
					alert(value)
				}
			});
	


----------------------------
easyui-progressbar方法		|
----------------------------
	options
		* 返回属性对象
	
	resize
		* 组件大小
		* demo
			$('#p').progressbar('resize');           // 更改进度条到原始宽度
			$('#p').progressbar('resize', 350);   // 更改进度条到新的宽度
	
	getValue
		* 获取到进度条的进度值
	
	setValue
		* 给进度条设置一个进度值
	

	
