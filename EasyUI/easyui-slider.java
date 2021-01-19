-----------------------
EasyUI-slider			|
-----------------------
	# 滑动条允许用户从一个有限的范围内选择一个数值。当滑块控件沿着轨道移动的时候，将会显示一个提示来表示当前值。用户可以通过设置其属性自定义滑块。
	# 继承关系
		draggable

-----------------------
EasyUI-slider	属性	|
-----------------------
	width
		* 滑动条的宽度
	
	height
		* 滑动条的高度
	
	mode
		* 声明滑动条的滚动类型
		* 可用值有：'h'(水平)、'v'(垂直)。
	
	reversed
		* 如果该值为 true
		* 则最大值也最小值会交换顺序
	
	showTip
		* 实现显示值信息提示
	
	disabled
		* 是否禁用滑动条
	
	range
		* 定义是否显示滑块的范围
		* 默认值为 false
	
	value
		* 默认值
	
	min
		* 允许最小值
	
	max
		* 允许的最大值
	
	step
		* 值增加或者减少
	
	rule
		* 显示标签旁边的滑块，'|' — 只显示一行。
		* 参数是一个 array
	
	tipFormatter
		* 该函数用于格式化滑动条。返回的字符串值将显示提示。
		* 参数是一个 function
	
	converter
		* 该转换器函数允许用户决定如何将一个值转换为进度条位置或进度条位置值。
		* demo
			$('#ss').slider({
				converter:{
					toPosition:function(value, size){
						var opts = $(this).slider('options');
						return (value-opts.min)/(opts.max-opts.min)*size;
					},
					toValue:function(pos, size){
						var opts = $(this).slider('options');
						return opts.min + (opts.max-opts.min)*(pos/size);
					}
				}
			});
	


-----------------------
EasyUI-slider	事件	|
-----------------------
	onChange
		* 在字段值更改的时候触发
		* 有两个参数:newValue, oldValue
	
	onSlideStart
		* 在开始拖动滑动条的时候触发
		* 参数为 :value
	
	onSlideEnd
		* 在结束拖拽滑动条的时候触发
		* 参数为 :value
	onComplete
		* 在滑块值被用户改变的时候触发，无论是拖动还是点击滑块
		* 参数为 :value
	

-----------------------
EasyUI-slider	方法	|
-----------------------
	options
		* 返回滑动条属性
	
	destroy
		* 销毁滑动条对象
	
	resize
		* 设置滑动条大小
		* 参数是一个对象{width:x,height:y}
	
	getValue
		* 获取滑动条的值
	
	getValues
		* 获取滑动条的值数组
	
	setValue
		* 设置滑动条的值
	
	setValues
		* 设置滑动条值数组
	
	clear
		* 清楚滑动条的值
	
	reset
		* 重置滑动条的值
	
	enable
		* 启用滑动条控件
	
	disable
		* 禁用滑动条控件
