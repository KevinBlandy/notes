--------------------
passwordbox			|
--------------------
	# 继承
		textbox

--------------------
passwordbox-属性	|
--------------------
	passwordChar 
		* string 在文本框中显示的密码字符。 %u25CF 
	checkInterval 
		* number 在间隔的时间后检查并转换输入的字符为密码字符。 200 
	lastDelay 
		* number 在延迟的时间后转换最后输入的字符为密码字符。 500 
	revealed 
		* boolean 定义是否默认显示真实密码。 false 
	showEye 
		* boolean 定义是否显示眼睛图标。 true 

--------------------
passwordbox-事件	|
--------------------
	# 完全继承 textbox
--------------------
passwordbox-方法	|
--------------------
	方法扩展自 textbox，以下是新增的文本框方法。

	options 
		* none 返回属性对象。 
	showPassword 
		* none 显示密码。
	hidePassword 
		* none 隐藏密码。 

