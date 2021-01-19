--------------------------
javascript-window			|
--------------------------

--------------------------
javascript-属性				|
--------------------------
	innerHeight
		*　浏览器窗口的内部高度
	innerWidth 
		* 浏览器窗口的内部宽度
	parent
		* 返回父级窗口的window对象
	to
		* 返回最顶级的窗口的window
	opener
		* 返回创建此窗口的引用

--------------------------
javascript-方法				|
--------------------------
	alert("弹窗");					
		* 弹出提示框

	confirm("喔唷");				
		* 弹出一个带有[确认],[取消]的提示框,该方法会返回一个Bollean值

	prompt("输入你银行卡密码");		
		* 弹出一个带文本输入框的弹窗,返回输入值

	open(url,name,features,replace);
		* 打开一个新的窗口
		* url		:表示打开的地址,如果没有,那么就是打开一个空白啥也没的
		* name		:一般为空,表示打开的方式
		* features	:表示新打开窗口的属性
		* replace	:

	opener;			
		* 返回创建此窗口的window对象
		* 例如:var main = window.opener;	
		* 那么mian,就是创建了这个窗口的 window 对象.通常用于跨窗口操作

	close();						
		* 关闭窗口,浏览器兼容比较差,在某些浏览器下估计会不好使

	setInterval(fun,10);		
		* 定时器,每隔10毫秒调用一次demo();这个方法。
		* 这个方法会返回一个id
		* Demo
			var x = 0;
			var intervalId = window.setInterval(function(){
				x++;
				if(x == 10){
					clearInterval(intervalId);
				}
				console.log(new Date().getTime());			
			},1000);
			

	clearInterval(id);				
		* 清除指定的定时器,传递定时器的id

	setTimeout(fun,10,param);		
		* 在10毫秒后,去执行指定的函数,也可以直接是JS代码,仅仅会执行一次,也会返回一个id
		* 最后一个参数(param),是执行fun时传递的参数,可以省略

	clearTimeOut(id);				
		* 清除指定的单次定时器,传递定时器的id

	moveBy(200,400);				
		* 偏移到一个位置,其中值是偏移值,根据当前位置而设值

	moveTo(200,400);				
		* 移动到一个位置,根据屏幕而设置

	resizeTo();
		* 重新调整浏览器大小

	focus();						
		* 让窗体一直获取焦点,也就是一直处于前置状态
	
	scrollBy(x,y);
		* 方法可把内容滚动指定的像素数。
		* 注意:要使此方法工作 window 滚动条的可见属性必须设置为true！	

	scroll();
		
	scrollTo(x,y);
		* scrollTo() 方法可把内容滚动到指定的坐标。
	

	btoa(v)
		* 把数据转换为base64编码,好像不支持中文
		* 对于中文可以先URI编码
	
	atob(b)
		* 把base64编码转换为原始数据
		
--------------------------
javascript-事件				|
--------------------------
	onload
		* 在整个文档加载完毕执行
	
	onstorage
		* 该事件应该注册到window对象上
		* 事件对象的特殊属性
			key				//key
			newValue		//新值
			oldValue		//如果是新插入,该值为null
			storageArea		//是 localStorage 还是 sessionStorage
			url				//当前URL

		* 这个事件属于广播事件,会通知当前浏览器中打开的所有相同的页面
		* 当同源页面的某个页面修改了localStorage,其余的同源页面只要注册了storage事件，就会触发
		* 在同一个网页修改本地存储，又在同一个网页监听，这样是没有效果的。
		* Chrome 下必须由其他页面触发。IE,Firefox 可以本页面触发
		* demo	
			window.addEventListener('storage',function(event){
				
			});
	
	onbeforeunload
		* 在窗口关闭之前执行
	
	onunload
		* 在窗口关闭之后执行
	