----------------------------
EasyUI-validate				|
----------------------------
	# easyui-validatebox
		* 一般标识在input框中
	
	# 是一个验证框架
	# 依赖关系
		tooltip
	


	
----------------------------
EasyUI-validate属性			|
----------------------------
	required
		* 是否为必填属性

	validType
		* 指定验证规则
			<!-- 必须是URL -->
			<input class="easyui-validatebox" data-options="required:true,validType:'url'">
			<!-- 必须是邮箱,必须是0-20个字符 -->
			<input class="easyui-validatebox" data-options="required:true,validType:['email','length[0,20]']">
	
	delay
		* 在输入后延迟指定的时间后才进行校验
	

	missingMessage
		* 当文本框未填写数据时的信息
	
	invalidMessage
		* 当文本框的内容被验证为无效时出现的提示。

	tipPosition
		* 当文本框内容无效的时候,提示信息出现的位置
		* 有效的值有：'left','right'
	
	deltaX
		* 提示框在水平方向的位移幅度
	
	novalidate
		* 如果该值为 true,则关闭验证功能
	
	editable
		* 如果该值为 true,用户可以在输入框中输入数据
	
	disabled
		* 如果该值为 true,关闭该字段的验证.并且表单提交的时候不会提交该字段的参数
	
	readonly
		* 如果该值为 true,则被标识的字段.是只读属性
	
	validateOnCreate
		* 如果该值为 true,则创建该字段就进行验证
	
	validateOnBlur
		* 如果该值为 true,则在当前字段失去焦点的时候进行验证
	

	
	

----------------------------
EasyUI-validate事件			|
----------------------------
	onBeforeValidate
		* 在验证一个字段之前触发
	
	onValidate
		* 在验证一个字段的时候触发

----------------------------
EasyUI-validate方法			|
----------------------------
	options
		* 返回属性对象
	
	destroy
		* 移除并且销毁组键
	
	validate
		* 执行验证文本框,内容是否有效
	
	isValid
		* 调用上面的方法,并且返回验证结果.true/false
	
	enableValidation
		* 启用验证
	
	disableValidation
		* 禁用验证
	
	resetValidation
		* 重置验证
	
	enable
		* 启用该组键
	
	disable
		* 禁用该组键
	
	readonly
		* 设置只读模式
		* 具备一个 boolean 形的参数,来控制是否只读.如果不写则默认为 true,也就是只读属性
	




-----------------------------
EasyUI-自己定义校验规则		 |
-----------------------------
	
	<input class="easyui-validatebox" data-options="validType:'xxxx[5]'">  
		* validType的值为,自定义的校验规则名称,后面的中括号是一个数组,可以传递N个参数

		$.extend($.fn.validatebox.defaults.rules, {    
			//自定义的校验规则名称
			xxxx: {    
				//校验方法
				validator: function(value, param){    
					/*
						value是表单的参数,param是自己传递的参数,在这个方法里面自己实现校验规则
						要注意的时候,param是一个数组.可以有多个参数的
					*/
					return value.length >= param[0];    
				},    
				//如果校验失败的提示信息,{0}.是一个格式占位符.执行提示的时候会替换成param中对应下标的参数
				message: 'Please enter at least {0} characters.'   
			},
			//再来一个自定义的校验规则
			zzzz: {
				validator: 	function(value,param){
					return true;
				}
				message: '自定义的提示消息';
			}
		});  