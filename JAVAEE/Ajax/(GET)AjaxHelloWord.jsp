<script type="text/javascript">
		function createXMLHttpRequest()//创建异步对象
		{
			try
			{
				return new XMLHttpRequest();//大多数浏览器
			}
			catch(e)
			{
				try
				{
					return new ActiveXObject("Msxml2.XMLHTTP");//IE6.0
				}
				catch(e)
				{
					try
					{
						return new ActiveXObject("Microsoft.XMLHTTP");//IE5.5以及更早版本
					}
					catch(e)
					{
						alert("为了更好的体验,请立即更换浏览器访问本站");
						throw e;
					}
				}
			}
		}
		window.onload = function()//文档加载完毕后执行
		{
			var btn = document.getElementById("btn");
			btn.onclick = function()//给摁钮的点击事件注册监听 
			{
				/*
					四步操作,得到服务器的响应
					把响应显示到h1元素中
				*/
				var xmlHttp = createXMLHttpRequest();
				xmlHttp.open("GET", "/WEBDemo/Aservlet", true);
				xmlHttp.send(null);//GET请求没有请求体,但是也要给出null,不然FireFox可能无法发送
				xmlHttp.onreadystatechange = function()//当xmlHttp状态发生变化时执行
				{
					if(xmlHttp.readyState == 4 && xmlHttp.status == 200)//双重判断
					{
						//获取服务器的响应结果
						var text = xmlHttp.responseText;
						var h1 = document.getElementById("h1");
						h1.innerHTML = text;
					}
				}
			}
		};
	</script>

  </head>
  <body>
	<button id="btn">点击这里</button>
	<h1 id="h1"></h1>
  </body>
