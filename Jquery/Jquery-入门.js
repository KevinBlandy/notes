------------------------
1,Jquery-入门			|
------------------------
	* Jquery,这东西其实就是对JS的一种封装
	* 简化了很多操作,节点获取,ajax,事件
	* 它无需考虑浏览器兼容的问题,代码少
	* JS涉及到的技术
		DOM操作
			document.getElementById("id");
			document.getElementsByTagName("li");
			document.getElementsByName("name");
		事件操作
			主流浏览器
				addEventListener();
				removeEventListener();
			IE浏览器
				attachEvent();
				detachEvent();
		事件对象阻止事件流产生
			主流浏览器
				事件处理函数的第一个形参
				function(event){}
			IE浏览器
				全局变量
				window.event;
		事件对象阻止浏览器默认动作
			主流浏览器
				evt.stoPropagation();
			IE浏览器
				evt.cancelBubble = true;
		Ajax
		
		
		
------------------------
2,Jquery-基本选择器		|
------------------------
	* 选择器就是在页面上获得各种元素节点对象使用的条件就是选择器
	$('#id');			//根据id来进行获取
	$('li');			//根据标签名称来获取,可以进行统一的样式操作
	$('.class');		//根据class属性来进行获取,可以进行统一样式操作
	$('*');				//通配符,获取所有的元素
	$('#s1,#s2,input');	//联合选择器,可以选择到符合的元素	
------------------------
3,Jquery-层次选择器		|
------------------------
	* 父子标签选择器
		$('ul li');			//在ul内部,获取li节点,不考虑层次,只要是ul里面的li标签都会被找到
		$('s1 > s2');		//在ul内部,获取li节点,要考虑层次,只获取第一个层次的li节点
	* 兄弟标签选择器
		$('s1 + s2');		//获取s1后面的第一个兄弟节点,这个特叼,只要是符合这个关系(s1 s2)的节点都会被找到
			* 只要指定的s1 和 s2是兄弟节点,那么就都会被找到(例如:$('div + span'),只要是div后有一个span的,第一个span就会被选择到)
		$('s1 ~ s2');		//获取s1后面的所有兄弟节点,这个也叼,只要是符合这个关系(s1 s2)的节点都会被找到
			* 同上,只要是符合表达关系的兄弟节点,后面的所有节点都会被找到
		
------------------------
4,Jquery-并且过滤选择器	|
------------------------
	1,并且选择器
	* $('li:first').css('color','red');		//li节点的第一个
		* first,不多解释
	* $('li:last');							//li节点的最后一个
	2,下标选择器
	* $('li:eq(0)').css('color','red');		//获取li节点的第一个节点
		* 下标是从0开始,不必多说,一看就懂
	* $('li:gt(5)').css('color','red');		//获取大于5这个下标的li所有节点,并不包括5
		* get,great than
	* $('li:lt(5)').css('color','red');		//获取小于5这个下标的li所有节点,并不包含5
		* lt,less than
	*　$('li:even').css('color','red');		//获取下标为偶数的节点
	* $('li:odd').css('color','blue');		//获取下标为奇数的节点
	3,过滤选择器
	* $('li:not(#id)');						//获取所有li节点,但是过滤掉id属性为id的节点
	4,标题标签选择器
	* $(':header');							//获取h1 - h6这些标签节点	
	
	复杂用法
	
	1,$(':header.pear').css('color','blue');
		*　必须是h1 - h6的标题标签,且class属性为:pear
	2,$('li:gt(1):lt(5)');
		* 获取所有li标签中,索引大于1,小于５的li标签
	3,$('li.pear');
		* 获取所有的class属性为pear的li标签
	总结
		* 并且选择器可以单独使用
		* 各种选择器都可以构造'并且'的关系，前后顺序没有要求，但是尽量不要产生歧义
		* 并且关系的选择器可以使用多个,每个选择器试用前,已获得的节点下标都要归位
		
------------------------
5,Jquery-内容过滤选择器	|
------------------------	
	1,contains(内容)
		$('div:contains('北京')');		//获取包含北京的div!
		* 包含内容选择器,就算是这个div中有个span,span里面才包含了北京,那么也是没影响的
	2,empty
		$('div:emtpty');				//获取一个不包含任何元素的div
		*　空格也算是文本,必须是啥都没，空格回车都没才是空白
	3,has(选择器)
		$('div:has(#aa)');				//获取一个div,这个div中必须有个name属性值为aa的节点
		* 就是div中有一个name属性值为aa的div
	4,parent
		$('div:parent');				//获取所有为父元素节点的div元素
		* div,里面就算是有空白,回车空格,都算是父节点
------------------------
6,Jquery-表单域选中选择器	|
------------------------	
	1,复选框,单选摁扭
		$('.hby:checked');				//获取到所有class为hby,且被选中的复选框
		$('.gender:checked');			//获取到其实跟上面是一个德行
	3,下拉列表
		$('option:selected');			//获取到下拉框中被选中的节点
		*　option，就是选择到了所有的下拉框，后面就是一个条件－被选择的
------------------------
6,Jquery-属性选择器		|
------------------------
	$('[size]');						//获取到有name属性的节点
	$('[name=kevin]');					//获取到有name属性,并且值是kevin的节点
	$('[name^=k]');						//获取到有name属性,并且值是以k开头的节点				
	$('[name$=k]');						//获取到有name属性,并且值是以k结尾的节点		
	$('[name!=kevin]');					//获取到有name属性,并且值不等于Kevin，以及没有name属性的节点
	$('[class][class!=orange]');		//获取到有class属性,并且属性值不等于orange的节点
	．．．　还有很多，自己百度去吧
------------------------				
7,Jquery-$符号的问题		|
------------------------	
	$,其实就是一个函数,我们在使用的时候,其实就是函数的调用过程
		jQuery();
		$();
		* 他俩其实是一样的... ...
------------------------
8,Jquery-Jquery和Dom		|
------------------------
	1,Jquery对象
		$('li'),$('#id'),等返回的就是Jquery对象

	2,Dom对象
		document.getElementById('id');返回的就是dom对象

	3,直接互调对方成员
		Jquery调用Dom对象的成员
			$('li:first').style.backgroundColor='blue';						//调用失败

		Dom对象调用Jquery对象的成员
			document.getElementsByTagName('div')[0].css('color','red');		//调用失败

		* 其实就是想给你说,他们是不能互相调用的对方成员的.

	4,源码剖析(Jquery底层是怎么封装Dom对象的)
		* Dom对象就是Jquery对象的数组组成部分

	5,Jquery对象变成Dom对象,才能调用Dom成员
		$('li')[0].style.backgroundColor = 'blue';
		$('#name').get(0);			//这种方法返回的也是一个dom对象

	6,Dom对象变成Jquery对象才能调用Jquery成员
		var dv = document.getElementsByTagName('div');
		$(dv).css('color','red');	
		* 获取到dom对象后,直接把这个对象传递给Jquery选择器,就可以调用了
		* '这里就是一个Dom对象选择器'

------------------------
8,Jquery-Jquer框架对象分析|
------------------------
	..略

------------------------
9,Jquery-each遍历		|
------------------------
	* 语法
		$.each(arr,function(k,v){
			//arr:被遍历的元素,可以是json对象,可以是数组
			//k:代表下标
			//v:代表当前遍历到的元素
		});

	* 演示
	  	//数组的遍历
		var arr = ['1','2','3','4'];
		$.each(arr,function(k,v){
			console.log(k+","+v);
		});
		//对象的遍历
		var obj = document.getElementsByTagName('input');
		$.each(obj,function(k,v){
			console.log(k+","+v.value);
		});
		//json对象的遍历
		var jso = {name:'kevin',age:22,gender:'boy'};
		$.each(jso,function(k,v){
			console.log(k+","+v);
			//此时的k,代表的就是属性名,v代表的是属性值
		});
		//Jquery对象的遍历
		$('input').each(function(k,v){
			console.log(k+","+v);
			//此处的k,是dom的下标,v是dom对象
		});
		
	* 在Jquery中使用this时候,this就是当前Dom对象
		$('input').each(function(k,v){
			this.style.color  = 'blue';
			$(this).attr('color','blue');
		});
		* this,就是当前的Dom对象

	
	
	
		
	
		
	
	


