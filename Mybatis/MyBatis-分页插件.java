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
	public static PageBounds getPageBounds(int pageCode,int pageSize,Order...orders){
		PageBounds pageBounds = new PageBounds(pageCode,pageSize);
		if(orders != null && orders.length > 0){
			//存在一个或者多个排序策略
			pageBounds.setOrders(Arrays.asList(orders));
		}
		//需要查询总数量
		pageBounds.setContainsTotalCount(Boolean.TRUE);
		//禁止异步查询总数量
		pageBounds.setAsyncTotalCount(Boolean.FALSE);
		return pageBounds;
	}
	/**
	 * 获取一组排序策略
	 * sorts和orders分别是排序的字段,和排序的策略.他们应该是一一对应
	 */
	public Order[] getOrders(String[] sorts,String[] orders){
		List<Order> result = new ArrayList<Order>();
		if(!this.isEmpty(sorts)){
			if(this.isEmpty(orders)){
				//未存在排序策略,默认全部为:DESC
				for(String sort : sorts){
					if(!isEmpty(sort)){
						result.add(this.getOrderDesc(sort));
					}
				}
			}else{
				//存在排序策略
				for(int x = 0 ;x < sorts.length ; x++){
					if(x < orders.length){
						if("ASC".equalsIgnoreCase(orders[x])){
							result.add(this.getOrderAsc(sorts[x]));		//ASC排序
						}else{
							result.add(this.getOrderDesc(sorts[x]));	//DESC排序
						}
					}else{
						result.add(this.getOrderDesc(sorts[x]));		//sorts未定义排序策略部分全部默认为:DESC
					}
				}
			}
		}
		return result.toArray(new Order[result.size()]);
	}
	/**
	 * 获取一个Order,正序排序
	 */
	public Order getOrderDesc(String field){
		return new Order(field,Direction.DESC,null);
	}
	/**
	 * 获取一个Order,逆序排序
	 */
	public Order getOrderAsc(String field){
		return new Order(field,Direction.ASC,null);
	}
}




当然需要注意的是，只要你用到了异步查询(page.setContainsTotalCount(true))，
由于里面使用了线程池，所以在使用时就要加入清理监听器，以便在停止服务时关闭线程池。需要在web.xml中加入

<listener>  
    <listener-class>com.github.miemiedev.mybatis.paginator.CleanupMybatisPaginatorListener</listener-class>  
</listener>  


附上maven仓库坐标地址:
	<dependency>
		<groupId>com.github.miemiedev</groupId>
		<artifactId>mybatis-paginator</artifactId>
		<version>${mybatis-paginator.version}</version>
	</dependency>