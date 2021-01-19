------------------------
Bootstrap-表单样式		|
------------------------
	role="form"
		* 添加到<form>标签,表示该form会被渲染

	form-group
		* 表单组样式,可以吧<label>和表单元素包在里面,可以获得更好的排列
		*　demo
		<div class="form-group">
			<label for="name">名称</label>
			<input type="text" class="form-control" id="name" placeholder="请输入名称">
		</div>

	input-group
		* 只能用于文本框input,连select都不行,连体框
		* demo
		<div class="input-group">
			<span class="input-group-addon">@</span>
			<input type="text" class="form-control" placeholder="twitterhandle">
		</div>

	input-group-sm/input-group-lg
		* 连体框大小
	



	form-control-static
		* 定义静态资源块
		<div class="form-group">
			<label class="col-xs-2 control-label">Email</label>
			<div class="col-xs-10">
				<p class="form-control-static">email@example.com</p>
			</div>
		</div>

	form-control
		* 表单元素样式,经常用于<input>,<textarea>,<select>元素

	form-inline
		* 内联表单样式,用于<form>元素,使表单中的元素一行排列

	radio-inline
		* 使一组单选框,在同一行

	checkbox-inline
		* 使一组复选框在同一行

	disabled
		* 可以禁用单选框,或者复选框选项的文本

	form-horizontal
		* 水平排列的表单(用于form元素),其实就是栅格系统
		* 要注意的是表单里面比较特殊,不是使用"row"属性的标签包裹,而是直接在form上添加这个属性
		* 那么这个form里面的表单项其实就是按照栅格系统来进行划分的
		* 一般而言,一个<label>和一个<div>,2:10分一行,也就是12列!
		* label上直接添加栅格的划分属性,div上也添加划分属性，就好了

	control-label
	 	* 缩短label与输入框的距离,标注在label上

	sr-only
		* 可以用于隐藏元素,加在哪个元素是哪个,哪个元素就会被隐藏
		* 可以加在任何元素

	help-block
		* 可以用于提示信息，直接跟随input,或者select后面,字体颜色会变浅
		<p class="help-block">仅支持jpg,gif,png格式的图片</p>
		<span class="help-block">用户名必须是中文</span>

	# input框大小
		input-lg
			* 用于控制input框的大小，大

		input-sm
			* 用于控制input框的大小，中

		input-sm
			* 用于控制input框的大小，小

	
	# input框长度
		col-lg-2
		col-lg-3
		col-lg-4
		* 用于控制input的长短,加在父div标签上
		<div class="col-lg-2">
			<input type="text" class="form-control" placeholder=".col-lg-2">
		</div>

	# 输入框虚线颜色的状态
	 * 可以通过改变input父级div的class属性来完成
		has-success		//输入正常
		has-warning		//输入警告
		has-error		//输入错误
		<div class="form-group has-success">
			<label class="col-sm-2 control-label" for="inputSuccess">
				输入成功
			</label>
			<div class="col-sm-10">
				 <input type="text" class="form-control" id="inputSuccess">
			</div>
		</div>
	
	# 设置文本+输入框同体的输入框(输入框组)

		input-group-addon
			* 用于在input前后添加元素,该属性给span就好
		
			1,把前缀或后缀元素放在一个带有 class .input-group 的 <div> 中。
			2,接着，在相同的 <div> 内，在 class 为 .input-group-addon 的 <span> 内放置额外的内容。
			3,把该 <span> 放置在 <input> 元素的前面或者后面。
			<div class="input-group">
				<span class="input-group-addon">用户名</span>
				<input type="text" class="form-control" placeholder="twitterhandle">
			</div>


------------------------
Bootstrap-input			|
------------------------



------------------------
Bootstrap-select		|
------------------------
	# 没啥好说的,添加 常规组件就是了 form-control
	<div class="form-group">
		<label for="name">选择列表</label>
		<select class="form-control">
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		</select>
		<label for="name">可多选的选择列表</label>
		<select multiple class="form-control">
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		</select>
	</div>


------------------------
Bootstrap-radio/checkbox|
------------------------
	checkbox
		* 标识一个checbox,
		* 标识在input父级div,该checbox会单独一行显示
			<div class="checkbox">
				<label>
					<input type="checkbox" value="">选项 1
				</label>
			</div>
			<div class="checkbox">
				<label>
					<input type="checkbox" value="">选项 2
				</label>
			</div>
	radio
		* 标识一个radio
		* 同上

	radio-inline
		* 使一组单选框,在同一行
		* 标识在父级 label
		<div>
			<label class="checkbox-inline">
				<input type="checkbox" id="inlineCheckbox1" value="option1">选项 1
			</label>
			<label class="checkbox-inline">
				<input type="checkbox" id="inlineCheckbox2" value="option2">选项 2
			</label>
			<label class="checkbox-inline">
				<input type="checkbox" id="inlineCheckbox3" value="option3">选项 3
			</label>
		</div>

	checkbox-inline
		* 使一组复选框在同一行
		* 标识在父级 label
		* 同上

	disabled
		* 可以禁用单选框,或者复选框选项的文本
	
	# 最佳实践
	<div>
		<label class="checkbox-inline">
			<input type="checkbox" id="inlineCheckbox1" value="option1">选项 1
		</label>
		<label class="checkbox-inline">
			<input type="checkbox" id="inlineCheckbox2" value="option2">选项 2
		</label>
		<label class="checkbox-inline">
			<input type="checkbox" id="inlineCheckbox3" value="option3">选项 3
		</label>
	</div>
	<div>
		<label class="radio-inline">
			<input type="radio" name="optionsRadiosinline" id="optionsRadios3" value="option1" checked>选项 1
		</label>
		<label class="radio-inline">
			<input type="radio" name="optionsRadiosinline" id="optionsRadios4" value="option2">选项 2
		</label>
	</div>

		
	
--------------------
案例				|
--------------------
	1,简单的
		 <form class="form-horizontal" role="form">
				<div class="form-group">
					<div class="col-xs-6">
						<input type="text" class="form-control" id="firstname1" placeholder="请输入称呼">
					</div>
					<div class="col-xs-6">
						<input type="text" class="form-control" id="firstname2" placeholder="请输入QQ/网址等任意联系信息">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<textarea class="form-control" id="lastname" placeholder="请输入留言信息" rows="5" style="resize: vertical"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success btn-block">提交</button>
					</div>
				</div>
			</form>