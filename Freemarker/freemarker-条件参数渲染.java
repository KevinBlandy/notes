import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriUtils;

import freemarker.template.SimpleHash;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author Administrator
 *
 */
public class QueryParamRenderFunction implements TemplateMethodModelEx {
	
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		
		String url = ((SimpleScalar)arguments.get(0)).getAsString();
		
		if (arguments.size() > 1) {
			
			@SuppressWarnings("unchecked")
			Map<String, String[]> params = ((SimpleHash)arguments.get(1)).toMap();
			
			if (!params.isEmpty()) {
				
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(url);
				
				if (url.lastIndexOf("?") == -1) {
					stringBuilder.append("?");
				}else if (!url.endsWith("&")){
					stringBuilder.append("&");
				}
				
				for (Map.Entry<String, String[]> entry : params.entrySet()) {
					String key = entry.getKey();
					String[] values = entry.getValue();
					if (values != null && values.length > 0) {
						for (String value : values) {
							stringBuilder.append(key).append("=").append(UriUtils.encode(value, StandardCharsets.UTF_8)).append("&");
						}
					} else {
						stringBuilder.append(key).append("=").append("").append("&");
					}
				}
				
				return stringBuilder.substring(0, stringBuilder.length() - 1);
			}
		}
		
		return url;
	}
}


// 配置
this.configuration.setSharedVariable("_queryParam", new QueryParamRenderFunction());

// Servlet 数据
Map<String, String[]> parameter = request.getParameterMap();
parameter.remove("page");
parameter.remove("rows");


ModelAndView modelAndView = new ModelAndView("test/test");
modelAndView.addObject("params", parameter);
modelAndView.addObject("page", page);
modelAndView.addObject("rows", rows);

// 页面使用
${_queryParam("/test?page=${page + 1}&rows=${rows + 1}", params)}