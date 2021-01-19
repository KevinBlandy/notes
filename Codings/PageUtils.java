package com.kevinblandy.simple.webchat.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * 分页工具类
 * @author	KevinBlandy
 * @version	1.0
 * @date	2017年5月11日 下午8:59:01
 */
public class PageUtils {
	 /**
     * 默认页码
     */
    private static final Integer DEFAULT_PAGE = 1;

    /**
     * 默认每页显示数量
     */
    private static final Integer DEFAULT_ROWS = 10;

    /**
     * 获取一个pageBounds,需要检索总记录数
     * @param page
     * @param rows
     * @param orders
     * @return
     */
    public static PageBounds getPageBounds(Integer page, Integer rows, Order... orders){
        return PageUtils.getPageBounds(page,rows,true,orders);
    }
    
    /**
     * 获取一个pageBounds,不需要检索总记录数
     * @param page
     * @param rows
     * @param orders
     * @return
     */
    public static PageBounds getPageBoundsNoneTotalCount(Integer page, Integer rows, Order... orders){
        return PageUtils.getPageBounds(page,rows,false,orders);
    }
    
    /**
     * 获取PageBounds
     * @param page              页码
     * @param rows              每页记录数
     * @param totalCount        是否检索总记录数
     * @param orders
     * @return
     */
    public static PageBounds getPageBounds(Integer page, Integer rows,boolean totalCount, Order... orders){
        if(page == null || page < 1){
            page = DEFAULT_PAGE;
        }
        if(rows == null || rows < 1){
            rows = DEFAULT_ROWS;
        }
        PageBounds pageBounds = new PageBounds(page,rows);
        if(!GeneralUtils.isEmpty(orders)){
            //存在一个或者多个排序字段
            pageBounds.setOrders(Arrays.asList(orders));
        }
        //是否检索总记录数
        pageBounds.setContainsTotalCount(totalCount);
        //禁止异步检索总记录数
        pageBounds.setAsyncTotalCount(Boolean.FALSE);
        return pageBounds;
    }
   
    /**
     * 获取一组排序策略
     * @param sorts     字段s
     * @param orders    策略s(ASC/DESC)
     * @return
     */
    public static Order[] getOrders(String[] sorts,String[] orders){
        List<Order> result = new ArrayList<Order>();
        if(!GeneralUtils.isEmpty(sorts)){
            //确定存在排序字段
            if(GeneralUtils.isEmpty(orders)){
                //没有任何排序策略,默认全部为DESC
                for(String sort : sorts){
                    if(!GeneralUtils.isEmpty(sort)){
                        result.add(getOrderDesc(sort));
                    }
                }
            }else{
                for(int x = 0 ;x < sorts.length ; x++){
                    if(GeneralUtils.isEmpty(sorts[x])){
                        continue;
                    }
                    if(x < orders.length){
                        if("ASC".equalsIgnoreCase(orders[x])){
                            //ASC
                            result.add(getOrderAsc(sorts[x]));
                        }else{
                            //DESC
                            result.add(getOrderDesc(sorts[x]));
                        }
                    }else{
                        //排序策略未定义部分,默认为DESC
                        result.add(getOrderDesc(sorts[x]));
                    }
                }
            }
        }
        return result.toArray(new Order[result.size()]);
    }

    /**
     * 获取一个逆序的ORDER
     * @param filed
     * @return
     */
    public static Order getOrderDesc(String filed){
        return new Order(filed, Order.Direction.DESC,null);
    }

    /**
     * 获取一个正序的ORDER
     * @param filed
     * @return
     */
    public static Order getOrderAsc(String filed){
        return new Order(filed, Order.Direction.ASC,null);
    }
}
