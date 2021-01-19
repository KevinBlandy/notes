------------------------
EasyUI-timespinner		|
------------------------
	# 时间微调组件的创建基于微调组件。它和数字微调类似，但是显示的时间值。时间微调组件允许用户点击组件右侧的小按钮来增加或减少时间。
	# 继承关系
		spinner

------------------------
EasyUI-timespinner	属性|
------------------------
	'该组件属性扩展自spinner(微调)，该组件新增的属性如下：'
	separator
		* 定义在小时分钟和秒之间的分隔符
	
	showSeconds
		* 是否显示秒钟信息
	
	highlight
		* 初始选中字段
		* 0=小时,1=分钟...
	
	formatter
		* 格式化日期函数，该函数接受date对象型参数并返回一个字符串值。
		* function (date){}
		* demo	
			//以下的示例代码展示了如何覆盖默认格式化器的方法。
			$.fn.timespinner.defaults.formatter = function(date){
				if (!date){return '';}
				var opts = $(this).timespinner('options');
				var tt = [formatN(date.getHours()), formatN(date.getMinutes())];
				if (opts.showSeconds){
					tt.push(formatN(date.getSeconds()));
				}
				return tt.join(opts.separator);
				
				function formatN(value){
					return (value < 10 ? '0' : '') + value;
				}
			}
	
	parser
		* 解析日期/时间字符串的函数，该函数接受date字符串类型的参数并返回一个date对象值
		* demo
			$.fn.timespinner.defaults.parser = function(s){
				var opts = $(this).timespinner('options');
				if (!s){return null;}
				var tt = s.split(opts.separator);
				return new Date(1900, 0, 0, 
				parseInt(tt[0],10)||0, parseInt(tt[1],10)||0, parseInt(tt[2],10)||0);
			}
	
	selections
		* 参数是一个array
		* 高亮选择部分的值，突出显示每一部分。例如：将字符从0点到2则高亮小时部分。
		* [[0,2],[3,5],[6,8]] 
	




------------------------
EasyUI-timespinner	事件|
------------------------
	'该组件事件完全继承自spinner(微调)。'

------------------------
EasyUI-timespinner	方法|
------------------------
	'该组件的方法扩展自spinner(微调)，该组件重写的方法如下：'

	options
		* 返回属性对象
	
	setValue
		* 设置时间微调组件的值
		* demo
			$('#ss').timespinner('setValue', '17:45');  // 设置时间微调组件的值
			var v = $('#ss').timespinner('getValue');  // 获取时间微调组件的值
			alert(v);
	
	getHours
		* 获当前小时数
	
	getMinutes
		* 获取当前分钟数
	
	getSeconds
		* 获取当前的秒数


	