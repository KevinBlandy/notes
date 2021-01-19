---------------------------------
ueditor-入门					 |
---------------------------------
	# 来自百度的神器
	# 官网
		http://ueditor.baidu.com/website/
	
	# maven  依赖
		<dependency>
		    <groupId>cn.songxinqiang</groupId>
		    <artifactId>com.baidu.ueditor</artifactId>
		    <version>1.1.2-offical</version>
		</dependency>


---------------------------------
ueditor-简单使用				 |
---------------------------------
	1,导入必要依赖
		 <!-- 配置文件 -->
	    <script type="text/javascript" src="${pageContext.request.contextPath }/static/ueditor/ueditor.config.js"></script>
	    <!-- 编辑器源码文件 -->
	    <script type="text/javascript" src="${pageContext.request.contextPath }/static/ueditor/ueditor.all.js"></script>
	2,maven添加依赖
		<dependency>
		    <groupId>cn.songxinqiang</groupId>
		    <artifactId>com.baidu.ueditor</artifactId>
		    <version>1.1.2-offical</version>
		</dependency>
	3,form表单添加编辑器实例
		<form id="submitform">
			<!-- name 对应表单项name属性 -->
			<script id="container" name="name" type="text/plain">
			</script>
		</form>
	4,实例化编辑器
		var ue = UE.getEditor('container',{
        	//初始化参数
        	 autoHeight: false
        });