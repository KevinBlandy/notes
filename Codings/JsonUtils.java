package com.kevin.blog.utils;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author KevinBlandy
 *
 */
public class JsonUtils {

	public static <T> String beanToJson(T bean,SerializerFeature ...serializerFeatures) {
		return JSON.toJSONString(bean, GeneralUtils.isEmpty(serializerFeatures) ? new SerializerFeature[] {SerializerFeature.PrettyFormat, SerializerFeature.QuoteFieldNames} : serializerFeatures);
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> toArray(String json, Class<T> clazz) {
		try {
			return JSON.parseArray(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
