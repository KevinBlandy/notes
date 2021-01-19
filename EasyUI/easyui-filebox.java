------------------------
EasyUI-filebox			|
------------------------
	# FileBox(文件框)组件在表单当中表示一个文件上传的字段。它扩展自 textbox (文本框)，大部分的属性、事件和方法都继承自文本框。但是由于浏览器的安全问题，其中的某些方法（如："setValue"）则不能用于 filebox 组件。
	# 继承关系
		textbox 

------------------------
EasyUI-filebox		属性|
------------------------
	'属性扩展自 textbox，以下是新增或重写的文件框属性。'
	buttonText
		* 文本框上附加的摁钮显示文本
	
	buttonIcon
		* 在文本框上附加的摁钮显示图标
	
	buttonAlign
		* 附件摁钮位置,可用值:"left", "right"。
	
	accept
		* 指定接受的文件类型
	
	multiple
		* 指定是否可以选择多个文件
	
	separator
		* 指定多个文件名称之间的分隔符

------------------------
EasyUI-filebox		事件|
------------------------
	'事件扩展自 textbox。'

------------------------
EasyUI-filebox		方法|
------------------------
	'方法扩展自 textbox。'

------------------------
EasyUI-filebox		实战|
------------------------

	# 解决Easyui获取 File 对象的问题
		* 文件框组件
			<input
				accept="image/gif,image/jpeg,image/png"
				class="easyui-filebox"
				data-options="
					onChange:fileUpdload
				"/>
		
		* onChange 的上传事件
			function fileUpdload() {
				//该值是一个字符串,仅仅获取的是本地文件的路径值,并不能获取到file对象
				var value = $(this).filebox('getValue');
				TD.println(value);
				
				//该对象就是一个file对象的数组,因为file有可能是多选
				var files = $('#filebox_file_id_1')[0].files;
				TD.println(files);
			}
		
		* filebox_file_id_1 这个ID 是easyui 自己创建的input 标签. 这里面是真正保存文件的地方.
		* 如果创建了多个 filebox  那么后面的ID 就是filebox_file_id_2,filebox_file_id_3.
	

