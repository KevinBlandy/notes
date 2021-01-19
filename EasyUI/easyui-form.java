----------------------------
EasyUI-form					|
----------------------------
	# 表单
	# 提交方式一共有三种.
		1,使用easyui-form 的 submit 方法提交
			//会触发: onsubmit 方法
		2,使用Jquery 选中 form 表单,调用dom方法 submit 提交
			//会触发: onsubmit 方法
		3,通过Jquery 的 $.ajax(); 获取到表单参数后进行.提交
			//不会触发: onsubmit 方法

----------------------------
EasyUI-form属性				|
----------------------------
	novalidate
		* 是否验证表单字段.
	
	iframe
		* 是否使用  iframe 方式提交表单
	
	ajax
		* 是否使用ajax方法提交表单
	
	queryParams
		* 当表单提交到后台后添加的参数
	
	url
		* 提交的地址

----------------------------
EasyUI-form事件				|
----------------------------
	onSubmit
		* 在提交之前触发.返回false可以阻止表单提交
		* 在这里一般会执行一些提交参数的校验
		* 参数:param

	onProgress
		* 在上传进度数据有效时触发。在“iframe”属性设置为true时该事件不会被触发。
		* 参数:percent
	
	success
		* 在表单提交成功后触发
		* 参数:data
	
	onBeforeLoad
		* 在请求加载数据之前触发,返回 false 可以阻止事件
		* 参数:param
	
	onLoadSuccess
		* 在表单数据加载完成后触发。
		* 参数:data
	
	onLoadError
		* 在表单数据加载错误的时候触发
	
	onChange
		* 在表单数据发生变化的时候触发
	
----------------------------
EasyUI-form方法				 |
-----------------------------
	submit
		* 执行提交操作，该选项的参数是一个对象，它包含以下属性：
			url			//请求的URL地址。
			onSubmit	//提交之前的回调函数。如果返回false,则终止表单
			success		//提交成功后的回调函数。这个返回的JSON数据,不是不会自动转换为JSON的.需要自己手动转换:
		* demo
			$.messager.progress();	// 开始提交则显示进度条
			$('#ff').form('submit', {
				url: '${pageContext.request.contextPath}/user',
				onSubmit: function(){
					var isValid = $(this).form('validate');
					if (!isValid){
						$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					}
					return isValid;						// 返回false终止表单提交
				},
				success: function(data){
					$.messager.progress('close');		// 如果提交成功则隐藏进度条
					var data = eval('(' + data + ')');  // 这种方式是不会把响应数据转换为JSON的.需要自己手动的去转换
					if (data.success){    
						alert(data.message)    
					}    
				}
			});


		

	load
		* 从远程地址读取参数,填充到表格中
		* 如果参数是一个字符串,则当作URL,去读取远程数据
		* 如果参数是一个对象.则作为本地的数据.载入表格

	validate
		* 调用此方法,表单中的所有校验选项都OK的话,返回 true,反之返回 false
		* 从上往下校验
	
	clear
		* 调用此方法,清空表单数据
	
	reset
		* 重置表单数据
	
	enableValidation
		* 启用验证
	
	disableValidation
		* 禁用验证
	
	resetValidation
		* 重置验证
	


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