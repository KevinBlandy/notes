<script>
	//数据校验
	function checkForm()
	{
		//校验用户名
		var username = document.getElementById("username").value;
		if(username == null || username == '')
		{
			alert("用户名不能为空");
			return false;
		}
		//校验密码
		var password = document.getElementById("password").value;
		if(password == null || password == '')
		{
			alert("密码不能为空");
			return false;
		}
		//确认密码校验
		var repassword = document.getElementById("repassword").value;
		if(repassword != password )
		{
			alert("两次密码输入不一致");
			return false;
		}
	}
	//异步校验
	function checkUserName()
	{
		// 获得文件框值:
		var username = document.getElementById("username").value;
		// 1.创建异步交互对象
		var xhr = createXmlHttp();
		// 2.设置监听
		xhr.onreadystatechange = function()
		{
			if(xhr.readyState == 4 && xhr.status == 200)
			{
				document.getElementById("span1").innerHTML = xhr.responseText;//获取服务器相应数据,显示在页面中
			}
		}
		// 3.打开连接
		xhr.open("GET","${pageContext.request.contextPath }/user_findByName.php?time="+new Date().getTime()+"&username="+username,true);
		// 4.发送
		xhr.send(null);
	}
	//创建异步交互
	function createXmlHttp()
	{
		   var xmlHttp;
		   try{ // Firefox, Opera 8.0+, Safari
		        xmlHttp=new XMLHttpRequest();
		    }
		    catch (e){
			   try{// Internet Explorer
			         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			      }
			    catch (e){
			      try{
			         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			      }
			      catch (e){alert("别拿IE整,下个谷歌很快的~~") throw(e)}
			      }
		    }
			return xmlHttp;
		 }
	//更换验证码
	function change()
	{
		var img1 = document.getElementById("checkImg");	//获取img节点对象
		img1.src="${pageContext.request.contextPath }/checkImg.php?"+new Date().getTime();
	}
</script>

对于需要数据校验的表单需要添加一个属性,然后套上面的模板吧孩子
onsubmit="return checkForm();"
对于需要被执行异步的表单input要添加一个事件:失去焦点
onblur="checkUserName()"

Action代码=============异步交互
	public String findByName() throws IOException
	{
		//调用Service进行查询
		User u = userService.findByUserName(user.getUsername());
		//获取response对象
		HttpServletResponse response =  ServletActionContext.getResponse();
		//设置相应文本以及字符编码
		response.setContentType("text/html;charset=UTF-8");
		if(u != null)	//用户名已存在
		{
			response.getWriter().print("<font color='red'>用户名已经存在</font>");
		}
		else			//用户名不存在
		{
			response.getWriter().print("<font color='green'>用户名可以使用</font>");
		}
		return NONE;	//ajax操作,无需页面跳转
	}



	！！！需要注意的是,这里的异步交互仅仅是刷新出了提示语句。并没有阻止表单的提交！实际使用的时候要注意

	建议建立全局变量, var flag = true;根据服务器的信息,如果用户名存在,那么 flag = false;在提交表单的时候,校验这个 flag!

练习总结
1,