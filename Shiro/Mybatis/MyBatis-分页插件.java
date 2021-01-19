import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;



在myabtis配置文件中添加如下插件配置
	<plugins>
		<plugin interceptor="com.github.miemiedev.mybatis.paginator.OffsetLimitInterceptor">
		<property name="dialectClass" value="com.github.miemiedev.mybatis.paginator.dialect.MySQLDialect"/>
		</plugin>
	</plugins>
	* 指明数据库方言




	setContainsTotalCount 这个方法很重要.设置为true,才可以有分页信息



package com.kevin.blog.comm.utils;
import java.util.ArrayList;
import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.Order.Direction;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
/**
 * 分页工具
 * */
public class PageUtils {
	/**
	 * 获取一个PageBounds
	 * */
	public static PageBounds getPageBounds(Integer pageCode,Integer limit,String... cloum){
		PageBounds page = new PageBounds();
		if(pageCode == null || pageCode < 1){
			pageCode = 1;
		}
		//当前页
		page.setPage(pageCode);
		//每页显示数据
		page.setLimit(limit);
		if(cloum != null && cloum.length > 0){
			List<Order> orders = new ArrayList<Order>();
			//存在一个,或者多个排序字段
			for(String str : cloum){
				Order order = new Order(str,Direction.DESC,null);
				orders.add(order);
			}
			page.setOrders(orders);
		}
		//需要查询用记录数
		page.setContainsTotalCount(true);
		//禁止异步查询分页数据
		page.setAsyncTotalCount(false);
		return page;
	}
}




当然需要注意的是，只要你用到了异步查询(page.setContainsTotalCount(true))，由于里面使用了线程池，所以在使用时就要加入清理监听器，以便在停止服务时关闭线程池。需要在web.xml中加入

<listener>  
    <listener-class>com.github.miemiedev.mybatis.paginator.CleanupMybatisPaginatorListener</listener-class>  
</listener>  


附上maven仓库坐标地址:
	<dependency>
		<groupId>com.github.miemiedev</groupId>
		<artifactId>mybatis-paginator</artifactId>
		<version>${mybatis-paginator.version}</version>
	</dependency>