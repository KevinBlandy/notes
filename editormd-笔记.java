
# 初始化
	//初始化编辑器
			editor = editormd({
				id:id,
				path:'/static/editormd/lib/',
				height:500,
				width:'100%',
				placeholder:'',
				saveHTMLToTextarea:true,
				//预览延迟,毫秒
				delay : 300,
				
				//支持
			    tex:true,
			    flowChart:true,
			    sequenceDiagram:true,
		        emoji:true,
		        taskList:false,
		        tocm:false,
		        
		        autoFocus:false,
		        atLink:false,
		        watch:true,
				htmlDecode:'style,script,iframe|on*',
		        imageUpload:true,
		        imageFormats: ['jpg,png,gif'],
		        imageUploadURL: '/upload/editor',
		        
		        //事件
		        onchange:function(){
		        }
			});
			
			let editorNode = document.querySelector('#' + id);

			/**
			 * 图片黏贴上传
			 */
		    editorNode.addEventListener('paste',function(event){
				let clipboardData = event.clipboardData;
		        if(clipboardData){
		            let items = clipboardData.items;
		            if(items && items.length > 0){
		                for(let item of items){
		                	if(item.type.startsWith("image/")){
		                		let file = item.getAsFile();
			                	if(!file){
			                		//TODO，文件读取失败，仅仅支持部分截图复制后的图片粘贴上传
			                		return;
			                	}
			                	//TODO，上传
		                	}
		                }
		            }
		        }
		    });

			/**
			 * 图片拖拽上传
			 */
			editorNode.addEventListener('dragenter',function(event){
				event.preventDefault();
			});
			editorNode.addEventListener('dragover',function(event){
				event.preventDefault();
			});
			editorNode.addEventListener('drop',function(event){
				event.preventDefault();
				let files = event.dataTransfer.files;
				if(files){
					if(files.length > 5){
						//TODO，拖拽数量大于5
						return;
					}
					if(Array.from(files).some(file => !file.type.startsWith('image/'))){
						//TODO，一个或者多个文件非图片数据
						return;
					}
					//TODO，上传
				}
			});
			editorNode.addEventListener('dragend',function(event){
				event.preventDefault();
			});
			
			return editor;
		}
			
		function post(){
			
			let formData = new FormData();
			
			//let htmlContent = editor.getHTML(); 
			let htmlContent = $('.editormd-preview')[0].innerHTML;
			let markdwonContent = editor.getMarkdown();
			
			formData.set('htmlContent',htmlContent);
			formData.set('markdwonContent',markdwonContent);
			
			fetch('/temp/post/create',{
				method:'POST',
				body:formData
			}).then(function(response){
				if(response.ok){
					response.json().then(function(rep){
						console.log(rep);
						window.location.href = '/temp/post/' + rep.data;
					});
				}
			}).catch(function(e){
				throw e;
			});
		}