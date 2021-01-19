----------------------------
面板						|
----------------------------
	# 依赖加载组件:element

----------------------------
折叠面板常规使用			|
----------------------------
	# 通过对内容元素设置class为 layui-show 来选择性初始展开某一个面板
	# element 模块会自动呈现状态图标
		<div class="layui-collapse">
			<div class="layui-colla-item">
				<!-- 面板标题 -->
				<h2 class="layui-colla-title">杜甫</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
			<div class="layui-colla-item">
				<!-- 面板标题 -->
				<h2 class="layui-colla-title">李清照</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
			<div class="layui-colla-item">
				<!-- 面板标题 -->
				<h2 class="layui-colla-title">鲁迅</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
		</div>

		<script>
			//注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
			layui.use('element', function() {
				var element = layui.element();

				//…
			});
		</script>

----------------------------
折叠面板开启手风琴			|
----------------------------
	# 在折叠面板的父容器设置属性 lay-accordion 来开启手风琴，那么在进行折叠操作时，始终只会展现当前的面板。
		<div class="layui-collapse" lay-accordion>
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">杜甫</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">李清照</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
			<div class="layui-colla-item">
				<h2 class="layui-colla-title">鲁迅</h2>
				<div class="layui-colla-content layui-show">内容区域</div>
			</div>
		</div>
	
	