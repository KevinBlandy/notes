------------------------
1,Jquery-加载事件			|
------------------------
	一,JavaScript的加载事件
		<body onload="load()">
		window.onload = function(){} 
		//如果存在多个,那么后者会覆盖前者
	二,Jquery的加载事件
		1,把document的dom对象变成Jquery对象
			$(document).read(function(){
				//代码
			 });
		 2,$()是创建Jquery对象,不过内部没有Dom对象的组成部分
			 $().ready(function(){
				//代码		 
			 });
		 3,对第一种方法的封装
			 $(function(){
			 	//代码
			 });
		//如果存在多个,会挨个执行
	三,Jquery加载事件月传统事件的区别
		1,同一个请求加载事件设置数量
			Jquery:可以设置多个
				* Jquery加载事件是把每个加载事件都存入一个数组,成为数组的元素,执行的时候,遍历执行
			onload:只有一个
				* 其实就是一个变量,屡次赋值.当然是最后一个为有效值了
		2,执行时机
			Jquery:相对较快
				* 只要内容(文字,图片,样式)在内存中对应的DOM树绘制完毕就执行,有可能对应的内容在浏览器中还没有显示
			script:相对较慢
				* 是全部内容(文字,图片,样式)都在浏览器显示完毕才执行加载事件
				
------------------------
3,Jquery-普通事件操作		|
------------------------
	一,点击事件(click)
		* 设置点击事件
			$('button:first').click(function(){
				alert('摁扭被点击');
			});
		* 触发点击事件
			$('button:first').click();
	二,鼠标事件
		* 鼠标指向
			$().mouseover(function(){
		
			});
		* 鼠标移出
			$().mouseout(function(){
				
			});
	三，一次性给多个节点添加事件
		$('li').mouseover(function(){
				this.color = 'red';
			});
		* mouseover 他们都有遍历属性，可以对指定的一波属性都进行事件的添加，那么当中的this，就是迭代的当前Dom对象
		* 要注意的是,并不是所有事件都有冒泡行为(遍历),使用的时候需要注意
------------------------
3,Jquery-三种事件绑定		|
------------------------
	* 事件绑定，是丰富我们对事件的操作
	1,普通事件绑定,其实就是跟上面的差不多
		$('button:eq(0)').bind('click',function(){
			
		});
		* 就是在bind括号里面,第一个写事件类型,第二个写执行方法
	2,多个事件的同时绑定
		$('button:eq(0)').bind('事件1　事件２',function(){
		
		});
		* 所有声明的事件，都会触发后面的函数
	3,多个事件，多个处理方法同时绑定(使用json)
		$('div:first').bind
		(
			{
				'mouseover':function()
				{
					$(this).css({'background-color':'pink','height':'100px','width':'100px'});
				},
				'mouseout':function()
				{
					$(this).css({'background-color':'white','height':'50px','width':'50px'});
				}
			}
		);
		* 里面其实就是一个json对象,key为事件类型,value就是一个处理函数
		* 里面的this还是代表当前的Dom对象
		* 这里演示的都是匿名函数，其实也可以使用非匿名的函数
			function demo(){}
			$('div').bind('clice',demo);
		* 还可以同时绑定多个事件,上面绑定一个mouseover，下面再绑定一个mouseover，也是可以的
------------------------
4,Jquery-取消事件，阻止事件|
------------------------
	1,Dom级的事件取消,也就是JavaScript操作
		xxx.onclick=null;
		xxx.removeEventListener();
		...不写了,自己去查
	2,Jquery的事件取消
		$('div:first').unbind();			//取消全部事件
		$('div:first').unbind('click');		//取消指定的事件
		$('div:first').unbind('click',demo);//取消指定事件的指定函数
			* 这个应该是最难理解的第一个
			* 因为，一个事件，可以有多个函数来进行处理，这个并不是取消了这个事件，而且取消了这个事件中的这个执行函数
			　如果该事件还有其他的执行函数的话，那么该事件还是会触发其他的处理函数
			* 该方法，只能去除'绑定事件时，使用非匿名函数的处理方法'

--------------------------------
5,Jquery-事件对象,阻止浏览器默认动作|
--------------------------------
	* 事件对象,阻止浏览器默认动作，阻止事件冒泡
	1,事件对象
		$('div:first').bind('click',function(evt){
		
		});
		$('div:first').click(function(evt){
		
		});
		* evt，就是事件对象,Jquery做了兼容处理，不用担心浏览器的问题
	2,阻止浏览器默认动作
		自己去查百度，后台狗一般用不到
			
			
	
	
	
	
