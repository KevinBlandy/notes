package io.springboot.demo.utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XssCleaner {
	// 允许使用的标签
	private List<String> allowTags;
	// 允许使用的html属性
	private List<String> allowAttributes;
	// 允许特殊标签使用的特殊属性
	private Map<String, List<String>> specialAttributes;
	// 指定标签设置的特殊属性
	private Map<String, Map<String, String>> fixedAttributes;
	// 允许指定标签，指定属性使用的协议
	private Map<String, Map<String, List<String>>> protocols;

	public String clean(String content) {

		Safelist safelist = Safelist.none();
		safelist.addTags(toArray(allowTags));
		safelist.addAttributes(":all", toArray(allowAttributes));
		specialAttributes.entrySet().forEach(entry -> {
			safelist.addAttributes(entry.getKey(), toArray(entry.getValue()));
		});
		fixedAttributes.entrySet().forEach(entry -> {
			entry.getValue().entrySet().forEach(item -> {
				safelist.addEnforcedAttribute(entry.getKey(), item.getKey(), item.getValue());
			});
		});
		protocols.entrySet().forEach(entry -> {
			entry.getValue().entrySet().forEach(item -> {
				safelist.addProtocols(entry.getKey(), item.getKey(), toArray(item.getValue()));
			});
		});

		return Jsoup.clean(content, "", safelist,
				new Document.OutputSettings().prettyPrint(false).charset(StandardCharsets.UTF_8));
	}

	private String[] toArray(List<String> list) {
		return list == null ? new String[0] : list.stream().toArray(String[]::new);
	}
}

// main--------------------------------------------------

package io.springcloud.test;

import java.util.List;
import java.util.Map;

import io.springboot.demo.utils.XssCleaner;
/*
<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
<dependency>
	<groupId>org.jsoup</groupId>
	<artifactId>jsoup</artifactId>
	<version>1.14.3</version>
</dependency>
*/
public class MainTest {

	public static void main(String[] args) throws Exception {
		String content = """
					<div class="content" style="width: 100px;">
					
						<a src="https://springcloud.io">springcloud</a>
						
						<script>
							console.log("Hi");
						</script>
						
						<img src="https://springcloud.io/logo.png"/>
						
						<a href="javascript:alert();">click this link</a>
						
						<a href="mailto:admin@springcloud.io">Email</a>
					</div>
				""";
		
		String result = XssCleaner.builder()
				.allowTags(List.of("a", "abbr", "acronym", "address", "area", "article", "aside", "audio", "b", "bdi",
						"big", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "datalist", "dd",
						"del", "details", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "h1",
						"h2", "h3", "h4", "h5", "h6", "hr", "i", "img", "li", "ins", "ol", "p", "pre", "q", "ul",
						"small", "span"))
				.allowAttributes(List.of("style", "title"))
				.specialAttributes(Map.of("a", List.of("href"), "img", List.of("src")))
				.fixedAttributes(Map.of("a", Map.of("rel", "nofollow")))
				.protocols(Map.of("a", Map.of("href", List.of("#","http","https","ftp","mailto")),
						"blockquote", Map.of("cite", List.of("http","https")),
						"cite", Map.of("cite", List.of("http","https")),
						"q", Map.of("cite", List.of("http","https"))))
				.build().clean(content);

		System.out.println(result);
	}
}
