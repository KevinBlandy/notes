------------------------
1,Jquery-样式操作			|
------------------------
	* 样式的操作
	$('div:first').css('color','red');		//给第一个div添加一个color样式，值为red
		* 不用多解释,这东西简单，有这个属性就修改，没就添加
		* 行内优先级比较高，而这个直接就是设置行内属性
	.css(json)
		* 里面可以放json对象,key,value分别对应样式名和值
	.css('color'function(){
			return "red";
		})
		* 也可以通过函数返回的形式来设置值
	．css('color');
		* 这个就不是设置了，是返回指定节点的指定css属性的值
		* 通过底层js只能获取行内样式,但是这种方式可以获取任意的样式
		
	* 复合样式
	* 这里就要提起一点:border
			这个样式是一个复合样式，又分为如下具体的样式
			border-top-style
			border-top-color
			border-top-width
			.. left,right就不写了，都一样
	$('div:first').css('border-top-color');
		* 复合样式就需要用这种方式来获取
		* 但是设置就可以直接进行设置
		
	总结
		1,Jquery可以获取:行内,内部,外部样式,而dom只能获取行内样式
		2,获取复合属性样式，需要拆分为'具体样式'
			$('div:first').css('border-left-color');
		3,复合样式可以直接进行设置
			$('div:first').css('border','5px solid blue');
		4,样式的设置会被设置为行内样式(优先级最高),有则修改，无则添加
	
		
	
