文件下载的问题
	-- 表单多文件项 name属性不一样
	--xml配置不起作用

值桟问题
	--set(name,Object);
	--存入值桟的时候,是每个数据都创建一个Map,还是说存入值桟的数据都只是一个Map
	--尼玛。。这死逼框架！直接放域对象多好事儿!整这出有毛病！弄个值桟有快感啊？
	--还OGNL。。。我了X！

关于验证码
	--> 不能使用Servlet,客户端请求验证码该如何用Action实现!
	--> 这个头到底是干嘛的?Content-disposition
	*  Action 返回 none,手动的获取response,获取流响应!还是Servlet那一套!
	*  Ajax 同理,直接 return null,手动获取response,自己做响应!



['项目经验']
	--> result结果自定义可以解决多个方法同时在一个Action的情况
	--> 校验文件是对所有方法都校验,一定要注意
	--> 使用通配符Action可能会出现,多个系统拦截器并发的使用同一个resut--->input视图
		* 例如:登录和上传文件都在同一个Action方法内!而这俩方法都有校验器!xml,出问题默认跳转的都是input视图.
		* 配置文件只允许一个resutl视图存在!不可能让两种不同的业务方式都朝一个地儿蹦吧？
		['解决方案']
		在某个含有input视图的方法上添加注解
		@InputConfig(resultName="自定义视图名称")
		> 这个注解就会改变方法中那些'自动跳转'input视图的拦截器!它们就会默认跳转到你指定的视图!
	--> <s:if test="#session.session_user == null">
			session中有数据的输出
		</s:if>
		<s:else>
			session中没数据的输出
		</s:else>

	---> 
		<jsp:include page="jsp页面"/>		动态包含
		可以把顶部的菜单:登录/注册/订单等等都丢到被包含页面中
	--> <s:fielderror fieldName="username"/>
		页面通过这个,取出Action中通过this.addFieldError("username", "验证码不正确");添加的数据
		* 只要往这个里面添加了数据,那么自动默认的就会往input视图跳
	-->	<s:actionmessage/>
		页面通过这个,取出Action中通过this.addActionMessage("恭喜,激活成功");添加的数据

	--> struts2 Action 中的get();所有东西都会出现在值桟! 直接通过<s:property value="xxx"/>	//getXxx();


	--> 商城首页的Action中有N多个Service,获取不同的数据.多个集合等！一并丢给首页进行展示
	--> ModelDriven接口方法返回的对象还是在值桟中,可以通过:model.属性  取值
	--> <s: iterator begin="1" end="pageBean.totalpage" var="x">
			<s:if test="#x == pageBean.pageCode")
				//if判断标签中,可以直接进行这样判断
			</s:if>
			<s:else>
				//else标签直接加在下面就好
			</s:else>
		</s:iterator>
		* 迭代标签中,可以通过这种方式进行迭代
	
	--> 关于批量删除
		1,可以在循环遍历'用户',的时候,在每个用户前面都加上一个checkbox,name属性随意.值为'用户id'!
		2,提交给服务器的时候,就会把被选中的id,以name为名的数组形式把数据传递给服务器!写个方法,循环删除!
	--> 关于Action中调用另一个Action也是可以的:return list();
		直接就返回该action的值,而且该action还进行了业务操作,连数据都放入了值桟!
	--> 重定向很重要,因为转发浏览器地址不发生变化,如果一些提交操作后是转发的话,页面地址还是提交的页面地址,再次刷新会重复提交数据

	--> <a href="javascript:delete('${user.id}')"/>
	   function delete(id){
			...代码....请求URL+id;
	   }
	 
	--> 解决用户名重复的问题
		* 插入的时候就简单,根据用户名查询一下先,能查出用户.那就是重复了!
			SELECT * FOMR user WHERE account="账号";
		* 编辑的时候
			1,编辑的时候,数据库中已经是有了对应的用户名.所有查询方式要改变
			SELECT * FROM user WHERE account='账号' AND id='用户id';
			* 账号为编辑之后的账号
			* 如果除了正在编辑的用户,还查出了账号对应的记录.那就是重复了.

	--> Radio的问题
		<s:radio list="#{'1':'有效','0':'无效'}" name="role.state"/>

	--> struts2使用OGNL判断
			<%--用户头像不为空 --%>
            <s:if test="%{user.headImg != null && user.headImg != ''}">
            	<img src="${pageContext.request.contextPath }${user.headImg}" width="100" height="100"/>
            	<input type="hidden" name="headImg" value="${user.headImg}"/>
            </s:if>