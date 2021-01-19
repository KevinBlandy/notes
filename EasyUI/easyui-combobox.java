-------------------------
EasyUI-ComboBox			 |
-------------------------
	# 下拉框,呵呵
	# 依赖关系
		combo
	# 支持标签
		<select class="easyui-combobox"></select>
		<input class="easyui-combobox"/>

	# JSON格式
		
		{
			id:'123',		//值(主键)
			text:'Kev',		//显示的文本
			selected:true	//是否选中
		}
-------------------------
EasyUI-ComboBox属性		 |
-------------------------
	'下拉列表框属性扩展自combo(自定义下拉框)，下拉列表框新增的属性如下：'

	url
		* 指定远程数据的请求地址
	
	valueField
		* 响应JSON中的,value字段 
	
	textField
		* 响应JSON中的,text字段 .就是显示的文本
	
	method
		* 请求方式,默认为'POST'
	
	mode
		* 值为:'remote'
		* 当定义了这个属性后,用户执行了任何的输入.都会被传递到后台.url就是当前的url,参数名为:q
		* 获取到响应后,会重新的加载这个列表
		* 其实就是模糊匹配
		* 值也可以是'local',默认值就是它.表示数据是从本地获取的.不需要URL属性

	
	groupField
		* 指定分组的字段名称
	
	groupFormatter
		* 返回格式化后的分组数据文本
		* demo
			$('#cc').combobox({
				groupFormatter: function(group){
					return '<span style="color:red">' + group + '</span>';
				}
			});

	method
		* 定义请求远程数据的请求方法,默认值为:'POST'
	
	data
		* 设置下拉框的数据,参数是一个json对象数组
		* demo
			<input class="easyui-combobox" data-options="
				valueField: 'label',
				textField: 'value',
				data: [{
					label: 'java',
					value: 'Java'
				},{
					label: 'perl',
					value: 'Perl'
				},{
					label: 'ruby',
					value: 'Ruby'
				}]" />
	
	queryParams
		* 请求远程服务器时额外提交的数据
			$('#cc').combobox({
				url : "127.0.0.1/xxx",
				queryParams: {
					"age" : 25,
					"order" : "desc"
				}
			});

	showItemIcon
		* 该值如果为 true .显示选中项的图标。
	
	groupPosition
		* 定位分组选项。可用值有:'static'和'sticky'。
		* 当设置为“sticky”时会将分组选项标签固顶在下拉栏中
	
	filter
		* 定义当'mode'设置为'local'时如何过滤本地数据，函数有2个参数
			q	//用户输入的文本。
			row	//列表行数据。
		* 返回true的时候允许行显示
		* demo
			$('#cc').combobox({
				filter: function(q, row){
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) == 0;
				}
			});
	
	formatter
		* 定义如何进行渲染,具备一个参数:row
			$('#cc').combobox({
				formatter: function(row){
					var opts = $(this).combobox('options');
					return row[opts.textField];
				}
			});
	
	loader
		* 定义了如何从远程服务器加载数据,返回 false 可以阻止事件
		* loader : function(param,success,error){}
			//param:传递到远程服务器的参数对象
			//在检索数据成功的时候调用该回调函数
			//在检索数据失败的时候调用该回调函数。
	
	loadFilter
		* 返回过滤后的数据并显示。
		* function(data){}
	

-------------------------
EasyUI-ComboBox事件		 |
-------------------------
	'下拉列表框事件继承自combo(自定义下拉框)，下拉列表框新增的事件如下：'

	onBeforeLoad
		* 在请求加载数据之前触发，返回false取消该加载动作。 
		* 具备一个参数
			$('#cc').combobox({
				onBeforeLoad: function(param){
					param.id = 2;
					param.language = 'js';
				}
			});
	
	onLoadSuccess
		* 在加载远程数据成功时触发,没有参数
	
	onLoadError
		* 在加载远程数据异常时触发,没有参数
	
	onChange
		* 在参数发生变化的时候触发.具备两个参数.第一个是新值,第二个是旧值
	
	onSelect
		* 在用户选择列表的时候触发.具备一个参数:record,该参数就是,用户单机的那个选项对象
	
	onUnselect
		* 在用户取消列表的时候触发.具备一个参数:record,该参数就是,用户单机的那个选项对象
	


-------------------------
EasyUI-ComboBox方法		 |
-------------------------
	'下拉列表框扩展自combo(自定义下拉框)，下拉列表框新增或重写的方法如下：'

	options
		* 返回属性对象
	
	getData
		* 返回加载的数据
	
	loadData
		* 读取本地数据,传递数据对象
	
	reload
		* 重新加载数据.参数就是URL,忽略的话.使用默认的URL
		demo
			$('#cc').combobox('reload');      // 使用旧的URL重新载入列表数据
			$('#cc').combobox('reload','get_data.php');  // 使用新的URL重新载入列表
	
	setValues
		* 设置下拉框的值
		* demo
			$('#cc').combobox('setValue', '001');
	
	clear
		* 清除下拉框的值
	
	select
		* 选择指定的选项
		* 有一个参数:value ,就是选项的的值
	
	unselect
		* 取消选择指定的选项


	reload
		* 重新载入数据.有一个url,参数.可以省略,如果不写,就是用旧的URL,如果传递了新的参数,就用新的URL
		$('#cc').combobox('reload','get_data/load');  // 使用新的URL重新载入列表数据
	
	getData
		* 获取当前combobox的所有数据
		* 是一个数组对象,里面是你下拉框的所有数据
	

	
	
-------------------------
EasyUI-ComboBox案例		 |
-------------------------
	联动案例
		<input id="cc1" class="easyui-combobox" data-options="    
			valueField: 'id',    
			textField: 'text',    
			url: 'get_data1.php',    
			onSelect: function(rec){    
				//获取点击节点的ID
				var url = 'get_data2.php?id='+rec.id;    
				//准备URL,提交的参数值为这个ID.然后把这个URL设置到指定的input框,执行reload方法.那么指定input框的的数据,就是来源当前点击的节点
				$('#cc2').combobox('reload', url);    
			}" />   
		<input id="cc2" class="easyui-combobox" data-options="valueField:'id',textField:'text'" />  
