------------------------
http
------------------------
	# java9提供的
	# 核心组件
		HttpClient 
		HttpRequest
		HttpResponse
		HttpHeaders
		WebSocket
		
		* 这些组件中的属性, 大都是'只读的', 只提供了 getter 方法
		* 初始化的时候需要通过 builder 来进行属性的 set

------------------------
http - GET
------------------------
	# GET
		

	# 下载
		

------------------------
http - POST
-----------------------
	// 创建 Http 客户端
	HttpClient httpClient = HttpClient.newHttpClient();
	
	// 创建请求和请求体
	HttpRequest request = HttpRequest
				.newBuilder(new URI("http://localhost/test"))
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.header("Accept", "application/json")   
				.POST(BodyPublishers.ofString("name=KevinBlandy"))
				.build();
	
	// 创建响应处理器
	BodyHandler<String> bodyHandler = BodyHandlers.ofString(StandardCharsets.UTF_8);
	
	// 执行请求，获取响应
	HttpResponse<String> responseBody = httpClient.send(request, bodyHandler);
	
	System.out.println(responseBody);

------------------------
http - 文件表单体
-----------------------
	# 没有现成的mutipart编码api，需要依赖
		<!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime -->
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpmime</artifactId>
		</dependency>

	# 实现
		import java.io.ByteArrayOutputStream;
		import java.io.File;
		import java.net.URI;
		import java.net.http.HttpClient;
		import java.net.http.HttpRequest;
		import java.net.http.HttpRequest.BodyPublishers;
		import java.net.http.HttpResponse;
		import java.net.http.HttpResponse.BodyHandler;
		import java.net.http.HttpResponse.BodyHandlers;
		import java.nio.charset.StandardCharsets;

		import org.apache.http.HttpEntity;
		import org.apache.http.entity.ContentType;
		import org.apache.http.entity.mime.MultipartEntityBuilder;
		import org.apache.http.entity.mime.content.StringBody;
		import org.springframework.web.util.UriUtils;


		public class MainTest {
			public static void main(String[] args) throws Exception {
				
				
				HttpEntity httpEntity = MultipartEntityBuilder.create()
						.addPart("name", new StringBody(UriUtils.encode("SpringBoot中文社区", StandardCharsets.UTF_8), ContentType.APPLICATION_FORM_URLENCODED))
						.addPart("info", new StringBody("{\"site\": \"https://springboot.io\", \"year\": 2019}", ContentType.APPLICATION_JSON))
						.addBinaryBody("logo", new File("D:\\logo.png"), ContentType.IMAGE_PNG, "logo.png")
						.build();
				
				// 如果内容过大，可以考虑IO到磁盘而不是内存
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) httpEntity.getContentLength());
				httpEntity.writeTo(byteArrayOutputStream);
				
				HttpClient httpClient = HttpClient.newHttpClient();
				
				// 创建请求和请求体
				HttpRequest request = HttpRequest
							.newBuilder(new URI("http://localhost/upload"))
							// 设置ContentType
							.header("Content-Type", httpEntity.getContentType().getValue())
							.header("Accept", "application/json")   
							.POST(BodyPublishers.ofByteArray(byteArrayOutputStream.toByteArray()))
							.build();
				
				// 创建响应处理器
				BodyHandler<String> bodyHandler = BodyHandlers.ofString(StandardCharsets.UTF_8);
				
				// 执行请求，获取响应
				HttpResponse<String> responseBody = httpClient.send(request, bodyHandler);
				
				System.out.println(responseBody);
			}
		}
