/**
 * 图片黏贴上传
 */
editorNode.addEventListener('paste',function(event){
	let clipboardData = event.clipboardData;
	if(clipboardData){
		let items = clipboardData.items;
		if(items){
			for(let item of items){
				//数据类型
				let type = item.type;
				if(type.startsWith('image/')){
					//文件对象
					let file = item.getAsFile();
					if(!file){
						//TODO 修改弹窗控件
						alert('文件读取失败,仅仅支持部分截图复制后的图片粘贴');
						return null;
					}
					//TODO 大小限制
					let formData = new FormData();
					formData.append('editormd-image-file',file);
					$.ajax({
						url:'/editor/upload',
						data:formData,
						type:'POST',
						processData:false,
						contentType:false,
						success:function(response){
							options.editor.insertValue("![](" + response.url + ")");
						},
						complete:function(){
							
						},
						error:function(error){
							
						}
					});
				}
			}
		}
	}
});


/**
 * 图片拖拽上传
 */
editorNode.addEventListener('dragenter',function(event){	//拖进
	event.preventDefault();
});
editorNode.addEventListener('dragover',function(event){	//拖来拖去
	event.preventDefault();
});
editorNode.addEventListener('drop',function(event){		//释放鼠标
	event.preventDefault();
	let files = event.dataTransfer.files;
	for(let file of files){
		if(file.type.startsWith('image/')){
			//必须是文件
			//TODO 大小限制
			let formData = new FormData();
			formData.append('editormd-image-file',file);
			$.ajax({
				url:'/editor/upload',
				data:formData,
				type:'POST',
				processData:false,
				contentType:false,
				success:function(response){
					options.editor.insertValue("![](" + response.url + ")");
				},
				complete:function(){
					
				},
				error:function(error){
					
				}
			});
		}
	}
});
editorNode.addEventListener('dragend',function(event){		//拖出
	event.preventDefault();
});