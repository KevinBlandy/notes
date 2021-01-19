<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="/static/tui-editor/lib/codemirror/lib/codemirror.css">
		<link rel="stylesheet" href="/static/tui-editor/lib/highlightjs/styles/github.css">
		<link rel="stylesheet" href="/static/tui-editor/dist/tui-editor.css">
	    <link rel="stylesheet" href="/static/tui-editor/dist/tui-editor-contents.css">
	    <link rel="stylesheet" href="/static/tui-editor/lib/tui-color-picker/dist/tui-color-picker.css">
	    <link rel="stylesheet" href="/static/tui-editor/lib/tui-chart/dist/tui-chart.css">
		
	</head>
	<body>
		<div id="editor"></div>
		<button onclick="post();" style="width: 200px;height: 50px">
			提交
		</button>
	</body>
	<script src="/static/tui-editor/lib/jquery/dist/jquery.js"></script>
    <script src="/static/tui-editor/lib/markdown-it/dist/markdown-it.js"></script>
    <script src="/static/tui-editor/lib/to-mark/dist/to-mark.js"></script>
    <script src="/static/tui-editor/lib/tui-code-snippet/dist/tui-code-snippet.js"></script>
    <script src="/static/tui-editor/lib/tui-color-picker/dist/tui-color-picker.js"></script>
    <script src="/static/tui-editor/lib/codemirror/lib/codemirror.js"></script>
    <script src="/static/tui-editor/lib/highlightjs/highlight.pack.js"></script>
    <script src="/static/tui-editor/lib/squire-rte/build/squire-raw.js"></script>
    <script src="/static/tui-editor/lib/plantuml-encoder/dist/plantuml-encoder.js"></script>
    <script src="/static/tui-editor/lib/raphael/raphael.js"></script>
    <script src="/static/tui-editor/lib/tui-chart/dist/tui-chart.js"></script>
    <script src="/static/tui-editor/dist/tui-editor-Editor-all.js"></script>
    <script type="text/javascript">

	   let editor = new tui.Editor({
		   	el:document.querySelector('#editor'),
		    viewer: true,
	    	//编辑器的类型,markdown,wysiwyg
	        initialEditType: 'markdown',
	        //初始职
	        //initialValue:content,
	        //markdown的预览样式,tab, vertical
	        previewStyle: 'vertical',
			//初始化高度,值还可以是:auto	        
	        height: '500px',
			//最低高度
			minHeight: '100px',
	        //中文
	        language:'zh_CN',
	        //是否启用键盘快捷键
	        useCommandShortcut:true,
	        //是否使用默认的html过滤器(xss安全)
	        useDefaultHTMLSanitizer:false,
	        //代码块支持的语言，默认是highlight.js支持的全部
	        //codeBlockLanguages:[]
	        //把主机名发送给谷歌服务器解析
	        usageStatistics:true,
	        //工具栏定义，默认加载所有
	        //toolbarItems: [],
	        //隐藏右下角模式切换栏
	        hideModeSwitch:false,
	        //扩展加载
	        exts: [
	        	'scrollSync',			//预览滚动同步
	        	'colorSyntax',			//文字颜色
	        	'table',				//表格
	        	'mark',					//未知
	        	'uml',					//uml开启
	        	'chart'					//统计图
	        	/** 统计图 还可以设置最小/大，宽/高等信息
				{						
					name: 'chart',
					minWidth: 100,
					maxWidth: 600,
					minHeight: 100,
					maxHeight: 300
				}
	        	**/
			],
	        //事件
	        events:{
	        	load(editor){
	        		console.log('加载完成');
	        	},
	        	change(editor){
	        		//console.log('内容发生变化');
	        	},
	        	stateChange(editor){
	        		//console.log('光标位置改变');
	        	},
	        	focus(editor){
	        		//console.log('编辑器获得焦点');
	        	},
	        	blur(editor){
	        		//console.log('编辑器失去焦点');
	        	}
	        },
	        //钩子函数
	        hooks:{
	        	//在预览事件发生之前,该参数为预览的html
	        	previewBeforeHook(p){
	        		//console.log(p);
	        	},
	        	//添加图片
	        	addImageBlobHook(file,call,source){
	        		console.log(file,call,source);
	        		call('https://springcloud-community.oss-cn-beijing.aliyuncs.com/2018/0730/d782731b3e144c57889709497a7c8766.png');
	        		/*
	        			file	文件对象
	        			call	用于回写地址的回调函数
	        			source	表示事件来源,'paste'(粘贴), 'drop'(拖拽), 'ui'(ui组件)
	        		*/
	        	}
	        }
	    });
	   /*
	   		## API
	   		
	   		//获取html
			getHtml();
	   		//获取markdown正文
			getMarkdown();
			//未知，感觉是和getMarkdown() 一样的
			getValue()
	   		//设置内容，会覆盖掉所有
			setValue();
	   		//在光标位置插入内容
			insertText();
	   		//显示编辑器
			show();
	   		//隐藏编辑器
	   		hide();
	   		
	   		## 文档地址
	   		//文档主页
			https://nhnent.github.io/tui.editor/api/latest/index.html
	   		//添加fontawesome摁钮
	   		https://nhnent.github.io/tui.editor/api/latest/tutorial-example16-toolbar-add-button-fontawesome.html
	   */
	   
		editor._getHtml = function(){
			return $('.te-preview')[0].innerHTML;
		}
	   
	   function post(){

				let formData = new FormData();
				
				let htmlContent = editor._getHtml();
				let markdownContent = editor.getMarkdown();
				
				formData.set('htmlContent',htmlContent);
				formData.set('markdownContent',markdownContent);
				
				fetch('/temp/tui-editor/post',{
					method:'POST',
					body:formData
				}).then(function(response){
					if(response.ok){
						response.json().then(function(rep){
							console.log(rep);
							window.location.href = '/temp/tui-editor/' + rep.data;
						});
					}
				}).catch(function(e){
					throw e;
				});
	   		
	   }
    </script>
</html>

1,全屏编辑没
2,鼠标指向统计图会有动态的提示
	* 生成动态图的时候,生成了一个id,js根据该id触发的相关事件
3,预览窗口的开关没
4,预览html的生成延迟太长
5,初始化时,<div></div>节点标签里面不支持初始化markdown内容
6,生成的uml图不是https的
