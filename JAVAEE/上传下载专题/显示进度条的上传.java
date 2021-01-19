---------------------------------
Jquery-异步						 |
---------------------------------
	//获取文件框的文件集合
	var files = $('#file')[0].files;
	//创建FormData对象
	var formData = new FormData();
	//添加第一个文件到FormData
	formData.append("file",files[0]);
	//添加普通属性
	formData.append("name","KevinBlandy");
	$.ajax({
		url : '${ctxPath}/test/upload',
		type : 'POST',
		data : formData,
		xhr: function(){
			//获取到原生的 XMLHttpRequest 对象
			myXhr = $.ajaxSettings.xhr();
			//确定 异步上传对象带上传属性
			if(myXhr.upload){			
				//监听上传属性的上传事件,每次上传事件都会执行 progressHandlingFunction
				myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
				//myXhr.upload.onprogress = function(){}			也可以
			}
			//返回给 $.ajax 使用
			return myXhr;
		},
		processData : false,
		contentType : false,
		success : function(response) {
			TD.showAlert(response.message);
		},
		error : function(response) {

		},
	});

	function progressHandlingFunction(event) {
		event.total;		//获取上传文件的总大小
		event.loaded;		//获取已经上传的文件大小
		//获取进度的百分比值
		var percent  = (event.loaded / event.total) * 100;
		//四舍五入保留两位小数
		percent = percent.toFixed(2);
	}


---------------------------------
原生-异步						 |
---------------------------------
	//获取文件筐的文件集合
	var files = document.getElementById("file").files;
	//创建FormData对象
	var formData = new FormData();
	//添加第一个文件到FormData
	formData.append("file",files[0]);
	//添加普通数据
	formData.append("name","KevinBlandy");
	//创建异步对象
	var xhr = createXMLHttpRequest();
	//打开连接
	xhr.open('POST','${ctxPath}/test/upload',true);
	//监听上传事件
	if(xhr.upload){
		//监听上传属性的上传事件,每次上传事件都会执行 progressHandlingFunction
		xhr.upload.addEventListener('progress',progressHandlingFunction, false);
		//xhr.upload.onprogress = function(){}			也可以
	}
	//执行上传
	xhr.send(formData);
	
	//上传监听
	function progressHandlingFunction(event) {
		event.total;		//获取上传文件的总大小
		event.loaded;		//获取已经上传的文件大小
		//获取进度的百分比值
		var percent  = (event.loaded / event.total) * 100;
		//四舍五入保留两位小数
		percent = percent.toFixed(2);
	}
        