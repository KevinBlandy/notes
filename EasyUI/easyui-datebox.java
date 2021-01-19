---------------------------
EasyUI-datebox				|
---------------------------
	# 扩展自$.fn.combo.defaults。使用$.fn.datebox.defaults重写默认值对象。
	# 继承关系
		combo 
		calendar 


---------------------------
EasyUI-datebox属性			|
---------------------------
	panelWidth
		* 下拉日历面板宽度
	
	panelHeight
		* 下拉日历面板高度
	
	currentText
		* 显示当天按钮,参数是字符串类型.作为文本显示在摁钮上
	
	closeText
		* 显示关闭摁钮,参数是字符串类型.作为文本显示在摁钮上
	
	disabled
		* 该属性为 true 的时候,禁用该字段
	
	buttons
		* 在日历下面显示一堆摁钮
		* 参数是一个摁钮数组
		* demo
			var buttons = $.extend([], $.fn.datebox.defaults.buttons);
				buttons.splice(1, 0, {
				text: 'MyBtn',
				handler: function(target){
					alert('click MyBtn');
				}
			});
			$('#dd').datebox({
				buttons: buttons
			});
	
	sharedCalendar
		* 将一个日历控件共享给多个datebox控件使用。
		* 具备两个参数
		* demo
			<input class="easyui-datebox" sharedCalendar="#sc">
			<input class="easyui-datebox" sharedCalendar="#sc">
			<div id="sc" class="easyui-calendar"></div>

	formatter
		* 该函数用于格式化日期
		* demo
			$.fn.datebox.defaults.formatter = function(date){
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				return m+'/'+d+'/'+y;
			}
	
	parser
		* 该函数用于解析一个日期字符串，它有一个'date'字符串参数并且会返回一个日期类型的值。下面的一个例子展示了如何重写默认的parser函数
		* demo
			$.fn.datebox.defaults.parser = function(s){
				var t = Date.parse(s);
				if (!isNaN(t)){
					return new Date(t);
				} else {
					return new Date();
				}
			}
	
	

---------------------------
EasyUI-datebox事件			|
---------------------------
	onSelect
		* 在用户选择了一个日期的时候触发。 
		* 具备一个参数.就是用户选择的日期
			$('#dd').datebox({
				onSelect: function(date){
					alert(date.getFullYear()+":"+(date.getMonth()+1)+":"+date.getDate());
				}
			});
	

---------------------------
EasyUI-datebox方法			|
---------------------------
	'方法扩展自 combo(自定义下拉框)，日期输入框重写的方法如下：'
	options
		* 返回属性对象
	
	calendar
		* 获取日历对象。下面的例子显示了如果获取日历对象并重新创建它。
		* demo
			// 获取日历对象
			var c = $('#dd').datebox('calendar');
			// 设置一周的第一天是星期几（0是周日，1是周一）
			c.calendar({
				firstDay: 1
			});
	
	setValue
		* 设置日历框的值
		* demo
			$('#dd').datebox('setValue', '6/1/2012');	// 设置日期输入框的值
			var v = $('#dd').datebox('getValue');		// 获取日期输入框的值
	
	cloneFrom
		* 克隆一个datebox控件。
		* demo
			<input id="from" class="easyui-datebox">
			// 克隆一个存在的datebox组件
			$('.dt').datebox('cloneFrom', '#from');



