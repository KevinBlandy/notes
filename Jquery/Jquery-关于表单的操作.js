------------------------
1,Jquery-input			|
------------------------
	* 获取input框的value
		$('input:first').val();
		$('input:first').val('demo');		
		* 显而易见，这就是设置值了
		$('input:first').attr('value');			//获取
		$('input:first').attt('value','Kevi');	//设置
		* 其实这个方式也是可以的
------------------------
2,Jquery-复选框			|
------------------------
	1, 获取所有被选中的复选框的值
		function demo(){
			for(var x = 0;x < $('input:checked').length;x++){
				alert($('input:checked:eq('+x+')').val());
			}
		}
		* $('input:checked').length:表示所有选中元素的长度
	2, 设置某个复选框为选中状态
		* 常规操作
		function demo(){
				for(var x = 0;x < $('input').length;x++){
					var value = $('input:eq('+x+')').val();
					if(value == 'zuqiu' || value == 'taiqiu'){
						$('input:eq('+x+')').attr('checked','true');
					}
				}
			}
		* 只要value是:lanqiu，zuqiu的都会被会选中
		* 简单操作
		$('input').val(['zuqiu','taiqiu']);
		* 这个操作就比较叼了,首先获取的是所有的input框,如果它的value等于'zuqiu'或者是'taiqiu'，就会被选中
		* 这个也好理解，多用用就知道了，里面的值是可以是多个
	3,判断是否被选中
		* $('input:first').attr('selected');
			* 返回boolean
	4,设置全选
		* $('.hb').attr('checked',true);
		* 同理,设置全不选就是false
		* 反选
		for(var x = 0;x < $('.hb').length ;x++){
			var flag = $('.hb:eq('+x+')').attr('checked');
			$('.hb:eq('+x+')').attr('checked',!flag);
		}
	5,获取Radio的值
		*	$('input:radio:checked').val();				//根据标签获取
		*	$("input[type='radio']:checked").val();		//根据type获取
		*	$("input[name='rd']:checked").val();		//根据name属性获取
------------------------
1,Jquery-下拉列表			|
------------------------
		* 获取下拉列表，被选中的value值
		* 一般单选，除非加上一个属性，其实可以是多选的
		1,获取被选中的值
		$('option:selected').val();
			* 不多解释,一看就懂
		2 设置某个选项为选中状态
		* 常规操作
		for(var x = 0;x < $('option').length ;x++){
				var value = $('option:eq('+x+')').val();
				if(value == '2'){
					$('option:eq('+x+')').attr('selected','true');
				}
			}
			* 遍历操作,如果下拉列表的值是'2'，那么就会选中
		* 简单操作
		$('select:first').val([2]);
			* 注意了,这里是select,不是option,'该中方式可以应付多选的select'
			* 如果是多选的select,那么就可以在val数组中传递多个值

------------------------
1,Jquery-单选摁扭			|
------------------------	
	* 这东西只能选择一个
	1,获取被选中的恩纽的值
	$('radio:checked').val();
	2,设置某个单选摁扭被选中
	* 常规操作
	for(var x = 0;x < $('.dd').length;x++){
		var value = $('.dd:eq('+x+')').val();
		if(value == 'boy'){
			$('.dd:eq('+x+')').attr('checked','true');
		}
	}
		* 这东西就是单选恩纽的class是dd,然后如果值是boy，就选中
	* 快捷操作
	$('.dd').val(['girl']);
		* 需要注意的其实就是,这里演示的是通过class属性来完成的
	
	
			
			
			
			
			
		
		
---------------------------------
1,Jquery-把表单序列化为json 对象|
---------------------------------	
		
	formToJoson : function(form){
        var o = {};
        $.each(form.serializeArray(),function(index){
            if(this['value'] != null && this['value'] != ''){
                if(o[this['name']]){
                    o[this['name']] = o[this['name']] + "," + this['value'];
                }else{
                    o[this['name']] = this['value'];
                }
            }
        });
        return o;
    },

	var json = formToJson($('#serch_form'));

	# 如果是多个参数,则以','号分割
		<checkbox name="type" value="1"/>
		<checkbox name="type" value="2"/>
		<checkbox name="type" value="3"/>
		<checkbox name="type" value="4"/>
	
		json = {
			type:"1，2，3，4"
		}

---------------------------------
1,Jquery-把表单序列化为请求参数字符串|
---------------------------------

$('#userfrom').serialize();

	username=123456&
	password=123455&
	gender=%E5%A5%B3&
	age=12321&
	birthday=2016-10-11&
	city=3&			//如果有多个值(checkbox),则会出现多个键值对
	city=5&
	city=6&
	salary=155&
	starttime=111&
	endtime=111&
	description=111