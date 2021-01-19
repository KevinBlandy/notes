------------------------
1,Jquery-get			|
------------------------
	* 显而易见,GET请求
	$.get(
			'${pageContext.request.contextPath}/ajax/get',
			{'name':'kevin','age':22},
			function(result)
			{
				alert(result);
			}
		);
		* 第一个参数:url
		* 第二个参数:json数据,传递给服务器
		* 第三个参数:请求成功,回调函数,参数为服务器响应数据
		* 第四个参数:服务器响应数据的类型,可以是JSON,html,xml

------------------------
1,Jquery-post			|
------------------------
	* 其实跟上面是一样的,只不过是POST方式提交的数据

------------------------
1,Jquery-ajax			|
------------------------
	$.ajax(
			{
				url:'${pageContext.request.contextPath}/ajax/get',
				date:"name=KevinBlandy",
				type:'POST',
				contentType:'application/json;charset=UTF-8',
				dataType:'JSON',
				success:function(message){
					alert(message);
				},
				error:function(){
					alert("失败");
				}
			}
		);
		* 一看就懂,这个比较叼
		还有很多的一些参数,具体用到的时候再去百度吧
		dataType:'JSON'
			* 服务器返回的数据类型

		async:true
			* 是否为异步请求,默认是

		cache:true
			* 是否要缓存?默认为真要缓存

		complete
			* 不管是成功还是失败都会调用该函数

		error
			* 失败就会调用这个函数
		
		processData
			* 布尔值，规定通过请求发送的数据是否转换为查询字符串。默认是 true
		
		contentType
			* 发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"。

		headers
			* 额外添加的请求头信息
			* 参数是一个对象
				headers: {
					"Access-Control-Allow-Origin":"http://example.edu",
					"Access-Control-Allow-Headers":"X-Requested-With"
				}

		statusCode
			* 这个会根据响应码来执行相应的函数
			* RESTful风格的开发中,就使用这种更为科学

			statusCode:{
				201:function(){
					//状态码201创建成功执行
				} ,
				500:function(){
					//状态码500,服务器异常执行
				}
			},
		
		xhr
			* 使用自己定义的的 XMLHttpRequest 进行异步交互
			xhr: function(){                            
				myXhr = $.ajaxSettings.xhr();		//获取ajaxSettings中的xhr对象
				return myXhr;						//xhr对象返回给jQuery使用
			},
		
		beforeSend
			* 在执行异步请求的前,执行的操作,是一个 function,会传递进来 XMLHttpRequest
			* beforeSend:function(xhr){}

parse用于从一个字符串中解析出json对象,如

	var str = '{"name":"huangxiaojian","age":"23"}'
	结果：
	JSON.parse(str)
	Object

		age: "23"
		name: "huangxiaojian"
		__proto__: Object

	注意：单引号写在{}外，每个属性名都必须用双引号，否则会抛出异常。


	stringify()用于从一个对象解析出字符串，如

		var a = {a:1,b:2}

		结果：

		JSON.stringify(a)


	"{"a":1,"b":2}"



	var obj = $.parseJSON(json);

------------------------
1,Jquery-getJson()		|
------------------------
	# 直接对指定的URL发起一个请求,返回JSON数据
	$.getJSON('url',function(result){
		
	});

-----------------DEMO
function login(){
	$.ajax({
		url:'${pageContext.request.contextPath}/test/login',
		type:'POST',
		dataType:'JSON',
		contentType:'application/json;charset=UTF-8',
		data: JSON.stringify(GetJsonData()),
		success:function(message){
			if(message.success){
				alert('OK');
			}else{
				alert('ERROR');
			}
		}
	});
}
function GetJsonData(){
	var json = {
		"name":$('#name').val(),
		"pass":$('#pass').val(),
		"hobb":["basketBoall","footBoll","boot"]
	};
	return json;
}