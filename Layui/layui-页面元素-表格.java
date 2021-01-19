----------------------------
表格						|
----------------------------
	# 在一个 <table> 容器中设定 class="layui-table" 来渲染一个表格元素块
	# 通过内置的自定义属性对表格进行风格的多样化设定

----------------------------
默认表格					|
----------------------------
	<table class="layui-table">
		<colgroup>
			<col width="150">
			<col width="200">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th>昵称</th>
				<th>加入时间</th>
				<th>签名</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>贤心</td>
				<td>2016-11-29</td>
				<td>人生就像是一场修行</td>
			</tr>
			<tr>
				<td>贤心</td>
				<td>2016-11-29</td>
				<td>人生就像是一场修行</td>
			</tr>
		</tbody>
	</table>
	# 基础属性
		属性名				属性值			备注
		lay-even			无				用于开启 偶数行 背景，可与其它属性一起使用
											line	:行边框 风格表格 
		lay-skin="属性值"	line/row/nob	row		:列边框 风格表格 
											nob		:无边框 风格表格
	
