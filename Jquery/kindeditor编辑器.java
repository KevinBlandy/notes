------------------------
1,富文本编辑器			|
------------------------
	1,导入相应的css和js文件
		<link href="${pageContext.request.contextPath }/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
	2,设置多行文本域,并且设置宽高,且隐藏
		<td>
			<textarea style="width:800px;height:300px;visibility:hidden;" name="desc"></textarea>
		</td>
	3,通过js来控制

		
		var itemAddEditor ;
		$(function(){
			/*	生成富文本编辑器
				createEditor : function(select){
					return KindEditor.create(select, TT.kingEditorParams);
				},

				1,select:选择器,选择一个多行文本
				2,TT.kingEditorParams:富文本编辑器相关的一些参数.主要是文件上传的参数

					kingEditorParams : {
						filePostName  : "uploadFile",
						uploadJson : '/rest/pic/upload',
						dir : "image"
					},
			*/
			itemAddEditor = TAOTAO.createEditor("#itemAddForm [name=desc]");
			TAOTAO.init({fun:function(node){
				TAOTAO.changeItemParam(node, "itemAddForm");
			}});
		});