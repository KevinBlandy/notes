import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (?<name>@[0-9a-zA-Z_\\u4e00-\\u9fa5]{2,14})(\\s+|:|：|，|,|\\.|。|!|！|;|；|\\)|）|】|]|-|\\||&nbsp;|》|}|｝|$|&lt;|&gt;|<)
 *
 * @author KevinBlandy
 *
 */
public class MainTest {
	
	// 提到用户的html模版
	static final String HTML_MENTION_TEMPLATE = "<a target=\"_blank\" href=\"/user/{0}\" id=\"{1}\">{2}</a>";
	
	// 提到的编码
	static final String ENCODE_REG = "(?<name>@[0-9a-zA-Z_\\u4e00-\\u9fa5]{2,14})(\\s+|$)";
	static final Pattern ENCODE_PATTERN = Pattern.compile(ENCODE_REG);
	
	// 提到的解码。
	static final String DECODE_REG = "(?<tag><a target=\"_blank\" href=\"/user/\\d+\" id=\"[a-z0-9]{32}\">@[0-9a-zA-Z_\\u4e00-\\u9fa5]{2,14}</a>)";
	static final Pattern DECODE_PATTERN = Pattern.compile(DECODE_REG);
	
	public static void main(String[] args) {
		String raw = "你好啊@KevinBla_ndy 这里是SpringBoot中文社区，@搞个大新闻 @Hello_Java开发者";
		System.out.println("原始字符串：" + raw);
		
		String result = encode(raw);
		System.out.println("编码后：" + result);
		
		result = decode(result);
		System.out.println("解码后：" + result);
		
		System.out.println("是否和原始字符串相等：" + result.equals(raw));
	}


	public static String encode(String content) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		Matcher matcher = ENCODE_PATTERN.matcher(content);

		int lastIndex = 0;

		while (matcher.find()) {

			String name = matcher.group("name");

			int start = matcher.start("name");

			int end = matcher.end("name");

			stringBuilder.append(content.substring(lastIndex, start));
			
			String anchorId = UUID.randomUUID().toString().replace("-", "");

			stringBuilder.append(MessageFormat.format(HTML_MENTION_TEMPLATE, 1 + "", anchorId, name));

			lastIndex = end;
		}

		stringBuilder.append(content.substring(lastIndex));

		return stringBuilder.toString();
	}
	
	public static String decode(String content) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		Matcher matcher = DECODE_PATTERN.matcher(content);
		
		int lastIndex = 0;

		while (matcher.find()) {
			
			String tag = matcher.group("tag");

			int start = matcher.start("tag");

			int end = matcher.end("tag");
			
			stringBuilder.append(content.substring(lastIndex, start));
			
			String name = tag.substring(tag.indexOf(">") + 1, tag.lastIndexOf("<"));
			
			stringBuilder.append(name);

			lastIndex = end;
		}

		stringBuilder.append(content.substring(lastIndex));

		return stringBuilder.toString();
	}
}
