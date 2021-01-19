6大数据

1,当前页		pageCode	pc
	> 如果页面没有传递当前页码,那么默认就是第一页.
	> 如果你告诉我是第几页,我就给你第几页
2,当前页数据		totalPages	tp
3,总记录数		totalRecored	tr
	> 委托DAO层获取数据库信息
	> select count(*) from emp;
4,每页记录数	
	> 业务数据,或者叫系统数据.跟我数据没关系,就是看你怎么想.你想给客户一页显示多少
5,总页数		beanList	bl
	> 总记录/每页记录数
	> 也就是最后一页的页码
6,url		--没怎么弄懂,好像就是在模糊查询的时候有用到

-------------------------

因为以上数据,都要在各层之间传递.我们就可以考虑,封装到javaBaen中.而javaBean中就要具备这6大数据.还要一个容器List来装载正儿八经的数据,也就是提供给页面显示的其他的javaBean,希望你明白
作为一个对象在各层之间传递.

-------------------------

select * from 表名 limit n,m;
	> 获取查询结果,从n开始,取m条
	* 注意:MYSQL中,第一条数据是从0开始的,limit语句,会包括自己
		也就是n,最小值为0,m条数据中包含了第n条数据.
在使用limit语句的时,需要参考的算法
	> 当前页-1 * 每页记录数
	> 这个当前页是由客户端提供的,如果客户端未提供,我们就默认显示第一页数据,
	> 如果这个当前页是负数,那么sql语句会出问题,limit 语句的第一个参数不能为负,最小为0;

-------------------------

在显示页面显示数据的时候
1,当为第一页的时候不显示[上一页]超链接/按钮
	> 当前页只要大于1,就显示		如果等于1,那就是第一页了.就没必要显示上一页
2,当为最后页的时候不显示[下一页]超链接/按钮
	> 当前页小于总页数,就显示		如果当前页等于了总页数,那么就是最后一页了.没必要显示下一页

-------------------------页码列表  [1] [2] [3] [4] [5]
这种东西,最多显示多少个页码,取决于码农和BOSS.
当前页在页码列表中的位置.....[1][2]【3】[4][5] 
	> 百度的页码位置在第6个,我们可以参考着整!
	> 只需要当前页码,就能定出来页码列表.
begin
end
	需要使用pc来推算出来begin,end...
	begin = pc - 5;
	end   = pc + 4;
计算公式:
	如果总页数小于等于列表长度.那么begin就等于1,end等于总页数
	使用上面的计算公式来计算
	头溢出:当begin小于1的时候,begin = 1
	尾溢出:当end > tp时,就让end =tp

------------------直接代码,千万别看傻
	<center>
		<%--给出分页相关的连接 --%>
		第${requestScope.pb.pc }页/共${requestScope.pb.tp }页
		<a href="<c:url value='/CustomerServlet?method=findAll&pc=1'/>">首页</a>
		<c:if test="${requestScope.pb.pc > 1 }"><%--第一页判断 --%>
			<a href="<c:url value='/CustomerServlet?method=findAll&pc=${requestScope.pb.pc-1 }'/>">上一页</a>
		</c:if>
		<%--整个 choose标签都是在计算开始与结束页,我们限定页码列表长度为10,当前页码为第6个 --%>
		<c:choose>
			<%--总页数不足10页,那么把所有的页数都显示出来 --%>
			<c:when test="${requestScope.pb.tp <=10 }">
				<c:set var="begin" value="1"/>
				<c:set var="end" value="${requestScope.pb.tp }"/>
			</c:when>
			<%--当总页数大于10 的时候,通过公式计算出begin和end --%>
			<c:otherwise>
				<c:set var="begin" value="${requestScope.pb.pc - 5 }"/>
				<c:set var="end" value="${requestScope.pb.pc+4 }"/>
				<%-- 头溢出--%>
				<c:if test="${begin < 1 }">
					<c:set var="begin" value="1"/>
					<c:set var="end" value="10"/>
				</c:if>
				<%-- 尾溢出--%>
				<c:if test="${end > pb.tp }">
					<c:set var="begin" value="${requestScope.pb.tp - 9 }"/>
					<c:set var="end" value="${requestScope.pb.tp }"/>
				</c:if>
			</c:otherwise>
		</c:choose>
		<%--页码计算结束 --%>
		
		<%-- 循环遍历页码列表 --%>
		<c:forEach var="x" begin="${begin }" end="${end }">
			<c:choose>
				<c:when test="${x eq pb.pc }"><%--判断当前页不显示为超链接 --%>
					[${x }]
				</c:when>
				<c:otherwise><%--非当前页,显示超链接 --%>
					<a href="<c:url value='/CustomerServlet?method=findAll&pc=${x }'/>">[${x }]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<%-- 下一页与最后一页 --%>
		<c:if test="${requestScope.pb.pc < requestScope.pb.tp }"><%--最后一页判断 --%>
			<a href="<c:url value='/CustomerServlet?method=findAll&pc=${requestScope.pb.pc+1 }'/>">下一页</a>
		</c:if>
		<a href="<c:url value='/CustomerServlet?method=findAll&pc=${requestScope.pb.tp }'/>">尾页</a>
	</center>


======================== js算法

//预定义开始
var begin = 0;

//预定义结束
var end = 0;

if(pageInfo.totalPage <= 10){
	begin = 1;
	end = pageInfo.totalPage;
}else{
	begin = pageInfo.page - 5;
	end = pageInfo.page + 4;
	if(begin < 1){
		begin = 1;
		end = 10;
	}
	if(end > pageInfo.totalPage){
		begin = pageInfo.totalPage - 9;
		end = pageInfo.totalPage;
	}	
}

if(begin > 1){
	//生成首页摁钮
}
if(pageInfo.page > 1){
	//生成上一页摁钮
}

for(let i = begin ; i <= end; i++){
	//生成页码摁钮
}

if(pageInfo.totalPage > pageInfo.page){
	//生成下一页摁钮
}

if(pageInfo.totalPage > end ){
	//生成尾页摁钮
}