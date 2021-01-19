------------------------
1,Jquery-节点追加			|
------------------------
	1,父子关系追加
		主动追加(父亲认儿子)
			$('#num').append('<li>6</li>');			//后置追加
				* 在id为num的元素最后添加节点
			$('#num').prepend('<li>0</li>');		//前置追加
				* 在id为num的元素最前面添加节点
		被动追加(儿子认父亲)
			$('<li>6</li>').appendTo($('#num'));	//后置追加
			$('<li>0</li>').prependTo($('#num'));	//前置追加
		使用现有节点,进行追加
			$('#num').append($('#abc li'));			
			* 被添加的元素可以是字符串表示,也可以直接是另一个节点对象
			* 已有节点追加,那么'原来的节点,会消失,会发生物理位置移动'

	2,兄弟关系追加
		主动追加(找个哥哥或者弟弟)
			$('li:first').after('<li>后面</li>');	//后置追加
				* 在指定的元素'后面'加上指定的元素
			$('li:first').befor('<li>前面</li>');	//前置追加
				* 在指定的元素'前面'加上指定的元素

		被动追加(认个哥哥或者弟弟)
			$('<li>7</li>').insertAfter($('li:eq(7)'));
				* 把指定节点,插入到指定节点的后面
			$('<li>5</li>').insertBefore($('li:eq(7)'));
				* 把指定节点,插入到指定节点的前面
		也可以获取现有的节点,来进行追加.'那么原节点会被移除'
		
------------------------
2,Jquery-节点替换/删除	|
------------------------
	1,主动替换(新节点,篡位)
		* 新节点替换
			$('<li>new</li>').replaceAll($('li:eq(0)'));
				* 使用新节点,替换掉旧节点
		* 已有节点替换
			$($('li:eq(5)')).replaceAll($('li:eq(0)'));	
				* 选择一个几点,替换掉旧节点，原节点会移除

	2,被动替换(旧节点,传位)
		*　新节点
			$('#old').replaceWith('<li>new</li>');
		*　已有节点
			$('#old').replaceWith($('#new'));
				* 必须要记住的就是已有节点进行替换，原节点会被移除
	
	3,节点删除
		$('#ul:first').empty();					//直接干掉指定节点所有子节点
			* 直接删除掉指定节点的所有子节点
		$('li:eq(0),li:eq(1)').remove();		//删除掉指定的节点
			* 其实这个移除操作，并没有父节点的参与,属于自杀

------------------------
2,Jquery-节点复制		|
------------------------
	$(节点).clone(true);
		* 复制指定节点，和其身上的事件都会被复制
	$(节点).clone(false);
		* 仅仅复制节点本身(包括内部信息)
	
	演示
		$('ul:first').append($('li:first').clone(true));
			* 把第一个li复制给第一个ul，添加到最后，并且事件还会存在
		

------------------------
3,Jquery-创建节点		|
------------------------
	var img = $("<img/>",{
		src:'URL连接',
		click:function(){
			alert('事件');
		}
	});
	* 创建了一个img标签,并且绑定了事件
	
	
------------------------
3,Jquery-节点的位置		|
------------------------
	* 修改指定节点的的位置
		var elt = $('#demo');			//获取到节点
		var position = elt.offset();	//获取到节点的坐标
		position.top += 100;			//修改节点的坐标
		elt.offset(position);			//重新把修改过的坐标,赋值给节点
	
	* 把指定节点朝指定方向移动(移动距离取决于他们在文档中的位置)
		$('h1').offset(function(index,curpos){			//获取到所有的h1节点,进行遍历
			return {				
				left:curpos.left + 25 * index,			//返回一个json,能看懂?
				top:curpos.top							//该函数两个参数，不能忘记！第一个是坐标
			};											//第二个就是节点对象本身,dom对象
		});												//通过获取它的值,加以改变
	
	* offset():是针对于整个文档进行定位的.
	* postion():是针对于当前节点的父节点对象来进行定位的
	* 部分获取参数的方法

		var body = $('body');
		var contentWidth = body.width();
		var paddingWidth = body.innerWidth();
		var borderWidth = body.outerWidth();
		var marginWidth = body.outerWidth(true);
		var padding = paddingWidth - contentWidth;		//左边距和右内边距的和
		var borders = borderWidth - paddingWidth;		//左边框和右边框的和
		var margins = marginWidth - borderWidth;		//左外边距和右外边距的和
	
	* 控制滚动
		function page(n){
			var w = $(window);						//把windows封装成Jquery对象
			var pageSize = w.height();				//得到页面的大小
			var current = w.scrollTop();			//得到当前滚动条的位置
			w.scrollTop(current + n*pageSize);		//刷新滚动条的位置
		}