------------------------
EasyUI-calendar			|
------------------------
	# 使用$.fn.calendar.defaults重写默认值对象。
	# 属性名称
		easyui-calendar	

------------------------
EasyUI-calendar	属性	|
------------------------
	width
		* 日历控件的宽度
	
	height
		* 日历空间的高度
	
	fit
		* 如果该值为 true,则日历控件会撑到父元素的大小
	
	border
		* 是否显示边框
	
	firstDay
		* 定义一周的第一天是星期几。0=星期日、1=星期一 等。
		* 具备一个数值型的参数:0/1
	
	weeks
		* 显示周列表内容
		* 参数是一个数组.
		* 默认值:['S','M','T','W','T','F','S']
	
	months
		* 显示月列表内容
		* 参数是一个数组
		* 默认值:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug','Sep', 'Oct', 'Nov', 'Dec']
	
	year
		* 年日历,下面的例子显示了如何使用指定的年份和月份创建一个日历。
		* 具备一个数值类型的参数
		* <div class="easyui-calendar" data-options="year:2012,month:6" />
	
	month
		* 月日历
		* 具备一个参数.就是月份.(从1月份开始)
	
	current
		* 具备一个参数. Date 对象
		* 设置当前的时间

	formatter
		* 日期格式化函数
			$('#cc').calendar({
				formatter: function(date){
					return date.getDate();
				}
			});
	
	styler
		* (date) 日历天的样式函数，返回行内样式或CSS样式表的Class名称。 
		* demo
			$('#cc').calendar({
				styler: function(date){
					if (date.getDay() == 1){
						return 'background-color:#ccc';
							// 函数可以返回预定义的css class和预定义的行内样式
							// return {class:'r1', style:{'color:#fff'}};	
						} else {
							return '';
						}
					}
				})
			
	validator
		* 验证器函数用于确定是否可以选择日历上的某一天，返回 false 将阻止选择当前
		* demo
			$('#cc').calendar({
				validator: function(date){
					if (date.getDay() == 1) {                                    
						return true;
					} else {                               
						return false;                                
					}
				}
			})
	

			

	



------------------------
EasyUI-calendar	事件	|
------------------------
	onSelect
		* 在选择一天事件的时候触发
		* demo
			$('#cc').calendar({
				onSelect: function(date){
					alert(date.getFullYear()+":"+(date.getMonth()+1)+":"+date.getDate());
				}
			});
	
	onChange
		* 在用户修改时间的触发
		* 具备两个参数:newDate, oldDate.也就是新的日期,和旧的日期
	

------------------------
EasyUI-calendar	方法	|
------------------------
	options
		* 返回参数对象
	
	resize
		* 调整日历大小
	
	moveTo
		* 移动日期到指定值
		* demo
			$('#cc').calendar('moveTo', new Date(2012, 6, 1));
	


