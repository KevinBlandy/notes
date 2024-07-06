---------------------
FormData
---------------------
	# 表示表单数据的键值对 key/value 的构造方式（multipart/form-data）。
	
	# 构造函数
		new FormData(form)
			form
				* 可选，<form> 表单元素，FormData 对象会自动将 form 中的表单值也包含进去，包括文件内容也会被编码之后包含进去。

---------------------
this
---------------------

	append(key, value, filename);
		* 添加一个数据，如果已有同名属性，则会添加到集合中
			key
				* 表单名称
			value
				* 表单值，可以是 USVString 或 Blob (包括子类型，如 File)。
			filename 
				* 可以省略, 表示传给服务器的文件名称

		* 当一个 Blob 或 File 被作为第二个参数的时候, Blob 对象的默认文件名是 "blob", File 对象的默认文件名是该文件的名称。
		* blob 对象可以设置ContentType
			const json = JSON.stringify({'name': 'KeviniBlandy'});
			formData.append('json', new Blob([json], {type: "application/json"}));

	delete()
	entries()
	get()
	getAll()
	has()
	keys()
	set()
	values()

---------------------
event
---------------------

---------------------
static
---------------------

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