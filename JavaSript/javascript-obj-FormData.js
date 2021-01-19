-----------------------
FormData				|
-----------------------
	# html5新东西
	# 可以实现异步的上传文件,对象
	# 创建
		var formData = new FormData();
			* 直接创建

		var formData = new FormData(document.getElementById('form'));
			* 通过已有的form表单进行创建
		
		var form =  document.getElementById("form");

		var formData = form.getFormData()
			* 通过已有的form表单进行创建

-----------------------
FormData	属性		|
-----------------------

-----------------------
FormData	方法		|
-----------------------
	append(key,value, filename);
		* 添加一个数据

		value
			* 可以是一个文件对象
		filename 
			* 可以省略, 表示传给服务器的文件名称
			* 当一个 Blob 或 File 被作为第二个参数的时候, Blob 对象的默认文件名是 "blob", File 对象的默认文件名是该文件的名称。
		
			* blob对象可以设置ContentType
				const json = JSON.stringify({'name': 'KeviniBlandy'});
				formData.append('json', new Blob([json], {type: "application/json"}));

	set(key,value);
		* 跟 append一样
		* 跟 append() 的区别是，当指定的 key 值存在时，append() 方法是将新增的添加的所以的键值对最后，而set()方法将会覆盖前面的设置的键值对。
	
	delete(key);
		* 删除指定的key
	
	get(key);
		* 返回第一个key对应的值

	getAll(key);
		* 返回所有key对应的值,返回的是一个数组
	
	has(key);
		* 判断是否有该值
	
	keys();
		* 没有参数,返回迭代器,是所有的key值
		*	for (var key in formData.keys()) {
			   console.log(key); 
			}

	values();
		* 同上,不过是values的迭代
	
	entries();
		* 有点像map的那个内部类,可以获取到key和value
		*	for(var pair in formData.entries()) {
				console.log(pair[0]+ ', '+ pair[1]); 
			}
-----------------------
FormData	事件		|
-----------------------


-----------------------
FormData	Demo		|
-----------------------
	
	# 使用 XMLHttpRequest 进行表单提交
		var xhr = createXMLHttpRequest();
		xhr.open("POST", "${ctxPath}/test/upload", true);
		xhr.send(formData);
		xhr.onreadystatechange = function()	{
			if(xhr.readyState == 4 && xhr.status == 200){
				var text = xhr.responseText;
				JSON.parse(text);
			}
		}
		
	# 整合Jquery异步上传示例
		<input id="file_upload" onchange="fileUpdload();" type="file"  accept="image/gif,image/jpeg,image/png"/>
		function fileUpdload() {
			//创建formData对象
			var form = new FormData();	
			//获取到选择文件对象,注意,files();方法返回是个数组,因为有可能是多个文件
			var file = $("#file_upload")[0].files[0];
			//添加文件到formData对象,第一个参数就是上传文件的name属性
			form.append("imgFile",file );
			$.ajax({
				url : '${pageContext.request.contextPath}/file/uploadFile',
				type : 'POST',
				data : form,
				processData : false,
				contentType : false,
				success : function(response) {

				},
				error : function(response) {

				}
			});
		}
	
	# 文件框的属性
		multiple="multiple"
			* 如果该属性存在,则表示允许多选
		accept="image/gif,image/jpeg,image/png"
			* 该值表示运行该文件框选择哪些文件

	# 文件的部分属性
		1, 获取文件框对象
			* 原生的方式获取
				var fileInput = document.getElementById("#file_upload");
			* JQ获取
				var fileInput = $("#file_upload")[0];
		
		2,从对象获取文件列表
			* 使用文件框对象的:files();方法获取
				var files = fileInput.files();		//注意,该 files 是个数组,因为文件框可能选择的多个文件

			* 属性
				lastModified
					* 最后一次修改的时间戳
				lastModifiedDate
					* 最后一次修改时间
				name
					* 文件名称
				size
					* 文件大小
				type
					* 文件类型
				webkitRelativePath
					* 相对路径
			
			* WEB显示本地图片
				var url = window.URL.createObjectURL(files[0]);		//在内存中根据文本创建了一个二进制对象
                $('#img').attr({'src':url});						//直接把这个二进制对象显示到img,要注意在显示之后,释放掉内存
				window.URL.revokeObjectURL(url);					//释放内存

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
		
		* filebox_file_id_1 这个ID 是easyui 自己创建的input 便签. 这里面是真正保存文件的地方.
		* 如果创建了多个 filebox  那么后面的ID 就是filebox_file_id_2,filebox_file_id_3.
	
		
