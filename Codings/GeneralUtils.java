package com.kevin.blog.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用工具类
 * @author	KevinBlandy
 * @version	1.0
 * @date	2017年5月11日 下午9:08:21
 */
public class GeneralUtils {
	
    private static final int[] NUMBERS = {0,1,2,3,4,5,6,7,8,9};
    
    private static final String[] LETTERS = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t", "u","v","w","x","y","z"};
    
    private static final String AJAX_HEADER_NAME = "x-requested-with";
	
	private static final String AJAX_HEADER_VALUE="XMLHttpRequest";
	
	private static final String OPTIONS_REQUEST_METHOD = "OPTIONS";
    
    /**
     * 数组是否为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] t){
        return t == null || t.length == 0;
    }

    /**
     * 字符串是否为空,或者为""
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        return string == null || string.trim().isEmpty();
    }

    /**
     * Set集合是否为空
     * @param set
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Set<T> set){
        return set == null || set.isEmpty();
    }

    /**
     * Collection是否为空
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> collection){
        return collection == null || collection.isEmpty();
    }

    /**
     * Map是否为空
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> boolean isEmpty(Map<K,V> map){
        return map == null || map.isEmpty();
    }
    
    /**
     * 获取32位无符号大写UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    
    /**
     * 获取0-9随机字符串,自定义长度
     * @param length
     * @return
     */
    public static String getRandomNum(int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int x = 0;x < length ; x++){
            sb.append(NUMBERS[random.nextInt(NUMBERS.length)]);
        }
        return sb.toString();
    }
    
    /**
     * 获取a-z随机字符串,自定义长度
     * @param length
     * @return
     */
    public static String getRandomLetter(int length){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int x = 0;x < length ; x++){
            sb.append(LETTERS[random.nextInt(LETTERS.length)]);
        }
        return sb.toString();
    }
    
    /**
     * 判断是否是Ajax异步请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
    	String header = request.getHeader(AJAX_HEADER_NAME);
	    return header == null ? false : header.equalsIgnoreCase(AJAX_HEADER_VALUE);
    }
    
    /**
     * 是否是OPTIONS请求
     * @param request
     * @return
     */
    public static boolean isOptionsRequest(HttpServletRequest request) {
    	return request.getMethod().equalsIgnoreCase(OPTIONS_REQUEST_METHOD);
    }
    
    /**
     * 对字符串进行Base64编码
     * @param data
     * @return
     */
    public static String base64Encode(String data){
        return new String(Base64.getEncoder().encode(data.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
    }

    /**
     * 对指定Base64编码字符串进行解码
     * @param data
     * @return
     */
    public static String base64Decode(String data){
        return new String(Base64.getDecoder().decode(data),StandardCharsets.UTF_8);
    }
}
