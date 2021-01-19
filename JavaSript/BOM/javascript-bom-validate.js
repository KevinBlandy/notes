----------------------------
validate					|
----------------------------
	# 浏览器自带的表单验证机制
	
	# form表单项都有3个方法, 和一个属性
		checkValidity()
			* 可以用来验证当前表单控件元素, 或者整个表单是否验证通过
			* 返回值是布尔值: true或者false
				document.querySelector('select').checkValidity(); 
				document.forms[0].checkValidity();

		reportValidity()
			* 触发浏览器的内置的验证提示交互, 返回布尔值: true或者false
			* reportValidity()方法IE浏览器并不支持, Edge 17+开始支持

		setCustomValidity()
			* 设置(reportValidity触发时)自定义的提示文字
			* 参数是一个字符串, 表示出错提示的文字信息, 如果是空字符串, 则表示不使用自定义的错误提示
				var eleSelect = document.querySelector('select');
				if (eleSelect.validity.valueMissing == true) {
					eleSelect.setCustomValidity('请选择城市');
				}
				eleSelect.reportValidity();

		validity
			* 每一个标准的表单控件，例如输入框，下拉框以及单复选框都内置一个validity属性(这是个只读属性)
			* 返回当前元素各种验证状态，所有属性也是只读
				badInput: false
					* 属性值为布尔类型。输入框里面的值浏览器没办法进行转换。
					* 例如'number'类型输入框里面是中文字符（MDN上说法，但是自己测试无法复现），此时值是true。此属性IE浏览器并不支持。此属性建议了解即可。

				customError: false
					* 性值为布尔类型。如果元素调用setCustomValidity()方法设置了自定义的验证信息则值是true。

				patternMismatch: false
					* 属性值为布尔类型。和指定的pattern正则属性值不匹配的时候值是true。
					* 会匹配: invalid 这个CSS伪类。
						input:invalid {  /** invalid 选择器用于在表单元素中的值是非法时设置指定样式。 **/
							border:2px solid pink;
						}
						// invalid 选择器只作用于能指定区间值的元素，例如 input 元素中的 min 和 max 属性，及正确的 email 字段, 合法的数字字段等。

				rangeOverflow: false
					* 属性值为布尔类型。超过max属性设置的值的时候会是true
					* 同时会匹配CSS :invalid和:out-of-range伪类

				rangeUnderflow: false
					* 属性值为布尔类型。小于min属性设置的值的时候会是true
					* 同时会匹配CSS :invalid和:out-of-range伪类

				stepMismatch: false
					* 属性值为布尔类型。输入框当前值和step属性值不匹配的时候stepMismatch的值会是true
					* 同时元素会匹配CSS :invalid和:out-of-range伪类。

				tooLong: false
					* 属性值为布尔类型。输入内容长度超出maxlength的限制时候会是true。
					* 同时会匹配CSS :invalid和:out-of-range伪类。

				tooShort: false
					* 属性值为布尔类型。输入内容长度不足minlength的限制时候会是true。
					* 同时会匹配CSS :invalid和:out-of-range伪类。此属性IE浏览器并不支持，因为不支持minlength属性。

				typeMismatch: false
					* 属性值为布尔类型。输入框的值和输入框类型不匹配的时候该属性值会是true。
					* 例如输入框type类型是email或者url时候。如果值是true，会匹配:invalid这个CSS伪类。

				valid: true
					* 属性值为布尔类型。当前元素是否完全验证通过
					* 通过是true，会匹配:valid这个CSS伪类；不通过是false，会匹配:invalid这个CSS伪类。

				valueMissing: false
					* 属性值为布尔类型。如果元素设置了required属性
					* 同时输入框的值为空，则该属性值是true。如果值是true，会匹配:invalid这个CSS伪类。
		

	
