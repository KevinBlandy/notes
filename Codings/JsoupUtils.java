package io.javaweb.community.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by KevinBlandy on 2017/7/18 17:38
 */
public class JsoupUtils {
	
    private static final Whitelist NONE_WHITELIST = Whitelist.none();

    private static final Logger LOGGER = LoggerFactory.getLogger(JsoupUtils.class);

    // 过滤配置
    private static final Document.OutputSettings outputsettings = new Document.OutputSettings();

    /**
     * 过滤XSS字符
     * @param content
     * @return
     */
    public static String clean(String content) {
        return content == null ? null : Jsoup.clean(content, "", NONE_WHITELIST, outputsettings);
    }

    static {
    	//过滤配置参数
    	outputsettings.prettyPrint(false);
    	outputsettings.charset(StandardCharsets.UTF_8);
    	
    	//读取配置JSON文件
        Resource resource = new ClassPathResource("xss-white.json");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line.trim());
            }

            JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());

            //允许标签
            JSONArray tags = jsonObject.getJSONArray("allow_tags");
            NONE_WHITELIST.addTags(tags.toArray(new String[tags.size()]));
            LOGGER.debug("允许标签:{}", tags);

            //允许属性
            JSONArray properties = jsonObject.getJSONArray("allow_properties");
            NONE_WHITELIST.addAttributes(":all",properties.toArray(new String[properties.size()]));
            LOGGER.debug("允许属性:{}",properties);

            //允许特殊属性
            JSONObject specialProperties = jsonObject.getJSONObject("special_properties");
            specialProperties.keySet().stream().forEach(tag -> {
                JSONArray attributes = specialProperties.getJSONArray(tag);
                NONE_WHITELIST.addAttributes(tag,attributes.toArray(new String[attributes.size()]));
                LOGGER.debug("允许特殊属性:标签={},属性={}",tag,attributes);
            });

            //允许特殊协议
            JSONObject protocols = jsonObject.getJSONObject("protocols");
            protocols.keySet().stream().forEach(tag -> {
                JSONObject protoObject = protocols.getJSONObject(tag);
                protoObject.keySet().stream().forEach(attr -> {
                    JSONArray protocolValues = protoObject.getJSONArray(attr);
                    NONE_WHITELIST.addProtocols(tag,attr,protocolValues.toArray(new String[protocolValues.size()]));
                    LOGGER.debug("允许特殊协议:标签={},属性={},协议={}",tag,attr,protocolValues);
                });
            });
            
            //固定属性值,非必须的
            JSONObject fixedProperties = jsonObject.getJSONObject("fixed_properties");
            if(fixedProperties != null && !fixedProperties.isEmpty()) {
            	fixedProperties.keySet().stream().forEach(tag -> {
            		JSONObject property = fixedProperties.getJSONObject(tag);
            		if(property != null && !property.isEmpty()) {
            			property.keySet().stream().forEach(attr -> {
            				String value = property.getString(attr);
            				NONE_WHITELIST.addEnforcedAttribute(tag, attr, value);
            				LOGGER.debug("强制属性:标签={},属性={},值={}",tag,attr,value);
            			});
            		}
                 });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载XSS过滤白名单异常,请检查文件 xss-white.json");
        }
    }
}
/*
	{	
	    "allow_tags": [
	        "a","abbr","acronym","address","area","article","aside","audio",
	        "b","bdi","big","blockquote","br",
	        "caption","cite","code","col","colgroup",
	        "datalist","dd","del","details","div","dl","dt",
	        "em",
	        "fieldset","figcaption","figure","footer",
	        "h1","h2","h3","h4","h5","h6","hr",
	        "i","img","li","ins",
	        "ol",
	        "p","pre",
	        "q",
	        "ul",
	        "small","span"
	    ],
	    "allow_properties":[
	        "style","title"
	    ],
	    "special_properties": {
	        "a":["href"],
	        "img":["src"]
	    },
	    "protocols": {
	        "a": {
	            "href":["#","http","https","ftp","mailto"]
	        },
	        "blockquote":{
	        	"cite":["http","https"]
	        },
	        "cite":{
	        	"cite":["http","https"]
	        },
	        "q":{
	        	"cite":["http","https"]
	        }
	    },
	    "fixed_properties":{
	    	"tag":{
	    		"attr":"value"
	    	}
	    },
	    "desc":{
	        "allow_tags":"仅仅允许使用的标签",
	        "allow_properties":"所有标签都允许使用的属性",
	        "special_properties":"特殊标签允许使用的特殊属性",
	        "protocols":"特殊标签,特殊属性,仅仅允许使用指定的协议",
	        "fixed":"特殊标签,必须会添加的固定属性与值(会覆盖原来的)"
	    }
	}
*/

// Gson 版本 ---------------------------
package io.zebra.common.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by KevinBlandy on 2017/7/18 17:38
 */
public class JsoupUtils {

	private static final Whitelist WHITELIST = Whitelist.none();

	private static final Logger LOGGER = LoggerFactory.getLogger(JsoupUtils.class);

	// 过滤配置
	private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings();

	/**
	 * 过滤XSS字符
	 * 
	 * @param content
	 * @return
	 */
	public static String clean(String content) {
		return content == null ? null : Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
	}

	static {
		// 过滤配置参数
		OUTPUT_SETTINGS.prettyPrint(false);
		OUTPUT_SETTINGS.charset(StandardCharsets.UTF_8);

		// 读取配置JSON文件
		Resource resource = new ClassPathResource("xss-white.json");
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line.trim());
			}

			JsonObject config = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();

			// 允许标签
			JsonArray allowTags = config.getAsJsonArray("allow_tags");
			WHITELIST.addTags(toArray(allowTags));
			LOGGER.debug("允许标签:{}", allowTags);

			// 允许属性
			JsonArray allowProperties = config.getAsJsonArray("allow_properties");
			WHITELIST.addAttributes(":all", toArray(allowProperties));
			LOGGER.debug("允许属性:{}", allowProperties);

			// 允许特殊属性
			JsonObject specialProperties = config.getAsJsonObject("special_properties");
			specialProperties.entrySet().forEach(entry -> {
				String tag = entry.getKey();
				JsonArray attributes = entry.getValue().getAsJsonArray();
				WHITELIST.addAttributes(tag, toArray(attributes));
				LOGGER.debug("允许特殊属性:标签={}, 属性={}", tag, attributes);
			});

			// 允许特殊协议
			JsonObject protocolConfig = config.getAsJsonObject("protocols");
			protocolConfig.entrySet().forEach(entry -> {
				String tag = entry.getKey();
				JsonObject attrsConfig = entry.getValue().getAsJsonObject();
				;
				attrsConfig.entrySet().forEach(_entry -> {
					String attr = _entry.getKey();
					JsonArray protocols = _entry.getValue().getAsJsonArray();
					WHITELIST.addProtocols(tag, attr, toArray(protocols));
					LOGGER.debug("允许特殊协议:标签={}, 属性={},协议={}", tag, attr, protocols);
				});
			});

			// 固定属性值,非必须的
			JsonObject fixedProperties = config.getAsJsonObject("fixed_properties");
			if (fixedProperties != null && fixedProperties.size() > 0) {
				fixedProperties.entrySet().forEach(entry -> {
					String tag = entry.getKey();
					JsonObject properties = entry.getValue().getAsJsonObject();
					properties.entrySet().forEach(prop -> {
						String key = prop.getKey();
						String value = prop.getValue().getAsString();
						WHITELIST.addEnforcedAttribute(tag, key, value);
						LOGGER.debug("强制属性:标签={},属性={},值={}", tag, key, value);
					});
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("加载XSS过滤白名单异常：" + e.getMessage());
		}
	}

	private static String[] toArray(JsonElement jsonElement) {
		if (jsonElement.isJsonArray()) {
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			String[] retVal = new String[jsonArray.size()];
			for (int i = 0; i < jsonArray.size(); i++) {
				retVal[i] = jsonArray.get(i).getAsString();
			}
			return retVal;
		}
		return null;
	}
}
/*
	{	
	    "allow_tags": [
	        "a","abbr","acronym","address","area","article","aside","audio",
	        "b","bdi","big","blockquote","br",
	        "caption","cite","code","col","colgroup",
	        "datalist","dd","del","details","div","dl","dt",
	        "em",
	        "fieldset","figcaption","figure","footer",
	        "h1","h2","h3","h4","h5","h6","hr",
	        "i","img","li","ins",
	        "ol",
	        "p","pre",
	        "q",
	        "ul",
	        "small","span"
	    ],
	    "allow_properties":[
	        "style","title"
	    ],
	    "special_properties": {
	        "a":["href"],
	        "img":["src"]
	    },
	    "protocols": {
	        "a": {
	            "href":["#","http","https","ftp","mailto"]
	        },
	        "blockquote":{
	        	"cite":["http","https"]
	        },
	        "cite":{
	        	"cite":["http","https"]
	        },
	        "q":{
	        	"cite":["http","https"]
	        }
	    },
	    "fixed_properties":{
	    	"tag":{
	    		"attr":"value"
	    	}
	    },
	    "desc":{
	        "allow_tags":"仅仅允许使用的标签",
	        "allow_properties":"所有标签都允许使用的属性",
	        "special_properties":"特殊标签允许使用的特殊属性",
	        "protocols":"特殊标签,特殊属性,仅仅允许使用指定的协议",
	        "fixed":"特殊标签,必须会添加的固定属性与值(会覆盖原来的)"
	    }
	}


 */