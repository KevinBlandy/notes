----------------------------
EasyUI-datetimebox			|
----------------------------
	# 日期时间输入框
	# 它跟 databox多了个微调的东西
	# 扩展自$.fn.datebox.defaults，使用$.fn.datetimebox.defaults重写默认值对象。
	# 继承关系
		datebox 
		timespinner
 
----------------------------
EasyUI-datetimebox属性		|
----------------------------
	'日期时间输入框扩展自datebox(日期输入框)，日期时间输入框新增的属性如下：'
	currentText
		* 显示'当前时间'的摁钮的文本
	
	closeText
		* 显示'关闭'的摁钮文本
	
	okText
		* 显示'OK'的摁钮文本
	
	spinnerWidth
		* 定义datetimebox组件嵌入的时间微调器的宽度。
		* 参数是一个数值,默认值: 100%
	
	showSeconds
		* 定义是否显示秒钟信息。
	
	timeSeparator
		* 定义在小时、分钟和秒之间的时间分割字符。
	

----------------------------
EasyUI-datetimebox事件		|
----------------------------
	

----------------------------
EasyUI-datetimebox方法		|
----------------------------
	'日期时间输入框的方法扩展自datebox(日期输入框)，日期时间输入框重写的方法如下：'
	options
		* 返回参数对象
	
	spinner
		* 返回时间微调器对象
	
	setValue
		* 具备一个参数.就是设置的字符串值

	cloneFrom
		* 克隆一个datetimebox控件。
		* 具备一个参数.就是一个 form表单对象
		* demo
			<input id="from" class="easyui-datetimebox">
			// 克隆一个存在的datebox组件
			$('.dt').datetimebox('cloneFrom', '#from');

		
		
