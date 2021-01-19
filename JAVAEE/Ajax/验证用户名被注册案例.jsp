	<script type="text/javascript">
	function createXMLHttpRequest(){
			try{
				return new XMLHttpRequest();
			}catch(e){
				try{
					return new ActiveXObject("Msxml2.XMLHTTP");}
				catch(e){
					try{
						return new ActiveXObject("Microsoft.XMLHTTP");}
					catch(e){
						alert("为了更好的体验,请立即更换浏览器访问本站");
						throw e;}
				}
			}
		}
	window.onload = function(){
		//获取文本框,给它失去焦点事件注册监听
		var userEle = document.getElementById("userNameEle");
		userEle.onblur = function(){
			//得到异步对象
			var xmlHttp = createXMLHttpRequest();
			//打开连接
			xmlHttp.open("POST", "<c:url value='/Aservlet'/>",true);
			//设置请求头
			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			//发送请求,给出请求体
			xmlHttp.send("userName="+userEle.value);
			//给xmlHttp的onreadystatechange注册监听
			xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
				{
					//获取服务器的响应,判断是否为1为span添加内容 
					var text = xmlHttp.responseText;
					if(text == "1"){
						//得到sapn
						var span = document.getElementById("errorSpan");
						span.innerHTML = "用户名已经被注册";
					}
					else
					{
						var span = document.getElementById("errorSpan");
						span.innerHTML = "用户名可用";
					}
				}
			};
		};
	};
	</script>
  </head>
  
  <body>
	<h1>演示用户名是否被注册</h1>
	<form action="" method="POST">
		用户名:<input type="text" name="userName" id="userNameEle"/><span id="errorSpan"></span><br/>
		密　码:<input type="text" name="userName" id="userNameEle"/><br/><br/>
		<input type="submit" value="登录" />
	</form>
  </body>