------------------------
1,Jquery-属性操作			|
------------------------
	<input type="text" name="name"　class="regist" id="name" value="KevinBlandy">
	* itnode.属性名
	* itnode.属性名=值
	* itnode.getAttribute(属性名);
	* itnode.setAttribute(属性名,值);
	
	$().attr('属性名');			//获取指定节点的属性值
		$('input:first').attr('type');
		* 获取到第一个input框的type属性
	$().arrt('属性名','值');		//设置指定节点的属性值
		$('input:first').attr('value','测试');
		* 设置第一个input框的value属性为测试
		* 如果是设置type属性的话,也许会失效,跟浏览器兼容相关
	$().removeAttr('属性名');	//删除指定节点的属性
		$('input:first').removeAttr('class');
		* 删除第一个input框的class属性
		* 除了type属性,其他属性都是可以做删除操作的
	$().arrt(json对象);			//同时为指定节点设置多个属性值
		var json = {name:'Kevin',id:'regist'};
		$('input:first').attr(json);
		* json对象,key为属性名称,value为属性值，批量修改指定节点的属性
	$().arrt(属性名称,function);	//通过某个函数的返回值来为指定的节点设置属性值
		* 案例，一看就懂
		$('input:first').attr('class'，function(){
			return "regist";
		});
	
	* 对于input框value的快捷操作
	$('#name').val();				//获取值
	$('#name').val('value');		//设置值

------------------------
2,Jquery-class属性快捷操作|
------------------------
	* 其实用上面的方法也是可以完成对class属性的操作
	$('p:fisrt').attr('class','demo');			//设置第一个p节点的class属性为demo
	* 快捷方式操作class属性
	$('p:first').addClass('demo');				//为指定节点设置class属性值
		* 可以同时对一个节点进行n此的.addClass操作，这些class属性都会同时存在
	$('p:last').removeClass('demo');			//为指定节点删除属性值
		* 这个仅仅是删除class属性中的demo,而上面的直接是干掉了class这个属性
	$('p:last').toggleClass('demo');			//开关效果，有就添加，没有就删除	

------------------------
3,Jquery-标签包含内容操作	|
------------------------
	* $('div:first').html();				//获取第一个div中的元素，字符串和标签都可以获取
		* 例如里面有个span包裹着文字，那么就连标签和文字一起返回
	* $('div:firt').html('<a>百度</a>');		//在第一个div中添加百度文本节点
		* 可以添加标签，文本，文本＋标签都可以
	* $('div:first').text();
		* 获取到的内容，是没有标签的！仅仅会获取到文本
	* $('div:first').text('百度');
		* 仅仅能添加字符串，不能添加标签,如果有标签，会吧标签<>给转义

------------------------
4,Jquery-获取img属性		|
------------------------
	$('img:first').width();		//返回的就是指定图片的宽度
	$('img:first').height();	//高度
	* 点击图片实现等比例放大和缩小
	function big(){
		var width = $('img:first').width();
		var height = $('img:first').height();
		var new_width = width + 5;
		var new_height = new_width * height / width;
		$('img:first').width(new_width);
		$('img:first').height(new_height);
	}
	function smal(){
		var width = $('img:first').width();
		var height = $('img:first').height();
		var new_width = width - 5;
		var new_height = new_width * height / width;
		$('img:first').width(new_width);
		$('img:first').height(new_height);
	}

	
	
	
	
		
