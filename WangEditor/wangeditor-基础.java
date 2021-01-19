---------------------------
wangeditor-基础				|
---------------------------
	# 下载地址
		http://www.wangeditor.com/
		http://www.kancloud.cn/wangfupeng/wangeditor2/113961
	
	# 必须引入
		<!--引入wangEditor.css-->
		<link rel="stylesheet" type="text/css" href="${ctxPath}/static/editor/css/wangEditor.min.css">

		……省略其他内容……
		<!--引入jquery和wangEditor.js-->   <!--注意：javascript必须放在body最后，否则可能会出现问题-->
		<script type="text/javascript" src="../dist/js/lib/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="${ctxPath}/static/editor/js/wangEditor.min.js"></script>
	
---------------------------
wangeditor-创建编辑器		|
---------------------------
	# 可以使用 div/textarea1 作为编辑器的dom元素
	# 使用ID值进行初始化
		var editor = new wangEditor('chat-editor');
		editor.create();
	
	# 启用/禁用编辑器

		editor.disable();

		editor.enable();

---------------------------
wangeditor-去除不需要的组件	|
---------------------------
	# 在初始化(create())之前,进行过滤操作
		editor.config.menus = $.map(wangEditor.config.menus, function(item, key) {
			if (item === 'fullscreen') {
				return null;
			}
			if (item === 'table'){
				return null;
			}
			if(item === 'location'){
				return null;
			}
			if(item === 'source'){
				return null;
			}
			return item;
		});

---------------------------
wangeditor-图片上传相关		|
---------------------------
	# 在初始化(create())之前,进行操作
		editor.config.hideLinkImg = true;
			* 禁止使用'插入网络图片'
		editor.config.uploadImgUrl = "${ctxPath}/upload";
			* 后台地址
		editor.config.uploadImgFileName = 'file';
			* 上传文件的名称
	
---------------------------
wangeditor-内容操作相关		|
---------------------------
	# 初始化内内容
		* 可直接将要初始化的html内容，放在将要用来创建编辑器的div或者textarea标签里面
		* 通过JS初始化内容
			 editor.$txt.html('<p>要初始化的内容</p>');
	
	# 获取内容
        var html = editor.$txt.html();
			* 获取编辑器区域完整html代码
			* encodeURIComponent(editor.$txt.html());

        var text = editor.$txt.text();
			* 获取编辑器纯文本内容

        var formatText = editor.$txt.formatText();
			* 获取格式化后的纯文本
		
	# 追加内容
		 editor.$txt.append('<p>新追加的内容</p>');
		
	# 清空内容
        editor.$txt.html('<p><br></p>');
			* 不能传入空字符串，而必须传入如上参数
	

			
	
