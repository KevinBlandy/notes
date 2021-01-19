----------------------------
异步文件上传				|
----------------------------
	# 市场上很多异步上传的插件,这里讲几个

----------------------------
jquery.uploadify.min.js		|
----------------------------
	#  该组件文件夹
		jquery.uploadify.min.js			//主js
		uploadify.css					//样式
		uploadify.swf					//上传动画
		uploadify-cancel.png			//退出图片

	<tr>
		<th>图片</th>
		<td><input name="pic" type="file" id="createUploadImage" accept="image/*"/></td>
	</tr>

	//使用Jquery 对上传框进行初始化
	$('#createUploadImage').uploadify({
		uploader : '/upload',  
		fileObjName : 'fileName',			//上传文件的名称.也就是SpringMVC中 Muti....  
		fileSizeLimit : '0',  
		method:'POST',
		fileSizeLimit:'5MB',
		swf:'/static/upload/uploadify.swf',			//上传动画
		fileTypeExts:'*.jpg;*.png;*.png;*.gif',		//限制类型
		onUploadSuccess : function(file, data, response){
			var json = JSON.parse(data);			//注意返回数据类型要经过JSON转换,哪怕你本身返回的就是JSON
			$('#imagePath').val(json.data);
			$('#prop_sucreate_img').attr('src',json.data);
		},
		onUploadStart : function(file) {
			//动态传递额外提交的参数
			$("#createUploadImage").uploadify("settings", "formData",{'propgruopId':currentPropgrouId});
		},
		onUploadError : function(file, errorCode, errorMsg, errorString){
			$.messager.alert('警告','上传异常,请尝试刷新页面后重试','error',function(){});
		}
	});

	//后台程序
	public ResponseEntity<Message<Void>> uplod(
											@RequestParam("fileName") MultipartFile uploadFile,
											HttpServletRequest request){
		//逻辑
		return null;
	}